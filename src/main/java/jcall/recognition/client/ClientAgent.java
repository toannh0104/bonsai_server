package jcall.recognition.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import jcall.util.CollectionUtil;

import org.apache.log4j.Logger;

public class ClientAgent {
  // private static final String InetAddress. = null;
  static Logger logger = Logger.getLogger(ClientAgent.class.getName());

  static Socket client;
  static int PORTNUM = 10500;
  static BufferedReader clientin;
  static PrintWriter clientout;
  // static OutputStream clientOS;
  static BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
  final static String FILEPATH = "./Data/JGrammar";

  public ClientAgent() {
  }

  /**
   * connect to the server
   * 
   * @param hostname
   * @param port
   */
  public static void doConnect(String hostname, int port) {
    try {
      if (hostname.equalsIgnoreCase("localhost")) {
        client = new Socket(InetAddress.getLocalHost(), port);
      } else {
        client = new Socket(InetAddress.getByName(hostname), port);
      }
      if (client != null) {
        System.out.println("Connecting to port " + port);
        clientin = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientout = new PrintWriter(client.getOutputStream(), true);
        // clientOS = client.getOutputStream();

      }

    } catch (Exception e) {
      System.out.println(e.toString());

    }

  }

  public static void doConnect(String hostname) {
    try {
      if (hostname.equalsIgnoreCase("localhost")) {
        client = new Socket(InetAddress.getLocalHost(), PORTNUM);
      } else {
        client = new Socket(InetAddress.getByName(hostname), PORTNUM);
      }
      if (client != null) {
        System.out.println("Connecting to port " + PORTNUM);
        clientin = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientout = new PrintWriter(client.getOutputStream(), true);
        // clientOS = client.getOutputStream();
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }

  public static void doConnect() {
    // System.out.println("enter doConnect()!");
    try {
      // default hostname is "localhost"
      client = new Socket(InetAddress.getLocalHost(), PORTNUM);

      if (client != null) {
        System.out.println("Connecting to port " + PORTNUM);
        clientin = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientout = new PrintWriter(client.getOutputStream(), true);
        // clientOS = client.getOutputStream();
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }

  }

  public static void doDisconnect() {

    try {
      client.close();
    } catch (IOException e) {
      System.out.println("IOException" + e.toString());
    }
  }

  /**
   * send data to server
   * 
   */

  public static void doSend(String strInfo) {
    logger.debug("enter doSend in ClientAgent, strInfo: " + strInfo);
    clientout.println(strInfo);
  }

  /**
   * send data to server
   * 
   */

  public static void doSend(char[] charInfo) {
    int count = CollectionUtil.CharStrlen(charInfo);
    // clientout.print(charInfo);
    clientout.write(charInfo, 0, count);
  }

  /**
   * send data to server
   * 
   * @param
   */
  public static void doSend(Socket sock, String info) {

    try {

      PrintWriter clientout1 = new PrintWriter(sock.getOutputStream(), true);
      clientout1.println(info);

    } catch (IOException e) {
      System.out.println(e.toString());
    }

  }

  /**
   * receive one line from server
   * 
   */
  public static String doReceive() {
    String strResult = new String();
    String str;
    try {
      if ((str = clientin.readLine()) != null) {
        strResult = str.trim() + "\n";
        System.out.print(strResult); // show every line
      }
    } catch (IOException e) {
      System.out.println(e.toString());
    }

    return strResult;
  }

  /**
   * receive data all line from server
   * 
   */
  public static void doReceiveLine() {
    // String strResult = new String();
    String str;
    try {
      while ((str = clientin.readLine()) != null) {
        str = str.trim();
        // strResult = strResult + str;
        System.out.println(str); // show every line
      }
    } catch (IOException e) {
      System.out.println(e.toString());
    }

    // return strResult;
  }

  public static void startRecognize(String suffix) {
    String fullname = FILEPATH + suffix;
    JGrammer.japiChangeGrammar(fullname);
    ClientAgent.doSend("RESUME");
    System.out.println(" end of start recognition!");
  }

  public static void startRecognition() {
    // new Connect("localhost",PORTNUM).start();
    // System.out.println("enter startRecognition()!");

    doConnect();
    System.out.println("end doConnect()!");
    System.out.println("start JulianConnect().start()!");
    new JulianConnect().start();

  }

  public static void startRecognize() {
    // new Connect("localhost",PORTNUM).start();
    // doConnect();
    new JulianConnect().start();
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) {

    /**
     * testing programe1 doSend is ok
     * 
     * try{ if (args.length < 1) {
     * System.out.println("error --Usage: java client servername"); return;
     * }else if(args.length==1){ String hostname = args[0];
     * ClientAgent.doConnect(hostname); }else { String hostname = args[0]; int
     * portnumber =Integer.parseInt(args[1]);
     * ClientAgent.doConnect(hostname,portnumber); } boolean done = false;
     * while(!done){ String strline = sysin.readLine();
     * //clientout.println(strline); ClientAgent.doSend(strline); if
     * (strline.equalsIgnoreCase("bye")) done = true;
     * 
     * System.out.println(clientin.readLine()); } ClientAgent.doDisconnect();
     * 
     * System.out.println(" end !"); }catch(IOException e){
     * System.out.println(e.toString()); }
     */

    /**
     * testing programe2 doReceive is ok
     */

    try {
      if (args.length < 1) {
        ClientAgent.startRecognition();
        // ClientAgent.doConnect();
      } else if (args.length == 1) {
        String hostname = args[0];
        ClientAgent.doConnect(hostname);
      } else {
        String hostname = args[0];
        int portnumber = Integer.parseInt(args[1]);
        ClientAgent.doConnect(hostname, portnumber);
      }
      boolean done = false;
      while (!done) {
        String strline = sysin.readLine();
        String strCommand = "";
        String strArg = "";
        // parse command
        StringTokenizer st = new StringTokenizer(strline, " ");
        if (st.hasMoreTokens()) {
          strCommand = st.nextToken();
          if (st.hasMoreTokens()) {
            strArg = st.nextToken();
          }
        }
        if (strCommand.equalsIgnoreCase("die")) {
          ClientAgent.doSend("DIE");
        } else if (strCommand.equalsIgnoreCase("version")) {
          ClientAgent.doSend("VERSION");
        } else if (strCommand.equalsIgnoreCase("status")) {
          ClientAgent.doSend("STATUS");
        } else if (strCommand.equalsIgnoreCase("pause")) {
          ClientAgent.doSend("PAUSE");
        } else if (strCommand.equalsIgnoreCase("resume")) {
          ClientAgent.doSend("RESUME");
        } else if (strCommand.equalsIgnoreCase("changegram")) {
          JGrammer.japiChangeGrammar(strArg);
        } else if (strCommand.equalsIgnoreCase("deletegram")) {
          JGrammer.japiDeleteGrammar(strArg);
          ClientAgent.doReceiveLine();
        } else if (strCommand.equalsIgnoreCase("addgram")) {
          JGrammer.japiAddGrammar(strArg);
        } else if (strCommand.equalsIgnoreCase("activategram")) {
          JGrammer.japiActivateGrammar(strArg);
        } else if (strCommand.equalsIgnoreCase("deactivategram")) {
          JGrammer.japiDeactivateGrammar(strArg);
        } else if (strCommand.equalsIgnoreCase("bye")) {
          JGrammer.japiDeactivateGrammar(strArg);
          done = true;
        } else {
          System.out.println("input string is wrong string");
        }
        // ClientAgent.startRecognize();//.doReceiveLine();
      }
      // String str;
      // while((str=clientin.readLine()) != null){
      // System.out.println(str);
      // }

      ClientAgent.doDisconnect();

      System.out.println(" end !");
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  public static Socket getClient() {
    return client;
  }

  public static BufferedReader getClientin() {
    return clientin;
  }

  public static void setClientin(BufferedReader clientin) {
    ClientAgent.clientin = clientin;
  }

}
