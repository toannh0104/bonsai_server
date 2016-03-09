/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import jcall.CALL_lessonConceptStruct;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.AdminDao;
import jp.co.gmo.rs.ghs.dao.ClientDao;
import jp.co.gmo.rs.ghs.dto.CallJScenarioDto;
import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;
import jp.co.gmo.rs.ghs.dto.PracticeTestDto;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.jcall.entity.LessonGHS;
import jp.co.gmo.rs.ghs.jcall.entity.WordGHS;
import jp.co.gmo.rs.ghs.model.DictBase;
import jp.co.gmo.rs.ghs.model.DictJA;
import jp.co.gmo.rs.ghs.model.Language;
import jp.co.gmo.rs.ghs.model.LessonInfo;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.Vocabulary;
import jp.co.gmo.rs.ghs.service.ClientPracticeTestService;
import jp.co.gmo.rs.ghs.service.LessonService;
import jp.co.gmo.rs.ghs.util.PageUtils;
import jp.co.gmo.rs.ghs.util.StringUtils;
import jp.co.gmo.rs.ghs.util.Translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClientPracticeTestServiceImpl class
 *
 * @author DongNSH
 *
 */
@Service
public class ClientPracticeTestServiceImpl implements ClientPracticeTestService {

    /* Init clientDao */
    @Autowired
    private ClientDao clientDao;

    /* Init adminDao */
    @Autowired
    private AdminDao adminDao;

    /* Init LessonService */
    @Autowired
    private LessonService lessonService;

    /**
     * Get practice test
     *
     * @param practiceTest
     *            mode
     * @param lessonInfoId
     *            id
     * @param memoryWritingSpeech
     *            mode
     * @param modeLanguage
     *            mode
     * @param userLanguage
     *            user's mother language
     * @return Object
     *
     * @throws IOException
     *             exception
     */
    @Override
    public PracticeTestDto getPracticeTest(final int lessonNo, int lessonType,
            final String practiceTest, final int lessonInfoId, final String memoryWritingSpeech,
            final int modeLanguage, final String userLanguage, int mode) throws IOException {
        // TODO
        // Initialize result dto
        PracticeTestDto practiceTestDto = new PracticeTestDto();

        // If mode is practice then get practice limit from database
        // If mode is test then default practice limit is 1
        if (ConstValues.PRACTICE.equals(practiceTest)) {
            practiceTestDto.setPageLimit(getPracticeLimit(lessonInfoId));
        }
        // Get LessonGHS
        LessonGHS lessonGHS;
        List<CallJVocabularyDto> callJVocabulary = getCallJVocabulary(lessonInfoId);
        if (lessonType == ConstValues.MODE_SCENARIO) {
            LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConceptOfLesson = lessonService
                    .getConceptAllLesson(getCallJScenario(lessonInfoId));
            lessonGHS = lessonService.loadAllSentences(callJVocabulary, lessonType, mapConceptOfLesson);
        } else {
            lessonGHS = lessonService.loadVocabulary(callJVocabulary, modeLanguage);
        }
        // Get vocabulary list from callJ
        List<WordGHS> callJVocabularyList = new ArrayList<WordGHS>();
        getCallJWordList(lessonGHS, callJVocabularyList, modeLanguage, lessonType);
        // Check lessonType to get practice test with mode scenario/vocabulary
        if (ConstValues.CONVERSATION_TEXT.equals(memoryWritingSpeech)) {
            // Conversation mode - just get list word from JCall
            if (lessonType == ConstValues.MODE_SCENARIO) {
                getConverationScenarioList(practiceTestDto, lessonGHS, modeLanguage);
                getConverVocaListModeScena(practiceTestDto, callJVocabularyList, modeLanguage);
            } else {
                getConverVocaListModeVoca(practiceTestDto, callJVocabularyList, modeLanguage);
            }
        } else if (ConstValues.MEMORY_TEXT.equals(memoryWritingSpeech)
                || ConstValues.WRITING_TEXT.equals(memoryWritingSpeech)) {
            // Get all Japanese dictionary from db
            List<DictJA> japaneseDict = clientDao.getAllJapaneseDict();
            // Get all user's mother language dictionary from db
            String userLanguageTableName = ConstValues.DICT_TEXT + userLanguage.toUpperCase().replace("_", "");
            List<DictBase> userLanguageDict = clientDao.getUserDict(userLanguageTableName);
            // Memory/Writing - get list word from JCall join with database
            if (lessonType == ConstValues.MODE_SCENARIO) {
                getMemoWritingScenarioList(practiceTestDto, lessonGHS, lessonInfoId,
                        modeLanguage, userLanguage, japaneseDict, userLanguageDict, mode);
            }
            getMemoWritingVocabularyList(practiceTestDto, callJVocabularyList, lessonInfoId, lessonType,
                    modeLanguage, userLanguage, japaneseDict, userLanguageDict);
            // Memory - get list answer of each sentence
            if (ConstValues.MEMORY_TEXT.equals(memoryWritingSpeech)) {
                if (lessonType == ConstValues.MODE_SCENARIO) {
                    practiceTestDto.setMemoryScenarioAnswerList(PageUtils.getMemoryAnswer(practiceTestDto.getScenarioList()));
                }
                practiceTestDto.setMemoryVocabularyAnswerList(PageUtils.getMemoryAnswer(practiceTestDto.getVocabularyList()));
            }
        }

        return practiceTestDto;
    }

    /**
     * Get scenario list of conversation.
     *
     * @param practiceTestDto
     * @param lessonGHS
     * @param modeLanguage
     * @throws IOException
     */
    private void getConverationScenarioList(PracticeTestDto practiceTestDto, LessonGHS lessonGHS,
            final int modeLanguage) throws IOException {
        if (lessonGHS != null) {
            List<List<WordGHS>> wordList = lessonGHS.getWordList();
            // Check wordList available
            if (wordList != null && wordList.size() > 0) {
                // Initialize
                int wordListSize = wordList.size();
                PageUtils.getSizePerPage(wordListSize, practiceTestDto);
                int pageLimit = practiceTestDto.getPageLimit();
                int sizePerPage = practiceTestDto.getSizePerPage();
                // Sort list in random order
                long seed = System.nanoTime();
                Collections.shuffle(wordList, new Random(seed));

                List<List<String>> scenarioList = practiceTestDto.getScenarioList();
                List<List<String>> scenarioRubyList = practiceTestDto.getScenarioRubyList();
                List<List<String>> scenarioJonList = practiceTestDto.getScenarioJSonList();

                // First time
                List<String> pageScenarioSentenceList = new ArrayList<String>();
                List<String> pageScenarioRubySentenceList = new ArrayList<String>();
                List<String> pageScenarioSentenceJSonList = new ArrayList<String>();
                scenarioList.add(pageScenarioSentenceList);
                scenarioRubyList.add(pageScenarioRubySentenceList);
                scenarioJonList.add(pageScenarioSentenceJSonList);

                for (int i = 0; i < wordListSize; i++) {
                    List<SentenceWordDto> jSonSentences = new ArrayList<SentenceWordDto>();
                    List<WordGHS> sentences = wordList.get(i);
                    // Join word into sentence
                    String sentence = "";
                    String rubySentence = "";
                    int wordSize = sentences.size();
                    for (int k = 0; k < wordSize; k++) {
                        // Join word
                        WordGHS word = sentences.get(k);
                        if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI) {
                            sentence += word.getRomaji() + " ";
                        } else if (modeLanguage == ConstValues.MODE_LANGUAGE_KANJI) {
                            sentence += word.getKanji();
                            if (!word.getKanji().equals(word.getKana())) {
                                rubySentence += word.getKanji() + "-" + word.getKana() + ";";
                            }
                        } else {
                            sentence += word.getKana();
                        }
                        // Join JSon word
                        jSonSentences.add(word.getSentenceWordDto());
                    }

                    // Add sentence into page
                    pageScenarioSentenceList.add(sentence.trim());
                    // Add ruby sentence
                    pageScenarioRubySentenceList.add(rubySentence);
                    // Add JSon
                    pageScenarioSentenceJSonList.add(StringUtils.convertObjToJson(jSonSentences));
                    if (pageScenarioSentenceList.size() == sizePerPage) {
                        if (scenarioList.size() >= pageLimit) {
                            break;
                        }
                        pageScenarioSentenceList = new ArrayList<String>();
                        pageScenarioRubySentenceList = new ArrayList<String>();
                        pageScenarioSentenceJSonList = new ArrayList<String>();
                        scenarioList.add(pageScenarioSentenceList);
                        scenarioRubyList.add(pageScenarioRubySentenceList);
                        scenarioJonList.add(pageScenarioSentenceJSonList);
                    }
                }
            }
        }
    }

    /**
     * Get vocabulary list of conversation with mode scenario.
     *
     * @param practiceTestDto
     * @param jCallWordGHSList
     * @param jCallSentenceWordDtoList
     * @param modeLanguage
     * @throws IOException
     */
    private void getConverVocaListModeScena(PracticeTestDto practiceTestDto, List<WordGHS> callJVocabularyList,
            final int modeLanguage) throws IOException {

        List<List<String>> scenarioList = practiceTestDto.getScenarioList();
        List<List<String>> vocabularyList = practiceTestDto.getVocabularyList();
        List<List<String>> vocabularyRubyList = practiceTestDto.getVocabularyRubyList();
        List<List<String>> vocabularyJonList = practiceTestDto.getVocabularyJSonList();

        int listWordSize = callJVocabularyList.size();
        int sizePerPage = practiceTestDto.getSizePerPage();

        // Get vocabulary list that map with scenario list
        for (List<String> scenarios : scenarioList) {
            List<String> pageVocabularyList = new ArrayList<String>();
            List<String> pageVocabularyRubyList = new ArrayList<String>();
            List<String> pageVocabularyJSonList = new ArrayList<String>();
            vocabularyList.add(pageVocabularyList);
            vocabularyRubyList.add(pageVocabularyRubyList);
            vocabularyJonList.add(pageVocabularyJSonList);
            // Get all scenario of one page
            String pageScenario = "";
            for (String scenario : scenarios) {
                pageScenario += scenario + "\n";
            }

            // Get all vocabulary of one page that map with scenario
            for (int i = 0; i < listWordSize; i++) {
                if (pageVocabularyList.size() >= ConstValues.PageDefault.SIZE_PER_PAGE) {
                    break;
                }
                WordGHS wordGHS = callJVocabularyList.get(i);
                String wordRubyString = "";
                String wordString = wordGHS.getKana();
                if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI) {
                    wordString = wordGHS.getRomaji();
                } else if (modeLanguage == ConstValues.MODE_LANGUAGE_KANJI) {
                    wordString = wordGHS.getKanji();
                    if (!wordGHS.getKanji().equals(wordGHS.getKana())) {
                        wordRubyString = wordGHS.getKanji() + "-" + wordGHS.getKana() + ";";
                    }
                }

                // Add word into page
                if (pageScenario.contains(wordString) && !pageVocabularyList.contains(wordString)) {
                    // Add word
                    pageVocabularyList.add(wordString);
                    // Add ruby word
                    pageVocabularyRubyList.add(wordRubyString);
                    // Add JSon
                    List<SentenceWordDto> jSon = new ArrayList<SentenceWordDto>();
                    jSon.add(wordGHS.getSentenceWordDto());
                    pageVocabularyJSonList.add(StringUtils.convertObjToJson(jSon));
                }
            }
        }
    }

    /**
     * Get vocabulary list of conversation with mode vocabulary.
     *
     * @param practiceTestDto
     * @param jCallWordGHSList
     * @param jCallSentenceWordDtoList
     * @param modeLanguage
     * @throws IOException
     */
    private void getConverVocaListModeVoca(PracticeTestDto practiceTestDto, List<WordGHS> callJVocabularyList,
            final int modeLanguage) throws IOException {
        // Check callJVocabularyList available
        if (callJVocabularyList != null && callJVocabularyList.size() > 0) {
            List<List<String>> vocabularyList = practiceTestDto.getVocabularyList();
            List<List<String>> vocabularyRubyList = practiceTestDto.getVocabularyRubyList();
            List<List<String>> vocabularyJonList = practiceTestDto.getVocabularyJSonList();

            // Sort list in random order
            long seed = System.nanoTime();
            Collections.shuffle(callJVocabularyList, new Random(seed));
            // Get sizePerPage and pageLimit
            int wordListSize = callJVocabularyList.size();
            PageUtils.getSizePerPage(wordListSize, practiceTestDto);
            int sizePerPage = practiceTestDto.getSizePerPage();
            int pageLimit = practiceTestDto.getPageLimit();

            // Fisrt time
            List<String> pageVocabularyList = new ArrayList<String>();
            List<String> pageVocabularyRubyList = new ArrayList<String>();
            List<String> pageVocabularyJSonList = new ArrayList<String>();
            vocabularyList.add(pageVocabularyList);
            vocabularyRubyList.add(pageVocabularyRubyList);
            vocabularyJonList.add(pageVocabularyJSonList);

            // Get vocabulary list that map with scenario list
            for (int i = 0; i < wordListSize; i++) {
                WordGHS wordGHS = callJVocabularyList.get(i);
                String wordRubyString = "";
                String wordString = wordGHS.getKana();
                if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI) {
                    wordString = wordGHS.getRomaji();
                } else if (modeLanguage == ConstValues.MODE_LANGUAGE_KANJI) {
                    wordString = wordGHS.getKanji();
                    if (wordGHS.getKanji().equals(wordGHS.getKana())) {
                        wordRubyString = wordGHS.getKanji() + "-" + wordGHS.getKana() + ";";
                    }
                }

                // Add word
                pageVocabularyList.add(wordString);
                // Add Ruby word
                pageVocabularyRubyList.add(wordRubyString);
                // Add JSon
                List<SentenceWordDto> jSon = new ArrayList<SentenceWordDto>();
                jSon.add(wordGHS.getSentenceWordDto());
                pageVocabularyJSonList.add(StringUtils.convertObjToJson(jSon));

                if (pageVocabularyList.size() == sizePerPage) {
                    if (vocabularyList.size() >= pageLimit) {
                        break;
                    }
                    pageVocabularyList = new ArrayList<String>();
                    pageVocabularyRubyList = new ArrayList<String>();
                    pageVocabularyJSonList = new ArrayList<String>();
                    vocabularyList.add(pageVocabularyList);
                    vocabularyRubyList.add(pageVocabularyRubyList);
                    vocabularyJonList.add(pageVocabularyJSonList);
                }
            }
        }
    }

    /**
     * Get scenario list of memory/writing.
     *
     * @param practiceTestDto
     * @param lessonGHS
     * @param lessonInfoId
     * @param modeLanguage
     */
    private void getMemoWritingScenarioList(PracticeTestDto practiceTestDto, LessonGHS lessonGHS,
            final int lessonInfoId, final int modeLanguage, final String userLanguage,
            final List<DictJA> japaneseDict, final List<DictBase> userLanguageDict, int mode) {
        // Get distinct scenario list of dbScenarioList and callJ
        List<String> scenario = new ArrayList<String>();
        List<String> rubyScenario = new ArrayList<String>();
        List<String> showScenario = new ArrayList<String>();
        List<String> element = new ArrayList<String>();
        getDistinctScenario(scenario, rubyScenario, showScenario, lessonGHS,
                modeLanguage, userLanguage, japaneseDict, userLanguageDict, element, mode);

        // Paginate random scenario list
        long seed = System.nanoTime();
        PageUtils.sortListRandom(seed, scenario);
        PageUtils.sortListRandom(seed, rubyScenario);
        PageUtils.sortListRandom(seed, showScenario);
        PageUtils.sortListRandom(seed, element);
        PageUtils.getSizePerPage(scenario.size(), practiceTestDto);
        PageUtils.paginateRubyWordList(
                scenario, rubyScenario, showScenario, practiceTestDto, ConstValues.MODE_SCENARIO, element);
    }

    /**
     * Get vocabulary list of memory/writing
     *
     * @param practiceTestDto
     * @param listWord
     * @param lessonInfoId
     * @param modeLanguage
     * @param lessonType
     */
    private void getMemoWritingVocabularyList(PracticeTestDto practiceTestDto,
            final List<WordGHS> callJVocabularyList, final int lessonInfoId,
            final int lessonType, final int modeLanguage, final String userLanguage,
            final List<DictJA> japaneseDict, final List<DictBase> userLanguageDict) {
        // Get distinct vocabularySet list of dbVocabularyList and callJ
        List<String> vocabulary = new ArrayList<String>();
        List<String> rubyVocabulary = new ArrayList<String>();
        List<String> showVocabulary = new ArrayList<String>();
        getDistinctVocabulary(vocabulary, rubyVocabulary, showVocabulary, callJVocabularyList,
                modeLanguage, userLanguage, japaneseDict, userLanguageDict);

        if (lessonType == ConstValues.MODE_SCENARIO) {
            // Get paginate vocabulary list map with scenario list
            paginateVocabularyModeScena(vocabulary, rubyVocabulary, showVocabulary, practiceTestDto);
        } else {
            // Paginate random vocabulary list
            long seed = System.nanoTime();
            PageUtils.sortListRandom(seed, vocabulary);
            PageUtils.sortListRandom(seed, rubyVocabulary);
            PageUtils.sortListRandom(seed, showVocabulary);
            PageUtils.getSizePerPage(vocabulary.size(), practiceTestDto);
            PageUtils.paginateRubyWordList(vocabulary, rubyVocabulary, showVocabulary,
                    practiceTestDto, ConstValues.MODE_VOCABULARY, null);
        }
    }

    /**
     * Get vocabulary list from callJ (except type Particle, Verb).
     *
     * @param lessonGHS
     * @param listWord
     */
    private void getCallJWordList(LessonGHS lessonGHS, List<WordGHS> listWord, Integer modeLanguage, int lessonType) {
        List<String> wordStringList = new ArrayList<String>();
        List<String> distinctSentenceList = new ArrayList<String>();
        List<List<WordGHS>> distinctWordList = new ArrayList<List<WordGHS>>();
        if (lessonGHS != null) {
            List<List<WordGHS>> wordList = lessonGHS.getWordList();
            if (wordList != null) {
                int wordListSize = wordList.size();
                for (int i = 0; i < wordListSize; i++) {
                    // Get sentence
                    List<WordGHS> sentences = wordList.get(i);
                    String sentenceString = ConstValues.CONST_STRING_EMPTY;
                    if (sentences != null) {
                        int sentencesSize = sentences.size();
                        for (int j = 0; j < sentencesSize; j++) {
                            WordGHS word = sentences.get(j);
                            String wordString = "";
                            // Join sentence string
                            if (ConstValues.MODE_LANGUAGE_ROMAJI == modeLanguage) {
                                wordString = word.getRomaji();
                            } else if (ConstValues.MODE_LANGUAGE_KANA == modeLanguage) {
                                wordString = word.getKana();
                            } else {
                                wordString = word.getKanji();
                            }
                            sentenceString += wordString;
                            // Add word except type Particle, Verb into
                            // vocabulary
                            // list

                            boolean flag = false;
                            if (word != null && !listWord.contains(word) && !wordStringList.contains(wordString)) {
                                if (lessonType == ConstValues.MODE_SCENARIO) {
                                    if (!PageUtils.checkParticle(word.getWordType()) && checkWordType(word)) {
                                        flag = true;
                                    }
                                } else {
                                    flag = true;
                                }
                            }
                            if (flag) {
                                wordStringList.add(wordString);
                                listWord.add(word);
                            }
                        }
                        // Remove exits sentence in word list
                        if (!distinctSentenceList.contains(sentenceString)) {
                            distinctSentenceList.add(sentenceString);
                            distinctWordList.add(sentences);
                        }
                    }
                }
                // Set distinct WordList into LessonGHS
                lessonGHS.setWordList(distinctWordList);
            }
        }
    }

    private boolean checkWordType(WordGHS word) {
        List<String> slotName = word.getSlotName();
        String wordType = word.getTypeSlot();
        String english = word.getEnglish();
        for (int i = 0; i < slotName.size(); i++) {
            if (slotName.get(i).equals(wordType) && (PageUtils.checkVerb(english))) {
                return true;
            }
        }
        return false;
    }
    /**
     * Get distinct scenario list.
     *
     * @param scenarioList
     * @param rubyScenarioList
     * @param showScenario
     * @param callJScenarioList
     * @param dbScenarioList
     */
    private void getDistinctScenario(List<String> scenarioList, List<String> rubyScenarioList,
            List<String> showScenarioList, LessonGHS lessonGHS, final int modeLanguage,
            final String userLanguage, final List<DictJA> japaneseDict,
            final List<DictBase> userLanguageDict, List<String> element, int mode) {
        // Get from jCall
        if (lessonGHS != null) {
            List<List<WordGHS>> listWord = lessonGHS.getWordList();
            if (listWord != null) {
                for (List<WordGHS> words : listWord) {
                    // Join word into sentence
                    String sentence = ConstValues.CONST_STRING_EMPTY;
                    String romajiSentence = ConstValues.CONST_STRING_EMPTY;
                    String kanaSentence = ConstValues.CONST_STRING_EMPTY;
                    String kanjiSentence = ConstValues.CONST_STRING_EMPTY;
                    String rubySentence = ConstValues.CONST_STRING_EMPTY;
                    for (WordGHS word : words) {
                        if (mode == ConstValues.MODE_WRITING) {
                            romajiSentence += word.getRomaji() + ";";
                            kanaSentence += word.getKana() + ";";
                            kanjiSentence += word.getKanji() + ";";
                        } else {
                            romajiSentence += word.getRomaji() + " ";
                            kanaSentence += word.getKana();
                            kanjiSentence += word.getKanji();
                        }
                        if (!word.getKanji().equals(word.getKana())) {
                            rubySentence += word.getKanji() + "-" + word.getKana() + ";";
                        }
                    }
                    if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI) {
                        sentence = romajiSentence.trim();
                    } else if (modeLanguage == ConstValues.MODE_LANGUAGE_KANA) {
                        sentence = kanaSentence;
                    } else {
                        sentence = kanjiSentence;
                    }
                    scenarioList.add(sentence);
                    element.add(Arrays.asList(sentence.split(";")).size() + "");
                    rubyScenarioList.add(rubySentence);
                    // Get translate string from db
                    // or translate to English then translate to user's mother language
                    if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI && mode == ConstValues.MODE_WRITING) {
                        showScenarioList.add(
                                getTranslateString(sentence.replace(";", " "), kanjiSentence.replace(";", ""), userLanguage,
                                japaneseDict, userLanguageDict));
                    } else {
                        showScenarioList.add(
                                getTranslateString(sentence.replace(";", ""), kanjiSentence.replace(";", ""), userLanguage,
                                japaneseDict, userLanguageDict));
                    }
                }
            }
        }
    }

    /**
     * Get distinct vocabulary list.
     *
     * @param vocabularyList
     * @param rubyVocabularyList
     * @param showVocabulary
     * @param jCallWordGHSList
     * @param dbVocabularyList
     * @param modeLanguage
     */
    private void getDistinctVocabulary(List<String> vocabularyList, List<String> rubyVocabularyList,
            List<String> showVocabularyList, final List<WordGHS> jCallWordGHSList, final int modeLanguage,
            final String userLanguage, final List<DictJA> japaneseDict, final List<DictBase> userLanguageDict) {
        // Get from jCall
        for (WordGHS word : jCallWordGHSList) {
            String wordString = ConstValues.CONST_STRING_EMPTY;
            String rubyWordString = ConstValues.CONST_STRING_EMPTY;
            if (modeLanguage == ConstValues.MODE_LANGUAGE_ROMAJI) {
                wordString = word.getRomaji();
            } else if (modeLanguage == ConstValues.MODE_LANGUAGE_KANJI) {
                wordString = word.getKanji();
                if (!word.getKanji().equals(word.getKana())) {
                    rubyWordString = word.getKanji() + "-" + word.getKana() + ";";
                }
            } else {
                wordString = word.getKana();
            }
            vocabularyList.add(wordString);
            rubyVocabularyList.add(rubyWordString);
            // Get translate string from db
            // or translate to English then translate to user's mother language
            showVocabularyList.add(getTranslateString(wordString, word.getKanji(), userLanguage,
                    japaneseDict, userLanguageDict));
        }
    }

    /**
     * If exits on db then get from db
     * If exits on db then translate kanji to English then translate to user's mother language
     *
     * @param kanji
     * @param japaneseDict
     * @param userLanguage
     * @return
     */
    private String getTranslateString(final String word, final String kanji, final String userLanguage,
            final List<DictJA> japaneseDict, final List<DictBase> userLanguageDict) {
        // TODO
        String translateString = ConstValues.CONST_STRING_EMPTY;
        if (kanji != null && kanji.length() > 0) {
            // Check if word exits on Japanese table
            int distID = -1;
            for (DictJA dictJA : japaneseDict) {
                if (kanji.equals(dictJA.getWord1())) {
                    distID = dictJA.getId();
                    break;
                }
            }

            try {
                // If word/sentence exits on Japanese table
                //     then get corresponding word/sentence from user's mother language table
                // If word/sentence not exits then translate it
                if (distID != -1) {
                    int length = userLanguageDict.size();
                    for (int i = 0; i < length; i++) {
                        DictBase dict = userLanguageDict.get(i);
                        if (distID == dict.getDictJaId().intValue()) {
                            translateString = dict.getWord1();
                            break;
                        }
                    }
                } else {
                    translateString = Translator.getInstance()
                            .translate(kanji, Language.JAPANESE, Language.ENGLISH);
                    translateString = Translator.getInstance()
                            .translate(translateString, Language.ENGLISH, userLanguage);
                    adminDao.insertMultiLanguage(kanji);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // If translate result is fail then return kanji
        if (translateString.equals("")) {
            translateString = word;
        }
        return translateString;
    }

    /**
     * Paginate Vocabulary list map with Scenario list.
     *
     * @param vocabularyList
     * @param rubyVocabularyList
     * @param showVocabulary
     * @param practiceTestDto
     */
    private void paginateVocabularyModeScena(List<String> wordList, List<String> rubyWordList,
            List<String> showWordList, PracticeTestDto practiceTestDto) {
        List<List<String>> scenarioList = practiceTestDto.getScenarioList();
        int sizePerPage = practiceTestDto.getSizePerPage();
        List<List<String>> vocabularyList = practiceTestDto.getVocabularyList();
        List<List<String>> vocabularyRubyList = practiceTestDto.getVocabularyRubyList();
        List<List<String>> vocabularyShowList = practiceTestDto.getVocabularyShowList();
        for (List<String> scenarios : scenarioList) {
            List<String> vocabularyPage = new ArrayList<String>();
            List<String> vocabularyRubyPage = new ArrayList<String>();
            List<String> vocabularyShowPage = new ArrayList<String>();
            // Get all scenario of one page
            String pageScenario = ConstValues.CONST_STRING_EMPTY;
            for (String scenario : scenarios) {
                pageScenario += scenario + "\n";
            }
            // Check if vocabulary exits in scenario then add to result
            int wordListSize = wordList.size();
            for (int i = 0; i < wordListSize; i++) {
                String word = wordList.get(i);
                if (ConstValues.PageDefault.SIZE_PER_PAGE <= vocabularyPage.size()) {
                    break;
                }
                if (pageScenario.contains(word) && !vocabularyPage.contains(word)) {
                    vocabularyPage.add(word);
                    vocabularyRubyPage.add(rubyWordList.get(i));
                    vocabularyShowPage.add(showWordList.get(i));
                }
            }
            vocabularyList.add(vocabularyPage);
            vocabularyRubyList.add(vocabularyRubyPage);
            vocabularyShowList.add(vocabularyShowPage);
        }
    }

    /**
     * Get practice limit from database.
     *
     * @param lessonInfoId
     * @return
     */
    private int getPracticeLimit(final int lessonInfoId) {
        LessonInfo lessonInfo = adminDao.getLessonInfoById(lessonInfoId);
        return lessonInfo.getPracticeLimit();
    }

    /**
     * Get CallJ scenario.
     *
     * @param lessonInfoId
     *            id
     * @return list
     */
    private List<CallJScenarioDto> getCallJScenario(final int lessonInfoId) {
        List<CallJScenarioDto> result = new ArrayList<CallJScenarioDto>();
        // Get list scenario from database via lessonInfoId
        List<Scenario> scenarioList = clientDao.getScenario(lessonInfoId);
        // Map field lessonNo , conceptName and questionName into CallJScenarioDto
        CallJScenarioDto callJScenario;
        for (Scenario scenario : scenarioList) {
            boolean isCheckContains = true;
            if (result.size() > 0) {
                // Check if record not exits in list
                for (CallJScenarioDto scenario2 : result) {
                    if (scenario2.getCalljLessonNo() == scenario.getCalljLessonNo()
                            && scenario2.getCalljConceptName().equals(scenario.getCalljConceptName())
                            && scenario2.getCalljQuestionName().equals(scenario.getCalljQuestionName())) {
                        isCheckContains = false;
                    }
                }
                // If record not exits then add into list
                if (isCheckContains) {
                    callJScenario = new CallJScenarioDto();
                    callJScenario.setCalljLessonNo(scenario.getCalljLessonNo());
                    callJScenario.setCalljConceptName(scenario.getCalljConceptName());
                    callJScenario.setCalljQuestionName(scenario.getCalljQuestionName());
                    result.add(callJScenario);
                }
            } else {
                 // First time
                callJScenario = new CallJScenarioDto();
                callJScenario.setCalljLessonNo(scenario.getCalljLessonNo());
                callJScenario.setCalljConceptName(scenario.getCalljConceptName());
                callJScenario.setCalljQuestionName(scenario.getCalljQuestionName());
                result.add(callJScenario);
            }
        }
        return result;
    }

    /**
     * Get CallJ vocabulary.
     *
     * @param lessonInfoId
     *            id
     * @return list
     */
    private List<CallJVocabularyDto> getCallJVocabulary(final int lessonInfoId) {
        List<CallJVocabularyDto> result = new ArrayList<CallJVocabularyDto>();
        // Get list vocabulary from database via lessonInfoId
        List<Vocabulary> vocabularyList = clientDao.getVocabulary(lessonInfoId);
        // Map field vocabulary and vocabularyEnglishName into CallJScenarioDto
        for (Vocabulary vocabulary : vocabularyList) {
            CallJVocabularyDto callJVocabulary = new CallJVocabularyDto();
            callJVocabulary.setVocabulary(vocabulary.getVocabulary());
            callJVocabulary.setVocabularyEnglishName(vocabulary.getVocabularyEnglishName());
            result.add(callJVocabulary);
        }
        return result;
    }
}