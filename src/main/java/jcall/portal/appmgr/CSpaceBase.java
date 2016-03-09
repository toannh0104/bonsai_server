package jcall.portal.appmgr;

import java.io.Serializable;

public class CSpaceBase implements Serializable {

  public String m_strType; // "appspace","spacedir","appunit","serviceunit"
  public String m_strLabel;

  public CSpaceBase(String s, String s1) {
    m_strLabel = s;
    m_strType = s1;
  }

  public CSpaceBase() {
    m_strLabel = "";
    m_strType = "";

  }

  public String getType() {
    return m_strType.toLowerCase();
  }

  public String getLabel() {
    return m_strLabel;
  }

  public String toString() {
    return m_strLabel;
  }

  public String Null2Str(String s) {
    if (s == null || s == "")
      s = "";
    return s;
  }
}
