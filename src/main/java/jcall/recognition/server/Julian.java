/**
 * Created on 2009/06/02
 * @author wang
 * Copyrights @kawahara lab
 */

package jcall.recognition.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Julian {

  static Logger logger = Logger.getLogger(Julian.class.getName());

  final static String JulianBat = "startJulian.bat";

  public Julian() {

  }

  public static void startJulian() {
    // TODO Auto-generated method stub

    // build or rebuild julian.bat
    writeJulianBat();

    // execute the bat command
    // String cmdBat =
    // "C:\\eclipse\\workspace\\JCALLSpokenExercise\\startJulian.bat /c";
    String currentPath = System.getProperty("user.dir");
    String cmdBat = currentPath + "\\" + JulianBat + " /c";
    // CmdExecution.ExeCmd(cmdBat);
    // CmdExecution.ExeCmd("./Julian/bin/julian.exe -C L1objects.jconf -module -input mic");
    try {
      Process julian_process = Runtime.getRuntime().exec(cmdBat);
      Thread.sleep(2000);
      julian_process.destroy();
    } catch (IOException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    } catch (InterruptedException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }

  }

  public static void writeJulianBat() {

    String filepath = System.getProperty("user.dir");

    String bat = JulianBat;
    File batFile = new File(filepath, bat);
    // check file path and make dir,create new file
    File batpath = new File(filepath);
    if (!batpath.exists()) {
      batpath.mkdir();
    }
    if (batFile.exists()) {
      batFile.delete();
    }
    try {
      batFile.createNewFile();

      logger.debug("update data,create a new julian bat---" + batFile.getAbsolutePath());
      // save to file

      // String cmd = "start "+ filepath+"\\"+"julian\\bin\\julian.exe -C " +
      // filepath+"\\"+ "L1objects.jconf -module -input mic";

      String firstcmd = "start " + filepath + "\\" + "julian\\bin\\julian.exe";
      String lastpart = " -C L1objects.jconf -module -input mic";

      int indexStart = firstcmd.indexOf("\\");
      int indexEnd = firstcmd.lastIndexOf("\\");

      String startpart = firstcmd.substring(0, indexStart + 1);
      String middlepart = "\"" + firstcmd.substring(indexStart + 1, indexEnd) + "\"";
      String endpart = firstcmd.substring(indexEnd);

      String cmd = startpart + middlepart + endpart + lastpart;

      System.out.println(cmd);
      // System.out.println(firstcmd);
      // System.out.println(startpart);
      // System.out.println(middlepart);
      // System.out.println(endpart);
      // System.out.println("first: "+indexStart +"; end: "+ indexEnd);

      BufferedWriter batBW = new BufferedWriter(new FileWriter(batFile));
      if (batBW != null) {
        batBW.write(cmd);
        batBW.newLine();
        logger.debug(cmd);
        batBW.flush();
        batBW.close();
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public void run() {

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    // Julian.writeJulianBat();
    // Julian.startJulian();
    JulianThread js = new JulianThread();
    js.start();
    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }
    // js.destroy();
    js.notrun();

  }

}

class JulianThread extends Thread {
  Process myprocess;

  public JulianThread() {

  }

  public void run() {
    try {
      myprocess = Runtime.getRuntime().exec("./Julian/bin/julian.exe -C L1objects.jconf -module -input mic");
      // PrintStream display = new PrintStream(process.getOutputStream());

      // display.println("./Julian/bin/julian.exe -C L1objects.jconf -module -input mic");
      // Thread.sleep(2000);
      // process.destroy();

      // display.close();
    } catch (IOException e) {
      System.out.println("IOException");
    }
  }

  public void notrun() {
    myprocess.destroy();
  }
}
