/**
 * Created on 2007/02/14
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.config;

public class ConfigItem {

  private String name;
  private String value;

  // private String description;

  public ConfigItem(String s, String s1) {
    name = s;
    value = s1;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
