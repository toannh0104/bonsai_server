// /////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.io.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Vector;
import java.util.Random;
import java.util.StringTokenizer;

import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_NetworkSubNodeStruct;
import jcall.db.JCALL_PredictionProcess;
import jcall.db.JCALL_Word;
import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;

public class CALL_lexiconStruct
{
    // Defines
    // -------
    static Logger logger = Logger.getLogger(CALL_lexiconStruct.class.getName());

    final static String dictionaryHTMLname = "./Data/html/dictionary.html";

    final static int LEX_NUM_TYPES = 23;
    final static int LEX_MAX_LEVELS = 5;

    // The types, same with JCALL_Lexicon
    static final int LEX_TYPE_UNSPECIFIED = 0;
    static final int LEX_TYPE_NOUN = 1;
    static final int LEX_TYPE_NOUN_PERSONNAME = 11;
    static final int LEX_TYPE_NOUN_AREANAME = 12;
    static final int LEX_TYPE_NOUN_DEFINITIVES = 13;
    static final int LEX_TYPE_NOUN_SURU = 14;
    static final int LEX_TYPE_NOUN_NUMERAL = 15;
    static final int LEX_TYPE_NOUN_QUANTIFIER = 19;
    static final int LEX_TYPE_NOUN_SUFFIX = 16;
    static final int LEX_TYPE_NOUN_PREFIX = 17;
    static final int LEX_TYPE_NOUN_TIME = 18;
    static final int LEX_TYPE_NUMQUANT = 111;
    static final int LEX_TYPE_NOUN_PRONOUN_PERSON = 112;
    // public static final int LEX_TYPE_NOUN_PRONOUN_THING = 113;

    // public static final int LEX_TYPE_NOUN_ADVERB = 19;
    static final int LEX_TYPE_VERB = 2;
    // static final int LEX_TYPE_VERB_VT = 21; //still no
    // static final int LEX_TYPE_VERB_VI = 22; //still no
    static final int LEX_TYPE_ADJECTIVE = 3;
    static final int LEX_TYPE_ADJVERB = 4;
    static final int LEX_TYPE_ADVERB = 5;

    static final int LEX_TYPE_PARTICLE_AUXIL = 6; // auxiliary word
    static final int LEX_TYPE_PARTICLE_AUXILV = 7;

    static final int LEX_TYPE_RENTAI = 8;//
    static final int LEX_TYPE_KANDOU = 9;//

    static final int LEX_TYPE_OTHER = 99;// just for typeinfostruct

    // for complilation of old version
    static final int LEX_TYPE_PARTICLES = 6;
    static final int LEX_TYPE_VERBS = 2;
    static final int LEX_TYPE_ADVERBS = 5;

    public static final int[] LEX_TYPE_TRANSFORM = { 1, 2, 18, 3, 5, 13, 15, 1, 6, 1, 16, 17 };

    public static final int[] LEX_TYPE_INTTRANSFORM = { 0, 1, 11, 12, 13, 14, 15, 16, 17, 18, 19, 2, 3, 4, 5, 6, 7, 8, 9, 111, 112 };

    // String definition of types
    static String[] typeString =
    {
            "UNK",
            "NOUN",
            "PERSONNAME",
            "AREANAME",
            "DEFINITIVES", // PRONOUN_Thing
            "SURUNOUN",
            "NUMERAL",
            "SUFFIX",
            "PREFIX",
            "TIME",
            "QUANTIFIER",
            "VERB",
            // "VERB_VT",
            // "VERB_VI",
            "ADJECTIVE",
            "ADJVERB",
            "ADVERB",
            "PARTICLE_AUXIL",
            "PARTICLE_AUXILV",
            "RENTAI",
            "KANDOU",
            "NUMQUANT",
            "PRONOUN_PERSON"
    };

    static final String typeIconStrings[] =
    {
            "Misc/UNKicon.gif",
            "Misc/NOUNicon.gif",
            "Misc/NOUNicon.gif",
            "Misc/NOUNicon.gif",
            "Misc/DEFINITIVEicon.gif",
            "Misc/NOUNicon.gif",
            "Misc/DIGITicon.gif", // numeral;
            "Misc/SUFFIXicon.gif",
            "Misc/PREFIXicon.gif",
            "Misc/TIMEicon.gif",
            "Misc/DIGITicon.gif", // quantifier;
            "Misc/VERBicon.gif",
            "Misc/ADJECTIVEicon.gif",
            "Misc/ADJECTIVEicon.gif",
            "Misc/ADVERBicon.gif",
            "Misc/PARTICLEicon.gif",
            "Misc/PARTICLEicon.gif",
            "Misc/MISCicon.gif",// rentai
            "Misc/MISCicon.gif",// kandou
            "Misc/DIGITicon.gif", // nq;
            "Misc/DEFINITIVEicon.gif"
    };

    final static int LEX_FORMAT_UNSPECIFIED = -1;
    final static int LEX_FORMAT_KANJI = 0;
    final static int LEX_FORMAT_KANA = 1;
    final static int LEX_FORMAT_ENGLISH = 2;
    final static int LEX_FORMAT_ROMAJI = 3;
    final static int LEX_FORMAT_ID = 4;

    final static int LEX_MODE_LEVELS = 0;
    final static int LEX_MODE_TYPES = 1;
    final static int LEX_MODE_CATGS = 2;

    // Rows for Level Mode
    final static int LEX_LEVEL_ONE = 0;
    final static int LEX_LEVEL_TWO = 1;
    final static int LEX_LEVEL_THREE = 2;
    final static int LEX_LEVEL_FOUR = 3;
    final static int LEX_LEVEL_NONE = 4;

    // Parent
    CALL_database parent;

    // Fields
    // -----

    CALL_typeInfoStruct typeInfo;
    CALL_levelInfoStruct levelInfo;

    Vector nouns; // NOUN,"AREANAME","SURUNOUN",PERSONNAME,"PRONOUN_PERSON"
    // Vector personnames; //PERSONNAME,
    // Vector pronounpersons; //included "watashi"etc
    Vector quantifiers; // "QUANTIFIER",
    Vector verbs; // "VERB","VERB_VT","VERB_VI",
    Vector times; // "TIME",
    Vector adjectives; // "ADJECTIVE",
    Vector adjverbs; // "ADJVERB"
    Vector adverbs; // "ADVERB"
    Vector definitives; // only "kore","sore","are" in type "definitive"
    Vector digits;// "NUMERAL",
    // Vector positions;
    // Vector misc;
    Vector particles; // "PARTICLE_AUXIL","PARTICLE_AUXILV",
    Vector suffixes; // "SUFFIX",
    Vector prefixes;// "PREFIX",
    Vector numquant;// "NUMQUANT",
    Vector others;// "RENTAI","KANDOU",

    Vector<CALL_wordStruct> allWords;

    // Category Arrays
    Vector categoryTable; // Search by group name, then by index
    Vector categoryTableActive; // As above, but only links in active words

    public CALL_lexiconStruct(CALL_database p)
    {
        parent = p;

        // Initialise the data structures
        nouns = new Vector();
        verbs = new Vector();
        times = new Vector();
        adjectives = new Vector();
        adverbs = new Vector();
        definitives = new Vector();
        digits = new Vector();
        // positions = new Vector();
        // misc = new Vector();
        particles = new Vector();
        suffixes = new Vector();

        allWords = new Vector();

        quantifiers = new Vector();
        adjverbs = new Vector();
        prefixes = new Vector();
        numquant = new Vector();
        others = new Vector();

        typeInfo = new CALL_typeInfoStruct();
        levelInfo = new CALL_levelInfoStruct();

        categoryTable = new Vector();
        categoryTableActive = new Vector();
    }

    public boolean load_lexicon(JCALL_Lexicon l)
    {
        JCALL_PredictionProcess pp = null;

        pp = new JCALL_PredictionProcess();
        boolean example = false;
        FileReader in;
        int readstatus = 0;
        int tempInt;
        int currentIndex = -1;

        CALL_wordStruct newWord;
        String readLine, tempString, token;
        StringTokenizer st;
        Vector v = l.getAllWords();
        for (int i = 0; i < v.size(); i++) {
            JCALL_Word tmpWord = (JCALL_Word) v.elementAt(i);
            tempInt = tmpWord.getIntType();

            // iid = i;
            // the index of the vector "allWords"
            // that is "allWords[i]==newWord"
            newWord = new CALL_wordStruct(this, tempInt, i);

            newWord.id = tmpWord.getId();
            tempString = tmpWord.getStrLevel();
            newWord.level = Integer.parseInt(tempString);

            tempString = tmpWord.getStrPicture();
            if (tempString != null) {
                newWord.picture = tmpWord.getStrPicture();

                // Activate based on picture status
                if (tempString.equals("<none>"))
                {
                    // Should have a picture, but doesn't. De-activate
                    newWord.active = false;
                }
                else if ((tempString.equals("<text>")) || (tempString.equals("<n/a>")))
                {
                    // No picture, but usage of text is ok
                    newWord.active = true;
                }
                else
                {
                    // Use picture specified to represent this component
                    // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                    // CALL_debug.DEBUG, "Picture: " + newWord.picture);
                    newWord.active = true;
                }
            }// end if(tempString!=null){

            tempString = tmpWord.getStrKana();
            newWord.kana = tempString;
            // Add it as an alternative
            tempString = tmpWord.getStrAltkana();
            if (tempString != null && tempString.length() > 0) {
                newWord.altKana.addElement(tempString);
                newWord.altKanji.addElement(tmpWord.getStrKanji());
            }

            // Deal with the default synonym words
            tempString = tmpWord.getDSynonymValue();
            if (tempString != null && tempString.length() > 0) {
                // judge if it has multiple synonyms
                StringTokenizer stSynonym = new StringTokenizer(tempString, ",");
                while (stSynonym.hasMoreElements()) {
                    tempString = (String) stSynonym.nextElement();
                    newWord.altKanji.addElement(tempString);
                    // find its kana
                    JCALL_Word synonymWord = l.getWordFmKanji(tempString, tmpWord.getIntType());
                    if (synonymWord != null) {
                        String str = synonymWord.getStrKana();
                        if (str != null && str.length() > 0) {
                            newWord.altKana.addElement(str);
                        } else {
                            System.out.println("error: not find synonym word, kanji: " + tempString + " type: " + tmpWord.getIntType());

                        }
                    }
                }
            }// end if has synonym words

            // Deal with the right accepted alternatives from EP
            // /*
            // *
            if (pp != null) {
                Vector vDW = pp.getAcceptedDWOnlyFmEP(tmpWord);
                if (vDW != null && vDW.size() > 0) {
                    // logger.debug("Word ["+ tmpWord.getStrKanji() +" ]" +
                    // " Have accepted dw [ "+vDW.size()+" ]; Added to old CALL_wordStruct ");
                    for (int j = 0; j < vDW.size(); j++) {
                        JCALL_NetworkSubNodeStruct subnode = (JCALL_NetworkSubNodeStruct) vDW.get(j);
                        String strkana = subnode.getStrKana();
                        String strkanji = subnode.getStrKanji();
                        newWord.altKanji.addElement(strkanji);
                        newWord.altKana.addElement(strkana);
                        // logger.debug("DW["+j+"]: "+strkanji );
                    }
                }
            }
            // */

            tempString = tmpWord.getStrKanji();
            newWord.kanji = tempString;

            tempString = tmpWord.getDEngMeaning();
            if (tempString != null) {
                newWord.english = tempString;
                newWord.genEnglish = tempString;
                // parameter1 for digit, like 5
                if (tmpWord.getIntType() == JCALL_Lexicon.LEX_TYPE_NOUN_NUMERAL) {
                    int p1 = Integer.parseInt(tempString);
                    if (p1 >= 0) {
                        newWord.parameter1 = p1;
                    }
                }

            }
            // parameter1 for verb
            tempString = tmpWord.getStrGroup();
            if (tempString != null && tempString.length() > 0) {
                int p1 = Integer.parseInt(tempString);
                if (p1 > 0) {
                    newWord.parameter1 = p1;
                }
            }
            else {// parameter1 for adjective

                if (tmpWord.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJVERB) {
                    newWord.parameter1 = 2;
                } else if (tmpWord.getIntType() == JCALL_Lexicon.LEX_TYPE_ADJECTIVE) {
                    newWord.parameter1 = 1;
                    // for group==3, we set the group parameter in the word xml
                    // file
                }
            }

            // Add the category name to the word category list
            Vector vec = tmpWord.getVCategories();
            if (vec != null) {
                newWord.categories = vec;
                for (int j = 0; j < vec.size(); j++) {
                    String strj = (String) vec.get(j);
                    // Add the word to the category specific list
                    addToCategory(strj, newWord);
                }

            } // end if

            addWord(newWord);

        }
        // Print summary of lexicon contents (depending of debug level)
        print_debug();

        // Output html version - FOR SETUP PURPOSES ONLY - CJW
        print_html_file();

        return true;

    }

    public static String typeInt2Name(int intType) {
        for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
            int aType = LEX_TYPE_INTTRANSFORM[i];
            if (intType == aType) {
                return typeString[i];
            }
        }
        logger.error("find no respoding type name");
        return typeString[0];
    }

    public static int typeName2Int(String strType) {
        for (int i = 0; i < typeString.length; i++) {
            String aType = typeString[i];
            if (strType.equalsIgnoreCase(aType)) {
                return LEX_TYPE_INTTRANSFORM[i];
            }
        }
        logger.error("find no respoding type name");
        return 0;
    }

    // targetComponentType = type
    public static String getTypeIconString(int targetComponentType)
    {

        for (int i = 0; i < LEX_TYPE_INTTRANSFORM.length; i++) {
            int aType = LEX_TYPE_INTTRANSFORM[i];
            if (targetComponentType == aType) {
                return typeIconStrings[i];
            }
        }
        logger.error("find no respoding type name");
        return typeIconStrings[0];
    }

    public boolean load_lexicon(String filename)
    {
        FileReader in;
        int readstatus = 0;
        int tempInt;
        CALL_wordStruct newWord;
        String readLine, tempString;
        StringTokenizer st;

        // Open file
        try
        {
            in = new FileReader(filename);
            if (in == null)
            {
                // File does not exist
                // //CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                // CALL_debug.ERROR, "Lexicon file not found");
                return false;
            }

        } catch (IOException e)
        {
            // File does not exist
            // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.ERROR,
            // "Problem opening lexicon file");
            return false;
        }

        newWord = null;

        // Read Line by line
        while (readstatus == 0)
        {
            readLine = CALL_io.readString(in);

            if (readLine == null)
            {
                // Reached end of file - stop reading
                readstatus = 1;
            }
            else
            {
                st = new StringTokenizer(readLine);
                tempString = st.nextToken();

                if (tempString != null)
                {
                    if (tempString.startsWith("#"))
                    {
                        // A comment...skip it
                        continue;
                    }
                    else if (tempString.startsWith("-eof"))
                    {
                        // Add any previous word
                        if (newWord != null)
                        {
                            // We have a previous word....add this to lexicon
                            // before moving on!
                            addWord(newWord);
                            newWord = null;
                        }

                        // EOF marker
                        readstatus = 1;
                    }
                    else
                    {
                        if (tempString.startsWith("-type"))
                        {
                            // The type command denotes the start of a new word,
                            // giving it's type (category)
                            if (newWord != null)
                            {
                                // We have a previous word....add this to
                                // lexicon before moving on!
                                addWord(newWord);
                                newWord = null;
                            }

                            // Now get the type of the new word, and create that
                            // word if appropriate
                            tempInt = CALL_io.getNextInt(st);
                            if (tempInt != CALL_io.INVALID_INT)
                            {
                                newWord = new CALL_wordStruct(this, tempInt, allWords.size());
                            }
                        }
                        else if (newWord != null)
                        {
                            // Consider all the variables that can be loaded
                            // into a word
                            if (tempString.equals("-id"))
                            {
                                tempInt = CALL_io.getNextInt(st);
                                if (tempInt != CALL_io.INVALID_INT)
                                {
                                    newWord.id = tempInt;
                                }
                            }
                            else if (tempString.equals("-level"))
                            {
                                tempInt = CALL_io.getNextInt(st);
                                if (tempInt != CALL_io.INVALID_INT)
                                {
                                    newWord.level = tempInt;
                                }
                            }
                            else if (tempString.equals("-parameter1"))
                            {
                                tempInt = CALL_io.getNextInt(st);
                                if (tempInt != CALL_io.INVALID_INT)
                                {
                                    newWord.parameter1 = tempInt;
                                }
                            }
                            else if (tempString.equals("-parameter1"))
                            {
                                tempInt = CALL_io.getNextInt(st);
                                if (tempInt != CALL_io.INVALID_INT)
                                {
                                    newWord.parameter2 = tempInt;
                                }
                            }
                            else if (tempString.equals("-picture"))
                            {
                                tempString = CALL_io.getNextToken(st);
                                if (tempString != null)
                                {
                                    newWord.picture = tempString;

                                    // Activate based on picture status
                                    if (tempString.equals("<none>"))
                                    {
                                        // Should have a picture, but doesn't.
                                        // De-activate
                                        newWord.active = false;
                                    }
                                    else if ((tempString.equals("<text>")) || (tempString.equals("<n/a>")))
                                    {
                                        // No picture, but usage of text is ok
                                        newWord.active = true;
                                    }
                                    else
                                    {
                                        // Use picture specified to represent
                                        // this component
                                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                        // CALL_debug.DEBUG, "Picture: " +
                                        // newWord.picture);
                                        newWord.active = true;
                                    }
                                }
                            }
                            else if (tempString.equals("-kana"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if ((tempString != null) && (!tempString.equals("<none>")))
                                {
                                    if (newWord.kana == null)
                                    {
                                        newWord.kana = tempString;
                                    }
                                    else
                                    {
                                        // Add it as an alternative
                                        newWord.altKana.addElement(tempString);
                                    }
                                }
                            }
                            else if (tempString.equals("-kanji"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if ((tempString != null) && (!tempString.equals("<none>")))
                                {
                                    if (newWord.kanji == null)
                                    {
                                        newWord.kanji = tempString;
                                    }
                                    else
                                    {
                                        // Add it as an alternative
                                        newWord.altKanji.addElement(tempString);
                                    }
                                }
                            }
                            else if (tempString.equals("-english"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if ((tempString != null) && (!tempString.equals("<none>")))
                                {
                                    if (newWord.english == null)
                                    {
                                        newWord.english = tempString;
                                    }
                                    else
                                    {
                                        // Add it as an alternative
                                        newWord.altEnglish.addElement(tempString);
                                    }
                                }
                            }
                            else if (tempString.equals("-genEnglish"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if ((tempString != null) && (!tempString.equals("<none>")))
                                {
                                    newWord.genEnglish = tempString;
                                }
                            }
                            else if (tempString.equals("-category"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if (tempString != null)
                                {
                                    // Add the category name to the word
                                    // category list
                                    newWord.categories.addElement(tempString);

                                    // Add the word to the category specific
                                    // list
                                    addToCategory(tempString, newWord);

                                }
                            }
                            else if (tempString.equals("-match"))
                            {
                                tempString = CALL_io.strRemainder(st);
                                if (tempString != null)
                                {
                                    newWord.matches.addElement(tempString);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Print summary of lexicon contents (depending of debug level)
        print_debug();

        // Output html version - FOR SETUP PURPOSES ONLY - CJW
        print_html_file();

        return true;
    }

    // ----------add by wang
    public CALL_wordStruct getWordByKanji(String wordStr)
    {
        CALL_wordStruct tempWord = null;

        // Get the word
        for (int i = 0; i < allWords.size(); i++)
        {
            tempWord = (CALL_wordStruct) allWords.elementAt(i);
            if (tempWord != null)
            {
                if (tempWord.kanji.equals(wordStr))
                {
                    // Found
                    break;
                }
                else
                {
                    tempWord = null;
                }

            }
        }

        return tempWord;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    // Add the new word to the lexicon (both the all words list and the type
    // dependent one
    // ///////////////////////////////////////////////////////////////////////////////////////

    public void addWord(CALL_wordStruct newWord)
    {
        if (newWord != null)
        {
            // Add to all words list
            allWords.addElement(newWord);

            // Then add to type specific list
            switch (newWord.type)
            {
                case LEX_TYPE_NOUN:
                    // Noun
                    nouns.addElement(newWord);
                    typeInfo.num_n++;
                    if (newWord.active)
                    {
                        typeInfo.num_n_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_AREANAME:
                    // Noun
                    nouns.addElement(newWord);
                    typeInfo.num_n++;
                    if (newWord.active)
                    {
                        typeInfo.num_n_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_PERSONNAME:
                    // Noun
                    nouns.addElement(newWord);
                    typeInfo.num_n++;
                    if (newWord.active)
                    {
                        typeInfo.num_n_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_PRONOUN_PERSON:
                    // Noun
                    nouns.addElement(newWord);
                    typeInfo.num_n++;
                    if (newWord.active)
                    {
                        typeInfo.num_n_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_SURU:
                    // Misc
                    nouns.addElement(newWord);
                    typeInfo.num_n++;
                    if (newWord.active)
                    {
                        typeInfo.num_n_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_DEFINITIVES:
                    // Noun
                    definitives.addElement(newWord);
                    typeInfo.num_def++;
                    if (newWord.active)
                    {
                        typeInfo.num_def_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_NUMERAL:
                    // Digits
                    digits.addElement(newWord);
                    typeInfo.num_digits++;
                    if (newWord.active)
                    {
                        typeInfo.num_digits_A++;
                    }
                    break;
                case LEX_TYPE_NUMQUANT:
                    // Digits
                    digits.addElement(newWord);
                    typeInfo.num_digits++;
                    if (newWord.active)
                    {
                        typeInfo.num_digits_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_TIME:
                    // Time word
                    times.addElement(newWord);
                    typeInfo.num_times++;
                    if (newWord.active)
                    {
                        typeInfo.num_times_A++;
                    }
                    break;
                case LEX_TYPE_VERB:
                    // Verb
                    verbs.addElement(newWord);
                    typeInfo.num_v++;
                    if (newWord.active)
                    {
                        typeInfo.num_v_A++;
                    }
                    break;

                case LEX_TYPE_ADJECTIVE:
                    // Adjective
                    adjectives.addElement(newWord);
                    typeInfo.num_adj++;
                    if (newWord.active)
                    {
                        typeInfo.num_adj_A++;
                    }
                    break;
                case LEX_TYPE_ADJVERB:
                    // Adjective
                    adjverbs.addElement(newWord);
                    typeInfo.num_adjverbs++;
                    if (newWord.active)
                    {
                        typeInfo.num_adjverbs_A++;
                    }
                    break;
                case LEX_TYPE_ADVERB:
                    // Adverb
                    adverbs.addElement(newWord);
                    typeInfo.num_adv++;
                    if (newWord.active)
                    {
                        typeInfo.num_adv_A++;
                    }

                    break;
                // case LEX_TYPE_POSITIONS:
                // // Digits
                // positions.addElement(newWord);
                // typeInfo.num_pos++;
                // if(newWord.active)
                // {
                // typeInfo.num_pos_A++;
                // }
                // break;
                case LEX_TYPE_PARTICLE_AUXIL:
                    // Misc
                    particles.addElement(newWord);
                    typeInfo.num_particles++;
                    if (newWord.active)
                    {
                        typeInfo.num_particles_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_SUFFIX:
                    // Misc
                    suffixes.addElement(newWord);
                    typeInfo.num_suffixes++;
                    if (newWord.active)
                    {
                        typeInfo.num_suffixes_A++;
                    }
                    break;
                case LEX_TYPE_NOUN_PREFIX:
                    // Misc
                    prefixes.addElement(newWord);
                    typeInfo.num_prefixes++;
                    if (newWord.active)
                    {
                        typeInfo.num_prefixes_A++;
                    }
                    break;

                case LEX_TYPE_NOUN_QUANTIFIER:
                    // Misc
                    quantifiers.addElement(newWord);
                    typeInfo.num_quantifiers++;
                    if (newWord.active)
                    {
                        typeInfo.num_quantifiers_A++;
                    }
                    break;
                case LEX_TYPE_KANDOU:
                    // Misc
                    others.addElement(newWord);
                    typeInfo.num_others++;
                    if (newWord.active)
                    {
                        typeInfo.num_others_A++;
                    }
                    break;
                case LEX_TYPE_RENTAI:
                    // Misc
                    others.addElement(newWord);
                    typeInfo.num_others++;
                    if (newWord.active)
                    {
                        typeInfo.num_others_A++;
                    }
                    break;
                // case LEX_TYPE_MISC:
                // // Misc
                // misc.addElement(newWord);
                // typeInfo.num_misc++;
                // if(newWord.active)
                // {
                // typeInfo.num_misc_A++;
                // }
                // break;
                default:
                    // Display a warning - invalid word type
                    // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                    // CALL_debug.WARN,
                    // "Error loading word - invalid word type [" + newWord.type
                    // + "]");
                    break;
            }

            // Finally, update level information
            if (newWord != null)
            {
                if ((newWord.level >= 0) && (newWord.level < LEX_MAX_LEVELS))
                {
                    // Add to levels count
                    int tempInt = levelInfo.num_level[newWord.level].intValue();
                    levelInfo.num_level[(newWord.level)] = (tempInt + 1);

                    // If active, add to the active count too
                    if (newWord.active)
                    {
                        tempInt = levelInfo.num_level_A[newWord.level].intValue();
                        levelInfo.num_level_A[(newWord.level)] = (tempInt + 1);
                    }
                }
            }
        }
    }

    public int addToCategory(String cat, CALL_wordStruct word)
    {
        int index = -1;
        CALL_lexiconCategoryStruct category = null;

        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
        // "Finding category [" + cat + "] to add new word...");

        // Find category
        for (int i = 0; i < categoryTable.size(); i++)
        {
            category = (CALL_lexiconCategoryStruct) categoryTable.elementAt(i);
            // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
            // "Looking at category [" + category.name + "]");
            if (category != null)
            {
                if (category.name.equals(cat))
                {
                    index = i;
                    break;
                }
            }
        }

        // Insert
        if (index != -1)
        {
            if (category != null)
            {
                category.add_word(word);
            }
        }
        else
        {
            // Make new category, then add the word
            category = new CALL_lexiconCategoryStruct(cat);
            categoryTable.add(category);
            category.add_word(word);
        }

        return index;
    }

    /**
     * get word from lexicon
     * @param group
     * @param callJVoca
     * @return
     */
    public Vector<CALL_wordStruct> pickWordFromGroupGHS(String group, List<CallJVocabularyDto> callJVoca, boolean flag)
    {
        int index = -1;
        CALL_wordStruct returnWord = null;
        CALL_lexiconCategoryStruct category = null;
        Vector<CALL_wordStruct> wordStructs = new Vector<CALL_wordStruct>();

        for (int i = 0; i < categoryTable.size(); i++)
        {
            category = (CALL_lexiconCategoryStruct) categoryTable.elementAt(i);
            if (category.name.equals(group))
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            for (int i = 0; i < category.words.size(); i++) {
                returnWord = (CALL_wordStruct) category.words.elementAt(i);
                // If flag = true is get word follow calljVoca
                // else flag = false is get all word
                if(flag){
                    for (int j = 0; j < callJVoca.size(); j++) {
                        if (returnWord != null)
                        {
                            if (returnWord.english != null && returnWord.english.length() > 0) {
                                if (returnWord.english.equals(callJVoca.get(j).getVocabularyEnglishName()))
                                {
                                    wordStructs.add(returnWord);
                                    continue;
                                }
                            }

                        }
                    }
                }else {
                    wordStructs.add(returnWord);
                }
            }
        }

        return wordStructs;
    }

    public CALL_wordStruct pickWordFromGroup(String group)
    {
        int index = -1;
        CALL_wordStruct returnWord = null;
        CALL_lexiconCategoryStruct category = null;

        for (int i = 0; i < categoryTable.size(); i++)
        {
            category = (CALL_lexiconCategoryStruct) categoryTable.elementAt(i);
            if (category.name.equals(group))
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            // We found a matching category group
            returnWord = (CALL_wordStruct) category.words.elementAt(0);
        }
        return returnWord;
    }

    public Vector getWordsInGroup(String group)
    {
        int index = -1;
        CALL_lexiconCategoryStruct category = null;
        Vector returnV = null;

        for (int i = 0; i < categoryTable.size(); i++)
        {
            category = (CALL_lexiconCategoryStruct) categoryTable.elementAt(i);
            if (category.name.equals(group))
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            // We found a matching category group
            returnV = category.words;
        }

        return returnV;
    }

    // First check english,then kanji,last kana

    public CALL_wordStruct getWordGHS(String wordStr,List<CallJVocabularyDto> callJVoca)
    {
        // logger.debug("enter getWord: "+ wordStr);
        wordStr = wordStr.trim();
        CALL_wordStruct wResult = null;
        boolean isFind = false;
        // Get the word
        // logger.debug("allWords.size(): "+ allWords.size());

        for (int i = 0; i < allWords.size(); i++)
        {
            CALL_wordStruct tempWord = (CALL_wordStruct) allWords.elementAt(i);
            if (tempWord != null)
            {

                // //logger.debug("word["+i+"]: "+ tempWord.kana
                // +" "+tempWord.getKanji() +" "+tempWord.getEnglish());

                if (tempWord.english != null && tempWord.english.length() > 0) {
                    if (tempWord.english.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        for(CallJVocabularyDto vocabularyDto : callJVoca){
                            if(vocabularyDto.getVocabularyEnglishName().equals(tempWord.english)){
                                wResult = tempWord;
                                isFind = true;
                                break;
                            }
                        }
                        // If isFind = true is find word in calljVoca
                        if(isFind){
                            break;
                        }
                    }
                }

                if (tempWord.kanji != null && tempWord.kanji.length() > 0) {
                    if (tempWord.kanji.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        for(CallJVocabularyDto vocabularyDto : callJVoca){
                            if(vocabularyDto.getVocabularyEnglishName().equals(tempWord.english)){
                                wResult = tempWord;
                                isFind = true;
                                break;
                            }
                        }
                        // If isFind = true is find word in calljVoca
                        if(isFind){
                            break;
                        }
                    }
                }

                if (tempWord.kana != null && tempWord.kana.length() > 0) {
                    if (tempWord.kana.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        for(CallJVocabularyDto vocabularyDto : callJVoca){
                            if(vocabularyDto.getVocabularyEnglishName().equals(tempWord.english)){
                                wResult = tempWord;
                                isFind = true;
                                break;
                            }
                        }
                        // If isFind = true is find word in calljVoca
                        if(isFind){
                            break;
                        }
                    }
                }

            }
        }
        return wResult;
    }


    public CALL_wordStruct getWord(String wordStr)
    {
        // logger.debug("enter getWord: "+ wordStr);
        wordStr = wordStr.trim();
        CALL_wordStruct wResult = null;

        // Get the word
        // logger.debug("allWords.size(): "+ allWords.size());

        for (int i = 0; i < allWords.size(); i++)
        {
            CALL_wordStruct tempWord = (CALL_wordStruct) allWords.elementAt(i);
            if (tempWord != null)
            {

                // //logger.debug("word["+i+"]: "+ tempWord.kana
                // +" "+tempWord.getKanji() +" "+tempWord.getEnglish());

                if (tempWord.english != null && tempWord.english.length() > 0) {
                    if (tempWord.english.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        wResult = tempWord;
                        break;
                    }
                }

                if (tempWord.kanji != null && tempWord.kanji.length() > 0) {
                    if (tempWord.kanji.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        wResult = tempWord;
                        break;
                    }
                }

                if (tempWord.kana != null && tempWord.kana.length() > 0) {
                    if (tempWord.kana.equals(wordStr))
                    {
                        // Found logger.debug("find it ");
                        wResult = tempWord;
                        break;
                    }
                }

            }
        }
        return wResult;
    }


    public CALL_wordStruct getWord(int id)
    {
        CALL_wordStruct tempWord = null;

        // Get the original word
        if ((id < allWords.size()) && (id >= 0))
        {
            tempWord = (CALL_wordStruct) allWords.elementAt(id);
            // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
            // "Returning word with id " + id +", [" + tempWord.english + "]");
        }

        return tempWord;
    }

    /*
     * id is the index of the vector "allWords"
     */
    public Vector getWords(int id)
    {
        CALL_wordStruct tempWord;
        Vector returnVector;

        returnVector = new Vector();

        // Get the original word
        if ((id < allWords.size()) && (id >= 0))
        {
            tempWord = (CALL_wordStruct) allWords.elementAt(id);
            returnVector.addElement(tempWord);

            // Add any alternatives (Not implemented yet, CJW)

            // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
            // "Returning word with id " + id +", [" + tempWord.english + "]");
            // logger.debug("Returning word with id " + id +", [" +
            // tempWord.english + "]");

        }

        return returnVector;
    }

    // Group: String representing the semantical group. NULL if any group is ok
    // Type: Integer represeting group to search. Verbs, nouns etc. -1 means
    // search all!
    // Format: Format of search string. Kanji, Kana, English. -1 means attempt
    // to match all!
    // searchStr: The string representing the word that we are trying to find
    // ======================================================================================
    public Vector getWords(String group, int type, int format, String searchStr)
    {
        Vector returnVector;
        Vector searchVector;
        CALL_wordStruct tempWord;
        boolean found = false;
        int i;

        returnVector = new Vector();

        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
        // "Looking for word [" + searchStr + "], Format [" + format +
        // "], Type [" + type + "]");

        switch (type)
        {
            case LEX_TYPE_UNSPECIFIED:
                searchVector = allWords;
                break;

            case LEX_TYPE_NOUN:
                // Noun
                searchVector = nouns;
                break;
            case LEX_TYPE_NOUN_AREANAME:
                // Noun
                searchVector = nouns;
                break;
            case LEX_TYPE_NOUN_PERSONNAME:
                // Noun
                searchVector = nouns;
                break;
            case LEX_TYPE_NOUN_PRONOUN_PERSON:
                // Noun
                searchVector = nouns;
                break;
            case LEX_TYPE_NOUN_SURU:
                // Misc
                searchVector = nouns;
                break;
            case LEX_TYPE_NOUN_DEFINITIVES:
                // Noun
                searchVector = definitives;
                break;
            case LEX_TYPE_NOUN_NUMERAL:
                // Digits
                searchVector = digits;
                break;
            case LEX_TYPE_NOUN_TIME:
                // Time word
                searchVector = times;
                break;
            case LEX_TYPE_VERB:
                // Verb
                searchVector = verbs;
                break;

            case LEX_TYPE_ADJECTIVE:
                // Adjective
                searchVector = adjectives;
                break;
            case LEX_TYPE_ADJVERB:
                // Adjective
                searchVector = adjverbs;
                break;
            case LEX_TYPE_ADVERB:
                // Adverb
                searchVector = adverbs;
                break;
            case LEX_TYPE_PARTICLE_AUXIL:
                // Misc
                searchVector = particles;
                break;
            case LEX_TYPE_NOUN_SUFFIX:
                // Misc
                searchVector = suffixes;
                break;
            case LEX_TYPE_NOUN_PREFIX:
                // Misc
                searchVector = prefixes;
                break;

            case LEX_TYPE_NOUN_QUANTIFIER:
                // Misc
                searchVector = quantifiers;
                break;
            case LEX_TYPE_KANDOU:
                // Misc
                searchVector = others;
                break;
            case LEX_TYPE_RENTAI:
                // Misc
                searchVector = others;
                break;

            default:
                // Problem
                searchVector = null;
                break;
        }
        if (searchVector != null)
        {
            if (format == LEX_FORMAT_UNSPECIFIED)
            {

                // First, search through all the words using KANJI as a key.
                // We do this in stages to give words with different kanji but
                // matching hirigana a chance
                // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
                // "Word search vector size " + searchVector.size());

                for (i = 0; i < searchVector.size(); i++)
                {
                    tempWord = (CALL_wordStruct) searchVector.elementAt(i);
                    if (tempWord != null)
                    {
                        // First Kanji
                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                        // CALL_debug.DEBUG, "Looking at word [" +
                        // tempWord.kanji + "]");
                        if (searchStr.equals(tempWord.kanji))
                        {
                            if ((group == null) || (tempWord.isInCategory(group)))
                            {
                                // Got one!
                                // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                // CALL_debug.DEBUG, "Looking at word [" +
                                // tempWord.kanji + "]");
                                // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                // CALL_debug.DEBUG,
                                // "Found word in Kanji form!");
                                returnVector.addElement(tempWord);
                                found = true;
                            }
                        }

                        // Then Kana
                        if (!found)
                        {
                            // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                            // CALL_debug.DEBUG, "Looking at word [" +
                            // tempWord.kana + "]");
                            if (searchStr.equals(tempWord.kana))
                            {
                                if ((group == null) || (tempWord.isInCategory(group)))
                                {
                                    // Got one!
                                    // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                    // CALL_debug.DEBUG,
                                    // "Found word in Kana form!");
                                    returnVector.addElement(tempWord);
                                    found = true;
                                }
                            }
                        }

                        // Then Romaji
                        if (!found)
                        {
                            // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                            // CALL_debug.DEBUG, "Looking at word [" +
                            // tempWord.kana + "]");
                            if (searchStr.equals(CALL_io.strKanaToRomaji(tempWord.kana)))
                            {
                                if ((group == null) || (tempWord.isInCategory(group)))
                                {
                                    // Got one!
                                    // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                    // CALL_debug.DEBUG,
                                    // "Found word in Romaji form!");
                                    returnVector.addElement(tempWord);
                                    found = true;
                                }
                            }
                        }

                        // Finally English
                        if (!found)
                        {
                            // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                            // CALL_debug.DEBUG, "Looking at word [" +
                            // tempWord.english + "]");
                            if (searchStr.equals(tempWord.english))
                            {
                                if ((group == null) || (tempWord.isInCategory(group)))
                                {
                                    // Got one!
                                    // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                    // CALL_debug.DEBUG,
                                    // "Found word in English form!");
                                    returnVector.addElement(tempWord);
                                    found = true;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                for (i = 0; i < searchVector.size(); i++)
                {
                    tempWord = (CALL_wordStruct) searchVector.elementAt(i);
                    if (tempWord != null)
                    {
                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                        // CALL_debug.DEBUG, "Looking at word [" + tempWord.kana
                        // + "]");

                        switch (format)
                        {
                            case LEX_FORMAT_KANJI:
                                if (searchStr.equals(tempWord.kanji))
                                {
                                    if ((group == null) || (tempWord.isInCategory(group)))
                                    {
                                        // Got one!
                                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                        // CALL_debug.DEBUG, "Found word!");
                                        returnVector.addElement(tempWord);
                                    }
                                }
                                break;

                            case LEX_FORMAT_KANA:
                                if (searchStr.equals(tempWord.kana))
                                {
                                    if ((group == null) || (tempWord.isInCategory(group)))
                                    {
                                        // Got one!
                                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                        // CALL_debug.DEBUG, "Found word!");
                                        returnVector.addElement(tempWord);
                                    }
                                }
                                break;

                            case LEX_FORMAT_ROMAJI:
                                if (searchStr.equals(CALL_io.strKanaToRomaji(tempWord.kana)))
                                {
                                    if ((group == null) || (tempWord.isInCategory(group)))
                                    {
                                        // Got one!
                                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                        // CALL_debug.DEBUG, "Found word!");
                                        returnVector.addElement(tempWord);
                                    }
                                }
                                break;

                            case LEX_FORMAT_ENGLISH:
                                if (searchStr.equals(tempWord.english))
                                {
                                    if ((group == null) || (tempWord.isInCategory(group)))
                                    {
                                        // Got one!
                                        // CALL_debug.printlog(CALL_debug.MOD_LEXICON,
                                        // CALL_debug.DEBUG, "Found word!");
                                        returnVector.addElement(tempWord);
                                    }
                                }
                                break;

                            default:
                                // Problem
                                break;
                        }
                    }
                }
            }
        }

        if (returnVector.size() < 1)
        {
            // Don't return an empty vector
            returnVector = null;
        }

        return returnVector;
    }

    public boolean wordRestrictionMatch(CALL_conceptInstanceStruct instance, String lString, String rString, String oString)
    {
        Vector tempV;
        CALL_wordStruct tempWord;
        boolean rc = false;
        int rhID, lhID = -1;
        String lhString = lString;
        String rhString = rString;
        String operatorString = oString;

        if ((lhString == null) || (rhString == null) || (operatorString == null))
        {
            // Incomplete data
            return false;
        }

        if (lhString.startsWith("["))
        {
            // Resolve
            lhID = instance.getWordID(lhString);
            lhString = instance.getDataString(lhString);
        }
        if (rhString.startsWith("["))
        {
            rhID = instance.getWordID(rhString);
            rhString = instance.getDataString(rhString);
        }

        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.DEBUG,
        // "Resolving [" + lhString + " " + operatorString + " " + rhString +
        // "]");

        if (operatorString.equals("=="))
        {
            if (lhString.equals(rhString))
            {
                rc = true;
            }
        }
        else if (operatorString.equals("!="))
        {
            if (!lhString.equals(rhString))
            {
                rc = true;
            }
        }
        else if (operatorString.equals("&&"))
        {
            if ((lhString.equals("true")) && (rhString.equals("true")))
            {
                rc = true;
            }
        }
        else if (operatorString.equals("||"))
        {
            if ((lhString.equals("true")) || (rhString.equals("true")))
            {
                rc = true;
            }
        }
        else if (operatorString.equals("=group="))
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Restriction: Is " + lhString + " in group " + rhString);
            // Does the word represented by the string fall into the specified
            // group?
            if (lhID != -1)
            {
                // Get the word(s) from the lexicon
                tempV = getWords(lhID);

                // Now for the (first) word, check it's groups with that
                // specified
                tempWord = (CALL_wordStruct) tempV.elementAt(0);
                if (tempWord != null)
                {
                    if (tempWord.isInCategory(rhString))
                    {
                        rc = true;
                    }
                }
            }
        }
        else if (operatorString.equals("!group="))
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Restriction: Is " + lhString + " in group " + rhString);
            // Does the word represented by the string fall into the specified
            // group?
            if (lhID != -1)
            {
                // Get the word(s) from the lexicon
                tempV = getWords(lhID);

                // Now for the (first) word, check it's groups with that
                // specified
                tempWord = (CALL_wordStruct) tempV.elementAt(0);
                if (tempWord != null)
                {
                    if (!tempWord.isInCategory(rhString))
                    {
                        rc = true;
                    }
                }
            }
        }
        return rc;
    }

    // ///////////////////////////////////////////////////////////////////////
    // This function is just used to update lexicon format when needed
    // ///////////////////////////////////////////////////////////////////////
    public boolean save_lexicon()
    {
        CALL_wordStruct theWord;
        String filename;
        FileWriter out;
        PrintWriter outP;

        // Open file

        // Open file
        filename = new String("./Data/lexicon.txt");
        try
        {
            out = new FileWriter(filename);
            if (out == null)
            {
                // File does not exist
                // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.ERROR,
                // "Failed to write lexicon file");
                return false;
            }

        } catch (IOException e)
        {
            // File does not exist
            // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.ERROR,
            // "Failed to write lexicon file");
            return false;
        }

        outP = new PrintWriter(out);

        for (int i = 0; i < allWords.size(); i++)
        {
            theWord = (CALL_wordStruct) allWords.elementAt(i);
            if (theWord != null)
            {
                theWord.write(outP);
            }
        }

        // Now close the file
        outP.println("-eof");
        outP.println("#");

        try
        {
            out.close();
            outP.close();
        } catch (IOException e)
        {
            // Ignore for now
        }

        return true;
    }

    public void print_html_file()
    {
        boolean changed = true;

        FileWriter htmlFile = null;
        PrintWriter out = null;
        CALL_wordStruct currentWord;
        CALL_wordStruct nextWord;
        Vector sortedVector;

        // Create output file
        try
        {
            htmlFile = new FileWriter(dictionaryHTMLname, false);
            out = new PrintWriter(htmlFile);
        } catch (IOException e)
        {
        }

        if (out != null)
        {

            // Create a vector to store words for sorting
            sortedVector = (Vector) allWords.clone();

            // Bubble sortage!
            while (changed)
            {
                changed = false;
                for (int i = 0; i < (sortedVector.size() - 1); i++)
                {
                    currentWord = (CALL_wordStruct) sortedVector.elementAt(i);
                    nextWord = (CALL_wordStruct) sortedVector.elementAt(i + 1);
                    if ((currentWord.english != null) && (nextWord.english != null))
                    {
                        if (currentWord.english.compareTo(nextWord.english) > 0)
                        {
                            // Swap the strings
                            sortedVector.setElementAt(nextWord, i);
                            sortedVector.setElementAt(currentWord, i + 1);
                            changed = true;
                        }
                    }
                }
            }

            // Print the table start information
            out.println("<CENTER>");
            out.println("<table width = 90% border=1>");
            out.println("<tr bgcolor=202090>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Image</FONT></td>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Eng</FONT></td>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Rom</FONT></td>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Kana</FONT></td>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Kanji</FONT></td>");
            out.println("<td><FONT size=5 COLOR=FFFFFF>Level</FONT></td>");
            out.println("</tr>");

            // Now print out the html for each word
            for (int j = 0; j < sortedVector.size(); j++)
            {
                currentWord = (CALL_wordStruct) sortedVector.elementAt(j);
                out.println("<tr>");

                // Link to image
                if ((currentWord.picture == null) || (currentWord.picture.equals("<text>")) || (currentWord.picture.equals("<none>")) || (currentWord.picture.equals("<n/a>")))
                {
                    // No image available
                    out.println("<td width=100>No Image</td>");
                }
                else
                {
                    // Path to the image
                    out.println("<td width=100><A HREF=\"../../Resource/Images" + currentWord.picture + "\">See Image</A></td>");
                }

                // Rest of information
                out.println("<td>" + currentWord.english + "</td>");
                out.println("<td>" + CALL_io.strKanaToRomaji(currentWord.kana) + "</td>");
                out.println("<td>" + currentWord.kana + "</td>");
                if (currentWord.kanji != null)
                {
                    out.println("<td>" + currentWord.kanji + "</td>");
                }
                else
                {
                    out.println("<td>" + currentWord.kana + "</td>");
                }

                out.println("<td>" + currentWord.level + "</td>");
                out.println("</tr>");
            }

            // Print the table close
            out.println("</table>");
            out.println("</CENTER>");

        }

        // Close the file handles
        if (out != null)
        {
            out.close();
        }

        if (htmlFile != null)
        {
            try
            {
                htmlFile.close();
            } catch (IOException e)
            {
                // Ignore for now
            }
        }
    }

    public void print_debug()
    {
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Nouns [" + typeInfo.num_n + " of " + typeInfo.num_n_A + "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Verbs [" + typeInfo.num_v + " of " + typeInfo.num_v_A + "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Adjectives [" + typeInfo.num_adj + " of " + typeInfo.num_adj_A +
        // "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Adverbs [" + typeInfo.num_adv + " of " + typeInfo.num_adv_A + "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Definitives [" + typeInfo.num_def + " of " + typeInfo.num_def_A +
        // "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Times [" + typeInfo.num_times + " of " + typeInfo.num_times_A +
        // "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Digits [" + typeInfo.num_digits + " of " + typeInfo.num_digits_A +
        // "]");
        // //CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Position [" + typeInfo.num_pos + " of " + typeInfo.num_pos_A + "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Particles [" + typeInfo.num_particles + " of " +
        // typeInfo.num_particles_A + "]");
        // CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Postfixes [" + typeInfo.num_suffixes + " of " +
        // typeInfo.num_suffixes_A + "]");
        // //CALL_debug.printlog(CALL_debug.MOD_LEXICON, CALL_debug.INFO,
        // "Misc [" + typeInfo.num_misc + " of " + typeInfo.num_misc_A + "]");
    }

    public Vector getAllWords() {
        return allWords;
    }

    public void setAllWords(Vector allWords) {
        this.allWords = allWords;
    }

}