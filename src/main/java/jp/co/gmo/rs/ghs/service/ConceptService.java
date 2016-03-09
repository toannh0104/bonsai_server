/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.util.HashMap;
import java.util.Vector;

import jcall.CALL_lessonConceptStruct;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;

/**
 * create AdminService
 *
 * @author ThuyTTT
 *
 */
public interface ConceptService {

    /**
     * Read file Concepts.txt and get all concept in file
     * 
     * @return listConcept
     */
    HashMap<String, Concept> getAllConcept();

    /**
     * Get all conceptStruct
     *
     * @return vector conceptStruct
     */
    Vector<CALL_lessonConceptStruct> getAllConceptStruct();

    /**
     * Get conceptStruct by conceptName and grammar
     *
     * @param conceptName
     * @param grammar
     * @return conceptStruct
     */
    CALL_lessonConceptStruct getConceptStructByConceptName(String conceptName, String grammar);
    
    /**
     * Get grammar by concept name
     *
     * @param conceptName
     * @return string grammar
     */
    String getGrammarByConceptName(String conceptName);
}
