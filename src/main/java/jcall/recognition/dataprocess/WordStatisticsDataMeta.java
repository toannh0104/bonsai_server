/**
 * Created on 2007/06/17
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.dataprocess;

import java.io.PrintWriter;

//import org.apache.log4j.Logger;

public class WordStatisticsDataMeta extends LexiconWordMeta implements Comparable {
  int intOccureceTime;

  // static Logger logger = Logger.getLogger
  // (WordStatisticsDataMeta.class.getName());

  public WordStatisticsDataMeta() {
    super();
    this.intOccureceTime = 0;
  }

  public WordStatisticsDataMeta(int intOccureceTime) {
    super();
    this.intOccureceTime = intOccureceTime;
  }

  public int getIntOccureceTime() {
    return this.intOccureceTime;
  }

  public void setIntOccureceTime(int intOccureceTime) {
    this.intOccureceTime = intOccureceTime;
  }

  public void write(PrintWriter outP) {
    super.write(outP);
    outP.println("-OccureceTime " + intOccureceTime);
  }

}
/*
 * public int compareTo(Object o) { //WordStatisticsDataMeta wsdm;
 * 
 * if(o instanceof WordStatisticsDataMeta){ WordStatisticsDataMeta wsdm
 * =(WordStatisticsDataMeta)o; if(super.compareTo(o)==0 && wsdm.id==this.id){
 * return 0; }else{ return 1; } } return -1; } }
 */