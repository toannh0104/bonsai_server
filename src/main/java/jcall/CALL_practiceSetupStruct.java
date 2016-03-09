///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

public class CALL_practiceSetupStruct {
  // Defines
  static final int PTYPE_CONTEXT = 0;
  static final int PTYPE_VERB = 1;
  static final int PTYPE_VOCAB = 2;

  int practiceType;

  // Takes the database and parent (lesson)
  public CALL_practiceSetupStruct() {
    practiceType = PTYPE_CONTEXT;
  }
}