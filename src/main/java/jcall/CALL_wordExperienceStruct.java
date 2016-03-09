///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

public class CALL_wordExperienceStruct {
  // Fields
  int type;
  int id;

  long experience; // Number of views
  long issues; // Number of issues (hints & errors) on this word

  // More information about errors etc?

  // Empty Constructor
  public CALL_wordExperienceStruct() {
    type = -1;
    id = -1;
    experience = 0;
    issues = 0;
  }

  // New constructor
  public CALL_wordExperienceStruct(int p_wordType, int p_wordID) {
    type = p_wordType;
    id = p_wordID;
    experience = 0;
    issues = 0;
  }

  // Full constructor
  public CALL_wordExperienceStruct(int p_wordType, int p_wordID, long p_experience, long p_issues) {
    type = p_wordType;
    id = p_wordID;
    experience = p_experience;
    issues = p_issues;
  }
}