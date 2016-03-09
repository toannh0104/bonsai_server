///////////////////////////// //////////////////////////////////////
// Application config information
//

package jcall;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.StringTokenizer;

public class CALL_configStruct {
  // Specific Data (may be lesson specific)
  CALL_configDataStruct settings[];

  // BitSet for those fields actually loaded (= those to be saved, in case of
  // lesson specific config)
  // 1 - Hint Costs
  // 2 - Initial Hints
  // 4 - Hit Choice
  // 8 - Output Style
  // 16 - logging
  // 32 - diagram markers
  BitSet whichLoaded[];

  // Static definitions
  static final String configFileName = "Data/config";

  // Bit numbers referencing setting type
  public static final int HINT_COSTS = 1;
  public static final int HINTS_LEVELS = 2;
  public static final int HINT_CHOICE = 3;
  public static final int OUTPUT_STYLE = 4;
  public static final int LOGGING = 5;
  public static final int DIAG_MARKERS = 6;

  // The config files are now based on the students
  int studentIndex;

  // The main constructor
  // ///////////////////////////////////////////////////////////////////////////////
  public CALL_configStruct() {
    // Load a generic config file (not tied to a student number
    settings = new CALL_configDataStruct[CALL_database.MAX_LESSONS + 1];
    whichLoaded = new BitSet[CALL_database.MAX_LESSONS + 1];

    studentIndex = -1;

    // Index 0 is for default settings, 1 for lesson 1 etc...
    settings[0] = new CALL_configDataStruct();
    whichLoaded[0] = new BitSet();

    // Load file
    loadConfig();

    // Write to log
    writeToLog();
  }

  public CALL_configStruct(int index) {
    // Load a student specific config file (based on the student index number
    // passed)
    settings = new CALL_configDataStruct[CALL_database.MAX_LESSONS + 1];
    whichLoaded = new BitSet[CALL_database.MAX_LESSONS + 1];

    studentIndex = index;

    // Index 0 is for default settings, 1 for lesson 1 etc...
    settings[0] = new CALL_configDataStruct();
    whichLoaded[0] = new BitSet();

    // Load file
    loadConfig();

    // Write to log
    writeToLog();
  }

  public boolean loadConfig() {
    String filename;
    boolean rc = true;
    FileReader in;
    int readstatus = 0;
    int lessonIndex = 0;

    String tempModule;
    int tempInt;

    String tempString;
    String readLine;
    StringTokenizer st;

    if (studentIndex != -1) {
      // Student based config file
      filename = configFileName + studentIndex + ".txt";
    } else {
      // Generic config file
      filename = configFileName + ".txt";
    }

    // Open file
    try {
      in = new FileReader(filename);
      if (in == null) {
        // File does not exist
        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
        // "Config file not found [" + filename + "]");
        return false;
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
      // "Problem opening config file [" + filename + "]");
      return false;
    }

    // Read Line by line
    while (readstatus == 0) {
      readLine = CALL_io.readString(in);

      if (readLine == null) {
        // Reached end of file - stop reading
        readstatus = 1;
      } else if (readLine.startsWith("#")) {
        // A comment...skip
        continue;
      } else {
        // We have some valid text, so parse it

        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
        // "Parsing line [" + readLine + "]");

        st = new StringTokenizer(readLine);
        tempString = st.nextToken();

        // Debug
        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.DEBUG,
        // "Token: [" + tempString + "]");

        // General Settings
        if (tempString.startsWith("-eof")) {
          // EOF marker
          readstatus = 1;
        } else if (tempString.equals("-lastStudent")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            settings[lessonIndex].lastStudent = tempString;
          }
        } else if (tempString.equals("-debugLevel")) {
          tempModule = CALL_io.getNextToken(st);
          if (tempModule != null) {
            tempString = CALL_io.getNextToken(st);
            if (tempString != null) {
              tempInt = CALL_io.str2int(tempString);
              if (tempInt != CALL_io.INVALID_INT) {
                CALL_debug.set_level(tempModule, tempInt);
              }
            }
          }
        } else if (tempString.equals("-session")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].sessionNumber = tempInt;
            }
          }
        } else if (tempString.equals("-practiceQuestions")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].numQuestionsPractice = tempInt;
            }
          }
        } else if (tempString.equals("-hintGrammarBias")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintGrammarBias = tempInt;
            }
          }
        } else if (tempString.equals("-hintLevelBias")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintLevelBias = tempInt;
            }
          }
        } else if (tempString.equals("-enableWordExperience")) {
          settings[lessonIndex].useWordExperience = true;
        } else if (tempString.equals("-disableWordExperience")) {
          settings[lessonIndex].useWordExperience = false;
        } else if (tempString.equals("-hintWordLearningImpact")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintWordLearningImpact = tempInt;
            }
          }
        } else if (tempString.equals("-hintWordLearningBiasLT")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintWordLearningBiasLT = tempInt;
            }
          }
        } else if (tempString.equals("-hintWordLearningBiasMT")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintWordLearningBiasMT = tempInt;
            }
          }
        } else if (tempString.equals("-hintWordLearningBiasST")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintWordLearningBiasST = tempInt;
            }
          }
        } else if (tempString.equals("-hintWordLearningRate")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintWordLearningRate = tempInt;
            }
          }
        } else if (tempString.equals("-hintCostAutoBalance")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintCostAutoBalance = tempInt;
            }
          }
        } else if (tempString.equals("-hintSTErrorImpact")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintErrorSTImpact = tempInt;
            }
          }
        } else if (tempString.equals("-hintLTErrorImpact")) {
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            tempInt = CALL_io.str2int(tempString);
            if (tempInt >= 0) {
              settings[lessonIndex].hintErrorLTImpact = tempInt;
            }
          }
        } else if (tempString.equals("-disableHintLevel")) {
          // Disable the various hint levels
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_TYPE)) {
              settings[lessonIndex].hintLevelType = false;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_ENGLISH)) {
              settings[lessonIndex].hintLevelEnglish = false;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_BASE)) {
              settings[lessonIndex].hintLevelBase = false;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_SYLABLE)) {
              settings[lessonIndex].hintLevelSylable = false;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_SURFACE)) {
              settings[lessonIndex].hintLevelSurface = false;
            }
          }
        } else if (tempString.equals("-enableHintLevel")) {
          // Disable the various hint levels
          tempString = CALL_io.getNextToken(st);
          if (tempString != null) {
            if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_TYPE)) {
              settings[lessonIndex].hintLevelType = true;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_ENGLISH)) {
              settings[lessonIndex].hintLevelEnglish = true;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_BASE)) {
              settings[lessonIndex].hintLevelBase = true;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_SYLABLE)) {
              settings[lessonIndex].hintLevelSylable = true;
            } else if (tempString.equals(CALL_configDataStruct.HINT_LEVEL_SURFACE)) {
              settings[lessonIndex].hintLevelSurface = true;
            }
          }
        } else if (tempString.equals("-disableHintCosts")) {
          whichLoaded[lessonIndex].set(HINT_COSTS);
          settings[lessonIndex].hintCostEnabled = false;
        } else if (tempString.equals("-enableHintCosts")) {
          whichLoaded[lessonIndex].set(HINT_COSTS);
          settings[lessonIndex].hintCostEnabled = true;
        } else if (tempString.equals("-disableHintChoice")) {
          whichLoaded[lessonIndex].set(HINT_CHOICE);
          settings[lessonIndex].hintChoice = false;
        } else if (tempString.equals("-enableHintChoice")) {
          whichLoaded[lessonIndex].set(HINT_CHOICE);
          settings[lessonIndex].hintChoice = true;
        } else if (tempString.equals("-useRomaji")) {
          whichLoaded[lessonIndex].set(OUTPUT_STYLE);
          settings[lessonIndex].outputStyle = CALL_io.romaji;
        } else if (tempString.equals("-useKana")) {
          whichLoaded[lessonIndex].set(OUTPUT_STYLE);
          settings[lessonIndex].outputStyle = CALL_io.kana;
        } else if (tempString.equals("-useKanji")) {
          whichLoaded[lessonIndex].set(OUTPUT_STYLE);
          settings[lessonIndex].outputStyle = CALL_io.kanji;
        } else if (tempString.equals("-disableDiagramMarkers")) {
          whichLoaded[lessonIndex].set(DIAG_MARKERS);
          settings[lessonIndex].diagMarkersEnabled = false;
        } else if (tempString.equals("-enableDiagramMarkers")) {
          whichLoaded[lessonIndex].set(DIAG_MARKERS);
          settings[lessonIndex].diagMarkersEnabled = true;
        } else if (tempString.equals("-showInactiveLessons")) {
          settings[lessonIndex].showInactiveLessons = true;
        } else if (tempString.equals("-hideInactiveLessons")) {
          settings[lessonIndex].showInactiveLessons = false;
        } else if (tempString.equals("-enableMiscTests")) {
          settings[lessonIndex].runMiscTests = true;
        } else if (tempString.equals("-disableMiscTests")) {
          settings[lessonIndex].runMiscTests = false;
        } else if (tempString.equals("-lesson")) {
          if (!st.hasMoreTokens())
            continue;
          tempString = st.nextToken();
          lessonIndex = CALL_io.str2int(tempString);
          if (settings[lessonIndex] == null) {
            settings[lessonIndex] = new CALL_configDataStruct(settings[0]);
            whichLoaded[lessonIndex] = new BitSet();
          }
        }
      }
    }

    // Close the file
    if (in != null) {
      try {
        in.close();
      } catch (IOException e) {
        // Ignore for now
      }
    }

    return rc;
  }

  public boolean saveConfig() {
    String filename;
    boolean rc = true;
    FileWriter out;
    PrintWriter outP;

    // Open file
    if (studentIndex == -1) {
      filename = configFileName + ".txt";
    } else {
      filename = configFileName + studentIndex + ".txt";
    }
    try {
      out = new FileWriter(filename);
      if (out == null) {
        // File does not exist
        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
        // "Failed to write config file");
        return false;
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
      // "Failed to write config file");
      return false;
    }

    outP = new PrintWriter(out);

    // Write config file
    outP.println("#");
    outP.println("# CallJ Config file (" + CALL_main.version + ")");
    outP.println("#");
    outP.println("#################################");
    outP.println("#");
    for (int i = 0; i <= CALL_database.MAX_LESSONS; i++) {
      if (settings[i] != null) {
        if (i != 0) {
          // Lesson Specific (i is 0 for the global configuration)
          outP.println("#");
          outP.println("-lesson " + i);
          outP.println("#");
        } else {
          // The last student (just for global)
          if (settings[i].lastStudent != null) {
            outP.println("-lastStudent " + settings[i].lastStudent);
          }

          // Session number
          outP.println("#");
          outP.println("-session " + (settings[i].sessionNumber + 1));
          outP.println("#");

          // Global config only (not configureable for lessons)
          for (int j = 0; j < CALL_debug.num_modules(); j++) {
            outP.println("-debugLevel " + CALL_debug.module_name(j) + " " + CALL_debug.get_level(j));
          }
          outP.println("#");

          // Misc
          outP.println("#");
          outP.println("-practiceQuestions " + settings[i].numQuestionsPractice);
          outP.println("#");

          // Hint bias etc
          outP.println("# Hint Cost Parameters");
          outP.println("#==================");
          outP.println("-hintGrammarBias " + settings[i].hintGrammarBias);
          outP.println("-hintLevelBias " + settings[i].hintLevelBias);
          outP.println("-hintWordLearningImpact " + settings[i].hintWordLearningImpact);
          outP.println("-hintWordLearningRate " + settings[i].hintWordLearningRate);
          outP.println("-hintWordLearningBiasLT " + settings[i].hintWordLearningBiasLT);
          outP.println("-hintWordLearningBiasMT " + settings[i].hintWordLearningBiasMT);
          outP.println("-hintWordLearningBiasST " + settings[i].hintWordLearningBiasST);
          outP.println("-hintCostAutoBalance " + settings[i].hintCostAutoBalance);
          outP.println("-hintSTErrorImpact " + settings[i].hintErrorSTImpact);
          outP.println("-hintLTErrorImpact " + settings[i].hintErrorLTImpact);
          outP.println("#");
          if (settings[i].useWordExperience) {
            outP.println("-enableWordExperience");
            outP.println("#-disableWordExperience");
          } else {
            outP.println("#-enableWordExperience");
            outP.println("-disableWordExperience");
          }
          outP.println("#");

          if (settings[i].showInactiveLessons) {
            outP.println("-showInactiveLessons");
            outP.println("#-hideInactiveLessons");
          } else {
            outP.println("#-showInactiveLessons");
            outP.println("-hideInactiveLessons");
          }

          outP.println("#");
          outP.println("#");
        }

        // THE FOLLOWING VARIABLES ARE THE ONES THAT CAN BE SET SPECIFIC TO
        // LESSON
        // ==============================================================================
        if ((i == 0) || whichLoaded[i].get(HINT_COSTS)) {
          if (settings[i].hintCostEnabled) {
            outP.println("#-disableHintCosts");
            outP.println("-enableHintCosts");
          } else {
            outP.println("-disableHintCosts");
            outP.println("#-enableHintCosts");
          }
          outP.println("#");
        }

        if ((i == 0) || whichLoaded[i].get(HINT_CHOICE)) {

          if (settings[i].hintChoice) {
            outP.println("#-disableHintChoice");
            outP.println("-enableHintChoice");
          } else {
            outP.println("-disableHintChoice");
            outP.println("#-enableHintChoice");
          }
          outP.println("#");
        }

        if ((i == 0) || whichLoaded[i].get(DIAG_MARKERS)) {

          if (settings[i].diagMarkersEnabled) {
            outP.println("#-disableDiagramMarkers");
            outP.println("-enableDiagramMarkers");
          } else {
            outP.println("-disableDiagramMarkers");
            outP.println("#-enableDiagramMarkers");
          }
          outP.println("#");
        }

        if ((i == 0) || whichLoaded[i].get(OUTPUT_STYLE)) {
          outP.println("#");
          if (settings[i].outputStyle == CALL_io.romaji)
            outP.println("-useRomaji");
          else if (settings[i].outputStyle == CALL_io.kana)
            outP.println("-useKana");
          else if (settings[i].outputStyle == CALL_io.kanji)
            outP.println("-useKanji");

          outP.println("#");
        }

        if (i == 0) {
          // Moving on to lesson specifc - want a gap for nice readability
          outP.println("#");
          outP.println("#");
          outP.println("# LESSON SPECIFIC SETTINGS - NOT CHANGED BY SYSTEM");
          outP.println("# ====================================================================================");
          outP.println("#");
        }
      }
    }
    outP.println("-eof");
    outP.println("#");

    try {
      out.close();
      outP.close();
    } catch (IOException e) {
      // Ignore for now
    }

    return rc;
  }

  public void writeToLog() {
    // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.INFO,
    // "Config Settings:");

    if (settings[0].hintCostEnabled) {
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.INFO,
      // "Hint Cost: Enabled");
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.INFO,
      // "Hint Cost: Disabled");
    }
  }
}