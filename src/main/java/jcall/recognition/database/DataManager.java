/**
 * Created on 2007/02/06
 * @author wang
 * Copyrights @kawahara lab
 */

package jcall.recognition.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManager implements Serializable {

  static Connection conn;
  ResultSet rs;
  PreparedStatement prestmt;
  static Statement stmt;

  public DataManager() {
    prestmt = null;
    conn = null;
    rs = null;
    prestmt = null;
  }

  public void connectToAccess(String dbdnsName) {
    try {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      String url = "jdbc:odbc:" + dbdnsName;
      conn = DriverManager.getConnection(url);
      stmt = conn.createStatement();
    } catch (ClassNotFoundException e) {
      System.out.println("JdbcOdbcDriver not find: " + e.getMessage());
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
    }
  }

  public void connectToMysql(String dbName, String strUserName, String strPsw) {
    try {
      Class.forName("org.gjt.mm.mysql.Driver");
      String url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&characterEncoding=SJIS";
      conn = DriverManager.getConnection(url, "strUserName", "strPsw");
      // conn =
      // DriverManager.getConnection("jdbc:mysql:JCALL?useUnicode=true&characterEncoding=SJIS");
      stmt = conn.createStatement();
    } catch (ClassNotFoundException e) {
      System.out.println("JdbcOdbcDriver not find: " + e.getMessage());
    } catch (SQLException e) {
      System.out.println("SQLException: " + e.getMessage());
    }
  }

  public int executeUpdate(String sql) {
    int rowcount = -1;
    try {
      if (stmt == null) {
        stmt = conn.createStatement();
      }
      rowcount = stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      System.err.println("sqlDeal.executeUpdate:".concat(String.valueOf(String.valueOf(ex.getMessage()))));
      ex.printStackTrace();
    }
    return rowcount;
  }

  public ResultSet executeQuery(String sql) {

    rs = null;
    try {
      if (stmt == null) {

        stmt = conn.createStatement();
      }

      rs = stmt.executeQuery(sql);

    } catch (SQLException ex) {
      System.err.println("sql_data.executeQuery:".concat(String.valueOf(String.valueOf(ex.getMessage()))));
    }

    return rs;
  }

  public int executeDelete(String sql) {
    int rowcount = 0;
    try {
      if (stmt == null) {
        stmt = conn.createStatement();
      }
      rowcount = stmt.executeUpdate(sql);
    } catch (SQLException ex) {
      System.err.println("sql_data.executeDelete:".concat(String.valueOf(String.valueOf(ex.getMessage()))));
    }

    return rowcount;
  }

  public boolean execute(String sql) {
    // System.out.println("enter executeQuery");
    boolean boo = false;
    try {
      if (stmt == null) {
        stmt = conn.createStatement();
      }
      boo = stmt.execute(sql);
    } catch (SQLException ex) {
      System.err.println("sql_data.execute:".concat(String.valueOf(String.valueOf(ex.getMessage()))));
    }

    return boo;
  }

  public void releaseConn() {

    try {
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
      if (conn != null) {
        conn.close();
        conn = null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public ResultSet searchTable(String strTableName, String strWhere) {
    String sql = "select * from " + strTableName + " " + strWhere;
    ResultSet hello = executeQuery(sql);
    return hello;

  }

  public ResultSet searchTable(String strResult, String strTableName, String strWhere) {
    String sql = "select " + strResult + " from " + strTableName + " " + strWhere;
    ResultSet hello = executeQuery(sql);
    return hello;

  }

  /*
   * String sql = "DELETE FROM HELLO_WORLD_TABLE " +
   * "WHERE HELLO_WORLD_TABLE.NO=3";
   */
  public int deleteRecord(String strTableName, int recordID) {
    int bResult = -1;
    try {
      String strWhere = "where ID='" + recordID + "'";

      ResultSet tmpRs = searchTable(strTableName, strWhere);
      if (!tmpRs.next()) {
        System.out.println("there is no such record");
      } else {
        String sql = "delete from " + strTableName + " where ID='" + recordID + "'";
        System.out.println("delete SQL:" + sql);
        bResult = executeDelete(sql);
      }
      releaseConn();
    } catch (Exception e) {
      System.out.println("SQLException in deleteBPModel of CBPManager:" + e.getMessage());

    }
    return bResult;
  }

  /*
   * sql = "UPDATE HELLO_WORLD_TABLE "+
   * "SET LANGUAGE='�X�y�C����',MESSAGE='Hola Mundo' " +
   * "WHERE HELLO_WORLD_TABLE.NO=1";
   */
  public boolean modifyRecord(String strTableName, String strSetValues, String strWhere) {
    // connectToAccess("history");

    try {
      String sql = "";
      if (strWhere.equals(""))
        sql = "update " + strTableName + " set " + strSetValues;
      else
        sql = "update " + strTableName + " set " + strSetValues + " where " + strWhere;
      return execute(sql);
    } catch (Exception e) {
      System.out.println("Exception:" + e.getMessage());
      System.out.println("SetValues " + strSetValues);
      return false;
    } finally {
      releaseConn();
    }

  }

  public boolean modifyRecord(String odbc, String strTableName, String strSetValues, String strWhere) {
    connectToAccess(odbc);

    try {
      String sql = "";
      if (strWhere.equals(""))
        sql = "update " + strTableName + " set " + strSetValues;
      else
        sql = "update " + strTableName + " set " + strSetValues + " where " + strWhere;
      return execute(sql);
    } catch (Exception e) {
      System.out.println("Exception:" + e.getMessage());
      System.out.println("SetValues " + strSetValues);
      return false;
    } finally {
      releaseConn();
    }

  }

  // public boolean modifyRecordNoRelease(String strTableName, String
  // strSetValues, String strWhere)
  // {
  // try
  // {
  // String sql = "";
  // if(strWhere.equals(""))
  // sql = "update " + strTableName + " set " + strSetValues;
  // else
  // sql = "update " + strTableName + " set " + strSetValues + " where " +
  // strWhere;
  // return execute(sql);
  // } catch (Exception e) {
  // System.out.println("Exception:" + e.getMessage());
  // System.out.println("SetValues " + strSetValues);
  // return false;
  // }
  //
  // }

  /*
   * sql = "INSERT INTO HELLO_WORLD_TABLE (NO,LANGUAGE,MESSAGE) " +
   * "VALUES(3 ,'�h�C�c��','Hallo Welt')";
   */
  public boolean insertRecord(String strTableName, String Fields, String Values) {
    // System.out.println("enter insertRecord");
    try {
      String sql = "INSERT INTO " + strTableName + " " + Fields + " VALUES" + Values;
      return execute(sql);
    } catch (Exception e) {
      System.out.println("Exception in insertRecord" + e.getMessage());
      return false;
    }
  }

  /*
   * String sql = "TRUNCATE TABLE table-name";
   */
  public boolean truncateTable(String strTableName, String strNewTableName, String newTableSetup) {
    boolean bResult = false;
    try {
      // how to at first check if the table exist, if so,drop or else just creat
      // a new table
      String sql = "DROP TABLE " + strTableName;
      bResult = execute(sql);
      if (bResult) {
        System.out.println("true---create new table");
        sql = "CREATE TABLE " + strNewTableName + " " + newTableSetup;
        bResult = execute(sql);
      } else {
        System.out.println("false---not create new table");
      }
    } catch (Exception e) {
      System.out.println("SQLException in TruncateTable " + e.getMessage());

    }
    return bResult;
  }

  /*
   * create a new table
   */
  public boolean createTable(String strTableName, String newTableSetup) {
    boolean bResult = false;
    try {
      // how to at first check if the table exist, if so,drop or else just creat
      // a new table

      String sql = "CREATE TABLE " + strTableName + " " + newTableSetup;
      bResult = execute(sql);

    } catch (Exception e) {
      System.out.println("SQLException in TruncateTable " + e.getMessage());

    }
    return bResult;
  }

  /**
   * @param args
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException {
    // TODO Auto-generated method stub
    // DataManager dm = new DataManager();
    // dm.connectToAccess("jcall");
    // String sql = "SELECT * FROM Errors";
    /*
     * 
     * ResultSet rs = dm.searchTable("Error", "");
     * 
     * if(rs.next()){
     * 
     * int no = rs.getInt("ID"); int studentID = rs.getInt("StudentID"); String
     * lesson = rs.getString("Lesson"); String question =
     * rs.getString("Question");// will add to the new database; String
     * basegrammar = rs.getString("BaseGrammar"); String generalType =
     * rs.getString("GeneralType"); String specificType =
     * rs.getString("SpecificType"
     * );//SUBS1,WRONG_FORM,INVALID_FORM,SPELL1,SPELL2,SPELL3,SPELL4; boolean
     * samesemanticgroup = rs.getBoolean("SameSemanticGroup"); boolean
     * formmistake = rs.getBoolean("FormMistakes"); boolean englishStem =
     * rs.getBoolean("EnglishStem"); boolean valid = rs.getBoolean("Valid");
     * System.out.println(no + " " + studentID + " " + basegrammar); }
     */
    // String sql =
    // "SELECT count(*) AS a FROM Errors WHERE SpecificType='SUBS2' ";
    // String sql =
    // "SELECT count(*) AS a FROM Errors WHERE (valid = 0 AND SpecificType='SUBS5') OR (valid = 1 AND GeneralType='CONCEPT' ) ";
    // CONCEPT,GRAMMAR,INPUT,LEXICAL
    // String sql3 =
    // "SELECT *  FROM Errors WHERE (SpecificType='SUBS2') And (GeneralType NOT IN('CONCEPT','GRAMMAR','INPUT','LEXICAL') )";
    /*
     * String sql1 =
     * "SELECT count(*) AS a FROM Errors WHERE SpecificType='SUBS6' "; String
     * sql2 =
     * "SELECT count(*) AS a FROM Errors WHERE (SpecificType='SUBS6') And (EnglishStem = 1)"
     * ;
     * 
     * ResultSet rs = dm.executeQuery(sql1); if(rs.next()){ int no =
     * rs.getInt("a");
     * 
     * System.out.println("the first"+"is" +no + " "); }
     * 
     * rs = dm.executeQuery(sql2); if(rs.next()){ int no = rs.getInt("a");
     * 
     * System.out.println("the second"+"is" +no + " "); } //And
     * (SameSemanticGroup=0 ) String sql3 =
     * "SELECT * FROM Errors WHERE (SpecificType='SUBS6') And (EnglishStem = 1)"
     * ; rs = dm.executeQuery(sql3); while(rs.next()){ int no = rs.getInt("ID");
     * 
     * System.out.println("the fourth" + no + " ");
     * 
     * }
     */
    /*
     * if(rs.next()){ int no = rs.getInt("ID"); String sql4 =
     * "UPDATE Errors SET SameSemanticGroup= 0 ,MatchingGroup1='' " +
     * "WHERE (SpecificType='SUBS8') And (SameSemanticGroup=1 )"; int rs2 =
     * dm.executeUpdate(sql4); if(rs.next()){
     * System.out.println("the success "+"is" + no + "---"+rs2 + " "); } }
     */
    // dm.releaseConn();

    /*
     * DataManager dm = new DataManager(); dm.connectToAccess("history"); String
     * strTableName = "Sentences"; String strTableSetup = "(" +
     * "ID Counter PRIMARY KEY, "; strTableSetup = strTableSetup+
     * "StudentID int, "; strTableSetup = strTableSetup+ "Lesson VarChar(50), ";
     * strTableSetup = strTableSetup+ "Question VarChar(50) NOT NULL, ";
     * strTableSetup = strTableSetup+ "InputSentence VarChar(200) NOT NULL, ";
     * strTableSetup = strTableSetup+ "TargetSentence VarChar(200) NOT NULL, ";
     * strTableSetup = strTableSetup+ "SentenceLevelError VarChar(50)"+")";
     * 
     * boolean bResult = false;
     * 
     * String sql = "CREATE TABLE abcdfg "+ strTableSetup; //String sql =
     * "drop table abcdfg"; bResult = dm.execute(sql); if(bResult){
     * System.out.println("create a new table"); }else{
     * System.out.println("not create a new table"); }
     * 
     * dm.releaseConn();
     * 
     * }
     */
    /*
     * DataManager dm = new DataManager(); dm.connectToAccess("history"); String
     * sql =
     * "Select count(*) FROM MSysObjects Where ((MSysObjects.Name) Like 'abc')";
     * String sql2 =
     * "Select count(*) FROM MSysObjects Where ((MSysObjects.Name) Like 'Sentences')"
     * ; String sql3 = "Select count(*) FROM Sentences"; String sql4 =
     * "Select count(*) FROM abc"; //String sql = "drop table abcdfg"; boolean
     * bResult = false; bResult = dm.execute(sql3); if(bResult){
     * System.out.println("exist 1"); }else{
     * System.out.println("not exist table 1"); } bResult = dm.execute(sql4);
     * 
     * if(bResult){ System.out.println("exist 2"); }else{
     * System.out.println("not exist table 2"); } dm.releaseConn();
     */
    String strTableName = "WordErrTable";
    boolean bResult = false;
    DataManager dm = new DataManager();
    dm.connectToAccess("history");
    WordDataMeta wdm;

    ResultSet tmpRs = dm.searchTable(strTableName, "");
    while (tmpRs.next()) {
      wdm = new WordDataMeta();
      int id = tmpRs.getInt("ID");
      int studentID = tmpRs.getInt("StudentID");
      String lesson = tmpRs.getString("Lesson");
      String quesiton = tmpRs.getString("Question");

      String strObserve = tmpRs.getString("ObservedWord");
      String strTarget = tmpRs.getString("TargetWord");
      String strTargetType = tmpRs.getString("TargetType");

      String strError = tmpRs.getString("SpecificType");

      System.out.println("ID: " + id + " strTarget: " + strTarget);

    }
    // String sql = "drop table "+ strTableName;
    // bResult = dm.execute(sql);
    // if(bResult){
    // System.out.println("true, not create a new table");
    // }else{
    // System.out.println("false, create a new table");
    // }
    // String strWordTableSetup = "(" + "ID Counter PRIMARY KEY, ";
    // strWordTableSetup += "StudentID int, ";
    // strWordTableSetup += "Lesson VarChar(10), ";
    // strWordTableSetup += "Question VarChar(10) NOT NULL, ";
    // strWordTableSetup += "BaseGrammar VarChar(50) NOT NULL, ";
    // strWordTableSetup += "FullGrammar VarChar(50) NOT NULL, ";
    // strWordTableSetup += "ObservedWord  VarChar(50) NOT NULL, ";
    // strWordTableSetup += "TargetWord VarChar(50) NOT NULL, ";
    // strWordTableSetup += "ObservedType VarChar(10) NOT NULL, ";
    // strWordTableSetup += "TargetType VarChar(10) NOT NULL, ";
    // strWordTableSetup += "SameType bit , ";
    // strWordTableSetup += "Valid bit , ";
    // strWordTableSetup += "SameConfusionGroup bit, ";
    // strWordTableSetup += "Spell bit, ";
    // strWordTableSetup += "EnglishStem bit, ";
    // strWordTableSetup += "SameStem bit, ";
    // strWordTableSetup += "InvalidForm bit, ";
    // strWordTableSetup += "OneStepErrW  bit,";
    // strWordTableSetup += "GeneralType VarChar(50),";
    // strWordTableSetup += "SpecificType VarChar(50),";
    // strWordTableSetup += "FormMistakes VarChar(50), ";
    // strWordTableSetup += "strFormDistance VarChar(10)"+")";
    // // System.out.println(strWordTableSetup);
    //
    // //dm.truncateTable(strwordTableName, strwordTableName,
    // strWordTableSetup);
    // bResult = dm.createTable("WordErrTable", strWordTableSetup);
    // if(bResult){
    // System.out.println("not create a new table");
    // }else{
    // System.out.println("create a new table");
    // }
    dm.releaseConn();

  }

}
