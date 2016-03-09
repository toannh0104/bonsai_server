///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.util.Vector;

public class CALL_diagramComponentStruct {
  // Statics
  static final int TYPE_IMAGE = 0;
  static final int TYPE_TEXT = 1;
  static final int UNINITIALISED = -999;

  // Co-ordinates and preferred size
  int x;
  int y;
  int w;
  int h;

  String name; // Name of component (obj1, misc1 etc)
  String text; // Text representing component - will override the slot text
               // information for tool-tips if set, and used for labelling too
  String mText; // A text string used for labeling the marker
  String slot; // The slot in the concept instance that the picture gets it's
               // graphics from (obj1 etc)
  String image; // Default image - used if no slot specified (background etc)
  String marker; // The name of the component marker, if used

  int type; // 0: Image, 1: Tex

  Vector restrictions; // Strings, representing the restrictions
  Vector settings; // A vector of CALL_stringPairsStructs, matching the vector
                   // above to show settings for each restriction

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

  public String getmText() {
    return mText;
  }

  public String getSlot() {
    return slot;
  }

  public String getImage() {
    return image;
  }

  public String getMarker() {
    return marker;
  }

  public int getType() {
    return type;
  }

  public Vector getRestrictions() {
    return restrictions;
  }

  public CALL_diagramComponentStruct() {
    init();
  }

  public CALL_diagramComponentStruct(String str) {
    init();
    name = str;
  }

  public void init() {
    x = UNINITIALISED;
    y = UNINITIALISED;
    w = UNINITIALISED;
    h = UNINITIALISED;
    type = UNINITIALISED;
    text = null;
    mText = null;
    name = null;
    slot = null;
    image = null;
    marker = null;
    restrictions = new Vector();
    settings = new Vector();
  }

  public void print_debug() {
    String tempString;
    CALL_stringPairsStruct rsettings;
    CALL_stringPairStruct parm;

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
    // "-->Component:" + name);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->Text:" +
    // text);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->MText:"
    // + mText);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->Slot:" +
    // slot);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->Image:"
    // + image);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->Image:"
    // + marker);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "-->Type:" +
    // type);
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
    // "-->Coords: [" + x + "," + y + "," + w + "," + h + "]");

    // If there are alternatives, print them too
    if (restrictions.size() > 0) {
      for (int i = 0; i < restrictions.size(); i++) {
        tempString = (String) restrictions.elementAt(i);
        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
        // "-->Restriction: " + tempString);

        if (i >= settings.size()) {
          // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.ERROR,
          // "---->Setting array size only [ " + settings.size() +
          // "], Restrictions: [" + restrictions.size() + "]");
        } else {

          rsettings = (CALL_stringPairsStruct) settings.elementAt(i);

          for (int j = 0; j < rsettings.size(); j++) {
            parm = rsettings.getPair(j);
            if (parm != null) {
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "---->(" + parm.parameter + " | " + parm.value + ")");
            }
          }
        }
      }
    }
  }
}