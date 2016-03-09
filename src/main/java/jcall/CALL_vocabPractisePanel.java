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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;

public class CALL_vocabPractisePanel extends CALL_practisePanel {
  static int DIAGRAM_X = 25;
  static int DIAGRAM_Y = 70;
  static int DIAGRAM_W = 550;
  static int DIAGRAM_H = 340;

  static final int MAX_BUTT_WIDTH = 720;

  // The panels (already have mainPanel and diagramPanel of course)
  JPanel hintPanel;
  JPanel statsPanel; // WHERE TO PUT THIS (QUESTION NUMBER, MAYBE SCORE?)
  JPanel scorePanel;

  // Borders
  Border loweredetched;
  TitledBorder hintPanelBorder;
  TitledBorder scorePanelBorder;
  TitledBorder statsPanelBorder;

  // JLabels
  JLabel nextHintL;
  JLabel scoreD;

  JLabel answerTL;
  JLabel answerL;
  JLabel your_answerTL;
  JLabel your_answerL;

  // For the progressive hints (just 1 button, as we're dealing with just 1
  // word)
  JButton hintButton;
  JLabel hintD;

  // Text area for answer
  JTextField answerField;

  int score;
  int maxscore;

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_vocabPractisePanel(CALL_main p, int lessonIndex) {
    super(p, lessonIndex);

    // Reset the lesson statistics
    answered = 0;

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

    int i, j, cost, compIndex, nextLevel;
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
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");
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
      titleL = new JLabel("Lesson " + lesson.index + " - Vocabulary Practice");
      titleL.setForeground(Color.BLACK);
      titleL.setBounds(insets.left + 25, insets.top, 750, 50);
      titleL.setFont(new Font("serif", Font.BOLD, 32));

      /* Hint Panel */
      hintPanel = new JPanel();
      hintPanel.setLayout(null);
      hintPanel.setOpaque(false);
      hintPanel.setBounds(insets.left + 25, insets.top + 435, 750, 70);
      hintPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Model Answer");
      hintPanelBorder.setTitleJustification(TitledBorder.LEFT);
      hintPanel.setBorder(hintPanelBorder);

      // Draw hint components:
      // First, how many components do we have? Base button width on this
      // information
      sentenceHints = currentQuestion.hints;
      if (sentenceHints.wordHints != null) {
        if (currentQuestion.hints != null) {
          if (sentenceHints.wordHints.size() <= 0) {
            // The case of no components being listed
            // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
            // "No components listed");
            return;
          }

          // Now, create and draw the button - use index 0, as we're just using
          // 1 word, the first word
          wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(0);

          if (wordHints != null) {
            hint = wordHints.getCurrentHint();
            cost = wordHints.getNextCost();

            if (hint != null) {
              hintD = new JLabel(wordHints.getCurrentHintString(gconfig.outputStyle));
              if (gconfig.outputStyle == CALL_io.kanji) {
                // Don't use Kanji on the tooltips, which are sort of used as
                // furigana
                hintD.setToolTipText(wordHints.getCurrentHintString(CALL_io.kana));
              } else {
                // Not kanji, so just use whatever setting we have
                hintD.setToolTipText(wordHints.getCurrentHintString(gconfig.outputStyle));
              }
            } else {
              // We're on the
              hintD = new JLabel(uknHintStr);
              hintD.setToolTipText(uknHintStr);
            }

            hintD.setBounds(insets.left + 15, insets.top + 12, MAX_BUTT_WIDTH, 20);
            hintD.setFont(new Font("serif", Font.ITALIC, 14));
            hintD.setForeground(Color.GRAY);
            hintD.setHorizontalAlignment(JLabel.CENTER);

            // Add now, because it's easier to do so at this point
            hintPanel.add(hintD);

            // As for the buttons, depends on whether we're using fixed or
            // flexible hints.
            // The following is just for when we allow hint choice. The fixed
            // hint button is done later.

            hintButton = new JButton("Hint");

            hintButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
                CALL_main.butt_margin_h));

            hintButton.setActionCommand("hint");

            hintButton.setBounds(insets.left + 15, insets.top + 35, MAX_BUTT_WIDTH, 30);
            hintButton.addActionListener(this);

            // Add now, because it's easier to do so at this point
            hintPanel.add(hintButton);
          }
        }
      }

      // Recreate the diagram
      diagramPanel = new CALL_diagramPanel(currentQuestion, db, DIAGRAM_W, DIAGRAM_H, gconfig.diagMarkersEnabled);
      diagramPanel.setMarkers(gconfig.diagMarkersEnabled);
      diagramPanel.setBounds(insets.left + DIAGRAM_X, insets.top + DIAGRAM_Y, diagramPanel.width, diagramPanel.height);
      diagramPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));

      /* Score Panel */
      scorePanel = new JPanel();
      scorePanel.setLayout(null);
      scorePanel.setOpaque(false);
      scorePanel.setBounds(insets.left + 600, insets.top + 60, 175, 105);
      scorePanelBorder = BorderFactory.createTitledBorder(loweredetched, "Available Points");
      scorePanelBorder.setTitleJustification(TitledBorder.LEFT);
      scorePanel.setBorder(scorePanelBorder);

      scoreD = new JLabel("" + ((score * 100) / maxscore) + "%");
      scoreD.setBounds(insets.left + 35, insets.top + 32, 225, 40);
      scoreD.setFont(new Font("serif", Font.PLAIN, 38));
      scoreD.setForeground(Color.BLACK);
      scorePanel.add(scoreD);

      /* Stats Panel */
      statsPanel = new JPanel();
      statsPanel.setLayout(null);
      statsPanel.setOpaque(false);
      statsPanel.setBounds(insets.left + 600, insets.top + 185, 175, 225);
      statsPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Record");
      statsPanelBorder.setTitleJustification(TitledBorder.LEFT);
      statsPanel.setBorder(statsPanelBorder);

      // Lower Button area
      // ----------------

      // If we're on the last question (trial mode), change skip button to
      // Finish
      skipButton = new JButton("Skip");
      skipButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      skipButton.setActionCommand("skip");
      skipButton.setToolTipText("Skip this problem");
      skipButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
      skipButton.addActionListener(this);
      mainPanel.add(skipButton);

      // Button for submitting a text answer
      textAnswerButton = new JButton("OK");
      textAnswerButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      textAnswerButton.setActionCommand("textanswer");
      textAnswerButton.setToolTipText("Submit the entered answer");
      textAnswerButton.setBounds(insets.left + 420, insets.top + 535, 50, 40);
      textAnswerButton.addActionListener(this);
      textAnswerButton.setEnabled(false);

      // Recording on/off button
      recordButton = new JButton("REC");
      recordButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      recordButton.setActionCommand("record");
      recordButton.setToolTipText("Stop/Start recording");
      recordButton.setBounds(insets.left + 480, insets.top + 535, 50, 40);
      recordButton.addActionListener(this);

      // Answer components
      answerField = new JTextField("");
      answerField.setBounds(insets.left + 80, insets.top + 535, 335, 40);
      answerField.getDocument().addDocumentListener(this);

      /* Add components to main panel */
      mainPanel.add(titleL);
      mainPanel.add(hintPanel);
      mainPanel.add(scorePanel);
      mainPanel.add(statsPanel);
      mainPanel.add(textAnswerButton);
      mainPanel.add(recordButton);
      mainPanel.add(answerField);
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
    CALL_wordStruct word;
    Vector wordList;

    currentQuestion = new CALL_questionStruct(db, config, gconfig, lesson, CALL_questionStruct.QTYPE_VOCAB);

    // Update the word experience
    // NOTE: Should only be 1 word here...but following the same pattern as if
    // we had whole sentence - CJW
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

    // Now redraw the screen
    if (diagram_initialised) {
      System.out.println("!");
      remove(mainPanel);
      mainPanel.removeAll();
      drawComponents();
    }
  }

  public void resetValues() {
    CALL_sentenceStruct sentence;
    int x, y, starty;

    super.resetValues();

    // Get the correct answer
    sentence = currentQuestion.sentence;
    if (sentence != null) {
      correctAnswer = sentence.getSentenceString(gconfig.outputStyle);
    }

    // If Kanji, we'll put the Hirigana reading afterwards
    if (gconfig.outputStyle == CALL_io.kanji) {
      correctAnswer = correctAnswer + " (" + sentence.getSentenceString(CALL_io.kana) + ")";
    }

    // Get the score information
    score = currentQuestion.getMaxScore();
    maxscore = score;

    // Log which lesson we're on
    // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
    // "============NEXT VOCABULARY QUESTION================");
    // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW, "Lesson " +
    // index + ", Vocabulary Question " + (questionNum + 1));
  }

  // Act on events
  // ////////////////////////////////////////////////////
  public void actionPerformed(ActionEvent e) {
    CALL_sentenceHintsStruct sentenceHints;
    CALL_wordHintsStruct wordHints;
    CALL_hintStruct prevHint, nextHint;

    String command = new String(e.getActionCommand());
    String tempString;
    String logString;
    String prevHintString;
    String catString;
    String slotString;
    Integer tempInt;

    int cost;
    int tempI;
    int level;
    int nextLevel, nextCategory;
    int compIndex;

    // DEBUG
    // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
    // "Action -> " + command);

    if (command.startsWith("hint")) {
      sentenceHints = currentQuestion.hints;
      if (sentenceHints != null) {
        // Get the appropriate word hints structure
        wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(0);

        if (wordHints != null) {
          // Log hint usage
          catString = wordHints.getCategory();
          prevHint = wordHints.getCurrentHint();

          if (prevHint != null) {
            // THIS IS PROBABLY MESSED UP RIGHT NOW - CJW
            prevHintString = prevHint.getHintString(gconfig.outputStyle);
          } else {
            prevHintString = new String("none");
          }

          // Update hint structure (get next hint)
          nextHint = wordHints.getNextHint();

          if (nextHint != null) {
            nextLevel = wordHints.currentLevel;
            tempString = wordHints.getCurrentHintString(gconfig.outputStyle);
            score -= wordHints.getCurrentCost();

            // Do some logging
            if (command.compareTo("nextHint") == 0) {
              logString = new String("Hint Determined (Fixed Order):  Component: 0, Level: " + nextLevel + ", Cat: "
                  + catString + ", Desc: " + tempString);
              // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
              // logString);
            } else {
              logString = new String("Hint Selected (Free Choice)::  Component: 0, Level: " + nextLevel + ", Cat: "
                  + catString + ", Desc: " + tempString);
              // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.HINTS,
              // logString);
            }

            // Update the buttons
            updateHintButtons(0, sentenceHints, command);
          }
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
        filename = new String("Data/Speech/utterance-ID" + db.currentStudent.id + "-S" + gconfig.sessionNumber + "-P"
            + parent.practiceCount + "-L" + index + "-W-" + questionNum + ".wav");
        // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
        // "Start Recording => " + filename);

        recording = true;
        CALL_io.startRecording(filename);

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
      }
      repaint();
    } else if (command.compareTo("skip") == 0) {

      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
      // "Question Skipped");

      // Update lesson stats
      questionNum++;
      nextQuestion();
    } else if (command.compareTo("next") == 0) {
      // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
      // "Next Question");

      // Update lesson stats
      questionNum++;
      nextQuestion();
    } else if (command.compareTo("textanswer") == 0) {
      // Get the answer
      String ansString = answerField.getText();

      // Log the answer
      if (ansString != null) {
        // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
        // "Given Answer:    " + ansString);
        if (correctAnswer != null) {
          // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.RESULTS,
          // "Correct Answer: "+ correctAnswer);
        }
      }

      // Hide Hint
      if (hintD != null)
        hintD.setVisible(false);
      if (hintButton != null)
        hintButton.setVisible(false);

      // Display given answer
      your_answerTL = new JLabel("Your Answer:");
      your_answerTL.setBounds(15, 15, 700, 22);
      your_answerTL.setFont(new Font("serif", Font.BOLD, 16));
      your_answerTL.setForeground(Color.BLACK);
      hintPanel.add(your_answerTL);

      your_answerL = new JLabel(ansString);
      your_answerL.setBounds(135, 15, 700, 22);
      your_answerL.setFont(new Font("serif", Font.PLAIN, 16));
      your_answerL.setForeground(Color.BLACK);
      hintPanel.add(your_answerL);

      // Display Correct Answer
      answerTL = new JLabel("Model Answer:");
      answerTL.setBounds(15, 40, 700, 22);
      answerTL.setFont(new Font("serif", Font.BOLD, 16));
      answerTL.setForeground(Color.BLACK);
      hintPanel.add(answerTL);

      answerL = new JLabel(correctAnswer);
      answerL.setBounds(135, 40, 700, 22);
      answerL.setFont(new Font("serif", Font.PLAIN, 16));
      answerL.setForeground(Color.BLACK);
      hintPanel.add(answerL);

      // NEED TO DO ERROR CHECKING HERE - NOT IMPLEMENTED YET, CJW

      // Change the Skip button to "Next"
      mainPanel.remove(skipButton);

      nextButton = new JButton("Next");
      nextButton.setActionCommand("next");
      nextButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
          CALL_main.butt_margin_h));

      nextButton.setToolTipText("Move on to the next question");
      nextButton.setBounds(insets.left + 715, insets.top + 535, 65, 40);
      nextButton.addActionListener(this);
      mainPanel.add(nextButton);
      nextButton.validate();

      // Disable answer button (just answered!)
      textAnswerButton.setEnabled(false);
      textAnswerButton.validate();

      // And stop any further edits to text field
      answerField.setEditable(false);
      repaint();
    }
  }

  // Update the hint buttons as necessary
  // ====================================================================================================================
  public void updateHintButtons(int compIndex, CALL_sentenceHintsStruct sentenceHints, String command) {
    CALL_wordHintsStruct wordHints;
    int nextCategory, nextLevel;
    int cost;
    int shade;
    Float r, g, b;

    // The individual hint button (no other choice in Vocab Practice Mode - but
    // sticking to usual framework)
    updateHintButton(compIndex, sentenceHints);

    // Update the score label
    shade = maxscore - score;
    r = new Float(0.0 + ((shade * 100.0) / maxscore) / 100.0);
    g = new Float(0.0);
    b = new Float(0.0);

    // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
    // "Score colour [R] based on " + (float)(((shade * 100.0)/maxscore) /
    // 100.0) + ", with Shade = " + shade + " and maxscore = " + maxscore);

    scoreD.setText("" + ((score * 100) / maxscore) + "%");
    scoreD.setForeground(new Color(r.floatValue(), g.floatValue(), b.floatValue()));
    scoreD.validate();

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
    int visibleCost;

    if (compIndex != 0) {
      // Can only update for index 0
      return;
    }

    wordHints = (CALL_wordHintsStruct) sentenceHints.wordHints.elementAt(compIndex);
    if (wordHints != null) {
      hint = (CALL_hintStruct) wordHints.hints[wordHints.currentLevel];

      if (hint != null) {
        // Display appropriate hint string
        hintString = wordHints.getCurrentHintString(gconfig.outputStyle);

        if (gconfig.outputStyle == CALL_io.romaji) {
          hintString = CALL_io.strKanaToRomaji(hintString);
        }

        if (gconfig.outputStyle == CALL_io.kanji) {
          // We need to use the hirigana for the tooltip
          toolTipString = wordHints.getCurrentHintString(CALL_io.kana);
        } else {
          // Tooltip string is the same as the hint string
          toolTipString = hintString;
        }

        if ((hintString != null) && (toolTipString != null)) {
          hintD.setText(hintString);
          hintD.setToolTipText(toolTipString);
          hintD.setForeground(Color.BLACK);

          if (!wordHints.areThereMoreHints()) {
            // No more hints left to give on this particular component
            hintD.setFont(new Font("serif", Font.BOLD, 14));
            hintButton.setToolTipText("Sorry, no more hints available!");
            hintButton.setEnabled(false);
          } else {
            cost = wordHints.getNextCost();
            visibleCost = ((score * 100) / maxscore) - (((score - cost) * 100) / maxscore);

            if (wordHints.lastHintNext()) {
              // The last hint
              if (config.hintCostEnabled) {
                // Include scoring
                hintButton.setToolTipText("The next hint will cost you " + visibleCost
                    + "%. Note: FINAL hint for this word!");
              } else {
                // Just give information that this is the last hint
                hintButton.setToolTipText("Next hint is the FINAL hint for this word!");
              }
            } else {
              // Include scoring
              hintButton.setToolTipText("The next hint will cost you " + visibleCost + "%");
            }
          }

          // Validate the button
          hintButton.validate();
        }
      }

      // Validatge button
      hintD.validate();
    }
  }

  // For text updates
  // ////////////////////////////////////////////////////
  public void changedUpdate(DocumentEvent e) {
    // //CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.INFO,
    // "Changed");
  }

  public void insertUpdate(DocumentEvent e) {
    textentered++;
    textAnswerButton.setEnabled(true);
  }

  public void removeUpdate(DocumentEvent e) {
    textentered--;
    if (textentered <= 0) {
      textentered = 0;
      textAnswerButton.setEnabled(false);
    }
  }

  // Do nothing
  public void redoDiagram() {
  }

  // Paint component
  // ////////////////////////////////////////////////////
  public void paintComponent(Graphics g) {
    Image recordLightImage;
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
      x = 535;
      y = 535;
      h = 40;
      w = 40;
      g.drawImage(recordLightImage, x, y, x + w, y + h, 0, 0, recordLightImage.getWidth(this),
          recordLightImage.getHeight(this), Color.WHITE, this);
    }
  }

  /*
   * add by wang
   * 
   * @see jcall.CALL_practisePanel#onReceived(java.lang.String)
   */

  @Override
  public void onReceived(String strAnswer) {
    // TODO Auto-generated method stub

  }

  public void onError(String error) {
    // TODO Auto-generated method stub

  }
}