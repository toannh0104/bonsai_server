///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_formDescGroupStruct {
  // Fields
  Vector forms; // A vector of formDescStruct
  Vector questions; // Vector of question names that use this form
  String name; // null is ok if no specific group name

  public CALL_formDescGroupStruct() {
    forms = new Vector();
    questions = new Vector();
    name = null;
  }

  public void print_debug() {
    String questionName;
    CALL_formDescStruct form;

    if (name != null) {
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
      // "Form Group: [" + name + "]");
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
      // "Form Group: [non-titled]");
    }

    // Individual form settings
    for (int i = 0; i < forms.size(); i++) {
      form = (CALL_formDescStruct) forms.elementAt(i);
      if (form != null) {
        form.print_debug();
      }
    }

    // Questions
    for (int j = 0; j < questions.size(); j++) {
      questionName = (String) questions.elementAt(j);
      if (questionName != null) {
        // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
        // "Used in question: " + questionName);
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "End of Group");
  }

  public CALL_stringPairsStruct getFormText(CALL_questionStruct question) {
    CALL_formDescStruct form;
    CALL_stringPairsStruct pairs;
    CALL_stringPairStruct pair;
    String restrictionString, parmString, operatorString, valueString;
    StringTokenizer st;
    boolean match;

    pairs = new CALL_stringPairsStruct();

    // Go through each of the forms in this group, create a string for it, and a
    // string to show whether this form is enable ("true" | "false")
    for (int i = 0; i < forms.size(); i++) {
      form = (CALL_formDescStruct) forms.elementAt(i);
      if (form != null) {
        match = true;

        for (int j = 0; j < form.restrictions.size(); j++) {
          restrictionString = (String) form.restrictions.elementAt(j);
          if (restrictionString != null) {
            st = new StringTokenizer(restrictionString);
            parmString = CALL_io.getNextToken(st);

            // It's a concept token
            operatorString = CALL_io.getNextToken(st);
            if (operatorString != null) {
              valueString = CALL_io.getNextToken(st);
              if (valueString != null) {
                if (parmString.startsWith("[")) {

                  // Case of checking concept token: De-bracketise the slot
                  // string
                  // -----------------------------------------------------
                  parmString = parmString.substring(1, parmString.length() - 1);

                  // Now from the instance, get the value for this slot string
                  if (question.instance != null) {
                    parmString = question.instance.getDataString(parmString);
                    if (parmString != null) {

                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                      // CALL_debug.INFO, "Restriction: Comparing " + parmString
                      // + " with " + valueString);

                      // Now make the check based on the operator
                      if (operatorString.equals("!=")) {
                        if (parmString.equals(valueString)) {
                          // A matching restriction
                          match = false;
                        }
                      } else if (operatorString.equals("==")) {
                        if (!(parmString.equals(valueString))) {
                          match = false;
                        }
                      }
                    }
                  }
                } else {
                  // It's a question parameter
                  if (parmString.equals("question")) {
                    if (operatorString.equals("==")) {
                      if (!valueString.equals(question.lessonQuestion.question)) {
                        match = false;
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals(question.lessonQuestion.question)) {
                        match = false;
                      }
                    }
                  } else if (parmString.equals("concept")) {
                    if (operatorString.equals("==")) {
                      if (!valueString.equals(question.lessonConcept.concept)) {
                        match = false;
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals(question.lessonConcept.concept)) {
                        match = false;
                      }
                    }
                  } else if (parmString.equals("diagram")) {
                    if (operatorString.equals("==")) {
                      if (!valueString.equals(question.diagram.name)) {
                        match = false;
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals(question.diagram.name)) {
                        match = false;
                      }
                    }
                  } else if (parmString.equals("sign")) {
                    if (operatorString.equals("==")) {
                      if (valueString.equals("positive")) {
                        if (question.sentence.sign != CALL_formStruct.POSITIVE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("negative")) {
                        if (question.sentence.sign != CALL_formStruct.NEGATIVE) {
                          match = false;
                        }
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals("negative")) {
                        if (question.sentence.sign != CALL_formStruct.POSITIVE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("positive")) {
                        if (question.sentence.sign != CALL_formStruct.NEGATIVE) {
                          match = false;
                        }
                      }
                    }
                  } else if (parmString.equals("tense")) {
                    if (operatorString.equals("==")) {
                      if (valueString.equals("past")) {
                        if (question.sentence.tense != CALL_formStruct.PAST) {
                          match = false;
                        }
                      }
                      if (valueString.equals("present")) {
                        if (question.sentence.tense != CALL_formStruct.PRESENT) {
                          match = false;
                        }
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals("present")) {
                        if (question.sentence.tense != CALL_formStruct.PAST) {
                          match = false;
                        }
                      }
                      if (valueString.equals("past")) {
                        if (question.sentence.tense != CALL_formStruct.PRESENT) {
                          match = false;
                        }
                      }
                    }
                  } else if (parmString.equals("style")) {
                    if (operatorString.equals("==")) {
                      if (valueString.equals("polite")) {
                        if (question.sentence.style != CALL_formStruct.POLITE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("plain")) {
                        if (question.sentence.style != CALL_formStruct.PLAIN) {
                          match = false;
                        }
                      }
                      if (valueString.equals("superpolite")) {
                        if (question.sentence.style != CALL_formStruct.SUPER_POLITE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("crude")) {
                        if (question.sentence.style != CALL_formStruct.CRUDE) {
                          match = false;
                        }
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals("plain")) {
                        if (question.sentence.style == CALL_formStruct.PLAIN) {
                          match = false;
                        }
                      }
                      if (valueString.equals("polite")) {
                        if (question.sentence.style == CALL_formStruct.POLITE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("superpolite")) {
                        if (question.sentence.style == CALL_formStruct.SUPER_POLITE) {
                          match = false;
                        }
                      }
                      if (valueString.equals("crude")) {
                        if (question.sentence.style == CALL_formStruct.CRUDE) {
                          match = false;
                        }
                      }
                    }
                  } else if (parmString.equals("type")) {
                    if (operatorString.equals("==")) {
                      if (valueString.equals("question")) {
                        if (question.sentence.type != CALL_formStruct.QUESTION) {
                          match = false;
                        }
                      }
                      if (valueString.equals("statement")) {
                        if (question.sentence.type != CALL_formStruct.STATEMENT) {
                          match = false;
                        }
                      }
                    } else if (operatorString.equals("!=")) {
                      if (valueString.equals("statement")) {
                        if (question.sentence.type != CALL_formStruct.QUESTION) {
                          match = false;
                        }
                      }
                      if (valueString.equals("question")) {
                        if (question.sentence.type != CALL_formStruct.STATEMENT) {
                          match = false;
                        }
                      }
                    }
                  } else if (parmString.equals("concept")) {
                    if (!(valueString.equals(question.lessonConcept.concept))) {
                      match = false;
                    }
                  }
                }
              }
            }
          }
        }

        // Now add the form, as well as if it's enabled or not!
        pair = new CALL_stringPairStruct();
        pair.parameter = form.description;

        if (match) {
          // An enabled parameter
          pair.value = new String("true");
        } else {
          // A disabled parameter
          pair.value = new String("false");
        }

        pairs.addElement(pair);
      }
    }

    return pairs;
  }
}