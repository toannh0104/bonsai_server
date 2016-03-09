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
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CALL_studentSelectDialog extends JDialog implements DocumentListener {
  // Panels
  JPanel mainPanel;
  JPanel textPanel;
  JPanel buttonPanel;

  // Labels
  JLabel nameL;
  JLabel passwordL;

  // Text fields
  JTextField nameT;
  JPasswordField passwordT;

  // Buttons
  JButton OKButton;
  JButton exitButton;
  JButton newButton;

  // Configuration
  CALL_configDataStruct gconfig;

  // Misc
  int textentered;

  public CALL_studentSelectDialog(CALL_main parent) {
    Insets insets;
    int width = 400;
    int height = 150;

    setSize(width, height);
    setTitle("Please Login");
    setResizable(false);
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setLayout(null);
    insets = mainPanel.getInsets();

    textPanel = new JPanel();
    textPanel.setLayout(null);
    textPanel.setBounds(insets.left + 5, insets.top + 5, 390, 120);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(null);
    buttonPanel.setBounds(insets.left + 5, insets.top + 85, 390, 50);

    // Save config
    if (parent != null) {
      gconfig = parent.config.settings[0];
    } else {
      gconfig = null;
    }

    // Add labels
    nameL = new JLabel("Name:");
    nameL.setBounds(15, 10, 100, 20);
    textPanel.add(nameL);

    passwordL = new JLabel("Password:");
    passwordL.setBounds(15, 45, 100, 20);
    textPanel.add(passwordL);

    // Add text areas
    if ((gconfig != null) && (gconfig.lastStudent != null)) {
      nameT = new JTextField(gconfig.lastStudent);
    } else {
      nameT = new JTextField("");
    }

    nameT.setBounds(120, 10, 200, 20);
    nameT.getDocument().addDocumentListener(this);
    textPanel.add(nameT);

    passwordT = new JPasswordField("");
    passwordT.setBounds(120, 45, 200, 20);
    passwordT.getDocument().addDocumentListener(this);
    textPanel.add(passwordT);

    // Add buttons
    newButton = new JButton("New");
    newButton.setBounds(5, 5, 100, 30);
    newButton.setActionCommand("student-new");
    newButton.addActionListener(parent);
    newButton.setEnabled(true);
    buttonPanel.add(newButton);

    OKButton = new JButton("Ok");
    OKButton.setBounds(310, 5, 75, 30);
    OKButton.setActionCommand("student-ok");
    OKButton.addActionListener(parent);
    // !!! for debug !!!
    // passwordT.setText("css");
    // OKButton.setEnabled(true);
    OKButton.setEnabled(false);
    buttonPanel.add(OKButton);

    // Set the ok button to respond to Enter key
    JRootPane rootPane = getRootPane();
    rootPane.setDefaultButton(OKButton);

    exitButton = new JButton("Exit");
    exitButton.setBounds(235, 5, 75, 30);
    exitButton.setActionCommand("student-exit");
    exitButton.addActionListener(parent);
    buttonPanel.add(exitButton);

    // Add panels
    mainPanel.add(buttonPanel);
    mainPanel.add(textPanel);

    getContentPane().add(mainPanel);

    textentered = 0;

    // Exit program if this is closed
    // setDefaultCloseOperation(EXIT_ON_CLOSE);
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
  }

  public String getName() {
    return nameT.getText();
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
    textentered++;
    OKButton.setEnabled(true);
  }

  public void removeUpdate(DocumentEvent e) {
    textentered--;
    if (textentered <= 0) {
      textentered = 0;
      OKButton.setEnabled(false);
    }
  }

}