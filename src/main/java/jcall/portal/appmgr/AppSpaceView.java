package jcall.portal.appmgr;

import java.util.Vector;

import jcall.config.FindConfig;

// Referenced classes of package portal.appmgr:
//            CAppSpaceAccess, CSpaceDirectory, CAppSpace

public class AppSpaceView {

  String strAppSpacePath;

  public AppSpaceView() {
    strAppSpacePath = "";
    strAppSpacePath = FindConfig.getConfig().findStr("APPSPACE");
  }

  /*
   * 
   * 
   * public void showSpaceView(String s, String s1, String s2, JspWriter
   * jspwriter) { String s3 = s1; String s4 = strAppSpacePath + s + ".xml";
   * CAppSpaceAccess cappspaceaccess = new CAppSpaceAccess(); File file = new
   * File(s4); if(!file.exists())
   * cappspaceaccess.saveAppSpace(cappspaceaccess.createAppSpace(s), s4);
   * CAppSpace cappspace = cappspaceaccess.getAppSpace(s4); if(cappspace !=
   * null) System.out.println("Space file:" + s4 + " id:" + s2);
   * if(!s3.equalsIgnoreCase("SPACEDIRMGR") &&
   * !s3.equalsIgnoreCase("SPACEDIRVIEW") && s2 != null) { CSpaceDirectory
   * cspacedirectory = cappspace.getSpaceDir();
   * if(!cspacedirectory.getID().equalsIgnoreCase(s2)) cappspace =
   * filterSpaceDir(cappspace, cspacedirectory, s2); } try { Document document =
   * new Document(); document.setRootElement(cappspace.toXML()); UITransform
   * uitransform = new UITransform(); uitransform.Xml2Html(document, s3,
   * jspwriter); } catch(Exception exception) {
   * System.out.println(exception.getMessage()); exception.printStackTrace(); }
   * }
   */
  /*
   * cut odious data
   */
  private CAppSpace filterSpaceDir(CAppSpace cappspace, CSpaceDirectory cspacedirectory, String s) {
    CAppSpace cappspace1 = cappspace;
    Vector vector = cspacedirectory.getSpaceDirs();
    int i = vector.size();
    for (int j = 0; j < i; j++) {
      CSpaceDirectory cspacedirectory1 = (CSpaceDirectory) vector.get(j);
      if (cspacedirectory1.getID().equalsIgnoreCase(s))
        cappspace1.setSpaceDir(cspacedirectory1);
      else
        cappspace1 = filterSpaceDir(cappspace, cspacedirectory1, s);
    }

    return cappspace1;
  }
}
