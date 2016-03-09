/**
 * Created on 2007/06/12
 *
 * @author wang Copyrights @kawahara lab
 */
package jcall.db;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.CALL_Form;
import jcall.CALL_adjectiveRulesStruct;
import jcall.CALL_database;
import jcall.CALL_formStruct;
import jcall.CALL_io;
import jcall.CALL_sentenceWordStruct;
import jcall.CALL_verbRulesStruct;
import jcall.CALL_wordWithFormStruct;
import jcall.recognition.languagemodel.FormatVerb;
import jcall.recognition.util.CharacterUtil;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;

import org.apache.log4j.Logger;

public class JCALL_PredictionProcess {

    static Logger logger = Logger.getLogger(JCALL_PredictionProcess.class.getName());

    // JCALL_PredictionParser epp ;
    // static JCALL_PredictionDatasStruct eps;
    // static JCALL_VerbErrorRules verules ;

    // static Lexicon l;
    static int intTotal;

    public static final String[] VOICEDROW = { "か", "さ", "た", "は", "ぱ" };
    static final String DOUBLECONSONENT = "っ";
    static final String TA = "た";
    static final String DA = "だ";

    public JCALL_PredictionProcess() {
        init();
    }

    private void init() {

        // epp =new JCALL_PredictionParser();
        // eps = JCALL_PredictionDatasStruct.getInstance();

        // verules = new JCALL_VerbErrorRules();
        // verules.loadRules(CALL_database.VerbErrorRuleFile);

    }

    // ///////////////////////////////////
    // /
    // / for the error prediction part for sentence grammar generation
    // /
    // //////////////////////////////////////

    public void getSubstitution(JCALL_NetworkNodeStruct gw, int lesson) {

        // logger.debug("enter getSubstitution");

        JCALL_Word call_word = gw.call_word;
        CALL_sentenceWordStruct tempSentenceWord = gw.sword;

        JCALL_PredictionProcess pp;
        try {
            pp = new JCALL_PredictionProcess();

            // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
            // String strForm = tempSentenceWord.getFormString();
            // String strTransRule = tempSentenceWord.getTransformationRule();

            if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB
                    || call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE
                    || call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJVERB) {

                getVerbSubstitution(gw, lesson);

            } else if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_NUMQUANT || tempSentenceWord.isUseCounterSettings()) {
                // get the predicted words, then add to the network;
                // || tempSentenceWord.isUseCounterSettings()
                // logger.debug("Counter prediction");
                String strCounter = tempSentenceWord.getTopWordString(CALL_io.kanji);

                if (strCounter != null && strCounter.length() > 0) {

                    // logger.debug("target counter string: "+ strCounter);
                    JCALL_Word wordTemp = JCALL_Lexicon.getInstance().getWordFmKanji(strCounter, JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                    if (wordTemp != null) {
                        // logger.debug("Counter kanji: "+
                        // wordTemp.getStrKanji());
                        // Now judge if its a quantifier+number;
                        // String snum = wordTemp.getStrNumber();
                        String sQuantifier = wordTemp.getStrQuantifier();
                        if (sQuantifier == null || sQuantifier.trim().length() == 0) {
                            // logger.debug("Find a wrong counter; Search again;");
                            String strCounterkana = tempSentenceWord.getTopWordString(CALL_io.kanji);
                            JCALL_Word wordTemp2 = JCALL_Lexicon.getInstance().getWordFmStr(strCounterkana,
                                    JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                            if (wordTemp2 != null && wordTemp2.strKana != null) {
                                wordTemp = wordTemp2;
                            }
                        }
                        Vector vec = pp.getNQErrors(wordTemp, lesson);
                        if (vec != null && vec.size() > 0) {
                            for (int i = 0; i < vec.size(); i++) {
                                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                                gw.addPNode(pnode);
                            }
                        }

                    }
                }

            } else if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

                // get the predicted words, then add to the network;
                getParticleSubstitution(gw, lesson);

            } else {// the only possible is noun
                // get the predicted words, then add to the network;
                Vector vec = pp.getNounSubstitution(call_word, lesson);
                if (vec != null && vec.size() > 0) {
                    for (int i = 0; i < vec.size(); i++) {
                        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                        gw.addPNode(pnode);
                    }
                }

            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void getVerbSubstitution(JCALL_NetworkNodeStruct gw, int lesson) {
        // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // JCALL_Word lwm, CALL_sentenceWordStruct tempSentenceWord

        // Vector<PNode> vecResult = new Vector();

        // to prevent repetiveness
        Set<String> hs = new HashSet<String>();
        Vector vec;
        String str;
        int intTotal = 0;

        JCALL_Word word = gw.call_word;
        CALL_sentenceWordStruct tempSentenceWord = gw.sword;
        CALL_database db = tempSentenceWord.getDb();

        // logger.debug("enter getVerbSubstitution, word: "+
        // word.getStrKanji());

        String strForm = tempSentenceWord.getFullFormString();
        String strTransRule = tempSentenceWord.getTransformationRule();

        if (strForm != null && strForm.length() > 0) {

            logger.info("sentence word FullForm: " + strForm + " transformation rule: " + strTransRule);

            // (int sign, int tense, int style, int type)
            CALL_Form call_form = new CALL_Form(tempSentenceWord.getSign(), tempSentenceWord.getTense(),
                    tempSentenceWord.getPoliteness(), tempSentenceWord.getQuestion());

            String answer = tempSentenceWord.getTopWordString(CALL_io.kana);

            logger.info("Top Word String: " + answer);

            vec = getTW_DFORM(db, word, strForm, strTransRule, answer); // including
                                                                        // the
                                                                        // answer
                                                                        // and
                                                                        // marked
                                                                        // correct
                                                                        // in
                                                                        // error
                                                                        // type

            if (vec != null && vec.size() > 0) {
                // int errsize =0;
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // errsize++;
                    }
                }
                intTotal += vec.size();
                // logger.debug("TW_DFORM(included correct one):"+vec.size());
            }

            vec = getDW_TFORM(db, word, strTransRule, lesson, call_form); // including
                                                                          // the
                                                                          // answer
                                                                          // and
                                                                          // marked
                                                                          // correct
                                                                          // in
                                                                          // error
                                                                          // type

            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("DW_TFORM: "+vec.size());
            }

            vec = getDW_DFORM(db, word, strForm, strTransRule, lesson, call_form); // including
                                                                                   // the
                                                                                   // answer
                                                                                   // and
                                                                                   // marked
                                                                                   // correct
                                                                                   // in
                                                                                   // error
                                                                                   // type
            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("DW_DFORM: "+vec.size());
            }

            vec = getTW_WIF(db, word, strForm, lesson); // including the answer
                                                        // and
                                                        // marked correct in
                                                        // error
                                                        // type
            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("TW_WIF: "+vec.size());
            }

        }// end if

    }

    public void getParticleSubstitution(JCALL_NetworkNodeStruct gw, int lesson) {
        // to prevent repetiveness
        Set<String> hs = new HashSet<String>();
        Vector vec;
        String str;
        int intTotal = 0;

        // logger.debug("enter getParticleSubstitution, lesson: "+ lesson);

        JCALL_Word word = gw.call_word;

        vec = getDW(word, lesson);

        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                str = pnode.getStrKana();
                if (!hs.contains(str)) {
                    hs.add(str);
                    gw.addPNode(pnode);
                    // vecResult.addElement(pnode);
                }
            }
            intTotal += vec.size();
            // logger.debug("DW: "+vec.size());
        }

    }

    Vector getDFORM(CALL_database db, JCALL_Word word, String strForm, String strTransRule) {

        // logger.debug("enter getDFORM, DFORM: "+ strForm);

        Vector vResult = new Vector();
        CALL_formStruct form = null;
        form = new CALL_formStruct();
        form.setFromString(strForm);
        CALL_Form call_form = new CALL_Form(form);

        if (word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB) {

            CALL_verbRulesStruct vRules = db.vrules;
            vResult = vRules.getVerbForms(word, strTransRule, call_form.getSign(), call_form.getTense(),
                    call_form.getStyle(), call_form.getType());
            // logger.debug("DForm size: "+ vResult.size());

        } else if (word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE
                || word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJVERB) {

            CALL_adjectiveRulesStruct adjRules = db.arules;
            // //logger.debug("word kanji: "+ word.getStrKanji());
            // int intType = word.getIntType();
            // //logger.debug("word Type: "+
            // JCALL_Lexicon.typeInt2Name(intType));
            vResult = adjRules.getAdjectiveForms(word, strTransRule, call_form.getSign(), call_form.getTense(),
                    call_form.getStyle(), call_form.getType());

        } else {

            logger.error(word.getStrKanji() + "is not a verb or adjective");

        }

        return vResult;

    }

    Vector getDW(JCALL_Word word, int lesson) {

        // logger.debug("Enter getDW, TW: "+ word.getStrKanji()
        // +" Type: "+word.getIntType()+"  lesson: "+lesson);

        Set<String> hs = new HashSet<String>();
        JCALL_PredictionDataStruct pdm = null;
        JCALL_NetworkSubNodeStruct node;
        String sLesson = new String("" + lesson);

        // JCALL_PredictionDatasStruct eps = db.eps;
        // if(eps==null){
        // eps = JCALL_PredictionDatasStruct.getInstance();
        // }

        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();
        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category

                // //logger.debug("CLASS WORD, pdm["+i+"] kanji: "+pdm.strKanji
                // +" type: "+
                // pdm.getIntType()+" Lessons: "+pdm.getLessonlist());

                if (pdm.isHasLesson(sLesson) && pdm.getIntType() == word.getIntType() && pdm.compareTo(word) == 0) {// same
                                                                                                                    // lesson

                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    node.vecSpecific = pdm.vecSpecific;
                    String s = pdm.strSubword;
                    if (s != null && s.length() > 0) {
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmStr(s, pdm.getIntType());
                            if (call_word != null) {
                                node.setStrKana(call_word.getStrKana());
                                node.setStrKanji(call_word.getStrKanji());
                                node.setId(call_word.getId());
                                if (!hs.contains(call_word.getStrKana())) {
                                    hs.add(call_word.getStrKana());
                                    vResult.addElement(node);
                                }
                                // logger.debug("get one subsitution word: "+node.getStrKana());
                            } else {
                                // logger.error("the subsitution word could not be found in lexicon: "+
                                // s);
                            }
                        }

                    }

                }
            } else if (pdm.strClass.equalsIgnoreCase("group")) {

                // //logger.debug("CLASS GROUP, pdm["+i+"] kanji: "+pdm.strKanji
                // +" type: "+
                // pdm.getIntType()+" Lessons: "+pdm.getLessonlist());
                String head;
                String tail;
                if (pdm.isHasLesson(sLesson) && pdm.getIntType() == word.getIntType()) {// same
                                                                                        // lesson
                    // include the category
                    if (pdm.strCategory != null && pdm.strCategory.length() > 0) {

                        if (word.IsHasCategory(pdm.strCategory)) {

                            String rule = pdm.strRule;
                            if (rule != null && rule.length() > 0) {
                                StringTokenizer st = new StringTokenizer(rule.trim());
                                Vector<String> v = new Vector<String>();
                                while (st.hasMoreElements()) {
                                    v.addElement(st.nextToken());
                                }

                                if (v.size() == 3) {
                                    head = ((String) v.elementAt(0)).trim();
                                    tail = ((String) v.elementAt(2)).trim();
                                    int intIndex = head.indexOf("*");
                                    if (intIndex == 0) { // //omit suffix or
                                                         // prefix;
                                        StringTokenizer stTail = new StringTokenizer(tail, ",");
                                        while (stTail.hasMoreElements()) {
                                            String strTemp = stTail.nextToken().trim();
                                            int intIndex2 = strTemp.indexOf("*");
                                            if (intIndex2 == 0) {// suffix
                                                String suffix = strTemp.substring(1);
                                                node = new JCALL_NetworkSubNodeStruct();
                                                node.bAccept = pdm.bAccept;
                                                node.bValid = pdm.bValid;
                                                node.strClass = pdm.strClass;
                                                node.strError = pdm.strError;
                                                node.vecSpecific = pdm.vecSpecific;
                                                node.setStrKana(word.getStrKana() + suffix);
                                                node.setStrKanji(word.getStrKanji() + suffix);
                                                // node.setId(word.getId());

                                                if (!hs.contains(node.getStrKana())) {
                                                    hs.add(node.getStrKana());
                                                    vResult.addElement(node);
                                                }

                                            } else {
                                                String prefix = strTemp.substring(0, intIndex2 - 1);
                                                node = new JCALL_NetworkSubNodeStruct();
                                                node.bAccept = pdm.bAccept;
                                                node.bValid = pdm.bValid;
                                                node.strClass = pdm.strClass;
                                                node.strError = pdm.strError;
                                                node.vecSpecific = pdm.vecSpecific;
                                                node.setStrKana(prefix + word.getStrKana());
                                                node.setStrKanji(prefix + word.getStrKanji());

                                                if (!hs.contains(node.getStrKana())) {
                                                    hs.add(node.getStrKana());
                                                    vResult.addElement(node);
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                        }

                    }

                }
            }
        }
        // synonym
        String synonumW = word.getDSynonymValue();

        if (synonumW != null && synonumW.length() > 0) {

            StringTokenizer stSynonym = new StringTokenizer(synonumW, ",");
            while (stSynonym.hasMoreElements()) {
                String tempString = (String) stSynonym.nextElement();
                // find its kana
                JCALL_Word synonumword = JCALL_Lexicon.getInstance().getWordFmStr(tempString, word.getIntType());
                if (synonumword != null) {
                    // String str = synonumword.getStrKana();
                    if (!hs.contains(synonumword.getStrKana())) {
                        hs.add(synonumword.getStrKana());
                        node = new JCALL_NetworkSubNodeStruct();
                        node.bAccept = true;
                        node.bValid = true;
                        node.strError = "correct";
                        node.strClass = "lexical";
                        node.setStrKana(synonumword.getStrKana());
                        node.setStrKanji(synonumword.getStrKanji());
                        node.setId(synonumword.getId());
                        vResult.addElement(node);
                    }
                }
            }
        }

        return vResult;

    }

    /*
     * same with getDW (CALL_database db,JCALL_Word word,int lesson ), but not
     * parameter "db";
     */

    // Do not care the lessons, any lessons are ok.
    public Vector getAcceptedDWOnlyFmEP(JCALL_Word word) {

        Set<String> hs = new HashSet<String>();
        JCALL_PredictionDataStruct pdm = null;
        JCALL_NetworkSubNodeStruct node;
        CALL_wordWithFormStruct newWord;
        // String sLesson = new String("" + lesson);
        // JCALL_PredictionDatasStruct eps = db.eps;
        Vector vResult = new Vector();
        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category
                // && pdm.isHasLesson("-1")
                if (pdm.getIntType() == word.getIntType() && pdm.bAccept == true && pdm.compareTo(word) == 0) {// be
                                                                                                               // accepted
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    node.vecSpecific = pdm.vecSpecific;
                    String s = pdm.strSubword;
                    if (s != null && s.length() > 0) {
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmStr(s, pdm.getIntType());
                            if (call_word != null) {
                                node.setStrKana(call_word.getStrKana());
                                node.setStrKanji(call_word.getStrKanji());
                                node.setId(call_word.getId());
                                if (!hs.contains(call_word.getStrKana())) {
                                    hs.add(call_word.getStrKana());
                                    vResult.addElement(node);
                                }
                                // logger.debug("get one subsitution word : "+pdm.getStrWord());
                            } else {
                                // logger.error("the subsitution word could not be found in lexicon: "+
                                // s);
                            }

                        }

                    }

                }
            }
        }
        return vResult;

    }

    Vector getTW_DFORM(CALL_database db, JCALL_Word word, String strForm, String strTransRule, String ansKana) {
        // logger.debug("enter getTW_DFORM, TW: "+ word.getStrKanji()
        // +"and TW_TForm is: "+ ansKana);

        Vector vecResult = new Vector();
        String str;
        Vector vec = getDFORM(db, word, strForm, strTransRule); // included the
                                                                // answer;
        String error = "TW_DFORM";
        String strClass = "GRAMMAR";
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) vec.get(i);

                str = wordwithForm.getSurfaceFormKana().trim();
                // logger.debug("DFORM: "+str);
                JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                if (str.equals(ansKana)) {
                    pnode.setBAccept(true);
                    pnode.setStrError("correct");
                } else {
                    pnode.setBAccept(false);
                    pnode.setStrError(error);
                    pnode.setStrClass(strClass);
                }
                pnode.setBValid(true);
                pnode.setStrKana(wordwithForm.getSurfaceFormKana());
                pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
                vecResult.addElement(pnode);
                // logger.debug(pnode.toString());
            }
        }

        return vecResult;

    }

    Vector getDW_DFORM(CALL_database db, JCALL_Word word, String strForm, String strTransRule, int lesson,
            CALL_Form call_form) {

        Vector vResult = new Vector();
        Set<String> hs = new HashSet<String>();
        String str = "";
        // logger.debug("enter getDW_DFORM, TW: "+word.getStrKana()+" Full Form: "+
        // strForm +" lesson: "+lesson);

        Vector vtform = getDW_TFORM(db, word, strTransRule, lesson, call_form);
        if (vtform != null && vtform.size() > 0) {
            for (int i = 0; i < vtform.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vtform.elementAt(i);
                str = node.getStrKana();
                if (!hs.contains(str)) {
                    hs.add(str);
                    // vResult.addElement(node);
                }
            }

            Vector vform = getDW_FORM(db, word, strForm, strTransRule, lesson);
            if (vform != null && vform.size() > 0) {
                for (int j = 0; j < vform.size(); j++) {
                    JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vform.elementAt(j);
                    str = node.getStrKana();
                    if (!hs.contains(str)) { // should be removed from the
                                             // result vector;
                        // hs.add(str);
                        vResult.addElement(node);
                    }

                }
            }

        }

        return vResult;

    }

    Vector getDW_FORM(CALL_database db, JCALL_Word word, String strForm, String strTransRule, int lesson) {

        // logger.debug("enter getDW_FORM, TW: "+word.getStrKana()+" lesson: "+lesson);

        Vector vResult = new Vector();
        String error = "DW_DFORM";
        String strClass = "GRAMMAR"; // "CONCEPT";

        Vector vDw = getDW(word, lesson);
        if (vDw != null && vDw.size() > 0) {
            for (int i = 0; i < vDw.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vDw.elementAt(i);
                JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmID(node.getId());

                if (call_word == null) {
                    // logger.debug("Find Dw["+i+"]:"+node.getStrKanji()+" using Kanji and Type");
                    call_word = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), word.intType);
                }

                if (call_word == null) {
                    // logger.debug("Find Dw["+i+"]:"+node.getStrKanji()+" using Kanji");
                    call_word = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji());
                }

                if (call_word != null) {
                    // logger.debug("Dw["+i+"]:"+node.getStrKanji()+" is found in the lexicon");
                    // general DW_DFORM generation

                    Vector v = getDFORM(db, call_word, strForm, strTransRule);
                    for (int j = 0; j < v.size(); j++) {
                        CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) v.get(j);
                        String str = wordwithForm.getSurfaceFormKana().trim();
                        JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                        pnode.setBAccept(false);
                        pnode.setStrError(error);
                        pnode.setStrClass(strClass);
                        pnode.setBValid(true);
                        pnode.setStrKana(wordwithForm.getSurfaceFormKana());
                        pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
                        vResult.addElement(pnode);
                        // logger.debug(pnode.toString());
                    }
                } else {
                    logger.error("Dw[" + i + "]:" + node.getStrKanji() + " is NO FOUND in the lexicon");
                }
            }// end for

        }
        return vResult;

    }

    Vector getDW_TFORM(CALL_database db, JCALL_Word word, String strTransRule, int lesson, CALL_Form call_form) {

        // logger.debug("enter getDW_TFORM, TW: "+word.getStrKana()+" lesson: "+lesson);

        Vector vResult = new Vector();
        Vector vec;
        String error = "DW_TFORM";
        String strClass = "GRAMMAR"; // "CONCEPT";

        Vector vDw = getDW(word, lesson);
        if (vDw != null && vDw.size() > 0) {
            // logger.debug("ALL vDw size: "+ vDw.size());
            for (int i = 0; i < vDw.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vDw.elementAt(i);
                JCALL_Word call_word = JCALL_Lexicon.getInstance().getWordFmID(node.getId());
                if (call_word == null) {
                    // logger.debug("Find Dw["+i+"]:"+node.getStrKanji()+" using Kanji and Type");
                    call_word = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), word.intType);
                }

                if (call_word == null) {
                    // logger.debug("Find Dw["+i+"]:"+node.getStrKanji()+" using Kanji");
                    call_word = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji());
                }

                if (call_word != null) {

                    // logger.debug("Dw["+i+"]:"+node.getStrKanji()+" is found in the lexicon");

                    // get the correct form
                    CALL_verbRulesStruct vRules = db.vrules;
                    vec = vRules.getVerbForms(call_word, strTransRule, call_form.getSign(), call_form.getTense(),
                            call_form.getStyle(), call_form.getType());

                    if (vec != null && vec.size() == 1) {
                        CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) vec.get(0);
                        String str = wordwithForm.getSurfaceFormKana().trim();
                        JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                        if (node.bAccept) { // a synonym word
                            pnode.setBAccept(true);
                            pnode.setStrError("correct");
                        } else {
                            pnode.setBAccept(false);
                            pnode.setStrError(error);
                        }
                        pnode.setStrClass(strClass);
                        pnode.setBValid(true);
                        pnode.setStrKana(wordwithForm.getSurfaceFormKana());
                        pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
                        vResult.addElement(pnode);
                        // logger.debug(pnode.toString());
                    }
                } else {
                    logger.error("Dw[" + i + "]:" + node.getStrKana() + "is not found in Lexicon");
                }
            }// end for

        }
        return vResult;
    }

    Vector getWIF(CALL_database db, JCALL_Word word, String strForm, String strTransRule) {

        Vector vResult = new Vector();

        CALL_formStruct form = null;
        form = new CALL_formStruct();
        form.setFromString(strForm);
        CALL_Form call_form = new CALL_Form(form);

        if (word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB) {

            JCALL_VerbErrorRules verules = db.verules;

            vResult = verules.getVerbErrorForms(word, strTransRule, call_form.getSign(), call_form.getTense(),
                    call_form.getStyle(), call_form.getType());

        } else {

            logger.error(word.getStrKanji() + "is not a verb or adjective");

        }

        return vResult;

    }

    Vector getWIF(CALL_database db, JCALL_Word word, String strForm, int lesson) {
        // logger.debug("enter getWIF");
        Vector vResult = new Vector();

        JCALL_PredictionDataStruct pdmVerb = null;

        if (!(word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB)) {
            return null;
        }

        JCALL_PredictionDatasStruct eps = db.eps;
        if (eps == null) {
            eps = JCALL_PredictionDatasStruct.getInstance();

        }
        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            JCALL_PredictionDataStruct pdm = eps.getVecPDM().get(i);
            if (pdm.strREF != null && pdm.strREF.length() > 0 && pdm.isHasLesson(lesson)) {
                pdmVerb = pdm;
                // logger.debug("find verpdm, strREF: "+pdm.strREF+ " lesson: "+
                // lesson);
                break;
            }
        }
        if (pdmVerb != null) {
            String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
            Vector vec = pdmVerb.getVecVerbInvalidFrom();
            if (vec != null && vec.size() > 0 && strRef != null) {
                // deal with different kinds of errors of these
                for (int i = 0; i < vec.size(); i++) {
                    String strRuleName = (String) vec.get(i);
                    strRuleName = strRef + "_" + strRuleName;
                    Vector vTemp = getWIF(db, word, strForm, strRuleName);
                    if (vTemp != null && vTemp.size() > 0) {
                        for (int j = 0; j < vTemp.size(); j++) {
                            vResult.addElement((CALL_wordWithFormStruct) vTemp.get(j));
                        }

                    }

                }
            }

        } else {
            // logger.debug("no error of WIF");
            return null;

        }

        return vResult;
    }

    Vector getTW_WIF(CALL_database db, JCALL_Word word, String strForm, int lesson) {
        // logger.debug("enter getTW_WIF");
        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector<JCALL_NetworkSubNodeStruct>();
        Set<String> hs = new HashSet<String>();
        String str;
        Vector vec = getWIF(db, word, strForm, lesson); // included the answer;
        String error = "TW_WIF";
        String strClass = "GRAMMAR";
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) vec.get(i);
                str = wordwithForm.getSurfaceFormKana().trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                    pnode.setBAccept(false);
                    pnode.setStrError(error);
                    pnode.setStrClass(strClass);
                    pnode.setBValid(false);
                    pnode.setStrKana(wordwithForm.getSurfaceFormKana());
                    pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
                    vecResult.addElement(pnode);
                    // logger.debug(pnode.toString());
                }

            }
        }

        return vecResult;

    }

    // ///////////////////////////////
    // /////
    // ////Error prediction, given the target sentence
    // ///
    // //////////////////////////////////////////////

    public Vector getNounSubstitution(JCALL_Word lwm, int lesson) {
        // logger.debug("getNounSubstitution, word ["+lwm.getStrKana()+"]");

        Vector vecResult = new Vector();
        String strWord = lwm.getStrKana();
        Set<String> hs = new HashSet<String>();
        hs.add(strWord); // first add the original kana word, for prevent the
                         // accepted prediction words is equal to strWord;

        // first add the original kana word, for prevent the accepted prediction
        // words is equal to strWord;
        JCALL_PredictionDataStruct pdm;
        String str = "";
        Vector vec;
        // INVS_PCE
        vec = getPCE(lwm, true, false, true, true, true, true);
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vec.get(i);
                str = (String) node.getStrKana();
                if (!hs.contains(str)) {
                    node.strError = "TW_PCE";
                    hs.add(str);
                    vecResult.addElement(node);
                }
            }
        } else {
            // logger.debug("Has not TW_PCE ");
        }
        // VDG
        Vector vecSubs = getDW(lwm, lesson);
        // vec =
        if (vecSubs != null && vecSubs.size() > 0) {
            for (int i = 0; i < vecSubs.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vecSubs.get(i);
                str = (String) node.getStrKana();
                if (!hs.contains(str)) {
                    hs.add(str);
                    node.strError = "DW";
                    vecResult.addElement(node);
                }
            }
        } else {
            // logger.debug("Has not DW ");
        }

        // separte the number;
        // INVDG_PCE+INVDG_WFORM
        Vector vecInv = getDW_PCE(vecSubs);
        if (vecInv != null && vecInv.size() > 0) {
            // logger.debug("DW_PCE is ["+ vecInv.size()+"]");
            for (int i = 0; i < vecInv.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vecInv.get(i);
                str = (String) node.getStrKana();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(node);
                }
            }
        }

        return vecResult;
    }

    /*
     * AddVoiced :is only checked by the first pronunciation is �� ,and confined
     * to the japanese word(no english-based words)
     */

    public static String getAddVoicedWord(JCALL_Word lwm) {
        // String strResult = null;
        // logger.debug("getAddVoicedWord4Noun");
        String strWrongWord = null;
        String strWord = lwm.getStrKana();
        String strChar = new String("" + strWord.charAt(0));
        if (strChar.equals(TA)) {
            if (lwm.getIntType() == JCALL_Lexicon.LEX_TYPE_NOUN) {
                strWrongWord = getReplaceWord(DA, strWord, 0);
                // strWrongWord = strWord.replaceFirst(TA, DA);
                // logger.debug("AddVoiced: "+strWrongWord);
                return strWrongWord;
            }
        }
        return null;
    }

    /**
     * @param strChar
     * @param strWord
     * @return its responding wrong word or else return null,
     */

    public static Vector getOmitVoicedWords(String strWord) {
        // logger.debug("enter getOmitVoicedWords4Noun;");
        Vector<String> vecResult = new Vector<String>();
        String strWrongWord;
        String strChar = null;
        for (int i = 0; i < strWord.length(); i++) {
            strChar = new String("" + strWord.charAt(i));
            strWrongWord = getOmitVoicedWord(strChar, strWord, i);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                // logger.debug( "OmitVoiced: "+strWrongWord);
                vecResult.addElement(strWrongWord);
            }
        }
        return vecResult;

    }

    /**
     * @param strChar
     * @param strWord
     * @return its responding wrong word or else return null,
     */
    private static String getOmitVoicedWord(String strChar, String strWord, int index) {
        // logger.debug("enter getOmitVoicedWord4Noun;");
        String strResult = null;
        String strVoiced = getOmitVoicedChar4Noun(strChar);
        if (strVoiced != null) {
            strResult = getReplaceWord(strVoiced, strWord, index);
            // logger.debug("is a voiced char");
        }
        return strResult;
    }

    /**
     * In all of the data,eng_based word only have two such error.They are
     * piza-pisa, beddo-betto; (pa-ba colomn,ha column),(ji,si)no pronunce
     * confusion for their big difference at least for pure japanese word; no
     * appears in our existing db
     *
     * @param strChar
     *            check the voiced pronuncation if it is da/ga/za
     *            colummn(exclude ji)
     * @return its responding wrong char or else return null za-sa ga-ka
     *         suzuki-susuki tomodati-tomotati codomo-cotomo ji-chi
     *         tegame-tekame ginnko-kinnco
     */
    private static String getOmitVoicedChar4Noun(String strChar) {
        // logger.debug("getOmitVoicedChar4Noun");
        String strResult = null;
        int i = 11;
        if (strChar.equals("じ") || strChar.equals("ジ")) {
            return null;
        } else {
            while (i <= 13) {
                for (int j = 0; j < 5; j++) {
                    String strTemp1 = CharacterUtil.HIRAGANATABLE[i][j];
                    String strTemp2 = CharacterUtil.KATAKANATABLE[i][j];
                    if (strChar.equals(strTemp1)) {
                        strResult = CharacterUtil.HIRAGANATABLE[i - 10][j];
                        return strResult;
                    } else if (strChar.equals(strTemp2)) {
                        strResult = CharacterUtil.KATAKANATABLE[i - 10][j];
                        return strResult;
                    }
                }
                i++;
            }
        }
        return null;
    }

    public static Vector getOmitLongWords(String strWord) {
        // logger.debug("getOmitLongWords");
        Vector<String> vecResult = new Vector<String>();
        String strWrongWord;
        String strChar = null;
        String strPreChar;

        // String strWord = lwm.getStrKana().trim(); //kana

        for (int i = 0; i < strWord.length(); i++) {
            strPreChar = strChar;
            strChar = new String("" + strWord.charAt(i));
            if (0 < i) {
                strWrongWord = getOmitLongWord(strPreChar, strChar, strWord, i);
                if (strWrongWord != null && strWrongWord.length() > 0) {
                    // logger.debug( "OmitLong: "+strWrongWord);
                    vecResult.addElement(strWrongWord);
                }
            }
        }
        return vecResult;

    }

    /*
     * 1
     * [あ]colummn+[あ];[い]colummn+[い];[う]colummn+[う];[え]colummn+[い]/[え];[お]colummn
     * +[う]/[お]; 2 [ゃ]+[あ];[ゅ]+[う];[ょ]+[う]; 3 [ー] check if it is a long
     * pronuncation if so, return its responding wrong word or else return null
     */
    private static String getOmitLongWord(String strPreChar, String strChar, String strWord, int index) {
        // System.out.println("enter getOmitLongWord strPreChar is "+strPreChar+"strChar is "+strChar+"strWord is"+strWord
        // );
        String strResult = null;
        if (checkLongChar(strPreChar, strChar)) {
            strResult = getReplaceWord("", strWord, index);
        }
        return strResult;
    }

    /**
     * @param strPreChar
     * @param strChar
     *            1 [あ]colummn+[あ];[い]colummn+[い];[う]colummn+[う];[え]colummn+[い]/
     *            [え]; [お]colummn+[う]/[お]; 2 [ゃ]+[あ];[ゅ]+[う];[ょ]+[う]; 3 [ー]
     *            check if it is a long pronuncation if so, return its
     *            responding wrong word or else return null
     * @return if strChar is a long pronunce
     */
    private static boolean checkLongChar(String strPreChar, String strChar) {
        // System.out.println("enter checkLongChar strPreChar is "+strPreChar+"strChar is "+strChar);
        String strResult = null;
        boolean booLongPronunce = false;
        if (strPreChar != null && strPreChar.length() > 0) {
            if (strChar.equals("ー")) {
                booLongPronunce = true;

            } else {
                // type 2
                if ((strPreChar.compareTo("ゃ") == 0 && strChar.equals("あ"))) {
                    booLongPronunce = true;
                } else if ((strPreChar.compareTo("ャ") == 0) && strChar.equals("ア")) {
                    booLongPronunce = true;
                } else if ((strPreChar.compareTo("ゅ") == 0) && strChar.equals("ウ")) {
                    booLongPronunce = true;
                } else if ((strPreChar.compareTo("ュ") == 0) && strChar.equals("ウ")) {
                    booLongPronunce = true;
                } else if ((strPreChar.compareTo("ょ") == 0) && strChar.equals("う")) {
                    booLongPronunce = true;
                } else if ((strPreChar.compareTo("ョ") == 0) && strChar.equals("ウ")) {
                    booLongPronunce = true;
                }// type 1
                else {
                    String charColumnName1 = CharacterUtil.charColumnName(strPreChar);
                    // String charColumnName2 =
                    // CharacterUtil.characterColumnName(strChar);
                    if (charColumnName1 != null) {
                        if (charColumnName1.equals(strChar)) {
                            booLongPronunce = true;
                        } else if (charColumnName1.equals("え") && strChar.equals("い")) {
                            booLongPronunce = true;
                        } else if (charColumnName1.equals("エ") && strChar.equals("イ")) {
                            booLongPronunce = true;
                        } else if (charColumnName1.equals("お") && strChar.equals("う")) {
                            booLongPronunce = true;
                        } else if (charColumnName1.equals("オ") && strChar.equals("ウ")) {
                            booLongPronunce = true;
                        }
                    }
                }
            }
        }
        return booLongPronunce;
    }

    /*
     * for noun check if it could connect [う] and become a long pronunciation or
     * we could say AddLong is detected,only the last character could add "う"
     * [う]colummn,[お]colummn,[ゅ],[ょ],except [う] For katakana words it happens
     * all the time except "ー"/"ン"
     */
    private String getAddLongWord(String strWord) {
        String strChar = strWord.substring(strWord.length() - 1);
        // logger.debug("the last char is: "+strChar);
        String strResult = null;
        int i = CharacterUtil.checkCharClass(strChar);
        // logger.debug("class is "+i);
        if (i == 1) {
            if (strChar.compareTo("ー") == 0 || strChar.compareTo("ン") == 0) {
                return null;
            } else {
                return null;
                // strResult = strWord.concat("ー");
            }
        } else {
            if ((strChar.compareTo("ゅ") == 0) || (strChar.compareTo("ょ") == 0)) {
                strResult = strWord.concat("う");
            } else {
                String charColumnName2 = CharacterUtil.charColumnName(strChar);
                if (charColumnName2 != null) {
                    if (charColumnName2.equals("う") && !strChar.equals("う")) {
                        strResult = strWord.concat("う");
                    } else if (charColumnName2.equals("お")) {
                        strResult = strWord.concat("う");
                    }
                }
            }
        }
        return strResult;
    }

    /**
     * @param lwm
     * @return Vector if get OmitDoubleConsonent Words its size>0;else size=0;
     */
    public static Vector getOmitDoubleConsonentWords(String strWord) {
        // logger.debug("the getOmitDoubleConsonentWords: "+strWord);
        Vector<String> vecResult = new Vector<String>();
        String strWrongWord;
        // String strKana = lwm.getStrKana().trim();
        int index;
        int i = 0;
        while (i < strWord.length()) {
            index = strWord.indexOf(DOUBLECONSONENT, i + 1);
            // logger.debug("index is "+ index);
            if (index != -1) {
                strWrongWord = getReplaceWord("", strWord, index);
                // logger.debug("have one,"+ strWrongWord);
                vecResult.addElement(strWrongWord);
                i = index;
            } else {
                // logger.debug("have no more OmitDoubleConsonent");
                i = strWord.length() + 1;
            }

        }
        return vecResult;
    }

    /*
     * strWord is the basic form of the NQ. no noun, no verb; we know this word
     * belongs to the lesson,but not sure this word has the lesson-specific
     * prediction error; SubstitutionGroup = Aternative word() + Confusion
     * word(); these are stored in an PredictionDataMeta which is constructed
     * from an QuantifierNode error Type is VDG Construct new PredictionDataMeta
     * and count its subsGrop's number
     */
    public Vector getDQ_SNum(JCALL_Word lwm, int Lesson) throws IOException {
        // logger.debug("enter getDQ_SNum");
        Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();
        String strNumber = lwm.getStrNumber();
        String strQuantifier = lwm.getStrQuantifier();
        JCALL_Word lwmTempN;
        JCALL_Word lwmTempQ;
        JCALL_Word lwmTempR;
        JCALL_NetworkSubNodeStruct pnode;
        String error = "DQ_SNum";
        JCALL_Word lwmTemp1 = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);

        if (lwmTemp1 != null) {
            logger.info("find the strQuantifier: " + lwmTemp1.getStrKana());
            // String str = lwmTemp1.getStrKana();
            Vector vecSub = getDW(lwmTemp1, Lesson);
            if (vecSub != null && vecSub.size() > 0) {
                // logger.debug("Subs Quantifier Size: "+ vecSub.size());

                lwmTempN = JCALL_Lexicon.getInstance().getWordFmStr(strNumber, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);
                for (int i = 0; i < vecSub.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vecSub.get(i);
                    lwmTempQ = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(),
                            JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);
                    if (lwmTempQ != null && lwmTempN != null) {
                        lwmTempR = JCALL_Lexicon.getInstance().searchNQ(lwmTempN, lwmTempQ);
                        if (lwmTempR != null) { // get the alternative QN
                            logger.info("subs NQ: " + lwmTempR.getStrKana());
                            pnode = new JCALL_NetworkSubNodeStruct();
                            pnode.bAccept = node.bAccept;
                            pnode.bValid = node.bValid;
                            pnode.strClass = node.strClass;
                            pnode.strError = error;
                            pnode.setIntType(JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                            pnode.vecSpecific = node.vecSpecific;
                            pnode.setStrKana(lwmTempR.getStrKana());
                            pnode.setStrKanji(lwmTempR.getStrKanji());
                            pnode.setId(lwmTempR.getId());
                            vResult.addElement(pnode);
                        }
                    } else {
                        logger.error("wrong number or wrong quantifier");
                    }

                }
            }
        } else {
            logger.error("wrong word , not find qantifier:+" + strQuantifier);

        }
        return vResult;
    }

    /**
     * @param strWord
     *            only noun-kana format, no NQ,no Verb,no Particle
     * @param OmitShort
     * @param AddShort
     *            is always false.
     * @param OmitLong
     * @param AddLong
     *            is detected,only the last character could add "��" OmitVoiced
     *            means g-k,d-t ,except quantifier; or (light weight continous
     *            words(like ������) ,but havent done this part NO AddLong for
     *            NQ word;
     * @param OmitVoiced
     * @param AddVoiced
     *            :is only checked by the first pronunciation is �� ,and
     *            confined to the japanese word(no english-based words)
     * @return
     * @throws IOException
     */
    public Vector getNQ_TW_PCE(JCALL_Word lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
            boolean OmitVoiced, boolean AddVoiced, boolean tw) throws IOException {
        Vector vecResult = new Vector();
        String strWrongWord;
        // String strChar = null;
        Vector v;
        String strWord = lwm.getStrKana().trim();
        String error = "";
        if (tw) {
            error = "TW_PCE";
        } else {
            error = "DW_PCE";
        }
        // logger.debug("int getNQ_TW_PCE; word ["+strWord+"]");
        // if (AddLong) {
        // strWrongWord = getAddLongWord(strWord);
        // if (strWrongWord != null
        // && strWrongWord.length() > 0) {
        // //logger.debug("AddLong"+strWrongWord );
        // JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
        // node.bValid=false;
        // node.strError=error;
        // node.setStrKana(strWrongWord);
        // node.addToVecSpecific("AddLong");
        // node.setStrKanji(strWrongWord);
        // vecResult.addElement(node);
        // }
        // }
        if (AddVoiced) {
            strWrongWord = getAddVoicedWord4NQ(lwm);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                node.bValid = false;
                node.strError = error;
                node.setStrKana(strWrongWord);
                node.addToVecSpecific("AddVoiced");
                node.setStrKanji(strWrongWord);
                vecResult.addElement(node);
            }
        }
        if (OmitVoiced) {// no care about the char position
            strWrongWord = getOmitVoicedWord4NQ(lwm);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                node.bValid = false;
                node.strError = error;
                node.setStrKana(strWrongWord);
                node.addToVecSpecific("OmitVoiced");
                node.setStrKanji(strWrongWord);
                vecResult.addElement(node);
            }
        }
        if (OmitLong) {// happens from the second position
            v = getOmitLongWords(strWord);
            if (v != null && v.size() > 0) {
                for (int i = 0; i < v.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                    node.bValid = false;
                    node.strError = error;
                    node.setStrKana((String) v.elementAt(i));
                    node.addToVecSpecific("OmitLong");
                    node.setStrKanji((String) v.elementAt(i));
                    vecResult.addElement(node);
                }
            }
        }
        if (OmitShort) {
            v = getOmitDoubleConsonentWords(strWord);
            if (v != null && v.size() > 0) {
                for (int i = 0; i < v.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                    node.bValid = false;
                    node.strError = error;
                    node.setStrKana((String) v.elementAt(i));
                    node.addToVecSpecific("OmitShort");
                    node.setStrKanji((String) v.elementAt(i));
                    vecResult.addElement(node);
                }
            }
        }

        return vecResult;

    }

    /**
     * @param lwm
     *            POS=NQ,like ���B҂�, use its kana format
     * @return null,if no such type error; or else return responding wrong word
     *         of this wsdm;
     */
    private static String getOmitVoicedWord4NQ(JCALL_Word lwm) {
        String strResult = null;
        String strKanji = lwm.getStrKanji().trim();
        String strKana = lwm.getStrKana().trim();
        String strQuantifier = lwm.getStrQuantifier().trim();
        if (strKanji.length() > 1) {
            JCALL_Word lwmTemp = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                    JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);
            if (lwmTemp != null) {
                String strQuantifierKana = lwmTemp.getStrKana().trim();
                int intLength = strQuantifierKana.length();
                int changeCharIndex = strKana.length() - intLength;
                String strCharSource = new String("" + strKana.charAt(changeCharIndex));
                String strCharTarget = CharacterUtil.changeVoicedToLight(strCharSource);
                if (strCharTarget != null) { // if it is an voicedLight
                    strResult = getReplaceWord(strCharTarget, strKana, changeCharIndex);
                    // logger.debug("one OmitVoicedWord4NQ: "+ strResult);
                }
            } else {
                System.out.println("wrong Quantifier! [" + strQuantifier + "]");
            }
        }
        return strResult;
    }

    /**
     * Feature: add voiced pronunciation to the first character of its
     * responding quantifier, only if the first charcter is "ka/sa/ta/ha/pa"
     * row.
     *
     * @param lwm
     *            POS=NQ,like ���B҂�, use its kana format *
     * @return null,if no such type error; or else return responding addvoiced
     *         wrong word of this wsdm;
     */

    public static String getAddVoicedWord4NQ(JCALL_Word lwm) throws IOException {
        String strResult = null;
        String strKanji = lwm.getStrKanji().trim();
        String strKana = lwm.getStrKana().trim();
        String strNumber; // number part by cutting quantifier
        String strQuantifier = lwm.getStrQuantifier();

        String strQuantifierLeft = "";
        if (strKanji.length() > 1) {
            JCALL_Word lwmTemp = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                    JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);
            if (lwmTemp != null) {
                String strQuantifierKana = lwmTemp.getStrKana().trim();
                String strChar = strQuantifierKana.substring(0, 1);
                int intLength = strQuantifierKana.length();
                strNumber = strKana.substring(0, strKana.length() - intLength); // the
                                                                                // string
                                                                                // without
                                                                                // quantifier;
                if (intLength > 1) {
                    strQuantifierLeft = strQuantifierKana.substring(1);
                }
                String strCharTemp = CharacterUtil.LightToVoiced(strChar);
                if (strCharTemp != null && strCharTemp.length() > 0) {
                    strResult = strNumber + strCharTemp + strQuantifierLeft;
                }
            }
        }
        return strResult;
    }

    /*
     * quantity1 = number type
     */
    public JCALL_NetworkSubNodeStruct getNQ_SNum(JCALL_Word lwm) {
        // Vector<String> vecResult = new Vector<String>();
        // logger.debug("enter getNQNUM");
        if (lwm != null) { // it is a num+quantifier type;
            String strNumber = lwm.getStrNumber();
            if (strNumber != null && strNumber.length() > 0) {
                // logger.debug("number: "+strNumber);
                JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(strNumber, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);
                if (word != null) {
                    JCALL_NetworkSubNodeStruct epl = new JCALL_NetworkSubNodeStruct();
                    epl.bAccept = false;
                    epl.bValid = false;
                    epl.setId(word.getId());
                    epl.setIntType(word.getIntType());
                    epl.setStrError("NQ_SNum");
                    epl.setStrKana(word.getStrKana());
                    epl.setStrKanji(word.getStrKanji());
                    // logger.debug("NQ_SNum: "+ word.getStrKanji());
                    return epl;
                }
            }
        }
        return null;
    }

    /*
     * quantity2= number+Quantity wrong type, if it should be special transform
     * format return null,when no such type error; or else return responding
     * wrong words;
     */
    public static Vector getTW_WForm(JCALL_Word lwm) throws IOException {
        // logger.debug("enter getTW_WForm");
        Vector<String> vecResult = new Vector<String>();
        // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
        if (lwm != null) { // it is a num+quantifier type;
            String strKanji = lwm.getStrKanji().trim();
            String strKana = lwm.getStrKana().trim();
            String strNumber = lwm.getStrNumber();
            String strQuantifier = lwm.getStrQuantifier();

            JCALL_Word numberWord = JCALL_Lexicon.getInstance().getWordFmStr(strNumber, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);

            JCALL_Word quantifierWord = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                    JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);

            if (numberWord != null && quantifierWord != null) {
                String strWrongWord1 = numberWord.getStrKana() + quantifierWord.getStrKana();
                String strWrongWord2 = null;
                if (numberWord.getStrAltkana().length() > 0) {
                    strWrongWord2 = numberWord.getStrAltkana() + quantifierWord.getStrKana();
                }
                if (strKana.equals(strWrongWord1)) {
                    return null;
                } else {
                    if (strWrongWord2 == null) {
                        vecResult.addElement(strWrongWord1);
                    } else {
                        if (strKana.equals(strWrongWord2)) {
                            return null;
                        } else {
                            vecResult.addElement(strWrongWord2);
                            vecResult.addElement(strWrongWord1);
                        }
                    }
                }

            } else {
                // logger.debug("number [" + strNumber + "]"
                // + " or quantifier [" + strQuantifier
                // + "] is not found in the lexicon");
            }
        }

        Vector vecQ = new Vector();
        if (vecResult != null && vecResult.size() > 0) {
            for (int j = 0; j < vecResult.size(); j++) {
                String strW = (String) vecResult.elementAt(j);
                JCALL_NetworkSubNodeStruct epl = new JCALL_NetworkSubNodeStruct();
                epl.bAccept = false;
                epl.bValid = false;
                epl.setStrError("TW_WForm");
                epl.setStrKana(strW);
                epl.setStrKanji(strW);
                vecQ.addElement(epl);
            }
        }
        // logger.debug("TW_WForm is ["+ vecQ.size()+"]");

        return vecQ;
    }

    private Vector getDQ_PCE(Vector v, int lesson) throws IOException {
        // logger.debug("enter getNQINVDG_PCE: "+ v.size());
        // int intCount = 0;
        Vector vecReuslt = new Vector();
        JCALL_Word lwm;
        JCALL_NetworkSubNodeStruct node;
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                node = ((JCALL_NetworkSubNodeStruct) v.get(i));
                // lwm = JCALL_Lexicon.getWordFmID(node.getId());
                lwm = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                // INVDG_PCE, watch out! the quantity1 &quantity1 now should be
                // false
                if (lwm != null) {
                    Vector vecPCE = getNQ_TW_PCE(lwm, true, false, false, true, true, true, false);
                    if (vecPCE != null && vecPCE.size() > 0) {
                        for (int j = 0; j < vecPCE.size(); j++) {
                            vecReuslt.addElement(vecPCE.get(j));
                            // intCount ++;
                        }
                    }
                }
            }
        }
        return vecReuslt;
    }

    public Vector getNQSubstitution(JCALL_Word lwm, int lesson) throws IOException {
        // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
        // logger.debug("int getNQSubstitution; word ["+lwm.getStrKana()+"]"+" lesson: "+
        // lesson);
        intTotal = 0;
        JCALL_NetworkSubNodeStruct epl = null;
        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector();
        Set<String> hs = new HashSet<String>();
        hs.add(lwm.getStrKana());
        String str;
        JCALL_PredictionDataStruct pdm;
        // TW_PCE
        Vector vec = getNQ_TW_PCE(lwm, true, false, false, true, true, true, true);
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                str = pnode.getStrKana();
                str = str.trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(pnode);
                    // vResult.addElement((String) vec.elementAt(i));
                }
            }
            intTotal += vec.size();
            // logger.debug("TW_PCE is ["+ vec.size()+"]");
        }

        // VDG
        Vector v = getDQ_SNum(lwm, lesson);
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) v.get(i);
                str = pnode.getStrKana();
                str = str.trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(pnode);
                }
            }

            intTotal += v.size();
            // logger.debug("DQ_SNum is ["+v.size()+"]");
        }

        // INVDG_PCE
        if (v != null && v.size() > 0) {
            Vector vVdg = getDQ_PCE(v, lesson);
            if (vVdg != null && vVdg.size() > 0) {
                for (int j = 0; j < vVdg.size(); j++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vVdg.get(j);
                    str = pnode.getStrKana();
                    str = str.trim();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        vecResult.addElement(pnode);
                    }
                }

                intTotal += vVdg.size();
                // logger.debug("DW_PCE is ["+ vVdg.size()+"]");
            }
        }

        // QUANTITY+QUANTITY2
        JCALL_NetworkSubNodeStruct Q1 = getNQ_SNum(lwm);
        if (Q1 != null && Q1.getStrKana() != null && Q1.getStrKana().length() > 0) {
            if (!hs.contains(Q1.getStrKana())) {
                hs.add(Q1.getStrKana());
                vecResult.addElement(Q1);
                // logger.debug("NQ_SNum: "+ Q1.getStrKanji());
            }
        }

        Vector vecQ = getTW_WForm(lwm);

        if (vecQ != null && vecQ.size() > 0) {
            for (int j = 0; j < vecQ.size(); j++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vecQ.get(j);
                str = pnode.getStrKana();
                str = str.trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(pnode);
                    // vResult.addElement((String) vec.elementAt(i));
                }
            }
            // logger.debug("TW_WForm is ["+ vecQ.size()+"]");
        }

        // logger.debug("all is ["+ hs.size()+"]");
        return vecResult;
    }

    // //////////////////////////////////
    // ///
    // //Complilation for the old version software
    // //
    // ////////////////////////////////////////////////////

    private Vector getDW_PCE(Vector vSubs) {
        // logger.debug("enter getNoun_DW_PCE");
        Vector vecResult = new Vector();
        Vector vec = vSubs;
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vec.get(i);
                // INVDG_PCE
                if (node.bValid) {
                    JCALL_Word lwm = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), node.getIntType());
                    if (lwm != null) {
                        Vector vecPCE = getPCE(lwm, true, false, true, true, true, true);
                        if (vecPCE != null && vecPCE.size() > 0) {
                            for (int j = 0; j < vecPCE.size(); j++) {
                                JCALL_NetworkSubNodeStruct nodeTmp = (JCALL_NetworkSubNodeStruct) vecPCE.get(j);
                                nodeTmp.strError = "DW_PCE";
                                vecResult.addElement(node);
                            }
                        }
                    }
                }
            }
        }
        return vecResult;
    }

    private Vector getINVDG_PCEWFORM4Noun(Vector vSubs, int lesson) throws IOException {
        // logger.debug("enter getINVDG_PCEWFORM4Noun");
        Vector vecResult = new Vector();
        Vector vec = vSubs;
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vec.get(i);
                // INVDG_PCE
                if (node.bValid) {
                    JCALL_Word lwm = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), node.getIntType());
                    if (lwm != null) {
                        Vector vecPCE = getPCE(lwm, true, false, true, true, true, true);
                        if (vecPCE != null && vecPCE.size() > 0) {
                            for (int j = 0; j < vecPCE.size(); j++) {
                                JCALL_NetworkSubNodeStruct nodeTmp = (JCALL_NetworkSubNodeStruct) vecPCE.get(j);
                                nodeTmp.strError = "DW_PCE";
                                vecResult.addElement(node);
                            }
                            // intCount += vecPCE.size();
                            // sss.addNoun("INVDG_PCE", vecPCE.size());
                        }
                        // INVDG_WFORM
                        Vector vTemp = getNounSubsGroup(lwm, lesson);
                        if (vTemp != null && vTemp.size() > 0) {
                            for (int k = 0; k < vTemp.size(); k++) {
                                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vTemp.elementAt(k);
                                if (!pnode.bValid && pnode.isHasErrSpecific("honorific")) {
                                    vecResult.addElement(pnode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return vecResult;
    }

    // same with getDW, but less of synonym prediction

    public static Vector getNounSubsGroup(JCALL_Word lwm, int lesson) {
        // logger.debug("enter getNounSubsGroup! "+lwm.getStrKana());
        String sLesson = new String("" + lesson);
        Vector vResult = null;
        JCALL_PredictionDataStruct pdm = null;
        JCALL_NetworkSubNodeStruct node;
        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();
        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category

                if (pdm.isHasLesson(sLesson) && pdm.compareTo(lwm) == 0) {// same
                                                                          // lesson
                    // logger.debug("Find a pdm: "+pdm.getStrKanji());
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    node.vecSpecific = pdm.vecSpecific;
                    String s = pdm.strSubword;
                    if (s != null && s.length() > 0) {
                        // logger.debug("subs word: "+s);
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(s, pdm.getIntType());
                            if (word != null) {
                                node.setStrKana(word.getStrKana());
                                node.setStrKanji(word.getStrKanji());
                                node.setId(word.getId());
                                if (vResult == null) {
                                    vResult = new Vector();
                                }
                                vResult.addElement(node);
                                // logger.debug("get one NounSubsGroup: "+pdm.getStrWord());
                            } else {
                                logger.error("no word in lexicon: " + s);
                            }

                        } else {
                            node.setStrKana(pdm.strSubword);
                            node.setStrKanji(pdm.strSubword);
                            if (vResult == null) {
                                vResult = new Vector();
                            }
                            vResult.addElement(node);
                        }

                    } else { // s==null or no subword

                        logger.error("no sub word");
                    }

                }
            } else if (pdm.strClass.equalsIgnoreCase("group")) {
                String head;
                String mid;
                String tail;
                String str = pdm.strCategory;
                if (str != null && str.length() > 0) {// group
                    if (lwm.IsHasCategory(str)) {
                        // apply rule
                        String rule = pdm.strRule;
                        if (rule != null && rule.length() > 0) {
                            StringTokenizer st = new StringTokenizer(rule.trim());
                            Vector v = new Vector();
                            while (st.hasMoreElements()) {
                                v.addElement(st.nextToken());
                            }

                            if (v.size() == 3) {
                                head = ((String) v.elementAt(0)).trim();
                                tail = ((String) v.elementAt(2)).trim();
                                int intIndex = head.indexOf("*");
                                if (intIndex == 0) { // //omit suffix or prefix;
                                    // Vector vTail = new Vector();
                                    StringTokenizer stTail = new StringTokenizer(tail, ",");
                                    while (stTail.hasMoreElements()) {
                                        // vTail.addElement(stTail.nextToken());
                                        String strTemp = stTail.nextToken().trim();
                                        int intIndex2 = strTemp.indexOf("*");
                                        if (intIndex2 == 0) {// suffix
                                            String suffix = strTemp.substring(1);

                                            node = new JCALL_NetworkSubNodeStruct();
                                            node.bAccept = pdm.bAccept;
                                            node.bValid = pdm.bValid;
                                            node.strClass = pdm.strClass;
                                            node.strError = pdm.strError;
                                            node.vecSpecific = pdm.vecSpecific;
                                            node.setStrKana(lwm.getStrKana() + suffix);
                                            node.setStrKanji(lwm.getStrKanji() + suffix);
                                            if (vResult == null) {
                                                vResult = new Vector();
                                            }
                                            vResult.addElement(node);
                                        } else {
                                            String prefix = strTemp.substring(0, intIndex2 - 1);
                                            node = new JCALL_NetworkSubNodeStruct();
                                            node.bAccept = pdm.bAccept;
                                            node.bValid = pdm.bValid;
                                            node.strClass = pdm.strClass;
                                            node.strError = pdm.strError;
                                            node.vecSpecific = pdm.vecSpecific;
                                            node.setStrKana(prefix + lwm.getStrKana());
                                            node.setStrKanji(prefix + lwm.getStrKanji());
                                            if (vResult == null) {
                                                vResult = new Vector();
                                            }
                                            vResult.addElement(node);
                                        }

                                    }

                                }
                            }
                        }

                    }

                }

            }

        }// end of for

        return vResult;
    }

    /**
     * @param strWord
     *            only noun-kana format, no NQ,no Verb,no Particle
     * @param OmitShort
     * @param AddShort
     *            is always false.
     * @param OmitLong
     * @param AddLong
     *            is detected,only the last character could add "��" OmitVoiced
     *            means g-k,d-t ,except quantifier; or (light weight continous
     *            words(like ������) ,but havent done this part
     * @param OmitVoiced
     * @param AddVoiced
     *            :is only checked by the first pronunciation is �� ,and
     *            confined to the japanese word(no english-based words)
     * @return
     * @throws IOException
     */
    public Vector getPCE(JCALL_Word lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
            boolean OmitVoiced, boolean AddVoiced) {
        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector<JCALL_NetworkSubNodeStruct>();
        String strWrongWord;
        JCALL_NetworkSubNodeStruct node;
        // String strChar = null;
        Vector v;
        String strWord = lwm.getStrKana().trim();
        // logger.debug("int getNounPCE; word ["+strWord+"]");

        if (lwm.IsHasCategory("family")) {
            // logger.debug("It is a family name, no PCE");
        } else {

            if (AddVoiced) {
                // //logger.debug("AddVoiced "+AddVoiced);
                strWrongWord = getAddVoicedWord(lwm);
                if (strWrongWord != null && strWrongWord.length() > 0) {
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = false;
                    node.bValid = false;
                    node.strError = "TW_PCE";
                    node.addToVecSpecific("AddVoiced");
                    node.setStrKana(strWrongWord);
                    node.setStrKanji(strWrongWord);
                    vecResult.addElement(node);
                } else {
                    // logger.debug("not prediction: AddVoiced ");
                }
            }
            if (AddLong) {
                // //logger.debug("AddLong "+AddLong);
                strWrongWord = getAddLongWord(strWord);
                if (strWrongWord != null && strWrongWord.length() > 0) {
                    // logger.debug("AddLong: "+strWrongWord );
                    // vecResult.addElement(strWrongWord);
                    // sss.addNoun("AddLong", 1);
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = false;
                    node.bValid = false;
                    node.strError = "TW_PCE";
                    node.addToVecSpecific("AddLong");
                    node.setStrKana(strWrongWord);
                    node.setStrKanji(strWrongWord);
                    vecResult.addElement(node);
                } else {
                    // logger.debug("not prediction: AddLong");
                }
            }

            if (OmitVoiced) {// not care about the char position
                v = getOmitVoicedWords(strWord);
                if (v != null && v.size() > 0) {
                    for (int i = 0; i < v.size(); i++) {
                        strWrongWord = ((String) v.elementAt(i));
                        node = new JCALL_NetworkSubNodeStruct();
                        node.bAccept = false;
                        node.bValid = false;
                        node.strError = "TW_PCE";
                        node.addToVecSpecific("OmitVoiced");
                        node.setStrKana(strWrongWord);
                        node.setStrKanji(strWrongWord);
                        vecResult.addElement(node);
                        // sss.addNoun("OmitVoiced", 1);
                        // //logger.debug("OmitVoiced: "+(String) v.elementAt(i)
                        // );
                    }
                    // System.out.println("OmitVoiced "+strWrongWord);
                } else {
                    // logger.debug("not prediction: OmitVoiced");
                }
            }
            if (OmitLong) {// happens from the second position
                v = getOmitLongWords(strWord);
                if (v != null && v.size() > 0) {
                    for (int i = 0; i < v.size(); i++) {
                        // vecResult.addElement((String) v.elementAt(i));
                        // sss.addNoun("OmitLong", 1);
                        strWrongWord = ((String) v.elementAt(i));
                        node = new JCALL_NetworkSubNodeStruct();
                        node.bAccept = false;
                        node.bValid = false;
                        node.strError = "TW_PCE";
                        node.addToVecSpecific("OmitLong");
                        node.setStrKana(strWrongWord);
                        node.setStrKanji(strWrongWord);
                        vecResult.addElement(node);
                        // logger.debug("OmitLong: "+(String) v.elementAt(i) );
                    }
                } else {
                    // logger.debug("not prediction: OmitLong");
                }
            }
            if (OmitShort) {
                v = getOmitDoubleConsonentWords(strWord);
                if (v != null && v.size() > 0) {
                    for (int i = 0; i < v.size(); i++) {
                        // vecResult.addElement((String) v.elementAt(i));
                        // sss.addNoun("OmitShort", 1);
                        strWrongWord = ((String) v.elementAt(i));
                        node = new JCALL_NetworkSubNodeStruct();
                        node.bAccept = false;
                        node.bValid = false;
                        node.strError = "TW_PCE";
                        node.addToVecSpecific("OmitShort");
                        node.setStrKana(strWrongWord);
                        node.setStrKanji(strWrongWord);
                        vecResult.addElement(node);

                        // logger.debug("OmitShort: "+(String) v.elementAt(i) );
                    }
                } else {
                    // logger.debug("not prediction: OmitShort");
                }
            }
        }
        return vecResult;

    }

    /*
     * for CALL_sentenceGrammar class
     */
    public Vector getVerbErrors(JCALL_Word lwm, int lesson) {
        // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // logger.debug("enter getVerbErrors");
        // System.out.println("enter getVerbErrors");

        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector();
        // to prevent repetiveness
        Set<String> hs = new HashSet<String>();
        String strWord = lwm.getStrKana();
        // hs.add(strWord); //first add the original kana word, for prevent the
        // accepted prediction words is equal to strWord;
        Vector vec;
        String str;
        JCALL_NetworkSubNodeStruct node;
        int intNum = 0;
        JCALL_PredictionDataStruct pdmVerb = null;
        String strForm = "";

        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        boolean booType = false;
        boolean booNegative = false;
        boolean booTense = false;
        //
        if (lwm != null) {
            // //logger.debug("eps.vecPDM.size: "+ eps.vecPDM.size());
            for (int i = 0; i < eps.getVecPDM().size(); i++) {
                JCALL_PredictionDataStruct pdm = eps.getVecPDM().get(i);
                if (pdm.strClass.equalsIgnoreCase("grammar") && pdm.isHasLesson(lesson)) {
                    pdmVerb = pdm;
                    // logger.debug("find verpdm, strREF: "+pdm.strREF+
                    // " lesson: "+
                    // lesson);

                    break;
                }
            }
            if (pdmVerb != null) {
                // set the form struct;
                Vector vecForm = pdmVerb.getVecVerbForm();

                for (int i = 0; i < vecForm.size(); i++) {
                    String strTemp = (String) vecForm.get(i);
                    strForm = strForm + " " + strTemp;
                }
                strForm = strForm.trim();
                // logger.debug("strForm: "+strForm);
                // System.out.println("strForm: "+strForm);

                CALL_formStruct form = null;
                form = new CALL_formStruct();
                form.setFromString(strForm);

                if (form.isQuestion() && form.isStatement()) {
                    booType = true;
                    // logger.debug("booType: "+true);
                }
                if (form.isNegative() && form.isPolite()) {
                    booNegative = true;
                    // logger.debug("booNegative: "+true);
                }
                if (form.isPast() && form.isPresent()) {
                    booTense = true;
                    // logger.debug("booTense: "+true);
                }

                vec = getVerb_TW_DFORM(lwm, pdmVerb, lesson);
                if (vec != null && vec.size() > 0) {
                    for (int i = 0; i < vec.size(); i++) {
                        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                        str = pnode.getStrKana();
                        str = str.trim();
                        if (!hs.contains(str)) {
                            hs.add(str);
                            vecResult.addElement(pnode);
                            // System.out.println(pnode.toString());

                        }
                    }
                    intTotal += vec.size();
                    // System.out.println("DFORM;"+vec.size());
                }

                vec = getVerb_DW_DFORM(lwm, pdmVerb, lesson); // includes all
                                                              // different
                                                              // form of it;
                if (vec != null && vec.size() > 0) {
                    for (int i = 0; i < vec.size(); i++) {
                        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                        str = pnode.getStrKana();
                        str = str.trim();
                        if (!hs.contains(str)) {
                            hs.add(str);
                            vecResult.addElement(pnode);
                            // System.out.println(pnode.toString());
                        }
                    }
                    intTotal += vec.size();
                    // System.out.println("DFORM;"+vec.size());
                }

                // vec =
                // getVerbError_INVS_REF(lwm,pdmVerb,lesson,booTense,booNegative,true);

                vec = getTW_WIF(lwm, strForm, lesson);

                if (vec != null && vec.size() > 0) {
                    for (int i = 0; i < vec.size(); i++) {
                        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                        str = pnode.getStrKana();
                        str = str.trim();
                        if (!hs.contains(str)) {
                            hs.add(str);
                            vecResult.addElement(pnode);
                            // System.out.println(pnode.toString());
                        }
                    }
                    intTotal += vec.size();
                    // System.out.println(vec.size());
                }

                // vec =
                // getVerbError_INVDG_REF(lwm,pdmVerb,lesson,booTense,booNegative);
                // if(vec!=null&&vec.size()>0){
                // for (int i = 0; i < vec.size(); i++) {
                // PNode pnode = (PNode) vec.get(i);
                // str = pnode.getStrKana();
                // str = str.trim();
                // if(!hs.contains(str)){
                // hs.add(str);
                // vecResult.addElement(pnode);
                // System.out.println(pnode.toString());
                // }
                // }
                // intTotal+=intNum;
                // //System.out.println(intNum);
                // }
            }
        }

        return vecResult;

    }

    Vector getWIF(JCALL_Word word, String strForm, String strTransRule) {

        // System.out.println("enter getWIF, strForm: " +strForm);

        Vector vResult = new Vector();
        CALL_formStruct form = null;
        form = new CALL_formStruct();
        form.setFromString(strForm);
        CALL_Form call_form = new CALL_Form(form);
        if (call_form == null) {
            System.out.println("call_form is null");
            return vResult;
        }

        if (word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB) {

            // System.out.println("Word type: Verb, call_form.getSign(): "+
            // call_form.getSign() +" call_form.getTense(): " +
            // call_form.getTense()
            // +" call_form.getStyle(): "+call_form.getStyle()
            // +" call_form.getType(): "+ call_form.getType() );

            JCALL_VerbErrorRules verules = JCALL_VerbErrorRules.getInstance();

            // if(verules == null){
            // verules = new JCALL_VerbErrorRules();
            // verules.loadRules(CALL_database.VerbErrorRuleFile);
            // }

            if (verules != null) {
                vResult = verules.getVerbErrorForms(word, strTransRule, call_form.getSign(), call_form.getTense(),
                        call_form.getStyle(), call_form.getType());
            }
        } else {

            logger.error(word.getStrKanji() + "is not a verb or adjective");

        }

        return vResult;

    }

    Vector getWIF(JCALL_Word word, String strForm, int lesson) {
        // logger.debug("enter getWIF");
        // System.out.println("enter getWIF");

        Vector vResult = new Vector();
        JCALL_PredictionDataStruct pdmVerb = null;

        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        if (!(word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB)) {
            System.out.println("Error, Not a verb in getWIF, word: " + word.getStrKanji());

        }

        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            JCALL_PredictionDataStruct pdm = eps.getVecPDM().get(i);
            if (pdm.strREF != null && pdm.strREF.length() > 0 && pdm.isHasLesson(lesson)) {
                pdmVerb = pdm;
                // logger.debug("find verpdm, strREF: "+pdm.strREF+ " lesson: "+
                // lesson);
                break;
            }
        }
        if (pdmVerb != null) {
            String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
            Vector vec = pdmVerb.getVecVerbInvalidFrom();
            if (vec != null && vec.size() > 0 && strRef != null) {
                // deal with different kinds of errors of these
                for (int i = 0; i < vec.size(); i++) {
                    String strRuleName = (String) vec.get(i);
                    strRuleName = strRef + "_" + strRuleName;

                    // System.out.println("strRuleName: "+ strRuleName);

                    Vector vTemp = getWIF(word, strForm, strRuleName);
                    if (vTemp != null && vTemp.size() > 0) {
                        for (int j = 0; j < vTemp.size(); j++) {
                            vResult.addElement((CALL_wordWithFormStruct) vTemp.get(j));
                        }

                    }

                }
            }

        } else {
            // logger.debug("no error of WIF");
            return null;

        }

        return vResult;
    }

    Vector getTW_WIF(JCALL_Word word, String strForm, int lesson) {
        // logger.debug("enter getTW_WIF");
        // System.out.println("enter getTW_WIF");

        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector<JCALL_NetworkSubNodeStruct>();
        Set<String> hs = new HashSet<String>();
        String str;
        Vector vec = getWIF(word, strForm, lesson); // included the answer;
        String error = "TW_WIF";
        String strClass = "GRAMMAR";
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                CALL_wordWithFormStruct wordwithForm = (CALL_wordWithFormStruct) vec.get(i);
                str = wordwithForm.getSurfaceFormKana().trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                    pnode.setBAccept(false);
                    pnode.setStrError(error);
                    pnode.setStrClass(strClass);
                    pnode.setBValid(false);
                    pnode.setStrKana(wordwithForm.getSurfaceFormKana());
                    pnode.setStrKanji(wordwithForm.getSurfaceFormKana());
                    vecResult.addElement(pnode);
                    // logger.debug(pnode.toString());
                }

            }
        }

        return vecResult;

    }

    private Vector getVerb_TW_DFORM(JCALL_Word lwm, JCALL_PredictionDataStruct pdmVerb, int lesson) {
        // TODO Auto-generated method stub

        // logger.debug("enter getVerb_TW_DFORM");
        Vector vResult = null;
        vResult = getDForm(lwm, pdmVerb, lesson, true);

        return vResult;
    }

    public Vector getVerb_DW_DFORM(JCALL_Word lwm, JCALL_PredictionDataStruct pdmVerb, int lesson) {
        // VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // logger.debug("enter getVerbErrors_DFORM; word ["+lwm.getStrKana()+"]");
        Vector<JCALL_NetworkSubNodeStruct> vResult = new Vector<JCALL_NetworkSubNodeStruct>();
        if (pdmVerb != null) {
            // VDG
            Vector vVDG = getVerbSubsGroup(lwm, lesson);
            if (vVDG != null && vVDG.size() > 0) {
                // System.out.println("has DW, size: "+ vVDG.size());

                for (int i = 0; i < vVDG.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vVDG.get(i);
                    // JCALL_Word lwmSubs =
                    // JCALL_Lexicon.getInstance().getWordFmID(node.getId());
                    JCALL_Word lwmSubs = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji());
                    if (lwmSubs != null && lwmSubs.getStrKana().length() > 0) {
                        Vector vecTemp = getDForm(lwmSubs, pdmVerb, lesson, false);

                        if (vecTemp != null && vecTemp.size() > 0) {
                            for (int j = 0; j < vecTemp.size(); j++) {
                                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vecTemp.elementAt(j);
                                vResult.addElement(pnode);
                            }
                        }
                    } else {
                        System.out.println("wrong, word [" + lwm.getStrKana() + "], its VDG: " + node.getStrKana()
                                + " can not find in the lexicon");
                    }
                }
            } else {
                // System.out.println("has no DW");
            }
            // end vVDG

        }

        return vResult;
    }

    /*
     *
     * for CALL_sentenceGrammar class
     */
    public Vector getVerbErrors_DFORM(JCALL_Word lwm, int lesson) throws IOException {
        // VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // logger.debug("enter getVerbErrors_DFORM; word ["+lwm.getStrKana()+"]");
        Vector vResult = new Vector();
        String word = lwm.getStrKana();
        JCALL_PredictionDataStruct pdmVerb = null;
        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();
        if (lwm != null) {
            // //logger.debug("eps.vecPDM.size: "+ eps.vecPDM.size());
            for (int i = 0; i < eps.getVecPDM().size(); i++) {
                JCALL_PredictionDataStruct pdm = eps.getVecPDM().get(i);
                if (pdm.strClass.equalsIgnoreCase("grammar") && pdm.isHasLesson(lesson)) {
                    pdmVerb = pdm;
                    // logger.debug("find verpdm, strREF: "+pdm.strREF+
                    // " lesson: "+
                    // lesson);
                    break;
                }
            }
            if (pdmVerb != null) {
                // VDG
                Vector vVDG = getVerbSubsGroup(lwm, lesson);
                if (vVDG != null && vVDG.size() > 0) {
                    for (int i = 0; i < vVDG.size(); i++) {
                        JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vVDG.get(i);
                        JCALL_Word lwmSubs = JCALL_Lexicon.getInstance().getWordFmID(node.getId());
                        if (lwmSubs != null && lwmSubs.getStrKana().length() > 0) {
                            Vector vecTemp = getDForm(lwmSubs, pdmVerb, lesson, false);

                            if (vecTemp != null && vecTemp.size() > 0) {
                                for (int j = 0; j < vecTemp.size(); j++) {
                                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vecTemp.elementAt(j);
                                    vResult.addElement(pnode);
                                }
                            }
                        } else {
                            System.out.println("wrong, word [" + lwm.getStrKana() + "], its VDG: " + node.getStrKana()
                                    + " can not find in the lexicon");
                        }
                    }
                } else {
                    logger.error("no sub word, verb: " + lwm.getStrKana());
                }
                // end vVDG

            }

        }

        return vResult;
    }

    public static Vector getDForm(JCALL_Word lwm, JCALL_PredictionDataStruct pdmVerb, int lesson, boolean tw) {
        Vector vecResult = new Vector();
        // logger.debug("enter getDForm;");
        // System.out.println("enter getDForm;");

        // String group = lwm.getStrCategory1();
        String group = lwm.getStrGroup();
        // //logger.debug("verb group: "+group);

        // set the form struct;
        Vector vecForm = pdmVerb.getVecVerbForm();
        String strForm = "";
        for (int i = 0; i < vecForm.size(); i++) {
            String strTemp = (String) vecForm.get(i);
            strForm = strForm + " " + strTemp;
        }
        strForm = strForm.trim();
        // logger.debug("strForm: "+strForm);

        CALL_formStruct form = null;
        form = new CALL_formStruct();
        form.setFromString(strForm);

        boolean booType = false;
        boolean booNegative = false;
        boolean booTense = false;
        if (form.isQuestion() && form.isStatement()) {
            booType = true;
            // logger.debug("booType: "+true);
        }
        if (form.isNegative() && form.isPolite()) {
            booNegative = true;
            // logger.debug("booNegative: "+true);
        }
        if (form.isPast() && form.isPresent()) {
            booTense = true;
            // logger.debug("booTense: "+true);
        }

        // verb's special form
        String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
        strRef = strRef.trim();
        String strSpecialTransformWordKana = null;
        String strSpecialTransformWordKanji = null;
        String strSpecialTransformWordMasyou = null;
        Vector vSpecialTransformWord = null;
        Vector v = null;
        boolean booV = false;
        if (strRef.length() > 0) {
            // //logger.debug("a special form,strRef.length()>0: "+strRef);
            if (strRef.equalsIgnoreCase("TETA")) {

                vSpecialTransformWord = FormatVerb.verbToOrder(lwm, booNegative, tw);

                vecResult = vSpecialTransformWord;
                // //logger.debug("SE: "+strSpecialTransformWord);
                return vecResult;
            } else if (strRef.equalsIgnoreCase("SE")) {
                strSpecialTransformWordKana = FormatVerb.verbToSe(lwm.getStrKana(), group);
                strSpecialTransformWordKanji = FormatVerb.verbToSe(lwm.getStrKanji(), group);
                // logger.debug("SE: "+strSpecialTransformWordKana);
                if (strSpecialTransformWordKana != null) {
                    vecResult = FormatVerb.getSurfaceForms(strSpecialTransformWordKana, strSpecialTransformWordKanji, strForm,
                            "Group2", tw);
                    return vecResult;
                }
                // System.out.println(strSpecialTransformWord
                // +"is the se format of ");
            } else if (strRef.equalsIgnoreCase("Masyou")) {
                JCALL_NetworkSubNodeStruct p = FormatVerb.verbToInvitationPolite(lwm, true);
                if (p != null) {
                    vecResult = new Vector();
                    vecResult.addElement(p);
                    if (booType) {
                        JCALL_NetworkSubNodeStruct pn = new JCALL_NetworkSubNodeStruct();
                        pn.bAccept = false;
                        pn.bValid = false;
                        pn.setStrError(p.getStrError());
                        pn.setStrKana(p.getStrKana() + "か");
                        pn.setStrKanji(p.getStrKanji() + "か");
                        vecResult.addElement(pn);
                    }
                }
                return vecResult;
            } else {
                // general verb;

                // TW_DFORM
                vecResult = FormatVerb.getSurfaceForms(lwm, strForm, tw);
                return vecResult;

            }

        }
        return vecResult;
    }

    public static Vector getVerbError_INVS_REF(JCALL_Word lwm, JCALL_PredictionDataStruct pdmVerb, int lesson,
            boolean booType, boolean booNegative, boolean tw) {
        Vector vecResult = new Vector();
        // logger.debug("enter getVerbError_INVS_REF");
        Vector vTemp = new Vector();
        String strForm = "";
        String strWordKana = lwm.getStrKana();
        String strWordKanji = lwm.getStrKanji();

        String group = lwm.getStrGroup();
        // logger.debug("verb group: "+group);
        String error = "";
        if (tw) {
            error = "TW_WIF";
        } else {
            error = "DW_WIF";
        }
        if (pdmVerb != null) {
            // logger.debug("find pdm");
            // set the form struct;
            Vector vecForm = pdmVerb.getVecVerbForm();
            for (int i = 0; i < vecForm.size(); i++) {
                String strTemp = (String) vecForm.get(i);
                strForm = strForm + " " + strTemp;
            }
            strForm = strForm.trim();

            // verb's special form
            String strRef = pdmVerb.getStrREF();// TETA,SE,Masyou
            strRef = strRef.trim();
            String strSpecialTransformWord = null;
            String strSpecialTransformWordMasyou = null;
            if (strRef.length() > 0) {
                // logger.debug("a special form: "+strRef);
                if (strRef.equals("TETA")) {
                    Vector vec = pdmVerb.getVecVerbInvalidFrom();
                    if (vec != null && vec.size() > 0) {
                        // deal with different kinds of errors of these
                        for (int i = 0; i < vec.size(); i++) {
                            String str = (String) vec.get(i);
                            String strKana = FormatVerb.verbToOrder(strWordKana, booNegative, group, str);
                            String strKanji = FormatVerb.verbToOrder(strWordKanji, booNegative, group, str);
                            if (strKana != null && strKana.length() > 0) {
                                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                                node.setStrKana(strKana);
                                node.setStrKanji(strKanji);
                                node.bAccept = false;
                                node.bValid = false;
                                node.setStrError(error);
                                node.addToVecSpecific(str);
                                vTemp.addElement(node);
                            }

                        }
                    }
                    return vTemp;
                } else if (strRef.equals("SE")) {
                    Vector vec = pdmVerb.getVecVerbInvalidFrom();
                    if (vec != null && vec.size() > 0) {
                        // deal with different kinds of errors of these
                        for (int i = 0; i < vec.size(); i++) {
                            String str = (String) vec.get(i);
                            if (str.equalsIgnoreCase("SaseSe")) {
                                strSpecialTransformWord = FormatVerb.verbToSe(strWordKana, group, "SaseSe");
                                String strSpecialTransformWordKanji = FormatVerb.verbToSe(strWordKanji, group, "SaseSe");
                                if (strSpecialTransformWord != null) {
                                    // logger.debug("SE: "+strSpecialTransformWord);
                                    Vector vecSe = FormatVerb.getSurfaceForms(strSpecialTransformWord, strSpecialTransformWordKanji,
                                            strForm, "2", true);
                                    if (vecSe != null && vecSe.size() > 0) {
                                        for (int j = 0; j < vecSe.size(); j++) {
                                            JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vecSe.get(j);
                                            node.setStrError(error);
                                            node.addToVecSpecific("SaseSe");
                                            vTemp.addElement(node);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return vTemp;
                    // System.out.println(strSpecialTransformWord
                    // +"is the se format of ");
                } else if (strRef.equals("Masyou")) {
                    Vector vec = pdmVerb.getVecVerbInvalidFrom();
                    if (vec != null && vec.size() > 0) {
                        // deal with different kinds of errors of these
                        for (int i = 0; i < vec.size(); i++) {
                            String str = (String) vec.get(i);
                            if (str.equalsIgnoreCase("Masho")) {
                                strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWordKana, group, "Masho");
                                String strSpecialTransformWordMasyouKanji = FormatVerb.verbToSe(strWordKanji, group, "Masho");
                                if (strSpecialTransformWordMasyou != null) {
                                    // //logger.debug("Masyou: "+strSpecialTransformWordMasyou);
                                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                                    node.setStrKana(strSpecialTransformWordMasyou);
                                    node.setStrKanji(strSpecialTransformWordMasyouKanji);
                                    node.setStrError(error);
                                    node.bValid = false;
                                    node.addToVecSpecific("Masho");
                                    vTemp.addElement(node);
                                }
                            } else if (str.equalsIgnoreCase("T1T2")) {
                                strSpecialTransformWordMasyou = FormatVerb.verbToInvitationPolite(strWordKana, group, "T1T2");
                                String strSpecialTransformWordMasyouKanji = FormatVerb.verbToSe(strWordKanji, group, "T1T2");
                                if (strSpecialTransformWordMasyou != null) {
                                    // //logger.debug("Masyou: "+strSpecialTransformWordMasyou);
                                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                                    node.setStrKana(strSpecialTransformWordMasyou);
                                    node.setStrKanji(strSpecialTransformWordMasyouKanji);
                                    node.setStrError(error);
                                    node.bValid = false;
                                    node.addToVecSpecific("T1T2");
                                    vTemp.addElement(node);
                                }
                            }

                        }
                    }

                }// end else if(strRef.equals("Masyou"))
            } // end if(strRef.length()>0){

            /*
             * still need to modify, for the TETA can't have tense changing, but
             * here we just known it won't
             */
            if (vTemp != null && vTemp.size() > 0) {
                for (int i = 0; i < vTemp.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vTemp.get(i);
                    vecResult.addElement(node);
                    if (booType) {
                        JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                        pnode.setStrKana(node.getStrKana() + "か");
                        pnode.setStrKanji(node.getStrKanji() + "か");
                        pnode.setStrError(error);
                        pnode.bValid = false;
                        pnode.setVecSpecific(node.getVecSpecific());
                        vecResult.addElement(pnode);
                    }
                }
            }
        }
        // add the base tranformation form of this word defined in this lesson
        // like
        // ���ׂ�����(L28) or ���؂�@(L2)

        return vecResult;
    }

    public Vector getVerbError_INVDG_REF(JCALL_Word lwm, JCALL_PredictionDataStruct pdmVerb, int lesson, boolean booType,
            boolean booNegative) throws IOException {
        // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // logger.debug("enter getVerbError_INVDG_REF; word: "+lwm.getStrKana());
        // Vector v = getVerbError_VDG(lwm,lesson,strC);
        Vector vResult = new Vector();

        Vector vVDG = getVerbSubsGroup(lwm, lesson);

        if (vVDG != null && vVDG.size() > 0) {
            for (int i = 0; i < vVDG.size(); i++) {
                JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vVDG.get(i);
                JCALL_Word lwmSubs = JCALL_Lexicon.getInstance().getWordFmID(node.getId());
                if (lwmSubs != null && lwmSubs.getStrKana().length() > 0) {
                    Vector vecTemp = getVerbError_INVS_REF(lwmSubs, pdmVerb, lesson, booType, booNegative, false);
                    if (vecTemp != null && vecTemp.size() > 0) {
                        for (int j = 0; j < vecTemp.size(); j++) {
                            JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vecTemp.elementAt(j);
                            vResult.addElement(pnode);
                        }
                    }

                } else {
                    System.out.println("wrong, word [" + lwm.getStrKana() + "], its VDG: " + node.getStrKana()
                            + " can not find in the lexicon");
                }
            }
        } else {
            // logger.debug("no subs word");
        }
        return vResult;
    }

    /**
     * @param strWord
     *            the target form of one general Verb. no Particle,no NQ,no
     *            Noun;
     * @param lesson
     * @return SubstitutionGroup = Aternative word() + Confusion word();these
     *         are stored in an PredictionDataMeta
     * @return the PredictionDataMeta of strWord is the the form of kana and
     *         kanji
     */

    public static Vector getVerbSubsGroup(JCALL_Word lwm, int lesson) {
        // PredictionDataMeta pdmResult = null;
        Vector vResult = new Vector();
        // logger.debug("enter getVerbSubsGroup, word: "+lwm.getStrKana()+" lesson: "+lesson);
        // System.out.println("enter getVerbSubsGroup, word: "+lwm.getStrKana()+" lesson: "+lesson);

        JCALL_PredictionDataStruct pdm = null;
        String sLesson = new String("" + lesson);
        JCALL_NetworkSubNodeStruct node;

        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category
                if (pdm.isHasLesson(sLesson) && pdm.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB && pdm.compareTo(lwm) == 0) {// same
                                                                                                                             // lesson
                    // System.out.println("Find a substitution iterm");

                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    node.vecSpecific = pdm.vecSpecific;
                    String s = pdm.strSubword;
                    if (s != null && s.length() > 0) {
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(s, pdm.getIntType());
                            if (word != null) {
                                node.setStrKana(word.getStrKana());
                                node.setStrKanji(word.getStrKanji());
                                node.setId(word.getId());
                                vResult.addElement(node);
                                // logger.debug("get one Verb Subs word: "+pdm.getStrWord());
                            } else {
                                logger.error("no word in lexicon: " + s);
                            }

                        }
                        // different with noun
                        else {
                            // node.setStrKana(pdm.strSubword);
                            // node.setStrKanji(pdm.strSubword);
                            // vResult.addElement(node);
                        }

                    } else { // s==null or no subword

                        logger.error("no substitution word: " + pdm.getStrKanji());
                    }

                }
            }

        }// end of for

        return vResult;
    }

    public Vector getParticleErrors(JCALL_Word lwm, int lesson) throws IOException {

        return getParticleSubsGroup(lwm, lesson);
    }

    /*
     * strWord is the target form of one general Particle. no verb,no NQ,no
     * Noun; SubstitutionGroup = Aternative word() + Confusion word(); these are
     * stored in an PredictionDataMeta
     */

    public static Vector getParticleSubsGroup(JCALL_Word lwm, int lesson) {
        String sLesson = new String("" + lesson);
        Vector vResult = new Vector();
        JCALL_PredictionDataStruct pdm = null;
        JCALL_NetworkSubNodeStruct node;
        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category

                if (pdm.isHasLesson(sLesson) && pdm.getIntType() == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL
                        && pdm.compareTo(lwm) == 0) {// same lesson
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    node.vecSpecific = pdm.vecSpecific;
                    String s = pdm.strSubword;
                    if (s != null && s.length() > 0) {
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(s, pdm.getIntType());
                            if (word != null) {
                                node.setStrKana(word.getStrKana());
                                node.setStrKanji(word.getStrKanji());
                                node.setId(word.getId());
                                vResult.addElement(node);
                                // logger.debug("get one NounSubsGroup: "+pdm.getStrWord());
                            } else {
                                logger.error("no word in lexicon: " + s);
                            }

                        } else {

                        }

                    } else { // s==null or no subword

                        logger.error("no sub word");
                    }

                }
            }

        }// end of for

        return vResult;
    }

    // **************
    // get error prediction from surface word;
    // old version prediction file format
    // *******************

    public Vector getNQErrors(JCALL_Word lwm, int lesson) throws IOException {
        // QUANTITY,QUANTITY2,INVS_PCE,INVDG_PCE
        logger.info("int getNQErrors; word [" + lwm.getStrKana() + "]" + " lesson: " + lesson);
        intTotal = 0;
        JCALL_NetworkSubNodeStruct epl = null;
        Vector<JCALL_NetworkSubNodeStruct> vecResult = new Vector();
        Set<String> hs = new HashSet<String>();
        hs.add(lwm.getStrKana());
        String str;
        JCALL_PredictionDataStruct pdm;
        // TW_PCE
        Vector vec = getNQPCE(lwm, true, false, false, false, true, true, true);
        if (vec != null && vec.size() > 0) {
            for (int i = 0; i < vec.size(); i++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                str = pnode.getStrKana();
                str = str.trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(pnode);
                    // vResult.addElement((String) vec.elementAt(i));
                }
            }
            intTotal += vec.size();
            // sss.addDigit("INVS_PCE", vec.size());
            // logger.debug("TW_PCE is ["+ vec.size()+"]");
        }

        // VDG
        Vector v = getNQSubsGroup(lwm, lesson);
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) v.get(i);
                str = pnode.getStrKana();
                str = str.trim();
                if (!hs.contains(str)) {
                    hs.add(str);
                    vecResult.addElement(pnode);
                    // vResult.addElement((String) vec.elementAt(i));
                }
            }

            intTotal += v.size();
            // sss.addDigit("VDG", v.size());
            // logger.debug("NQSubsGroup is ["+v.size()+"]");
        }

        // INVDG_PCE
        if (v != null && v.size() > 0) {
            Vector vVdg = getNQINVDG_PCE(v, lesson);
            if (vVdg != null && vVdg.size() > 0) {
                for (int j = 0; j < vVdg.size(); j++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vVdg.get(j);
                    str = pnode.getStrKana();
                    str = str.trim();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        vecResult.addElement(pnode);
                    }
                }

                intTotal += vVdg.size();
                // sss.addDigit("INVDG_PCE", num);
                // logger.debug("DW_PCE is ["+ vVdg.size()+"]");
            }
        }

        // QUANTITY+QUANTITY2
        String Q1 = getNQNUM(lwm);
        if (Q1 != null && Q1.length() > 0) {
            // logger.debug("number: "+Q1);
            JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(Q1, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);
            if (word != null) {
                if (!hs.contains(word.getStrKana())) {
                    hs.add(word.getStrKana());
                    epl = new JCALL_NetworkSubNodeStruct();
                    epl.bAccept = false;
                    epl.bValid = false;
                    epl.setId(word.getId());
                    epl.setIntType(word.getIntType());
                    epl.setStrError("NQ_SNum");
                    epl.setStrKana(word.getStrKana());
                    epl.setStrKanji(word.getStrKanji());
                    vecResult.addElement(epl);
                    // logger.debug("NQ_SNum: "+ word.getStrKanji());
                }
            }

        }

        Vector vecQ = getNQ_NPlusQ(lwm);
        if (vecQ != null && vecQ.size() > 0) {
            for (int j = 0; j < vecQ.size(); j++) {
                if (!hs.contains((String) vecQ.elementAt(j))) {
                    hs.add((String) vecQ.elementAt(j));
                    String strW = (String) vecQ.elementAt(j);
                    epl = new JCALL_NetworkSubNodeStruct();
                    epl.bAccept = false;
                    epl.bValid = false;
                    epl.setStrError("TW_WForm");
                    epl.setStrKana(strW);
                    epl.setStrKanji(strW);
                    vecResult.addElement(epl);
                }
            }
            intTotal += vecQ.size();
            // sss.addDigit("QUANTITY2", vecQ.size());
            // logger.debug("TW_WForm is ["+ vecQ.size()+"]");
        }

        // logger.debug("all is ["+ intTotal+"]");
        return vecResult;
    }

    private Vector getNQINVDG_PCE(Vector v, int lesson) throws IOException {
        // logger.debug("enter getNQINVDG_PCE: "+ v.size());
        int intCount = 0;
        Vector vecReuslt = new Vector();
        JCALL_Word lwm;
        JCALL_NetworkSubNodeStruct node;
        if (v != null && v.size() > 0) {
            for (int i = 0; i < v.size(); i++) {
                node = ((JCALL_NetworkSubNodeStruct) v.get(i));
                // lwm = JCALL_Lexicon.getWordFmID(node.getId());
                lwm = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(), JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                // INVDG_PCE, watch out! the quantity1 &quantity1 now should be
                // false
                if (lwm != null) {
                    Vector vecPCE = getNQPCE(lwm, true, false, false, true, true, true, false);
                    if (vecPCE != null && vecPCE.size() > 0) {
                        for (int j = 0; j < vecPCE.size(); j++) {
                            vecReuslt.addElement(vecPCE.get(j));
                            intCount++;
                        }
                    }
                }
            }
        }
        return vecReuslt;
    }

    /*
     * strWord is the basic form of the NQ. no noun, no verb; we know this word
     * belongs to the lesson,but not sure this word has the lesson-specific
     * prediction error; SubstitutionGroup = Aternative word() + Confusion
     * word(); these are stored in an PredictionDataMeta which is constructed
     * from an QuantifierNode error Type is VDG Construct new PredictionDataMeta
     * and count its subsGrop's number
     */
    public static Vector getNQSubsGroup(JCALL_Word lwm, int Lesson) throws IOException {
        logger.info("enter getNQSubsGroup");
        Vector vResult = new Vector();
        JCALL_PredictionDataStruct pdm = null;
        String strNumber = lwm.getStrNumber();
        String strQuantifier = lwm.getStrQuantifier();
        JCALL_Word lwmTempN;
        JCALL_Word lwmTempQ;
        JCALL_Word lwmTempR;

        JCALL_Word lwmTemp1 = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);

        if (lwmTemp1 != null) {
            logger.info("find the strQuantifier: " + lwmTemp1.getStrKana());
            String str = lwmTemp1.getStrKana();
            Vector vecSub = getQSubsGroup(lwmTemp1, Lesson);
            if (vecSub != null && vecSub.size() > 0) {
                // logger.debug("subs size: "+ vecSub.size());
                for (int i = 0; i < vecSub.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = (JCALL_NetworkSubNodeStruct) vecSub.get(i);
                    lwmTempQ = JCALL_Lexicon.getInstance().getWordFmKanji(node.getStrKanji(),
                            JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);
                    lwmTempN = JCALL_Lexicon.getInstance().getWordFmStr(strNumber, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);
                    if (lwmTempQ != null && lwmTempN != null) {
                        lwmTempR = JCALL_Lexicon.getInstance().searchNQ(lwmTempN, lwmTempQ);
                        if (lwmTempR != null) { // get the alternative QN name
                            logger.info("subs NQ: " + lwmTempR.getStrKana());
                            JCALL_NetworkSubNodeStruct pnode = new JCALL_NetworkSubNodeStruct();
                            pnode.bAccept = node.bAccept;
                            pnode.bValid = node.bValid;
                            pnode.strClass = node.strClass;
                            pnode.strError = "DQ_SNum";
                            pnode.setIntType(JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                            pnode.vecSpecific = node.vecSpecific;
                            pnode.setStrKana(lwmTempR.getStrKana());
                            pnode.setStrKanji(lwmTempR.getStrKanji());
                            pnode.setId(lwmTempR.getId());
                            vResult.addElement(pnode);
                        }
                    } else {
                        System.out.println("wrong number or wrong quantifier");
                    }

                }
            }
        } else {
            logger.error("wrong word , not find qantifier:+" + strQuantifier);

        }
        return vResult;
    }

    public static Vector getQSubsGroup(JCALL_Word lwm, int lesson) {
        // logger.debug("enter getQSubsGroup:"+lwm.getStrKana());
        // String strWord = lwm.getStrKana();
        Vector vResult = new Vector();
        JCALL_PredictionDataStruct pdm = null;
        String sLesson = new String("" + lesson);
        JCALL_NetworkSubNodeStruct node;

        JCALL_PredictionDatasStruct eps = JCALL_PredictionDatasStruct.getInstance();

        for (int i = 0; i < eps.getVecPDM().size(); i++) {
            pdm = eps.getVecPDM().get(i);
            if (pdm.strClass.equalsIgnoreCase("word")) {// word category
                if (pdm.isHasLesson(sLesson) && pdm.getIntType() == JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER
                        && pdm.compareTo(lwm) == 0) {// same lesson
                    // logger.debug("Find the pdm");
                    node = new JCALL_NetworkSubNodeStruct();
                    node.bAccept = pdm.bAccept;
                    node.bValid = pdm.bValid;
                    node.strClass = pdm.strClass;
                    node.strError = pdm.strError;
                    if (pdm.vecSpecific != null) {
                        node.vecSpecific = pdm.vecSpecific;
                    }
                    String s = pdm.strSubword;
                    // logger.debug("subs word: "+s);
                    if (s != null && s.length() > 0) {
                        if (pdm.bValid) {
                            // find s in lexicon
                            JCALL_Word word = JCALL_Lexicon.getInstance().getWordFmStr(s, JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);
                            if (word != null) {
                                node.setStrKana(word.getStrKana());
                                node.setStrKanji(word.getStrKanji());
                                node.setId(word.getId());
                                vResult.addElement(node);
                                // logger.debug("get one Q Subs word: "+word.getStrKanji());
                            } else {
                                logger.error("no word in lexicon: " + s);
                            }

                        }
                        // different with noun
                        else {
                            // node.setStrKana(pdm.strSubword);
                            // node.setStrKanji(pdm.strSubword);
                            // vResult.addElement(node);
                        }

                    } else { // s==null or no subword

                        logger.error("no sub word");
                    }

                }
            }

        }// end of for
        return vResult;
    }

    /*
     *
     * for all the noun,included numeral+quantifier;
     */

    /**
     * @param strWord
     *            only noun-kana format, no NQ,no Verb,no Particle
     * @param OmitShort
     * @param AddShort
     *            is always false.
     * @param OmitLong
     * @param AddLong
     *            is detected,only the last character could add "��" OmitVoiced
     *            means g-k,d-t ,except quantifier; or (light weight continous
     *            words(like ������) ,but havent done this part
     * @param OmitVoiced
     * @param AddVoiced
     *            :is only checked by the first pronunciation is �� ,and
     *            confined to the japanese word(no english-based words)
     * @return
     * @throws IOException
     */
    public Vector getNQPCE(JCALL_Word lwm, boolean OmitShort, boolean AddShort, boolean OmitLong, boolean AddLong,
            boolean OmitVoiced, boolean AddVoiced, boolean tw) throws IOException {
        Vector vecResult = new Vector();
        String strWrongWord;
        // String strChar = null;
        Vector v;
        String strWord = lwm.getStrKana().trim();
        String error = "";
        if (tw) {
            error = "TW_PCE";
        } else {
            error = "DW_PCE";
        }
        // logger.debug("int getNQPCE; word ["+strWord+"]");
        if (AddLong) {
            strWrongWord = getAddLongWord(strWord);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                // logger.debug("AddLong"+strWrongWord );
                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                node.bValid = false;
                node.strError = error;
                node.setStrKana(strWrongWord);
                node.addToVecSpecific("AddLong");
                node.setStrKanji(strWrongWord);
                vecResult.addElement(node);
            }
        }
        if (AddVoiced) {
            strWrongWord = getAddVoicedWord4NQ(lwm);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                node.bValid = false;
                node.strError = error;
                node.setStrKana(strWrongWord);
                node.addToVecSpecific("AddVoiced");
                node.setStrKanji(strWrongWord);
                vecResult.addElement(node);
            }
        }
        if (OmitVoiced) {// no care about the char position
            strWrongWord = getOmitVoicedWord4NQ(lwm);
            if (strWrongWord != null && strWrongWord.length() > 0) {
                JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                node.bValid = false;
                node.strError = error;
                node.setStrKana(strWrongWord);
                node.addToVecSpecific("OmitVoiced");
                node.setStrKanji(strWrongWord);
                vecResult.addElement(node);
            }
        }
        if (OmitLong) {// happens from the second position
            v = getOmitLongWords(strWord);
            if (v != null && v.size() > 0) {
                for (int i = 0; i < v.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                    node.bValid = false;
                    node.strError = error;
                    node.setStrKana((String) v.elementAt(i));
                    node.addToVecSpecific("OmitLong");
                    node.setStrKanji((String) v.elementAt(i));
                    vecResult.addElement(node);
                }
            }
        }
        if (OmitShort) {
            v = getOmitDoubleConsonentWords(strWord);
            if (v != null && v.size() > 0) {
                for (int i = 0; i < v.size(); i++) {
                    JCALL_NetworkSubNodeStruct node = new JCALL_NetworkSubNodeStruct();
                    node.bValid = false;
                    node.strError = error;
                    node.setStrKana((String) v.elementAt(i));
                    node.addToVecSpecific("OmitShort");
                    node.setStrKanji((String) v.elementAt(i));
                    vecResult.addElement(node);
                }
            }
        }

        return vecResult;

    }

    /*
     * quantity1 = number type quantity2= number+Quantity wrong type, if it
     * should be special transform format return null,when no such type error;
     * or else return responding wrong words;
     */
    public static String getNQNUM(JCALL_Word lwm) {
        // Vector<String> vecResult = new Vector<String>();
        // logger.debug("enter getNQNUM");
        if (lwm != null) { // it is a num+quantifier type;
            String strNumber = lwm.getStrNumber();
            return strNumber;
        }
        return null;
    }

    private static String getReplaceWord(String targetChar, String strWord, int i) {
        String strResult = "";
        String strHead = strWord.substring(0, i);
        String strTail = strWord.substring(i + 1);
        strResult = strHead + targetChar + strTail;
        return strResult;
    }

    /*
     * quantity1 = number type quantity2= number+Quantity wrong type, if it
     * should be special transform format return null,when no such type error;
     * or else return responding wrong words;
     */
    public static Vector getNQ_NPlusQ(JCALL_Word lwm) throws IOException {
        // logger.debug("enter getNQ_NPlusQ");
        Vector<String> vecResult = new Vector<String>();
        // QuantifierNode qn = Lexicon.checkQuantifier(strWord);
        if (lwm != null) { // it is a num+quantifier type;
            String strKanji = lwm.getStrKanji().trim();
            String strKana = lwm.getStrKana().trim();
            String strNumber = lwm.getStrNumber();
            String strQuantifier = lwm.getStrQuantifier();

            JCALL_Word numberWord = JCALL_Lexicon.getInstance().getWordFmStr(strNumber, JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL);

            JCALL_Word quantifierWord = JCALL_Lexicon.getInstance().getWordFmStr(strQuantifier,
                    JCALL_Lexicon.LEX_TYPE_NOUN_QUANTIFIER);

            if (numberWord != null && quantifierWord != null) {
                String strWrongWord1 = numberWord.getStrKana() + quantifierWord.getStrKana();
                String strWrongWord2 = null;
                if (numberWord.getStrAltkana().length() > 0) {
                    strWrongWord2 = numberWord.getStrAltkana() + quantifierWord.getStrKana();
                }
                if (strKana.equals(strWrongWord1)) {
                    return null;
                } else {
                    if (strWrongWord2 == null) {
                        vecResult.addElement(strWrongWord1);
                    } else {
                        if (strKana.equals(strWrongWord2)) {
                            return null;
                        } else {
                            vecResult.addElement(strWrongWord2);
                            vecResult.addElement(strWrongWord1);
                        }
                    }
                }

            } else {
                // logger.debug("number [" + strNumber + "]"
                // + " or quantifier [" + strQuantifier
                // + "] is not found in the lexicon");
            }
        }
        return vecResult;
    }

    // ////////////////////
    // ////////
    // ////////
    // ////////////////////////////////////////////

    public static void main(String[] args) throws IOException {
        JCALL_PredictionProcess pp = new JCALL_PredictionProcess();
        // LexiconWordMeta lwm = new LexiconWordMeta();
        // SentenceStatisticStructure sss = new SentenceStatisticStructure();
        // lwm.setStrKana("いっぴき");
        // lwm.setStrKanji("一匹") ;
        // lwm.setStrCategory1("一");
        // lwm.setStrCategory2("匹");
        /*
         *
         * lwm.setStrKana("いっぱい"); lwm.setStrKanji("一杯") ;
         * lwm.setStrCategory1("一"); lwm.setStrCategory2("杯"); Vector v =
         * pp.getNQError(lwm,21, sss); for (int i = 0; i < v.size(); i++) {
         * System.out.println("getVerbError ["+i+"] "+v.get(i)); } int a =
         * pp.getNQErrorNumber(lwm, 21, sss ); System.out.println(a);
         */
        /*
         * lwm.setStrKana("ほんやくする"); lwm.setStrKanji("翻訳する");
         * lwm.setStrAttribute("Group3");
         */
        // pp.getVerbError_VS_DFORM(lwm, 2, sss);
        // int a = pp.getVerbError_VDG_SFORM(lwm, 2, sss, "outlesson");

        /*
         *
         * noun test code
         */
        // JCALL_Word lwm = new JCALL_Word();
        // lwm.setStrKana("ぎゅうにゅう");
        // lwm.setStrKanji("牛乳") ;
        // // lwm.se("ミルク");
        // Vector v = pp.getNounError(lwm,1);
        // for (int i = 0; i < v.size(); i++) {
        // System.out.println("getVerbError ["+i+"] "+v.get(i));
        // }
        // int a = pp.getNounErrorNumber(lwm, 2, sss);
        // System.out.println(a);

        /*
         * verb test code
         */
        // "もらう",22;
        JCALL_Word lwm = new JCALL_Word();
        // lwm = JCALL_Lexicon.getWordFmStr("行く", JCALL_Lexicon.LEX_TYPE_VERB);
        // "牛乳"
        // lwm = JCALL_Lexicon.getWordFmStr( "牛乳", JCALL_Lexicon.LEX_TYPE_NOUN);
        // lwm = JCALL_Lexicon.getWordFmStr( "は",
        // JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL);
        // lwm = JCALL_Lexicon.getWordFmStr( "それ",
        // JCALL_Lexicon.LEX_TYPE_NOUN_DEFINITIVES);

        lwm = JCALL_Lexicon.getInstance().getWordFmStr("さんまい", JCALL_Lexicon.LEX_TYPE_NUMQUANT);

        if (lwm != null) {
            System.out.println("kana: " + lwm.getStrKana() + "Kanji: " + lwm.getStrKanji() + " Type: " + lwm.getIntType());
        } else {
            System.out.println("lwm is null");
        }
        // String[] v = pp.getVerbErrors(lwm, 7, true, false);
        // Vector v = pp.getVerbErrors(lwm, 28);
        // Vector v = pp.getNounErrors(lwm, 1);
        Vector v = pp.getNQErrors(lwm, 21);
        // Vector v = pp.getParticleErrors(lwm, 1);

        if (v != null) {
            for (int i = 0; i < v.size(); i++) {
                JCALL_NetworkSubNodeStruct epl = (JCALL_NetworkSubNodeStruct) v.elementAt(i);
                System.out.println("getVerbError [" + i + "]: " + epl.getStrKana());
            }
        } else {
            System.out.println("Err, no verb errors");
        }

        // int a = pp.getVerbErrorNumber(lwm, 22, sss, "outlesson");
        // System.out.println(a);

    }

    public void getSubstitution(JCALL_NetworkNodeStruct gw, int lesson, SentenceWordDto sentenceWordDto) {
     // logger.debug("enter getSubstitution");

        JCALL_Word call_word = gw.call_word;
//        CALL_sentenceWordStruct tempSentenceWord = gw.sword;

        JCALL_PredictionProcess pp;
        try {
            pp = new JCALL_PredictionProcess();

            // CALL_sentenceWordStruct tempSentenceWord = gw.sword;
            // String strForm = tempSentenceWord.getFormString();
            // String strTransRule = tempSentenceWord.getTransformationRule();

            if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_VERB
                    || call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE
                    || call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJVERB) {

                getVerbSubstitution(gw, lesson, sentenceWordDto);

            } else if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_NUMQUANT || sentenceWordDto.getUseCounterSettings()) {
                // get the predicted words, then add to the network;
                // || tempSentenceWord.isUseCounterSettings()
                // logger.debug("Counter prediction");
                String strCounter = sentenceWordDto.getTopWordStringKanji();

                if (strCounter != null && strCounter.length() > 0) {

                    // logger.debug("target counter string: "+ strCounter);
                    JCALL_Word wordTemp = JCALL_Lexicon.getInstance().getWordFmKanji(strCounter, JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                    if (wordTemp != null) {
                        // logger.debug("Counter kanji: "+
                        // wordTemp.getStrKanji());
                        // Now judge if its a quantifier+number;
                        // String snum = wordTemp.getStrNumber();
                        String sQuantifier = wordTemp.getStrQuantifier();
                        if (sQuantifier == null || sQuantifier.trim().length() == 0) {
                            // logger.debug("Find a wrong counter; Search again;");
                            String strCounterkana = sentenceWordDto.getTopWordStringKanji();
                            JCALL_Word wordTemp2 = JCALL_Lexicon.getInstance().getWordFmStr(strCounterkana,
                                    JCALL_Lexicon.LEX_TYPE_NUMQUANT);
                            if (wordTemp2 != null && wordTemp2.strKana != null) {
                                wordTemp = wordTemp2;
                            }
                        }
                        Vector vec = pp.getNQErrors(wordTemp, lesson);
                        if (vec != null && vec.size() > 0) {
                            for (int i = 0; i < vec.size(); i++) {
                                JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                                gw.addPNode(pnode);
                            }
                        }

                    }
                }

            } else if (call_word.getIntType() == JCALL_Lexicon.LEX_TYPE_PARTICLE_AUXIL) {

                // get the predicted words, then add to the network;
                getParticleSubstitution(gw, lesson);

            } else {// the only possible is noun
                // get the predicted words, then add to the network;
                Vector vec = pp.getNounSubstitution(call_word, lesson);
                if (vec != null && vec.size() > 0) {
                    for (int i = 0; i < vec.size(); i++) {
                        JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                        gw.addPNode(pnode);
                    }
                }

            }

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    private void getVerbSubstitution(JCALL_NetworkNodeStruct gw, int lesson, SentenceWordDto sentenceWordDto) {
     // VS_DFORM,VDG_SFORM,VDG_DFORM,INVS_REF,INVDG_REF
        // JCALL_Word lwm, CALL_sentenceWordStruct tempSentenceWord

        // Vector<PNode> vecResult = new Vector();

        // to prevent repetiveness
        Set<String> hs = new HashSet<String>();
        Vector vec;
        String str;
        int intTotal = 0;

        JCALL_Word word = gw.call_word;
//        CALL_sentenceWordStruct tempSentenceWord = gw.sword;
        CALL_database db = new CALL_database();

        // logger.debug("enter getVerbSubstitution, word: "+
        // word.getStrKanji());

        String strForm = sentenceWordDto.getFullFormString();
        String strTransRule = sentenceWordDto.getTransformationRule();

        if (strForm != null && strForm.length() > 0) {

            logger.info("sentence word FullForm: " + strForm + " transformation rule: " + strTransRule);

            // (int sign, int tense, int style, int type)
            CALL_Form call_form = new CALL_Form(
                    Integer.valueOf(sentenceWordDto.getSign()),
                    Integer.valueOf(sentenceWordDto.getTense()),
                    Integer.valueOf(sentenceWordDto.getPolitenes()),
                    Integer.valueOf(sentenceWordDto.getQuestion()));

            String answer = sentenceWordDto.getTopWordStringKanna();

            logger.info("Top Word String: " + answer);

            vec = getTW_DFORM(db, word, strForm, strTransRule, answer); // including
                                                                        // the
                                                                        // answer
                                                                        // and
                                                                        // marked
                                                                        // correct
                                                                        // in
                                                                        // error
                                                                        // type

            if (vec != null && vec.size() > 0) {
                // int errsize =0;
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // errsize++;
                    }
                }
                intTotal += vec.size();
                // logger.debug("TW_DFORM(included correct one):"+vec.size());
            }

            vec = getDW_TFORM(db, word, strTransRule, lesson, call_form); // including
                                                                          // the
                                                                          // answer
                                                                          // and
                                                                          // marked
                                                                          // correct
                                                                          // in
                                                                          // error
                                                                          // type

            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("DW_TFORM: "+vec.size());
            }

            vec = getDW_DFORM(db, word, strForm, strTransRule, lesson, call_form); // including
                                                                                   // the
                                                                                   // answer
                                                                                   // and
                                                                                   // marked
                                                                                   // correct
                                                                                   // in
                                                                                   // error
                                                                                   // type
            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("DW_DFORM: "+vec.size());
            }

            vec = getTW_WIF(db, word, strForm, lesson); // including the answer
                                                        // and
                                                        // marked correct in
                                                        // error
                                                        // type
            if (vec != null && vec.size() > 0) {
                for (int i = 0; i < vec.size(); i++) {
                    JCALL_NetworkSubNodeStruct pnode = (JCALL_NetworkSubNodeStruct) vec.get(i);
                    str = pnode.getStrKana();
                    if (!hs.contains(str)) {
                        hs.add(str);
                        gw.addPNode(pnode);
                        // vecResult.addElement(pnode);
                    }
                }
                intTotal += vec.size();
                // logger.debug("TW_WIF: "+vec.size());
            }

        }// end if

    }

}
