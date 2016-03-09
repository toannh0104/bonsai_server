/**
 * Created on 2007/06/04
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.io.Serializable;
import java.util.Vector;

import jcall.CALL_database;

import org.apache.log4j.Logger;

public class JCALL_PredictionDatasStruct implements Serializable {

  static Logger logger = Logger.getLogger(JCALL_PredictionDatasStruct.class.getName());

  private static Vector<JCALL_PredictionDataStruct> vecPDM;

  private static JCALL_PredictionDatasStruct predictdata;

  private JCALL_PredictionDatasStruct() {
    init();
  }

  private void init() {
    // String file =
    // FindConfig.getConfig().findStr(ConfigInstant.CONFIG_EP_FILE);
    // vecPDM = new Vector<JCALL_PredictionDataStruct>();
    JCALL_PredictionParser pp = new JCALL_PredictionParser();
    vecPDM = pp.loadData(CALL_database.PREDICTIONFILE);

  }

  public static JCALL_PredictionDatasStruct getInstance() {
    if (predictdata == null) {
      predictdata = new JCALL_PredictionDatasStruct();
    }
    return predictdata;
  }

  public void addObject(JCALL_PredictionDataStruct pdm) {
    if (vecPDM == null) {
      vecPDM = new Vector<JCALL_PredictionDataStruct>();
    }
    this.vecPDM.addElement(pdm);
  }

  public Vector<JCALL_PredictionDataStruct> getVecPDM() {
    return vecPDM;
  }

}
