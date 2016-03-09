/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jcall.CALL_diagramInstanceStruct;
import jcall.CALL_lessonStruct;
import jcall.CALL_questionStruct;
import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.dto.ResultDto;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.dto.response.SpeechResponseDto;
import jp.co.gmo.rs.ghs.jcall.entity.Word;
import jp.co.gmo.rs.ghs.service.SpeechService;
import jp.co.gmo.rs.ghs.util.JCallUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * SpeechServiceImpl
 *
 * @author LongVNH
 *
 */
@Service
public class SpeechServiceImpl implements SpeechService {

    static final String JULIANBAT = "/startJulian.bat";
    static final String WAVLST = "wavlst";
    static final String CONFIG = "config.jconf";
    static final String GRAMMAR = "current";
    static final String SPEECHPATH = "Data\\speech\\";
    static final String SRC = "\\src\\main\\resources\\";
    static final String[] PREFIX = { "ã�•ã‚“", "æ°�", "ã��ã‚“", "å�›",
        "ã�¡ã‚ƒã‚“", "ã�•ã�¾", "æ§˜", "ã�©ã�®", "æ®¿", "ã�Šã�Ÿã��" };
    static final List<Word> WORDS = new LexiconServiceImpl().getDictionary();
    public static final String CONTEXTFILENAME = new String("currentQuestion");

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeechServiceImpl.class);

    /**
     * @param path
     * @throws IOException
     */
    public void writeJulianBat(String path) throws IOException {
        String bat = JULIANBAT;
        File fileBat = new File(path, bat);
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        if (fileBat.exists()) {
            fileBat.delete();
        }

        String operSystem = System.getProperty("os.name").toLowerCase();
        fileBat.createNewFile();
        BufferedWriter vocaBW = new BufferedWriter(new FileWriter(fileBat));
        if (vocaBW != null) {
            if (operSystem.indexOf("win") >= 0) {
                vocaBW.write("start julian/bin/julius-4.2.1.exe -C L1objects.jconf");
            }
            /*
             * else { vocaBW.write("#!/bin/bash"); vocaBW.newLine();
             * vocaBW.write(
             * "/home/julius/bin/julius -C /opt/tomcat/webapps/JCall/WEB-INF/classes/L1objects.jconf"
             * ); }
             */

            vocaBW.newLine();
            vocaBW.flush();
            vocaBW.close();
        }

    }

    /**
     * Get SpeechResult
     *
     * @param path
     *            - path of file out
     * @param filename
     *            - name of file out
     * @return SpeechResponseDto
     * @throws IOException
     */
    public SpeechResponseDto readOutfile(String path, String filename) throws IOException {
        SpeechResponseDto speechRes = new SpeechResponseDto();
        ResultDto resultDto = new ResultDto();
        String lineString;
        String sentence = "";
        String kanji = "";

        // String filePath = System.getProperty("user.dir") + SRC + SPEECHPATH;
        File fileOut = new File(path + "/Data/speech/" + filename);
        LOGGER.warn("File Out Path3:" + fileOut.getAbsolutePath());
        if (fileOut.exists()) {
            InputStream inputStream = getClass().getResourceAsStream(
                    "/Data/speech/" + filename);
            Reader reader = new InputStreamReader(inputStream, "UTF8");
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((lineString = bufferedReader.readLine()) != null) {
                if (!lineString.contains("</s>")) {
                    LOGGER.error("Error search failed!");
                    speechRes.setStatus(ConstValues.Status.NG);
                } else {
                    // SET KANA
                    if (lineString.contains("sentence1:")) {
                        sentence = StringUtils.substringAfter(lineString,
                                "sentence1");
                        sentence = StringUtils.substringBetween(sentence,
                                "<s>", "</s>").trim();
                        resultDto.setKana(sentence.trim().replaceAll("\\s", ""));
                    }
                    // SET KANJI
                    if (!StringUtils.isEmpty(sentence)) {
                        kanji = getKanji(sentence);
                        if (!StringUtils.isEmpty(kanji)) {
                            resultDto.setKanji(kanji.replaceAll("\\s", ""));
                        }
                    }
                    // SET ROMAJI
                    if (!StringUtils.isEmpty(sentence)) {
                        resultDto.setRomaji(JCallUtil
                                .strKanaToRomaji(sentence));
                    }

                }
            }
            // set resultDto to speech
            speechRes.setResultDto(resultDto);

        } else {
            LOGGER.warn("File not exist!");
            return null;
        }

        return speechRes != null ? speechRes : null;
    }

    /**
     * @param fileName
     * @param request
     * @throws IOException
     */
    public void saveFileOut(String fileName, HttpServletRequest request) throws IOException {
        String pathOut = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes").replace("\\", "/") + "/Data/speech/" + fileName;
        File file = new File(pathOut);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * @param path
     * @param fileName
     * @param request
     */
    public void checkFileOut(String path, String fileName, HttpServletRequest request) {
        String pathOut = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes").replace("\\", "/") + "/Data/speech/" + fileName;
        try {
            int count = 0;
            File fileOut = new File(pathOut);
            if (fileOut.exists()) {
                do {
                    Thread.sleep(ConstValues.ThreadControls.SLEEP);
                    count++;
                    if (checkSizeOfFile(pathOut) != 0) {
                        File fileNew = new File(pathOut);
                        File fileOld = new File(path);
                        if (fileNew.exists() && fileOld.exists()) {
                            if (fileNew.length() != fileOld.length()) {
                                break;
                            }
                        }
                    }
                } while (count < ConstValues.ThreadControls.COUNT);
                File fileOld = new File(path);
                if (fileOut.exists()) {
                    FileUtils.copyFile(fileOut, fileOld);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param path
     * @return
     */
    private double checkSizeOfFile(String path) {
        double result = 0;
        File file = new File(path);
        if (file.exists()) {
            result = file.length();
        }
        return result;
    }

    /**
     * @param word
     * @return
     */
    public String getKanjiByWord(String word) {
        String result = "";
        for (Word w : WORDS) {
            if (w.getKana().equals(word)) {
                result = w.getKanji();
            }
        }
        return !StringUtils.isEmpty(result) ? result : word;
    }

    /**
     * @param kana
     * @return
     */
    public String getKanji(String kana) {
        String kanji = "";

        String[] list = new String[ConstValues.DEFAULT_KANA_ARRAY_SIZE];
        String prefix = "";
        if (kana.contains(" ")) {
            list = kana.split(" ");
        }
        for (String string : list) {
            if (!StringUtils.isEmpty(string)) {
                for (String st : PREFIX) {
                    if (string.contains(st)) {
                        prefix = st;
                        string = StringUtils.substringBefore(string, prefix);
                    }
                }
                if (!StringUtils.isEmpty(string)) {
                    kanji += getKanjiByWord(string);
                    string = "";
                }
                if (!StringUtils.isEmpty(prefix)) {
                    kanji += getKanjiByWord(prefix);
                    prefix = "";
                }
                kanji += " ";
            }
        }
        return kanji;
    }

    /**
     * Write config file
     *
     * @param fileGrammar
     *            - name of file dfa and dict return config.jconf file
     */
    public void writeJconfig(String fileGrammar) {
        String filePath = System.getProperty("user.dir") + SRC;
        File fileConfig = new File(filePath, CONFIG);
        if (!fileConfig.exists()) {
            try {
                fileConfig.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(
                        fileConfig));
                if (writer != null) {
                    writer.write("-dfa Data/JGrammar/" + fileGrammar + ".dfa\n");
                    writer.write("-v Data/JGrammar/" + fileGrammar + ".dict\n");
                    writer.write("C julian/hmm_ptm.jconf\n");
                    writer.write("-input rawfile\n");
                    writer.write("-filelist " + WAVLST + "\n");
                    writer.write("-outfile");
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileConfig.delete();
            writeJconfig(GRAMMAR);
        }
    }

    /**
     * startJulian to create file .out
     *
     * @param path
     *            path Ã² startJulian.bat
     * @throws IOException
     * @throws InterruptedException
     */
    public void startJulian(String path, HttpServletRequest request, String user)
        throws IOException, InterruptedException {
        String fileBat;

        if (user != null) {
            File file = new File(path + "/SpeechData/" + user);

            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                // run on window
                fileBat = path + "/SpeechData/" + user + JULIANBAT;
                ProcessBuilder builder = new ProcessBuilder(fileBat);
                LOGGER.info("Bat path:" + fileBat);
                builder.directory(file);

                Process process = builder.start();
                process.waitFor();
                LOGGER.info("Julian started on window!");

            } else {
                // run on linux
                fileBat = path + "/SpeechData/"
                        + user
                        + "/startJulian.sh";
                // String[] command = { "/bin/bash", fileBat, "" };
                ProcessBuilder builder = new ProcessBuilder("/bin/bash",
                        fileBat);
                LOGGER.info("Start julian on linux.......");
                LOGGER.info(fileBat);
                // builder.directory(file);
                int i = 0;
                Process process = builder.start();
                while (true) {
                    LOGGER.info("Start Julian in: " + i + "s");
                    i++;
                    Thread.currentThread();
                    Thread.sleep(ConstValues.ThreadControls.SLEEP);
                    if (process.waitFor() == 0) {
                        break;
                    }
                }

            }
        }

    }

    /**
     * @param request
     * @param userName
     * @throws IOException
     */
    public void writeFileBatch(HttpServletRequest request, String userName) throws IOException {
        String root = "";
        String path = "";
        StringBuilder fileContent = new StringBuilder();
        String content;

        String os = System.getProperty("os.name").toLowerCase();
        root = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes/SpeechData/")
                .replace("\\", "/");

        if (os.indexOf("win") >= 0) {
            path = root + "/" + userName + "/startJulian.bat";
            content = "start ../../julian/bin/julius-4.3.1.exe -C L1objects.jconf";
        } else {
            path = root + userName + "startJulian.sh";
            content = "#!/bin/bash\n/home/julius/bin/julius -C " + root
                    + "/" + userName + "/L1objects.jconf";
        }
        File folder = new File(root + "/" + userName);
        if (!folder.exists()) {
            folder.mkdirs();
            LOGGER.info("Make dir");
        }
        File file = new File(path);
        // if file doesnt exists, then create it
        if (file.exists()) {
            file.delete();
        }
        // create file
        file.createNewFile();
        LOGGER.info("Make file bat");

        fileContent.append(content);
        FileWriter fstreamWrite = new FileWriter(path);
        BufferedWriter out = new BufferedWriter(fstreamWrite);
        out.write(fileContent.toString());
        out.flush();
        fstreamWrite.close();
        out.close();

    }

    /**
     * write file check.out
     *
     * @param request
     *            : to get path
     * @param userName
     * @throws IOException
     */
    public void fileOut(HttpServletRequest request, String userName) throws IOException {

        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes/SpeechData/" + userName);
        File folder = new File(path);
        if (folder.exists()) {
            File file = new File(path + "/check.out");
            if (!file.exists()) {
                file.createNewFile();
            }
        }

    }

    /**
     * @param request
     * @param userName
     * @throws IOException
     */
    public void writeFileL1object(HttpServletRequest request, String userName) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes/SpeechData/" + userName);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(path + "/L1objects.jconf");
        // if file doesn't exists, then create it
        if (file.exists()) {
            file.delete();
        }
        // create file
        file.createNewFile();
        LOGGER.info("Create file jconf");
        fileContent.append("-dfa JGrammar/currentQuestion.dfa" + "\n");
        fileContent.append("-v JGrammar/currentQuestion.dict" + "\n");
        fileContent.append("-outfile" + "\n");
        fileContent.append("-logfile log.txt" + "\n");
        fileContent.append("-C ../../julian/hmm_ptm.jconf" + "\n");
        fileContent.append("-quiet" + "\n");
        fileContent.append("-input rawfile" + "\n");
        fileContent.append("-filelist wavlst" + "\n");
        // fileContent.append("-cutsilence" + "\n");
        // fileContent.append("-lv 2000" + "\n");
        // fileContent.append("-zc 60" + "\n");
        fileContent.append("#-fallback1pass");
        // fileContent.append("-realtime" + "\n");

        FileWriter fstreamWrite = new FileWriter(path + "/L1objects.jconf");
        BufferedWriter out = new BufferedWriter(fstreamWrite);
        out.write(fileContent.toString());
        out.flush();
        out.close();
    }

    /**
     * @param lessonIndex
     * @param type
     * @param request
     * @return
     */
    public CALL_questionStruct nextQuestion(int lessonIndex, int type,
            HttpServletRequest request) {
        // CALL_database db = new CALL_database();
        CALL_lessonStruct lesson = DB.lessons.getLesson(lessonIndex - 1);
        CALL_questionStruct currentQuestion = new CALL_questionStruct(DB,
                CONFIG_DSTRUCT, GCONFIG_DSTRUCT, lesson, type, request);

        return currentQuestion;
    }

    /**
     * @param lessonIndex
     * @param type
     * @param request
     * @param swDto
     * @return
     * @throws IOException
     */
    public CALL_questionStruct createJGrammar(int lessonIndex, int type,
            HttpServletRequest request, List<SentenceWordDto> swDto, String userName) throws IOException {
        // CALL_database db = new CALL_database();
        CALL_lessonStruct lesson = DB.lessons.getLesson(lessonIndex - 1);
        CALL_questionStruct currentQuestion = new CALL_questionStruct(lesson, request, swDto, userName);

        return currentQuestion;
    }

    /**
     * @param questionStruct
     * @return
     */
    public CALL_diagramInstanceStruct nextDiagram(
            CALL_questionStruct questionStruct) {
        return new CALL_diagramInstanceStruct(questionStruct, DB);
    }

    /**
     * Write file wavlst
     *
     * @param fileName
     *            - name of
     * @param request
     * @throws IOException
     */
    public void writeFileWavLst(String fileName, HttpServletRequest request, String user) throws IOException {
        String rootPath = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes");
        LOGGER.info("Root path: " + rootPath);
        LOGGER.info("Username: " + user);
        // check path of wavlst folder
        File fileRoot = new File(rootPath + "/SpeechData/" + user);
        if (!fileRoot.exists()) {
            fileRoot.mkdir();
            LOGGER.info("Create folder " + user);
        }
        // check and create file walst
        File filewavlst = new File(rootPath + "/SpeechData/" + user + "/wavlst");
        if (filewavlst.exists()) {
            filewavlst.delete();
        }

        filewavlst.createNewFile();
        LOGGER.info("Create file wavlst " + filewavlst);
        BufferedWriter wb = new BufferedWriter(new FileWriter(filewavlst));
        if (wb != null) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                wb.write("../../Data/speech/" + fileName);
            } else {
                wb.write(rootPath + "/Data/speech/" + fileName);
            }
            LOGGER.info("Write content into wavlst.");
            wb.flush();
            wb.close();
        }

    }

    @Override
    public void checkLogic() {
    }

}
