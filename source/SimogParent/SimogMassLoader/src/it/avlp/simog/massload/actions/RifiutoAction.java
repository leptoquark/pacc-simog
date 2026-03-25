package it.avlp.simog.massload.actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Classe da implementare che si occupa della gestione degli stati di richiesta cancellazione e annullamento
 * e del loro rifiuto.
 * 
 * @author vletizia
 *
 */
/**
 * @author vletizia
 *@deprecated
 */
public class RifiutoAction {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public RifiutoAction(Connection con, Logger logger, String cfUtente){
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	public boolean rifiuta(String blocco, String idScheda, boolean isCancellazione) throws SQLException, Exception{
		if(isCancellazione) return rifiutaRichiestaCancellazione(blocco, idScheda);
		return rifiutaRichiestaAnnullamento(blocco, idScheda);
	}
	public boolean rifiutaRichiestaAnnullamento(String blocco, String idScheda) throws SQLException, Exception{
		return false;
	}
	public boolean rifiutaRichiestaCancellazione(String blocco, String idScheda) throws SQLException, Exception{
		return false;
	}
}
