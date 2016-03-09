/**
 * Created on 2007/01/25
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class WavePlayer {
  private SourceDataLine sourceLine;
  private AudioFileFormat.Type sourceType;
  private AudioFormat audioFormat;
  private static int bufSize;
  private FileInputStream fileInputStream;
  private static final String codebase = "./";
  private String filename;

  public WavePlayer() {
    audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 1, 2, 44100.0F, false);

    sourceType = AudioFileFormat.Type.WAVE;

    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
    sourceLine = null;
    try {
      sourceLine = (SourceDataLine) AudioSystem.getLine(info);
      // //make sure the buffer size is integral frames
      bufSize = (int) (audioFormat.getSampleRate());
      System.out.println("bufSize -----" + bufSize + "--------samplerate" + audioFormat.getSampleRate());
      // bufSize=bufSize % audioFormat.getFrameSize();
      // System.out.println("bufSize -----" +bufSize);
      sourceLine.open(audioFormat, bufSize);

    } catch (LineUnavailableException e) {

      System.out.println("in WavePlayer:" + e.getCause());
    }

  }

  /*
   * @param file : just name of file like "1.wav";
   */
  public void start(String file) {
    filename = file;
    sourceLine.flush();
    sourceLine.start();
    int numBytesRead = 0;
    byte[] data = new byte[bufSize];

    try {

      fileInputStream = new FileInputStream(new File(codebase, filename));
      numBytesRead = fileInputStream.read(data, 0, bufSize);
      while (numBytesRead != -1) {
        sourceLine.write(data, 0, bufSize);
        numBytesRead = fileInputStream.read(data, 0, bufSize);
      }
    } catch (FileNotFoundException e) {
      System.out.println("file not found");
      System.exit(1);
    } catch (IOException e) {
      System.exit(1);
    }

  }

  public void stop() {
    sourceLine.stop();
    sourceLine.close();

  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    WavePlayer wp = new WavePlayer();
    wp.start("1.wav");
    // Timer time = new Timer(6000,null);
    // time.start();
  }

}
