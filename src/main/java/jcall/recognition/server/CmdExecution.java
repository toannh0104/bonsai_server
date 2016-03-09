/**
 * Created on 2007/09/07
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.server;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

public class CmdExecution {

  public static void ExeCmd(String args) {

    try {
      String cmd = args;

      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(cmd);

      // any error message?
      StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");

      // any output?
      StreamGobbler outputGobbler = new
      // StreamGobbler(proc.getInputStream(), "OUT");

      StreamGobbler(proc.getInputStream(), "OUT", System.out);

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal = proc.waitFor();
      System.out.println("ExitValue: " + exitVal);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public static void main(String args[]) {

    // String cmd =
    // ".\\Julian\\bin\\julian -C L1objects.jconf -module -input mic";
    // C:\eclipse\workspace\JCALLSpokenExercise

    // String cmd =
    // "C:\\eclipse\\workspace\\JCALLSpokenExercise\\Julian\\bin\\julian -C C:\\eclipse\\workspace\\JCALLSpokenExercise\\L1objects.jconf -module -input mic";
    //
    // // String cmd = ".\\Julian\\bin\\julian -C .\\L1objects.jconf -module";
    // //can not be executed
    //
    //
    // System.out.println("befor execution");
    //
    // // String cmd =
    // "D:\\julian\\bin\\julian -C D:\\julian\\testmic.jconf -module -input mic";
    // ExeCmd(cmd);

    //
    /*
     * when cmd use the direct path it can execute successfully BUT can not stop
     * and not print out "after execution".
     */

    // String cmdBat =
    // "cmd /c start C:\\eclipse\\workspace\\JCALLSpokenExercise\\CALLJ.bat";
    /*
     * can not execute because can not find the indirect rootwhen the callj.bat
     * is echo starting julian.... start julian\bin\julian.exe -C
     * L1objects.jconf -module -input mic rem start JCALL for practicing echo
     * starting JCALL.... start CALLJ.exe
     * 
     * And it still does not work after I changed the path with "./" as start
     * .\julian\bin\julian.exe -C .\L1objects.jconf -module -input mic start
     * .\CALLJ.exe
     * 
     * 
     * And it works after I changed the path with the absolute path BUT the
     * callj process stoppedd when loading the resources the system print out
     * (""after execution"")
     */

    // String cmdBat =
    // "cmd /c start C:\\eclipse\\workspace\\JCALLSpokenExercise\\CALLJ.bat";
    String cmdBat = "C:\\eclipse\\workspace\\JCALLSpokenExercise\\startJulian.bat /c";
    ExeCmd(cmdBat);
    //
    System.out.println("after execution");

  }

}
