///////////////////////////////////////
//
// Concept CALL
//
// A CALL System for learners of Japanese
// Christopher Waple 2006
//
///////////////////////////////////////
package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import jcall.config.ConfigInstant;
import jcall.config.Configuration;
import jcall.recognition.event.Notifier;

import org.apache.log4j.Logger;

//////////////////////////////
// CALL main panel
//
//////////////////////////////
public class CALL_main extends JPanel implements ItemListener, ActionListener {
  // add by wang------------------
  static Logger logger = Logger.getLogger(CALL_main.class.getName());
  // private static Log logger = LogFactory.getLog(CALL_main.class);
  static boolean bOral = false;
  JContextOralPractisePanel cop;
  // ---------------------------

  static JFrame frame;
  static Dimension d;
  static CALL_main callMain;
  static JLabel progressLabel;
  static JProgressBar progressBar;
  static Color backgroundColor;

  static String characterHelp = new String("/Data/html/characters.html");
  static String dictionaryHelp = new String("/Data/html/dictionary.html");
  static String workingDir = System.getProperty("user.dir");

  // States (for main state machine)
  // 0: Login
  // 1: New Student
  // 2: TITLE
  // 3: LessonMenu
  // 4: Lesson
  // 5: Notes
  // 6: Example
  // 7: Practise Setup
  // 8: Practise
  // 9: Test
  // 10: OptionMenu
  // 11: ProgressMenu
  static final int STATE_LOGIN = 0;
  static final int STATE_NEW_STUDENT = 1;
  static final int STATE_TITLE = 2;
  static final int STATE_LESSON_MENU = 3;
  static final int STATE_LESSON = 4;
  static final int STATE_NOTES = 5;
  static final int STATE_EXAMPLE = 6;
  static final int STATE_PRACTICE_SETUP = 7;
  static final int STATE_PRACTICE_CONTEXT = 8;
  static final int STATE_PRACTICE_VERB = 9;
  static final int STATE_PRACTICE_VOCAB = 10;
  static final int STATE_TEST = 11;
  static final int STATE_OPTION_MENU = 12;
  static final int STATE_PROGRESS = 13;
  static final int STATE_ERROR = 99;

  // Menus
  JMenu helpMenu;
  JMenu configMenu;

  // Help Menu
  private JMenuItem aboutMI;
  private JMenuItem lexiconMI;
  private JMenuItem overviewMI;
  private JMenuItem dictionaryMI;
  private JMenuItem charactersMI;
  private JMenuItem costConfigMI;
  private JMenuItem orderConfigMI;
  // 2011.01.14 T.Tajima add
  private JMenuItem reloadMI;
  private JMenuItem tellusMI;

  // Configuration Menu
  private JMenuItem configMI;

  // Menu buttons etc
  JCheckBoxMenuItem romajiCB;
  JCheckBoxMenuItem kanaCB;
  JCheckBoxMenuItem kanjiCB;

  JCheckBoxMenuItem markersCB;

  // Menu buttons added later for label
  JCheckBoxMenuItem enCB;
  JCheckBoxMenuItem chCB;

  // Global configuration (pre-student login)
  // ==================================
  CALL_configStruct gconfig;

  // Configuration
  CALL_configStruct config;

  // xml configration, for changable settings
  Configuration configxml;

  // Practice configuration
  CALL_practiceSetupStruct practiceConfig;

  // Panels, Dialogs
  JPanel mainPanel;

  // All the sub-screens
  CALL_titlePanel tp;

  // Sub panels etc
  CALL_lessonMenuPanel lmp;
  CALL_lessonPanel lp;
  CALL_lessonNotesPanel lnp;
  CALL_practiseSetupPanel psp;
  CALL_practisePanel pp;
  CALL_lessonNotesDialog lnd;
  CALL_examplePanel lep;

  // Pop-up dialogs
  CALL_studentSelectDialog loginDialog;
  CALL_studentNewDialog newStudentDialog;
  CALL_feedbackDialog feedbackDialog;

  CALL_hintCostConfigDialog hintCostDialog;
  CALL_errorPredictionConfigDialog errorPredictionDialog;

  CALL_aboutDialog aboutDialog;
  CALL_lexiconSummaryDialog lexiconDialog;

  // Datastructs
  CALL_database db;

  // States etc
  int debug_level;
  int state;
  int lessonIndex;
  int practiceCount; // Number of current practice session in current software
                     // session (for logging)

  String studentName;

  // Constants
  public static final int butt_margin_v = 4;
  public static final int butt_margin_h = 10;
  public static final int width = 800;
  public static final int height = 645;

  public static final String title = "CallJ";
  public static final String subtitle = "Japanese Learning by Concept";
  public static final String version = "1.5.1 - August 2011";

  // For debugging, testing etc
  public static final int NUM_EXAMPLES = 10;
  public static final int EXAMPLE_SETS = 5;
  public static final int QUESTION_MODE = -1;

  // 2011.04.04 T.Tajima add
  static class Julian extends Thread {
    java.lang.ProcessBuilder pb;
    Process process;

    public void run() {
      try {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
          // Runtime.getRuntime().exec(new String[]
          // {".\\julian\\bin\\julian.exe","-C",".\\L1objects.jconf","-module","-input","mic"});
          // java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(new
          // String[]
          // {".\\julian\\bin\\julian.exe","-C",".\\L1objects.jconf","-module","-input","mic"});
          pb = new java.lang.ProcessBuilder(new String[] { ".\\julian\\bin\\julian.exe", "-C", ".\\L1objects.jconf",
              "-module", "-input", "mic" });
          // process = pb.start();
          pb.start();
        } else if (osName.startsWith("Linux")) {
          process = Runtime.getRuntime().exec(
              new String[] { "./julian/bin/julius-4.1.5", "-C", "L1objects.jconf", "-module", "-input", "mic" });
          process.waitFor();
        }
      } catch (IOException e) {
        // TODO
        e.printStackTrace();
      } catch (InterruptedException e) {
        // TODO
        e.printStackTrace();
      }
    }

    public void MyStop() {
      if (process != null) {
        process.destroy();
        process = null;
      }
      // ClientAgent.doConnect();
      // ClientAgent.doSend("DIE");
      // ClientAgent.doDisconnect();
    }
  }

  static Julian julian = new Julian();

  // Constructor for CALLJ main frame
  // ////////////////////////////////////////////////////
  public CALL_main() throws IOException {
    // Set time
    CALL_time.initTime();

    // Set debugging
    debug_level = CALL_debug.WARN;
    // CALL_debug.set_level(debug_level);
    // 2011.03.29 T.Tajima change
    CALL_debug.set_level(CALL_debug.MOD_GLOBAL, CALL_debug.WARN);

    // 2011.03.29 T.Tajima add
    // DEBUG < INFO < WARN < ERROR < FATAL
    // org.apache.log4j.BasicConfigurator.configure();
    logger.setLevel(org.apache.log4j.Level.WARN);

    /*
     * start one
     */
    // 2011.01.06 T.Tajima
    // Julian.startJulian();

    // Initialise states
    state = 0;
    lessonIndex = 0;
    practiceCount = 0;
    progressBar.setMaximum(100);
    progressBar.setValue(0);

    // Set the icon for progress panel
    frame.setIconImage(CALL_images.getInstance().getImage("Misc/about.jpg"));
    frame.setVisible(true);

    // Load generic config (this will be discarded after log in, as a student
    // based config file is loaded to replace it
    progressLabel.setText("Loading Configuration");
    config = new CALL_configStruct();
    progressBar.setValue(10);

    // Initialise Data Structures
    practiceConfig = null;
    progressLabel.setText("Loading Data");
    db = new CALL_database();
    progressBar.setValue(50);

    // Run any tests, data debugging etc here - CJW
    // ========================================
    if ((config.settings[0] != null) && (config.settings[0].runMiscTests)) {
      progressLabel.setText("Running tests");
      logExampleSentence(QUESTION_MODE, NUM_EXAMPLES, EXAMPLE_SETS);
      progressBar.setValue(60);
    }

    // ==========add new config.xml
    configxml = Configuration.getConfig();
    // studentName = configxml.getItemValue("systeminfo", "laststudent");

    // Initialise image structures
    progressLabel.setText("Loading images");
    CALL_images callimage = CALL_images.getInstance();
    // new CALL_images(progressBar, 90);
    progressBar.setValue(50);

    progressLabel.setText("Loading fonts");
    new CALL_fonts();

    // The about dialog
    aboutDialog = new CALL_aboutDialog(this);
    aboutDialog.setVisible(false);

    // The lexicon dialog
    lexiconDialog = new CALL_lexiconSummaryDialog(this);
    lexiconDialog.setVisible(false);

    // Hint dialog
    hintCostDialog = null;
    errorPredictionDialog = null;
    feedbackDialog = null;

    // Final progress
    progressBar.setValue(100);

    // Store the current configuration as the generic settings (used for saving
    // name)
    gconfig = config;

    // Display the login dialog(s)
    newStudentDialog = new CALL_studentNewDialog(this);
    newStudentDialog.setLocationRelativeTo(this);
    newStudentDialog.setVisible(false);

    loginDialog = new CALL_studentSelectDialog(this);
    loginDialog.setLocationRelativeTo(this);
    loginDialog.setVisible(true);
    // loginDialog.getCursor();
    loginDialog.toFront();

    // 2011.02.18 T.Tajima
    frame.setVisible(false);

  }

  public void intialiseMainPanel() {
    setLayout(new BorderLayout());
    setBorder(new EtchedBorder());
    add(createMenuBar(), BorderLayout.NORTH);
  }

  // Create the menu bar
  // ////////////////////////////////////////////////////
  private JMenuBar createMenuBar() {

    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    JMenuBar menuBar = new JMenuBar();

    // Help menu
    // ---------------------------------------------------------
    helpMenu = (JMenu) menuBar.add(new JMenu("Help"));

    // Sub menu
    JMenu subHelp = (JMenu) helpMenu.add(new JMenu("Notes"));
    overviewMI = (JMenuItem) subHelp.add(new JMenuItem("Overview"));
    overviewMI.setActionCommand("help-overview");
    dictionaryMI = (JMenuItem) subHelp.add(new JMenuItem("Dictionary"));
    dictionaryMI.setActionCommand("help-diagram");
    charactersMI = (JMenuItem) subHelp.add(new JMenuItem("Characters"));
    charactersMI.setActionCommand("help-characters");

    // Add separator on MAIN help list
    helpMenu.addSeparator();

    // Lexicon Summary
    lexiconMI = (JMenuItem) helpMenu.add(new JMenuItem("Lexicon"));
    lexiconMI.setActionCommand("menu-lexicon");

    // Add separator on MAIN help list
    helpMenu.addSeparator();

    // About box
    aboutMI = (JMenuItem) helpMenu.add(new JMenuItem("About"));
    aboutMI.setActionCommand("menu-about");

    overviewMI.addActionListener(this);
    dictionaryMI.addActionListener(this);
    aboutMI.addActionListener(this);
    lexiconMI.addActionListener(this);
    charactersMI.addActionListener(this);

    // 2011.01.14 T.Tajima
    helpMenu.addSeparator();
    reloadMI = (JMenuItem) helpMenu.add(new JMenuItem("Reload"));
    reloadMI.setActionCommand("menu-reload");
    reloadMI.addActionListener(this);

    // 2011.09.14 T.Tajima add
    helpMenu.addSeparator();
    tellusMI = (JMenuItem) helpMenu.add(new JMenuItem("Tell us"));
    tellusMI.setActionCommand("menu-tellus");
    tellusMI.addActionListener(this);

    // Config menu
    // ---------------------------------------------------------
    configMenu = (JMenu) menuBar.add(new JMenu("Config"));

    // Sub menu "Diagram"
    JMenu diagramConfig = (JMenu) configMenu.add(new JMenu("Diagram"));

    markersCB = (JCheckBoxMenuItem) diagramConfig.add(new JCheckBoxMenuItem("Display Markers"));
    markersCB.setActionCommand("config-diagrammarkers");
    markersCB.addActionListener(this);
    markersCB.setSelected(config.settings[0].diagMarkersEnabled);

    // Sub menu "Text"
    JMenu textConfig = (JMenu) configMenu.add(new JMenu("Text"));

    /*
     * delete Romaji
     */
    romajiCB = (JCheckBoxMenuItem) textConfig.add(new JCheckBoxMenuItem("Romaji"));
    romajiCB.setActionCommand("config-romaji");
    romajiCB.addActionListener(this);
    romajiCB.setSelected((config.settings[0].outputStyle == CALL_io.romaji));

    // String outputstyle = configxml.getItemValue("default", "langform");

    kanaCB = (JCheckBoxMenuItem) textConfig.add(new JCheckBoxMenuItem("Kana"));
    kanaCB.setActionCommand("config-kana");
    kanaCB.addActionListener(this);
    kanaCB.setSelected((config.settings[0].outputStyle == CALL_io.kana));

    kanjiCB = (JCheckBoxMenuItem) textConfig.add(new JCheckBoxMenuItem("Kanji"));
    kanjiCB.setActionCommand("config-kanji");
    kanjiCB.addActionListener(this);
    kanjiCB.setSelected((config.settings[0].outputStyle == CALL_io.kanji));

    // Sub menu "Label"
    JMenu labelConfig = (JMenu) configMenu.add(new JMenu("Label"));

    enCB = (JCheckBoxMenuItem) labelConfig.add(new JCheckBoxMenuItem("English"));
    enCB.setActionCommand("config-en");
    enCB.addActionListener(this);
    enCB.setSelected(true);

    chCB = (JCheckBoxMenuItem) labelConfig.add(new JCheckBoxMenuItem("Chinese"));
    chCB.setActionCommand("config-ch");
    chCB.addActionListener(this);
    chCB.setSelected(false);

    // Sub menu "Advanced"
    JMenu advancedConfig = (JMenu) configMenu.add(new JMenu("Advanced"));

    costConfigMI = (JMenuItem) advancedConfig.add(new JMenuItem("Score System"));
    costConfigMI.setActionCommand("hintCost-config");
    costConfigMI.addActionListener(this);

    orderConfigMI = (JMenuItem) advancedConfig.add(new JMenuItem("Perceived Difficulty"));
    orderConfigMI.setActionCommand("errorPrediction-config");
    orderConfigMI.addActionListener(this);

    return menuBar;
  }

  // Clean up when closing (close logs, etc)
  // ////////////////////////////////////////////////////
  public void cleanup() {
    saveStudentData();

    // Save lexicon - turn this on to recreate the lexicon if the formats been
    // changed in the code - CJW
    // db.lexicon.save_lexicon();

    // close logging
    CALL_debug.closeLog();
  }

  // Save the student configuration and history files
  // ////////////////////////////////////////////////////
  public void saveStudentData() {
    // Save config data (tied to student)
    if (config != null) {
      if (config.studentIndex != -1) {
        config.saveConfig();
      }
    }

    // Save the generic configuration data (for name etc)
    if (gconfig != null) {
      gconfig.saveConfig();
    }

    // Save student data
    if (db != null) {
      if (db.students != null) {
        db.students.save(CALL_database.dataDirectory);
      }
    }
  }

  // Act on events
  // ////////////////////////////////////////////////////
  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    // First, deal with any state independent actions
    if (command.compareTo("menu-about") == 0) {
      aboutDialog.setLocationRelativeTo(this);
      aboutDialog.setVisible(true);
    }
    if (command.compareTo("menu-lexicon") == 0) {
      lexiconDialog.setLocationRelativeTo(this);
      lexiconDialog.setVisible(true);
    }
    // 2011.01.14 T.Tajima
    else if (command.compareTo("menu-reload") == 0) {
      db = new CALL_database();
      // CHECK THE LOG-IN INFORMATION
      db.currentStudent = db.students.getStudent(loginDialog.getName(), loginDialog.getPassword());
      if (db.currentStudent != null) {
        // Update the value in the config file
        if (gconfig.settings[0] != null) {
          gconfig.settings[0].lastStudent = loginDialog.getName();
        }

        // Update the value in the new xml config file
        configxml.updateItem("systeminfo", "laststudent", loginDialog.getName());

        // Load the config for this student
        config = new CALL_configStruct(db.currentStudent.id);

        // Hide the login dialog
        loginDialog.setVisible(false);
        mainPanel.removeAll();
        // Re-add the title panel
        tp = new CALL_titlePanel(this);
        mainPanel.add(tp, BorderLayout.CENTER);
        validate();

        // Update the state variable
        state = STATE_TITLE;
        /*
         * // Display tile page intialiseMainPanel(); initialize_frame();
         * 
         * UIManager.put("Button.margin", new Insets(0,0,0,0));
         * 
         * // Create and add the main content panel mainPanel = new JPanel(new
         * GridLayout(1,1)); add(mainPanel, BorderLayout.CENTER);
         * 
         * // Add the title panel tp = new CALL_titlePanel(this);
         * mainPanel.add(tp, BorderLayout.CENTER);
         * 
         * validate(); repaint();
         * 
         * state = STATE_TITLE;
         */

      }

    }
    // 2011.09.14 T.Tajima add
    else if (command.compareTo("menu-tellus") == 0) {
      try {
        Desktop.getDesktop().browse(new URI("http://www.al.media.kyoto-u.ac.jp/callj/"));
      } catch (IOException e1) {
        // TODO
        e1.printStackTrace();
      } catch (URISyntaxException e1) {
        // TODO
        e1.printStackTrace();
      }
    } else if (command.compareTo("help-diagram") == 0) {
      CALL_io.openURL(workingDir + dictionaryHelp);
    } else if (command.compareTo("help-characters") == 0) {
      CALL_io.openURL(workingDir + characterHelp);
    } else if (command.compareTo("config-hintcost") == 0) {
      if (config.settings[0].hintCostEnabled) {
        config.settings[0].hintCostEnabled = false;
      } else {
        config.settings[0].hintCostEnabled = true;
      }
    } else if (command.compareTo("hintCost-config") == 0) {
      hintCostDialog = new CALL_hintCostConfigDialog(this);
      hintCostDialog.setLocationRelativeTo(this);
      hintCostDialog.setVisible(true);
    } else if (command.compareTo("errorPrediction-config") == 0) {
      errorPredictionDialog = new CALL_errorPredictionConfigDialog(this);
      errorPredictionDialog.setLocationRelativeTo(this);
      errorPredictionDialog.setVisible(true);
    } else if (command.compareTo("config-romaji") == 0) {
      config.settings[0].outputStyle = CALL_io.romaji;

      String strType = configxml.getItemValue("systeminfo", "laststudent");
      configxml.updateItem(strType, "langform", "romaji");

      romajiCB.setSelected(true);
      kanaCB.setSelected(false);
      kanjiCB.setSelected(false);
    } else if (command.compareTo("config-kana") == 0) {
      config.settings[0].outputStyle = CALL_io.kana;

      String strType = configxml.getItemValue("systeminfo", "laststudent");
      configxml.updateItem(strType, "langform", "kana");

      romajiCB.setSelected(false);
      kanaCB.setSelected(true);
      kanjiCB.setSelected(false);
    } else if (command.compareTo("config-kanji") == 0) {
      config.settings[0].outputStyle = CALL_io.kanji;

      String strType = configxml.getItemValue("systeminfo", "laststudent");
      configxml.updateItem(strType, "langform", "kanji");

      romajiCB.setSelected(false);
      kanaCB.setSelected(false);
      kanjiCB.setSelected(true);
    }

    else if (command.compareTo("config-en") == 0) {

      String strType = configxml.getItemValue("systeminfo", "laststudent");
      configxml.updateItem(strType, "label", ConfigInstant.CONFIG_LabelOption_EN);

      chCB.setSelected(false);
      enCB.setSelected(true);

      config.settings[0].diagMarkersEnabled = true;
    } else if (command.compareTo("config-ch") == 0) {
      // 2011.09.01 T.Tajima delete for remove chinese
      JOptionPane.showMessageDialog(frame, "chinese will be removed", "Caution!!", JOptionPane.INFORMATION_MESSAGE);
      chCB.setSelected(false);
      enCB.setSelected(true);
      /*
       * String strType = configxml.getItemValue("systeminfo", "laststudent");
       * configxml.updateItem(strType, "label",
       * ConfigInstant.CONFIG_LabelOption_CH);
       * 
       * chCB.setSelected(true); enCB.setSelected(false);
       * 
       * config.settings[0].diagMarkersEnabled = true;
       */
    } else if (command.compareTo("config-diagrammarkers") == 0) {
      if (config.settings[0].diagMarkersEnabled) {
        config.settings[0].diagMarkersEnabled = false;
      } else {
        config.settings[0].diagMarkersEnabled = true;
      }

      // Do we need to update the diagram (is it being displayed?)
      if (state == STATE_PRACTICE_CONTEXT) {
        if (pp != null) {
          // Recreate the diagram
          pp.redoDiagram();
        }
      }
    } else if (command.compareTo("hintCostConfig-cancel") == 0) {
      if (hintCostDialog != null) {
        // Hide dialog
        hintCostDialog.setVisible(false);
        hintCostDialog = null;
      }
    } else if (command.compareTo("hintCostConfig-ok") == 0) {
      if (hintCostDialog != null) {
        // Update config
        if ((config != null) && (config.settings[0] != null)) {
          config.settings[0].hintGrammarBias = hintCostDialog.grammarBiasS.getValue();
          config.settings[0].hintLevelBias = hintCostDialog.levelBiasS.getValue();
          config.settings[0].hintCostEnabled = hintCostDialog.costEnabled;

          if (hintCostDialog.costCalibration) {
            // We disregard possibility of option 2 for now CJW
            config.settings[0].hintCostAutoBalance = 1;
          } else {
            // Its turned off
            config.settings[0].hintCostAutoBalance = 0;
          }
        }

        // Hide dialog
        hintCostDialog.setVisible(false);
        hintCostDialog = null;
      }
    } else if (command.compareTo("errorPredictionConfig-cancel") == 0) {
      if (errorPredictionDialog != null) {
        // Hide dialog
        errorPredictionDialog.setVisible(false);
        errorPredictionDialog = null;
      }
    } else if (command.compareTo("errorPredictionConfig-ok") == 0) {
      if (errorPredictionDialog != null) {
        // Update config
        if ((config != null) && (config.settings[0] != null)) {
          config.settings[0].hintWordLearningRate = errorPredictionDialog.learningRateS.getValue();
          config.settings[0].hintWordLearningBiasLT = errorPredictionDialog.longBiasS.getValue();
          config.settings[0].hintWordLearningBiasMT = errorPredictionDialog.mediumBiasS.getValue();
          config.settings[0].hintWordLearningBiasST = errorPredictionDialog.shortBiasS.getValue();
          config.settings[0].hintWordLearningImpact = errorPredictionDialog.wordImpactS.getValue();
          config.settings[0].useWordExperience = errorPredictionDialog.useWordExp;

          config.settings[0].hintErrorSTImpact = errorPredictionDialog.errorSTS.getValue();
          config.settings[0].hintErrorLTImpact = errorPredictionDialog.errorLTS.getValue();
        }

        // Hide dialog
        errorPredictionDialog.setVisible(false);
        errorPredictionDialog = null;
      }
    } else {
      switch (state) {
        case STATE_LOGIN:
          // Student log-in
          if (command.compareTo("student-ok") == 0) {
            // CHECK THE LOG-IN INFORMATION
            db.currentStudent = db.students.getStudent(loginDialog.getName(), loginDialog.getPassword());
            if (db.currentStudent != null) {
              // Update the value in the config file
              if (gconfig.settings[0] != null) {
                gconfig.settings[0].lastStudent = loginDialog.getName();
              }

              // Update the value in the new xml config file
              configxml.updateItem("systeminfo", "laststudent", loginDialog.getName());

              // Load the config for this student
              config = new CALL_configStruct(db.currentStudent.id);

              // Hide the login dialog
              loginDialog.setVisible(false);

              // Display tile page
              intialiseMainPanel();
              initialize_frame();

              UIManager.put("Button.margin", new Insets(0, 0, 0, 0));

              // Create and add the main content panel
              mainPanel = new JPanel(new GridLayout(1, 1));
              add(mainPanel, BorderLayout.CENTER);

              // Add the title panel
              tp = new CALL_titlePanel(this);
              mainPanel.add(tp, BorderLayout.CENTER);

              // 2011.02.18 T.Tajima
              frame.setVisible(true);

              validate();
              repaint();

              state = STATE_TITLE;
            } else {
              // Redisplay the dialog
              loginDialog.setVisible(false);
              loginDialog = new CALL_studentSelectDialog(this);
              loginDialog.setLocationRelativeTo(this);
              loginDialog.setVisible(true);
            }
          } else if (command.compareTo("student-exit") == 0) {
            cleanup();

            // 2011.01.14 T.Tajima
            // TODO
            // process.destroy();
            julian.MyStop();

            // Close application
            System.exit(0);
          } else if (command.compareTo("student-new") == 0) {
            // To Do
            loginDialog.setVisible(false);

            // Recreate - to refresh
            newStudentDialog = new CALL_studentNewDialog(this);
            newStudentDialog.setLocationRelativeTo(this);
            newStudentDialog.setVisible(true);

            state = STATE_NEW_STUDENT;
          }
          break;

        case STATE_NEW_STUDENT:
          // New student sign up
          if (command.compareTo("newstudent-ok") == 0) {
            if (newStudentDialog.passwordsMatch()) {
              db.currentStudent = db.students.createStudent(newStudentDialog.getName(), newStudentDialog.getPassword());

              if (db.currentStudent != null) {
                // Update the value in the config file
                if (gconfig.settings[0] != null) {
                  gconfig.settings[0].lastStudent = newStudentDialog.getName();
                }

                // Update the value in the new xml config file
                String tmpname = newStudentDialog.getName();
                String tmppwd = newStudentDialog.getPassword();
                configxml.updateItem("systeminfo", "laststudent", tmpname);
                // configxml.addPersonalConfig(tmpname);
                // configxml.updateItem(tmpname, "pwd",tmppwd);
                configxml.addPersonalConfig(tmpname, tmppwd);

                // Load the config for this student
                config = new CALL_configStruct(db.currentStudent.id);

                // Hide the login dialog
                newStudentDialog.setVisible(false);

                // Display tile page
                intialiseMainPanel();
                initialize_frame();

                UIManager.put("Button.margin", new Insets(0, 0, 0, 0));

                // Create and add the main content panel
                mainPanel = new JPanel(new GridLayout(1, 1));
                add(mainPanel, BorderLayout.CENTER);

                // Add the title panel
                tp = new CALL_titlePanel(this);
                mainPanel.add(tp, BorderLayout.CENTER);

                validate();
                repaint();

                state = STATE_TITLE;
              } else {
                // 2011.03.06 T.Tajima add
                // to show what is wrong
                JOptionPane
                    .showMessageDialog(
                        null,
                        "Cannot create the new account.\nPlease check following points:\n  1. You can use a~z, A~Z, 0~9 and \"_\"(underscore) for Name.\n  2. Maybe the Name is already used.\nPlease try another Name.",
                        "FAILED", JOptionPane.ERROR_MESSAGE);
                logger.debug("Cannot create new student =" + newStudentDialog.getName());

                // Redisplay the dialog
                newStudentDialog.setVisible(false);
                newStudentDialog = new CALL_studentNewDialog(this);
                newStudentDialog.setLocationRelativeTo(this);
                newStudentDialog.setVisible(true);
              }
            } else {
              // Redisplay the dialog
              newStudentDialog.setVisible(false);
              newStudentDialog = new CALL_studentNewDialog(this);
              newStudentDialog.setLocationRelativeTo(this);
              newStudentDialog.setVisible(true);
            }
          } else if (command.compareTo("newstudent-cancel") == 0) {
            newStudentDialog.setVisible(false);

            loginDialog = new CALL_studentSelectDialog(this);
            loginDialog.setLocationRelativeTo(this);
            loginDialog.setVisible(true);

            // 2011.02.18 T.Tajima
            state = STATE_LOGIN;
          }
          break;

        case STATE_TITLE:
          // Title Screen
          if (command.compareTo("lesson") == 0) {
            // Clear the panel
            mainPanel.removeAll();

            // Add the Lesson panel
            lmp = new CALL_lessonMenuPanel(this);
            mainPanel.add(lmp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON_MENU;
          }

          else if (command.compareTo("progress") == 0) {
            // Clear the panel
            // mainPanel.removeAll();

            // Add the Progress panel
            // CALL_progressPanel pp = new CALL_progressPanel(this);
            // mainPanel.add(pp, BorderLayout.CENTER);
            // validate();

            // Update the state variable
            // state = STATE_PROGRESS;
          } else if (command.compareTo("exit") == 0) {
            cleanup();

            // 2011.01.14 T.Tajima
            // TODO
            // process.destroy() ;
            julian.MyStop();

            // Close application
            System.exit(0);
          } else if (command.compareTo("logoff") == 0) {
            // Save student data
            saveStudentData();
            db.currentStudent = null;

            // Hide main title screen
            frame.setVisible(false);
            removeAll();
            validate();

            // Display login dialog
            loginDialog = new CALL_studentSelectDialog(this);
            loginDialog.setLocationRelativeTo(this);
            loginDialog.setVisible(true);

            // Change state
            state = STATE_LOGIN;
          }

          break;

        case STATE_LESSON_MENU:
          // Lesson Menu (listing)
          if (command.compareTo("back") == 0) {
            mainPanel.removeAll();

            // Re-add the title panel
            tp = new CALL_titlePanel(this);
            mainPanel.add(tp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_TITLE;
          } else if (command.startsWith("start_lesson")) {
            lessonIndex = (Integer.decode(command.substring(13))).intValue();
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
            // "Selected lesson " + lessonIndex);

            logger.debug("Selected lesson " + lessonIndex);

            if (lessonIndex >= 0) {
              // ////////////////////////////////////////////////////////////////////////////////////////////////////////
              // //////////////////////////////////////
              String strType = configxml.getItemValue("systeminfo", "laststudent");
              String labelOpt = configxml.getItemValue(strType, "label");
              if (labelOpt != null) {
                logger.debug("Labeloption: " + labelOpt);
                if (labelOpt.equalsIgnoreCase(ConfigInstant.CONFIG_LabelOption_CH)) {
                  // String strType = configxml.getItemValue("systeminfo",
                  // "laststudent");
                  // configxml.updateItem(strType, "label",
                  // ConfigInstant.CONFIG_LabelOption_CH);

                  chCB.setSelected(true);
                  enCB.setSelected(false);
                } else {
                  chCB.setSelected(false);
                  enCB.setSelected(true);
                }

              } else {
                logger.error("Labeloption: NULL");

              }

              // We have a valid lesson
              mainPanel.removeAll();

              // Refresh the student's short term word experience (starts from 0
              // with each lesson)
              db.currentStudent.wordExperienceST = new CALL_lexiconExperienceStruct();

              // Go to the lesson menu (specific)
              lp = new CALL_lessonPanel(this, lessonIndex);
              mainPanel.add(lp, BorderLayout.CENTER);
              validate();

              // Update the state variable
              state = STATE_LESSON;
            }
          }

          break; // 2011.02.15 T.Tajima add

        case STATE_OPTION_MENU:
          // Option Menu
          break;

        case STATE_PROGRESS:
          // Progress Menu
          break;

        case STATE_LESSON:
          // Lesson Menu (Specific)
          if (command.compareTo("back") == 0) {
            mainPanel.removeAll();

            // Add the Lesson panel
            // lmp = new CALL_lessonMenuPanel(this);
            // 2011.02.15 T.Tajima removed ^
            // 2011.02.15 T.Tajima add(constructor) v
            lmp = new CALL_lessonMenuPanel(this, lessonIndex);
            mainPanel.add(lmp, BorderLayout.CENTER);
            validate();

            // No longer in a lesson
            lessonIndex = 0;

            // Update the state variable
            state = STATE_LESSON_MENU;
          } else if (command.compareTo("practise") == 0) {
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
            // "Practice started, lesson " + lessonIndex);
            // logger.debug("typing practice started, lesson " + lessonIndex);
            logger.debug("typing practice started, lesson " + lessonIndex);

            // Update the value in the new xml config file
            String str = configxml.getItemValue("systeminfo", "laststudent");
            configxml.updateItem(str, "input", "typing");

            // Go to the practise screen
            mainPanel.removeAll();

            // Go to the practice setup panel
            psp = new CALL_practiseSetupPanel(this, lessonIndex);
            mainPanel.add(psp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_PRACTICE_SETUP;
          }

          /*---------------------------------------------------
           * add one if condition by wang					 *
           */
          else if (command.compareTo("oralpractise") == 0) {

            // logger.debug("Oral practice started, lesson " + lessonIndex);
            logger.debug("Pressed oralpactise button, oral practice started, lesson " + lessonIndex);

            bOral = true;

            // Update the value in the new xml config file
            String str = configxml.getItemValue("systeminfo", "laststudent");
            configxml.updateItem(str, "input", "speech");

            // Go to the practise screen
            mainPanel.removeAll();

            // Go to the practice setup panel
            psp = new CALL_practiseSetupPanel(this, lessonIndex);
            mainPanel.add(psp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_PRACTICE_SETUP;

          } // -----------------------------------------------

          else if (command.compareTo("notes") == 0) {
            // Log usage of notes
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
            // "Notes viewed");

            // Go to the practise screen
            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lnp = new CALL_lessonNotesPanel(this, lessonIndex);
            mainPanel.add(lnp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_NOTES;
          } else if (command.compareTo("example") == 0) {
            // Log usage of notes
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
            // "Example viewed");

            // Go to the practise screen
            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lep = new CALL_examplePanel(this, lessonIndex);
            mainPanel.add(lep, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_EXAMPLE;
          }
          break;

        case STATE_NOTES:
          // Lesson Notes
          if (command.compareTo("back") == 0) {
            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          }
          break;
        case STATE_EXAMPLE:
          // Lesson Example
          if (command.compareTo("back") == 0) {
            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          }
          break;
        case STATE_PRACTICE_SETUP:
          if (command.compareTo("ok") == 0) {
            // Update the configuration settings
            if (psp != null) {
              psp.updateSettings();
            }

            // Go to the practise screen
            mainPanel.removeAll();

            // Update practice count (used for log naming purposes)
            practiceCount++;

            // Varies depending on the practices state
            if (practiceConfig != null) {
              if (practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_CONTEXT) {
                // ------changed by wang ---- add the else part-----------
                studentName = configxml.getItemValue("systeminfo", "laststudent");

                String strOralPrText = configxml.getItemValue(studentName, "input");
                if (strOralPrText.equalsIgnoreCase("typing")) {
                  logger.debug("typing ");
                  pp = new CALL_contextPractisePanel(this, lessonIndex);
                  mainPanel.add(pp, BorderLayout.CENTER);
                  validate();
                  state = STATE_PRACTICE_CONTEXT;

                } else if (strOralPrText.equalsIgnoreCase("speech")) {

                  logger.debug("speech ");
                  try {
                    cop = new JContextOralPractisePanel(this, lessonIndex);
                    Notifier notifier = Notifier.getNotifier();
                    notifier.addListener(cop);

                  } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    logger.debug("IOException: " + e1.toString());
                    e1.printStackTrace();
                  }
                  mainPanel.add(cop, BorderLayout.CENTER);
                  validate();
                  state = STATE_PRACTICE_CONTEXT;

                }

                // if(!bOral){
                // logger.debug("typing ");
                // pp = new CALL_contextPractisePanel(this, lessonIndex);
                // mainPanel.add(pp, BorderLayout.CENTER);
                // validate();
                // state = STATE_PRACTICE_CONTEXT;
                // } else
                // {
                // logger.debug("speech ");
                // try {
                // cop = new JContextOralPractisePanel(this, lessonIndex);
                // Notifier notifier = Notifier.getNotifier();
                // notifier.addListener(cop);
                //
                // } catch (IOException e1) {
                // // TODO Auto-generated catch block
                // logger.debug("IOException: "+ e1.toString());
                // e1.printStackTrace();
                // }
                // mainPanel.add(cop, BorderLayout.CENTER);
                // validate();
                // state = STATE_PRACTICE_CONTEXT;
                // }
              } else if (practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_VERB) {
                // pp = new CALL_verbPractisePanel(this, lessonIndex);
                // mainPanel.add(pp, BorderLayout.CENTER);
                // validate();
                // state = STATE_PRACTICE_VERB;

                // TEMP - go back to lesson menu
                lp = new CALL_lessonPanel(this, lessonIndex);
                mainPanel.add(lp, BorderLayout.CENTER);
                validate();

                // Update the state variable
                state = STATE_LESSON;
              } else if (practiceConfig.practiceType == CALL_practiceSetupStruct.PTYPE_VOCAB) {
                pp = new CALL_vocabPractisePanel(this, lessonIndex);
                mainPanel.add(pp, BorderLayout.CENTER);
                validate();
                state = STATE_PRACTICE_VOCAB;
              }
            } else {
              // No practice config! ERROR
              state = STATE_ERROR;
            }
          } else if (command.compareTo("back") == 0) {
            // Back to lesson information
            mainPanel.removeAll();
            /*
             * add by wang
             */
            bOral = false;

            // Go to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          }
          break;
        case STATE_PRACTICE_CONTEXT:
          // Lesson Practise
          if (command.compareTo("quit") == 0) {
            // Log quiting of lesson
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
            // "Lesson Quit");

            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          } else if (command.compareTo("finish") == 0) {
            mainPanel.removeAll();

            // WOULD ACTUALLY GO TO RESULTS HERE - CJW
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
            // "Lesson Ends");

            // Go back to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          } else if (command.compareTo("textanswer") == 0) {
            mainPanel.removeAll();

            // THIS IS JUST TO END THE SESSION AT END OF EXAMPLES...

            // Go back to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          } else if (command.compareTo("notes") == 0) {
            // Pop up the notes dialog box
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.MISC,
            // "Notes viewed (dialog)");
            lnd = new CALL_lessonNotesDialog(this, lessonIndex);
            lnd.setLocationRelativeTo(this);
            lnd.setVisible(true);
          }
          break;

        case STATE_PRACTICE_VOCAB:
          // Lesson Practise
          if (command.compareTo("quit") == 0) {
            // Log quiting of lesson
            // CALL_debug.printlog(CALL_debug.MOD_USAGE, CALL_debug.FLOW,
            // "Vocab Practice Quit");

            mainPanel.removeAll();

            // Go to the lesson menu (specific)
            lp = new CALL_lessonPanel(this, lessonIndex);
            mainPanel.add(lp, BorderLayout.CENTER);
            validate();

            // Update the state variable
            state = STATE_LESSON;
          }
          break;

        case STATE_TEST:
          // Lesson Test
          break;

        case STATE_ERROR:
          // We have had an error
          // Assume the error has been logged previously, just shutdown here
          cleanup();
          System.exit(0);
          break;

        default:
          break;
      }
    }
  }

  // Act on change of state (Check boxes etc)
  // ////////////////////////////////////////////////////
  public void itemStateChanged(ItemEvent e) {
    validate();
  }

  public void start() {
  }

  public void stop() {
  }

  public static void initialize_frame() {
    // Clear the progress bar stuff
    frame.getContentPane().removeAll();

    // Set the icon - nice if we could do this for the progress bar too...ah
    // well
    frame.setIconImage(CALL_images.getInstance().getImage("Misc/about.jpg"));
    frame.setResizable(false);
    frame.setVisible(true);

    // Add the new panel (callMain)
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(callMain, BorderLayout.CENTER);

    frame.setLocation(d.width / 2 - width / 2, d.height / 2 - height / 2);
    frame.setSize(width, height);
    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    frame.validate();
  }

  // The MAIN function - this is where it starts
  // ////////////////////////////////////////////////////
  public static void main(String args[]) throws IOException {

    // System.out.println("Enter main class");

    // PropertyConfigurator.configure("log4j.properties");
    int WIDTH = 350, HEIGHT = 100;
    Image call_icon;

    // Set the look of the UI
    try {
      // UIManager.setLookAndFeel(
      // "com.sun.java.swing.plaf.motif.MotifLookAndFeel" );
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (Exception e) {
      // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
      // "Failed to set look of UI - using defaults");
    }

    // Create our starting frame
    frame = new JFrame("CallJ");
    frame.setSize(WIDTH, HEIGHT);
    d = Toolkit.getDefaultToolkit().getScreenSize();

    frame.setLocation(d.width / 2 - WIDTH / 2, d.height / 2 - HEIGHT / 2);
    frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if (callMain != null) {
          callMain.cleanup();
          // 2011.02.28 T.Tajima add
          // TODO
          // callMain.process.destroy() ;
          callMain.julian.MyStop();
        }
        System.exit(0);
      }

      public void windowDeiconified(WindowEvent e) {
        if (callMain != null) {
          callMain.start();
        }
      }

      public void windowIconified(WindowEvent e) {
        if (callMain != null) {
          callMain.stop();
        }
      }
    });

    JOptionPane.setRootFrame(frame);

    JPanel progressPanel = new JPanel();
    progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
    frame.getContentPane().add(progressPanel, BorderLayout.CENTER);

    Dimension labelSize = new Dimension(WIDTH, 20);
    progressLabel = new JLabel("Loading, please wait...");
    progressLabel.setAlignmentX(CENTER_ALIGNMENT);
    progressLabel.setMaximumSize(labelSize);
    progressLabel.setPreferredSize(labelSize);
    progressPanel.add(progressLabel);
    progressPanel.add(Box.createRigidArea(new Dimension(1, 20)));

    progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressLabel.setLabelFor(progressBar);
    progressBar.setAlignmentX(CENTER_ALIGNMENT);
    progressBar.setMinimum(0);
    progressBar.setValue(0);
    progressBar.getAccessibleContext().setAccessibleName("Java2D loading progress");
    progressPanel.add(progressBar);

    // 2011.01.06 T.Tajima add
    if (args.length < 1 || !args[0].equals("--noJulian")) {
      julian.start();
      System.out.println("AFTER JULIAN START");
    }

    callMain = new CALL_main();

  }

  // ==========================================================================================
  public void logExampleSentence(int lessonIndex, int nQuestions, int nSets) throws IOException {

    CALL_questionStruct currentQuestion;
    CALL_lessonStruct lesson;
    FileWriter logfile;
    PrintWriter logOut;
    int index;

    System.out.println("Running the Example Question debug routine..");

    for (int s = 0; s < nSets; s++) {
      // We're not using the logfile
      try {
        logfile = new FileWriter("example_sentences" + (s + 1) + ".txt", false);
        logOut = new PrintWriter(logfile);
      } catch (IOException e) {
        // Error
        return;
      }

      if (logOut != null) {
        if (lessonIndex == -1) {
          for (int i = 0; i < db.lessons.numberLessons(); i++) {
            // Get lesson information
            lesson = db.lessons.getLesson(i);
            if (lesson != null) {
              if (lesson.lessonActive()) {
                index = lesson.index;
                logOut.println("Lesson " + index + " Examples:");
                logOut.println("===================");

                for (int q = 0; q < nQuestions; q++) {
                  currentQuestion = new CALL_questionStruct(db, config.settings[index], config.settings[0], lesson,
                      CALL_questionStruct.QTYPE_CONTEXT);
                  logOut.println("" + (q + 1) + ") Kanji Sentence: "
                      + currentQuestion.sentence.getSentenceString(CALL_io.kanji));
                  logOut.println("" + (q + 1) + ") Kana Sentence: "
                      + currentQuestion.sentence.getSentenceString(CALL_io.kana));
                  logOut.println("" + (q + 1) + ") Romaji Sentence: "
                      + currentQuestion.sentence.getSentenceString(CALL_io.romaji));
                  logOut.println("" + (q + 1) + ") English Concept: " + currentQuestion.instance.getInstanceString());
                  logOut.println("" + (q + 1) + ") Form: " + currentQuestion.getFormString());
                  logOut.println(" ");
                }

                // Lesson spacing
                logOut.println(" ");
                logOut.println("============================ ");
                logOut.println(" ");
              }
            }
          }
        } else {
          // Get lesson information
          lesson = db.lessons.getLesson(lessonIndex);
          if (lesson != null) {
            index = lesson.index;
            logOut.println("Lesson " + index + " Examples:");
            logOut.println("===================");

            for (int q = 0; q < nQuestions; q++) {
              currentQuestion = new CALL_questionStruct(db, config.settings[index], config.settings[0], lesson,
                  CALL_questionStruct.QTYPE_CONTEXT);
              logOut.println("" + (q + 1) + ") Japanese Sentence: "
                  + currentQuestion.sentence.getSentenceString(CALL_io.kanji));
              logOut.println("" + (q + 1) + ") English Concept: " + currentQuestion.instance.getInstanceString());
              logOut.println(" ");
            }
          }
        }

        // Close the logfile
        logOut.close();
        try {
          logfile.close();
        } catch (IOException e) {
          // Ignore for now
        }
      }

    } // if(logOut != null)
  }
}
