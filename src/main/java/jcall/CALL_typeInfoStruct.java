///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import javax.swing.table.AbstractTableModel;

public class CALL_typeInfoStruct extends AbstractTableModel {
  private String columns[] = { "Type", "Active", "Total" };

  // Counters
  int num_n;
  int num_v;
  int num_times;
  int num_adj;
  int num_adv;
  int num_def;
  int num_digits;
  // int num_pos;
  int num_particles;
  int num_suffixes;
  // int num_misc;
  // added by wang
  int num_quantifiers;
  int num_adjverbs;
  int num_prefixes;
  int num_numquant;
  int num_others;

  // Active counters
  int num_n_A;
  int num_v_A;
  int num_times_A;
  int num_adj_A;
  int num_adv_A;
  int num_def_A;
  int num_digits_A;
  // int num_pos_A;
  int num_particles_A;
  int num_suffixes_A;
  // int num_misc_A;

  // added by wang
  int num_quantifiers_A;
  int num_adjverbs_A;
  int num_prefixes_A;
  int num_numquant_A;
  int num_others_A;

  public CALL_typeInfoStruct() {
    // Initialise counters
    num_n = 0;
    num_v = 0;
    num_times = 0;
    num_adj = 0;
    num_adv = 0;
    num_def = 0;
    // num_pos = 0;
    // num_misc = 0;
    num_particles = 0;
    num_suffixes = 0;
    num_quantifiers = 0;
    num_adjverbs = 0;
    num_prefixes = 0;
    num_numquant = 0;

    num_n_A = 0;
    num_v_A = 0;
    num_times_A = 0;
    num_adj_A = 0;
    num_adv_A = 0;
    num_def_A = 0;
    // num_pos_A = 0;
    // num_misc_A = 0;
    num_particles_A = 0;
    num_suffixes_A = 0;
    num_quantifiers_A = 0;
    num_adjverbs_A = 0;
    num_prefixes_A = 0;
    num_numquant_A = 0;
  }

  // Table Model functions
  // -------------------
  public int getColumnCount() {
    return columns.length;
  }

  public int getRowCount() {
    // Add 1 as we will include totals
    return (CALL_lexiconStruct.LEX_NUM_TYPES + 1);
  }

  public String getColumnName(int col) {
    return columns[col];
  }

  public Object getValueAt(int row, int col) {
    Object data = null;

    switch (row) {
      case CALL_lexiconStruct.LEX_TYPE_NOUN:
        if (col == 0) {
          data = (Object) (new String("Nouns"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_n_A));
        } else {
          data = (Object) (new Integer(num_n));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_VERB:
        if (col == 0) {
          data = (Object) (new String("Verbs"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_v_A));
        } else {
          data = (Object) (new Integer(num_v));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_TIME:
        if (col == 0) {
          data = (Object) (new String("Time Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_times_A));
        } else {
          data = (Object) (new Integer(num_times));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_ADJECTIVE:
        if (col == 0) {
          data = (Object) (new String("Adjectives"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_adj_A));
        } else {
          data = (Object) (new Integer(num_adj));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_ADVERB:
        if (col == 0) {
          data = (Object) (new String("Adverbs"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_adv_A));
        } else {
          data = (Object) (new Integer(num_adv));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_DEFINITIVES:
        if (col == 0) {
          data = (Object) (new String("Definitives"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_def_A));
        } else {
          data = (Object) (new Integer(num_def));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_NUMERAL:
        if (col == 0) {
          data = (Object) (new String("Numerical"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_digits_A));
        } else {
          data = (Object) (new Integer(num_digits));
        }
        break;
      // case CALL_lexiconStruct.LEX_TYPE_POSITION:
      // if(col == 0)
      // {
      // data =(Object)(new String("Position Words"));
      // }
      // else if(col == 1)
      // {
      // data =(Object)(new Integer(num_pos_A));
      // }
      // else
      // {
      // data =(Object)(new Integer(num_pos));
      // }
      // break;

      case CALL_lexiconStruct.LEX_TYPE_PARTICLE_AUXIL:
        if (col == 0) {
          data = (Object) (new String("Particles"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_particles_A));
        } else {
          data = (Object) (new Integer(num_particles));
        }
        break;

      case CALL_lexiconStruct.LEX_TYPE_NOUN_SUFFIX:
        if (col == 0) {
          data = (Object) (new String("Postfix"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_suffixes_A));
        } else {
          data = (Object) (new Integer(num_suffixes));
        }
        break;

      // case CALL_lexiconStruct.LEX_TYPE_MISC:
      // if(col == 0)
      // {
      // data =(Object)(new String("Other"));
      // }
      // else if(col == 1)
      // {
      // data =(Object)(new Integer(num_misc_A));
      // }
      // else
      // {
      // data =(Object)(new Integer(num_misc));
      // }
      // break;

      case CALL_lexiconStruct.LEX_NUM_TYPES:
        if (col == 0) {
          data = (Object) (new String("Total"));
        } else if (col == 1) {
          // int total = num_misc_A + num_pos_A + num_digits_A + num_def_A +
          // num_adv_A + num_adj_A + num_times_A + num_v_A + num_n_A;
          int total = num_digits_A + num_def_A + num_adv_A + num_adj_A + num_times_A + num_v_A + num_n_A;
          total = total + num_numquant_A + num_prefixes_A + num_adjverbs_A + num_quantifiers_A + num_others_A;
          data = (Object) (new Integer(total));
        } else {
          // int total = num_misc + num_pos + num_digits + num_def + num_adv +
          // num_adj + num_times + num_v + num_n;
          int total = num_digits + num_def + num_adv + num_adj + num_times + num_v + num_n;
          total = total + num_numquant + num_prefixes + num_adjverbs + num_quantifiers + num_others;
          data = (Object) (new Integer(total));
        }
        break;

      case CALL_lexiconStruct.LEX_TYPE_NUMQUANT:
        if (col == 0) {
          data = (Object) (new String("Numer+Qutifier Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_numquant_A));
        } else {
          data = (Object) (new Integer(num_numquant));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_PREFIX:
        if (col == 0) {
          data = (Object) (new String("Prefix Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_prefixes_A));
        } else {
          data = (Object) (new Integer(num_prefixes));
        }
        break;

      case CALL_lexiconStruct.LEX_TYPE_ADJVERB:
        if (col == 0) {
          data = (Object) (new String("Adjective Verb Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_adjverbs_A));
        } else {
          data = (Object) (new Integer(num_adjverbs));
        }
        break;
      case CALL_lexiconStruct.LEX_TYPE_NOUN_QUANTIFIER:
        if (col == 0) {
          data = (Object) (new String("Quantifier Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_quantifiers_A));
        } else {
          data = (Object) (new Integer(num_quantifiers));
        }
        break;

      case CALL_lexiconStruct.LEX_TYPE_OTHER:
        if (col == 0) {
          data = (Object) (new String("Other Words"));
        } else if (col == 1) {
          data = (Object) (new Integer(num_others_A));
        } else {
          data = (Object) (new Integer(num_others));
        }
        break;
      default:
        data = (Object) new Integer(0);
    }
    // Finally, return
    return data;

  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
}