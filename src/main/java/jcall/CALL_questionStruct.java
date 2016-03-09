// /////////////////////////////////////////////////////////////////
// Verb Rule Structures - holds the information about how to construct
// the various inflections for each verb group.
// All data is loaded from the verbRules.txt file.
//
// /////////////////////////////////////////////////////////////////

package jcall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jcall.config.Configuration;
import jcall.db.JCALL_NetWorkGeneration;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;
import jp.co.gmo.rs.ghs.dto.FormSettingGrammarDto;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.dto.WordStructDto;
import jp.co.gmo.rs.ghs.jcall.entity.LessonGHS;
import jp.co.gmo.rs.ghs.jcall.entity.WordGHS;
import jp.co.gmo.rs.ghs.util.JCallUtil;

import org.apache.log4j.Logger;

public class CALL_questionStruct {

    static Logger logger = Logger
            .getLogger(CALL_questionStruct.class.getName());

    // Statics
    static final String NO_GROUP_NAME = new String("null");

    static final int QTYPE_CONTEXT = 0;
    static final int QTYPE_VERB = 1;
    static final int QTYPE_VOCAB = 2;
    int index = 0;
    public HttpServletRequest servletRequest;

    // Fields
    CALL_lessonStruct lesson;
    CALL_configDataStruct config;
    CALL_configDataStruct gconfig;
    CALL_database db;

    // What is generated
    CALL_conceptInstanceStruct instance;
    CALL_diagramStruct diagram;
    CALL_sentenceStruct sentence;
    CALL_sentenceHintsStruct hints;
    CALL_lessonQuestionStruct lessonQuestion;
    CALL_lessonConceptStruct lessonConcept;
    CALL_stringPairStruct parameter;
    LessonGHS lessonGHS;
    Vector<CALL_lessonConceptStruct> concepts;

    /*
     * Getter and setter propertiesAdd by Quyettv
     */
    public CALL_lessonStruct getLesson() {
        return lesson;
    }

    public void setLesson(CALL_lessonStruct lesson) {
        this.lesson = lesson;
    }

    public CALL_configDataStruct getConfig() {
        return config;
    }

    public void setConfig(CALL_configDataStruct config) {
        this.config = config;
    }

    public CALL_configDataStruct getGconfig() {
        return gconfig;
    }

    public void setGconfig(CALL_configDataStruct gconfig) {
        this.gconfig = gconfig;
    }

    public CALL_database getDb() {
        return db;
    }

    public void setDb(CALL_database db) {
        this.db = db;
    }

    public CALL_conceptInstanceStruct getInstance() {
        return instance;
    }

    public void setInstance(CALL_conceptInstanceStruct instance) {
        this.instance = instance;
    }

    public CALL_diagramStruct getDiagram() {
        return diagram;
    }

    public void setDiagram(CALL_diagramStruct diagram) {
        this.diagram = diagram;
    }

    public CALL_sentenceStruct getSentence() {
        return sentence;
    }

    public void setSentence(CALL_sentenceStruct sentence) {
        this.sentence = sentence;
    }

    public CALL_sentenceHintsStruct getHints() {
        return hints;
    }

    public void setHints(CALL_sentenceHintsStruct hints) {
        this.hints = hints;
    }

    public CALL_lessonQuestionStruct getLessonQuestion() {
        return lessonQuestion;
    }

    public void setLessonQuestion(CALL_lessonQuestionStruct lessonQuestion) {
        this.lessonQuestion = lessonQuestion;
    }

    public CALL_lessonConceptStruct getLessonConcept() {
        return lessonConcept;
    }

    public void setLessonConcept(CALL_lessonConceptStruct lessonConcept) {
        this.lessonConcept = lessonConcept;
    }

    public CALL_stringPairStruct getParameter() {
        return parameter;
    }

    public void setParameter(CALL_stringPairStruct parameter) {
        this.parameter = parameter;
    }

    public Vector getNetwork() {
        return network;
    }

    public void setNetwork(Vector network) {
        this.network = network;
    }

    public Vector getFormGroups() {
        return formGroups;
    }

    public void setFormGroups(Vector formGroups) {
        this.formGroups = formGroups;
    }

    public Vector getForms() {
        return forms;
    }

    public void setForms(Vector forms) {
        this.forms = forms;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public LessonGHS getLessonGHS() {
        return lessonGHS;
    }

    public void setLessonGHS(LessonGHS lessonGHS) {
        this.lessonGHS = lessonGHS;
    }

    public Vector<CALL_lessonConceptStruct> getConcepts() {
        return concepts;
    }

    public void setConcepts(Vector<CALL_lessonConceptStruct> concepts) {
        this.concepts = concepts;
    }

    /*
     * add by wang
     */
    // JCALL_JulianGrammarStruct juliangrammar;
    Vector network; // A vector of JCALL_NetworkNodeStruct
    Vector formGroups; // A vector of form group names (eg. "Style")
    Vector forms; // A vector of CALL_stringPairsStruct (eg. "Polite" &
                  // "disabled")

    int questionType;



    public CALL_questionStruct() {}

    // Takes the database and parent (lesson)
    public CALL_questionStruct(CALL_database data, CALL_configDataStruct lc,
            CALL_configDataStruct gc, CALL_lessonStruct l, int type,
            HttpServletRequest request) {
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
        servletRequest = request;
        network = null;

        // Now generate the question itself (ie. sentence concept, lattice,
        // diagram etc)
        try {
            generate();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CALL_questionStruct(CALL_database data, CALL_configDataStruct lc,
            CALL_configDataStruct gc, int type, CALL_lessonConceptStruct concept) {
        db = data;
        config = lc;
        gconfig = gc;
        instance = null;
        sentence = null;
        hints = null;
        questionType = type;

        // Now generate the question itself (ie. sentence concept, lattice,
        // diagram etc)
        try {
            // Generate by concept
            generate(concept);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CALL_questionStruct(CALL_database data, CALL_configDataStruct lc,
            CALL_configDataStruct gc, int type, LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConcepts, List<CallJVocabularyDto> callJVoca) {
        db = data;
        config = lc;
        gconfig = gc;
        instance = null;
        sentence = null;
        hints = null;
        questionType = type;

        // Now generate the question itself (ie. sentence concept,
        // lattice,diagram etc)
        try {
            generateGHS(mapConcepts, callJVoca);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Count concept of lesson
     *
     * @param data
     * @param lc
     * @param gc
     * @param l
     */
    public CALL_questionStruct(CALL_database data, CALL_configDataStruct lc,
            CALL_configDataStruct gc, CALL_lessonStruct l, String questionName) {
        db = data;
        config = lc;
        gconfig = gc;
        lesson = l;

        // Now generate the question itself (ie. sentence concept,
        // lattice,diagram etc)
        try {
            countConceptOfLesson(questionName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Takes the database and parent (lesson)
    public CALL_questionStruct(CALL_database data, CALL_configDataStruct lc, CALL_configDataStruct gc, CALL_lessonStruct l, int type)
    {
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

        network = null;

        // Now generate the question itself (ie. sentence concept, lattice,
        // diagram etc)
        try {
            generate();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Generate voca grammar file from question
     *
     * @return
     * @throws IOException
     */
    public CALL_questionStruct(CALL_lessonStruct l, HttpServletRequest request, List<SentenceWordDto> swDto, String userName)
            throws IOException {
        lesson = l;
        servletRequest = request;

        // Now generate the question itself (ie. sentence concept, lattice,
        // diagram etc)
        generate(swDto, userName);

    }

    @SuppressWarnings("unchecked")
    public boolean countConceptOfLesson(String questionName) throws Exception {
        Vector<CALL_lessonQuestionStruct> questions = null;
        CALL_lessonQuestionStruct question = null;
        concepts = new Vector<CALL_lessonConceptStruct>();
        questions = (Vector<CALL_lessonQuestionStruct>) lesson.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            // Get question
            question = (CALL_lessonQuestionStruct) questions.elementAt(i);
            if (questionName.equals(question.question)) {
                // Get all concepts from question and add to concepts
                concepts.addAll((Vector<CALL_lessonConceptStruct>) question.getConcepts());
            }
        }
        return true;
    }

    /**
     * Generate all sentences
     *
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public boolean generateGHS(LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConcepts, List<CallJVocabularyDto> callJVoca) throws Exception {

        // Init
        List<List<WordGHS>> wordList = new ArrayList<List<WordGHS>>();
        lessonGHS = new LessonGHS();

        // Get all concept instance
        for (Entry<String, Vector<CALL_lessonConceptStruct>> entryMap : mapConcepts.entrySet()) {

            for (CALL_lessonConceptStruct lessconConcept : entryMap.getValue()) {

                Vector<CALL_conceptInstanceStruct> conceptsIn = db.concepts.getInstanceGHS(lessconConcept.concept, questionType, lessconConcept.grammar, callJVoca, true);
                CALL_grammarRuleStruct grule = null;

                for (CALL_conceptInstanceStruct instanceStruct : conceptsIn) {
                    Vector<CALL_sentenceStruct> sentenceStructs = null;
                    Vector<String> slotConcept = instanceStruct.getLabel();
                    if (grule == null) {
                        // Start by getting the top level rule
                        grule = db.grules.getGrammarRule(null, instanceStruct.getGrammar());
                    }
                    // Get all sentence from 1 instance
                    sentenceStructs = getSentencesFromInstance(null, instanceStruct, grule);

                    for (CALL_sentenceStruct callSentence : sentenceStructs) {
                        // Set value for sentence
                        sentence = callSentence;

                        // Get Hints
                        hints = new CALL_sentenceHintsStruct(this);
                        // Init List
                        List<WordGHS> words = new ArrayList<WordGHS>();
                        SentenceWordDto sentenceWordDto = null;
                        // Get sentence word
                        Vector<CALL_sentenceWordStruct> sentenceWord = sentence.getSentenceWords();
                        // Check rules
                        int isCheckWord = isCheckRulesOfWord(sentenceWord, callJVoca, slotConcept);
                        // Check rules of word
                        if (isCheckWord == 0) {
                            // Get word hint
                            Vector<CALL_wordHintsStruct> wordHintsStructs = hints.getWordHints();
                            int wordListSize = sentenceWord.size();

                            int wordHintSize = wordHintsStructs.size();
                            for (int j = 0; j < wordHintSize; j++) {

                                // Init word
                                WordGHS word = new WordGHS();
                                // Set value for word
                                word.setBaseKana(wordHintsStructs.elementAt(j).getHints()[2].getHintKana());
                                word.setBaseKanji(wordHintsStructs.elementAt(j).getHints()[2].getHintKanji());
                                word.setWordType(wordHintsStructs.elementAt(j).getHints()[0].getHintKana());
                                word.setTypeSlot(sentenceWord.get(j).getGrammarRule());

                                for (int k = 0; k < wordHintsStructs.elementAt(j).getHints().length; k++) {
                                    if (wordHintsStructs.elementAt(j).getHints()[k] != null) {
                                        if (wordHintsStructs.elementAt(j).getHints()[k].getHintKana() != null) {
                                            word.setKana(wordHintsStructs.elementAt(j).getHints()[k].getHintKana());
                                            word.setKanji(wordHintsStructs.elementAt(j).getHints()[k].getHintKanji());
                                            // data for speech result
                                            for (int m = 0; m < wordListSize; m++) {
                                                // Get sentence word
                                                CALL_sentenceWordStruct swS = (CALL_sentenceWordStruct) sentenceWord.elementAt(m);
                                                if (swS.getWord().getKanji().equals(wordHintsStructs.elementAt(j).getHints()[k].getHintKanji())) {
                                                    // Init Object
                                                    sentenceWordDto = new SentenceWordDto();
                                                    // Set value for sentence word
                                                    sentenceWordDto.setSentence(wordHintsStructs.elementAt(j).getHints()[k].getHintKana());
                                                    sentenceWordDto.setIndex(String.valueOf(j));
                                                    sentenceWordDto.setGrammarRule(swS.getGrammarRule());
                                                    sentenceWordDto.setComponentName(swS.getComponentName());
                                                    sentenceWordDto.setUseCounterSettings(swS.isUseCounterSettings());
                                                    sentenceWordDto.setTopWordStringKanna(swS.getTopWordString(CALL_io.kana));
                                                    sentenceWordDto.setTopWordStringKanji(swS.getTopWordString(CALL_io.kanji));
                                                    sentenceWordDto.setFullFormString(swS.getFullFormString());
                                                    sentenceWordDto.setTransformationRule(swS.getTransformationRule());
                                                    sentenceWordDto.setSign(String.valueOf(swS.getSign()));
                                                    sentenceWordDto.setTense(String.valueOf(swS.getTense()));
                                                    sentenceWordDto.setPolitenes(String.valueOf(swS.getPoliteness()));
                                                    sentenceWordDto.setQuestion(String.valueOf(swS.getQuestion()));
                                                    sentenceWordDto.setLessonNo(entryMap.getKey());

                                                    WordStructDto wordStructDto = new WordStructDto();

                                                    wordStructDto.setKanji(wordHintsStructs.elementAt(j).getHints()[2].getHintKanji());
                                                    wordStructDto.setType(String.valueOf(swS.getWord().type));
                                                    sentenceWordDto.setWordStruct(wordStructDto);
                                                    word.setEnglish(swS.getWord().english);

                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                word.setRomaji(JCallUtil.strKanaToRomaji(word.getKana()));
                                word.setSentenceWordDto(sentenceWordDto);
                                word.setSlotName(slotConcept);
                                words.add(word);
                            }
                        }
                        if (words.size() > 0) {
                            // Set value in List
                            wordList.add(words);
                        }
                    }
                }
            }

        }
        // Set value in Lesson
        lessonGHS.setWordList(wordList);
        return true;
    }

    /**
     *
     * @param sentenceWord
     * @param callJVoca
     * @return
     */
    private int isCheckRulesOfWord(Vector<CALL_sentenceWordStruct> sentenceWord, List<CallJVocabularyDto> callJVoca, Vector<String> slotConcept) {
        int flag = 0;
        for (int i = 0; i < sentenceWord.size(); i++) {
            if (sentenceWord.elementAt(i).transformationRule != null && checkSlotConcept(sentenceWord.elementAt(i).componentName, slotConcept)) {
                String kana = ConstValues.CONST_STRING_EMPTY;
                String kanji = ConstValues.CONST_STRING_EMPTY;
                CALL_wordStruct wordStruct = sentenceWord.elementAt(i).getWord();
                // If word is "is" continue
                if (wordStruct.english.equals("is") || wordStruct.english.equals("go")) {
                    flag = 0;
                    continue;
                } else {
                    // Get value from sentence word
                    String rName = sentenceWord.elementAt(i).transformationRule;
                    int sign = sentenceWord.elementAt(i).sign;
                    int tense = sentenceWord.elementAt(i).tense;
                    int politeness = sentenceWord.elementAt(i).politeness;
                    int question = sentenceWord.elementAt(i).question;

                    // Get word kana and kanji from rules
                    kana = db.applyTransformationRule(wordStruct, rName, sign, tense, politeness, question, false);
                    kanji = db.applyTransformationRule(wordStruct, rName, sign, tense, politeness, question, true);
                    boolean isCheck = false;
                    for (CallJVocabularyDto vocabularyDto : callJVoca) {
                        // If word kana contains voca is break
                        if (vocabularyDto.getVocabulary() != null && vocabularyDto.getVocabulary().equals(kana)) {
                            flag = 0;
                            isCheck = true;
                            break;
                        }
                        // If word kanji contains voca is break
                        if (vocabularyDto.getVocabulary() != null && vocabularyDto.getVocabulary().equals(kanji)) {
                            flag = 0;
                            isCheck = true;
                            break;
                        }
                    }
                    // Check word not contains in voca
                    if (!isCheck) {
                        flag = 1;
                        break;
                    }
                }

            }
        }

        return flag;
    }

    /**
     * Check name slot of concept
     *
     * @param nameSlot
     * @param slotConcept
     * @return
     */
    private boolean checkSlotConcept(String nameSlot, Vector<String> slotConcept) {
        for (int i = 0; i < slotConcept.size(); i++) {
            if (slotConcept.get(i).equals(nameSlot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get sentences form instance
     *
     * @param instance
     * @param grule
     * @return
     */
    public Vector<CALL_sentenceStruct> getSentencesFromInstance(CALL_database dataBase, CALL_conceptInstanceStruct instance, CALL_grammarRuleStruct grule) {
        // Init
        Vector<CALL_sentenceStruct> sentenceStructs = new Vector<CALL_sentenceStruct>();
        Vector<CALL_formInstanceStruct> formInstance = null;

        // For the sentences form calculations
        Vector formVector = null;
        String formName = null;
        CALL_formStruct form = null;
        List<FormSettingGrammarDto> listFormSetting = new ArrayList<FormSettingGrammarDto>();

        Vector<CALL_formStruct> formSettingStructs = null;
        Vector<CALL_formInstanceStruct> formSettings = null;
        Vector<String> formNames = null;
        // Check null data base
        if(db == null){
            db = dataBase;
        }
        if (instance.getType() == CALL_questionStruct.QTYPE_CONTEXT)
        {

            // Resolve using the recursive function
            if (grule != null)
            {
                // STEP 1: PICK AND ADD FORMS SPECIFIED BY THIS RULE TO THE
                // SENTENCE RULE SETTINGS
                // =====================================================================================

                logger.debug("STEP 1: PICK AND ADD FORMS SPECIFIED BY THIS RULE TO THE SENTENCE RULE SETTINGS");
                logger.debug("Different <form> size : " + grule.formSettings.size());
                // logger.debug("For each <form>, pick one form setting template from the group at random ");

                for (int m = 0; m < grule.formSettings.size(); m++)
                {

                    formNames = new Vector<String>();
                    formVector = (Vector) grule.formSettings.elementAt(m);
                    formName = (String) grule.formNames.elementAt(m);
                    formNames.add(formName);

                    if ((formVector != null) && (formName != null))
                    {
                        // Now, pick one of the form templates from the form
                        // group at random
                        if (formVector.size() > 0)
                        {

                            logger.debug("choose one setting in formVector, seletable size(): " + formVector.size());
                            for (int j = 0; j < formVector.size(); j++) {

                                formSettingStructs = new Vector<CALL_formStruct>();
                                form = (CALL_formStruct) formVector.get(j);
                                formSettingStructs.add(form);

                                if (form != null)
                                {
                                    logger.debug("Detemine settings and its instance for form: " + formName);
                                    logger.debug("Detemine form setting: " + form.toString());
                                    // Concept first Check formInstance
                                    formInstance = form.pickSettingsGHS(db, instance);
                                    for (CALL_formInstanceStruct formInstanceStruct : formInstance) {
                                        if (formInstanceStruct != null)
                                        {
                                            // Check form second in grammar
                                            if (m > 0 && listFormSetting.size() > 0) {
                                                for (int i = 0; i < listFormSetting.size(); i++) {
                                                    listFormSetting.get(i).getFormNames().add(formName);
                                                    listFormSetting.get(i).getFormSettings().add(formInstanceStruct);
                                                    listFormSetting.get(i).getFormSettingStructs().add(form);
                                                }
                                            } else {// Check form first in
                                                    // grammar

                                                formSettings = new Vector<CALL_formInstanceStruct>();
                                                formSettings.add(formInstanceStruct);

                                                FormSettingGrammarDto settingGrammarDto = new FormSettingGrammarDto();
                                                settingGrammarDto.setFormNames(formNames);
                                                settingGrammarDto.setFormSettings(formSettings);
                                                settingGrammarDto.setFormSettingStructs(formSettingStructs);

                                                listFormSetting.add(settingGrammarDto);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                for (FormSettingGrammarDto grammarDto : listFormSetting) {
                    // Get sentences
                    sentenceStructs.add(new CALL_sentenceStruct(db, grule, instance, grammarDto));
                }

            }
        }

        return sentenceStructs;
    }

    /**
     * Generate by concept
     *
     * @param concept
     *            the concept
     * @return boolean
     * @throws IOException
     *             the IOException
     */
    public boolean generate(CALL_lessonConceptStruct concept) throws IOException {

        // 3) Get the concept instance
        instance = db.concepts.getInstance(concept.concept, questionType);
        // logger.info("Concept instance: "+ instance.toString());
        instance.print_debug();
        // 4) Generate the sentence
        sentence = new CALL_sentenceStruct(db, concept.grammar, instance);
        // Finally, generate the hints
        hints = new CALL_sentenceHintsStruct(this);
        return true;
    }

    public boolean generate() throws IOException {
        int size;
        double weight;
        boolean match;
        boolean useThisFormGroup;
        Random rand;
        String restrictionString;
        String questionString;

        CALL_formDescGroupStruct formGroup;
        CALL_stringPairsStruct formPairs;

        // CALL_SentenceGrammar sg;
        JCALL_NetWorkGeneration sg;

        rand = new Random();

        // 1) Pick a question
        weight = rand.nextDouble() * lesson.questionWeightTotal;
        lessonQuestion = lesson.getQuestion(weight);
        if (lessonQuestion == null) {
            // Error
            // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
            // "Failed to aquire lesson question during question generation");
            return false;
        } else {

            // logger.debug("Pick a question: "+lessonQuestion.getQuestion() );

        }

        // 2) Pick a concept from within that question
        weight = rand.nextDouble() * lessonQuestion.conceptWeightTotal;
        lessonConcept = lessonQuestion.getConcept(weight);
        if (lessonConcept == null) {
            // Error
            // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
            // "Failed to aquire lesson concept during question generation");
            return false;
        } else {
            // logger.debug("From that question, Pick a concept : "+lessonConcept.concept
            // +" Its grammar: "+ lessonConcept.grammar );
        }

        // 3) Get the concept instance
        instance = db.concepts.getInstance(lessonConcept.concept, questionType);
        if (instance == null) {
            // Error
            // CALL_debug.printlog(CALL_debug.MOD_LESSON, CALL_debug.ERROR,
            // "Failed to generate concept instance during question generation");

            return false;
        } else {
            // Add Chinese meaning now;
            // VINH remove
            // instance.addLanguage(ConfigInstant.CONFIG_LabelOption_CH);

        }

        // logger.info("Concept instance: "+ instance.toString());
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

        /*
         * add by wang
         */

        // 4.5) Generate Julian grammar
        Configuration configxml = Configuration.getConfig();
        String str = configxml.getItemValue("systeminfo", "laststudent");
        String style = configxml.getItemValue(str, "input");
        if (style != null) {
            if (style.equalsIgnoreCase("speech")) {
                sg = new JCALL_NetWorkGeneration(servletRequest);
                network = sg.sentenceGrammar(sentence, lesson.index);
            } else {
                network = null;
            }
        }

        // logger.debug("afet sg.sentenceGrammar, in CALL_questionStruct");

        // 5) Get the appropriate diagram

        if (questionType == QTYPE_CONTEXT) {
            // A standard question, based on a whole sentence - thus the full
            // diagram is needed
            diagram = lesson.getDiagram(lessonConcept.diagram);
        } else if (questionType == QTYPE_VOCAB) {
            // A one component diagram
            diagram = new CALL_diagramStruct("Word");
            diagram.singleWord();
        }

        // 6) Set the forms based on grammar & instance
        if (questionType == QTYPE_CONTEXT) {
            for (int i = 0; i < lesson.formGroups.size(); i++) {
                formGroup = (CALL_formDescGroupStruct) lesson.formGroups
                        .elementAt(i);

                if (formGroup != null) {
                    // Check whether this group is picky over which questions
                    // may use it
                    useThisFormGroup = true;
                    if ((formGroup.questions != null)
                            && (formGroup.questions.size() > 0)) {
                        useThisFormGroup = false;
                        for (int j = 0; j < formGroup.questions.size(); j++) {
                            questionString = (String) formGroup.questions
                                    .elementAt(j);
                            if (questionString != null) {
                                if (questionString
                                        .equals(lessonQuestion.question)) {
                                    // This question does use this form
                                    useThisFormGroup = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (useThisFormGroup) {
                        formPairs = (CALL_stringPairsStruct) formGroup
                                .getFormText(this);
                        if (formPairs != null) {
                            // Add the display string pairs (eg. "Polite",
                            // "false")
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
        hints = new CALL_sentenceHintsStruct(this);

        hints.print_debug();

        // logger.debug("before return in generate()");

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
                    pair = (CALL_stringPairStruct) pairs.parameters
                            .elementAt(j);
                    if (pair != null) {
                        // We have a form string
                        if (pair.parameter != null) {
                            if (pair.value.equals("true")) {
                                // Form in use - add to string
                                if (formString == null) {
                                    formString = pair.parameter;
                                } else {
                                    formString = formString + ", "
                                            + pair.parameter;
                                }
                            }
                        }
                    }
                }
            }
        }

        return formString;
    }

    /**
     * @param swDto
     * @return
     * @throws IOException
     */
    public boolean generate(List<SentenceWordDto> swDto, String userName) throws IOException {

        // CALL_SentenceGrammar sg;
        JCALL_NetWorkGeneration sg;

        // 4.5) Generate Julian grammar
        Configuration configxml = Configuration.getConfig();
        String str = configxml.getItemValue("systeminfo", "laststudent");
        String style = configxml.getItemValue(str, "input");
        if (style != null) {
            if (style.equalsIgnoreCase("speech")) {
                sg = new JCALL_NetWorkGeneration(servletRequest, userName);
                network = sg.sentenceGrammar(swDto, lesson.index);
            } else {
                network = null;
            }
        }

        return true;
    }
}