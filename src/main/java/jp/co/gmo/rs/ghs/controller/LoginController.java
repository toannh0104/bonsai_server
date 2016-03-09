/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.constant.MessageConst;
import jp.co.gmo.rs.ghs.dto.UserDto;
import jp.co.gmo.rs.ghs.dto.response.LoginResponseDto;
import jp.co.gmo.rs.ghs.dto.response.LogoutResponseDto;
import jp.co.gmo.rs.ghs.dto.response.SpeechResponseDto;
import jp.co.gmo.rs.ghs.model.UserLog;
import jp.co.gmo.rs.ghs.service.AdminService;
import jp.co.gmo.rs.ghs.service.LoginService;
import jp.co.gmo.rs.ghs.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller in login page
 *
 * @author ThuyTTT
 *
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AdminService adminService;

    /**
     * Go to login page
     *
     * @param model
     *            is model attribute
     * @return login
     */
    @RequestMapping(value = "/login", method = { RequestMethod.GET })
    public String show(Model model) {

        UserDto userDto = new UserDto();
        // Set to view
        model.addAttribute("userDto", userDto);

        return "login";
    }

    /**
     * logout page
     * 
     * @param model
     * @return login page
     */
    @RequestMapping(value = "/logout", method = { RequestMethod.GET })
    @ResponseBody
    public LogoutResponseDto logout(Model model) {

        LogoutResponseDto responseDto = new LogoutResponseDto();
        Integer idLog = this.getUserSessionDto().getIdLog();
        if (idLog != null) {
            UserLog userLog = adminService.getUserLogByID(idLog);
            userLog.setEndTime(new Date());
            adminService.updateUseLog(userLog);
            responseDto.setStatus(ConstValues.Status.OK);
        } else {
            responseDto.setMessage(MessageConst.EMSG0006);
            responseDto.setStatus(ConstValues.Status.NG);
        }

        return responseDto;
    }

    /**
     * login in page and save data in user_log
     *
     * @param model
     * @param loginForm
     * @param binding
     * @return lesson list page
     */
    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST,
            produces = "application/json; charset=utf-8")
    @ResponseBody
    public LoginResponseDto login(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("uuid") String uuid,
            @RequestParam("device_name") String deviceName,
            @RequestParam("device_version") String deviceVersion,
            @RequestParam("location") String location) {

        StringBuilder path = new StringBuilder();
        path.append(request.getScheme());
        path.append("://");
        path.append(request.getServerName());
        path.append(":");
        path.append(request.getServerPort());
        path.append(request.getContextPath());

        LoginResponseDto responseDto = new LoginResponseDto();

        UserDto userDto = loginService.getUserLogin(userName, password);
        if (userDto == null) {
            responseDto.setStatus(ConstValues.Status.NG);
            responseDto.setMessage(MessageConst.EMSG0004);
        } else {
            try {
                Date startTime = new Date();
                
                // save user to table UserLog
                Integer idLog = loginService.insertInUserLog(userDto, uuid, ConstValues.CONST_STRING_EMPTY, deviceName, deviceVersion, location, startTime);
                
                // create token
                String token = TokenUtils.createToken(String.valueOf(userDto.getId()), userName, uuid, deviceName, deviceVersion, location, idLog);
                
                if (idLog != null) {
                    UserLog userLog = adminService.getUserLogByID(idLog);
                    userLog.setToken(token);
                    adminService.updateUseLog(userLog);
                }
                
                String learningJapanese = "";
                String learningSafetyBasic = "";
                if (userDto.getRole() == ConstValues.ROLE_ADMIN) {
                    learningJapanese = path.toString() + "/admin/lesson/list";
                    learningSafetyBasic = path.toString() + "/admin/security/list";
                } else {
                    learningJapanese = path.toString() + "/client/lesson/list";
                    // TODO
                    // doesnot have jsp page, comment out
                    // leaningSecurity = path.toString() +
                    // "/client/security/list";
                }
                responseDto.setStatus(ConstValues.Status.OK);
                responseDto.setLearningJpBasic(learningJapanese);
                responseDto.setLearningSafetyBasic(learningSafetyBasic);
                responseDto.setToken(token);
                
                // TODO QA get nativeLang
                setCookie(response, ConstValues.TokenAuthentication.AUTH_HEADER_NAME, token);
            } catch (Exception e) {
                responseDto.setStatus(ConstValues.Status.NG);
                responseDto.setMessage(MessageConst.EMSG0005);
            }
        }

        // return
        return responseDto;
    }

    /**
     * @param request
     * @param response
     * @return error page
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public SpeechResponseDto errorHandler(HttpServletRequest request) {
        SpeechResponseDto speechRes = new SpeechResponseDto();
        speechRes.setMessage(MessageConst.EMSG0003);
        speechRes.setStatus(ConstValues.Status.NG);

        Integer idLog = this.getUserSessionDto().getIdLog();
        if (idLog != null) {
            UserLog userLog = adminService.getUserLogByID(idLog);
            userLog.setEndTime(new Date());
            adminService.updateUseLog(userLog);
        }

        // return
        return speechRes;
    }

}