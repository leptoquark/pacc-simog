package it.avlp.simog.fllusso.action;

import it.avlp.simog.beans.SchedaState;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.validatore.RichAnnCancController;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Classe da implementare che si occupa di controllare la presenza di 
 * richieste (cancellazione o annullamento), viene usata in fase di "Load" della situazione attuale del flusso.
 * 
 * @author vletizia
 *
 */
public class RichiesteAction {

	private Connection con;
	private Logger logger;
	
	public RichiesteAction(Connection con, Logger logger){
		this.con = con;
		this.logger = logger;
	}
	/**
	 * Controlla che ci sia qualche richiesta(cancellazione o annullamento) appesa.
	 * 
	 * <strong><warn>Gli id e le date inizio volute sono solamente quelle di Info comuni e di Aggiudicazioni!</warn></strong>
	 * 
	 * @param nomeScheda
	 * @param idScheda per tutte le schede deve essere id_aggiudicazione
	 * 					per i dati comuni deve essere id_info
	 * @param dataInizioidScheda per tutte le schede deve essere data_inizio_aggiudicazione
	 * 					per i dati comuni deve essere data_inizio_info
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public int isInRichiesta(String nomeScheda, boolean multipla, long idScheda, Timestamp dataInizioScheda) throws SQLException, Exception{
		
		RichAnnCancController chk = new RichAnnCancController(this.con, this.logger);
		
		SchedaState schedaState = chk.getSchedaState(nomeScheda, multipla, idScheda, dataInizioScheda);
		
		if(schedaState.isRichDelete()) return StatoScheda.RICHIESTACANCELLAZIONE;
		if(schedaState.isRichAnn()) return StatoScheda.RICHIESTAANNULLAMENTO;
		
		return StatoScheda.NESSUNARICHIESTA;
	}
//	/**
//	 * @param nomeScheda
//	 * @param multipla
//	 * @param idScheda
//	 * @param dataInizioScheda
//	 * @return
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public boolean isInRichiestaAnnullamento(String nomeScheda, boolean multipla, long idScheda, Timestamp dataInizioScheda) throws SQLException, Exception{
//
//		RichAnnCancController chk = new RichAnnCancController(this.con, this.logger);
//				
//		return chk.getSchedaState(nomeScheda, multipla, idScheda, dataInizioScheda).isRichAnn();
//	}
//	/**
//	 * @param nomeScheda
//	 * @param multipla
//	 * @param idScheda
//	 * @param dataInizioScheda
//	 * @return
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public boolean isInRichiestaCancellazione(String nomeScheda, boolean multipla, long idScheda, Timestamp dataInizioScheda) throws SQLException, Exception{
//
//		RichAnnCancController chk = new RichAnnCancController(this.con, this.logger);
//
//		return chk.getSchedaState(nomeScheda, multipla, idScheda, dataInizioScheda).isRichDelete();
//	}
}
