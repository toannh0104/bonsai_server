///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.util.StringTokenizer;
import java.util.Vector;

import jcall.config.ConfigInstant;
import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_Word;

import org.apache.log4j.Logger;

public class CALL_diagramInstanceStruct {
  static Logger logger = Logger.getLogger(CALL_diagramInstanceStruct.class.getName());

  // Static
  static final String EMPTY_STRING = new String("none");

  Vector center; // A vector of booleans, one for each group - do we center
                 // components?
  Vector image; // A vector of vectors. Index by group number, then component
  Vector marker; // A vector of vectors. Index by group number, then component
  Vector text; // A vector of vectors. Index by group number, then component
  Vector mText; // A vector of vectors. Index by group number, then component

  Vector chText; // Chinese Text;

  Vector x; // A vector of vectors. Index by group number, then component
  Vector y; // A vector of vectors. Index by group number, then component
  Vector w; // A vector of vectors. Index by group number, then component
  Vector h; // A vector of vectors. Index by group number, then component
  boolean vcentering;

  public Vector getCenter() {
    return center;
  }

  public void setCenter(Vector center) {
    this.center = center;
  }

  public Vector getImage() {
    return image;
  }

  public void setImage(Vector image) {
    this.image = image;
  }

  public Vector getMarker() {
    return marker;
  }

  public void setMarker(Vector marker) {
    this.marker = marker;
  }

  public Vector getText() {
    return text;
  }

  public void setText(Vector text) {
    this.text = text;
  }

  public Vector getmText() {
    return mText;
  }

  public void setmText(Vector mText) {
    this.mText = mText;
  }

  public Vector getChText() {
    return chText;
  }

  public void setChText(Vector chText) {
    this.chText = chText;
  }

  public Vector getX() {
    return x;
  }

  public void setX(Vector x) {
    this.x = x;
  }

  public Vector getY() {
    return y;
  }

  public void setY(Vector y) {
    this.y = y;
  }

  public Vector getW() {
    return w;
  }

  public void setW(Vector w) {
    this.w = w;
  }

  public Vector getH() {
    return h;
  }

  public void setH(Vector h) {
    this.h = h;
  }

  public boolean isVcentering() {
    return vcentering;
  }

  public void setVcentering(boolean vcentering) {
    this.vcentering = vcentering;
  }

  public CALL_database getDb() {
    return db;
  }

  public void setDb(CALL_database db) {
    this.db = db;
  }

  static final int MAX_GROUPS = 32;

  CALL_database db;

  public CALL_diagramInstanceStruct(CALL_questionStruct question, CALL_database data) {
    // logger.debug("Create the diagram instance, by question struct and db");

    db = data;
    init();
    generate(question);

    // For debugging purposes
    print_debug();
  }

  public void init() {
    // Initialise arrays
    center = new Vector();
    image = new Vector();
    marker = new Vector();
    text = new Vector();
    mText = new Vector();
    x = new Vector();
    y = new Vector();
    w = new Vector();
    h = new Vector();
    vcentering = false;

    chText = new Vector();
  }

  public boolean generate(CALL_questionStruct question) {
    // logger.debug("Enter CALL_diagramInstanceStruct generate");

    int cx, cy, cw, ch;
    CALL_diagramStruct diagram;
    CALL_diagramGroupStruct group;
    CALL_diagramComponentStruct component;
    CALL_conceptInstanceStruct instance;
    CALL_wordStruct word;
    CALL_stringPairsStruct strPairs;
    CALL_stringPairStruct strPair;
    CALL_formInstanceStruct form;

    Vector xVector;
    Vector yVector;
    Vector wVector;
    Vector hVector;
    Vector imageVector;
    Vector markerVector;
    Vector textVector;
    Vector chTextVector; // added
    Vector mTextVector;
    Vector nameVector;
    Vector wordVector;

    String restrictionString, slotString, subSlotString, operatorString, fillerString;
    String imageString, markerString, textString, nameString, tempString, componentString, mTextString, chTextString;
    String param, value, value1, value2, value3;
    StringTokenizer st, st2;
    Integer id;
    int tempx, tempy, tempw, temph;
    int index;
    boolean textSet;
    boolean match;
    boolean added;

    // Get the diagram template
    diagram = question.diagram;
    instance = question.instance;

    // deal with the label choice
    // Configuration configxml = Configuration.getConfig();
    // String strType = configxml.getItemValue("systeminfo", "laststudent");
    // String strLabelOption = configxml.getItemValue(strType, "label");
    // //logger.debug("strLabelOption: "+ strLabelOption);

    if (diagram == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.ERROR,
      // "Failed to generate diagram: no diagram struct!");
      // logger.debug( "Failed to generate diagram: no diagram struct!");

      return false;
    }

    if (instance == null) {
      // Error
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.ERROR,
      // "Failed to generate question instance: no instance struct!");
      // logger.debug(
      // "Failed to generate question instance: no instance struct!");
      return false;
    }

    // Set centering
    vcentering = diagram.vcentering;

    for (int i = 0; i < diagram.gfxGroups.size(); i++) {
      group = (CALL_diagramGroupStruct) diagram.gfxGroups.elementAt(i);
      added = false;

      if (group != null && group.components.size() > 0) {
        // Make vectors for the groups components here too
        xVector = new Vector();
        yVector = new Vector();
        wVector = new Vector();
        hVector = new Vector();
        imageVector = new Vector();
        markerVector = new Vector();
        textVector = new Vector();
        chTextVector = new Vector(); // added
        mTextVector = new Vector();
        nameVector = new Vector();

        for (int j = 0; j < group.components.size(); j++) {
          component = (CALL_diagramComponentStruct) group.components.elementAt(j);
          if (component != null) {
            // Name of the component
            nameString = component.name;
            // logger.debug("gfxGroups"+" ["+i+"] " + "components"+" ["+j+"]: "+
            // nameString);

            // Representation strings - AT LEAST ONE MUST BE FILLED FOR
            // COMPONENT TO BE ADDED LATER
            imageString = component.image;
            markerString = component.marker; // component.marker is the name of
                                             // the component marker
            // if component.text is null, then return null
            textString = instance.convertInstanceVariables(component.text, ConfigInstant.CONFIG_LabelOption_EN);

            chTextString = instance.convertInstanceVariables(component.text, ConfigInstant.CONFIG_LabelOption_CH);

            // logger.debug("Text string set as: "+ textString
            // +" chText string set as: "+ chTextString);
            // //logger.debug("If it is a picture, the above strings are both null");

            mTextString = instance.convertInstanceVariables(component.mText);
            // logger.debug("mText string set as: "+ mTextString);

            // Set the co-ordinates from component defaults
            cx = component.x;
            cy = component.y;
            cw = component.w;
            ch = component.h;

            // Now evaluate to get the image etc if set
            // First use the default component settings
            // NOTE: if text string is null, we assign slot data to textString
            componentString = component.slot;

            if (componentString != null) {
              if (componentString.startsWith("[")) {
                // We are looking for a slot
                index = componentString.indexOf("]");
                componentString = componentString.substring(1, index);

                for (int k = 0; k < instance.getLabel().size(); k++) {
                  slotString = (String) instance.getLabel().elementAt(k);

                  // // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                  // CALL_debug.DEBUG, "Comparing slot [" + componentString +
                  // "], to instance label [" + slotString + "]");

                  // logger.debug("Comparing slot [" + componentString +
                  // "], to instance label [" + slotString + "]");

                  if (slotString.equals(componentString)) {
                    // logger.debug("found the matching slot in the instance");
                    // We've found the matching slot in the instance
                    tempString = (String) instance.getData().elementAt(k);

                    if (component.text == null) {
                      // No text string set, so update

                      textString = tempString;
                      if (instance.getChData() != null && instance.getChData().size() > 0) {
                        chTextString = (String) instance.getChData().elementAt(k);
                      } else {
                        chTextString = tempString;
                        // logger.debug("No chinese data in the concept instance, SO WE SET CH==EN");
                      }

                      // logger.debug("No text string set, so update textString as: "+
                      // textString +" and chText as: "+ chTextString);
                    }

                    id = (Integer) instance.getId().elementAt(k);

                    if (id != -1) {
                      // Get image string from lexicon
                      word = db.lexicon.getWord(id.intValue());
                      if (word != null) {
                        if (word.picture.startsWith("<")) {
                          // This word doesn't have an assoicated picture
                          imageString = null;
                        } else {
                          imageString = word.picture;
                        }
                      } else {
                        logger.error("NO FIND of word in db.lexicon.getWord(" + id + ")");
                      }
                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                      // CALL_debug.DEBUG, "Getting component [" +
                      // componentString + "] image, " + imageString);
                      // logger.debug("Getting component(==slotString==) [" +
                      // slotString + "] image, " + imageString);

                    }

                    break;
                  }

                }
              }
            } else {
              // logger.debug("component.slot is NULL");
            }

            // logger.debug("then, check restrictions and change settings if necessary");

            // Then, check restrictions and change settings if necessary
            if (component.restrictions.size() > 0) {
              // logger.debug("Contain restrictions, process them:  "+
              // component.restrictions.size());
              for (int k = 0; k < component.restrictions.size(); k++) {
                match = false;
                restrictionString = (String) component.restrictions.elementAt(k);
                st = new StringTokenizer(restrictionString);
                slotString = CALL_io.getNextToken(st);

                if (slotString != null) {
                  // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                  // CALL_debug.INFO, "Restriction Slot: " + slotString);

                  // logger.debug("In component of Diagram, Restriction Slot: "
                  // + slotString);

                  if (slotString.equals("*")) {
                    // This restriction is always true
                    // A hack to allow the restriction processing in all cases,
                    // such as when we want to append/insert to the text string
                    // no matter what!
                    match = true;
                  } else {
                    if (slotString.startsWith("[")) {
                      operatorString = CALL_io.getNextToken(st);
                      if (operatorString != null) {
                        fillerString = CALL_io.getNextToken(st);
                        if (fillerString != null) {

                          // Case of checking concept token: De-bracketise the
                          // slot string
                          // -----------------------------------------------------
                          slotString = slotString.substring(1, slotString.length() - 1);

                          // Now from the instance, get the value for this slot
                          // string
                          slotString = instance.getDataString(slotString);
                          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                          // CALL_debug.INFO, "SlotString = [" + slotString
                          // +"]");

                          if (slotString != null) {

                            // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                            // CALL_debug.INFO, "Restriction: Comparing " +
                            // slotString + " with " + fillerString);

                            // Now make the check based on the operator
                            if (operatorString.equals("==")) {
                              if (slotString.equals(fillerString)) {
                                // A matching restriction
                                match = true;
                              }
                            } else if (operatorString.equals("!=")) {
                              if (!(slotString.equals(fillerString))) {
                                match = true;
                              }
                            }
                          } else {
                            if (fillerString.equals("NULL")) {
                              if (operatorString.equals("==")) {
                                // We were looking for a null, and got one!
                                match = true;
                              } else if (operatorString.equals("!=")) {
                                // We were looking for a non-null...no match
                                match = false;
                              }
                            }
                          }
                        }
                      } else {
                        // Just the key on it's own - this is a check for
                        // existence!
                        if (instance.getDataString(slotString) != null) {
                          match = true;
                        }
                      }
                    } else if (slotString.startsWith("{")) {
                      // This is for checking a form setting
                      // ==============================

                      // STEP 1: GET THE REFERENCED FORM FROM THE SENTENCE
                      // STRUCTURE
                      // --------------------------------------------------------------------
                      slotString = slotString.substring(1, slotString.length() - 1); // slotString
                                                                                     // is
                                                                                     // verbForm,etc.
                      form = question.sentence.getForm(slotString);
                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                      // CALL_debug.INFO, "Restriction: Based on Form for " +
                      // slotString + ":");

                      if (form != null) {
                        form.print_debug();

                        subSlotString = CALL_io.getNextToken(st);
                        if (subSlotString != null) {
                          operatorString = CALL_io.getNextToken(st);
                          if (operatorString != null) {
                            fillerString = CALL_io.getNextToken(st);
                            if (fillerString != null) {
                              // Case of checking grammar form used:
                              // De-bracketise the slot string
                              // ----------------------------------------------------------
                              if (question.sentence != null) {
                                slotString = slotString.substring(1, slotString.length() - 1);

                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Slot: " + subSlotString);

                                // Now check the conditions
                                if (subSlotString.equals("type")) {
                                  if (operatorString.equals("==")) {
                                    if (fillerString.equals("question")) {
                                      if (form.type == CALL_formStruct.QUESTION) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("statement")) {
                                      if (form.type == CALL_formStruct.STATEMENT) {
                                        match = true;
                                      }
                                    }
                                  } else if (operatorString.equals("!=")) {
                                    if (fillerString.equals("question")) {
                                      if (form.type == CALL_formStruct.STATEMENT) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("statement")) {
                                      if (form.type == CALL_formStruct.QUESTION) {
                                        match = true;
                                      }
                                    }
                                  }
                                } else if (subSlotString.equals("tense")) {
                                  if (operatorString.equals("==")) {
                                    if (fillerString.equals("past")) {
                                      if (form.tense == CALL_formStruct.PAST) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("present")) {
                                      if (form.tense == CALL_formStruct.PRESENT) {
                                        match = true;
                                      }
                                    }
                                  } else if (operatorString.equals("!=")) {
                                    if (fillerString.equals("past")) {
                                      if (form.tense == CALL_formStruct.PRESENT) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("present")) {
                                      if (form.tense == CALL_formStruct.PAST) {
                                        match = true;
                                      }
                                    }
                                  }
                                } else if (subSlotString.equals("sign")) {
                                  if (operatorString.equals("==")) {
                                    if (fillerString.equals("positive")) {
                                      if (form.sign == CALL_formStruct.POSITIVE) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("negative")) {
                                      if (form.sign == CALL_formStruct.NEGATIVE) {
                                        match = true;
                                      }
                                    }
                                  } else if (operatorString.equals("!=")) {
                                    if (fillerString.equals("positive")) {
                                      if (form.sign == CALL_formStruct.NEGATIVE) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("negative")) {
                                      if (form.sign == CALL_formStruct.POSITIVE) {
                                        match = true;
                                      }
                                    }
                                  }
                                } else if (subSlotString.equals("style")) {
                                  if (operatorString.equals("==")) {
                                    if (fillerString.equals("polite")) {
                                      if (form.style == CALL_formStruct.POLITE) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("plain")) {
                                      if (form.style == CALL_formStruct.PLAIN) {
                                        match = true;
                                      }
                                    }
                                  } else if (operatorString.equals("!=")) {
                                    if (fillerString.equals("polite")) {
                                      if (form.style == CALL_formStruct.PLAIN) {
                                        match = true;
                                      }
                                    } else if (fillerString.equals("plain")) {
                                      if (form.style == CALL_formStruct.POLITE) {
                                        match = true;
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      } else {
                        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                        // CALL_debug.WARN, "No matching form found");
                        // logger.debug("No matching form found");
                      }
                    }
                  }

                  if (match) {
                    // We have a matching restriction, so update the settings
                    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                    // CALL_debug.INFO, "Restriction Match!");
                    // logger.debug("Restriction Match!");

                    strPairs = (CALL_stringPairsStruct) component.settings.elementAt(k);
                    if (strPairs == null) {
                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                      // CALL_debug.WARN, "-- but no settings!");
                    } else {
                      for (int r = 0; r < strPairs.parameters.size(); r++) {
                        strPair = (CALL_stringPairStruct) strPairs.parameters.elementAt(r);
                        if (strPair != null) {
                          param = strPair.parameter;
                          value = strPair.value;
                          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                          // CALL_debug.INFO, "Considering setting [" + param +
                          // " | " + value + "]");
                          if ((param != null) && (value != null)) {
                            if (param.equals("coords")) {
                              tempx = CALL_io.INVALID_INT;
                              tempy = CALL_io.INVALID_INT;
                              tempw = CALL_io.INVALID_INT;
                              temph = CALL_io.INVALID_INT;

                              // We need 4 integer values
                              st2 = new StringTokenizer(value);
                              tempString = CALL_io.getNextToken(st2);
                              if (tempString != null) {
                                tempx = CALL_io.str2int(tempString);
                                tempString = CALL_io.getNextToken(st2);
                                if ((tempx != CALL_io.INVALID_INT) && (tempString != null)) {
                                  tempy = CALL_io.str2int(tempString);
                                  tempString = CALL_io.getNextToken(st2);
                                  if ((tempy != CALL_io.INVALID_INT) && (tempString != null)) {
                                    tempw = CALL_io.str2int(tempString);
                                    tempString = CALL_io.getNextToken(st2);
                                    if ((tempw != CALL_io.INVALID_INT) && (tempString != null)) {
                                      temph = CALL_io.str2int(tempString);
                                      if (temph != CALL_io.INVALID_INT) {
                                        // Use these overridden values
                                        cx = tempx;
                                        cy = tempy;
                                        cw = tempw;
                                        ch = temph;
                                        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                        // CALL_debug.INFO,
                                        // "Changed coordinates [" + cx + "," +
                                        // cy + "]");
                                      }
                                    }
                                  }
                                }
                              }
                            } else if (param.equals("image")) {
                              if (value != null) {
                                imageString = value;
                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Changed Image: " +
                                // imageString);
                              }
                            } else if (param.equals("text")) {
                              if (value != null) {
                                textString = instance.convertInstanceVariables(value,
                                    ConfigInstant.CONFIG_LabelOption_EN);
                                chTextString = instance.convertInstanceVariables(value,
                                    ConfigInstant.CONFIG_LabelOption_CH);

                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Changed Text: " +
                                // textString );
                                // logger.debug("Changed Text: " + textString
                                // +"chText: "+ chTextString);
                              }
                            } else if (param.equals("marker")) {
                              if (value != null) {
                                markerString = value;
                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Changed Marker: " +
                                // markerString);
                              }
                            } else if (param.equals("mtext")) {
                              if (value != null) {
                                mTextString = instance.convertInstanceVariables(value,
                                    ConfigInstant.CONFIG_LabelOption_EN);
                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Changed MText: " +
                                // mTextString);
                              }
                            } else if (param.equals("sub")) {
                              if (value != null) {
                                st = new StringTokenizer(value);
                                value1 = CALL_io.getNextToken(st);

                                String tmpStr = CALL_io.getNextToken(st);
                                value2 = instance.convertInstanceVariables(tmpStr, ConfigInstant.CONFIG_LabelOption_EN);
                                value3 = instance.convertInstanceVariables(tmpStr, ConfigInstant.CONFIG_LabelOption_CH);

                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.DEBUG, "Replacing text [" + value1
                                // + "] with [" + value2 + "]");
                                // logger.debug("Replacing text [" + value1 +
                                // "] with [" + value2 + "] or with ["
                                // +value3+"]");
                                if ((value1 != null) && (value2 != null)) {
                                  if (textString != null) {
                                    textString = CALL_io.strReplace(textString, value1, value2);

                                    String chstr = null;
                                    if (value3 != null) {
                                      if (value1.equalsIgnoreCase("****")) {
                                        chstr = value1;
                                        chTextString = CALL_io.strReplace(chTextString, chstr, value3);
                                      } else {

                                        JCALL_Lexicon lexi = JCALL_Lexicon.getInstance();
                                        JCALL_Word tmpword = lexi.getWordFmStrEnMeaning(value1);
                                        if (tmpword != null) {
                                          chstr = tmpword.getDChMeaning();
                                          if (!chstr.isEmpty()) {
                                            int inI = chstr.indexOf("(");
                                            if (inI != -1) {
                                              chstr = chstr.substring(0, inI);
                                            }
                                            chTextString = CALL_io.strReplace(chTextString, chstr, value3);
                                          }
                                        }
                                      }
                                    }
                                    if (chstr == null) {
                                      logger.error("NOT FIND the word, so no corret chTextString");
                                      // chTextString = textString;
                                    }

                                    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                    // CALL_debug.DEBUG, "New String [" +
                                    // textString + "]" +" chText String: [" +
                                    // chTextString + "]" );
                                  }
                                }
                              }
                              // logger.debug("sub; textString: ["+ textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("subS")) {
                              if (value != null) {
                                st = new StringTokenizer(value);
                                value1 = " " + CALL_io.getNextToken(st) + " ";
                                value2 = " "
                                    + instance.convertInstanceVariables(CALL_io.getNextToken(st),
                                        ConfigInstant.CONFIG_LabelOption_EN) + " ";
                                value3 = " "
                                    + instance.convertInstanceVariables(CALL_io.getNextToken(st),
                                        ConfigInstant.CONFIG_LabelOption_CH) + " ";

                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.DEBUG, "Replacing text [" + value1
                                // + "] with [" + value2 + "]");

                                if ((value1 != null) && (value2 != null)) {
                                  if (textString != null) {
                                    textString = CALL_io.strReplace(textString, value1, value2);

                                    String chstr = null;
                                    if (value3 != null) {
                                      if (value1.equalsIgnoreCase("****")) {
                                        chstr = value1;
                                        chTextString = CALL_io.strReplace(chTextString, chstr, value3);
                                      } else {

                                        JCALL_Lexicon lexi = JCALL_Lexicon.getInstance();
                                        JCALL_Word tmpword = lexi.getWordFmStrEnMeaning(value1);
                                        if (tmpword != null) {
                                          chstr = tmpword.getDChMeaning();
                                          // 2011.03.04 T.Tajima add
                                          if (chstr == null && chstr.isEmpty()) {
                                            chstr = new String("no chinese");
                                          }
                                          if (!chstr.isEmpty()) {
                                            int inI = chstr.indexOf("(");
                                            if (inI != -1) {
                                              chstr = chstr.substring(0, inI);
                                            }
                                            chTextString = CALL_io.strReplace(chTextString, chstr, value3);
                                          }
                                        }
                                      }
                                    }
                                    if (chstr == null) {
                                      logger.error("NOT FIND the word, so no corret chTextString");
                                      // chTextString = textString;
                                    }
                                    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                    // CALL_debug.DEBUG, "New String [" +
                                    // textString + "]");
                                  }
                                }
                              }
                              // logger.debug("subS; textString: ["+ textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("append")) {
                              if (value != null) {
                                if (textString != null) {
                                  textString = textString
                                      + instance.convertInstanceVariables(value, ConfigInstant.CONFIG_LabelOption_EN);

                                  // Deal with chinese text ...
                                  if (value.equals("...") || value.equals("?")) {
                                    chTextString = chTextString + value;
                                  } else {
                                    StringTokenizer stoken = new StringTokenizer(chTextString);
                                    String strEndOne = "";
                                    String strFront = "";
                                    for (int a = 0; a < stoken.countTokens(); a++) {
                                      if (a == stoken.countTokens() - 1) {
                                        strEndOne = stoken.nextToken();
                                      } else {
                                        strFront = stoken.nextToken() + " ";
                                      }
                                    }

                                    chTextString = strFront
                                        + instance.convertInstanceVariables(value, ConfigInstant.CONFIG_LabelOption_CH)
                                        + strEndOne;

                                  }

                                }
                              }
                              // logger.debug("append; textString: ["+
                              // textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("appendS")) {
                              // As above, but with leading space
                              if (value != null) {
                                if (textString != null) {
                                  textString = textString + " "
                                      + instance.convertInstanceVariables(value, ConfigInstant.CONFIG_LabelOption_EN);
                                  // chTextString =
                                  // instance.convertInstanceVariables(value,ConfigInstant.CONFIG_LabelOption_CH)
                                  // + " " + chTextString;

                                  // Deal with chinese text ...
                                  if (value.equals("...") || value.equals("?")) {
                                    chTextString = chTextString + value;
                                  } else {
                                    StringTokenizer stoken = new StringTokenizer(chTextString);
                                    String strEndOne = "";
                                    String strFront = "";
                                    for (int a = 0; a < stoken.countTokens(); a++) {
                                      if (a == stoken.countTokens() - 1) {
                                        strEndOne = stoken.nextToken();
                                      } else {
                                        strFront = stoken.nextToken() + " ";
                                      }
                                    }

                                    // chTextString = strFront +
                                    // instance.convertInstanceVariables(value,ConfigInstant.CONFIG_LabelOption_CH)+" "+strEndOne;
                                    chTextString = chTextString
                                        + instance.convertInstanceVariables(value, ConfigInstant.CONFIG_LabelOption_CH);
                                  }
                                }
                              }
                              // logger.debug("appendS; textString: ["+
                              // textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("insert")) {
                              if (value != null) {
                                if (textString != null) {
                                  textString = instance.convertInstanceVariables(value,
                                      ConfigInstant.CONFIG_LabelOption_EN) + textString;

                                  chTextString = instance.convertInstanceVariables(value,
                                      ConfigInstant.CONFIG_LabelOption_CH) + chTextString;
                                }
                              }
                              // logger.debug("insert; textString: ["+
                              // textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("insertS")) {
                              // As above, but with trailing space
                              if (value != null) {
                                if (textString != null) {
                                  textString = instance.convertInstanceVariables(value,
                                      ConfigInstant.CONFIG_LabelOption_EN) + " " + textString;
                                  chTextString = instance.convertInstanceVariables(value,
                                      ConfigInstant.CONFIG_LabelOption_CH) + " " + chTextString;
                                }
                              }
                              // logger.debug("insertS; textString: ["+
                              // textString
                              // +"] chTextString: ["+chTextString+"]");
                            } else if (param.equals("gfx_data")) {
                              if (value != null) {
                                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                // CALL_debug.INFO, "Changing slot: " + value);

                                // It's matching with a slot
                                if (value.startsWith("[")) {
                                  value = value.substring(1, value.length() - 1);

                                  for (int n = 0; n < instance.getLabel().size(); n++) {

                                    slotString = (String) instance.getLabel().elementAt(n);

                                    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                    // CALL_debug.INFO,
                                    // "Looking for filler...comparing [" +
                                    // slotString + " | " + value + "]");
                                    if (slotString.equals(value)) {
                                      // We've found the matching slot in the
                                      // instance
                                      tempString = (String) instance.getData().elementAt(n);
                                      if (component.text == null) {
                                        // Only update textString if not
                                        // explicity defined by component
                                        nameString = tempString;
                                        textString = tempString;

                                        chTextString = (String) instance.getChData().elementAt(n);
                                      }

                                      id = (Integer) instance.getId().elementAt(n);

                                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                      // CALL_debug.INFO, "Changed to: " +
                                      // tempString);

                                      if (id != -1) {
                                        // Get image string from lexicon
                                        word = db.lexicon.getWord(id.intValue());
                                        if (word != null) {
                                          if (word.picture.startsWith("<")) {
                                            // This word doesn't have an
                                            // assoicated picture
                                            imageString = null;
                                          } else {
                                            imageString = word.picture;
                                          }
                                          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                                          // CALL_debug.INFO, "Changed Image: "
                                          // + imageString);
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              } // Restriction handling
            } // end if have Restriction
              // Now we can add the graphics object if it's ok
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Considering adding object: " + nameString);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Text: " + textString);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "chText: " + chTextString);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Image: " + imageString);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "mText: " + mTextString);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "X: " + cx);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Y: " + cy);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "W: " + cw);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "H " + ch);

            // logger.debug("Considering adding object: " + nameString);
            // logger.debug("Text: " + textString);
            // logger.debug("chText: " + chTextString);
            // logger.debug("Image: " + imageString);
            // logger.debug("mText: " + mTextString);
            // //logger.debug( "X: " + cx);
            // //logger.debug("Y: " + cy);
            // //logger.debug("W: " + cw);
            // //logger.debug( "H " + ch);

            if ((textString != null) || (imageString != null)) {
              if ((cx != CALL_io.INVALID_INT) && (cy != CALL_io.INVALID_INT) && (cy != CALL_io.INVALID_INT)
                  && (cy != CALL_io.INVALID_INT)) {
                // If image string is null, replace with "none"
                if (imageString == null) {
                  imageString = CALL_diagramGroupStruct.BLANKIMAGE;
                }
                if (markerString == null) {
                  markerString = CALL_diagramGroupStruct.BLANKIMAGE;
                }
                if (textString == null) {
                  textString = new String(EMPTY_STRING);
                }

                if (chTextString == null) {
                  chTextString = new String(EMPTY_STRING);
                }

                if (nameString == null) {
                  nameString = new String(EMPTY_STRING);
                }

                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
                // "Adding gfx component: " + textString);
                // logger.debug("Adding gfx component: [" + nameString
                // +"] with text string: "+textString);

                imageVector.addElement(imageString);
                markerVector.addElement(markerString);
                textVector.addElement(textString);

                chTextVector.addElement(chTextString);

                mTextVector.addElement(mTextString);
                nameVector.addElement(nameString);

                xVector.addElement(new Integer(cx));
                yVector.addElement(new Integer(cy));
                wVector.addElement(new Integer(cw));
                hVector.addElement(new Integer(ch));
                added = true;
                // //logger.debug("Add the object: " + nameString);
              }
            } else {
              // logger.debug("WILL NOT Add gfx component: " + nameString);
            }

          } // end if(component != null)
        }// end for(int j = 0; j < group.components.size(); j++)

        if (added) {
          // Add vector to super-vector
          // ===========================================
          text.addElement(textVector);
          chText.addElement(chTextVector);

          mText.addElement(mTextVector);
          image.addElement(imageVector);
          marker.addElement(markerVector);
          x.addElement(xVector);
          y.addElement(yVector);
          w.addElement(wVector);
          h.addElement(hVector);

          if (group.autocentering) {
            // Centering
            center.addElement(new Integer(1));
          } else {
            // No centering
            center.addElement(new Integer(0));
          }
        }

      }// END if(group != null) && if(group.components.size() > 0)
    }// END for(int i = 0; i < diagram.gfxGroups.size(); i++)

    return true;
  }

  public void centering(int CWIDTH, int CHEIGHT, int WIDTH, int HEIGHT) {
    Vector xVector;
    Vector wVector;
    Vector yVector;
    Vector hVector;
    Vector textVector;
    Vector imgVector;

    Integer cx, cw, cy, ch, newX, newY;
    int dx, centerX, centerScreen, dy, centerY;
    int maxX, minX;
    int maxY, minY;
    Integer centering;
    String compName;

    int i, j;

    maxY = 0;
    minY = 99999;

    for (i = 0; i < x.size(); i++) {
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
      // "Looking to scale/center group:" + i);

      // i = Group Index
      xVector = (Vector) x.elementAt(i);
      wVector = (Vector) w.elementAt(i);
      yVector = (Vector) y.elementAt(i);
      hVector = (Vector) h.elementAt(i);
      textVector = (Vector) text.elementAt(i);
      imgVector = (Vector) image.elementAt(i);
      centering = (Integer) center.elementAt(i);
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG, "Group "
      // + i + " Centering: " + centering);

      // Need to reset this for each group
      maxX = 0;
      minX = 999;

      if (xVector != null) {
        for (j = 0; j < xVector.size(); j++) {
          // J = Component index within group
          compName = (String) textVector.elementAt(j);
          if (compName == null) {
            compName = new String("Undefined");
          }

          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
          // "Scaling component: " + compName);

          // First do scaling
          cx = (Integer) xVector.elementAt(j);
          cw = (Integer) wVector.elementAt(j);
          cy = (Integer) yVector.elementAt(j);
          ch = (Integer) hVector.elementAt(j);

          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
          // "Old co-ordinates: " + cx + ", " + cy);
          cx = ((cx * WIDTH) / CWIDTH);
          cy = ((cy * HEIGHT) / CHEIGHT);
          cw = ((cw * WIDTH) / CWIDTH);
          ch = ((ch * HEIGHT) / CHEIGHT);
          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
          // "New co-ordinates: " + cx + ", " + cy);
          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
          // "New dimensions: " + cw + ", " + ch);

          xVector.setElementAt(cx, j);
          yVector.setElementAt(cy, j);
          wVector.setElementAt(cw, j);
          hVector.setElementAt(ch, j);

          // Then centering - first, store max and mins in the vertical axis if
          // vcentering enabled
          if (vcentering) {
            if (cy.intValue() < minY) {
              minY = cy.intValue();
            }

            if ((cy.intValue() + ch.intValue()) > maxY) {
              maxY = (cy.intValue() + ch.intValue());
            }
          }

          // Then the horizontal (conditional at this point
          if (centering.intValue() != 0) {
            if ((cx != null) && (cw != null)) {
              if (cx.intValue() < minX) {
                minX = cx.intValue();
                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
                // "Group min x updated: " +minX + "(" + cx + ")");
              }
              if ((cx.intValue() + cw.intValue()) > maxX) {
                maxX = (cx.intValue() + cw.intValue());
                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
                // "Group max x updated: " +maxX + "(" + (cx + cw) + ")");
              }
            }
          }
        }

        if (centering.intValue() != 0) {
          // Now generate vector X
          centerX = (maxX + minX) / 2;
          centerScreen = (WIDTH / 2);
          dx = centerScreen - centerX;
          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
          // "Centering vector:" + dx);

          // Now apply dx to all the components in this group
          for (j = 0; j < xVector.size(); j++) {
            cx = (Integer) xVector.elementAt(j);
            if (cx != null) {
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
              // "Previous X:" + cx);
              newX = new Integer(cx.intValue() + dx);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
              // "New X:" +newX);
              xVector.setElementAt(newX, j);
            }
          }
        }
      }
    }

    // Now, do total vertical centering, if appropriate
    if (vcentering) {
      centerY = (maxY + minY) / 2;
      centerScreen = (HEIGHT / 2);
      dy = centerScreen - centerY;
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
      // "V-Centering vector:" + dy);

      for (i = 0; i < y.size(); i++) {
        yVector = (Vector) y.elementAt(i);

        for (j = 0; j < yVector.size(); j++) {
          cy = (Integer) yVector.elementAt(j);
          newY = new Integer(cy.intValue() + dy);
          yVector.setElementAt(newY, j);
        }
      }
    }
  }

  public void print_debug() {
    CALL_diagramGroupStruct group;
    String imageString;
    String textString;
    Integer cx, cy, cw, ch;

    Vector imageVector, textVector;
    Vector xVector, yVector, wVector, hVector;

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
    // "Diagram Information:");

    // For each group
    for (int i = 0; i < text.size(); i++) {
      // Get group vector
      textVector = (Vector) text.elementAt(i);
      imageVector = (Vector) image.elementAt(i);
      xVector = (Vector) x.elementAt(i);
      yVector = (Vector) y.elementAt(i);
      wVector = (Vector) w.elementAt(i);
      hVector = (Vector) h.elementAt(i);

      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
      // "-->Group " + i + ":");

      for (int j = 0; j < textVector.size(); j++) {
        // For each component in the group
        imageString = (String) imageVector.elementAt(j);
        textString = (String) textVector.elementAt(j);
        cx = (Integer) xVector.elementAt(j);
        cy = (Integer) yVector.elementAt(j);
        cw = (Integer) wVector.elementAt(j);
        ch = (Integer) hVector.elementAt(j);

        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
        // "---->Image:" + imageString);
        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
        // "---->Text:" + textString);
        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
        // "---->Co-ordinates:" + cx + "," + cy + "," + cw + "," + ch);
      }
    }
  }
}