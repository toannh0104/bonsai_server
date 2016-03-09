//////////////////////////////////////////////////////////////
// Debug handling module
//
package jcall;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public class CALL_debug {
  // Defines
  public static final int ERROR = 0;
  public static final int WARN = 1;
  public static final int INFO = 2;
  public static final int DEBUG = 3;

  // For the usage debug
  public static final int RESULTS = 0;
  public static final int HINTS = 1;
  public static final int MISC = 2;
  public static final int FLOW = 3;

  public static final int MAX_LEVEL = 3;
  public static final int MAX_MODULES = 128;

  // Modules
  public static final int MOD_GLOBAL = -1;
  public static final int MOD_GENERAL = 0;
  public static final int MOD_USAGE = 1;
  public static final int MOD_CONCEPT = 2;
  public static final int MOD_GRULES = 3;
  public static final int MOD_VRULES = 4;
  public static final int MOD_ARULES = 5;
  public static final int MOD_CRULES = 6;
  public static final int MOD_LESSON = 7;
  public static final int MOD_LEXICON = 8;
  public static final int MOD_IO = 9;
  public static final int MOD_IMAGE = 10;
  public static final int MOD_DATABASE = 11;
  public static final int MOD_DEBUG = 12;
  public static final int MOD_CONFIG = 13;
  public static final int MOD_DIAGRAM = 14;
  public static final int MOD_SENTENCE = 15;
  public static final int MOD_HINTS = 16;
  public static final int MOD_STUDENT = 17;
  public static final int MOD_STACK = 18;
  public static final int MOD_NEURALNET = 19;
  public static final int MOD_ERRORS = 20;
  public static final int MOD_COSTS = 21;
  public static final int MOD_UI = 22;

  public static final String logfilename = "\\Data\\log\\log.txt";

  // Member variables
  private static Integer debug_level[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

  private static FileWriter logfile;
  private static PrintWriter logOut;

  private static String[] levelLabel = { "E: ", "W: ", "I: ", "D: " };

  private static String[] levelLabelAlt = { "Res: ", "Hnt: ", "Misc: ", "Flow: " };

  private static final String modNames[] = { "General", "Usage", "Concept", "G.Rules", "V.Rules", "A.Rules", "C.Rules",
      "Lesson", "Lexicon", "IO", "Image", "Database", "Debug", "Config", "Diagram", "Sentence", "Hints", "Student",
      "Stack", "NNet", "Errors", "Costs", "UI" };

  public CALL_debug() {
    // Not instantiated, used as static only
  }

  public static void closeLog() {
    CALL_debug.printlog(MOD_DEBUG, INFO, "Cleaning up logs...", null);
    if (logOut != null) {
      logOut.close();
    }

    if (logfile != null) {
      try {
        logfile.close();
      } catch (IOException e) {
        // Ignore for now
      }
    }
  }

  // Function to allow the external setting of the debug level
  public static void set_level(int module, int level) {
    if (level > MAX_LEVEL) {
      level = MAX_LEVEL;
    }

    if ((module >= 0) && (module < debug_level.length)) {
      if (level < 0)
        level = 0;
      if (level > MAX_LEVEL)
        level = MAX_LEVEL;
      debug_level[module] = level;
    } else if (module == MOD_GLOBAL) {
      // Set all the modules to be at the same level
      for (int i = 0; i < debug_level.length; i++) {
        debug_level[i] = level;
      }
    }
  }

  // Function to allow the external setting of the debug level using String
  // representation of module
  public static void set_level(String modulestr, int level) {
    int module = -999;

    // Find the module
    for (int i = 0; i < modNames.length; i++) {
      if (modNames[i].equals(modulestr)) {
        module = i;
        break;
      }
    }

    set_level(module, level);
  }

  public static int get_level(int module) {
    if ((module >= 0) && (module < debug_level.length)) {
      return debug_level[module];
    }

    return -1;
  }

  public static String module_name(int module) {
    if ((module >= 0) && (module < modNames.length)) {
      return modNames[module];
    }

    return null;
  }

  public static int num_modules() {
    return debug_level.length;
  }

  // Function to print out debug message to the log
  public static void printlog(int module, int level, String message, HttpServletRequest request) {
    String tempString;
    String moduleString;
    String dateString;
    String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/") + logfilename;

    if ((module >= 0) && (module < debug_level.length)) {
      if (level < 0)
        level = 0;
      if (level > MAX_LEVEL)
        level = MAX_LEVEL;
      if (level <= debug_level[module]) {

        // Does the logfile handle exist yet?
        if (logfile == null) {
          // Open Log file
          try {
            logfile = new FileWriter(path, true);
            logOut = new PrintWriter(logfile);
            logOut.println("");
            logOut.println("=====================NEW SESSION=====================");
            logOut.println("");
          } catch (IOException e) {
            System.out.println("logfile of log.txt is not found");
          }
        }

        // Log to file
        if (CALL_time.initialised) {
          dateString = CALL_time.getDate(CALL_time.DEFAULT_FORMAT);
        } else {
          // No date/time set yet!
          dateString = new String("");
        }

        if ((module >= 0) && (module < modNames.length)) {
          moduleString = modNames[module];
        } else {
          moduleString = new String("Unknown");
        }

        if (module != MOD_USAGE) {
          tempString = new String(levelLabel[level] + moduleString + ": " + message);
        } else {
          tempString = new String(levelLabelAlt[level] + moduleString + ": " + message);
        }

        logOut.println(dateString + " " + tempString);
      }
    }
  }

  // /////////////// OLD FUNCTIONS, PRE-MODULISING. PHASE THESE OUT
  // ///////////////////////

  // OLD: To be phased out! Function to allow the external setting of the debug
  // level
  public static void set_level(int level) {
    set_level(MOD_GLOBAL, level);
  }

  // Function to print out debug message to the command line - BECOMING
  // OBSOLETE, USE BELOW
  public static void println(int level, String message, HttpServletRequest request) {
    printlog(MOD_GENERAL, level, message, request);
  }

  // Function to print out debug message to the command line
  public static void println(int level, int module, String message, HttpServletRequest request) {
    printlog(MOD_GENERAL, level, message, request);
  }

  // Function to print out debug message to the log
  public static void printlog(int level, String message, HttpServletRequest request) {
    printlog(MOD_GENERAL, level, message, request);
  }

  public static void setLogging(boolean val) {
    // Do nothing
  }

  public static boolean getLogging() {
    // Fixed to on now
    return true;
  }
}
