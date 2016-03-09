/**
 * Created on 2008/01/13
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.audio;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {

  private AudioFormat format;
  private byte[] samples;

  // privateInputStream stream;

  public Player(String filename) {
    try {
      // open the audio input stream
      AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
      format = stream.getFormat();
      // get the audio samples
      samples = getSamples(stream);
      // stream =new ByteArrayInputStream(sound.getSamples());
    } catch (UnsupportedAudioFileException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public byte[] getSamples() {
    return samples;
  }

  private byte[] getSamples(AudioInputStream audioStream) {
    // get the number of bytes to read
    int length = (int) (audioStream.getFrameLength() * format.getFrameSize());

    // read the entire stream
    byte[] samples = new byte[length];
    DataInputStream is = new DataInputStream(audioStream);
    try {
      is.readFully(samples);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    // return the samples
    return samples;
  }

  public void play(InputStream source) {

    // use a short, 100ms (1/10th sec) buffer for real-time
    // change to the sound stream
    int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
    byte[] buffer = new byte[bufferSize];

    // create a line to play to
    SourceDataLine line;
    try {
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
      line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(format, bufferSize);
    } catch (LineUnavailableException ex) {
      ex.printStackTrace();
      return;
    }

    // start the line
    line.start();

    // copy data to the line
    try {
      int numBytesRead = 0;
      while (numBytesRead != -1) {
        numBytesRead = source.read(buffer, 0, buffer.length);
        if (numBytesRead != -1) {
          line.write(buffer, 0, numBytesRead);
        }
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    // wait until all data is played, then close the line
    line.drain();
    line.close();

  }

  /*
   * @param file : just name of file like "1.wav";
   */
  public void start(InputStream source) {
    play(source);
  }

  public static void main(String args[]) throws Exception {
    Player sound = new Player("wave//2007.0918.101156.wav");
    InputStream stream = new ByteArrayInputStream(sound.getSamples());
    // play the sound
    sound.play(stream);

  }

}
