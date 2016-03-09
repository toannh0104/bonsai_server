///////////////////////////////////////////////////////////////////
// Title Panel
// This holds a background image, and two buttons - host and client
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CALL_titlePanel extends JPanel {
  Image background;
  JButton lessonButton;
  JButton progressButton;
  JButton exitButton;
  JButton logOffButton;

  JLabel titleL;
  JLabel nameL;
  JLabel versionL;

  public CALL_titlePanel(CALL_main parent) {
    FlowLayout buttonLayout;
    JPanel mainPanel;

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);
    background = CALL_images.getInstance().getImage("./Resource/Images/Screens/title-sakura.jpg");

    /* Used for component placing */
    Insets insets = mainPanel.getInsets();

    /* Title */
    titleL = new JLabel(parent.title + " - " + "Welcome " + parent.db.currentStudent.name + "!");
    titleL.setForeground(Color.WHITE);
    titleL.setBounds(insets.left + 25, insets.top + 2, 725, 40);
    titleL.setFont(new Font("serif", Font.BOLD, 32));
    mainPanel.add(titleL);

    /* Version */
    versionL = new JLabel("Version " + parent.version);
    versionL.setForeground(Color.WHITE);
    versionL.setBounds(insets.left + 25, insets.top + 42, 725, 16);
    versionL.setFont(new Font("serif", Font.ITALIC, 12));
    mainPanel.add(versionL);

    /* Buttons */
    exitButton = new JButton("Exit");
    exitButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    exitButton.setActionCommand("exit");
    exitButton.setBounds(insets.left + 25, insets.top + 535, 85, 40);
    exitButton.addActionListener(parent);

    logOffButton = new JButton("Log Off");
    logOffButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    logOffButton.setActionCommand("logoff");
    logOffButton.setBounds(insets.left + 120, insets.top + 535, 85, 40);
    logOffButton.addActionListener(parent);

    lessonButton = new JButton("Lesson");
    lessonButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    lessonButton.setActionCommand("lesson");
    lessonButton.setBounds(insets.left + 600, insets.top + 535, 85, 40);
    lessonButton.addActionListener(parent);

    progressButton = new JButton("Review");
    progressButton.setMargin(new Insets(CALL_main.butt_margin_v, CALL_main.butt_margin_h, CALL_main.butt_margin_v,
        CALL_main.butt_margin_h));

    progressButton.setEnabled(false);
    progressButton.setActionCommand("progress");
    progressButton.setBounds(insets.left + 695, insets.top + 535, 85, 40);
    progressButton.addActionListener(parent);

    /* Add components to main panel */
    mainPanel.add(exitButton);
    mainPanel.add(logOffButton);
    mainPanel.add(lessonButton);
    mainPanel.add(progressButton);

    /* Finally add button panels */
    add(mainPanel, BorderLayout.CENTER);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); // paint background

    // Draw image at its natural size first.
    g.drawImage(background, 0, 0, 800, 600, this);
  }
}