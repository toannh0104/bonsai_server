///////////////////////////////////////////////////////////////////
// Lesson Panel
// The main menu for a particular lesson
// Contains options for Test, Practise, Notes, Example, Back.
// Should also contain an overview of the lesson.
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class CALL_htmlDialog extends JDialog implements ActionListener {
  // Main background panel
  CALL_imagePanel mainPanel;

  // Title
  JLabel titleL;

  // Buttons
  JButton okButton;

  // The text
  JEditorPane notesPane;
  JScrollPane scrollPane;

  // Diaglog dimensions
  static final int width = 800;
  static final int height = 600;

  /* Used for component placing */
  Insets insets;
  CALL_main parent;

  public CALL_htmlDialog(CALL_main p, String titleString, String htmlString) {
    FlowLayout buttonLayout;
    String url;

    setSize(width, height);
    setTitle("Help");

    setLayout(new BorderLayout());
    parent = p;

    setResizable(false);

    /* Main panel */
    mainPanel = new CALL_imagePanel(width, height, "Screens/white2.jpg", true);
    insets = mainPanel.getInsets();

    /* Title */
    titleL = new JLabel(titleString);
    titleL.setForeground(Color.WHITE);
    titleL.setBounds(insets.left + 5, insets.top + 7, 370, 32);
    titleL.setFont(new Font("serif", Font.BOLD, 24));

    // Buttons
    okButton = new JButton("OK");
    okButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    okButton.setActionCommand("ok");
    okButton.setBounds(insets.left + (width / 2) - 35, insets.top + 525, 80, 40);
    okButton.addActionListener(this);

    /* Load the notes */
    notesPane = new JEditorPane();
    notesPane.setContentType("text/html");
    notesPane.setEditable(false);

    URL notesURL = CALL_titlePanel.class.getResource(htmlString);

    if (notesURL != null) {
      try {
        notesPane.setPage(notesURL);
      } catch (Exception e) {
        // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
        // "Attempted to read a bad URL: " + notesURL);
      }
    } else {
      // CALL_debug.println(CALL_debug.ERROR, htmlString);
    }

    /* set some properties of the panel */
    notesPane.setEditable(false);

    /* Create the scroll pane */
    scrollPane = new JScrollPane(notesPane);
    scrollPane.setBounds(insets.left + 0, insets.top + 50, width - 5, height - 150);

    /* Finally add main panels */
    mainPanel.add(titleL);
    mainPanel.add(okButton);
    mainPanel.add(scrollPane);

    /* Finally add button panels */
    add(mainPanel, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    if (command.compareTo("ok") == 0) {
      // Close dialog
      setVisible(false);
    }
  }
}