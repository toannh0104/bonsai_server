///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class CALL_hintCostConfigDialog extends JDialog implements ItemListener {
  // Panels
  JPanel mainPanel;
  JPanel buttonPanel;

  // Labels
  JLabel levelBiasL;
  JLabel grammarBiasL;
  JLabel costCalibrationL;

  // Sliders
  JSlider levelBiasS;
  JSlider grammarBiasS;

  // Small labels
  JLabel levelBiasLL;
  JLabel levelBiasRL;
  JLabel grammarBiasLL;
  JLabel grammarBiasRL;

  // Check Box
  JCheckBox costCalibrationCB;
  JCheckBox costEnabledCB;

  // Buttons
  JButton OKButton;
  JButton cancelButton;

  // Values
  int levelBias;
  int grammarBias;
  boolean costCalibration;
  boolean costEnabled;

  public CALL_hintCostConfigDialog(CALL_main parent) {
    Insets insets;
    int width = 300;
    int height = 400;
    CALL_configDataStruct cfg;

    // These defaults will hopefully never be used
    levelBias = 50;
    grammarBias = 50;
    costCalibration = false;
    costEnabled = false;

    setSize(width, height);
    setTitle("Score System");
    setResizable(false);

    cfg = parent.config.settings[0];

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.setLayout(null);
    insets = mainPanel.getInsets();

    buttonPanel = new JPanel();
    buttonPanel.setLayout(null);
    buttonPanel.setBounds(0, 335, 300, 50);

    if (cfg != null) {
      levelBias = cfg.hintLevelBias;
      grammarBias = cfg.hintGrammarBias;
      costEnabled = cfg.hintCostEnabled;

      if (cfg.hintCostAutoBalance != 0) {
        costCalibration = true;
      } else {
        costCalibration = false;
      }
    }

    // Sliders
    levelBiasL = new JLabel("Component vs Category Bias:");
    levelBiasL.setBounds(15, 10, 200, 20);
    mainPanel.add(levelBiasL);

    levelBiasRL = new JLabel("Category", JLabel.RIGHT);
    levelBiasRL.setFont(new Font("serif", Font.PLAIN, 10));
    levelBiasRL.setBounds(195, 75, 75, 14);
    mainPanel.add(levelBiasRL);

    levelBiasLL = new JLabel("Component", JLabel.LEFT);
    levelBiasLL.setFont(new Font("serif", Font.PLAIN, 10));
    levelBiasLL.setBounds(30, 75, 75, 14);
    mainPanel.add(levelBiasLL);

    levelBiasS = new JSlider(JSlider.HORIZONTAL, 0, 100, levelBias);
    levelBiasS.setMajorTickSpacing(10);
    levelBiasS.setMinorTickSpacing(5);
    levelBiasS.setBounds(25, 35, 250, 45);
    levelBiasS.setPaintTicks(true);
    mainPanel.add(levelBiasS);

    grammarBiasL = new JLabel("Communication vs Grammar Bias:");
    grammarBiasL.setBounds(15, 100, 200, 20);
    mainPanel.add(grammarBiasL);

    grammarBiasRL = new JLabel("Grammar", JLabel.RIGHT);
    grammarBiasRL.setFont(new Font("serif", Font.PLAIN, 10));
    grammarBiasRL.setBounds(195, 165, 75, 14);
    mainPanel.add(grammarBiasRL);

    grammarBiasLL = new JLabel("Communication", JLabel.LEFT);
    grammarBiasLL.setFont(new Font("serif", Font.PLAIN, 10));
    grammarBiasLL.setBounds(30, 165, 75, 14);
    mainPanel.add(grammarBiasLL);

    grammarBiasS = new JSlider(JSlider.HORIZONTAL, 0, 100, grammarBias);
    grammarBiasS.setMajorTickSpacing(10);
    grammarBiasS.setMinorTickSpacing(5);
    grammarBiasS.setBounds(25, 125, 250, 45);
    grammarBiasS.setPaintTicks(true);
    mainPanel.add(grammarBiasS);

    // Check boxes
    costCalibrationCB = new JCheckBox("Score auto-calibration");
    costCalibrationCB.setBounds(25, 230, 200, 20);
    costCalibrationCB.setSelected(costCalibration);
    costCalibrationCB.addItemListener(this);
    mainPanel.add(costCalibrationCB);

    costEnabledCB = new JCheckBox("Enable Scoring");
    costEnabledCB.setBounds(25, 280, 200, 20);
    costEnabledCB.setSelected(costEnabled);
    costEnabledCB.addItemListener(this);
    mainPanel.add(costEnabledCB);

    // Buttons
    OKButton = new JButton("Ok");
    OKButton.setBounds(130, 0, 75, 30);
    OKButton.setActionCommand("hintCostConfig-ok");
    OKButton.addActionListener(parent);
    buttonPanel.add(OKButton);

    cancelButton = new JButton("Cancel");
    cancelButton.setBounds(210, 0, 75, 30);
    cancelButton.setActionCommand("hintCostConfig-cancel");
    cancelButton.addActionListener(parent);
    buttonPanel.add(cancelButton);

    // Add panels
    mainPanel.add(buttonPanel);

    getContentPane().add(mainPanel);

    // Set components enabled / disables as appropriate
    updateComponents();

    // Exit program if this is closed
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void updateComponents() {
    levelBiasL.setEnabled(costEnabled);
    levelBiasRL.setEnabled(costEnabled);
    levelBiasLL.setEnabled(costEnabled);
    levelBiasS.setEnabled(costEnabled);

    grammarBiasL.setEnabled(costEnabled);
    grammarBiasRL.setEnabled(costEnabled);
    grammarBiasLL.setEnabled(costEnabled);
    grammarBiasS.setEnabled(costEnabled);

    costCalibrationCB.setEnabled(costEnabled);

    validate();
  }

  public void itemStateChanged(ItemEvent e) {
    Object source = e.getItemSelectable();
    if (source == costCalibrationCB) {
      // Invert the costCalibration option
      if (costCalibration) {
        costCalibration = false;
      } else {
        costCalibration = true;
      }
    } else if (source == costEnabledCB) {
      // Invert the costCalibration option
      if (costEnabled) {
        costEnabled = false;
      } else {
        costEnabled = true;
      }
    }

    // And update the components
    updateComponents();
  }
}