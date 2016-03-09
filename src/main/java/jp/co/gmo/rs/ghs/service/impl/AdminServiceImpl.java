/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import jcall.CALL_conceptInstanceStruct;
import jcall.CALL_database;
import jcall.CALL_grammarRuleStruct;
import jcall.CALL_lessonConceptStruct;
import jcall.CALL_questionStruct;
import jcall.CALL_sentenceStruct;
import jcall.CALL_sentenceWordStruct;
import jcall.CALL_wordStruct;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.constant.MessageConst;
import jp.co.gmo.rs.ghs.dao.AdminDao;
import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.ListSentenceDto;
import jp.co.gmo.rs.ghs.dto.ScenarioDto;
import jp.co.gmo.rs.ghs.dto.SentenceDto;
import jp.co.gmo.rs.ghs.dto.UserLogDto;
import jp.co.gmo.rs.ghs.dto.VocabularyDto;
import jp.co.gmo.rs.ghs.dto.form.LogForm;
import jp.co.gmo.rs.ghs.dto.response.AllSentencesExampleResponseDto;
import jp.co.gmo.rs.ghs.dto.response.AllVocabularyResponseDto;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlot;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotData;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotRestriction;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.model.Lesson;
import jp.co.gmo.rs.ghs.model.LessonInfo;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.model.Vocabulary;
import jp.co.gmo.rs.ghs.service.AdminService;
import jp.co.gmo.rs.ghs.service.ConceptService;
import jp.co.gmo.rs.ghs.service.LessonService;
import jp.co.gmo.rs.ghs.service.LexiconService;
import jp.co.gmo.rs.ghs.util.DateUtils;
import jp.co.gmo.rs.ghs.util.PageUtils;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Admin Service Implement
 *
 * @author ThuyTTT
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

    /* Init AdminDao */
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private LexiconService lexiconService;

    @Autowired
    private ConceptService conceptService;

    @Override
    public LessonDto getLesson(int lessonId, String languageId) {

        // Get lessonDto
        LessonDto lessonDto = convertLessonToObjectDto(adminDao.getLesson(lessonId), languageId);

        // Get lessonInfoDto to set lessonDto
        if (lessonDto != null && lessonDto.getId() != null) {
            List<LessonInfoDto> lessonInfoDtos = convertListLessonInfoToObjectDto(adminDao.getLessonInfo(lessonId), languageId);
            for (int i = 0; i < lessonInfoDtos.size(); i++) {
                LessonInfoDto lessonInfoDto = lessonInfoDtos.get(i);
                // Get list scenario and list vocabulary to set lessonInfo
                if (lessonInfoDto.getId() != null) {

                    // Get list vocabulary
                    lessonInfoDtos.get(i).setVocabularyDtos(convertListVocabularyToObjectDto(
                            adminDao.getVocabulary(lessonInfoDto.getId()), languageId));

                    // Get list scenario
                    lessonInfoDtos.get(i).setScenarioDtos(convertListScenarioToObjectDto(
                            adminDao.getScenario(lessonInfoDto.getId()), languageId));
                }
            }
            // Set lessonInfoDto for lessonDto
            lessonDto.setLessonInfoDtos(lessonInfoDtos);

        }

        return lessonDto;
    }

    @Override
    public LessonInfoDto getLessonInfo(int lessonId, int lessonNo, String languageId) {

        // Get LessonInfoDto
        LessonInfoDto lessonInfoDto = convertLessonInfoToObjectDto(adminDao.getLessonInfo(lessonId, lessonNo), languageId);

        // Get list scenario and list vocabulary to set lessonInfoDto
        if (lessonInfoDto.getId() != null) {

            // Get list scenario
            lessonInfoDto.setScenarioDtos(convertListScenarioToObjectDto(
                    adminDao.getScenario(lessonInfoDto.getId()), languageId));

            // Get list vocabulary
            lessonInfoDto.setVocabularyDtos(convertListVocabularyToObjectDto(
                    adminDao.getVocabulary(lessonInfoDto.getId()), languageId));
        }

        return lessonInfoDto;
    }

    @Override
    public List<LessonInfoDto> getLessonInfo(int lessonId, String languageId) {
        // Get list lessoninfoDto
        List<LessonInfoDto> lessonInfoDtos = convertListLessonInfoToObjectDto(adminDao.getLessonInfo(lessonId), languageId);

        // Get list scenario and list vocabulary to set lessonInfoDto
        for (int i = 0; i < lessonInfoDtos.size(); i++) {
            LessonInfoDto lessonInfoDto = lessonInfoDtos.get(i);
            if (lessonInfoDto.getId() != null) {

                // Get list vocabulary
                lessonInfoDtos.get(i).setVocabularyDtos(convertListVocabularyToObjectDto(
                        adminDao.getVocabulary(lessonInfoDto.getId()), languageId));

                // Get list scenario
                lessonInfoDtos.get(i).setScenarioDtos(convertListScenarioToObjectDto(
                        adminDao.getScenario(lessonInfoDto.getId()), languageId));
            }
        }

        return lessonInfoDtos;
    }

    /**
     * get all lesson
     *
     * @param learningType
     *            is learning type of lesson table
     * @return listLesson
     */
    @Override
    public List<LessonDto> getAllLesson(Integer learningType) {

        // Get list lesson
        List<Lesson> lessons = adminDao.getAllLesson(learningType);

        // Convert lesson to object Dto if lesson not null
        if (lessons != null) {
            return convertListLessonToObjectDto(lessons, null);
        }

        return null;
    }

    /**
     * Convert Lesson to object Dto
     *
     * @param lesson
     * @return lessonDto
     */
    private LessonDto convertLessonToObjectDto(Lesson lesson, String languageId) {
        LessonDto lessonDto = null;
        if (lesson != null) {

            lessonDto = new LessonDto();

            lessonDto.setId(lesson.getId());
            lessonDto.setLessonName(lesson.getLessonName());
            lessonDto.setLessonType(lesson.getLessonType());
            lessonDto.setLevel(lesson.getJapaneseLevel());
            lessonDto.setPercentComplete(lesson.getPercentComplete());
            lessonDto.setAverageScore(lesson.getAverageScore());
            lessonDto.setLessonRange(lesson.getLessonRange());
            lessonDto.setLessonMethodRomaji(lesson.getLessonMethodRomaji());
            lessonDto.setLessonMethodHiragana(lesson.getLessonMethodHiragana());
            lessonDto.setLessonMethodKanji(lesson.getLessonMethodKanji());
            lessonDto.setDeleteFlg(lesson.getDeleteFlg());
            lessonDto.setCreatedAt(lesson.getCreatedAt());
            lessonDto.setUpdatedAt(lesson.getUpdatedAt());
            lessonDto.setDeleteAt(lesson.getDeleteAt());
            lessonDto.setLearningType(lesson.getLearningType());
            if (lesson.getLessonNameMlId() != null && languageId != null) {
                List<String> listMultiLanguage = adminDao.getMultiLanguage(lesson.getLessonNameMlId(), languageId);
                lessonDto.setLessonNameEn(listMultiLanguage.get(0));
                lessonDto.setLessonNameLn(listMultiLanguage.get(1));
            }
        }
        return lessonDto;
    }

    /**
     * Convert list lessonInfo to object Dto
     *
     * @param lessonInfos
     * @return lessonInfoDtos
     */
    private List<LessonDto> convertListLessonToObjectDto(List<Lesson> lessons, String languageId) {
        List<LessonDto> lessonDtos = new ArrayList<LessonDto>();
        if (lessons.size() > 0) {
            for (Lesson lesson : lessons) {
                lessonDtos.add(convertLessonToObjectDto(lesson, languageId));
            }
        }
        return lessonDtos;
    }

    /**
     * Convert lessonInfo to object Dto
     *
     * @param lessonInfo
     * @return lessonInfoDto
     */
    private LessonInfoDto convertLessonInfoToObjectDto(LessonInfo lessonInfo, String languageId) {
        LessonInfoDto lessonInfoDto = null;
        if (lessonInfo != null) {

            lessonInfoDto = new LessonInfoDto();

            lessonInfoDto.setId(lessonInfo.getId());
            lessonInfoDto.setLessonId(lessonInfo.getLessonId());
            lessonInfoDto.setLessonNo(lessonInfo.getLessonNo());
            lessonInfoDto.setPracticeMemoryCondition(lessonInfo.getPracticeMemoryCondition());
            lessonInfoDto.setPracticeWritingCondition(lessonInfo.getPracticeWritingCondition());
            lessonInfoDto.setPracticeConversationCondition(lessonInfo.getPracticeConversationCondition());
            lessonInfoDto.setTestConversationCondition(lessonInfo.getTestConversationCondition());
            lessonInfoDto.setTestMemoryCondition(lessonInfo.getTestMemoryCondition());
            lessonInfoDto.setTestWritingCondition(lessonInfo.getTestWritingCondition());
            lessonInfoDto.setScenarioName(lessonInfo.getScenarioName());
            lessonInfoDto.setScenarioSyntax(lessonInfo.getScenarioSyntax());
            lessonInfoDto.setPracticeNo(lessonInfo.getPracticeNo());
            lessonInfoDto.setPracticeLimit(lessonInfo.getPracticeLimit());
            lessonInfoDto.setDeleteFlg(lessonInfo.getDeleteFlg());
            lessonInfoDto.setCreatedAt(lessonInfo.getCreatedAt());
            lessonInfoDto.setUpdatedAt(lessonInfo.getUpdatedAt());
            lessonInfoDto.setDeleteAt(lessonInfo.getDeleteAt());
            lessonInfoDto.setSpeakerOneName(lessonInfo.getSpeakerOneName());
            lessonInfoDto.setSpeakerTwoName(lessonInfo.getSpeakerTwoName());
            if (lessonInfo.getScenarioNameMlId() != null && languageId != null) {
                List<String> listMultiLanguage = adminDao.getMultiLanguage(lessonInfo.getScenarioNameMlId(), languageId);
                lessonInfoDto.setScenarioNameEn(listMultiLanguage.get(0));
                lessonInfoDto.setScenarioNameLn(listMultiLanguage.get(1));
            }
        }
        return lessonInfoDto;
    }

    /**
     * Convert list lessonInfo to object Dto
     *
     * @param lessonInfos
     * @return lessonInfoDtos
     */
    private List<LessonInfoDto> convertListLessonInfoToObjectDto(List<LessonInfo> lessonInfos, String languageId) {
        List<LessonInfoDto> lessonInfoDtos = new ArrayList<LessonInfoDto>();

        // Check size
        if (lessonInfos.size() > 0) {

            LessonInfoDto lessonInfoDto = null;

            for (LessonInfo lessonInfo : lessonInfos) {
                lessonInfoDto = convertLessonInfoToObjectDto(lessonInfo, languageId);

                if (lessonInfoDto != null) {
                    lessonInfoDtos.add(lessonInfoDto);
                }
            }
        }
        return lessonInfoDtos;
    }

    /**
     * Convert scenario to object Dto
     *
     * @param scenario
     * @return scenarioDto
     */
    private ScenarioDto convertScenarioToObjectDto(Scenario scenario, String languageId) {

        ScenarioDto scenarioDto = null;
        if (scenario != null) {

            scenarioDto = new ScenarioDto();

            scenarioDto.setId(scenario.getId());
            scenarioDto.setLessonInfoId(scenario.getLessonInfoId());
            scenarioDto.setScenarioPart(scenario.getScenarioPart());
            scenarioDto.setScenario(scenario.getScenario());
            scenarioDto.setScenarioOrder(scenario.getScenarioOrder());
            scenarioDto.setDeleteFlg(scenario.getDeleteFlg());
            scenarioDto.setCreatedAt(scenario.getCreatedAt());
            scenarioDto.setUpdatedAt(scenario.getUpdatedAt());
            scenarioDto.setDeleteAt(scenario.getDeleteAt());
            scenarioDto.setCalljLessonNo(scenario.getCalljLessonNo());
            scenarioDto.setCalljConceptName(scenario.getCalljConceptName());
            scenarioDto.setCalljQuestionName(scenario.getCalljQuestionName());
            scenarioDto.setUserInputFlg(scenario.getUserInputFlg());
            scenarioDto.setCalljWord(scenario.getCalljWord());
            scenarioDto.setRubyWord(scenario.getRubyWord());
            scenarioDto.setCategoryWord(scenario.getCategoryWord());
            scenarioDto.setLearningType(scenario.getLearningType());
            if (scenario.getScenarioMlId() != null && languageId != null) {
                List<String> listMultiLanguage = adminDao.getMultiLanguage(scenario.getScenarioMlId(), languageId);
                scenarioDto.setScenarioEn(listMultiLanguage.get(0));
                scenarioDto.setScenarioLn(listMultiLanguage.get(1));
            }
        }
        return scenarioDto;
    }

    /**
     * Convert list scenario to object Dto
     *
     * @param scenarios
     * @return scenarioDtos
     */
    private List<ScenarioDto> convertListScenarioToObjectDto(List<Scenario> scenarios, String languageId) {

        List<ScenarioDto> scenarioDtos = new ArrayList<ScenarioDto>();

        // Check size
        if (scenarios.size() > 0) {

            ScenarioDto scenarioDto = null;

            for (Scenario scenario : scenarios) {
                scenarioDto = convertScenarioToObjectDto(scenario, languageId);

                if (scenarioDto != null) {
                    scenarioDtos.add(scenarioDto);
                }
            }
        }
        return scenarioDtos;
    }

    /**
     * Convert vocabulary to object Dto
     *
     * @param vocabulary
     * @return vocabularyDto
     */
    private VocabularyDto convertVocabularyToObjectDto(Vocabulary vocabulary, String languageId) {
        VocabularyDto vocabularyDto = new VocabularyDto();
        if (vocabulary != null) {

            vocabularyDto.setId(vocabulary.getId());
            vocabularyDto.setLessonInfoId(vocabulary.getLessonInfoId());
            vocabularyDto.setVocabulary(vocabulary.getVocabulary());
            vocabularyDto.setVocabularyOrder(vocabulary.getVocabularyOrder());
            vocabularyDto.setDeleteFlg(vocabulary.getDeleteFlg());
            vocabularyDto.setCreatedAt(vocabulary.getCreatedAt());
            vocabularyDto.setUpdatedAt(vocabulary.getUpdatedAt());
            vocabularyDto.setDeleteAt(vocabulary.getDeleteAt());
            vocabularyDto.setUserInputFlg(vocabulary.getUserInputFlg());
            vocabularyDto.setVocabularyEnglishName(vocabulary.getVocabularyEnglishName());
            vocabularyDto.setRubyWord(vocabulary.getRubyWord());
            vocabularyDto.setVocabularyCategories(vocabulary.getVocabularyCategories());
            vocabularyDto.setLearningType(vocabulary.getLearningType());
            vocabularyDto.setVocabularyKanaName(vocabulary.getVocabularyKanaName());
            if (vocabulary.getVocabularyMlId() != null && languageId != null) {
                List<String> listMultiLanguage = adminDao.getMultiLanguage(vocabulary.getVocabularyMlId(), languageId);
                vocabularyDto.setVocabularyEn(listMultiLanguage.get(0));
                vocabularyDto.setVocabularyLn(listMultiLanguage.get(1));
            }
        }
        return vocabularyDto;
    }

    /**
     * Convert list vocabulary to object Dto
     *
     * @param vocabularys
     * @return vocabularyDtos
     */
    private List<VocabularyDto> convertListVocabularyToObjectDto(List<Vocabulary> vocabularys, String languageId) {
        List<VocabularyDto> vocabularyDtos = new ArrayList<VocabularyDto>();
        if (vocabularys.size() > 0) {
            for (Vocabulary vocabulary : vocabularys) {
                vocabularyDtos.add(convertVocabularyToObjectDto(vocabulary, languageId));
            }
        }
        return vocabularyDtos;
    }

    /**
     * Registration of a new name of creating content
     *
     * @param lessonDto
     */
    @Override
    public LessonReponseDto saveAs(LessonDto lessonDto, List<Word> lexicons) {
        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        try {
            saveNewLexiconWord(lessonDto, lexicons);
            Integer lessonId = adminDao.saveAs(lessonDto);
            lessonReponseDto.setMessage(lessonId.toString());
            lessonReponseDto.setStatus(ConstValues.Status.OK);
        } catch (Exception e) {
            lessonReponseDto.setMessage(MessageConst.MSG_E0017);
            lessonReponseDto.setStatus(ConstValues.Status.NG);
        }
        return lessonReponseDto;
    }

    /**
     * Editing by calling the existing content
     *
     * @param lessonDto
     * @param type
     */
    @Override
    public LessonReponseDto save(LessonDto lessonDto, int type, List<Word> lexicons) {
        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        try {
            saveNewLexiconWord(lessonDto, lexicons);
            Integer lessonId = adminDao.save(lessonDto, type);
            lessonReponseDto.setMessage(lessonId.toString());
            lessonReponseDto.setStatus(ConstValues.Status.OK);
        } catch (Exception e) {
            lessonReponseDto.setMessage(MessageConst.MSG_E0017);
            lessonReponseDto.setStatus(ConstValues.Status.NG);
        }
        return lessonReponseDto;
    }

    /**
     * Delete an existing content
     *
     * @param lessonDto
     */
    @Override
    public boolean delete(LessonDto lessonDto) {
        boolean result = false;
        if (lessonDto.getId() != null) {
            try {
                result = adminDao.delete(lessonDto);
            } catch (Exception e) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Get lesson by lessonName
     *
     * @param lessonName
     * @param learningType
     *            is learning type of lesson table
     * @return lessonDto
     */
    @Override
    public LessonDto getLessonByLessonName(String lessonName, String languageId, Integer learningType) {
        LessonDto lessonDto = null;
        try {
            Lesson lesson = adminDao.getLessonByLessonName(lessonName, learningType);
            lessonDto = convertLessonToObjectDto(lesson, languageId);
        } catch (Exception e) {
            return null;
        }
        return lessonDto;
    }

    /**
     * Get all sentences example
     */
    @Override
    public AllSentencesExampleResponseDto getAllSentencesDelegate(int lesson, int learningType) {

        AllSentencesExampleResponseDto responseDto = new AllSentencesExampleResponseDto();
        responseDto.setStatus(ConstValues.Status.OK);

        try {

            Map<String, SentenceDto> map = new HashMap<String, SentenceDto>();
            if (learningType == ConstValues.MODE_SECURITY) {
                List<Scenario> scenarios = adminDao.getScenarioByLearningType(ConstValues.MODE_JAPANESE);
                for (int i = 0; i < scenarios.size(); i++) {
                    Scenario scenario = scenarios.get(i);

                    SentenceDto sentenceDto = new SentenceDto();
                    sentenceDto.setLessonId(lesson);
                    sentenceDto.setNameQuestion(scenario.getCalljQuestionName());
                    sentenceDto.setNameConcept(scenario.getCalljConceptName());
                    sentenceDto.setRubyWords(scenario.getRubyWord());
                    sentenceDto.setFullSentence(scenario.getScenario());
                    sentenceDto.setCategoryWord(scenario.getCategoryWord());
                    sentenceDto.setCalljWord(scenario.getCalljWord());

                    // Add sentence to list sentence
                    map.put(scenario.getScenario(), sentenceDto);
                }

                // Set result
                responseDto.setResult(new ArrayList<SentenceDto>(map.values()));
            } else {
                ListSentenceDto listSentenceDto = new ListSentenceDto();
                listSentenceDto = lessonService.loadAllSentencesDelegate(lesson);
                // Set result
                responseDto.setResult(listSentenceDto.getResult());
            }

        } catch (Exception e) {

            e.printStackTrace();
            // Error
            responseDto.setStatus(ConstValues.Status.NG);
        }

        return responseDto;
    }

    /**
     * Get all vocabularies
     *
     * @param lessonId
     *            lesson id
     * @param conceptIndex
     *            index of concept
     * @param lessonMethod
     *            lesson method (romaji, kana, kanji)
     * @return AllVocabularyResponseDto
     */
    @Override
    public AllVocabularyResponseDto getAllVocabularies(HashMap<String, Concept> conceptMap, List<Word> lexicons,
            List<String> listConceptName) {

        AllVocabularyResponseDto responseDto = new AllVocabularyResponseDto();
        responseDto.setStatus(ConstValues.Status.OK);

        Set<Word> words = new HashSet<Word>();
        try {
            for (String conceptName : listConceptName) {

                words.addAll(
                        getWordByConcept(conceptMap, conceptName, lexicons,
                                conceptService.getGrammarByConceptName(conceptName)));
            }

            // Create new wordResult base on words
            List<Word> wordResult = new ArrayList<Word>(words);
            // Sort wordResult base on romaji
            Collections.sort(wordResult, new Comparator<Word>() {
                public int compare(Word word1, Word word2) {
                    return word1.getRomaji().compareTo(word2.getRomaji());
                }
            });

            // Set result
            responseDto.setResult(wordResult);
        } catch (Exception e) {
            e.printStackTrace();
            // Error
            responseDto.setStatus(ConstValues.Status.NG);
        }

        return responseDto;
    }

    /**
     * get list word
     *
     * @param db
     * @param conceptStruct
     * @param listword
     * @param conceptMap
     * @param conceptName
     * @param lexicons
     * @param conceptSlotDataList
     * @param conceptSlotList
     * @return list words
     */
    private List<Word> getListWord(
            Concept concept,
            CALL_database db,
            CALL_lessonConceptStruct conceptStruct,
            HashMap<String,
            Concept> conceptMap,
            String conceptName,
            List<Word> lexicons,
            List<ConceptSlotData> conceptSlotDataList,
            List<ConceptSlot> conceptSlotList,
            String slotName) {

        LexiconServiceImpl lexiconServiceImpl = new LexiconServiceImpl();
        List<Word> listword = new ArrayList<>();
        for (ConceptSlotData conceptSlotData : conceptSlotDataList) {
            if (conceptSlotData.getType() == 1) {
                listword.addAll(lexiconServiceImpl.getListWordByCatelogy(lexicons, conceptSlotData.getData()));
                // NOT CHECK WHEN
            } else if (conceptSlotData.getType() == 2 && !conceptSlotData.getData().equals("time")) {
                listword.addAll(
                        getWordByConcept(conceptMap, conceptSlotData.getData(), lexicons,
                                conceptService.getGrammarByConceptName(concept.getName())));
            } else if (conceptSlotData.getType() == 0) {
                listword.addAll(getWordType0(
                        db,
                        conceptStruct,
                        lexicons,
                        conceptSlotData.getData(),
                        conceptSlotList,
                        slotName));
            }
        }
        return listword;
    }

    /**
     * get word incase type is 0
     *
     * @param db
     * @param conceptStruct
     * @param listword
     * @param lexicons
     * @param data
     * @param conceptSlotList
     * @return list words
     */
    @SuppressWarnings("unchecked")
    private List<Word> getWordType0(
            CALL_database db,
            CALL_lessonConceptStruct conceptStruct,
            List<Word> lexicons,
            String data,
            List<ConceptSlot> conceptSlotList,
            String slotName) {

        boolean flagCheckNameSlot = false;
        List<Word> listword = new ArrayList<>();
        CALL_grammarRuleStruct grule = null;
        // Get word by kanji
        CALL_wordStruct wordByKanji = db.lexicon.getWord(data);

        List<String> listKanaString = new ArrayList<>();

        // Get the concept instance
        Vector<CALL_conceptInstanceStruct> conceptsIn =
                db.concepts.getInstanceGHS(conceptStruct.concept, 0, conceptStruct.grammar, null, false);
        for (CALL_conceptInstanceStruct instance : conceptsIn) {
            // Init Obj
            Vector<CALL_sentenceStruct> sentenceStructs = null;
            if (grule == null) {
                // Start by getting the top level rule
                grule = db.grules.getGrammarRule(null, instance.getGrammar());
            }
            // Get all sentence from 1 instance
            sentenceStructs = new CALL_questionStruct().getSentencesFromInstance(db, instance, grule);
            for (CALL_sentenceStruct sentence : sentenceStructs) {
                // Get list word in sentence
                Vector<CALL_sentenceWordStruct> sentenceWord = sentence.getSentenceWords();
                String kana = ConstValues.CONST_STRING_EMPTY;
                String kanji = ConstValues.CONST_STRING_EMPTY;

                for (int i = 0; i < sentenceWord.size(); i++) {
                    flagCheckNameSlot = false;

                    // Check ComponentName have in list slot
                    if (slotName.equals(sentenceWord.elementAt(i).getGrammarRule()) && !PageUtils.checkParticle(slotName)) {
                        flagCheckNameSlot = true;
                    }

                    if (sentenceWord.elementAt(i).getTransformationRule() != null && flagCheckNameSlot) {
                        if (PageUtils.checkVerbIs(sentenceWord.elementAt(i).getWord().getEnglish())
                                && sentenceWord.elementAt(i).getWord().getEnglish().equals(wordByKanji.english)) {

                            CALL_wordStruct wordStruct = sentenceWord.elementAt(i).getWord();
                            String rName = sentenceWord.elementAt(i).getTransformationRule();
                            int sign = sentenceWord.elementAt(i).getSign();
                            int tense = sentenceWord.elementAt(i).getTense();
                            int politeness = sentenceWord.elementAt(i).getPoliteness();
                            int question = sentenceWord.elementAt(i).getQuestion();

                            kana = db.applyTransformationRule(wordStruct, rName, sign, tense, politeness, question, false);
                            kanji = db.applyTransformationRule(wordStruct, rName, sign, tense, politeness, question, true);

                            Word newWord = null;
                            for (Word word : lexicons) {
                                if (word.getKana().equals(data) && !listKanaString.contains(kana)) {
                                    // Add list word
                                    setValueForLisWord(word, newWord, kana, kanji, listword, listKanaString);
                                    break;
                                } else if (word.getKanji().equals(data) && !listKanaString.contains(kana)) {
                                    // Add list word
                                    setValueForLisWord(word, newWord, kana, kanji, listword, listKanaString);
                                    break;
                                } else if (word.getEnglish().equals(data) && !listKanaString.contains(kana)) {
                                    // Add list word
                                    setValueForLisWord(word, newWord, kana, kanji, listword, listKanaString);
                                    break;
                                }
                            }
                        }
                    } else if (sentenceWord.elementAt(i).getTransformationRule() == null && flagCheckNameSlot) {
                        for (Word word : lexicons) {
                            if (word.getKana().equals(data) && !listKanaString.contains(kana)) {
                                listword.add(word);
                                // Add list kana
                                listKanaString.add(word.getKana());
                                break;
                            } else if (word.getKanji().equals(data) && !listKanaString.contains(kana)) {
                                listword.add(word);
                                // Add list kana
                                listKanaString.add(word.getKana());
                                break;
                            } else if (word.getEnglish().equals(data) && !listKanaString.contains(kana)) {
                                listword.add(word);
                                // Add list kana
                                listKanaString.add(word.getKana());
                                break;
                            }
                        }
                    }
                }
            }
            break;
        }
        List<Word> result = removeDuplicateWord(listword);
        return result;
    }

    /**
     * Set value for list word
     * @param oldWord
     * @param newWord
     * @param kana
     * @param kanji
     * @param listword
     * @param listKanaString
     */
    private void setValueForLisWord(Word oldWord, Word newWord, String kana,
            String kanji, List<Word> listword, List<String> listKanaString) {
        // Init new word
        newWord = new Word();
        // Copy from old word
        BeanUtils.copyProperties(oldWord, newWord);
        newWord.setKana(kana);
        newWord.setKanji(kanji);
        listword.add(newWord);
        // Add list kana
        listKanaString.add(kana);
    }

    /**
     * Remove duplicate word
     * @param list
     * @return
     */
    private List<Word> removeDuplicateWord(List<Word> list) {

        // Store unique items in result.
        List<Word> result = new ArrayList<Word>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<String>();

        // Loop over argument list.
        for (Word item : list) {

            // If object is not in set, add it to the list and the set.
            if (!set.contains(item.getKana())) {
                result.add(item);
                set.add(item.getKana());
            }
        }
        return result;
    }

    /**
     * get word by concept
     *
     * @param listword
     *            is list words
     * @param conceptMap
     *            is list concept
     * @param conceptName
     *            is concept name
     * @param lexicons
     *            is all lexicons
     * @return list word
     */
    private List<Word> getWordByConcept(
            HashMap<String, Concept> conceptMap, String conceptName, List<Word> lexicons, String grammar) {
        // Get data
        CALL_database db = new CALL_database();
        // Get conceptStruct
        CALL_lessonConceptStruct conceptStruct = conceptService.getConceptStructByConceptName(conceptName, grammar);

        List<Word> listword = new ArrayList<>();
        Concept concept = conceptMap.get(conceptName);
        List<ConceptSlot> conceptSlotList = concept.getConceptSlotList();
        for (ConceptSlot conceptSlot : conceptSlotList) {
            if (conceptSlot.getConceptSlotDataList().size() > 0) {
                listword.addAll(getListWord(concept, db, conceptStruct, conceptMap, conceptName, lexicons,
                        conceptSlot.getConceptSlotDataList(), conceptSlotList, conceptSlot.getName()));
            }
            if (conceptSlot.getConceptSlotRestrictionList().size() > 0) {
                for (ConceptSlotRestriction conceptSlotRestriction : conceptSlot.getConceptSlotRestrictionList()) {
                    listword.addAll(getListWord(concept, db, conceptStruct, conceptMap, conceptName, lexicons,
                            conceptSlotRestriction.getConceptSlotDataList(), conceptSlotList, conceptSlot.getName()));
                }
            }
        }
        return listword;
    }

    /**
     * Get all Category
     *
     * @param conceptNames
     *            is list concept name
     * @return catesList
     */
    @Override
    public List<String> getListCategory(List<String> conceptNames, HashMap<String, Concept> conceptMap) {
        Set<String> categorys = new HashSet<String>();
        Set<String> listCategory = new HashSet<String>();
        for (String name : conceptNames) {
            categorys.addAll(getListCategoryByConcept(listCategory, name, conceptMap));
        }
        List<String> catesList = new ArrayList<String>(categorys);
        // Sort catesList base on name
        Collections.sort(catesList, new Comparator<String>() {
            public int compare(String cate1, String cate2) {
                return cate1.compareTo(cate2);
            }
        });
        return catesList;
    }

    /**
     * @param lessonDto
     */
    private void saveNewLexiconWord(LessonDto lessonDto, List<Word> wordsOld) {
        List<LessonInfoDto> lessonInfoDtos = lessonDto.getLessonInfoDtos();
        List<Word> wordsNew = new ArrayList<Word>();
        for (LessonInfoDto lessonInfo : lessonInfoDtos) {
            List<VocabularyDto> vocabularyDtos = lessonInfo.getVocabularyDtos();
            List<String> categories = new ArrayList<String>();
            for (VocabularyDto vocabularyDto : vocabularyDtos) {
                Integer userInputFlg = vocabularyDto.getUserInputFlg();
                if (ConstValues.USER_INPUT_FLAG == userInputFlg) {
                    String[] data = vocabularyDto.getVocabularyCategories().split(",");
                    for (int k = 0; k < data.length; k++) {
                        if (!data[k].equals(ConstValues.CONST_STRING_EMPTY)) {
                            categories.add(data[k]);
                        }
                    }
                    Word word = new Word();
                    word.setCategoryList(categories);
                    word.setEnglish(vocabularyDto.getVocabularyEnglishName());
                    word.setKana(vocabularyDto.getVocabularyKanaName());
                    word.setKanji(vocabularyDto.getVocabulary());
                    wordsNew.add(word);
                    categories = new ArrayList<String>();
                }
            }
        }
        lexiconService.saveListWord(wordsNew, wordsOld);
    }

    private Set<String> getListCategoryByConcept(
            Set<String> listCategory,
            String conceptName,
            HashMap<String,
            Concept> conceptMap) {
        Concept concept = conceptMap.get(conceptName);
        List<ConceptSlot> conceptSlotList = concept.getConceptSlotList();
        for (ConceptSlot conceptSlot : conceptSlotList) {
            if (conceptSlot.getConceptSlotDataList().size() > 0) {
                listCategory.addAll(getConceptSlotName(listCategory, conceptSlot.getConceptSlotDataList(), conceptMap));
            }
            if (conceptSlot.getConceptSlotRestrictionList().size() > 0) {
                for (ConceptSlotRestriction conceptSlotRestriction : conceptSlot.getConceptSlotRestrictionList()) {
                    listCategory.addAll(getConceptSlotName(
                            listCategory,
                            conceptSlotRestriction.getConceptSlotDataList(),
                            conceptMap));
                }
            }
        }
        return listCategory;
    }

    private Set<String> getConceptSlotName(Set<String> listCategory, List<ConceptSlotData> conceptSlotDataList,
            HashMap<String, Concept> conceptMap) {
        for (ConceptSlotData conceptSlotData : conceptSlotDataList) {
            if (conceptSlotData.getType() == 1) {
                listCategory.add(conceptSlotData.getData());
            } else if (conceptSlotData.getType() == 2) {
                listCategory.addAll(getListCategoryByConcept(listCategory, conceptSlotData.getData(), conceptMap));
            }
        }
        return listCategory;
    }

    @Override
    public AllVocabularyResponseDto getAllVocabularies(Integer learningType) {
        AllVocabularyResponseDto responseDto = new AllVocabularyResponseDto();
        responseDto.setStatus(ConstValues.Status.OK);

        try {

            List<Vocabulary> vocabularies = adminDao.getVocabularyByLearningType(learningType);
            Map<String, Word> mapWords = new HashMap<String, Word>();

            for (Vocabulary voca : vocabularies) {
                Word word = new Word();
                word.setKanji(voca.getVocabulary());
                word.setRubyWords(voca.getRubyWord());
                word.setEnglish(voca.getVocabularyEnglishName());
                word.setKana(voca.getVocabularyKanaName());
                word.setCategoryList(getListCategory(voca.getVocabularyCategories()));

                mapWords.put(voca.getVocabulary(), word);
            }

            // Create new wordResult base on words
            List<Word> wordResult = new ArrayList<Word>(mapWords.values());
            // Sort wordResult base on romaji
            Collections.sort(wordResult, new Comparator<Word>() {
                public int compare(Word word1, Word word2) {
                    return word1.getKanji().compareTo(word2.getKanji());
                }
            });

            // Set result
            responseDto.setResult(wordResult);
        } catch (Exception e) {
            e.printStackTrace();
            // Error
            responseDto.setStatus(ConstValues.Status.NG);
        }

        return responseDto;
    }

    private List<String> getListCategory(String vocabularyCategories) {
        if (StringUtils.isEmpty(vocabularyCategories)) {
            return null;
        }
        String[] list = vocabularyCategories.split(",");
        return Arrays.asList(list);
    }

    /**
     * get size user log by mode log
     * 
     * @param modeLog
     * @param logForm
     */
    @Override
    public List<UserLogDto> getSizeUserLogByModeLog(Integer modeLog, LogForm logForm) {
        List<UserLog> listUserLogs = adminDao.getSizeUserLogByModeLog(modeLog, logForm);
        List<UserLogDto> lisUserLogDtos = new ArrayList<UserLogDto>();
        if (listUserLogs.size() > 0) {
            for (UserLog userLog : listUserLogs) {
                lisUserLogDtos.add(convertUserLog(userLog));
            }
        }
        return lisUserLogDtos;
     }

    /**
     * convert user log to user log dto
     * 
     * @param userLog
     * @return
     */
    private UserLogDto convertUserLog(UserLog userLog) {
        UserLogDto userLogDto = new UserLogDto();
        userLogDto.setId(userLog.getId());
        userLogDto.setUserId(userLog.getUserId());
        userLogDto.setUserName(userLog.getUserName());
        userLogDto.setDeviceId(userLog.getDeviceId());
        userLogDto.setDeviceName(userLog.getDeviceName());
        userLogDto.setDeviceVersion(userLog.getDeviceVersion());
        userLogDto.setLocation(userLog.getLocation());
        userLogDto.setStartTime(DateUtils.formatDate(userLog.getStartTime()));
        userLogDto.setEndTime(DateUtils.formatDate(userLog.getEndTime()));
        userLogDto.setModeLearning(userLog.getModeLearning());
        userLogDto.setLessonNo(userLog.getLessonNo());
        userLogDto.setModePracticeOrTest(userLog.getModePracticeOrTest());
        userLogDto.setCourse(userLog.getCourse());
        userLogDto.setScenarioOrWord(userLog.getScenarioOrWord());
        userLogDto.setPracticeTime(userLog.getPracticeTimes());
        userLogDto.setAnswer(userLog.getAnswer());
        userLogDto.setScore(userLog.getScore());
        userLogDto.setLogFlg(userLog.getLogFlg());
        userLogDto.setToken(userLogDto.getToken());
        userLogDto.setModeSkill(userLog.getModeSkill());
        userLogDto.setTime(DateUtils.getTime(userLog.getStartTime(), userLog.getEndTime()));
        return userLogDto;
    }

    /**
     * update userLog
     */
    @Override
    public boolean updateUseLog(UserLog userLog) {

        return adminDao.updateUserLog(userLog);
    }

    @Override
    public UserLog getUserLogByID(Integer id) {
        return adminDao.getUserLogByID(id);
    }

}