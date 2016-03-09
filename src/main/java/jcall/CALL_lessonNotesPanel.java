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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//2011.08.05 T.Tajima add for xml + xsl

public class CALL_lessonNotesPanel extends JPanel {
  // Main background panel
  JPanel mainPanel;
  Image background;

  // Title
  JLabel titleL;

  // Buttons
  JButton backButton;

  // Configuration settings
  CALL_configDataStruct config;

  // The text
  // 2011.08.30 T.Tajima Change
  // JEditorPane notesPane;
  JCALL_lessonInfoPane notesPane;
  JScrollPane scrollPane;
  int lessonIndex;

  CALL_main parent;

  /* Used for component placing */
  Insets insets;

  // Static definitions

  public CALL_lessonNotesPanel(CALL_main p, int index) {
    FlowLayout buttonLayout;
    String typeStr;
    String url;

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
    lessonIndex = index + 1;
    parent = p;

    // Get lesson specific configuration
    if (parent.config.settings[index + 1] == null) {
      config = parent.config.settings[0];
    } else {
      config = parent.config.settings[index + 1];
    }

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");
    insets = mainPanel.getInsets();

    /* Title */
    titleL = new JLabel("Lesson " + lessonIndex + " - Grammar Notes");
    titleL.setForeground(Color.BLACK);
    titleL.setBounds(insets.left + 25, insets.top, 525, 80);
    titleL.setFont(new Font("serif", Font.BOLD, 32));

    // Buttons
    backButton = new JButton("Back");
    backButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    backButton.setActionCommand("back");
    backButton.setBounds(insets.left + 25, insets.top + 535, 85, 40);
    backButton.addActionListener(parent);

    /* Load the notes */
    // 2011.08.30 T.Tajima Change
    notesPane = new JCALL_lessonInfoPane(config, lessonIndex);
    notesPane.setNotes();
    /*
     * notesPane = new JEditorPane(); notesPane.setEditable(false);
     * 
     * // What type of notes if(config.outputStyle == CALL_io.romaji) { typeStr
     * = new String("R"); } else if(config.outputStyle == CALL_io.kana) {
     * typeStr = new String("K"); } else { typeStr = new String("J"); }
     * 
     * java.net.URL notesURL =
     * CALL_lessonNotesPanel.class.getResource("../Data/html/" + lessonIndex +
     * "/notes-" + typeStr + ".html"); if (notesURL != null) { try {
     * notesPane.setPage(notesURL); } catch (Exception e) {
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
     * "Attempted to read a bad URL: " + notesURL); } } else {
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
     * "Couldn't find file: Lesson" + lessonIndex + ".html");
     * 
     * notesURL = CALL_lessonPanel.class.getResource("../Data/html/sorry.html");
     * if (notesURL != null) { try { notesPane.setPage(notesURL); } catch
     * (Exception e) { CALL_debug.printlog(CALL_debug.MOD_GENERAL,
     * CALL_debug.ERROR, "Attempted to read a bad URL: " + notesURL); } }
     * 
     * }
     */

    /* set some properties of the panel */
    notesPane.setEditable(false);

    /* Create the scroll pane */
    scrollPane = new JScrollPane(notesPane);
    scrollPane.setBounds(insets.left + 25, insets.top + 75, 750, 400);

    /* Finally add main panels */
    mainPanel.add(titleL);
    mainPanel.add(backButton);
    mainPanel.add(scrollPane);

    /* Finally add button panels */
    add(mainPanel, BorderLayout.CENTER);
  }

  // Paint component
  // ////////////////////////////////////////////////////
  public void paintComponent(Graphics g) {
    super.paintComponent(g); // paint background

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    // Draw image at its natural size first.
    g.drawImage(background, 0, 0, this);
  }
}