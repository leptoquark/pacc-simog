package it.avlp.simog.beans.aggiudicazione;

public enum TipoAggiudicazione {
	A,S,E,Q;
	/* A = aggiudicazione
	 * S = sottosoglia 
	 * E = esclusi
	 * Q = adesione ad accordo quadro
	 */	
	public static TipoAggiudicazione fromString(String str){
		if(str == null || "".equals(str.trim()))
			return TipoAggiudicazione.A;
		else
			return TipoAggiudicazione.valueOf(str);
		
	}
	

}
