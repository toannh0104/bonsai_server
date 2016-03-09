/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.util.HashMap;
import java.util.List;

import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.UserLogDto;
import jp.co.gmo.rs.ghs.dto.form.LogForm;
import jp.co.gmo.rs.ghs.dto.response.AllSentencesExampleResponseDto;
import jp.co.gmo.rs.ghs.dto.response.AllVocabularyResponseDto;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.model.UserLog;

/**
 * create AdminService
 *
 * @author ThuyTTT
 *
 */
public interface AdminService {

    /**
     * Query [LessonDto] table by lessonId
     *
     * @param lessonId is lessonId of lesson table
     * @param languageId is code language
     * @return lessonDto
     */
    LessonDto getLesson(int lessonId, String languageId);

    /** Query [LessonDto] get all lesson
     *
     * @param learningType is learning type of lesson table
     * @return listLessonDto
     */
    List<LessonDto> getAllLesson(Integer learningType);

    /**
     * Query [LessonInfoDto] table by lessonId and lessonNo
     *
     * @param lessonId is lessonId of lesson table
     * @param lessonNo is lessonNo of lessonInfo table
     * @param languageId is code language
     * @return lessonInfoDto
     */
    LessonInfoDto getLessonInfo(int lessonId, int lessonNo, String languageId);

    /**
     * Query [LessonInfoDto] table by lessonId
     *
     * @param lessonId is lessonId of lesson table
     * @param languageId is code language
     * @return listLessonInfoDto
     */
    List<LessonInfoDto> getLessonInfo(int lessonId, String languageId);

    /**
     * Registration of a new name of creating content
     *
     * @param lessonDto mapping lesson table
     * @return lessonReponseDto
     */
    LessonReponseDto saveAs(LessonDto lessonDto, List<Word> lexicons);

    /**
     * Editing by calling the existing content
     *
     * @param lessonDto mapping lesson table
     * @param type is insert or update
     * @return lessonReponseDto
     */
    LessonReponseDto save(LessonDto lessonDto, int type, List<Word> lexicons);

    /**
     * Delete an existing content
     *
     * @param lessonDto mapping lesson table
     * @return true or false
     */
    boolean delete(LessonDto lessonDto);

    /**
     * Query [Lesson] table by lessonId
     *
     * @param lessonName is lessonName of lesson table
     * @param languageId is code language
     * @param learningType is learning type of lesson table
     * @return lessonDto
     */
    LessonDto getLessonByLessonName(String lessonName, String languageId, Integer learningType);

    /**
     * Get all sentences example
     *
     * @param lesson the lesson
     * @return AllSentencesResponseDto
     */
    AllSentencesExampleResponseDto getAllSentencesDelegate(int lesson, int learningType);

    /**
     * Get all vocabularies
     * @param conceptMap is all concept
     * @param lexicons is all lexicons
     * @param listConceptName is list concept name
     * @return AllVocabularyResponseDto
     */
    AllVocabularyResponseDto getAllVocabularies(HashMap<String, Concept> conceptMap, List<Word> lexicons,
            List<String> listConceptName);

    /**
     * @param learningType
     * @return AllVocabularyResponseDto
     */
    AllVocabularyResponseDto getAllVocabularies(Integer learningType);

    /**
     * Get all Category
     * 
     * @param conceptNames is list concept name
     * @return listCategory
     */
    List<String> getListCategory(List<String> conceptNames, HashMap<String, Concept> conceptMap);

    /**
     * get size use log by mode log
     * 
     * @param modeLog
     * @return count
     */
    List<UserLogDto> getSizeUserLogByModeLog(Integer modeLog, LogForm logForm);

    /**
     * update userLog
     * 
     * @param userDto
     * @return true/false
     */
    boolean updateUseLog(UserLog userLog);

    /**
     * get user log by id
     * 
     * @param id
     * @return userLog
     */
    UserLog getUserLogByID(Integer id);
}