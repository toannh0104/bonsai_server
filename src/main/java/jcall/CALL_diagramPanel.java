///////////////////////////////////////////////////////////////////
// Lesson Panel
// The main menus for Lessons
//
//

package jcall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jcall.config.ConfigInstant;
import jcall.config.Configuration;

import org.apache.log4j.Logger;

public class CALL_diagramPanel extends JPanel {

  static Logger logger = Logger.getLogger(CALL_diagramPanel.class.getName());

  static final int MARKER_WIDTH = 25;
  static final int MIN_MARKER_X = 5;
  static final int MAX_TEXT_H = 24;
  static final int TOOLTIP_REDUCTION_FACTOR = 15; // 20 would mean a tooltip box
                                                  // 1/2 size of component. 30
                                                  // would be 1/3 etc

  // The main panel
  JPanel mainPanel;
  CALL_diagramInstanceStruct diagramInstance; // The diagram template in use
  JLabel questionLabel;

  boolean drawMarkers;

  int width;
  int height;

  // Conceptual width (all diagram templates are made assuming these values)
  public static final int CWIDTH = 450;
  public static final int CHEIGHT = 375;

  public CALL_diagramPanel(CALL_questionStruct question, CALL_database data, int w, int h, boolean markers) {
    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Default to markers off
    drawMarkers = markers;

    // Set the dimensions
    width = w;
    height = h;

    // Create the diagram instance, and update the centering
    diagramInstance = new CALL_diagramInstanceStruct(question, data);
    diagramInstance.centering(CWIDTH, CHEIGHT, width, height);

    logger.debug("Finished CALL_diagramInstanceStruct generation");

    /* Main panel */
    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setOpaque(false);

    // Add component labels (these are blank, but are used to show the tooltips)
    addComponentLabels();
    logger.debug("Finished addComponentLabels; and return back to construction function of CALL_diagramPanel");

    add(mainPanel, BorderLayout.CENTER);
  }

  public void setMarkers(boolean val) {
    drawMarkers = val;
  }

  /*
   * add label, why some are said is blank for tool-tip????
   */

  public void addComponentLabels() {
    logger.debug("Enter addComponentLabels()");

    Configuration configxml = Configuration.getConfig();
    String strType = configxml.getItemValue("systeminfo", "laststudent");
    String strLabelOption = configxml.getItemValue(strType, "label");

    logger.debug("strLabelOption: " + strLabelOption);

    Integer x, y, w, h, oldW, oldH;
    Vector imageVector, markerVector, textVector, mTextVector, xVector, yVector, wVector, hVector, chTextVector;
    String image, text, mText, marker, chText;
    JLabel tempLabel;

    logger.debug("diagramInstance.image.size():  " + diagramInstance.image.size());
    for (int i = 0; i < diagramInstance.image.size(); i++) {
      imageVector = (Vector) diagramInstance.image.elementAt(i);
      markerVector = (Vector) diagramInstance.marker.elementAt(i);
      textVector = (Vector) diagramInstance.text.elementAt(i);
      mTextVector = (Vector) diagramInstance.mText.elementAt(i);

      chTextVector = (Vector) diagramInstance.chText.elementAt(i);

      xVector = (Vector) diagramInstance.x.elementAt(i);
      yVector = (Vector) diagramInstance.y.elementAt(i);
      wVector = (Vector) diagramInstance.w.elementAt(i);
      hVector = (Vector) diagramInstance.h.elementAt(i);

      if ((imageVector != null) && (textVector != null)) {
        if ((xVector != null) && (yVector != null) && (wVector != null) && (hVector != null)) {
          for (int j = 0; j < imageVector.size(); j++) {
            image = (String) imageVector.elementAt(j);
            marker = (String) markerVector.elementAt(j);
            text = (String) textVector.elementAt(j);
            mText = (String) mTextVector.elementAt(j);

            chText = (String) chTextVector.elementAt(j);

            if (text != null) {
              logger.debug("text: " + text + "----- imageVector" + j);
              // Use a see through label so that we can use the - not for
              // backdrop!
              if (!(image.equals(CALL_diagramGroupStruct.BLANKIMAGE))) {
                logger.debug("image !=BLANKIMAGE");

                if (!text.equals("none")) {
                  tempLabel = new JLabel();

                  x = (Integer) xVector.elementAt(j);
                  y = (Integer) yVector.elementAt(j);
                  w = (Integer) wVector.elementAt(j);
                  h = (Integer) hVector.elementAt(j);

                  // We're only going to use the center section of the image
                  oldW = w;
                  oldH = h;

                  w = (w * 10) / TOOLTIP_REDUCTION_FACTOR;
                  x = x + ((oldW - w) / 2);
                  h = (h * 10) / TOOLTIP_REDUCTION_FACTOR;
                  y = y + ((oldH - h) / 2);

                  // Make a transparent label (for tool-tip)
                  tempLabel.setBounds(x.intValue(), y.intValue(), w.intValue(), h.intValue());

                  // add contex information about the label language
                  if (strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)) {
                    if (!chText.equals("none") && chText != null) {
                      tempLabel.setToolTipText(chText);
                      logger.debug("Added chtext tool tip label: " + chText + ", " + x + ", " + y);

                    } else {
                      tempLabel.setToolTipText(text);
                      logger.debug("Added en text tool tip label: " + text + ", " + x + ", " + y);
                    }

                  } else {

                    tempLabel.setToolTipText(text);
                    logger.debug("Added en text tool tip label: " + text + ", " + x + ", " + y);
                  }
                  tempLabel.setOpaque(false);
                  mainPanel.add(tempLabel);

                }

              } else { // image.equals(CALL_diagramGroupStruct.BLANKIMAGE)
                logger.debug("image ==BLANKIMAGE");
                // Use a visible text label instead of image
                tempLabel = new JLabel(text);

                x = (Integer) xVector.elementAt(j);
                y = (Integer) yVector.elementAt(j);
                w = (Integer) wVector.elementAt(j);
                h = (Integer) hVector.elementAt(j);

                // Need to center on the Y axis (mainly for the cases where text
                // is replacing a missing picture)
                y += (h / 2);

                if (h > MAX_TEXT_H) {
                  // We need to do this for when text replaces images but has
                  // big H
                  h = MAX_TEXT_H;
                  y -= h;
                }

                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
                // "Text [" + text + "]. X:" + x + ", Y:" + y + ", W:" + w +
                // ", H:" + h);
                tempLabel.setBounds(x.intValue(), y.intValue(), w.intValue(), h.intValue() + 8);
                tempLabel.setFont(new Font("serif", Font.PLAIN, h));
                tempLabel.setHorizontalAlignment(JLabel.CENTER);
                if (strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)) {
                  if (!chText.equals("none") && chText != null) {
                    tempLabel.setToolTipText(chText);
                    logger.debug("Added chtext tool tip label: " + chText + ", " + x + ", " + y);

                  } else {
                    tempLabel.setToolTipText(text);
                    logger.debug("Added en text tool tip label: " + text + ", " + x + ", " + y);
                  }
                } else {
                  tempLabel.setToolTipText(text);
                }
                tempLabel.setOpaque(false);
                mainPanel.add(tempLabel);
              }
              // }//end if (text!=null)
              // if text ==null, should be no drawing and no markers, Right?;

              // And now the marker text

              if (drawMarkers) {
                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
                // "Looking to add marker");

                logger.debug("Looking to add marker");
                logger.debug("For other language's markers, now we did not do it");

                if (mText != null) {
                  // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                  // CALL_debug.INFO, "We have mText: [" + mText + "]");
                  logger.debug("We have mText: [" + mText + "]");

                  // Use a see through label so that we can use the - not for
                  // backdrop!
                  if (!(marker.equals(CALL_diagramGroupStruct.BLANKIMAGE))) {
                    if (!marker.equals("none")) {
                      tempLabel = new JLabel();

                      x = (Integer) xVector.elementAt(j);
                      y = (Integer) yVector.elementAt(j);

                      // The above are the image co-ordinates...calculate the
                      // markers position etc
                      x = x - (MARKER_WIDTH / 2);
                      if (x < MIN_MARKER_X) {
                        // Make sure our shifting of mx didn:t move it off the
                        // canvas
                        x = MIN_MARKER_X;
                      }

                      // Make a transparent label (for tool-tip)
                      // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM,
                      // CALL_debug.INFO, "Adding marker lable " + x + ", " +
                      // y);
                      tempLabel.setBounds(x.intValue(), y.intValue(), MARKER_WIDTH, MARKER_WIDTH);
                      tempLabel.setToolTipText(mText);
                      tempLabel.setOpaque(false);
                      mainPanel.add(tempLabel);
                    }
                  }
                } else {
                  logger.debug("We have no mText, thus [NULL]");
                }
              }

            }// Change the position of end if (text!=null) to here

          }
        }
      }
    }
  }

  public void paintComponent(Graphics g) {
    Integer x, y, w, h;
    int mx, my;
    int cx, cy, cw, ch;
    String image, marker, text;
    Image tempImage;
    Vector imageVector, markerVector, textVector, xVector, yVector, wVector, hVector;

    super.paintComponent(g); // paint background

    // Used for component placing
    Insets insets = mainPanel.getInsets();

    // Go through each of the components
    for (int i = 0; i < diagramInstance.image.size(); i++) {
      imageVector = (Vector) diagramInstance.image.elementAt(i);
      markerVector = (Vector) diagramInstance.marker.elementAt(i);
      textVector = (Vector) diagramInstance.text.elementAt(i);
      xVector = (Vector) diagramInstance.x.elementAt(i);
      yVector = (Vector) diagramInstance.y.elementAt(i);
      wVector = (Vector) diagramInstance.w.elementAt(i);
      hVector = (Vector) diagramInstance.h.elementAt(i);

      if ((imageVector != null) && (textVector != null)) {
        if ((xVector != null) && (yVector != null) && (wVector != null) && (hVector != null)) {
          for (int j = 0; j < imageVector.size(); j++) {
            image = (String) imageVector.elementAt(j);
            marker = (String) markerVector.elementAt(j);
            text = (String) textVector.elementAt(j);
            x = (Integer) xVector.elementAt(j);
            y = (Integer) yVector.elementAt(j);
            w = (Integer) wVector.elementAt(j);
            h = (Integer) hVector.elementAt(j);

            if ((image != null) && (image.compareTo(CALL_diagramGroupStruct.BLANKIMAGE) != 0)
                && (image.compareTo(CALL_diagramGroupStruct.INVISIBLEIMAGE) != 0)) {
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Displaying: " + image);
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "X:" + x + " Y:" + y + " W:" + w + " H: " + h);
              try {
                // 2011.09.01 T.Tajima change
                g.drawImage(CALL_images.getInstance().getImage(image, w.intValue(), h.intValue()), x.intValue(),
                    y.intValue(), null);
                /*
                 * // Display image tempImage =
                 * CALL_images.getInstance().getImage(image);
                 * 
                 * g.drawImage(tempImage, x.intValue(), y.intValue(),
                 * x.intValue() + w.intValue(), y.intValue() + h.intValue(), 0,
                 * 0, tempImage.getWidth(this), tempImage.getHeight(this),
                 * Color.WHITE, this);
                 */
              } catch (Exception e) {
                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.WARN,
                // "Possible problem displaying diagram");
              }
            }

            // Now for the marker
            if ((drawMarkers) && (marker != null) && (marker.compareTo(CALL_diagramGroupStruct.BLANKIMAGE) != 0)) {
              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "Displaying: marker " + marker);

              // Calculate co-ordinates for marker
              mx = x.intValue() - (MARKER_WIDTH / 2);
              my = y.intValue();
              if (mx < MIN_MARKER_X) {
                // Make sure our shifting of mx didn:t move it off the canvas
                mx = MIN_MARKER_X;
              }

              // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.INFO,
              // "X:" + mx + " Y:" + my + " W:" + MARKER_WIDTH + " H: " +
              // MARKER_WIDTH);
              try {
                // 2011.09.01 T.Tajima Change
                g.drawImage(CALL_images.getInstance().getImage(marker, MARKER_WIDTH, MARKER_WIDTH), mx, my,
                    Color.WHITE, null);
                /*
                 * // Display image tempImage =
                 * CALL_images.getInstance().getImage(marker);
                 * 
                 * g.drawImage(tempImage, mx, my, mx + MARKER_WIDTH, my +
                 * MARKER_WIDTH, 0, 0, tempImage.getWidth(this),
                 * tempImage.getHeight(this), Color.WHITE, this);
                 */
              } catch (Exception e) {
                // CALL_debug.printlog(CALL_debug.MOD_DIAGRAM, CALL_debug.WARN,
                // "Possible problem displaying diagram");
              }
            }
          }
        }
      }
    }
  }
}