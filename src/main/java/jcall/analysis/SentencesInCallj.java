/**
 * Created on 2009/12/08
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.analysis;

import java.util.Vector;

import jcall.CALL_configStruct;
import jcall.CALL_database;
import jcall.config.Configuration;

public class SentencesInCallj {

  public SentencesInCallj() {
    // TODO Auto-generated constructor stub
  }

  /*
   * All available sentences
   */
  public Vector generateSentences(int lessonIndex) {

    // Prepare the database, and configs
    CALL_database db = new CALL_database();
    // add config
    CALL_configStruct config = new CALL_configStruct(); // default config
    CALL_configStruct gconfig = config; // Store the current configuration as
                                        // the generic settings (used for saving
                                        // name)
    Configuration configxml = Configuration.getConfig(); // new user config

    /*
     * ********
     * *Question Generation ********
     */

    // question types in Lesson (lessonIndex)
    // currentQuestion = new CALL_questionStruct(db, config, gconfig, lesson,
    // CALL_questionStruct.QTYPE_CONTEXT);

    return null;
  }

}
