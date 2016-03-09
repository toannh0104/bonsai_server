///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

public class CALL_wordHintsStruct {
  static final int MAX_LEVELS = 5;

  // Fields
  CALL_hintStruct hints[]; // 0: Type (eg. Verb, Subj), 1: English, 2: Japanese,
                           // 3: Final form (Varies with type etc)
  int currentLevel;
  int currentStage;
  int totalScore;

  public CALL_hintStruct[] getHints() {
    return hints;
  }

  // Empty Constructor
  public CALL_wordHintsStruct() {
    totalScore = 0;
    hints = new CALL_hintStruct[MAX_LEVELS];

    currentLevel = 0;
    currentStage = 0;

    for (int i = 0; i < MAX_LEVELS; i++) {
      hints[i] = null;
    }
  }

  // Get the current hint level - this is the hint that is currently being
  // displayed
  // ===============================================================================
  public CALL_hintStruct getCurrentHint() {
    CALL_hintStruct hint = null;

    if ((currentLevel >= 0) && (currentLevel < MAX_LEVELS)) {
      hint = hints[currentLevel];

      if (hint == null) {
        // Current hint doesn't exist, so have to move on
        hint = getNextHint();
      }
    }

    return hint;
  }

  // Get the current hint string - get the string for the hint that is currently
  // being displayed, and includes the subhints
  // ====================================================================================================
  public String getCurrentHintString(int outputFormat) {
    String retString = null;
    CALL_hintStruct hint = getCurrentHint();

    if (hint != null) {
      retString = hint.getHintString(outputFormat, currentStage);
    }

    return retString;
  }

  // Move on to the next hint - this might either be the hint at the next level,
  // or just moving down through sub-levels of a hint
  // =========================================================================================================
  public CALL_hintStruct getNextHint() {
    CALL_hintStruct nextHint = null;
    CALL_hintStruct currentHint = null;

    // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
    // "Getting next hint");

    currentHint = getCurrentHint();
    if ((currentHint != null) && (currentStage < currentHint.stages)) {
      // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
      // "Same hint, but next sub-stage");
      currentStage++;
      nextHint = currentHint;
    } else {
      // Move on to next hint level
      while (currentLevel < MAX_LEVELS) {
        incrementLevel();
        currentStage = 0;

        // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
        // "Next hint...");

        if ((currentLevel >= 0) && (currentLevel < MAX_LEVELS)) {
          if (hints[currentLevel] != null) {
            nextHint = hints[currentLevel];
            // CALL_debug.printlog(CALL_debug.MOD_HINTS, CALL_debug.DEBUG,
            // "Next hint found");
            break;
          }
        }
      }
    }
    return nextHint;
  }

  // Get the hint for the next level if we've finished with the hint from the
  // current level (no more unrevealed sub-hints etc)
  // ======================================================================================================
  public int getNextLevel() {
    int tempLevel;
    int rLevel = -1;

    for (tempLevel = currentLevel; tempLevel < MAX_LEVELS; tempLevel++) {
      if (tempLevel < 0) {
        // Invalid level, continue
        continue;
      }

      if (hints[tempLevel] != null) {
        if (tempLevel == currentLevel) {
          if (hints[tempLevel] != null) {
            if ((hints[tempLevel].stages > 0) && (currentStage < hints[tempLevel].stages)) {
              // Still on current level (more stages to go)
              rLevel = tempLevel;
              break;
            }
          }
        } else {
          // Move on to this level next
          rLevel = tempLevel;
          break;
        }
      }
    }

    return rLevel;
  }

  // Returns the hint structure for the given level
  // =======================================================
  public CALL_hintStruct getLevelHint(int level) {
    CALL_hintStruct hint = null;

    if (levelUnrevealed(level)) {
      // There is a hint available at this level
      currentLevel = level;
      hint = hints[level];
    }

    return hint;
  }

  public boolean areThereMoreHints() {
    int tempLevel;
    boolean rc = false;

    for (tempLevel = currentLevel; tempLevel < MAX_LEVELS; tempLevel++) {
      if (tempLevel < 0)
        continue;

      if (tempLevel == currentLevel) {
        if (currentStage < hints[tempLevel].stages) {
          rc = true;
        }
      } else if (hints[tempLevel] != null) {
        // There are more hints
        rc = true;
      }
    }

    // If we get here, there are no more hints
    return rc;
  }

  // Is the next hint the last one?
  // =============================
  public boolean lastHintNext() {

    boolean rc = false;
    int tempLevel;
    int furtherQ = 0;

    if (areThereMoreHints()) {
      for (tempLevel = currentLevel; tempLevel < MAX_LEVELS; tempLevel++) {
        if (tempLevel == currentLevel) {
          // Deal with current levels subStages
          furtherQ += (hints[tempLevel].stages) - currentStage;
        }

        else if (hints[tempLevel] != null) {
          // An unrevealed hint
          furtherQ += hints[tempLevel].stages;
        }
      }
    }

    if (furtherQ == 1) {
      // There is only 1 more hint left unrevealed
      rc = true;
    }

    return rc;
  }

  // Returns the total cost of the component
  // Note, this doesn't take any notice of what
  // the levels are set as, so will always include
  // all the levels, even if they have been
  // revealed, or are to be skipped
  // ====================================
  public int getTotalCost() {
    int retInt = 0;

    for (int i = 0; i < MAX_LEVELS; i++) {
      if (hints[i] != null) {
        retInt += hints[i].getTotalCost();
      }
    }

    return retInt;
  }

  // Returns the cost to reach the specified level (in it's completeness - all
  // sublevels revealed)
  // =====================================================================================
  public int costToLevel(int level) {
    int cost = 0;
    int tempLevel;
    Integer stageCost;

    if (areThereMoreHints()) {
      for (tempLevel = currentLevel; tempLevel <= level; tempLevel++) {
        if (tempLevel < 0)
          continue;

        if (tempLevel == currentLevel) {
          // Only from current stage up
          cost += hints[tempLevel].getRemainingCost(currentStage + 1);
        } else {
          if (hints[tempLevel] != null) {
            // Cost for all stages
            cost += hints[tempLevel].getRemainingCost(0);
          }
        }
      }
    }

    return cost;
  }

  // Get the cost of the next hint of this component
  // ===========================================
  public int getNextCost() {
    int tempLevel;
    int cost = 0;
    CALL_hintStruct hint;

    for (tempLevel = currentLevel; tempLevel < MAX_LEVELS; tempLevel++) {
      if (tempLevel < 0)
        continue;

      hint = hints[tempLevel];

      if (hint != null) {
        if (tempLevel == currentLevel) {
          if (currentStage < (hint.stages - 1)) {
            // More stages on current hint, so get the cost for that...
            cost = hint.getCost(currentStage + 1);
            break;
          }
        } else {

          // Ok, this is the next hint - get cost of first subhint, and break
          cost = hint.getCost(0);
          break;
        }
      }
    }

    return cost;
  }

  public int getCurrentCost() {
    int retInt = 0;
    CALL_hintStruct hint = getCurrentHint();

    if (hint != null) {
      retInt = hint.getCost(currentStage);
    }

    return retInt;
  }

  public String getCategory() {
    String cat = null;
    if (hints[0] != null) {
      cat = hints[0].hintKana; // Both the kana and kanji hints are actually
                               // english for "type"
    }

    return cat;
  }

  public void incrementLevel() {
    currentLevel++;
  }

  public boolean levelUnrevealed(int level) {
    // Is there a hint a category (level), and is it yet to be revealed?
    boolean rc = false;

    if ((level > currentLevel) && (level >= 0) && (level < MAX_LEVELS)) {
      if (hints[level] != null) {
        rc = true;
      }
    }
    if (level == currentLevel) {
      if ((hints[currentLevel] != null) && (currentStage < (hints[currentLevel].stages - 1))) {
        // We're on this level now, but still some unrevealed hints
        rc = true;
      }
    }

    return rc;
  }

  public int getStages(int level) {
    int retInt = 0;

    if (hints[level] != null) {
      retInt = hints[level].stages;
    }

    return retInt;
  }

  public void print_debug() {
    for (int i = 0; i < MAX_LEVELS; i++) {
      if (hints[i] != null) {
        hints[i].print_debug();
      }
    }
  }
}