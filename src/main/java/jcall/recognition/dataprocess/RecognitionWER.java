/**
 * Created on 2007/10/01
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import jcall.recognition.database.DataManager;

import org.apache.log4j.Logger;

public class RecognitionWER {
  static Logger logger = Logger.getLogger(RecognitionWER.class.getName());
  DataManager dm;
  static final String FILEPATH = "./Data/JGrammar";

  // static final String FULLNAME= FILEPATH
  // +"\\"+CALL_SentenceGrammar.CONTEXTFILENAME;
  public RecognitionWER() {
    init();
  }

  private void init() {

    dm = new DataManager();

  }

  /**
   * @param args
   * @throws SQLException
   * @throws NumberFormatException
   */
  public void getWER_baseline(String DBTableName, int intID) throws NumberFormatException, SQLException {
    // connect to julian server port

    // ClientAgent.startRecognition();

    // connect to db
    dm.connectToAccess("EXPERIMENTS");

    String sql = "SELECT * FROM " + DBTableName + " WHERE ID=" + intID;
    ResultSet rs = dm.executeQuery(sql);
    String strWord = "";
    StringTokenizer st;
    String grammarFile = "";
    int intIndex = 0; // words' index of one sentence'
    Vector<String> v;
    if (rs.next()) {// still remains sentences
      int intId = rs.getInt("ID");

      String strLesson = rs.getString("Lesson").trim();
      int intLesson = Integer.parseInt(strLesson);

      String strQuestion = rs.getString("Question");
      String strTargetSentence = rs.getString("strTargetAnswer");
      st = new StringTokenizer(strTargetSentence);

      grammarFile = FILEPATH + "\\L" + strLesson + "\\" + strQuestion;

      logger.info("ID: " + intId + " Lesson: " + strLesson);
      logger.info("Model Answer: " + strTargetSentence);
      // split the sentence into components
      intIndex = 0;
      v = new Vector<String>();
      while (st.hasMoreTokens()) { // handle word
        intIndex++;
        strWord = st.nextToken();
        strWord = strWord.trim();
        v.addElement(strWord);
      }

      if (strTargetSentence != null) {

        // ClientAgent.doSend("PAUSE");
        // JGrammer.japiChangeGrammar(grammarFile);
        // ClientAgent.doSend("RESUME");

      }

    }

  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    // MakeDFA dfa = new MakeDFA();
    // dfa.getDFAFile("L28\\L28permission");

  }

}
