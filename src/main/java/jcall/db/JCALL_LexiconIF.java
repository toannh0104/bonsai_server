/**
 * Created on 2007/11/09
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db;

public interface JCALL_LexiconIF {

  public JCALL_Word getWord(int id);

  public void save(JCALL_Word word);

  public void update(JCALL_Word word);

}
