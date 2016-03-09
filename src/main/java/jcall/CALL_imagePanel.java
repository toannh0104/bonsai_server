package jcall;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Defines a panel for displaying an image
 */
public class CALL_imagePanel extends JPanel {
  Image picture;
  boolean scaling;
  int width;
  int height;

  public CALL_imagePanel(int w, int h, String img) {
    // Kept this version, without scaling, for compatibility
    width = w;
    height = h;
    picture = CALL_images.getInstance().getImage(img);
    setSize(width, height);
    setLayout(null);
    setOpaque(false);
    scaling = false;
  }

  public CALL_imagePanel(int w, int h, String img, boolean scale) {
    width = w;
    height = h;
    picture = CALL_images.getInstance().getImage(img);
    setSize(width, height);
    setLayout(null);
    setOpaque(false);
    scaling = scale;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); // paint background

    if (scaling) {
      // Draw image with scaling
      g.drawImage(picture, 0, 0, width, height, 0, 0, picture.getWidth(this), picture.getHeight(this), this);
    } else {
      // Draw image at its natural size
      g.drawImage(picture, 0, 0, this);
    }
  }
}
