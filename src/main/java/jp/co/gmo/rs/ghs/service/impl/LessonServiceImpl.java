/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import jcall.CALL_conceptInstanceStruct;
import jcall.CALL_conceptSlotFillerStruct;
import jcall.CALL_configDataStruct;
import jcall.CALL_database;
import jcall.CALL_lessonConceptStruct;
import jcall.CALL_lessonQuestionStruct;
import jcall.CALL_lessonStruct;
import jcall.CALL_questionStruct;
import jcall.CALL_sentenceHintsStruct;
import jcall.CALL_sentenceWordStruct;
import jcall.CALL_wordHintsStruct;
import jcall.CALL_wordStruct;
import jcall.db.JCALL_Lexicon;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.CallJScenarioDto;
import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;
import jp.co.gmo.rs.ghs.dto.CategoryOrWord;
import jp.co.gmo.rs.ghs.dto.ListSentenceDto;
import jp.co.gmo.rs.ghs.dto.SentenceDto;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.dto.WordStructDto;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlot;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotData;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotRestriction;
import jp.co.gmo.rs.ghs.jcall.entity.LessonGHS;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.jcall.entity.WordGHS;
import jp.co.gmo.rs.ghs.service.ConceptService;
import jp.co.gmo.rs.ghs.service.LessonService;
import jp.co.gmo.rs.ghs.service.LexiconService;
import jp.co.gmo.rs.ghs.util.PageUtils;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create lesson service impl
 *
 * @author HiepNH
 *
 */
@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LexiconService lexiconService;

    @Autowired
    private ConceptService conceptService;

    /**
     * loadAllSentences
     *
     * @param lessonIndex
     *            is lesson index
     * @param type
     *            is lesson type
     * @return result
     */
    @Override
    public LessonGHS loadAllSentences(List<CallJVocabularyDto> callJVoca,
            int type, LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConcepts) {
        // Init db object
        CALL_database db = new CALL_database();
        CALL_configDataStruct config = new CALL_configDataStruct();
        CALL_configDataStruct gconfig = new CALL_configDataStruct();

        CALL_questionStruct currentQuestion = new CALL_questionStruct(db, config, gconfig, type, mapConcepts, callJVoca);

        return currentQuestion.getLessonGHS();
    }

    /**
     * loadVocabulary
     *
     * @param callJVoca
     *            db vocabulary
     * @param modeLanguage
     *            language mode
     * @return LessonGHS
     */
    public LessonGHS loadVocabulary(List<CallJVocabularyDto> callJVoca, final int modeLanguage) {
        LessonGHS lessonGHS = new LessonGHS();
        List<List<WordGHS>> wordList = new ArrayList<List<WordGHS>>();

        // Init db object
        List<Word> dictionary = lexiconService.getDictionary();
        for (CallJVocabularyDto dto : callJVoca) {
            for (Word word : dictionary) {
                // Check English name and vocabulary on db with lexicon
                if (word.getEnglish().equals(dto.getVocabularyEnglishName()) && word.getKanji().equals(dto.getVocabulary())) {
                    List<WordGHS> words = new ArrayList<WordGHS>();
                    WordGHS wordGHS = new WordGHS();
                    wordGHS.setRomaji(word.getRomaji());
                    wordGHS.setKana(word.getKana());
                    wordGHS.setKanji(word.getKanji());
                    SentenceWordDto sentenceWordDto = new SentenceWordDto();

                    // default value
                    sentenceWordDto.setLessonNo(ConstValues.VocabularySpeech.DEFAULT_LESSON_NO);
                    sentenceWordDto.setIndex(ConstValues.VocabularySpeech.DEFAULT_INDEX);
                    sentenceWordDto.setGrammarRule(ConstValues.VocabularySpeech.DEFAULT_GRAMMA_RULE);
                    sentenceWordDto.setComponentName(ConstValues.VocabularySpeech.DEFAULT_COMPONENT_NAME);
                    sentenceWordDto.setFullFormString(ConstValues.CONST_STRING_EMPTY);
                    sentenceWordDto.setTransformationRule(ConstValues.CONST_STRING_EMPTY);
                    sentenceWordDto.setSign(ConstValues.VocabularySpeech.ALL_VERB_SIGNS);
                    sentenceWordDto.setTense(ConstValues.VocabularySpeech.ALL_VERB_TENSES);
                    sentenceWordDto.setPolitenes(ConstValues.VocabularySpeech.ALL_VERB_STYLES);
                    sentenceWordDto.setQuestion(ConstValues.VocabularySpeech.ALL_QUESTION_OPTIONS);

                    // set UseCounterSetting for prediction process
                    if (word.getType() == JCALL_Lexicon.LEX_TYPE_NUMQUANT) {
                        sentenceWordDto.setUseCounterSettings(true);
                    } else {
                        sentenceWordDto.setUseCounterSettings(false);
                    }
                    sentenceWordDto.setTopWordStringKanna(word.getKana());
                    sentenceWordDto.setTopWordStringKanji(word.getKanji());
                    sentenceWordDto.setSentence(word.getKana());

                    WordStructDto wordStructDto = new WordStructDto();
                    wordStructDto.setKanji(word.getKanji());
                    wordStructDto.setType(String.valueOf(word.getType()));

                    // add to sentenceword
                    sentenceWordDto.setWordStruct(wordStructDto);
                    // add to word ghs
                    wordGHS.setSentenceWordDto(sentenceWordDto);
                    // add to list ghs
                    words.add(wordGHS);
                    // add to list list ghs
                    wordList.add(words);

                    break;
                }
            }
        }

        // set data to lesson
        lessonGHS.setWordList(wordList);

        return lessonGHS;
    }

    /**
     * count concept of lesson
     *
     * @param lessonIndex
     *            is lesson index
     * @return lessonConceptStruct vector
     */
    @Override
    public LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> getConceptAllLesson(List<CallJScenarioDto> jScenarioDtos) {
        // Init db object
        CALL_database db = new CALL_database();
        CALL_configDataStruct config = new CALL_configDataStruct();
        CALL_configDataStruct gconfig = new CALL_configDataStruct();
        // Init map
        LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConceptStructs =
                new LinkedHashMap<String, Vector<CALL_lessonConceptStruct>>();

        Vector<CALL_lessonConceptStruct> concepts = null;
        Vector<CALL_lessonConceptStruct> resultConcepts = null;
        // Count size of jScenarioDtos
        int callJScenarioSize = jScenarioDtos.size();
        for (int i = 0; i < callJScenarioSize; i++) {
            // Get lessonNo of jScenarioDtos
            Integer lessonNo = jScenarioDtos.get(i).getCalljLessonNo();
            // Get questionName of jScenarioDtos
            String questionName = jScenarioDtos.get(i).getCalljQuestionName();
            // Get conceptName of jScenarioDtos
            String conceptName = jScenarioDtos.get(i).getCalljConceptName();

            if (lessonNo != null && questionName != null && conceptName != null) {
                // Get lesson
                CALL_lessonStruct lesson = db.lessons.getLesson(jScenarioDtos.get(i).getCalljLessonNo() - 1);
                CALL_questionStruct currentQuestion =
                        new CALL_questionStruct(db, config, gconfig, lesson, jScenarioDtos.get(i).getCalljQuestionName());
                // Count lesson
                int findLesson = 0;
                for (CALL_lessonConceptStruct concept : currentQuestion.getConcepts()) {

                    // Check concept name
                    if (jScenarioDtos.get(i).getCalljConceptName().equals(concept.concept)) {
                        if (i >= 1) {
                            for (Entry<String, Vector<CALL_lessonConceptStruct>> entry : mapConceptStructs.entrySet()) {
                                // Check lessonNo contains map
                                if (Integer.parseInt(entry.getKey()) == lesson.getIndex()) {
                                    findLesson++;
                                    concepts = entry.getValue();
                                }
                            }
                            // If findLesson = 0 is not found in map
                            if (findLesson == 0) {
                                concepts = new Vector<CALL_lessonConceptStruct>();
                            }
                        }
                        if (concepts == null) {
                            concepts = new Vector<CALL_lessonConceptStruct>();
                        }
                        // Set value for vector
                        concepts.add(concept);
                        findLesson = 0;
                    }
                }
                resultConcepts = concepts;
                // Set value for map Concept
                mapConceptStructs.put(String.valueOf(lessonNo), resultConcepts);
                currentQuestion.getConcepts().clear();
            }

        }
        return mapConceptStructs;
    }

    /**
     * loadAllSentencesDelegate
     *
     * @param lesson
     *            the lesson
     */
    @SuppressWarnings("unchecked")
    @Override
    public ListSentenceDto loadAllSentencesDelegate(int lesson) {

        CALL_database db = new CALL_database();
        CALL_configDataStruct config = new CALL_configDataStruct();
        CALL_configDataStruct gconfig = new CALL_configDataStruct();

        ListSentenceDto listSentenceReturn = new ListSentenceDto();
        List<SentenceDto> listSentence = new ArrayList<>();
        SentenceDto sentence = null;
        int lessonId;
        String questionsName;
        String conceptName;
        List<String> listSlotName = null;
        Concept concept = null;

        // Get all concept
        HashMap<String, Concept> conceptMap = conceptService.getAllConcept();

        // Get all lesson
        List<CALL_lessonStruct> lessons = db.lessons.getAllLesson();

        // For on list lesson
        for (CALL_lessonStruct lessonInList : lessons) {

            // Get lesson id
            lessonId = lessonInList.getIndex();

            // Get list questions of lesson
            Vector<CALL_lessonQuestionStruct> questions = (Vector<CALL_lessonQuestionStruct>) lessonInList.getQuestions();

            // For on list questions
            for (CALL_lessonQuestionStruct questionInList : questions) {

                // Get questions name
                questionsName = questionInList.getQuestion();

                // Get list conceptStruct of question
                Vector<CALL_lessonConceptStruct> concepts = questionInList.getConcepts();

                // For on list conceptStruct
                for (CALL_lessonConceptStruct conceptInList : concepts) {

                    // Get conceptStruct name
                    conceptName = conceptInList.concept;
                    // Get concept by conceptName
                    concept = new Concept();
                    concept = conceptMap.get(conceptName);
                    System.out.println(conceptName);
                    if (conceptName.equals("causative1")) {
                        System.out.println("");
                    }

                    // Get list slot name
                    listSlotName = new ArrayList<>();

                    // Get question current to get hint
                    CALL_questionStruct current = new CALL_questionStruct(db, config, gconfig, 0, conceptInList);

                    // Get the concept instance to check list category
                    CALL_conceptInstanceStruct instance = db.concepts.getInstance(conceptInList.concept, 0);

                    // Get listWordStruct to get category
                    Vector<CALL_sentenceWordStruct> listWordStruct = current.getSentence().getAllSentenceWords();

                    sentence = new SentenceDto();
                    List<WordGHS> listWord = new ArrayList<>();

                    // Get word hint
                    CALL_sentenceHintsStruct hintsStructs = current.getHints();
                    Vector<CALL_wordHintsStruct> whs = hintsStructs.getWordHints();

                    // Add word to list word to create a sentence
                    for (CALL_wordHintsStruct wordInList : whs) {

                        WordGHS wordGHS = new WordGHS();
                        // Set base kana
                        wordGHS.setBaseKana(wordInList.getHints()[2].getHintKana());
                        // Set base kanji
                        wordGHS.setBaseKanji(wordInList.getHints()[2].getHintKanji());
                        wordGHS.setWordType(wordInList.getHints()[0].getHintKana());

                        for (int i = 0; i < wordInList.getHints().length; i++) {
                            if (wordInList.getHints()[i] != null) {
                                if (wordInList.getHints()[i].getHintKana() != null) {
                                    // Set kana
                                    wordGHS.setKana(wordInList.getHints()[i].getHintKana());
                                    // Set kanji
                                    wordGHS.setKanji(wordInList.getHints()[i].getHintKanji());

                                }

                            }

                        }

                        // Set listCategory
                        wordGHS.setCategoryOrWord(
                                getListCategoryByKana(db, listWordStruct, wordGHS, instance.getLabel(),
                                        concept, conceptMap, listSlotName));
                        // Set romaji
                        wordGHS.setRomaji(StringUtils.strKanaToRomaji(wordGHS.getKana()));
                        listWord.add(wordGHS);
                    }

                    // Set property for sentence
                    sentence.setLessonId(lessonId);
                    sentence.setNameQuestion(questionsName);
                    sentence.setNameConcept(conceptName);
                    sentence.setSentence(listWord);
                    sentence.setListSlotName(PageUtils.removeDuplicates(listSlotName));

                    // Add sentence to list sentence
                    listSentence.add(sentence);
                }

            }

        }

        // Set list sentence to list return
        listSentenceReturn.setResult(listSentence);
        return listSentenceReturn;
    }

    /**
     * Get list category by Kana
     *
     * @param listWordStruct
     * @param kana
     * @return list category
     */
    private CategoryOrWord getListCategoryByKana(CALL_database db, Vector<CALL_sentenceWordStruct> listWordStruct,
            WordGHS wordGHS, Vector<String> listSlotCompar, Concept concept,
            HashMap<String, Concept> conceptMap, List<String> listSlotName) {

        // Init Obj
        CategoryOrWord categoryOrWord = new CategoryOrWord();

        List<String> tempCategoryList = new ArrayList<String>();
        List<String> tempWordList = new ArrayList<String>();

        CALL_wordStruct word = db.lexicon.getWord(wordGHS.getBaseKanji());

        List<String> strDuplicate = new ArrayList<String>();
        for (CALL_sentenceWordStruct wordStruct : listWordStruct) {

            String grammarRules = ConstValues.CONST_STRING_EMPTY;

            if (wordStruct.getWord().getEnglish().equals(word.getEnglish())) {
                // Get component and grammar rules from word struct
                grammarRules = wordStruct.getGrammarRule();
                int indexSlot = -1;
                boolean isCheckFind = false;

                for (ConceptSlot slot : concept.getConceptSlotList()) {

                    indexSlot++;
                    // Check grammar equals slot name
                    if (checkGrammarRules(grammarRules, slot, listSlotCompar, indexSlot, concept)) {
                        // Set type slot
                        wordGHS.setTypeSlot(slot.getName());
                        // Set List slot name
                        listSlotName.add(slot.getName());
                        // ConceptSlotDataList > 0
                        if (slot.getConceptSlotDataList().size() > 0) {
                            for (ConceptSlotData slotData : slot.getConceptSlotDataList()) {
                                // Add category in list temp
                                setListCategoryOrWord(slotData, strDuplicate, tempCategoryList, tempWordList,
                                        conceptMap, categoryOrWord, db, grammarRules, wordGHS, listSlotName);
                                isCheckFind = true;
                            }
                        }
                        // ConceptSlotRestrictionList > 0
                        if (slot.getConceptSlotRestrictionList().size() > 0) {
                            for (ConceptSlotRestriction slotData : slot.getConceptSlotRestrictionList()) {
                                for (ConceptSlotData slotDataRest : slotData.getConceptSlotDataList()) {
                                    // Add category in list temp
                                    setListCategoryOrWord(slotDataRest, strDuplicate, tempCategoryList, tempWordList,
                                            conceptMap, categoryOrWord, db, grammarRules, wordGHS, listSlotName);
                                    isCheckFind = true;
                                }
                            }
                        }
                        // Clear list temp
                        strDuplicate.clear();

                    }
                    if (isCheckFind) {
                        break;
                    }
                }
            }
        }
        // Set category
        categoryOrWord.setCategory(PageUtils.removeDuplicates(tempCategoryList));
        // Set word
        categoryOrWord.setWord(PageUtils.removeDuplicates(tempWordList));

        return categoryOrWord;
    }

    /**
     * Check grammar rules with concept slot
     *
     * @param grammarRules
     * @param slot
     * @param listCategoryCompar
     * @param index
     * @param concept
     * @return
     */
    private boolean checkGrammarRules(String grammarRules, ConceptSlot slot,
            Vector<String> listSlotCompar, int index, Concept concept) {
        // Check particle
        if (!PageUtils.checkParticle(grammarRules) && !slot.getName().equals("GFXBG1")) {
            // Check grammar rules contains concept slot
            if (grammarRules.equals(slot.getName())) {
                return true;
            } else {
                // Check grammar rules contains list slot name
                if (grammarRules.equals(listSlotCompar.get(index))) {
                    return true;
                } else {
                    // Count concept slot
                    if (index == (concept.getConceptSlotList().size() - 1)) {
                        // If concep slot == list slot => get grammar relue follow index
                        if (concept.getConceptSlotList().size() == listSlotCompar.size()) {
                            if (grammarRules.equals(listSlotCompar.get(index))) {
                                return true;
                            }
                         // If concep slot < list slot => get grammar relue follow index + 1
                        } else if (concept.getConceptSlotList().size() < listSlotCompar.size()) {
                            if (grammarRules.equals(listSlotCompar.get(index + 1))) {
                                return true;
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    /**
     * Set value category and word in list
     *
     * @param slotData
     * @param strDuplicate
     * @param tempList
     * @param isCheckFind
     * @param conceptMap
     */
    private void setListCategoryOrWord(ConceptSlotData slotData, List<String> strDuplicate,
            List<String> tempCategoryList, List<String> tempWordList, HashMap<String, Concept> conceptMap,
            CategoryOrWord categoryOrWord, CALL_database db, String grammarRules, WordGHS wordGHS, List<String> listSlotName) {
        // Get concept
        Concept conceptTemp = conceptMap.get(slotData.getData());

        if (conceptTemp == null) {
            // Set list
            setList(db, slotData, strDuplicate, categoryOrWord, tempCategoryList, tempWordList);
        } else if (conceptTemp != null) {
            for (ConceptSlot slot : conceptTemp.getConceptSlotList()) {
                // Check grammar equals slot name
                if (grammarRules.equals(slot.getName())) {
                    // Set slot type for word
                    wordGHS.setTypeSlot(slot.getName());
                    // Set List slot name
                    listSlotName.add(slot.getName());
                    // ConceptSlotDataList > 0
                    if (slot.getConceptSlotDataList().size() > 0) {
                        for (ConceptSlotData slotDataNew : slot.getConceptSlotDataList()) {
                            // Set list
                            setList(db, slotDataNew, strDuplicate, categoryOrWord, tempCategoryList, tempWordList);
                        }
                    }
                    // ConceptSlotRestrictionList > 0
                    if (slot.getConceptSlotRestrictionList().size() > 0) {
                        for (ConceptSlotRestriction slotDataRes : slot.getConceptSlotRestrictionList()) {
                            for (ConceptSlotData slotDataRestNew : slotDataRes.getConceptSlotDataList()) {
                                // Set list
                                setList(db, slotDataRestNew, strDuplicate, categoryOrWord, tempCategoryList, tempWordList);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Set value in list
     *
     * @param db
     * @param slotData
     * @param strDuplicate
     * @param categoryOrWord
     * @param tempCategoryList
     * @param tempWordList
     */
    private void setList(CALL_database db, ConceptSlotData slotData, List<String> strDuplicate,
            CategoryOrWord categoryOrWord, List<String> tempCategoryList, List<String> tempWordList) {
        if (slotData.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {

            String category = slotData.getData();
            if (!strDuplicate.contains(category)) {
                // Add category in list
                tempCategoryList.add(slotData.getData());
                categoryOrWord.setFlag(true);
                strDuplicate.add(category);
            }
        } else if (slotData.getType() == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
            // Get word form lexicon
            String stkanji = db.lexicon.getWord(slotData.getData()).getKanji();

            if (!strDuplicate.contains(stkanji)) {
                // Add all word in list
                tempWordList.add(stkanji);
                categoryOrWord.setFlag(false);
                strDuplicate.add(stkanji);
            }
        }
    }
}
