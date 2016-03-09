///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

public class CALL_feedbackDialog extends JDialog implements ActionListener {
  static Logger logger = Logger.getLogger(CALL_feedbackDialog.class.getName());

  // Statics
  static final int AVG_SENTENCE_WORDS = 5; // Typical sentence length...doesn't
                                           // need to be precise, just scales
                                           // things
  static final int HAPPINESS_COEFICIENT = 6;

  private static final String errorSignificanceStrings[] = { "A critical error", "An important error",
      "A moderate error", "A minor error", "A trivial error" };

  JPanel mainPanel;

  // Sub panels
  JPanel basicPanel;
  JPanel detailsPanel;

  // Borders
  Border loweredetched;
  TitledBorder basicPanelBorder;
  TitledBorder detailsPanelBorder;

  // Buttons
  JButton OKButton;

  // Titles
  JLabel targetT;
  JLabel observedT;
  JLabel costT;
  JLabel typeT;
  JLabel subTypeT;
  JLabel componentT;
  JLabel spellingT;
  JLabel detailsT;

  // Details
  JLabel targetD;
  JLabel observedD;
  JLabel costD;
  JLabel typeD;
  JLabel subTypeD;
  JLabel componentD;
  JLabel spellingD;

  // Icon Labels
  JLabel costI;
  JLabel typeI;
  JLabel componentI;
  JLabel spellingI;

  JTextArea detailsD;
  JScrollPane detailsSP;

  double errorSignificance;

  // A dialog to give feedback on an error
  // Should be edited to take error object (which includes information)
  // This information should be taken from practicePanel's error store (for the
  // question)
  // But this structure is not yet implemented - CJW!
  public CALL_feedbackDialog(CALL_main parent, CALL_errorPairStruct error, double costScaling, int totalPoints,
      int numWords) {

    logger.info("Enter CALL_feedbackDialog; costScaling: " + costScaling + " totalPoints: " + totalPoints
        + " numWords: " + numWords);

    int width = 500;
    int height = 400;
    int cost;
    errorSignificance = 0.0;

    int happiness;
    ImageIcon smileyFaceIcon;
    ImageIcon errorTypeIcon;
    ImageIcon componentTypeIcon;
    ImageIcon spellingIcon;
    String filename;
    String subErrorString;
    String helpTextArray[];
    String helpText;

    setSize(width, height);
    setTitle("Error Feedback");
    setResizable(false);

    // The main panel on which all is placed
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setBackground(Color.WHITE);

    /* Basic error information Panel */
    basicPanel = new JPanel();
    basicPanel.setLayout(null);
    basicPanel.setOpaque(false);
    basicPanel.setBounds(5, 10, 490, 210);
    basicPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Basic Error Information");
    basicPanelBorder.setTitleJustification(TitledBorder.LEFT);
    basicPanel.setBorder(basicPanelBorder);
    mainPanel.add(basicPanel);

    /* Details and advice Panel */
    detailsPanel = new JPanel();
    detailsPanel.setLayout(null);
    detailsPanel.setOpaque(false);
    detailsPanel.setBounds(5, 227, 490, 95);
    detailsPanelBorder = BorderFactory.createTitledBorder(loweredetched, "Details");
    detailsPanelBorder.setTitleJustification(TitledBorder.LEFT);
    detailsPanel.setBorder(detailsPanelBorder);
    mainPanel.add(detailsPanel);

    // Ok button
    OKButton = new JButton("OK");
    OKButton.setBounds(230, 330, 65, 40);
    OKButton.setActionCommand("ok");
    OKButton.addActionListener(this);
    mainPanel.add(OKButton);

    // Prepare the feedback information
    if (error != null) {
      logger.info("CALL_errorPairStruct(error) is not null");
      logger.info("error.targetString: " + error.targetString);
      logger.info("error.observedString: " + error.observedString);
      logger.info("Error Type: " + error.errorTypeString());
      logger.info("Component: " + error.errorTargetComponentString());

      cost = (int) ((double) error.getErrorCost() * costScaling);
      errorSignificance = (double) ((cost * 100) / totalPoints);

      // Target Word
      targetT = new JLabel("Target: ");
      targetT.setBounds(15, 25, 95, 20);
      targetT.setHorizontalAlignment(JLabel.LEFT);
      targetT.setFont(new Font("serif", Font.BOLD, 16));
      targetT.setForeground(Color.BLACK);
      basicPanel.add(targetT);

      targetD = new JLabel(error.targetString);
      targetD.setBounds(110, 25, 350, 20);
      targetD.setHorizontalAlignment(JLabel.LEFT);
      targetD.setFont(new Font("serif", Font.PLAIN, 16));
      targetD.setForeground(Color.BLACK);
      basicPanel.add(targetD);

      // Observed Word
      observedT = new JLabel("Observed: ");
      observedT.setBounds(15, 55, 95, 20);
      observedT.setHorizontalAlignment(JLabel.LEFT);
      observedT.setFont(new Font("serif", Font.BOLD, 16));
      observedT.setForeground(Color.BLACK);
      basicPanel.add(observedT);

      observedD = new JLabel(error.observedString);
      observedD.setBounds(110, 55, 350, 20);
      observedD.setHorizontalAlignment(JLabel.LEFT);
      observedD.setFont(new Font("serif", Font.PLAIN, 16));
      observedD.setForeground(Color.BLACK);
      basicPanel.add(observedD);

      // Error Type
      typeT = new JLabel("Error Type: ");
      typeT.setBounds(15, 85, 95, 20);
      typeT.setHorizontalAlignment(JLabel.LEFT);
      typeT.setFont(new Font("serif", Font.BOLD, 16));
      typeT.setForeground(Color.BLACK);
      basicPanel.add(typeT);

      typeD = new JLabel(error.errorTypeString());
      typeD.setBounds(110, 85, 350, 20);
      typeD.setHorizontalAlignment(JLabel.LEFT);
      typeD.setFont(new Font("serif", Font.PLAIN, 16));
      typeD.setForeground(Color.BLACK);
      basicPanel.add(typeD);

      // Error type icon
      errorTypeIcon = CALL_images.getInstance().getIcon(error.errorTypeIcon());

      // errorTypeIcon = CALL_images(
      if (errorTypeIcon != null) {
        typeI = new JLabel(errorTypeIcon);
        typeI.setToolTipText(error.errorTypeString());
        typeI.setBounds(440, 70, 50, 50);
        basicPanel.add(typeI);
      }

      // Component Type
      typeT = new JLabel("Component: ");
      typeT.setBounds(15, 115, 95, 20);
      typeT.setHorizontalAlignment(JLabel.LEFT);
      typeT.setFont(new Font("serif", Font.BOLD, 16));
      typeT.setForeground(Color.BLACK);
      basicPanel.add(typeT);

      typeD = new JLabel(error.errorTargetComponentString());
      typeD.setBounds(110, 115, 350, 20);
      typeD.setHorizontalAlignment(JLabel.LEFT);
      typeD.setFont(new Font("serif", Font.PLAIN, 16));
      typeD.setForeground(Color.BLACK);
      basicPanel.add(typeD);

      // Component type icon
      componentTypeIcon = CALL_images.getInstance().getIcon(error.componentTypeIcon());
      if (componentTypeIcon != null) {
        componentI = new JLabel(componentTypeIcon);
        componentI.setToolTipText(error.errorTargetComponentString());
        componentI.setBounds(440, 100, 50, 50);
        basicPanel.add(componentI);
      }

      // Spelling Probability
      // spellingT = new JLabel("Typing: ");
      // spellingT.setBounds(15,145,95,20);
      // spellingT.setHorizontalAlignment(JLabel.LEFT);
      // spellingT.setFont(new Font("serif", Font.BOLD, 16));
      // spellingT.setForeground(Color.BLACK);
      // basicPanel.add(spellingT);
      //
      // if(error.spellingErrorLikelihood > 0.7)
      // {
      // spellingD = new JLabel("Probably a typing mistake");
      // spellingIcon= CALL_images.getInstance().getIcon("Misc/yesIcon.gif");
      // }
      // else if(error.spellingErrorLikelihood > 0.3)
      // {
      // spellingD = new JLabel("Possibly a typing mistake");
      // spellingIcon= CALL_images.getInstance().getIcon("Misc/maybeIcon.gif");
      // }
      // else
      // {
      // spellingD = new JLabel("Unlikely to be a typing mistake");
      // spellingIcon= CALL_images.getInstance().getIcon("Misc/noIcon.gif");
      // }
      //
      // spellingD.setBounds(110,145,350,20);
      // spellingD.setHorizontalAlignment(JLabel.LEFT);
      // spellingD.setFont(new Font("serif", Font.PLAIN, 16));
      // spellingD.setForeground(Color.BLACK);
      // basicPanel.add(spellingD);
      //
      // if(spellingIcon != null)
      // {
      // spellingI = new JLabel(spellingIcon);
      // spellingI.setToolTipText(error.errorTargetComponentString());
      // spellingI.setBounds(440,130,50,50);
      // basicPanel.add(spellingI);
      // }

      // Cost
      costT = new JLabel("Error Cost: ");
      // costT.setBounds(15,175,95,20);
      costT.setBounds(15, 145, 95, 20);
      costT.setHorizontalAlignment(JLabel.LEFT);
      costT.setFont(new Font("serif", Font.BOLD, 16));
      costT.setForeground(Color.BLACK);
      basicPanel.add(costT);

      costD = new JLabel("" + errorSignificance + "% of the total points available. (" + cost + " out of "
          + totalPoints + ")");
      // costD = new JLabel(cost + " out of " + totalPoints +")");
      // costD.setBounds(110,175,350,20);
      costD.setBounds(110, 145, 350, 20);
      costD.setHorizontalAlignment(JLabel.LEFT);
      costD.setFont(new Font("serif", Font.PLAIN, 16));
      costD.setForeground(Color.BLACK);
      basicPanel.add(costD);

      // Error significance Icon (based on cost)
      errorSignificance = (double) ((cost * 100) / totalPoints);
      happiness = (int) (errorSignificance * numWords) / AVG_SENTENCE_WORDS;
      happiness = 5 - (happiness / HAPPINESS_COEFICIENT);
      if (happiness > 5)
        happiness = 5;
      if (happiness < 1)
        happiness = 1;
      // 2011.08.30 T.Tajima Change
      // filename = new String("Misc/happy" + happiness + "small.gif");
      filename = new String("Misc/happy" + happiness + "small.png");
      smileyFaceIcon = CALL_images.getInstance().getIcon(filename);
      costI = new JLabel(smileyFaceIcon);
      costI.setToolTipText(errorSignificanceStrings[happiness - 1]);
      if (costI != null) {
        costI.setBounds(440, 160, 50, 50);
        basicPanel.add(costI);
      }

      // Now for the feedback text
      subErrorString = error.subErrorTypeString();
      helpTextArray = parent.db.helpText.getHelpText(subErrorString);
      helpText = null;

      // First the main text
      helpText = helpTextArray[0];

      // Now the spelling
      if (error.spellingErrorLikelihood > 0.7) {
        // The text if it is probably a spelling error
        if (helpTextArray[1] != null) {
          if (helpText == null) {
            helpText = helpTextArray[1];
          } else {
            helpText = helpText + " " + helpTextArray[1];
          }
        }
      } else if (error.spellingErrorLikelihood > 0.3) {
        // The text if it is maybe a spelling error
        if (helpTextArray[2] != null) {
          if (helpText == null) {
            helpText = helpTextArray[2];
          } else {
            helpText = helpText + " " + helpTextArray[2];
          }
        }
      } else {
        // The text if it is probably not a spelling error
        if (helpTextArray[3] != null) {
          if (helpText == null) {
            helpText = helpTextArray[3];
          } else {
            helpText = helpText + " " + helpTextArray[3];
          }
        }
      }

      if (helpText == null) {
        helpText = new String("Sorry, now feedback available for this error.");
      } else {
        // Parse for wildcards
        helpText = parseForWildcards(helpText, error);
      }

      // Create and add the text area
      detailsD = new JTextArea(helpText);
      detailsD.setLineWrap(true);
      detailsD.setWrapStyleWord(true);
      detailsD.setEditable(false);

      detailsSP = new JScrollPane(detailsD);
      detailsSP.setBounds(10, 15, 470, 70);
      detailsPanel.add(detailsSP);
    }

    // Add the main panel
    getContentPane().add(mainPanel);

    // Paint (for icons etc)
    repaint();
  }

  public String parseForWildcards(String inputStr, CALL_errorPairStruct error) {
    String returnString = inputStr;
    String tempString;

    returnString = CALL_io.strReplace(returnString, "[TARGWORD]", error.targetString);
    returnString = CALL_io.strReplace(returnString, "[OBSWORD]", error.observedString);
    returnString = CALL_io.strReplace(returnString, "[SHAREDGROUPS]", error.sharedSemanticGroups());

    if ((error.targetWord != null) && (error.targetWord.word != null)) {
      returnString = CALL_io.strReplace(returnString, "[TARGENG]", error.targetWord.word.english);
      returnString = CALL_io.strReplace(returnString, "[TARGGENENG]", error.targetWord.word.genEnglish);

      tempString = error.errorTargetComponentString();
      returnString = CALL_io.strReplace(returnString, "[TARGTYPE]", tempString.toLowerCase());

      if (error.targetFormDiff != null) {
        returnString = CALL_io.strReplace(returnString, "[TARGFORM]", error.targetFormDiff.toLowerCase());
      } else {
        returnString = CALL_io.strReplace(returnString, "[TARGFORM]", "undefined");
      }
    }

    if ((error.observedWord != null) && (error.observedWord.word != null)) {
      returnString = CALL_io.strReplace(returnString, "[OBSENG]", error.observedWord.word.english);
      returnString = CALL_io.strReplace(returnString, "[OBSGENENG]", error.observedWord.word.genEnglish);

      tempString = error.errorObservedComponentString();
      returnString = CALL_io.strReplace(returnString, "[OBSTYPE]", tempString.toLowerCase());

      if (error.observedFormDiff != null) {
        returnString = CALL_io.strReplace(returnString, "[OBSFORM]", error.observedFormDiff.toLowerCase());
      } else {
        returnString = CALL_io.strReplace(returnString, "[OBSFORM]", "undefined");
      }
    }

    return returnString;
  }

  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    if (command.compareTo("ok") == 0) {
      // Close dialog
      setVisible(false);
    }
  }
}