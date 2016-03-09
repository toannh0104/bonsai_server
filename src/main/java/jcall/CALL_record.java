//////////////////////////////////////////////////////////////
// IO handling methods for F1 Manager
//
package jcall;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

/**
 * Handles the timing functions, such as getting current time/date etc
 */

public class CALL_record extends Thread {
  private TargetDataLine m_line;
  private AudioFileFormat.Type m_targetType;
  private AudioInputStream m_audioInputStream;
  private File m_outputFile;

  public CALL_record(TargetDataLine line, AudioFileFormat.Type targetType, String file) {
    m_line = line;
    m_audioInputStream = new AudioInputStream(line);
    m_targetType = targetType;
    m_outputFile = new File(file);
  }

  /**
   * Starts the recording. To accomplish this, (i) the line is started and (ii)
   * the thread is started.
   */
  public void start() {
    // Starting the TargetDataLine. It tells the line that
    // we now want to read data from it. If this method
    // isn't called, we won't
    // be able to read data from the line at all.

    m_line.start();

    // Starting the thread.
    super.start();
  }

  // Stops the recording.
  public void stopRecording() {
    m_line.stop();
    m_line.close();
  }

  // Main working method.
  public void run() {
    try {
      AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
