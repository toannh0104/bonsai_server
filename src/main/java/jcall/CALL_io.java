//////////////////////////////////////////////////////////////
// IO handling methods for F1 Manager
//
package jcall;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
// For the recording
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * A cache of the dynamically loaded fonts found in the fonts directory.
 */
public class CALL_io {
  static Logger logger = Logger.getLogger(CALL_io.class.getName());

  // Defines
  // Kurita 2011/02/18
  // public static final int MAX_SENTENCE_LENGTH = 128;
  public static final int MAX_SENTENCE_LENGTH = 256;

  // Character Types
  public static final int CHAR_TAB = 9;

  public static final int INVALID_INT = -999;
  public static final long INVALID_LONG = -999;
  public static final double INVALID_DBL = -999.99;

  // Conversion look up tables
  public static final int romaji = 0;
  public static final int kana = 1;
  public static final int kanji = 2;

  public static final int kanaLookupSize = 76;

  public static final String[] internalStrings = { "POS", "NEG", "PAST", "PRES", "POLIT", "PLAIN", "CRUDE", "S-POL",
      "HUMBL", "STATEMEN", "QUESTION" };

  public static final String[] displayStrings = { "Positive", "Negative", "Past", "Present", "Polite", "Plain",
      "Crude", "Super-Polite", "Humble", "Statement", "Question" };

  public static final String[] romajiPunctuationTable = { " ", ",", ".", "?", ";", "!" };

  public static final String[] romajiConsonantsTable = { "k", "g", "h", "b", "p", "s", "z", "t", "d", "r", "n", "n",
      "m", "y", "w" };

  public static final String[] hiraganaTable = { "あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "が", "ぎ", "ぐ", "げ",
      "ご", "は", "ひ", "ふ", "へ", "ほ", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ", "さ", "し", "す", "せ", "そ", "ざ",
      "じ", "ず", "ぜ", "ぞ", "た", "ち", "つ", "て", "と", "だ", "ぢ", "づ", "で", "ど", "ら", "り", "る", "れ", "ろ", "な", "に", "ぬ",
      "ね", "の", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "わ", "を", "ん", "ー", "？", "。", "、", "！" };

  public static final String[] katakanaTable = { "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "ガ", "ギ", "グ", "ゲ",
      "ゴ", "ハ", "ヒ", "フ", "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ", "サ", "シ", "ス", "セ", "ソ", "ザ",
      "ジ", "ズ", "ゼ", "ゾ", "タ", "チ", "ツ", "テ", "ト", "ダ", "ヂ", "ヅ", "デ", "ド", "ラ", "リ", "ル", "レ", "ロ", "ナ", "ニ", "ヌ",
      "ネ", "ノ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ワ", "ヲ", "ン", "ー", "？", "。", "、", "！"

  };
  public static final String[] romajiTable = { "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "ga", "gi", "gu",
      "ge", "go", "ha", "hi", "fu", "he", "ho", "ba", "bi", "bu", "be", "bo", "pa", "pi", "pu", "pe", "po", "sa",
      "shi", "su", "se", "so", "za", "ji", "zu", "ze", "zo", "ta", "chi", "tsu", "te", "to", "da", "di", "zu", "de",
      "do", "ra", "ri", "ru", "re", "ro", "na", "ni", "nu", "ne", "no", "ma", "mi", "mu", "me", "mo", "ya", "yu", "yo",
      "wa", "wo", "n", "-", "?", ".", ",", "!" };

  // Recorder
  static private CALL_record recorder = null;
  static private boolean recordingInIO = false;

  public CALL_io() {
  }

  public static String readString(FileReader in) {
    int c, cc;
    boolean written = false;
    boolean skipchar = false;

    String outputString = "";

    cc = 0;

    while (written == false) {
      do {
        try {
          c = in.read();
          skipchar = false;

          if ((char) c == '\n' || (char) c == ';')
            break;

          // Check for leading spaces, tabs
          if (!written) {
            skipchar = Character.isWhitespace(c);
            // //CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
            // "Character [" + (char)c + "], skippable? [" + skipchar +"]");
            // logger.debug("Character [" + (char)c + "], skippable? [" +
            // skipchar +"]");
          }

          // Check for command chars
          if ((!skipchar) && (Character.isISOControl(c))) {
            skipchar = true;
          }

          if (!skipchar) {
            // Non-whitespace, begin reading
            written = true;
            outputString = outputString + (char) c;
            cc++;
          }
        } catch (Exception e) {
          // It's buggered
          break;
        }
      } while (cc < MAX_SENTENCE_LENGTH);
    }
    return outputString;
  }

  public static int str2int(String tempString) {
    int rc;
    char ch;

    if (tempString == null) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
      // "Attempted to decode a null string,");
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN, "Setting to " +
      // INVALID_INT);
      rc = INVALID_INT;
    } else {
      try {
        rc = (Integer.decode(tempString)).intValue();
      } catch (Exception e) {
        // Clearly not an integer then!
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
        // "Attempted to decode a non numeric character, [" + tempString +
        // "]. Codes:");
        for (int i = 0; i < tempString.length(); i++) {
          ch = tempString.charAt(i);
          // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
          // "Character " + i + ", Code " + Character.getNumericValue(ch));
        }
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN, "Setting to "
        // + INVALID_INT);
        rc = INVALID_INT;
      }
    }

    return rc;
  }

  public static long str2long(String tempString) {
    long rc;
    char ch;

    if (tempString == null) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
      // "Attempted to decode a null string,");
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN, "Setting to " +
      // INVALID_LONG);
      rc = INVALID_LONG;
    } else {
      try {
        rc = (Long.decode(tempString)).longValue();
      } catch (Exception e) {
        // Clearly not an integer then!
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
        // "Attempted to decode a non numeric character, [" + tempString +
        // "]. Codes:");
        for (int i = 0; i < tempString.length(); i++) {
          ch = tempString.charAt(i);
          // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
          // "Character " + i + ", Code " + Character.getNumericValue(ch));
        }
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN, "Setting to "
        // + INVALID_LONG);
        rc = INVALID_LONG;
      }
    }

    return rc;
  }

  public static double str2double(String tempString) {
    double rc;
    char ch;

    if (tempString == null) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
      // "Attempted to convert a null string to a double");
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN, "Setting to  "
      // + INVALID_DBL);
      rc = INVALID_DBL;
    } else {
      try {
        rc = Double.parseDouble(tempString);
      } catch (Exception e) {
        // Clearly not a double then!
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
        // "Attempted to decode a non numeric character, [" + tempString +
        // "]. Codes:");
        for (int i = 0; i < tempString.length(); i++) {
          ch = tempString.charAt(i);
          // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
          // "Character " + i + ", Code " + Character.getNumericValue(ch));
        }
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.WARN,
        // "Setting to  " + INVALID_DBL);
        rc = INVALID_DBL;
      }
    }

    return rc;
  }

  // Takes string tokenizer, and returns remainder of string
  public static String strRemainder(StringTokenizer st) {
    String tempString, finalString;
    boolean firstpass = true;

    finalString = new String("");

    while (st.hasMoreTokens()) {
      tempString = new String(finalString);
      if (firstpass) {
        firstpass = false;
        finalString = new String(st.nextToken());
      } else {
        finalString = new String(tempString + " " + st.nextToken());
      }
    }

    return finalString;
  }

  public static String strStripSpaces(String str) {
    String character;
    String tempStr;
    String outputStr;

    outputStr = new String("");

    for (int i = 0; i < str.length(); i++) {
      character = new String("" + str.charAt(i));

      // Romanised & Japanese spaces
      if ((character.compareTo(" ") != 0) && (character.compareTo("�@") != 0)) {
        outputStr = outputStr + character;
      }
    }

    return outputStr;
  }

  // Removes any trailing control characters
  public static String strStripCC(String str) {
    String tempString;
    char ch;
    boolean val;
    boolean trim = false;
    int index = -1;

    for (int i = str.length() - 1; i >= 0; i--) {
      ch = str.charAt(i);
      val = Character.isISOControl(ch);
      // //CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.INFO,
      // "Looking at char [" + ch + "], Value [" + val + "]");

      if (!val) {
        // Index of the last non-control character
        index = i;
        break;
      } else {
        trim = true;
        // //CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.INFO,
        // "CC detected");
      }
    }

    if (trim) {
      tempString = new String(str.substring(0, (index + 1)));
    } else {
      tempString = new String(str);
    }

    return tempString;
  }

  // Gets the next integer from a string tokenizer, or returns the invalid int
  // value
  public static int getNextInt(StringTokenizer st) {
    int tempInt = INVALID_INT;
    String tempString = CALL_io.getNextToken(st);

    if (tempString != null) {
      tempInt = CALL_io.str2int(tempString);
    }

    return tempInt;
  }

  // Gets the next integer from a string tokenizer, or returns the invalid int
  // value
  public static long getNextLong(StringTokenizer st) {
    long tempLong = INVALID_LONG;
    String tempString = CALL_io.getNextToken(st);

    if (tempString != null) {
      tempLong = CALL_io.str2long(tempString);
    }

    return tempLong;
  }

  // Gets the next integer from a string tokenizer, or returns the invalid int
  // value
  public static double getNextDouble(StringTokenizer st) {
    double tempDbl = INVALID_DBL;
    String tempString = CALL_io.getNextToken(st);

    if (tempString != null) {
      tempDbl = CALL_io.str2double(tempString);
    }

    return tempDbl;
  }

  // Converts a double to a double with set number of decimal places (lossy)
  public static double dblDecPlace(double input, int numberOfPlaces) {
    int tempInt;
    int factor;
    double tempDouble;

    factor = (int) (Math.pow(10.0, (double) (numberOfPlaces)));
    tempInt = (int) (input * factor);
    tempDouble = (double) tempInt / (double) factor;

    return tempDouble;
  }

  // Usefull for getting next token, already checked for control characters and
  // other problems
  public static String getNextToken(StringTokenizer st) {
    String tempString = null;

    if (st == null) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
      // "Attempt to get a token from an invalid tokenizer");
      return null;
    }
    if (st.hasMoreTokens()) {
      tempString = CALL_io.strStripCC(st.nextToken());
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG, "Token: [" +
      // tempString + "]");
      // logger.debug("Token: [" + tempString + "]");
      if ((tempString == null) || (tempString.length() == 0) || (tempString.equals(" "))) {
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
        // "Returning a null string");
        tempString = null;
      }
    }

    return tempString;
  }

  public static String getNextBracketedToken(StringTokenizer st) {
    String tempString = null;
    String tempString2 = null;
    char bracketChar = ' ';

    if (st == null) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
      // "Attempt to get a token from an invalid tokenizer");
      return null;
    }
    if (st.hasMoreTokens()) {
      tempString = CALL_io.strStripCC(st.nextToken());

      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG, "Token: [" +
      // tempString + "]");
      if ((tempString == null) || (tempString.length() == 0) || (tempString.equals(" "))) {
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
        // "Returning a null string");
        tempString = null;
      } else {
        // Deal with the bracket issue (want to return all within bracket
        if (tempString.startsWith("{")) {
          if (!tempString.endsWith("}")) {
            bracketChar = '}';
          }
        } else if (tempString.startsWith("[")) {
          if (!tempString.endsWith("]")) {
            bracketChar = ']';
          }
        } else if (tempString.startsWith("<")) {
          if (!tempString.endsWith(">")) {
            bracketChar = '>';
          }
        } else if (tempString.startsWith("(")) {
          if (!tempString.endsWith(")")) {
            bracketChar = ')';
          }
        } else if (tempString.startsWith("|")) {
          if (!tempString.endsWith("|")) {
            bracketChar = '|';
          }
        }

        if (bracketChar != ' ') {
          while (st.hasMoreTokens()) {
            tempString2 = CALL_io.strStripCC(st.nextToken());

            // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
            // "Token: [" + tempString2 + "]");
            if ((tempString2 == null) || (tempString2.length() == 0) || (tempString2.equals(" "))) {
              break;
            }

            tempString = tempString + " " + tempString2;
            if (tempString.endsWith("" + bracketChar)) {
              // We have all the tokens we need now!
              break;
            }
          }
        }
      }
    }

    return tempString;
  }

  // Get a string of parameter repeated n times
  public static String getNString(String p, int n) {
    String tempString;

    tempString = new String("");

    for (int i = 0; i < n; i++) {
      tempString = tempString + p;
    }

    return tempString;
  }

  // Extract a command in <brackets>, return that string (without the brackets),
  // as well as any remaining string
  // ReturnString[0] - The <command> String (if exists)
  // ReturnString[1] - The remaining string after the command (the whole string
  // if no <command>
  //
  public static String[] extractCommand(String str) {
    int i, j;
    StringTokenizer st;
    String returnString[];
    String tempString;

    returnString = new String[2];
    returnString[0] = null;
    returnString[1] = null;

    i = str.indexOf('<');
    if (i >= 0) {
      j = str.indexOf('>', i);
      if ((j != -1) && ((j - i) > 1)) {
        returnString[0] = str.substring((i + 1), (j));

        if (j < (str.length() - 1)) {
          // There is remainder - but we don't want spaces, so use tokenizer
          tempString = str.substring(j + 1);
          st = new StringTokenizer(tempString);
          returnString[1] = strRemainder(st);
        }
      } else {
        // No valid command, return the whole string untouched
        returnString[1] = str;
        returnString[0] = null;
      }
    } else {
      // No valid command, return the whole string untouched
      returnString[1] = str;
      returnString[0] = null;
    }

    // Debug
    // System.out.println("Command: [" + returnString[0] + "]");
    // System.out.println("Remainder: [" + returnString[1] + "]");

    return returnString;
  }

  // ////////////////////////////////////////////////////
  //
  // Recording functionality
  //
  // ////////////////////////////////////////////////////

  public static int startRecording(String filename) {
    logger.debug("Enter startRecording");

    DataLine.Info info;
    TargetDataLine targetDataLine;
    AudioFormat audioFormat;
    AudioFileFormat.Type targetType;

    // Initialise preferences
    audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
    targetType = AudioFileFormat.Type.WAVE;

    // Data line later used to read data
    info = new DataLine.Info(TargetDataLine.class, audioFormat);
    targetDataLine = null;
    try {
      targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
      targetDataLine.open(audioFormat);
    } catch (LineUnavailableException e) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
      // "Unable to get a recording line");
      logger.error("Unable to get a recording line");
      return -1;
    }
    // 2012.02.17 T.Tajima add
    catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null,
          "処理中にエラーが発生しました\nマイクが接続されていないか、または\nお使いの環境が44.1kHz 16bit stereo PCMの録音に対応しているかご確認ください", "録音エラー",
          JOptionPane.INFORMATION_MESSAGE);
      logger.error("Cannot recording by IllegalArgumentException");
      return -1;
    }

    // Create SimpleAudioRecorder object for starting and stopping the
    // recording, reading audio data, and outputting to file
    recorder = new CALL_record(targetDataLine, targetType, filename);

    // Start Recording
    recordingInIO = true;
    recorder.start();

    logger.debug("Return from startRecording");
    return 0;
  }

  public static int stopRecording() {
    // Stop recording
    if ((recorder != null) && (recordingInIO)) {
      recorder.stopRecording();
      return 0;
    }

    return -1;
  }

  // ////////////////////////////////////////////////////
  //
  // JAPANESE character handling
  //
  // ////////////////////////////////////////////////////

  public static String strKanaToRomaji(String str) {
    String outputString = null;
    String character;
    boolean smalltsu;
    boolean written;

    outputString = new String("");
    smalltsu = false;

    if (str != null) {
      for (int i = 0; i < str.length(); i++) {
        character = new String("" + str.charAt(i));

        // Is it a space
        if (character.compareTo(" ") == 0) {
          outputString = outputString + " ";
        }

        // Is it a small tsu?
        else if ((character.compareTo("っ") == 0) || (character.compareTo("ッ") == 0)) {
          smalltsu = true;
        }

        // Is character a small one?
        else if ((character.compareTo("ゃ") == 0) || (character.compareTo("ャ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to sha etc
              outputString = outputString + "a";
            } else {
              // Standard case
              outputString = outputString + "ya";
            }
          }
        } else if ((character.compareTo("ゅ") == 0) || (character.compareTo("ュ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to shu etc
              outputString = outputString + "u";
            } else {
              // Standard case
              outputString = outputString + "yu";
            }
          }
        } else if ((character.compareTo("ょ") == 0) || (character.compareTo("ョ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);

            if ((outputString.endsWith("sh")) || (outputString.endsWith("ch"))) {
              // Special case: Change shi to shu etc
              outputString = outputString + "o";
            } else {
              // Standard case
              outputString = outputString + "yo";
            }
          }
        } else if ((character.compareTo("ぁ") == 0) || (character.compareTo("ァ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "a";
        } else if ((character.compareTo("ぃ") == 0) || (character.compareTo("ィ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "i";
        } else if ((character.compareTo("ぅ") == 0) || (character.compareTo("ゥ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "u";
        } else if ((character.compareTo("ぇ") == 0) || (character.compareTo("ェ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "e";
        } else if ((character.compareTo("ぉ") == 0) || (character.compareTo("ォ") == 0)) {
          if (outputString.length() > 0) {
            outputString = outputString.substring(0, outputString.length() - 1);
          }
          outputString = outputString + "o";
        } else {
          written = false;

          // Treat as normal kana
          for (int j = 0; j < kanaLookupSize; j++) {
            if ((character.compareTo(hiraganaTable[j]) == 0) || (character.compareTo(katakanaTable[j]) == 0)) {
              if (smalltsu) {
                // We want to have two of the first character
                smalltsu = false;
                outputString = outputString + (romajiTable[j]).charAt(0);
              }

              outputString = outputString + romajiTable[j];
              written = true;
            }
          }

          if (!written) {
            // Just write the character as is
            outputString = outputString + character;
          }
        }
      }
    }

    // Return the combine string
    // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
    // "String conversion: In [" + str + "], Out: [" + outputString + "]");

    // logger.debug("String conversion: In [" + str + "], Out: [" + outputString
    // + "]");

    return outputString;
  }

  // Eg, for �� returns "a"
  // For �� returns "-"
  // For invalid kana returns "x"
  public static char kanaVowelClass(char ch) {
    String originalStr;
    String tempStr;
    char rc = 'x';

    originalStr = "" + ch;
    tempStr = strKanaToRomaji(String.valueOf(ch));

    if (originalStr.equals(tempStr)) {
      // Hasn't changed - probably romaji to start with
      rc = 'x';
    } else {
      if (tempStr.equals("n")) {
        rc = '-';
      } else {
        rc = tempStr.charAt(tempStr.length() - 1);
      }
    }

    return rc;
  }

  // Eg, for �� returns "k"
  public static char kanaConstClass(char ch) {
    String tempStr;
    char rc;

    tempStr = strKanaToRomaji(String.valueOf(ch));
    rc = tempStr.charAt(0);
    if ((rc == 'a') || (rc == 'i') || (rc == 'u') || (rc == 'e') || (rc == 'o')) {
      rc = '-';
    }

    return rc;
  }

  // For one character only: Type: Hiragana 0, Katakana 1
  public static String kanaLookup(String str, int type) {
    String returnStr = null;

    if (str.length() <= 2) {
      // Look for some exceptions first
      if (str.equals("ti")) {
        str = new String("chi");
      }
      if (str.equals("tu")) {
        str = new String("tsu");
      }
      if (str.equals("si")) {
        str = new String("shi");
      }

      for (int i = 0; i < kanaLookupSize; i++) {
        if (romajiTable[i].equals(str)) {
          // Found a match, just return the kana equivalent
          if (type == 0) {
            // Hiragana
            returnStr = new String(hiraganaTable[i]);
          } else {
            // Katakana
            returnStr = new String(katakanaTable[i]);
          }
          break;
        }
      }
    }

    return returnStr;
  }

  // Takes a romaji string, and returns the alternative form, that which matches
  // our convention
  //
  // Decapitalize
  //
  // o => WO;
  // e => HE;
  // wa => HA;
  // xxmxx => xxNxx
  // dewa => DEHA;
  // oo => OU;
  // shyo => SHO;
  // shyu => SHU;
  // shya => SHA;
  //
  // /////////////////////////////////////////////////////////////////////////
  static public String correctRomajiFormat(String originalString) {
    String rString = null;
    boolean replace = false;

    String stage1String;
    String stage2String;
    String stage3String;
    String stage4String;
    String workingString;
    String searchString;
    String currentWord;
    StringTokenizer st;

    // We assume spaces segmenting words (unless it's just a single word of
    // course!)
    st = new StringTokenizer(originalString);

    workingString = null;

    for (;;) {
      currentWord = CALL_io.getNextToken(st);
      if (currentWord == null) {
        break;
      }

      // Firstly, de-capitalize all letters
      currentWord = currentWord.toLowerCase();

      // Then run through all the comon mismatches

      // 1) WO
      if (currentWord.equals("o")) {
        currentWord = "wo";
      }

      // 2) HE
      if (currentWord.equals("e")) {
        currentWord = "he";
      }

      // 3) HA
      if (currentWord.equals("wa")) {
        currentWord = "ha";
      }

      // 4) M, N
      if (currentWord.indexOf("m") != -1) {
        // Check for an m at end of sentence
        if (currentWord.endsWith("m")) {
          currentWord = currentWord.substring(0, currentWord.length() - 1) + "n";
        }

        // Check for an m ahead of punctuation
        for (int i = 0; i < romajiPunctuationTable.length; i++) {
          searchString = "m" + romajiPunctuationTable[i];
          if (currentWord.indexOf(searchString) != -1) {
            currentWord = strReplace(currentWord, searchString, "n" + romajiConsonantsTable[i]);
          }
        }

        // Then look for an n followed by a consonant
        for (int i = 0; i < romajiConsonantsTable.length; i++) {
          searchString = "m" + romajiConsonantsTable[i];
          if (currentWord.indexOf(searchString) != -1) {
            currentWord = strReplace(currentWord, searchString, "n" + romajiConsonantsTable[i]);
          }
        }
      }

      // 5) DEHA
      if (currentWord.startsWith("dewa")) {
        if (currentWord.length() > 4) {
          currentWord = ("deha" + currentWord.substring(4));
        } else {
          currentWord = "deha";
        }
      }

      // 6) OO, OU
      if (currentWord.indexOf("oo") != -1) {
        currentWord = strReplace(currentWord, "oo", "ou");
      }

      // 7) SHO, CHO, JYO etc
      if (currentWord.indexOf("shyo") != -1) {
        currentWord = strReplace(currentWord, "shyo", "sho");
      }
      if (currentWord.indexOf("syo") != -1) {
        currentWord = strReplace(currentWord, "syo", "sho");
      }

      if (currentWord.indexOf("chyo") != -1) {
        currentWord = strReplace(currentWord, "chyo", "cho");
      }

      if (currentWord.indexOf("jyo") != -1) {
        currentWord = strReplace(currentWord, "jyo", "jo");
      }

      // 8) SHU, CHU, JU etc
      if (currentWord.indexOf("shyu") != -1) {
        currentWord = strReplace(currentWord, "shyu", "shu");
      }
      if (currentWord.indexOf("syu") != -1) {
        currentWord = strReplace(currentWord, "syu", "shu");
      }

      if (currentWord.indexOf("chyu") != -1) {
        currentWord = strReplace(currentWord, "chyu", "chu");
      }

      if (currentWord.indexOf("jyu") != -1) {
        currentWord = strReplace(currentWord, "jyu", "ju");
      }

      // 9) SHA, CHA, JA etc
      if (currentWord.indexOf("shya") != -1) {
        currentWord = strReplace(currentWord, "shya", "sha");
      }
      if (currentWord.indexOf("sya") != -1) {
        currentWord = strReplace(currentWord, "sya", "sha");
      }

      if (currentWord.indexOf("chya") != -1) {
        currentWord = strReplace(currentWord, "chya", "cha");
      }

      if (currentWord.indexOf("jya") != -1) {
        currentWord = strReplace(currentWord, "jya", "ja");
      }

      // Add this word to new string
      if (currentWord != null) {
        if (rString == null) {
          rString = currentWord;
        } else {
          rString = rString + " " + currentWord;
        }
      }

    }

    return rString;
  }

  // //////////////////////////
  // MISC FUNCTIONS
  // //////////////////////////
  public static String strReplace(String source, String pattern, String replace) {
    if (source != null) {
      final int len = pattern.length();
      StringBuffer sb = new StringBuffer();
      int found = -1;
      int start = 0;

      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG, "Pattern: " +
      // pattern + ", Replace:" + replace);
      while ((found = source.indexOf(pattern, start)) != -1) {
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
        // "Found! Index: " + found);
        sb.append(source.substring(start, found));
        sb.append(replace);
        start = found + len;
        // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG, "SB now " +
        // sb.toString());
      }

      sb.append(source.substring(start));

      return sb.toString();
    }

    else
      return "";
  }

  // Takes an internal string (eg. POS) and gives the display string (eg.
  // Positive)
  // ===============================================================
  public static String getDisplayString(String inString) {
    String outString = inString;

    if (inString != null) {
      for (int i = 0; i < internalStrings.length; i++) {
        if (inString.equals(internalStrings[i])) {
          // Match
          outString = displayStrings[i];
          break;
        }
      }
    }

    return outString;
  }

  public static void openURL(String url) {
    String osName = System.getProperty("os.name");

    // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.DEBUG,
    // "Attempting to open url [" + url + "]");

    try {

      if (osName.startsWith("Windows")) {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
      } else if (osName.startsWith("Mac OS")) {
        Class fileMgr = Class.forName("com.apple.eio.FileManager");
        Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
        openURL.invoke(null, new Object[] { url });
      } else {
        // assume Unix or Linux
        String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
        String browser = null;
        for (int count = 0; count < browsers.length && browser == null; count++) {
          if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
            browser = browsers[count];
          }
          if (browser == null) {
            throw new Exception("Could not find web browser");
          } else {
            Runtime.getRuntime().exec(new String[] { browser, url });
          }
        }
      }
    } catch (Exception e) {
      // CALL_debug.printlog(CALL_debug.MOD_IO, CALL_debug.ERROR,
      // "Failed to open web browser");
    }
  }

  public static void main(String[] agrs) {
    CALL_io io = new CALL_io();
    String[] str = io.extractCommand("<title 'This is a book' - Introduction to simple statements and questions>");
    for (int i = 0; i < str.length; i++) {
      System.out.println(str[i]);
    }
  }

}
