/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.AdminDao;
import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.ScenarioDto;
import jp.co.gmo.rs.ghs.dto.VocabularyDto;
import jp.co.gmo.rs.ghs.dto.form.LogForm;
import jp.co.gmo.rs.ghs.model.DictAF;
import jp.co.gmo.rs.ghs.model.DictAR;
import jp.co.gmo.rs.ghs.model.DictAZ;
import jp.co.gmo.rs.ghs.model.DictBE;
import jp.co.gmo.rs.ghs.model.DictBG;
import jp.co.gmo.rs.ghs.model.DictBN;
import jp.co.gmo.rs.ghs.model.DictCA;
import jp.co.gmo.rs.ghs.model.DictCS;
import jp.co.gmo.rs.ghs.model.DictCY;
import jp.co.gmo.rs.ghs.model.DictDA;
import jp.co.gmo.rs.ghs.model.DictDE;
import jp.co.gmo.rs.ghs.model.DictEL;
import jp.co.gmo.rs.ghs.model.DictEN;
import jp.co.gmo.rs.ghs.model.DictES;
import jp.co.gmo.rs.ghs.model.DictET;
import jp.co.gmo.rs.ghs.model.DictEU;
import jp.co.gmo.rs.ghs.model.DictFA;
import jp.co.gmo.rs.ghs.model.DictFI;
import jp.co.gmo.rs.ghs.model.DictFR;
import jp.co.gmo.rs.ghs.model.DictGA;
import jp.co.gmo.rs.ghs.model.DictGL;
import jp.co.gmo.rs.ghs.model.DictGU;
import jp.co.gmo.rs.ghs.model.DictHI;
import jp.co.gmo.rs.ghs.model.DictHR;
import jp.co.gmo.rs.ghs.model.DictHT;
import jp.co.gmo.rs.ghs.model.DictHU;
import jp.co.gmo.rs.ghs.model.DictHY;
import jp.co.gmo.rs.ghs.model.DictID;
import jp.co.gmo.rs.ghs.model.DictIS;
import jp.co.gmo.rs.ghs.model.DictIT;
import jp.co.gmo.rs.ghs.model.DictIW;
import jp.co.gmo.rs.ghs.model.DictJA;
import jp.co.gmo.rs.ghs.model.DictKA;
import jp.co.gmo.rs.ghs.model.DictKN;
import jp.co.gmo.rs.ghs.model.DictKO;
import jp.co.gmo.rs.ghs.model.DictLA;
import jp.co.gmo.rs.ghs.model.DictLN;
import jp.co.gmo.rs.ghs.model.DictLV;
import jp.co.gmo.rs.ghs.model.DictMK;
import jp.co.gmo.rs.ghs.model.DictMS;
import jp.co.gmo.rs.ghs.model.DictMT;
import jp.co.gmo.rs.ghs.model.DictNL;
import jp.co.gmo.rs.ghs.model.DictNO;
import jp.co.gmo.rs.ghs.model.DictPL;
import jp.co.gmo.rs.ghs.model.DictPT;
import jp.co.gmo.rs.ghs.model.DictRO;
import jp.co.gmo.rs.ghs.model.DictRU;
import jp.co.gmo.rs.ghs.model.DictSK;
import jp.co.gmo.rs.ghs.model.DictSL;
import jp.co.gmo.rs.ghs.model.DictSQ;
import jp.co.gmo.rs.ghs.model.DictSR;
import jp.co.gmo.rs.ghs.model.DictSV;
import jp.co.gmo.rs.ghs.model.DictSW;
import jp.co.gmo.rs.ghs.model.DictTA;
import jp.co.gmo.rs.ghs.model.DictTE;
import jp.co.gmo.rs.ghs.model.DictTH;
import jp.co.gmo.rs.ghs.model.DictTL;
import jp.co.gmo.rs.ghs.model.DictTR;
import jp.co.gmo.rs.ghs.model.DictUK;
import jp.co.gmo.rs.ghs.model.DictUR;
import jp.co.gmo.rs.ghs.model.DictVI;
import jp.co.gmo.rs.ghs.model.DictYI;
import jp.co.gmo.rs.ghs.model.DictZHCN;
import jp.co.gmo.rs.ghs.model.DictZHTW;
import jp.co.gmo.rs.ghs.model.Language;
import jp.co.gmo.rs.ghs.model.Lesson;
import jp.co.gmo.rs.ghs.model.LessonInfo;
import jp.co.gmo.rs.ghs.model.Scenario;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.model.Vocabulary;
import jp.co.gmo.rs.ghs.util.DateUtils;
import jp.co.gmo.rs.ghs.util.StringUtils;
import jp.co.gmo.rs.ghs.util.Translator;

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
public class AdminDaoImpl implements AdminDao {

    /* Init sessionFactory is null */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * get Lesson
     * 
     * @param lessonId
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Lesson getLesson(Integer lessonId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query get lesson
        Query query = session.createQuery("from Lesson where id =? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get lesson
        List<Lesson> lessons = query.list();
        Lesson lesson = null;

        if (lessons.size() > 0) {
            lesson = lessons.get(0);
        }

        return lesson;
    }

    /**
     * get LessonInfo by Id
     * 
     * @param lessonInfoId
     * 
     */
    @Override
    public LessonInfo getLessonInfoById(int lessonInfoId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query get all lesson info
        Query query = session.createQuery("from LessonInfo where id =? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonInfoId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list lesson info
        LessonInfo lessonInfos = (LessonInfo) query.uniqueResult();

        return lessonInfos;
    }

    /**
     * get list LessonInfo
     * 
     * @param lessonId
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LessonInfo> getLessonInfo(int lessonId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query get all lesson info
        Query query = session.createQuery("from LessonInfo where lesson_id =? and delete_flg =? order by lesson_no asc");

        // Set parameter
        query.setParameter(0, lessonId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list lesson info
        List<LessonInfo> lessonInfos = query.list();

        return lessonInfos;
    }

    /**
     * get LessonInfo
     * 
     * @param lessonId
     * @param lessonNo
     */
    @SuppressWarnings("unchecked")
    @Override
    public LessonInfo getLessonInfo(int lessonId, int lessonNo) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get lesson info
        Query query = session.createQuery("from LessonInfo where lesson_id =? and lesson_no =? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonId);
        query.setParameter(1, lessonNo);
        query.setParameter(2, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get lesson info
        List<LessonInfo> lessonInfos = query.list();
        LessonInfo lessonInfo = new LessonInfo();

        if (lessonInfos.size() > 0) {
            lessonInfo = lessonInfos.get(0);
        }

        return lessonInfo;
    }

    /**
     * get List Scenario
     * 
     * @param lessonInfoId
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Scenario> getScenario(int lessonInfoId) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from Scenario where lesson_info_id = ? and delete_flg = ?");

        // Set parameter
        query.setParameter(0, lessonInfoId);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<Scenario> scenarios = query.list();

        return scenarios;
    }

    /**
     * get List Scenario
     * 
     * @param lessonInfoId
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Scenario> getScenarioByLearningType(int learningType) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from Scenario where learning_type = ? and delete_flg = ?");

        // Set parameter
        query.setParameter(0, learningType);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<Scenario> scenarios = query.list();

        return scenarios;
    }

    /**
     * get List Scenario
     * 
     * @param lessonInfoId
     * @param part
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Scenario> getScenario(int lessonInfoId, int part) {

        Session session = this.sessionFactory.getCurrentSession();

        // Query for get scenario
        Query query = session.createQuery("from Scenario where lesson_info_id = ? and scenario_part = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, lessonInfoId);
        query.setParameter(1, part);
        query.setParameter(2, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list scenario
        List<Scenario> scenarios = query.list();

        return scenarios;
    }

    /**
     * get List Vocabulary
     * 
     * @param lessonInfoId
     * 
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
     * get lesson by lesson name
     * 
     * @param lessonName
     *            is name of lesson
     * @param learningType
     *            is learning type of lesson table
     */
    @SuppressWarnings("unchecked")
    @Override
    public Lesson getLessonByLessonName(String lessonName, Integer learningType) {
        Session session = this.sessionFactory.getCurrentSession();
        // Get lesson by id
        Query query = session.createQuery("from Lesson where lesson_name = ? and learning_type =? and delete_flg =?");
        // Set parameter
        query.setParameter(0, lessonName);
        query.setParameter(1, learningType);
        query.setParameter(2, ConstValues.DELETE_FLAG_VALUES_OFF);
        // Get lesson
        List<Lesson> lessons = query.list();
        Lesson lesson = null;
        if (lessons.size() > 0) {
            lesson = lessons.get(0);
        }
        return lesson;
    }

    /**
     * Get all lesson
     * 
     * @param learningType
     *            is learning type of lesson table
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Lesson> getAllLesson(Integer learningType) {
        Session session = this.sessionFactory.getCurrentSession();
        // Get lesson by id
        Query query = session.createQuery("from Lesson where learning_type =? and delete_flg =?");
        // Set parameter
        query.setParameter(0, learningType);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);
        // Get lesson
        return query.list();
    }

    /**
     * Registration of a new name of creating content
     * 
     * @param lessonDto
     * @return lessonId
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Integer saveAs(LessonDto lessonDto) {
        Integer lessonId = null;
        Session session = null;

        try {
            session = this.sessionFactory.getCurrentSession();

            // insert Lesson
            Lesson lesson = setValueLesson(new Lesson(), lessonDto, ConstValues.TYPE_INSERT);
            lessonId = (Integer) session.save(lesson);

            // insert LessonInfo
            if (lessonId != null && lessonId.intValue() > 0) {
                List<LessonInfoDto> listLessonInfoDtos = lessonDto.getLessonInfoDtos();
                if (listLessonInfoDtos.size() > 0 && !lesson.getLessonRange().equals("")) {
                    for (LessonInfoDto lessonInfoDto : listLessonInfoDtos) {
                        insertLessonInfo(session, lessonId, lessonInfoDto, lesson.getLessonType());
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return lessonId;
    }

    /**
     * Editing by calling the existing content
     * 
     * @param lessonDto
     * @param type
     * @return lessonId
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Integer save(LessonDto lessonDto, int type) {
        Integer lessonId = null;
        Session session = null;

        try {
            session = this.sessionFactory.getCurrentSession();

            // save or update lesson
            Lesson lesson = setValueLesson(getLesson(lessonDto.getId()), lessonDto, type);
            session.saveOrUpdate(lesson);

            // set value for lessonId
            lessonId = lesson.getId();

            // Get list lessonInfo from db
            List<LessonInfo> listLessonInfoDb = getLessonInfo(lesson.getId());

            // Get list lessonInfo from submitted form
            List<LessonInfoDto> lessonInfoDtos = lessonDto.getLessonInfoDtos();

            // Check for insert/update
            if (lessonInfoDtos.size() > 0) {
                for (LessonInfoDto lessonInfoDto : lessonInfoDtos) {

                    // Check for insert
                    if (lessonInfoDto.getId() == null) {
                        insertLessonInfo(session, lesson.getId(), lessonInfoDto, lesson.getLessonType());
                    } else {

                        // Check for update
                        for (LessonInfo lessonInfo : listLessonInfoDb) {

                            if (lessonInfo.getId().equals(lessonInfoDto.getId())) {

                                updateLessonInfo(session, lesson.getId(), lessonInfo, lessonInfoDto, lesson.getLessonType());
                            }
                        }

                    }
                }

                // Check for delete
                if (listLessonInfoDb.size() > 0) {

                    List<LessonInfo> existedList = new ArrayList<LessonInfo>();

                    // Iterate through list from db, check if any lessonInfo
                    // still exist
                    for (LessonInfo lessonInfo : listLessonInfoDb) {
                        for (LessonInfoDto lessonInfoDto : lessonInfoDtos) {
                            // If exist, add to remove list
                            if (lessonInfoDto != null && lessonInfo.getId().equals(lessonInfoDto.getId())) {
                                existedList.add(lessonInfo);
                            }
                        }
                    }

                    // Remove exist from list
                    listLessonInfoDb.removeAll(existedList);

                    // After remove, if still have lessonInfo, must remove
                    // from db
                    if (listLessonInfoDb.size() > 0) {
                        for (LessonInfo lessonInfo : listLessonInfoDb) {
                            // Update delete flag
                            lessonInfo.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_ON);
                            // Delete from db, update delete flag
                            session.update(lessonInfo);
                        }
                    }

                }
            }
        } catch (Exception e) {
            throw e;
        }
        return lessonId;
    }

    /**
     * Delete an existing content
     * 
     * @param lessonDto
     * @return result
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean delete(LessonDto lessonDto) {
        boolean result = false;
        Session session = null;

        try {
            session = this.sessionFactory.getCurrentSession();
            // delete logic Lesson
            Lesson lesson = setValueLesson(getLesson(lessonDto.getId()),
                    null, ConstValues.TYPE_DELETE);
            session.update(lesson);

            // delete logic LessonInfo
            List<LessonInfo> listLessonInfo = getLessonInfo(lesson.getId());
            for (LessonInfo lessonInfo : listLessonInfo) {
                LessonInfo info = setValueLessonInfo(lesson.getId(), lessonInfo, null, ConstValues.TYPE_DELETE);
                session.update(info);

                // delete logic Scenario
                List<Scenario> listScenario = getScenario(lessonInfo.getId());
                for (Scenario scenario : listScenario) {
                    Scenario sc = setValueScenario(info.getId(), scenario, null, ConstValues.TYPE_DELETE);
                    session.update(sc);
                }

                // delete logic Vocabulary
                List<Vocabulary> listVocabulary = getVocabulary(lessonInfo.getId());
                for (Vocabulary vocabulary : listVocabulary) {
                    Vocabulary vc = setValueVocabulary(info.getId(), vocabulary, null, ConstValues.TYPE_DELETE);
                    session.update(vc);
                }
            }
            result = true;

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * insert lessonInfo base on lessonId
     * 
     * @param session
     * @param lessonId
     * @param lessonInfoDto
     */
    private void insertLessonInfo(Session session, Integer lessonId, LessonInfoDto lessonInfoDto, Integer lessonType) {
        // Insert lessonInfo
        Integer lessonInfoId = (Integer) session.save(setValueLessonInfo(lessonId,
                new LessonInfo(), lessonInfoDto, ConstValues.TYPE_INSERT));

        // check insert success in lessonInfo
        if (lessonInfoId != null && lessonInfoId.intValue() > 0) {
            if (!lessonType.equals(ConstValues.MODE_VOCABULARY)) {
                // insert Scenario
                List<ScenarioDto> listScenario = lessonInfoDto.getScenarioDtos();
                if (listScenario.size() > 0 && lessonInfoDto.getScenarioDtos().size() > 0) {
                    for (ScenarioDto scenarioDto : listScenario) {
                        session.save(setValueScenario(lessonInfoId, new Scenario(), scenarioDto, ConstValues.TYPE_INSERT));
                    }
                }
            }

            // insert Vocabulary
            List<VocabularyDto> listVocabulary = lessonInfoDto.getVocabularyDtos();
            if (listVocabulary.size() > 0 && lessonInfoDto.getVocabularyDtos().size() > 0) {
                for (VocabularyDto vocabularyDto : listVocabulary) {
                    session.save(setValueVocabulary(lessonInfoId, new Vocabulary(), vocabularyDto, ConstValues.TYPE_INSERT));
                }
            }
        }
    }

    /**
     * update Lesson Info base on lessonId
     * 
     * @param session
     * @param lessonId
     * @param lessonInfo
     * @param lessonInfoDto
     */
    private void updateLessonInfo(Session session, Integer lessonId, LessonInfo lessonInfo,
            LessonInfoDto lessonInfoDto, Integer lessonType) {
        // Upadate LessonInfo
        LessonInfo info = setValueLessonInfo(lessonId, lessonInfo, lessonInfoDto, ConstValues.TYPE_UPDATE);
        session.update(info);

        // Incase lessonType is Vocabulary
        if (lessonType.equals(ConstValues.MODE_VOCABULARY)) {
            deleteAllScenario(info.getId(), session);
        } else {
            // Update Scenario
            List<Scenario> listScenario = getScenario(lessonInfo.getId());
            List<ScenarioDto> listScenarioDtos = lessonInfoDto.getScenarioDtos();
            if (listScenarioDtos != null) {
                for (ScenarioDto scenarioDto : listScenarioDtos) {

                    // Check insert new scenario
                    if (scenarioDto.getId() == null) {
                        session.save(setValueScenario(lessonInfo.getId(), new Scenario(), scenarioDto,
                                ConstValues.TYPE_INSERT));

                        // Check update scenario
                    } else {
                        for (Scenario scenario : listScenario) {
                            if (scenario.getId().equals(scenarioDto.getId())) {
                                Scenario senten = setValueScenario(lessonInfo.getId(), scenario,
                                        scenarioDto, ConstValues.TYPE_UPDATE);
                                session.update(senten);
                            }
                        }
                    }
                }
            }
        }

        // Update Vocabulary
        List<Vocabulary> listVocabulary = getVocabulary(lessonInfo.getId());
        List<VocabularyDto> vocabularyDtos = lessonInfoDto.getVocabularyDtos();
        for (VocabularyDto vocabularyDto : vocabularyDtos) {

            // Check insert new vocabulary
            if (vocabularyDto.getId() == null) {
                session.save(setValueVocabulary(lessonInfo.getId(), new Vocabulary(), vocabularyDto, ConstValues.TYPE_INSERT));

                // Check for update vocabulary
            } else {
                for (Vocabulary vocabulary : listVocabulary) {
                    if (vocabulary.getId().equals(vocabularyDto.getId())) {
                        Vocabulary voca = setValueVocabulary(lessonInfo.getId(),
                                vocabulary, vocabularyDto, ConstValues.TYPE_UPDATE);
                        session.update(voca);
                    }
                }
            }
        }
    }

    /**
     * delete all scenario when lesson type is Vocabulary
     * 
     * @param lessonInfoId
     *            is id of lessonInfo
     */
    private void deleteAllScenario(Integer lessonInfoId, Session session) {
        // delete logic Scenario
        List<Scenario> listScenario = getScenario(lessonInfoId);
        for (Scenario scenario : listScenario) {
            Scenario sc = setValueScenario(lessonInfoId, scenario, null, ConstValues.TYPE_DELETE);
            session.update(sc);
        }
    }

    /**
     * convert form LessonDto to Lesson
     * 
     * @param lesson
     * @param lessonDto
     * @param type
     * @return Lesson
     */
    private Lesson setValueLesson(Lesson lesson, LessonDto lessonDto, int type) {
        Date today = DateUtils.now();

        // check lessonDto is not null
        if (lessonDto != null) {

            // If lesson entity was null, initialize
            if (lesson == null) {
                lesson = new Lesson();
            }

            lesson.setLessonType(lessonDto.getLessonType());
            lesson.setJapaneseLevel(lessonDto.getLevel());
            lesson.setPercentComplete(lessonDto.getPercentComplete());
            lesson.setAverageScore(lessonDto.getAverageScore());
            lesson.setLessonRange(lessonDto.getLessonRange());
            lesson.setLessonMethodRomaji(lessonDto.getLessonMethodRomaji());
            lesson.setLessonMethodHiragana(lessonDto.getLessonMethodHiragana());
            lesson.setLessonMethodKanji(lessonDto.getLessonMethodKanji());
            lesson.setLearningType(lessonDto.getLearningType());

            // get DictJA table
            DictJA dictJa = getDictJAByWord1(lessonDto.getLessonName());

            // In case insert
            if (type == ConstValues.TYPE_INSERT) {
                lesson.setLessonName(lessonDto.getLessonName());
                if (dictJa != null) {
                    lesson.setLessonNameMlId(dictJa.getId());
                } else {
                    // lesson.setLessonNameMlId(insertMultiLanguage(lessonDto.getLessonName()));
                }
                lesson.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
                lesson.setCreatedAt(today);

                // In case update
            } else if (type == ConstValues.TYPE_UPDATE) {
                if (lesson.getLessonNameMlId() == null
                        || !lessonDto.getLessonName().equals(lesson.getLessonName())) {
                    lesson.setLessonName(lessonDto.getLessonName());
                    if (dictJa != null) {
                        lesson.setLessonNameMlId(dictJa.getId());
                    } else {
                        // lesson.setLessonNameMlId(insertMultiLanguage(lessonDto.getLessonName()));
                    }
                }
                lesson.setUpdatedAt(today);
            }
        }

        // In case delete
        if (type == ConstValues.TYPE_DELETE) {
            lesson.setDeleteAt(today);
            lesson.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_ON);
        }

        return lesson;
    }

    /**
     * convert form LessonInfoDto to LessonInfo
     * 
     * @param lessonId
     * @param lessonInfo
     * @param lessonInfoDto
     * @param type
     * @return LessonInfo
     */
    private LessonInfo setValueLessonInfo(Integer lessonId, LessonInfo lessonInfo, LessonInfoDto lessonInfoDto, int type) {
        Date today = DateUtils.now();

        // check lessonInfoDto is not null
        if (lessonInfoDto != null) {
            lessonInfo.setLessonId(lessonId);
            lessonInfo.setLessonNo(lessonInfoDto.getLessonNo());
            lessonInfo.setPracticeMemoryCondition(lessonInfoDto.getPracticeMemoryCondition());
            lessonInfo.setPracticeWritingCondition(lessonInfoDto.getPracticeWritingCondition());
            lessonInfo.setPracticeConversationCondition(lessonInfoDto.getPracticeConversationCondition());
            lessonInfo.setTestConversationCondition(lessonInfoDto.getTestConversationCondition());
            lessonInfo.setTestMemoryCondition(lessonInfoDto.getTestMemoryCondition());
            lessonInfo.setTestWritingCondition(lessonInfoDto.getTestWritingCondition());
            lessonInfo.setScenarioSyntax(lessonInfoDto.getScenarioSyntax());
            lessonInfo.setPracticeNo(lessonInfoDto.getPracticeNo());
            lessonInfo.setPracticeLimit(lessonInfoDto.getPracticeLimit());
            lessonInfo.setSpeakerOneName(lessonInfoDto.getSpeakerOneName());
            lessonInfo.setSpeakerTwoName(lessonInfoDto.getSpeakerTwoName());

            // get DictJA table
            DictJA dictJa = getDictJAByWord1(lessonInfoDto.getScenarioName());

            // In case insert
            if (type == ConstValues.TYPE_INSERT) {
                lessonInfo.setScenarioName(lessonInfoDto.getScenarioName());
                if (dictJa != null) {
                    lessonInfo.setScenarioNameMlId(dictJa.getId());
                } else {
                    // lessonInfo.setScenarioNameMlId(insertMultiLanguage(lessonInfoDto.getScenarioName()));
                }
                lessonInfo.setCreatedAt(today);
                lessonInfo.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);

                // In case update
            } else if (type == ConstValues.TYPE_UPDATE) {
                if (lessonInfo.getScenarioNameMlId() == null
                        || !lessonInfoDto.getScenarioName().equals(lessonInfo.getScenarioName())) {
                    lessonInfo.setScenarioName(lessonInfoDto.getScenarioName());
                    if (dictJa != null) {
                        lessonInfo.setScenarioNameMlId(dictJa.getId());
                    } else {
                        // lessonInfo.setScenarioNameMlId(insertMultiLanguage(lessonInfoDto.getScenarioName()));
                    }
                }
                lessonInfo.setUpdatedAt(today);
            }
        }

        // In case delete
        if (type == ConstValues.TYPE_DELETE) {
            lessonInfo.setDeleteAt(today);
            lessonInfo.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_ON);
        }

        return lessonInfo;
    }

    /**
     * convert form ScenarioDto to Scenario
     * 
     * @param lessonInfoId
     * @param scenario
     * @param scenarioDto
     * @param type
     * @return Scenario
     */
    private Scenario setValueScenario(int lessonInfoId, Scenario scenario, ScenarioDto scenarioDto, int type) {
        Date today = DateUtils.now();

        // check scenarioDto is not null
        if (scenarioDto != null) {
            scenario.setLessonInfoId(lessonInfoId);
            scenario.setScenarioPart(scenarioDto.getScenarioPart());
            scenario.setScenarioOrder(scenarioDto.getScenarioOrder());
            scenario.setUserInputFlg(scenarioDto.getUserInputFlg());
            scenario.setCalljConceptName(scenarioDto.getCalljConceptName());
            scenario.setCalljLessonNo(scenarioDto.getCalljLessonNo());
            scenario.setCalljQuestionName(scenarioDto.getCalljQuestionName());
            scenario.setCalljWord(scenarioDto.getCalljWord());
            scenario.setRubyWord(scenarioDto.getRubyWord());
            scenario.setLearningType(scenarioDto.getLearningType());
            scenario.setCategoryWord(scenarioDto.getCategoryWord());

            // get DictJA table
            DictJA dictJa = getDictJAByWord1(scenarioDto.getScenario());

            // In case insert
            if (type == ConstValues.TYPE_INSERT) {
                scenario.setScenario(scenarioDto.getScenario());
                if (dictJa != null) {
                    scenario.setScenarioMlId(dictJa.getId());
                } else {
                    // scenario.setScenarioMlId(insertMultiLanguage(scenarioDto.getScenario()));
                }
                scenario.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
                scenario.setCreatedAt(today);

                // In case update
            } else if (type == ConstValues.TYPE_UPDATE) {
                if (scenario.getScenarioMlId() == null
                        || !scenarioDto.getScenario().equals(scenario.getScenario())) {
                    scenario.setScenario(scenarioDto.getScenario());
                    if (dictJa != null) {
                        scenario.setScenarioMlId(dictJa.getId());
                    } else {
                        // scenario.setScenarioMlId(insertMultiLanguage(scenarioDto.getScenario()));
                    }
                }
                scenario.setUpdatedAt(today);
            }
        }

        // In case delete
        if (type == ConstValues.TYPE_DELETE) {
            scenario.setDeleteAt(today);
            scenario.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_ON);
        }

        return scenario;
    }

    /**
     * convert form VocabularyDto to Vocabulary
     * 
     * @param lessonInfoId
     * @param vocabulary
     * @param vocabularyDto
     * @param type
     * @return Vocabulary
     */
    private Vocabulary setValueVocabulary(int lessonInfoId, Vocabulary vocabulary, VocabularyDto vocabularyDto, int type) {
        Date today = DateUtils.now();

        // check vocabularyDto is not null
        if (vocabularyDto != null) {
            vocabulary.setLessonInfoId(lessonInfoId);
            vocabulary.setVocabularyOrder(vocabularyDto.getVocabularyOrder());
            vocabulary.setUserInputFlg(vocabularyDto.getUserInputFlg());
            vocabulary.setVocabularyEnglishName(vocabularyDto.getVocabularyEnglishName());
            vocabulary.setRubyWord(vocabularyDto.getRubyWord());
            vocabulary.setVocabularyCategories(vocabularyDto.getVocabularyCategories());
            vocabulary.setVocabularyKanaName(vocabularyDto.getVocabularyKanaName());
            vocabulary.setLearningType(vocabularyDto.getLearningType());

            // get dictJaId in DictJA table
            DictJA dictJa = getDictJAByWord1(vocabularyDto.getVocabulary());

            // In case insert
            if (type == ConstValues.TYPE_INSERT) {
                vocabulary.setVocabulary(vocabularyDto.getVocabulary());
                if (dictJa != null) {
                    vocabulary.setVocabularyMlId(dictJa.getId());
                } else {
                    // vocabulary.setVocabularyMlId(insertMultiLanguage(vocabularyDto.getVocabulary()));
                }
                vocabulary.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
                vocabulary.setCreatedAt(today);

                // Incase update
            } else if (type == ConstValues.TYPE_UPDATE) {
                if (vocabulary.getVocabularyMlId() == null
                        || !vocabularyDto.getVocabulary().equals(vocabulary.getVocabulary())) {
                    vocabulary.setVocabulary(vocabularyDto.getVocabulary());
                    if (dictJa != null) {
                        vocabulary.setVocabularyMlId(dictJa.getId());
                    } else {
                        // vocabulary.setVocabularyMlId(insertMultiLanguage(vocabularyDto.getVocabulary()));
                    }
                }
                vocabulary.setUpdatedAt(today);
            }
        }

        // In case delete
        if (type == ConstValues.TYPE_DELETE) {
            vocabulary.setDeleteAt(today);
            vocabulary.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_ON);
        }
        return vocabulary;
    }

    /**
     * insert multi language in db
     * 
     * @param japanText
     *            japanese text
     * @return dictJaId
     */
    @Override
    public Integer insertMultiLanguage(String japanText) {
        Session session = null;
        Integer dictJaId = null;
        session = this.sessionFactory.getCurrentSession();

        try {
            // insert DictJA
            dictJaId = insertDictJA(session, japanText);
            if (dictJaId != null) {

                // get list language need insert
                List<String> listLanguage = getListLanguage();

                // initializing Translator translate
                Translator translate = Translator.getInstance();

                // Transalate JA to EN
                List<String> enOutput = new ArrayList<String>();
                enOutput.add(Language.ENGLISH.toLowerCase());
                Map<String, String> enText = translate.translate(japanText, Language.JAPANESE.toLowerCase(),
                        enOutput);
                // retrieve a list of translated EN words in each language
                Map<String, String> mapValue = translate.translate(enText.get("en"), Language.ENGLISH.toLowerCase(), listLanguage);

                // initializing current date
                Date today = DateUtils.now();

                // insert multi language
                insertDictAF(session, dictJaId, today, mapValue);
                insertDictSQ(session, dictJaId, today, mapValue);
                insertDictAR(session, dictJaId, today, mapValue);
                insertDictHY(session, dictJaId, today, mapValue);
                insertDictAZ(session, dictJaId, today, mapValue);
                insertDictEU(session, dictJaId, today, mapValue);
                insertDictBE(session, dictJaId, today, mapValue);
                insertDictBN(session, dictJaId, today, mapValue);
                insertDictBG(session, dictJaId, today, mapValue);
                insertDictCA(session, dictJaId, today, mapValue);
                insertDictHR(session, dictJaId, today, mapValue);
                insertDictCS(session, dictJaId, today, mapValue);
                insertDictDA(session, dictJaId, today, mapValue);
                insertDictNL(session, dictJaId, today, mapValue);
                insertDictEN(session, dictJaId, today, mapValue);
                insertDictET(session, dictJaId, today, mapValue);
                insertDictTL(session, dictJaId, today, mapValue);
                insertDictFI(session, dictJaId, today, mapValue);
                insertDictFR(session, dictJaId, today, mapValue);
                insertDictGL(session, dictJaId, today, mapValue);
                insertDictKA(session, dictJaId, today, mapValue);
                insertDictDE(session, dictJaId, today, mapValue);
                insertDictEL(session, dictJaId, today, mapValue);
                insertDictGU(session, dictJaId, today, mapValue);
                insertDictHT(session, dictJaId, today, mapValue);
                insertDictIW(session, dictJaId, today, mapValue);
                insertDictHI(session, dictJaId, today, mapValue);
                insertDictHU(session, dictJaId, today, mapValue);
                insertDictIS(session, dictJaId, today, mapValue);
                insertDictID(session, dictJaId, today, mapValue);
                insertDictGA(session, dictJaId, today, mapValue);
                insertDictIT(session, dictJaId, today, mapValue);
                insertDictKN(session, dictJaId, today, mapValue);
                insertDictKO(session, dictJaId, today, mapValue);
                insertDictLA(session, dictJaId, today, mapValue);
                insertDictLV(session, dictJaId, today, mapValue);
                insertDictLN(session, dictJaId, today, mapValue);
                insertDictMK(session, dictJaId, today, mapValue);
                insertDictMS(session, dictJaId, today, mapValue);
                insertDictMT(session, dictJaId, today, mapValue);
                insertDictNO(session, dictJaId, today, mapValue);
                insertDictFA(session, dictJaId, today, mapValue);
                insertDictPL(session, dictJaId, today, mapValue);
                insertDictPT(session, dictJaId, today, mapValue);
                insertDictRO(session, dictJaId, today, mapValue);
                insertDictRU(session, dictJaId, today, mapValue);
                insertDictSR(session, dictJaId, today, mapValue);
                insertDictSK(session, dictJaId, today, mapValue);
                insertDictSL(session, dictJaId, today, mapValue);
                insertDictES(session, dictJaId, today, mapValue);
                insertDictSW(session, dictJaId, today, mapValue);
                insertDictSV(session, dictJaId, today, mapValue);
                insertDictTA(session, dictJaId, today, mapValue);
                insertDictTE(session, dictJaId, today, mapValue);
                insertDictTH(session, dictJaId, today, mapValue);
                insertDictTR(session, dictJaId, today, mapValue);
                insertDictUK(session, dictJaId, today, mapValue);
                insertDictUR(session, dictJaId, today, mapValue);
                insertDictVI(session, dictJaId, today, mapValue);
                insertDictCY(session, dictJaId, today, mapValue);
                insertDictYI(session, dictJaId, today, mapValue);
                insertDictZHCN(session, dictJaId, today, mapValue);
                insertDictZHTW(session, dictJaId, today, mapValue);

            }
        } catch (Exception e) {
        }
        return dictJaId;
    }

    /**
     * get list language
     * 
     * @return listLanguage
     */
    private List<String> getListLanguage() {
        Language language = Language.getInstance();
        HashMap<String, String> mapLanguage = language.getHashLanguage();
        List<String> listLanguage = new ArrayList<String>();
        for (String s : mapLanguage.keySet()) {
            if (s.equals("LN")) {
                listLanguage.add("lt");
            } else {
                listLanguage.add(s.toLowerCase());
            }
        }
        return listLanguage;
    }

    /**
     * Insert DictJA into DB
     * 
     * @param session
     * @param japanText
     * @return dictJaId
     */
    private Integer insertDictJA(Session session, String japanText) {

        // Innit DictJA
        DictJA dictJA = new DictJA();
        dictJA.setWord1(japanText);
        dictJA.setCreatedAt(DateUtils.now());
        dictJA.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);

        return (Integer) session.save(dictJA);
    }

    /**
     * Insert DictAF into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictAF(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictAF dict = new DictAF();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.AFRIKAANS));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSQ into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSQ(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSQ dict = new DictSQ();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ALBANIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictAR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictAR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictAR dict = new DictAR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ARABIC));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictHY into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictHY(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictHY dict = new DictHY();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ARMENIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictAZ into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictAZ(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictAZ dict = new DictAZ();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.AZERBAIJANI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictEU into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictEU(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictEU dict = new DictEU();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.BASQUE));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictBE into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictBE(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictBE dict = new DictBE();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.BELARUSIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictBN into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictBN(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictBN dict = new DictBN();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.BENGALI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictBG into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictBG(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictBG dict = new DictBG();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.BULGARIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictCA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictCA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictCA dict = new DictCA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.CATALAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictHR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictHR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictHR dict = new DictHR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.CROATIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictCS into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictCS(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictCS dict = new DictCS();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.CZECH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictDA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictDA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictDA dict = new DictDA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.DANISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictNL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictNL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictNL dict = new DictNL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.DUTCH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictEN into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictEN(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictEN dict = new DictEN();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ENGLISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictET into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictET(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictET dict = new DictET();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ESTONIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictTL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictTL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictTL dict = new DictTL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.FILIPINO));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictFI into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictFI(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictFI dict = new DictFI();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.FINNISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictFR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictFR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictFR dict = new DictFR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.FRENCH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictGL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictGL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictGL dict = new DictGL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.GALICIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictKA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictKA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictKA dict = new DictKA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.GEORGIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictDE into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictDE(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictDE dict = new DictDE();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.GERMAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictEL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictEL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictEL dict = new DictEL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.GREEK));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictGU into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictGU(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictGU dict = new DictGU();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.GUJARATI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictHT into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictHT(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictHT dict = new DictHT();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.HAITIAN_CREOLE));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictIW into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictIW(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictIW dict = new DictIW();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.HEBREW));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictHI into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictHI(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictHI dict = new DictHI();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.HINDI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictHU into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictHU(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictHU dict = new DictHU();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.HUNGARIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictIS into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictIS(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictIS dict = new DictIS();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ICELANDIC));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictID into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictID(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictID dict = new DictID();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.INDONESIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictGA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictGA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictGA dict = new DictGA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.IRISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictIT into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictIT(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictIT dict = new DictIT();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ITALIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictKN into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictKN(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictKN dict = new DictKN();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.KANNADA));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictKO into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictKO(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictKO dict = new DictKO();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.KOREAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictLA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictLA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictLA dict = new DictLA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.LATIN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictLV into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictLV(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictLV dict = new DictLV();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.LATVIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictLN into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictLN(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictLN dict = new DictLN();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get("lt"));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictMK into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictMK(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictMK dict = new DictMK();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.MACEDONIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictMS into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictMS(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictMS dict = new DictMS();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.MALAY));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictMT into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictMT(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictMT dict = new DictMT();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.MALTESE));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictNO into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictNO(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictNO dict = new DictNO();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.NORWEGIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictFA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictFA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictFA dict = new DictFA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.PERSIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictPL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictPL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictPL dict = new DictPL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.POLISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictPT into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictPT(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictPT dict = new DictPT();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.PORTUGUESE));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictRO into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictRO(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictRO dict = new DictRO();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.ROMANIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictRU into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictRU(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictRU dict = new DictRU();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.RUSSIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSR dict = new DictSR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SERBIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSK into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSK(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSK dict = new DictSK();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SLOVAK));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSL into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSL(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSL dict = new DictSL();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SLOVENIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictES into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictES(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictES dict = new DictES();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SPANISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSW into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSW(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSW dict = new DictSW();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SWAHILI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictSV into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictSV(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictSV dict = new DictSV();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.SWEDISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictTA into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictTA(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictTA dict = new DictTA();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.TAMIL));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictTE into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictTE(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictTE dict = new DictTE();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.TELUGU));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictTH into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictTH(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictTH dict = new DictTH();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.THAI));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictTR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictTR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictTR dict = new DictTR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.TURKISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictUK into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictUK(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictUK dict = new DictUK();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.UKRAINIAN));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictUR into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictUR(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictUR dict = new DictUR();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.URDU));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictVI into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictVI(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictVI dict = new DictVI();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.VIETNAMESE));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictCY into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictCY(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictCY dict = new DictCY();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.WELSH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictYI into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictYI(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictYI dict = new DictYI();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.YIDDISH));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictZHCN into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictZHCN(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictZHCN dict = new DictZHCN();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.CHINESE_SIMPLIFIED.toLowerCase()));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * Insert DictZHTW into DB
     * 
     * @param session
     * @param dictJaId
     * @param today
     * @param mapValue
     */
    private void insertDictZHTW(Session session, Integer dictJaId, Date today, Map<String, String> mapValue) {
        DictZHTW dict = new DictZHTW();
        dict.setCreatedAt(today);
        dict.setDictJaId(dictJaId);
        dict.setWord1(mapValue.get(Language.CHINESE_TRADITIONAL.toLowerCase()));
        dict.setDeleteFlg(ConstValues.DELETE_FLAG_VALUES_OFF);
        session.save(dict);
    }

    /**
     * get dictJA base on word1
     * 
     * @param word1
     * @return dictJA
     */
    private DictJA getDictJAByWord1(String word1) {
        Session session = this.sessionFactory.getCurrentSession();
        DictJA dictJA = new DictJA();

        // Query get DictJA
        Query query = session.createQuery("from DictJA where word_1 = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, word1);
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list DictJA
        dictJA = (DictJA) query.uniqueResult();

        return dictJA;
    }

    /**
     * getMultiLanguage
     * 
     * @param lessonNameMlId
     * @param motherLanguage
     */
    @Override
    public List<String> getMultiLanguage(Integer lessonNameMlId, String motherLanguage) {
        List<String> result = new ArrayList<String>();
        Session session = this.sessionFactory.getCurrentSession();

        // add word1 form DictEN table
        result.add(getDict(session, "DictEN", lessonNameMlId));

        // add word1 form Dict name corresponding
        result.add(getDict(session, "Dict" + motherLanguage.replace("_", "").toUpperCase(), lessonNameMlId));

        return result;
    }

    /**
     * select word1
     * 
     * @param session
     * @param tableName
     * @param lessonNameMlId
     * @return word1
     */
    private String getDict(Session session, String tableName, Integer lessonNameMlId) {

        // Query get Dict
        Query query = session.createQuery("select word1 from " + tableName
                + " where dictJaId = :dictJaId and deleteFlg = :deleteFlg");

        // Set parameter
        query.setParameter("dictJaId", lessonNameMlId);
        query.setParameter("deleteFlg", ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get word1
        String word1 = (String) query.uniqueResult();

        return word1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Vocabulary> getVocabularyByLearningType(Integer learningType) {
        Session session = this.sessionFactory.getCurrentSession();

        // Query for get vocabulary
        Query query = session.createQuery("from Vocabulary where learning_type = ? and delete_flg =?");

        // Set parameter
        query.setParameter(0, String.valueOf(learningType));
        query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

        // Get list vocabulary
        List<Vocabulary> vocabularys = query.list();

        return vocabularys;
    }

    /**
     * get user log by mode log
     * 
     * @param modeLog
     * @param logForm
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserLog> getSizeUserLogByModeLog(Integer modeLog, LogForm logForm) {

        // Get session
        Session session = this.sessionFactory.getCurrentSession();

        HashMap<Integer, Object> hashMap = new HashMap<>();

        // Set string query
        String stringQuery = "from UserLog where delete_flg LIKE ? and log_flg LIKE ?";

        // Put to hash map
        int i = 0;
        hashMap.put(i, ConstValues.DELETE_FLAG_VALUES_OFF);
        i++;
        hashMap.put(i, modeLog);
        i++;

        // case modeLog is login
        if (modeLog == 0) {

            // Check search condition Terminal
            if (!StringUtils.isEmpty(logForm.getTerminal()) && !logForm.getTerminal().equals("全て")) {
                // Add condition to string query
                stringQuery = stringQuery + " and device_name LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getTerminal());
                i++;
            }

            // Check search condition UserNameLogin
            if (logForm.getUserNameLogin() != null) {
                // Add condition to string query
                stringQuery = stringQuery + " and user_name LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getUserNameLogin());
                i++;
            }

            // Check search condition Location
            if (!StringUtils.isEmpty(logForm.getLocation())) {
                // Add condition to string query
                stringQuery = stringQuery + " and location LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getLocation());
                i++;
            }

            // Check search condition LoginPeriodFrom
            if (!StringUtils.isEmpty(logForm.getLoginPeriodFrom())) {
                // Add condition to string query
                stringQuery = stringQuery + " and start_time LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getLoginPeriodFrom());
                i++;
            }

            // Check search condition LoginPeriodTo
            if (!StringUtils.isEmpty(logForm.getLoginPeriodTo())) {
                // Add condition to string query
                stringQuery = stringQuery + " and end_time LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getLoginPeriodTo());
            }
        // Case modeLog is Data
        } else {

            // Check search condition UserNameData
            if (logForm.getUserNameData() != null) {
                // Add condition to string query
                stringQuery = stringQuery + " and user_name LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getUserNameData());
                i++;
            }

            // Check search condition ModeLearning
            if (!StringUtils.isEmpty(logForm.getModeLearning()) && !logForm.getModeLearning().equals("全て")) {
                // Add condition to string query
                stringQuery = stringQuery + " and mode_learning LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getModeLearning());
                i++;
            }

            // Check search condition LessonNo
            if (logForm.getLessonNo() != null) {
                // Add condition to string query
                stringQuery = stringQuery + " and lesson_no LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getLessonNo());
                i++;
            }

            // Check search condition Course
            if (!StringUtils.isEmpty(logForm.getCourse())) {
                // Add condition to string query
                stringQuery = stringQuery + " and course LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getCourse());
                i++;
            }

            // Check search condition ModePracticeOrTest
            if (!StringUtils.isEmpty(logForm.getModePracticeOrTest()) && !logForm.getModePracticeOrTest().equals("全て")) {
                // Add condition to string query
                stringQuery = stringQuery + " and mode_practice_or_test LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getModePracticeOrTest());
                i++;
            }

            // Check search condition ModeSkill
            if (!StringUtils.isEmpty(logForm.getModeSkill()) && !logForm.getModeSkill().equals("全て")) {
                // Add condition to string query
                stringQuery = stringQuery + " and mode_skill LIKE ?";
                // Put to hash map
                hashMap.put(i, logForm.getModeSkill());
            }
        }

        // Query for get vocabulary
        Query query = session.createQuery(stringQuery);

        // Set parameter
        for (int key : hashMap.keySet()) {
            query.setParameter(key, "%" + hashMap.get(key) + "%");
        }

        // Get list vocabulary
        List<UserLog> listUserLog = query.list();
        return listUserLog;
    }

    /**
     * get user log by id
     * 
     * @param id
     */
    @Override
    public UserLog getUserLogByID(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        UserLog userlog = new UserLog();
        try {
            // Query for get vocabulary
            Query query = session.createQuery("from UserLog where id = ? and delete_flg =?");

            // Set parameter
            query.setParameter(0, id);
            query.setParameter(1, ConstValues.DELETE_FLAG_VALUES_OFF);

            // Get list userlog
            userlog = (UserLog) query.uniqueResult();
        } catch (Exception e) {
            throw e;
        }
        return userlog;
    }

    /**
     * update user log
     */
    @Override
    public boolean updateUserLog(UserLog userLog) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.update(userLog);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
}