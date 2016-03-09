/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * Concept
 *
 * @author LongVNH
 *
 */
public class Concept {
    private String name;

    private List<ConceptSlot> conceptSlotList;

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
     * @return the conceptSlotList
     */
    public List<ConceptSlot> getConceptSlotList() {
        return conceptSlotList;
    }

    /**
     * @param conceptSlotList the conceptSlotList to set
     */
    public void setConceptSlotList(List<ConceptSlot> conceptSlotList) {
        this.conceptSlotList = conceptSlotList;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Concept [name=" + name + ", conceptSlotList=" + conceptSlotList + "]";
    }

}
