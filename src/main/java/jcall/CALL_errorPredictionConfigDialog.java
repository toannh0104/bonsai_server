///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CALL_errorPredictionConfigDialog extends JDialog implements ItemListener {
  static final int WP_TOP = 40;
  static final int EP_TOP = 40;

  // Tabs
  JTabbedPane tabbedPane;

  // Panels
  JPanel mainPanel;
  JPanel wordPanel;
  JPanel errorPanel;
  JPanel buttonPanel;

  // Borders
  TitledBorder wordPanelBorder;
  TitledBorder errorPanelBorder;
  Border loweredetched;

  // Labels
  JLabel shortBiasL;
  JLabel mediumBiasL;
  JLabel longBiasL;
  JLabel wordImpactL;
  JLabel learningRateL;
  JLabel errorSTL;
  JLabel errorLTL;

  // Slider
  JSlider wordImpactS;
  JSlider learningRateS;
  JSlider shortBiasS;
  JSlider mediumBiasS;
  JSlider longBiasS;

  JSlider errorSTS;
  JSlider errorLTS;

  // Small labels
  JLabel shortBiasLL;
  JLabel mediumBiasLL;
  JLabel longBiasLL;
  JLabel learningRateLL;
  JLabel wordImpactLL;
  JLabel errorSTLL;
  JLabel errorLTLL;
  JLabel shortBiasRL;
  JLabel mediumBiasRL;
  JLabel longBiasRL;
  JLabel learningRateRL;
  JLabel wordImpactRL;
  JLabel errorSTRL;
  JLabel errorLTRL;

  // Check Box
  JCheckBox useWordExperienceCB;

  // Buttons
  JButton OKButton;
  JButton cancelButton;

  // Values
  boolean useWordExp;
  int wordImpact;
  int shortBias;
  int mediumBias;
  int longBias;
  int learningRate;
  int errorST;
  int errorLT;

  public CALL_errorPredictionConfigDialog(CALL_main parent) {
    int width = 600;
    int height = 450;
    CALL_configDataStruct cfg;

    // These defaults will hopefully never be used
    shortBias = 50;
    mediumBias = 50;
    longBias = 50;
    learningRate = 50;
    errorST = 50;
    errorLT = 50;
    wordImpact = 50;
    useWordExp = true;

    setSize(width, height);
    setTitle("Perceived Difficulty - Parameters");
    setResizable(false);

    cfg = parent.config.settings[0];

    // Temp border
    loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // Configure the panels
    // ================================================-
    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setLayout(null);

    // The tabbed pane
    tabbedPane = new JTabbedPane();
    tabbedPane.setBounds(0, 0, 600, 380);
    mainPanel.add(tabbedPane);

    wordPanel = new JPanel(new BorderLayout());
    wordPanel.setLayout(null);
    wordPanel.setBounds(5, 5, 590, 370);
    wordPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Impact of Word Experience");
    wordPanelBorder.setTitleJustification(TitledBorder.RIGHT);
    wordPanel.setBorder(wordPanelBorder);

    errorPanel = new JPanel(new BorderLayout());
    errorPanel.setLayout(null);
    errorPanel.setBounds(5, 5, 590, 370);
    errorPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Impact of Student's Error History");
    errorPanelBorder.setTitleJustification(TitledBorder.RIGHT);
    errorPanel.setBorder(errorPanelBorder);

    buttonPanel = new JPanel();
    buttonPanel.setLayout(null);
    buttonPanel.setBounds(300, 385, 300, 50);

    if (cfg != null) {
      wordImpact = cfg.hintWordLearningImpact;
      shortBias = cfg.hintWordLearningBiasST;
      mediumBias = cfg.hintWordLearningBiasMT;
      longBias = cfg.hintWordLearningBiasLT;
      learningRate = cfg.hintWordLearningRate;
      errorST = cfg.hintErrorSTImpact;
      errorLT = cfg.hintErrorLTImpact;
      useWordExp = cfg.useWordExperience;
    }

    // Sliders
    // ================================================-

    // WORD EXPERIENCE PANEL
    // ============================

    shortBiasL = new JLabel("Short Term:");
    shortBiasL.setBounds(315, WP_TOP, 200, 20);
    wordPanel.add(shortBiasL);

    shortBiasRL = new JLabel("High", JLabel.RIGHT);
    shortBiasRL.setFont(new Font("serif", Font.PLAIN, 10));
    shortBiasRL.setBounds(495, WP_TOP + 65, 75, 14);
    wordPanel.add(shortBiasRL);

    shortBiasLL = new JLabel("Low", JLabel.LEFT);
    shortBiasLL.setFont(new Font("serif", Font.PLAIN, 10));
    shortBiasLL.setBounds(330, WP_TOP + 65, 75, 14);
    wordPanel.add(shortBiasLL);

    shortBiasS = new JSlider(JSlider.HORIZONTAL, 0, 100, shortBias);
    shortBiasS.setMajorTickSpacing(10);
    shortBiasS.setMinorTickSpacing(5);
    shortBiasS.setBounds(325, WP_TOP + 25, 250, 45);
    shortBiasS.setPaintTicks(true);
    wordPanel.add(shortBiasS);

    mediumBiasL = new JLabel("Medium Term:");
    mediumBiasL.setBounds(315, WP_TOP + 90, 200, 20);
    wordPanel.add(mediumBiasL);

    mediumBiasRL = new JLabel("High", JLabel.RIGHT);
    mediumBiasRL.setFont(new Font("serif", Font.PLAIN, 10));
    mediumBiasRL.setBounds(495, WP_TOP + 155, 75, 14);
    wordPanel.add(mediumBiasRL);

    mediumBiasLL = new JLabel("Low", JLabel.LEFT);
    mediumBiasLL.setFont(new Font("serif", Font.PLAIN, 10));
    mediumBiasLL.setBounds(330, WP_TOP + 155, 75, 14);
    wordPanel.add(mediumBiasLL);

    mediumBiasS = new JSlider(JSlider.HORIZONTAL, 0, 100, mediumBias);
    mediumBiasS.setMajorTickSpacing(10);
    mediumBiasS.setMinorTickSpacing(5);
    mediumBiasS.setBounds(325, WP_TOP + 115, 250, 45);
    mediumBiasS.setPaintTicks(true);
    wordPanel.add(mediumBiasS);

    longBiasL = new JLabel("Long Term:");
    longBiasL.setBounds(315, WP_TOP + 180, 200, 20);
    wordPanel.add(longBiasL);

    longBiasRL = new JLabel("High", JLabel.RIGHT);
    longBiasRL.setFont(new Font("serif", Font.PLAIN, 10));
    longBiasRL.setBounds(495, WP_TOP + 245, 75, 14);
    wordPanel.add(longBiasRL);

    longBiasLL = new JLabel("Low", JLabel.LEFT);
    longBiasLL.setFont(new Font("serif", Font.PLAIN, 10));
    longBiasLL.setBounds(330, WP_TOP + 245, 75, 14);
    wordPanel.add(longBiasLL);

    longBiasS = new JSlider(JSlider.HORIZONTAL, 0, 100, longBias);
    longBiasS.setMajorTickSpacing(10);
    longBiasS.setMinorTickSpacing(5);
    longBiasS.setBounds(325, WP_TOP + 205, 250, 45);
    longBiasS.setPaintTicks(true);
    wordPanel.add(longBiasS);

    wordImpactL = new JLabel("Experience Impact:");
    wordImpactL.setBounds(15, WP_TOP, 200, 20);
    wordPanel.add(wordImpactL);

    wordImpactRL = new JLabel("High", JLabel.RIGHT);
    wordImpactRL.setFont(new Font("serif", Font.PLAIN, 10));
    wordImpactRL.setBounds(195, WP_TOP + 65, 75, 14);
    wordPanel.add(wordImpactRL);

    wordImpactLL = new JLabel("Low", JLabel.LEFT);
    wordImpactLL.setFont(new Font("serif", Font.PLAIN, 10));
    wordImpactLL.setBounds(30, WP_TOP + 65, 75, 14);
    wordPanel.add(wordImpactLL);

    wordImpactS = new JSlider(JSlider.HORIZONTAL, 0, 100, wordImpact);
    wordImpactS.setMajorTickSpacing(10);
    wordImpactS.setMinorTickSpacing(5);
    wordImpactS.setBounds(25, WP_TOP + 25, 250, 45);
    wordImpactS.setPaintTicks(true);
    wordPanel.add(wordImpactS);

    learningRateL = new JLabel("Adaptation Rate:");
    learningRateL.setBounds(15, WP_TOP + 90, 200, 20);
    wordPanel.add(learningRateL);

    learningRateRL = new JLabel("High", JLabel.RIGHT);
    learningRateRL.setFont(new Font("serif", Font.PLAIN, 10));
    learningRateRL.setBounds(195, WP_TOP + 155, 75, 14);
    wordPanel.add(learningRateRL);

    learningRateLL = new JLabel("Low", JLabel.LEFT);
    learningRateLL.setFont(new Font("serif", Font.PLAIN, 10));
    learningRateLL.setBounds(30, WP_TOP + 155, 75, 14);
    wordPanel.add(learningRateLL);

    learningRateS = new JSlider(JSlider.HORIZONTAL, 0, 100, learningRate);
    learningRateS.setMajorTickSpacing(10);
    learningRateS.setMinorTickSpacing(5);
    learningRateS.setBounds(25, WP_TOP + 115, 250, 45);
    learningRateS.setPaintTicks(true);
    wordPanel.add(learningRateS);

    useWordExperienceCB = new JCheckBox("Use Word Experience");
    useWordExperienceCB.setBounds(25, WP_TOP + 205, 200, 20);
    useWordExperienceCB.setSelected(useWordExp);
    useWordExperienceCB.addItemListener(this);
    wordPanel.add(useWordExperienceCB);

    // PAST ERRORS PANEL
    // ================================
    errorSTL = new JLabel("Short Term:");
    errorSTL.setBounds(15, EP_TOP, 200, 20);
    errorPanel.add(errorSTL);

    errorSTRL = new JLabel("High", JLabel.RIGHT);
    errorSTRL.setFont(new Font("serif", Font.PLAIN, 10));
    errorSTRL.setBounds(195, EP_TOP + 65, 75, 14);
    errorPanel.add(errorSTRL);

    errorSTLL = new JLabel("Low", JLabel.LEFT);
    errorSTLL.setFont(new Font("serif", Font.PLAIN, 10));
    errorSTLL.setBounds(30, EP_TOP + 65, 75, 14);
    errorPanel.add(errorSTLL);

    errorSTS = new JSlider(JSlider.HORIZONTAL, 0, 100, errorST);
    errorSTS.setMajorTickSpacing(10);
    errorSTS.setMinorTickSpacing(5);
    errorSTS.setBounds(25, EP_TOP + 25, 250, 45);
    errorSTS.setPaintTicks(true);
    errorPanel.add(errorSTS);

    errorLTL = new JLabel("Long Term:");
    errorLTL.setBounds(15, EP_TOP + 90, 200, 20);
    errorPanel.add(errorLTL);

    errorLTRL = new JLabel("High", JLabel.RIGHT);
    errorLTRL.setFont(new Font("serif", Font.PLAIN, 10));
    errorLTRL.setBounds(195, EP_TOP + 155, 75, 14);
    errorPanel.add(errorLTRL);

    errorLTLL = new JLabel("Low", JLabel.LEFT);
    errorLTLL.setFont(new Font("serif", Font.PLAIN, 10));
    errorLTLL.setBounds(30, EP_TOP + 155, 75, 14);
    errorPanel.add(errorLTLL);

    errorLTS = new JSlider(JSlider.HORIZONTAL, 0, 100, errorLT);
    errorLTS.setMajorTickSpacing(10);
    errorLTS.setMinorTickSpacing(5);
    errorLTS.setBounds(25, EP_TOP + 115, 250, 45);
    errorLTS.setPaintTicks(true);
    errorPanel.add(errorLTS);

    // Buttons
    // ================================================-
    OKButton = new JButton("Ok");
    OKButton.setBounds(130, 0, 75, 30);
    OKButton.setActionCommand("errorPredictionConfig-ok");
    OKButton.addActionListener(parent);
    buttonPanel.add(OKButton);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(210, 0, 75, 30);
    cancelButton.setActionCommand("errorPredictionConfig-cancel");
    cancelButton.addActionListener(parent);
    buttonPanel.add(cancelButton);

    // Add panels
    mainPanel.add(buttonPanel);
    tabbedPane.addTab("Word Experience", null, wordPanel, "The impact of word experience");
    tabbedPane.addTab("Past Errors", null, errorPanel, "The impact of previous errors");

    getContentPane().add(mainPanel);

    // Exit program if this is closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Finally, update whether components are enabled or not based on settings
    updateComponents();
  }

  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    if (source == useWordExperienceCB) {
      // Invert the costCalibration option
      if (useWordExp) {
        useWordExp = false;
      } else {
        useWordExp = true;
      }
    }

    // And update the components
    updateComponents();
  }

  public void updateComponents() {
    if (useWordExp) {
      // Labels
      shortBiasL.setEnabled(true);
      mediumBiasL.setEnabled(true);
      longBiasL.setEnabled(true);
      wordImpactL.setEnabled(true);
      learningRateL.setEnabled(true);

      // Slider
      wordImpactS.setEnabled(true);
      learningRateS.setEnabled(true);
      shortBiasS.setEnabled(true);
      mediumBiasS.setEnabled(true);
      longBiasS.setEnabled(true);

      // Small labels
      shortBiasLL.setEnabled(true);
      mediumBiasLL.setEnabled(true);
      longBiasLL.setEnabled(true);
      learningRateLL.setEnabled(true);
      wordImpactLL.setEnabled(true);
      shortBiasRL.setEnabled(true);
      mediumBiasRL.setEnabled(true);
      longBiasRL.setEnabled(true);
      learningRateRL.setEnabled(true);
      wordImpactRL.setEnabled(true);
    } else {
      // Labels
      shortBiasL.setEnabled(false);
      mediumBiasL.setEnabled(false);
      longBiasL.setEnabled(false);
      wordImpactL.setEnabled(false);
      learningRateL.setEnabled(false);

      // Slider
      wordImpactS.setEnabled(false);
      learningRateS.setEnabled(false);
      shortBiasS.setEnabled(false);
      mediumBiasS.setEnabled(false);
      longBiasS.setEnabled(false);

      // Small labels
      shortBiasLL.setEnabled(false);
      mediumBiasLL.setEnabled(false);
      longBiasLL.setEnabled(false);
      learningRateLL.setEnabled(false);
      wordImpactLL.setEnabled(false);
      shortBiasRL.setEnabled(false);
      mediumBiasRL.setEnabled(false);
      longBiasRL.setEnabled(false);
      learningRateRL.setEnabled(false);
      wordImpactRL.setEnabled(false);
    }

    validate();
  }

}