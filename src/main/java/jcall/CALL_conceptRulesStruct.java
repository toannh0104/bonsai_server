// /////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components
// that may be associated with it.
//

package jcall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.gmo.rs.ghs.dto.CallJVocabularyDto;

import org.apache.log4j.Logger;

public class CALL_conceptRulesStruct
{

    static Logger logger = Logger.getLogger(CALL_conceptRulesStruct.class.getName());

    // Defines
    private static final int STATE_INITIAL = 0;
    private static final int STATE_FRAME = 1;
    private static final int STATE_SLOT = 2;
    private static final int STATE_RESTRICTION = 3;
    private static final int STATE_END = 4;
    private static final int STATE_ERROR = 5;
    private LinkedHashMap<String, Vector<CALL_conceptInstanceStruct>> mapInstance = new LinkedHashMap<String, Vector<CALL_conceptInstanceStruct>>();
    private boolean flagRecursively = false;
    // Fields
    Hashtable frames; // A hashed list of CALL_conceptFrameStruct- sorted based
                      // on frame name

    Vector frameNames; // A list of all concept names stored - for debugging
                       // purposes really.

    CALL_database db;

    public CALL_conceptRulesStruct(CALL_database database)
    {
        db = database;

        frameNames = new Vector(); // A list of all the concepts stored
        frames = new Hashtable(); // The hash table of the concepts themselves

        // 2011.03.29 T.Tajima add
        logger.setLevel(org.apache.log4j.Level.WARN);
    }

    // Load the concept defintions
    public boolean load_concepts(String filename)
    {
        logger.debug("enter load_concepts");

        boolean rc = true;
        boolean done = false;
        FileReader in;

        // Read State
        // 0: Initial
        // 1: Frame level
        // 2: Slot level
        // 3: Restriction level
        // 4: Finished
        // 5: Error
        int readState = STATE_INITIAL;
        int fillerType;
        double tempDbl;

        String commandString[];
        String tempString;
        String readLine;
        StringTokenizer st;

        CALL_conceptFrameStruct newFrame = null;
        CALL_conceptSlotStruct newSlot = null;
        CALL_conceptSlotFillerStruct newSlotFiller = null;
        CALL_conceptSlotRestrictionStruct newRestriction = null;

        // Open file
        try
        {
            InputStream inputStream = new FileInputStream(filename);
            Reader reader = new InputStreamReader(inputStream, "Shift-JIS");
            BufferedReader bufferedReader = new BufferedReader(reader);

            while (readState < STATE_END) {

                readLine = bufferedReader.readLine().trim();

                if (readLine == null)
                {
                    // Prematurely reached end of file - stop reading
                    readState = STATE_ERROR;
                    break;
                }
                else if (readLine.startsWith("#"))
                {
                    // A comment...skip
                    continue;
                }
                else
                {
                    // We have some valid text, so parse it
                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                    // CALL_debug.DEBUG, "Parsing line [" + readLine + "]");

                    // logger.debug("Parsing line [" + readLine + "]");

                    if (readLine.startsWith("-eof"))
                    {
                        // EOF
                        readState = STATE_END;
                    }

                    // Otherwise, refer to the state machine
                    done = false;
                    tempString = readLine.trim();
                    // Extract a command in <brackets>, return that string
                    // (without the brackets), as well as any remaining string
                    commandString = CALL_io.extractCommand(tempString);

                    switch (readState)
                    {
                        case STATE_INITIAL:

                            if (commandString[0] != null)
                            {
                                // We have a command of some sort, so parse it

                                st = new StringTokenizer(commandString[0]);
                                tempString = CALL_io.getNextToken(st);

                                if (tempString.startsWith("frame"))
                                {
                                    if (st.hasMoreTokens())
                                    {
                                        tempString = CALL_io.strRemainder(st);
                                        newFrame = new CALL_conceptFrameStruct(tempString);
                                        readState = STATE_FRAME;
                                    }
                                }

                            }

                            break;

                        case STATE_FRAME:

                            if (commandString[0] != null)
                            {
                                // We have a command of some sort, so parse it

                                st = new StringTokenizer(commandString[0]);
                                tempString = CALL_io.getNextToken(st);

                                if (tempString.startsWith("slot"))
                                {
                                    // Adding a new slot to the frame

                                    if (st.hasMoreTokens())
                                    {
                                        tempString = CALL_io.getNextToken(st);
                                        newSlot = new CALL_conceptSlotStruct(tempString);

                                        if (st.hasMoreTokens())
                                        {
                                            // The slot has a weight associated
                                            // with it
                                            tempString = CALL_io.strRemainder(st);
                                            tempDbl = CALL_io.str2double(tempString);
                                            if (tempDbl != CALL_io.INVALID_DBL)
                                            {
                                                newSlot.likelihood = tempDbl;
                                            }
                                        }

                                        readState = STATE_SLOT;
                                    }
                                }
                                else if (tempString.startsWith("/frame"))
                                {
                                    // End of the frame - store the concept (and
                                    // name in the namelist)
                                    frameNames.addElement(newFrame.name);
                                    frames.put(newFrame.name, newFrame);
                                    readState = STATE_INITIAL;
                                }
                            }

                            break;

                        case STATE_SLOT:
                            if (commandString[0] != null)
                            {
                                logger.debug("This line contain commandString[0]");
                                // We have a command of some sort, so parse it
                                st = new StringTokenizer(commandString[0]);

                                tempString = CALL_io.getNextToken(st);

                                if (tempString.startsWith("/slot"))
                                {
                                    // Add slot to the frame
                                    newFrame.slots.addElement(newSlot);
                                    readState = STATE_FRAME;
                                }
                                else if (tempString.equals("restriction"))
                                {
                                    // We have a restriction
                                    tempString = CALL_io.strRemainder(st);
                                    if (tempString != null)
                                    {
                                        newRestriction = new CALL_conceptSlotRestrictionStruct(tempString);
                                        readState = STATE_RESTRICTION;
                                    }
                                }
                            }
                            else if (commandString[1] != null)
                            {
                                st = new StringTokenizer(commandString[1]);
                                tempString = CALL_io.getNextToken(st);

                                logger.debug("First token of commandString: " + tempString);

                                if ((tempString.startsWith("[")) && (tempString.endsWith("]")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP;
                                    tempString = tempString.substring(1, tempString.length() - 1);

                                }
                                else if ((tempString.startsWith("{")) && (tempString.endsWith("}")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT;
                                    tempString = tempString.substring(1, tempString.length() - 1);

                                }
                                else if ((tempString.startsWith("(")) && (tempString.endsWith(")")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_OTHER;
                                    tempString = tempString.substring(1, tempString.length() - 1);
                                }
                                else
                                {
                                    // It's a plain word reference
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD;
                                }

                                newSlotFiller = new CALL_conceptSlotFillerStruct(tempString);
                                newSlotFiller.type = fillerType;

                                if (st.hasMoreTokens())
                                {
                                    // The weight associated with the slot
                                    // filler
                                    tempString = CALL_io.strRemainder(st);
                                    tempDbl = CALL_io.str2double(tempString);
                                    if (tempDbl != CALL_io.INVALID_DBL)
                                    {
                                        newSlotFiller.weight = tempDbl;
                                        newSlot.fillerWeightTotal += tempDbl;
                                    }
                                }

                                // Now add this slot filler to to the slot
                                newSlot.fillers.addElement(newSlotFiller);
                            }

                            break;

                        case STATE_RESTRICTION:
                            if (commandString[0] != null)
                            {
                                // We have a command of some sort, so parse it
                                st = new StringTokenizer(commandString[0]);

                                tempString = CALL_io.getNextToken(st);

                                if (tempString.startsWith("/restriction"))
                                {
                                    // Add restriction to the slot
                                    newSlot.restrictions.addElement(newRestriction);
                                    newRestriction = null;
                                    readState = STATE_SLOT;
                                }
                            }
                            else if (commandString[1] != null)
                            {
                                st = new StringTokenizer(commandString[1]);
                                tempString = CALL_io.getNextToken(st);

                                if ((tempString.startsWith("[")) && (tempString.endsWith("]")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP;
                                    tempString = tempString.substring(1, tempString.length() - 1);

                                }
                                else if ((tempString.startsWith("{")) && (tempString.endsWith("}")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT;
                                    tempString = tempString.substring(1, tempString.length() - 1);

                                }
                                else if ((tempString.startsWith("(")) && (tempString.endsWith(")")))
                                {
                                    // Other (grammar rule reference)
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_OTHER;
                                    tempString = tempString.substring(1, tempString.length() - 1);
                                }
                                else
                                {
                                    // It's a plain word reference
                                    fillerType = CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD;
                                }

                                newSlotFiller = new CALL_conceptSlotFillerStruct(tempString);
                                newSlotFiller.type = fillerType;

                                if (st.hasMoreTokens())
                                {
                                    // The weight associated with the slot
                                    // filler
                                    tempString = CALL_io.strRemainder(st);
                                    tempDbl = CALL_io.str2double(tempString);
                                    if (tempDbl != CALL_io.INVALID_DBL)
                                    {
                                        newSlotFiller.weight = tempDbl;
                                        newSlot.fillerWeightTotal += tempDbl;
                                    }
                                }

                                // Now add this slot filler to to the slot
                                newRestriction.fillers.addElement(newSlotFiller);
                            }
                            break;

                        default:
                            // Shouldnt get here - ignore
                            break;
                    }
                }

            }
            if (readState == STATE_ERROR)
            {
                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.ERROR,
                // "There was an error loading the concept definition file");
                rc = false;
            }
            else
            {
                // Log information on leaded concepts, depending on debug level
                // set
                print_debug();
            }
        } catch (IOException e)
        {
            // File does not exist
            // CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.ERROR,
            // "Problem opening concept definition file");
            return false;
        }

        System.out.println(frameNames.size());
        return rc;
    }

    // Get concept
    public CALL_conceptFrameStruct getConcept(String name)
    {
        CALL_conceptFrameStruct data = null;

        // Retrieve from the hash table, based on the name
        data = (CALL_conceptFrameStruct) frames.get(name);
        return data;
    }

    // Takes a rule name, and gives an instantiated concept (data included)
    public CALL_conceptInstanceStruct getInstance(String name, int type) {
        logger.debug("enter getInstance of concept: " + name + " type: " + type);

        CALL_conceptInstanceStruct retStruct;
        CALL_conceptFrameStruct current_frame;

        retStruct = new CALL_conceptInstanceStruct();
        retStruct.setType(type);

        // Now go through the concept tree recursively, picking out the slot
        // fillers, and...filling them!
        current_frame = getConcept(name);
        if (current_frame != null)
        {

            if (type == CALL_questionStruct.QTYPE_CONTEXT)
            {
                recursivelyResolve(current_frame, retStruct);
            }

            else if (type == CALL_questionStruct.QTYPE_VOCAB)
            {
                chooseOneWord(current_frame, retStruct);
            }
        }

        return retStruct;
    }

    // Takes a rule name, and gives an instantiated concept (data included)
    public Vector<CALL_conceptInstanceStruct> getInstanceGHS(String name, int type, String grammar, List<CallJVocabularyDto> callJVoca, boolean flag) {
        logger.debug("enter getInstance of concept: " + name + " type: " + type);

        // CALL_conceptInstanceStruct retStruct;
        Vector<CALL_conceptInstanceStruct> retStructs = new Vector<CALL_conceptInstanceStruct>();
        CALL_conceptFrameStruct current_frame;

        // Now go through the concept tree recursively, picking out the slot
        // fillers, and...filling them!
        current_frame = getConcept(name);
        if (current_frame != null)
        {

            if (type == CALL_questionStruct.QTYPE_CONTEXT)
            {
                recursivelyResolveGHS(current_frame, retStructs, type, grammar, callJVoca, flag);
            }

        }

        return retStructs;
    }

    // Takes a concept name, and gives all instantiated concepts (data included)
    public CALL_conceptInstanceStruct getInstances(String name, int type)
    {
        logger.debug("enter getInstance of concept: " + name + " type: " + type);

        CALL_conceptInstanceStruct retStruct;
        CALL_conceptFrameStruct current_frame;

        retStruct = new CALL_conceptInstanceStruct();
        retStruct.setType(type);

        // Now go through the concept tree recursively, picking out the slot
        // fillers, and...filling them!
        // current_frame = getConcept(name);
        // if (current_frame != null)
        // {
        //
        // if (type == CALL_questionStruct.QTYPE_CONTEXT)
        // {
        // recursivelyResolve(current_frame, retStruct);
        //
        // }
        //
        // else if (type == CALL_questionStruct.QTYPE_VOCAB)
        // {
        // chooseOneWord(current_frame, retStruct);
        // }
        // }

        return retStruct;
    }

    // Picks one possible word that might occur in the instance as specified in
    // a lexical group
    public void chooseOneWord(CALL_conceptFrameStruct currentFrame, CALL_conceptInstanceStruct instance)
    {
        CALL_wordStruct word = null;
        Vector fillerWords;
        Random rand;

        // 1) First, get a list of all the possible filler classes within the
        // current concept frame
        fillerWords = new Vector();
        recursiveGetFillerWords(currentFrame, fillerWords);

        // 2) Next, pick one of these words
        if (fillerWords != null)
        {
            if (fillerWords.size() > 0)
            {
                rand = new Random();
                int i = rand.nextInt(fillerWords.size());

                word = (CALL_wordStruct) fillerWords.elementAt(i);
                if (word != null)
                {
                    // Add with id - this is used for picture
                    instance.addElement(CALL_conceptInstanceStruct.SINGLE_WORD_SLOT, word.english, word.internal_id, null);
                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                    // CALL_debug.DEBUG, "Adding Word to Instance: " +
                    // word.english);
                }
            }
        }

        // And give some debug, just to check
        instance.print_debug();
    }

    public void recursiveGetFillerWords(CALL_conceptFrameStruct frame, Vector fillerWords)
    {
        int i, j, k, l, id;
        CALL_conceptSlotStruct slot;
        CALL_conceptSlotFillerStruct filler;
        CALL_conceptFrameStruct childFrame;
        CALL_conceptSlotRestrictionStruct restriction;
        Vector wordList;

        String tempString;

        CALL_wordStruct word = null;

        Random rand;
        boolean match;

        if (frame != null)
        {
            for (i = 0; i < frame.slots.size(); i++)
            {
                slot = (CALL_conceptSlotStruct) frame.slots.elementAt(i);
                if (slot != null)
                {
                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                    // CALL_debug.DEBUG, "We have a slot...");

                    // Firstly, deal with those outside of restrictions
                    for (l = 0; l < slot.fillers.size(); l++)
                    {
                        filler = (CALL_conceptSlotFillerStruct) slot.fillers.elementAt(l);

                        if (filler != null)
                        {
                            // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                            // CALL_debug.DEBUG, "We have a filler, type " +
                            // filler.type + "...");

                            if (filler.type == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT)
                            {
                                // Sub concept, go recursive
                                childFrame = getConcept(filler.data);
                                if (childFrame != null)
                                {
                                    recursiveGetFillerWords(childFrame, fillerWords);
                                }
                            }
                            else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD)
                            {
                                // Add specified word to instance
                                word = db.lexicon.getWord(filler.data);
                                if (word != null)
                                {
                                    // Add the word
                                    fillerWords.addElement(word);
                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                    // CALL_debug.DEBUG, "Considering " +
                                    // word.english + " as valid vocabulary");
                                }
                            }
                            else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP)
                            {
                                // Get all matching words in lexicon, and add to
                                // our vector
                                wordList = db.lexicon.getWordsInGroup(filler.data);
                                if (wordList != null)
                                {
                                    for (k = 0; k < wordList.size(); k++)
                                    {
                                        word = (CALL_wordStruct) wordList.elementAt(k);
                                        if (word != null)
                                        {
                                            // Add the word
                                            fillerWords.addElement(word);
                                            // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                            // CALL_debug.DEBUG, "Considering "
                                            // + word.english +
                                            // " as valid vocabulary");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Now for those within restrictions
                    for (j = 0; j < slot.restrictions.size(); j++)
                    {
                        restriction = (CALL_conceptSlotRestrictionStruct) slot.restrictions.elementAt(j);

                        if (restriction != null)
                        {
                            // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                            // CALL_debug.DEBUG, "We have a restriction...");

                            for (l = 0; l < restriction.fillers.size(); l++)
                            {
                                filler = (CALL_conceptSlotFillerStruct) restriction.fillers.elementAt(l);

                                if (filler != null)
                                {
                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                    // CALL_debug.DEBUG,
                                    // "We have a filler, type " + filler.type +
                                    // "...");

                                    if (filler.type == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT)
                                    {
                                        // Sub concept, go recursive
                                        childFrame = getConcept(filler.data);
                                        if (childFrame != null)
                                        {
                                            recursiveGetFillerWords(childFrame, fillerWords);
                                        }
                                    }
                                    else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD)
                                    {
                                        // Add specified word to instance
                                        word = db.lexicon.getWord(filler.data);
                                        if (word != null)
                                        {
                                            // Add the word
                                            fillerWords.addElement(word);
                                            // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                            // CALL_debug.DEBUG, "Considering "
                                            // + word.english +
                                            // " as valid vocabulary");
                                        }
                                    }
                                    else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP)
                                    {
                                        // Get all matching words in lexicon,
                                        // and add to our vector
                                        wordList = db.lexicon.getWordsInGroup(filler.data);
                                        if (wordList != null)
                                        {
                                            for (k = 0; k < wordList.size(); k++)
                                            {
                                                word = (CALL_wordStruct) wordList.elementAt(k);
                                                if (word != null)
                                                {
                                                    // Add the word
                                                    fillerWords.addElement(word);
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "Considering " +
                                                    // word.english +
                                                    // " as valid vocabulary");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } // FOR (slots)
        }
    }

    // Recursive function for creating instance from concept structures
    public void recursivelyResolveGHS(CALL_conceptFrameStruct frame, Vector<CALL_conceptInstanceStruct> instance,
            int type, String grammar, List<CallJVocabularyDto> callJVoca, boolean flag)
    {
        logger.debug("enter recursivelyResolve, Frame: " + frame.name);
        int i, j;
        CALL_conceptSlotStruct slot;
        CALL_conceptSlotFillerStruct filler;
        Vector<CALL_conceptSlotFillerStruct> fillerAll = new Vector<CALL_conceptSlotFillerStruct>();

        CALL_conceptFrameStruct childFrame;
        CALL_conceptSlotRestrictionStruct restriction;

        CALL_wordStruct word = null;
        Vector<CALL_wordStruct> wordStructs = new Vector<CALL_wordStruct>();
        CALL_conceptInstanceStruct retStruct = null;
        Vector<CALL_conceptInstanceStruct> resultinstance = new Vector<CALL_conceptInstanceStruct>();

        int indexSlot = 0;
        int countSlot = 0;

        String slotName = "";
        if (frame != null)
        {
            logger.debug("frame.slots.size(): " + frame.slots.size());

            for (i = 0; i < frame.slots.size(); i++)
            {
                countSlot++;
                slot = (CALL_conceptSlotStruct) frame.slots.elementAt(i);
                // Set slot name
                slotName = slot.name;
                logger.debug("slot : [" + i + "]" + " name: " + slot.name);

                if (slot != null)
                {
                    indexSlot++;
                    filler = null;
                    // Check restriction
                    if (slot.restrictions.size() > 0) {
                        // Init
                        resultinstance = new Vector<CALL_conceptInstanceStruct>();
                        Vector<CALL_conceptInstanceStruct> stempInstance = new Vector<CALL_conceptInstanceStruct>();

                        for (Map.Entry<String, Vector<CALL_conceptInstanceStruct>> entryMap : mapInstance.entrySet()) {
                            for (CALL_conceptInstanceStruct conceptInstanceStruct : entryMap.getValue()) {
                                // Get restriction
                                restriction = applicableRestrictionTest(slot, conceptInstanceStruct);

                                if (restriction != null) {
                                    if (restriction.fillers.size() >= 1) {
                                        for (j = 0; j < restriction.fillers.size(); j++) {

                                            CALL_conceptInstanceStruct call_conceptInstanceStruct = new CALL_conceptInstanceStruct();
                                            // Set value for
                                            // CALL_conceptInstanceStruct
                                            call_conceptInstanceStruct.setChData((Vector<String>) conceptInstanceStruct.getChData().clone());
                                            call_conceptInstanceStruct.setData((Vector<String>) conceptInstanceStruct.getData().clone());
                                            call_conceptInstanceStruct.setGroup((Vector<String>) conceptInstanceStruct.getGroup().clone());
                                            call_conceptInstanceStruct.setLabel((Vector<String>) conceptInstanceStruct.getLabel().clone());
                                            call_conceptInstanceStruct.setId((Vector<Integer>) conceptInstanceStruct.getId().clone());
                                            call_conceptInstanceStruct.setGrammar(conceptInstanceStruct.getGrammar());
                                            call_conceptInstanceStruct.setType(conceptInstanceStruct.getType());

                                            // Get filler of restriction
                                            filler = (CALL_conceptSlotFillerStruct) restriction.fillers.elementAt(j);

                                            if (filler.type == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT) {
                                                logger.debug("filler.type: TYPE_SUB_CONCEPT");
                                                // Sub concept, go recursive
                                                childFrame = getConcept(filler.data);
                                                if (childFrame != null) {
                                                    flagRecursively = true;
                                                    recursivelyResolveGHS(childFrame, instance, type, grammar, callJVoca, flag);
                                                    flagRecursively = false;
                                                }
                                            } else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD) {
                                                // Add specified word to
                                                // instance
                                                logger.debug("filler.type: TYPE_LEXICON_WORD, find word in lexicon: " + filler.data);
                                                // If flag = true go to client
                                                if(flag){
                                                    // If slot nam startswith = "Particle" is get word
                                                    if(slotName.startsWith("Particle")){
                                                        word = db.lexicon.getWord(filler.data);
                                                    }else {// else check word contains list calljVoca
                                                        word = db.lexicon.getWordGHS(filler.data,callJVoca);
                                                    }
                                                }else {// else flag = false go to admin
                                                    word = db.lexicon.getWord(filler.data);
                                                }

                                                if (word != null) {
                                                    // Add with id - this is
                                                    // used for picture
                                                    call_conceptInstanceStruct.addElement(slot.name, word.english, word.internal_id, filler.data);
                                                } else {
                                                    call_conceptInstanceStruct.addElement(slot.name, filler.data, -1, null);
                                                }
                                                stempInstance.add(call_conceptInstanceStruct);
                                            }
                                            else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                                                logger.debug("filler.type: TYPE_LEXICON_GROUP, pickWordFromGroup: " + filler.data);
                                                // Pick list word from lexicon
                                                // group
                                                wordStructs = db.lexicon.pickWordFromGroupGHS(filler.data, callJVoca, flag);

                                                for (CALL_wordStruct words : wordStructs) {
                                                    // Init
                                                    CALL_conceptInstanceStruct stempConceptInstanceStruct = new CALL_conceptInstanceStruct();
                                                    if (words != null) {
                                                        // Set value for stem
                                                        // Concept
                                                        stempConceptInstanceStruct.setChData((Vector<String>) call_conceptInstanceStruct.getChData().clone());
                                                        stempConceptInstanceStruct.setData((Vector<String>) call_conceptInstanceStruct.getData().clone());
                                                        stempConceptInstanceStruct.setGroup((Vector<String>) call_conceptInstanceStruct.getGroup().clone());
                                                        stempConceptInstanceStruct.setLabel((Vector<String>) call_conceptInstanceStruct.getLabel().clone());
                                                        stempConceptInstanceStruct.setId((Vector<Integer>) call_conceptInstanceStruct.getId().clone());
                                                        stempConceptInstanceStruct.setGrammar(call_conceptInstanceStruct.getGrammar());
                                                        stempConceptInstanceStruct.setType(call_conceptInstanceStruct.getType());

                                                        stempConceptInstanceStruct.addElement(slot.name, words.english, words.internal_id, filler.data);

                                                    } else {
                                                        stempConceptInstanceStruct.addElement(slot.name, filler.data, -1, null);
                                                    }

                                                    stempInstance.add(stempConceptInstanceStruct);
                                                }

                                            } else {
                                                call_conceptInstanceStruct.addElement(slot.name, filler.data, -1, null);
                                                stempInstance.add(call_conceptInstanceStruct);
                                            }

                                            for (CALL_conceptInstanceStruct struct : stempInstance) {
                                                resultinstance.add(struct);
                                            }
                                            // Set value for instance
                                            stempInstance = new Vector<CALL_conceptInstanceStruct>();
                                        }
                                    }
                                }

                            }

                            // Set value in map
                            mapInstance.put(entryMap.getKey(), resultinstance);
                            resultinstance = new Vector<CALL_conceptInstanceStruct>();
                        }

                    } else {
                        if (filler == null)
                        {
                            // We will search the standard list as there seems
                            // to be no restriction applicable
                            logger.debug("no restriction");
                            if (slot.fillers.size() >= 1)
                            {
                                logger.debug("slot.fillers.size(): " + slot.fillers.size());

                                for (j = 0; j < slot.fillers.size(); j++)
                                {
                                    filler = (CALL_conceptSlotFillerStruct) slot.fillers.elementAt(j);
                                    logger.debug("filler: [" + j + "]" + " data: " + filler.data + " type: " + filler.type);
                                    // Set all filler
                                    fillerAll.add(filler);
                                }
                            }
                        }
                        logger.debug("deal with the filler");

                        if (fillerAll != null && fillerAll.size() > 0)
                        {

                            int countFiller = 0;
                            for (int k = 0; k < fillerAll.size(); k++) {
                                // Count filler
                                countFiller++;
                                if (fillerAll.get(k).type == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT)
                                {
                                    logger.debug("filler.type: TYPE_SUB_CONCEPT");
                                    // Sub concept, go recursive
                                    childFrame = getConcept(fillerAll.get(k).data);
                                    if (childFrame != null)
                                    {
                                        flagRecursively = true;
                                        recursivelyResolveGHS(childFrame, instance, type, grammar, callJVoca, flag);
                                        flagRecursively = false;
                                    }
                                }
                                else if (fillerAll.get(k).type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD)
                                {
                                    // Add specified word to instance
                                    logger.debug("filler.type: TYPE_LEXICON_WORD, find word in lexicon: " + fillerAll.get(k).data);
                                    // If flag = true go to client
                                    if(flag){
                                        // If slot nam startswith = "Particle" is get word
                                        if(slotName.startsWith("Particle")){
                                            word = db.lexicon.getWord(fillerAll.get(k).data);
                                        }else {// else check word contains list calljVoca
                                            word = db.lexicon.getWordGHS(fillerAll.get(k).data,callJVoca);
                                        }
                                    }else {// else flag = false go to admin
                                        word = db.lexicon.getWord(fillerAll.get(k).data);
                                    }
                                    if (word != null) {
                                        // Set value for instance
                                        setInstance(indexSlot, fillerAll.get(k).type, fillerAll.get(k), word, retStruct, mapInstance, slot, resultinstance, type, grammar, flagRecursively);
                                    }
                                    if (countFiller == fillerAll.size()) {
                                        if (indexSlot == frame.slots.size()) {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance = new LinkedHashMap<>();
                                                mapInstance.put("data", resultinstance);

                                            }
                                        } else {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance.put("data", resultinstance);
                                                resultinstance = new Vector<CALL_conceptInstanceStruct>();

                                            }
                                        }
                                    }

                                }
                                else if (fillerAll.get(k).type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP)
                                {
                                    logger.debug("filler.type: TYPE_LEXICON_GROUP, pickWordFromGroup: " + fillerAll.get(k).data);
                                    // Pick word from lexicon group (at random)
                                    wordStructs = db.lexicon.pickWordFromGroupGHS(fillerAll.get(k).data, callJVoca, flag);
                                    if (wordStructs != null && wordStructs.size() > 0)
                                    {
                                        for (CALL_wordStruct wordStruct : wordStructs) {
                                            // Set value for instance
                                            setInstance(indexSlot, fillerAll.get(k).type, fillerAll.get(k), wordStruct, retStruct, mapInstance, slot, resultinstance, type, grammar, flagRecursively);
                                        }
                                    }
                                    if (countFiller == fillerAll.size()) {
                                        if (indexSlot == frame.slots.size()) {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance = new LinkedHashMap<>();
                                                mapInstance.put("data", resultinstance);

                                            }
                                        } else {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance.put("data", resultinstance);
                                                resultinstance = new Vector<CALL_conceptInstanceStruct>();

                                            }
                                        }
                                    }
                                } else {
                                    retStruct = new CALL_conceptInstanceStruct();
                                    // Set value for instance
                                    setInstance(indexSlot, fillerAll.get(k).type, fillerAll.get(k), null, retStruct, mapInstance, slot, resultinstance, type, grammar, flagRecursively);

                                    // Clear
                                    if (countFiller == fillerAll.size()) {
                                        if (indexSlot == frame.slots.size()) {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance = new LinkedHashMap<>();
                                                mapInstance.put("data", resultinstance);

                                            }
                                        } else {
                                            if (resultinstance != null && resultinstance.size() > 0) {
                                                mapInstance.put("data", resultinstance);
                                                resultinstance = new Vector<CALL_conceptInstanceStruct>();

                                            }
                                        }
                                    }
                                }
                            }
                            // Clear value
                            fillerAll = new Vector<CALL_conceptSlotFillerStruct>();
                        }
                    }
                }
                // Check is not flagRecursively
                if (!flagRecursively) {
                    if (countSlot == frame.slots.size()) {
                        for (Map.Entry<String, Vector<CALL_conceptInstanceStruct>> entry : mapInstance.entrySet()) {
                            for (CALL_conceptInstanceStruct instanceStruct : entry.getValue()) {
                                instance.add(instanceStruct);
                            }
                            resultinstance = new Vector<CALL_conceptInstanceStruct>();
                        }
                        // Clear
                        mapInstance = new LinkedHashMap<String, Vector<CALL_conceptInstanceStruct>>();
                    }

                }

            }
        }
    }

    /**
     * Set value in instance
     *
     * @param type
     * @param filler
     * @param word
     * @param retStruct
     * @param map
     * @param slot
     * @param resultinstance
     */
    public void setInstance(int index, int type, CALL_conceptSlotFillerStruct filler, CALL_wordStruct word, CALL_conceptInstanceStruct retStruct,
            LinkedHashMap<String, Vector<CALL_conceptInstanceStruct>> map, CALL_conceptSlotStruct slot, Vector<CALL_conceptInstanceStruct> resultinstance,
            int typeInstance, String grammar, boolean flagRecursively) {

        if ((index > 1 || flagRecursively) && map.size() > 0) {

            for (Map.Entry<String, Vector<CALL_conceptInstanceStruct>> entryMap : map.entrySet()) {

                Vector<CALL_conceptInstanceStruct> intances = new Vector<CALL_conceptInstanceStruct>();
                for (CALL_conceptInstanceStruct conceptInstanceStruct : entryMap.getValue()) {

                    CALL_conceptInstanceStruct call_conceptInstanceStruct = new CALL_conceptInstanceStruct();
                    call_conceptInstanceStruct.setChData((Vector<String>) conceptInstanceStruct.getChData().clone());
                    call_conceptInstanceStruct.setData((Vector<String>) conceptInstanceStruct.getData().clone());
                    call_conceptInstanceStruct.setGroup((Vector<String>) conceptInstanceStruct.getGroup().clone());
                    call_conceptInstanceStruct.setLabel((Vector<String>) conceptInstanceStruct.getLabel().clone());
                    call_conceptInstanceStruct.setId((Vector<Integer>) conceptInstanceStruct.getId().clone());
                    call_conceptInstanceStruct.setGrammar(conceptInstanceStruct.getGrammar());
                    call_conceptInstanceStruct.setType(conceptInstanceStruct.getType());

                    if (type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD || type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                        if (word != null) {
                            call_conceptInstanceStruct.addElement(slot.name, word.english, word.internal_id, filler.data);
                        } else {
                            call_conceptInstanceStruct.addElement(slot.name, filler.data, -1, null);
                        }
                    } else {
                        call_conceptInstanceStruct.addElement(slot.name, filler.data, -1, null);
                    }
                    // Add value conceptInstanceStruct in vector
                    intances.add(call_conceptInstanceStruct);
                }
                // Add value result Instance in map
                for (CALL_conceptInstanceStruct struct : intances) {
                    resultinstance.add(struct);
                }
            }
        } else {
            if (type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD || type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP) {
                retStruct = new CALL_conceptInstanceStruct();
                retStruct.setType(typeInstance);
                // Set grammar
                retStruct.setGrammar(grammar);
                if (word != null) {
                    // Add value for concept instance struct
                    retStruct.addElement(slot.name, word.english, word.internal_id, filler.data);
                } else {
                    retStruct.addElement(slot.name, filler.data, -1, null);
                }
            } else {
                retStruct.addElement(slot.name, filler.data, -1, null);
            }
            resultinstance.add(retStruct);
        }

    }

    // Recursive function for creating instance from concept structures
    public void recursivelyResolve(CALL_conceptFrameStruct frame, CALL_conceptInstanceStruct instance)
    {
        logger.debug("enter recursivelyResolve, Frame: " + frame.name);

        int i;
        CALL_conceptSlotStruct slot;
        CALL_conceptSlotFillerStruct filler;
        CALL_conceptFrameStruct childFrame;
        CALL_conceptSlotRestrictionStruct restriction;

        CALL_wordStruct word = null;

        if (frame != null)
        {
            logger.debug("frame.slots.size(): " + frame.slots.size());

            for (i = 0; i < frame.slots.size(); i++)
            {

                slot = (CALL_conceptSlotStruct) frame.slots.elementAt(i);
                logger.debug("slot : [" + i + "]" + " name: " + slot.name);

                if (slot != null)
                {

                    filler = null;

                    restriction = applicableRestriction(slot, instance);
                    if (restriction != null)
                    {

                        if (restriction.fillers.size() >= 1)
                        {
                            filler = (CALL_conceptSlotFillerStruct) restriction.fillers.elementAt(0);

                        }
                    }

                    if (filler == null)
                    {
                        // We will search the standard list as there seems
                        // to be no restriction applicable
                        logger.debug("no restriction");

                        if (slot.fillers.size() >= 1)
                        {
                            logger.debug("slot.fillers.size(): " + slot.fillers.size());
                            // Now pick a value for this filler
                            filler = (CALL_conceptSlotFillerStruct) slot.fillers.elementAt(0);
                            logger.debug("chosen filler, data: " + filler.data + " type: " + filler.type);

                        }
                    }

                    logger.debug("deal with the filler");

                    if (filler != null)
                    {
                        if (filler.type == CALL_conceptSlotFillerStruct.TYPE_SUB_CONCEPT)
                        {
                            logger.debug("filler.type: TYPE_SUB_CONCEPT");
                            // Sub concept, go recursive
                            childFrame = getConcept(filler.data);
                            if (childFrame != null)
                            {
                                recursivelyResolve(childFrame, instance);
                            }
                        }
                        else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_WORD)
                        {
                            // Add specified word to instance
                            logger.debug("filler.type: TYPE_LEXICON_WORD, find word in lexicon: " + filler.data);

                            word = db.lexicon.getWord(filler.data);

                            if (word != null)
                            {
                                // Add with id - this is used for picture
                                instance.addElement(slot.name, word.english, word.internal_id, filler.data);
                            }
                            else
                            {
                                instance.addElement(slot.name, filler.data, -1, null);
                            }
                        }
                        else if (filler.type == CALL_conceptSlotFillerStruct.TYPE_LEXICON_GROUP)
                        {
                            logger.debug("filler.type: TYPE_LEXICON_GROUP, pickWordFromGroup: " + filler.data);
                            // Pick word from lexicon group (at random) and
                            // add
                            word = db.lexicon.pickWordFromGroup(filler.data);
                            if (word != null)
                            {
                                instance.addElement(slot.name, word.english, word.internal_id, filler.data);
                            }
                            else
                            {
                                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                // CALL_debug.ERROR,
                                // "There was an error retrieving words from group ["
                                // + filler.data + "]");
                            }
                        }
                        else
                        {
                            // Just add the token as is (pressumeably
                            // handled by grammar rules)
                            instance.addElement(slot.name, filler.data, -1, null);
                        }
                    }
                    else
                    {
                        // No slot fillers? Give a warning
                        // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                        // CALL_debug.ERROR, "Slot [" + slot.name +
                        // "] has no fillers!");
                    }
                }
            }
        }
    }

    public CALL_conceptSlotRestrictionStruct applicableRestriction(CALL_conceptSlotStruct slot, CALL_conceptInstanceStruct instance)
    {
        CALL_conceptSlotRestrictionStruct restriction, returnStruct;
        String tempString, slotString, fillerString, operatorString;
        StringTokenizer st;
        Integer wordId;
        Vector fillerWords = null;
        Vector dataStrings = null;
        CALL_wordStruct tempWord;
        boolean match = false;

        // Counters
        int i, j, k, d, w;

        returnStruct = null;

        // Do we have a restriction in place for this slot?
        for (j = 0; j < slot.restrictions.size(); j++)
        {
            restriction = (CALL_conceptSlotRestrictionStruct) slot.restrictions.elementAt(j);

            if (restriction != null)
            {
                st = new StringTokenizer(restriction.restriction);

                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG,
                // "Checking restriction " + restriction.restriction);

                slotString = CALL_io.getNextToken(st);
                if (slotString != null)
                {
                    operatorString = CALL_io.getNextToken(st);
                    if (operatorString != null)
                    {
                        fillerString = CALL_io.getNextToken(st);
                        if (fillerString != null)
                        {
                            // Search the concept instance for completion of
                            // specified slot
                            // Note that instance.label is a vector of Strings,
                            // representing the names of each slot
                            for (k = 0; k < instance.getLabel().size(); k++)
                            {
                                tempString = (String) instance.getLabel().elementAt(k);
                                if (tempString != null)
                                {
                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                    // CALL_debug.DEBUG, "Looking at slot " +
                                    // tempString);
                                    if (tempString.equals(slotString))
                                    {
                                        // OK - We have found the slot being
                                        // referred to in this restriction

                                        // First, get the word id associated
                                        // with the slot filler, if it has one
                                        wordId = (Integer) instance.getId().elementAt(k);

                                        // Now find the words associated with
                                        // this ID
                                        fillerWords = db.lexicon.getWords(wordId.intValue());
                                        dataStrings = new Vector();

                                        if (fillerString.startsWith("["))
                                        {
                                            // Looking for a sub-group, so we
                                            // need to go through all possible
                                            // words, and all their categories
                                            fillerString = fillerString.substring(1, fillerString.length() - 1);
                                            if (fillerWords.size() > 0)
                                            {
                                                // We have words
                                                for (w = 0; w < fillerWords.size(); w++)
                                                {
                                                    tempWord = (CALL_wordStruct) fillerWords.elementAt(w);
                                                    if (tempWord != null)
                                                    {
                                                        for (d = 0; d < tempWord.categories.size(); d++)
                                                        {
                                                            tempString = (String) tempWord.categories.elementAt(d);
                                                            if (tempString != null)
                                                            {
                                                                dataStrings.addElement(tempString);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                // Just add the group stored in
                                                // the instance...don't like
                                                // this mind - CJW
                                                dataStrings.addElement((String) instance.getGroup().elementAt(k));
                                            }
                                        }
                                        else
                                        {
                                            // Just use the data string from the
                                            // filler
                                            dataStrings.addElement((String) instance.getData().elementAt(k));
                                        }

                                        // Now, go through each of the possible
                                        // filler data strings
                                        for (i = 0; i < dataStrings.size(); i++)
                                        {
                                            tempString = (String) dataStrings.elementAt(i);
                                            if (tempString != null)
                                            {
                                                if (operatorString.equals("=="))
                                                {
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "Check: [" + tempString
                                                    // +" == " + fillerString +
                                                    // "]?");
                                                    if (tempString.equals(fillerString))
                                                    {
                                                        match = true;
                                                    }
                                                }
                                                else if (operatorString.equals("!="))
                                                {
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "Check: [" + tempString
                                                    // +" != " + fillerString +
                                                    // "]?");
                                                    if (!(tempString.equals(fillerString)))
                                                    {
                                                        match = false;
                                                        break;
                                                    }

                                                    if (i == (dataStrings.size() - 1))
                                                    {
                                                        // We've got to the end
                                                        // without matching, so
                                                        // the restriction
                                                        // succeeds!
                                                        match = true;
                                                    }
                                                }
                                                else
                                                {
                                                    match = false;
                                                }
                                                if (match)
                                                {
                                                    // The restriction matches -
                                                    // select a filler from this
                                                    // restriction
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "MATCH!");
                                                    returnStruct = restriction;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (returnStruct != null)
                                {
                                    // End the search
                                    break;
                                }
                            }

                            if (returnStruct != null)
                            {
                                // We've found one
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returnStruct;
    }

    public CALL_conceptSlotRestrictionStruct applicableRestrictionTest(CALL_conceptSlotStruct slot, CALL_conceptInstanceStruct instance)
    {
        CALL_conceptSlotRestrictionStruct restriction, returnStruct;
        String tempString, slotString, fillerString, operatorString;
        StringTokenizer st;
        Integer wordId;
        Vector fillerWords = null;
        Vector dataStrings = null;
        CALL_wordStruct tempWord;
        boolean match = false;

        // Counters
        int i, j, k, d, w;

        returnStruct = null;

        // Do we have a restriction in place for this slot?
        for (j = 0; j < slot.restrictions.size(); j++)
        {
            restriction = (CALL_conceptSlotRestrictionStruct) slot.restrictions.elementAt(j);

            if (restriction != null)
            {
                st = new StringTokenizer(restriction.restriction);

                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG,
                // "Checking restriction " + restriction.restriction);

                slotString = CALL_io.getNextToken(st);
                if (slotString != null)
                {
                    operatorString = CALL_io.getNextToken(st);
                    if (operatorString != null)
                    {
                        fillerString = CALL_io.getNextToken(st);
                        if (fillerString != null)
                        {
                            // Search the concept instance for completion of
                            // specified slot
                            // Note that instance.label is a vector of Strings,
                            // representing the names of each slot
                            for (k = 0; k < instance.getLabel().size(); k++)
                            {
                                tempString = (String) instance.getLabel().elementAt(k);
                                if (tempString != null)
                                {
                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                    // CALL_debug.DEBUG, "Looking at slot " +
                                    // tempString);
                                    if (tempString.equals(slotString))
                                    {
                                        // OK - We have found the slot being
                                        // referred to in this restriction

                                        // First, get the word id associated
                                        // with the slot filler, if it has one
                                        wordId = (Integer) instance.getId().elementAt(k);

                                        // Now find the words associated with
                                        // this ID
                                        fillerWords = db.lexicon.getWords(wordId.intValue());
                                        dataStrings = new Vector();

                                        if (fillerString.startsWith("["))
                                        {
                                            // Looking for a sub-group, so we
                                            // need to go through all possible
                                            // words, and all their categories
                                            fillerString = fillerString.substring(1, fillerString.length() - 1);
                                            if (fillerWords.size() > 0)
                                            {
                                                // We have words
                                                for (w = 0; w < fillerWords.size(); w++)
                                                {
                                                    tempWord = (CALL_wordStruct) fillerWords.elementAt(w);
                                                    if (tempWord != null)
                                                    {
                                                        for (d = 0; d < tempWord.categories.size(); d++)
                                                        {
                                                            tempString = (String) tempWord.categories.elementAt(d);
                                                            if (tempString != null)
                                                            {
                                                                dataStrings.addElement(tempString);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                // Just add the group stored in
                                                // the instance...don't like
                                                // this mind - CJW
                                                dataStrings.addElement((String) instance.getGroup().elementAt(k));
                                            }
                                        }
                                        else
                                        {
                                            // Just use the data string from the
                                            // filler
                                            dataStrings.addElement((String) instance.getData().elementAt(k));
                                        }

                                        // Now, go through each of the possible
                                        // filler data strings
                                        for (i = 0; i < dataStrings.size(); i++)
                                        {
                                            tempString = (String) dataStrings.elementAt(i);
                                            if (tempString != null)
                                            {
                                                if (operatorString.equals("=="))
                                                {
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "Check: [" + tempString
                                                    // +" == " + fillerString +
                                                    // "]?");
                                                    if (tempString.equals(fillerString))
                                                    {
                                                        match = true;
                                                    }
                                                }
                                                else if (operatorString.equals("!="))
                                                {
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "Check: [" + tempString
                                                    // +" != " + fillerString +
                                                    // "]?");
                                                    if (!(tempString.equals(fillerString)))
                                                    {
                                                        match = false;
                                                        break;
                                                    }

                                                    if (i == (dataStrings.size() - 1))
                                                    {
                                                        // We've got to the end
                                                        // without matching, so
                                                        // the restriction
                                                        // succeeds!
                                                        match = true;
                                                    }
                                                }
                                                else
                                                {
                                                    match = false;
                                                }
                                                if (match)
                                                {
                                                    // The restriction matches -
                                                    // select a filler from this
                                                    // restriction
                                                    // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                                    // CALL_debug.DEBUG,
                                                    // "MATCH!");
                                                    returnStruct = restriction;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (returnStruct != null)
                                {
                                    // End the search
                                    break;
                                }
                            }

                            if (returnStruct != null)
                            {
                                // We've found one
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returnStruct;
    }

    public void print_debug()
    {
        String frameName;
        CALL_conceptFrameStruct currentFrame = null;
        CALL_conceptSlotStruct currentSlot = null;
        CALL_conceptSlotFillerStruct currentFiller = null;

        // Go through each entry in the hash table
        for (int i = 0; i < frameNames.size(); i++)
        {
            frameName = (String) frameNames.elementAt(i);
            currentFrame = getConcept(frameName);
            if (currentFrame != null)
            {
                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.INFO,
                // "Frame: " + currentFrame.name);

                for (int j = 0; j < currentFrame.slots.size(); j++)
                {
                    currentSlot = (CALL_conceptSlotStruct) currentFrame.slots.elementAt(j);
                    if (currentSlot != null)
                    {
                        // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                        // CALL_debug.INFO, "-->Slot: " + currentSlot.name);

                        for (int k = 0; k < currentSlot.fillers.size(); k++)
                        {
                            currentFiller = (CALL_conceptSlotFillerStruct) currentSlot.fillers.elementAt(k);
                            if (currentFiller != null)
                            {
                                // CALL_debug.printlog(CALL_debug.MOD_CONCEPT,
                                // CALL_debug.INFO, "---->Filler: " +
                                // currentFiller.data + ", Type: [" +
                                // currentFiller.type + "], [" +
                                // currentFiller.weight + "]");
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] agrs) {
        CALL_conceptRulesStruct struct = new CALL_conceptRulesStruct(null);
        struct.load_concepts("/Data/concept.txt");
        System.out.println("1");
    }

}