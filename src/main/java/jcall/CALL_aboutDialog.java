///////////////////////////////////////////////////////////////////
// The about box
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CALL_aboutDialog extends JDialog implements ActionListener {
  JPanel mainPanel;
  JPanel buttonPanel;
  JPanel OKButtonPanel;
  JPanel versionPanel;
  CALL_imagePanel imagePanel;
  JButton OKButton;
  JLabel titleL;
  JLabel subtitleL;
  JLabel versionL;
  JLabel subtitleLS;
  JLabel titleLS;

  public CALL_aboutDialog(CALL_main parent) {
    int width = 260;
    int height = 235;

    setSize(width, height);
    setTitle("About");
    setResizable(false);
    mainPanel = new JPanel(new BorderLayout());
    imagePanel = new CALL_imagePanel(width, height, "Misc/about.jpg");
    buttonPanel = new JPanel(new GridLayout(2, 1));
    OKButtonPanel = new JPanel(new GridLayout(1, 3));
    versionPanel = new JPanel();

    /* Create text labels */
    versionL = new JLabel("CALJ: " + parent.version);
    versionL.setBounds(105, 0, 300, 20);
    versionPanel.add(versionL);

    titleL = new JLabel(CALL_main.title);
    titleL.setForeground(Color.WHITE);
    titleL.setBounds(50, 10, 220, 72);
    titleL.setFont(new Font("serif", Font.BOLD, 64));

    titleLS = new JLabel(CALL_main.title);
    titleLS.setForeground(Color.BLACK);
    titleLS.setBounds(53, 13, 220, 72);
    titleLS.setFont(new Font("serif", Font.BOLD, 64));

    subtitleL = new JLabel(CALL_main.subtitle);
    subtitleL.setForeground(Color.WHITE);
    subtitleL.setBounds(40, 100, 220, 24);
    subtitleL.setFont(new Font("serif", (Font.ITALIC + Font.BOLD), 14));

    subtitleLS = new JLabel(CALL_main.subtitle);
    subtitleLS.setForeground(Color.BLACK);
    subtitleLS.setBounds(42, 102, 220, 24);
    subtitleLS.setFont(new Font("serif", (Font.ITALIC + Font.BOLD), 14));

    /* Build button panel */
    OKButton = new JButton("OK");
    OKButton.setActionCommand("ok");
    OKButton.addActionListener(this);

    OKButtonPanel.add(new CALL_blankPanel(50, 20));
    OKButtonPanel.add(OKButton, BorderLayout.CENTER);
    OKButtonPanel.add(new CALL_blankPanel(50, 20));

    buttonPanel.add(versionPanel);
    buttonPanel.add(OKButtonPanel);

    mainPanel.add(imagePanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    imagePanel.add(titleL);
    imagePanel.add(subtitleL);
    imagePanel.add(titleLS);
    imagePanel.add(subtitleLS);

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