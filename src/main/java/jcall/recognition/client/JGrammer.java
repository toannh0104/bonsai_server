package jcall.recognition.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import jcall.util.CollectionUtil;

import org.apache.log4j.Logger;

/*
 * JGrammer
 */
public class JGrammer {

  static Logger logger = Logger.getLogger(JGrammer.class.getName());

  static int MAXLINELEN = 4096;

  public JGrammer() {

  }

  /**
   * check grammar file existing
   */
  static int checkGrammerPath(String prefix) {

    int iResult = 0;

    if (prefix.length() < MAXLINELEN && prefix.length() > 0) {
      String strTemp1 = prefix.trim() + ".dfa";
      String strTemp2 = prefix.trim() + ".dict";
      File dfaFile = new File(strTemp1);
      File dictFile = new File(strTemp2);
      if (!dfaFile.exists()) {
        System.out.println("dfaFile in : " + strTemp1 + " not exist");
      } else if (!dictFile.exists()) {
        System.out.println("dictFile in : " + strTemp2 + " not exist");
      }
      if (!dfaFile.exists() || !dictFile.exists()) {
        // DebugLogger.printlog("dfafile or dictfile not exist");
        iResult = -1;
      }
    }
    return iResult;

  }

  /**
   * send grammar body to server
   * 
   * public static int sendGrammar (String prefix){ int iResult = 0;
   * 
   * String strTemp1 = prefix.trim()+ ".dfa"; String strTemp2 = prefix.trim()+
   * ".dict"; char[] buf = new char[MAXLINELEN]; // read and send .dfa file try
   * { FileReader dfaFR = new FileReader(strTemp1); BufferedReader dfaBR = new
   * BufferedReader(dfaFR);
   * 
   * int c = dfaBR.read(buf); //int c = dictFR.read(); while(c!=-1){
   * ClientAgent.doSend(buf); c = dfaBR.read(); }
   * 
   * // String line = dfaBR..readLine(); // while(line!=null){ //
   * ClientAgent.doSend(line+"\n"); // line = dfaBR.readLine(); // }
   * 
   * ClientAgent.doSend("DFAEND"); dfaBR.close(); } catch (FileNotFoundException
   * e) {
   * 
   * Logger.printlog("file not found exception "); return -1; } catch
   * (IOException e) {
   * 
   * e.printStackTrace(); }
   * 
   * //read and send .dict file
   * 
   * try { FileReader dictFR = new FileReader(strTemp2);
   * 
   * int c = dictFR.read(buf); //int c = dictFR.read(); while(c!=-1){
   * ClientAgent.doSend(buf); c = dictFR.read(buf); }
   * 
   * ClientAgent.doSend("DICEND\n"); dictFR.close();
   * 
   * } catch (FileNotFoundException e) {
   * Logger.printlog("file not found exception "); return -1; } catch
   * (IOException e) {
   * 
   * e.printStackTrace(); }
   * 
   * return iResult; }
   */

  /**
   * send grammar body to server
   */
  public static int sendGrammar(String prefix) {
    int iResult = 0;

    String strTemp1 = prefix.trim() + ".dfa";
    String strTemp2 = prefix.trim() + ".dict";
    char[] buf = new char[MAXLINELEN];
    // read and send .dfa file
    try {
      FileReader dfaFR = new FileReader(strTemp1);
      BufferedReader dfaBR = new BufferedReader(dfaFR);

      String line = dfaBR.readLine();
      while (line != null) {
        ClientAgent.doSend(line);
        line = dfaBR.readLine();
      }
      ClientAgent.doSend("DFAEND");
      dfaBR.close();
    } catch (FileNotFoundException e) {
      // DebugLogger.printlog("file not found exception ");
      return -1;
    } catch (IOException e) {

      e.printStackTrace();
    }

    // read and send .dict file

    try {
      FileReader dictFR = new FileReader(strTemp2);
      BufferedReader dictBR = new BufferedReader(dictFR);
      String line = dictBR.readLine();
      while (line != null) {
        ClientAgent.doSend(line);
        line = dictBR.readLine();
      }
      ClientAgent.doSend("DICEND");
      dictFR.close();

    } catch (FileNotFoundException e) {
      // DebugLogger.printlog("file not found exception ");
      return -1;
    } catch (IOException e) {

      e.printStackTrace();
    }

    return iResult;
  }

  /**
   * send grammar body to server
   */
  public static int sendGrammar(String prefix, PrintWriter out) {
    int iResult = 0;

    String strTemp1 = prefix.trim() + ".dfa";
    String strTemp2 = prefix.trim() + ".dict";
    // read and send .dfa file
    try {
      FileReader dfaFR = new FileReader(strTemp1);
      BufferedReader dfaBR = new BufferedReader(dfaFR);

      String line = dfaBR.readLine();
      while (line != null) {
        out.println(line);
        line = dfaBR.readLine();
      }

      out.println("DFAEND");
      dfaBR.close();
    } catch (FileNotFoundException e) {
      // DebugLogger.printlog("file not found exception ");
      return -1;
    } catch (IOException e) {

      e.printStackTrace();
    }

    // read and send .dict file

    try {
      FileReader dictFR = new FileReader(strTemp2);
      BufferedReader dictBR = new BufferedReader(dictFR);
      String line = dictBR.readLine();
      while (line != null) {
        out.println(line);
        line = dictBR.readLine();
      }
      out.println("DICEND");
      dictFR.close();

    } catch (FileNotFoundException e) {
      // DebugLogger.printlog("file not found exception ");
      return -1;
    } catch (IOException e) {

      e.printStackTrace();
    }

    return iResult;
  }

  /**
   * send comma-separated grammar ID list (ex. "1,3,5") ??????
   */

  public static int sendIDList(String idlist) {
    idlist.replace(',', ' ');
    ClientAgent.doSend(idlist);
    return 0;
  }

  public static int sendIDList(String idlist, PrintWriter out) {
    idlist.replace(',', ' ');
    out.println(idlist);
    return 0;
  }

  public static void japiChangeGrammar(String prefix) {
    if (checkGrammerPath(prefix) < 0) {
      logger.debug("wrong prefix: " + prefix + " ,return");
      return;
    }
    logger.debug("in japiChangeGrammar, correct prefix: " + prefix);
    ClientAgent.doSend("CHANGEGRAM");
    sendGrammar(prefix);
  }

  public static void japiChangeGrammar(String prefix, PrintWriter out) {
    if (checkGrammerPath(prefix) < 0)
      return;
    out.println("CHANGEGRAM");
    sendGrammar(prefix, out);
  }

  public static void japiAddGrammar(String prefix) {
    if (checkGrammerPath(prefix) < 0)
      return;
    ClientAgent.doSend("ADDGRAM");
    sendGrammar(prefix);
  }

  public static void japiAddGrammar(String prefix, PrintWriter out) {
    if (checkGrammerPath(prefix) < 0)
      return;
    out.println("ADDGRAM");
    sendGrammar(prefix, out);
  }

  public static void japiDeleteGrammar(String idlist) {

    ClientAgent.doSend("DELGRAM");
    sendIDList(idlist);
  }

  public static void japiDeleteGrammar(String idlist, PrintWriter out) {

    out.println("DELGRAM");
    sendIDList(idlist, out);
  }

  public static void japiActivateGrammar(String idlist) {

    ClientAgent.doSend("ACTIVATEGRAM");
    sendIDList(idlist);
  }

  public static void japiActivateGrammar(String idlist, PrintWriter out) {

    out.println("ACTIVATEGRAM");
    sendIDList(idlist, out);
  }

  public static void japiDeactivateGrammar(String idlist) {

    ClientAgent.doSend("DEACTIVATEGRAM");
    sendIDList(idlist);
  }

  public static void japiDeactivateGrammar(String idlist, PrintWriter out) {

    out.println("DEACTIVATEGRAM");
    sendIDList(idlist, out);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

    String prefix = "D:\\Test\\fruit";
    String strTemp1 = prefix.trim() + ".dfa";
    String strTemp2 = prefix.trim() + ".dfa";
    // read and send .dfa file
    try {
      FileReader dfaFR = new FileReader(strTemp1);
      BufferedReader dfaBR = new BufferedReader(dfaFR);
      String strBuf = "";
      String line = dfaBR.readLine();
      while (line != null) {
        strBuf += line + "\n";
        line = dfaBR.readLine();
      }
      System.out.print(strBuf + "\n");
      System.out.println("readline  finished---");
      dfaBR.close();
    } catch (FileNotFoundException e) {

      // DebugLogger.printlog("file not found exception ");

    } catch (IOException e) {

      e.printStackTrace();
    }

    // read and send .dict file

    try {
      FileReader dictFR = new FileReader(strTemp2);
      char[] buf = new char[MAXLINELEN];
      // String strBuf2 ="";
      int c = dictFR.read(buf);
      // int c = dictFR.read();
      while (c != -1) {
        // System.out.print((char)c);
        int count = CollectionUtil.CharStrlen(buf);
        // buf = buf[0,count]
        // out.write(buf);
        System.out.print(buf);
        c = dictFR.read();
      }
      // System.out.print(strBuf2);
      System.out.println("\nread char  finished---");
      dictFR.close();

    } catch (FileNotFoundException e) {
      // DebugLogger.printlog("file not found exception ");

    } catch (IOException e) {

      e.printStackTrace();
    }

  }

}
