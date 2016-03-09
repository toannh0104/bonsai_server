///////////////////////////////////////////////////////////////////
// Lesson Structure - holds the information and constraints of a lesson
//
//

package jcall;

import java.util.Vector;

public class CALL_lessonStruct {
  // Statics
  static final int MAX_LEVEL = 4;
  static final int DEFAULT_INDEX = -1;
  static final String DEFAULT_TITLE = "unused";

  // Structures
  public Vector questions; // A vector of lessonQuestionStructs (the possible
                           // question types for lesson)
  Vector formGroups; // A vector of formDescGroupStructs
  Vector diagrams; // The diagram structure(s) (Vector of type
                   // CALL_diagramStruct)
  Vector examples; // Stores the information on the lesson example(s)

  // Fields
  int index;
  boolean levels[] = { false, false, false, false }; // Levels 1, 2, 3 and 4
                                                     // allowed

  String title;
  String description;

  // Is the lesson active or not (does it have sufficient data etc
  double questionWeightTotal;

  public CALL_lessonStruct() {
    // Doesn't do much in here at present
    index = DEFAULT_INDEX;
    title = new String(DEFAULT_TITLE);
    description = null;

    // Set up the data structures
    formGroups = new Vector();
    questions = new Vector();
    diagrams = new Vector();
    examples = new Vector();
  }
  public void reset(){
	    index = DEFAULT_INDEX;
	    title = null;
	    description = null;

	    // Set up the data structures
	    formGroups = null;
	    questions = null;
	    diagrams = null;
	    examples = null;
  }
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Vector getDiagrams() {
    return diagrams;
  }

  public void setDiagrams(Vector diagrams) {
    this.diagrams = diagrams;
  }

  public Vector getExamples() {
    return examples;
  }

  public void setExamples(Vector examples) {
    this.examples = examples;
  }

  public Vector getFormGroups() {
    return formGroups;
  }

  public void setFormGroups(Vector formGroups) {
    this.formGroups = formGroups;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public boolean[] getLevels() {
    return levels;
  }

  public void setLevels(boolean[] levels) {
    this.levels = levels;
  }

  public Vector getQuestions() {
    return questions;
  }

  public void setQuestions(Vector questions) {
    this.questions = questions;
  }

  public double getQuestionWeightTotal() {
    return questionWeightTotal;
  }

  public void setQuestionWeightTotal(double questionWeightTotal) {
    this.questionWeightTotal = questionWeightTotal;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  // Return the selected lesson question based on weight passed in
  public CALL_lessonQuestionStruct getQuestion(double weight) {
    CALL_lessonQuestionStruct question;
    CALL_lessonQuestionStruct returnData = null;
    double currentWeight = 0;

    for (int i = 0; i < questions.size(); i++) {
      question = (CALL_lessonQuestionStruct) questions.elementAt(i);
      if (question != null) {
        currentWeight += question.weight;
        if (currentWeight >= weight) {
          // Stop here - this is the concept we'll use
          returnData = question;
          break;
        }
      }
    }

    return returnData;
  }

  // Is the lesson complete, and thus active
  public boolean lessonActive() {
    boolean active = false;

    if (questions.size() > 0) {
      if (formGroups.size() > 0) {
        if (diagrams.size() > 0) {
          active = true;
        }
      }
    }

    return active;
  }

  public void setLevel(int level, boolean value) {
    if ((level >= 1) && (level <= MAX_LEVEL)) {
      levels[level - 1] = value;
    }
  }

  // Return the diagram with given name
  public CALL_diagramStruct getDiagram(String name) {
    CALL_diagramStruct diagram = null;

    for (int i = 0; i < diagrams.size(); i++) {
      diagram = (CALL_diagramStruct) diagrams.elementAt(i);
      if (diagram != null) {
        if (diagram.name.equals(name)) {
          // We've found our match
          break;
        } else {
          diagram = null;
        }
      }
    }

    return diagram;
  }

  // 2011.08.10 T.Tajima change for xml+xsl
  /*
   * public boolean hasNotes(int outputStyle) { String typeStr; File testFile;
   *
   * if(outputStyle == CALL_io.romaji) { typeStr = new String("R"); } else
   * if(outputStyle == CALL_io.kana) { typeStr = new String("K"); } else {
   * typeStr = new String("J"); }
   *
   * testFile = new File("./Data/html/" + index + "/notes-" + typeStr +
   * ".html"); if(testFile == null) { return false; } else
   * if(!testFile.exists()) { return false; }
   *
   * return true; }
   */

  public boolean hasNotes(int outputStyle) {
    JCALL_lessonInfoPane notes = new JCALL_lessonInfoPane(outputStyle, index);
    return notes.hasNotes();
  }

  public void print_debug() {
    CALL_lessonQuestionStruct question;
    CALL_diagramStruct diagram;
    CALL_formDescGroupStruct formGroup;
    String tempStr;
    int i, j;

    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO, "Lesson: " +
    // index);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO, "Title: " +
    // title);
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "# Question Types:" + questions.size());
    // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.INFO,
    // "# Form Groups:" + formGroups.size());
    // Now for the sub structures

    // First the sub-questions
    for (i = 0; i < questions.size(); i++) {
      question = (CALL_lessonQuestionStruct) questions.elementAt(i);
      if (question != null) {
        question.print_debug();
      }
    }

    // Now the form groups
    for (i = 0; i < formGroups.size(); i++) {
      formGroup = (CALL_formDescGroupStruct) formGroups.elementAt(i);
      if (formGroup != null) {
        formGroup.print_debug();
      }
    }

    // The diagram template
    for (i = 0; i < diagrams.size(); i++) {
      diagram = (CALL_diagramStruct) diagrams.elementAt(i);

      if (diagram != null) {
        diagram.print_debug();
      }
    }
  }
}