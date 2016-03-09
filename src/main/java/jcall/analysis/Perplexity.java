/**
 * Created on 2008/07/24
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_NetWorkGeneration;
import jcall.db.JCALL_NetworkNodeStruct;
import jcall.db.JCALL_NetworkStruct;
import jcall.db.JCALL_NetworkSubNodeStruct;
import jcall.db.JCALL_Word;
import jcall.recognition.database.WordDataMeta;
import jcall.recognition.dataprocess.NewLogParser;
import jcall.recognition.dataprocess.OldCallLogParser;
import jcall.recognition.dataprocess.SentenceDataMeta;
import jcall.recognition.dataprocess.WordErrorAnalysis;
import jcall.recognition.languagemodel.MakeDFA;
import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class Perplexity {

  static Logger logger = Logger.getLogger(Perplexity.class.getName());

  static final String FERRPREDICT_M1 = "./data/FERRPREDICT_M1.txt";
  static final String FERRPREDICT_M2 = "./data/FERRPREDICT_M2.txt";

  // static final String

  public Perplexity() {

  }

  public void computePerplexity() {
    // load old sentence data
    ComputeScore cs = new ComputeScore();
    Vector errors_ex1 = cs.getSentencesFromDb_ex1(OldCallLogParser.SentencesTable);
    if (errors_ex1 != null && errors_ex1.size() > 0) {

      System.out.println("size: " + errors_ex1.size());

      for (int j = 0; j < errors_ex1.size(); j++) {
        SentenceDataMeta sdm = (SentenceDataMeta) errors_ex1.get(j);
        // System.out.println("Sentence: "+ sdm.getIntID());

      }

    }

  }

  public void writeJulianCompileFiles(int repeatNum) throws IOException {

    WordErrorAnalysis wea = new WordErrorAnalysis();
    Vector vSentences = wea.getSetenceFromTable(NewLogParser.SpeakSentencesTable3);// kana
                                                                                   // table
                                                                                   // stage2
    Vector vErrors = getErrorPrediction(repeatNum);
    if (vSentences == null) {
      return;
    }

    SenPerplexityStruct per = new SenPerplexityStruct();

    logger.info("vSentences: [" + vSentences.size() + "] ");

    for (int i = 0; i < vSentences.size(); i++) {
      if (vSentences.size() > 0 && i < vSentences.size()) {

        per.ISentenceNum = vSentences.size();

        SentenceDataMeta objMeta = (SentenceDataMeta) vSentences.elementAt(i);
        int QNum = objMeta.intID;

        logger.debug("NO: [" + QNum + "] ");

        sentenceGrammar(objMeta, vErrors, repeatNum, QNum, per);

        writeBatFile(repeatNum, QNum);
      }
    }

    // logger.info(per.toString());

    System.out.println(per.toString());

  }

  // execute the julian
  // start julian\bin\julian.exe -C julian/hmm_ptm.jconf -dfa
  // Data/JGrammar/NO1.dfa -v Data/JGrammar/NO1.dict -input rawfile -quiet
  // String fpath = "Data/JGrammar/"+repeatNum+"/"+QNum+"/";
  // String fdfa = fpath+JCALL_NetWorkGeneration.CONTEXTFILENAME +".dfa";
  // String fdict = fpath+JCALL_NetWorkGeneration.CONTEXTFILENAME +".dict";
  // String strBat =
  // "start julian\\bin\\julian.exe -C julian/hmm_ptm.jconf -dfa " + fdfa +
  // " -v " + fdict +" -input rawfile -quiet";
  // String batFName = ".\\" + "NO"+QNum+".bat";

  // String fpath = JCALL_NetWorkGeneration.JGRAMBASE +
  // "\\"+repeatNum+"\\"+QNum+"\\";
  // String fdfa = fpath+JCALL_NetWorkGeneration.CONTEXTFILENAME +".dfa";
  // String fdict = fpath+JCALL_NetWorkGeneration.CONTEXTFILENAME +".dict";
  // String strCommand =
  // ".\\Julian\\bin\\julian -C .\\Julian\\hmm_ptm.jconf -dfa " + fdfa + " -v "
  // + fdict +" -input rawfile -quiet";
  // it works, but do not know how to get the feedback sentence;
  // int intReturn = SysCmdExe.cmdExec(strCommand);
  // if(intReturn==0){
  // logger.info("succeed creating dfafile: "+fdfa);
  // }else{
  // logger.info("failed to creating dfafile: "+fdfa);
  // }

  public void writeBatFile(String batFName, String strBat) throws IOException {

    // check file path and make dir,create new file
    File batFile = new File(batFName);
    if (batFile.exists()) {
      batFile.delete();
    }
    batFile.createNewFile();

    logger.debug("update all data,creat new file---" + batFile.getAbsolutePath());
    // save to file

    BufferedWriter vocaBW = new BufferedWriter(new FileWriter(batFile));
    if (vocaBW != null) {

      vocaBW.write(strBat);
      vocaBW.newLine();
      vocaBW.flush();
      vocaBW.close();
    }

  }

  public void writeBatFile(int repeatNum, int QNum) throws IOException {

    String fpath = "Data/JGrammar/" + repeatNum + "/" + QNum + "/";
    String fdfa = fpath + JCALL_NetWorkGeneration.CONTEXTFILENAME + ".dfa";
    String fdict = fpath + JCALL_NetWorkGeneration.CONTEXTFILENAME + ".dict";
    String strBat = "start julian\\bin\\julian.exe -C julian/hmm_ptm.jconf -dfa " + fdfa + " -v " + fdict
        + " -input rawfile -quiet";

    String batFName = ".\\" + "NO" + QNum + ".bat";

    // check file path and make dir,create new file
    File batFile = new File(batFName);
    if (batFile.exists()) {
      batFile.delete();
    }
    batFile.createNewFile();

    logger.info("update all data,creat new file---" + batFile.getAbsolutePath());
    // save to file

    BufferedWriter vocaBW = new BufferedWriter(new FileWriter(batFile));
    if (vocaBW != null) {

      vocaBW.write(strBat);
      vocaBW.newLine();
      vocaBW.flush();
      vocaBW.close();
    }

  }

  public void sentenceGrammar(SentenceDataMeta objMeta, Vector vErrors, int repeatNum, int QNum, SenPerplexityStruct per) {

    Vector<String> wordList = null;
    String sentenceStr;
    String tempWord;
    JCALL_NetworkStruct network = new JCALL_NetworkStruct();
    JCALL_NetworkNodeStruct gw;
    JCALL_NetworkNodeStruct pregw = null;

    // print the top sentence
    sentenceStr = objMeta.getStrTargetSentence();
    logger.info("---> Target S: " + sentenceStr);
    // System.out.println("---> Target S: "+ sentenceStr);

    if (sentenceStr == null || sentenceStr.length() == 0) {
      per.ISentenceNum--;
      return;
    }

    StringTokenizer st = new StringTokenizer(sentenceStr);

    while (st.hasMoreElements()) {

      String sWord = (String) st.nextElement();

      if (wordList == null) {
        wordList = new Vector<String>();
      }
      wordList.addElement(sWord);

      // logger.debug("word  is: "+sWord);

    }

    if (wordList != null) {
      network = new JCALL_NetworkStruct();

      logger.debug("target sentence, word size is: " + wordList.size());

      if (QNum >= 0) {
        per.sentenceWordNum[QNum] = wordList.size();
      }
      per.ITotalWordNum += wordList.size();

      for (int i = 0; i < wordList.size(); i++) {
        tempWord = (String) wordList.elementAt(i);

        gw = new JCALL_NetworkNodeStruct();
        if (tempWord != null) {
          logger.debug("tempt word " + i + " is " + tempWord);

          JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmSurFormPRoma(tempWord);

          if (call_word != null) {
            logger.debug("find target word in the lexicon: " + tempWord);
            gw.setCall_word(call_word);
            // String strComponentLabel = "Word_"+ i;
            // gw.setStrLabel(strComponentLabel);
            //
            // getSubstitution(gw,vErrors,tempWord);

            String strComponentLabel = "Word_" + i;
            gw.setStrLabel(strComponentLabel);

            int totalSubs = getSubstitution(gw, vErrors, tempWord);

            per.sentenceTotolPerp[QNum] += totalSubs;

          } else {// end of if lwm
            logger.error(" not found the word in lexicon in Perplexity");
            // System.out.println(" not found the word in lexicon in Perplexity");
          }

        }

        pregw = gw;
        network.addGW(gw);

      }// end of for(wordlist)
    }

    // /*
    // file
    try {
      // write to file
      writeVocaFile(repeatNum, QNum, wordList.size(), network.getVGW());
      writeGrammarFile(repeatNum, QNum, wordList.size(), network.getVGW());

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // invoke the mkdfa
    MakeDFA dfa = new MakeDFA();
    String filepath = JCALL_NetWorkGeneration.JGRAMBASE + "\\" + repeatNum + "\\" + QNum + "\\";
    dfa.getDFAFile(filepath, JCALL_NetWorkGeneration.CONTEXTFILENAME);

    // */

  }

  public void writeVocaFile(int repeatNum, int QNum, int sentenceListSize, Vector network) throws IOException {

    // file
    Set<String> hs;

    String filepath = JCALL_NetWorkGeneration.JGRAMBASE + "\\" + repeatNum + "\\" + QNum + "\\";
    logger.info("voca filepath: " + filepath);

    // String filepath = PATH + "\\" ;
    String voca = JCALL_NetWorkGeneration.CONTEXTFILENAME + ".voca";

    // check file path and make dir,create new file
    File vocaPath = new File(filepath);
    if (!vocaPath.exists()) {
      vocaPath.mkdir();
      logger.info("path mkdir");
    }
    File vocaFile = new File(filepath, voca);
    if (vocaFile.exists()) {
      vocaFile.delete();
    }
    vocaFile.createNewFile();
    logger.info("update all data,creat new file---" + vocaFile.getAbsolutePath());
    // save to file

    BufferedWriter vocaBW = new BufferedWriter(new FileWriter(vocaFile));
    if (vocaBW != null) {
      if (network != null) {
        if (network.size() == sentenceListSize) {
          for (int i = 0; i < network.size(); i++) {
            hs = new HashSet<String>();
            JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.elementAt(i);
            vocaBW.write("% " + gwTemp.getStrLabel());
            vocaBW.newLine();
            logger.info("% " + gwTemp.getStrLabel());

            // first add answer
            // String str2 = correctAnswer.trim()+
            // JCALL_NetWorkGeneration.STR_TAB;
            // String str = correctAnswer.trim();
            // hs.add(str);
            int type = gwTemp.getCall_word().getIntType();
            // String strJulianSentenceAnswer = str2
            // + CharacterUtil.wordKanaToJulianVoca(str,type);
            // vocaBW.write(strJulianSentenceAnswer);
            // vocaBW.newLine();
            // logger.info(strJulianSentenceAnswer);

            // other substitutions
            Vector wordList = gwTemp.getVecSubsWord();
            if (wordList != null) {
              for (int j = 0; j < wordList.size(); j++) {
                JCALL_NetworkSubNodeStruct epl = (JCALL_NetworkSubNodeStruct) wordList.elementAt(j);
                // String strKanji = epl.getStrKanji().trim() + STR_TAB;
                String strkana2 = epl.getStrKana().trim() + JCALL_NetWorkGeneration.STR_TAB;
                String strkana = epl.getStrKana().trim();
                if (!hs.contains(strkana)) {
                  hs.add(strkana);
                  int intType = type;
                  String strJulianSentence = strkana2 + CharacterUtil.wordKanaToJulianVoca(strkana, intType);
                  vocaBW.write(strJulianSentence);
                  vocaBW.newLine();
                  logger.info(strJulianSentence);
                }
              }
            }

          }
          vocaBW.write("% NS_B");
          vocaBW.newLine();
          vocaBW.write("<s>	silB");
          vocaBW.newLine();
          vocaBW.write("% NS_E");
          vocaBW.newLine();
          vocaBW.write("</s>	silE");
          vocaBW.newLine();
          vocaBW.flush();
          vocaBW.close();
        }
      } else {
        logger.error("network is null");
      }
    }

  }

  public void writeGrammarFile(int repeatNum, int QNum, int sentenceListSize, Vector network) throws IOException {

    // file
    String filepath = JCALL_NetWorkGeneration.JGRAMBASE + "\\" + repeatNum + "\\" + QNum + "\\";

    // String filepath = PATH + "\\" ;
    String grammar = JCALL_NetWorkGeneration.CONTEXTFILENAME + ".grammar";
    File gramFile = new File(filepath, grammar);
    // check file path and make dir,create new file
    if (true) {
      File vocaPath = new File(filepath);
      if (!vocaPath.exists()) {
        vocaPath.mkdir();
      }
      if (gramFile.exists()) {
        gramFile.delete();
      }
      gramFile.createNewFile();
      System.out.println("update all data,creat new file---" + gramFile.getAbsolutePath());

    }
    // save to file

    BufferedWriter gramBW = new BufferedWriter(new FileWriter(gramFile));

    if (network != null && network.size() == sentenceListSize) {
      if (network.size() == 1) {
        JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.get(0);
        gramBW.write("S : NS_B " + gwTemp.getStrLabel() + " NS_E");
        gramBW.close();
      } else {
        for (int i = 0; i < network.size(); i++) {
          JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.get(i);
          // single word;
          gramBW.write("S : NS_B " + gwTemp.getStrLabel() + " NS_E");
          gramBW.newLine();
        }
        // whole sentence
        gramBW.write("S : NS_B ");
        for (int i = 0; i < network.size(); i++) {
          JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.get(i);
          // grammar file;
          gramBW.write(gwTemp.getStrLabel() + " ");
        }
        gramBW.write("NS_E");
        // close the file
        gramBW.close();
      }
    } // end of "if network!=null"

  }

  // include the correct answer
  public int getSubstitution(JCALL_NetworkNodeStruct gw, Vector vErrors, String word) {
    // to prevent repetiveness
    Set<String> hs = new HashSet<String>();
    Vector vec;
    String str;
    int intTotal = 0;

    logger.debug("enter getSubstitution");

    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
    node.bAccept = true;
    node.bValid = true;
    // node.strClass = "DW";
    node.strError = "CORRECT";
    if (word != null && word.length() > 0) {
      node.setStrKana(word);
      node.setStrKanji(word);
      if (!hs.contains(word)) {
        hs.add(word);
        gw.addPNode(node);
        intTotal++;
      }
    }

    vec = getDW(word, vErrors);

    if (vec != null && vec.size() > 0) {
      for (int i = 0; i < vec.size(); i++) {
        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
        str = pnode.getStrKana();
        if (!hs.contains(str)) {
          hs.add(str);
          gw.addPNode(pnode);
          intTotal++;
        }
      }
      // intTotal+=vec.size();
      logger.debug("DW: " + intTotal);
    }

    return intTotal;

  }

  Vector getDW(String word, Vector vErrors) {

    if (word == null) {
      return null;
    }
    Set<String> hs = new HashSet<String>();
    JCALL_NetworkSubNodeStruct node;
    WordDataMeta wordmeta;
    Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();

    for (int i = 0; i < vErrors.size(); i++) {
      wordmeta = (WordDataMeta) vErrors.get(i);
      if (wordmeta != null) {
        String strT = wordmeta.getStrTargetWord();
        String strO = wordmeta.getStrObservedWord();
        if (strT != null && strT.equals(word)) {
          node = new JCALL_NetworkSubNodeStruct();
          node.bAccept = false;
          node.bValid = false;
          // node.strClass = "DW";
          node.strError = "DW";
          if (strO != null && strO.length() > 0) {
            node.setStrKana(strO);
            node.setStrKanji(strO);
            if (!hs.contains(strO)) {
              hs.add(strO);
              vResult.addElement(node);
            }
            logger.debug("get one VerbSubsGroup: " + strO);
          }

        }

      }

    }

    return vResult;

  }

  public Vector getErrorPrediction(int repeatPersons) {

    // load err Table from experiment 1
    ComputeScore cs = new ComputeScore();
    Vector errors_ex1 = cs.getErrorsFromDb_ex1(OldCallLogParser.WordTableName);

    // get errors of each lesson, then
    Vector predictErrors = null;
    if (errors_ex1 != null) {
      // errors_ex1_pure =
      logger.debug("All errors_ex1 Item is: " + errors_ex1.size());
      // System.out.println("All errors_ex1 Item is: "+ errors_ex1.size());
      predictErrors = getErrors(errors_ex1, repeatPersons);
      if (predictErrors != null) {

        logger.info("repeatPersons[" + repeatPersons + "], error size: " + predictErrors.size());

      }
    }

    return predictErrors;
  }

  // repeatPersons, at least "repeatPersons" person make the errors
  //
  Vector getErrors(Vector vErrors, int repeatPersons) {
    // Vector vResult = new Vector();
    Vector<WordDataMeta> vRec = new Vector<WordDataMeta>();

    // Vector vRecCopy = new Vector();

    if (vErrors == null) {
      return null;
    }

    Vector vRecDiffItems = getErrors(vErrors);

    if (vRecDiffItems != null && vRecDiffItems.size() > 0) {

      logger.info("Diff Word Items size: " + vRecDiffItems.size());

      // System.out.println("Diff Word Items size: "+ vRecDiffItems.size());

      for (int i = 0; i < vRecDiffItems.size(); i++) {
        WordDataMeta wordmeta = (WordDataMeta) vRecDiffItems.get(i);
        // IsContain(wordmeta, vResult, false)
        if (FindOut(wordmeta, vErrors) >= repeatPersons) {
          vRec.addElement(wordmeta);
        }
      }
    } else {
      vRec = null;
      logger.error("No Diff Items are Foundd");
    }

    return vRec;

  }

  // pured error items, deleted the "CORRECT" item
  Vector getErrors(Vector vErrors) {
    // Vector vResult = new Vector();
    Vector<WordDataMeta> vRec = new Vector<WordDataMeta>();
    // Vector vRecCopy = new Vector();
    if (vErrors == null) {
      return null;
    }

    int ICorrect = 0;
    int ISpell = 0;
    int ISentence = 0;
    int INull = 0;
    int IIns = 0;
    int IOrignErrors = 0;
    for (int i = 0; i < vErrors.size(); i++) {
      WordDataMeta wordmeta = (WordDataMeta) vErrors.get(i);
      // IsContain(wordmeta, vResult, false)
      String specificType = wordmeta.getStrSpecificType();
      String observed = wordmeta.getStrObservedWord();
      if (specificType != null && !specificType.equalsIgnoreCase("CORRECT") && !specificType.equalsIgnoreCase("SPELL")
          && !specificType.equalsIgnoreCase("sentence") && !specificType.equalsIgnoreCase("INS")
          && !specificType.equalsIgnoreCase("NULL")) {
        IOrignErrors++;
        if (observed != null && observed.length() > 0) {
          if (!IsContain(wordmeta, vRec, false)) {
            vRec.addElement(wordmeta);
          }
        }
      }

      if (specificType != null && specificType.equalsIgnoreCase("SPELL")) {
        ISpell++;
      }
      if (specificType != null && specificType.equalsIgnoreCase("CORRECT")) {
        ICorrect++;
      }
      if (specificType != null && specificType.equalsIgnoreCase("sentence")) {
        ISentence++;
      }

      if (specificType != null && specificType.equalsIgnoreCase("NULL")) {
        INull++;
      }
      if (specificType != null && specificType.equalsIgnoreCase("INS")) {
        IIns++;
      }

    }

    logger.info("OrignErrors: " + IOrignErrors);
    logger.info("SPELL: " + ISpell + " Correct: " + ICorrect + " Sentence: " + ISentence + " NULL: " + INull + " INS: "
        + IIns);

    return vRec;

  }

  /**
   * @param meta
   * @param v
   * @param sDiff
   *          : if compare the student ID; true: compare; false: not compare
   * @return
   */
  public boolean IsContain(WordDataMeta meta, Vector v, boolean sDiff) {
    if (v == null) {
      v = new Vector();
    }
    for (int i = 0; i < v.size(); i++) {

      WordDataMeta wordmeta = (WordDataMeta) v.get(i);

      if (wordmeta.equals(meta)) {

        if (sDiff) {

          if (wordmeta.intStudentID == meta.intStudentID) {
            return true;
          }

        } else {

          return true;
        }

      }
    }

    return false;

  }

  /**
   * @param meta
   * @param v
   * @param sDiff
   *          : if compare the student ID; true: compare; false: not compare,
   *          here is always true
   * @return
   */
  public int FindOut(WordDataMeta meta, Vector v) {
    int intRec = 0;

    if (v == null) {
      v = new Vector();
    }

    for (int i = 0; i < v.size(); i++) {

      WordDataMeta wordmeta = (WordDataMeta) v.get(i);

      if (wordmeta.equals(meta)) {
        if (wordmeta.intStudentID == meta.intStudentID && intRec == 0) {
          intRec++;
        } else if (wordmeta.intStudentID != meta.intStudentID && intRec > 0) {
          intRec++;
        }
      }
    }

    return intRec;
  }

  public static void main(String args[]) throws IOException, Exception {

    Perplexity per = new Perplexity();

    // Vector v =per.getErrorPrediction(2);
    // for (int i = 0; i < v.size(); i++) {
    // WordDataMeta wordmeta = (WordDataMeta)v.get(i);
    //
    // System.out.println("["+i+"]---> Target: "+ wordmeta.strTargetWord
    // +" observed: "+ wordmeta.strObservedWord);
    //
    // }

    // per.writeJulianCompileFiles(1);
    per.writeJulianCompileFiles(2);
    // per.writeBatFile(1,1);

  }

}
