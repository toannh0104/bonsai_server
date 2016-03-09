///////////////////////////////////////////////////////////////////
// Lexicon Structure - holds the information about all the words
//
//

package jcall;

import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class CALL_studentsStruct {
  // Data
  Vector students;

  public CALL_studentsStruct() {
    students = new Vector();
  }

  public boolean load(String dataDirectory) {
    CALL_studentStruct student;
    FileReader in;
    String tempString;
    String readLine;
    StringTokenizer st;
    File directory;

    String[] files;

    boolean rc = true;

    // Search for all files in the given directory named "student*.txt"
    directory = new File(dataDirectory);
    if (directory != null) {
      if (!directory.isDirectory()) {
        // The path given is not a directory!
        // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.ERROR,
        // "Path [" + dataDirectory + "] is not a valid directory!");
        return false;
      }

      files = directory.list();

      // Go through each file in directory, and determine if it's a student
      // file. If so, load it into a new student object
      for (int i = 0; i < files.length; i++) {
        if (files[i] != null) {
          if ((files[i].startsWith("student")) && (files[i].endsWith(".txt"))) {
            // Has a valid name for a student file
            student = new CALL_studentStruct();
            if (student.load(/* dataDirectory */"/Data/" + files[i])) {
              // Was a valid student, so add the object
              students.add(student);
            }
          }
        }
      }
    }

    // Now print the student list
    print_debug();

    return true;
  }

  // Save all student data
  public boolean save(String dataDirectory) {
    boolean rc = false;
    int index;
    CALL_studentStruct student;

    for (index = 0; index < students.size(); index++) {
      student = (CALL_studentStruct) students.elementAt(index);
      if (student != null) {
        rc = student.save(dataDirectory);
      }
    }

    return rc;
  }

  public CALL_studentStruct getStudent(String name, String password) {
    int index;
    CALL_studentStruct student;
    CALL_studentStruct returnStudent = null;

    if ((name != null) && (password != null)) {
      for (index = 0; index < students.size(); index++) {
        student = (CALL_studentStruct) students.elementAt(index);
        if (student != null) {
          if (student.name.compareTo(name) == 0) {
            if (student.password.compareTo(password) == 0) {
              // We have our student!
              returnStudent = student;
              break;
            }
          }
        }
      }
    }

    return returnStudent;
  }

  /**
   * 
   * @param name
   * @return boolean
   */
  private boolean isStudentExists(String name) {
    CALL_studentStruct student;

    for (int index = 0; index < students.size(); index++) {
      student = (CALL_studentStruct) students.elementAt(index);
      if (student != null) {
        if (student.name.compareTo(name) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 
   * @param name
   * @return boolean
   */
  private boolean isValidName(String name) {
    if (name == "") {
      return false;
    } else if (isStudentExists(name)) {
      return false;
    } else if (name.matches("[a-zA-Z0-9_]+")) {
      return true;
    } else {
      return false;
    }
  }

  public CALL_studentStruct createStudent(String name, String password) {
    int id;
    int highestId = 0;
    CALL_studentStruct newStudent, otherStudent;

    // 2012.03.06 T.Tajima add
    // if "name" is invalid, then return null (for CALL_main.actionPerformed)
    if (!isValidName(name)) {
      return null;
    }

    newStudent = new CALL_studentStruct();
    newStudent.name = name;
    newStudent.password = password;

    // Find ID
    for (int i = 0; i < students.size(); i++) {
      otherStudent = (CALL_studentStruct) students.elementAt(i);
      if (otherStudent != null) {
        if (otherStudent.id > highestId) {
          highestId = otherStudent.id;
        }
      }
    }
    newStudent.id = highestId + 1;

    // Now add student to vector
    students.addElement(newStudent);

    // And return the new student
    return newStudent;
  }

  public void print_debug() {
    CALL_studentStruct student;

    if (students.size() <= 0) {
      // CALL_debug.printlog(CALL_debug.MOD_STUDENT, CALL_debug.INFO,
      // "No students");
    } else {
      for (int i = 0; i < students.size(); i++) {
        student = (CALL_studentStruct) students.elementAt(i);
        if (student != null) {
          student.print_debug();
        }
      }
    }
  }
}