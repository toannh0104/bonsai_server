/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.ScoreDto;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.form.QuestionAnswerForm;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.dto.response.ScoreResponseDto;

/**
 * create ClientService
 *
 * @author BaoTQ
 *
 */
public interface ClientService {

    /**
     * Initialize data when load page
     * 
     * @param lessonId id of lesson
     * @param userId id of user
     * @param languageId id of language
     * @param learningType Japan/security
     * @return object
     */
    LessonDto innitData(Integer lessonId, Integer userId, String languageId, Integer learningType);

    /**
     *  Check if able to switch to test.
     * @param scoreDto score
     * @param lessonInfoId id of lesson info
     * @return object
     */
    LessonReponseDto checkSwitchToTest(final ScoreDto scoreDto, final Integer lessonInfoId);

    /**
     * Check if able to show next LessonInfo.
     * 
     * @param scoreDto score
     * @param lessonInfoId id of lesson info
     * @return object
     */
    LessonReponseDto checkNextLessonInfo(final ScoreDto scoreDto, final int lessonInfoId);

    /**
     * Query [Score] table by lessonId, lessonInfoId, userId
     * 
     * @param lessonId id of lesson
     * @param lessonInfoId id of lesson info
     * @param userId id of user
     * @return object
     */
    ScoreDto getScore(int lessonId, int lessonInfoId, int userId);
    
    /**
     * Query [Score]
     *
     * @param questionAnswerForm the questionAnswerForm
     * @param userSessionDto the userSessionDto
     * @return object
     */
    ScoreResponseDto getScoreResponse(QuestionAnswerForm questionAnswerForm, UserSessionDto userSessionDto);

    /**
     * Update score in [Score] table
     * 
     * @param lessonId id of lesson
     * @param lessonInfoId id of lesson info
     * @param userId id of user
     * @param field field
     * @param score score
     * @return boolean
     */
    boolean updateScore(int lessonId, int lessonInfoId, int userId, String field, double score);

}
