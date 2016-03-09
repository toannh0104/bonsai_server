///////////////////////////////////////////////////////////////////
// Lesson Structure - holds the information and constraints of a lesson
//
//

package jcall;

public class CALL_lessonConceptStruct {
  // Statics
  final static double DEFAULT_WEIGHT = 1.0;

  // Fields
  String text;
  public String concept;
  public String grammar;
  String diagram;

  double weight;

  public CALL_lessonConceptStruct() {
    init();
  }

  public CALL_lessonConceptStruct(String name) {
    init();
    concept = name;
  }

  public void init() {
    text = null;
    concept = null;
    grammar = null;
    diagram = null;
    weight = DEFAULT_WEIGHT;
  }

	public void reset() {
		text = null;
		concept = null;
		grammar = null;
		diagram = null;
		weight = DEFAULT_WEIGHT;
	}
  public void print_debug() {
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "Lesson Concept: " + text);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "-->Concept: " + concept);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "-->Grammar: " + grammar);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "-->Diagram: " + diagram);
  }
}