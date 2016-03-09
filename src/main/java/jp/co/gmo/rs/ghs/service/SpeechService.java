/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jcall.CALL_diagramInstanceStruct;
import jcall.CALL_questionStruct;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.dto.response.SpeechResponseDto;

/**
 * create interface SpeechService
 *
 * @author LongVNH
 *
 */
public interface SpeechService extends BaseService {

    /**
     * @param path
     * @throws IOException
     */
    void writeJulianBat(String path) throws IOException;

    /**
     * Get SpeechResult
     *
     * @param path
     *            - path of file out
     * @param filename
     *            - name of file out
     * @return SpeechResponseDto
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    SpeechResponseDto readOutfile(String path, String filename) throws IOException;

    /**
     * @param fileName
     * @param request
     * @throws IOException
     */
    void saveFileOut(String fileName, HttpServletRequest request) throws IOException;

    /**
     * @param path
     * @param fileName
     * @param request
     */
    void checkFileOut(String path, String fileName, HttpServletRequest request);

    /**
     * @param word
     * @return
     */
    String getKanjiByWord(String word);

    /**
     * @param kana
     * @return
     */
    String getKanji(String kana);

    /**
     * Write config file
     *
     * @param fileGrammar
     *            - name of file dfa and dict return config.jconf file
     */
    void writeJconfig(String fileGrammar);

    /**
     * startJulian to create file .out
     *
     * @param path
     *            path Ã² startJulian.bat
     * @throws IOException
     * @throws InterruptedException
     */
    void startJulian(String path, HttpServletRequest request, String userName) throws IOException, InterruptedException;

    /**
     * @param request
     * @param userName
     * @throws IOException
     */
    void writeFileBatch(HttpServletRequest request, String userName) throws IOException;

    /**
     * write file check.out
     *
     * @param request
     *            : to get path
     * @param userName
     * @throws IOException
     */
    void fileOut(HttpServletRequest request, String userName) throws IOException;

    /**
     * @param request
     * @param userName
     * @throws IOException
     */
    void writeFileL1object(HttpServletRequest request, String userName) throws IOException;

    /**
     * @param lessonIndex
     * @param type
     * @param request
     * @return
     */
    CALL_questionStruct nextQuestion(int lessonIndex, int type,
            HttpServletRequest request);

    /**
     * @param lessonIndex
     * @param type
     * @param request
     * @param swDto
     * @param userName
     * @return
     * @throws IOException
     */
    CALL_questionStruct createJGrammar(int lessonIndex, int type,
            HttpServletRequest request, List<SentenceWordDto> swDto, String userName) throws IOException;

    /**
     * @param questionStruct
     * @return
     */
    CALL_diagramInstanceStruct nextDiagram(
            CALL_questionStruct questionStruct);

    /**
     * Write file wavlst
     *
     * @param fileName
     *            - name of
     * @param request
     * @throws IOException
     */
    void writeFileWavLst(String fileName, HttpServletRequest request, String username) throws IOException;

}
