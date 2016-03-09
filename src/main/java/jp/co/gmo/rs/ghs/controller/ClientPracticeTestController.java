/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.PracticeTestDto;
import jp.co.gmo.rs.ghs.dto.form.PracticeTestRequestForm;
import jp.co.gmo.rs.ghs.service.ClientPracticeTestService;
import jp.co.gmo.rs.ghs.service.ClientService;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class ClientPracticeTestController extends BaseController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientPracticeTestService clientPracticeTestService;


    /**
     * Show practice test
     *
     * @param model
     *            model attribute
     * @param request
     *            http request
     * @param lessonForm
     *            form
     * @return string
     */
    @RequestMapping(value = "/lesson/practicetest", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showPracticeTest(Model model, HttpServletRequest request,
            @ModelAttribute PracticeTestRequestForm practiceTestRequestForm) {

        // Get variable from session
        /*UserSessionDto userSessionDto = this.getUserSessionDto();
        int userId = userSessionDto.getUserId();
        String userName = userSessionDto.getUserName();*/

        String languageId = practiceTestRequestForm.getLanguageId();
        if (languageId == null) {
            languageId = ConstValues.DEFAULT_LANGUAGE;
        }

        // Get variable from form submit
/*        Integer id = lessonForm.getId();
        Integer currentLessonInfoIndex = lessonForm.getCurrentLessonInfoIndex();
        String scenavoca = lessonForm.getScenarioVocabulary();*/
        String practiceTest = practiceTestRequestForm.getPracticeTest();
        String memoryWritingConversation = practiceTestRequestForm.getMemoryWritingConversation();
        Integer lessonType = practiceTestRequestForm.getLessonType();
        Integer lessonInfoId = practiceTestRequestForm.getLessonInfoId();
        Integer lessonNo = practiceTestRequestForm.getLessonNo();
        Integer modeLanguage = practiceTestRequestForm.getModeLanguage();
        //Integer learningType = ConstValues.MODE_JAPANESE;
        /*Integer practiceNo = lessonForm.getPracticeNo();
        if(practiceNo == null || lessonForm.getPracticeTest().equals(ConstValues.TEST)){
            practiceNo = 1;
        }*/

/*        if(practiceTestRequestForm.getPageIndexPracticeMemory() == null){
            practiceTestRequestForm.setPageIndexPracticeMemory(1);
        }
        
        if(practiceTestRequestForm.getPageIndexPracticeWriting() == null){
            practiceTestRequestForm.setPageIndexPracticeWriting(1);
        }
        
        if(practiceTestRequestForm.getPageIndexPracticeConversation() == null){
            practiceTestRequestForm.setPageIndexPracticeConversation(1);
        }
        if(practiceTestRequestForm.getPageIndexPracticeMemoryVocabulary() == null){
            practiceTestRequestForm.setPageIndexPracticeMemoryVocabulary(1);
        }
        
        if(practiceTestRequestForm.getPageIndexPracticeWritingVocabulary() == null){
            practiceTestRequestForm.setPageIndexPracticeWritingVocabulary(1);
        }
        
        if(practiceTestRequestForm.getPageIndexPracticeConversationVocabulary() == null){
            practiceTestRequestForm.setPageIndexPracticeConversationVocabulary(1);
        }*/
        // init data when load page
        /*LessonDto lessonDto = clientService.innitData(id, userId, languageId, learningType);
        List<LessonInfoDto> lessonInfoDtos = new ArrayList<LessonInfoDto>();
        lessonInfoDtos.add(lessonDto.getLessonInfoDtos().get(currentLessonInfoIndex));
        model.addAttribute("currentLessonInfoIndex", currentLessonInfoIndex);
        model.addAttribute("lessonInfoDtos", lessonInfoDtos);
        model.addAttribute("lessonDto", lessonDto);*/

        int mode = 0;
        if (ConstValues.MEMORY_TEXT.equals(memoryWritingConversation)) {
            mode = ConstValues.MODE_MEMORY;
        } else if (ConstValues.WRITING_TEXT.equals(memoryWritingConversation)) {
            mode = ConstValues.MODE_WRITING;
        } else {
            mode = ConstValues.MODE_CONVERSATION;
        }

        // Call business
        PracticeTestDto practiceTestDto;
        try {
            practiceTestDto = clientPracticeTestService.getPracticeTest(lessonNo, lessonType, practiceTest,
                    lessonInfoId, memoryWritingConversation, modeLanguage, languageId, mode);
            practiceTestDto.setStatus(ConstValues.Status.OK);
            practiceTestDto.setPageIndex(1);
            practiceTestDto.setPracticeTest(practiceTest);
            practiceTestDto.setMemoryWritingConversation(memoryWritingConversation);
            practiceTestDto.setLessonType(lessonType);
            SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            practiceTestDto.setStartTime(sf.format(new java.util.Date()));
        } catch (IOException e) {
            practiceTestDto = new PracticeTestDto();
            practiceTestDto.setStatus(ConstValues.Status.NG);
        }

        // Set to view
        /*model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("languageId", languageId);
        model.addAttribute("modeLanguage", modeLanguage);*/

        model.addAttribute("practice_test", practiceTest);
        model.addAttribute("memory_writing_speech", memoryWritingConversation);
        model.addAttribute("modeScenario", mode);
        model.addAttribute("modeVocabulary", mode);

        //model.addAttribute("scena_voca", scenavoca);

        model.addAttribute("pageIndex", 1);

        practiceTestDto.setPageIndexPracticeMemory(practiceTestRequestForm.getPageIndexPracticeMemory());
        practiceTestDto.setPageIndexPracticeWriting(practiceTestRequestForm.getPageIndexPracticeWriting());
        practiceTestDto.setPageIndexPracticeConversation(practiceTestRequestForm.getPageIndexPracticeConversation());
        practiceTestDto.setPageIndexPracticeMemoryVocabulary(practiceTestRequestForm.getPageIndexPracticeMemoryVocabulary());
        practiceTestDto.setPageIndexPracticeWritingVocabulary(practiceTestRequestForm.getPageIndexPracticeWritingVocabulary());
        practiceTestDto.setPageIndexPracticeConversationVocabulary(practiceTestRequestForm.getPageIndexPracticeConversationVocabulary());

        //model.addAttribute("practiceNo", practiceNo);

        model.addAttribute("pageLimit", practiceTestDto.getPageLimit());
        model.addAttribute("scenarioList", practiceTestDto.getScenarioList());
        model.addAttribute("vocabularyList", practiceTestDto.getVocabularyList());
        model.addAttribute("scenarioShowList", practiceTestDto.getScenarioShowList());
        model.addAttribute("vocabularyShowList", practiceTestDto.getVocabularyShowList());
        model.addAttribute("scenarioJSonList", practiceTestDto.getScenarioJSonList());
        model.addAttribute("scenarioRubyList", practiceTestDto.getScenarioRubyList());
        model.addAttribute("vocabularyRubyList", practiceTestDto.getVocabularyRubyList());
        model.addAttribute("vocabularyJSonList", practiceTestDto.getVocabularyJSonList());
        model.addAttribute("memoryScenarioAnswerList", practiceTestDto.getMemoryScenarioAnswerList());
        model.addAttribute("memoryVocabularyAnswerList", practiceTestDto.getMemoryVocabularyAnswerList());
        model.addAttribute("element", practiceTestDto.getElement());
        
        /*SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        model.addAttribute("startTime", sf.format(new java.util.Date()));*/

        
        return StringUtils.convertObjToJson(practiceTestDto);
    }
}
