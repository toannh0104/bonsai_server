/**
 * Created on 2007/01/29
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.Properties;

/**
 * @author kyoto-u
 *
 */
public class SysCmdExe {

  // String strCommand;

  public SysCmdExe() {

  }

  /**
   * @param strCommand
   *          : String an executable command like "mkfa -e1 -f L2" in which the
   *          mkfa command is in the default present path or other executable
   *          command
   */
  public static void cmdExe(String strCommand) {
    try {
      Process process = Runtime.getRuntime().exec(strCommand);

      InputStream is = process.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      // String line;

      try {
        System.out.println("process.waitFor();");
        process.waitFor();
      } catch (InterruptedException e) {
        System.err.println("process was interrupted");
      }
      System.out.println("check its exit value");
      // check its exit value
      if (process.exitValue() != 0) {
        System.err.println("exit value was non-zero");
      } else {
        System.out.println("exit successfully");
      }
      br.close();
    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  /*
   * cmdExec it will deal with error stream and inputstream but no rederect
   * function
   */
  public static int cmdExec(String args) {
    try {
      String cmd = args;
      Runtime rt = Runtime.getRuntime();
      Process proc = rt.exec(cmd);

      // any error message?
      StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERR");

      // any output?
      StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUT");

      // any output?
      // StreamGobbler inputGobbler = new
      // StreamGobbler(proc.getOutputStream(), "IN");

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal = proc.waitFor();
      System.out.println("ExitValue: " + exitVal);
      return exitVal;
    } catch (Throwable t) {
      t.printStackTrace();
      return -1;

    }
  }

  /**
   * 
   * execute command in the system shell (XP,Windows NT/Windows 95)
   * 
   * @param exeEnvPath
   *          : envrionment path
   * @param strCommand
   *          : a bat cmd like "startJCALL.bat" or a .exe command like
   *          "c:\\Julius\\mkfa -e1 -f L2" or a shell command like "dir" in
   *          which case exeEnvPath is "" or an executable command which is in
   *          the default searching path like "notepad" in which case exeEnvPath
   *          is also ""
   * 
   */
  public static void cmdExe(String exeEnvPath, String strCommand) {
    // "cmd /c start /D\"d:\\julian\\\" c:\\Julius\\mkfa -e1 -f L2"
    String cmd = "cmd";
    if (!(System.getProperty("os.name").equals("Windows XP")))
      cmd = "command.com";// Windows 95
    cmd = cmd + " /C ";
    if (exeEnvPath != null && exeEnvPath.length() > 0) {
      cmd = cmd + "start /D" + "\"" + exeEnvPath + "\"" + " ";
    }
    cmd = cmd + strCommand;
    cmdExe(cmd);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    String strCommand = "E:\\julian\\bin\\julian.exe -C E:\\julian\\testfile.jconf -filelist E:\\julian\\waves";
    //
    SysCmdExe.cmdExec(strCommand);

    System.out.println("end of cmdExe");
  }

}
