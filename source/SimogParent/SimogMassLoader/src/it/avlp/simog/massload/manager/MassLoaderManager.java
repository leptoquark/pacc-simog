/**
 * 
 */
package it.avlp.simog.massload.manager;

import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.db.AccessiDB;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @author vletizia
 *
 */
public class MassLoaderManager extends AccessiDB {

	/**
	 * @param currentActiveConnection
	 * @param logger
	 */
	public MassLoaderManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/**
	 * XXX: VL - PATCH - in data 20-01-2010 per garantire la retrocompatibilita' e' stato previsto che nel caso in cui
	 * 			l'id locale sia nullo sulla base dati debba essere aggiornato qualora sia presente sul file xml.
	 * 			la regola e' valida solamente per le schede singole in quanto le multiple non sono bindabili (non si ha modo
	 * 			di sapere di quale scheda si sta parlando).
	 * 			Dato che sono presenti dei controlli preventivi(controlli formali) per garantire che qualora che siano
	 * 			uguali gli id locali l'operazione non procede, posso inserire l'aggiornamento in modo programmatico
	 * 			ad ogni aggiornamento di scheda singola senza dover per altro portarmi a presso qualche flag che mi
	 * 			indichi che debba effettuare l'operazione.
	 * 
	 * Occorre gestire un feedback ?
	 * 
	 * Per maggiori informazioni vedi il commento della traccia della superclasse
	 * @see it.avlp.simog.db.AccessiDB#aggiornaIdLocale(java.lang.String, java.lang.String, java.lang.String, long, java.sql.Timestamp, java.lang.String)
	 * @see it.avlp.simog.db.AccessiDB#idLocaleNecessitaDiAggiornamento(java.lang.String, java.lang.String, java.lang.String, long, java.sql.Timestamp, java.lang.String)
	 */
	
	public boolean aggiornaIdLocale(String tableName, String nomeCampoId, String nomeCampoDataInizio, long idSimog, Timestamp dataInizioSimog, String idLocale, SchedaSpecificaValidationBean fillEsitoPositivo, ArrayList<SchedaSpecificaValidationBean> esiti) throws Exception{
		boolean esito = false;
		boolean isDaAggiornareIdLocale = false;
		
		try{
			// controllo se id locale e' nullo o da aggiornare 
			isDaAggiornareIdLocale = super.idLocaleNecessitaDiAggiornamento(tableName, nomeCampoId, nomeCampoDataInizio, idSimog, dataInizioSimog, idLocale);

		}catch (Exception e) {
			logger.error("Errore durante la ricerca del record il cui id locale e' da aggiornare");
		}
		
		try{
			// se e' da aggiornare
			if(isDaAggiornareIdLocale){

				esito = super.aggiornaIdLocale(tableName, nomeCampoId, nomeCampoDataInizio, idSimog, dataInizioSimog, idLocale);
				
				// se esito positivo aggiungo la info ai validation
				if(esito){
					esiti.add(fillEsitoPositivo);
				}
			}
		}catch (Exception e) {
			logger.error("Errore durante l'aggiornamento dell'id locale");
			throw e;
		}
		return esito;
	}

}
