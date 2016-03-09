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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

//2011.08.05 T.Tajima add for xml + xsl

public class CALL_lessonNotesDialog extends JDialog implements ActionListener {
  // Main background panel
  CALL_imagePanel mainPanel;

  // Title
  JLabel titleL;

  // Buttons
  JButton okButton;

  // Configuration settings
  CALL_configDataStruct config;
  CALL_configDataStruct gconfig;

  // The text
  // 2011.08.30 T.Tajima Change
  // JEditorPane notesPane;
  JCALL_lessonInfoPane notesPane;
  JScrollPane scrollPane;
  int lessonIndex;

  // Diaglog dimensions
  static final int width = 700;
  static final int height = 600;

  /* Used for component placing */
  Insets insets;
  CALL_main parent;

  public CALL_lessonNotesDialog(CALL_main p, int index) {
    FlowLayout buttonLayout;
    String typeStr;
    String url;

    setSize(width, height);
    setTitle("Notes");

    setLayout(new BorderLayout());
    lessonIndex = index + 1;
    parent = p;

    // Get config
    if (parent.config.settings[index + 1] == null) {
      config = parent.config.settings[0];
    } else {
      config = parent.config.settings[index + 1];
    }

    // Global config
    gconfig = parent.config.settings[0];

    /* Main panel */
    mainPanel = new CALL_imagePanel(width, height, "Screens/white2.jpg", true);
    insets = mainPanel.getInsets();

    /* Title */
    titleL = new JLabel("Lesson " + lessonIndex + " - Grammar Notes");
    titleL.setForeground(Color.WHITE);
    titleL.setBounds(insets.left + 175, insets.top + 7, 370, 32);
    titleL.setFont(new Font("serif", Font.BOLD, 24));

    // Buttons
    okButton = new JButton("OK");
    okButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    okButton.setActionCommand("ok");
    okButton.setBounds(insets.left + (width / 2) - 40, insets.top + (height - 70), 80, 40);
    okButton.addActionListener(this);

    /* Load the notes */
    // 2011.08.30 T.Tajima Change
    notesPane = new JCALL_lessonInfoPane(gconfig, lessonIndex);
    notesPane.setNotes();
    /*
     * notesPane.setContentType("text/html"); notesPane.setEditable(false);
     * 
     * // What type of notes if(gconfig.outputStyle == CALL_io.romaji) { typeStr
     * = new String("R"); } else if(gconfig.outputStyle == CALL_io.kana) {
     * typeStr = new String("K"); } else { typeStr = new String("J"); }
     * 
     * java.net.URL notesURL =
     * CALL_lessonNotesPanel.class.getResource("../Data/html/" + lessonIndex +
     * "/notes-" + typeStr + ".html"); if (notesURL != null) { try {
     * notesPane.setPage(notesURL); } catch (Exception e) {
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
     * "Attempted to read a bad URL: " + notesURL); } } else {
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
     * "Couldn't find file: Lesson" + lessonIndex + ".html");
     * 
     * notesURL = CALL_lessonPanel.class.getResource("../Data/html/sorry.html");
     * if (notesURL != null) { try { notesPane.setPage(notesURL); } catch
     * (Exception e) { CALL_debug.printlog(CALL_debug.MOD_GENERAL,
     * CALL_debug.ERROR, "Attempted to read a bad URL: " + notesURL); } } }
     */

    /* set some properties of the panel */
    notesPane.setEditable(false);

    /* Create the scroll pane */
    scrollPane = new JScrollPane(notesPane);
    scrollPane.setBounds(insets.left + 0, insets.top + 50, width - 10, height - 150);

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