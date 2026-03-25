package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Raggruppa tutte le operazioni per effettuare la cancellazione 
 * della scheda inizio lavori
 * 
 * @author vletizia
 *
 */
public class EliminazioneInizioLavori {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneInizioLavori(Connection con, Logger logger,String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Cancellazione di inizio lavori (e dati correlati) tramite l'id del inizio lavori
	 * 
	 * @param idInizioLavoriString
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idInizioLavoriString) throws SQLException{ //, Exception{
		
		InizioLavoriManager inizioManager = new InizioLavoriManager(con, logger);
		ResponsabileInizioManager respIniManager = new ResponsabileInizioManager(con, logger);
		PosizAggiudManager posManager = new PosizAggiudManager(con, logger);
		InizioLavoriBean inizioBean = inizioManager.loadByIdSimog(Long.parseLong(idInizioLavoriString));
		long idInizio = inizioBean.getIdInizioLavori();
		Timestamp dataInizioLavori = inizioBean.getDataInizioLavori();
		boolean esitoOperazione = true;
		
		esitoOperazione = respIniManager.annulla(idInizio,dataInizioLavori);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idInizioLavoriString+"]");

		esitoOperazione = posManager.annulla(idInizio,dataInizioLavori);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idInizioLavoriString+"]");

		esitoOperazione = inizioManager.annulla(Long.parseLong(idInizioLavoriString), cfUtente);
		
		
		return esitoOperazione;
	}
	
	/**
	 * Cancellazione di inizio lavori (e dati correlati) tramite l'idLocale e l'id di aggiudicazione
	 * 
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale,String idAggiudicazione) throws SQLException{ //, Exception{
		
		InizioLavoriManager inizioManager = new InizioLavoriManager(con, logger);
		ResponsabileInizioManager respIniManager = new ResponsabileInizioManager(con, logger);
		PosizAggiudManager posManager = new PosizAggiudManager(con, logger);
		InizioLavoriBean inizioBean = inizioManager.loadByIdLocale(idLocale, idAggiudicazione);
		long idInizio = inizioBean.getIdInizioLavori();
		Timestamp dataInizioLavori = inizioBean.getDataInizioLavori();
		boolean esitoOperazione = true;
		
		esitoOperazione = respIniManager.annulla(idInizio,dataInizioLavori);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = posManager.annulla(idInizio,dataInizioLavori);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = inizioManager.annulla(idLocale, idAggiudicazione, cfUtente);
		
		
		return esitoOperazione;
	}
}
