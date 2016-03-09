/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jcall.recognition.languagemodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import jcall.recognition.util.SysCmdExe;

import org.apache.regexp.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author longVNH
 *
 */
public class MakeDFA {
    private static final Logger LOGGER = LoggerFactory.getLogger(MakeDFA.class);

    private static String gramfile;

    private static String vocafile;

    private static String dfafile;

    private static String dictfile;

    private static String path;

    private static String pathfile;

    private static HttpServletRequest servletRequest;

    BufferedReader br;

    PrintWriter pw;

    private String user;

    public MakeDFA() {
        init();
    }

    public MakeDFA(HttpServletRequest request) {
        servletRequest = request;
        init();
    }

    public MakeDFA(HttpServletRequest request, String userName) {
        servletRequest = request;
        user = userName;
        init();
    }

    private void init() {
        // path = FindConfig.getConfig().findStr("JGRAMBASE");
        // fix to web path
        path = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes");

        gramfile = new String();
        vocafile = new String();
        dfafile = new String();
        dictfile = new String();

    }

    /*
     * generate reverse grammar file
     */
    public void getRGramFile(String fullpathname, String fullpathnewfile) {
        LOGGER.warn("enter getRGramFile");
        String strReplace1 = "";
        int rulenum = 0;
        boolean boo = setReaderWriter(fullpathname, fullpathnewfile);
        if (!boo) {
            LOGGER.error("Not a correct grammar file");
        } else {
            String line;
            try {
                line = br.readLine();
                while (line != null) {
                    strReplace1 = getRGramLine(line);
                    if (strReplace1.trim().length() > 0) {
                        pw.println(strReplace1);
                        rulenum++;
                    }
                    line = br.readLine();
                    strReplace1 = "";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            closeReaderWriter();
        }
        LOGGER.warn("This grammar file has " + rulenum + " rules ");
    }

    /*
     * set new BufferedReader,PrintWriter
     */
    private boolean setReaderWriter(String fullpathname, String fullpathnewfile) {
        File oneFile = new File(fullpathname);
        File tempFile = new File(fullpathnewfile);
        boolean booResult = false;
        if (!oneFile.exists()) {
            LOGGER.error("the file do not exist, in setReaderWriter ");
        } else {
            if (tempFile.exists()) {
                tempFile.delete();
            }
            try {
                System.out.println("in try of setReaderWriter");
                tempFile.createNewFile();
                br = new BufferedReader(new InputStreamReader(new FileInputStream(oneFile), "UTF8"));
                pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF8"), true);
                booResult = true;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                LOGGER.error("file not found in getRGramFile");
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return booResult;
    }

    /*
     * get temptory voca file
     */
    public void getTempVocaFile(String fullpathname, String fullpathnewfile) {
        LOGGER.warn("enter getTempVocaFile");
        String strTemp = "";
        int n1 = 0;
        int n2 = 0;
        boolean boo = setReaderWriter(fullpathname, fullpathnewfile);
        if (!boo) {
            LOGGER.error("No correct voca file");
        } else {
            String line;
            try {
                String strRegex = "^%[ \t]*([A-Za-z0-9_]*)";
                RE re = new RE(strRegex);
                line = br.readLine();
                while (line != null) {
                    if (re.match(line)) {
                        strTemp = re.getParen(1);
                        // System.out.println("matched "+strTemp);
                        if (strTemp.trim().length() > 0) {
                            pw.println("#" + strTemp);
                        }
                        n1++;
                    } else {
                        n2++;
                    }
                    line = br.readLine();
                    strTemp = "";
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            closeReaderWriter();
        }
        LOGGER.warn("This voca file has " + n1 + " categories " + n2 + " words");

    }

    private void closeReaderWriter() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (pw != null) {
            pw.close();
        }
    }

    private String getRGramLine(String line) {
        String[] vReplace;
        String strReplace = "";
        if (line.length() > 0) {
            line = line.replaceAll("#.*", " ");
            line = line.trim();
            if (line.length() > 0) {
                vReplace = line.split(":");
                if (vReplace.length != 2) {
                    LOGGER.debug("grammar constructure is invalid");
                } else {
                    LOGGER.debug("grammar constructure is valid");
                    strReplace = vReplace[0] + ":";
                    // System.out.println("vReplace[0] is
                    // "+vReplace[0]+"vReplace[1] is" + vReplace[1]);
                    String str = reverse(vReplace[1], " +");
                    if (str.trim().length() > 0) {
                        strReplace = strReplace + str;
                        // System.out.println("strReplace is"+strReplace);
                    }
                    // strReplace =strReplace+ reverse(vReplace[1]," +");
                }
            }
        }
        return strReplace;
    }

    private String reverse(String str, String regex) {
        String strResult = new String();
        // logger.debug("enter reverse and split string-----" + str);
        String[] arrayTemp = str.split(regex);
        if (arrayTemp.length > 0) {
            // logger.debug("array length is " + arrayTemp.length);
            for (int i = arrayTemp.length - 1; i > 0; i--) {
                strResult += " " + arrayTemp[i];
                // logger.debug("arrayTemp[" + i + "] is " + arrayTemp[i]);
            }
        }
        return strResult;
    }

    /*
     * convert .voca to .dict
     */
    public void getDictFile(String fullpathname, String fullpathnewfile) {
        LOGGER.warn("Make dict file");
        int id = -1; //
        String[] vReplace;
        String strTemp;
        boolean boo = setReaderWriter(fullpathname, fullpathnewfile);
        if (!boo) {
            LOGGER.error("No correct voca file");
        } else {
            String line;
            try {
                line = br.readLine();
                while (line != null) {
                    line = line.replaceAll("#.*", " ");
                    line = line.trim();
                    if (line.length() > 0) {
                        if (line.startsWith("%")) {
                            id++;
                        } else {
                            // logger.debug("line is " + line);
                            vReplace = line.split("[ \t]+");
                            // logger.debug("vReplace has " + vReplace.length);
                            strTemp = id + "\t" + "[" + vReplace[0] + "]\t";
                            for (int i = 1; i < vReplace.length - 1; i++) {
                                strTemp += vReplace[i] + " ";
                            }
                            strTemp += vReplace[vReplace.length - 1];
                            pw.println(strTemp);
                        }
                    }
                    line = br.readLine();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            closeReaderWriter();
        }

    }

    /*
     * the difference between toDFAFile and getDFAFile is here the temp voca and
     * grammar file is in the temporary running environment.
     */

    public void toDFAFile(String contextfilebase) {
        // String contextfilebase="L1\\L1";
        pathfile = path + "\\" + contextfilebase;
        // pathfile = "D:\\julian"+"\\"+contextfilebase;
        // pathfile = contextfilebase;
        String origGramfile = pathfile + ".grammar";
        String origVocafile = pathfile + ".voca";
        System.out.println("origGramfile in toDFAFile : " + origGramfile);
        dictfile = pathfile + ".dict";
        dfafile = pathfile + ".dfa";
        // gramfile = pathfile + "new.grammar";
        // vocafile = pathfile + "new.voca";
        // temporary file directory
        String tmpdir = System.getProperty("java.io.tmpdir");
        // System.out.println(tmpdir);
        gramfile = tmpdir + "\\" + contextfilebase + ".grammar";
        vocafile = tmpdir + "\\" + contextfilebase + ".voca";

        String hfile = pathfile + ".h";
        // boolean booResult = false;
        getRGramFile(origGramfile, gramfile);
        getTempVocaFile(origVocafile, vocafile);
        getDictFile(origVocafile, dictfile);
        if (pathfile != null) {
            String strCommand = "mkfa -e1 -fg " + gramfile + " -fv " + vocafile + " -fo " + dfafile + " -fh " + hfile;
            SysCmdExe.cmdExec(strCommand);
            // SysCmdExe.cmdExe(strCommand);
            // SysCmdExe.cmdExeForGrammar();
        }
        File hFile = new File(hfile);
        if (hFile.exists()) {
            hFile.delete();
        }

        // return booResult;
    }

    public void getDFAFile(String contextfilebase) {
        // String contextfilebase="L1\\L1";
        pathfile = path + "/SpeechData/" + user + "/JGrammar/" + contextfilebase;
        pathfile = pathfile.replace("\\", "/");
        LOGGER.warn("Path file:" + pathfile);

        String origGramfile = pathfile + ".grammar";
        String origVocafile = pathfile + ".voca";
        LOGGER.warn("origGramfile in getDFAFile : " + origGramfile);
        dictfile = pathfile + ".dict";
        dfafile = pathfile + ".dfa";
        gramfile = pathfile + "new.grammar";
        vocafile = pathfile + "new.voca";
        String hfile = pathfile + ".h";
        // boolean booResult = false;
        getRGramFile(origGramfile, gramfile);
        getTempVocaFile(origVocafile, vocafile);
        getDictFile(origVocafile, dictfile);
        if (pathfile != null) {
            String p = servletRequest.getSession().getServletContext().getRealPath("/WEB-INF/classes");
            String os = System.getProperty("os.name").toLowerCase();
            String strCommand;
            if (os.indexOf("win") >= 0) {
                strCommand = p.replace("\\", "/") + "/Julian/bin/mkfa -e1 -fg " + gramfile + " -fv " + vocafile + " -fo "
                        + dfafile + " -fh " + hfile;
            } else {
                // linux
                strCommand = "/home/julius/bin/mkfa -e1 -fg " + gramfile + " -fv " + vocafile + " -fo " + dfafile + " -fh "
                        + hfile;
            }
            LOGGER.warn(strCommand);
            if (strCommand != null) {
                int intReturn = SysCmdExe.cmdExec(strCommand);
                if (intReturn == 0) {
                    LOGGER.warn("succeed creating dfafile: " + dfafile);
                } else {
                    LOGGER.warn("failed to creating dfafile: " + dfafile);
                }
            }

            // SysCmdExe.cmdExe(strCommand);
            // SysCmdExe.cmdExeForGrammar();
        }

        File hFile = new File(hfile);
        if (hFile.exists()) {
            hFile.delete();
            LOGGER.warn("hfile exists in MakeDFA class");
        }

        // return booResult;
    }

    public void getDFAFile(String spath, String contextfilebase) {
        // String contextfilebase="L1\\L1";
        pathfile = spath + "\\" + contextfilebase;
        String origGramfile = pathfile + ".grammar";
        String origVocafile = pathfile + ".voca";
        LOGGER.debug("origGramfile in getDFAFile : " + origGramfile);
        dictfile = pathfile + ".dict";
        dfafile = pathfile + ".dfa";
        gramfile = pathfile + "new.grammar";
        vocafile = pathfile + "new.voca";
        String hfile = pathfile + ".h";
        // boolean booResult = false;
        getRGramFile(origGramfile, gramfile);
        getTempVocaFile(origVocafile, vocafile);
        getDictFile(origVocafile, dictfile);
        if (pathfile != null) {
            String strCommand = ".\\Julian\\bin\\mkfa -e1 -fg " + gramfile + " -fv " + vocafile + " -fo " + dfafile + " -fh "
                    + hfile;
            int intReturn = SysCmdExe.cmdExec(strCommand);

            if (intReturn == 0) {
                LOGGER.debug("succeed creating dfafile: " + dfafile);
            } else {
                LOGGER.debug("failed to creating dfafile: " + dfafile);
            }
            // SysCmdExe.cmdExe(strCommand);
            // SysCmdExe.cmdExeForGrammar();
        }

        File hFile = new File(hfile);
        if (hFile.exists()) {
            System.out.println("hfile exists in MakeDFA class");
            hFile.delete();
        }

        // return booResult;
    }

}
