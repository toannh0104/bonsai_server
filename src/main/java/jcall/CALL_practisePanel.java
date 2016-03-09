///////////////////////////// //////////////////////////////////////
// Practise Panel
// The panel used when practising a lesson
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jcall.recognition.event.EventListener;

abstract class CALL_practisePanel extends JPanel implements ActionListener, DocumentListener, EventListener {
  /*
   * add by wang
   */
  boolean recognition;
  boolean speaking;

  boolean alt; // alt =false, speaking, alt= true,typing.

  // Vector network = new Vector();

  // Co-ordinates and size of diagram - these should be overwritten by Classes
  // inheriting from here
  static int DIAGRAM_W = 0;
  static int DIAGRAM_H = 0;
  static int DIAGRAM_X = 0;
  static int DIAGRAM_Y = 0;

  // The main panel
  JPanel mainPanel;
  CALL_diagramPanel diagramPanel;

  Image background;
  CALL_main parent;

  // Title
  JLabel titleL;

  // Root Pane (used for default button selection)
  JRootPane rootPane;

  // Configuration settings
  CALL_configDataStruct config;
  CALL_configDataStruct gconfig;

  // The current question
  CALL_questionStruct currentQuestion;

  // Buttons (common to all practice pannels)
  JButton quitButton;
  JButton skipButton;
  JButton nextButton;
  JButton finishButton;
  JButton textAnswerButton; // text is "OK"

  // wang
  JButton changeStyleButton;
  JButton buttonListenRecordSentence;
  JButton oralAnswerButton;
  JButton speakButton;
  JLabel oralAnswerLabel;

  JButton recordButton;
  JButton nextAnswer;
  JButton previousAnswer;

  // Vector for storing the model answers
  Vector modelAnswers; // A vector of sentences
  int displayedAnswerIndex; // The index of the current modelAnswer being
                            // displayed
  Vector modelAnswersExtra; // With extra info for logging

  // JTextAreas
  int textentered;
  String correctAnswer;

  // Lesson statistics
  int questionNum;
  int answered;
  int totalScore;
  int maxTotalScore;

  // For recording
  boolean recording;

  // Lesson information
  int index;
  int exampleIndex; // Used when faking to give multiple examples of one lesson

  // Student record
  CALL_lessonHistoryStruct history;

  String latelyRecordFile;

  /* Used for component placing */
  Insets insets;

  // Statics
  static final String uknHintStr = "????";

  // The lesson being practiced
  CALL_lessonStruct lesson;

  // And the database
  CALL_database db;

  boolean diagram_initialised;

  // The main constructor
  // ////////////////////////////////////////////////////////////////////////////////
  public CALL_practisePanel(CALL_main p, int lessonIndex) {
    FlowLayout buttonLayout;

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);
    diagram_initialised = false;
    parent = p;

    // Set the root pane
    rootPane = p.getRootPane();
    if (rootPane == null)
      System.out.println("Gulp");

    // Get the lesson
    db = p.db;
    lesson = db.lessons.getLesson(lessonIndex);
    index = lesson.index;

    // Get the student history
    history = db.currentStudent.getLessonHistory(index);

    // Get config
    if (parent.config.settings[lessonIndex + 1] == null) {
      config = parent.config.settings[0];
    } else {
      config = parent.config.settings[lessonIndex + 1];
    }

    // Global config
    gconfig = parent.config.settings[0];

    // We need to make sure this is set for when paintComponent is called
    recording = false;
    /*
     * add by wang
     */
    recognition = false;
    speaking = false;
    alt = false;

    // Reset the lesson statistics
    questionNum = 0;
    totalScore = 0;
    maxTotalScore = 0;
    answered = 0;

    // Initialise the model answer vector
    modelAnswers = new Vector();
    displayedAnswerIndex = 0;

    String latelyRecordFile = null;
  }

  // Abstract functions that must be addes
  // ///////////////////////////////////////////////////////////
  abstract void drawComponents();

  abstract void redoDiagram();

  abstract void nextQuestion() throws IOException;

  void resetValues() {
    // Always start with recording off
    recording = false;
    /*
     * add by wang
     */
    recognition = false;
    speaking = false;
    alt = false;

    textentered = 0;
  };

  // Act on events
  // ////////////////////////////////////////////////////
  abstract public void actionPerformed(ActionEvent e);

  /*
   * add by wang
   */
  //
  // abstract public void onReceive();
  //
  abstract public void onReceived(String strAnswer);

  // Update the hint buttons as necessary
  // ====================================================================================================================
  abstract void updateHintButtons(int compIndex, CALL_sentenceHintsStruct sentenceHints, String command);

  // Update one individual hint button
  // ====================================================================================================================
  abstract void updateHintButton(int compIndex, CALL_sentenceHintsStruct sentenceHints);

  // For text updates
  // ////////////////////////////////////////////////////
  public void changedUpdate(DocumentEvent e) {
    // do nothing here
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

  // Paint component
  // ////////////////////////////////////////////////////
  public void paintComponent(Graphics g) {
    Image recordLightImage;
    int x, y, w, h;

    super.paintComponent(g); // paint background

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    // Draw image at its natural size first.
    g.drawImage(background, 0, 0, this);
  }

}