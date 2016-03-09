///////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components 
// that may be associated with it.
//

package jcall;

public class CALL_conceptSlotFillerStruct {
  // Defines
  public static final double DEFAULT_WEIGHT = 1.0;

  // Types
  public static final int TYPE_LEXICON_WORD = 0;
  public static final int TYPE_LEXICON_GROUP = 1;
  public static final int TYPE_SUB_CONCEPT = 2;
  public static final int TYPE_OTHER = 3;

  // Fields
  int type; // The type of the slot filler
  String data; // Slot data

  double weight; // A weight reflecting chances of the filler being used

  public CALL_conceptSlotFillerStruct() {
    data = null;
    type = TYPE_OTHER;
    weight = DEFAULT_WEIGHT;
  }

  public CALL_conceptSlotFillerStruct(String str) {
    data = str;
    type = TYPE_OTHER;
    weight = DEFAULT_WEIGHT;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

}