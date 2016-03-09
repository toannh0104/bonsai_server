// /////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CALL_lessonsStruct {
    private CALL_database db;
    private List<CALL_lessonStruct> lessons;

    public CALL_lessonsStruct(CALL_database database) {
        db = database;
        lessons = new ArrayList<>();
    }

    // Load the lesson structures from file
    public boolean load(String filename) {
        // For loading the text data
        String tempString;
        String tempString2;
        String readLine;
        StringTokenizer st;
        String parsedString[];
        String commandString;
        String dataString;
        String centerString;

        int tempInt;
        double tempDouble;

        // For creating the new structures
        CALL_lessonStruct newLesson = null;
        CALL_lessonQuestionStruct newLessonQuestion = null;
        CALL_lessonConceptStruct newLessonConcept = null;
        CALL_stringPairStruct newParameter = null;
        CALL_lessonExampleStruct newExample = null;

        CALL_formDescGroupStruct newFormGroup = null;
        CALL_formDescStruct newFormDesc = null;

        CALL_diagramStruct newDiagram = null;
        CALL_diagramGroupStruct newGfxGroup = null;
        CALL_diagramComponentStruct newGfx = null;
        CALL_stringPairsStruct newRestrictions = null;
        CALL_stringPairStruct newRestriction = null;

        boolean rc = false;
        FileReader in;

        // Keep track of what we've loaded
        boolean b_forms = false;
        boolean b_example = false;
        boolean b_diagram = false;

        String helpString = null;

        int readState;
        int prevState;

        // State definitions
        final int initialState = 0;
        final int lessonState = 1;
        final int questionState = 2;
        final int conceptState = 3;
        final int formGroupState = 4;
        final int formDescState = 5;
        final int diagramState = 6;
        final int gfxGroupState = 7;
        final int gfxState = 8;
        final int gfxRestrictState = 9;
        final int exampleState = 10;
        final int exampleFormState = 11;
        final int exampleFormGrpState = 12;
        final int formHelpState = 13;
        final int gfxHelpState = 14;
        final int diagHelpState = 15;
        final int errorState = 16;
        final int endState = 17;

        CALL_stringPairsStruct newEgFormGroup = null;

        // Open file
        try {
            InputStream inputStream = new FileInputStream(filename);
            Reader reader = new InputStreamReader(inputStream, "UTF8");
            BufferedReader bf = new BufferedReader(reader);

            // Read Line by line
            prevState = -1;
            readState = initialState;

            // 2011.04.01 T.Tajima add(readingCount,try-catch)
            int readingCount = 0;
            try {
                while (readState < errorState) {
                    readLine = bf.readLine().trim();
                    readingCount++;
                    if (readLine == null) {
                        // Prematurely reached end of file - error, stop reading
                        // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                        // CALL_debug.ERROR,
                        // "Null line in lesson defintion file");
                        readState = errorState;
                    } else if (readLine.startsWith("#")) {
                        // A comment...skip
                        continue;
                    } else if (readLine.startsWith("-eof")) {
                        // Reached end of file
                        if (readState != initialState) {
                            // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                            // CALL_debug.ERROR,
                            // "Unexpected end of lesson defintion file (state "
                            // + readState +
                            // ")");
                            readState = errorState;
                        } else {
                            // File ended appropriately
                            readState = endState;
                        }
                    } else {
                        // We have some valid text, so parse it
                        // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                        // CALL_debug.DEBUG,
                        // "Parsing line [" + readLine + "]");

                        // Get the command
                        parsedString = CALL_io.extractCommand(readLine);
                        commandString = parsedString[0];
                        dataString = parsedString[1];

                        // Into the state machine
                        switch (readState) {
                            case initialState:
                                if (commandString != null) {
                                    if (commandString.equals("lesson")) {
                                        newLesson = new CALL_lessonStruct();
                                        readState = lessonState;
                                    }
                                }
                                break;
                            case lessonState:
                                if (commandString != null) {
                                    st = new StringTokenizer(commandString);
                                    tempString = CALL_io.getNextToken(st);

                                    if (tempString != null) {
                                        if (tempString.equals("/lesson")) {
                                            lessons.add(newLesson);
                                            newLesson = null;
                                            prevState = readState;
                                            readState = initialState;
                                        } else if (tempString.equals("title")) {
                                            newLesson.title = CALL_io.strRemainder(st);
                                        } else if (tempString.equals("id")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                // This is the lesson index
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newLesson.index = tempInt;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            }
                                        } else if (tempString.equals("level")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                // This is the vocal level limit
                                                // of the lesson
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newLesson.setLevel(tempInt, true);
                                                }
                                            } else {
                                                // Error
                                                prevState = readState;
                                                readState = errorState;
                                            }
                                        } else if (tempString.equals("question")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                newLessonQuestion = new CALL_lessonQuestionStruct(tempString);

                                                // Now get the likelihood (if
                                                // specified)
                                                tempString = CALL_io.getNextToken(st);
                                                if (tempString != null) {
                                                    // This is the vocal level
                                                    // limit of the lesson
                                                    tempDouble = CALL_io.str2double(tempString);
                                                    if (tempDouble != CALL_io.INVALID_DBL) {
                                                        newLessonQuestion.weight = tempDouble;
                                                    }

                                                    // Update total concept
                                                    // weight value
                                                    newLesson.questionWeightTotal += newLessonQuestion.weight;
                                                }

                                                prevState = readState;
                                                readState = questionState;
                                                // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                // CALL_debug.DEBUG,
                                                // "Going to question state");
                                            } else {
                                                // Error
                                                prevState = readState;
                                                readState = errorState;
                                            }
                                        } else if (tempString.equals("formgroup")) {
                                            newFormGroup = new CALL_formDescGroupStruct();
                                            prevState = readState;
                                            readState = formGroupState;
                                        } else if (tempString.equals("diagram")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                centerString = CALL_io.getNextToken(st);
                                                if (centerString != null) {
                                                    if (centerString.equals("vcenter")) {
                                                        newDiagram = new CALL_diagramStruct(tempString, true);
                                                    } else {
                                                        newDiagram = new CALL_diagramStruct(tempString, false);
                                                    }
                                                } else {
                                                    newDiagram = new CALL_diagramStruct(tempString, false);
                                                }
                                                prevState = readState;
                                                readState = diagramState;
                                            } else {
                                                // Error
                                                prevState = readState;
                                                readState = errorState;
                                            }
                                        } else if (tempString.equals("example")) {
                                            prevState = readState;

                                            // Create the example object
                                            newExample = new CALL_lessonExampleStruct();

                                            readState = exampleState;
                                        }
                                    }
                                }
                                break;

                            case questionState:
                                // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                // CALL_debug.DEBUG,
                                // "Question State.  Command [" + commandString
                                // + "]");
                                if (commandString != null) {
                                    if (commandString.equals("/question")) {
                                        if (newLessonQuestion != null) {
                                            // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                            // CALL_debug.DEBUG,
                                            // "Adding question");
                                            newLesson.questions.addElement(newLessonQuestion);
                                            newLessonQuestion = null;
                                        }
                                        prevState = readState;
                                        readState = lessonState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);

                                        if (tempString != null) {
                                            if (tempString.equals("concept")) {
                                                tempString = CALL_io.getNextToken(st);
                                                if (tempString != null) {
                                                    newLessonConcept = new CALL_lessonConceptStruct(tempString);

                                                    // Now get the likelihood
                                                    // (if specified)
                                                    tempString = CALL_io.getNextToken(st);
                                                    if (tempString != null) {
                                                        // This is the vocal
                                                        // level limit of the
                                                        // lesson
                                                        tempDouble = CALL_io.str2double(tempString);
                                                        if (tempDouble != CALL_io.INVALID_DBL) {
                                                            newLessonConcept.weight = tempDouble;
                                                        }

                                                        // Update total concept
                                                        // weight value
                                                        newLessonQuestion.conceptWeightTotal += newLessonConcept.weight;
                                                    }

                                                    prevState = readState;
                                                    readState = conceptState;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            }
                                        }
                                    }
                                }
                                break;

                            case conceptState:

                                if (commandString != null) {
                                    if (commandString.equals("/concept")) {
                                        if (newLessonQuestion != null) {
                                            // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                            // CALL_debug.DEBUG,
                                            // "Adding concept to question");
                                            newLessonQuestion.concepts.addElement(newLessonConcept);
                                        }
                                        newLessonConcept = null;
                                        prevState = readState;
                                        readState = questionState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);
                                        if (tempString.equals("text")) {
                                            // The text displayed (in form spec)
                                            // if this concept is
                                            // being used
                                            tempString = CALL_io.strRemainder(st);
                                            newLessonConcept.text = tempString;
                                        } else if (tempString.equals("grammar")) {
                                            // The grammar rule to be used with
                                            // this concept
                                            tempString = CALL_io.getNextToken(st);
                                            newLessonConcept.grammar = tempString;
                                        } else if (tempString.equals("diagram")) {
                                            // The diagram associated with this
                                            // concept
                                            tempString = CALL_io.getNextToken(st);
                                            newLessonConcept.diagram = tempString;
                                        }
                                    }
                                }
                                break;

                            case formGroupState:
                                if (commandString != null) {
                                    if (commandString.equals("/formgroup")) {
                                        newLesson.formGroups.addElement(newFormGroup);
                                        b_forms = true;
                                        newFormGroup = null;
                                        readState = lessonState;
                                    } else if (commandString.equals("question")) {
                                        if (dataString != null) {
                                            // This is a question that allows
                                            // this form
                                            newFormGroup.questions.addElement(dataString);
                                        }
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);

                                        if (tempString.equals("form_desc")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                newFormDesc = (CALL_formDescStruct) new CALL_formDescStruct();
                                                newFormDesc.description = tempString;
                                                prevState = readState;
                                                readState = formDescState;
                                            } else {
                                                // Error
                                                prevState = readState;
                                                readState = errorState;
                                            }
                                        }
                                    }
                                }
                                break;

                            case formDescState:
                                if (commandString != null) {
                                    if (commandString.equals("/form_desc")) {
                                        // Finished form description, add to
                                        // group then move back a
                                        // state
                                        if (newFormDesc != null) {
                                            if (newFormGroup == null) {
                                                // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                // CALL_debug.ERROR,
                                                // "newFormGroup null pointer during load operation");
                                            } else if (newFormGroup.forms == null) {
                                                // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                // CALL_debug.ERROR,
                                                // "newFormGroup contains null vector during load operation");
                                            } else {
                                                newFormGroup.forms.addElement(newFormDesc);
                                                newFormDesc = null;
                                            }
                                        }
                                        prevState = readState;
                                        readState = formGroupState;
                                    }
                                } else if (dataString != null) {
                                    // Add the string as a restriction case
                                    newFormDesc.restrictions.addElement(dataString);
                                }
                                break;

                            // //////////////diagram/////////////////

                            case diagramState:
                                if (commandString != null) {
                                    if (commandString.equals("/diagram")) {
                                        newLesson.diagrams.addElement(newDiagram);
                                        newDiagram = null;
                                        b_diagram = true;
                                        prevState = readState;
                                        readState = lessonState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);

                                        if (tempString.equals("gfxgroup")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                // Do we have centering enabled?
                                                if ((tempString.equals("center")) || (tempString.equals("centre"))) {
                                                    newGfxGroup = newDiagram.add_group(true);
                                                } else {
                                                    newGfxGroup = newDiagram.add_group(false);
                                                }
                                            } else {
                                                // No centering
                                                newGfxGroup = newDiagram.add_group(false);
                                            }

                                            // Finally, update state
                                            prevState = readState;
                                            readState = gfxGroupState;
                                        }
                                    }
                                }
                                break;

                            case gfxGroupState:
                                if (commandString != null) {
                                    if (commandString.equals("/gfxgroup")) {
                                        newGfxGroup = null;
                                        prevState = readState;
                                        readState = diagramState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);

                                        if (tempString.equals("gfx")) {
                                            tempString = CALL_io.getNextToken(st);
                                            if (tempString != null) {
                                                newGfx = new CALL_diagramComponentStruct(tempString);
                                                prevState = readState;
                                                readState = gfxState;
                                            } else {
                                                // Error - gfx object without
                                                // name defined
                                                prevState = readState;
                                                readState = errorState;
                                            }
                                        }
                                    }
                                }
                                break;

                            case gfxState:
                                if (commandString != null) {
                                    if (commandString.equals("/gfx")) {
                                        newGfxGroup.components.addElement(newGfx);
                                        newGfx = null;
                                        readState = gfxGroupState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);
                                        if (tempString != null) {
                                            if (tempString.equals("text")) {
                                                tempString = CALL_io.strRemainder(st);
                                                if (tempString != null) {
                                                    newGfx.text = tempString;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            } else if (tempString.equals("coords")) {
                                                // X
                                                tempString = CALL_io.getNextToken(st);
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newGfx.x = tempInt;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }

                                                // Y
                                                tempString = CALL_io.getNextToken(st);
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newGfx.y = tempInt;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }

                                                // Width
                                                tempString = CALL_io.getNextToken(st);
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newGfx.w = tempInt;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }

                                                // Height
                                                tempString = CALL_io.getNextToken(st);
                                                tempInt = CALL_io.str2int(tempString);
                                                if ((tempInt != CALL_io.INVALID_INT) && (tempInt >= 0)) {
                                                    newGfx.h = tempInt;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            } else if (tempString.equals("image")) {
                                                tempString = CALL_io.strRemainder(st);
                                                if (tempString != null) {
                                                    newGfx.image = tempString;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            } else if (tempString.equals("gfx_data")) {
                                                tempString = CALL_io.strRemainder(st);
                                                if (tempString != null) {
                                                    newGfx.slot = tempString;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            } else if (tempString.equals("gfx_marker")) {
                                                tempString = CALL_io.strRemainder(st);
                                                if (tempString != null) {
                                                    newGfx.marker = tempString;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            }
                                            if (tempString.equals("gfx_marker_text")) {
                                                tempString = CALL_io.strRemainder(st);
                                                if (tempString != null) {
                                                    newGfx.mText = tempString;
                                                } else {
                                                    // Error
                                                    prevState = readState;
                                                    readState = errorState;
                                                }
                                            } else if (tempString.equals("restriction")) {
                                                prevState = readState;
                                                tempString = CALL_io.strRemainder(st);

                                                if (tempString != null) {
                                                    newGfx.restrictions.addElement(tempString);
                                                    newRestrictions = new CALL_stringPairsStruct();
                                                    readState = gfxRestrictState;
                                                } else {
                                                    // Error
                                                    readState = errorState;
                                                }
                                            }
                                        }
                                    }
                                }
                                break;

                            case gfxRestrictState:
                                if (commandString != null) {
                                    if (commandString.equals("/restriction")) {
                                        newGfx.settings.addElement(newRestrictions);
                                        newRestrictions = null;
                                        readState = gfxState;
                                    } else {
                                        // A new restriction setting
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);
                                        if (tempString != null) {
                                            tempString2 = CALL_io.strRemainder(st);
                                            if (tempString2 != null) {
                                                newRestriction = new CALL_stringPairStruct(tempString, tempString2);
                                                newRestrictions.addElement(newRestriction);
                                            }
                                        }
                                    }
                                }
                                break;
                            case exampleState:
                                if (commandString != null) {
                                    st = new StringTokenizer(commandString);
                                    tempString = CALL_io.getNextToken(st);

                                    if (commandString.equals("/example")) {
                                        // Add example to lesson
                                        if ((newExample != null) && (newLesson != null)) {
                                            newLesson.examples.add(newExample);
                                        }

                                        newExample = null;

                                        // Move along
                                        prevState = readState;
                                        readState = lessonState;
                                    } else {
                                        st = new StringTokenizer(commandString);
                                        tempString = CALL_io.getNextToken(st);
                                        // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                        // CALL_debug.DEBUG, "Command string: ["
                                        // + commandString +
                                        // "]");
                                        // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                        // CALL_debug.DEBUG,
                                        // "Command considered: [" + tempString
                                        // +
                                        // "]");

                                        if (tempString != null) {
                                            if (newExample != null) {
                                                if (tempString.equals("diagram")) {
                                                    newExample.imageStr = CALL_io.strRemainder(st);
                                                    // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                    // CALL_debug.DEBUG, "L" +
                                                    // newLesson.index +
                                                    // ", Example Diagram: " +
                                                    // newExample.imageStr);
                                                } else if (tempString.equals("sentence")) {
                                                    newExample.english = CALL_io.strRemainder(st);
                                                    // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                    // CALL_debug.DEBUG, "L" +
                                                    // newLesson.index +
                                                    // ", Example English: " +
                                                    // newExample.english);
                                                } else if (tempString.equals("answerJ")) {
                                                    newExample.answerJ = CALL_io.strRemainder(st);
                                                    // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                    // CALL_debug.DEBUG, "L" +
                                                    // newLesson.index +
                                                    // ", Example Answer: " +
                                                    // newExample.answerJ);
                                                } else if (tempString.equals("answerK")) {
                                                    newExample.answerK = CALL_io.strRemainder(st);
                                                    // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                    // CALL_debug.DEBUG, "L" +
                                                    // newLesson.index +
                                                    // ", Example Answer: " +
                                                    // newExample.answerK);
                                                } else if (tempString.equals("questionType")) {
                                                    newExample.questionType = CALL_io.strRemainder(st);
                                                    // CALL_debug.printlog(CALL_debug.MOD_LESSON,
                                                    // CALL_debug.DEBUG, "L" +
                                                    // newLesson.index +
                                                    // ",Question Type: " +
                                                    // newExample.questionType);
                                                } else if (tempString.equals("form")) {
                                                    prevState = readState;
                                                    readState = exampleFormState;
                                                } else if (tempString.equals("formHelp")) {
                                                    prevState = readState;
                                                    readState = formHelpState;
                                                } else if (tempString.equals("gfxHelp")) {
                                                    prevState = readState;
                                                    readState = gfxHelpState;
                                                } else if (tempString.equals("diagHelp")) {
                                                    prevState = readState;
                                                    readState = diagHelpState;
                                                }
                                            }
                                        }
                                    }
                                }

                                break;

                            case exampleFormState:
                                if (commandString != null) {
                                    st = new StringTokenizer(commandString);
                                    tempString = CALL_io.getNextToken(st);

                                    if (commandString.equals("/form")) {
                                        // Move along
                                        prevState = readState;
                                        readState = exampleState;
                                    } else if (commandString.equals("fgroup")) {
                                        newEgFormGroup = new CALL_stringPairsStruct();
                                        prevState = readState;
                                        readState = exampleFormGrpState;
                                    }
                                }

                                break;

                            case exampleFormGrpState:
                                if (commandString != null) {
                                    st = new StringTokenizer(commandString);
                                    tempString = CALL_io.getNextToken(st);

                                    if (commandString.equals("/fgroup")) {
                                        // Add form group (vector) to list
                                        if ((newEgFormGroup != null) && (newEgFormGroup.size() > 0)) {
                                            newExample.forms.add(newEgFormGroup);
                                        }

                                        newEgFormGroup = null;

                                        // Move along
                                        prevState = readState;
                                        readState = exampleFormState;
                                    } else if ((commandString.equals("off")) || (commandString.equals("on"))) {
                                        // Add the form setting as a string pair
                                        // into the formGroup
                                        // stringPairs struct
                                        if (newEgFormGroup != null) {
                                            newEgFormGroup.addElement(dataString, commandString, false);
                                        }
                                    }
                                }
                                break;

                            case formHelpState:
                                if (commandString != null) {
                                    if (commandString.equals("/formHelp")) {
                                        // Add the string to the example
                                        if (helpString != null) {
                                            newExample.formHelp = helpString;
                                            helpString = null;
                                        }

                                        prevState = readState;
                                        readState = exampleState;
                                    }
                                } else if (dataString != null) {
                                    if (helpString != null) {
                                        helpString = helpString + " " + dataString;
                                    } else {
                                        helpString = dataString;
                                    }
                                }
                                break;

                            case gfxHelpState:
                                if (commandString != null) {
                                    if (commandString.equals("/gfxHelp")) {
                                        // Add the string to the example
                                        if (helpString != null) {
                                            newExample.gfxHelp = helpString;
                                            helpString = null;
                                        }

                                        prevState = readState;
                                        readState = exampleState;
                                    }
                                } else if (dataString != null) {
                                    if (helpString != null) {
                                        helpString = helpString + " " + dataString;
                                    } else {
                                        helpString = dataString;
                                    }
                                }
                                break;

                            case diagHelpState:
                                if (commandString != null) {
                                    if (commandString.equals("/diagHelp")) {
                                        // Add the string to the example
                                        if (helpString != null) {
                                            newExample.diagHelp = helpString;
                                            helpString = null;
                                        }

                                        prevState = readState;
                                        readState = exampleState;
                                    }
                                } else if (dataString != null) {
                                    if (helpString != null) {
                                        helpString = helpString + " " + dataString;
                                    } else {
                                        helpString = dataString;
                                    }
                                }
                                break;

                            case errorState:
                                break;

                            case endState:
                                break;
                            default:
                                // Shouldn't really get here
                                break;
                        }
                    }
                }
            }// try
            catch (NullPointerException e) {
                // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
                // "NullPointerException at Lessons.txt Line: "+readingCount);
                System.err.println("NullPointerException at Lessons.txt Line: " + readingCount);
                throw (e);
            }

        } catch (IOException e) {
            // File does not exist
            // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
            // "Problem opening lesson definitions file");
            return false;
        }

        if (readState == endState) {
            // This means we exited ok - print the lesson summary and then exit
            // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
            // "Printing Lesson Information");
            print_debug();
            rc = true;
        }
        System.out.println(lessons.size());
        return rc;
    }

    // Public access of the lessons
    public CALL_lessonStruct getLesson(int index) {
        CALL_lessonStruct lesson = null;

        if (index < lessons.size()) {
            lesson = (CALL_lessonStruct) lessons.get(index);
        }

        return lesson;
    }

    // Public get all lessons
    public List<CALL_lessonStruct> getAllLesson() {
        List<CALL_lessonStruct> listLesson = new ArrayList<>();

        for (int i = 0; i < lessons.size(); i++) {
            listLesson.add((CALL_lessonStruct) lessons.get(i));
        }

        return listLesson;
    }

    public int numberLessons() {
        return lessons.size();
    }

    // Print debug on a lesson
    public void print_debug() {
        CALL_lessonStruct lesson = null;

        for (int i = 0; i < lessons.size(); i++) {
            lesson = (CALL_lessonStruct) lessons.get(i);
            if (lesson != null) {
                lesson.print_debug();
            }
        }
    }

    public static void main(String[] agrs) {
        CALL_lessonsStruct struct = new CALL_lessonsStruct(null);
        struct.load("/Data/lessons.txt");
        System.out.println("1");
    }
}