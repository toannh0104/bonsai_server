///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.util.Vector;

public class CALL_diagramGroupStruct {
  // Fields - will want to make these more dynamic!
  boolean autocentering;

  // Components
  Vector components; // Of type CALL_diagramComponentStruct

  // Statics
  static final String BLANKIMAGE = "BLANK";
  static final String INVISIBLEIMAGE = "INVISIBLE";
  static final int CIMAGE = 0;
  static final int CTEXT = 1;

  public Vector getComponents() {
    return components;
  }

  public void setComponents(Vector components) {
    this.components = components;
  }

  public CALL_diagramGroupStruct() {
    // Initialise arrays
    components = new Vector();

    autocentering = false;
  }

  public String getComponentLabel(String slot) {
    String returnString = null;
    CALL_diagramComponentStruct component;

    for (int i = 0; i < components.size(); i++) {
      component = (CALL_diagramComponentStruct) components.elementAt(i);
      if (component != null) {
        // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
        // "Comparing [" + component.slot +"] with [" + slot +"]");
        if (component.slot != null) {
          if (component.slot.compareTo(slot) == 0) {
            returnString = new String(component.name);
            break;
          }
        }
      }
    }

    return returnString;
  }

  public void setCentering(boolean val) {
    autocentering = val;
  }

  public boolean isCentering() {
    return autocentering;
  }

  public void clearAllComponentData() {
    // Clears just the graphics of a component - the X,Y and class information
    // remain, ready to be used again
    components.clear();
  }

  // This is filling in a slot in an existing component
  public boolean addComponentData(String slotName, String imgName, String name) {
    boolean found = false;
    CALL_diagramComponentStruct component;

    for (int i = 0; i < components.size(); i++) {
      component = (CALL_diagramComponentStruct) components.elementAt(i);
      if (component != null) {
        if (component.slot.compareTo(slotName) == 0) {
          found = true;
          component.image = slotName;
          component.name = name;
          break;
        }
      }
    }

    return found;
  }

  public Integer get_X_centering_vector(int screenw) {
    int minx = 99999;
    int maxx = -1;
    int width = 0;
    int ix;
    Integer trans;
    CALL_diagramComponentStruct component;

    // Get the boundries of all the components
    for (int i = 0; i < components.size(); i++) {
      component = (CALL_diagramComponentStruct) components.elementAt(i);
      if (component != null) {
        if (component.image != null) {
          if (component.image.compareTo(CALL_diagramGroupStruct.BLANKIMAGE) != 0) {
            if (component.x < minx)
              minx = component.x;
            if (component.x + component.w > maxx)
              maxx = component.x + component.w;
          }
        }
      }
    }

    width = maxx - minx;

    // Work out translation vector
    ix = (screenw - width) / 2;

    // Have to change this - look at re-scalling if image larger than display
    // area
    if (ix < 0)
      ix = 0;

    trans = new Integer(ix - minx);

    return trans;
  }

  public void print_debug() {
    CALL_diagramComponentStruct component;

    if (autocentering) {
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
      // "GFX Group. Auto-Centering");
    } else {
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
      // "GFX Group. No Centering");
    }

    for (int i = 0; i < components.size(); i++) {
      component = (CALL_diagramComponentStruct) components.elementAt(i);
      if (component != null) {
        component.print_debug();
      }
    }

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO, "GFX End");
  }
}