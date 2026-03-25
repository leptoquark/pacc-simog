package it.avlp.simog.util;

import java.util.ArrayList;

/***
 * 
 * @author diego.squillaci
 *
 */
public class ConcatenateString {

	/**
	 * Concatena una lista di String con apici  Sql Es... '111',222','333'
	 * @param list
	 * @return
	 */
	public static StringBuffer concatenate(ArrayList list) {
		
		StringBuffer element = new StringBuffer();
		
		if(list.isEmpty()||list == null) {
			return element;
		}
		
		for(int i =0;i<list.size();i++) {
			
			element.append("'" + list.get(i) + "'");
			
			if(i<list.size()-1) {
				element.append(",");
			}
		}
		
		return element;
	}
}
