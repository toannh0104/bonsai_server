/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.co.gmo.rs.ghs.constant.ConstValues;
import jp.co.gmo.rs.ghs.constant.MessageConst;
import jp.co.gmo.rs.ghs.dto.ResultDto;
import jp.co.gmo.rs.ghs.dto.SentenceWordDto;
import jp.co.gmo.rs.ghs.dto.UserSessionDto;
import jp.co.gmo.rs.ghs.dto.response.SpeechResponseDto;
import jp.co.gmo.rs.ghs.service.SpeechService;
import jp.co.gmo.rs.ghs.util.AudioUtils;
import jp.co.gmo.rs.ghs.util.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * SpeechController
 *
 * @author LongVNH
 *
 */
@Controller
@RequestMapping(value = "/speech")
public class SpeechController extends BaseController {

    @Autowired
    private SpeechService speechService;

    /**
     * @param file
     * @param lessonId
     * @param txtQuestion
     * @param modeLanguage
     * @param session
     * @param request
     * @return
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    @RequestMapping(value = "/uploadFileBlob", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public SpeechResponseDto fileUpload(@RequestParam(value = "file", required = true) MultipartFile file,
        @RequestParam(value = "lessonId", required = true) String lessonId,
        @RequestParam(value = "txtQuestion", required = true) String txtQuestion,
        @RequestParam(value = "modeLanguage", required = true) Integer modeLanguage,
        HttpSession session, HttpServletRequest request)
        throws IOException, LineUnavailableException, UnsupportedAudioFileException {

        // Init data
        String msg = "";
        SpeechResponseDto speechRes = new SpeechResponseDto();
        String fileOut = "";

        try {
            String userName = "";
            UserSessionDto userSessionDto = this.getUserSessionDto();
            if (userSessionDto != null) {
                userName = userSessionDto.getUserName();
            } else {
                speechRes.setMessage(MessageConst.EMSG0003);
                speechRes.setStatus(ConstValues.Status.NG);
                return speechRes;
            }

            // create file batch
            speechService.writeFileBatch(request, userName);
            // create file L1object
            speechService.writeFileL1object(request, userName);
            // create file check.out
            speechService.fileOut(request, userName);

            // json to obj
            ObjectMapper mapper = new ObjectMapper();
            // Decode URI
            List<SentenceWordDto> swDto =
                    mapper.readValue(URLDecoder.decode(txtQuestion, "UTF-8"), new TypeReference<List<SentenceWordDto>>() { });

            // create voca gramma
            if (!swDto.isEmpty()) {
                speechService.createJGrammar(Integer.valueOf(swDto.get(0).getLessonNo()), 0, request, swDto, userName);

                String fileName = file.getOriginalFilename();
                byte[] audioData = file.getBytes();
                // Creating the directory to store file
                msg += "Creating the directory to store file\n";
                String rootPath = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/classes");
                File dir = new File(rootPath + File.separator + "Data"
                        + File.separator + "speech");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Create the file on server
                msg += "Create the file on server \n";
                File serverFile = new File(dir.getAbsolutePath() + File.separator
                        + fileName);

                // upload file to server
                writeWav(audioData, serverFile);
                msg += "Convert audio to wav file with same rate 16000HZ \n";
                msg += "Upload file to server with fileName : " + fileName
                        + ", Destination : " + serverFile + " \n";

                // write file wavlst
                speechService.writeFileWavLst(fileName, request, userName);
                msg += "Update file wavlst \n";

                // start Julian
                String path = request.getSession().getServletContext()
                        .getRealPath("/WEB-INF/classes").replace("\\", "/")
                        + "/SpeechData/" + userName + "/check.out";
                fileOut = StringUtils.substringBefore(fileName, ".")
                        + ".out";
                speechService.saveFileOut(fileOut, request);
                msg += "Create file .out \n";
                speechService.startJulian(rootPath.replace("\\", "/"), request, userName);
                msg += "Start julian \n";
                speechService.checkFileOut(path, fileOut, request);
                msg += "Check file out \n";
                msg += "Process complete! \n";

                // msg for server
                System.out.println(msg);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            speechRes.setMessage(MessageConst.EMSG0002);
            speechRes.setStatus(ConstValues.Status.NG);
            return speechRes;
        }

        // get speech msg
        ResultDto reDto = getResult(fileOut, request, modeLanguage);
        if (reDto == null) {
            speechRes.setMessage(MessageConst.EMSG0002);
            speechRes.setStatus(ConstValues.Status.NG);
            return speechRes;
        }

        // success
        speechRes.setMessage(MessageConst.EMSG0001);
        speechRes.setStatus(ConstValues.Status.OK);
        speechRes.setResultDto(reDto);

        return speechRes;
    }

    /**
     * Get speech txt romaji, kana, kanji
     *
     * @param fileName
     * @param request
     * @param modeLanguage
     * @return
     * @throws IOException
     */
    public ResultDto getResult(String fileName, HttpServletRequest request, Integer modeLanguage)
        throws IOException {
        ResultDto reDto = null;
        String path = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/classes").replace("\\", "/");

        String fileout = StringUtils.substringBefore(fileName, ".")
                + ".out";
        // check file out exist
        File fout = new File(path + "/Data/speech/" + fileout);
        if (!fout.exists()) {
            return reDto;
        } else {
            SpeechResponseDto speechRes = speechService.readOutfile(path, fileout);
            String strRomaji = speechRes.getResultDto().getRomaji();
            String strKana = speechRes.getResultDto().getKana();
            String strKanji = speechRes.getResultDto().getKanji();

            // check all fields is empty
            if (StringUtils.isEmpty(strKana)) {
                return reDto;
            } else {
                reDto = new ResultDto();
                reDto.setFileName(fileout);
                switch (modeLanguage) {
                    case 0:
                        if (!StringUtils.isEmpty(strRomaji)) {
                            reDto.setRomaji(strRomaji);
                        } else {
                            reDto.setRomaji(strKana);
                        }
                        break;
                    case 1:
                        reDto.setKana(strKana);
                        break;
                    case 2:
                        if (!StringUtils.isEmpty(strKanji)) {
                            reDto.setKanji(strKanji);
                        } else {
                            reDto.setKanji(strKana);
                        }
                        break;
                    default:
                        reDto.setKana(strKana);
                        break;
                }
            }
        }

        return reDto;
    }

    /**
     * @param theResult
     * @param outfile
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
    private void writeWav(byte[] theResult, File outfile)
        throws UnsupportedAudioFileException, IOException {
        // now convert theResult into a wav file
        InputStream is = new ByteArrayInputStream(theResult);
        AudioInputStream audioInputStream = null;

        audioInputStream = AudioSystem.getAudioInputStream(is);
        audioInputStream = new AudioUtils().convertStream(audioInputStream,
                ConstValues.SAMPLE_RATE_WAV, 1);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
                outfile);

    }

}
