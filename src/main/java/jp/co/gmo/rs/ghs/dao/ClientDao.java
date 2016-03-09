/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao;

import java.util.Date;
import java.util.List;

import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.form.QuestionAnswerForm;
import jp.co.gmo.rs.ghs.model.DictBase;
import jp.co.gmo.rs.ghs.model.DictJA;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.Score;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.model.Vocabulary;

/**
 * create Client: insert, edit, update, delete
 * 
 * @author BaoTQ
 * 
 */
public interface ClientDao {

    /**
     * Get Score by lessonId, lessonInfoId, userId
     * 
     * @param lessonId
     *            the lessonId
     * @param lessonInfoId
     *            the lessonInfoId
     * @param userId
     *            the userId
     * @return score
     */
    Score getScore(int lessonId, int lessonInfoId, int userId);

    /**
     * Update Score by lessonId, lessonInfoId, userId, field, score
     * 
     * @param lessonId
     *            the lessonId
     * @param lessonInfoId
     *            the lessonInfoId
     * @param userId
     *            the userId
     * @param field
     *            the field
     * @param score
     *            the score
     * @return Boolean
     */
    boolean updateScore(int lessonId, int lessonInfoId, int userId, String field, double score);

    /**
     * Get the User Score by lessonId, userId
     * 
     * @param lessonId the lessonId
     * @param userId the userId
     * @return list
     */
    List<Score> getUserLessonScore(int lessonId, int userId);

    /**
     * get List Scenario
     *
     * @param lessonInfoId id
     * @return list
     */
    List<Scenario> getScenario(int lessonInfoId);

    /**
     * get List Vocabulary
     *
     * @param lessonInfoId id
     * @return list
     */
    List<Vocabulary> getVocabulary(int lessonInfoId);

    /**
     * get all Japanese dictionary.
     *
     * @return list
     */
    List<DictJA> getAllJapaneseDict();

    /**
     * get user dictionary.
     *
     * @param tableName name
     * @return dict
     */
    List<DictBase> getUserDict(String tableName);

    /**
     * get user dictionary.
     *
     * @param tableName name
     * @param dictID id
     * @return dict
     */
    DictBase getUserDict(String tableName, int dictID);

    /**
     * write log to DB
     *
     * @param userSessionDto
     * @param questionAnswerForm
     * @param score
     * @param endTime
     * @param answer
     * @return Boolean
     */
    Integer writeLog(UserSessionDto userSessionDto, QuestionAnswerForm questionAnswerForm, double score, Date endTime, String answer);

    /**
     * update userLog
     *
     * @param userLog
     * @return boolean
     */
    boolean updateUserLog(UserLog userLog);

    /**
     * get UserLog by id
     *
     * @param id
     * @return UserLog
     */
    UserLog getUserLogByID(Integer id);

}
