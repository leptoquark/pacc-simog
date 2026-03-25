package it.avlp.simog.util;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.validatore.SimogValidator;

public abstract class ConversionUtils {

	/**
	 * Metodo che si occupa di rimpiazzare le stringhe vuote con una stringa con zero per evitare
	 * il numeber format exception
	 * 
	 * @param id_tipologica
	 * @return
	 */
	protected String setDefault(String id_tipologica){
		if(id_tipologica != null && "".equals(id_tipologica.trim())){
			return "0";
		}return id_tipologica;
	}
	/**
	 * Introdotto per evitare dei problemi di conversione a numero quando
	 * la stringa non contiene un numero
	 * 
	 * @param id_tipologica
	 * @return
	 */
	protected String setDefaultAncheSeNonValido(String id_tipologica){
		if(id_tipologica != null && "".equals(id_tipologica.trim())){
			return "0";
		//solo se e' un numero va bene
		}else if(id_tipologica != null && !"".equals(id_tipologica.trim())){
			if(SimogValidator.isNumero(id_tipologica)){
				return id_tipologica;
			}		
		}return "0";
	}
	/**
	 * Funzione richiesta da Aosta in quanto loro hanno 
	 * il codice istat da 8 mentre internamente noi gestiamo
	 * un istat di lunghezza 6.
	 * 
	 * Nel caso in cui la stringa in ingresso sia:
	 * - non nulla
	 * - non sia una stringa vuota
	 * - abbia lunghezza maggiore di 6
	 * 
	 * ritorno una stringa contenente gli ultimi 6 caratteri.
	 * Altrimenti ritorno la stringa in ingresso
	 * 
	 * @return String
	 */
	protected String setCodiceIstatDaSei(String codiceIstat){
		String luogoIstat = codiceIstat;
		//se sono valide queste condizioni modifica il codiceIstat
		if(luogoIstat != null && !"".equals(luogoIstat) && luogoIstat.length() > 6){
			return luogoIstat.substring((luogoIstat.length() - 6));
		//altrimenti ritorna l'input string
		}return codiceIstat;
	}
	/**
	 * Equals che ritiene validi ed uguali accoppiate [null] - stringa vuota[""] - ["IT"]
	 * 
	 * @param fromBean
	 * @param fromXML
	 * @return
	 */
	protected boolean equalsCodiceStato(String fromBean, String fromXML){
		String fb = fromBean != null && !fromBean.equalsIgnoreCase(Costanti.CODICE_STATO_ITALIANO) ? fromBean : "";
		String fx = fromXML != null && !fromXML.equalsIgnoreCase(Costanti.CODICE_STATO_ITALIANO)  ? fromXML : "";
		return fb.equalsIgnoreCase(fx);
	}
	/**
	 * Necessario per via della validazione tramite campi che potrebbero non esserci
	 * 
	 * @param spb
	 * @param cf
	 * @param codiceStato
	 * @return
	 */
	protected boolean setPosizione(boolean presenteNelXml,SoggettoPartecipanteBean spb, String cf, String codiceStato){
		if(!presenteNelXml){
			if(spb != null){
				spb.setCodiceFiscale(cf);
				spb.setId_stato(codiceStato);
				return true;
			}
		}return false;
	}
}
