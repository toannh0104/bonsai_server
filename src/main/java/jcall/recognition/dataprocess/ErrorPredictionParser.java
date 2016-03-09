package jcall.recognition.dataprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.recognition.database.DataManager;
import jcall.util.FileManager;

import org.apache.log4j.Logger;
import org.jdom2.Element;

public class ErrorPredictionParser {
  static Logger logger = Logger.getLogger(ErrorPredictionParser.class.getName());

  static String predictFile = "./Data/LessonErrorPrediction.txt";
  static final String lexfile = "./Data/newlexicon.txt";
  static final String strLexiconTableName = "Lexicon";
  static final String strLexiconFields = "(Type,Level,Kanji,kana,MainMeaning,Picture,Altkana,OtherMeaning,Category1,Category2)";
  static final String strQuantifierRuleName = "./Data/QuantiferRules.xml";

  Vector<PredictionDataMeta> vecNoun;
  Vector<PredictionDataMeta> vecVerb;
  Vector<PredictionDataMeta> vecParticle;
  Vector<PredictionDataMeta> vecNumeral;
  Vector<PredictionDataMeta> vecQantifier;
  Vector<PredictionDataMeta> vecPronoun;

  // static ErrorPredictionStruct eps;
  static Vector<PredictionDataMeta> vecAltKana;
  static Vector<PredictionDataMeta> vecAltKanaResult;
  static Vector<PredictionDataMeta> vecLessonnew;
  static Vector<PredictionDataMeta> vecGeneralnew;

  final static int START = 0;
  final static int LESSON = 1;
  final static int GRAMMAR = 2;
  final static int CONCEPT = 3;
  final static int LEXICON = 4;
  final static int OUTLESSONLEXICON = 5;
  final static int LEXICONGROUP = 6;
  final static int LEXICONWORD = 7;
  final static int END = 8;
  int readstatus;
  BufferedReader br;
  Vector vecResult1;

  // ErrorPredictionProcess epprocess;
  public ErrorPredictionParser() throws IOException {
    // vecAllWord = new Vector();
    init();
  }

  private void init() throws IOException {
    // eps = new ErrorPredictionStruct();
    vecAltKana = new Vector<PredictionDataMeta>();
    vecAltKanaResult = new Vector<PredictionDataMeta>();
    vecResult1 = new Vector();
    vecLessonnew = new Vector<PredictionDataMeta>();
    vecGeneralnew = new Vector<PredictionDataMeta>();
    // epprocess = new ErrorPredictionProcess();
  }

  /*
   * return all the prediction words except for counter.
   */
  public ErrorPredictionStruct loadDataFromFile(String strFileName) throws IOException {
    // Vector vecResult = new Vector();
    // init();
    ErrorPredictionStruct eps = new ErrorPredictionStruct();
    LexiconWordMeta newWord;
    PredictionDataMeta pdm = null;

    // load the words from the lexicon which has altKana as the first part of
    // the prediction
    LexiconParser lp = new LexiconParser();
    Lexicon lexicon = lp.loadDataFromFile(lexfile);
    Vector vecTemp = lexicon.vecAltKanaActiveWord;
    for (int i = 0; i < vecTemp.size(); i++) {
      newWord = (LexiconWordMeta) vecTemp.get(i);
      pdm = wordMetaToPdm(newWord);
      addAltKanaWord(pdm);
    }

    // load and parse the predict file;be careful with the repeated words;
    br = new BufferedReader(new FileReader(new File(strFileName)));
    readstatus = START;
    StringTokenizer st;
    String readLine, tempString = null;
    int intCount = 0;
    // Read Line by line
    while (readstatus != END) {
      readLine = br.readLine();
      intCount++;
      if (readLine != null && readLine.length() > 0) {
        // System.out.println(readLine);
        st = new StringTokenizer(readLine);
        tempString = st.nextToken();
        if (tempString.startsWith("##")) {
          // A comment,skip it
          continue;
        } else if (tempString.startsWith("-eof")) {
          // Add any previous word
          if (pdm != null && pdm.getStrWord().length() > 0) {
            addWord(pdm, eps);
            pdm.clear();
          }
          addWord(pdm, eps);
          readstatus = END;
        } else {
          switch (readstatus) {
            case START:
              if (tempString.startsWith("-Lesson")) {
                pdm = new PredictionDataMeta();
                pdm.setLesson(Integer.parseInt(st.nextToken()));
              } else if (tempString.startsWith("#Grammar")) {
                pdm.setStrClass("Grammar");
                readstatus = GRAMMAR;
              } else if (tempString.startsWith("#Concept")) {
                pdm.setStrClass("Concept");
                readstatus = CONCEPT;
              } else if (tempString.startsWith("#Lexicon")) {
                pdm.setStrClass("Lexicon");
                readstatus = LEXICON;
              }
              break;
            case GRAMMAR:
              if (tempString.startsWith("-word")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                tempString = st.nextToken();
                // tempInt = Lexicon.getType(tempString);
                // pdm.setType(tempInt);
                tempString = st.nextToken();
                if (tempString != null) {
                  LexiconWordMeta wmTemp = (LexiconWordMeta) ErrorPredictionProcess.searchLexiconWord(tempString,
                      lexicon);
                  if (wmTemp != null) {
                    wordMetaToPdm(wmTemp, pdm);
                  } else {
                    logger.info("no such a word in lexicon [" + tempString + "]");
                  }
                } else {
                  logger.error("wrong form of the word type error in getPredictData");
                }

              } else if (tempString.startsWith("-sub")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  int i = 1;
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      i++;
                    }
                    // pdm.addToVecVerbType();
                  }
                  pdm.addCount(i - 1);
                }
                //
                // pdm.addToVecSubWord(tempString);
                // pdm.addCount();
              } else if (tempString.startsWith("-restrict")) {
                tempString = st.nextToken();
                pdm.setStrRestriction(tempString);
              } else if (tempString.startsWith("-alt")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  int i = 1;
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      i++;
                    }
                    // pdm.addToVecVerbType();
                  }
                  pdm.addCount(i - 1);
                }
                // pdm.addToVecAltWord(tempString);
                // pdm.addCount();
              } else if (tempString.startsWith("#Concept")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                pdm.setStrClass("Concept");
                readstatus = CONCEPT;
              }
              break;
            case CONCEPT:
              if (tempString.startsWith("-verb")) {
                pdm.setStrWord("verb");
                tempString = st.nextToken();
                String str[] = tempString.split(",");
                int i = 1;
                for (int j = 0; j < str.length; j++) {
                  pdm.addToVecVerbType(str[j]);
                  i = i * 2;
                }
                pdm.addCount(i - 1);

              } else if (tempString.startsWith("-restrict")) {
                tempString = st.nextToken();
                pdm.setStrRestriction(tempString);
              } else if (tempString.startsWith("-REF")) {
                tempString = st.nextToken();
                pdm.setStrREF(tempString);
              } else if (tempString.startsWith("-type")) {
                tempString = st.nextToken();
                String str[] = tempString.split(",");
                for (int i = 0; i < str.length; i++) {
                  pdm.addToVecVerbInvalidFrom(str[i]);
                }
                int k = str.length;
                pdm.addCount(k);
              } else if (tempString.startsWith("#Lexicon")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                pdm.setStrClass("Lexicon");
                readstatus = LEXICON;
              } else if (tempString.startsWith("-Lesson")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                pdm = new PredictionDataMeta();
                pdm.setLesson(Integer.parseInt(st.nextToken()));
                readstatus = START;
              } else if (tempString.startsWith("#GeneralLexicon")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                pdm = new PredictionDataMeta();
                pdm.setStrClass("lexicon");
                readstatus = LEXICON;
              }
              break;
            // LEXICONGROUP,LEXICONWORD
            case LEXICON:
              if (tempString.startsWith("-ErrorType")) {
                // pdm = new PredictionDataMeta();
                tempString = st.nextToken();
                if (tempString.equalsIgnoreCase("group")) {
                  readstatus = LEXICONGROUP;
                } else if (tempString.equalsIgnoreCase("word")) {
                  readstatus = LEXICONWORD;
                }
              } else if (tempString.startsWith("-Lesson")) {
                pdm = new PredictionDataMeta();
                pdm.setLesson(Integer.parseInt(st.nextToken()));
                readstatus = START;
              }
              break;
            case LEXICONGROUP:
              if (tempString.startsWith("-category")) {
                tempString = st.nextToken();
                tempString = st.nextToken();
                vecResult1 = (Vector) ErrorPredictionProcess.searchWords(tempString, lexicon);
                System.out.println("-category--" + tempString + vecResult1.size());
              } else if (tempString.startsWith("-rule")) {
                tempString = st.nextToken();
                String tempString1 = st.nextToken();
                String tempString2 = st.nextToken();
                if (tempString1.equalsIgnoreCase("sub")) {
                  for (int i = 0; i < vecResult1.size(); i++) {
                    LexiconWordMeta wmTemp = (LexiconWordMeta) vecResult1.get(i);
                    pdm = wordMetaToPdm(wmTemp);
                    int intCount1 = tempString1.trim().length();
                    int intCount2 = tempString2.trim().length();
                    if (intCount1 > intCount2) { // omit some suffix;
                      String suffix = tempString.substring(1);
                      String strSub = wmTemp.getStrKana().trim();
                      String strSubWord = strSub.substring(0, strSub.length() - suffix.length());
                      pdm.addToVecSubWord(strSubWord);
                      pdm.addCount(); // for the rule ,add one
                    } else {
                      pdm.addCount(); // for the rule ,add one
                    }
                    addWord(pdm, eps);
                  }
                } else if (tempString1.equalsIgnoreCase("alt")) {
                  // System.out.println("alt"+tempString);
                  for (int i = 0; i < vecResult1.size(); i++) {
                    LexiconWordMeta wmTemp = (LexiconWordMeta) vecResult1.get(i);
                    pdm = wordMetaToPdm(wmTemp);
                    pdm.addCount(); // for the rule ,add one
                    addWord(pdm, eps);
                  }

                } else if (tempString1.equalsIgnoreCase("honorific")) {
                  // System.out.println("honorific"+tempString);
                  int index = tempString.indexOf("*");
                  int index2 = tempString2.indexOf("*");
                  String[] strRule = tempString2.split(",");
                  String strPart[] = new String[strRule.length];
                  if (tempString.equals("*")) {
                    if (index2 == 0) {
                      for (int j = 0; j < vecResult1.size(); j++) {
                        LexiconWordMeta wmTemp = (LexiconWordMeta) vecResult1.get(j);
                        pdm = wordMetaToPdm(wmTemp);
                        // pdm.setStrAltWord(newWord.getStrAltkana());
                        for (int i = 0; i < strRule.length; i++) {
                          strPart[i] = strRule[i].substring(index2 + 1);
                          String temp = wmTemp.getStrKana().concat(strPart[i]);
                          pdm.addToHonorificWord(temp);
                          pdm.addCount();
                        }
                        addWord(pdm, eps);
                      }
                    }

                  } else {
                    if (tempString2.equals("*") && index == 0) {
                      String part = tempString.substring(index + 1);
                      for (int m = 0; m < vecResult1.size(); m++) {
                        LexiconWordMeta wmTemp = (LexiconWordMeta) vecResult1.get(m);
                        pdm = wordMetaToPdm(wmTemp);
                        // pdm.setStrAltWord(newWord.getStrAltkana());
                        String temp = wmTemp.getStrKana().concat(part);
                        pdm.addToHonorificWord(temp);
                        pdm.addCount();
                        addWord(pdm, eps);
                      }
                    }
                  }
                }
              } else if (tempString.startsWith("-ErrorType")) {
                pdm = new PredictionDataMeta();
                tempString = st.nextToken();
                if (tempString.equalsIgnoreCase("word")) {
                  readstatus = LEXICONWORD;
                }
              }
              break;
            case LEXICONWORD:
              if (tempString.startsWith("-word")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                  pdm.clear();
                }
                tempString = st.nextToken();
                tempString = st.nextToken();
                if (tempString != null) {
                  LexiconWordMeta wmTemp = (LexiconWordMeta) ErrorPredictionProcess.searchLexiconWord(tempString,
                      lexicon);
                  if (wmTemp != null) {
                    pdm = wordMetaToPdm(wmTemp);
                  } else {
                    pdm.setStrWord(tempString);
                    pdm.setBooAltkana(false);
                    logger.info("no such a word in lexicon [" + tempString + "]");
                  }
                } else {
                  logger.info("wrong form of the word type error in getPredictData");
                }

              } else if (tempString.startsWith("-sub")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      pdm.addCount();
                    }
                    // pdm.addToVecVerbType();
                  }
                }
              } else if (tempString.startsWith("-restrict")) {
                tempString = st.nextToken();
                pdm.setStrRestriction(tempString);
              } else if (tempString.startsWith("-alt")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      pdm.addCount();
                    }
                  }
                }
                // String[] strRule = tempString.split(",");
                // for (int i = 0; i < strRule.length; i++) {
                // pdm.addToVecAltWord(strRule[i]);
                // pdm.addCount();
                // }
              } else if (tempString.startsWith("-honorific")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      pdm.addCount();
                    }
                  }
                }
                // String[] strRule = tempString.split(",");
                // for (int i = 0; i < strRule.length; i++) {
                // pdm.addToHonorificWord(strRule[i]);
                // //System.out.println("-honorific "+ strRule[i]);
                // pdm.addCount();
                // }
              } else if (tempString.startsWith("-wronghonorific")) {
                tempString = st.nextToken();
                if (tempString != null && tempString.length() > 0) {
                  String str[] = tempString.split(",");
                  for (int j = 0; j < str.length; j++) {
                    if (str[j] != null && str[j].length() > 0) {
                      pdm.addToVecSubWord(str[j]);
                      pdm.addCount();
                    }
                  }
                }
                // String[] strRule = tempString.split(",");
                // for (int i = 0; i < strRule.length; i++) {
                // pdm.addToWrongHonorificWord(strRule[i]);
                // //System.out.println("-wronghonorific "+ strRule[i]);
                // pdm.addCount();
                // }
                // System.out.println(pdm.toString()); its right here
              } else if (tempString.startsWith("-Lesson")) {
                if (pdm.getStrWord().length() > 0) {
                  addWord(pdm, eps);
                }
                pdm = new PredictionDataMeta();
                pdm.setLesson(Integer.parseInt(st.nextToken()));
                readstatus = START;
              }
              break;
          }// end of switch

        } // end of else*/

      }// end of if

    }// end of while
     // add all left words which only has altkanawors
    for (int i = 0; i < vecAltKanaResult.size(); i++) {
      // PredictionDataMeta pd = vecAltKanaResult.get(i);
      eps.addToVecGeneral(vecAltKanaResult.get(i));

    }
    return eps;

  }

  private void addWordOld(PredictionDataMeta newWord) {
    int type = newWord.getId();
    switch (type) {
      case 1:
        vecNoun.addElement(newWord);
        break;
      case 15:
        vecNumeral.addElement(newWord);
        break;
      case 19:
        vecQantifier.addElement(newWord);
        break;
      case 2:
        vecVerb.addElement(newWord);
        break;
      case 6:
        vecParticle.addElement(newWord);
        break;
      case 13:
        vecPronoun.addElement(newWord);
        break;
    }

  }

  private void addAltKanaWord(PredictionDataMeta newWord) {
    vecAltKana.addElement(newWord);
    vecAltKanaResult.addElement(newWord);

  }

  private void addWord(PredictionDataMeta newWord, ErrorPredictionStruct eps) {
    // check if it is an AltKanaWord
    PredictionDataMeta pd;
    if (newWord.isBooAltkana()) {
      for (int i = 0; i < vecAltKanaResult.size(); i++) {
        pd = vecAltKanaResult.get(i);
        if (pd.getId() == newWord.getId()) {
          vecAltKanaResult.removeElementAt(i);
        }
      }
    }
    pd = newWord.doClone();
    if (newWord.getLesson() == -1) {
      eps.addToVecGeneral(pd);
      // vecGeneralnew.addElement(pd);
    } else {
      eps.vecLesson.addElement(pd);
      // vecLessonnew.addElement(pd);
    }
    /*
     * test code Vector vNewWord = newWord.getVecWrongHonorificWord(); Vector
     * vCloneWord = pd.getVecWrongHonorificWord(); if(vNewWord!=null &&
     * vNewWord.size()>0){
     * System.out.println("vNewWord "+vNewWord.size()+" vCloneWord"
     * +vCloneWord.size()); }
     */
    // System.out.println("wordid--"+newWord.getId()+"--getStrWord--"+newWord.getStrWord()+"--REF["+newWord.getStrREF()+"]--confusioncount--"+newWord.getConfusionCount());
  }

  public static void getConfusionGroup() throws SQLException, IOException {
    FileManager fm = new FileManager();
    // File oneFile = FileManager.createFile(predictFile);
    FileWriter fw = new FileWriter(predictFile);
    BufferedWriter bw = new BufferedWriter(fw);

    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    // String strTableName = "WordErrTable";
    // boolean bResult = false;
    // String sql =
    // "SELECT *  FROM WordErrTable WHERE (TargetType IN('DEFINITIVE','MISC','NOUN')And (SpecificType IN('SUBS1','SUBS2')) )";
    String sql = "SELECT *  FROM WordErrTable WHERE (TargetType IN('DEFINITIVE','MISC','NOUN')And (SpecificType ='WRONGFORM') )";

    ResultSet rs = dm.executeQuery(sql);
    while (rs.next()) {
      String targetWord = rs.getString("TargetWord");
      String observedWord = rs.getString("ObservedWord");
      System.out.println("the TargetWord" + "---" + targetWord + "--ObservedWord ---- " + observedWord);
      bw.write("-word noun " + targetWord);
      bw.newLine();
      bw.write("-sub " + observedWord);
      bw.newLine();
      bw.newLine();
    }
    bw.close();
    fw.close();
    dm.releaseConn();
  }

  private PredictionDataMeta wordMetaToPdm(LexiconWordMeta wm) {
    PredictionDataMeta pdm = new PredictionDataMeta();
    pdm.setId(wm.getId());
    pdm.setStrWord(wm.getStrKana());
    pdm.setType(wm.getIntType());
    pdm.setStrKanji(wm.getStrKanji());
    pdm.setStrWord(wm.getStrKana());
    String strAltKana = wm.getStrAltkana();
    if (strAltKana != null && strAltKana.length() > 0) {
      pdm.addToVecAltWord(strAltKana);
      pdm.addCount();
      pdm.setBooAltkana(true);
    }
    return pdm;
  }

  private void wordMetaToPdm(LexiconWordMeta wm, PredictionDataMeta pdm) {
    // PredictionDataMeta pdm = new PredictionDataMeta();
    pdm.setId(wm.getId());
    pdm.setStrWord(wm.getStrKana());
    pdm.setType(wm.getIntType());
    pdm.setStrKanji(wm.getStrKanji());
    pdm.setStrWord(wm.getStrKana());
    String strAltKana = wm.getStrAltkana();
    if (strAltKana != null && strAltKana.length() > 0) {
      pdm.addToVecAltWord(strAltKana);
      pdm.addCount();
      pdm.setBooAltkana(true);
    }
    // return pdm;
  }

  public Element EPStoString(ErrorPredictionStruct eps) {
    Element eRoot = new Element("rule");
    eRoot.setAttribute("name", "Error Prediction Rule for Each Lesson");
    for (int i = 0; i < eps.vecLesson.size(); i++) {
      PredictionDataMeta pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      System.out.println(pdm.getStrWord() + "\\" + pdm.getStrREF());
    }
    for (int i = 0; i < eps.vecGeneral.size(); i++) {
      PredictionDataMeta pdm = (PredictionDataMeta) eps.vecLesson.get(i);
      System.out.println(pdm.getStrWord() + "\\" + pdm.getStrREF());

    }
    return eRoot;

    // this.vecLesson.addElement(pdm);
  }

  /**
   * @param args
   * @throws IOException
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException, IOException {
    // TODO Auto-generated method stub
    // PredictError.getConfusionGroup();
    ErrorPredictionParser epp = new ErrorPredictionParser();
    ErrorPredictionStruct eps = epp.loadDataFromFile(predictFile);
    int w = 0;
    int h = 0;
    Vector vecTemp = eps.vecLesson;
    for (int i = 0; i < vecTemp.size(); i++) {
      PredictionDataMeta pdm = (PredictionDataMeta) (eps.vecLesson).get(i);
      if (pdm.getStrWord() != null) {
        w++;
      }
      // System.out.println(pdm.getStrWord()
      // +"[]"+pdm.getStrREF()+"[]"+pdm.getType());
      // System.out.println(pdm.toString());
    }
    for (int k = 0; k < eps.vecGeneral.size(); k++) {
      PredictionDataMeta pdm = (PredictionDataMeta) (eps.vecGeneral).get(k);
      // System.out.println(pdm.getStrWord()
      // +"[]"+pdm.getStrREF()+"[]"+pdm.getType());
      if (pdm.getStrWord() != null) {
        w++;
      }
      System.out.println(pdm.toString());
    }
    // System.out.println(eps.vecLesson.size()+eps.vecGeneral.size());
    // System.out.println(vecLessonnew.size()+vecGeneralnew.size());

    System.out.println("the end w=" + w + "--h=" + h);
  }

}
