package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Raggruppa tutte le operazioni per effettuare la cancellazione 
 * della scheda dati comuni
 * 
 * @author vletizia
 *
 */
public class EliminazioneInfoComuni {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneInfoComuni(Connection con, Logger logger, String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}

	/**
	 * Cancellazione di info comuni (e dati correlati) tramite l'id info comuni
	 * 
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idSimog) throws SQLException { 
		InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
		PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(con, logger);
		boolean esitoOperazione = true;
		
		esitoOperazione = pubblicazioneManager.annulla(Long.parseLong(idSimog), cfUtente);
		if(!esitoOperazione) logger.debug("Non sono presenti Pubblicazioni per i dati comuni: " + "["+idSimog+"]");

		esitoOperazione = infoComuniManager.annulla(Long.parseLong(idSimog), cfUtente);
		
		return esitoOperazione;
		
	}
	
	/**
	 * Cancellazione di info comuni (e dati correlati) tramite l'idLocale e l'id info comuni
	 * 
	 * @param idLocale
	 * @param CIG
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale, String CIG) throws SQLException{
		InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
		PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(con, logger);
		boolean esitoOperazione = true;
		esitoOperazione = pubblicazioneManager.annulla(idLocale, CIG, cfUtente);
		if(!esitoOperazione) logger.debug("Non sono presenti Pubblicazioni per i dati comuni: " + "["+idLocale+" , "+CIG+"]");
			
		esitoOperazione = infoComuniManager.annulla(idLocale, CIG, cfUtente);
		
		return esitoOperazione;
	}
}
