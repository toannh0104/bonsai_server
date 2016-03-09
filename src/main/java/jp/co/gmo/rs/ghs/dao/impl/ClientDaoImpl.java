/*
 * Copyright (C) 2015 by GMO Runsystem Company Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.ClientDao;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.form.QuestionAnswerForm;
import jp.co.gmo.rs.ghs.model.DictBase;
import jp.co.gmo.rs.ghs.model.DictJA;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.Score;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.model.Vocabulary;
import jp.co.gmo.rs.ghs.util.DateUtils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * create AdminDao: insert, edit, update, delete
 * 
 * @author ThuyTTT
 * 
 */
@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {

    /* Init sessionFactory is null */
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Score getScore(int lessonId, int lessonInfoId, int userId) {
        Session session = this.sessionFactory.getCurrentSession();

        // Query get score
        Query query = session
                .createQuery("from Score where lesson_id = ? and lesson_info_id = ? and user_id = ? and delete_flg = ?");

        int i = 0;
        // Set parameter
        query.setParameter(i++, lessonId);
        query.setParameter(i++, lessonInfoId);
        query.setParameter(i++, userId);
        query.setParameter(i, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list lesson info
        Score score = (Score) query.uniqueResult();

        return score;
    }

    @Override
    public boolean updateScore(int lessonId, int lessonInfoId, int userId, String field, double score) {
        Session session = this.sessionFactory.getCurrentSession();

        // get time
        Date today = DateUtils.now();

        try {

            // check insert or update
            Score scoreDelegate = getScore(lessonId, lessonInfoId, userId);

            // update
            if (scoreDelegate != null) {

                // set score to field
                if (field.equals("practice_memory_score")) {
                    scoreDelegate.setPracticeMemoryScore(score);
                }
                if (field.equals("practice_writing_score")) {
                    scoreDelegate.setPracticeWritingScore(score);
                }
                if (field.equals("practice_conversation_score")) {
                    scoreDelegate.setPracticeConversationScore(score);
                }
                if (field.equals("test_memory_score")) {
                    scoreDelegate.setTestMemoryScore(score);
                }
                if (field.equals("test_writing_score")) {
                    scoreDelegate.setTestWritingScore(score);
                }
                if (field.equals("test_conversation_score")) {
                    scoreDelegate.setTestConversationScore(score);
                }

                // set date update
                scoreDelegate.setUpdatedAt(DateUtils.now());

                // update score
                session.update(scoreDelegate);

                // insert
            } else {

                Score scoreInsert = new Score();

                // Set lesson id
                scoreInsert.setLessonId(lessonId);

                // Set lesson info id
                scoreInsert.setLessonInfoId(lessonInfoId);

                // Set user id
                scoreInsert.setUserId(userId);

                // Set score to field
                if (field.equals("practice_memory_score")) {
                    scoreInsert.setPracticeMemoryScore(score);
                }
                if (field.equals("practice_writing_score")) {
                    scoreInsert.setPracticeWritingScore(score);
                }
                if (field.equals("practice_conversation_score")) {
                    scoreInsert.setPracticeConversationScore(score);
                }
                if (field.equals("test_memory_score")) {
                    scoreInsert.setTestMemoryScore(score);
                }
                if (field.equals("test_writing_score")) {
                    scoreInsert.setTestWritingScore(score);
                }
                if (field.equals("test_conversation_score")) {
                    scoreInsert.setTestConversationScore(score);
                }

                // Set delete flag
                scoreInsert.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);

                // Set date create
                scoreInsert.setCreatedAt(today);

                // insert score
                session.save(scoreInsert);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Score> getUserLessonScore(int lessonId, int userId) {
        Session session = this.sessionFactory.getCurrentSession();

        // Query get score
        Query query = session.createQuery("from Score where lesson_id = ? and user_id = ? and delete_flg = ?");

        // Set parameter
        query.setParameter(0, lessonId);
        query.setParameter(1, userId);
        query.setParameter(2, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list lesson info
        List<Score> score = query.list();

        return score;
    }

    /**
     * get List Scenario
     * 
     * @param lessonInfoId
     *            id
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Scenario> getScenario(int lessonInfoId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from Scenario where lesson_info_id = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonInfoId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<Scenario> scenarios = query.list();

        return scenarios;
    }

    /**
     * get List Vocabulary
     * 
     * @param lessonInfoId
     *            id
     * @return list
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Vocabulary> getVocabulary(int lessonInfoId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get vocabulary
        Query query = session.createQuery("from Vocabulary where lesson_info_id = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonInfoId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list vocabulary
        List<Vocabulary> vocabularys = query.list();

        return vocabularys;
    }

    /**
     * get all Japanese dictionary.
     * 
     * @return list
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DictJA> getAllJapaneseDict() {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from DictJA where delete_flg =?");

        // Set parameter
        query.setParameter(0, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<DictJA> japaneseDict = query.list();

        return japaneseDict;
    }

    /**
     * get user dictionary.
     * 
     * @param tableName
     *            name
     * @return dict
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DictBase> getUserDict(String tableName) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from " + tableName + " where delete_flg =?");

        // Set parameter
        query.setParameter(0, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<DictBase> japaneseDict = query.list();

        return japaneseDict;
    }

    /**
     * get user dictionary by id.
     * 
     * @param tableName
     *            name
     * @param dictID
     *            id
     * @return dict
     */
    @Override
    public DictBase getUserDict(String tableName, int dictID) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from " + tableName + " where dict_ja_id=? delete_flg =?");

        // Set parameter
        query.setParameter(0, dictID);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        DictBase dict = (DictBase) query.uniqueResult();

        return dict;
    }

    /**
     * write log to DB
     * 
     * @param userSessionDto
     * @param questionAnswerForm
     * @param score
     * @param endTime
     * @param answer
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Integer writeLog(UserSessionDto userSessionDto, QuestionAnswerForm questionAnswerForm, double score, Date endTime, String answer) {
        // Get session
        Session session = this.sessionFactory.getCurrentSession();

        Integer id = null;

        // get time
        Date today = DateUtils.now();

        try {
            // Format date
            SimpleDateFormat sf = new SimpleDateFormat(ConstValues.DATE_FORMAT);
            Date startTime = sf.parse(questionAnswerForm.getStartTime());

            UserLog userLog = new UserLog();

            // Set user id
            userLog.setUserId(userSessionDto.getUserId());

            // Set user name
            userLog.setUserName(userSessionDto.getUserName());

            // Set device id
            userLog.setDeviceId(userSessionDto.getUuid());

            // Set device name
            userLog.setDeviceName(userSessionDto.getDeviceName());

            // Set device version
            userLog.setDeviceVersion(userSessionDto.getDeviceVersion());

            // Set location
            userLog.setLocation(userSessionDto.getLocation());

            // Set token
            userLog.setToken(userSessionDto.getToken());

            // set log flg
            userLog.setLogFlg(ConstValues.MODE_LOG_DATA);

            // Set start time
            userLog.setStartTime(startTime);

            // Set end time
            userLog.setEndTime(endTime);

            // Set mode learning
            if (questionAnswerForm.getLessonType() == 0) {
                userLog.setModeLearning(ConstValues.MODE_LEARNING_SCENARIO_JP);
            } else {
                userLog.setModeLearning(ConstValues.MODE_LEARNING_VOCABULARY_JP);
            }

            // Set lesson no
            userLog.setLessonNo(questionAnswerForm.getLessonNo().toString());

            // Set mode practice or test
            if (questionAnswerForm.getPracticeTest().equals(ConstValues.PRACTICE)) {
                userLog.setModePracticeOrTest(ConstValues.MODE_PRACTICE_JP);
            } else {
                userLog.setModePracticeOrTest(ConstValues.MODE_TEST_JP);
            }

            // Set mode skill
            if (questionAnswerForm.getMemoryWriteSpeech().equals(ConstValues.MEMORY_TEXT)) {
                userLog.setModeSkill(ConstValues.MEMORY_TEXT_JP);
            } else if (questionAnswerForm.getMemoryWriteSpeech().equals(ConstValues.WRITING_TEXT)) {
                userLog.setModeSkill(ConstValues.WRITING_TEXT_JP);
            } else {
                userLog.setModeSkill(ConstValues.CONVERSATION_TEXT_JP);
            }

            // Set course
            userLog.setCourse(questionAnswerForm.getLessonName());

            // Set scenario or word
            userLog.setScenarioOrWord(questionAnswerForm.getAreaScore());

            // Set practice times
            userLog.setPracticeTimes(questionAnswerForm.getPracticeTime());

            // Set answer
            userLog.setAnswer(answer);

            // Set score
            userLog.setScore(score);

            // Set day create
            userLog.setCreatedAt(today);

            // Set delete flag
            userLog.setDeleteFlg(0);

            // insert userLog
            id = (Integer) session.save(userLog);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * update user log
     *
     * @param userLog
     */
    @Override
    public boolean updateUserLog(UserLog userLog) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.update(userLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get user log by id
     * 
     * @param id
     */
    @Override
    public UserLog getUserLogByID(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        // Query for get vocabulary
        Query query = session.createQuery("from UserLog where id = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, id);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list userlog
        UserLog userlog = (UserLog) query.uniqueResult();
        return userlog;
    }
}