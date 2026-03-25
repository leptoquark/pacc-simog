package it.avlp.simog.beans.comparators;

public class MyComparator {

	/**
	 * In  questo metodo null e stringa vuota sono uguali.
	 * il compare delle stringhe e' eseguito tramite equalsIgnoreCase
	 * 
	 * @param s
	 * @param s1
	 * @return esito confronto
	 */
	public boolean equals(String s,String s1){
		if(s != null && s1 != null){
			return s.equalsIgnoreCase(s1);
		}else if(s == null && s1 == null){
			return true;
		}else if(s == null && (s1 != null && "".equals(s1.trim()))){
			return true;
		}else if(s1 == null && (s != null && "".equals(s.trim()))){
			return true;
		}
		return false;
	}
}
