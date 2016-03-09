/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * ConceptSlotRestriction
 *
 * @author LongVNH
 */
public class ConceptSlotRestriction {
    private String slotRestricName;

    private String value;

    private List<ConceptSlotData> conceptSlotDataList;

    /**
     * @return the slotRestricName
     */
    public String getSlotRestricName() {
        return slotRestricName;
    }

    /**
     * @param slotRestricName the slotRestricName to set
     */
    public void setSlotRestricName(String slotRestricName) {
        this.slotRestricName = slotRestricName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the conceptSlotDataList
     */
    public List<ConceptSlotData> getConceptSlotDataList() {
        return conceptSlotDataList;
    }

    /**
     * @param conceptSlotDataList the conceptSlotDataList to set
     */
    public void setConceptSlotDataList(List<ConceptSlotData> conceptSlotDataList) {
        this.conceptSlotDataList = conceptSlotDataList;
    }

}
