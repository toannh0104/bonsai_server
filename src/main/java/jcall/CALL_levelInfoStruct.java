///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import javax.swing.table.AbstractTableModel;

public class CALL_levelInfoStruct extends AbstractTableModel {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private String columns[] = { "Level", "Active", "Total" };

  Integer num_level[]; // Count of all loaded words by level
  Integer num_level_A[]; // As above, but only including active words

  public CALL_levelInfoStruct() {
    int i;

    // Initialise level info arrays
    num_level = new Integer[CALL_lexiconStruct.LEX_MAX_LEVELS];
    num_level_A = new Integer[CALL_lexiconStruct.LEX_MAX_LEVELS];
    for (i = 0; i < CALL_lexiconStruct.LEX_MAX_LEVELS; i++) {
      num_level[i] = new Integer(0);
      num_level_A[i] = new Integer(0);
    }
  }

  // Table Model functions
  // -------------------
  public int getColumnCount() {
    return columns.length;
  }

  public int getRowCount() {
    // Add 1 as we will include totals
    return (CALL_lexiconStruct.LEX_MAX_LEVELS + 1);
  }

  public String getColumnName(int col) {
    return columns[col];
  }

  public Object getValueAt(int row, int col) {
    Object data = null;

    if ((row >= 0) && (row < (CALL_lexiconStruct.LEX_MAX_LEVELS - 1))) {
      // Classified level
      if (col == 0) {
        data = (Object) (new String("Level  " + (row + 1)));
      } else if (col == 1) {
        data = (Object) num_level_A[row + 1];
      } else {
        data = (Object) num_level[row + 1];
      }
    } else if (row == (CALL_lexiconStruct.LEX_MAX_LEVELS - 1)) {
      // The unclassified level
      if (col == 0) {
        data = (Object) (new String("Unclassified"));
      } else if (col == 1) {
        data = (Object) num_level_A[0];
      } else {
        data = (Object) num_level[0];
      }
    } else if (row == CALL_lexiconStruct.LEX_MAX_LEVELS) {
      int total = 0;

      if (col == 0) {
        data = (Object) (new String("Total"));
      } else if (col == 1) {
        // Return total
        for (int i = 0; i < CALL_lexiconStruct.LEX_MAX_LEVELS; i++) {
          total += num_level_A[i].intValue();
        }
        data = (Object) total;
      } else {
        // Return total
        for (int i = 0; i < CALL_lexiconStruct.LEX_MAX_LEVELS; i++) {
          total += num_level[i].intValue();
        }
        data = (Object) total;
      }
    } else {
      // Invalid row
      data = null;
    }

    // Finally, return
    return data;

  }

  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
}