/**
 *
 */
package jcall;

/**
 * @author T.Tajima
 * @since 2011/08/30
 */

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.swing.JEditorPane;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class JCALL_lessonInfoPane extends JEditorPane {

  static final String xmldir = new String("../Data/lesson-info/");
  static final String xsldir = new String("../Data/lesson-info/xsl/");
  static final String sorry = new String("../Data/html/sorry.html");

  java.net.URL xmlURL;
  java.net.URL xslURL;

  int outputStyle;
  int lessonIndex;

  public JCALL_lessonInfoPane(CALL_configDataStruct _gconfig, int _lessonIndex) {
    outputStyle = _gconfig.outputStyle;
    lessonIndex = _lessonIndex;

    this.setContentType("text/html;charset=utf-8");
    this.setEditable(false);
  }

  public JCALL_lessonInfoPane(int _outputStyle, int _lessonIndex) {
    outputStyle = _outputStyle;
    lessonIndex = _lessonIndex;

    this.setContentType("text/html;charset=utf-8");
    this.setEditable(false);
  }

  private void parse() {
    /*
     * //css javax.swing.text.html.HTMLEditorKit kit = new
     * javax.swing.text.html.HTMLEditorKit(); overviewPane.setEditorKit(kit);
     * javax.swing.text.html.StyleSheet styleSheet = kit.getStyleSheet();
     * java.net.URL cssURL = CALL_lessonPanel.class.getResource( xsldir +
     * "test.css"); styleSheet.importStyleSheet(cssURL);
     * overviewPane.setDocument(kit.createDefaultDocument());
     */

    if (xmlURL != null) {
      try {
        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource xslsrc = new StreamSource(xslURL.openStream());

        Transformer transformer = factory.newTransformer(xslsrc);

        Properties prop = new Properties();
        prop.setProperty("encoding", "utf-8");
        transformer.setOutputProperties(prop);

        if (outputStyle == CALL_io.romaji) {
          transformer.setParameter("ja", "ja_ROME");
        } else if (outputStyle == CALL_io.kana) {
          transformer.setParameter("ja", "ja_KANA");
        } else {
          transformer.setParameter("ja", "ja_JP");
        }
        transformer.setParameter("tr", "en");

        StreamSource xmlsrc = new StreamSource(xmlURL.openStream());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(xmlsrc, new StreamResult(out));

        String outString = new String(out.toString("utf-8"));
        outString = outString.replaceFirst("<\\?.*\\?>", "");
        this.setText(outString);
        this.setCaretPosition(0);
      } catch (Exception e) {
        // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
        // "Attempted to read a bad URL: " + xmlURL.toString());
      }
    } else {
      java.net.URL sorryURL = JCALL_lessonInfoPane.class.getResource(sorry);
      if (sorryURL != null) {
        try {
          this.setPage(sorryURL);
        } catch (Exception e) {
          // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.ERROR,
          // "Attempted to read a bad URL: " + sorryURL);
        }
      }

      // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN,
      // "Couldn't find file: [Lesson " + lessonIndex + " xml]");
    }
  }

  public void setOverview() {

    xslURL = JCALL_lessonInfoPane.class.getResource(xsldir + "overview.xsl");
    xmlURL = JCALL_lessonInfoPane.class.getResource(xmldir + lessonIndex + ".xml");

    parse();
  }

  public void setNotes() {

    xslURL = JCALL_lessonInfoPane.class.getResource(xsldir + "notes.xsl");
    xmlURL = JCALL_lessonInfoPane.class.getResource(xmldir + lessonIndex + ".xml");

    parse();
  }

  public boolean hasNotes() {

    xslURL = JCALL_lessonInfoPane.class.getResource(xsldir + "notes.xsl");
    xmlURL = JCALL_lessonInfoPane.class.getResource(xmldir + lessonIndex + ".xml");

    if (xslURL == null || xmlURL == null) {
      return false;
    } else {
      return true;
    }

  }
}
