//////////////////////////////////////////////////////////////
// Font handling methods for F
//
package jcall;

import java.awt.Font;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * A cache of the dynamically loaded fonts found in the fonts directory.
 */
public class CALL_fonts {

  private String[] names = {};
  private static Hashtable cache;

  public CALL_fonts() {
    cache = new Hashtable(names.length);
    for (int i = 0; i < names.length; i++) {
      cache.put(names[i], getFont(names[i]));
    }
  }

  public static Font getFont(String name) {
    Font font = null;
    if (cache != null) {
      if ((font = (Font) cache.get(name)) != null) {
        return font;
      }
    }
    String fName = "/fonts/" + name;
    try {
      InputStream is = CALL_fonts.class.getResourceAsStream(fName);
      font = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (Exception ex) {
      ex.printStackTrace();
      // CALL_debug.printlog(CALL_debug.MOD_GENERAL, CALL_debug.WARN, fName +
      // " not loaded.  Using serif font.");
      font = new Font("serif", Font.PLAIN, 24);
    }
    return font;
  }
}
