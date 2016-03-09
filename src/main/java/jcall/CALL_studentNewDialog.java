///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CALL_studentNewDialog extends JDialog implements DocumentListener {
  // Panels
  JPanel mainPanel;
  JPanel textPanel;
  JPanel buttonPanel;

  // Labels
  JLabel nameL;
  JLabel passwordL;
  JLabel repeatPasswordL;

  // Text fields
  JTextField nameT;
  JPasswordField passwordT;
  JPasswordField repeatPasswordT;

  // Buttons
  JButton OKButton;
  JButton cancelButton;

  public CALL_studentNewDialog(CALL_main parent) {
    Insets insets;
    int width = 400;
    int height = 200;

    setSize(width, height);
    setTitle("Please enter your details");
    setResizable(false);
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setLayout(null);
    insets = mainPanel.getInsets();

    textPanel = new JPanel();
    textPanel.setLayout(null);
    textPanel.setBounds(insets.left + 5, insets.top + 5, 390, 120);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(null);
    buttonPanel.setBounds(insets.left + 5, insets.top + 135, 390, 50);

    // Add labels
    nameL = new JLabel("Name:");
    nameL.setBounds(15, 10, 100, 20);
    textPanel.add(nameL);

    passwordL = new JLabel("Password:");
    passwordL.setBounds(15, 45, 100, 20);
    textPanel.add(passwordL);

    repeatPasswordL = new JLabel("Re-Type:");
    repeatPasswordL.setBounds(15, 75, 100, 20);
    textPanel.add(repeatPasswordL);

    // Add text areas
    nameT = new JTextField("");
    nameT.setBounds(120, 10, 200, 20);
    nameT.getDocument().addDocumentListener(this);
    textPanel.add(nameT);

    passwordT = new JPasswordField("");
    passwordT.setBounds(120, 45, 200, 20);
    passwordT.getDocument().addDocumentListener(this);
    textPanel.add(passwordT);

    repeatPasswordT = new JPasswordField("");
    repeatPasswordT.setBounds(120, 75, 200, 20);
    repeatPasswordT.getDocument().addDocumentListener(this);
    textPanel.add(repeatPasswordT);

    // Add buttons
    OKButton = new JButton("Ok");
    OKButton.setBounds(310, 5, 75, 30);
    OKButton.setActionCommand("newstudent-ok");
    OKButton.addActionListener(parent);
    OKButton.setEnabled(false);
    buttonPanel.add(OKButton);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(235, 5, 75, 30);
    cancelButton.setActionCommand("newstudent-cancel");
    cancelButton.addActionListener(parent);
    buttonPanel.add(cancelButton);

    // Add panels
    mainPanel.add(buttonPanel);
    mainPanel.add(textPanel);

    getContentPane().add(mainPanel);

    // Exit program if this is closed
    // setDefaultCloseOperation(EXIT_ON_CLOSE);
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

  }

  public String getName() {
    return nameT.getText();
  }

  public boolean passwordsMatch() {
    String s1, s2;

    s1 = new String(passwordT.getPassword());
    s2 = new String(repeatPasswordT.getPassword());
    if (s2.equals(s1)) {
      return true;
    }

    return false;
  }

  public String getPassword() {
    String rString;
    rString = new String(passwordT.getPassword());
    return rString;
  }

  // For text updates
  // ////////////////////////////////////////////////////
  public void changedUpdate(DocumentEvent e) {
  }

  public void insertUpdate(DocumentEvent e) {
    String s1, s2, s3;

    s1 = new String(passwordT.getPassword());
    s2 = new String(repeatPasswordT.getPassword());
    s3 = nameT.getText();

    if ((s1 != null) && (s2 != null) && (s3 != null)) {
      if ((s1.length() != 0) && (s2.length() != 0) && (s3.length() != 0)) {
        if (s1.equals(s2)) {
          // Ok to enable the button
          OKButton.setEnabled(true);
        }
      }
    } else {
      OKButton.setEnabled(false);
    }
  }

  public void removeUpdate(DocumentEvent e) {
    String s1, s2, s3;

    s1 = new String(passwordT.getPassword());
    s2 = new String(repeatPasswordT.getPassword());
    s3 = nameT.getText();

    if ((s1 != null) && (s2 != null) && (s3 != null)) {
      if ((s1.length() != 0) && (s2.length() != 0) && (s3.length() != 0)) {
        if (s1.equals(s2)) {
          // Ok to enable the button
          OKButton.setEnabled(true);
        }
      }
    } else {
      OKButton.setEnabled(false);
    }
  }

}