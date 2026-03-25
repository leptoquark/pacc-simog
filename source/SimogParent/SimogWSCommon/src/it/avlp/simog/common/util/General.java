package it.avlp.simog.common.util;

import java.io.IOException;
import java.util.Properties;

public class General {

	/**
	 * Controlla se la stringa � riconducibile ad un numero,
	 * <br>
	 * nel caso di una stringa vuota o nulla se b = false ritorna true
	 * <br>
	 * nel caso di una stringa vuota o nulla se b = true ritorna false
	 * 
	 * @param String: s
	 * @return boolean
	 */
	public static boolean isNumber(String s,boolean emptyIsAlpha){
		try{
			if(!emptyIsAlpha){
				if(s == null || s.equals("")){
					return true;
				}
				Integer.parseInt(s);
				return true;
			}else{
				if(s == null || s.equals("")){
					return false;
				}
				Integer.parseInt(s);
				return true;
			}
			
		}catch(NumberFormatException nfe){
			return false;
		}
	}
}
