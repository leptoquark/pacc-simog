package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.managers.variante.EventiMotiviVariantiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.variante.VarianteBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Raggruppa tutte le operazioni per effettuare la cancellazione 
 * della scheda varianti.
 * 
 * @author vletizia
 *
 */
public class EliminazioneVarianti {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneVarianti(Connection con, Logger logger, String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Cancellazione di variante (e dati correlati) tramite l'id del variante
	 * 
	 * @param idVarianteString
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idVarianteString) throws SQLException{ //, Exception{
		
		VarianteManager varianteManager = new VarianteManager(con, logger);
		EventiMotiviVariantiManager eventiManager = new EventiMotiviVariantiManager(con, logger);
		VarianteBean varianteBean = varianteManager.loadByIdSimog(Long.parseLong(idVarianteString));
		long idVariante = varianteBean.getIdVariante();
		Timestamp dataInizioVariante = varianteBean.getDataInizioVar();
		boolean esitoOperazione = true;
		
		esitoOperazione = eventiManager.annulla(idVariante,dataInizioVariante);
		if(!esitoOperazione) logger.debug("Non sono presenti Pubblicazioni per i dati comuni: " + "["+idVarianteString+"]");

		esitoOperazione = varianteManager.annulla(Long.parseLong(idVarianteString), cfUtente);
		
		
		return esitoOperazione;
	}
	
	/**
	 * Cancellazione di variante (e dati correlati) tramite l'idLocale e l'id di Aggiudicazione
	 * 
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale,String idAggiudicazione) throws SQLException{ //, Exception{
		
		VarianteManager varianteManager = new VarianteManager(con, logger);
		EventiMotiviVariantiManager eventiManager = new EventiMotiviVariantiManager(con, logger);
		VarianteBean varianteBean = varianteManager.loadByIdLocale(idLocale, idAggiudicazione);
		long idVariante = varianteBean.getIdVariante();
		Timestamp dataInizioVariante = varianteBean.getDataInizioVar();
		boolean esitoOperazione = true;
		
		esitoOperazione = eventiManager.annulla(idVariante,dataInizioVariante);
		if(!esitoOperazione) logger.debug("Non sono presenti Eventi motivi varianti per la varinante: " + "["+idLocale+" , "+idAggiudicazione+"]");

		esitoOperazione = varianteManager.annulla(idLocale, idAggiudicazione, cfUtente);
		
		
		return esitoOperazione;
	}
}
