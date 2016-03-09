package jcall;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * A imagePanelCache of all the images found in the Resource/Images directory.
 */
public class CALL_images {
  static Logger logger = Logger.getLogger(CALL_images.class.getName());
  // in "/Misc" : most common used images like background, happy, sad
  private static Hashtable<String, BufferedImage> imagePanelCache = null;

  // when it is full, we always discard the Least Recently Used stategy
  // keep CONTEXTCACHE(10) images there,
  private static Hashtable<String, BufferedImage> imageContexCache = null;
  private static Vector<String> contextCacheKey = null;
  // private static int[] contextCacheIndex = null;

  // Finish imageLessonCache in the future
  // private static Hashtable imageLessonCache = null; // images a lesson
  // required

  private static CALL_images sp;
  private static Vector names; // names for images in catche of imagePanelCache
  // private static Vector namelist;//names for all images
  private static BufferedImage noImage = null;

  // private static boolean cacheOnLoad = true;

  // Some defines
  static final String imagedir = new String("./Resource/Images");
  // for common catche
  static final String cacheImagedir = new String("./Resource/Images/Screens");
  static final String cacheImagedir2 = new String("./Resource/Images/Misc");
  private final static int CONTEXTCACHE = 16;

  static final String[] imagecategories = { "Screens", "Examples", "Misc", "Backdrops", "Countries", "Sports", "Nouns",
      "Time", "People", "Verbs" };

  static final String[] extensions = { ".gif", ".GIF", ".jpg", ".JPG", ".bmp", ".BMP"
      // 2011.08.30 T.Tajima add
      , ".png", ".PNG" };

  private CALL_images() {
    init();
  }

  private void init() {

    // 2011.03.29 T.Tajima add
    logger.setLevel(org.apache.log4j.Level.WARN);

    noImage = getImage(imagedir + "/Misc/none.jpg");

    if (noImage == null) {
      logger.error("NOT FOUND BLANK IMAGE, it is wrong");
    }

    imageContexCache = new Hashtable<String, BufferedImage>();
    contextCacheKey = new Vector();
    // contextCacheIndex = new int[CONTEXTCACHE];

    // Scan for all image files in Parameter One, and add them to names
    names = new Vector();
    findImages(cacheImagedir, ""); // Cache commonly used images only to start
                                   // with
    findImages(cacheImagedir2, "");
    logger.info("common cathe size: " + names.size());

    imagePanelCache = new Hashtable(names.size());
    loadCache();

  }

  public static CALL_images getInstance() {
    if (sp == null) {
      sp = new CALL_images();
    }

    return sp;
  }

  // Constructor - store the images in the imagePanelCache
  // //////////////////////////////////////////////////////////////////
  private void loadCache() {

    logger.debug("enter loadPanelCache");
    String tempstring;

    for (int i = 0; i < names.size(); i++) {
      tempstring = (String) names.get(i);
      logger.debug("Loading image [" + tempstring + "]");
      try {
        imagePanelCache.put(tempstring, getImage(tempstring));
      } catch (Exception e) {
        logger.error("Failed to load image [" + tempstring + "] to cache");
      }
    }

    // No longer use the imagePanelCache
    // HAVE TO LOOK AT THIS SOMEHOW. NEED TO FREE UP CACHE SOMETIMES, GETS TOO
    // FULL - CJW
    // MAYBE HAVE ONE CACHE TO ALWAYS KEEP, ANOTHER THAT WE FREE AFTER EACH
    // EXAMPLE? - CJW
    // cacheOnLoad = false;

    // sp = this;
  }

  /*
   * add to context cache
   */
  // when it is full, we always discard the Least Recently Used stategy
  void addObjectToCache(BufferedImage obj, String fname) {

    if (imageContexCache.size() == CONTEXTCACHE) {
      if (contextCacheKey.size() == CONTEXTCACHE) {
        String name = (String) contextCacheKey.elementAt(0);
        Image img = imageContexCache.remove(name);
        if (img == null) {
          logger.error("WRONG: not found image in context cache with the key:  " + name);
        } else {
          contextCacheKey.remove(0);
          contextCacheKey.add(fname);
          imageContexCache.put(fname, obj);

        }
      } else {
        logger.error("WRONG: contextCacheKey.size [" + contextCacheKey.size()
            + "] is not consist with imageContexCache size [" + imageContexCache.size() + "]");
      }

    } else {
      contextCacheKey.add(fname);
      imageContexCache.put(fname, obj);
    }
  }

  void updateCache(String fname) {
    boolean boo = false;
    for (int i = 0; i < contextCacheKey.size(); i++) {
      String name = (String) contextCacheKey.elementAt(i);
      if (name.equalsIgnoreCase(fname)) {
        contextCacheKey.removeElementAt(i);
        contextCacheKey.addElement(fname);
        boo = true;
        break;
      }
    }
    if (!boo) {
      logger.error("WRONG: not found image key in contextCacheKey:  " + fname);
    }

  }

  // Recursive function that searches a dir structure adding image names to the
  // list
  // //////////////////////////////////////////////////////////////////////////
  void findImages(String fname, String prefix) {
    logger.debug("enter findImages, fname: " + fname + " prefix: " + prefix);
    File sdir;
    String subfiles[];
    String filename;

    filename = new String(prefix + fname);

    // Print debug
    // CALL_debug.printlog(CALL_debug.MOD_IMAGE, CALL_debug.DEBUG,
    // "Searching for images...[" + filename + "]");

    // Get handle to file (or directory)
    sdir = new File(filename);

    if (sdir.isDirectory()) {
      subfiles = sdir.list();

      for (int i = 0; i < subfiles.length; i++) {
        findImages(subfiles[i], filename + "/");
      }
    } else {
      // It's a file. Check the extension to see if it's suitable
      for (int i = 0; i < extensions.length; i++) {
        if (filename.endsWith(extensions[i])) {
          // We've found an image
          names.add(filename);
          logger.debug("Add to names list, the found image [" + fname + "]");
          // Print debug
          // CALL_debug.printlog(CALL_debug.MOD_IMAGE, CALL_debug.DEBUG,
          // "Found image [" + fname + "]");
          break;
        }
      }
    }

  }

  public BufferedImage getImage(String name) {
    BufferedImage readImage = null;
    String tempString;

    // Does it have the prefix already?
    if (!name.startsWith(imagedir)) {
      tempString = new String(imagedir + "/" + name);
      name = tempString;
    }

    logger.debug("Getting image [" + name + "]");

    // Is it in the imagePanelCache?

    if (imagePanelCache != null) {
      readImage = (BufferedImage) imagePanelCache.get(name);
      if (readImage != null) {
        logger.debug("Image found in imagePanelCache, returning");
        return readImage;
      }
    }
    // Is it in the imageContexCache?

    if (contextCacheKey != null) {
      for (int i = 0; i < contextCacheKey.size(); i++) {
        String str = (String) contextCacheKey.get(i);
        if (str.equalsIgnoreCase(name)) {
          readImage = (BufferedImage) imageContexCache.get(str);
          logger.debug("Image found in imageContexCache, returning");
          logger.debug("Now update cache");
          updateCache(str);
          return readImage;
        }
      }
    }

    // Load image
    try {
      readImage = ImageIO.read(new File(name));
      if (readImage != null) {
        logger.debug("Image was successfully loaded, returning");
        logger.debug("Now update cache by adding");
        addObjectToCache(readImage, name);
        return readImage;
      }

    } catch (Exception e) {
      logger.error("Exception in reading the Image: " + name);
      readImage = null;
    }

    // Use the blank image
    readImage = (BufferedImage) noImage;

    logger.debug("Failed to get image [" + name + "]");

    return readImage;
  }

  // 2011.09.01 T.Tajima add
  public Image getImage(String name, int width, int height) {
    return getImage(name).getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
  }

  //
  // Slightly different, but returns an icon based on a path

  public ImageIcon getIcon(String name) {
    ImageIcon newIcon = null;
    Image img;

    // System.out.println("Looking for image " + name);
    img = getImage(name);
    if (img != null) {
      // System.out.println("Got image, setting icon");
      newIcon = new ImageIcon(img);
    }
    return newIcon;
  }

}
