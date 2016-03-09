///////////////////////////////////////////////////////////////////
// Lesson History - the students history for a particular lesson
//
//

package jcall;

public class CALL_lessonHistoryStruct {
  // Defines
  static final long MAX_TOTALSCORE = 99999;
  static final long SCORE_SCALE_FACTOR = 10;

  // General information
  int lessonIndex;

  // For practice
  int practiceRuns;
  int practiceQuestions;
  int practiceAnswered;
  long practiceTotalScore;
  long practiceTotalMaxScore;
  long practiceTimeSpent;

  // For tests
  int testRuns;
  int testQuestions;
  int testAnswered;
  int testBestScore;
  long testTotalScore;
  long testTotalMaxScore;
  long testTimeSpent;

  public CALL_lessonHistoryStruct() {
    // Just reset the variables
    lessonIndex = 0;
    practiceRuns = 0;
    practiceQuestions = 0;
    practiceAnswered = 0;
    practiceTimeSpent = 0;
    practiceTotalScore = 0;
    practiceTotalMaxScore = 0;
    testRuns = 0;
    testQuestions = 0;
    testAnswered = 0;
    testTimeSpent = 0;
    testTotalScore = 0;
    testTotalMaxScore = 0;
    testBestScore = 0;
  }

  // Gives the values as a string
  public String toString() {
    String tempString = null;

    tempString = "" + lessonIndex;
    tempString = tempString + " " + practiceRuns;
    tempString = tempString + " " + practiceQuestions;
    tempString = tempString + " " + practiceAnswered;
    tempString = tempString + " " + practiceTotalScore;
    tempString = tempString + " " + practiceTotalMaxScore;
    tempString = tempString + " " + practiceTimeSpent;

    tempString = tempString + " " + testRuns;
    tempString = tempString + " " + testQuestions;
    tempString = tempString + " " + testAnswered;
    tempString = tempString + " " + testBestScore;
    tempString = tempString + " " + testTotalScore;
    tempString = tempString + " " + testTotalMaxScore;
    tempString = tempString + " " + testTimeSpent;

    return tempString;
  }

  // Scale the value so they don't swell to much over time
  public void normalize() {
    if (practiceTotalMaxScore > MAX_TOTALSCORE) {
      practiceTotalMaxScore = practiceTotalMaxScore / SCORE_SCALE_FACTOR;
      practiceTotalScore = practiceTotalScore / SCORE_SCALE_FACTOR;
    }
  }
}