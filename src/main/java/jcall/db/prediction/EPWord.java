/**
 * Created on 2007/06/05
 * @author wang
 * Copyrights @kawahara lab
 */
package jcall.db.prediction;

import java.io.Serializable;

import jcall.db.JCALL_WordBase;

import org.apache.log4j.Logger;

public class EPWord extends JCALL_WordBase implements Cloneable, Serializable {

  // String strLabel;
  String StrSetting;
  static Logger logger = Logger.getLogger(EPWord.class.getName());

  JCALL_WordBase prefix;
  JCALL_WordBase suffix;

  PNodes nodes;

  public EPWord() {
    // TODO Auto-generated constructor stub
    super();
    prefix = null;
    suffix = null;
    // vSubsWord = null;
  }

  public EPWord(int id, int intType, String strKana, String strKanji) {
    super(id, intType, strKana, strKanji);
    // TODO Auto-generated constructor stub
  }

  public EPWord(int intType, String strKana, String strKanji) {
    super(intType, strKana, strKanji);
    // TODO Auto-generated constructor stub
  }

  public JCALL_WordBase getPrefix() {
    return prefix;
  }

  public void setPrefix(JCALL_WordBase prefix) {
    this.prefix = prefix;
  }

  public JCALL_WordBase getSuffix() {
    return suffix;
  }

  public void setSuffix(JCALL_WordBase suffix) {
    this.suffix = suffix;
  }

  // public Element toElement(){
  // Element elemRoot = new Element("word");
  // if(this.getId()>=-1){
  // elemRoot.setAttribute("id",Integer.toString(this.getId()));
  // }
  // if(this.getIntType() >=-1){
  // elemRoot.setAttribute("type",Integer.toString(this.getIntType()));
  // }
  // if(this.getStrKana()!=null && this.getStrKana().length()>0){
  // elemRoot.setAttribute("kana",this.getStrKana());
  // }
  // if(this.getStrKanji()!=null && this.getStrKanji().length()>0){
  // elemRoot.setAttribute("kanji",this.getStrKanji());
  // }
  //
  // if(this.getStrAltkana()!=null && this.getStrAltkana().length()>0){
  // elemRoot.setAttribute("altkana",this.getStrAltkana());
  // }
  // if(this.prefix!=null ){
  // if(this.prefix.getStrKana()!=null && this.prefix.getStrKana().length()>0){
  // elemRoot.setAttribute("prefix",this.prefix.getStrKana());
  // }
  // }
  // if(this.suffix!=null ){
  // if(this.suffix.getStrKana()!=null && this.suffix.getStrKana().length()>0){
  // elemRoot.setAttribute("suffix",this.suffix.getStrKana());
  // }
  // }
  // // if(vSubsWord!=null && vSubsWord.size()>0){
  // // Element child = new Element("subs");
  // // for (int i = 0; i < vSubsWord.size(); i++) {
  // // JCALL_WSubs w = vSubsWord.elementAt(i);
  // // Element eTemp = w.toElement();
  // // if(eTemp!=null){
  // // child.addContent(eTemp);
  // // }
  // // }
  // // elemRoot.addContent(child);
  // // }
  // return elemRoot;
  // }
  //
  //
  // public void toObject(Element e)
  // {
  // if(e.getAttribute("id")!=null){
  // this.setId(Integer.parseInt(e.getAttributeValue("id")));
  // }
  // if(e.getAttribute("type")!=null){
  // this.setStrKana(e.getAttributeValue("type"));
  // }
  // if(e.getAttribute("kana")!=null){
  // this.setStrKana(e.getAttributeValue("kana"));
  // }
  // if(e.getAttribute("kanji")!=null){
  // this.setStrKana(e.getAttributeValue("kanji"));
  // }
  // if(e.getAttribute("altkana")!=null){
  // this.setStrKana(e.getAttributeValue("altkana"));
  // }
  // if(e.getAttribute("prefix")!=null){
  // String str = e.getAttributeValue("prefix");
  // JCALL_Lexicon lexicon = JCALL_Lexicon.getInstance();
  // prefix = lexicon.getWordFmStr(str,JCALL_Lexicon.LEX_TYPE_NOUN_PREFIX);
  // if(prefix==null){
  // logger.error("In lexicon, no find [prefix]: "+str);
  // }
  // }
  // if(e.getAttribute("suffix")!=null){
  // String str = e.getAttributeValue("suffix");
  // JCALL_Lexicon lexicon = JCALL_Lexicon.getInstance();
  // suffix = lexicon.getWordFmStr(str,JCALL_Lexicon.LEX_TYPE_NOUN_SUFFIX);
  // if(suffix==null){
  // logger.error("In lexicon, no find [suffix]: "+str);
  // }
  // }
  //
  // // List content = e.getContent();
  // // Iterator iterator = content.iterator();
  // // while (iterator.hasNext()) {
  // // Object o = iterator.next();
  // // if (o instanceof Element) {
  // // Element child = (Element)o;
  // // if(child.getName().equalsIgnoreCase("subs")){
  // // List listcontent = child.getContent();
  // // Iterator it = listcontent.iterator();
  // // while (it.hasNext()) {
  // // Object aObj = iterator.next();
  // // if (aObj instanceof Element) {
  // // Element parentchild = (Element)aObj;
  // // JCALL_WSubs w = new JCALL_WSubs();
  // // w.toObject(parentchild);
  // // this.addObj(w);
  // // }
  // // }
  // // }
  // // }
  // // }
  //
  // }
  //

}
