///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Vector;

public class CALL_hintStruct {
  // Fields
  String hintKana;
  String hintKanji;

  // We allow sub-stages to a hint. Not like levels, so not used for labeling.
  // e.g. Showing baseform sylable by sylable
  // If stages > 1, the substages are stored in subHintKana/Kanji.
  //
  // eg. for the word WATASHI, we would* have: (* The order of sylable
  // uncovering may vary, randomly)
  //
  // stages = 3
  // hintKana = "WATASHI"
  // subHintKana(0) = "WA****"
  // subHintKana(1) = "WATA**"
  //
  // Note that the cost would be divided across the substages in this case...
  int stages;
  Vector subHintKana;
  Vector subHintCost;

  public String getHintKana() {
    return hintKana;
  }

  public String getHintKanji() {
    return hintKanji;
  }

  public Vector getSubHintKana() {
    return subHintKana;
  }

  public Vector getSubHintCost() {
    return subHintCost;
  }

  int cost; // The cost associated with this hint
  int difficulty; // The perceived difficulty rating of this hint

  // Empty Constructor
  public CALL_hintStruct() {
    hintKana = null;
    cost = 0;
    difficulty = 0;
    stages = 0;
    subHintKana = new Vector();
    subHintCost = new Vector();
  }

  public String getHintString(int outputStyle) {
    String rString;

    if (outputStyle == CALL_io.kanji) {
      rString = hintKanji;
    } else {
      rString = hintKana;
    }

    return rString;
  }

  public String getHintString(int outputStyle, int stage) {
    String rString = null;

    if (stage >= 0) {
      if (stage >= stages) {
        // The final "stage" (actually not a stage, but the main hint)
        // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
        // "Returning the main hint string");
        rString = getHintString(outputStyle);
      } else {
        // We are looking at a sub-stage step (NO KANJI VERSION, always use
        // kana)
        // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
        // "Returning a sub hint string");
        rString = (String) subHintKana.elementAt(stage);
      }
    }

    return rString;
  }

  // Gets the cost of the (sub)Hint at the specified stage
  // =================================================
  public int getCost(int currentStage) {
    Integer tempInt;
    int retInt = 0;

    if (currentStage >= 0) {
      if (currentStage < stages) {
        // An sub-level hint. Get cost from sub-structure
        tempInt = (Integer) subHintCost.elementAt(currentStage);
        if (tempInt != null) {
          retInt = tempInt.intValue();
        }
      } else {
        // It's the last element...this cost is stored in this structure
        retInt = cost;
      }
    }

    return retInt;
  }

  public int getTotalCost() {
    return getRemainingCost(0);
  }

  // Get the remaining cost for this hint, from the next stage onwards
  // ==============================================================
  public int getRemainingCost(int fromStage) {
    int retInt = 0;
    Integer tempInt;

    if (fromStage <= stages) {
      // Do the sub levels
      for (int i = fromStage; i < stages; i++) {
        retInt += getCost(i);
      }

      // Now the main hint
      retInt += cost;
    }

    return retInt;
  }

  public void print_debug() {
    String subHintString;
    Integer subCost;

    // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.INFO, "-->Hint [" +
    // hintKana + "]; Cost: " + cost + ", Difficulty: " + difficulty);
    for (int i = 0; i < stages; i++) {
      subHintString = (String) subHintKana.elementAt(i);
      subCost = (Integer) subHintCost.elementAt(i);
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.INFO,
      // "----> Sub hint [" + subHintString + "], Cost: " + subHintCost);
    }
  }
}