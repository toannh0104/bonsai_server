/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.io.IOException;

import jp.co.gmo.rs.ghs.dto.PracticeTestDto;

/**
 * ClientPracticeTestService interface
 *
 * @author LongVNH
 *
 */
public interface ClientPracticeTestService {

    /**
     * Get practice test
     * 
     * @param lessonNo lesson number
     * @param lessonType lesson type
     * @param practiceTest mode
     * @param lessonInfoId id
     * @param memoryWritingSpeech mode
     * @param modeLanguage mode
     * @param userLanguage user's mother language
     * @return Object
     * 
     * @throws IOException exception
     */
    PracticeTestDto getPracticeTest(int lessonNo, int lessonType, String practiceTest, int lessonInfoId,
            String memoryWritingSpeech, int modeLanguage, final String userLanguage, int mode) throws IOException;
}
