package it.avlp.simog.massload.util;

import java.util.ArrayList;

/**
 * Classe tool per tenere memoria dei cig sui quali e' stata effettuata una operazione
 * per evitare di tentare di ripetere l'operazione.
 * 
 * E' usato per:
 * - la cancellazione by CIG 
 * - per il caricamento delle situazione by CIG
 * 
 * @author vletizia
 *
 */
public class CigTool {

	private ArrayList<keyValue> listOfStructuredCig = new ArrayList<keyValue>();
	
	/**
	 * Aggiungi il cig e se l'operazione e' stata effettuata o meno
	 * s
	 * @param cig
	 * @param operazioneEffettuata
	 */
	public void add(String cig, boolean operazioneEffettuata){
		for(keyValue key : listOfStructuredCig){
			if(key.equals(cig)){
				if(key.isOperazioneEffettuata() == operazioneEffettuata){
					return;
				}else{
					key.operazioneEffettuata = operazioneEffettuata;
					return;
				}
			}
		}
		this.listOfStructuredCig.add(new keyValue(operazioneEffettuata, cig));
	}
	/**
	 * Controlla se e' gia stata effettuata l'operazione per il cig , cercando nella
	 * sua lista interna..
	 * 
	 * @param cig
	 * @return
	 */
	public boolean isAlreadyOperazioneEffettuata(String cig){
		for(keyValue key : listOfStructuredCig){
			if(key.equals(cig)){
				if(key.isOperazioneEffettuata()){
					return true;
				}
			}
		}return false;
	}
	
	
	
	
	
	private class keyValue{
		
		boolean operazioneEffettuata;
		String cig;
		
		public keyValue(boolean operazioneEffettuata, String cig) {
			super();
			this.operazioneEffettuata = operazioneEffettuata;
			this.cig = cig;
		}
		public boolean equals(String cig){
			return this.cig.equals(cig);
		}
		public boolean isOperazioneEffettuata(){
			return this.operazioneEffettuata;
		}
		
	}
}
