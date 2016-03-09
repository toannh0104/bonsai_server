/**
 * Created on 2007/02/14
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.config;

public class ConfigType {

  private String name;
  private String description;

  public ConfigType(String s, String s1) {
    name = s;
    description = s1;
  }

  public ConfigType(String s) {
    name = s;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }
}
