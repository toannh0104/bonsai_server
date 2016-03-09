/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * Gfx
 *
 * @author LongVNH
 */
public class Gfx {
    private String type;

    private int left;

    private int top;

    private int width;

    private int height;

    private String text;

    private String gfx_data;

    private String gfx_marker;

    private String image;

    private String imageText;

    private String gfx_markerText;

    public List<StringPair> restrictionList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGfx_data() {
        return gfx_data;
    }

    public void setGfx_data(String gfx_data) {
        this.gfx_data = gfx_data;
    }

    public String getGfx_marker() {
        return gfx_marker;
    }

    public void setGfx_marker(String gfx_marker) {
        this.gfx_marker = gfx_marker;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

    public String getGfx_markerText() {
        return gfx_markerText;
    }

    public void setGfx_markerText(String gfx_markerText) {
        this.gfx_markerText = gfx_markerText;
    }

    public List<StringPair> getRestrictionList() {
        return restrictionList;
    }

    public void setRestrictionList(List<StringPair> restrictionList) {
        this.restrictionList = restrictionList;
    }
}
