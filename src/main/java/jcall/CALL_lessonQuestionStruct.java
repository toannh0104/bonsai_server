///////////////////////////////////////////////////////////////////
// Lesson Structure - holds the information and constraints of a lesson
//
//

package jcall;

import java.util.Vector;

public class CALL_lessonQuestionStruct {
  // Statics
  final static double DEFAULT_WEIGHT = 1.0;

  // Fields
  String question;
  double conceptWeightTotal;
  boolean verbOnlyOption;

  // Sub structures
  public Vector concepts;

  double weight;

  public CALL_lessonQuestionStruct() {
    init();
  }

  public CALL_lessonQuestionStruct(String name) {
    init();
    question = name;
  }

  public void init() {
    concepts = new Vector();
    question = null;
    verbOnlyOption = false;
    conceptWeightTotal = 0;
    weight = DEFAULT_WEIGHT;
  }

  public void reset(){
	  concepts.clear();
	  question = null;
	  verbOnlyOption = false;
	  verbOnlyOption = false;
	  conceptWeightTotal = 0;
	  weight = DEFAULT_WEIGHT;
  }
  public CALL_lessonConceptStruct getConcept(double weight) {
    CALL_lessonConceptStruct concept;
    CALL_lessonConceptStruct returnData = null;
    double currentWeight = 0;

    for (int i = 0; i < concepts.size(); i++) {
      concept = (CALL_lessonConceptStruct) concepts.elementAt(i);
      if (concept != null) {
        currentWeight += concept.weight;
        if (currentWeight >= weight) {
          // Stop here - this is the concept we'll use
          returnData = concept;
          break;
        }
      }
    }

    return returnData;
  }

  public void print_debug() {
    CALL_lessonConceptStruct concept;

    // Basic information
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "Question Type: " + question);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "Question Weight: " + weight);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "Question - Allow Verb Only: " + verbOnlyOption);

    // Now for the concepts
    for (int i = 0; i < concepts.size(); i++) {
      concept = (CALL_lessonConceptStruct) concepts.elementAt(i);
      if (concept != null) {
        concept.print_debug();
      }
    }
  }

  public Vector getConcepts() {
    return concepts;
  }

  public void setConcepts(Vector concepts) {
    this.concepts = concepts;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

}