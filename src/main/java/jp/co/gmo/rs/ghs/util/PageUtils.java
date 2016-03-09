/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.PracticeTestDto;

/**
 * PageUtils
 *
 * @author DongNSH
 *
 */
public class PageUtils {

    /**
     * Constructor
     */
    protected PageUtils() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Get size per page.
     *
     * @param totalSize
     *            size of list
     * @param practiceTestDto
     *            get page limit
     */
    public static void getSizePerPage(final int totalSize, PracticeTestDto practiceTestDto) {
        int sizePerPage = ConstValues.PageDefault.SIZE_PER_PAGE;
        int pageLimit = 0;
        if (totalSize > 0) {
            if (totalSize <= ConstValues.PageDefault.SIZE_PER_PAGE) {
                // In case totalSize <= pageLimit then just practice one time
                sizePerPage = totalSize;
                pageLimit = 1;
            } else {
                pageLimit = totalSize / ConstValues.PageDefault.SIZE_PER_PAGE;
                if (totalSize % ConstValues.PageDefault.SIZE_PER_PAGE > 0) {
                    pageLimit++;
                }
                // pageLimit max in db
                if (pageLimit > practiceTestDto.getPageLimit()) {
                    pageLimit = practiceTestDto.getPageLimit();
                }
            }
        }
        practiceTestDto.setSizePerPage(sizePerPage);
        practiceTestDto.setPageLimit(pageLimit);
    }

    /**
     * Sort list in random order.
     *
     * @param seed
     *            seed
     * @param sortList
     *            list
     */
    public static void sortListRandom(long seed, List<String> sortList) {
        // Sort list in random order
        // long seed = System.nanoTime();
        Collections.shuffle(sortList, new Random(seed));
    }

    /**
     * Paginate RubyWord List
     *
     * @param wordList
     *            word/sentence
     * @param rubyWordList
     *            ruby word/ruby sentence
     * @param showWordList
     *            word/ruby
     * @param practiceTestDto
     *            result
     * @param modeScenaVoca
     *            scenario/vocabulary
     */
    public static void paginateRubyWordList(final List<String> wordList, final List<String> rubyWordList,
            final List<String> showWordList, PracticeTestDto practiceTestDto, final int modeScenaVoca, List<String> element) {
        int sizePerPage = practiceTestDto.getSizePerPage();
        int pageLimit = practiceTestDto.getPageLimit();
        // result list
        List<List<String>> resultList;
        List<List<String>> resultRubyList;
        List<List<String>> resultShowList;
        List<List<String>> resultElement = null;
        // Check mode scenario/vocabulary to get result list
        if (ConstValues.MODE_SCENARIO == modeScenaVoca) {
            resultList = practiceTestDto.getScenarioList();
            resultRubyList = practiceTestDto.getScenarioRubyList();
            resultShowList = practiceTestDto.getScenarioShowList();
            resultElement = practiceTestDto.getElement();
        } else {
            resultList = practiceTestDto.getVocabularyList();
            resultRubyList = practiceTestDto.getVocabularyRubyList();
            resultShowList = practiceTestDto.getVocabularyShowList();
        }
        // Initialize page list on first time
        List<String> pageList = new ArrayList<String>();
        List<String> pageRubyList = new ArrayList<String>();
        List<String> pageShowList = new ArrayList<String>();
        List<String> elements = new ArrayList<String>();
        resultList.add(pageList);
        resultRubyList.add(pageRubyList);
        resultShowList.add(pageShowList);
        if (ConstValues.MODE_SCENARIO == modeScenaVoca) {
            resultElement.add(elements);
        }

        // Check if wordList is available
        if (wordList != null && wordList.size() > 0) {
            int sizeIndex = wordList.size();
            for (int i = 0; i < sizeIndex; i++) {
                String str = wordList.get(i);
                String rubyStr = rubyWordList.get(i);
                String showStr = showWordList.get(i);
                // Add scena/voca into page
                pageList.add(str);
                pageRubyList.add(rubyStr);
                pageShowList.add(showStr);
                if (ConstValues.MODE_SCENARIO == modeScenaVoca) {
                    String e = element.get(i);
                    elements.add(e);
                }
                // Add page scena/voca into result list
                if (pageList.size() == sizePerPage) {
                    if (resultList.size() >= pageLimit) {
                        break;
                    }
                    pageList = new ArrayList<String>();
                    pageRubyList = new ArrayList<String>();
                    pageShowList = new ArrayList<String>();
                    resultList.add(pageList);
                    resultRubyList.add(pageRubyList);
                    resultShowList.add(pageShowList);
                    if (ConstValues.MODE_SCENARIO == modeScenaVoca) {
                        elements = new ArrayList<String>();
                        resultElement.add(elements);
                    }
                }
            }
        }
    }

    /**
     * @param number
     *            limit random
     * @return int
     */
    public static int randomZeroToNumber(int number) {
        return (int) (Math.random() * (number));
    }

    /**
     * Get answer list for mode memory.
     *
     * @param paginateList
     *            list
     * @return list
     */
    public static List<List<List<String>>> getMemoryAnswer(final List<List<String>> paginateList) {
        List<List<List<String>>> result = new ArrayList<List<List<String>>>();
        for (List<String> paginate : paginateList) {
            List<List<String>> pageAnswer = new ArrayList<List<String>>();
            int pageSize = paginate.size();
            for (int i = 0; i < pageSize; i++) {
                // Get all question in one page
                List<String> answer = new ArrayList<String>();
                answer.addAll(paginate);
                if (answer.size() < ConstValues.PageDefault.SIZE_PER_PAGE) {
                    additionList(answer, paginateList);
                }

                // Sort list in random order
                long seed = System.nanoTime();
                Collections.shuffle(answer, new Random(seed));
                pageAnswer.add(answer);
            }
            result.add(pageAnswer);
        }
        return result;
    }

    /**
     * Check if answer list size lester than 10 then add more element
     *
     * @param answer
     * @param paginateList
     */
    private static void additionList(List<String> answer, final List<List<String>> paginateList) {
        for (List<String> page : paginateList) {
            for (String string : page) {
                // Check if answer list is enough then return
                if (answer.size() >= ConstValues.PageDefault.SIZE_PER_PAGE) {
                    return;
                }
                // Add string into list if answer list not exits
                if (!answer.contains(string)) {
                    answer.add(string);
                }
            }
        }
    }

    /**
     * Check if name is "Particle" : true : false
     *
     * @param name
     * @return
     */
    public static boolean checkParticle(String name) {
        if (name.startsWith("Particle")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public static boolean checkVerb(String name) {
        if (!name.equals("is") && !name.equals("go")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public static boolean checkVerbIs(String name) {
        if (!name.equals("is")) {
            return true;
        }
        return false;
    }

    /**
     * Remove duplicate list string
     *
     * @param list
     * @return
     */
    public static <T> List<T> removeDuplicates(Collection<T> list) {

        // Store unique items in result.
        ArrayList<T> result = new ArrayList<T>();

        // Record encountered Strings in HashSet.
        HashSet<T> set = new HashSet<T>();

        // Loop over argument list.
        for (T item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }
}
