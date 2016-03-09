///**
// * Created on 2009/12/09
// * @author wang
// * Copyrights @kawahara lab
// */
//package jcall.analysis;
//
//import java.util.Vector;
//
//import org.apache.log4j.Logger;
//
//import jcall.CALL_conceptInstanceStruct;
//import jcall.CALL_configDataStruct;
//import jcall.CALL_database;
//import jcall.CALL_debug;
//import jcall.CALL_diagramStruct;
//import jcall.CALL_lessonConceptStruct;
//import jcall.CALL_lessonQuestionStruct;
//import jcall.CALL_lessonStruct;
//import jcall.CALL_sentenceHintsStruct;
//import jcall.CALL_sentenceStruct;
//import jcall.CALL_stringPairStruct;
//import jcall.CALL_stringPairsStruct;
//import jcall.JCALL_formDescGroupStruct;
//import jcall.JCALL_sentenceHintsStruct;
//import jcall.db.JCALL_NetworkStruct;
//
//public class CALL_Question {
//
//	static Logger logger = Logger.getLogger (CALL_Question.class.getName());
//	
//	// Fields
//	int lesson;
//	int questionType;
//	
//	CALL_conceptInstanceStruct 	instance;
////	CALL_sentenceStruct 		sentence;	//??????
//
////	CALL_lessonQuestionStruct	lessonQuestion;
////	CALL_lessonConceptStruct 	lessonConcept;
////	CALL_stringPairStruct 		parameter;
//
////	
////	Vector network; // A vector of JCALL_NetworkNodeStruct
//	JCALL_NetworkStruct wordnet; // A vector of JCALL_NetworkNodeStruct
//	
//	Vector formGroups;	// A vector of form group names (eg. "Style")
//	Vector forms;		// A vector of CALL_stringPairsStruct (eg. "Polite" & "disabled")
//	
//	
//	
//	//The most propriate sentence -------generated
//	
//	public void questionGen(CALL_database data,CALL_lessonQuestionStruct lessonQuestion, CALL_lessonConceptStruct lessonConcept,int type) {
//		// TODO Auto-generated method stub
//		// 3) Get the concept instance
//		
//		instance = data.concepts.getInstance(lessonConcept.concept, type);
//		
//		
//		if(instance == null)
//		{
//			// Error
//			logger.debug("Failed to generate concept instance during question generation");
//			return;
//		}
//		instance.print_debug();
//		
//		//CALL_database db;
//		// 4) Generate the sentence
//		CALL_sentenceStruct sentence = new CALL_sentenceStruct(db, lessonConcept.grammar, instance);
//		if(sentence == null)
//		{
//			// Error
//			CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR, "Failed to generate word lattice during question generation");
//			return false;
//		}
//		sentence.print_debug();
//		
//		// 4.5) Generate Julian grammar
//		// grammar = new CALL_julianGrammarStruct(db, lessonConcept.grammar, instance
//		
//		// 5) Get the appropriate diagram
//		if(questionType == QTYPE_CONTEXT)
//		{
//			// A standard question, based on a whole sentence - thus the full diagram is needed
//			diagram = lesson.getDiagram(lessonConcept.diagram);
//		}
//		else if(questionType == QTYPE_VOCAB)
//		{
//			// A one component diagram
//			diagram = new CALL_diagramStruct("Word");
//			diagram.singleWord();
//		}		
//		
//		// 6) Set the forms based on grammar & instance
//		if(questionType == QTYPE_CONTEXT)
//		{
//			for(int i = 0; i < lesson.formGroups.size(); i++)
//			{
//				formGroup = (JCALL_formDescGroupStruct) lesson.formGroups.elementAt(i);
//				
//				if(formGroup != null)
//				{
//					// Check whether this group is picky over which questions may use it
//					useThisFormGroup = true;
//					if((formGroup.questions != null) && (formGroup.questions.size() > 0))
//					{
//						useThisFormGroup = false;
//						for(int j = 0; j < formGroup.questions.size(); j++)
//						{
//							questionString = (String) formGroup.questions.elementAt(j);
//							if(questionString != null)
//							{
//								if(questionString.equals(lessonQuestion.question))
//								{
//									// This question does use this form
//									useThisFormGroup = true;
//									break;
//								}
//							}
//						}
//					}
//					
//					if(useThisFormGroup)
//					{
//						formPairs = (CALL_stringPairsStruct) formGroup.getFormText(this);
//						
//						if(formPairs != null)
//						{
//							// Add the display string pairs (eg. "Polite", "false")
//							forms.addElement(formPairs);
//							
//							// Now add the group name
//							if(formGroup.name != null)
//							{
//								formGroups.addElement(formGroup.name);
//							}
//							else
//							{
//								formGroups.addElement(NO_GROUP_NAME);
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		
//		
//	}
//	
//	
//	
//	
//	
// }
