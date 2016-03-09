/**
 * Created on 2007/05/24
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.CALL_database;
import jcall.CALL_io;
import jcall.CALL_lexiconStruct;
import jcall.CALL_wordStruct;
import jcall.recognition.database.DataManager;
import jcall.util.FileManager;

/**
 * @author kyoto-u
 *
 */
public class LexiconParser {
  public static CALL_database db;
  public static CALL_lexiconStruct lexicon;

  static final String lexiconFileName = "./Data/lexicon.txt";
  static final String newLexiconFileName = "./Data/newlexicon.txt";

  static final String strLexiconTableName = "Lexicon";
  static final String strLexiconFields = "(Type,Level,Kanji,kana,MainMeaning,Picture,Altkana,OtherMeaning,Category1,Category2)";

  final static int[] LEX_TYPE_TRANSFORM = { 1, 2, 18, 3, 5, 13, 15, 1, 6, 1, 16, 17 };
  final static int[] LEX_TYPE_INTTRANSFORM = { 0, 1, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 21, 22, 3, 4, 5, 6, 7, 8,
      9, 111 };

  // String definition of types
  static String[] typeString = { "UNK", "NOUN", "NOUN_PERSONNAME", "NOUN_AREANAME", "NOUN_PRONOUN", "NOUN_SURU",
      "NOUN_NUMERAL", "NOUN_SUFFIX", "NOUN_PREFIX", "NOUN_TIME", "NOUN_QUANTIFIER", "VERB", "VERB_VT", "VERB_VI",
      "ADJECTIVE", "ADJVERB", "ADVERB", "PARTICLE_AUXIL", "PARTICLE_AUXILV", "RENTAI", "KANDOU", "NUMQUANT" };

  PrintWriter pw;
  BufferedReader br;
  static String strQuantifierRuleName = "./Data/QuantiferRules.xml";

  public LexiconParser() {
    // init();
  }

  public void importDataToFile() throws IOException {
    LexiconWordMeta tmpWord;

    FileManager.createFile(newLexiconFileName);
    pw = new PrintWriter(new FileWriter(newLexiconFileName, true));

    db = new CALL_database();
    if (db == null) {
      System.out.println("failed loading databases");
    } else {
      lexicon = db.lexicon;
      Vector vecTemp = (Vector) lexicon.getAllWords();
      for (int i = 0; i < vecTemp.size(); i++) {
        CALL_wordStruct newWord = (CALL_wordStruct) vecTemp.get(i);
        tmpWord = new LexiconWordMeta();
        tmpWord.setIntLevel(newWord.getLevel());
        Vector tmpVector = newWord.getCategories();
        if (tmpVector.size() > 0) {
          tmpWord.setCategories(tmpVector);
          tmpWord.setStrCategory1((String) tmpVector.get(0));
          if (tmpVector.size() > 1) {
            tmpWord.setStrCategory2((String) tmpVector.get(1));
          }
        }
        tmpWord.setIntType(LEX_TYPE_TRANSFORM[newWord.getType()]);
        tmpWord.setPicture(newWord.getPicture());
        tmpWord.setStrMainMeaning(newWord.getGenEnglish());
        tmpWord.setStrKana(newWord.getKana());
        tmpWord.setStrKanji(newWord.getKanji());
        tmpWord.write(pw);
      }
      pw.close();
    }

  }

  public void importDataToTable() throws IOException {
    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    LexiconWordMeta tmpWord = null;
    String strsubFields = "(Type,Kanji,kana,MainMeaning,Picture,Altkana,OtherMeaning,Category1,Category2)";
    String strValues = tmpWord.toSubVALUES();
    System.out.println("strValues is [" + strValues + "]");
    dm.insertRecord(strLexiconTableName, strsubFields, strValues);
    dm.releaseConn();

  }

  /**
   * @param filename
   *          from newLexicon
   * @return
   * @throws IOException
   */
  public Lexicon loadDataFromFile(String filename) throws IOException {
    Lexicon lResult = new Lexicon();
    LexiconWordMeta newWord = new LexiconWordMeta();
    // load data form the lexicon file
    br = new BufferedReader(new FileReader(new File(filename)));
    // load and parse data
    int readstatus = 0;
    StringTokenizer st;

    int tempInt;
    int currentIndex = 0;
    String readLine, tempString;
    // Read Line by line
    while (readstatus == 0) {
      readLine = br.readLine();;
      if (readLine != null) {
        st = new StringTokenizer(readLine);
        tempString = st.nextToken();
        if (tempString != null) {
          if (tempString.startsWith("#")) {
            // A comment,skip it
            continue;
          } else if (tempString.startsWith("-eof")) {
            // Add any previous word
            lResult.addWord(newWord);
            currentIndex++;
            readstatus = 1;
          } else {

            if (tempString.startsWith("-id")) {
              // The type command denotes the start of a new word, giving it's
              // type (category)
              if (newWord != null && newWord.getStrKana().length() > 0) {
                lResult.addWord(newWord);
                currentIndex++;
              }
              tempInt = Integer.parseInt(st.nextToken());
              newWord = new LexiconWordMeta();
              newWord.setId(tempInt);

            } else if (tempString.equalsIgnoreCase("-Type")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntType(tempInt);
            } else if (tempString.equalsIgnoreCase("-componentID")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setComponentID(tempInt);
            } else if (tempString.equalsIgnoreCase("-Level")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntLevel(tempInt);

            } else if (tempString.equalsIgnoreCase("-Kanji")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKanji(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-kana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-picture")) {
              tempString = CALL_io.getNextToken(st);
              if (tempString != null) {
                tempString = tempString.trim();
                newWord.setPicture(tempString);
                if (tempString.length() > 0) {
                  if (!(tempString.equalsIgnoreCase("<none>"))) {
                    newWord.setActive(true);
                  }
                }

              }
            } else if (tempString.equalsIgnoreCase("-MainMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrMainMeaning(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Altkana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAltkana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Attribute")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAttribute(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Category1")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setStrCategory1(tempString);
                newWord.setActive(true);
                // addToCategory(tempString, newWord);

              }
            } else if (tempString.equalsIgnoreCase("-Category2")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setActive(true);
                newWord.setStrCategory2(tempString);

              }
            } else if (tempString.equalsIgnoreCase("-OtherMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null) {
                newWord.setStrOtherMeaning(tempString);
              }
            }
          }
        }
      }
    }
    System.out.println("currentIndex is ---" + currentIndex);
    br.close();
    // load data form the quantifier rule file
    /*
     * int currentID = currentIndex+1; QuantifierRuleParser qrp = new
     * QuantifierRuleParser(); QuantifierRule qr
     * =qrp.loadFromFile(strQuantifierRuleName); Vector vecCategory =
     * qr.getVecQC(); for (int i = 0; i < vecCategory.size(); i++) {
     * QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i); Vector
     * vecNode = qc.getVecNode(); for (int j = 0; j < vecNode.size(); j++) {
     * QuantifierNode qn = (QuantifierNode) vecNode.get(j); LexiconWordMeta lwm
     * = new LexiconWordMeta(); lwm.setActive(true); lwm.setId(currentID);
     * currentID++; lwm.setIntLevel(3); lwm.setIntType(111);
     * lwm.setPicture("<n/a>"); lwm.setStrKana(qn.getStrKana());
     * lwm.setStrKanji(qn.getStrKanji()); addWord(lwm); } }
     * 
     * System.out.println("after add all numquant ,the currentIndex is ---"+
     * currentIndex);
     */
    return lResult;

  }

  /**
   * @param filename
   *          file name of WordStatisticsResult
   * @return WordStatisticsStruct
   * @throws IOException
   */
  public WordStatisticsStruct loadDataToWSS(String filename) throws IOException {
    WordStatisticsStruct wssResult = new WordStatisticsStruct();
    WordStatisticsDataMeta newWord = null;
    // load data form the WordStatisticsResult file
    br = new BufferedReader(new FileReader(new File(filename)));
    // load and parse data
    int readstatus = 0;
    StringTokenizer st;

    int tempInt;
    int currentIndex = 0;
    String readLine, tempString;
    // Read Line by line
    while (readstatus == 0) {
      readLine = br.readLine();;
      if (readLine != null) {
        st = new StringTokenizer(readLine);
        tempString = st.nextToken();
        if (tempString != null) {
          if (tempString.startsWith("#")) {
            // A comment,skip it
            continue;
          } else if (tempString.startsWith("-eof")) {
            // Add any previous word
            wssResult.addToVecWord(newWord);
            currentIndex++;
            readstatus = 1;
          } else {

            if (tempString.startsWith("-id")) {
              // The type command denotes the start of a new word, giving it's
              // type (category)
              if (newWord != null && newWord.getStrKana().length() > 0) {
                wssResult.addToVecWord(newWord);
                currentIndex++;
              }
              tempInt = Integer.parseInt(st.nextToken());
              newWord = new WordStatisticsDataMeta();
              newWord.setId(tempInt);

            } else if (tempString.equalsIgnoreCase("-Type")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntType(tempInt);
            } else if (tempString.equalsIgnoreCase("-componentID")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setComponentID(tempInt);
            } else if (tempString.equalsIgnoreCase("-Level")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntLevel(tempInt);

            } else if (tempString.equalsIgnoreCase("-Kanji")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKanji(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-kana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-picture")) {
              tempString = CALL_io.getNextToken(st);
              if (tempString != null) {
                tempString = tempString.trim();
                newWord.setPicture(tempString);
                if (tempString.length() > 0) {
                  if (!(tempString.equalsIgnoreCase("<none>"))) {
                    newWord.setActive(true);
                  }
                }

              }
            } else if (tempString.equalsIgnoreCase("-MainMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrMainMeaning(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Altkana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAltkana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Attribute")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAttribute(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-OccureceTime")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntOccureceTime(tempInt);

            } else if (tempString.equalsIgnoreCase("-Category1")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setStrCategory1(tempString);
                newWord.setActive(true);
                // addToCategory(tempString, newWord);

              }
            } else if (tempString.equalsIgnoreCase("-Category2")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setActive(true);
                newWord.setStrCategory2(tempString);

              }
            } else if (tempString.equalsIgnoreCase("-OtherMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null) {
                newWord.setStrOtherMeaning(tempString);
              }
            }
          }
        }
      }
    }
    System.out.println("currentIndex is ---" + currentIndex);
    br.close();
    return wssResult;

  }

  public Lexicon loadData(String lexiconfilename) throws IOException {
    Lexicon l = loadDataFromFile(lexiconfilename);
    LexiconWordMeta lwm;
    // add NQ
    // String NQRULEFILE = "./Data/QuantiferRules.xml";

    return l;
  }

  public Lexicon loadData(String lexiconfilename, String NQfilename) throws IOException {
    Lexicon l = loadDataFromFile(lexiconfilename);
    LexiconWordMeta lwm;
    // add NQ
    // String NQRULEFILE = "./Data/QuantiferRules.xml";
    QuantifierRuleParser qrp = new QuantifierRuleParser();
    QuantifierRule qr = qrp.loadFromFile(NQfilename);
    QuantifierNode qn = null;
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      Vector vecNode = qc.getVecNode();
      for (int j = 0; j < vecNode.size(); j++) {
        qn = (QuantifierNode) vecNode.get(j);
        lwm = new LexiconWordMeta();
        lwm.toLexiconWord(qn);
        lwm.setId(1000 + i * 10 + j + 1);
        l.addWord(lwm);
      }
    }
    return l;
  }

  public Lexicon updateLexicon(String lexiconfilename, String NQfilename) throws IOException {
    Lexicon lResult = new Lexicon();
    loadDataUniquely(lexiconfilename, NQfilename, lResult);
    exportToFileOnly(lResult, lexiconfilename);
    return lResult;
  }

  public void loadDataUniquely(String lexiconfilename, String NQfilename, Lexicon lResult) throws IOException {

    LexiconWordMeta newWord = new LexiconWordMeta();
    // load data form the lexicon file
    br = new BufferedReader(new FileReader(new File(lexiconfilename)));
    // load and parse data
    int readstatus = 0;
    StringTokenizer st;

    int tempInt;
    int currentIndex = 0;
    String readLine, tempString;
    // Read Line by line
    while (readstatus == 0) {
      readLine = br.readLine();;
      if (readLine != null) {
        st = new StringTokenizer(readLine);
        tempString = st.nextToken();
        if (tempString != null) {
          if (tempString.startsWith("#")) {
            // A comment,skip it
            continue;
          } else if (tempString.startsWith("-eof")) {
            // Add any previous word
            lResult.addWordUnique(newWord);// .addWord(newWord);
            currentIndex++;
            readstatus = 1;
          } else {

            if (tempString.startsWith("-id")) {
              // The type command denotes the start of a new word, giving it's
              // type (category)
              if (newWord != null && newWord.getStrKana().length() > 0) {
                lResult.addWordUnique(newWord);
                currentIndex++;
              }
              tempInt = Integer.parseInt(st.nextToken());
              newWord = new LexiconWordMeta();
              newWord.setId(tempInt);

            } else if (tempString.equalsIgnoreCase("-Type")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntType(tempInt);
            } else if (tempString.equalsIgnoreCase("-componentID")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setComponentID(tempInt);
            } else if (tempString.equalsIgnoreCase("-Level")) {
              tempInt = Integer.parseInt(st.nextToken());
              newWord.setIntLevel(tempInt);

            } else if (tempString.equalsIgnoreCase("-Kanji")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKanji(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-kana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrKana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-picture")) {
              tempString = CALL_io.getNextToken(st);
              if (tempString != null) {
                tempString = tempString.trim();
                newWord.setPicture(tempString);
                if (tempString.length() > 0) {
                  if (!(tempString.equalsIgnoreCase("<none>"))) {
                    newWord.setActive(true);
                  }
                }

              }
            } else if (tempString.equalsIgnoreCase("-MainMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrMainMeaning(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Altkana")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAltkana(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Attribute")) {
              tempString = CALL_io.strRemainder(st);
              if ((tempString != null) && (!tempString.equals("<none>"))) {
                newWord.setStrAttribute(tempString);
              }
            } else if (tempString.equalsIgnoreCase("-Category1")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setStrCategory1(tempString);
                newWord.setActive(true);
                // addToCategory(tempString, newWord);

              }
            } else if (tempString.equalsIgnoreCase("-Category2")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null && tempString.length() > 0) {
                tempString = tempString.trim();
                newWord.setActive(true);
                newWord.setStrCategory2(tempString);

              }
            } else if (tempString.equalsIgnoreCase("-OtherMeaning")) {
              tempString = CALL_io.strRemainder(st);
              if (tempString != null) {
                newWord.setStrOtherMeaning(tempString);
              }
            }
          }
        }
      }
    }
    br.close();
    System.out.println("currentIndex is ---" + currentIndex + " actual size is---" + lResult.getVecAllWord().size());

    // load data form the quantifier rule file
    LexiconWordMeta lwm;
    // add NQ
    // String NQRULEFILE = "./Data/QuantiferRules.xml";
    QuantifierRuleParser qrp = new QuantifierRuleParser();
    QuantifierRule qr = qrp.loadFromFile(NQfilename);
    QuantifierNode qn = null;
    Vector vecCategory = qr.getVecQC();
    for (int i = 0; i < vecCategory.size(); i++) {
      QuantifierCategory qc = (QuantifierCategory) vecCategory.get(i);
      Vector vecNode = qc.getVecNode();
      for (int j = 0; j < vecNode.size(); j++) {
        qn = (QuantifierNode) vecNode.get(j);
        lwm = new LexiconWordMeta();
        lwm.toLexiconWord(qn);
        // lwm.setId(1000+i*10+j+1);
        lResult.addWordUnique(lwm);
      }
    }
    System.out.println("add NQ, all is ---" + lResult.getVecAllWord().size());

    // return lResult;
  }

  public void exportToFileOnly(Lexicon lex, String newlexiconFileName) throws IOException {
    FileManager.createFile(newlexiconFileName);
    pw = new PrintWriter(new FileWriter(newlexiconFileName, true));
    for (int i = 0; i < lex.vecAllWord.size(); i++) {
      LexiconWordMeta tmpWord = (LexiconWordMeta) lex.vecAllWord.get(i);
      // tmpWord.setId(i+1);
      tmpWord.write(pw);
    }
    pw.println("-eof");
    pw.close();
  }

  public void exportToFile(Lexicon lex) throws IOException {
    FileManager.createFile(newLexiconFileName);
    pw = new PrintWriter(new FileWriter(newLexiconFileName, true));
    for (int i = 0; i < lex.vecAllWord.size(); i++) {
      LexiconWordMeta tmpWord = (LexiconWordMeta) lex.vecAllWord.get(i);
      tmpWord.setId(i + 1);
      tmpWord.write(pw);
    }
    pw.println("-eof");
    pw.close();
  }

  public static String getType(int intType) {
    String strResult = null;
    for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
      if (intType == LEX_TYPE_INTTRANSFORM[i]) {
        strResult = typeString[i];
        break;
      }
    }
    return strResult;
  }

  public static int getType(String strType) {
    int intResult = -1;
    for (int i = 0; i < typeString.length; i++) {
      if (strType.equalsIgnoreCase(typeString[i])) {
        intResult = LEX_TYPE_INTTRANSFORM[i];
        break;
      }
    }
    return intResult;
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    LexiconParser lp = new LexiconParser();

    // Lexicon lex = lp.loadDataFromFile(newLexiconFileName);
    // System.out.println("enter LexiconParser!");
    /*
     * code segment to testify the validity of loadDataToWSS
     * 
     * WordStatisticsStruct wss =
     * lp.loadDataToWSS(WordStatisticsOfLog.WSRFILENAME); Vector v =
     * wss.getVecWord(); for (int i = 0; i < v.size(); i++) {
     * WordStatisticsDataMeta wsmTemp = (WordStatisticsDataMeta) v.get(i);
     * if(wsmTemp.getStrKana().equalsIgnoreCase("������")){
     * System.out.println(wsmTemp
     * .getStrKana()+" occurrence time is: "+wsmTemp.intOccureceTime); } }
     */
    /*
     * code segment for update lexicon;do not forget to change the verb part of
     * Lexicon.addWord() before executing
     */
    String NQRULEFILE = "./Data/QuantiferRules.xml";
    // Lexicon lex = lp.loadData(newLexiconFileName,NQRULEFILE);
    Lexicon lex = lp.updateLexicon(newLexiconFileName, NQRULEFILE);
    Vector vecall = lex.getVecPersonName();
    System.out.println("VecAllWord is " + lex.getVecAllWord().size());
    System.out.println(" getVecPersonName is " + lex.vecParticle.size());
    for (int i = 0; i < vecall.size(); i++) {
      LexiconWordMeta lwm = (LexiconWordMeta) vecall.get(i);
      System.out.println(lwm.getId() + "---" + lwm.getStrKana() + " " + lwm.getStrKanji());
    }
    // */

    // l.importDataToFile();
    // l.createLexiconTable();
    // l.importDataToTable();
    // l.importDataFromFile();
    // System.out.println(l.vecAltKanaActiveWord.size());
    // l.exportToFile();
    /*
     * db = new CALL_database(); if (db == null) {
     * System.out.println("failed loading databases"); } else { lexicon =
     * db.lexicon; Vector vecTemp = (Vector)lexicon.getAllWords();
     * System.out.println(vecTemp.size()); }
     */
    // l.searchWords("nationality");
    // l.searchWords("nationality");

  }

}
