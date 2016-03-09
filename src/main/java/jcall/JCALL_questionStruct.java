/**
 * Created on 2007/07/02
 * @author wang
 * Copyrights @kawahara lab
 */
///////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
//				  the various inflections for each verb group.
//				  All data is loaded from the verbRules.txt file.
//
///////////////////////////////////////////////////////////////////

package jcall;

import java.util.Random;
import java.util.Vector;

public class JCALL_questionStruct {
  // Statics
  static final String NO_GROUP_NAME = new String("null");

  static final int QTYPE_CONTEXT = 0;
  static final int QTYPE_VERB = 1;
  static final int QTYPE_VOCAB = 2;

  // Fields
  CALL_lessonStruct lesson;
  CALL_configDataStruct config;
  CALL_configDataStruct gconfig;
  CALL_database db;

  // What is generated
  CALL_conceptInstanceStruct instance;
  CALL_diagramStruct diagram;
  CALL_sentenceStruct sentence;
  JCALL_sentenceHintsStruct hints;
  CALL_lessonQuestionStruct lessonQuestion;
  CALL_lessonConceptStruct lessonConcept;
  CALL_stringPairStruct parameter;

  Vector formGroups; // A vector of form group names (eg. "Style")
  Vector forms; // A vector of CALL_stringPairsStruct (eg. "Polite" &
                // "disabled")

  int questionType;

  // Takes the database and parent (lesson)
  public JCALL_questionStruct(CALL_database data, CALL_configDataStruct lc, CALL_configDataStruct gc,
      CALL_lessonStruct l, int type) {
    db = data;
    config = lc;
    gconfig = gc;
    lesson = l;

    instance = null;
    sentence = null;
    hints = null;
    lessonQuestion = null;
    lessonConcept = null;

    formGroups = new Vector();
    forms = new Vector();

    questionType = type;

    // Now generate the question itself (ie. sentence concept, lattice, diagram
    // etc)
    generate();
  }

  public boolean generate() {
    int size;
    double weight;
    boolean match;
    boolean useThisFormGroup;
    Random rand;
    String restrictionString;
    String questionString;

    JCALL_formDescGroupStruct formGroup;
    CALL_stringPairsStruct formPairs;

    rand = new Random();

    // 1) Pick a question
    weight = rand.nextDouble() * lesson.questionWeightTotal;
    lessonQuestion = lesson.getQuestion(weight);
    if (lessonQuestion == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
      // "Failed to aquire lesson question during question generation");
      return false;
    }

    // 2) Pick a concept from within that question
    weight = rand.nextDouble() * lessonQuestion.conceptWeightTotal;
    lessonConcept = lessonQuestion.getConcept(weight);
    if (lessonConcept == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
      // "Failed to aquire lesson concept during question generation");
      return false;
    }

    // 3) Get the concept instance
    instance = db.concepts.getInstance(lessonConcept.concept, questionType);
    if (instance == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
      // "Failed to generate concept instance during question generation");
      return false;
    }
    instance.print_debug();

    // 4) Generate the sentence
    sentence = new CALL_sentenceStruct(db, lessonConcept.grammar, instance);
    if (sentence == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
      // "Failed to generate word lattice during question generation");
      return false;
    }
    sentence.print_debug();

    // 4.5) Generate Julian grammar
    // grammar = new CALL_julianGrammarStruct(db, lessonConcept.grammar,
    // instance

    // 5) Get the appropriate diagram
    if (questionType == QTYPE_CONTEXT) {
      // A standard question, based on a whole sentence - thus the full diagram
      // is needed
      diagram = lesson.getDiagram(lessonConcept.diagram);
    } else if (questionType == QTYPE_VOCAB) {
      // A one component diagram
      diagram = new CALL_diagramStruct("Word");
      diagram.singleWord();
    }

    // 6) Set the forms based on grammar & instance
    if (questionType == QTYPE_CONTEXT) {
      for (int i = 0; i < lesson.formGroups.size(); i++) {
        formGroup = (JCALL_formDescGroupStruct) lesson.formGroups.elementAt(i);

        if (formGroup != null) {
          // Check whether this group is picky over which questions may use it
          useThisFormGroup = true;
          if ((formGroup.questions != null) && (formGroup.questions.size() > 0)) {
            useThisFormGroup = false;
            for (int j = 0; j < formGroup.questions.size(); j++) {
              questionString = (String) formGroup.questions.elementAt(j);
              if (questionString != null) {
                if (questionString.equals(lessonQuestion.question)) {
                  // This question does use this form
                  useThisFormGroup = true;
                  break;
                }
              }
            }
          }

          if (useThisFormGroup) {
            formPairs = (CALL_stringPairsStruct) formGroup.getFormText(this);

            if (formPairs != null) {
              // Add the display string pairs (eg. "Polite", "false")
              forms.addElement(formPairs);

              // Now add the group name
              if (formGroup.name != null) {
                formGroups.addElement(formGroup.name);
              } else {
                formGroups.addElement(NO_GROUP_NAME);
              }
            }
          }
        }
      }
    }

    // Finally, generate the hints
    hints = new JCALL_sentenceHintsStruct(this);

    hints.print_debug();

    return true;
  }

  // This returns the remaining (maximum) score
  // ===========================================
  public int getMaxScore() {
    return hints.getRemainingCost();
  }

  public String getFormString() {
    String formString = null;
    CALL_stringPairsStruct pairs;
    CALL_stringPairStruct pair;

    for (int i = 0; i < forms.size(); i++) {
      pairs = (CALL_stringPairsStruct) forms.elementAt(i);
      if (pairs != null) {
        for (int j = 0; j < pairs.parameters.size(); j++) {
          pair = (CALL_stringPairStruct) pairs.parameters.elementAt(j);
          if (pair != null) {
            // We have a form string
            if (pair.parameter != null) {
              if (pair.value.equals("true")) {
                // Form in use - add to string
                if (formString == null) {
                  formString = pair.parameter;
                } else {
                  formString = formString + ", " + pair.parameter;
                }
              }
            }
          }
        }
      }
    }

    return formString;
  }
}
