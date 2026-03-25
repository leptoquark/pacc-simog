package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avlp.simog.beans.collaudo.CollaudoBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Raggruppa tutte le operazioni per effettuare la cancellazione 
 * della scheda collaudo.
 * 
 * @author vletizia
 *
 */
public class EliminazioneCollaudo {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneCollaudo(Connection con, Logger logger, String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Cancellazione di Collaudo (e dati correlati) tramite l'id collaudo
	 * 
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idSimog) throws SQLException{//, Exception{
		
		CollaudoManager collaudoManager = new CollaudoManager(con, logger);
		ResponsabileCollManager respCollManager = new ResponsabileCollManager(con, logger);
		CollaudoBean collaudoBean = collaudoManager.loadByIdSimog(Long.parseLong(idSimog));
		long idCollaudo = collaudoBean.getIdCollaudo();
		Timestamp dataInizioCollaudo = collaudoBean.getDataIniColl();
		boolean esitoOperazione = true;
		
		esitoOperazione = respCollManager.annulla(idCollaudo,dataInizioCollaudo);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per il Collaudo: " + "["+idSimog+"]");
		esitoOperazione = collaudoManager.annulla(Long.parseLong(idSimog), cfUtente);
		
		
		return esitoOperazione;
	}
	
	/**
	 * Cancellazione di Collaudo (e dati correlati) tramite l'idLocale e l'id di aggiudicazione
	 * 
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale,String idAggiudicazione) throws SQLException{//, Exception{
		
		CollaudoManager collaudoManager = new CollaudoManager(con, logger);
		ResponsabileCollManager respCollManager = new ResponsabileCollManager(con, logger);
		CollaudoBean collaudoBean = collaudoManager.loadByIdLocale(idLocale, idAggiudicazione);
		long idCollaudo = collaudoBean.getIdCollaudo();
		Timestamp dataInizioCollaudo = collaudoBean.getDataIniColl();
		boolean esitoOperazione = true;
		
		esitoOperazione = respCollManager.annulla(idCollaudo,dataInizioCollaudo);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per il Collaudo: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = collaudoManager.annulla(idLocale, idAggiudicazione, cfUtente);
		
		
		return esitoOperazione;
	}
}
