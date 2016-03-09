/**
 * Created on 2007/05/28
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class JCALL_PredictionParser {
  static Logger logger = Logger.getLogger(JCALL_PredictionParser.class.getName());

  // Vector<PredictionDataMeta> vecNoun;
  // Vector<PredictionDataMeta> vecVerb;
  // Vector<PredictionDataMeta> vecParticle;
  // Vector<PredictionDataMeta> vecNumeral;
  // Vector<PredictionDataMeta> vecQantifier;
  // Vector<PredictionDataMeta> vecPronoun;

  final static int START = 0;
  final static int LESSON = 1;
  final static int GRAMMAR = 2;
  final static int CONCEPT = 3;
  final static int LEXICON = 4;
  final static int OUTLESSONLEXICON = 5;
  final static int LEXICONGROUP = 6;
  final static int LEXICONWORD = 7;
  final static int END = 8;

  int readstatus;
  BufferedReader br;

  public JCALL_PredictionParser() {
    init();

  }

  private void init() {
  }

  /*
   * return all the prediction words except for counter.
   */
  public Vector loadData(String strFileName) {
    Vector<JCALL_PredictionDataStruct> vecResult = new Vector<JCALL_PredictionDataStruct>();
    // init();
    logger.debug("enter (JCALL_PredictionDatasStruct) loadDataFromFile, file: " + strFileName);

    // JCALL_PredictionDatasStruct eps = new JCALL_PredictionDatasStruct();

    JCALL_WordBase word;
    JCALL_PredictionDataStruct pdm = null;

    // load the words from the lexicon
    JCALL_Lexicon lexicon = JCALL_Lexicon.getInstance();

    // load and parse the predict file;be careful with the repeated words;
    try {
      InputStream inputStream = new FileInputStream(strFileName);
      br = new BufferedReader(new InputStreamReader(inputStream, "SJIS"));

      readstatus = START;
      StringTokenizer st;
      String readLine, tempString = null;
      int intCount = 0;
      // Read Line by line
      while (readstatus != END) {
        readLine = br.readLine();

        // System.out.println(readLine);
        intCount++;
        if (readLine != null && readLine.trim().length() > 0) {
          st = new StringTokenizer(readLine);
          // System.out.println("After assign the StringTokenizer");
          // logger.debug("Line:"+readLine);
          if (!st.hasMoreTokens()) {
            logger.error("befor st.nextToken, it has no tokens");
            continue;
          }
          tempString = st.nextToken();

          if (tempString.startsWith("##")) {
            // A comment,skip it
            continue;
          } else if (tempString.startsWith("-eof")) {
            // Add any previous word
            if (pdm != null && pdm.getIntType() > 0) {
              vecResult.addElement(pdm);
              pdm = null;
            }
            return vecResult;
          } else {
            // switch(readstatus){
            // case START:

            // logger.debug("LineStart:"+tempString);

            if (tempString.startsWith("-word")) {
              if (pdm != null && pdm.getIntType() > 0) {
                vecResult.addElement(pdm);
                pdm = null;
              }
              pdm = new JCALL_PredictionDataStruct();
              tempString = st.nextToken();
              if (tempString != null) {// type string
                pdm.setType(tempString);
                int intType = lexicon.typeName2Int(tempString.trim());
                if (intType == lexicon.LEX_TYPE_UNSPECIFIED) {
                  logger.error("int lexicon no type: " + tempString);
                } else {
                  pdm.setIntType(intType);
                }
              }
              tempString = st.nextToken();
              if (tempString != null) {
                // search word in lexicon
                word = lexicon.getWordFmStr(tempString, pdm.getIntType());

                if (word != null) {
                  //
                  pdm.setId(word.getId());
                  pdm.setStrKana(word.getStrKana());
                  pdm.setStrKanji(word.getStrKanji());
                  pdm.setStrAltkana(word.getStrAltkana());
                  // readstatus = LEXICONWORD;

                } else {
                  logger.error("no such a word in lexicon [" + tempString + "]" + ", type: " + pdm.getIntType());
                }

              } else {
                logger.error("wrong form of the word type error in prediction.txt");
              }

            } else if (tempString.startsWith("-group")) {

              if (pdm != null && pdm.getIntType() > 0) {
                vecResult.addElement(pdm);
                pdm = null;
              }
              pdm = new JCALL_PredictionDataStruct();
              pdm.setStrClass("group");
              tempString = st.nextToken();
              // System.out.println("-group " + tempString);
              if (tempString != null) {// type string
                pdm.setType(tempString);
                int intType = lexicon.typeName2Int(tempString.trim());
                if (intType == lexicon.LEX_TYPE_UNSPECIFIED) {
                  System.out.println("int lexicon no type: " + tempString);
                } else {
                  pdm.setIntType(intType);
                }

              }
              tempString = st.nextToken();
              if (tempString != null) {
                // category name
                pdm.strCategory = tempString;

                // readstatus = LEXICONGROUP;

              }

            } else if (tempString.startsWith("-grammar")) {

              if (pdm != null && pdm.getIntType() > 0) {
                vecResult.addElement(pdm);
                pdm = null;
              }
              pdm = new JCALL_PredictionDataStruct();
              pdm.setStrClass("grammar");
              tempString = st.nextToken();
              // System.out.println("-grammar " + tempString);
              if (tempString != null) {// type string
                pdm.setType(tempString);
                int intType = lexicon.typeName2Int(tempString.trim());
                if (intType == lexicon.LEX_TYPE_UNSPECIFIED) {
                  System.out.println("int lexicon no type: " + tempString);
                } else {
                  pdm.setIntType(intType);
                }

              }
            }

            else if (tempString.startsWith("-sub")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                pdm.strSubword = tempString;
              }
            } else if (tempString.startsWith("-slot")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                pdm.strSlot = tempString;
              }
            } else if (tempString.startsWith("-accept")) {
              tempString = st.nextToken().trim();
              if (tempString != null && tempString.length() > 0) {
                if (tempString.equalsIgnoreCase("yes")) {
                  pdm.bAccept = true;
                } else if (tempString.equalsIgnoreCase("no")) {
                  pdm.bAccept = false;
                }
              }
            } else if (tempString.startsWith("-error")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                pdm.strError = tempString;
              }
            } else if (tempString.startsWith("-lesson")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                String str[] = tempString.split(",");
                pdm.addToVecLesson(tempString);
              }
              //
            } else if (tempString.startsWith("-condition")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                pdm.strCondition = tempString;
              }
            } else if (tempString.startsWith("-specific")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                pdm.addToVecSpecific(tempString);
              }
            } else if (tempString.startsWith("-valid")) {
              tempString = st.nextToken();
              if (tempString != null && tempString.length() > 0) {
                if (tempString.equalsIgnoreCase("yes")) {
                  pdm.bValid = true;
                } else if (tempString.equalsIgnoreCase("no")) {
                  pdm.bValid = false;
                }
              }
            } else if (tempString.startsWith("-rule")) {
              tempString = st.nextToken();
              while (st.hasMoreTokens()) {
                tempString = tempString + " " + st.nextToken();
              }
              if (tempString != null && tempString.length() > 0) {
                pdm.strRule = tempString;
              }

            } else if (tempString.startsWith("-vform")) {
              tempString = st.nextToken();
              // System.out.println("-vform: "+tempString);
              if (tempString != null && tempString.length() > 0) {
                StringTokenizer stTemp2 = new StringTokenizer(tempString, ",");
                while (stTemp2.hasMoreTokens()) {
                  String str = (String) stTemp2.nextElement();
                  // logger.debug("-form "+str);
                  pdm.addToVecVerbForm(str);

                }
              }

            } else if (tempString.startsWith("-REF")) {
              tempString = st.nextToken();
              // System.out.println("-REF " + tempString);
              pdm.setStrREF(tempString);
            } else if (tempString.startsWith("-invalidform")) {
              tempString = st.nextToken();
              // System.out.println("-invalidform " + tempString);
              if (tempString != null && tempString.length() > 0) {
                StringTokenizer stTemp = new StringTokenizer(tempString, ",");
                while (stTemp.hasMoreTokens()) {
                  String str = (String) stTemp.nextElement();
                  // logger.debug("-form "+str);
                  pdm.addToVecVerbInvalidFrom(str);
                }
              }
            }
            // else if(tempString.startsWith("-restrict")){
            // tempString = st.nextToken();
            // // System.out.println("-REF " + tempString);
            // pdm.s(tempString);
            //
            // }
            // break;
            // //LEXICONGROUP,LEXICONWORD
            //
            // case END:
            // return eps;

            // }//end of switch

          } // end of else*/

        }// end of if

      }// end of while

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      logger.error("FileNotFoundException, the parameter file" + e.getStackTrace());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // add all left words which only has altkanawors
    // for (int i = 0; i < vecAltKanaResult.size(); i++) {
    // //PredictionDataMeta pd = vecAltKanaResult.get(i);
    // eps.addToVecGeneral(vecAltKanaResult.get(i));
    //
    // }
    return vecResult;
  }

  /**
   * @param args
   * @throws IOException
   * @throws SQLException
   */
  public static void main(String[] args) throws SQLException, IOException {
    // TODO Auto-generated method stub
    // PredictError.getConfusionGroup();
    JCALL_PredictionParser parser = new JCALL_PredictionParser();

    String predictFile = "/Data/EP.txt";
    Vector es = parser.loadData(predictFile);

  }

}
