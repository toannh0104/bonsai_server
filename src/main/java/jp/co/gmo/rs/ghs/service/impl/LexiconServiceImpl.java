/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.model.DataConfig;
import jp.co.gmo.rs.ghs.service.LexiconService;
import jp.co.gmo.rs.ghs.util.JCallUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * LexiconServiceImpl
 * 
 * @author ThuyTTT
 * 
 */
@Service
public class LexiconServiceImpl implements LexiconService {
    private Properties properties;

    /**
     * get worsd list
     * 
     * @param wordList
     * @return words
     */
    private List<Word> getWordList(NodeList wordList) {
        List<Word> words = new ArrayList<Word>();
        Word word = null;
        for (int i = 0; i < wordList.getLength(); i++) {

            // wordNode
            Node wordNode = wordList.item(i);

            // word instance
            String id = wordNode.getAttributes().getNamedItem("id").getNodeValue();

            // list prop
            NodeList propList = wordNode.getChildNodes();

            // get word
            word = getWord(id, propList);
            if (word != null) {
                words.add(word);
            }

        }
        return words != null ? words : null;

    }

    /**
     * get word
     * 
     * @param id
     * @param propList
     * @return word
     */
    private Word getWord(String id, NodeList propList) {
        // word field
        String level = "";
        String group = "";
        String kana = "";
        String kanji = "";
        String romaji = "";
        String type = "";
        String picture = "";
        List<String> categories = new ArrayList<String>();
        String english = "";
        String chinese = "";
        String quantifier = "";
        String number = "";
        String synonym = "";
        String vietnamese = "";

        Word word = new Word();
        for (int j = 0; j < propList.getLength(); j++) {
            Node propNode = propList.item(j);

            if (propNode.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) propNode;
                String name = element.getAttribute("name");
                String value = element.getAttribute("value");

                if (name.equals("level")) {
                    level = value;
                } else if (name.equals("group")) {
                    group = value;
                } else if (name.equals("kana")) {
                    kana = value;
                } else if (name.equals("kanji")) {
                    kanji = value;
                } else if (name.equals("type")) {
                    type = value;
                } else if (name.equals("picture")) {
                    picture = value;
                } else if (name.equals("quantifier")) {
                    quantifier = value;
                } else if (name.equals("number")) {
                    number = value;
                } else if (name.equals("categories")) {
                    String[] data = value.split(";");
                    for (int k = 0; k < data.length; k++) {
                        if (!data[k].equals("")) {
                            categories.add(data[k]);
                        }
                    }
                }
                if (!StringUtils.isEmpty(kanji)) {
                    romaji = JCallUtil.strKanaToRomaji(kana);
                    if (kana.length() > ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH) {
                        romaji = JCallUtil.strKanaToRomaji(kana.substring(
                                0, ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH - 1));
                    }
                }
                // check child node
                if (element.hasChildNodes()) {
                    NodeList setList = element.getChildNodes();
                    for (int k = 0; k < setList.getLength(); k++) {
                        Node setNode = setList.item(k);
                        if (setNode.hasChildNodes()) {
                            NodeList itemList = setNode.getChildNodes();
                            for (int l = 0; l < itemList.getLength(); l++) {
                                Node itemNode = itemList.item(l);
                                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element itemElement = (Element) itemNode;
                                    name = itemElement.getAttribute("name");
                                    value = itemElement
                                            .getAttribute("value");
                                    // add E & C
                                    if (name.equals("english")) {
                                        english = value;
                                    }
                                    if (name.equals("chinese")) {
                                        chinese = value;
                                    }
                                    if (name.equals("synonym")) {
                                        synonym = value;
                                    }
                                    if (name.equals("vietnamese")) {
                                        vietnamese = value;
                                    }
                                }
                            }
                        }

                    }
                }

                // add word value
                if (id != "" && id != null) {
                    try {
                        word.setId(Integer.valueOf(id));
                    } catch (Exception e) {
                        word.setId(-1);
                    }
                } else {
                    word.setId(0);
                }
                if (!StringUtils.isEmpty(level)) {
                    try {
                        word.setLevel(Integer.valueOf(level));
                    } catch (Exception e) {
                        word.setLevel(-1);
                    }
                } else {
                    word.setLevel(0);
                }
                if (!StringUtils.isEmpty(group)) {
                    try {
                        word.setGroups(Integer.valueOf(group));
                    } catch (Exception e) {
                        word.setGroups(-1);
                    }
                } else {
                    word.setGroups(0);
                }
                if (!StringUtils.isEmpty(type)) {
                    try {
                        word.setType(Integer.valueOf(type));
                    } catch (Exception e) {
                        word.setType(-1);
                    }
                } else {
                    word.setType(0);
                }
                if (kana.length() > ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH) {
                    word.setKana(kana.substring(0, ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH - 1));
                } else {
                    word.setKana(kana);
                }
                if (kanji.length() > ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH) {
                    word.setKanji(kanji.substring(0, ConstValues.KanaKanjiRomaji.KANA_LIMIT_LENGTH - 1));
                } else {
                    word.setKanji(kanji);
                }
                word.setRomaji(romaji);
                word.setPicture(picture);
                word.setQuantifier(quantifier);
                word.setNumber(number);
                word.setCategoryList(categories);
                if (english.length() > ConstValues.KanaKanjiRomaji.LANG_LIMIT_LENGTH) {
                    word.setEnglish(english.substring(0, ConstValues.KanaKanjiRomaji.LANG_LIMIT_LENGTH - 1));
                } else {
                    word.setEnglish(english);
                }
                word.setChinese(chinese);
                word.setSynonym(synonym);
                if (vietnamese.length() > ConstValues.KanaKanjiRomaji.LANG_LIMIT_LENGTH) {
                    word.setVietnamese(vietnamese.substring(0, ConstValues.KanaKanjiRomaji.LANG_LIMIT_LENGTH - 1));
                } else {
                    word.setVietnamese(vietnamese);
                }
            }
        }
        return word;
    }

    /**
     * get all dictinary
     * 
     * @return words
     */
    @SuppressWarnings("static-access")
    @Override
    public List<Word> getDictionary() {
        List<Word> words = new ArrayList<Word>();
        String path = "";

        properties = new DataConfig().getProperty();
        try {
            path = properties.getProperty("data.lexicon");

            InputStream is = new FileInputStream(path);

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = null;

            document = builder.parse(is);
            document.getDocumentElement().normalize();
            // list word
            NodeList wordList = document.getDocumentElement().getElementsByTagName("word");
            words = getWordList(wordList);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return words != null ? words : null;
    }

    /**
     * get list by catelogy
     * 
     * @param lexicons
     *            is list all lexicons
     * @param catelogy
     *            is catelogy name
     * @return list word
     */
    @Override
    public List<Word> getListWordByCatelogy(List<Word> lexicons, String catelogy) {
        List<Word> words = new ArrayList<Word>();
        if (lexicons != null && lexicons.size() > 0 && catelogy != null && catelogy.length() > 0) {
            for (Word word : lexicons) {
                for (String cate : word.getCategoryList()) {
                    if (cate.equals(catelogy)) {
                        words.add(word);
                        break;
                    }
                }
            }
        }
        return words;
    }

    /**
     * write list words to file lexicon
     * 
     * @param words
     *            is words
     * @param filePath
     *            is filePath
     * @return string status
     */
    private String writeFile(List<Word> words, String filePath) {
        // check words size
        if (words.size() <= 0) {
            return "error:Export failed!List word is empty!";
        }
        try {
            File fExport = new File(filePath);
            if (fExport.exists()) {
                fExport.delete();
            }
            fExport.createNewFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fExport), "UTF8"));
            StringBuffer text = new StringBuffer();
            if (writer != null) {
                text.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                text.append("<database>\n");
                for (Word word : words) {
                    if (word != null) {
                        text.append("<word id=\"" + word.getId()
                                + "\" componentID=\"" + word.getId() + "\">\n");
                        text.append("<prop name=\"level\" value=\""
                                + word.getLevel() + "\"/>\n");
                        text.append("<prop name=\"kana\" value=\""
                                + word.getKana() + "\"/>\n");
                        if (word.getKanji() != null) {
                            text.append("<prop name=\"kanji\" value=\""
                                    + word.getKanji() + "\"/>\n");
                        }
                        if (word.getType() != 0) {
                            text.append("<prop name=\"type\" value=\""
                                    + word.getType() + "\"/>\n");
                        }
                        if (word.getPicture() != null
                                && !word.getPicture().isEmpty()) {
                            String picture = word.getPicture();
                            if (picture.contains("<")) {
                                picture = picture.replace("<", "&lt;");
                                picture = picture.replace(">", "&gt;");
                            }

                            text.append("<prop name=\"picture\" value=\""
                                    + picture + "\"/>\n");
                        }
                        if (word.getCategoryList() != null) {
                            if (word.getCategoryList().size() > 0) {
                                text.append("<prop name=\"categories\" value=\""
                                        + word.categorytoString()
                                        + "\"/>\n");
                            }
                        }
                        if (word.getGroups() != null && word.getGroups() != 0) {
                            text.append("<prop name=\"group\" value=\""
                                    + word.getGroups() + "\"/>\n");
                        }
                        if (word.getQuantifier() != null
                                && !word.getQuantifier().isEmpty()) {
                            text.append("<prop name=\"quantifier\" value=\""
                                    + word.getQuantifier() + "\"/>\n");
                        }
                        if (word.getNumber() != null
                                && !word.getNumber().isEmpty()) {
                            text.append("<prop name=\"number\" value=\""
                                    + word.getNumber() + "\"/>\n");
                        }
                        text.append("<prop name=\"meaning\">\n");
                        text.append("<set mtype=\"default\">\n");
                        if (word.getEnglish() != null) {
                            text.append("<Item name=\"english\" value=\""
                                    + word.getEnglish() + "\"/>\n");
                        }
                        // default write chinese
                        text.append("<Item name=\"chinese\" value=\""
                                + word.getChinese() + "\"/>\n");

                        if (word.getVietnamese() != null
                                && !word.getVietnamese().isEmpty()) {
                            text.append("<Item name=\"vietnamese\" value=\""
                                    + word.getVietnamese() + "\"/>\n");
                        }
                        if (word.getSynonym() != null
                                && !word.getSynonym().isEmpty()) {
                            text.append("<Item name=\"synonym\" value=\""
                                    + word.getSynonym() + "\"/>\n");
                        }

                        text.append("</set>\n");
                        text.append("</prop>\n");
                        text.append("</word>\n");
                    }
                }
                text.append("</database>\n");
                writer.write(text.toString());
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            return "error:Export failed!File not exist!";

        }
        return "export:Export success!";
    }

    /**
     * save list words
     * 
     * @param words
     *            is list words
     * @return boolean
     */
    @SuppressWarnings("static-access")
    @Override
    public boolean saveListWord(List<Word> wordsNew, List<Word> wordsOld) {
        String path = "";

        properties = new DataConfig().getProperty();
        if (wordsNew.size() > 0) {
            try {
                path = properties.getProperty("data.lexicon");
                // Write content to file lexicon
                String status = writeFile(addListWord(wordsNew, wordsOld), path);

                if (status.equals("export:Export success!")) {
                    return true;
                }

            } catch (Exception e) {

                // Error
                return false;
            }

        }
        return false;
    }

    /**
     * Add list words
     *
     * @param wordsOld
     * @param wordsNew
     * @return
     */
    private List<Word> addListWord(List<Word> wordsNew, List<Word> wordsOld) {
        List<Word> listWordReturn = null;
        if (wordsOld != null && wordsOld.size() > 0 && wordsNew != null && wordsNew.size() > 0) {
            listWordReturn = new ArrayList<Word>();
            listWordReturn.addAll(wordsOld);
            // Remove exits on wordNew 
            removeExitsOnWordList(wordsNew, wordsNew, ConstValues.LEXICON_SAME_LIST);
            removeExitsOnWordList(listWordReturn, wordsNew, ConstValues.LEXICON_DIFFERENT_LIST);
            for (int i = 0; i < wordsNew.size(); i++) {
                Word wordTmp = new Word();
                wordTmp = wordsNew.get(i);
                
                // Set id
                wordTmp.setId(wordsOld.size() + i);
                // Set level
                wordTmp.setLevel(ConstValues.LEXICON_USER_INPUT_LEVEL);
                // Set type
                wordTmp.setType(ConstValues.LEXICON_USER_INPUT_TYPE);
                // Set picture
                wordTmp.setPicture(ConstValues.LEXICON_USER_INPUT_PICTURE);
                // Set chinese
                wordTmp.setChinese(ConstValues.LEXICON_USER_INPUT_CHINESE);
                // Add word to list return
                listWordReturn.add(wordTmp);
            }
        }
        return listWordReturn;
    }

    /**
     * Remove exits on wordList.
     * 
     * @param wordOldList
     * @param wordNewList
     */
    private void removeExitsOnWordList(List<Word> wordOldList, List<Word> wordNewList, int indexCount) {
        // List index word exits on list
        int exitsCount = 0;
        int jIndex = -1;

        for (int i = 0; i < wordNewList.size(); i++) {
            Word wordNew = wordNewList.get(i);
            for (int j = 0; j < wordOldList.size(); j++) {
                Word wordOld = wordOldList.get(j);
                if (wordNew.getKana().equals(wordOld.getKana())
                        && wordNew.getKanji().equals(wordOld.getKanji())
                        && wordNew.getEnglish().equals(wordOld.getEnglish())) {
                    exitsCount++;
                    if (exitsCount > indexCount) {
                        if (indexCount == ConstValues.LEXICON_DIFFERENT_LIST) {
                            jIndex = j;
                        }
                        break;
                    }
                    jIndex = j;
                }
            }
            // Check if element exits more than one on list
            // then update categoryList on old list and remove this element
            if (exitsCount > indexCount) {
                Word wordOld = wordOldList.get(jIndex);
                wordOld.setCategoryList(wordNew.getCategoryList());
                wordNewList.remove(i);
                i--;
            }
            exitsCount = 0;
        }
    }
}
