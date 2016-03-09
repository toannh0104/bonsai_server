package jcall.recognition.event;

import java.util.ArrayList;

/**
 *
 * @author
 * @version 1.0
 */
public class Notifier {

  private static ArrayList listeners = null;
  private static Notifier instance = null;

  private Notifier() {
    listeners = new ArrayList();
  }

  public static synchronized Notifier getNotifier() {
    if (instance == null) {
      instance = new Notifier();
      return instance;
    } else
      return instance;
  }

  public void addListener(EventListener l) {
    synchronized (listeners) {
      if (!listeners.contains(l))
        listeners.add(l);
    }
  }

  // public void fireOnReceive() throws Exception {
  // for (int i = listeners.size() - 1; i >= 0; i--)
  // ( (EventListener) listeners.get(i)).onReceive();
  // }

  public void fireOnReceived(String strAnswer) throws Exception {
    for (int i = listeners.size() - 1; i >= 0; i--)
      ((EventListener) listeners.get(i)).onReceived(strAnswer);
  }

  public void fireOnError(String error) {
    for (int i = listeners.size() - 1; i >= 0; i--)
      ((EventListener) listeners.get(i)).onError(error);
  }
}
