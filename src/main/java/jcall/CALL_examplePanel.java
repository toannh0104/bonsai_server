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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CALL_examplePanel extends JPanel implements ActionListener {
  // Defines
  static final int DIAGRAM_W = 395;
  static final int DIAGRAM_H = 320;
  static final int DIAGRAM_X = 27;
  static final int DIAGRAM_Y = 111;

  // The main panel
  JPanel mainPanel;
  JPanel answerPanel;
  JPanel formPanel;
  JPanel gfxHelpPanel;
  JPanel formHelpPanel;

  CALL_imagePanel diagramPanel;

  Image background;
  Image gfxImage;

  CALL_main parent;

  // Configuration settings
  CALL_configDataStruct config;
  CALL_configDataStruct gconfig;

  // And the database
  CALL_database db;

  // The current lesson and example
  CALL_lessonStruct lesson;
  CALL_lessonExampleStruct currentExample;
  int exampleIndex;
  int lessonIndex;

  // Buttons
  JButton prevButton;
  JButton nextButton;
  JButton exitButton;

  // JLabels
  JLabel titleL;
  JLabel answerL;
  JLabel englishL;
  JLabel englishTL;
  JLabel answerTL;
  JLabel questionTypeL;

  // Text areas
  JTextArea formHelp;
  JTextArea gfxHelp;
  JTextArea diagHelp;

  // Scroll Pane
  JScrollPane formHelpPane;

  Vector formGroupL; // A vector of labels
  Vector formL; // A vector of vector of labels

  /* Used for component placing */
  Insets insets;

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_examplePanel(CALL_main p, int lesIndex) {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
    parent = p;

    // Get the lesson
    db = p.db;
    lesson = db.lessons.getLesson(lesIndex);
    lessonIndex = lesson.index;

    // Get config
    if (parent.config.settings[lessonIndex + 1] == null) {
      config = parent.config.settings[0];
    } else {
      config = parent.config.settings[lessonIndex + 1];
    }

    // Global config
    gconfig = parent.config.settings[0];

    // Get the first example
    exampleIndex = 0;
    getExample();

  }

  // For drawing all the components
  // ///////////////////////////////////////////////////////////
  public void drawComponents() {
    String tempString;
    JLabel tempLabel;
    CALL_stringPairsStruct pairs;
    CALL_stringPairStruct pair;

    // Main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/example.jpg");
    insets = mainPanel.getInsets();

    if (currentExample != null) {
      // Title
      titleL = new JLabel("Lesson: " + (lessonIndex) + ", Example: " + (exampleIndex + 1) + " of "
          + (lesson.examples.size()));
      titleL.setFont(new Font("serif", Font.BOLD, 20));
      titleL.setBounds(20, 5, 700, 25);
      mainPanel.add(titleL);

      // Diagram panel
      if (currentExample.imageStr != null) {
        diagramPanel = new CALL_imagePanel(DIAGRAM_W, DIAGRAM_H, currentExample.imageStr);
        diagramPanel.setBounds(DIAGRAM_X, DIAGRAM_Y, DIAGRAM_W, DIAGRAM_H);
        mainPanel.add(diagramPanel);
      }

      // Question Type
      if (currentExample.questionType != null) {
        questionTypeL = new JLabel(currentExample.questionType);
        questionTypeL.setFont(new Font("serif", Font.BOLD, 20));
        questionTypeL.setForeground(Color.WHITE);
        questionTypeL.setHorizontalAlignment(JLabel.CENTER);
        questionTypeL.setBounds(150, 532, 450, 40);
        mainPanel.add(questionTypeL);
      }

      // Gfx help
      gfxHelp = new JTextArea(8, 40);
      gfxHelp.setEditable(false);
      gfxHelp.setLineWrap(true);
      gfxHelp.setWrapStyleWord(true);
      gfxHelp.setOpaque(false);
      gfxHelp.append(currentExample.gfxHelp);
      gfxHelp.setBounds(20, 40, 765, 70);
      gfxHelp.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
      mainPanel.add(gfxHelp);

      // Form help
      formHelp = new JTextArea(8, 12);
      formHelp.setEditable(false);
      formHelp.setLineWrap(true);
      formHelp.setWrapStyleWord(true);
      formHelp.setOpaque(false);
      formHelp.append(currentExample.formHelp);
      formHelp.setBounds(438, 280, 160, 150);
      formHelp.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
      mainPanel.add(formHelp);

      // Diagram Help
      diagHelp = new JTextArea(8, 12);
      diagHelp.setEditable(false);
      diagHelp.setLineWrap(true);
      diagHelp.setWrapStyleWord(true);
      diagHelp.setOpaque(false);
      diagHelp.append(currentExample.diagHelp);
      diagHelp.setBounds(438, 112, 160, 150);
      diagHelp.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
      mainPanel.add(diagHelp);

      // Form
      int yval = 140;

      for (int j = 0; j < currentExample.forms.size(); j++) {
        // Get the form group information
        pairs = (CALL_stringPairsStruct) currentExample.forms.elementAt(j);

        if (pairs != null) {
          tempLabel = new JLabel(j + 1 + ")");
          tempLabel.setBounds(625, yval, 75, 16);
          mainPanel.add(tempLabel);

          for (int i = 0; i < pairs.parameters.size(); i++) {
            pair = (CALL_stringPairStruct) pairs.parameters.elementAt(i);
            if (pair != null) {
              // We have a form string
              if (pair.parameter != null) {
                tempLabel = new JLabel(pair.parameter);
                tempLabel.setBounds(650, yval, 250, 18);
                yval += 18;

                if (pair.value.equals("on")) {
                  // Form in use
                  tempLabel.setFont(new Font("serif", Font.BOLD, 14));
                  tempLabel.setForeground(Color.BLACK);
                } else {
                  // Disabled form
                  tempLabel.setFont(new Font("serif", Font.PLAIN, 14));
                  tempLabel.setForeground(Color.GRAY);
                }

                mainPanel.add(tempLabel);
              }
            }
          }

          // For spacing between groups
          yval += 10;
        }
      }

      // Answer and text
      englishTL = new JLabel("English: ");
      englishTL.setBounds(DIAGRAM_X, 435, 100, 30);
      englishTL.setFont(new Font("serif", Font.BOLD, 20));
      englishTL.setForeground(Color.BLACK);
      englishTL.setHorizontalAlignment(JLabel.LEFT);
      mainPanel.add(englishTL);

      englishL = new JLabel(currentExample.english);
      englishL.setBounds(DIAGRAM_X + 120, 435, 700, 30);
      englishL.setFont(new Font("serif", Font.PLAIN, 20));
      englishL.setForeground(Color.BLACK);
      englishL.setHorizontalAlignment(JLabel.LEFT);
      mainPanel.add(englishL);

      answerTL = new JLabel("Japanese: ");
      answerTL.setBounds(DIAGRAM_X, 470, 100, 30);
      answerTL.setFont(new Font("serif", Font.BOLD, 20));
      answerTL.setForeground(Color.BLACK);
      answerTL.setHorizontalAlignment(JLabel.LEFT);
      mainPanel.add(answerTL);

      // Text depends on display config
      if (gconfig != null) {
        if (gconfig.outputStyle == CALL_io.romaji) {
          tempString = CALL_io.strKanaToRomaji(currentExample.answerJ);
        } else if (gconfig.outputStyle == CALL_io.kana) {
          tempString = currentExample.answerJ;
        } else {
          tempString = currentExample.answerK;
        }
      } else {
        // No config?
        tempString = currentExample.answerJ;
      }

      answerL = new JLabel(tempString);
      answerL.setBounds(DIAGRAM_X + 120, 470, 700, 30);
      answerL.setFont(new Font("serif", Font.PLAIN, 20));
      answerL.setForeground(Color.BLACK);
      answerL.setHorizontalAlignment(JLabel.LEFT);
      mainPanel.add(answerL);

      // Buttons
      nextButton = new JButton(">");
      nextButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      nextButton.setActionCommand("next");
      nextButton.setToolTipText("View Next Example");
      nextButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
      nextButton.addActionListener(this);
      mainPanel.add(nextButton);

      prevButton = new JButton("<");
      prevButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      prevButton.setActionCommand("prev");
      prevButton.setToolTipText("View Previous Example");
      prevButton.setBounds(insets.left + 645, insets.top + 535, 65, 40);
      prevButton.addActionListener(this);
      mainPanel.add(prevButton);

      exitButton = new JButton("Back");
      exitButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      exitButton.setActionCommand("back");
      exitButton.setToolTipText("Back");
      exitButton.setBounds(insets.left + 10, insets.top + 535, 65, 40);
      exitButton.addActionListener(parent);
      mainPanel.add(exitButton);

      add(mainPanel);
    }
  }

  public void getExample() {
    if ((exampleIndex >= 0) && (exampleIndex < lesson.examples.size())) {
      currentExample = (CALL_lessonExampleStruct) lesson.examples.elementAt(exampleIndex);
      if (currentExample != null) {
        // Redraw the components etc
        removeAll();
        drawComponents();
        validate();
      }
    }
  }

  public void prevExample() {
    exampleIndex--;
    if (exampleIndex < 0) {
      exampleIndex = lesson.examples.size() - 1;
    }

    getExample();
  }

  public void nextExample() {
    exampleIndex++;
    if (exampleIndex >= lesson.examples.size()) {
      exampleIndex = 0;
    }

    getExample();
  }

  // Act on events
  // ////////////////////////////////////////////////////
  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    if (command.equals("next")) {
      nextExample();
    } else if (command.equals("prev")) {
      prevExample();
    }
  }

  // Paint component
  // ////////////////////////////////////////////////////
  public void paintComponent(Graphics g) {
    Image recordLightImage;
    int x, y, w, h;

    super.paintComponent(g);

    // Draw the background
    g.drawImage(background, 0, 0, this);
  }
}