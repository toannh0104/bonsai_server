///////////////////////////////////////////////////////////////////
// Lesson Panel
// The main menus for Lessons
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CALL_lessonMenuPanel extends JPanel implements ActionListener {
  // Defines
  static final int ICON_SIZE = 13;
  static final int ICON_STARTX = 585;
  static final int ICON_STARTY = 113;
  static final int ICON_INCY = 40;
  static final int ICON_INCX = 5;

  // CHANGE THIS TO SHOW/HIDE ALL THE INACTIVE LESSONS FROM THE LIST! (v0.2e)
  static final boolean DISPLAY_INACTIVE = false;

  // The main panel
  JPanel mainPanel;

  Image background;

  // Title
  JLabel titleL;

  // Index of first lesson displayed on page.
  int lessonIndex;

  // Global configuration (whether to show inactive lessons, etc)
  CALL_configDataStruct config;

  // Lesson buttons
  JButton lessonButtons[];

  // Page Buttons
  JButton nextButton;
  JButton prevButton;

  // Lesson Descriptions
  JLabel levelTitle;
  JLabel experienceTitle;
  JLabel gradeTitle;

  Integer lessonIndexes[];

  JLabel lessonLabels[];
  JLabel lessonExperience[];
  JLabel lessonGrades[];

  // Lesson Levels
  Image level3icon;
  Image level4icon;

  // Reference to parent
  CALL_main parent;

  // return to main menu
  JButton backButton;

  // Static definitions
  static final int levelIconX = 725;
  static final int lessonsPerPage = 10;
  static final int lessonButtonSpacing = 40;
  static final int topLessonButton = 100; // The Y offset of button 1
  static final int topLessonLabel = 80; // Y Offset of lable compared with
                                        // button
  static final int topLessonIcon = 105; // Y Offset of level icon compared with
                                        // button

  public CALL_lessonMenuPanel(CALL_main p) {
    FlowLayout buttonLayout;
    int i;

    parent = p;

    config = parent.config.settings[0];

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    /* Set some member variables */
    lessonIndex = 0;

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");

    drawComponents();
  }

  // 2011.02.15 T.Tajima constructor(overload) add
  public CALL_lessonMenuPanel(CALL_main p, int previous_lesson) {
    FlowLayout buttonLayout;
    int i;

    parent = p;

    config = parent.config.settings[0];

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    for (i = 1;; i++) {
      if (previous_lesson < i * lessonsPerPage) {
        break;
      }
    }
    lessonIndex = (i - 1) * lessonsPerPage;

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("Screens/white1.jpg");

    drawComponents();
  }

  public void drawComponents() {
    String tempStr;
    CALL_lessonStruct lesson;
    CALL_lessonHistoryStruct lessonHistory;
    int tempInt;

    int currentIndex = lessonIndex;
    int i;

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    /* Clear the main panel */
    mainPanel.removeAll();

    /* Title */
    titleL = new JLabel("Lessons");
    titleL.setForeground(Color.BLACK);
    titleL.setBounds(insets.left + 25, insets.top, 325, 80);
    titleL.setFont(new Font("serif", Font.BOLD, 32));
    mainPanel.add(titleL);

    // Level grade Images
    level3icon = CALL_images.getInstance().getImage("Misc/level3icon.gif");
    level4icon = CALL_images.getInstance().getImage("Misc/level4icon.gif");

    // Buttons
    backButton = new JButton("Back");
    backButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    backButton.setActionCommand("back");
    backButton.setBounds(insets.left + 25, insets.top + 535, 85, 40);
    backButton.addActionListener(parent);
    mainPanel.add(backButton);

    // Next page
    nextButton = new JButton(">");
    nextButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    nextButton.setActionCommand("nextpage");
    nextButton.setBounds(insets.left + 710, insets.top + 535, 60, 40);
    nextButton.addActionListener(this);
    mainPanel.add(nextButton);

    // Prev page
    prevButton = new JButton("<");
    prevButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    prevButton.setActionCommand("prevpage");
    prevButton.setBounds(insets.left + 650, insets.top + 535, 60, 40);
    // 2011.02.15 T.Tajima repair ^
    prevButton.addActionListener(this);
    mainPanel.add(prevButton);

    /* Lesson Information */
    lessonIndexes = new Integer[lessonsPerPage];
    lessonButtons = new JButton[lessonsPerPage];
    lessonLabels = new JLabel[lessonsPerPage];
    lessonExperience = new JLabel[lessonsPerPage];
    lessonGrades = new JLabel[lessonsPerPage];

    // Column titles
    levelTitle = new JLabel("Level(s)");
    levelTitle.setForeground(Color.BLACK);
    levelTitle.setFont(new Font("serif", Font.BOLD, 12));
    levelTitle.setBounds(ICON_STARTX, insets.top + topLessonLabel - (lessonButtonSpacing), 555, 80);
    mainPanel.add(levelTitle);

    experienceTitle = new JLabel("Exp.");
    experienceTitle.setForeground(Color.BLACK);
    experienceTitle.setFont(new Font("serif", Font.BOLD, 12));
    experienceTitle.setBounds(685, insets.top + topLessonLabel - (lessonButtonSpacing), 555, 80);
    mainPanel.add(experienceTitle);

    gradeTitle = new JLabel("Grade");
    gradeTitle.setForeground(Color.BLACK);
    gradeTitle.setFont(new Font("serif", Font.BOLD, 12));
    gradeTitle.setBounds(745, insets.top + topLessonLabel - (lessonButtonSpacing), 555, 80);
    mainPanel.add(gradeTitle);

    for (i = 0; i < lessonsPerPage; i++) {
      // Get next lesson index
      for (;;) {
        if (currentIndex >= parent.db.lessons.numberLessons()) {
          // No more lessons
          lessonIndexes[i] = new Integer(-1);
          break;
        }

        // Get the lesson
        lesson = (CALL_lessonStruct) parent.db.lessons.getLesson(currentIndex);
        if (lesson != null) {
          if ((lesson.lessonActive()) || (config.showInactiveLessons)) {
            // Make note of the index
            lessonIndexes[i] = new Integer(currentIndex);

            // Create the button
            lessonButtons[i] = new JButton("Lesson " + (lesson.index));
            lessonButtons[i].setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h,
                CALL_main.butt_margin_v, CALL_main.butt_margin_h));

            lessonButtons[i].setBounds(insets.left + 25, insets.top + topLessonButton + (lessonButtonSpacing * i), 100,
                40);
            lessonButtons[i].setActionCommand("start_lesson_" + currentIndex);
            lessonButtons[i].addActionListener(parent);
            mainPanel.add(lessonButtons[i]);

            // Create the label
            lessonLabels[i] = new JLabel(lesson.title);
            lessonLabels[i].setForeground(Color.BLACK);
            lessonLabels[i].setBounds(insets.left + 175, insets.top + topLessonLabel + (lessonButtonSpacing * i), 555,
                80);
            mainPanel.add(lessonLabels[i]);

            // Add the practice attempts label
            lessonHistory = parent.db.currentStudent.getLessonHistory(lesson.index);
            if (lessonHistory != null) {
              // Add the test attempts label
              tempInt = lessonHistory.practiceQuestions + lessonHistory.testQuestions;
              lessonExperience[i] = new JLabel("" + tempInt);

              // Add the grade
              lessonGrades[i] = new JLabel("-");
            } else {
              // Defaults
              lessonExperience[i] = new JLabel("0");
              lessonGrades[i] = new JLabel("-");
            }

            lessonExperience[i].setForeground(Color.BLACK);
            lessonExperience[i].setBounds(685, insets.top + topLessonLabel + (lessonButtonSpacing * i), 75, 80);
            mainPanel.add(lessonExperience[i]);

            lessonGrades[i].setForeground(Color.BLACK);
            lessonGrades[i].setBounds(750, insets.top + topLessonLabel + (lessonButtonSpacing * i), 50, 80);
            mainPanel.add(lessonGrades[i]);

            if (!lesson.lessonActive()) {
              // An inactive lesson
              lessonButtons[i].setEnabled(false);
            }

            // We've added a button, so break the loop
            currentIndex++;
            break;
          } else {
            // Increment lesson index
            currentIndex++;
          }
        }
      }
    }

    // Enable, disable page buttons as necessary
    if (lessonIndex < lessonsPerPage) {
      prevButton.setEnabled(false);
    } else {
      prevButton.setEnabled(true);
    }

    if (currentIndex >= parent.db.lessons.numberLessons()) {
      nextButton.setEnabled(false);
    } else {
      nextButton.setEnabled(true);
    }

    /* Finally add button panels */
    add(mainPanel, BorderLayout.CENTER);

    /* And redraw */
    repaint();
    validate();
  }

  // Act on events
  // ////////////////////////////////////////////////////
  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    // DEBUG
    // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.DEBUG,
    // "Action -> " + command);

    // Dealing with turning pages only
    if (command.compareTo("prevpage") == 0) {
      lessonIndex = lessonIndex - lessonsPerPage;
      remove(mainPanel);
      drawComponents();
    } else if (command.compareTo("nextpage") == 0) {
      lessonIndex = lessonIndex + lessonsPerPage;
      remove(mainPanel);
      drawComponents();
    }
  }

  // Paint the panel
  // ////////////////////////////////////////////////////////

  public void paintComponent(Graphics g) {
    super.paintComponent(g); // paint background
    int x, y, currentIndex;

    CALL_lessonStruct lesson;

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    // Draw image at its natural size first.
    g.drawImage(background, 0, 0, this);

    // Now draw lesson level tokens
    for (int i = 0; i < lessonsPerPage; i++) {
      // Get next lesson index
      currentIndex = ((Integer) lessonIndexes[i]).intValue();
      if (currentIndex == -1) {
        // No more lessons to display
        break;
      }

      // Get the lesson
      lesson = (CALL_lessonStruct) parent.db.lessons.getLesson(currentIndex);
      x = ICON_STARTX;
      y = ICON_STARTY + (i * ICON_INCY);

      // Level 4
      if (lesson.levels[3]) {
        g.drawImage(level4icon, x, y, x + ICON_SIZE, y + ICON_SIZE, 0, 0, level4icon.getWidth(this),
            level4icon.getHeight(this), Color.WHITE, this);
        x += (ICON_SIZE + ICON_INCX);
      }

      // Level 3
      if (lesson.levels[2]) {
        g.drawImage(level3icon, x, y, x + ICON_SIZE, y + ICON_SIZE, 0, 0, level3icon.getWidth(this),
            level3icon.getHeight(this), Color.WHITE, this);
        x += (ICON_SIZE + ICON_INCX);
      }

      currentIndex++;
    }
  }
}