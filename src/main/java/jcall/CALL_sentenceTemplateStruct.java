///////////////////////////////////////////////////////////////////
// Grammar Rule Structure
//

package jcall;

public class CALL_sentenceTemplateStruct {
  // Default, NULL template string
  final static String DEFAULT_TEMPLATE = new String("[NULL]");

  // Defines
  final static int TYPE_STANDARD = 0;
  final static int TYPE_VERB = 1;
  final static int TYPE_COUNTER = 2;
  final static int TYPE_ADJECTIVE = 3;

  // Fields - Simple for now, but may well want more!
  String structure;
  String restriction;

  // Type. 0: Standard, 1: Verb, 2: Counter
  int type;

  // The name of the form setting specification
  String form; // form name like "basic" for verb

  // Name of the form to use (in the case of verbs)
  String rule;

  // The following is just for counters
  String counter_form;

  public CALL_sentenceTemplateStruct() {
    init();
  }

  public CALL_sentenceTemplateStruct(String str) {
    init();
    structure = new String(str);
  }

  private void init() {
    type = TYPE_STANDARD;
    structure = null;
    form = null;
    restriction = null;
    counter_form = null;
  }

  public String toString() {
    String str = "";
    str = str + "---structure: " + structure;
    str = str + "---restriction: " + restriction;
    str = str + "---form: " + form;
    str = str + "---type: " + type;
    str = str + "---rule: " + rule;
    str = str + "---counter_form: " + counter_form;
    return str;
  }

}