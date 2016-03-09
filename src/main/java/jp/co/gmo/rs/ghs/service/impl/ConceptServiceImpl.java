/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import jcall.CALL_database;
import jcall.CALL_lessonConceptStruct;
import jcall.CALL_lessonQuestionStruct;
import jcall.CALL_lessonStruct;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.jcall.entity.Concept;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlot;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotData;
import jp.co.gmo.rs.ghs.jcall.entity.ConceptSlotRestriction;
import jp.co.gmo.rs.ghs.model.DataConfig;
import jp.co.gmo.rs.ghs.service.ConceptService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * create AdminService
 *
 * @author ThuyTTT
 *
 */
@Service
public class ConceptServiceImpl implements ConceptService {
    private Properties properties;

    /**
     * Read file Concepts.txt and get all concept in file
     *
     * @return listConcept
     */
    @SuppressWarnings("resource")
    @Override
    public HashMap<String, Concept> getAllConcept() {
        HashMap<String, Concept> conceptMap = new HashMap<String, Concept>();
        Concept concept = null;

        List<ConceptSlot> conceptSlotList = new ArrayList<ConceptSlot>();
        ConceptSlot conceptSlot = null;

        List<ConceptSlotData> conceptSlotDataList = new ArrayList<ConceptSlotData>();
        ConceptSlotData conceptSlotData = null;

        List<ConceptSlotRestriction> conceptSlotRestrictionList = new ArrayList<ConceptSlotRestriction>();
        ConceptSlotRestriction conceptSlotRestriction = null;

        List<ConceptSlotData> conceptSlotDataRestricList = new ArrayList<ConceptSlotData>();

        String commandString = null;
        String dataString = null;

        // state
        int readState;
        final int initialState = 0;
        final int frameState = 1;
        final int slotState = 2;
        final int restrictionState = 3;
        final int errorState = 4;
        final int endState = 5;

        // open file and readline
        String lineString;
        properties = DataConfig.getProperty();
        try {
            readState = initialState;

            InputStream inputStream = new FileInputStream(properties.getProperty("data.concepts"));
            Reader fileReader = new InputStreamReader(inputStream, "Shift-JIS");

            BufferedReader reader = new BufferedReader(fileReader);
            while ((lineString = reader.readLine()) != null) {

                if (lineString.trim().startsWith("#")) {
                    continue;
                }
                if (lineString.startsWith("-eof")) {
                    readState = endState;
                    continue;
                } else {
                    // remove "\t"
                    lineString = lineString.trim();
                    // remove tag <>
                    if (lineString.indexOf("<") == 0 && (lineString.indexOf(">") + 1 == lineString.length())) {
                        lineString = StringUtils.substringBetween(lineString,
                                "<", ">");
                    }
                    String[] split = new String[3];

                    commandString = lineString;
                    dataString = "";

                    // check the co chua gia tri vd <frame lesson12-1>
                    if (lineString.indexOf(" ") > 0) {
                        split = lineString.split("\\s");
                        // get field text and data text
                        commandString = split[0];
                        dataString = StringUtils.substringAfter(lineString,
                                commandString + " ");
                    }
                    switch (readState) {
                        case initialState:
                            if (commandString != null) {
                                // check node <frame>. tao conceptQuestion
                                if (commandString.trim().equals("frame")) {
                                    concept = new Concept();

                                    concept.setName(dataString);
                                    readState = frameState;
                                }
                            }
                            break;
                        case frameState:
                            if (commandString != null) {
                                // check node </frame>. fill dl vao
                                // conceptQuestions

                                if (commandString.equals("/frame")) {
                                    // add conceptSlotList into concept
                                    if (conceptSlotList != null) {
                                        concept.setConceptSlotList(conceptSlotList);
                                        conceptSlotList = new ArrayList<ConceptSlot>();
                                    }
                                    // add item into conceptList
                                    if (concept != null) {
                                        conceptMap.put(concept.getName(), concept);
                                        readState = initialState;
                                    }
                                    readState = initialState;
                                } else if (commandString.equals("slot")) {
                                    conceptSlot = new ConceptSlot();
                                    conceptSlotDataList = new ArrayList<ConceptSlotData>();

                                    String[] data = new String[3];
                                    String name = "";
                                    double weight = 1;

                                    if (dataString.indexOf(" ") > 0) {
                                        data = dataString.split(" ");
                                        name = data[0];
                                        weight = Double.valueOf(data[1]);
                                    }
                                    conceptSlot.setName(name);
                                    conceptSlot.setWeight(weight);

                                    readState = slotState;
                                } else {
                                    readState = frameState;
                                }
                            }
                            break;
                        // doc the con cua formgroup
                        case slotState:
                            // </slot>
                            if (commandString.equals("/slot")) {
                                // add conceptSlotDataRestricList into
                                // conceptSlot
                                if (conceptSlotDataRestricList != null) {
                                    conceptSlot.setConceptSlotRestrictionList(conceptSlotRestrictionList);
                                    conceptSlotRestrictionList = new ArrayList<ConceptSlotRestriction>();
                                }

                                // add conceptSlotDataList vao conceptSlot
                                if (conceptSlotDataList != null) {
                                    conceptSlot.setConceptSlotDataList(conceptSlotDataList);
                                    conceptSlotDataList = new ArrayList<ConceptSlotData>();
                                }
                                //
                                if (conceptSlot != null) {
                                    conceptSlotList.add(conceptSlot);
                                }
                                readState = frameState;
                            } else if (commandString.equals("restriction")) {
                                conceptSlotRestriction = new ConceptSlotRestriction();

                                if (dataString != null) {
                                    conceptSlotRestriction.setSlotRestricName(dataString);
                                }
                                // conceptSlotRestriction.setValue(value);
                                readState = restrictionState;
                            } else {
                                // slot data
                                conceptSlotData = getConceptSlotData(lineString);

                                // add data into conceptSlotDataList
                                if (conceptSlotData != null) {
                                    conceptSlotDataList.add(conceptSlotData);
                                }
                            }
                            break;
                        case restrictionState:
                            if (commandString.equals("/restriction")) {
                                // add conceptSlotDataRestricList
                                if (conceptSlotDataRestricList != null) {
                                    conceptSlotRestriction.setConceptSlotDataList(conceptSlotDataRestricList);
                                    conceptSlotDataRestricList = new ArrayList<ConceptSlotData>();
                                }
                                if (conceptSlotRestriction != null) {
                                    conceptSlotRestrictionList.add(conceptSlotRestriction);
                                }
                                readState = slotState;
                            } else {
                                // doc du lieu ConceptSlotData
                                conceptSlotData = getConceptSlotData(lineString);

                                // add conceptSlotData vao list conceptSlotData
                                if (conceptSlotData != null) {
                                    conceptSlotDataRestricList.add(conceptSlotData);
                                }

                            }
                            break;
                        case errorState:
                            break;
                        case endState:
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return conceptMap != null ? conceptMap : null;
    }

    /**
     * get concep slot data
     *
     * @param lineString
     * @return conceptSlotData
     */
    private ConceptSlotData getConceptSlotData(String lineString) {
        ConceptSlotData conceptSlotData = new ConceptSlotData();
        String[] dataSplit = new String[3];
        int type = 0;
        String data = ConstValues.CONST_STRING_EMPTY;
        double weight = 0;
        String slotData = lineString;
        if (lineString.indexOf(" ") > 0) {
            dataSplit = lineString.split(" ");

            slotData = dataSplit[0];
            weight = Double.valueOf(dataSplit[1]);
        }
        // lay type va data
        if (slotData.indexOf("[") == 0 || slotData.indexOf("]") == slotData.length() - 1) {
            type = 1;
            data = StringUtils.substringBetween(slotData, "[", "]");
        } else if (slotData.indexOf("{") == 0 || slotData.indexOf("}") == slotData.length() - 1) {
            type = 2;
            data = StringUtils.substringBetween(slotData, "{", "}");
        } else if (slotData.indexOf("(") == 0 || slotData.indexOf(")") == slotData.length() - 1) {
            type = 3;
            data = StringUtils.substringBetween(slotData, "(", ")");
        } else {
            // 1 tu xac dinh
            data = slotData;
        }
        // add data vao conceptSlotData
        conceptSlotData.setType(type);
        conceptSlotData.setData(data);
        conceptSlotData.setWeight(weight);

        return conceptSlotData != null ? conceptSlotData : null;
    }

    /**
     * Get all conceptStruct
     */
    @SuppressWarnings("unchecked")
    @Override
    public Vector<CALL_lessonConceptStruct> getAllConceptStruct() {

        CALL_database db = new CALL_database();
        Vector<CALL_lessonConceptStruct> vectorConceptStructReturn = new Vector<>();

/*        // Get all concept
        HashMap<String, Concept> listconcepts = getAllConcept();*/

        // Get all lesson
        List<CALL_lessonStruct> lessons = db.lessons.getAllLesson();

        if (lessons.size() > 0) {
            // For on list lesson
            for (CALL_lessonStruct lessonInList : lessons) {
                // Get list questions of lesson
                Vector<CALL_lessonQuestionStruct> questions = (Vector<CALL_lessonQuestionStruct>) lessonInList.getQuestions();
                if (questions.size() > 0) {
                    // For on list questions
                    for (CALL_lessonQuestionStruct questionInList : questions) {
                        // Get list concept of question
                        Vector<CALL_lessonConceptStruct> concepts = questionInList.getConcepts();
                        if (concepts.size() > 0) {
                            vectorConceptStructReturn.addAll(concepts);
                        }
                    }
                }
            }
        }
/*
        // Get list name of list concept not exist in list name of list
        // conceptStruct
        List<String> conceptNames = getListConceptNameNotExist(vectorConceptStructReturn, listconcepts);

        // Get list concept name contain name in list conceptNames
        HashMap<String, String> conceptNamesContain = getListConceptNameContain(listconcepts, conceptNames);

        // Get list Grammar Rule by list conceptNamesContain
        HashMap<String, String> grammarRule = getListGrammarRule(vectorConceptStructReturn, conceptNamesContain);

        // Add ListConceptStructAdditional to ListConceptStructReturn
        vectorConceptStructReturn.addAll(getListConceptStructAdditional(conceptNames, grammarRule));

        List<String> tmp = new ArrayList<>();
        for (CALL_lessonConceptStruct c : vectorConceptStructReturn) {
            tmp.add(c.concept);
        }*/

        return vectorConceptStructReturn;
    }

    /**
     * Get conceptStruct by conceptName
     */
    @Override
    public CALL_lessonConceptStruct getConceptStructByConceptName(String conceptName, String grammar) {

        // Get all conceptStruct
        Vector<CALL_lessonConceptStruct> concepts = getAllConceptStruct();

        if (concepts != null && !concepts.isEmpty()) {

            // For on list concepts
            for (CALL_lessonConceptStruct conceptInList : concepts) {

                if (conceptInList.concept.equals(conceptName)) {
                    conceptInList.grammar = grammar;
                    return conceptInList;
                }
                System.out.println(conceptInList.concept);
            }
            // Case if concept in list concepts haven't name like conceptName, return concept exception
            CALL_lessonConceptStruct conceptException = new CALL_lessonConceptStruct();
            conceptException.concept = conceptName;
            conceptException.grammar = grammar;
            return conceptException;
        }
        return null;
    }

    /**
     * Get grammar by concept name
     */
    @Override
    public String getGrammarByConceptName(String conceptName) {

        // Get all conceptStruct
        Vector<CALL_lessonConceptStruct> concepts = getAllConceptStruct();

        if (concepts != null && !concepts.isEmpty()) {

            // For on list concepts
            for (CALL_lessonConceptStruct conceptInList : concepts) {

                if (conceptInList.concept.equals(conceptName)) {
                    return conceptInList.grammar;
                }
            }
        }

        return null;
    }

    /**
     * Get list name of list concept not exist in list name of list
     * conceptStruct
     *
     * @param conceptStructs
     * @param concepts
     * @return list string
     */
/*    private List<String> getListConceptNameNotExist(
            Vector<CALL_lessonConceptStruct> conceptStructs,
            HashMap<String, Concept> concepts) {

        List<String> listReturn = new ArrayList<>();

        for (String name : concepts.keySet()) {
            // Default flagCheckExist = false
            boolean flagCheckExist = false;

            for (CALL_lessonConceptStruct conceptStruct : conceptStructs) {

                if (name.equals(conceptStruct.concept)) {
                    // Update flagCheckExist = true
                    flagCheckExist = true;
                    break;
                }
            }

            if (!flagCheckExist) {
                // Add name concept to list return
                listReturn.add(name);
            }
        }
        return listReturn;
    }*/

    /**
     * Get list concept name contain name in list names
     *
     * @param concepts
     * @param names
     * @return list string
     */
/*    private HashMap<String, String> getListConceptNameContain(HashMap<String, Concept> concepts, List<String> names) {

        HashMap<String, String> hashMapReturn = new HashMap<>();

        for (String name : names) {

            for (String key : concepts.keySet()) {
                for (ConceptSlot conceptSlot : concepts.get(key).getConceptSlotList()) {
                    for (ConceptSlotData conceptSlotData : conceptSlot.getConceptSlotDataList()) {
                        if (conceptSlotData.getData().equals(name)) {

                            hashMapReturn.put(name, key);

                        }
                    }
                }
            }
        }
        return hashMapReturn;
    }*/

    /**
     * Get list Grammar Rule by list conceptNames
     *
     * @param conceptStructs
     * @param conceptNames
     * @return list string
     */
/*    private HashMap<String, String> getListGrammarRule(Vector<CALL_lessonConceptStruct> conceptStructs,
 * HashMap<String, String> conceptNames) {

        HashMap<String, String> hashMaplistReturn = new HashMap<>();

        for (String key : conceptNames.keySet()) {
            for (CALL_lessonConceptStruct conceptStruct : conceptStructs) {
                if (conceptStruct.concept.equals(conceptNames.get(key))) {
                    hashMaplistReturn.put(key, conceptStruct.grammar);
                    break;
                }
            }
        }
        return hashMaplistReturn;
    }*/

    /**
     *
     *
     * @param conceptNames
     * @param grammarRules
     * @return
     */
/*    private Vector<CALL_lessonConceptStruct> getListConceptStructAdditional(List<String> conceptNames,
 * HashMap<String, String> grammarRules) {
        Vector<CALL_lessonConceptStruct> vectorReturn = new Vector<>();
        // Loop list concept name
        for (String conceptname : conceptNames) {
            CALL_lessonConceptStruct conceptStruct = new CALL_lessonConceptStruct();
            // Set concept name
            conceptStruct.concept = conceptname;
            // Set grammar default = ""
            conceptStruct.grammar = "";
            // Loop hashMap
            for (String key : grammarRules.keySet()) {

                if (conceptname.equals(key)) {
                    // Update grammar
                    conceptStruct.grammar = grammarRules.get(key);
                    break;
                }
            }
            vectorReturn.add(conceptStruct);
        }
        return vectorReturn;
    }*/
}
