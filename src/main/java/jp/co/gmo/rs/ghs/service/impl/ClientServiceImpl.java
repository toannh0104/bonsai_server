/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dao.AdminDao;
import jp.co.gmo.rs.ghs.dao.ClientDao;
import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.OutComeDto;
import jp.co.gmo.rs.ghs.dto.ScoreDto;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.form.QuestionAnswerForm;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.dto.response.ScoreResponseDto;
import jp.co.gmo.rs.ghs.model.LessonInfo;
import jp.co.gmo.rs.ghs.model.Score;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.service.AdminService;
import jp.co.gmo.rs.ghs.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create AdminServiceImpl
 *
 * @author BaoTQ
 *
 */
@Service
public class ClientServiceImpl implements ClientService {

    /* Init AdminDao */
    @Autowired
    private AdminDao adminDao;

    /* Init ClientDao */
    @Autowired
    private ClientDao clientDao;

    @Autowired
    private AdminService adminService;

    @Override
    public LessonDto innitData(final Integer lessonId, final Integer userId, final String languageId,
            final Integer learningType) {
        // get lesson form service
        LessonDto lessonDto = adminService.getLesson(lessonId, languageId);
        if (lessonDto == null) {
            // Init sample data for lesson 1
            lessonDto = new LessonDto();

            List<LessonInfoDto> lessonInfoDtos = new ArrayList<>();
            LessonInfoDto lessonInfoDto = new LessonInfoDto();

            // Set lessonNo = 1
            lessonInfoDto.setLessonNo(1);
            lessonInfoDtos.add(lessonInfoDto);
            lessonDto.setLessonInfoDtos(lessonInfoDtos);
        }
        lessonDto.setLearningType(learningType);
        // Add score, percent complete and percent average
        addScoreCompAve(lessonDto, lessonId, userId);

        return lessonDto;
    }

    /**
     * Add score, percent complete and percent average.
     *
     * @param lessonDto
     * @param lessonId
     * @param userId
     */
    private void addScoreCompAve(LessonDto lessonDto, final int lessonId, final int userId) {
        List<LessonInfoDto> lessonInfoDtos = lessonDto.getLessonInfoDtos();
        List<Score> scores = clientDao.getUserLessonScore(lessonId, userId);

        int completeCount = 0;
        int scoreSum = 0;
        for (Score score : scores) {
            int lessonInfoCompleteCount = 0;
            int lessonInfoscoreSum = 0;
            int lessonInfoId = score.getLessonInfoId();
            for (LessonInfoDto lessonInfo : lessonInfoDtos) {
                if (lessonInfo.getId() == lessonInfoId) {
                    // Add score to lessoninfo
                    lessonInfo.setPracticeMemoryScore(score.getPracticeMemoryScore());
                    lessonInfo.setPracticeWritingScore(score.getPracticeWritingScore());
                    lessonInfo.setPracticeConversationScore(score.getPracticeConversationScore());
                    lessonInfo.setTestMemoryScore(score.getTestMemoryScore());
                    lessonInfo.setTestWritingScore(score.getTestWritingScore());
                    lessonInfo.setTestConversationScore(score.getTestConversationScore());

                    // Calculate user complete how many practice and test
                    Double pracMemoryScore = score.getPracticeMemoryScore();
                    Double pracWritingScore = score.getPracticeWritingScore();
                    Double pracConversationScore = score.getPracticeConversationScore();
                    Double testMemoryScore = score.getTestMemoryScore();
                    Double testWritingScore = score.getTestWritingScore();
                    Double testConversationScore = score.getTestConversationScore();
                    if (pracMemoryScore != null && pracMemoryScore >= lessonInfo.getPracticeMemoryCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getPracticeMemoryScore();
                    }
                    if (pracWritingScore != null && pracWritingScore >= lessonInfo.getPracticeWritingCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getPracticeWritingScore();
                    }
                    if (pracConversationScore != null
                            && pracConversationScore >= lessonInfo.getPracticeConversationCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getPracticeConversationScore();
                    }
                    if (testMemoryScore != null && testMemoryScore >= lessonInfo.getTestMemoryCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getTestMemoryScore();
                    }
                    if (testWritingScore != null && testWritingScore >= lessonInfo.getTestWritingCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getTestWritingScore();
                    }
                    if (testConversationScore != null && testConversationScore >= lessonInfo.getTestConversationCondition()) {
                        lessonInfoCompleteCount++;
                        lessonInfoscoreSum += score.getTestConversationScore();
                    }
                    if (lessonInfoCompleteCount == ConstValues.LESSONINFO_COMPLETE_COUNT) {
                        completeCount++;
                        scoreSum += lessonInfoscoreSum;
                    }
                    break;
                }
            }
        }
        // Calculate complete and average
        double persenComplete = 0;
        double averageScore = 0;
        int lessonInfoSize = lessonDto.getLessonInfoDtos().size();
        if (lessonInfoSize > 0) {
            persenComplete = (completeCount * ConstValues.FULL_PERCENT) / lessonInfoSize;
        }
        if (completeCount > 0) {
            averageScore = scoreSum / (completeCount * ConstValues.LESSONINFO_COMPLETE_COUNT);
        }
        lessonDto.setPercentComplete(persenComplete);
        lessonDto.setAverageScore(averageScore);
    }

    @Override
    public LessonReponseDto checkSwitchToTest(final ScoreDto scoreDto, final Integer lessonInfoId) {

        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        lessonReponseDto.setResult("false");
        lessonReponseDto.setStatus(ConstValues.Status.OK);

        try {
            LessonInfo lessonInfo = adminDao.getLessonInfoById(lessonInfoId);
            if (scoreDto != null && lessonInfo != null) {
                Double pracMemoryScore = scoreDto.getPracticeMemoryScore();
                Double pracWritingScore = scoreDto.getPracticeWritingScore();
                Double pracConversationScore = scoreDto.getPracticeConversationScore();
                if (pracMemoryScore != null
                        && pracMemoryScore >= lessonInfo.getPracticeMemoryCondition()
                        && pracWritingScore != null
                        && pracWritingScore >= lessonInfo.getPracticeWritingCondition()
                        && pracConversationScore != null
                        && pracConversationScore >= lessonInfo.getPracticeConversationCondition()) {
                    lessonReponseDto.setResult("true");
                }
            }
        } catch (Exception e) {
            // Error
            lessonReponseDto.setStatus(ConstValues.Status.NG);
        }
        return lessonReponseDto;
    }

    @Override
    public LessonReponseDto checkNextLessonInfo(final ScoreDto scoreDto, final int lessonInfoId) {

        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        lessonReponseDto.setResult("false");
        lessonReponseDto.setStatus(ConstValues.Status.OK);

        try {
            LessonInfo lessonInfo = adminDao.getLessonInfoById(lessonInfoId);
            if (scoreDto != null && lessonInfo != null) {
                Double pracMemoryScore = scoreDto.getPracticeMemoryScore();
                Double pracWritingScore = scoreDto.getPracticeWritingScore();
                Double pracConversationScore = scoreDto.getPracticeConversationScore();
                Double testMemoryScore = scoreDto.getTestMemoryScore();
                Double testWritingScore = scoreDto.getTestWritingScore();
                Double testConversationScore = scoreDto.getTestConversationScore();
                if (pracMemoryScore != null && pracMemoryScore >= lessonInfo.getPracticeMemoryCondition()
                        && pracWritingScore != null && pracWritingScore >= lessonInfo.getPracticeWritingCondition()
                        && pracConversationScore != null
                        && pracConversationScore >= lessonInfo.getPracticeConversationCondition()
                        && testMemoryScore != null
                        && testMemoryScore >= lessonInfo.getTestMemoryCondition()
                        && testWritingScore != null
                        && testWritingScore >= lessonInfo.getTestWritingCondition()
                        && testConversationScore != null
                        && testConversationScore >= lessonInfo.getTestConversationCondition()) {
                    lessonReponseDto.setResult("true");
                }
            }
        } catch (Exception e) {
            // Error
            lessonReponseDto.setStatus(ConstValues.Status.NG);
        }
        return lessonReponseDto;
    }

    @Override
    public ScoreDto getScore(final int lessonId, final int lessonInfoId, final int userId) {
        return convertScoreToObjectDto(clientDao.getScore(lessonId, lessonInfoId, userId));
    }

    @Override
    public ScoreResponseDto getScoreResponse(final QuestionAnswerForm questionAnswerForm, final UserSessionDto userSessionDto) {
        ScoreResponseDto scoreResponseDto = new ScoreResponseDto();
        try {

            // Set value
            int i = 0;
            int userId = userSessionDto.getUserId();
            double score = 0;
            double scoreScenario = 0;
            double scoreVocabulary = 0;
            int correctAnswersScenario = 0;
            int correctAnswersVocabulary = 0;
            double maxScore = 0;
            String modeMemoryWriteSpeech = questionAnswerForm.getMemoryWriteSpeech();
            String modePracticeTest = questionAnswerForm.getPracticeTest();
            int lessionId = questionAnswerForm.getLessonId();
            int lessionInfo = questionAnswerForm.getLessonInfo();
            boolean avgScoreFlg = false;
            List<OutComeDto> listOutCome = new ArrayList<>();

            // Set string field for parameter field of function update score
            String field = ConstValues.CONST_STRING_EMPTY;
            if (ConstValues.MEMORY_TEXT.equals(modeMemoryWriteSpeech)) {
                field = modePracticeTest + ConstValues.MEMORY_SCORE_TEXT;
            } else if (ConstValues.WRITING_TEXT.equals(modeMemoryWriteSpeech)) {
                field = modePracticeTest + ConstValues.WRITING_SCORE_TEXT;
            } else {
                field = modePracticeTest + ConstValues.CONVERSATION_SCORE_TEXT;
            }

            // Check null Scenario
            if (questionAnswerForm.getAnswers() != null) {
                // Calculate score Scenario
                for (String answer : questionAnswerForm.getAnswers()) {
                    if (!answer.equals("null")) {
                        OutComeDto outComeDto = new OutComeDto();
                        outComeDto.setQuestion(questionAnswerForm.getQuestions().get(i));
                        outComeDto.setAnswer(answer);
                        outComeDto.setListIndexError(getListIndexError(questionAnswerForm.getQuestions().get(i), answer));
                        outComeDto.setQuestionId(questionAnswerForm.getQuestionIds().get(i));
                        listOutCome.add(outComeDto);
                        if (answer.trim().equals(questionAnswerForm.getQuestions().get(i).trim())) {
                            correctAnswersScenario++;
                        }
                    }
                    i++;
                }
                scoreScenario = (correctAnswersScenario * ConstValues.FULL_PERCENT) / questionAnswerForm.getQuestions().size();
            }

            i = 0;

            // Check null Vocabulary
            if (questionAnswerForm.getVocabularyAnswers() != null) {
                // Calculate score Vocabulary
                for (String answer : questionAnswerForm.getVocabularyAnswers()) {
                    if (!answer.equals("null")) {
                        OutComeDto outComeDto = new OutComeDto();
                        outComeDto.setQuestion(questionAnswerForm.getVocabularyQuestions().get(i));
                        outComeDto.setAnswer(answer);
                        outComeDto.setListIndexError(
                                getListIndexError(questionAnswerForm.getVocabularyQuestions().get(i), answer));
                        outComeDto.setQuestionId(questionAnswerForm.getQuestionIds().get(i));
                        listOutCome.add(outComeDto);
                        if (answer.trim().equals(questionAnswerForm.getVocabularyQuestions().get(i))) {
                            correctAnswersVocabulary++;
                        }
                    }
                    i++;
                }
                scoreVocabulary =
                        (correctAnswersVocabulary * ConstValues.FULL_PERCENT)
                                / questionAnswerForm.getVocabularyQuestions().size();
            }

            // If lesson type is Scenario
            if (questionAnswerForm.getLessonType() == 0) {
                if ((questionAnswerForm.getFlagAvgScenario() && isFull(questionAnswerForm.getVocabularyAnswers()))
                        || (questionAnswerForm.getFlagAvgVocabulary() && isFull(questionAnswerForm.getAnswers()))) {

                    if (questionAnswerForm.getAreaScore().equals(ConstValues.TEST_AREA_SCORE_SCENARIO)) {
                        // Set avg score
                        score = (int) (scoreScenario + questionAnswerForm.getSaveScoreVocabulary()) / 2;
                    } else {
                        // Set avg score
                        score = (int) (scoreVocabulary + questionAnswerForm.getSaveScoreScenario()) / 2;
                    }

                    // update avgScoreFlg
                    avgScoreFlg = true;

                }
                // else lesson type is Vocabulary
            } else {

                score = scoreVocabulary;

                // update avgScoreFlg
                avgScoreFlg = true;
            }

            // Do base on condition
            if (avgScoreFlg) {
                // Get scoreDto to get score at DB
                ScoreDto scoreDto = getScore(
                        questionAnswerForm.getLessonId(),
                        questionAnswerForm.getLessonInfo(),
                        userId);

                // Update flag
                boolean updateFlg = true;

                // Get score assigned to maxScore
                if (scoreDto.getId() != null) {
                    switch (field) {
                        case ConstValues.PRACTICE_MEMORY_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getPracticeMemoryScore());
                            break;
                        case ConstValues.PRACTICE_WRITING_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getPracticeWritingScore());
                            break;
                        case ConstValues.PRACTICE_CONVERSATION_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getPracticeConversationScore());
                            break;
                        case ConstValues.TEST_MEMORY_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getTestMemoryScore());
                            break;
                        case ConstValues.TEST_WRITING_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getTestWritingScore());
                            break;
                        case ConstValues.TEST_CONVERSATION_SCORE:
                            maxScore = getScoreIfNull(scoreDto.getTestConversationScore());
                            break;
                        default:
                            break;

                    }

                    // Check if score smaller than max, don't update
                    if (maxScore > score) {
                        updateFlg = false;
                    }
                }

                // Update score base on condition
                if (updateFlg) {

                    updateScore(
                            lessionId,
                            lessionInfo,
                            userId,
                            field,
                            score);
                }
            }

            scoreResponseDto = convertScoreToObjectResponseDto(clientDao.getScore(lessionId, lessionInfo, userId));

            Date endTime = new Date();

            // Set flagAvgScenario
            scoreResponseDto.setFlagAvgScenario(questionAnswerForm.getFlagAvgScenario());
            if (questionAnswerForm.getAnswers() != null && isFull(questionAnswerForm.getAnswers())) {
                scoreResponseDto.setFlagAvgScenario(true);
            }

            // Set flagAvgVocabulary
            scoreResponseDto.setFlagAvgVocabulary(questionAnswerForm.getFlagAvgVocabulary());
            if (questionAnswerForm.getVocabularyAnswers() != null && isFull(questionAnswerForm.getVocabularyAnswers())) {
                scoreResponseDto.setFlagAvgVocabulary(true);
            }

            // Set area score
            scoreResponseDto.setAreaScore(questionAnswerForm.getAreaScore());

            String answer = setAnswer(questionAnswerForm);

            // Set score for scoreDtoReturn
            if (questionAnswerForm.getAreaScore().equals(ConstValues.TEST_AREA_SCORE_SCENARIO)) {
                scoreResponseDto.setScore(scoreScenario);
                // write log to DB
                UserLog userLog = clientDao.getUserLogByID(questionAnswerForm.getCurrentIdRecordScenario());
                if (userLog != null) {
                    userLog.setEndTime(endTime);
                    userLog.setAnswer(answer);
                    userLog.setScore(scoreScenario);
                    clientDao.updateUserLog(userLog);
                    scoreResponseDto.setCurrentIdRecordScenario(questionAnswerForm.getCurrentIdRecordScenario());
                } else {
                    scoreResponseDto.setCurrentIdRecordScenario(clientDao.writeLog(userSessionDto, questionAnswerForm, scoreScenario, endTime, answer));
                }
            } else {
                scoreResponseDto.setScore(scoreVocabulary);
                // write log to DB
                UserLog userLog = clientDao.getUserLogByID(questionAnswerForm.getCurrentIdRecordVocabulary());
                if (userLog != null) {
                    userLog.setEndTime(endTime);
                    userLog.setAnswer(answer);
                    userLog.setScore(scoreVocabulary);
                    clientDao.updateUserLog(userLog);
                    scoreResponseDto.setCurrentIdRecordVocabulary(questionAnswerForm.getCurrentIdRecordVocabulary());
                } else {
                    scoreResponseDto.setCurrentIdRecordVocabulary(clientDao.writeLog(userSessionDto, questionAnswerForm, scoreVocabulary, endTime, answer));
                }
            }

            // Set list out come for scoreDtoReturn
            scoreResponseDto.setListOutCome(listOutCome);

            // Set endTime
            Date endTimeRespon = new Date();
            SimpleDateFormat sf = new SimpleDateFormat(ConstValues.DATE_FORMAT);
            scoreResponseDto.setEndTime(sf.format(endTimeRespon));

            scoreResponseDto.setStatus(ConstValues.Status.OK);

        } catch (Exception e) {
            // Error
            scoreResponseDto.setStatus(ConstValues.Status.NG);
        }
        scoreResponseDto.setStringClass(questionAnswerForm.getStringClass());
        return scoreResponseDto;
    }

    /**
     * Update score
     *
     * @param lessonId
     * @param lessonInfoId
     * @param userId
     * @param field
     * @param score
     */
    @Override
    public boolean updateScore(final int lessonId, final int lessonInfoId, final int userId,
            final String field, final double score) {
        return clientDao.updateScore(lessonId, lessonInfoId, userId, field, score);
    }

    /**
     * Convert score to object Dto
     *
     * @param score
     * @return scoreDto
     */
    private ScoreDto convertScoreToObjectDto(final Score score) {
        ScoreDto scoreDto = new ScoreDto();
        if (score != null) {
            scoreDto.setId(score.getId());
            scoreDto.setPracticeConversationScore(score.getPracticeConversationScore());
            scoreDto.setPracticeMemoryScore(score.getPracticeMemoryScore());
            scoreDto.setPracticeWritingScore(score.getPracticeWritingScore());
            scoreDto.setTestConversationScore(score.getTestConversationScore());
            scoreDto.setTestMemoryScore(score.getTestMemoryScore());
            scoreDto.setTestWritingScore(score.getTestWritingScore());
        }
        return scoreDto;
    }

    /**
     * Convert score to object ResponseDto
     *
     * @param score
     * @return
     */
    private ScoreResponseDto convertScoreToObjectResponseDto(final Score score) {
        ScoreResponseDto scoreResponseDto = new ScoreResponseDto();
        if (score != null) {
            scoreResponseDto.setId(score.getId());
            scoreResponseDto.setPracticeConversationScore(score.getPracticeConversationScore());
            scoreResponseDto.setPracticeMemoryScore(score.getPracticeMemoryScore());
            scoreResponseDto.setPracticeWritingScore(score.getPracticeWritingScore());
            scoreResponseDto.setTestConversationScore(score.getTestConversationScore());
            scoreResponseDto.setTestMemoryScore(score.getTestMemoryScore());
            scoreResponseDto.setTestWritingScore(score.getTestWritingScore());
        }
        return scoreResponseDto;
    }

    /**
     * Get score if null
     *
     * @param score
     * @return
     */
    private double getScoreIfNull(final Double score) {

        if (score != null) {
            return score;
        }

        return 0;
    }

    /**
     * Get list index error
     *
     * @param question
     * @param answer
     * @return list index error
     */
    private List<Integer> getListIndexError(String question, String answer) {

        List<Integer> listReturn = new ArrayList<>();
        // Case question and answer is Scenario
        if (answer.indexOf(";") != -1) {
            // split word of question by ";"
            String[] listWordQuestion = question.split(";");

            // split word of answer by ";"
            String[] listWordAnswer = answer.split(";");

            for (int i = 0; i < listWordAnswer.length; i++) {
                // Case word answer not like word question
                if (!listWordAnswer[i].equals(listWordQuestion[i])) {
                    // Add index to list index error
                    listReturn.add(i);
                }
            }
            // Case question and answer is Vocabulary
        } else {
            // Case word answer not like word question
            if (!answer.equals(question)) {
                // Add index to list index error
                listReturn.add(0);
            }
        }
        return listReturn;
    }

    /**
     * Check is full
     *
     * @param listString
     * @return boolean
     */
    private boolean isFull(List<String> listString) {
        if (listString == null) {
            return false;
        }
        for (String string : listString) {
            if (string.equals("null")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get string question-answer by questionAnswerForm
     *
     * @param questionAnswerForm
     * @return
     */
    private String setAnswer(QuestionAnswerForm questionAnswerForm) {
        String answerset = ConstValues.CONST_STRING_EMPTY;
        boolean flagCheckFirst = true;
        if (questionAnswerForm.getAreaScore().equals(ConstValues.TEST_AREA_SCORE_SCENARIO)) {
            for (int i = 0; i < questionAnswerForm.getAnswers().size(); i++) {
                if (!questionAnswerForm.getAnswers().get(i).equals("null")) {
                    if (flagCheckFirst) {
                        flagCheckFirst = false;
                        answerset = answerset + questionAnswerForm.getQuestions().get(i).replace(";", "") + "-" + questionAnswerForm.getAnswers().get(i).replace(";", "");
                    } else {
                        answerset = answerset + "; ";
                        answerset = answerset + questionAnswerForm.getQuestions().get(i).replace(";", "") + "-" + questionAnswerForm.getAnswers().get(i).replace(";", "");
                    }
                }
            }
        } else {
            for (int i = 0; i < questionAnswerForm.getVocabularyAnswers().size(); i++) {
                if (!questionAnswerForm.getVocabularyAnswers().get(i).equals("null")) {
                    if (flagCheckFirst) {
                        flagCheckFirst = false;
                        answerset = answerset + questionAnswerForm.getVocabularyQuestions().get(i).replace(";", "") + "-" + questionAnswerForm.getVocabularyAnswers().get(i).replace(";", "");
                    } else {
                        answerset = answerset + "; ";
                        answerset = answerset + questionAnswerForm.getVocabularyQuestions().get(i).replace(";", "") + "-" + questionAnswerForm.getVocabularyAnswers().get(i).replace(";", "");
                    }
                }
            }
        }
        return answerset;
    }
}