/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.xml.sax.SAXException;

/**
 * WebUtils
 *
 * @author LongVNH
 * @version 1.0
 */
public class WebUtils {

    /**
     * Constructor
     */
    protected WebUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    public static String source(String urlSite) {
        StringBuilder result = new StringBuilder();

        try {
            WebConversation webConversation = new WebConversation();
            HttpUnitOptions.setScriptingEnabled(false);
            HttpUnitOptions.setDefaultCharacterSet("UTF8");

            WebResponse webResponse = webConversation.getResponse(urlSite);
           /* Reader reader = new InputStreamReader(webResponse.getInputStream(),
                    "utf-8");*/
            Reader reader = new InputStreamReader(webResponse.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);

            int byteRead;
            while ((byteRead = br.read()) != -1) {
                result.append((char) byteRead);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
