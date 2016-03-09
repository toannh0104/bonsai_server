///////////////////////////////////////////////////////////////////
// Holds the information about the concept diagram
//
//

package jcall;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import java.io.*;
import java.util.*;

public class CALL_diagramStruct {
  boolean vcentering; // Whether to perform final vertical centering on the
                      // diagram at draw-time
  String name; // Diagram name (used for selecting appropriate diagram template)
  Vector gfxGroups; // Of CALL_diagramGroupStruct objects
  String question; // The question to ask for all cases of this diagram (best
                   // left to lesson struct???)

  public Vector getGfxGroups() {
    return gfxGroups;
  }

  public String getName() {
    return name;
  }

  static final int MAX_GROUPS = 32;

  public CALL_diagramStruct() {
    // Initialise data
    name = null;
    gfxGroups = new Vector();
    question = null;
    vcentering = false;
  }

  public CALL_diagramStruct(String pname) {
    // Initialise data
    name = pname;
    gfxGroups = new Vector();
    question = null;
    vcentering = false;
  }

  public CALL_diagramStruct(String pname, boolean centering) {
    // Initialise data
    name = pname;
    gfxGroups = new Vector();
    question = null;
    vcentering = centering;
  }

  // Used to define groups of elements - used in centering (each group centered
  // individually)
  public CALL_diagramGroupStruct add_group(boolean centering) {
    CALL_diagramGroupStruct newGroup;

    newGroup = new CALL_diagramGroupStruct();
    newGroup.setCentering(centering);
    gfxGroups.addElement(newGroup);

    return newGroup;
  }

  // Used to define groups of elements - used in centering (each group centered
  // individually)
  public void add_question(String q) {
    question = new String(q);
  }

  public String getCompleteQuestion() {
    StringTokenizer st;
    String tempStr;
    String insertStr;
    String typeStr;
    String finalString;

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
    // "Converting template question [" + question + "] to full question");

    finalString = new String("");

    if (question != null) {
      st = new StringTokenizer(question);

      while (st.hasMoreTokens()) {
        tempStr = st.nextToken();
        if (tempStr.startsWith("[")) {
          if (tempStr.length() > 2) {
            // Have a keyword to be substituted
            typeStr = tempStr.substring(1, tempStr.length() - 1);
            insertStr = getComponentLabel(typeStr);
            if (insertStr != null) {
              finalString = finalString + insertStr + " ";
            }
          }
        } else {
          finalString = finalString + tempStr + " ";
        }
      }
    }
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
    // "Returning [" + finalString + "]");
    return finalString;
  }

  public String getComponentLabel(String comp_type) {
    String rString;
    CALL_diagramGroupStruct group;

    rString = null;

    for (int i = 0; i < gfxGroups.size(); i++) {
      group = (CALL_diagramGroupStruct) gfxGroups.elementAt(i);
      if (group != null) {
        rString = group.getComponentLabel(comp_type);
      }

      if (rString != null)
        break;
    }

    return rString;
  }

  public void clearAllComponentData() {
    // Clears just the graphics of a component - the X,Y and class information
    // remain, ready to be used again
    // gfxGroups.clear();
    question = null;
  }

  // Add a picture to a component slot - search through all the groups for
  // matching slot name.
  public boolean addComponentData(String img_class, String img_name, String name) {
    boolean found = false;
    CALL_diagramGroupStruct group;

    for (int i = 0; i < gfxGroups.size(); i++) {
      group = (CALL_diagramGroupStruct) gfxGroups.elementAt(i);
      if (group != null) {
        if (group.addComponentData(img_class, img_name, name)) {
          found = true;
          break;
        }
      }
    }
    return found;
  }

  public Integer[][] get_centering_vector(int screenw, int screenh) {
    Integer trans[][];
    CALL_diagramGroupStruct group;
    CALL_diagramComponentStruct component;

    int miny = 99999;
    int maxy = -1;

    trans = new Integer[MAX_GROUPS][2];

    // Get the translation vector per group (X-Axis)
    for (int i = 0; i < gfxGroups.size(); i++) {
      group = (CALL_diagramGroupStruct) gfxGroups.elementAt(i);
      if (group != null) {
        if (group.isCentering()) {
          trans[i][0] = group.get_X_centering_vector(screenw);
        } else {
          trans[i][0] = 0;
        }

        // Get Y limits
        for (int j = 0; j < group.components.size(); j++) {
          component = (CALL_diagramComponentStruct) group.components.elementAt(j);
          if (component != null) {
            if (component.slot != null) {
              if (component.slot.compareTo(CALL_diagramGroupStruct.BLANKIMAGE) != 0) {
                if (component.y < miny)
                  miny = component.y;
                if ((component.y + component.h) > maxy)
                  maxy = component.y + component.h;
              }
            }
          }
        }
      }
    }

    // Y Translation vector
    trans[0][1] = ((screenh - (maxy - miny)) / 2) - miny;
    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
    // "Recentered Y: Screenh [" + screenh + "], miny [" + miny + "], maxy [" +
    // maxy + "], T:[" + trans[0][1] + "]");

    return trans;
  }

  public void singleWord() {
    CALL_diagramGroupStruct gfxGroup;
    CALL_diagramComponentStruct component;

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.DEBUG,
    // "Creating Single Word diagram");

    // Create the diagram struct always used for vocab practice (ie. the single
    // word image
    vcentering = false;
    name = new String("SingleWord"); // Diagram name (used for selecting
                                     // appropriate diagram template)

    // 1. Create the graphics group 1 (for the grapics)
    // ======================================
    gfxGroup = new CALL_diagramGroupStruct();
    gfxGroup.autocentering = true;

    // Create the graphics component
    component = new CALL_diagramComponentStruct();
    component.mText = new String("Please give the Japanese for this image");
    component.slot = new String("[" + CALL_conceptInstanceStruct.SINGLE_WORD_SLOT + "]");
    component.x = 125;
    component.y = 85;
    component.w = 200;
    component.h = 200;
    gfxGroup.components.addElement(component);
    gfxGroups.addElement(gfxGroup);

    // 2. Create the graphics group 2 (for the text)
    // ===================================
    gfxGroup = new CALL_diagramGroupStruct();
    gfxGroup.autocentering = true;

    // The text component
    component = new CALL_diagramComponentStruct();
    component.text = new String("[WORD]");
    component.x = 25;
    component.y = 5;
    component.w = 700;
    component.h = 24;
    gfxGroup.components.addElement(component);
    gfxGroups.addElement(gfxGroup);

    question = new String("What is this \"[WORD]\" in Japanese?");
  }

  public void print_debug() {
    CALL_diagramGroupStruct group;

    // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
    // "Outputting Diagram Debug, diagram [" + name + "]");
    if (question != null) {
      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
      // "Diagram Question: " + question);
    }

    for (int i = 0; i < gfxGroups.size(); i++) {
      group = (CALL_diagramGroupStruct) gfxGroups.elementAt(i);
      if (group != null) {
        group.print_debug();
      }
    }
  }
}