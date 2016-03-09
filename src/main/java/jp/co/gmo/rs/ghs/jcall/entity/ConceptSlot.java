/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * ConceptSlot
 *
 * @author LongVNH
 *
 */
public class ConceptSlot {
    private String name;

    private String value;

    private double weight;

    private List<ConceptSlotData> conceptSlotDataList;

    private List<ConceptSlotRestriction> conceptSlotRestrictionList;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
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

    /**
     * @return the conceptSlotRestrictionList
     */
    public List<ConceptSlotRestriction> getConceptSlotRestrictionList() {
        return conceptSlotRestrictionList;
    }

    /**
     * @param conceptSlotRestrictionList the conceptSlotRestrictionList to set
     */
    public void setConceptSlotRestrictionList(List<ConceptSlotRestriction> conceptSlotRestrictionList) {
        this.conceptSlotRestrictionList = conceptSlotRestrictionList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConceptSlot [name=" + name + ", value=" + value + ", weight=" + weight 
                + ", conceptSlotDataList=" + conceptSlotDataList + ", conceptSlotRestrictionList=" 
                + conceptSlotRestrictionList + "]";
    }

}
