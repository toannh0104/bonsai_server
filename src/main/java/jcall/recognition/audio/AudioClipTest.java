/**
 * Created on 2007/01/25
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AudioClipTest extends Applet implements MouseListener {

  AudioClip audio;

  public void init() {
    audio = getAudioClip(getCodeBase(), "1.wav");
    System.out.println("getDocumentBase is --------" + getCodeBase());
    addMouseListener(this);
  }

  public void mousePressed(MouseEvent evt) {
    if (audio != null)
      audio.play();
  }

  public void mouseEntered(MouseEvent me) {
  }

  public void mouseExited(MouseEvent me) {
  }

  public void mouseClicked(MouseEvent me) {
  }

  public void mouseReleased(MouseEvent me) {
  }

}
