package it.avlp.simog.massload.util.comparators;

public abstract class MultiComparator {

	/**
	 * @deprecated
	 * @param o1
	 * @param o2
	 * @return
	 * @throws Exception
	 */
	public abstract int compare(Object o1,Object o2) throws Exception;
	
	public abstract boolean equals(Object o1,Object o2) throws Exception;
	
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
