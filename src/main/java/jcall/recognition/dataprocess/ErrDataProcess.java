/**
 * Created on 2007/05/03
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import jcall.recognition.database.DataManager;
import jcall.recognition.database.WordErrDataMeta;

public class ErrDataProcess {

  BufferedReader br;
  PrintWriter pw;
  int state;
  static final int STATE_NEW_SESSION = 0;
  static final int STATE_NEW_LESSON = 1;
  static final int STATE_NEXT_QUESTION = 2;
  static final int STATE_ERROR_DETAIL = 3;
  static final int STATE_SENTENCE = 4;
  static final int STATE_QUESTION_END = 5;
  static final int STATE_ERROE_SUBSTITION = 6;
  // static final int STATE_QUESTION_END = 7;
  // static final int STATE_QUESTION_END = 8;
  // NOUN DEFINITIVE DIGIT
  static final String[] NOUN_OLDTYPE = { "WRONGFORM", "SUBS1", "SUBS2", "SUBS3", "SUBS4", "SUBS5", "NTDERIV", "ERRW1",
      "ERRW2", "ERRW3_Ge", "ERRW3_ONEEDIT", "SUBS1-ERRW1", "SUBS1-ERRW4" };// 13
  static final String[] NOUN_NEWTYPE = { "VDG_STYLE", "VDG", "VDG_ELABEL", "VDOUT", "VDOUT", "VOTHER", "INVS_WFORM",
      "INVS_PCE_JONEEDIT", "INVOUT_J", "INVOUT_E", "INVS_PCE_EONEEDIT", "INVDG_PCE_JONEEDIT", "INVDG_WFORM" };// 13
  static final String[] VERB_OLDTYPE = { "WRONGFORM", "SUBS1_VSFORM", "SUBS1_VDFORM", "SUBS4", "SUBS5", "INVALIDFORM",
      "INVALIDFORM_WRONGW", "ERRW2" };// 8
  static final String[] VERB_NEWTYPE = { "VS_DFORM", "VDG_SFORM", "VDG_DFORM", "VDOUT", "VOTHER", "INVS_REF",
      "INVDOUT", "INVDOUT" };// 8
  static final String[] PARTICAL_OLDTYPE = { "SUBS1_ALT1", "SUBS1_ALT2", "SUBS1_ALT3" };
  static final String[] PARTICAL_NEWTYPE = { "VDG_ALT1", "VDG_ALT2", "VDG_ALT3" };
  static final String[] DIGIT_OLDTYPE = { "QUANTITY", "QUANTITY2", "SUBS1", "SUBS1_ONEEDIT", "INVALIDFORM_ERRW1",
      "WRONGNUMBER", "OTHER" };// 7
  static final String[] DIGIT_NEWTYPE = { "QUANTITY", "QUANTITY2", "VDG", "INVDG_PCE", "INVS_PCE", "VDOUT_WNUM",
      "INVOUT" };// 7

  DataManager dm = new DataManager();
  String strTableName = "Sentences";
  String strwordTableName = "WordErrTable";

  public ErrDataProcess() {

  }

  /**
   * /* CHANGE THE WRONG TYPE
   */
  public void wrongTypeProcess() {
    // delete all the data and create such a new table
    dm.connectToAccess("history");
    // strwordTableName
    // read data into WordErrDataMeta one by one, change attribute on the basis
    // of its type

    try {
      WordErrDataMeta objEDMeta;
      ResultSet tmpRs = dm.searchTable(strwordTableName, "");
      while (tmpRs.next()) {
        objEDMeta = new WordErrDataMeta();
        // objEDMeta.intID = tmpRs.getInt("ID");
        objEDMeta.strTargetType = tmpRs.getString("TargetType");
        objEDMeta.strSpecificType = tmpRs.getString("SpecificType");

        String strDetailType = objEDMeta.strSpecificType;
        if (strDetailType.equalsIgnoreCase("NULL") || strDetailType.equalsIgnoreCase("CORRECT")
            || strDetailType.equalsIgnoreCase("SENTENCE")) {
          System.out.println("need not change, type is [" + strDetailType + "]");
        } else {
          // type parsing

        }

      }

    } catch (Exception e) {
      System.out.println("Exception in wrongTypeProcess of CBPManager " + e.getMessage());
      e.printStackTrace();
    }

    dm.releaseConn();
  }

  public static void main(String args[]) throws IOException, Exception {
    /*
     * 
     * DataManager dm = new DataManager(); dm.connectToAccess("history"); String
     * strTableName = "Sentences"; String strTableSetup = "(" +
     * "ID Counter PRIMARY KEY, "; strTableSetup = strTableSetup+
     * "StudentID int, "; strTableSetup = strTableSetup+ "Lesson VarChar(50), ";
     * strTableSetup = strTableSetup+ "Question VarChar(50) NOT NULL, ";
     * strTableSetup = strTableSetup+ "InputSentence VarChar(200) NOT NULL, ";
     * strTableSetup = strTableSetup+ "TargetSentence VarChar(200) NOT NULL, ";
     * strTableSetup = strTableSetup+ "SentenceLevelError VarChar(50)"+")";
     * dm.TruncateTable(strTableName,strTableSetup); dm.releaseConn();
     */
    ErrDataProcess edp = new ErrDataProcess();
    // edp.sentenceDataProcess();
    // edp.DataProcessTest();
    // edp.wordDataProcess();
  }

}
