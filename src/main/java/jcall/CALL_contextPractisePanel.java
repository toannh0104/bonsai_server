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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;

import jcall.recognition.util.CharacterUtil;

import org.apache.log4j.Logger;

public class CALL_contextPractisePanel extends CALL_practisePanel {
  static Logger logger = Logger.getLogger(CALL_contextPractisePanel.class.getName());
  // Defines
  static final int TYPE_CURRENT = 0; // Stats Type: Current session only
  static final int TYPE_ALL = 1; // Stats Type: All sessions

  static final int ANSWER_LABEL_W = 125;
  static final int ANSWER_FONT_SIZE = 16;
  static final int KANA_WIDTH = 16;
  static final int ROMAJI_WIDTH = 10;
  static final int SPACE_WIDTH = 16;

  static final int HINT_PANEL_X = 160;
  static final int SCORE_SUMMARY_X = 175;
  static final int FACE_X = 385;
  static final int FACE_Y = 525;
  static final int FACE_S = 60;

  static int DIAGRAM_X = 25;
  static int DIAGRAM_Y = 60;
  static int DIAGRAM_W = 400;
  static int DIAGRAM_H = 325;

  static final int MAX_BUTT_WIDTH = 675;

  // The panels (already have mainPanel and diagramPanel of course)
  JPanel hintPanel;
  JPanel formPanel;
  JPanel scorePanel;
  JPanel statsPanel;

  // Borders
  Border loweredetched;
  TitledBorder hintPanelBorder;
  TitledBorder formPanelBorder;
  TitledBorder scorePanelBorder;
  TitledBorder statsPanelBorder;

  // Lesson statistics
  int statsType; // 0: Current, 1: Total

  // Lesson times
  GregorianCalendar startTime;
  GregorianCalendar currentTime;

  // Hint level, , etc
  int numComponents;
  int nextRecommendation;
  int score;
  int maxscore;
  int happiness;

  // Buttons
  JButton notesButton;
  JButton hintCategoryButton;
  JButton fixedHintButton;
  JButton allHintsButton;
  JButton statsTypeButton;

  // For errors and feedback
  JButton feedbackButtons[];
  JButton altWordButtons[];
  CALL_errorPairStruct errors[];
  int usedWidth[];

  // JLabels
  JLabel nextHintL;
  JLabel scoreD;
  JLabel scoreP;

  JLabel answerTL;
  JLabel your_answerTL;
  JLabel feedbackL;

  JLabel your_answerL[];
  JLabel answerL[];

  JLabel youScored;
  JLabel thisQuestionScore;

  // Stats panel 1 - Current Session Stats
  JPanel statsPanel1;
  JLabel modeL1;
  JLabel qNumL1;
  JLabel qNumD1;
  JLabel answeredD1;
  JLabel answeredL1;
  JLabel answeredP1;
  JLabel totScoreL1;
  JLabel totScoreP1;
  JLabel timeL1;
  JLabel timeD1;

  // Stats panel 2 - All Session Stats
  JPanel statsPanel2;
  JLabel modeL2;
  JLabel qNumL2;
  JLabel qNumD2;
  JLabel answeredD2;
  JLabel answeredL2;
  JLabel answeredP2;
  JLabel totScoreL2;
  JLabel totScoreP2;
  JLabel timeL2;
  JLabel timeD2;

  // Text fields for answers
  JTextField answerField[];

  Vector givenAnswer; // The set of strings representing a student's answer
  Vector matchingTemplate; // Closest matching template - a vector of vectors of
                           // sentenceWords
  Vector wordTable; // A vector of vectors (Strings). Stores all the possible
                    // fillers for each slot (from the template)

  int matchingWordIndexes[]; // The index within each sub-vector above of the
                             // currently displayed word (starts with best
                             // matching)

  // Store how much time was in the lesson history originally...
  long originalTime;

  Vector formGroupL; // A vector of labels
  Vector formL; // A vector of vector of labels

  // For the progressive hints
  JButton hintButton[];
  JLabel hintD[];

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_contextPractisePanel(CALL_main p, int lessonIndex) {
    super(p, lessonIndex);

    // Get the time spent in context practice for the lesson so far by the
    // student
    originalTime = history.practiceTimeSpent;

    // Reset the lesson statistics
    statsType = 0;
    answered = 0;
    startTime = new GregorianCalendar();
    currentTime = new GregorianCalendar();

    // Update the history
    history.practiceRuns++;

    // Create the arrays of hint buttons / labels
    hintD = new JLabel[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    hintButton = new JButton[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    answerField = new JTextField[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    feedbackButtons = new JButton[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    altWordButtons = new JButton[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    usedWidth = new int[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];

    // And those used in results processing
    matchingWordIndexes = new int[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    your_answerL = new JLabel[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];
    answerL = new JLabel[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];

    // Get the next question
    nextQuestion();

    // Graphical settings
    drawComponents();
  }

  // For drawing all the components
  // ///////////////////////////////////////////////////////////
  public void drawComponents() {
    CALL_sentenceHintsStruct sentenceHints;
    CALL_wordHintsStruct wordHints;
    CALL_hintStruct hint;

    CALL_stringPairsStruct pairs;
    CALL_stringPairStruct pair;
    Integer tempInt;

    int i, j, cost, cwidth, compIndex, nextLevel;
    int currentLevel;
    int size;
    JLabel tempLabel;
    Vector tempVector;

    String labelString;

    Date d1, d2;
    long elapsedTime;

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white4.jpg");
    insets = mainPanel.getInsets();

    /* Do quit button first - if lesson is null, this is all we'll do */
    quitButton = new JButton("Quit");
    quitButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    quitButton.setActionCommand("quit");
    quitButton.setToolTipText("Quit practising");
    quitButton.setBounds(insets.left + 10, insets.top + 535, 65, 40);
    quitButton.addActionListener(parent);
    mainPanel.add(quitButton);

    if ((lesson != null) && (currentQuestion != null)) {
      /* Title */
      titleL = new JLabel("Lesson " + lesson.index + " - Practice");
      titleL.setForeground(Color.BLACK);
      titleL.setBounds(insets.left + 25, insets.top, 325, 50);
      titleL.setFont(new Font("serif", Font.BOLD, 32));

      /* Hint Panel */
      hintPanel = new JPanel();
      hintPanel.setLayout(null);
      hintPanel.setOpaque(false);
      hintPanel.setBounds(insets.left + 25, insets.top + 390, 750, 120);
      hintPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Answer");
      hintPanelBorder.setTitleJustification(TitledBorder.LEFT);
      hintPanel.setBorder(hintPanelBorder);

      /* Form Panel */
      formPanel = new JPanel();
      formPanel.setLayout(null);
      formPanel.setOpaque(false);
      formPanel.setBounds(insets.left + 445, insets.top + 60, 165, 325);
      formPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Grammar Form");
      formPanelBorder.setTitleJustification(TitledBorder.LEFT);
      formPanel.setBorder(formPanelBorder);

      /* Score Panel */
      scorePanel = new JPanel();
      scorePanel.setLayout(null);
      scorePanel.setOpaque(false);
      scorePanel.setBounds(insets.left + 620, insets.top + 60, 155, 105);
      scorePanelBorder = BorderFactory.createTitledBorder(loweredetched, "Available Points");
      scorePanelBorder.setTitleJustification(TitledBorder.LEFT);
      scorePanel.setBorder(scorePanelBorder);

      /* Stats Panel */
      statsPanel = new JPanel();
      statsPanel.setLayout(null);
      statsPanel.setOpaque(false);
      statsPanel.setBounds(insets.left + 620, insets.top + 185, 155, 200);
      statsPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Lesson Record");
      statsPanelBorder.setTitleJustification(TitledBorder.LEFT);
      statsPanel.setBorder(statsPanelBorder);

      // Draw hint components:
      // First, how many components do we have? Base button width on this
      // information
      sentenceHints = currentQuestion.hints;
      if (sentenceHints.wordHints != null) {
        if (currentQuestion.hints != null) {
          numComponents = sentenceHints.wordHints.size();
          if (numComponents <= 0) {
            // The case of no components being listed
            // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
            // "No components listed");
            return;
          }

          if (numComponents >= CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS) {
            // We have limited size arrays to consider (for the buttons)
            numComponents = CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS - 1;
          }

          cwidth = (MAX_BUTT_WIDTH / numComponents) - 1;
          if (cwidth > MAX_BUTT_WIDTH)
            cwidth = MAX_BUTT_WIDTH;

          // Now, create and draw the hint buttons, and matching answer fields
          for (i = 0; i < numComponents; i++) {
            wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(i);
            if (wordHints != null) {
              hint = wordHints.getCurrentHint();
              cost = wordHints.getNextCost();

              if (hint != null) {
                hintD[i] = new JLabel(wordHints.getCurrentHintString(gconfig.outputStyle));
                if (gconfig.outputStyle == CALL_io.kanji) {
                  hintD[i].setToolTipText(wordHints.getCurrentHintString(CALL_io.kana));
                } else {
                  hintD[i].setToolTipText(wordHints.getCurrentHintString(gconfig.outputStyle));
                }
              } else {
                // No hints revealed, show ?????
                hintD[i] = new JLabel(uknHintStr);
                hintD[i].setToolTipText(uknHintStr);
              }

              hintD[i].setBounds(insets.left + 15 + (i * (cwidth + 1)), insets.top + 12, cwidth, 20);
              hintD[i].setFont(new Font("serif", Font.ITALIC, 14));
              hintD[i].setForeground(Color.GRAY);
              hintD[i].setHorizontalAlignment(JLabel.CENTER);

              // Add now, because it's easier to do so at this point
              hintPanel.add(hintD[i]);

              // As for the buttons, depends on whether we're using fixed or
              // flexible hints.
              // The following is just for when we allow hint choice. The fixed
              // hint button is done later.
              if (config.hintChoice) {
                if (i == nextRecommendation) {
                  // The recommended hint
                  hintButton[i] = new JButton("Hint*");
                } else {
                  // Alternative hint
                  hintButton[i] = new JButton("Hint");
                }

                hintButton[i].setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h,
                    CALL_main.butt_margin_v, CALL_main.butt_margin_h));

                hintButton[i].setActionCommand("hint" + i);

                if (config.hintCostEnabled) {
                  // Only have scoring information if scoring is enabled
                  if (wordHints.lastHintNext()) {
                    hintButton[i].setToolTipText("The next hint will cost you " + cost
                        + " points. Note: FINAL hint for this word!");
                  } else {
                    hintButton[i].setToolTipText("The next hint will cost you " + cost + " points!");
                  }
                }
                hintButton[i].setBounds(insets.left + 15 + (i * (cwidth + 1)), insets.top + 35, cwidth, 30);
                hintButton[i].addActionListener(this);

                // Add now, because it's easier to do so at this point
                hintPanel.add(hintButton[i]);

                // And add the answer fields too
                answerField[i] = new JTextField("");
                answerField[i].setBounds(insets.left + 15 + (i * (cwidth + 1)), insets.top + 80, cwidth, 30);
                answerField[i].getDocument().addDocumentListener(this);
                answerField[i].setHorizontalAlignment(JTextField.CENTER);
                hintPanel.add(answerField[i]);
              }
            }
          }

          // And the button for submitting the answer
          textAnswerButton = new JButton("OK");
          textAnswerButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h,
              CALL_main.butt_margin_v, CALL_main.butt_margin_h));

          textAnswerButton.setActionCommand("textanswer");
          textAnswerButton.setToolTipText("Submit the entered answer");
          textAnswerButton.setBounds(insets.left + 695, insets.top + 80, 45, 30);
          textAnswerButton.addActionListener(this);
          textAnswerButton.setEnabled(false);
          hintPanel.add(textAnswerButton);

          // Now for the fixed hint button
          // The remaining hints actually go on to the main panel
          fixedHintButton = new JButton("<html>" + "Recommended" + "<br>" + "Hint" + "</html>");
          fixedHintButton.setBounds(insets.left + HINT_PANEL_X, insets.top + 535, 100, 40);
          fixedHintButton.setHorizontalAlignment(SwingConstants.CENTER);
          fixedHintButton.setActionCommand("nextHint");
          fixedHintButton.addActionListener(this);

          if ((nextRecommendation >= 0) && (nextRecommendation < sentenceHints.wordHints.size())) {

            wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(nextRecommendation);
            if (wordHints != null) {
              if (wordHints.areThereMoreHints()) {
                if (config.hintCostEnabled) {
                  // Only have scoring information if scoring is enabled

                  cost = wordHints.getNextCost();
                  if (cost > 0) {
                    fixedHintButton.setToolTipText("The next hint will cost you " + cost + " points!");
                  } else {
                    fixedHintButton.setToolTipText("");
                  }
                }
              } else {
                // No more hints - disable button
                fixedHintButton.setEnabled(false);
              }
            }
          } else {
            // No more hints - disable button
            fixedHintButton.setEnabled(false);
          }

          // Finally, add to the main panel
          mainPanel.add(fixedHintButton);

          // Now the reveal all hints button
          allHintsButton = new JButton("<html>" + "Reveal" + "<br>" + "All Hints" + "</html>");
          allHintsButton.setBounds(insets.left + HINT_PANEL_X + 105, insets.top + 535, 100, 40);
          allHintsButton.setActionCommand("allHints");
          allHintsButton.addActionListener(this);
          mainPanel.add(allHintsButton);

          // Now the Hint Category Button
          nextLevel = sentenceHints.getUnrevealedLevel();
          if ((nextLevel >= 0) && (nextLevel < CALL_wordHintsStruct.MAX_LEVELS)) {
            hintCategoryButton = new JButton("<html>" + "Reveal" + "<br>" + "["
                + sentenceHints.getLevelString(nextLevel) + "]" + "</html>");
            hintCategoryButton.setToolTipText("Revealing will cost " + sentenceHints.getCostToLevel(nextLevel)
                + " points!");
            hintCategoryButton.setEnabled(true);
          } else {
            hintCategoryButton = new JButton("Hints Revealed");
            hintCategoryButton.setToolTipText("No more hints to reveal");
            hintCategoryButton.setEnabled(false);
          }

          hintCategoryButton.setBounds(insets.left + HINT_PANEL_X + 210, insets.top + 535, 100, 40);
          hintCategoryButton.setActionCommand("nextCategory");
          hintCategoryButton.addActionListener(this);
          mainPanel.add(hintCategoryButton);

          // If we're in trial mode (0.2e+), disable the fixed hint buttons
          if (db.currentStudent.type == CALL_studentStruct.trialInt) {
            fixedHintButton.setEnabled(false);
            hintCategoryButton.setEnabled(false);
            allHintsButton.setEnabled(false);
          }
        }
      }

      // The score area
      // -------------
      scoreD = new JLabel("" + score);
      scoreD.setBounds(insets.left + 15, insets.top + 15, 225, 40);
      scoreD.setFont(new Font("serif", Font.PLAIN, 32));
      scoreD.setForeground(Color.BLACK);
      scorePanel.add(scoreD);

      scoreP = new JLabel("(" + ((score * 100) / maxscore) + "%)");
      scoreP.setBounds(insets.left + 15, insets.top + 55, 225, 40);
      scoreP.setFont(new Font("serif", Font.ITALIC, 32));
      scoreP.setForeground(Color.GRAY);
      scorePanel.add(scoreP);

      // The stats area (155x200)
      // ---------------------

      // Panel 1 - Current Session
      // -------------------------
      statsPanel1 = new JPanel();
      statsPanel1.setLayout(null);
      statsPanel1.setOpaque(false);
      statsPanel1.setBounds(0, 0, 155, 150);
      statsPanel.add(statsPanel1);

      modeL1 = new JLabel("Current Session Only");
      modeL1.setBounds(insets.left + 10, insets.top + 30, 300, 15);
      modeL1.setFont(new Font("serif", Font.ITALIC + Font.BOLD, 12));
      modeL1.setForeground(Color.BLACK);
      statsPanel1.add(modeL1);

      qNumL1 = new JLabel("Questions:");
      qNumL1.setBounds(insets.left + 10, insets.top + 55, 70, 15);
      qNumL1.setFont(new Font("serif", Font.BOLD, 12));
      qNumL1.setForeground(Color.BLACK);
      statsPanel1.add(qNumL1);

      qNumD1 = new JLabel("" + questionNum);
      qNumD1.setBounds(insets.left + 70, insets.top + 55, 50, 15);
      qNumD1.setFont(new Font("serif", Font.PLAIN, 12));
      qNumD1.setForeground(Color.BLACK);
      statsPanel1.add(qNumD1);

      answeredL1 = new JLabel("Answered:");
      answeredL1.setBounds(insets.left + 10, insets.top + 80, 70, 15);
      answeredL1.setFont(new Font("serif", Font.BOLD, 12));
      answeredL1.setForeground(Color.BLACK);
      statsPanel1.add(answeredL1);

      if (questionNum > 0) {
        answeredP1 = new JLabel("" + (answered * 100) / questionNum + "%");
      } else {
        answeredP1 = new JLabel("0%");
      }
      answeredP1.setBounds(insets.left + 70, insets.top + 80, 50, 15);
      answeredP1.setFont(new Font("serif", Font.PLAIN, 12));
      answeredP1.setForeground(Color.BLACK);
      statsPanel1.add(answeredP1);

      answeredD1 = new JLabel("(" + answered + ")");
      answeredD1.setBounds(insets.left + 100, insets.top + 80, 50, 15);
      answeredD1.setFont(new Font("serif", Font.ITALIC, 12));
      answeredD1.setForeground(Color.GRAY);
      statsPanel1.add(answeredD1);

      totScoreL1 = new JLabel("Score:");
      totScoreL1.setBounds(insets.left + 10, insets.top + 105, 70, 15);
      totScoreL1.setFont(new Font("serif", Font.BOLD, 12));
      totScoreL1.setForeground(Color.BLACK);
      statsPanel1.add(totScoreL1);

      if (maxTotalScore > 0) {
        totScoreP1 = new JLabel("" + (totalScore * 100) / maxTotalScore + "%");
      } else {
        totScoreP1 = new JLabel("0%");
      }
      totScoreP1.setBounds(insets.left + 70, insets.top + 105, 50, 15);
      totScoreP1.setFont(new Font("serif", Font.PLAIN, 12));
      totScoreP1.setForeground(Color.BLACK);
      statsPanel1.add(totScoreP1);

      timeL1 = new JLabel("Time:");
      timeL1.setBounds(insets.left + 10, insets.top + 130, 70, 15);
      timeL1.setFont(new Font("serif", Font.BOLD, 12));
      timeL1.setForeground(Color.BLACK);
      statsPanel1.add(timeL1);

      d1 = currentTime.getTime();
      d2 = startTime.getTime();
      elapsedTime = (d1.getTime() - d2.getTime()) / 1000;

      timeD1 = new JLabel(CALL_time.getTimeString((long) elapsedTime));
      timeD1.setBounds(insets.left + 70, insets.top + 130, 75, 15);
      timeD1.setFont(new Font("serif", Font.PLAIN, 12));
      timeD1.setForeground(Color.BLACK);
      statsPanel1.add(timeD1);

      // Panel 2 - All Sessions
      // --------------------
      statsPanel2 = new JPanel();
      statsPanel2.setLayout(null);
      statsPanel2.setOpaque(false);
      statsPanel2.setBounds(0, 0, 155, 150);
      statsPanel.add(statsPanel2);

      if (statsType == TYPE_CURRENT) {
        statsPanel2.setVisible(true);
      } else {
        statsPanel2.setVisible(false);
      }

      modeL2 = new JLabel("All Sessions (" + history.practiceRuns + " runs)");
      modeL2.setBounds(insets.left + 10, insets.top + 30, 350, 15);
      modeL2.setFont(new Font("serif", Font.ITALIC + Font.BOLD, 12));
      modeL2.setForeground(Color.BLACK);
      statsPanel2.add(modeL2);

      qNumL2 = new JLabel("Questions:");
      qNumL2.setBounds(insets.left + 10, insets.top + 55, 70, 15);
      qNumL2.setFont(new Font("serif", Font.BOLD, 12));
      qNumL2.setForeground(Color.BLACK);
      statsPanel2.add(qNumL2);

      qNumD2 = new JLabel("" + history.practiceQuestions);
      qNumD2.setBounds(insets.left + 70, insets.top + 55, 50, 15);
      qNumD2.setFont(new Font("serif", Font.PLAIN, 12));
      qNumD2.setForeground(Color.BLACK);
      statsPanel2.add(qNumD2);

      answeredL2 = new JLabel("Answered:");
      answeredL2.setBounds(insets.left + 10, insets.top + 80, 70, 15);
      answeredL2.setFont(new Font("serif", Font.BOLD, 12));
      answeredL2.setForeground(Color.BLACK);
      statsPanel2.add(answeredL2);

      if (history.practiceQuestions > 0) {
        answeredP2 = new JLabel("" + (history.practiceAnswered * 100) / history.practiceQuestions + "%");
      } else {
        answeredP2 = new JLabel("0%");
      }
      answeredP2.setBounds(insets.left + 70, insets.top + 80, 50, 15);
      answeredP2.setFont(new Font("serif", Font.PLAIN, 12));
      answeredP2.setForeground(Color.BLACK);
      statsPanel2.add(answeredP2);

      answeredD2 = new JLabel("(" + history.practiceAnswered + ")");
      answeredD2.setBounds(insets.left + 100, insets.top + 80, 50, 15);
      answeredD2.setFont(new Font("serif", Font.ITALIC, 12));
      answeredD2.setForeground(Color.GRAY);
      statsPanel2.add(answeredD2);

      totScoreL2 = new JLabel("Score:");
      totScoreL2.setBounds(insets.left + 10, insets.top + 105, 70, 15);
      totScoreL2.setFont(new Font("serif", Font.BOLD, 12));
      totScoreL2.setForeground(Color.BLACK);
      statsPanel2.add(totScoreL2);

      if (history.practiceTotalMaxScore > 0) {
        totScoreP2 = new JLabel("" + (history.practiceTotalScore * 100) / history.practiceTotalMaxScore + "%");
      } else {
        totScoreP2 = new JLabel("0%");
      }
      totScoreP2.setBounds(insets.left + 70, insets.top + 105, 50, 15);
      totScoreP2.setFont(new Font("serif", Font.PLAIN, 12));
      totScoreP2.setForeground(Color.BLACK);
      statsPanel2.add(totScoreP2);

      timeL2 = new JLabel("Time:");
      timeL2.setBounds(insets.left + 10, insets.top + 130, 70, 15);
      timeL2.setFont(new Font("serif", Font.BOLD, 12));
      timeL2.setForeground(Color.BLACK);
      statsPanel2.add(timeL2);

      d1 = currentTime.getTime();
      d2 = startTime.getTime();
      elapsedTime = ((d1.getTime() - d2.getTime()) / 1000);
      history.practiceTimeSpent = elapsedTime + originalTime;

      timeD2 = new JLabel(CALL_time.getTimeString((long) history.practiceTimeSpent));
      timeD2.setBounds(insets.left + 70, insets.top + 130, 75, 15);
      timeD2.setFont(new Font("serif", Font.PLAIN, 12));
      timeD2.setForeground(Color.BLACK);
      statsPanel2.add(timeD2);

      // Generic stats (shown on underlying panel
      statsTypeButton = new JButton("Switch Mode");
      statsTypeButton.setBounds(insets.left + 5, insets.top + 165, 145, 30);
      statsTypeButton.setActionCommand("statsType");
      statsTypeButton.addActionListener(this);
      statsPanel.add(statsTypeButton);

      // Setting which of the sub stats panels is visible initially
      if (statsType == TYPE_CURRENT) {
        statsPanel1.setVisible(true);
        statsPanel2.setVisible(false);
      } else {
        statsPanel1.setVisible(false);
        statsPanel2.setVisible(true);
      }

      /* Some descriptive text to help describe the concept */
      int yval = 25;

      // Initialise vectors
      formGroupL = new Vector();
      formL = new Vector();

      for (j = 0; j < currentQuestion.forms.size(); j++) {
        // Get the form group information
        pairs = (CALL_stringPairsStruct) currentQuestion.forms.elementAt(j);
        if (pairs != null) {
          tempLabel = new JLabel(j + 1 + ")");
          tempLabel.setBounds(insets.left + 10, insets.top + yval, 75, 16);
          formPanel.add(tempLabel);
          formGroupL.addElement(tempLabel);
          tempVector = new Vector();

          for (i = 0; i < pairs.parameters.size(); i++) {
            pair = (CALL_stringPairStruct) pairs.parameters.elementAt(i);
            if (pair != null) {
              // We have a form string
              if (pair.parameter != null) {
                tempLabel = new JLabel(pair.parameter);
                tempLabel.setBounds(insets.left + 35, insets.top + yval, 250, 18);
                yval += 18;

                if (pair.value.equals("true")) {
                  // Form in use
                  tempLabel.setFont(new Font("serif", Font.BOLD, 14));
                  tempLabel.setForeground(Color.BLACK);
                } else {
                  // Disabled form
                  tempLabel.setFont(new Font("serif", Font.PLAIN, 14));
                  tempLabel.setForeground(Color.GRAY);
                }

                formPanel.add(tempLabel);
                tempVector.addElement(tempLabel);
              }
            }
          }

          // For spacing between groups
          yval += 10;
        }
      }

      // Recreate the diagram
      diagramPanel = new CALL_diagramPanel(currentQuestion, db, DIAGRAM_W, DIAGRAM_H, gconfig.diagMarkersEnabled);
      diagramPanel.setMarkers(gconfig.diagMarkersEnabled);
      diagramPanel.setBounds(insets.left + DIAGRAM_X, insets.top + DIAGRAM_Y, diagramPanel.width, diagramPanel.height);
      diagramPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));

      // Lower Button area
      // The notes button
      notesButton = new JButton("Notes");
      notesButton.setActionCommand("notes");
      notesButton.setToolTipText("Review the lesson notes (practise only)");
      notesButton.setBounds(insets.left + 645, insets.top + 535, 65, 40);
      notesButton.addActionListener(parent); // message sent to parent for
                                             // processing

      // Do the lesson notes exist?
      if (!lesson.hasNotes(gconfig.outputStyle)) {
        notesButton.setEnabled(false);
      }

      // If we're on the last question (trial mode), change skip button to
      // Finish
      if ((gconfig.numQuestionsPractice != -1) && (questionNum >= (gconfig.numQuestionsPractice - 1))) {
        // Add the finish button
        finishButton = new JButton("Finish");
        finishButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
            CALL_main.butt_margin_h));

        finishButton.setActionCommand("finish");
        finishButton.setToolTipText("Ends the Practice Session");
        finishButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
        finishButton.addActionListener(parent);
        mainPanel.add(finishButton);
      } else {
        // It's the Skip button we want
        skipButton = new JButton("Skip");
        skipButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
            CALL_main.butt_margin_h));

        skipButton.setActionCommand("skip");
        skipButton.setToolTipText("Skip this problem");
        skipButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
        skipButton.addActionListener(this);
        mainPanel.add(skipButton);
      }

      // Recording on/off button
      recordButton = new JButton("REC");
      recordButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      recordButton.setActionCommand("record");
      recordButton.setToolTipText("Stop/Start recording");
      recordButton.setBounds(insets.left + 540, insets.top + 535, 50, 40);
      recordButton.addActionListener(this);

      /* Add components to main panel */
      mainPanel.add(titleL);
      mainPanel.add(hintPanel);
      mainPanel.add(formPanel);
      mainPanel.add(scorePanel);
      mainPanel.add(statsPanel);
      mainPanel.add(notesButton);
      mainPanel.add(recordButton);
      mainPanel.add(diagramPanel);
    }

    /* Finally add button panels */
    add(mainPanel, BorderLayout.CENTER);

    /* And redraw */
    repaint();
    validate();
    diagram_initialised = true;
  }

  public void nextQuestion() {
    CALL_sentenceHintsStruct hints;
    CALL_wordStruct word;
    Vector wordList;

    currentQuestion = new CALL_questionStruct(db, config, gconfig, lesson, CALL_questionStruct.QTYPE_CONTEXT);

    CALL_sentenceStruct sentenceStruct = currentQuestion.sentence;
    if (sentenceStruct != null) {
      // logger.debug("in nextQuestion, print sentence info");
      sentenceStruct.print_debug();
    }
    // Update the word experience
    wordList = currentQuestion.sentence.getWords();

    if (wordList != null) {
      for (int i = 0; i < wordList.size(); i++) {
        word = (CALL_wordStruct) wordList.elementAt(i);
        if (word != null) {
          // Increment the ammount of times this word has been seen (in the Long
          // Term, MT, and ST lists)
          db.currentStudent.wordExperienceLT.incrementWordExperience(word.type, word.id);
          db.currentStudent.wordExperienceMT.incrementWordExperience(word.type, word.id);
          db.currentStudent.wordExperienceST.incrementWordExperience(word.type, word.id);
        }
      }
    }
    resetValues();

    // Update time information
    currentTime = new GregorianCalendar();

    // Now redraw the screen
    if (diagram_initialised) {
      remove(mainPanel);
      mainPanel.removeAll();
      drawComponents();
    }
  }

  public void resetValues() {
    CALL_sentenceStruct sentence;
    int x, y, starty;

    super.resetValues();

    // Reset the error list
    errors = new CALL_errorPairStruct[CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS];

    // Get the correct answer
    sentence = currentQuestion.sentence;
    if (sentence != null) {
      // Get the model answers
      modelAnswers = sentence.getAllSentenceStrings(gconfig.outputStyle, false);
      modelAnswersExtra = sentence.getAllSentenceStrings(gconfig.outputStyle, true);
      displayedAnswerIndex = 0;
    }

    // What is the next recommended hint
    nextRecommendation = currentQuestion.hints.recommendNextHint();

    score = currentQuestion.getMaxScore();
    maxscore = score;
    happiness = -1;

    // Reset the best matching answer template - generated again once an answer
    // is submitted
    matchingTemplate = null;
    wordTable = new Vector();

    // Default to the text answer button
    // Maybe not such a good idea - CJW
    // rootPane.setDefaultButton(textAnswerButton);

    // Log which lesson we're on
    // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
    // "============NEXT QUESTION================");
    // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW, "Lesson " +
    // index + ", Question " + (questionNum + 1));
    // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
    // "Question Type: " + currentQuestion.lessonQuestion.question);
  }

  // Act on events
  // ////////////////////////////////////////////////////
  public void actionPerformed(ActionEvent e) {
    CALL_sentenceHintsStruct sentenceHints;
    CALL_wordHintsStruct wordHints;
    CALL_sentenceWordStruct targetWord, tempWord;
    CALL_hintStruct prevHint, nextHint;

    String command = new String(e.getActionCommand());
    String correctAnswer;
    String tempString;
    String logString;
    String prevHintString;
    String catString;
    String slotString;
    Integer tempInt;

    String tempWordT;
    String wordT, wordO;
    Vector wordV;
    Vector targetWordV;
    Vector<String> allWordList;
    Vector wordV_kanji, wordV_kana, wordV_romaji;
    String wordT_kanji, wordT_kana, wordT_romaji;
    String tempString_kanji, tempString_kana, tempString_romaji;

    double currentBestMatch;
    double matchScore, matchScore_kanji, matchScore_kana, matchScore_romaji;

    int idealWidth;
    int oWidth;
    int tWidth;
    int tempWidth;
    int cwidth;

    int currentXt;
    int currentXo;

    int errorCost;
    int errIndex;
    int cost;
    int tempI;
    int level;
    int nextLevel, nextCategory;
    int compIndex;

    boolean match;
    boolean firstError;
    boolean wordSearchComplete;

    // DEBUG
    // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
    // "Action -> " + command);

    if ((command.startsWith("hint")) || (command.compareTo("nextHint") == 0)) {
      sentenceHints = currentQuestion.hints;
      if (sentenceHints != null) {
        if (command.compareTo("nextHint") == 0) {
          // Determine the next hint to display (that which may be shown next,
          // with highest score)
          compIndex = nextRecommendation;
        } else {
          // Get component number
          compIndex = (Integer.decode(command.substring(4))).intValue();
        }

        if ((compIndex >= 0) && (compIndex < sentenceHints.wordHints.size())) {

          // Get the appropriate word hints structure
          wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(compIndex);

          if (wordHints != null) {
            // Log hint usage
            catString = wordHints.getCategory();

            prevHint = wordHints.getCurrentHint();
            if (prevHint != null) {
              // Messed up right now I suspect - CJW
              prevHintString = prevHint.getHintString(gconfig.outputStyle);
            } else {
              prevHintString = new String("none");
            }

            // Update hint structure (get next hint)
            nextHint = wordHints.getNextHint();

            if (nextHint != null) {
              nextLevel = wordHints.currentLevel;
              tempString = wordHints.getCurrentHintString(gconfig.outputStyle);

              // Update the score
              score -= wordHints.getCurrentCost();

              // CALL_debug.printlog(CALL_debug.MOD_USAGE,
              // CALL_debug.HINTS,"Score deducted by " +
              // wordHints.getCurrentCost());

              // Do some logging
              if (command.compareTo("nextHint") == 0) {
                logString = new String("Hint Determined (Fixed Order):  Component: " + compIndex + ", Level: "
                    + nextLevel + ", Cat: " + catString + ", Desc: " + tempString);
                // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
                // logString);
              } else {
                logString = new String("Hint Selected (Free Choice)::  Component: " + compIndex + ", Level: "
                    + nextLevel + ", Cat: " + catString + ", Desc: " + tempString);
                // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
                // logString);
              }

              // Update the buttons
              updateHintButtons(compIndex, sentenceHints, command);

              // And the score
              updateScoreDisplay();
            }
          }
        }
      }
    } else if (command.compareTo("allHints") == 0) {
      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
      // "Unveil all hints");
      sentenceHints = currentQuestion.hints;
      if (sentenceHints != null) {
        score -= sentenceHints.getRemainingCost();
        sentenceHints.setGlobalLevel((CALL_wordHintsStruct.MAX_LEVELS - 1), true);

        // Update the hint buttons
        updateHintButtons(-1, sentenceHints, command);

        // And the score
        updateScoreDisplay();
      }
    } else if (command.compareTo("nextCategory") == 0) {
      sentenceHints = currentQuestion.hints;
      if (sentenceHints != null) {
        nextCategory = sentenceHints.getUnrevealedLevel();
        // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
        // "Unveil next category [" + nextCategory + "]");

        if ((nextCategory >= 0) && (nextCategory < CALL_wordHintsStruct.MAX_LEVELS)) {
          cost = sentenceHints.getCostToLevel(nextCategory);
          sentenceHints.setGlobalLevel(nextCategory, true);
          score -= cost;

          // Update the hint buttons
          updateHintButtons(-1, sentenceHints, command);

          // And the score
          updateScoreDisplay();
        }
      }
    } else if (command.compareTo("record") == 0) {
      String filename;

      if (recording) {
        // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
        // "Stop Recording");

        recording = false;
        CALL_io.stopRecording();

        // Reenable action buttons
        if (quitButton != null) {
          quitButton.setEnabled(true);
          quitButton.validate();
        }
        if (skipButton != null) {
          skipButton.setEnabled(true);
          skipButton.validate();
        }
        if (nextButton != null) {
          nextButton.setEnabled(true);
          nextButton.validate();
        }
        if (finishButton != null) {
          finishButton.setEnabled(true);
          finishButton.validate();
        }
      } else {
        // System.out.println("before recording");
        filename = new String("Data/Speech/utterance-ID" + db.currentStudent.id + "-S" + gconfig.sessionNumber + "-P"
            + parent.practiceCount + "-L" + index + "-Q-" + questionNum + ".wav");
        // System.out.println("set  recording file name");
        // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
        // "Start Recording => " + filename);

        recording = true;
        CALL_io.startRecording(filename);

        // System.out.println("start recording");

        // Disable all other action buttons
        if (quitButton != null) {
          quitButton.setEnabled(false);
          quitButton.validate();
        }
        if (skipButton != null) {
          skipButton.setEnabled(false);
          skipButton.validate();
        }
        if (nextButton != null) {
          nextButton.setEnabled(false);
          nextButton.validate();
        }
        if (finishButton != null) {
          finishButton.setEnabled(false);
          finishButton.validate();
        }
      }
      repaint();
    } else if (command.compareTo("statsType") == 0) {
      statsType++;
      if (statsType >= 2) {
        // Loop back around
        statsType = 0;
      }

      switch (statsType) {
        case TYPE_CURRENT:
          statsPanel2.setVisible(false);
          statsPanel2.validate();
          statsPanel1.setVisible(true);
          statsPanel1.validate();
          break;
        case TYPE_ALL:
          statsPanel1.setVisible(false);
          statsPanel1.validate();
          statsPanel2.setVisible(true);
          statsPanel2.validate();
          break;
      }
    } else if (command.compareTo("skip") == 0) {
      // 2011.11.11 T.Tajima add for debug
      /*
       * //if(CALL_main.debug_mode){ //for(int i=0; i<100; i++){ sentenceHints =
       * currentQuestion.hints; if(sentenceHints != null) { score -=
       * sentenceHints.getRemainingCost();
       * sentenceHints.setGlobalLevel((CALL_wordHintsStruct.MAX_LEVELS - 1),
       * true);
       * 
       * // Update the hint buttons updateHintButtons(-1, sentenceHints,
       * command);
       * 
       * // And the score updateScoreDisplay(); }
       * 
       * Dimension sz = Toolkit.getDefaultToolkit().getScreenSize(); try { Robot
       * robot = new Robot(); Image img = robot.createScreenCapture(new
       * Rectangle(CALL_main.frame.getX(), CALL_main.frame.getY(),
       * CALL_main.frame.getWidth(), CALL_main.frame.getHeight()));
       * javax.imageio.ImageIO.write((java.awt.image.RenderedImage)img,"png",new
       * java.io.File(index+"-"+0+".png")); } catch (AWTException ef) { throw
       * new RuntimeException(ef); } catch(java.io.IOException ef) { }
       * maxTotalScore += maxscore; history.practiceTotalMaxScore += maxscore;
       * questionNum++; history.practiceQuestions++;
       * 
       * nextQuestion(); //} //}
       */

      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
      // "Example Skipped");

      // Update lesson stats given that we skipped the answer, and get 0 points
      // for it
      maxTotalScore += maxscore;
      history.practiceTotalMaxScore += maxscore;
      questionNum++;
      history.practiceQuestions++;

      nextQuestion();
    } else if (command.compareTo("next") == 0) {

      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
      // "Next Question");
      nextQuestion();
    } else if (command.compareTo("textanswer") == 0) {
      // Get the given answer
      String ansString = null;

      for (int i = 0; i < numComponents; i++) {
        if (answerField[i] != null) {
          if (ansString == null) {
            ansString = answerField[i].getText();
          } else {
            ansString = ansString + " " + answerField[i].getText();
          }
        }
      }

      // Hide Hints and answer boxes
      for (int i = 0; i < CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS; i++) {
        if (hintD[i] != null)
          hintD[i].setVisible(false);
        if (hintButton[i] != null)
          hintButton[i].setVisible(false);
        if (answerField[i] != null)
          answerField[i].setVisible(false);
      }

      // Hide other hint buttons
      // These will be replaced by the score breakdown
      if (fixedHintButton != null) {
        fixedHintButton.setVisible(false);
      }
      if (allHintsButton != null) {
        allHintsButton.setVisible(false);
      }
      if (hintCategoryButton != null) {
        hintCategoryButton.setVisible(false);
      }

      // Hide answer button (just answered!)
      textAnswerButton.setVisible(false);

      // Get the closest matching template
      // rewrite now
      // Note: This is currently a NAIVE method, not using matching, but just
      // top template
      matchingTemplate = currentQuestion.sentence.getSentenceWordVectors();

      if (matchingTemplate.size() > 0) {

        // Display given and correct answers
        // ------------------------------

        // First the labels
        your_answerTL = new JLabel("Your Answer:");
        your_answerTL.setHorizontalAlignment(JLabel.RIGHT);
        your_answerTL.setBounds(0, 65, ANSWER_LABEL_W, 22);
        your_answerTL.setFont(new Font("serif", Font.BOLD, 16));
        your_answerTL.setForeground(Color.BLACK);
        hintPanel.add(your_answerTL);

        answerTL = new JLabel("Model Answer:");
        answerTL.setHorizontalAlignment(JLabel.RIGHT);
        answerTL.setBounds(0, 40, ANSWER_LABEL_W, 22);
        answerTL.setFont(new Font("serif", Font.BOLD, 16));
        answerTL.setForeground(Color.BLACK);
        hintPanel.add(answerTL);

        // Then the actual data
        // MAX_BUTT_WIDTH = 675;
        cwidth = ((MAX_BUTT_WIDTH - 100) / matchingTemplate.size()) - 1;

        // Where the answers begin printing from
        currentXt = 150;
        currentXo = 150;

        logger.info("Initial cwidth: ((MAX_BUTT_WIDTH - 100) / matchingTemplate.size()) - 1;");
        logger.info("Initial Value; cwidth: " + cwidth + " currentXt: " + currentXt + " currentXo: " + currentXo);
        logger.info("matchingTemplate size: " + matchingTemplate.size());

        firstError = true;
        correctAnswer = null;

        for (int i = 0; i < matchingTemplate.size(); i++) {
          // Work out the maximum width for this component
          cwidth = ((MAX_BUTT_WIDTH - currentXo) / (matchingTemplate.size() - i)) - 1;

          logger.info("cwidth" + "[" + i + "]: " + cwidth
              + " Formula: ((MAX_BUTT_WIDTH - currentXo) / (matchingTemplate.size() - i)) - 1;");

          // Reset some values
          wordT = null;
          wordT_kana = null;
          wordT_kanji = null;
          wordT_romaji = null;
          targetWord = null;

          matchingWordIndexes[i] = -1;
          tWidth = 0;
          oWidth = 0;
          usedWidth[i] = 0;
          wordSearchComplete = false;

          allWordList = new Vector();

          // The word in your answer
          // ---------------------
          if (answerField[i] != null) {
            wordO = answerField[i].getText();
            if (wordO != null && wordO.length() > 0) {
              wordO = CALL_io.strStripSpaces(wordO);
            } else {
              wordO = null;
              logger.info("wordO: null");
              // 2011.09.02 T.Tajima add
              wordO = "";
            }
            // Delete spaces from word

          } else {
            wordO = null;
            logger.info("wordO: null");
            // 2011.09.02 T.Tajima add
            wordO = "";
          }

          // The target word
          // --------------
          targetWordV = (Vector) matchingTemplate.elementAt(i);

          if (targetWordV != null) {

            logger.info("Word Node[" + i + "], size: " + targetWordV.size());

            // Set starting values for matching search
            currentBestMatch = -1;

            for (int j = 0; j < targetWordV.size(); j++) {
              // Go through each potential word for this component in turn
              tempWord = (CALL_sentenceWordStruct) targetWordV.elementAt(j);

              // Find the closest matching word
              if (tempWord != null) {
                // We need to get the target strings in all output formats
                wordV_kanji = tempWord.getWordStrings(CALL_io.kanji);
                wordV_kana = tempWord.getWordStrings(CALL_io.kana);
                wordV_romaji = tempWord.getWordStrings(CALL_io.romaji);
                // All the above should be the same size!

                logger.info("Word[" + i + "][" + j + "], alternative words: " + wordV_kanji.size());

                if ((wordV_kanji != null) && (wordV_kana != null) && (wordV_romaji != null)) {

                  if (wordV_kanji.size() == wordV_kana.size() && wordV_kana.size() == wordV_romaji.size()) {

                    for (int w = 0; w < wordV_kanji.size(); w++) {
                      tempString_kanji = (String) wordV_kanji.elementAt(w);
                      tempString_kana = (String) wordV_kana.elementAt(w);
                      tempString_romaji = (String) wordV_romaji.elementAt(w);

                      // Add appropriate string to our wordTable
                      // The word table is used when looking up alternative
                      // words for display
                      // but is not used for the initially displayed answer,
                      // which may be displayed
                      // in a form that matches the input as opposed to the
                      // selected output format
                      switch (gconfig.outputStyle) {
                        case CALL_io.kanji:
                          allWordList.addElement(tempString_kanji);

                          tempWidth = tempString_kanji.length() * KANA_WIDTH;
                          if (tempWidth > usedWidth[i]) {
                            usedWidth[i] = tempWidth;
                          }
                          break;
                        case CALL_io.kana:
                          allWordList.addElement(tempString_kana);
                          tempWidth = tempString_kana.length() * KANA_WIDTH;

                          break;
                        case CALL_io.romaji:
                          allWordList.addElement(tempString_romaji);
                          tempWidth = tempString_romaji.length() * ROMAJI_WIDTH;
                          if (tempWidth > usedWidth[i]) {
                            usedWidth[i] = tempWidth;
                          }
                          break;

                        default:
                          tempWidth = 0;
                          break;
                      }

                      // If this is the longest width so far seen, save it
                      if (tempWidth > usedWidth[i]) {
                        usedWidth[i] = tempWidth;
                      }

                      // ?????????????????? why has this if?
                      if ((wordO == null) && (w == 0)) {
                        // No observed word to compare, just use top word
                        // And don't bother continuing through the list of all
                        // words
                        wordT = tempWord.getTopWordString(gconfig.outputStyle);
                        wordT_kanji = tempString_kanji;
                        wordT_kana = tempString_kana;
                        wordT_romaji = tempString_romaji;
                      }

                      else if (!wordSearchComplete) {
                        if ((tempString_kanji.equals(wordO)) || (tempString_kana.equals(wordO))
                            || (tempString_romaji.equals(CALL_io.correctRomajiFormat(wordO)))) {
                          // A perfect match
                          // We're not going to break off the search though, as
                          // we want to include all words in our
                          // word table.
                          matchingWordIndexes[i] = (allWordList.size() - 1);
                          currentBestMatch = 0;
                          targetWord = tempWord;
                          wordT_kanji = tempString_kanji;
                          wordT_kana = tempString_kana;
                          wordT_romaji = tempString_romaji;
                          wordSearchComplete = true;

                          logger.info("Found matched word: " + wordT_kana);

                        } else {
                          matchScore_kanji = CALL_distance.getDistance(tempString_kanji, wordO);
                          matchScore_kana = CALL_distance.getDistance(tempString_kana, wordO);
                          matchScore_romaji = CALL_distance.getDistance(tempString_romaji,
                              CALL_io.correctRomajiFormat(wordO));

                          matchScore = Math.min(matchScore_romaji, Math.min(matchScore_kanji, matchScore_kana));

                          if ((currentBestMatch < 0) || (matchScore < currentBestMatch)) {
                            matchingWordIndexes[i] = (allWordList.size() - 1);
                            targetWord = tempWord;
                            wordT_kanji = tempString_kanji;
                            wordT_kana = tempString_kana;
                            wordT_romaji = tempString_romaji;
                            currentBestMatch = matchScore;
                          }
                        }
                      }
                    } // Go through each Kanji (kana etc) entry for this word

                  } else {
                    logger.error("Size is different------wordV_kanji.size(): " + wordV_kanji.size()
                        + " wordV_kana.size(): " + wordV_kana.size() + "]: " + " wordV_romaji.size(): "
                        + wordV_romaji.size());

                  }
                }
              } else {
                logger.error("Word[" + i + "][" + j + "]: null");
              }
            } // Go through each word
          }// end if(targetWordV != null)
          else {
            // There was no target word to be found
            logger.error("There was no target words to be found!!");

            wordT = null;
            wordT_kanji = null;
            wordT_kana = null;
            wordT_romaji = null;
            // targetWord = null //should add this
          }

          // Now display the words
          if (wordO != null) {
            match = false;

            if ((wordT_kanji != null) && (wordT_kana != null) && (wordT_romaji != null)) {
              // Try and match the word
              if (wordT_kanji.equals(wordO)) {
                oWidth = wordO.length() * KANA_WIDTH;
                tWidth = wordT_kanji.length() * KANA_WIDTH;
                wordT = wordT_kanji;
                match = true;
              } else if (wordT_kana.equals(wordO)) {
                oWidth = wordO.length() * KANA_WIDTH;
                tWidth = wordT_kana.length() * KANA_WIDTH;
                wordT = wordT_kana;
                match = true;
              } else if (wordT_romaji.equals(CALL_io.correctRomajiFormat(wordO))) {
                oWidth = wordO.length() * ROMAJI_WIDTH;
                tWidth = wordT_romaji.length() * ROMAJI_WIDTH;
                wordT = wordT_romaji;
                match = true;
              } else {
                logger.info("No match with the target word");
                // use the type of the observed word type
                int intType = CharacterUtil.checkWordClass(wordO);
                if (intType == CharacterUtil.TYPE_KANJI) {
                  wordT = wordT_kanji;
                  tWidth = (wordT_kanji.length()) * KANA_WIDTH;
                  oWidth = (wordO.length()) * KANA_WIDTH;

                } else if (intType == CharacterUtil.TYPE_HIRAGANA || intType == CharacterUtil.TYPE_KATAKANA) {

                  wordT = wordT_kana;
                  tWidth = (wordT_kana.length()) * KANA_WIDTH;
                  oWidth = (wordO.length()) * KANA_WIDTH;

                } else if (intType == CharacterUtil.TYPE_ROMAJI) {

                  wordT = wordT_romaji;
                  tWidth = (wordT_romaji.length()) * ROMAJI_WIDTH;
                  oWidth = (wordO.length()) * ROMAJI_WIDTH;

                } else {// others, use kana

                  wordT = wordT_kana;
                  tWidth = (wordT_kana.length()) * KANA_WIDTH;
                  oWidth = (wordO.length()) * KANA_WIDTH;

                }

              }

              // Set the width of the word component
              idealWidth = Math.max(Math.max(usedWidth[i], oWidth), tWidth);
              usedWidth[i] = Math.min(idealWidth + SPACE_WIDTH, cwidth);
            } // end if((wordT_kanji != null) && (wordT_kana != null) &&
              // (wordT_romaji != null))
            // }//I added to test
            else {
              logger.error("No given answer, just use selected output format width");

              // No given answer, just use selected output format width
              switch (gconfig.outputStyle) {
                case CALL_io.kanji:
                  idealWidth = (wordO.length()) * KANA_WIDTH;
                  break;
                case CALL_io.kana:
                  idealWidth = (wordO.length()) * KANA_WIDTH;
                  break;
                case CALL_io.romaji:
                  idealWidth = (wordO.length()) * ROMAJI_WIDTH;
                  break;
                default:
                  idealWidth = 0;
              }

              usedWidth[i] = Math.min(idealWidth + SPACE_WIDTH, cwidth);
            }

            // Print the given word
            // -----------------

            your_answerL[i] = new JLabel(wordO);
            your_answerL[i].setToolTipText(wordO);

            logger.info("Print the given word: " + wordO + "; Format: you_answerL[i] = new JLabel(wordO); ");

            // Check if it matches to determine the colour
            // --------------------------------------
            // if((wordT != null) || (!match) || wordO==null )
            if ((!match) || wordT == null) {
              // =====================================
              // WE ARE SAYING THIS IS AN ERROR
              // =====================================

              logger.info("Error; WordO: " + wordO + " wordT: " + wordT);

              // Create the error item
              errors[i] = new CALL_errorPairStruct(db, wordT, wordO, targetWord);
              errors[i].logError();

              // Deduct the relevant points (all remaining score on the hint)
              sentenceHints = currentQuestion.hints;
              errorCost = (int) ((double) errors[i].getErrorCost() * currentQuestion.hints.costCoefficient);
              score -= errorCost;

              // To stop the score going into the negative
              if (score < 0)
                score = 0;

              // Update the score
              updateScoreDisplay();

              // Update the word experience
              if ((targetWord != null) && (targetWord.word != null)) {
                db.currentStudent.wordExperienceLT.incrementWordIssues(targetWord.word.type, targetWord.word.id);
                db.currentStudent.wordExperienceMT.incrementWordIssues(targetWord.word.type, targetWord.word.id);
                db.currentStudent.wordExperienceST.incrementWordIssues(targetWord.word.type, targetWord.word.id);
              }

              your_answerL[i].setForeground(Color.RED);

              // Add a feedback button
              feedbackButtons[i] = new JButton("?");
              feedbackButtons[i].setBounds(currentXo + ((usedWidth[i] - SPACE_WIDTH) / 2) - 11, 90, 22, 22);
              feedbackButtons[i].setVisible(true);
              feedbackButtons[i].setToolTipText("Click here for more information about this error");
              feedbackButtons[i].setActionCommand("feedback" + i);
              feedbackButtons[i].addActionListener(this);
              hintPanel.add(feedbackButtons[i]);

              // If first error, added the error feedback label
              if (firstError) {
                firstError = false;
                feedbackL = new JLabel("Feedback:");
                feedbackL.setHorizontalAlignment(JLabel.RIGHT);
                feedbackL.setBounds(0, 90, ANSWER_LABEL_W, 22);
                feedbackL.setFont(new Font("serif", Font.BOLD, 16));
                feedbackL.setForeground(Color.BLACK);
                hintPanel.add(feedbackL);
              }

              repaint();
            } else {
              // Use the matched answer as guide for width as opposed to the
              // longest possible answer
              usedWidth[i] = Math.min(cwidth, (Math.max(tWidth, oWidth) + SPACE_WIDTH));
              your_answerL[i].setForeground(Color.BLACK);
            }

            your_answerL[i].setBounds(currentXo, 65, usedWidth[i], 22);
            your_answerL[i].setFont(new Font("serif", Font.PLAIN, ANSWER_FONT_SIZE));
            hintPanel.add(your_answerL[i]);
            currentXo += usedWidth[i];
          }// end if(wordO!=null)
          else {
            logger.info("observerd Word is null");

            // Just use set format for wordT
            switch (gconfig.outputStyle) {
              case CALL_io.kanji:
                wordT = wordT_kanji;
                usedWidth[i] = (wordT.length() + 1) * KANA_WIDTH;
                break;
              case CALL_io.kana:
                wordT = wordT_kana;
                usedWidth[i] = (wordT.length() + 1) * KANA_WIDTH;
                break;
              case CALL_io.romaji:
                wordT = wordT_romaji;
                usedWidth[i] = (wordT.length() + 1) * ROMAJI_WIDTH;
                break;
              default:
                usedWidth[i] = 0;
                wordT = null;
            }

            // 2011.03.28 T.Tajima add
            currentXo += usedWidth[i];

            // Create the error item
            // errors[i] = new CALL_errorPairStruct(db, wordT, wordO,
            // targetWord);
            // errors[i].logError();
            //
            // // Deduct the relevant points (all remaining score on the hint)
            // sentenceHints = currentQuestion.hints;
            // errorCost = (int) ((double) errors[i].getErrorCost() *
            // currentQuestion.hints.costCoefficient);
            // score -= errorCost;
            //
            // // To stop the score going into the negative
            // if(score < 0) score = 0;
            //
            // // Update the score
            // updateScoreDisplay();
            //
            // // Update the word experience
            // if((targetWord != null) && (targetWord.word != null))
            // {
            // db.currentStudent.wordExperienceLT.incrementWordIssues(targetWord.word.type,
            // targetWord.word.id);
            // db.currentStudent.wordExperienceMT.incrementWordIssues(targetWord.word.type,
            // targetWord.word.id);
            // db.currentStudent.wordExperienceST.incrementWordIssues(targetWord.word.type,
            // targetWord.word.id);
            // }
            //
            // your_answerL[i].setForeground(Color.RED);
            //
            // // Add a feedback button
            // feedbackButtons[i] = new JButton("?");
            // feedbackButtons[i].setBounds(currentXo + ((usedWidth[i] -
            // SPACE_WIDTH) / 2) - 11, 90, 22, 22);
            // feedbackButtons[i].setVisible(true);
            // feedbackButtons[i].setToolTipText("Click here for more information about this error");
            // feedbackButtons[i].setActionCommand("feedback" + i);
            // feedbackButtons[i].addActionListener(this);
            // hintPanel.add(feedbackButtons[i]);
            //
            // // If first error, added the error feedback label
            // if(firstError)
            // {
            // firstError = false;
            // feedbackL = new JLabel("Feedback:");
            // feedbackL.setHorizontalAlignment(JLabel.RIGHT);
            // feedbackL.setBounds(0, 90, ANSWER_LABEL_W, 22);
            // feedbackL.setFont(new Font("serif", Font.BOLD, 16));
            // feedbackL.setForeground(Color.BLACK);
            // hintPanel.add(feedbackL);
            // }
            //
            // repaint();
            //
            //
            // your_answerL[i].setBounds(currentXo, 65, usedWidth[i], 22);
            // your_answerL[i].setFont(new Font("serif", Font.PLAIN,
            // ANSWER_FONT_SIZE));
            // hintPanel.add(your_answerL[i]);
            // currentXo += usedWidth[i];

          }

          if (wordT != null) {
            // For logging, later
            if (correctAnswer == null) {
              correctAnswer = wordT;
            } else {
              correctAnswer = correctAnswer + " " + wordT;
            }

            // Add the correct answer
            answerL[i] = new JLabel(wordT);
            answerL[i].setBounds(currentXt, 40, usedWidth[i], 22);
            answerL[i].setToolTipText(wordT);
            answerL[i].setFont(new Font("serif", Font.PLAIN, ANSWER_FONT_SIZE));
            answerL[i].setForeground(Color.BLACK);
            hintPanel.add(answerL[i]);

            // Add the change word button, only if there are alternatives
            if ((allWordList != null) && (allWordList.size() > 1)) {
              altWordButtons[i] = new JButton("*");
              altWordButtons[i].setBounds(currentXt + ((usedWidth[i] - SPACE_WIDTH) / 2) - 11, 15, 22, 22);
              altWordButtons[i].setVisible(true);
              altWordButtons[i].setToolTipText("Click here to see alternative words for this slot");
              altWordButtons[i].setActionCommand("altWord" + i);
              altWordButtons[i].addActionListener(this);
              hintPanel.add(altWordButtons[i]);
            }

            currentXt += usedWidth[i];
          }

          // Add list of words for this component to the wordTable
          if (allWordList != null) {
            wordTable.add(i, allWordList);
          }

        }// For i = 0 to Template Size (i.e. all words in template)

        // Log the answer against all the possible valid correct answers
        // NOTE: This answer was created from the original sentence lattice, not
        // from the matching template obtained above
        // --------------------------------------------------------------------------------------------------
        if (ansString != null) {
          // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
          // "Given Answer:      " + ansString);
          // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
          // "Matched Answer:  " + correctAnswer);
          // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
          // "All Correct Answers:");
          if (modelAnswersExtra != null) {
            for (int a = 0; a < modelAnswersExtra.size(); a++) {
              correctAnswer = (String) modelAnswersExtra.elementAt(a);
              if (correctAnswer != null) {
                // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
                // "Option " + a + ": "+ correctAnswer);
              }
            }
          }
        }

        // Display the score summary
        // =======================
        showScoreSummary();
      }

      // Check if we're on the last question (based on config settings for
      // number of questions)
      // In that case, we woin't change the skip button, as it will already be
      // hidden in favour of the Finish button
      if ((gconfig.numQuestionsPractice == -1) || (questionNum < (gconfig.numQuestionsPractice - 1))) {
        // Change the Skip button to "Next", and set this as the default button
        mainPanel.remove(skipButton);

        nextButton = new JButton("Next");
        nextButton.setActionCommand("next");
        nextButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
            CALL_main.butt_margin_h));

        nextButton.setToolTipText("Move on to the next question");
        nextButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
        nextButton.addActionListener(this);
        mainPanel.add(nextButton);

        rootPane.setDefaultButton(nextButton);
      }

      // Update lesson stats
      questionNum++;
      history.practiceQuestions++;

      maxTotalScore += maxscore;
      history.practiceTotalMaxScore += (long) maxscore;

      if (score > 0) {
        totalScore += score;
        answered++;

        history.practiceTotalScore += (long) score;
        history.practiceAnswered++;
      }

      // Update on screen
      updateLessonStats();
      repaint();
    } else if (command.startsWith("altWord")) {
      // Show the next alternative word for this answer
      compIndex = (Integer.decode(command.substring(7))).intValue();
      if ((compIndex >= 0) && (compIndex < CALL_sentenceHintsStruct.MAX_HINT_COMPONENTS)) {
        updateTargetWord(compIndex);
      }
    } else if (command.startsWith("feedback")) {
      // Pop up the feedback (if we can get error information)
      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
      // "Feedback viewed (dialog)");

      logger.info("command: " + command + "; Feedback viewed (dialog)");

      if ((currentQuestion != null) && (currentQuestion.hints != null)) {
        // Get the error pair struct, and the coefficient (that the cost needs
        // to be multiplied by
        errIndex = CALL_io.str2int(command.substring(8));

        logger.info("errIndex: " + errIndex);

        if ((errIndex != CALL_io.INVALID_INT) && (errors != null) && (errors.length > errIndex)) {
          parent.feedbackDialog = new CALL_feedbackDialog(parent, errors[errIndex],
              currentQuestion.hints.costCoefficient, maxscore, numComponents);
          parent.feedbackDialog.setLocationRelativeTo(parent);
          parent.feedbackDialog.setVisible(true);
        }
      }
    }
  }

  // Move on to next alternative word for given component
  // ====================================================================================================================
  public void updateTargetWord(int compIndex) {
    String tempString;
    Vector wordVector;
    int currentIndex;

    if (wordTable != null) {
      if ((compIndex >= 0) && (compIndex < wordTable.size())) {
        wordVector = (Vector) wordTable.elementAt(compIndex);
        if (wordVector != null) {
          // Work out new index
          currentIndex = matchingWordIndexes[compIndex];
          currentIndex++;
          if (currentIndex >= wordVector.size()) {
            currentIndex = 0;
          }
          matchingWordIndexes[compIndex] = currentIndex;

          // Now get string
          tempString = (String) wordVector.elementAt(currentIndex);
          if (tempString != null) {
            // Update the label
            answerL[compIndex].setText(tempString);
            answerL[compIndex].setToolTipText(tempString);
            answerL[compIndex].validate();
            repaint();
          }
        }
      }
    }
  }

  // Updates both lesson statistics panels
  // =================================
  public void updateLessonStats() {
    Date d1, d2;
    long elapsedTime;

    // Update the timer
    currentTime = new GregorianCalendar();

    // Current session panel
    // -------------------
    qNumD1.setText("" + questionNum);
    qNumD1.validate();

    if (questionNum > 0) {
      answeredP1.setText("" + (answered * 100) / questionNum + "%");
    } else {
      answeredP1.setText("0%");
    }
    answeredP1.validate();

    answeredD1.setText("(" + answered + ")");
    answeredD1.validate();

    if (maxTotalScore > 0) {
      totScoreP1.setText("" + (totalScore * 100) / maxTotalScore + "%");
    } else {
      totScoreP1.setText("0%");
    }
    totScoreP1.validate();

    d1 = currentTime.getTime();
    d2 = startTime.getTime();
    elapsedTime = (d1.getTime() - d2.getTime()) / 1000;
    timeD1.setText(CALL_time.getTimeString((long) elapsedTime));
    timeD1.validate();

    // All History Panel
    // ---------------
    modeL2.setText("All Sessions (" + history.practiceRuns + " runs)");
    modeL2.validate();

    qNumD2.setText("" + history.practiceQuestions);
    qNumD2.validate();

    if (history.practiceQuestions > 0) {
      answeredP2.setText("" + (history.practiceAnswered * 100) / history.practiceQuestions + "%");
    } else {
      answeredP2.setText("0%");
    }
    answeredP2.validate();

    answeredD2.setText("(" + history.practiceAnswered + ")");
    answeredD2.validate();

    if (history.practiceTotalMaxScore > 0) {
      totScoreP2.setText("" + (history.practiceTotalScore * 100) / history.practiceTotalMaxScore + "%");
    } else {
      totScoreP2.setText("0%");
    }
    totScoreP2.validate();

    d1 = currentTime.getTime();
    d2 = startTime.getTime();
    elapsedTime = ((d1.getTime() - d2.getTime()) / 1000);
    history.practiceTimeSpent = elapsedTime + originalTime;
    timeD2.setText(CALL_time.getTimeString((long) history.practiceTimeSpent));
    timeD2.validate();
  }

  // Update the hint buttons as necessary
  // ====================================================================================================================
  public void updateHintButtons(int compIndex, CALL_sentenceHintsStruct sentenceHints, String command) {
    CALL_wordHintsStruct wordHints;
    int nextCategory, nextLevel;
    int cost;

    // The individual hint button
    if (compIndex != -1) {
      updateHintButton(compIndex, sentenceHints);
    } else {
      // Update all the hint buttons
      for (int i = 0; i < sentenceHints.wordHints.size(); i++) {
        updateHintButton(i, sentenceHints);
      }
    }

    // Update and Validate fixed hint button
    if (fixedHintButton != null) {
      if ((nextRecommendation >= 0) && (nextRecommendation < sentenceHints.wordHints.size())) {
        wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(nextRecommendation);
        if (wordHints != null) {
          if (wordHints.areThereMoreHints()) {
            if (config.hintCostEnabled) {
              // Only have scoring information if scoring is enabled
              cost = wordHints.getNextCost();
              if (cost > 0) {
                fixedHintButton.setToolTipText("The next hint will cost you " + cost + " points!");
              } else {
                // No more hints - disable button
                fixedHintButton.setToolTipText("The next hint is free");
              }
            }
          } else {
            // No more hints left - update other hint buttons
            fixedHintButton.setToolTipText("No more hints available");
            fixedHintButton.setEnabled(false);

            allHintsButton.setToolTipText("No more hints available");
            allHintsButton.setEnabled(false);

            hintCategoryButton.setToolTipText("No more hints available");
            hintCategoryButton.setEnabled(false);
          }
        }
      } else {
        // No more hints left
        fixedHintButton.setEnabled(false);
      }

      // Finally validate the button
      fixedHintButton.validate();
    }

    // Update the hint category button
    if (hintCategoryButton != null) {
      nextCategory = sentenceHints.getUnrevealedLevel();
      if ((nextCategory >= 0) && (nextCategory < CALL_wordHintsStruct.MAX_LEVELS)) {
        hintCategoryButton.setText("Reveal [" + (String) sentenceHints.getLevelString(nextCategory) + "]");
        hintCategoryButton.setToolTipText("Revealing will cost " + sentenceHints.getCostToLevel(nextCategory)
            + " points!");
        hintCategoryButton.setEnabled(true);
      } else {
        hintCategoryButton.setText("Hints Revealed");
        hintCategoryButton.setToolTipText("No more hints to reveal");
        hintCategoryButton.setEnabled(false);
      }
      hintCategoryButton.validate();
    }

    // Update the all hints button
    if (allHintsButton != null) {
      if (!sentenceHints.moreHints()) {
        allHintsButton.setEnabled(false);
      }

      allHintsButton.validate();
    }

    repaint();
  }

  public void updateScoreDisplay() {
    int shade;
    Float r, g, b;

    shade = maxscore - score;
    r = new Float(0.0 + ((shade * 100.0) / maxscore) / 100.0);
    g = new Float(0.0);
    b = new Float(0.0);

    // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
    // "Score colour [R] based on " + (float)(((shade * 100.0)/maxscore) /
    // 100.0) + ", with Shade = " + shade + " and maxscore = " + maxscore);

    scoreD.setText("" + score);
    scoreD.setForeground(new Color(r.floatValue(), g.floatValue(), b.floatValue()));
    scoreD.validate();
    scoreP.setText("(" + ((score * 100) / maxscore) + "%)");
    scoreP.validate();
  }

  public void showScoreSummary() {
    int shade;
    Float r, g, b;

    shade = maxscore - score;
    r = new Float(0.0 + ((shade * 100.0) / maxscore) / 100.0);
    g = new Float(0.0);
    b = new Float(0.0);

    youScored = new JLabel("For this question you scored:");
    youScored.setBounds(SCORE_SUMMARY_X, insets.top + 520, 175, 15);

    thisQuestionScore = new JLabel("" + (score * 100) / maxscore + "%");
    thisQuestionScore.setBounds(SCORE_SUMMARY_X + 50, insets.top + 525, 150, 64);
    thisQuestionScore.setForeground(new Color(r.floatValue(), g.floatValue(), b.floatValue()));
    thisQuestionScore.setFont(new Font("serif", Font.ITALIC + Font.BOLD, 48));

    mainPanel.add(youScored);
    mainPanel.add(thisQuestionScore);

    // Set the happiness level
    happiness = 1 + ((((score * 100) / maxscore) - 10) / 20);
    if (happiness <= 0)
      happiness = 1;
    if (happiness > 5)
      happiness = 5;

    repaint();
  }

  // Update one individual hint button
  // ====================================================================================================================
  public void updateHintButton(int compIndex, CALL_sentenceHintsStruct sentenceHints) {
    CALL_wordHintsStruct wordHints;
    CALL_hintStruct hint;
    int nextCategory, nextLevel;
    String hintString, toolTipString;
    int cost;

    if ((compIndex >= 0) && (compIndex < sentenceHints.wordHints.size())) {
      wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(compIndex);
      if (wordHints != null) {
        hint = (CALL_hintStruct) wordHints.hints[wordHints.currentLevel];

        if (hint != null) {
          // Display appropriate hint string
          hintString = wordHints.getCurrentHintString(gconfig.outputStyle);

          if (gconfig.outputStyle == CALL_io.romaji) {
            hintString = CALL_io.strKanaToRomaji(hintString);
          }

          toolTipString = hintString;

          if (gconfig.outputStyle == CALL_io.kanji) {
            // We need to use the hirigana for the tooltip
            toolTipString = wordHints.getCurrentHintString(CALL_io.kana);
          }
          if ((hintString != null) && (toolTipString != null)) {
            hintD[compIndex].setText(hintString);
            hintD[compIndex].setToolTipText(toolTipString);
            hintD[compIndex].setForeground(Color.BLACK);

            if (config.hintChoice) {
              if (!wordHints.areThereMoreHints()) {
                // No more hints left to give on this particular component
                hintD[compIndex].setFont(new Font("serif", Font.BOLD, 14));
                hintButton[compIndex].setToolTipText("Sorry, no more hints available!");
                hintButton[compIndex].setEnabled(false);
              } else {
                cost = wordHints.getNextCost();

                if (wordHints.lastHintNext()) {
                  // The last hint
                  if (config.hintCostEnabled) {
                    // Include scoring
                    hintButton[compIndex].setToolTipText("The next hint will cost you " + cost
                        + " points. Note: FINAL hint for this word!");
                  } else {
                    // Just give information that this is the last hint
                    hintButton[compIndex].setToolTipText("Next hint is the FINAL hint for this word!");
                  }
                } else {
                  // Still lots of hints left
                  if (config.hintCostEnabled) {
                    // Include scoring
                    hintButton[compIndex].setToolTipText("The next hint will cost you " + cost + " points!");
                  }
                }
              }

              // Validate the button
              hintButton[compIndex].validate();
            }

          }
        }

        // Update the recommended hint information
        // ===========================================

        if (config.hintChoice) {
          if (nextRecommendation >= 0) {
            // This was the previous recommendation - set it back
            hintButton[nextRecommendation].setText("Hint");
            hintButton[nextRecommendation].validate();
          }
        }

        // Get the next recommendation
        nextRecommendation = sentenceHints.recommendNextHint();

        if (config.hintChoice) {
          if ((nextRecommendation >= 0) && (nextRecommendation < numComponents)) {
            // This is the new recommendation - put a * on it
            hintButton[nextRecommendation].setText("Hint*");
            hintButton[nextRecommendation].validate();
          }
        }

      }

      // Validate button
      hintD[compIndex].validate();
    }
  }

  // For text updates
  // ////////////////////////////////////////////////////
  public void changedUpdate(DocumentEvent e) {
    // // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.INFO,
    // "Changed");
  }

  public void insertUpdate(DocumentEvent e) {
    textentered++;
    if (textentered == 1) {
      // Enable text answer button, and default to using it
      textAnswerButton.setEnabled(true);
      rootPane.setDefaultButton(textAnswerButton);
    }
  }

  public void removeUpdate(DocumentEvent e) {
    textentered--;
    if (textentered <= 0) {
      textentered = 0;
      textAnswerButton.setEnabled(false);
    }
  }

  // Update the diagram
  public void redoDiagram() {
    if (diagramPanel != null) {
      mainPanel.remove(diagramPanel);
    }

    diagramPanel = new CALL_diagramPanel(currentQuestion, db, DIAGRAM_W, DIAGRAM_H, gconfig.diagMarkersEnabled);
    diagramPanel.setBounds(insets.left + DIAGRAM_X, insets.top + DIAGRAM_Y, diagramPanel.width, diagramPanel.height);
    diagramPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));

    mainPanel.add(diagramPanel);
    mainPanel.validate();
    repaint();
  }

  // Paint component
  // ////////////////////////////////////////////////////
  public void paintComponent(Graphics g) {
    Image recordLightImage;
    Image smileyFaceImage;
    String filename;
    int x, y, w, h;

    // Call teh practicePanel class paintComponent
    super.paintComponent(g);

    // Drawing the record button
    if (recording) {
      // Draw the light lit
      recordLightImage = CALL_images.getInstance().getImage("Misc/rec-on.gif");
    } else {
      // Draw the light off
      recordLightImage = CALL_images.getInstance().getImage("Misc/rec-off.gif");
    }
    if (recordLightImage != null) {
      x = 595;
      y = 535;
      h = 40;
      w = 40;
      g.drawImage(recordLightImage, x, y, x + w, y + h, 0, 0, recordLightImage.getWidth(this),
          recordLightImage.getHeight(this), Color.WHITE, this);
    }

    if (happiness > 0) {
      // 2011.08.30 T.Tajima Change
      // filename = new String("Misc/happy" + happiness + ".gif");
      filename = new String("Misc/happy" + happiness + ".png");
      smileyFaceImage = CALL_images.getInstance().getImage(filename);
      if (smileyFaceImage != null) {
        // 2011.08.30 T.Tajima Change
        g.drawImage(CALL_images.getInstance().getImage(filename, FACE_S, FACE_S), FACE_X, FACE_Y, null);
        /*
         * x = FACE_X; y = FACE_Y; h = FACE_S; w = FACE_S;
         * 
         * g.drawImage(smileyFaceImage, x, y, x + w, y + h, 0, 0,
         * smileyFaceImage.getWidth(this), smileyFaceImage.getHeight(this),
         * Color.WHITE, this);
         */
      }
    }
  }

  @Override
  public void onReceived(String strAnswer) {
    // TODO Auto-generated method stub

  }

  public void onError(String error) {
    // TODO Auto-generated method stub

  }
}