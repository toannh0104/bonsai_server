/**
 * Created on 2007/08/13
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.recognition.event;

public interface EventListener {

  public void onError(String error);

  // public void onReceive() throws Exception;

  // public void onReceived(Answer answer) throws Exception;
  public void onReceived(String strAnswer) throws Exception;
}
