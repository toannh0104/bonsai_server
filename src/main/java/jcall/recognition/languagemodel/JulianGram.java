package jcall.recognition.languagemodel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.CALL_conceptFrameStruct;
import jcall.CALL_conceptRulesStruct;
import jcall.CALL_conceptSlotFillerStruct;
import jcall.CALL_conceptSlotStruct;
import jcall.CALL_database;
import jcall.CALL_lessonConceptStruct;
import jcall.CALL_lessonQuestionStruct;
import jcall.CALL_lessonStruct;
import jcall.CALL_lessonsStruct;
import jcall.CALL_lexiconStruct;
import jcall.CALL_wordStruct;
import jcall.recognition.util.CharacterUtil;
import jcall.recognition.util.OrigCallUtil;

import org.apache.log4j.Logger;

public class JulianGram {
  static Logger logger = Logger.getLogger(JulianGram.class.getName());
  public static CALL_database db;

  public static CALL_lexiconStruct lexicon;

  public static CALL_lessonsStruct lessons;

  // public CALL_verbRulesStruct vrules;
  // public CALL_counterRulesStruct crules;

  public static CALL_conceptRulesStruct concepts;

  // public CALL_studentsStruct students;
  // StringTokenizer st = new StringTokenizer(readLine);

  static final String DATAPATH = "./Data/JGrammar";

  public static final String STR_TAB = new String("\t");

  public JulianGram() {
    db = new CALL_database();
    db.loadData();
    if (db == null) {
      System.out.println("failed loading databases");
    } else {
      // System.out.println("succeed add db");
      lessons = db.lessons;
      lexicon = db.lexicon;
      // grules = db.grules;
      concepts = db.concepts;
    }
  }

  public Vector getConceptAndGrammar(int lessonIndex) {
    Vector<Vector<String>> vResult = new Vector<Vector<String>>();
    // System.out.println("in getGrammer");
    CALL_lessonStruct lesson = lessons.getLesson(lessonIndex);
    Vector<String> vConcept = new Vector<String>();
    Vector<String> vGrammar = new Vector<String>();
    if (lesson != null) {
      // System.out.println("in if");
      Vector vQuestion = lesson.questions;

      for (int i = 0; i < vQuestion.size(); i++) {
        CALL_lessonQuestionStruct lessonQuestion = (CALL_lessonQuestionStruct) vQuestion.get(i);
        Vector vLessonConcept = (Vector) lessonQuestion.concepts;
        // System.out.println("vLessonConcept is "
        // +vLessonConcept.size());
        for (int j = 0; j < vLessonConcept.size(); j++) {
          CALL_lessonConceptStruct lessonConcept = (CALL_lessonConceptStruct) vLessonConcept.get(j);
          if (lessonConcept != null) {
            vConcept.add((String) lessonConcept.concept);
            vGrammar.add((String) lessonConcept.grammar);
          } else {
            System.out.println("error in the parsing of the CALL_lessonConceptStruct");
          }
        }
      }
    } else {
      System.out.println("lesson is null");
    }
    vResult.add(vConcept);
    vResult.add(vGrammar);
    // lexicon.getWordsInGroup(group)

    return vResult;
  }

  /*
   * one question one grammar,but maybe diffrent concepts;
   */
  public Vector getQuestionConceptAndGrammar(CALL_lessonQuestionStruct lessonQuestion) {
    Vector<Vector<String>> vResult = new Vector<Vector<String>>();
    Vector<String> vConcept = new Vector<String>();
    Vector<String> vGrammar = new Vector<String>();
    if (lessonQuestion != null) {
      // System.out.println("in if");
      Vector vLessonConcept = (Vector) lessonQuestion.concepts;
      for (int j = 0; j < vLessonConcept.size(); j++) {
        CALL_lessonConceptStruct lessonConcept = (CALL_lessonConceptStruct) vLessonConcept.get(j);
        if (lessonConcept != null) {
          vConcept.add((String) lessonConcept.concept);
          vGrammar.add((String) lessonConcept.grammar);
        } else {
          System.out.println("error in the parsing of the CALL_lessonConceptStruct");
        }
      }

    } else {
      System.out.println("lessonQuestionn is null");
    }
    vResult.add(vConcept);
    vResult.add(vGrammar);
    // lexicon.getWordsInGroup(group)
    return vResult;
  }

  public Vector getQuestionConcept(CALL_lessonQuestionStruct lessonQuestion) {
    Vector v1 = (Vector) getQuestionConceptAndGrammar(lessonQuestion);
    Vector v = (Vector) v1.get(0);
    return v;
  }

  public String getQuestionGrammar(CALL_lessonQuestionStruct lessonQuestion) {
    Vector v1 = (Vector) getQuestionConceptAndGrammar(lessonQuestion);
    Vector v = (Vector) v1.get(1);
    String strResult = (String) v.elementAt(0);
    return strResult;
  }

  public Vector getConceptList(int lessonIndex) {
    Vector v1 = (Vector) getConceptAndGrammar(lessonIndex);
    Vector v = (Vector) v1.get(0);
    return v;
  }

  public Vector getConcepts(int lessonIndex) {
    Vector v1 = (Vector) getConceptAndGrammar(lessonIndex);
    Vector v = (Vector) v1.get(0);
    Vector vResult = new Vector();
    HashSet hs = new HashSet();
    for (int i = 0; i < v.size(); i++) {
      String strTemp = (String) v.elementAt(i);
      if (!hs.contains(strTemp)) {
        hs.add(strTemp);
        vResult.addElement(strTemp);
      }

    }

    return vResult;
  }

  public Vector getGramRuleList(int lessonIndex) {
    Vector v1 = (Vector) getConceptAndGrammar(lessonIndex);
    Vector v = (Vector) v1.get(1);
    return v;
  }

  public void getGram(Vector grammarVec) {
    // Vector v = new Vector();

  }

  public int getConceptNumber(int lessonIndex, String strWordKanji, String strWordkana) {
    Vector vConcept = getConceptList(lessonIndex);
    CALL_conceptFrameStruct concept;
    CALL_wordStruct wstruct = lexicon.getWord(strWordKanji);
    if (wstruct == null) {
      wstruct = lexicon.getWord(strWordkana);
      if (wstruct == null) {
        System.out.println("error not found the word----" + strWordKanji);
        return 0;
      }
    }
    CALL_conceptSlotStruct slot;
    String slotName = "";
    Vector fillerWords = new Vector();
    CALL_conceptSlotFillerStruct filler;
    CALL_conceptFrameStruct childFrame;
    CALL_wordStruct word = null;
    Vector wordGroup;
    for (int i = 0; i < vConcept.size(); i++) {

      String strTemp = (String) vConcept.elementAt(i);
      System.out.println("in concept---" + strTemp + "-----the" + i + "th----- concept");
      // deal with each concept
      concept = concepts.getConcept(strTemp);
      // parse this concept
      if (concept != null) {
        Vector vslot = (Vector) concept.getSlots();
        System.out.println("slot size ---" + vslot.size());
        for (int j = 0; j < vslot.size(); j++) {
          Vector vSlotWordList = new Vector();
          System.out.println("in slot--" + j);
          slot = (CALL_conceptSlotStruct) vslot.elementAt(j);
          if (slot != null) {
            // ----------each slot
            slotName = slot.getName().toUpperCase();
            System.out.println("We have a slot..slotName is----" + slotName);
            for (int l = 0; l < slot.getFillers().size(); l++) {
              filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);
              if (filler != null) {
                System.out.println("We have a filler, type " + filler.getType() + "...");
                int intFillerType = filler.getType();
                if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
                  // Sub concept, go recursive
                  System.out.println("filter type ==TYPE_SUB_CONCEPT");
                  childFrame = concepts.getConcept(filler.getData());
                  if (childFrame != null) {
                    OrigCallUtil.getAllFillerWords(db, concepts, childFrame, fillerWords);
                    if (fillerWords != null || fillerWords.size() > 0) {
                      for (int m = 0; m < fillerWords.size(); m++) {
                        word = (CALL_wordStruct) fillerWords.elementAt(i);
                        if (word.getKana().equalsIgnoreCase(wstruct.getKana())) {
                          return fillerWords.size();
                        }
                      }

                    }

                  }// end if
                } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
                  System.out.print("filter type ==TYPE_LEXICON_WORD");
                  // Add specified word to instance
                  word = db.lexicon.getWord(filler.getData());

                  if (word != null) {
                    // Add the word
                    System.out.println("just add a word" + word.english);
                    if (word.getKana().equalsIgnoreCase(wstruct.getKana())) {
                      return 1;
                    }
                  }

                } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                  // Get all matching words in lexicon, and add to our vector
                  System.out.println("filter type ==TYPE_LEXICON_GROUP");
                  wordGroup = db.lexicon.getWordsInGroup(filler.getData());
                  if (wordGroup != null) {
                    System.out.println("GROUP size---" + wordGroup.size() + "----GroupName---" + filler.getData());
                    for (int k = 0; k < wordGroup.size(); k++) {
                      word = (CALL_wordStruct) wordGroup.elementAt(k);
                      if (word != null) {
                        if (word.getKana().equalsIgnoreCase(wstruct.getKana())) {
                          return wordGroup.size();
                        }
                      }
                    }
                  }
                }
              }// if filler
            }
          }// if slot
        }
      }// if concept
    }

    return 0;
  }

  public Vector getConceptVector(int lessonIndex, String strWordKanji, String strWordkana) {
    logger.debug("enter getConceptVector, kanji: " + strWordKanji + "kana: " + strWordkana);
    Vector<CALL_wordStruct> vResult = new Vector();
    Vector vConcept = getConcepts(lessonIndex);
    CALL_conceptFrameStruct concept;
    CALL_wordStruct wstruct = lexicon.getWord(strWordKanji);
    if (wstruct == null) {
      wstruct = lexicon.getWord(strWordkana);
      if (wstruct == null) {
        logger.error("In old lexicon, not found the word----" + strWordKanji);
        return null;
      }
    }
    CALL_conceptSlotStruct slot;
    String slotName = "";
    Vector fillerWords = new Vector();
    CALL_conceptSlotFillerStruct filler;
    CALL_conceptFrameStruct childFrame;
    CALL_wordStruct word = null;
    Vector wordGroup;
    logger.debug("Concept size in Lesson[" + lessonIndex + "] is: " + vConcept.size());
    for (int i = 0; i < vConcept.size(); i++) {
      String strTemp = (String) vConcept.elementAt(i);
      logger.info("Concept---" + strTemp + "---the " + i + "th concept");
      // deal with each concept
      concept = concepts.getConcept(strTemp);
      // parse this concept
      if (concept != null) {
        Vector vslot = (Vector) concept.getSlots();
        logger.debug("slot size ---" + vslot.size());
        for (int j = 0; j < vslot.size(); j++) {
          Vector vSlotWordList = new Vector();
          logger.debug("in slot--" + j);
          slot = (CALL_conceptSlotStruct) vslot.elementAt(j);
          if (slot != null) {
            // ----------each slot
            slotName = slot.getName().toUpperCase();
            logger.info("We have a slot..slotName is----" + slotName);
            for (int l = 0; l < slot.getFillers().size(); l++) {
              filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);
              if (filler != null) {
                logger.debug("We have a filler, data: " + filler.getData() + " Type: " + filler.getType());
                logger.debug("filler type: TYPE_LEXICON_WORD=0; TYPE_LEXICON_GROUP=1; TYPE_SUB_CONCEPT=2");
                int intFillerType = filler.getType();
                if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
                  // Sub concept, go recursive
                  logger.info("filter type ==TYPE_SUB_CONCEPT");
                  childFrame = concepts.getConcept(filler.getData());
                  if (childFrame != null) {
                    logger.debug("childFrame: " + childFrame.getName());

                    // OrigCallUtil.getAllFillerWords(db,concepts,childFrame,fillerWords);
                    // vResult =
                    // OrigCallUtil.getConceptWords(db,concepts,childFrame,fillerWords,wstruct);
                    OrigCallUtil.getConceptWords(db, concepts, childFrame, fillerWords, wstruct);
                    if (fillerWords != null && fillerWords.size() > 0) {
                      vResult = new Vector();
                      logger.info("find the right concept and it has [" + vResult.size() + "]");
                      for (int n = 0; n < fillerWords.size(); n++) {
                        vResult.addElement((CALL_wordStruct) fillerWords.elementAt(n));
                      }
                      return vResult;
                    }

                    // if(fillerWords!=null||fillerWords.size()>0){
                    //
                    // for (int m = 0; m < fillerWords.size(); m++) {
                    // word = (CALL_wordStruct) fillerWords.elementAt(i);
                    // logger.debug(i +"---" +word.getKanji() );
                    // if(word.getKana().equalsIgnoreCase(wstruct.getKana())){
                    // vResult = new Vector();
                    // logger.info("find the right concept and it has ["+
                    // vResult.size()+"]");
                    // for(int n = 0; n < fillerWords.size(); n++){
                    // vResult.addElement((CALL_wordStruct)
                    // fillerWords.elementAt(n));
                    // }
                    // return vResult;
                    // }
                    // }
                    // }else{
                    // logger.info("fillerWords is null or zero");
                    // }
                    //
                  }// end if
                  else {
                    logger.error("childFrame is null");
                  }
                } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
                  logger.info("filter type ==TYPE_LEXICON_WORD");
                  // Add specified word to instance
                  word = db.lexicon.getWord(filler.getData());

                  if (word != null) {
                    // Add the word
                    logger.debug("LEXICON_WORD: " + word.getKanji());
                    if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                        || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                      // Vector<CALL_wordStruct> v = new Vector();
                      logger.debug("just add a word" + word.getKanji());
                      vResult = new Vector();

                      vResult.addElement(word);
                      return vResult;
                    }
                  }

                } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                  // Get all matching words in lexicon, and add to our vector
                  logger.info("filter type ==TYPE_LEXICON_GROUP");
                  wordGroup = db.lexicon.getWordsInGroup(filler.getData());
                  if (wordGroup != null) {
                    logger.info("LEXICON_GROUP size: " + wordGroup.size() + "----GroupName: " + filler.getData());
                    for (int k = 0; k < wordGroup.size(); k++) {

                      word = (CALL_wordStruct) wordGroup.elementAt(k);
                      logger.debug("Word[" + k + "], Kanji: " + word.getKanji());
                      if (word != null) {
                        if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                            || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                          vResult = new Vector();
                          logger.info("find the right concept, GroupName: " + filler.getData());
                          for (int n = 0; n < wordGroup.size(); n++) {
                            CALL_wordStruct wordTemp = (CALL_wordStruct) wordGroup.elementAt(n);
                            logger.debug("In " + filler.getData() + ", word[" + n + "] : " + wordTemp.getKanji());
                            vResult.addElement((CALL_wordStruct) wordGroup.elementAt(n));
                          }
                          // logger.info("find the right concept and it has ["+
                          // vResult.size()+"]");
                          return vResult;
                        }

                      } else {
                        logger.error("word is null");
                      }
                    }// for group
                  }
                } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_OTHER) {
                  logger.info("filter type ==TYPE_OTHER");
                  // wordGroup = db.lexicon.getWordsInGroup(filler.getData());
                  // get all words in the upper level
                  // slot = (CALL_conceptSlotStruct) vslot.elementAt(j);
                  Vector vecSlot = getSlotWords(slot, wstruct);
                  if (vecSlot != null) {
                    vResult = new Vector();
                    HashSet<String> hset = new HashSet();
                    logger.info("find the right concept");
                    for (int n = 0; n < vecSlot.size(); n++) {
                      CALL_wordStruct wordTemp = (CALL_wordStruct) vecSlot.elementAt(n);
                      if (!hset.contains(wordTemp.getKana())) {
                        logger.debug("word[" + n + "] : " + wordTemp.getKanji());
                        hset.add(wordTemp.getKana());
                        vResult.addElement((CALL_wordStruct) vecSlot.elementAt(n));
                      }
                    }
                    // logger.info("find the right concept and it has ["+
                    // vResult.size()+"]");
                    return vResult;
                  }
                }

              }// if filler
            }
          }// if slot
        }
      }// if concept
    }

    return vResult;
  }

  public Vector getSlotWords(CALL_conceptSlotStruct slot, CALL_wordStruct wstruct) {
    Vector vLocation = new Vector();
    Vector vWhere = new Vector();
    CALL_conceptSlotFillerStruct filler;
    CALL_wordStruct word = null;
    boolean IsWhere = false;
    boolean IsLocation = false;
    logger.debug("enter getSlotWords, slot: " + slot.getName());
    for (int l = 0; l < slot.getFillers().size(); l++) {
      filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);

      if (filler != null) {
        logger.debug("We have a filler: " + filler.getData());
        int intFillerType = filler.getType();
        if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_OTHER) {
          logger.debug("filter type ==TYPE_OTHER");
          String strData = filler.getData();
          String strLocation = null;
          String strWhere = null;
          StringTokenizer st = new StringTokenizer(strData, "_");
          if (st.hasMoreElements()) {
            strLocation = (String) st.nextElement();
            logger.debug("strLocation: " + strLocation);
            word = lexicon.getWord(strLocation);
            if (word != null) {
              vLocation.add(word);
              logger.debug("WORD: " + word.getKanji());
              if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                  || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                logger.debug("TYPE_OTHER, add a word: " + word.getKanji());
                IsLocation = true;
              }
            } else {
              if (strLocation.equalsIgnoreCase("on")) {
                word = lexicon.getWord("����");

              } else if (strLocation.equalsIgnoreCase("under")) {
                word = lexicon.getWord("����");

              } else if (strLocation.equalsIgnoreCase("next")) {
                word = lexicon.getWord("�ƂȂ�");

              } else if (strLocation.equalsIgnoreCase("in")) {
                word = lexicon.getWord("�Ȃ�");

              } else if (strLocation.equalsIgnoreCase("infront")) {
                word = lexicon.getWord("�܂�");
              }
              if (word != null) {
                vLocation.add(word);
                logger.debug("WORD: " + word.getKanji());
                if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                    || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                  logger.debug("TYPE_OTHER, add a word: " + word.getKanji());
                  IsLocation = true;
                }
              }
            }

          }
          if (st.hasMoreElements()) {
            strWhere = (String) st.nextElement();
            logger.debug("strWhere: " + strWhere);
            word = lexicon.getWord(strWhere);
            if (word != null) {
              vWhere.add(word);
              logger.debug("WORD: " + word.getKanji());
              if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                  || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                logger.debug("TYPE_OTHER, add a word: " + word.getKanji());
                IsWhere = true;
                // fillerWords.addElement(word);
              }
            }

          }

          // fillerWords.addElement(word);

        }
      }
    }
    logger.debug("end getFillerWords");
    if (IsLocation) {
      return vLocation;
    } else if (IsWhere) {
      return vWhere;
    } else {
      logger.error("no filler matches in this slot");
      return null;
    }
  }

  public int getQuestionNumber(int lessonIndex, String strWordKanji, String strWordkana) {
    CALL_lessonStruct lesson = lessons.getLesson(lessonIndex);
    String strQ = "";
    CALL_conceptSlotStruct slot = new CALL_conceptSlotStruct();
    String slotName = "";
    CALL_wordStruct wstruct = lexicon.getWord(strWordKanji);
    if (wstruct == null) {
      wstruct = lexicon.getWord(strWordkana);
      if (wstruct == null) {
        System.out.println("error not found the word----" + strWordKanji);
        return 0;
      } else {
        System.out.println("find target word-----" + strWordkana);
      }
    } else {
      System.out.println("find target word-----" + strWordKanji);
    }
    if (lesson != null) {
      // System.out.println("in if");
      Vector vQuestion = lesson.questions;
      System.out.println("have questions-----" + vQuestion.size());
      for (int i = 0; i < vQuestion.size(); i++) {
        CALL_lessonQuestionStruct lessonQuestion = (CALL_lessonQuestionStruct) vQuestion.get(i);
        Vector vConcept = getQuestionConcept(lessonQuestion);
        strQ = lessonQuestion.getQuestion();
        System.out.println("lesson [" + lessonIndex + "]" + "question [" + i + "]" + "[" + strQ + "]");
        // getVocaFile(lessonIndex,strQ,vConcept);
        CALL_conceptFrameStruct concept = getOneLessonConcept(vConcept);
        if (concept != null) {
          Vector vslot = (Vector) concept.getSlots();
          System.out.println("slot size ---" + vslot.size());
          for (int j = 0; j < vslot.size(); j++) {
            Vector vSlotWordList = new Vector();
            System.out.println("in slot--" + j);
            slot = (CALL_conceptSlotStruct) vslot.elementAt(j);
            if (slot != null) {
              slotName = slot.getName().toUpperCase();

              System.out.println("We have a slot..slotName is----" + slotName);

              OrigCallUtil.getFillerWords(db, concepts, slot, vSlotWordList);
              // Vector newSlotWordList =
              // OrigCallUtil.getUniquePicWordList(vSlotWordList);
              // Vector newSlotWordList = vSlotWordList;
              Vector newSlotWordList = OrigCallUtil.getUniqueWordList(vSlotWordList);
              System.out.println("the slot originally have [" + vSlotWordList.size()
              // + "] words; ---now have not duplicatal ["
              // + newSlotWordList1.size()
                  + "] words; and after delete the none pic words, now we have [" + newSlotWordList.size() + "]");

              if (newSlotWordList != null || newSlotWordList.size() > 0) {
                System.out.println("newSlotWordList ---" + newSlotWordList.size());
                for (int m = 0; m < newSlotWordList.size(); m++) {
                  CALL_wordStruct word = (CALL_wordStruct) newSlotWordList.elementAt(m);
                  String strKanji = word.getKanji().trim();
                  String strkana = word.getKana().trim();
                  System.out.println("strKanji ---" + strKanji + "----strkana---" + strkana);
                  if (strkana.equalsIgnoreCase(wstruct.getKana()) || strKanji.equalsIgnoreCase(wstruct.getKanji())) {
                    System.out.println("found one target word!");
                    return newSlotWordList.size();
                  }
                }
              }

            }
          }
        }
      }
    }
    return 0;
  }

  /*
   * get one conceptFrameStruct of one lesson
   */
  public CALL_conceptFrameStruct getOneLessonConcept(Vector conceptVec) {
    CALL_conceptFrameStruct conceptResult = new CALL_conceptFrameStruct();
    System.out.println("in getOneLessonConcept");

    for (int i = 0; i < conceptVec.size(); i++) {

      String strTemp = (String) conceptVec.elementAt(i);
      System.out.println("in concept---" + strTemp + "-----the" + i + "th----- concept");
      if (i == 0) {
        conceptResult = concepts.getConcept(strTemp);
      } else {
        CALL_conceptFrameStruct concept = concepts.getConcept(strTemp);
        if (concept != null && conceptResult != null) {

          conceptResult = OrigCallUtil.mergeConcept(conceptResult, concept);
        }
      }
    }
    return conceptResult;
  }

  /*
   * if update is true, create new file,else add content to the existing file
   */
  public void writeWords2File(String slotname, Vector wordList, int lessonIndex, boolean update) throws IOException {
    lessonIndex++;
    String filename = new String("L" + lessonIndex);
    String filepath = DATAPATH + "\\" + filename;
    String voca = filename + ".voca";

    File vocaFile = new File(filepath, voca);

    // check file path and make dir,create new file
    if (update) {
      File vocaPath = new File(filepath);
      if (!vocaPath.exists()) {
        vocaPath.mkdir();
      }
      if (vocaFile.exists()) {
        vocaFile.delete();
      }
      vocaFile.createNewFile();
      System.out.println("update all data,creat new file---" + vocaFile.getAbsolutePath());
    } else {
      if (!vocaFile.exists()) {
        System.out.println("wrong,file not exist");
        return;
      }
    }

    // save to file
    // FileWriter vocaFW = new FileWriter(vocaFile);
    BufferedWriter vocaBW = new BufferedWriter(new FileWriter(vocaFile));
    // RandomAccessFile vocaRAF = new RandomAccessFile(vocaFile,"rw");

    vocaBW.write("% " + slotname);
    vocaBW.newLine();
    System.out.println("writing file...... add--" + wordList.size() + "-of words");
    for (int i = 0; i < wordList.size(); i++) {
      CALL_wordStruct word = (CALL_wordStruct) wordList.elementAt(i);
      String strKanji = word.getKanji().trim() + STR_TAB;
      String strkana = word.getKana().trim();
      String strJulianSentence = strKanji + CharacterUtil.strKanaToPronunce(strkana, word.getType());
      vocaBW.write(strJulianSentence);
      vocaBW.newLine();
    }

  }

  public static void main(String[] args) throws IOException {

    // DebugLogger.printlog("testing....");
    JulianGram jg = new JulianGram();

    // Vector v1 = jg.getConceptAndGrammar(0);
    // Vector v = (Vector) v1.get(0);
    // jg.getVocaFile(v, 0);
    // jg.getVocaFile(27);

    // for lesson 22
    // int a = jg.getConceptNumber(1, "ab");
    // System.out.println(a);
    //

    /*
     * for(int i = 0;i<v.size();i++){ System.out.println(v.get(i)); }
     */

  }

}