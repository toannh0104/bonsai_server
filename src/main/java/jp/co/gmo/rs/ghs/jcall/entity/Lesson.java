/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.jcall.entity;

import java.util.List;

/**
 * Lesson
 *
 * @author LongVNH
 */
public class Lesson {
    private String index;

    private String title;

    private String link;

    private List<String> level;

    private double lessonWeight; // = tong questionweight

    private List<Example> exampleList;

    private List<QuestionLesson> questionLessonList;

    private List<DiagramLesson> diagramLessonList;

    private List<GrammarFormGroup> grammarFormGroupList;

    private String experience;

    private String grade;

    private String answered;

    private String poin;

    private String totalPoin;

    private String times;

    private String totalTime;

    private String speechTimes;

    private String speechExperien;

    private String speechAnswered;

    private String speechPoin;

    private String speechTotalPoin;

    private String speechTotalTime;

    private List<Double> listScore;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getLevel() {
        return level;
    }

    public void setLevel(List<String> level) {
        this.level = level;
    }

    public double getLessonWeight() {
        return lessonWeight;
    }

    public void setLessonWeight(double lessonWeight) {
        this.lessonWeight = lessonWeight;
    }

    public List<Example> getExampleList() {
        return exampleList;
    }

    public void setExampleList(List<Example> exampleList) {
        this.exampleList = exampleList;
    }

    public List<QuestionLesson> getQuestionLessonList() {
        return questionLessonList;
    }

    public void setQuestionLessonList(List<QuestionLesson> questionLessonList) {
        this.questionLessonList = questionLessonList;
    }

    public List<DiagramLesson> getDiagramLessonList() {
        return diagramLessonList;
    }

    public void setDiagramLessonList(List<DiagramLesson> diagramLessonList) {
        this.diagramLessonList = diagramLessonList;
    }

    public List<GrammarFormGroup> getGrammarFormGroupList() {
        return grammarFormGroupList;
    }

    public void setGrammarFormGroupList(List<GrammarFormGroup> grammarFormGroupList) {
        this.grammarFormGroupList = grammarFormGroupList;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAnswered() {
        return answered;
    }

    public void setAnswered(String answered) {
        this.answered = answered;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getTotalPoin() {
        return totalPoin;
    }

    public void setTotalPoin(String totalPoin) {
        this.totalPoin = totalPoin;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getSpeechTimes() {
        return speechTimes;
    }

    public void setSpeechTimes(String speechTimes) {
        this.speechTimes = speechTimes;
    }

    public String getSpeechExperien() {
        return speechExperien;
    }

    public void setSpeechExperien(String speechExperien) {
        this.speechExperien = speechExperien;
    }

    public String getSpeechAnswered() {
        return speechAnswered;
    }

    public void setSpeechAnswered(String speechAnswered) {
        this.speechAnswered = speechAnswered;
    }

    public String getSpeechPoin() {
        return speechPoin;
    }

    public void setSpeechPoin(String speechPoin) {
        this.speechPoin = speechPoin;
    }

    public String getSpeechTotalPoin() {
        return speechTotalPoin;
    }

    public void setSpeechTotalPoin(String speechTotalPoin) {
        this.speechTotalPoin = speechTotalPoin;
    }

    public String getSpeechTotalTime() {
        return speechTotalTime;
    }

    public void setSpeechTotalTime(String speechTotalTime) {
        this.speechTotalTime = speechTotalTime;
    }

    public List<Double> getListScore() {
        return listScore;
    }

    public void setListScore(List<Double> listScore) {
        this.listScore = listScore;
    }

    public Lesson(String index) {
        this.index = index;
    }

    public Lesson() {
    }
}
