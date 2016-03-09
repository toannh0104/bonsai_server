/**
 * Created on 2008/06/17
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.analysis;

import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class HintStruct {

  int studentID;
  int lesson;
  String Question;
  String TargetS;
  String TargetW; // final surface form;
  String hintLevel;

  int component;
  int clickTimes; // for the intermediate level hints
  int allTimes; // for the intermediate level hints
  String desc;
  Vector hints;

  public HintStruct() {

    studentID = -1;
    lesson = -1;
    Question = "-1";
    TargetS = "";
    TargetW = "";
    hintLevel = "-1";

    component = -1;
    clickTimes = -1;
    allTimes = -1;
    desc = "";
    hints = null;
  }

  public Vector getHints() {
    return hints;
  }

  public void setHints(Vector hints) {
    this.hints = hints;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getHintLevel() {
    return hintLevel;
  }

  public void setHintLevel(String hintLevel) {
    this.hintLevel = hintLevel;
  }

  public int getLesson() {
    return lesson;
  }

  public void setLesson(int lesson) {
    this.lesson = lesson;
  }

  public String getQuestion() {
    return Question;
  }

  public void setQuestion(String question) {
    Question = question;
  }

  public int getStudentID() {
    return studentID;
  }

  public void setStudentID(int studentID) {
    this.studentID = studentID;
  }

  public String getTargetS() {
    return TargetS;
  }

  public void setTargetS(String targetS) {
    TargetS = targetS;
  }

  public String getTargetW() {
    return TargetW;
  }

  public void setTargetW(String targetW) {
    TargetW = targetW;
  }

  public int getAllTimes() {
    return allTimes;
  }

  public void setAllTimes(int allTimes) {
    this.allTimes = allTimes;
  }

  public int getClickTimes() {
    return clickTimes;
  }

  public void setClickTimes(int clickTimes) {
    this.clickTimes = clickTimes;
  }

  public int getComponent() {
    return component;
  }

  public void setComponent(int component) {
    this.component = component;
  }

  void setTW() {
    // System.out.println("Enter setTW");
    int index = 0;
    if (component < 0) {
      this.TargetW = "";
    }
    if (TargetS != null && TargetS.length() > 0) {
      StringTokenizer st = new StringTokenizer(TargetS);
      while (st.hasMoreTokens()) {
        String str = (String) st.nextToken();
        if (index == component) {
          this.TargetW = str;

          System.out.println("TargetW: " + str);

          // return str;
        }
        index++;
      }
    }
    // return null;

  }

  void setHLevel() {
    System.out.println("Enter setHLevel; desc: " + desc + " TargetW: " + TargetW);

    if (desc != null && desc.length() > 0) {
      char head = desc.charAt(0);
      char tail = desc.charAt(desc.length() - 1);
      String sHead = "" + head;
      String sTail = "" + tail;
      if (sTail.equals("*")) {
        if (sHead.equals("*")) {
          this.hintLevel = "length";
        } else {
          this.hintLevel = "intermediate";

        }
      } else {
        this.hintLevel = "baseform";

        if (this.TargetW.equals(desc)) {
          TargetS = TargetS.trim();
          String last = this.TargetS.substring(TargetS.lastIndexOf(" "));

          System.out.println("last: " + last);

          if (last.equals(TargetW)) {
            this.hintLevel = "final";
          }

        }

      }

    }

  }

  public String toString() {
    System.out.println("Enter toString");
    setTW();
    setHLevel();
    String ret = "";
    ret = ret + studentID + "	";
    ret = ret + this.lesson + "	";
    ret = ret + this.Question + "	";
    ret = ret + "[" + this.TargetS + "]" + "	";
    ret = ret + this.TargetW + "	";
    ret = ret + this.hintLevel + "	";

    ret = ret + this.component + "	\n";

    if (this.hints != null && this.hints.size() > 0) {

      for (int i = 0; i < hints.size(); i++) {
        HintStruct hsTemp = (HintStruct) hints.get(i);
        hsTemp.TargetS = this.TargetS;
        hsTemp.setTW();
        hsTemp.setHLevel();

        ret = ret + hsTemp.toString();

      }

    }

    return ret;

  }

  public void printHint(PrintWriter pw) {
    System.out.println("desc: " + desc);
    setTW();
    setHLevel();
    String ret = "";
    ret = ret + studentID + "	";
    ret = ret + this.lesson + "	";
    ret = ret + this.Question + "	";
    ret = ret + "[" + this.TargetS + "]" + "	";
    ret = ret + this.TargetW + "	";
    ret = ret + this.hintLevel + "	";

    ret = ret + this.component;

    pw.println(ret);

    if (this.hints != null && this.hints.size() > 0) {

      for (int i = 0; i < hints.size(); i++) {
        HintStruct hsTemp = (HintStruct) hints.get(i);
        hsTemp.TargetS = this.TargetS;
        hsTemp.setTW();
        hsTemp.setHLevel();
        pw.println();
        hsTemp.printHint(pw);

      }

    }
  }

  public void clear() {

    // studentID = -1;
    // lesson = -1;
    Question = "-1";
    TargetS = "";
    TargetW = "";
    hintLevel = "-1";

    component = -1;
    clickTimes = -1;
    allTimes = -1;
    desc = "";
    hints = null;

  }

}
