///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_studentStruct {
  // Constants
  static final String trialName = "TRIAL";
  static final int trialInt = 1;

  // Data
  int id;
  String name;
  String password;

  // Is this student a special case? (0: Normal, 1: Trial)
  int type;

  // History
  Vector lessonHistory; // A vector of CALL_lessonHistoryStructs

  // Word Experience Structures (A vector, of vectors one element for each word
  // type, and per id).
  // =================================================================================
  // The first structure is long term - the total experience with a word..this
  // information is loaded from the student file.
  // The second is medium term - the total experience within a log-in sesson.
  // This starts empty on running the program.
  // The second is short term - the total experience within a test / practice
  // session. This is re-created each time a lesson is started.
  CALL_lexiconExperienceStruct wordExperienceLT;
  CALL_lexiconExperienceStruct wordExperienceMT;
  CALL_lexiconExperienceStruct wordExperienceST;

  // Errors

  // Preferences

  public CALL_studentStruct() {
    id = -1;
    name = null;
    password = null;
    type = 0;
    lessonHistory = new Vector();
    wordExperienceLT = new CALL_lexiconExperienceStruct();
    wordExperienceMT = new CALL_lexiconExperienceStruct();
    wordExperienceST = new CALL_lexiconExperienceStruct();
  }

  public boolean load(String filename) {
    boolean rc = false;
    boolean ok = true;

    FileReader in;
    int readstatus = 0;
    int tempInt;
    int wordType, wordId;
    long wordValue;
    long experience, issues;
    long tempLong;

    CALL_lessonHistoryStruct lessonHist;

    String tempString;
    String readLine;
    StringTokenizer st;

    // Open file
    try {
      InputStream inputStream = getClass().getResourceAsStream(filename);
      Reader reader = new InputStreamReader(inputStream);
      BufferedReader bufferedReader = new BufferedReader(reader);

      while (readstatus == 0) {

        readLine = bufferedReader.readLine().trim();

        if (readLine == null) {
          // Reached end of file - stop reading
          readstatus = 1;
        } else if (readLine.startsWith("#")) {
          // A comment...skip
          continue;
        } else {
          // We have some valid text, so parse it
          // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
          // "Parsing line [" + readLine + "]",request);

          st = new StringTokenizer(readLine);
          tempString = CALL_io.getNextToken(st);

          // Debug
          // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
          // "Token: [" + tempString + "]",request);

          // General Settings
          if (tempString.startsWith("-eof")) {
            // EOF marker - end read process
            readstatus = 1;
          } else if (tempString.equals("-id")) {
            // Get the student id
            tempString = CALL_io.getNextToken(st);
            if (tempString != null) {
              tempInt = CALL_io.str2int(tempString);
              if (tempInt != CALL_io.INVALID_INT) {
                id = tempInt;
              }
            }
          } else if (tempString.equals("-name")) {
            // Get the student name
            tempString = CALL_io.getNextToken(st);
            if (tempString != null) {
              name = tempString;
            }
          } else if (tempString.equals("-password")) {
            // Get the student password
            tempString = CALL_io.getNextToken(st);
            if (tempString != null) {
              // Will have to do decryption here - CJW
              password = tempString;
            }
          } else if (tempString.equals("-lessonHistory")) {
            // Get the students lesson history
            ok = true;
            lessonHist = new CALL_lessonHistoryStruct();

            // Lesson ID
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.lessonIndex = tempInt;
                ok = true;
              }
            }

            // Practice runs
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.practiceRuns = tempInt;
                ok = true;
              }
            }

            // Practice questions
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.practiceQuestions = tempInt;
                ok = true;
              }
            }

            // Practice answered questions
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.practiceAnswered = tempInt;
                ok = true;
              }
            }

            // Practice total score
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.practiceTotalScore = tempLong;
                ok = true;
              }
            }

            // Practice total max score
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.practiceTotalMaxScore = tempLong;
                ok = true;
              }
            }

            // Practice time spent
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.practiceTimeSpent = tempLong;
                ok = true;
              }
            }

            // Test runs
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.testRuns = tempInt;
                ok = true;
              }
            }

            // Test questions
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.testQuestions = tempInt;
                ok = true;
              }
            }

            // Test Answered questions
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.testAnswered = tempInt;
                ok = true;
              }
            }

            // Test best score
            if (ok) {
              ok = false;
              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                lessonHist.testBestScore = tempInt;
                ok = true;
              }
            }

            // Test total score
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.testTotalScore = tempLong;
                ok = true;
              }
            }

            // Test total max score
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.testTotalMaxScore = tempLong;
                ok = true;
              }
            }

            // Test time spent
            if (ok) {
              ok = false;
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                lessonHist.testTimeSpent = tempLong;
                ok = true;
              }
            }

            // Finally add the structure, if it was loaded ok
            if (ok) {
              lessonHistory.addElement(lessonHist);
            }
          } else if (tempString.equals("-wordExperience")) {
            // Format: -wordExperience <type> <id> <experience>
            ok = false;

            wordType = -1;
            wordId = -1;
            experience = 0;
            issues = 0;

            tempInt = CALL_io.getNextInt(st);
            if (tempInt != CALL_io.INVALID_INT) {
              wordType = tempInt;
              ok = true;
            }
            if (ok) {
              ok = false;

              tempInt = CALL_io.getNextInt(st);
              if (tempInt != CALL_io.INVALID_INT) {
                wordId = tempInt;
                ok = true;
              }
            }
            if (ok) {
              ok = false;

              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                experience = tempLong;
                ok = true;
              }
            }
            if (ok) {
              // This is optional
              tempLong = CALL_io.getNextLong(st);
              if (tempLong != CALL_io.INVALID_LONG) {
                issues = tempLong;
                ok = true;
              }
            }

            if (ok) {
              // We found all the values, so add this word experience
              // information
              // (The loaded values all count as long term)
              wordExperienceLT.addWordExperience(wordType, wordId, experience, issues);
            }
          }
        }
      }

      if ((id != -1) && (name != null) && (password != null)) {
        // We have a valid student entry
        rc = true;

        // Set the student type
        if ((name.equals(trialName)) && (password.equals(trialName))) {
          type = trialInt;
        }
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.ERROR,
      // "Problem opening student file [" + filename + "]",request);
      return false;
    }
    return rc;
  }

  public boolean save(String dataDirectory) {
    CALL_lessonHistoryStruct history;
    boolean rc = false;
    FileWriter out;
    PrintWriter outP;
    String filename;

    // Set filename
    filename = new String(dataDirectory + "student" + id + ".txt");

    // Open file
    try {
      out = new FileWriter(filename);
      if (out == null) {
        // File does not exist
        // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
        // "Failed to write student file",request);
        return false;
      }

    } catch (IOException e) {
      // File does not exist
      // CALL_debug.printlog(CALL_debug.MOD_CONFIG, CALL_debug.ERROR,
      // "Failed to write student file",request);
      return false;
    }

    outP = new PrintWriter(out);

    // Write student file
    outP.println("# Student Config File");
    outP.println("#");
    outP.println("-id " + id);
    outP.println("-name " + name);
    outP.println("-password " + password); // Will need to do encryption here!
    outP.println("#");

    // All lesson histories
    for (int i = 0; i < lessonHistory.size(); i++) {
      history = (CALL_lessonHistoryStruct) lessonHistory.elementAt(i);

      // This reduces the big score values down (they get too high over time)
      history.normalize();

      if ((history != null) && (type != trialInt)) {
        // We don't write the history for the TRIAL account
        outP.println("-lessonHistory " + history.toString());
      }
    }

    // Now the word experiences
    outP.println("#");

    if (type != trialInt) {
      // We don't use experience for the TRIAL account
      wordExperienceLT.write(outP);
    }

    outP.println("#");
    outP.println("-eof");
    outP.println("#");

    try {
      out.close();
      outP.close();
    } catch (IOException e) {
      // Ignore for now
    }

    return true;
  }

  public CALL_lessonHistoryStruct getLessonHistory(int index) {
    CALL_lessonHistoryStruct history;
    CALL_lessonHistoryStruct rc = null;

    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
    // "Looking for lesson " + index + " record.",request);

    for (int i = 0; i < lessonHistory.size(); i++) {
      history = (CALL_lessonHistoryStruct) lessonHistory.elementAt(i);
      if (history != null) {
        if (history.lessonIndex == index) {
          // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
          // "Found record.");
          rc = history;
          break;
        }
      }
    }

    if (rc == null) {
      // No record found, so make one
      // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
      // "No record, creating new record.");
      rc = new CALL_lessonHistoryStruct();
      rc.lessonIndex = index;
      lessonHistory.addElement(rc);
    }

    return rc;
  }

  public void print_debug() {
    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.INFO, "Student: "
    // + id,request);
    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.INFO, "Name: " +
    // name,request);
    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.INFO, "Password: "
    // + password,request);
  }

  public static void main(String[] agrs) {
    CALL_studentStruct struct = new CALL_studentStruct();
    struct.load("/Data/student1.txt");
    System.out.println("1");
  }
}