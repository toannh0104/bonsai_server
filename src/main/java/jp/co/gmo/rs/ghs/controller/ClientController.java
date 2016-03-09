/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.ScoreDto;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.form.LessonForm;
import jp.co.gmo.rs.ghs.dto.form.QuestionAnswerForm;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.dto.response.ScoreResponseDto;
import jp.co.gmo.rs.ghs.model.Language;
import jp.co.gmo.rs.ghs.service.AdminService;
import jp.co.gmo.rs.ghs.service.ClientPracticeTestService;
import jp.co.gmo.rs.ghs.service.ClientService;
import jp.co.gmo.rs.ghs.service.LessonService;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClientContronller class
 *
 * @author DongNSH
 *
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientPracticeTestService clientPracticeTestService;

    /* Init LessonService */
    @Autowired
    private LessonService lessonService;

    /**
     * Show list screen.
     *
     * @param model
     *            the model
     * @return String
     */
    @RequestMapping(value = "/lesson/list", method = RequestMethod.GET)
    public String showListLesson(Model model) {

        Integer learningType = ConstValues.MODE_JAPANESE;

        model.addAttribute("lessonForm", new LessonForm());
        model.addAttribute("lstLessonDto", adminService.getAllLesson(learningType));
        Language language = Language.getInstance();
        Map<String, String> languageMap = language.getHashLanguage();
        // Sort languageMap in ascending order
        languageMap = StringUtils.sortByValues(languageMap, -1);
        model.addAttribute("languageMap", languageMap);

        return "client/lesson/list";
    }

    /**
     * Show lesson
     *
     * @param model
     *            the model
     * @param lessonForm
     *            the lessonForm
     * @param id
     *            the id
     * @param session
     *            the session
     * @return String
     */
    @RequestMapping(value = "/lesson/{id}", method = { RequestMethod.POST })
    public String show(Model model,
            @ModelAttribute LessonForm lessonForm,
            @PathVariable int id,
            HttpSession session) {

        // Get variable from session
        UserSessionDto userSessionDto = this.getUserSessionDto();
        int userId = userSessionDto.getUserId();
        String userName = userSessionDto.getUserName();

        String languageId = lessonForm.getLanguageId();
        if (languageId == null) {
            languageId = ConstValues.DEFAULT_LANGUAGE;
        }
        userSessionDto.setNativeLanguage(languageId);

        // Set default currentLessonInfoIndex
        int currentLessonInfoIndex = 0;
        // Init data when load page
        Integer learningType = ConstValues.MODE_JAPANESE;
        LessonDto lessonDto = clientService.innitData(id, userId, languageId, learningType);

        List<LessonInfoDto> lessonInfoDtos = new ArrayList<LessonInfoDto>();
        if (lessonDto.getLessonInfoDtos().size() > 0) {
            lessonInfoDtos.add(lessonDto.getLessonInfoDtos().get(currentLessonInfoIndex));
        }

        // Set to view
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("userSessionDto", this.getUserSessionDto());
        model.addAttribute("languageId", languageId);
        model.addAttribute("currentLessonInfoIndex", currentLessonInfoIndex);
        model.addAttribute("lessonInfoDtos", lessonInfoDtos);
        model.addAttribute("lessonDto", lessonDto);
        model.addAttribute("modeScenario", null);
        model.addAttribute("modeVocabulary", null);

        return "client/start";
    }

    /**
     * Check able switch to test.
     *
     * @param model
     *            the model
     * @param lessonId
     *            the lessonId
     * @param lessonInfoId
     *            the lessonInfoId
     * @return String
     */
    @RequestMapping(value = "/lesson/{lessonId}/{lessonInfoId}/checkswitchtotest", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkSwitchToTest(Model model, @PathVariable int lessonId,
            @PathVariable int lessonInfoId) {
        // Get variable from session
        int userId = this.getUserSessionDto().getUserId();

        ScoreDto scoreDto = clientService.getScore(lessonId, lessonInfoId, userId);
        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        lessonReponseDto = clientService.checkSwitchToTest(scoreDto, lessonInfoId);

        // Convert to json
        return StringUtils.convertObjToJson(lessonReponseDto);
    }

    /**
     * Check next lesson info.
     *
     * @param model
     *            the model
     * @param lessonId
     *            the lessonId
     * @param lessonInfoId
     *            the lessonInfoId
     * @return String
     */
    @RequestMapping(value = "/lesson/{lessonId}/{lessonInfoId}/checkNextLessonInfo", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkNextLessonInfo(Model model, @PathVariable int lessonId,
            @PathVariable int lessonInfoId) {
        // Get variable from session
        int userId = this.getUserSessionDto().getUserId();

        ScoreDto scoreDto = clientService.getScore(lessonId, lessonInfoId, userId);
        LessonReponseDto lessonReponseDto = new LessonReponseDto();
        lessonReponseDto = clientService.checkNextLessonInfo(scoreDto, lessonInfoId);

        // Convert to json
        return StringUtils.convertObjToJson(lessonReponseDto);
    }

    /**
     * Show next/previous lesson info.
     *
     * @param model
     *            the model
     * @param id
     *            the id
     * @param currentLessonInfoIndex
     *            the currentLessonInfoIndex
     * @param nextPrevious
     *            the nextPrev
     * @param lessonForm
     *            the lessonForm
     * @return String
     */
    @RequestMapping(value = "/lesson/{id}/{currentLessonInfoIndex}/{nextPrevious}")
    public String showNextPrevious(Model model, @PathVariable int id,
            @PathVariable int currentLessonInfoIndex, @PathVariable String nextPrevious,
            @ModelAttribute LessonForm lessonForm) {
        // Get variable from session
        int userId = this.getUserSessionDto().getUserId();
        String userName = this.getUserSessionDto().getUserName();

        String languageId = lessonForm.getLanguageId();
        if (languageId == null) {
            languageId = ConstValues.DEFAULT_LANGUAGE;
        }

        // Init data when load page
        Integer learningType = ConstValues.MODE_JAPANESE;
        LessonDto lessonDto = clientService.innitData(id, userId, languageId, learningType);
        int lessonInfoDtosSize = lessonDto.getLessonInfoDtos().size();

        // Calculate current index
        if (ConstValues.GO_NEXT.equals(nextPrevious)) {
            if (currentLessonInfoIndex < (lessonInfoDtosSize - 1)) {
                currentLessonInfoIndex++;
            } else {
                currentLessonInfoIndex = 0;
            }
        } else {
            if (currentLessonInfoIndex > 0) {
                currentLessonInfoIndex--;
            } else {
                currentLessonInfoIndex = lessonInfoDtosSize - 1;
            }
        }

        List<LessonInfoDto> lessonInfoDtos = new ArrayList<LessonInfoDto>();
        lessonInfoDtos.add(lessonDto.getLessonInfoDtos().get(currentLessonInfoIndex));

        // Set to view
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("languageId", languageId);
        model.addAttribute("lessonDto", lessonDto);
        model.addAttribute("lessonInfoDtos", lessonInfoDtos);
        model.addAttribute("currentLessonInfoIndex", currentLessonInfoIndex);
        model.addAttribute("modeScenario", null);
        model.addAttribute("modeVocabulary", null);

        return "client/start";
    }

    /**
     * Calculate score of user when practice/test.
     *
     * @param model
     *            the model
     * @param questionAnswerForm
     *            the questionAnswerForm
     * @return String
     */
    @RequestMapping(value = "/score", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String score(Model model,
            @ModelAttribute QuestionAnswerForm questionAnswerForm) {

        // Get value user session
        UserSessionDto userSessionDto = this.getUserSessionDto();

        // Get scoreDto to return
        ScoreResponseDto scoreResponseDto = clientService.getScoreResponse(questionAnswerForm, userSessionDto);

        // Convert to json
        return StringUtils.convertObjToJson(scoreResponseDto);
    }
}
