///////////////////////////// //////////////////////////////////////
// Stores information about the cost weighting for different components,
// hint and error types, etc.
//

package jcall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_errorTextStruct {
  // Static definitions
  static final int INITSTATE = 0;
  static final int TYPESTATE = 1;
  static final int SPELLYSTATE = 2;
  static final int SPELLNSTATE = 3;
  static final int SPELLMSTATE = 4;
  static final int ERRORSTATE = 99;
  static final int ENDSTATE = 100;

  static final int NUM_STRINGS = 4;

  Vector typeTextV; // A vector of class CALL_errorTypeTextStruct, defined below

  // The main constructor
  // ///////////////////////////////////////////////////////////////////////////////
  public CALL_errorTextStruct() {
    // Initialise the weights to default settings
    init();
  }

  //
  // Initialise the weights to default settings
  public void init() {
    typeTextV = new Vector();
  }

  //
  // Load the cost weights - only those specified in file will change
  public boolean load(String filename) {

    FileReader in;
    int readState = INITSTATE;

    int tempInt;
    double tempDouble;

    String parsedString[];

    String tempString;
    String readLine;
    String commandString;
    String dataString;

    StringTokenizer st;

    CALL_errorTypeTextStruct currentType = null;

    // Open file
    try {
      InputStream inputStream = new FileInputStream(filename);
      Reader reader = new InputStreamReader(inputStream);
      BufferedReader bf = new BufferedReader(reader);
      // Read Line by line
      while (readState < ERRORSTATE) {
        readLine = bf.readLine().trim();

        if ((readLine == null) || (readLine.startsWith("-eof"))) {
          // Reached end of file - stop reading
          readState = ENDSTATE;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else {
          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]");

          // Get the command and or data strings
          parsedString = CALL_io.extractCommand(readLine);
          commandString = parsedString[0];
          dataString = parsedString[1];

          // Use the state machine from this point onwards
          // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
          // "Read state: " + readState);

          switch (readState) {
            case INITSTATE:
              // Initial state
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("type")) {
                  // Starting an error type's text description block
                  if (dataString != null) {
                    currentType = new CALL_errorTypeTextStruct();
                    currentType.typeString = dataString;
                    dataString = null;
                    readState = TYPESTATE;
                  }
                }
              }
              break;

            case TYPESTATE:
              // Inside an error type
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/type")) {
                  // End of type. Add the record, and move back to initial state
                  typeTextV.addElement(currentType);
                  currentType = null;
                  readState = INITSTATE;
                } else if (tempString.equals("spell")) {
                  if (dataString != null) {
                    if (dataString.equals("Yes")) {
                      // We will add text for the spelling mistake case
                      readState = SPELLYSTATE;
                    } else if (dataString.equals("No")) {
                      // We will add text for the non-spelling mistake case
                      readState = SPELLNSTATE;
                    } else {
                      // We will add text for the maybe spelling mistake case
                      readState = SPELLMSTATE;
                    }
                  }
                }
              } else if (dataString != null) {
                // Read the text string
                if (currentType.mainText != null) {
                  // Append this to end of existing text
                  currentType.mainText = currentType.mainText + " " + dataString;
                } else {
                  // This is the first block of text
                  currentType.mainText = dataString;
                }
              }
              break;

            case SPELLYSTATE:
              // Inside a Spelling:Yes clause
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/spell")) {
                  // End of spelling section. Move back up to TYPE state
                  readState = TYPESTATE;
                }
              } else if (dataString != null) {
                // Read the text string
                if (currentType.spellingYesText != null) {
                  // Append this to end of existing text
                  currentType.spellingYesText = currentType.spellingYesText + " " + dataString;
                } else {
                  // This is the first block of text
                  currentType.spellingYesText = dataString;
                }
              }
              break;

            case SPELLNSTATE:
              // Inside a Spelling:No clause
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/spell")) {
                  // End of spelling section. Move back up to TYPE state
                  readState = TYPESTATE;
                }
              } else if (dataString != null) {
                // Read the text string
                if (currentType.spellingNoText != null) {
                  // Append this to end of existing text
                  currentType.spellingNoText = currentType.spellingNoText + " " + dataString;
                } else {
                  // This is the first block of text
                  currentType.spellingNoText = dataString;
                }
              }
              break;

            case SPELLMSTATE:
              // Inside a Spelling:Maybe clause
              if (commandString != null) {
                st = new StringTokenizer(commandString);
                tempString = CALL_io.getNextToken(st);

                if (tempString.equals("/spell")) {
                  // End of spelling section. Move back up to TYPE state
                  readState = TYPESTATE;
                }
              } else if (dataString != null) {
                // Read the text string
                if (currentType.spellingMaybeText != null) {
                  // Append this to end of existing text
                  currentType.spellingMaybeText = currentType.spellingMaybeText + " " + dataString;
                } else {
                  // This is the first block of text
                  currentType.spellingMaybeText = dataString;
                }
              }
              break;

            default:
              break;
          }
        }
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.ERROR,
      // "Problem opening Error help text file [" + ERROR_TEXT_FILENAME + "]");
      readState = ERRORSTATE;
      in = null;
    }

    if (readState == ENDSTATE) {
      // Finished loading succesfully
      printDebug();
      return true;
    } else {
      // Must be in the error state
      return false;
    }

  }

  public String[] getHelpText(String type) {
    String returnStrings[];
    CALL_errorTypeTextStruct currentType;

    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.DEBUG,
    // "Looking for help text for type: " + type);

    // Initialise return string struct
    returnStrings = new String[NUM_STRINGS];
    for (int i = 0; i < NUM_STRINGS; i++) {
      returnStrings[i] = null;
    }

    if (type != null) {
      for (int i = 0; i < typeTextV.size(); i++) {
        currentType = (CALL_errorTypeTextStruct) typeTextV.elementAt(i);
        if (currentType != null) {
          if ((currentType.typeString != null) && (currentType.typeString.equals(type))) {
            // We have a match
            returnStrings[0] = currentType.mainText;
            returnStrings[1] = currentType.spellingYesText;
            returnStrings[2] = currentType.spellingNoText;
            returnStrings[3] = currentType.spellingMaybeText;
            break;
          }
        }
      }
    }

    return returnStrings;
  }

  public void printDebug() {
    CALL_errorTypeTextStruct currentType;

    for (int i = 0; i < typeTextV.size(); i++) {
      currentType = (CALL_errorTypeTextStruct) typeTextV.elementAt(i);
      if (currentType != null) {
        currentType.printDebug();
      }
    }
  }
}

class CALL_errorTypeTextStruct {
  String typeString;
  String mainText;
  String spellingYesText;
  String spellingNoText;
  String spellingMaybeText;

  public CALL_errorTypeTextStruct() {
    init();
  }

  private void init() {
    typeString = null;
    mainText = null;
    spellingYesText = null;
    spellingNoText = null;
    spellingMaybeText = null;
  }

  public void printDebug() {
    // CALL_debug.printlog(CALL_debug.MOD_ERRORS, CALL_debug.INFO,
    // "Error Help Text, Type: " + typeString);
    // if(mainText != null) CALL_debug.printlog(CALL_debug.MOD_ERRORS,
    // CALL_debug.INFO, "Contains Main Text");
    // if(spellingYesText != null) CALL_debug.printlog(CALL_debug.MOD_ERRORS,
    // CALL_debug.INFO, "Contains Spelling (Yes) Text");
    // if(spellingNoText != null) CALL_debug.printlog(CALL_debug.MOD_ERRORS,
    // CALL_debug.INFO, "Contains Spelling (No) Text");
    // if(spellingMaybeText != null) CALL_debug.printlog(CALL_debug.MOD_ERRORS,
    // CALL_debug.INFO, "Contains Spelling (Maybe) Text");
  }
}