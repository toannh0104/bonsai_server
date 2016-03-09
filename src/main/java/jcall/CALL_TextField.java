/**
 * Created on 2009/03/04
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall;

import javax.swing.JTextField;

public class CALL_TextField extends JTextField {

  private java.awt.Component _target = null;

  public CALL_TextField() {

    Initialize();

  }

  public CALL_TextField(String s) {

    this.setText(s);
    Initialize();

  }

  private void Initialize() {

    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == 10) {
          if (((CALL_TextField) e.getSource())._target == null) {
            CALL_TextField.this.transferFocus();
          } else {
            CALL_TextField.this._target.requestFocus();
          }
        }
      }
    });

  }

  public void ChangeNext(java.awt.Component target) {

    this._target = target;

  }

}
