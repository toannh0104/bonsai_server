/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import jcall.CALL_lessonConceptStruct;
import jp.co.gmo.rs.ghs.dto.CallJScenarioDto;
import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;
import jp.co.gmo.rs.ghs.dto.ListSentenceDto;
import jp.co.gmo.rs.ghs.jcall.entity.LessonGHS;

/**
 * LessonService interface
 *
 * @author LongVNH
 *
 */
public interface LessonService {

    /**
     * load add sentences
     *
     * @param lessonNo is lesson no
     * @param type is lesson type
     * @param mapConcepts is list mapConcepts
     * @return lesonGHS
     */
    LessonGHS loadAllSentences(final List<CallJVocabularyDto> lessonNo, final int type,
            final LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> mapConcepts);

    /**
     * loadVocabulary
     *
     * @param callJVoca db vocabulary
     * @param modeLanguage language mode
     * @return LessonGHS
     */
    LessonGHS loadVocabulary(List<CallJVocabularyDto> callJVoca, final int modeLanguage);

    /**
     * load add sentences example
     *
     * @param lesson the lesson
     * @return ListSentence
     */
    ListSentenceDto loadAllSentencesDelegate(final int lesson);

    /**
     * count Concept of Lesson
     *
     * @param jScenarioDtos
     * @return list WordGHS
     */
    LinkedHashMap<String, Vector<CALL_lessonConceptStruct>> getConceptAllLesson(final List<CallJScenarioDto> jScenarioDtos);
}
