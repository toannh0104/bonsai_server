/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao;

import java.util.List;

import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.form.LogForm;
import jp.co.gmo.rs.ghs.model.Lesson;
import jp.co.gmo.rs.ghs.model.LessonInfo;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.model.Vocabulary;

/**
 * create AdminDao: insert, edit, update, delete
 *
 * @author ThuyTTT
 *
 */
public interface AdminDao {

    /**
     * Query [Lesson] table by lessonId
     *
     * @param lessonId is lessonId of lesson table
     * @return lesson
     */
    Lesson getLesson(Integer lessonId);

    /**
     * Query [Lesson] table get all Lesson
     *
     * @param learningType is learning type of lesson table
     * @return listLesson
     */
    List<Lesson> getAllLesson(Integer learningType);

    /**
     * Query [LessonInfo] table by lessonInfoId
     *
     * @param lessonInfoId is lessonInfoId of lessonInfo table
     * @return lessonInfo
     */
    LessonInfo getLessonInfoById(int lessonInfoId);

    /** Query [LessonInfo] table by lessonId
     *
     * @param lessonId is lessonId of lesson table
     * @return listLessonInfo
     */
    List<LessonInfo> getLessonInfo(int lessonId);

    /** Query [LessonInfo] table by lessonId and lessonNo
     *
     * @param lessonId is lessonId of lesson table
     * @param lessonNo is lessonNo od lessonInfo table
     * @return lessonInfo
     */
    LessonInfo getLessonInfo(int lessonId, int lessonNo);

    /** Query [Scenario] table by lessonInfoId
     *
     * @param lessonInfoId is lessonInfoId if lessonInfo table
     * @return listScenario
     */
    List<Scenario> getScenario(int lessonInfoId);

    /** Query [Scenario] table by lessonInfoId and part
     *
     * @param lessonInfoId is lessonInfoId if lessonInfo table
     * @param part is part of scenario table
     * @return listScenario
     */
    List<Scenario> getScenario(int lessonInfoId, int part);

    /** Query [Vocabulary] table by lessonInfoId
     *
     * @param lessonInfoId is lessonInfoId if lessonInfo table
     * @return listVocabulary
     */
    List<Vocabulary> getVocabulary(int lessonInfoId);

    /** Registration of a new name of creating content
     *
     * @param lessonDto mapping with lesson table
     * @return lessonId
     */
    Integer saveAs(LessonDto lessonDto);

    /** Editing by calling the existing content
     *
     * @param lessonDto mapping with lesson table
     * @param type is insert or update
     * @return lessonID
     */
    Integer save(LessonDto lessonDto, int type);

    /** Delete an existing content
     *
     * @param lessonDto mapping with lesson table
     * @return true or false
     */
    boolean delete(LessonDto lessonDto);

    /** Query [Lesson] table by lessonId
     *
     * @param lessonName is lessonName of lesson table
     * @param learningType is learning type of lesson table
     * @return lesson
     */
    Lesson getLessonByLessonName(String lessonName, Integer learningType);

    /** Query multi language
     *
     * @param lessonNameMlId is id if DictJA table
     * @param motherLanguage is code language choosed
     * @return listString
     */
    List<String> getMultiLanguage(Integer lessonNameMlId, String motherLanguage);
    
    /**
     * insert multi language in db
     *
     * @param japanText japanese text
     * @return dictJaId
     */
    Integer insertMultiLanguage(String japanText);

    /**
     * @param learningType
     * @return
     */
    List<Scenario> getScenarioByLearningType(int learningType);

    /**
     * @param learningType
     * @return
     */
    List<Vocabulary> getVocabularyByLearningType(Integer learningType);

    /**
     * get size user log by mode log
     * 
     * @param modeLog
     * @param logForm
     * @return listUserLog
     */
    List<UserLog> getSizeUserLogByModeLog(Integer modeLog, LogForm logForm);

    /**
     * update user log
     * 
     * @param id
     * @return true or false
     */
    boolean updateUserLog(UserLog userLog);

    /**
     * Get user log by id
     *
     * @param id
     * @return UserLog
     */
    UserLog getUserLogByID(Integer id);
}