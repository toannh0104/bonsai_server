// /////////////////////////////////////////////////////////////////
// Holds the information about the form specification for a verb / adjective
//
//

package jcall;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CALL_formStruct {

    static Logger logger = Logger.getLogger(CALL_formStruct.class.getName());

    // Fields
    String rule;

    boolean positive;
    boolean negative;
    boolean past;
    boolean present;
    boolean plain;
    boolean polite;
    boolean superpolite;
    boolean crude;
    boolean humble;
    boolean question;
    boolean statement;

    CALL_stringPairsStruct conditions; // These are conditions that limit
                                       // possible
                                       // forms

    // Statics
    public final static int STYLE_FORMS = 5;
    public final static int POLITE = 1;
    public final static int PLAIN = 0;
    public final static int SUPER_POLITE = 2;
    public final static int HUMBLE = 3;
    public final static int CRUDE = 4;

    public final static int TENSE_FORMS = 2;
    public final static int PAST = 1;
    public final static int PRESENT = 0;

    public final static int SIGN_FORMS = 2;
    public final static int POSITIVE = 0;
    public final static int NEGATIVE = 1;

    public final static int TYPE_FORMS = 2;
    public final static int STATEMENT = 0;
    public final static int QUESTION = 1;

    public CALL_formStruct() {
        rule = null;
        positive = true;
        negative = true;
        past = true;
        present = true;
        plain = true;
        polite = true;
        superpolite = true;
        crude = true;
        humble = true;
        question = true;
        statement = true;
        conditions = null;
    }

    public CALL_formStruct(String settings) {
        setFromString(settings);
    }

    public void setFromString(String settings) {
        StringTokenizer st;
        String tempString;

        // Set all to false
        positive = false;
        negative = false;
        past = false;
        present = false;
        plain = false;
        polite = false;
        superpolite = false;
        crude = false;
        question = false;
        statement = false;
        conditions = null;

        // Then re-enable the ones mentioned in string
        st = new StringTokenizer(settings);
        while (st.hasMoreTokens()) {
            tempString = CALL_io.getNextToken(st);

            if (tempString != null) {
                if (tempString.equals("pos")) {
                    positive = true;
                } else if (tempString.equals("neg")) {
                    negative = true;
                } else if (tempString.equals("past")) {
                    past = true;
                } else if (tempString.equals("present")) {
                    present = true;
                } else if (tempString.equals("polite")) {
                    polite = true;
                } else if (tempString.equals("plain")) {
                    plain = true;
                } else if (tempString.equals("superpolite")) {
                    superpolite = true;
                } else if (tempString.equals("crude")) {
                    crude = true;
                } else if (tempString.equals("statement")) {
                    statement = true;
                } else if (tempString.equals("question")) {
                    question = true;
                }
            }
        }
    }

    public String toString() {
        String retString;

        retString = new String("");

        if (positive) retString = retString + " " + "pos";// Positive
        if (negative) retString = retString + " " + "neg";
        if (past) retString = retString + " " + "past";
        if (present) retString = retString + " " + "present";
        if (plain) retString = retString + " " + "plain";
        // if(superpolite) retString = retString + " " + "superpolite ";
        // if(crude) retString = retString + " " + "crude ";
        if (polite) retString = retString + " " + "polite";
        if (question) retString = retString + " " + "question";
        if (statement) retString = retString + " " + "statement";

        return retString.trim();
    }

    public CALL_formInstanceStruct pickSettings(CALL_database db, CALL_conceptInstanceStruct instance) {
        int options[] = { -1, -1, -1, -1, -1, -1, -1, -1 }; // 8 options per
                                                            // type
        int choices;
        int selection;

        CALL_formInstanceStruct formInstance;
        Random rand;

        // Local copies of the settings - we may need to change them due to
        // conditions
        boolean l_positive = positive;
        boolean l_negative = negative;
        boolean l_past = past;
        boolean l_present = present;
        boolean l_plain = plain;
        boolean l_polite = polite;
        boolean l_superpolite = superpolite;
        boolean l_crude = crude;
        boolean l_humble = humble;
        boolean l_question = question;
        boolean l_statement = statement;

        StringTokenizer st;

        CALL_stringPairStruct pair;
        String condition;
        String effect;

        boolean match;

        String lhString, rhString, operatorString;

        // STEP 1: DETERMINE OUR OPTIONS BY COMPARING WITH THE CONDITIONS
        // ====================================================================

        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Picking a set of form values");
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Template:");
        print_debug();

        // Now go through each condition
        if (conditions != null) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form has " + conditions.size() + " conditions");
            // logger.debug("Form has [" + conditions.size() + "] conditions");
            for (int i = 0; i < conditions.size(); i++) {
                pair = conditions.getPair(i);

                if (pair != null) {
                    condition = pair.parameter;
                    effect = pair.value;
                    // logger.debug("interation["+i+"]" );
                    st = new StringTokenizer(condition);

                    lhString = CALL_io.getNextToken(st);
                    operatorString = CALL_io.getNextToken(st);
                    rhString = CALL_io.getNextToken(st);

                    match = db.lexicon.wordRestrictionMatch(instance, lhString, rhString, operatorString);
                    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                    // CALL_debug.DEBUG,
                    // "Considering form condition:" + lhString + " " +
                    // operatorString +
                    // " " + rhString);
                    // logger.debug("Considering form condition:" + lhString +
                    // " " +
                    // operatorString + " " + rhString);

                    if (match) {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG,
                        // "Match!");
                        // logger.debug("Condition Match!");
                        // Our condition matches, so now see which form value we
                        // have to
                        // change
                        st = new StringTokenizer(effect);
                        lhString = CALL_io.getNextToken(st);
                        rhString = CALL_io.getNextToken(st);
                        operatorString = CALL_io.getNextToken(st);

                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG,
                        // "Considering consequence:" + lhString + " " +
                        // rhString + " " +
                        // operatorString);
                        // logger.debug("Considering consequence:" + lhString +
                        // " " +
                        // rhString + " " + operatorString);

                        if ((lhString != null) && (lhString.equals("FORM"))) {
                            if (rhString != null) {
                                if (rhString.equals("neg")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_negative = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling negative form");
                                        // logger.debug("Disabling negative form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_negative = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling negative form");
                                        // logger.debug("Enabling negative form");
                                    }
                                }
                                if (rhString.equals("pos")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_positive = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling positive form");
                                        // logger.debug("Disabling positive form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_positive = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling positive form");
                                        // logger.debug("Enabling positive form");
                                    }
                                }
                                if (rhString.equals("past")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_past = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling past form");
                                        // logger.debug("Disabling past form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_past = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling past form");
                                        // logger.debug("Enabling past form");
                                    }
                                }
                                if (rhString.equals("present")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_present = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling present form");
                                        // logger.debug("Disabling present form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_present = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling present form");
                                        // logger.debug("Enabling present form");
                                    }
                                }
                                if (rhString.equals("plain")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_plain = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling plain form");
                                        // logger.debug("Disabling plain form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_plain = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling plain form");
                                        // logger.debug("Enabling plain form");
                                    }
                                }
                                if (rhString.equals("polite")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_polite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling polite form");
                                        // logger.debug("Disabling polite form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_polite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling polite form");
                                        // logger.debug("Enabling polite form");
                                    }
                                }
                                if (rhString.equals("superpolite")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_superpolite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling superpolite form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_superpolite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling superpolite form");
                                    }
                                }
                                if (rhString.equals("crude")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_crude = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling crude form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_crude = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling crude form");
                                    }
                                }
                                if (rhString.equals("humble")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_humble = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling humble form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_humble = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling humble form");
                                    }
                                }
                                if (rhString.equals("question")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_question = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling question form");
                                        // logger.debug("Disabling question form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_question = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling question form");
                                        // logger.debug("Enabling question form");
                                    }
                                }
                                if (rhString.equals("statement")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_statement = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling statement form");
                                        // logger.debug("Disabling statement form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_statement = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling statement form");
                                        // logger.debug("Enabling statement form");
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }

        // STEP 2: FROM THE DETERMINED OPTIONS, PICK THE SETTINGS
        // ==========================================================

        formInstance = new CALL_formInstanceStruct();
        rand = new Random();

        //
        // Sign
        if ((l_positive) && (l_negative)) {
            // We are allowing either option
            // if (rand.nextDouble() > 0.5) {
            // formInstance.sign = POSITIVE;
            // } else {
            // formInstance.sign = NEGATIVE;
            // }
            formInstance.sign = POSITIVE;
        } else if (l_positive) {
            // Can only be positive
            formInstance.sign = POSITIVE;
        } else {
            // Well, assume negative then!
            formInstance.sign = NEGATIVE;
        }

        //
        // Tense
        if ((l_past) && (l_present)) {
            // We are allowing either option
            // if (rand.nextDouble() > 0.5) {
            // formInstance.tense = PAST;
            // } else {
            // formInstance.tense = PRESENT;
            // }
            formInstance.tense = PRESENT;
        } else if (l_past) {
            // Can only be past
            formInstance.tense = PAST;
        } else {
            // Well, assume present then!
            formInstance.tense = PRESENT;
        }

        // Politeness - 3 options for this currently
        choices = 0;

        if (l_polite) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + POLITE);
            options[choices] = POLITE;
            choices++;
        }
        if (l_plain) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + PLAIN);
            options[choices] = PLAIN;
            choices++;
        }
        if (l_crude) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + CRUDE);
            options[choices] = CRUDE;
            choices++;
        }

        if (choices == 0) {
            // Have to return something even if they all seem disabled!
            formInstance.style = POLITE;
        } else {
            // selection = (int) (rand.nextDouble() * choices);
            // if ((selection >= 0) && (selection < choices)) {
            // formInstance.style = options[selection];
            // } else {
            // // Oops!
            // formInstance.style = POLITE;
            // }
            formInstance.style = options[0];
        }

        // Some debug!
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Form: Picked " + formInstance.style);

        // Question
        if ((l_question) && (l_statement)) {
            // We are allowing either option
            // if (rand.nextDouble() > 0.5) {
            // formInstance.type = QUESTION;
            // } else {
            // formInstance.type = STATEMENT;
            // }
            formInstance.type = STATEMENT;
        } else if (l_question) {
            // Can only be a question
            formInstance.type = QUESTION;
        } else {
            // Well, assume statement then!
            formInstance.type = STATEMENT;
        }

        // Now return the completed instance
        // logger.debug("Picked Form Instance: "+formInstance.toString());
        return formInstance;

    }

    /**
     * Pick value first of setting
     *
     * @param db
     * @param instance
     * @return
     */
    public CALL_formInstanceStruct pickFirstSettings(CALL_database db, CALL_conceptInstanceStruct instance, String[] arrayForm) {
        int options[] = { -1, -1, -1, -1, -1, -1, -1, -1 }; // 8 options per
                                                            // type
        int choices;
        int selection;

        CALL_formInstanceStruct formInstance;
        Random rand;

        // Local copies of the settings - we may need to change them due to
        // conditions
        boolean l_positive = positive;
        boolean l_negative = negative;
        boolean l_past = past;
        boolean l_present = present;
        boolean l_plain = plain;
        boolean l_polite = polite;
        boolean l_superpolite = superpolite;
        boolean l_crude = crude;
        boolean l_humble = humble;
        boolean l_question = question;
        boolean l_statement = statement;

        StringTokenizer st;

        CALL_stringPairStruct pair;
        String condition;
        String effect;

        boolean match;

        String lhString, rhString, operatorString;

        // STEP 1: DETERMINE OUR OPTIONS BY COMPARING WITH THE CONDITIONS
        // ====================================================================

        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Picking a set of form values");
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Template:");
        print_debug();

        // Now go through each condition
        if (conditions != null) {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form has " + conditions.size() + " conditions");
            // logger.debug("Form has [" + conditions.size() + "] conditions");
            for (int i = 0; i < conditions.size(); i++) {
                pair = conditions.getPair(i);

                if (pair != null) {
                    condition = pair.parameter;
                    effect = pair.value;
                    // logger.debug("interation["+i+"]" );
                    st = new StringTokenizer(condition);

                    lhString = CALL_io.getNextToken(st);
                    operatorString = CALL_io.getNextToken(st);
                    rhString = CALL_io.getNextToken(st);

                    match = db.lexicon.wordRestrictionMatch(instance, lhString, rhString, operatorString);
                    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                    // CALL_debug.DEBUG,
                    // "Considering form condition:" + lhString + " " +
                    // operatorString +
                    // " " + rhString);
                    // logger.debug("Considering form condition:" + lhString +
                    // " " +
                    // operatorString + " " + rhString);

                    if (match) {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG,
                        // "Match!");
                        // logger.debug("Condition Match!");
                        // Our condition matches, so now see which form value we
                        // have to
                        // change
                        st = new StringTokenizer(effect);
                        lhString = CALL_io.getNextToken(st);
                        rhString = CALL_io.getNextToken(st);
                        operatorString = CALL_io.getNextToken(st);

                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG,
                        // "Considering consequence:" + lhString + " " +
                        // rhString + " " +
                        // operatorString);
                        // logger.debug("Considering consequence:" + lhString +
                        // " " +
                        // rhString + " " + operatorString);

                        if ((lhString != null) && (lhString.equals("FORM"))) {
                            if (rhString != null) {
                                if (rhString.equals("neg")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_negative = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling negative form");
                                        // logger.debug("Disabling negative form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_negative = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling negative form");
                                        // logger.debug("Enabling negative form");
                                    }
                                }
                                if (rhString.equals("pos")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_positive = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling positive form");
                                        // logger.debug("Disabling positive form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_positive = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling positive form");
                                        // logger.debug("Enabling positive form");
                                    }
                                }
                                if (rhString.equals("past")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_past = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling past form");
                                        // logger.debug("Disabling past form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_past = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling past form");
                                        // logger.debug("Enabling past form");
                                    }
                                }
                                if (rhString.equals("present")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_present = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling present form");
                                        // logger.debug("Disabling present form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_present = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling present form");
                                        // logger.debug("Enabling present form");
                                    }
                                }
                                if (rhString.equals("plain")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_plain = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling plain form");
                                        // logger.debug("Disabling plain form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_plain = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling plain form");
                                        // logger.debug("Enabling plain form");
                                    }
                                }
                                if (rhString.equals("polite")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_polite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling polite form");
                                        // logger.debug("Disabling polite form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_polite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling polite form");
                                        // logger.debug("Enabling polite form");
                                    }
                                }
                                if (rhString.equals("superpolite")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_superpolite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling superpolite form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_superpolite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling superpolite form");
                                    }
                                }
                                if (rhString.equals("crude")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_crude = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling crude form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_crude = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling crude form");
                                    }
                                }
                                if (rhString.equals("humble")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_humble = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling humble form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_humble = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling humble form");
                                    }
                                }
                                if (rhString.equals("question")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_question = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling question form");
                                        // logger.debug("Disabling question form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_question = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling question form");
                                        // logger.debug("Enabling question form");
                                    }
                                }
                                if (rhString.equals("statement")) {
                                    if ((operatorString != null) && (operatorString.equals("off"))) {
                                        l_statement = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling statement form");
                                        // logger.debug("Disabling statement form");
                                    } else if ((operatorString != null) && (operatorString.equals("on"))) {
                                        l_statement = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling statement form");
                                        // logger.debug("Enabling statement form");
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }

        // STEP 2: FROM THE DETERMINED OPTIONS, PICK THE SETTINGS
        // ==========================================================

        formInstance = new CALL_formInstanceStruct();
        rand = new Random();

        //
        // Sign
        if ((l_positive) && (l_negative)) {

            for (int i = 0; i < arrayForm.length; i++) {
                if (arrayForm[i].equals("pos")) {
                    formInstance.sign = POSITIVE;
                    break;
                } else if (arrayForm[i].equals("neg")) {
                    formInstance.sign = NEGATIVE;
                    break;
                }
            }
        } else if (l_positive) {
            // Can only be positive
            formInstance.sign = POSITIVE;
        } else {
            // Well, assume negative then!
            formInstance.sign = NEGATIVE;
        }

        //
        // Tense
        if ((l_past) && (l_present)) {
            for (int i = 0; i < arrayForm.length; i++) {
                if (arrayForm[i].equals("past")) {
                    formInstance.tense = PAST;
                    break;
                } else if (arrayForm[i].equals("present")) {
                    formInstance.tense = PRESENT;
                    break;
                }
            }
        } else if (l_past) {
            // Can only be past
            formInstance.tense = PAST;
        } else {
            // Well, assume present then!
            formInstance.tense = PRESENT;
        }

        // Politeness - 3 options for this currently
//        choices = 0;
//
//        if (l_polite) {
//            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
//            // "Form. Option " + choices + " set to " + POLITE);
//            options[choices] = POLITE;
//            choices++;
//        }
//        if (l_plain) {
//            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
//            // "Form. Option " + choices + " set to " + PLAIN);
//            options[choices] = PLAIN;
//            choices++;
//        }
//        if (l_crude) {
//            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
//            // "Form. Option " + choices + " set to " + CRUDE);
//            options[choices] = CRUDE;
//            choices++;
//        }
//
//        if (choices == 0) {
//            // Have to return something even if they all seem disabled!
//            formInstance.style = POLITE;
//        } else {
//            int count = 0;
//            for (int i = 0; i < arrayForm.length; i++) {
//                for (int j = 0; j < options.length; j++) {
//                    if(arrayForm[i].equals(options[j])){
//                        formInstance.style = options[j];
//                        count ++;
//                        break;
//                    }
//                }
//                if(count == 1){
//                    break;
//                }
//            }
//        }
        // Style
        for (int i = 0; i < arrayForm.length; i++) {
            if(arrayForm[i].equals("plain")){
                formInstance.style = PLAIN;
                break;
            }
            if(arrayForm[i].equals("polite")){
                formInstance.style = POLITE;
                break;
            }
        }
        // Some debug!
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Form: Picked " + formInstance.style);

        // Question
        if ((l_question) && (l_statement)) {
            for (int i = 0; i < arrayForm.length; i++) {
                if (arrayForm[i].equals("question")) {
                    formInstance.type = QUESTION;
                    break;
                } else if (arrayForm[i].equals("statement")) {
                    formInstance.type = STATEMENT;
                    break;
                }
            }
        } else if (l_question) {
            // Can only be a question
            formInstance.type = QUESTION;
        } else {
            // Well, assume statement then!
            formInstance.type = STATEMENT;
        }

        // Now return the completed instance
        // logger.debug("Picked Form Instance: "+formInstance.toString());
        return formInstance;

    }

    /**
     * Pick Setting from grammar
     *
     * @param db
     * @param instance
     * @return
     */
    public Vector<CALL_formInstanceStruct> pickSettingsGHS(CALL_database db, CALL_conceptInstanceStruct instance)
    {
        int options[] = { -1, -1, -1, -1, -1, -1, -1, -1 }; // 8 options per
                                                            // type
        int choices;
        int selection;

        CALL_formInstanceStruct formInstance;

        Random rand;

        // Local copies of the settings - we may need to change them due to
        // conditions
        boolean l_positive = positive;
        boolean l_negative = negative;
        boolean l_past = past;
        boolean l_present = present;
        boolean l_plain = plain;
        boolean l_polite = polite;
        boolean l_superpolite = superpolite;
        boolean l_crude = crude;
        boolean l_humble = humble;
        boolean l_question = question;
        boolean l_statement = statement;

        StringTokenizer st;

        CALL_stringPairStruct pair;
        String condition;
        String effect;

        boolean match;

        String lhString, rhString, operatorString;

        // STEP 1: DETERMINE OUR OPTIONS BY COMPARING WITH THE CONDITIONS
        // ====================================================================

        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Picking a set of form values");
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Template:");
        print_debug();

        // Now go through each condition
        if (conditions != null)
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form has " + conditions.size() + " conditions");
            // logger.debug("Form has [" + conditions.size() + "] conditions");
            for (int i = 0; i < conditions.size(); i++)
            {
                pair = conditions.getPair(i);

                if (pair != null)
                {
                    condition = pair.parameter;
                    effect = pair.value;
                    // logger.debug("interation["+i+"]" );
                    st = new StringTokenizer(condition);

                    lhString = CALL_io.getNextToken(st);
                    operatorString = CALL_io.getNextToken(st);
                    rhString = CALL_io.getNextToken(st);

                    match = db.lexicon.wordRestrictionMatch(instance, lhString, rhString, operatorString);
                    // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                    // CALL_debug.DEBUG, "Considering form condition:" +
                    // lhString + " " + operatorString + " " + rhString);
                    // logger.debug("Considering form condition:" + lhString +
                    // " " + operatorString + " " + rhString);

                    if (match)
                    {
                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Match!");
                        // logger.debug("Condition Match!");
                        // Our condition matches, so now see which form value we
                        // have to change
                        st = new StringTokenizer(effect);
                        lhString = CALL_io.getNextToken(st);
                        rhString = CALL_io.getNextToken(st);
                        operatorString = CALL_io.getNextToken(st);

                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                        // CALL_debug.DEBUG, "Considering consequence:" +
                        // lhString + " " + rhString + " " + operatorString);
                        // logger.debug("Considering consequence:" + lhString +
                        // " " + rhString + " " + operatorString);

                        if ((lhString != null) && (lhString.equals("FORM")))
                        {
                            if (rhString != null)
                            {
                                if (rhString.equals("neg"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_negative = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling negative form");
                                        // logger.debug("Disabling negative form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_negative = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling negative form");
                                        // logger.debug("Enabling negative form");
                                    }
                                }
                                if (rhString.equals("pos"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_positive = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling positive form");
                                        // logger.debug("Disabling positive form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_positive = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling positive form");
                                        // logger.debug("Enabling positive form");
                                    }
                                }
                                if (rhString.equals("past"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_past = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling past form");
                                        // logger.debug("Disabling past form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_past = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling past form");
                                        // logger.debug("Enabling past form");
                                    }
                                }
                                if (rhString.equals("present"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_present = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling present form");
                                        // logger.debug("Disabling present form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_present = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling present form");
                                        // logger.debug("Enabling present form");
                                    }
                                }
                                if (rhString.equals("plain"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_plain = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling plain form");
                                        // logger.debug("Disabling plain form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_plain = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling plain form");
                                        // logger.debug("Enabling plain form");
                                    }
                                }
                                if (rhString.equals("polite"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_polite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling polite form");
                                        // logger.debug("Disabling polite form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_polite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling polite form");
                                        // logger.debug("Enabling polite form");
                                    }
                                }
                                if (rhString.equals("superpolite"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_superpolite = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling superpolite form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_superpolite = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling superpolite form");
                                    }
                                }
                                if (rhString.equals("crude"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_crude = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling crude form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_crude = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling crude form");
                                    }
                                }
                                if (rhString.equals("humble"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_humble = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling humble form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_humble = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling humble form");
                                    }
                                }
                                if (rhString.equals("question"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_question = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling question form");
                                        // logger.debug("Disabling question form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_question = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling question form");
                                        // logger.debug("Enabling question form");
                                    }
                                }
                                if (rhString.equals("statement"))
                                {
                                    if ((operatorString != null) && (operatorString.equals("off")))
                                    {
                                        l_statement = false;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Disabling statement form");
                                        // logger.debug("Disabling statement form");
                                    }
                                    else if ((operatorString != null) && (operatorString.equals("on")))
                                    {
                                        l_statement = true;
                                        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE,
                                        // CALL_debug.DEBUG,
                                        // "Enabling statement form");
                                        // logger.debug("Enabling statement form");
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }

        // STEP 2: FROM THE DETERMINED OPTIONS, PICK THE SETTINGS
        // ==========================================================

        Vector<CALL_formInstanceStruct> formSign = new Vector<CALL_formInstanceStruct>();
        Vector<CALL_formInstanceStruct> formTense = new Vector<CALL_formInstanceStruct>();
        Vector<CALL_formInstanceStruct> formStyle = new Vector<CALL_formInstanceStruct>();
        Vector<CALL_formInstanceStruct> formType = new Vector<CALL_formInstanceStruct>();
        Vector<CALL_formInstanceStruct> result = new Vector<CALL_formInstanceStruct>();
        //
        // Sign
        if ((l_positive) && (l_negative))
        {
            for (int i = 0; i < 2; i++) {
                formInstance = new CALL_formInstanceStruct();
                // We are allowing either option
                if (i == 0)
                {

                    formInstance.sign = POSITIVE;
                }
                else
                {
                    formInstance.sign = NEGATIVE;
                }
                // Add setting
                formSign.add(formInstance);
            }
        }
        else if (l_positive)
        {
            formInstance = new CALL_formInstanceStruct();
            // Can only be positive
            formInstance.sign = POSITIVE;
            // Add setting
            formSign.add(formInstance);
        }
        else
        {
            formInstance = new CALL_formInstanceStruct();
            // Well, assume negative then!
            formInstance.sign = NEGATIVE;
            // Add setting
            formSign.add(formInstance);
        }

        //
        // Tense
        if ((l_past) && (l_present))
        {
            for (int j = 0; j < 2; j++) {
                formInstance = new CALL_formInstanceStruct();
                // We are allowing either option
                if (j == 0)
                {
                    formInstance.tense = PAST;
                }
                else
                {
                    formInstance.tense = PRESENT;
                }
                formTense.add(formInstance);
            }

        }
        else if (l_past)
        {
            formInstance = new CALL_formInstanceStruct();
            // Can only be past
            formInstance.tense = PAST;
            formTense.add(formInstance);
        }
        else
        {
            formInstance = new CALL_formInstanceStruct();
            // Well, assume present then!
            formInstance.tense = PRESENT;
            formTense.add(formInstance);
        }

        // Politeness - 3 options for this currently
        choices = 0;

        if (l_polite)
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + POLITE);
            options[choices] = POLITE;
            choices++;
        }
        if (l_plain)
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + PLAIN);
            options[choices] = PLAIN;
            choices++;
        }
        if (l_crude)
        {
            // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
            // "Form. Option " + choices + " set to " + CRUDE);
            options[choices] = CRUDE;
            choices++;
        }

        if (choices == 0)
        {
            formInstance = new CALL_formInstanceStruct();
            // Have to return something even if they all seem disabled!
            formInstance.style = POLITE;
        }
        else
        {
            int count = 0;
            for (int i = 0; i < options.length; i++) {
                formInstance = new CALL_formInstanceStruct();
                if (options[i] != -1) {
                    formInstance.style = options[i];
                    formStyle.add(formInstance);
                } else {
                    // not found
                    count++;
                }
            }
            if (count == options.length) {
                formInstance = new CALL_formInstanceStruct();
                formInstance.style = POLITE;
                formStyle.add(formInstance);
            }
        }

        // Some debug!
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Form: Picked " + formInstance.style);

        // Question
        if ((l_question) && (l_statement))
        {
            for (int k = 0; k < 2; k++) {
                formInstance = new CALL_formInstanceStruct();
                // We are allowing either option
                if (k == 0)
                {
                    formInstance.type = QUESTION;
                }
                else
                {
                    formInstance.type = STATEMENT;
                }
                formType.add(formInstance);
            }

        }
        else if (l_question)
        {
            formInstance = new CALL_formInstanceStruct();
            // Can only be a question
            formInstance.type = QUESTION;
            formType.add(formInstance);
        }
        else
        {
            formInstance = new CALL_formInstanceStruct();
            // Well, assume statement then!
            formInstance.type = STATEMENT;
            formType.add(formInstance);
        }

        for (CALL_formInstanceStruct signStruct : formSign) {
            for (CALL_formInstanceStruct tenseStruct : formTense) {
                for (CALL_formInstanceStruct styleStruct : formStyle) {
                    for (CALL_formInstanceStruct typeStruct : formType) {
                        formInstance = new CALL_formInstanceStruct();
                        formInstance.sign = signStruct.sign;
                        formInstance.tense = tenseStruct.tense;
                        formInstance.style = styleStruct.style;
                        formInstance.type = typeStruct.type;
                        result.add(formInstance);
                    }
                }
            }
        }

        // Now return the completed instance
        // logger.debug("Picked Form Instance: "+formInstance.toString());
        return result;

    }

    public CALL_formInstanceStruct getFullFormInstance(CALL_formStruct form) {

        // Local copies of the settings - we may need to change them due to
        // conditions
        boolean l_positive = positive;
        boolean l_negative = negative;
        boolean l_past = past;
        boolean l_present = present;
        boolean l_plain = plain;
        boolean l_polite = polite;
        boolean l_superpolite = superpolite;
        boolean l_crude = crude;
        boolean l_humble = humble;
        boolean l_question = question;
        boolean l_statement = statement;

        // get the whole CALL_formInstanceStruct
        CALL_formInstanceStruct formInstance = new CALL_formInstanceStruct();
        if ((l_positive) && (l_negative)) {
            // We are allowing either option
            formInstance.sign = -1;

        } else if (l_positive) {
            // Can only be positive
            formInstance.sign = POSITIVE;
        } else {
            // Well, assume negative then!
            formInstance.sign = NEGATIVE;
        }

        // Tense
        if ((l_past) && (l_present)) {
            // We are allowing either option

            formInstance.tense = -1;

        } else if (l_past) {
            // Can only be past
            formInstance.tense = PAST;
        } else {
            // Well, assume present then!
            formInstance.tense = PRESENT;
        }

        if (l_polite && l_plain) {
            formInstance.style = -1;
        } else if (l_polite) {
            // Have to return something even if they all seem disabled!
            formInstance.style = POLITE;
        } else {
            formInstance.style = PLAIN;
        }

        // Question
        if ((l_question) && (l_statement)) {

            formInstance.type = -1;

        } else if (l_question) {
            // Can only be a question
            formInstance.type = QUESTION;
        } else {
            // Well, assume statement then!
            formInstance.type = STATEMENT;
        }

        // Now return the completed instance
        return formInstance;
    }

    public void print_debug() {
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Positive: " + positive);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Negative: " + negative);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Past: " +
        // past);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Present: " + present);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Plain: "
        // + plain);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Polite: "
        // + polite);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Keego: "
        // + superpolite);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Crude: "
        // + crude);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Question: " + question);
        // CALL_debug.printlog(CALL_debug.MOD_SENTENCE, CALL_debug.DEBUG,
        // "Statement: " + statement);
    }

    public String toString4Debug() {
        String str = "";
        str = str + " " + "Positive: " + positive;
        str = str + " " + "Negative: " + negative;
        str = str + " " + "Past: " + past;
        str = str + " " + "Present: " + present;
        str = str + " " + "Plain: " + plain;
        str = str + " " + "Polite: " + polite;
        str = str + " " + "Question: " + question;
        str = str + " " + "Statement: " + statement;
        return str;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public boolean isPlain() {
        return plain;
    }

    public void setPlain(boolean plain) {
        this.plain = plain;
    }

    public boolean isPolite() {
        return polite;
    }

    public void setPolite(boolean polite) {
        this.polite = polite;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isQuestion() {
        return question;
    }

    public void setQuestion(boolean question) {
        this.question = question;
    }

    public boolean isStatement() {
        return statement;
    }

    public void setStatement(boolean statement) {
        this.statement = statement;
    }

    public boolean isSuperpolite() {
        return superpolite;
    }

    public void setSuperpolite(boolean superpolite) {
        this.superpolite = superpolite;
    }
}