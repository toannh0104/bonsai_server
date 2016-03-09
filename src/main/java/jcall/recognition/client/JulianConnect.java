package jcall.recognition.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jcall.recognition.event.Notifier;
import jcall.recognition.util.DataBuffers;

import org.apache.log4j.Logger;

public class JulianConnect extends Thread {

  static Logger logger = Logger.getLogger(JulianConnect.class.getName());

  static String resultData;
  BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
  Notifier notifier;

  public JulianConnect() {

    resultData = "";
    notifier = Notifier.getNotifier();
  }

  public void run() {
    String line;

    try {

      BufferedReader clientin = new BufferedReader(new InputStreamReader(ClientAgent.client.getInputStream()));
      while (true) {
        line = clientin.readLine();
        if (line != null) {
          if (line.length() == 0) {
            logger.info("in JulianConnect: read a line which lenghth is zero");
          } else {
            logger.debug("readLine: " + line);

            if ((line.indexOf("WORD") != -1) && (line.indexOf("CLASSID") != -1) && (line.indexOf("=\"<") == -1)) {
              resultData = resultData + " " + line.substring(line.indexOf("\"") + 1, line.indexOf("CLASSID") - 2);
            } else if (line.equalsIgnoreCase("</RECOGOUT>") && resultData.length() > 0) {
              // IOUtil.saveData(resultData.trim());
              DataBuffers.saveData2Stack(resultData.trim());
              logger.info("Recognition Result: " + resultData);
              notifier.fireOnReceived(resultData.trim());
              resultData = "";
            }
          }
        }
      }

      // ClientAgent.client.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e.toString());
      System.exit(1);
    }

  }
}
