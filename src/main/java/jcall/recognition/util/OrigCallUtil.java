package jcall.recognition.util;

import java.util.Vector;

import jcall.CALL_conceptFrameStruct;
import jcall.CALL_conceptRulesStruct;
import jcall.CALL_conceptSlotFillerStruct;
import jcall.CALL_conceptSlotStruct;
import jcall.CALL_database;
import jcall.CALL_wordStruct;

import org.apache.log4j.Logger;

public class OrigCallUtil {
  static Logger logger = Logger.getLogger(OrigCallUtil.class.getName());

  public OrigCallUtil() {

  }

  public static CALL_conceptFrameStruct mergeConcept(CALL_conceptFrameStruct concept1, CALL_conceptFrameStruct concept2) {
    System.out.println("in mergeConcept");
    CALL_conceptFrameStruct conceptResult = concept1;
    Vector vResultSolt = new Vector();
    Vector vslot1 = (Vector) concept1.getSlots();
    Vector vslot2 = (Vector) concept2.getSlots();
    for (int j = 0; j < vslot2.size(); j++) {
      CALL_conceptSlotStruct tempslot = (CALL_conceptSlotStruct) vslot2.get(j);
      vResultSolt = (Vector) addSlot(vslot1, tempslot);
    }
    conceptResult.setSlots(vResultSolt);
    System.out.println("end mergeConcept");
    return conceptResult;
  }

  public static Vector addSlot(Vector vSlotList, CALL_conceptSlotStruct slot) {
    System.out.println("in addSlot");
    boolean boo = false;
    Vector vResult = vSlotList;
    String strSlotName = slot.getName().trim();
    for (int i = 0; i < vSlotList.size(); i++) {
      CALL_conceptSlotStruct tempSlot = (CALL_conceptSlotStruct) vSlotList.elementAt(i);
      String tempSlotName = tempSlot.getName().trim();
      if (strSlotName.equals(tempSlotName)) {
        CALL_conceptSlotStruct slotResult = mergeSlot(tempSlot, slot);
        vResult.remove(i);
        vResult.add(slotResult);
        boo = true;
        break;
      }
    }
    if (!boo) {
      vResult.add(slot);
    }
    System.out.println("end addSlot");
    return vResult;
  }

  public static CALL_conceptSlotStruct mergeSlot(CALL_conceptSlotStruct slot1, CALL_conceptSlotStruct slot2) {
    System.out.println("in mergeSlot");
    CALL_conceptSlotStruct slotResult = slot1;
    Vector vfiller1 = (Vector) slot1.getFillers();
    Vector vfiller2 = (Vector) slot2.getFillers();
    for (int j = 0; j < vfiller2.size(); j++) {
      CALL_conceptSlotFillerStruct tempFiller = (CALL_conceptSlotFillerStruct) vfiller2.get(j);
      AddFiller(vfiller1, tempFiller);
    }
    slotResult.setFillers(vfiller1);
    return slotResult;
  }

  public static void AddFiller(Vector<CALL_conceptSlotFillerStruct> vFillList, CALL_conceptSlotFillerStruct filler) {
    boolean booResult = false;
    String strFillerName = filler.getData().trim();
    for (int i = 0; i < vFillList.size(); i++) {
      CALL_conceptSlotFillerStruct tempFiller = (CALL_conceptSlotFillerStruct) vFillList.get(i);
      String strFillerName2 = tempFiller.getData().trim();
      if (strFillerName2.equalsIgnoreCase(strFillerName)) {
        booResult = true;
        break;
      }
    }
    if (!booResult) {
      vFillList.add(filler);
    }
  }

  /*
   * check whether a word in a wordList. every word is a CALL_wordStruct return
   * true if the wordlist contains that word
   */
  public static boolean checkWordExist(Vector vWordList, CALL_wordStruct word) {
    boolean booResult = false;
    String strkana2 = word.getKana().trim();
    for (int i = 0; i < vWordList.size(); i++) {
      CALL_wordStruct tempWord = (CALL_wordStruct) vWordList.elementAt(i);
      String strkana1 = tempWord.getKana().trim();
      if (strkana2.equals(strkana1)) {
        booResult = true;
        break;
      }
    }
    return booResult;
  }

  /*
   * get unique worldlist
   */

  public static Vector getUniqueWordList(Vector vWordList) {
    Vector<CALL_wordStruct> vResult = new Vector<CALL_wordStruct>();
    for (int i = 0; i < vWordList.size(); i++) {
      CALL_wordStruct tempWord = (CALL_wordStruct) vWordList.elementAt(i);
      if (i == 0) {
        vResult.add(tempWord);
      } else {
        if (!checkWordExist(vResult, tempWord)) {
          vResult.add(tempWord);
        }
      }
    }
    return vResult;
  }

  /*
   * get unique worldlist in which every word has a picture.
   */

  public static Vector getUniquePicWordList(Vector vWordList) {
    Vector<CALL_wordStruct> vResult = new Vector<CALL_wordStruct>();
    for (int i = 0; i < vWordList.size(); i++) {
      CALL_wordStruct tempWord = (CALL_wordStruct) vWordList.elementAt(i);
      // String tempPic = tempWord.getPicture();
      if (tempWord.isActive()) {
        if (i == 0) {
          vResult.add(tempWord);
        } else {
          if (!checkWordExist(vResult, tempWord)) {
            vResult.add(tempWord);
          }
        }
      }
    }
    return vResult;
  }

  // //////////////////////////////////////////////////////////////////
  //
  // using original CALL_conceptRulesStruct class
  //
  // ///////////////////////////////////////////////////////////////////

  /*
   * get one filler words
   */

  public static void getFillerWords(CALL_database db, CALL_conceptRulesStruct conceptRule, CALL_conceptSlotStruct slot,
      Vector fillerWords) {
    Vector wordGroup = new Vector();
    CALL_conceptSlotFillerStruct filler;
    CALL_conceptFrameStruct childFrame;
    CALL_wordStruct word = null;
    System.out.println("in a GetFillerWords");

    for (int l = 0; l < slot.getFillers().size(); l++) {
      filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);

      if (filler != null) {
        System.out.println("We have a filler, type " + filler.getType() + "...");
        int intFillerType = filler.getType();
        if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
          // Sub concept, go recursive
          System.out.println("filter type ==TYPE_SUB_CONCEPT");
          childFrame = conceptRule.getConcept(filler.getData());
          if (childFrame != null) {
            getAllFillerWords(db, conceptRule, childFrame, fillerWords);
          }
        } else if (intFillerType == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
          System.out.print("filter type ==TYPE_LEXICON_WORD");
          // Add specified word to instance
          word = db.lexicon.getWord(filler.getData());

          if (word != null) {
            // Add the word
            System.out.println("just add a word" + word.english);
            fillerWords.add(word);
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
                // Add the word
                fillerWords.addElement(word);
                System.out.println("in TYPE_LEXICON_GROUP, Considering " + word.getKana() + " as valid vocabulary");
              }
            }
          }
        }
        /*
         * else if(intFillerType == CALL_conceptSlotFillerStruct.TYPE_OTHER){
         * System.out.println("filter type ==TYPE_OTHER");
         * //System.out.println("just add a word"+ word.english);
         * 
         * fillerWords.add(word); filler.data;//is the content in (); }
         */
      }
    }
    System.out.println("end getFillerWords");
  }

  /*
   * get filler words of one slot
   */

  public static void getAllFillerWords(CALL_database db, CALL_conceptRulesStruct conceptRule,
      CALL_conceptFrameStruct frame, Vector fillerWords) {
    int i, k, l;
    CALL_conceptSlotStruct slot;
    CALL_conceptSlotFillerStruct filler;
    CALL_conceptFrameStruct childFrame;
    Vector wordList;

    CALL_wordStruct word = null;

    if (frame != null) {
      Vector vecSlot = frame.getSlots();

      logger.debug("start in getAllFillerWords, frame slot size: " + vecSlot.size());
      // int slotnum = frame.slots.size();

      for (i = 0; i < vecSlot.size(); i++) {

        slot = (CALL_conceptSlotStruct) vecSlot.elementAt(i);
        logger.debug("slot[" + i + "]");
        if (slot != null) {
          logger.debug("slot name: " + slot.getName());
          // Firstly, deal with those outside of restrictions
          for (l = 0; l < slot.getFillers().size(); l++) {
            filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);

            if (filler != null) {
              logger.debug("We have a filler, type " + filler.getType() + "...");

              if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
                // Sub concept, go recursive
                logger.debug("filter type ==TYPE_SUB_CONCEPT");
                childFrame = conceptRule.getConcept(filler.getData());
                if (childFrame != null) {
                  getAllFillerWords(db, conceptRule, childFrame, fillerWords);
                }
              } else if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
                logger.debug("filter type ==TYPE_LEXICON_WORD");
                // Add specified word to instance
                word = db.lexicon.getWord(filler.getData());

                if (word != null) {
                  // Add the word
                  logger.debug("LEXICON_WORD, word: " + word.getKanji());
                  fillerWords.addElement(word);

                }
              } else if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                // Get all matching words in lexicon, and add to our vector
                logger.debug("filter type ==TYPE_LEXICON_GROUP");
                wordList = db.lexicon.getWordsInGroup(filler.getData());
                if (wordList != null) {
                  for (k = 0; k < wordList.size(); k++) {
                    word = (CALL_wordStruct) wordList.elementAt(k);
                    if (word != null) {
                      // Add the word
                      fillerWords.addElement(word);
                      logger.debug("LEXICON_GROUP, word: " + word.getKanji());
                    }
                  }
                }
              }// end of if type
            }// end of if filler
          }

        } // end of if slot
      } // FOR (slots)

    } else {
      logger.error("Frame is null");
    }
  }

  public static void getConceptWords(CALL_database db, CALL_conceptRulesStruct conceptRule,
      CALL_conceptFrameStruct frame, Vector fillerWords, CALL_wordStruct wstruct) {
    logger.debug("enter getConceptWords");
    int i, k, l;
    CALL_conceptSlotStruct slot;
    CALL_conceptSlotFillerStruct filler;
    CALL_conceptFrameStruct childFrame;
    Vector wordList;

    CALL_wordStruct word = null;

    if (frame != null) {
      Vector vecSlot = frame.getSlots();

      logger.debug("start in getConceptWords, frame slot size: " + vecSlot.size());
      // int slotnum = frame.slots.size();

      for (i = 0; i < vecSlot.size(); i++) {

        slot = (CALL_conceptSlotStruct) vecSlot.elementAt(i);
        logger.debug("slot[" + i + "]");
        if (slot != null) {
          logger.debug("slot name: " + slot.getName());
          // Firstly, deal with those outside of restrictions
          for (l = 0; l < slot.getFillers().size(); l++) {
            filler = (CALL_conceptSlotFillerStruct) slot.getFillers().elementAt(l);

            if (filler != null) {
              logger.debug("We have a filler, type " + filler.getType() + "...");

              if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
                // Sub concept, go recursive
                logger.debug("filter type ==TYPE_SUB_CONCEPT");
                childFrame = conceptRule.getConcept(filler.getData());
                if (childFrame != null) {
                  getConceptWords(db, conceptRule, childFrame, fillerWords, wstruct);
                }
              } else if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
                logger.debug("filter type ==TYPE_LEXICON_WORD");
                // Add specified word to instance
                word = db.lexicon.getWord(filler.getData());

                if (word != null) {
                  logger.debug("LEXICON_WORD: " + word.getKanji());
                  if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                      || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                    // Vector<CALL_wordStruct> v = new Vector();
                    // fillerWords = new Vector();
                    // Add the word
                    logger.debug("LEXICON_WORD, add a word: " + word.getKanji());
                    fillerWords.addElement(word);
                    return;
                    // return fillerWords;
                  }

                  // fillerWords.addElement(word);

                }
              } else if (filler.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                // Get all matching words in lexicon, and add to our vector
                logger.debug("filter type ==TYPE_LEXICON_GROUP");
                wordList = db.lexicon.getWordsInGroup(filler.getData());
                if (wordList != null) {
                  for (k = 0; k < wordList.size(); k++) {
                    word = (CALL_wordStruct) wordList.elementAt(k);
                    if (word != null) {
                      logger.debug("LEXICON_GROUP, word: " + word.getKanji());
                      // Add the word

                      if (word.getKana().equalsIgnoreCase(wstruct.getKana())
                          || word.getKanji().equalsIgnoreCase(wstruct.getKanji())) {
                        // Vector<CALL_wordStruct> v = new Vector();
                        // fillerWords = new Vector();
                        // Add the word
                        logger.info("find the right concept, GroupName: " + filler.getData());

                        for (int n = 0; n < wordList.size(); n++) {
                          CALL_wordStruct wordTemp = (CALL_wordStruct) wordList.elementAt(n);
                          logger.debug("Word[" + n + "], Kanji: " + wordTemp.getKanji());
                          fillerWords.addElement(wordTemp);
                        }
                        return;
                        // logger.info("find the right concept and it has ["+
                        // vResult.size()+"]");
                        // return fillerWords;
                      }

                    }
                  }
                }
              }// end of if type
            }// end of if filler
          }

        } // end of if slot
      } // FOR (slots)

    } else {
      logger.error("Frame is null");
    }
    // return fillerWords;
  }

}
