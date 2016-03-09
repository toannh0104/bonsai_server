///////////////////////////////////////////////////////////////////
// Concept Structure - for each verb, holds a list of the possible components
// that may be associated with it.
//

package jcall;

import java.util.StringTokenizer;
import java.util.Vector;

import jcall.config.ConfigInstant;
import jcall.config.Configuration;
import jcall.db.JCALL_Lexicon;
import jcall.db.JCALL_Word;

import org.apache.log4j.Logger;

public class CALL_conceptInstanceStruct
{
	private static Logger logger = Logger.getLogger (CALL_conceptInstanceStruct.class.getName());

	// Defines
	public static final String SINGLE_WORD_SLOT = "WORD";

	// Fields
	private Vector<String> label;	// The slot name (eg. description, subject..etc)
	private Vector<String> group;	// The category the data belongs to, if appropriate (eg. small_object)
	private Vector<String> data;	// The data chosen to go with each label (eg. apple)
	private Vector<String> chData;  //chinese data
	private Vector<Integer> id;		// The ID to go with each data (if available, -1 otherwise) (eg. word id)

	private int type;  //question type

	private String grammar;

	public CALL_conceptInstanceStruct()
	{
		group = new Vector();
		label = new Vector();
		data = new Vector();
		id = new Vector();
		chData = new Vector();

		type = -1;
	}

	public void addLanguage(String strLanguage)
	{
		JCALL_Lexicon lex = JCALL_Lexicon.getInstance();
		if(chData==null){
			chData =new Vector();
		}
		if(strLanguage!=null && strLanguage.equals(ConfigInstant.CONFIG_LabelOption_CH)){
			/////
			if(id!=null && data!=null && lex!=null){
				for (int i = 0; i < data.size(); i++) {
					String strData = (String)data.elementAt(i);
					int intId = (int)id.elementAt(i);
					if(strData!=null){
						JCALL_Word word = lex.getWordFmStrEnMeaning(strData);
						if(word!=null){
							String strChData = word.getDChMeaning();
							chData.add(strChData);

						//logger.debug("Find: [ "+strData+" ]"+ " Corresponding chinese is: " + strChData);

						}else{
						//logger.debug("Did not find: [ "+strData+" ] in the JCALL_lexicon, Trying to use id");

							if(intId>0){

								word = lex.getWord(intId);
								if(word!=null){
									String strChData = word.getItemValue("default", "chinese");

									chData.add(strChData);

								//logger.debug("Find: [ "+strData+" ]"+ " Corresponding chinese is: " + strChData +" Using id: "+ intId);

							}else{
							//logger.debug("Did not find: [ "+strData+" ] in the JCALL_lexicon even using id");
								chData.add(null);
							}

							}else{
								chData.add(null);
							}

						}
//
					}else{//strData==null
						chData.add(null);
					}

					if(chData.size()!= i+1){
						logger.error("Wrong when adding the chinese words");
					}
				}

			}

			/////
		}
	}
    public void addElementTest(String labelStr, String valStr, int idp, String groupStr)
    {
        // Set value for lable
        if(label.size() == 0 || label.size() == 1){
            label.addElement(labelStr);
        }else {
            for (int i = 0; i < label.size(); i++) {
                if(label.get(i).equals(labelStr)){
                    label.remove(i);
                    label.addElement(labelStr);
                }
            }
        }

        // Set value for data
        if(data.size() == 0 || data.size() == 1){
            data.addElement(valStr);
        }else {
            for (int i = 0; i < data.size(); i++) {
                if(i == 1){
                    data.remove(i);
                    data.addElement(valStr);
                }
            }
        }
        // Set value for id
        if(id.size() == 0 || id.size() == 1){
            id.addElement(new Integer(idp));
        }else {
            for (int i = 0; i < id.size(); i++) {
                if(id.get(i) == -1){
                    id.remove(i);
                    id.addElement(new Integer(idp));
                }
            }
        }
        // Set value for group
        if (group != null)
        {
            if(groupStr != null){
                group.addElement(groupStr);
            }
        }
    }



	public void addElement(String labelStr, String valStr, int idp, String groupStr)
	{
		label.addElement(labelStr);
		data.addElement(valStr);
		id.addElement(new Integer(idp));
		if(group != null)
		{
			group.addElement(groupStr);
		}
		else
		{
			group.addElement("unspecified");
		}
	}

	public void addElement(String labelStr, String valStr, int idp, String groupStr,String chVal)
	{
		label.addElement(labelStr);
		data.addElement(valStr);
		chData.addElement(chVal);
		id.addElement(new Integer(idp));
		if(group != null)
		{
			group.addElement(groupStr);
		}
		else
		{
			group.addElement("unspecified");
		}
	}

	public String getDataString(String labelString)
	{
		String returnString = null;
		String tempString;
		int index;

		// May get passed reference in brackets, in which case strip these
		if(labelString.startsWith("["))
		{
			index = labelString.indexOf("]");
			labelString = labelString.substring(1, index);
		}

		for(int i = 0; i < label.size(); i++)
		{
			tempString = (String) label.elementAt(i);

			if(labelString.equals(tempString))
			{
				returnString = (String) data.elementAt(i);
				break;
			}
		}

		return returnString;
	}



	public String getDataString(String labelString, String strLabelOption)
	{
		String returnString = null;
		String tempString;
		int index;

		// May get passed reference in brackets, in which case strip these
		if(labelString.startsWith("["))
		{
			index = labelString.indexOf("]");
			labelString = labelString.substring(1, index);
		}

		for(int i = 0; i < label.size(); i++)
		{
			tempString = (String) label.elementAt(i);

			if(labelString.equals(tempString))
			{
//				returnString = (String) data.elementAt(i);

				if(strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)){
					returnString = (String) chData.elementAt(i);
				}else{
					returnString = (String) data.elementAt(i);
				}

				break;
			}
		}

		return returnString;
	}


	public int getWordID(String labelString)
	{
		Integer tempInt;
		int returnInt = -1;
		String tempString;
		int index;

		// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Looking for word id in slot " + labelString);

		// May get passed reference in brackets, in which case strip these
		if(labelString.startsWith("["))
		{
			index = labelString.indexOf("]");
			if(index != -1)
			{
				labelString = labelString.substring(1, index);
			}
		}

		for(int i = 0; i < label.size(); i++)
		{
			tempString = (String) label.elementAt(i);

			if(labelString.equals(tempString))
			{
				tempInt = (Integer) id.elementAt(i);
				if(tempInt != null)
				{
					returnInt = tempInt.intValue();
				}
				break;
			}
		}

		return returnInt;
	}

	public String getInstanceString()
	{
		String labelString;
		String dataString;
		String rString = null;

		for(int i = 0; i < label.size(); i++)
		{
			labelString = (String) label.elementAt(i);
			dataString = (String) data.elementAt(i);

			if(i == 0)
			{
				rString = dataString + " (" + labelString + ")";
			}
			else
			{
				rString = rString +", " + dataString + " (" + labelString + ")";
			}
		}

		return rString;
	}

	// Takes a string, and if it contains any instance variables (eg. "[Subject]") these are converted to the filler values
	//context sensible
	public String convertInstanceVariables(String input)
	{
		boolean changed;
		int index1, index2;
		String subString1, subString2, keyString, labelString,chKeyString;
		String finalString = input;

		// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Looking to fill in instance slots in string [" + finalString + "]");
	//logger.debug( "Looking to fill in instance slots in string [" + finalString + "]");

		//label option
		Configuration cgxml = Configuration.getConfig();
		String strType = cgxml.getItemValue("systeminfo", "laststudent");
		String strLabelOption = cgxml.getItemValue(strType, "label");

		if(finalString != null)
		{
			for(;;)
			{
				// Reset values
				changed = false;
				subString1 = "";
				subString2 = "";

				// Get indexes of [ and ] if they exist
				index1 = finalString.indexOf('[');
				index2 = finalString.indexOf(']');

				if((index1 != -1) && (index2 != -1) && ((index2 - index1) > 1))
				{
					// We have a potential key
					if(index1 > 0)
					{
						// The key doesn't start from the first character
						subString1 = finalString.substring(0, index1);
					}

					if(index2 < (finalString.length() - 1))
					{
						// The key doesn't extend to the end of the main string
						subString2 = finalString.substring(index2 + 1, finalString.length());
					}

					labelString = finalString.substring(index1 + 1, index2);
					// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Looking at label [" + labelString + "]");

				//logger.debug("Looking at label [" + labelString + "]");

					// Now look up the string, if it matches, make the switch
					keyString = getDataString(labelString);
					chKeyString = getDataString(labelString,ConfigInstant.CONFIG_LabelOption_CH);

					if(keyString != null)
					{
						changed = true;
//						finalString = subString1 + keyString + subString2;

						if(strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)){
							finalString = chKeyString;
						}else{//english
							finalString = subString1 + keyString + subString2;
						}

					//logger.debug("Match found => [" + finalString + "] ");

					}
					else
					{
						changed = true;
						finalString = subString1 + labelString + subString2;
					   logger.debug("No match..stripping brackets => [" + finalString + "] ");

					}
				}/////////if


				if(!changed)
				{
					// Finished parsing
					break;
				}
			}
		}
		// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Returning the String [" + finalString + "]");

	//logger.debug("Return(in EN default) String [" + finalString + "]");

		return finalString;
	}




	// Takes a string, and if it contains any instance variables (eg. "[Subject]") these are converted to the filler values
	public String convertInstanceVariables(String input,String strLabelOption)
	{
	//logger.debug("enter convertInstanceVariables; (String input: ]"+input+"]  String strLabelOption: ["+ strLabelOption+"]");

		boolean changed, flag;
		int index1, index2;
		String subString1, subString2, keyString, labelString,chKeyString;
		String finalString = input;

		// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Looking to fill in instance slots in string [" + finalString + "]");
//	//logger.debug("Looking to fill in instance slots in string [" + finalString + "]");

		if(finalString ==null){
		//logger.debug("Returning the String [" + finalString + "]");
			return null;
		}
		if(finalString != null)
		{
			flag=false;
			for(;;)
			{
				// Reset values
				changed = false;
				subString1 = "";
				subString2 = "";

				// Get indexes of [ and ] if they exist
				index1 = finalString.indexOf('[');
				index2 = finalString.indexOf(']');

				if((index1 != -1) && (index2 != -1) && ((index2 - index1) > 1))
				{
					// We have a potential key
				//logger.debug("We have a potential key");

					if(index1 > 0)
					{
						// The key doesn't start from the first character
						subString1 = finalString.substring(0, index1);
					}

					if(index2 < (finalString.length() - 1))
					{
						// The key doesn't extend to the end of the main string
						subString2 = finalString.substring(index2 + 1, finalString.length());
					}

					labelString = finalString.substring(index1 + 1, index2);
					// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Looking at label [" + labelString + "]");

				//logger.debug("Looking at label [" + labelString + "]");

					// Now look up the string, if it matches, make the switch
					keyString = getDataString(labelString);
					chKeyString = getDataString(labelString,ConfigInstant.CONFIG_LabelOption_CH);

					if(keyString != null)
					{
						changed = true;

						if(strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)){
							finalString = chKeyString;
						}else{//english
							finalString = subString1 + keyString + subString2;;
						}

						// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Match found => [" + finalString + "] ");
					//logger.debug("Match found => [" + finalString + "] ");

						flag=true; //means need not change it to other languages again;
					}
					else
					{
						changed = true;
//						if(strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)){
//							finalString = chKeyString;
//						}else{//english
//							finalString = subString1 + keyString + subString2;;
//						}

						finalString = subString1 + labelString + subString2;
						// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "No match..stripping brackets => [" + finalString + "] ");
					//logger.debug("No match..stripping brackets => [" + finalString + "] ");
						flag = false;
					}
				}//end if

				if(!changed)
				{
					// Finished parsing
					break;
				}
			}//end for

			// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.DEBUG, "Returning the String [" + finalString + "]");

		 if(!flag){
//			 simple String(only a word,like "not" "is")
			 StringTokenizer stoken = new StringTokenizer(finalString);
//			 if(stoken.countTokens()>1){
//				 logger.debug("No dealing now ");
//			 }else
			  if(stoken.countTokens()==1){// search in teh dictionary
				  /* 2011.09.02 T.Tajima delete for remove chinese
			if(strLabelOption.equals(ConfigInstant.CONFIG_LabelOption_CH)){
				JCALL_Lexicon lexi = JCALL_Lexicon.getInstance();
				JCALL_Word word = lexi.getWordFmStrEnMeaning(finalString);
				if(word!=null){
					String chstr = word.getDChMeaning();
					int inI = chstr.indexOf("（");//
					if(inI!=-1){
						finalString = chstr.substring(0, inI);
					}else{
						finalString = chstr;
					}
				}
			}
			*/
			 }
			// else {//english, no change == finalString

		 }
		 logger.debug("Returning the String [" + finalString + "]");
			return finalString;



		}
		return null;



	}



	public void print_debug()
	{
		String instanceString;

		instanceString = getInstanceString();

		// CALL_debug.printlog(CALL_debug.MOD_CONCEPT, CALL_debug.INFO, "Concept Instance: " + instanceString);
	}

	public String toString() {

		String labelString;
		String dataString;
		String chDataString;
		String rString = "";

		for(int i = 0; i < label.size(); i++)
		{

			labelString = (String) label.elementAt(i);
			dataString = (String) data.elementAt(i);
			if(i < chData.size()){
				chDataString = (String) chData.elementAt(i);
			}else{
				logger.error("chData[ "+i+"] is not exist! the chData size is less than label vector");
				chDataString =null;
			}

			if(i == 0)
			{
				rString = dataString + " (" + labelString + ")" +" ["+chDataString +"]" ;
			}
			else
			{
				rString = rString +", " + dataString + " (" + labelString + ")"+" ["+chDataString +"]";
			}
		}

		return rString;

	}

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

	public Vector<String> getLabel() {
		return label;
	}

	public void setLabel(Vector<String> label) {
		this.label = label;
	}

	public Vector<String> getGroup() {
		return group;
	}

	public void setGroup(Vector<String> group) {
		this.group = group;
	}

	public Vector<String> getData() {
		return data;
	}

	public void setData(Vector<String> data) {
		this.data = data;
	}

	public Vector<String> getChData() {
		return chData;
	}

	public void setChData(Vector<String> chData) {
		this.chData = chData;
	}

	public Vector<Integer> getId() {
		return id;
	}

	public void setId(Vector<Integer> id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}



}