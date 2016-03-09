package jcall.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class FileManager {

  protected static Logger logger = Logger.getLogger(FileManager.class);

  /*
   * check file exists
   */
  public static boolean checkFile(String fullpathname) {

    File oneFile = new File(fullpathname);
    if (!oneFile.exists()) {

      return false;
    } else {
      return true;
    }
  }

  /*
   * check file exists, if exist,delete then create
   */
  public static File createFile(String fullpathname) {

    File oneFile = new File(fullpathname);
    if (oneFile.exists()) {
      oneFile.delete();
    }

    try {
      oneFile.createNewFile();
      System.out.println("create new file---" + oneFile.getAbsolutePath());
    } catch (IOException e) {
      System.out.println("can not create a new file,--exception ---" + e.toString());

    }

    return oneFile;
  }

  /*
   * check file path and make dir,create new file (if old exists, delete)
   */
  public static int createFile(String path, String name) {

    File oneFile = new File(path, name);
    File filePath = new File(path);
    if (!filePath.exists()) {
      filePath.mkdir();
    }
    if (!oneFile.exists()) {
      try {
        oneFile.createNewFile();
        System.out.println("create new file---" + oneFile.getAbsolutePath());
      } catch (IOException e) {
        System.out.println("can not create a new file,--exception ---" + e.toString());
        return 0;
      }
      System.out.println("update all data,creat new file---" + oneFile.getAbsolutePath());
    }
    return 1;
  }

  /**
   * Return the contents of the provided File as a String
   * 
   * @param file
   * @return
   */
  public static String contentsOfFile(File file) {
    String s = new String();
    char[] buff = new char[50000];
    // InputStream is;
    InputStreamReader reader;
    try {
      reader = new FileReader(file);
      int nch;
      while ((nch = reader.read(buff, 0, buff.length)) != -1) {
        s = s + new String(buff, 0, nch);
      }
    } catch (java.io.IOException ex) {
      s = null;
    }
    return s;
  }

  /**
   * Return the contents of the provided file name as a String
   * 
   * @param fileName
   * @return
   */
  public static String contentsOfFile(String fileName) {
    return contentsOfFile(new File(fileName));
  }

  /**
     * 
     */
  public static void copy(File inFile, File outFile) throws IOException {

    if (inFile.getCanonicalPath().equals(outFile.getCanonicalPath())) {
      // inFile and outFile are the same,
      // hence no copying is required
      return;
    }
    FileInputStream fin = new FileInputStream(inFile);
    FileOutputStream fout = new FileOutputStream(outFile);
    copy(fin, fout);
  }

  /**
     * 
     */
  public static void copy(InputStream in, OutputStream out) throws IOException {

    synchronized (in) {
      synchronized (out) {

        byte[] buffer = new byte[256];
        try {
          while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1)
              break;
            out.write(buffer, 0, bytesRead);
          }
        } catch (IOException e) {
          closeStreams(in, out);
          throw e;
        }
      }
      closeStreams(in, out);

    }

  }

  /**
   * Close the provided input and output streams. Ignore any exception.
   * 
   * @param inputStream
   * @param outputStream
   */
  public static void closeStreams(InputStream inputStream, OutputStream outputStream) {
    try {
      inputStream.close();
      outputStream.close();
    } catch (Exception e) {
    }
  }

  /**
   * Ensure that the provided file exists. If it does not, create it along with
   * any necessary directories in the path.
   * 
   * @param file
   * @return
   */
  public static boolean ensureFilePathExists(File file) {
    File path;
    try {
      path = new File(file.getCanonicalPath());
    } catch (IOException e) {
      logger.debug("IOException on file " + file);
      return false;
    }
    if (!path.exists()) {
      File parent = new File(path.getParent());
      logger.debug("path does not exist, call self with parent " + parent);
      ensurePathExists(parent);
    }
    return true;
  }

  /**
   * Ensure that the path to the provided file exists. If it does not, create
   * it.
   * 
   * @param file
   * @return
   */
  public static boolean ensurePathExists(File file) {
    File path;
    try {
      path = new File(file.getCanonicalPath());
    } catch (IOException e) {
      logger.debug("IOException on file " + file);
      return false;
    }
    if (!path.exists()) {
      if (path.getParent() == null) {
        logger.debug("Operation terminating. Unable to get parent for " + path.getAbsolutePath());
        return false;
      }
      File parent = new File(path.getParent());
      ensurePathExists(parent);
      boolean result = path.mkdir();
      logger.debug("result " + result + "  mkdir for " + path);
      return result;

    }
    return true;
  }

  /**
   * Ensure that the provided file name exists. If it does not, create it along
   * with any necessary directories in the path.
   * 
   * 
   * @param fileString
   * @return
   */
  public static boolean ensureFilePathExists(String fileString) {
    return ensureFilePathExists(new File(fileString));
  }

  /**
   * Ensure that the path to the provided file name exists. If it does not,
   * create it.
   * 
   * @param fileString
   * @return
   */
  public static boolean ensurePathExists(String fileString) {
    return ensurePathExists(new File(fileString));
  }

  public static PrintStream newPrintStreamOnFileNamed(File directory, String name) throws IOException {
    File file = new File(directory, name);
    logger.debug("creating file " + name + " in " + directory.toString());
    return new PrintStream(new FileOutputStream(file));
  }

  /**
   * Create and return a PrintWriter for the provided directory and file name.
   * 
   * @param directory
   * @param name
   * @return
   * @throws IOException
   */
  public static PrintWriter newPrintWriterOnFileNamed(File directory, String name) throws IOException {
    File file = new File(directory, name);
    return new PrintWriter(new FileOutputStream(file));
  }

  /**
   * Convert the provided File into a BufferedInputStream. Then read the stream
   * and return the Object that was read.
   * 
   * @param file
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Object readObjectFromBufferedFileObject(File file) throws IOException, ClassNotFoundException {
    ObjectInputStream ois;
    Object result = null;
    logger.debug("Opening input stream...");
    FileInputStream fis = new FileInputStream(file);
    BufferedInputStream bis = new BufferedInputStream(fis, 4096);
    ois = new ObjectInputStream(bis);
    logger.debug("Reading serialized object from stream...");
    result = ois.readObject();
    return (result);
  }

  /**
   * Read the provded file and return the contents as an Object.
   *
   * @param file
   *          java.lang.String
   * @return java.lang.Object
   * @exception IOException
   *              Description of Exception
   * @exception ClassNotFoundException
   *              Description of Exception
   */
  public static Object readObjectFromFile(File file) throws IOException, ClassNotFoundException {
    logger.debug("File " + file.toString());
    return readObjectFromBufferedFileObject(file);
  }

  /**
   * Read the contents of the provided file name residing in the provided
   * directory. Return the contents as an Object.
   *
   * @param directory
   * @param name
   * @return java.lang.Object
   * @exception IOException
   * @exception ClassNotFoundException
   */
  public static Object readObjectFromFile(File directory, String name) throws IOException, ClassNotFoundException {
    File file;
    file = new File(directory, name);
    logger.debug(directory.toString() + " " + name);
    return readObjectFromBufferedFileObject(file);
  }

  /**
   * Read the contents of the provided file name residing in the provided
   * directory. Return the contents as an Object.
   *
   * @param fileWithPath
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Object readObjectFromFileWithPath(String fileWithPath) throws IOException, ClassNotFoundException {
    return readObjectFromBufferedFileObject(new File(fileWithPath));
  }

  /**
   * Redirect standard output to the log file.
   */
  public static void redirectStandardOutput() {
    redirectStandardOutput(getLogFileName());
  }

  /**
   * Redirect standard output to the provided File.
   * 
   * @param file
   */
  public static void redirectStandardOutput(File file) {
    try {
      logger.debug("redirectStandardOutput to " + file.getAbsolutePath());
      PrintStream stdout = new PrintStream(new FileOutputStream(file));
      System.setOut(stdout);
    } catch (Exception e) {
      logger.debug("Unable to redirect stdout to " + file.getAbsolutePath());
    }
  }

  /**
   * Redirect standard output to the provided file name.
   * 
   * @param file
   */
  public static void redirectStandardOutput(String fileName) {
    redirectStandardOutput(new File(fileName));
  }

  /**
   * Return the contents of the provided file as a String.
   * 
   * @param file
   * @return
   */
  public static String stringFromFile(File file) {
    return contentsOfFile(file);
  }

  /**
   * Return the contents of the provided file name as a String.
   * 
   * @param file
   * @return
   */
  public static String stringFromFile(String fileName) {
    return contentsOfFile(new File(fileName));
  }

  /**
   * Convert the provided String into the provided File.
   * 
   * @param string
   * @param file
   */
  public static void stringToFile(String string, File file) {

    try {
      PrintWriter out = new PrintWriter(new FileWriter(file));
      out.print(string);
      out.close();
    } catch (FileNotFoundException fnfe) {
      String msg = "File not found exception: " + file;
      logger.error(msg);
    } catch (IOException ioe) {
      String msg = "IO Exception writing object output stream to file: " + file;
      logger.error(msg);
    }
  }

  /**
   * Convert the provided String into a File of the provided file name.
   * 
   * @param string
   * @param file
   */
  public static void stringToFile(String string, String fileString) {

    stringToFile(string, new File(fileString));
  }

  /**
   * Convert the provided String into the provided FileOutputStream.
   * 
   * @param string
   * @param fos
   * @throws IOException
   */
  public static void stringToFileOutputStream(String string, FileOutputStream fos) throws IOException {
    logger.debug("");
    fos.write(string.getBytes());
  }

  /**
   * Write the provided object to a file of the provided directory and file
   * name.
   * 
   * @param object
   * @param directory
   * @param name
   * @throws IOException
   */
  public static void writeObjectToFile(Object object, File directory, String name) throws IOException {
    ObjectOutputStream oos;
    File file;

    logger.debug("creating file " + name + " in " + directory.toString());
    file = new File(directory, name);
    writeObjectToFileObject(object, file);
  }

  /**
   * Write the provided object to the provided File.
   * 
   * @param object
   * @param file
   * @throws IOException
   */
  public static void writeObjectToFileObject(Object object, File file) throws IOException {
    ObjectOutputStream oos = null;
    logger.debug("creating output stream on file...");
    oos = new ObjectOutputStream(new FileOutputStream(file));
    logger.debug("writing object to stream...");
    oos.writeObject(object);
    logger.debug("closing stream...");
    oos.close();
  }

  /**
   * Write the provided object to the provided file name.
   * 
   * @param object
   * @param fileAndPath
   * @throws IOException
   */
  public static void writeObjectToFileWithPath(Object object, String fileAndPath) throws IOException {
    writeObjectToFileObject(object, new File(fileAndPath));
  }

  /**
   * Write the provided iterator to a file of the provided file name.
   * 
   * @param it
   * @param fileName
   * @throws IOException
   */
  public static void iteratorToFile(Iterator it, String fileName) throws IOException {
    logger.debug("");
    FileOutputStream stream = new FileOutputStream(fileName);
    while (it.hasNext()) {
      stringToFileOutputStream(it.next() + "\n", stream);
    }

  }

  /**
   * Return the provided File as a BufferedReader
   * 
   * @param file
   * @return
   * @throws FileNotFoundException
   */
  public static java.io.BufferedReader getBufferedReaderFromFile(File file) throws FileNotFoundException {
    BufferedReader bufferedReader = null;
    FileReader frdr = new FileReader(file);
    bufferedReader = new BufferedReader(frdr);
    return bufferedReader;
  }

  /**
   * Return the provided file name as a BufferedReader
   * 
   * @param fileString
   * @return
   * @throws FileNotFoundException
   */
  public static java.io.BufferedReader getBufferedReaderFromFile(String fileString) throws FileNotFoundException {
    return getBufferedReaderFromFile(new File(fileString));
  }

  public static String getExceptionMessage(Exception e, File file) {
    return file.toString() + ": " + e.toString() + " Msg: " + e.getMessage();
  }

  /**
   * Return the name of the log file.
   * 
   * @return
   */
  public static String getLogFileName() {

    // Create a new output stream for the standard output.
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MMddyy.HHmmssSSS");
    String fileName = null;// PropertyUtility.getBaseDirectory() + "/logs/." +
                           // formatter.format(new java.util.Date()) + ".log";

    return fileName;
  }

  /**
   * Return a File for an existing file. If we can't find it by the fileName,
   * find it in the classpath. If that fails, return null.
   * 
   * @param fileName
   * @return
   */
  public static File findExistingFile(String fileName) {
    File file = null;
    file = new File(fileName);
    if (!file.exists()) {
      file = null;
      logger.debug("find as resource");
      URL url = FileManager.class.getResource("/" + fileName);
      if (url != null) {
        String urlFileName = url.getFile();
        file = new File(urlFileName);
      }
    }

    if (file != null) {
      logger.debug("file " + file.getAbsolutePath() + " " + file.exists());
    } else {
      logger.debug("file null");
    }
    return file;
  }

  /**
   * Return the provided InputStream as a String
   * 
   * @param inputStream
   * @return
   */
  public static String streamToString(InputStream inputStream) {
    String s = new String();
    char[] buff = new char[50000];
    try {
      InputStreamReader reader = new InputStreamReader(inputStream);
      int nch;
      while ((nch = reader.read(buff, 0, buff.length)) != -1) {
        s = s + new String(buff, 0, nch);
      }
    } catch (java.io.IOException ex) {
      s = null;
    }
    return s;
  }

}
