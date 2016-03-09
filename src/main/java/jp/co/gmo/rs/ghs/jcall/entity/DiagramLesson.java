/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * DiagramLesson
 *
 * @author LongVNH
 */
public class DiagramLesson {
    private String name;

    private List<Gfx> gfxList;

    private boolean vcenter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Gfx> getGfxList() {
        return gfxList;
    }

    public void setGfxList(List<Gfx> gfxList) {
        this.gfxList = gfxList;
    }

    public boolean isVcenter() {
        return vcenter;
    }

    public void setVcenter(boolean vcenter) {
        this.vcenter = vcenter;
    }
}
