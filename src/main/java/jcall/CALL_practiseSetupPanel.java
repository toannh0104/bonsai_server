///////////////////////////// //////////////////////////////////////
// Practise Panel
// The panel used when practising a lesson
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CALL_practiseSetupPanel extends JPanel {
  // Defines
  static final int LEFT_COLUMN_W = 350;

  // The main panel
  JPanel mainPanel;

  // Sub Panels
  JPanel formPanel;
  JPanel vocabPanel;
  JPanel practicePanel;
  JPanel questionPanel;

  // Borders (for sub panels)
  Border loweredetched;
  TitledBorder formPanelBorder;
  TitledBorder practicePanelBorder;
  TitledBorder vocabPanelBorder;
  TitledBorder questionPanelBorder;

  Image background;
  CALL_main parent;

  // Title
  JLabel titleL;

  // Buttons
  JButton backButton;
  JButton okButton;

  // JLabels
  JLabel practiceVerbL;
  JLabel practiceVocabL;
  JLabel practiceContextL;

  // JRadio Buttons
  JRadioButton practiceVerbButton;
  JRadioButton practiceContextButton;
  JRadioButton practiceVocabularyButton;

  // Check boxes
  JCheckBox level3vocab;
  JCheckBox level4vocab;

  // JRadio Button Groups
  ButtonGroup practiceGroup;

  // Static definitions
  static final int verbLeftMargin = 25;
  static final int verbTopMargin = -15;
  static final int radioButtonW = 90;

  // Lesson information
  int index;

  /* Used for component placing */
  Insets insets;

  // The lesson being practiced
  CALL_lessonStruct lesson;

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_practiseSetupPanel(CALL_main p, int lessonIndex) {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
    parent = p;

    lesson = p.db.lessons.getLesson(lessonIndex);
    index = lessonIndex;

    // Set the default settings (maybe save with user profile or something)
    if (parent.practiceConfig == null) {
      // Shouldn't happen, but we do need that config
      parent.practiceConfig = new CALL_practiceSetupStruct();
    }

    drawComponents();
  }

  // For drawing all the components
  // ///////////////////////////////////////////////////////////
  public void drawComponents() {
    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");
    insets = mainPanel.getInsets();

    // Buttons
    backButton = new JButton("Back");
    backButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    backButton.setActionCommand("back");
    backButton.setToolTipText("Go back to lesson menu");
    backButton.setBounds(insets.left + 25, insets.top + 535, 85, 40);
    backButton.addActionListener(parent);
    mainPanel.add(backButton);

    /* Temp Border */
    loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    if (lesson != null) {
      /* Practice type panel */
      practicePanel = new JPanel();
      practicePanel.setLayout(null);
      practicePanel.setOpaque(false);
      practicePanel.setBounds(insets.left + 25, insets.top + 100, LEFT_COLUMN_W, 250);
      practicePanelBorder = BorderFactory.createTitledBorder(loweredetched, "Practice Type");
      practicePanelBorder.setTitleJustification(TitledBorder.RIGHT);
      practicePanel.setBorder(practicePanelBorder);

      /* Vocabulary Level panel */
      vocabPanel = new JPanel();
      vocabPanel.setLayout(null);
      vocabPanel.setOpaque(false);
      vocabPanel.setBounds(insets.left + 25, insets.top + 355, LEFT_COLUMN_W, 145);
      vocabPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Vocabulary Level");
      vocabPanelBorder.setTitleJustification(TitledBorder.RIGHT);
      vocabPanel.setBorder(vocabPanelBorder);

      /* Form Settings panel */
      formPanel = new JPanel();
      formPanel.setLayout(null);
      formPanel.setOpaque(false);
      formPanel.setBounds(insets.left + 35 + LEFT_COLUMN_W, insets.top + 100, 725 - LEFT_COLUMN_W, 400);
      formPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Form Restrictions");
      formPanelBorder.setTitleJustification(TitledBorder.RIGHT);
      formPanel.setBorder(formPanelBorder);

      /* Title */
      titleL = new JLabel("Lesson " + lesson.index + " - Practise Setup");
      titleL.setForeground(Color.BLACK);
      titleL.setBounds(insets.left + 25, insets.top, 475, 80);
      titleL.setFont(new Font("serif", Font.BOLD, 32));

      okButton = new JButton("Ok");
      okButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      okButton.setActionCommand("ok");
      okButton.setToolTipText("Proceed with practice");
      okButton.setBounds(insets.left + 675, insets.top + 535, 85, 40);
      okButton.addActionListener(parent);

      // Check Boxes
      // ============================

      level3vocab = new JCheckBox("Use Level 3 Vocabulary");
      level3vocab.setBounds(insets.left + 25, 45, 200, 20);
      vocabPanel.add(level3vocab);
      if (lesson.levels[2]) {
        level3vocab.setSelected(true);
      }

      level4vocab = new JCheckBox("Use Level 4 Vocabulary");
      level4vocab.setBounds(insets.left + 25, 95, 200, 20);
      vocabPanel.add(level4vocab);
      if (lesson.levels[3]) {
        level4vocab.setSelected(true);
      }

      // Radio Buttons
      // =============================

      // Complexity Group
      practiceContextButton = new JRadioButton("Sentences");
      practiceContextButton.setBounds(verbLeftMargin, 35, radioButtonW, 20);
      practiceContextButton.setBackground(Color.WHITE);
      practiceContextButton.addActionListener(parent);
      practiceContextButton.setOpaque(false);
      practicePanel.add(practiceContextButton);

      if (parent.practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_CONTEXT) {
        practiceContextButton.setSelected(true);
      }
      practiceContextL = new JLabel("Practice constructing complete sentences");
      practiceContextL.setBounds(verbLeftMargin + 5, 50, 300, 20);
      practiceContextL.setFont(new Font("serif", Font.PLAIN, 14));
      practicePanel.add(practiceContextL);

      practiceVocabularyButton = new JRadioButton("Vocabulary");
      practiceVocabularyButton.setBounds(verbLeftMargin, 95, radioButtonW, 20);
      practiceVocabularyButton.setBackground(Color.WHITE);
      practiceVocabularyButton.addActionListener(parent);
      practiceVocabularyButton.setOpaque(false);
      practicePanel.add(practiceVocabularyButton);

      if (parent.practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_VOCAB) {
        practiceVocabularyButton.setSelected(true);
      }
      practiceVocabL = new JLabel("Practice only the vocabulary used in this lesson");
      practiceVocabL.setBounds(verbLeftMargin + 5, 110, 300, 20);
      practiceVocabL.setFont(new Font("serif", Font.PLAIN, 14));
      practicePanel.add(practiceVocabL);

      practiceVerbButton = new JRadioButton("Verb Form");
      practiceVerbButton.setBounds(verbLeftMargin, 155, radioButtonW, 20);
      practiceVerbButton.setBackground(Color.WHITE);
      practiceVerbButton.addActionListener(parent);
      practiceVerbButton.setOpaque(false);
      practicePanel.add(practiceVerbButton);

      if (parent.practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_VERB) {
        practiceVerbButton.setSelected(true);
      }
      practiceVerbL = new JLabel("Practice only the verb forms used in this lesson");
      practiceVerbL.setBounds(verbLeftMargin + 5, 170, 300, 20);
      practiceVerbL.setFont(new Font("serif", Font.PLAIN, 14));
      practicePanel.add(practiceVerbL);

      practiceGroup = new ButtonGroup();
      practiceGroup.add(practiceContextButton);
      practiceGroup.add(practiceVocabularyButton);
      practiceGroup.add(practiceVerbButton);

      /* Add components to main panel */
      mainPanel.add(titleL);
      mainPanel.add(okButton);

      mainPanel.add(formPanel);
      mainPanel.add(practicePanel);
      mainPanel.add(vocabPanel);
    }

    /* Finally add main panel */
    add(mainPanel, BorderLayout.CENTER);

    /* And redraw */
    repaint();
    validate();
  }

  // Function that updates the underlying config struct from the current button
  // settings
  // /////////////////////////////////////////////////////////////////////////
  public void updateSettings() {

    if (parent.practiceConfig != null) {
      // Practice Type Settings
      if (practiceVerbButton.isSelected()) {
        parent.practiceConfig.practiceType = CALL_practiceSetupStruct.PTYPE_VERB;
      } else if (practiceVocabularyButton.isSelected()) {
        parent.practiceConfig.practiceType = CALL_practiceSetupStruct.PTYPE_VOCAB;
      } else {
        parent.practiceConfig.practiceType = CALL_practiceSetupStruct.PTYPE_CONTEXT;
      }
    }
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