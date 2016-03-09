///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.io.PrintWriter;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CALL_lexiconExperienceStruct {
  static Logger logger = Logger.getLogger(CALL_lexiconExperienceStruct.class.getName());

  // Defines
  static final int INITIAL_WORD_ARRAY_SIZE = 500;
  static final int MAX_ID = 99999;
  static final int MAX_TYPE = 99;

  // Fields
  Vector wordExperience;

  // Empty Constructor
  public CALL_lexiconExperienceStruct() {
    wordExperience = new Vector();
    for (int i = 0; i < CALL_lexiconStruct.LEX_NUM_TYPES; i++) {
      // Add the sub-vectors, all empty initially
      wordExperience.addElement(new Vector());
    }
  }

  public void addWordExperience(int type, int id, long experience, long issues) {
    Vector desiredVector;
    CALL_wordExperienceStruct word;

    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
    // "Adding word experience: Type: " + type + ", ID: " + id + ", Exp: " +
    // experience + "Iss:" + issues);

    if ((type >= 0) && (id >= 0) && (id < MAX_ID) && (type < MAX_TYPE)) {
      // We should have filtered out any silly values now!

      if (type < wordExperience.size()) {
        desiredVector = (Vector) wordExperience.elementAt(type);
        if (id < desiredVector.size()) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(id);
          if (word != null) {
            word.experience = experience;
            word.issues = issues;
            // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
            // "Added");
          }
        } else {
          // Need to add elements up until the desired id
          for (int i = desiredVector.size(); i <= id; i++) {
            word = new CALL_wordExperienceStruct(type, i);
            if (i == id) {
              // This is the one we really want to add
              word.experience = experience;
              word.issues = issues;
              // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
              // "Created, then Added");
            }
            desiredVector.addElement(word);

          }
        }
      }
    }
  }

  public void incrementWordExperience(int type, int id) {
    CALL_wordExperienceStruct word;
    Vector desiredVector;

    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
    // "Incrementing experience of word type " + type + ", id " + id);

    if ((type >= 0) && (id >= 0) && (id < MAX_ID) && (type < MAX_TYPE)) {
      // We should have filtered out any silly values now!

      if (type < wordExperience.size()) {
        desiredVector = (Vector) wordExperience.elementAt(type);
        if (id < desiredVector.size()) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(id);
          if (word != null) {
            word.experience++;
            // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
            // "Incremented.  Experience now : " + word.experience);
          }
        } else {
          // Need to add elements up until the desired id
          for (int i = desiredVector.size(); i <= id; i++) {
            word = new CALL_wordExperienceStruct(type, i);
            if (i == id) {
              // This is the one we really want to increment, but it's a new
              // one, so set it to 1
              word.experience = 1;
              word.issues = 0;
              // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
              // "Created, then incremented.  Experience now : " +
              // word.experience);
            }
            desiredVector.addElement(word);
          }
        }
      }
    }
  }

  public void incrementWordIssues(int type, int id) {
    logger.debug("enter incrementWordIssues");

    CALL_wordExperienceStruct word;
    Vector desiredVector;

    // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
    // "Incrementing experience of word type " + type + ", id " + id);

    if ((type >= 0) && (id >= 0) && (id < MAX_ID) && (type < MAX_TYPE)) {
      // We should have filtered out any silly values now!

      if (type < wordExperience.size()) {
        desiredVector = (Vector) wordExperience.elementAt(type);
        if (id < desiredVector.size()) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(id);
          if (word != null) {
            word.issues++;
            // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
            // "Incremented.  Issues now : " + word.issues);
          }
        } else {
          // Need to add elements up until the desired id
          for (int i = desiredVector.size(); i <= id; i++) {
            word = new CALL_wordExperienceStruct(type, i);
            if (i == id) {
              // This is the one we really want to increment, but it's a new
              // one, so set it to 1
              word.experience = 1;
              word.issues = 1;
              // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.DEBUG,
              // "Created, then incremented.  Experience now : " + word.issues);
            }
            desiredVector.addElement(word);
          }
        }
      }
    }
  }

  public long getWordExperience(int type, int id) {
    long experience = 0;
    CALL_wordExperienceStruct word;
    Vector desiredVector;

    if ((type >= 0) && (id >= 0) && (id < MAX_ID) && (type < MAX_TYPE)) {
      // We should have filtered out any silly values now!

      if (type < wordExperience.size()) {
        desiredVector = (Vector) wordExperience.elementAt(type);
        if (id < desiredVector.size()) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(id);
          if (word != null) {
            experience = word.experience;
          }
        }
      }
    }

    return experience;
  }

  public long getWordIssues(int type, int id) {
    long issues = 0;
    CALL_wordExperienceStruct word;
    Vector desiredVector;

    if ((type >= 0) && (id >= 0) && (id < MAX_ID) && (type < MAX_TYPE)) {
      // We should have filtered out any silly values now!

      if (type < wordExperience.size()) {
        desiredVector = (Vector) wordExperience.elementAt(type);
        if (id < desiredVector.size()) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(id);
          if (word != null) {
            issues = word.issues;
          }
        }
      }
    }

    return issues;
  }

  public void write(PrintWriter outP) {
    CALL_wordExperienceStruct word;
    Vector desiredVector;

    for (int i = 0; i < wordExperience.size(); i++) {
      desiredVector = (Vector) wordExperience.elementAt(i);
      if (desiredVector != null) {
        for (int j = 0; j < desiredVector.size(); j++) {
          word = (CALL_wordExperienceStruct) desiredVector.elementAt(j);
          if (word != null) {
            if ((word.id != -1) && (word.type != -1)) {
              if (word.experience > 0) {
                // A valid word, with experience, so write it out
                outP.println("-wordExperience " + word.type + " " + word.id + " " + word.experience + " " + word.issues);
              }
            }
          }
        }
      }
    }
  }
}