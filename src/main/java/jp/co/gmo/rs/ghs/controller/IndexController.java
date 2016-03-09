/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 *
 * @author LongVNH
 *
 */
@Controller
public class IndexController extends BaseController {

    /**
     * Home page controller
     *
     * @return login page
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "redirect:/login";
    }

}
