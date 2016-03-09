/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.constant.MessageConst;
import jp.co.gmo.rs.ghs.dto.LessonDto;
import jp.co.gmo.rs.ghs.dto.LessonInfoDto;
import jp.co.gmo.rs.ghs.dto.ScenarioDto;
import jp.co.gmo.rs.ghs.dto.UserLogDto;
import jp.co.gmo.rs.ghs.dto.VocabularyDto;
import jp.co.gmo.rs.ghs.dto.form.LessonForm;
import jp.co.gmo.rs.ghs.dto.form.LessonInfoForm;
import jp.co.gmo.rs.ghs.dto.form.LogForm;
import jp.co.gmo.rs.ghs.dto.form.ScenarioForm;
import jp.co.gmo.rs.ghs.dto.form.VocabularyForm;
import jp.co.gmo.rs.ghs.dto.response.AllSentencesExampleResponseDto;
import jp.co.gmo.rs.ghs.dto.response.AllVocabularyResponseDto;
import jp.co.gmo.rs.ghs.dto.response.LessonReponseDto;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.model.Language;
import jp.co.gmo.rs.ghs.service.AdminService;
import jp.co.gmo.rs.ghs.service.ConceptService;
import jp.co.gmo.rs.ghs.service.LessonService;
import jp.co.gmo.rs.ghs.service.LexiconService;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller in admin page
 *
 * @author ThuyTTT
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LexiconService lexiconService;
    @Autowired
    private ConceptService conceptService;
    /**
     * Load lesson screen with lessonId
     *
     * @param model is model attribute
     * @param lessonForm is lessonForm get value form page
     * @param id is lessonId
     * @param session is session
     * @return lesson/start
     */
    @RequestMapping(value = "/lesson/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String show(Model model,
            @ModelAttribute LessonForm lessonForm,
            @PathVariable int id,
            HttpSession session,
            HttpServletRequest request) {

        // set lang
        String languageId = lessonForm.getLanguageId();
        if (languageId == null) {
            languageId = ConstValues.DEFAULT_LANGUAGE;
        }
        this.getUserSessionDto().setNativeLanguage(languageId);

        // init data when load page
        innitData(model, id, languageId, ConstValues.MODE_JAPANESE);
        session.removeAttribute(ConstValues.IS_NEW);
        model.addAttribute(ConstValues.IS_NEW, "false");
        return "lesson/start";
    }

    /**
     * show list screen
     *
     * @param model is model attribute
     * @return lesson/list
     */
    @RequestMapping(value = "/lesson/list", method = RequestMethod.GET)
    public String showListLesson(Model model) {

        model.addAttribute("lstLesson", adminService.getAllLesson(ConstValues.MODE_JAPANESE));
        model.addAttribute("lstLesson_security", adminService.getAllLesson(ConstValues.MODE_SECURITY));
        model.addAttribute("lessonForm", new LessonForm());
        Language language = Language.getInstance();
        Map<String, String> languageMap = language.getHashLanguage();
        // Sort languageMap in ascending order
        languageMap = StringUtils.sortByValues(languageMap, -1);
        model.addAttribute("languageMap", languageMap);
        return "lesson/list";
    }

    /**
     * get all Scenario
     *
     * @param lessonId is lessonId of Lesson
     * @return data
     */
    @RequestMapping(value = "/allScenario", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllScenario(@RequestParam("lessonId") Integer lessonId,
            @RequestParam("learningType") Integer learningType) {

        // Get all sentences from examples
        AllSentencesExampleResponseDto responseDto = adminService.getAllSentencesDelegate(lessonId, learningType);

        // Convert to json
        return StringUtils.convertObjToJson(responseDto);
    }

    /**
     * get all Vocabulary
     *
     * @param listConceptName is concept name
     * @return data
     */
    @RequestMapping(value = "/allVocabulary", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllVocabulary(@RequestParam("listConceptName") String listConceptName) {

        AllVocabularyResponseDto responseDto = new AllVocabularyResponseDto();

        List<Word> lexicons = lexiconService.getDictionary();
        HashMap<String, Concept> conceptMap = conceptService.getAllConcept();

        if (listConceptName.equals("")) {
            responseDto.setResult(lexicons);
            responseDto.setStatus(ConstValues.Status.OK);
            // array to list concept name
            List<String> conceptNames = new ArrayList<String>();
            for (String concepName : conceptMap.keySet()) {
                conceptNames.add(concepName);
            }
            responseDto.setListCategory(adminService.getListCategory(conceptNames, conceptMap));
        } else {
            // String to list concept name
            String[] arrConceptName = listConceptName.split(",");

            // array to list concept name
            List<String> conceptNames = new ArrayList<String>(Arrays.asList(arrConceptName));

            // Get all vocabularies
            responseDto = adminService.getAllVocabularies(conceptMap, lexicons, conceptNames);
            responseDto.setListCategory(adminService.getListCategory(conceptNames, conceptMap));
        }

        // Convert to json
        return StringUtils.convertObjToJson(responseDto);
    }

    /**
     * getAllVocabularyByLearningType
     *
     * @param learningType
     * @return data
     */
    @RequestMapping(value = "/allVocabularyByLearningType", method = RequestMethod.GET,
            produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    @ResponseBody
    public String getAllVocabularyByLearningType(@RequestParam("learningType") Integer learningType) {

        AllVocabularyResponseDto responseDto = new AllVocabularyResponseDto();

        // Get all vocabularies
        responseDto = adminService.getAllVocabularies(learningType);

        // Convert to json
        return StringUtils.convertObjToJson(responseDto);
    }

    /**
     * Handling when creating new
     *
     * @param model is model attribute
     * @param lessonForm is lesson form get value form page
     * @return lesson screen
     */
    @RequestMapping(value = "/saveAs",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String saveAs(Model model, @ModelAttribute LessonForm lessonForm) {

        List<Word> lexicons = lexiconService.getDictionary();

        LessonReponseDto lessonResult = new LessonReponseDto();


        String languageId = this.getUserSessionDto().getNativeLanguage();

        // Get LessonDto in screen
        LessonDto lessonDto = convertLessonForm(lessonForm);

        // Get LessonDto in DB with lessonName
        LessonDto lessonDtoDB = adminService.getLessonByLessonName(lessonDto.getLessonName(),
                languageId, lessonForm.getLearningType());

        // Check lessonName exist
        if (lessonDtoDB == null && lessonDto != null) {
            lessonResult = adminService.saveAs(lessonDto, lexicons);
        } else {
            lessonResult.setMessage(MessageConst.MSG_E0016);
            lessonResult.setStatus(ConstValues.Status.NG);
        }

        Map<String, Object> lessonMap = new LinkedHashMap<String, Object>();
        lessonMap.put("lessonResult", lessonResult);

        // Convert to json
        return StringUtils.convertObjToJson(lessonMap);
    }

    /**
     * Handling when update
     *
     * @param model is model attribute
     * @param lessonForm is lesson form get value form page
     * @param session is session
     * @return lesson screen
     */
    @RequestMapping(value = "/save",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String save(Model model,
            @ModelAttribute LessonForm lessonForm,
            HttpSession session) {

        LessonReponseDto lessonResult = new LessonReponseDto();

        List<Word> lexicons = lexiconService.getDictionary();

        String languageId = this.getUserSessionDto().getNativeLanguage();
        String isNew = (String) session.getAttribute(ConstValues.IS_NEW);

        LessonDto lessonDto = convertLessonForm(lessonForm);

        // Get LessonDto in DB with lessonName
        LessonDto lessonDtoDB = adminService.getLessonByLessonName(lessonDto.getLessonName(), languageId,
                lessonForm.getLearningType());

        if (isNew != null && lessonDtoDB != null) {
            lessonResult.setMessage(MessageConst.MSG_E0016);
            lessonResult.setStatus(ConstValues.Status.NG);
        } else {

            int type = ConstValues.TYPE_UPDATE;
            // Check insert or update
            if (isNew != null) {
                type = ConstValues.TYPE_INSERT;
            }

            lessonResult = adminService.save(lessonDto, type, lexicons);

        }

        Map<String, Object> lessonMap = new LinkedHashMap<String, Object>();
        lessonMap.put("lessonResult", lessonResult);

        // Convert to json
        return StringUtils.convertObjToJson(lessonMap);
    }

    /**
     * Handling when delete
     *
     * @param model is model attribute
     * @param lessonForm is lesson form get value form page
     * @param binding is register error message
     * @return lesson screen
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteLesson(Model model,
            @ModelAttribute LessonForm lessonForm,
            BindingResult binding) {

        // set lang
        String languageId = this.getUserSessionDto().getNativeLanguage();

        LessonDto lessonDto = convertLessonForm(lessonForm);
        boolean delete = adminService.delete(lessonDto);

        // In case delete success
        if (delete) {
            if (lessonForm.getLearningType().equals(ConstValues.MODE_SECURITY)) {
                return "redirect:security/list";
            }
            return "redirect:lesson/list";

            // In case delete fail
        } else {
            binding.rejectValue("", MessageConst.MSG_E0017, new Object[] { "削除" }, null);
            innitData(model, lessonForm.getId(), languageId, lessonForm.getLearningType());

            if (lessonForm.getLearningType().equals(ConstValues.MODE_SECURITY)) {
                return "redirect:/admin/security/" + lessonDto.getId();
            }
            return "redirect:/admin/lesson/" + lessonDto.getId();
        }
    }

    /**
     * load page new
     *
     * @param model
     *            is model attribute
     * @param session
     *            is session
     * @return lesson/start
     */
    @RequestMapping(value = "/new")
    public String newLesson(Model model, HttpSession session) {
        // init data when load page
        innitData(model, null, ConstValues.DEFAULT_LANGUAGE, ConstValues.MODE_JAPANESE);
        session.setAttribute(ConstValues.IS_NEW, "true");
        model.addAttribute(ConstValues.IS_NEW, "true");
        model.addAttribute("languageId", "EN");
        return "lesson/start";
    }

    /**
     * load page new Security
     *
     * @param model is model attribute
     * @param session is session
     * @return lesson/start
     */
    @RequestMapping(value = "/newSecurity")
    public String newSecurity(Model model, HttpSession session) {
        // init data when load page
        innitData(model, null, ConstValues.DEFAULT_LANGUAGE, ConstValues.MODE_SECURITY);
        session.setAttribute(ConstValues.IS_NEW, "true");
        model.addAttribute(ConstValues.IS_NEW, "true");
        return "lesson/start";
    }
    /**
     * innit data when load page
     *
     * @param model is model attribute
     * @param lessonId is lessonId of Lesson
     * @param learningType is learning type
     */
    private void innitData(Model model, Integer lessonId, String languageId, Integer learningType) {
        LessonDto lessonDto = null;

        // set data form session
        model.addAttribute("userId", this.getUserSessionDto().getUserId());
        model.addAttribute("userName", this.getUserSessionDto().getUserName());

        // Check lessonId
        if (lessonId != null) {
            // get lesson form service
            lessonDto = adminService.getLesson(lessonId, languageId);
        } else {

            // Init lesson if null (when create new)
            if (lessonDto == null) {
                // Init sample data for lesson
                lessonDto = new LessonDto();
                lessonDto.setLessonInfoDtos(new ArrayList<LessonInfoDto>());
                lessonDto.setLearningType(learningType);
            }
        }

        // Init lesson info if null (need at least 1)
        if (lessonDto != null && lessonDto.getLessonInfoDtos() != null
                && lessonDto.getLessonInfoDtos().size() == 0) {
            List<LessonInfoDto> lessonInfoDtos = new ArrayList<>();
            LessonInfoDto lessonInfoDto = new LessonInfoDto();

            // Set sample lessonNo = 1
            lessonInfoDto.setLessonNo(1);
            lessonInfoDtos.add(lessonInfoDto);
            lessonDto.setLessonInfoDtos(lessonInfoDtos);
        }

        // Set to view
        model.addAttribute("lessonDto", lessonDto);
        model.addAttribute("languageId", languageId);
    }

    /**
     * convert form LessonForm to LessonDto
     *
     * @param lessonForm
     *            is lesson form get value form page
     * @return LessonDto
     */
    private LessonDto convertLessonForm(LessonForm lessonForm) {
        LessonDto lessonDto = new LessonDto();
        if (lessonForm != null) {
            lessonDto.setId(lessonForm.getId());
            lessonDto.setLessonName(lessonForm.getLessonName());
            lessonDto.setLessonType(lessonForm.getLessonType());
            lessonDto.setLevel(lessonForm.getLevel());
            lessonDto.setPercentComplete(lessonForm.getPercentComplete());
            lessonDto.setAverageScore(lessonForm.getAverageScore());
            lessonDto.setLessonRange(lessonForm.getLessonRange());
            lessonDto.setLessonMethodRomaji(lessonForm.getLessonMethodRomaji());
            lessonDto.setLessonMethodHiragana(lessonForm.getLessonMethodHiragana());
            lessonDto.setLessonMethodKanji(lessonForm.getLessonMethodKanji());
            lessonDto.setLearningType(lessonForm.getLearningType());
            List<LessonInfoDto> lessonInfoDtos = getValueLessonInfo(lessonForm);
            lessonDto.setLessonInfoDtos(lessonInfoDtos);
        }
        return lessonDto;
    }

    /**
     * convert form lessonInfoForms to lessonInfoDtos
     *
     * @param lessonForm
     *            is lesson form get value form page
     * @return lessonInfoDtos
     */
    private List<LessonInfoDto> getValueLessonInfo(LessonForm lessonForm) {
        List<LessonInfoDto> lessonInfoDtos = new ArrayList<LessonInfoDto>();
        List<LessonInfoForm> lessonInfoForms = lessonForm.getLessonInfoForms();
        if (lessonInfoForms != null) {
            for (LessonInfoForm lessonInfoForm : lessonInfoForms) {
                if (lessonInfoForm.getLessonNo() != null) {
                    LessonInfoDto lessonInfoDto = new LessonInfoDto();
                    lessonInfoDto.setId(lessonInfoForm.getId());
                    lessonInfoDto.setLessonId(lessonInfoForm.getLessonId());
                    lessonInfoDto.setLessonNo(lessonInfoForm.getLessonNo());
                    lessonInfoDto.setPracticeConversationCondition(lessonInfoForm.getPracticeConversationCondition());
                    lessonInfoDto.setPracticeMemoryCondition(lessonInfoForm.getPracticeMemoryCondition());
                    lessonInfoDto.setPracticeWritingCondition(lessonInfoForm.getPracticeWritingCondition());
                    lessonInfoDto.setTestConversationCondition(lessonInfoForm.getTestConversationCondition());
                    lessonInfoDto.setTestMemoryCondition(lessonInfoForm.getTestMemoryCondition());
                    lessonInfoDto.setTestWritingCondition(lessonInfoForm.getTestWritingCondition());
                    lessonInfoDto.setScenarioName(lessonInfoForm.getScenarioName());
                    lessonInfoDto.setScenarioNameEn(lessonInfoForm.getScenarioNameEn());
                    lessonInfoDto.setScenarioNameLn(lessonInfoForm.getScenarioNameLn());
                    lessonInfoDto.setScenarioSyntax(lessonInfoForm.getScenarioSyntax());
                    lessonInfoDto.setPracticeNo(lessonInfoForm.getPracticeNo());
                    lessonInfoDto.setPracticeLimit(lessonInfoForm.getPracticeLimit());
                    List<ScenarioDto> scenarioDtos = getValueScenario(lessonInfoForm, lessonForm.getLearningType());
                    lessonInfoDto.setScenarioDtos(scenarioDtos);
                    List<VocabularyDto> vocabularyDtos = getValueVocabulary(lessonInfoForm, lessonForm.getLearningType());
                    lessonInfoDto.setVocabularyDtos(vocabularyDtos);
                    lessonInfoDto.setSpeakerOneName(lessonInfoForm.getSpeakerOneName());
                    lessonInfoDto.setSpeakerTwoName(lessonInfoForm.getSpeakerTwoName());
                    lessonInfoDtos.add(lessonInfoDto);
                }
            }
        }
        return lessonInfoDtos;
    }

    /**
     * convert form listVocabularyForm to listVocabularyDto
     *
     * @param lessonInfoForm
     *            is lessonInfo form get value form page
     * @return listVocabularyDto
     */
    private List<VocabularyDto> getValueVocabulary(LessonInfoForm lessonInfoForm, Integer learningType) {
        List<VocabularyDto> listVocabularyDto = new ArrayList<VocabularyDto>();
        List<VocabularyForm> listVocabularyForm = lessonInfoForm.getVocabularyForms();
        if (listVocabularyForm != null) {
            for (VocabularyForm vocabularyForm : listVocabularyForm) {
                VocabularyDto vocabularyDto = new VocabularyDto();
                vocabularyDto.setId(vocabularyForm.getId());
                vocabularyDto.setLessonInfoId(vocabularyForm.getLessonInfoId());
                vocabularyDto.setVocabularyOrder(vocabularyForm.getVocabularyOrder());
                vocabularyDto.setVocabulary(vocabularyForm.getVocabulary());
                vocabularyDto.setUserInputFlg(vocabularyForm.getUserInputFlg());
                vocabularyDto.setVocabularyEnglishName(vocabularyForm.getVocabularyEnglishName());
                vocabularyDto.setRubyWord(vocabularyForm.getRubyWord());
                vocabularyDto.setLearningType(learningType);
                vocabularyDto.setVocabularyCategories(vocabularyForm.getVocabularyCategories());
                vocabularyDto.setVocabularyKanaName(vocabularyForm.getVocabularyKanaName());
                listVocabularyDto.add(vocabularyDto);
            }
        }
        return listVocabularyDto;
    }

    /**
     * convert form listScenarioForm to listScenarioDto
     *
     * @param lessonInfoForm
     *            is lessonInfo form get value form page
     * @return listScenarioDto
     */
    private List<ScenarioDto> getValueScenario(LessonInfoForm lessonInfoForm, Integer learningType) {
        List<ScenarioDto> listScenarioDto = new ArrayList<ScenarioDto>();
        List<ScenarioForm> listScenarioForm = lessonInfoForm.getScenarioForms();
        if (listScenarioForm != null) {
            for (ScenarioForm scenarioForm : listScenarioForm) {
                ScenarioDto scenarioDto = new ScenarioDto();
                scenarioDto.setId(scenarioForm.getId());
                scenarioDto.setLessonInfoId(scenarioForm.getLessonInfoId());
                scenarioDto.setScenarioPart(scenarioForm.getScenarioPart());
                scenarioDto.setScenarioOrder(scenarioForm.getScenarioOrder());
                scenarioDto.setScenario(scenarioForm.getScenario().trim());
                scenarioDto.setUserInputFlg(scenarioForm.getUserInputFlg());
                scenarioDto.setCalljConceptName(scenarioForm.getCalljConceptName());
                scenarioDto.setCalljQuestionName(scenarioForm.getCalljQuestionName());
                scenarioDto.setCalljLessonNo(scenarioForm.getCalljLessonNo());
                scenarioDto.setCalljWord(scenarioForm.getCalljWord());
                scenarioDto.setRubyWord(scenarioForm.getRubyWord());
                scenarioDto.setLearningType(learningType);
                scenarioDto.setCategoryWord(scenarioForm.getCategoryWord());
                listScenarioDto.add(scenarioDto);
            }
        }
        return listScenarioDto;
    }

    /**
     *
     * Load security screen with lessonId
     *
     * @param model
     *            is model attribute
     * @param lessonForm
     *            is lessonForm get value form page
     * @param id
     *            is lessonId
     * @param session
     *            is session
     * @return lesson/start
     */

    @RequestMapping(value = "/security/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String showSecurity(Model model,
            @ModelAttribute LessonForm lessonForm,
            @PathVariable int id,
            HttpSession session) {

        String languageId = lessonForm.getLanguageId();
        if (languageId == null) {
            languageId = ConstValues.DEFAULT_LANGUAGE;
        }
        this.getUserSessionDto().setNativeLanguage(languageId);

        // init data when load page
        innitData(model, id, languageId, ConstValues.MODE_SECURITY);
        session.removeAttribute(ConstValues.IS_NEW);
        model.addAttribute(ConstValues.IS_NEW, "false");
        return "lesson/start";

    }

    /**
     * show list screen
     *
     * @param model is model attribute
     * @return security/list
     */
    @RequestMapping(value = "/security/list", method = RequestMethod.GET)
    public String showListSecurity(Model model) {

        model.addAttribute("lstLessonDto", adminService.getAllLesson(ConstValues.MODE_SECURITY));
        model.addAttribute("lessonForm", new LessonForm());
        Language language = Language.getInstance();
        Map<String, String> languageMap = language.getHashLanguage();
        // Sort languageMap in ascending order
        languageMap = StringUtils.sortByValues(languageMap, -1);
        model.addAttribute("languageMap", languageMap);
        return "security/list";
    }

    /**
     * show log page
     *
     * @return log
     */
    @RequestMapping(value = "/showLog", method = RequestMethod.GET)
    public String showLog(Model model){

        LogForm logForm = new LogForm();
        logForm.setCurrentPageData(ConstValues.FIRST_PAGE);
        logForm.setCurrentPageLogin(ConstValues.FIRST_PAGE);
        initData(model, logForm, ConstValues.MODE_LOG_LOGIN);

        return "log";
    }

    /**
     * init when load page
     *
     * @param model
     * @param page
     */
    private void initData(Model model, LogForm logForm, Integer modeLog) {

        List<UserLogDto> listLogData = adminService.getSizeUserLogByModeLog(ConstValues.MODE_LOG_DATA, logForm);
        List<UserLogDto> listLogLogin = adminService.getSizeUserLogByModeLog(ConstValues.MODE_LOG_LOGIN, logForm);

        model.addAttribute("logForm", logForm);

        model.addAttribute("modeLogin", ConstValues.MODE_LOG_LOGIN);
        model.addAttribute("modeData", ConstValues.MODE_LOG_DATA);

        model.addAttribute("listLogData", listLogData);
        model.addAttribute("listLogLogin", listLogLogin);

        model.addAttribute("modeLog", modeLog);

        List<String> terminal = new ArrayList<String>();
        terminal.add("全て");
        terminal.add("IOS");
        terminal.add("Android");
        terminal.add("PC");
        model.addAttribute("terminal", terminal);

        List<String> modeLearning = new ArrayList<String>();
        modeLearning.add("全て");
        modeLearning.add("構文");
        modeLearning.add("単語");
        model.addAttribute("modeLearning", modeLearning);

        List<String> modePracticeOrTest = new ArrayList<String>();
        modePracticeOrTest.add("全て");
        modePracticeOrTest.add("練習");
        modePracticeOrTest.add("テスト");
        model.addAttribute("modePracticeOrTest", modePracticeOrTest);

        List<String> modeSkill = new ArrayList<String>();
        modeSkill.add("全て");
        modeSkill.add("記憶力");
        modeSkill.add("筆記力");
        modeSkill.add("会話力");
        model.addAttribute("modeSkill", modeSkill);
    }

    /**
     * Handling when creating new
     *
     * @param model is model attribute
     * @param lessonForm is lesson form get value form page
     * @return lesson screen
     */
    @RequestMapping(value = "/logLogin/modeLog/{modeLog}",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    public String loadLog(Model model, @ModelAttribute LogForm logForm, @PathVariable Integer modeLog) {

        initData(model, logForm, modeLog);
        return "log";
    }

}