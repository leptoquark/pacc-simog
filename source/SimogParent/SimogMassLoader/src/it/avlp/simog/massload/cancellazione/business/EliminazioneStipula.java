package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.stipula.StipulaBean;

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
public class EliminazioneStipula {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneStipula(Connection con, Logger logger,String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Cancellazione di stipula (e dati correlati) tramite l'id della stipula
	 * 
	 * @param idStipulaString
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idStipulaString) throws SQLException{ //, Exception{
		
		StipulaManager stipulaManager = new StipulaManager(con, logger);
		ResponsabileInizioManager respIniManager = new ResponsabileInizioManager(con, logger);
		PosizAggiudManager posManager = new PosizAggiudManager(con, logger);
		StipulaBean stipulaBean = stipulaManager.loadByIdSimog(Long.parseLong(idStipulaString));
		long idStipula = stipulaBean.getIdStipula();
		Timestamp dataInizioStipula = stipulaBean.getDataInizioStipula();
		boolean esitoOperazione = true;
		
		esitoOperazione = respIniManager.annulla(idStipula,dataInizioStipula);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idStipulaString+"]");

		esitoOperazione = posManager.annulla(idStipula,dataInizioStipula);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idStipulaString+"]");

		esitoOperazione = stipulaManager.annulla(Long.parseLong(idStipulaString), cfUtente);
		
		
		return esitoOperazione;
	}
	
	/**
	 * Cancellazione di stipula (e dati correlati) tramite l'idLocale e l'id di aggiudicazione
	 * 
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale,String idAggiudicazione) throws SQLException{ //, Exception{
		
		StipulaManager stipulaManager = new StipulaManager(con, logger);
		ResponsabileInizioManager respIniManager = new ResponsabileInizioManager(con, logger);
		PosizAggiudManager posManager = new PosizAggiudManager(con, logger);
		StipulaBean stipulaBean = stipulaManager.loadByIdLocale(idLocale, idAggiudicazione);
		long idStipula = stipulaBean.getIdStipula();
		Timestamp dataInizioStipula = stipulaBean.getDataInizioStipula();
		boolean esitoOperazione = true;
		
		esitoOperazione = respIniManager.annulla(idStipula,dataInizioStipula);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = posManager.annulla(idStipula,dataInizioStipula);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per Inizio Lavori: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = stipulaManager.annulla(idLocale, idAggiudicazione, cfUtente);
		
		
		return esitoOperazione;
	}
}
