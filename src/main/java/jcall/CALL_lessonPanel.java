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

public class CALL_lessonPanel extends JPanel {
  // The main panel
  JPanel mainPanel;

  Image background;

  // Title
  JLabel titleL;

  // Index of first lesson displayed on page.
  int lessonIndex;

  // Configuration settings
  CALL_configDataStruct config; // Lesson Specific
  CALL_configDataStruct gconfig; // Generic config

  // Lesson Overview
  // 2011.08.30 T.Tajima Change
  // JEditorPane overviewPane;
  JCALL_lessonInfoPane overviewPane;
  JScrollPane scrollPane;

  // Buttons
  JButton lessonNotes;
  JButton lessonExample;
  JButton lessonPractise;
  JButton lessonTest;

  // return to main menu
  JButton backButton;

  /*
   * add by wang
   */
  JButton oralPracticButton;

  // Static definitions

  public CALL_lessonPanel(CALL_main parent, int index) {
    CALL_lessonStruct lesson;
    FlowLayout buttonLayout;
    String typeStr;
    String url;
    int i;

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Get config
    if (parent.config.settings[index + 1] == null) {
      config = parent.config.settings[0];
    } else {
      config = parent.config.settings[index + 1];
    }

    // Global config
    gconfig = parent.config.settings[0];

    // Get a handle on the lesson information required
    lesson = parent.db.lessons.getLesson(index);
    if (lesson != null) {
      lessonIndex = lesson.index;
      // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
      // "Creating new lesson panel, lesson index [" + lessonIndex + "]");
    }

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    /* Title */
    titleL = new JLabel("Lesson " + lessonIndex + " - Overview");
    titleL.setForeground(Color.BLACK);
    titleL.setBounds(insets.left + 25, insets.top, 325, 80);
    titleL.setFont(new Font("serif", Font.BOLD, 32));

    /* Overview Panel */
    // 2011.08.30 T.Tajima Change
    overviewPane = new JCALL_lessonInfoPane(gconfig, lessonIndex);
    overviewPane.setOverview();
    /*
     * overviewPane.setContentType("text/html");
     * 
     * overviewPane.setEditable(false);
     * 
     * // What type of notes if(gconfig.outputStyle == CALL_io.romaji) { typeStr
     * = new String("R"); } else if(gconfig.outputStyle == CALL_io.kana) {
     * typeStr = new String("K"); } else { typeStr = new String("J"); }
     * 
     * java.net.URL overviewURL =
     * CALL_lessonNotesPanel.class.getResource("../Data/html/" + lessonIndex +
     * "/overview-" + typeStr + ".html"); if (overviewURL != null) { try {
     * overviewPane.setPage(overviewURL); } catch (Exception e) {
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
     * "Attempted to read a bad URL: " + overviewURL); } } else { overviewURL =
     * CALL_lessonPanel.class.getResource("../Data/html/sorry.html"); if
     * (overviewURL != null) { try { overviewPane.setPage(overviewURL); } catch
     * (Exception e) { CALL_debug.printlog(CALL_debug.MOD_GENERAL,
     * CALL_debug.ERROR, "Attempted to read a bad URL: " + overviewURL); } }
     * 
     * CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
     * "Couldn't find file: [Lesson" + lessonIndex + "overview.html]"); }
     */

    /* Create the scroll pane */
    scrollPane = new JScrollPane(overviewPane);
    scrollPane.setBounds(insets.left + 25, insets.top + 75, 750, 400);

    // Buttons
    backButton = new JButton("Back");
    backButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    backButton.setActionCommand("back");
    backButton.setBounds(insets.left + 25, insets.top + 535, 85, 40);
    backButton.addActionListener(parent);

    lessonNotes = new JButton("Notes");
    lessonNotes.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    lessonNotes.setActionCommand("notes");
    lessonNotes.setBounds(insets.left + 430, insets.top + 535, 85, 40);
    lessonNotes.addActionListener(parent);

    // Do the lesson notes exist?
    if (!lesson.hasNotes(gconfig.outputStyle)) {
      lessonNotes.setEnabled(false);
    }

    lessonExample = new JButton("Example");
    lessonExample.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    lessonExample.setActionCommand("example");
    lessonExample.setBounds(insets.left + 515, insets.top + 535, 85, 40);
    lessonExample.addActionListener(parent);

    if (lesson.examples.size() > 0) {
      // We have some examples
      lessonExample.setEnabled(true);
    } else {
      // No examples for this lesson
      lessonExample.setEnabled(false);
    }

    lessonPractise = new JButton("Typing");
    lessonPractise.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    lessonPractise.setActionCommand("practise");
    lessonPractise.setBounds(insets.left + 600, insets.top + 535, 85, 40);
    lessonPractise.addActionListener(parent);

    /*
     * add by wang
     */
    oralPracticButton = new JButton("Speech");
    oralPracticButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    oralPracticButton.setActionCommand("oralpractise");
    oralPracticButton.setBounds(insets.left + 685, insets.top + 535, 85, 40);
    oralPracticButton.addActionListener(parent);

    /*
     * 
     * lessonTest = new JButton("Test"); lessonTest.setMargin(new
     * Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h,
     * CALL_main.butt_margin_v, CALL_main.butt_margin_h));
     * 
     * lessonTest.setActionCommand("test"); lessonTest.setBounds(insets.left +
     * 685, insets.top + 535, 85, 40); lessonTest.addActionListener(parent);
     * lessonTest.setEnabled(false); // TEMP - CJW
     */

    /* Add components to main panel */
    mainPanel.add(titleL);
    mainPanel.add(scrollPane);
    mainPanel.add(lessonNotes);
    mainPanel.add(lessonExample);
    mainPanel.add(lessonPractise);
    // mainPanel.add(lessonTest);
    // ------------------------------------------------add by wang------
    mainPanel.add(oralPracticButton);

    mainPanel.add(backButton);
    /* Finally add main panels */
    add(mainPanel, BorderLayout.CENTER);

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); // paint background

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    // Draw image at its natural size first.
    g.drawImage(background, 0, 0, this);
  }
}