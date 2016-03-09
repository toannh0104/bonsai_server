/**
 * Created on 2007/06/04
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.Serializable;
import java.util.Vector;

public class ErrorPredictionStruct implements Serializable {

  Vector<PredictionDataMeta> vecLesson;
  Vector<PredictionDataMeta> vecGeneral;

  public ErrorPredictionStruct() {
    init();
  }

  private void init() {
    vecGeneral = new Vector<PredictionDataMeta>();
    vecLesson = new Vector<PredictionDataMeta>();
  }

  public Vector<PredictionDataMeta> getVecGeneral() {
    return vecGeneral;
  }

  public void setVecGeneral(Vector<PredictionDataMeta> vecGeneral) {
    this.vecGeneral = vecGeneral;
  }

  public Vector<PredictionDataMeta> getVecLesson() {
    return vecLesson;
  }

  public void setVecLesson(Vector<PredictionDataMeta> vecLesson) {
    this.vecLesson = vecLesson;
  }

  public void addToVecGeneral(PredictionDataMeta pdm) {
    this.vecGeneral.addElement(pdm);
  }

  public void addToVecLesson(PredictionDataMeta pdm) {
    this.vecLesson.addElement(pdm);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

}
