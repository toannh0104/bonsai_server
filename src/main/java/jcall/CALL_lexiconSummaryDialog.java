///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class CALL_lexiconSummaryDialog extends JDialog implements ActionListener {
  JPanel mainPanel;
  JPanel buttonPanel;
  JPanel OKButtonPanel;
  JPanel versionPanel;

  JTabbedPane tablePane;
  JTable componentTable;
  JTable levelTable;

  JScrollPane levelScrollPane;
  JScrollPane componentScrollPane;

  JButton OKButton;

  public CALL_lexiconSummaryDialog(CALL_main parent) {
    int width = 260;
    int height = 270;

    setSize(width, height);
    setTitle("Lexicon");
    setResizable(false);
    mainPanel = new JPanel(new BorderLayout());
    buttonPanel = new JPanel(new GridLayout(2, 1));
    OKButtonPanel = new JPanel(new GridLayout(1, 3));
    versionPanel = new JPanel();

    /* Tabbed pane */
    tablePane = new JTabbedPane();

    /* Tables */
    levelTable = new JTable(parent.db.lexicon.levelInfo);
    componentTable = new JTable(parent.db.lexicon.typeInfo);

    /* Add tables to scroll panes */
    levelScrollPane = new JScrollPane(levelTable);
    componentScrollPane = new JScrollPane(componentTable);

    /* Add scroll panes to tabbed panel */
    tablePane.addTab("Level", null, levelScrollPane, "View number of words by JPLT level");
    tablePane.addTab("Type", null, componentScrollPane, "View number of words by word type");

    /* Build button panel */
    OKButton = new JButton("OK");
    OKButton.setActionCommand("ok");
    OKButton.addActionListener(this);

    OKButtonPanel.add(new CALL_blankPanel(50, 20));
    OKButtonPanel.add(OKButton, BorderLayout.CENTER);
    OKButtonPanel.add(new CALL_blankPanel(50, 20));

    buttonPanel.add(versionPanel);
    buttonPanel.add(OKButtonPanel);

    mainPanel.add(tablePane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    getContentPane().add(mainPanel);
  }

  public void actionPerformed(ActionEvent e) {
    String command = new String(e.getActionCommand());

    if (command.compareTo("ok") == 0) {
      // Close dialog
      setVisible(false);
    }
  }
}