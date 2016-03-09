/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.util.List;

import jp.co.gmo.rs.ghs.jcall.entity.Word;

/**
 * create interface LexiconService
 *
 * @author LongVNH
 *
 */
public interface LexiconService {

    /**
     * get all dictinary
     *
     * @return listWord
     */
    List<Word> getDictionary();

    /**
     * get list by catelogy
     *
     * @param lexicons is list all lexicons
     * @param catelogy is catelogy name
     * @return list Word
     */
    List<Word> getListWordByCatelogy(List<Word> lexicons, String catelogy);

    /**
     * save list words
     *
     * @param wordsNew is wordsNew
     * @param wordsOld is wordsOld
     * @return boolean
     */
    boolean saveListWord(List<Word> wordsNew, List<Word> wordsOld);
}
