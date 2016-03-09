///**
// * Created on 2009/12/09
// * @author wang
// * Copyrights @kawahara lab
// */
//package jcall.analysis;
//
//import java.io.IOException;
//import java.util.Random;
//import java.util.Vector;
//
//import org.apache.log4j.Logger;
//
//import jcall.CALL_configDataStruct;
//import jcall.CALL_database;
//import jcall.CALL_debug;
//import jcall.CALL_diagramStruct;
//import jcall.CALL_lessonConceptStruct;
//import jcall.CALL_lessonQuestionStruct;
//import jcall.CALL_lessonStruct;
//import jcall.CALL_sentenceStruct;
//import jcall.CALL_stringPairsStruct;
//import jcall.JCALL_formDescGroupStruct;
//import jcall.JCALL_sentenceHintsStruct;
//
//public class CALL_Questions {
//
//	static Logger logger = Logger.getLogger (CALL_Questions.class.getName());
//	
//	Vector  vQuestion; //Element type: CALL_Question 
//	
//	CALL_database db;
//	
//	int questionType;
//	
//	CALL_lessonStruct lesson; //?? delete?
//	
//	/*
//	 * Takes the database and lesson Struct l
//	 * 
//	 */
//	
//	public CALL_Questions(CALL_database data,CALL_lessonStruct l, int type)
//	{
//		db = data;
//		lesson = l;
//					
//		questionType = type;
//		vQuestion = new Vector();
//		
//		questionListGen();
//		
//	}
//	
//	private void questionListGen() {
//		// TODO Auto-generated method stub
//		
//		CALL_Question question;
//		
//	////////////////////////////////////////
//	/////////////////////////////////
//		
////		    int size;
////			double weight;
////			boolean match;
////			boolean useThisFormGroup;
////			Random rand;
////			String restrictionString;
////			String questionString;
////			
////			JCALL_formDescGroupStruct formGroup;
////			CALL_stringPairsStruct formPairs;
//			
//			
//			 CALL_lessonQuestionStruct lessonQuestion;
//			 CALL_lessonConceptStruct lessonConcept;
//			 
////			 1) Analysis question 
//			 Vector lessonQuestions = lesson.questions;
//			 
//			 if (lessonQuestions!=null) {
//				
//				 for (int i = 0; i < lessonQuestions.size(); i++) {
//					
//					 lessonQuestion = (CALL_lessonQuestionStruct) lessonQuestions.elementAt(i);
//					 if(lessonQuestion == null)
//						{
//							// Error
//							logger.error( "No question of ["+i+"]th + in Lesson--"+lesson.getIndex());
//							return;
//						}
//					 					 
////					 1) 
//					 /*
//					  * iiiiiiiiiiiiiiiiiso ko kara
//					  */
//					 logger.debug("Step 1: Question ["+i+"]");	
//					 
//					 Vector lessonConcepts = lessonQuestion.concepts;  //CALL_lessonConceptStruct
////					 CALL_lessonConceptStruct lessonConcept; 
//					 
//					 if (lessonConcepts!=null) {
//						 for (int j = 0; j < lessonConcepts.size(); j++) {
//							
//							 lessonConcept = (CALL_lessonConceptStruct) lessonConcepts.elementAt(j);
//							 if(lessonConcept == null)
//								{
//									// Error
//									logger.error("Lesson concept is NULL;");
//									break;
//								}
//							 question = new CALL_Question();
//							 question.questionGen(db,lessonQuestion,lessonConcept);
//							 
//						}
//						 
//						 
//						 
//					 
//					 
//					 
//					 
//					 
//				}
//				 
//			}
//
//			
//			 }
//
//		//////////////////////////////
//		
//		
//		
//		
//		
//	}
//
//	
//	
//	
// }
