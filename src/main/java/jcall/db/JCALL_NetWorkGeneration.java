/**
 * Created on 2008/03/12
 *
 * @author wang Copyrights @kawahara lab
 */
package jcall.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jcall.CALL_database;
import jcall.CALL_io;
import jcall.CALL_sentenceStruct;
import jcall.CALL_sentenceWordStruct;
import jcall.CALL_wordStruct;
import jcall.config.FindConfig;
import jcall.recognition.database.DataManager;
import jcall.recognition.dataprocess.NewLogParser;
import jcall.recognition.dataprocess.SentenceDataMeta;
import jcall.recognition.languagemodel.MakeDFA;
import jcall.recognition.util.CharacterUtil;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class JCALL_NetWorkGeneration {
    static Logger logger = Logger.getLogger(JCALL_NetWorkGeneration.class.getName());

    static JCALL_NetworkStruct network;
    JCALL_NetworkNodeStruct gw;
    JCALL_NetworkNodeStruct pregw = null;
    private HttpServletRequest servletRequest;
    private String user;

    public static String path;
    public static String JGRAMBASE = FindConfig.getConfig().findStr("JGRAMBASE");
    public static final String STR_TAB = new String("\t");
    // public static final String PATH = "D:\\julian";
    public static final String CONTEXTFILENAME = new String("currentQuestion");

    public static String[] particle = { "ã�¯", "ã�Œ", "ã‚‚", "ã�®", "ã�¨", "ã�‹ã‚‰", "ã�¸", "ã�«", "ã‚’", "ã�§", "ã�¾ã�§" };
    static final String TA = "ã�Ÿ";
    static final String DA = "ã� ";

    JCALL_PredictionProcess pp;
    DataManager dm;

    public JCALL_NetWorkGeneration() {
        init();
    }

    public JCALL_NetWorkGeneration(HttpServletRequest request) {
        servletRequest = request;
        init();
    }

    public JCALL_NetWorkGeneration(HttpServletRequest request, String userName) {
        servletRequest = request;
        user = userName;
        init();
    }

    private void init() {
        gw = new JCALL_NetworkNodeStruct();
        network = new JCALL_NetworkStruct();
        dm = new DataManager();
        pp = new JCALL_PredictionProcess();
        path = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes");

    }

    public Vector sentenceGrammar(CALL_sentenceStruct sentence, int lesson) {

        Vector wordList;
        String sentenceStr;
        CALL_sentenceWordStruct tempSentenceWord;

        // print the top sentence
        sentenceStr = sentence.getSentenceString(CALL_io.kanji);

        // //logger.debug("Enter sentenceGrammar, Top sentence: " +
        // sentenceStr);
        // logger.debug("next");
        // it is the top sentence, using same function with the hintsStruct
        if (sentenceStr == null || sentenceStr.length() == 0) {
            return null;
        }

        //TODO test
        wordList = sentence.getSentenceWords();
//        wordList = createWordlist();

        if (wordList != null) {
            network = new JCALL_NetworkStruct();

            // logger.debug("target sentence, word size is: " +
            // wordList.size());

            for (int i = 0; i < wordList.size(); i++) {
                tempSentenceWord = (CALL_sentenceWordStruct) wordList.elementAt(i);

                gw = new JCALL_NetworkNodeStruct();
                if (tempSentenceWord != null) {
                    gw.setSword(tempSentenceWord);

                    CALL_wordStruct wordstruct = tempSentenceWord.getWord();
                    String wordKanji = wordstruct.getKanji();
                    int intType = wordstruct.getType();
                    // logger.debug("word " + i + ", kanji is " + wordKanji);

                    JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmKanji(wordKanji, intType);
                    if (call_word == null) {
                        call_word = JCALL_Lexicon.getInstance().getWordFmKanji(wordKanji);
                    }
                    if (call_word != null) {
                        // logger.debug("find target word in the lexicon: "
                        // + wordKanji);

                        gw.setCall_word(call_word);

                        String strGrammarRule = tempSentenceWord.getGrammarRule();
                        String strComponentName = tempSentenceWord.getComponentName();
                        String strComponentLabel;
                        if (strComponentName != null && strComponentName.length() > 0) {
                            // check if this ComponentName has whitespace
                            StringTokenizer st = new StringTokenizer(strComponentName);
                            String str1 = "";
                            String str2 = "";
                            if (st.hasMoreElements()) {
                                str1 = (String) st.nextElement();
                            } else {
                                logger.error("strComponentName is not null,but return no string");
                            }

                            if (st.hasMoreElements()) {
                                str2 = (String) st.nextElement();
                                if (str1.length() >= str2.length()) {
                                    strComponentLabel = str1;
                                } else {
                                    strComponentLabel = str2;
                                }
                            } else {
                                strComponentLabel = strComponentName;
                            }

                            logger.debug("strComponentName: " + strComponentName);

                            if (strComponentName.equalsIgnoreCase("Particle") && pregw != null) {
                                gw.setStrLabel(pregw.getStrLabel().trim() + "_" + strComponentLabel.trim());
                            } else {
                                gw.setStrLabel(strComponentLabel);
                            }

                        } else {
                            if (strGrammarRule != null && strGrammarRule.length() > 0) {
                                gw.setStrLabel(strGrammarRule);
                            } else {
                                gw.setStrLabel("NoLabel");
                                logger.error("error, along with no grammar rule ");
                            }
                        }

                        pp.getSubstitution(gw, lesson);

                    } else {// end of if lwm
                        logger.error(" not found the word in lexicon in CALL_SentenceGrammar");
                    }
                }

                /*
                 * end of coping with the target word Then for each word, add
                 * all substitution words (on the basis of errorType, create
                 * errors or extract from prediction file )
                 */

                pregw = gw;
                network.addGW(gw);

            }// end of for(wordlist)
        }
        // file
        try {
            if (user != null) {
                // create folder SpeechData
                String rootpath = path + "/SpeechData";
                File fileRoot = new File(rootpath);
                if (!fileRoot.exists()) {
                    fileRoot.mkdir();
                }
                // create folder user
                String userpath = path + "/SpeechData/" + user;
                File fileuser = new File(userpath);
                if (!fileuser.exists()) {
                    fileuser.mkdir();
                }
                // write to file
                writeVocaFile(lesson, wordList.size(), network.vGW);
                writeGrammarFile(lesson, wordList.size(), network.vGW);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // invoke the mkdfa
        MakeDFA dfa = new MakeDFA(servletRequest);
        dfa.getDFAFile(CONTEXTFILENAME);
        // sentence.setVGWNetwork(network);

        return network.vGW;
    }

    public Vector sentenceGrammar(List<SentenceWordDto> swDto, int lesson)
            throws JsonParseException, JsonMappingException, IOException {

        SentenceWordDto sentenceWordDto;

        //TODO test
//        sentenceStr = "ラジオはベッドの上にありますか";

        if (swDto != null) {
            network = new JCALL_NetworkStruct();

            for (int i = 0; i < swDto.size(); i++) {
                sentenceWordDto = swDto.get(i);

                gw = new JCALL_NetworkNodeStruct();
                if (sentenceWordDto != null) {

                    String wordKanji = sentenceWordDto.getWordStruct().getKanji();
                    int intType = Integer.valueOf(sentenceWordDto.getWordStruct().getType());

                    JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmKanji(wordKanji, intType);
                    if (call_word == null) {
                        call_word = JCALL_Lexicon.getInstance().getWordFmKanji(wordKanji);
                    }
                    if (call_word != null) {

                        gw.setCall_word(call_word);

                        String strGrammarRule = sentenceWordDto.getGrammarRule();
                        String strComponentName = sentenceWordDto.getComponentName();
                        String strComponentLabel;
                        if (strComponentName != null && strComponentName.length() > 0) {
                            // check if this ComponentName has whitespace
                            StringTokenizer st = new StringTokenizer(strComponentName);
                            String str1 = "";
                            String str2 = "";
                            if (st.hasMoreElements()) {
                                str1 = (String) st.nextElement();
                            } else {
                                logger.error("strComponentName is not null,but return no string");
                            }

                            if (st.hasMoreElements()) {
                                str2 = (String) st.nextElement();
                                if (str1.length() >= str2.length()) {
                                    strComponentLabel = str1;
                                } else {
                                    strComponentLabel = str2;
                                }
                            } else {
                                strComponentLabel = strComponentName;
                            }

                            logger.debug("strComponentName: " + strComponentName);

                            if (strComponentName.equalsIgnoreCase("Particle") && pregw != null) {
                                gw.setStrLabel(pregw.getStrLabel().trim() + "_" + strComponentLabel.trim());
                            } else {
                                gw.setStrLabel(strComponentLabel);
                            }

                        } else {
                            if (strGrammarRule != null && strGrammarRule.length() > 0) {
                                gw.setStrLabel(strGrammarRule);
                            } else {
                                gw.setStrLabel("NoLabel");
                                logger.error("error, along with no grammar rule ");
                            }
                        }

                        pp.getSubstitution(gw, lesson, sentenceWordDto);

                    } else {// end of if lwm
                        logger.error(" not found the word in lexicon in CALL_SentenceGrammar");
                    }
                }

                pregw = gw;
                network.addGW(gw);

            }// end of for(wordlist)
        }
        // file
        try {
            if (user != null) {
                // create folder SpeechData
                String rootpath = path + "/SpeechData";
                File fileRoot = new File(rootpath);
                if (!fileRoot.exists()) {
                    fileRoot.mkdirs();
                }
                // create folder user
                String userpath = path + "/SpeechData/" + user;
                File fileuser = new File(userpath);
                if (!fileuser.exists()) {
                    fileuser.mkdirs();
                }
                // write to file
                writeVocaFile(lesson, swDto.size(), network.vGW);
                writeGrammarFile(lesson, swDto.size(), network.vGW);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // invoke the mkdfa
        MakeDFA dfa = new MakeDFA(servletRequest, user);
        dfa.getDFAFile(CONTEXTFILENAME);
        // sentence.setVGWNetwork(network);

        return network.vGW;
    }

    public void writebatFile(String filename) throws IOException {

        String filepath = "C:\\eclipse\\workspace\\JCALLSpokenExercise\\";

        String bat = filename + ".bat";
        File batFile = new File(filepath, bat);
        // check file path and make dir,create new file
        File batpath = new File(filepath);
        if (!batpath.exists()) {
            batpath.mkdir();
        }
        if (batFile.exists()) {
            batFile.delete();
        }
        batFile.createNewFile();
        logger.debug("update all data,creat new file---" + batFile.getAbsolutePath());
        // save to file

        BufferedWriter vocaBW = new BufferedWriter(new FileWriter(batFile));
        if (vocaBW != null) {
            vocaBW.write("start julian\\bin\\julian.exe -C julian/hmm_ptm.jconf -dfa Data/JGrammar/" + filename
                    + ".dfa -v Data/JGrammar/" + filename + ".dict -input rawfile -quiet");
            vocaBW.newLine();
            logger.debug("start julian\\bin\\julian.exe -C julian/hmm_ptm.jconf -dfa Data/JGrammar/" + filename
                    + ".dfa -v Data/JGrammar/" + filename + ".dict -input rawfile -quiet");
            vocaBW.flush();
            vocaBW.close();
        }

    }

    public void writeVocaFile(int lessonIndex, int sentenceListSize, Vector network) throws IOException {

        // file
        // grammarFileName = "\\L"+index+"\\L"+index+correctAnswer;
        // String filename = new String("L" + (lessonIndex));
        // String filename ="L"+lessonIndex+ correctAnswer;
        Set<String> hs;
        String OS = System.getProperty("os.name").toLowerCase();
        String filepath;
        if (OS.indexOf("win") >= 0) {
            filepath = path + "/SpeechData/" + user + "/JGrammar";
        } else {
            filepath = servletRequest.getSession().getServletContext()
                    .getRealPath("/WEB-INF/classes/SpeechData/" + user + "/JGrammar/");
        }
        //
        filepath = filepath.replace("\\", "/");
        // String filepath = PATH + "\\" ;
        String voca = CONTEXTFILENAME + ".voca";
        // check file path and make dir,create new file
        File vocaPath = new File(filepath);
        if (!vocaPath.exists()) {
            vocaPath.mkdirs();
        }
        File vocaFile = new File(filepath, voca);
        if (vocaFile.exists()) {
            vocaFile.delete();
            // logger.debug("Exist voca-->> Delete");
        }
        vocaFile.createNewFile();
        logger.debug("update all data,creat new file voca---" + vocaFile.getAbsolutePath());
        // save to file

        BufferedWriter vocaBW = new BufferedWriter(new FileWriterWithEncoding(vocaFile, "UTF8"));
        if (vocaBW != null) {
            if (network != null) {
                if (network.size() == sentenceListSize) {
                    for (int i = 0; i < network.size(); i++) {
                        hs = new HashSet<String>();
                        JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.elementAt(i);
                        vocaBW.write("% " + gwTemp.getStrLabel());
                        vocaBW.newLine();
                        // //logger.debug("% " + gwTemp.getStrLabel());
                        // first add answer
                        String str2 = gwTemp.getCall_word().getStrKana().trim() + STR_TAB;
                        String str = gwTemp.getCall_word().getStrKana().trim();
                        hs.add(str);
                        int type = gwTemp.getCall_word().getIntType();
                        String strJulianSentenceAnswer = str2 + CharacterUtil.wordKanaToJulianVoca(str, type);
                        vocaBW.write(strJulianSentenceAnswer);
                        vocaBW.newLine();
                        // //logger.debug(strJulianSentenceAnswer);

                        // other substitutions
                        Vector wordList = gwTemp.getVecSubsWord();
                        if (wordList != null) {
                            for (int j = 0; j < wordList.size(); j++) {
                                JCALL_NetworkSubNodeStruct epl = (JCALL_NetworkSubNodeStruct) wordList.elementAt(j);
                                // String strKanji = epl.getStrKanji().trim() +
                                // STR_TAB;
                                String strkana2 = epl.getStrKana().trim() + STR_TAB;
                                String strkana = epl.getStrKana().trim();
                                if (!hs.contains(strkana)) {
                                    hs.add(strkana);
                                    int intType = epl.getIntType();
                                    String strJulianSentence = strkana2 + CharacterUtil.wordKanaToJulianVoca(strkana, intType);
                                    vocaBW.write(strJulianSentence);
                                    vocaBW.newLine();
                                    // //logger.debug(strJulianSentence);
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

    public void writeGrammarFile(int lessonIndex, int sentenceListSize, Vector network) throws IOException {

        // file
        // String filename = new String("L" + (lessonIndex));
        String OS = System.getProperty("os.name").toLowerCase();
        String filepath;
        if (OS.indexOf("win") >= 0) {
            filepath = path + "/SpeechData/" + user + "/JGrammar";
        } else {
            filepath = servletRequest.getSession().getServletContext()
                    .getRealPath("/WEB-INF/classes/SpeechData/" + user + "/JGrammar/");
        }
        filepath = filepath.replace("\\", "/");
        // add folder for each user
        String grammar = CONTEXTFILENAME + ".grammar";
        File gramFile = new File(filepath, grammar);
        // check file path and make dir,create new file
        if (true) {
            File grammPath = new File(filepath);
            if (!grammPath.exists()) {
                grammPath.mkdirs();
            }
            if (gramFile.exists()) {
                gramFile.delete();
                // logger.debug("Exist voca-->> Delete");
            }
            gramFile.createNewFile();
            // System.out.println("update all data,creat new file---"
            // + gramFile.getAbsolutePath());

        }
        // save to file

        BufferedWriter gramBW = new BufferedWriter(new FileWriter(gramFile));

        if (network != null && network.size() == sentenceListSize) {
            if (network.size() == 1) {
                JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.get(0);
                gramBW.write("S : NS_B " + gwTemp.getStrLabel() + " NS_E");
                gramBW.close();
            } else {

                // for (int i = 0; i < network.size(); i++) {
                // JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct)
                // network.get(i);
                // single word;
                // gramBW.write("S : NS_B " + gwTemp.getStrLabel()+ " NS_E");
                // gramBW.newLine();
                // }

                // whole sentence
                gramBW.write("S : NS_B ");
                for (int i = 0; i < network.size(); i++) {
                    JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.get(i);
                    // grammar file;
                    gramBW.write(gwTemp.getStrLabel() + " ");
                }
                gramBW.write("NS_E");

                // gramBW.write("S : NS_B ");
                // for (int i = 0; i < network.size(); i++) {
                // JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct)
                // network.get(i);
                // grammar file;
                // gramBW.write(gwTemp.getStrLabel()+ " NOISE ");
                // }
                // gramBW.write("NS_E");

                // close the file
                gramBW.close();
            }
        } // end of "if network!=null"

    }

    public void writeVocaFile(int lessonIndex, int sentenceListSize, Vector network, String correctAnswer, String filename)
            throws IOException {

        // file
        // grammarFileName = "\\L"+index+"\\L"+index+correctAnswer;
        // String filename = new String("L" + (lessonIndex));
        // String filename ="L"+lessonIndex+ correctAnswer;

        String filepath = JGRAMBASE + "\\";
        // String filepath = PATH + "\\" ;
        String voca = filename + ".voca";
        File vocaFile = new File(filepath, voca);
        // check file path and make dir,create new file
        File vocaPath = new File(filepath);
        if (!vocaPath.exists()) {
            vocaPath.mkdir();
        }
        if (vocaFile.exists()) {
            vocaFile.delete();
        }
        vocaFile.createNewFile();
        // logger.debug("update all data,creat new file---"
        // + vocaFile.getAbsolutePath());
        // save to file

        BufferedWriter vocaBW = new BufferedWriter(new FileWriter(vocaFile));
        if (vocaBW != null) {
            if (network != null) {
                if (network.size() == sentenceListSize) {
                    for (int i = 0; i < network.size(); i++) {
                        JCALL_NetworkNodeStruct gwTemp = (JCALL_NetworkNodeStruct) network.elementAt(i);
                        vocaBW.write("% " + gwTemp.getStrLabel());
                        vocaBW.newLine();
                        // logger.debug("% " + gwTemp.getStrLabel());
                        Vector wordList = gwTemp.getVecSubsWord();
                        for (int j = 0; j < wordList.size(); j++) {
                            JCALL_NetworkSubNodeStruct epl = (JCALL_NetworkSubNodeStruct) wordList.elementAt(j);
                            // String strKanji = epl.getStrKanji().trim() +
                            // STR_TAB;
                            String strkana2 = epl.getStrKana().trim() + STR_TAB;
                            String strkana = epl.getStrKana().trim();
                            int intType = epl.getIntType();
                            String strJulianSentence = strkana2 + CharacterUtil.wordKanaToJulianVoca(strkana, intType);
                            vocaBW.write(strJulianSentence);
                            vocaBW.newLine();
                            // logger.debug(strJulianSentence);
                        }
                        String str = gwTemp.getCall_word().getDSynonymValue();
                        if (str != null && str.length() > 0) {
                            // String strKanji = str.trim() + STR_TAB;
                            String strkana2 = str.trim() + STR_TAB;
                            String strkana = str.trim();
                            int intType = gwTemp.getCall_word().getIntType();
                            String strJulianSentence = strkana2 + CharacterUtil.wordKanaToJulianVoca(strkana, intType);
                            vocaBW.write(strJulianSentence);
                            vocaBW.newLine();
                            // logger.debug(strJulianSentence);
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

                }
            } else {
                System.out.println("network size is:" + network.size() + " not the same with sentenceListSize: "
                        + sentenceListSize);
            }
            vocaBW.flush();
            vocaBW.close();

        } else {
            System.out.println("network is null");
        }

    }

    public void writeGrammarFile(int lessonIndex, int sentenceListSize, Vector network, String correctAnswer,
            String filename) throws IOException {

        // file
        // String filename = new String("L" + (lessonIndex));
        String filepath = path + JGRAMBASE + "\\";
        // String filepath = PATH + "\\" ;
        String grammar = filename + ".grammar";
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

    // public void getSubstitution(JCALL_NetworkNodeStruct gw,int lesson){
    //
    // logger.debug("enter getSubstitution");
    // JCALL_Word call_word = gw.call_word;
    // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
    //
    // JCALL_PredictionProcess pp;
    // try {
    // pp = new JCALL_PredictionProcess();
    //
    //
    // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
    // String strForm = tempSentenceWord.getFormString();
    // String strTransRule = tempSentenceWord.getTransformationRule();
    //
    // if(call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_VERB ||
    // call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_ADJECTIVE ||
    // call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_ADJVERB ){
    // getVerbSubstitution(gw,lesson);
    //
    // }
    // else if(call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_NUMQUANT ||
    // tempSentenceWord.isUseCounterSettings()){
    // get the predicted words, then add to the network;
    // || tempSentenceWord.isUseCounterSettings()
    // logger.debug("Counter prediction");
    // String strCounter = tempSentenceWord.getTopWordString(CALL_io.kanji);
    // if(strCounter!=null && strCounter.length()>0){
    //
    // logger.debug("target counter string: "+ strCounter);
    // JCALL_Word wordTemp = JCALL_Lexicon.getWordFmKanji(strCounter);
    // if(wordTemp!=null){
    // logger.debug("Counter kanji: "+ wordTemp.getStrKanji());
    // Vector vec =pp.getNQErrors(wordTemp, lesson);
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // gw.addPNode(pnode);
    // }
    // }
    //
    // }
    // }
    //
    // }
    // else if(call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_NUMQUANT){
    //
    // }
    // else if(call_word.getIntType()==JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL){
    //
    //
    // get the predicted words, then add to the network;
    // getParticleSubstitution(gw, lesson);
    //
    // }else{//the only possible is noun
    // get the predicted words, then add to the network;
    // Vector vec = pp.getNounSubstitution(call_word, lesson);
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // gw.addPNode(pnode);
    // }
    // }
    //
    // }
    //
    // } catch (IOException e1) {
    // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    //
    // }
    //
    //
    //
    //
    // public void getVerbSubstitution(JCALL_NetworkNodeStruct gw,int lesson) {
    // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
    // JCALL_Word lwm, CALL_sentenceWordStruct tempSentenceWord
    //
    // Vector<PNode> vecResult = new Vector();
    //
    // to prevent repetiveness
    // Set<String> hs = new HashSet<String>();
    // Vector vec;
    // String str;
    // int intTotal=0;
    //
    //
    // JCALL_Word word = gw.call_word;
    // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
    // CALL_database db = tempSentenceWord.getDb();
    //
    // logger.debug("enter getVerbSubstitution, word: "+ word.getStrKanji());
    //
    // String strForm = tempSentenceWord.getFullFormString();
    // String strTransRule = tempSentenceWord.getTransformationRule();
    //
    // if(strForm!=null && strForm.length()>0){
    //
    //
    // logger.debug("sentence word form: "+ strForm +" transformation rule: "+
    // strTransRule);
    // (int sign, int tense, int style, int type)
    //
    // CALL_Form call_form = new
    // CALL_Form(tempSentenceWord.getSign(),tempSentenceWord.getTense(),tempSentenceWord.getPoliteness(),tempSentenceWord.getQuestion());
    //
    // String answer = tempSentenceWord.getTopWordString(CALL_io.kana);
    //
    // vec = getTW_DFORM(db,word,strForm,strTransRule,answer); //including the
    // answer and marked correct in error type
    //
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // str = pnode.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // gw.addPNode(pnode);
    // vecResult.addElement(pnode);
    // }
    // }
    // intTotal+=vec.size();
    // logger.debug("TW_DFORM;"+vec.size());
    // }
    //
    // vec = getDW_TFORM(db,word,strTransRule,lesson,call_form); //including the
    // answer and marked correct in error type
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // str = pnode.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // gw.addPNode(pnode);
    // vecResult.addElement(pnode);
    // }
    // }
    // intTotal+=vec.size();
    // logger.debug("DW_TFORM: "+vec.size());
    // }
    //
    //
    //
    // vec = getDW_DFORM(db,word,strForm,strTransRule,lesson,call_form);
    // including the answer and marked correct in error type
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // str = pnode.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // gw.addPNode(pnode);
    // vecResult.addElement(pnode);
    // }
    // }
    // intTotal+=vec.size();
    // logger.debug("DW_DFORM: "+vec.size());
    // }
    //
    //
    // vec = getTW_WIF(db,word,strForm,lesson); //including the answer and
    // marked correct in error type
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // str = pnode.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // gw.addPNode(pnode);
    // vecResult.addElement(pnode);
    // }
    // }
    // intTotal+=vec.size();
    // logger.debug("TW_WIF: "+vec.size());
    // }
    //
    // }//end if
    //
    // }
    //
    //
    //
    //
    // public void getParticleSubstitution(JCALL_NetworkNodeStruct gw,int
    // lesson) {
    // to prevent repetiveness
    // Set<String> hs = new HashSet<String>();
    // Vector vec;
    // String str;
    // int intTotal=0;
    //
    // logger.debug("enter getParticleSubstitution");
    //
    // JCALL_Word word = gw.call_word;
    // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
    // CALL_database db = tempSentenceWord.getDb();
    //
    // vec = getDW(db,word,lesson);
    //
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct)
    // vec.get(i);
    // str = pnode.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // gw.addPNode(pnode);
    // vecResult.addElement(pnode);
    // }
    // }
    // intTotal+=vec.size();
    // logger.debug("DW: "+vec.size());
    // }
    //
    //
    //
    // }
    //
    //
    //
    //
    //
    // Vector getDFORM(CALL_database db,JCALL_Word word, String strForm, String
    // strTransRule){
    //
    // logger.debug("enter getDFORM, DFORM: "+ strForm);
    //
    //
    // Vector vResult = new Vector();
    // CALL_formStruct form=null;
    // form = new CALL_formStruct();
    // form.setFromString(strForm);
    // CALL_Form call_form = new CALL_Form(form);
    //
    //
    // if(word.getIntType()== JCALL_Lexicon.LEX_TYPE_VERB){
    //
    // CALL_verbRulesStruct vRules = db.vrules;
    // vResult = vRules.getVerbForms(word, strTransRule, call_form.getSign(),
    // call_form.getTense(), call_form.getStyle(), call_form.getType());
    // logger.debug("DForm size: "+ vResult.size());
    //
    // }else if(word.getIntType()== JCALL_Lexicon.LEX_TYPE_ADJECTIVE ||
    // word.getIntType()== JCALL_Lexicon.LEX_TYPE_ADJVERB){
    //
    // CALL_adjectiveRulesStruct adjRules = db.arules;
    // //logger.debug("word kanji: "+ word.getStrKanji());
    // int intType = word.getIntType();
    // //logger.debug("word Type: "+ JCALL_Lexicon.typeInt2Name(intType));
    // vResult = adjRules.getAdjectiveForms(word, strTransRule,
    // call_form.getSign(), call_form.getTense(), call_form.getStyle(),
    // call_form.getType());
    //
    //
    // }else {
    //
    // logger.error(word.getStrKanji() + "is not a verb or adjective");
    //
    // }
    //
    //
    //
    // return vResult;
    //
    //
    // }
    //
    //
    //
    //
    // Vector getDW(CALL_database db,JCALL_Word word,int lesson ){
    //
    // Set<String> hs = new HashSet<String>();
    // JCALL_PredictionDataStruct pdm= null;
    // JCALL_NetworkSubNodeStruct node;
    // CALL_wordWithFormStruct newWord;
    // String sLesson = new String("" + lesson);
    // JCALL_PredictionDatasStruct eps = db.eps;
    // Vector vResult= new Vector();
    // for (int i = 0; i < eps.vecPDM.size(); i++) {
    // pdm = eps.vecPDM.get(i);
    // if(pdm.strClass.equalsIgnoreCase("word")){//word category
    //
    // if(pdm.isHasLesson(sLesson) && pdm.getIntType()==word.getIntType() &&
    // pdm.compareTo(word)==0) {// same lesson
    // node = new JCALL_NetworkSubNodeStruct();
    // node.bAccept = pdm.bAccept;
    // node.bValid = pdm.bValid;
    // node.strClass = pdm.strClass;
    // node.strError = pdm.strError;
    // node.vecSpecific = pdm.vecSpecific;
    // String s = pdm.strSubword;
    // if(s!=null &&s.length()>0){
    // if(pdm.bValid){
    // find s in lexicon
    // JCALL_Word call_word = JCALL_Lexicon.getWordFmStr(s, pdm.getIntType());
    // if(call_word!=null){
    // node.setStrKana(call_word.getStrKana());
    // node.setStrKanji(call_word.getStrKanji());
    // node.setId(call_word.getId());
    // if(!hs.contains(call_word.getStrKana())){
    // hs.add(call_word.getStrKana());
    // vResult.addElement(node);
    // }
    // logger.debug("get one VerbSubsGroup: "+pdm.getStrWord());
    // }else{
    // logger.error("the subsitution word could not be found in lexicon: "+ s);
    // }
    //
    // }
    //
    //
    // }
    //
    // }
    // }
    // }
    // synonym
    // String synonumW = word.getDSynonymValue();
    //
    // if(synonumW!=null && synonumW.length()>0){
    //
    // StringTokenizer stSynonym = new StringTokenizer(synonumW,",");
    // while(stSynonym.hasMoreElements()){
    // String tempString = (String) stSynonym.nextElement();
    // find its kana
    // JCALL_Word synonumword = JCALL_Lexicon.getWordFmStr(tempString,
    // word.getIntType());
    // if(synonumword!=null){
    // String str = synonumword.getStrKana();
    // if(!hs.contains(synonumword.getStrKana())){
    // hs.add(synonumword.getStrKana());
    // node = new JCALL_NetworkSubNodeStruct();
    // node.bAccept = true;
    // node.bValid = true;
    // node.strError = "correct";
    // node.strClass ="lexical";
    // node.setStrKana(synonumword.getStrKana());
    // node.setStrKanji(synonumword.getStrKanji());
    // node.setId(synonumword.getId());
    // vResult.addElement(node);
    // }
    // }
    // }
    // }
    //
    // return vResult;
    //
    // }
    //
    //
    //
    // Vector getTW_DFORM(CALL_database db,JCALL_Word word, String strForm,
    // String strTransRule,String ansKana){
    // logger.debug("enter getTW_DFORM, TW: "+ word.getStrKanji());
    //
    // Vector vecResult = new Vector();
    // String str;
    // Vector vec = getDFORM(db,word,strForm,strTransRule); //included the
    // answer;
    // String error = "TW_DFORM";
    // String strClass = "GRAMMAR";
    // if(vec!=null&&vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // CALL_wordWithFormStruct wordwithForm =
    // (CALL_wordWithFormStruct)vec.get(i);
    //
    // str = wordwithForm.getSurfaceFormKana().trim();
    // logger.debug("DFORM: "+str);
    // JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
    // if(str.equals(ansKana)){
    // pnode.setBAccept(true);
    // pnode.setStrError("correct");
    // }else{
    // pnode.setBAccept(false);
    // pnode.setStrError(error);
    // pnode.setStrClass(strClass);
    // }
    // pnode.setBValid(true);
    // pnode.setStrKana(wordwithForm.getSurfaceFormKana());
    // pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
    // vecResult.addElement(pnode);
    // logger.debug(pnode.toString());
    // }
    // }
    //
    // return vecResult;
    //
    // }
    //
    //
    //
    //
    //
    // Vector getDW_DFORM(CALL_database db,JCALL_Word word, String strForm,
    // String strTransRule,int lesson,CALL_Form call_form){
    //
    // Vector vResult = new Vector();
    // Set<String> hs = new HashSet<String>();
    // String str ="";
    // logger.debug("enter getDW_DFORM, word: "+word.getStrKana()+" lesson: "+lesson);
    //
    // Vector vtform = getDW_TFORM(db, word, strTransRule, lesson, call_form);
    // if(vtform!=null && vtform.size()>0){
    // for (int i = 0; i < vtform.size(); i++) {
    // JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct)
    // vtform.elementAt(i);
    // str = node.getStrKana();
    // if(!hs.contains(str)){
    // hs.add(str);
    // vResult.addElement(node);
    // }
    //
    // }
    //
    // Vector vform = getDW_FORM(db, word, strForm, strTransRule, lesson);
    // if(vform!=null && vform.size()>0){
    // for (int j = 0; j < vform.size(); j++) {
    // JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct)
    // vform.elementAt(j);
    // str = node.getStrKana();
    // if(!hs.contains(str)){ //should be removed from the result vector;
    // hs.add(str);
    // vResult.addElement(node);
    // }
    //
    // }
    // }
    //
    // }
    //
    // return vResult;
    //
    //
    // }
    //
    //
    //
    //
    // Vector getDW_FORM(CALL_database db,JCALL_Word word, String strForm,
    // String strTransRule,int lesson){
    //
    // Vector vResult = new Vector();
    // String error = "DW_DFORM";
    // String strClass = "GRAMMAR"; //"CONCEPT";
    // logger.debug("enter getDW_FORM, word: "+word.getStrKana()+" lesson: "+lesson);
    // Vector vDw = getDW(db,word,lesson);
    // if(vDw!=null && vDw.size()>0){
    // for (int i = 0; i < vDw.size(); i++) {
    // JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct)
    // vDw.elementAt(i);
    // JCALL_Word call_word = JCALL_Lexicon.getWordFmID(node.getId());
    // if(call_word!=null){
    // general DW_DFORM generation
    // Vector v = getDFORM(db, call_word, strForm, strTransRule);
    // for (int j = 0; j < v.size(); j++) {
    // CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct)v.get(j);
    // String str = wordwithForm.getSurfaceFormKana().trim();
    // JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
    // pnode.setBAccept(false);
    // pnode.setStrError(error);
    // pnode.setStrClass(strClass);
    // pnode.setBValid(true);
    // pnode.setStrKana(wordwithForm.getSurfaceFormKana());
    // pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
    // vResult.addElement(pnode);
    // logger.debug(pnode.toString());
    // }
    // }
    // }//end for
    //
    // }
    // return vResult;
    //
    //
    // }
    //
    //
    //
    //
    // Vector getDW_TFORM(CALL_database db,JCALL_Word word, String
    // strTransRule,int lesson,CALL_Form call_form){
    //
    // Vector vResult = new Vector();
    // Vector vec;
    // String error = "DW_TFORM";
    // String strClass = "GRAMMAR"; //"CONCEPT";
    // logger.debug("enter getDW_TFORM, word: "+word.getStrKana()+" lesson: "+lesson);
    // Vector vDw = getDW(db,word,lesson);
    // if(vDw!=null && vDw.size()>0){
    // for (int i = 0; i < vDw.size(); i++) {
    // JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct)
    // vDw.elementAt(i);
    // JCALL_Word call_word = JCALL_Lexicon.getWordFmID(node.getId());
    // if(call_word!=null){
    // get the correct form
    // CALL_verbRulesStruct vRules = db.vrules;
    // vec = vRules.getVerbForms(call_word, strTransRule, call_form.getSign(),
    // call_form.getTense(), call_form.getStyle(), call_form.getType());
    // if(vec!=null && vec.size()==1){
    // CALL_wordWithFormStruct wordwithForm =
    // (CALL_wordWithFormStruct)vec.get(0);
    // String str = wordwithForm.getSurfaceFormKana().trim();
    // JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
    // if(node.bAccept){ //a synonym word
    // pnode.setBAccept(true);
    // pnode.setStrError("correct");
    // }else{
    // pnode.setBAccept(false);
    // pnode.setStrError(error);
    // }
    // pnode.setStrClass(strClass);
    // pnode.setBValid(true);
    // pnode.setStrKana(wordwithForm.getSurfaceFormKana());
    // pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
    // vResult.addElement(pnode);
    // logger.debug(pnode.toString());
    // }
    // }
    //
    // }//end for
    //
    // }
    // return vResult;
    //
    //
    // }
    //
    //
    //
    //
    //
    //
    // Vector getWIF(CALL_database db,JCALL_Word word, String strForm, String
    // strTransRule){
    //
    // Vector vResult = new Vector();
    //
    // CALL_formStruct form=null;
    // form = new CALL_formStruct();
    // form.setFromString(strForm);
    // CALL_Form call_form = new CALL_Form(form);
    //
    //
    //
    // if(word.getIntType()== JCALL_Lexicon.LEX_TYPE_VERB){
    //
    // JCALL_VerbErrorRules verules = db.verules;
    //
    // vResult = verules.getVerbErrorForms(word, strTransRule,
    // call_form.getSign(), call_form.getTense(), call_form.getStyle(),
    // call_form.getType());
    //
    //
    // }
    // else {
    //
    // logger.error(word.getStrKanji() + "is not a verb or adjective");
    //
    // }
    //
    // return vResult;
    //
    //
    // }
    //
    //
    //
    // Vector getWIF(CALL_database db,JCALL_Word word, String strForm, int
    // lesson){
    // logger.debug("enter getWIF");
    // Vector vResult = new Vector();
    //
    // JCALL_PredictionDataStruct pdmVerb=null;
    //
    // if(!(word.getIntType()== JCALL_Lexicon.LEX_TYPE_VERB)){
    // return null;
    // }
    //
    // JCALL_PredictionDatasStruct eps = db.eps;
    //
    // for (int i = 0; i < eps.vecPDM.size(); i++) {
    // JCALL_PredictionDataStruct pdm = eps.vecPDM.get(i);
    // if(pdm.strREF!=null && pdm.strREF.length()>0 && pdm.isHasLesson(lesson)){
    // pdmVerb = pdm;
    // logger.debug("find verpdm, strREF: "+pdm.strREF+ " lesson: "+ lesson);
    // break;
    // }
    // }
    // if(pdmVerb!=null){
    // String strRef = pdmVerb.getStrREF();//TETA,SE,Masyou
    // Vector vec = pdmVerb.getVecVerbInvalidFrom();
    // if(vec!=null && vec.size()>0 && strRef!=null){
    // deal with different kinds of errors of these
    // for (int i = 0; i < vec.size(); i++) {
    // String strRuleName = (String) vec.get(i);
    // strRuleName = strRef +"_" + strRuleName;
    // Vector vTemp = getWIF(db, word, strForm, strRuleName);
    // if(vTemp!= null && vTemp.size()>0){
    // for (int j = 0; j < vTemp.size(); j++) {
    // vResult.addElement((CALL_wordWithFormStruct)vTemp.get(j));
    // }
    //
    // }
    //
    // }
    // }
    //
    // }else{
    // logger.debug("no error of WIF");
    // return null;
    //
    // }
    //
    //
    // return vResult;
    // }
    //
    //
    //
    // Vector getTW_WIF(CALL_database db,JCALL_Word word, String strForm, int
    // lesson){
    // logger.debug("enter getTW_WIF");
    // Vector<JCALL_NetworkSubNodeStruct> vecResult = new
    // Vector<JCALL_NetworkSubNodeStruct>();
    // Set<String> hs = new HashSet<String>();
    // String str;
    // Vector vec = getWIF(db,word,strForm,lesson); //included the answer;
    // String error = "TW_WIF";
    // String strClass = "GRAMMAR";
    // if(vec!=null&& vec.size()>0){
    // for (int i = 0; i < vec.size(); i++) {
    // CALL_wordWithFormStruct wordwithForm =
    // (CALL_wordWithFormStruct)vec.get(i);
    // str = wordwithForm.getSurfaceFormKana().trim();
    // if(!hs.contains(str)){
    // hs.add(str);
    // JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
    // pnode.setBAccept(false);
    // pnode.setStrError(error);
    // pnode.setStrClass(strClass);
    // pnode.setBValid(false);
    // pnode.setStrKana(wordwithForm.getSurfaceFormKana());
    // pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
    // vecResult.addElement(pnode);
    // logger.debug(pnode.toString());
    // }
    //
    // }
    // }
    //
    // return vecResult;
    //
    // }
    //
    //

    public void getTW_NTD(CALL_database db, JCALL_Word word, int lesson) {

    }

    public Vector getSetenceFromTable(String tablename) {

        String strOdbc = FindConfig.getConfig().findStr(NewLogParser.OdbcName);
        dm.connectToAccess(strOdbc);

        Vector<SentenceDataMeta> v = new Vector<SentenceDataMeta>();
        // read data into WordErrDataMeta one by one, change attribute on the
        // basis of its type
        try {

            SentenceDataMeta objMeta;
            ResultSet tmpRs = dm.searchTable(tablename, "");
            while (tmpRs.next()) {
                // objEDMeta.intID = tmpRs.getInt("ID");
                int iD = tmpRs.getInt("ID");
                int studentID = tmpRs.getInt("StudentID");
                String lesson = tmpRs.getString("Lesson");
                String strQuestion = tmpRs.getString("Question");
                String strTargetWord = tmpRs.getString("strTargetAnswer");
                String strRecognitionWord = tmpRs.getString("strSpeechAnswer");
                String strListenWord = tmpRs.getString("strListeningAnswer");
                // System.out.println("Question: "+ strQuestion);
                objMeta = new SentenceDataMeta(iD, studentID, lesson, strQuestion, strTargetWord, strListenWord,
                        strRecognitionWord);

                // logger.debug("Draw sentence: " + strTargetWord);
                v.addElement(objMeta);
            }// end while

        } catch (Exception e) {
            System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
            e.printStackTrace();
        }
        dm.releaseConn();

        return v;
    }

    /*
     * method is 0,1,2,3,4 4 is proposed method
     */

    public void sentenceGrammar4Method(String tablename, int method) throws NumberFormatException, SQLException,
            IOException {

        Vector v = getSetenceFromTable(tablename);
        for (int i = 0; i < v.size(); i++) {
            SentenceDataMeta objMeta = (SentenceDataMeta) v.elementAt(i);
            String targetSen = objMeta.getStrTargetSentence();
            int intLesson = Integer.parseInt(objMeta.strLesson);
            // sentenceGrammar4Method(targetSen,objMeta.intID,intLesson,method);

        }

    }

    /**
     * @param targetSentence
     *            : all words in the surface form
     * @param intID
     * @param lesson
     * @param method
     * @return
     */
    // public Vector sentenceGrammar4DiffMethod(String targetSentence, int
    // intID,int lesson,int method) {
    //
    // Vector wordList;
    // String sentenceStr;
    // CALL_sentenceWordStruct tempSentenceWord;
    // StringTokenizer st;
    // String strWord;
    // print the top sentence
    // sentenceStr = targetSentence;
    // logger.debug("Sentence: "+ sentenceStr);
    // it is the top sentence, using same function with the hintsStruct
    // if(sentenceStr==null || sentenceStr.length()==0){
    // return null;
    // }
    // get word list
    // st= new StringTokenizer(sentenceStr);
    // while(st.hasMoreTokens()){ // handle word
    // strWord = st.nextToken();
    // strWord = strWord.trim();
    // wordList.addElement(strWord);
    // }
    //
    // wordList = sentence.getSentenceWords();
    //
    // if(wordList != null)
    // {
    // network = new GrammarNetwork();
    // logger.debug("target sentence, word size is: "+ wordList.size());
    // for(int i=0; i < wordList.size(); i++)
    // {
    // tempSentenceWord = (CALL_sentenceWordStruct) wordList.elementAt(i);
    // strWord = (String) wordList.elementAt(i);
    // gw = new GrammarWord();
    //
    //
    // CALL_wordStruct tempWord;
    // if(strWord != null)
    // {
    //
    // JCALL_Word call_word = JCALL_Lexicon.getWordFmStr(strWord);
    // if(call_word==null){
    // logger.error("Not Find string in lexicon: "+ strWord);
    //
    //
    // }else{
    //
    //
    //
    // }
    //
    //
    // int intType = wordstruct.getType();
    // logger.debug("word "+i+", kanji is "+wordKanji);
    //
    // JCALL_Word call_word = JCALL_Lexicon.getWordFmKanji(wordKanji, intType);
    //
    // if(call_word==null){
    // call_word = JCALL_Lexicon.getWordFmKanji(wordKanji);
    // }
    // if(call_word!=null){
    // logger.debug("find target word in the lexicon: "+ wordKanji);
    //
    // gw.setCall_word(call_word);
    //
    // String strGrammarRule = tempSentenceWord.getGrammarRule();
    // String strComponentName = tempSentenceWord.getComponentName();
    // String strComponentLabel;
    // if(strComponentName!=null && strComponentName.length()>0){
    // check if this ComponentName has whitespace
    // StringTokenizer st = new StringTokenizer(strComponentName);
    // String str1 = "";
    // String str2 ="";
    // if(st.hasMoreElements()){
    // str1 = (String) st.nextElement();
    // }else{
    // logger.error("strComponentName is not null,but return no string");
    // }
    // if(st.hasMoreElements()){
    // str2 = (String) st.nextElement();
    // if(str1.length()>=str2.length()){
    // strComponentLabel = str1;
    // }else{
    // strComponentLabel = str2;
    // }
    // }else{
    // strComponentLabel= strComponentName;
    // }
    //
    // logger.debug("strComponentName: "+strComponentName);
    // if(strComponentName.equalsIgnoreCase("Particle") && pregw!=null){
    // gw.setStrLabel(pregw.getStrLabel().trim()+"_"+strComponentLabel.trim());
    // }else{
    // gw.setStrLabel(strComponentLabel);
    // }
    //
    // }else{
    // if(strGrammarRule!=null && strGrammarRule.length()>0){
    // gw.setStrLabel(strGrammarRule);
    // }else{
    // gw.setStrLabel("NoLabel");
    // logger.error("error, along with no grammar rule ");
    // }
    // }
    //
    //
    // getSubstitution(gw,lesson);
    //
    //
    // }else {// end of if lwm
    // logger.error(" not found the word in lexicon in CALL_SentenceGrammar");
    // }
    // }
    //
    // /*
    // * end of coping with the target word
    // * Then for each word, add all substitution words (on the basis of
    // errorType, create errors or extract from prediction file )
    // */
    //
    //
    // pregw =gw;
    // network.addGW(gw);
    //
    // }//end of for(wordlist)
    // }
    // file
    // try {
    // write to file
    // writeVocaFile(lesson,wordList.size(),network.vGW,sentenceStr);
    // writeGrammarFile(lesson,wordList.size(),network.vGW,sentenceStr);
    //
    // } catch (IOException e) {
    // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // invoke the mkdfa
    // MakeDFA dfa = new MakeDFA();
    // dfa.getDFAFile(pathfile);
    // dfa.getDFAFile(CONTEXTFILENAME);
    // sentence.setVGWNetwork(network);
    //
    // return network.vGW;
    // }
    //
    //
    //
    //

    // sentenceGrammar4GeneralMethod

    public static void main(String[] args) throws IOException, NumberFormatException, SQLException {
        JCALL_NetWorkGeneration sg = new JCALL_NetWorkGeneration();

        // String targetSentence =
        // "Ã£ï¿½â€œÃ£â€šÅ’Ã£ï¿½Â¯Ã£ï¿½Â»Ã£â€šâ€œÃ£ï¿½Â§Ã£ï¿½â„¢";
        // sg.sentenceGrammar4GeneralMethod(targetSentence,34,3);
        // MakeDFA dfa = new MakeDFA();
        // dfa.getDFAFile(pathfile);
        // String name = new String("NO5");
        // dfa.getDFAFile(name);

        // sg.sentenceGrammar("SpeechSentencesStageTwo");

        CALL_sentenceStruct sentence = new CALL_sentenceStruct();

        sg.sentenceGrammar(sentence, 1);

        System.out.println("finished");
    }

}
