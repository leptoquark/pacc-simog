/**
 * 
 */
package it.avcp.simog.manager.paesi;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.STATI_ESTERI;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author vletizia
 *
 */
public class PaesiManager extends AccessiDB {

	
	/**
	 * 
	 */
	public PaesiManager() {}

	/**
	 * @param currentActiveConnection
	 * @param logger
	 */
	public PaesiManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/**
	 * metodo per il recupero della lista dei paesi attenzione!
	 * per una questione di ordinamento la chiave � la descrizione
	 * e il valore la chiave
	 * 
	 * @return Map&lt;String,String&gt; id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadPaesi(Object data)throws SQLException{
		return super.getTipologica(STATI_ESTERI.TABLE_NAME, 				 
				STATI_ESTERI.DESCRIZIONE, 
				STATI_ESTERI.ID_STATO,
				STATI_ESTERI.DATA_FINE_VALIDITA,data);
	}
	
	/**
	 * metodo per il caricamento della descrizione un singolo paese
	 * 
	 * @param id_stato
	 * @param data
	 * @return
	 */
	public Map<String,String> loadPaese(String id_stato,Object data)throws SQLException{
		Map<String,String> result = new HashMap<String,String>();
		Map<String,String> resultAll = super.getTipologica(STATI_ESTERI.TABLE_NAME, 				 
					  			STATI_ESTERI.ID_STATO, 
					  			STATI_ESTERI.DESCRIZIONE,
					  			STATI_ESTERI.DATA_FINE_VALIDITA,data);
		if(!"".equals(id_stato) && id_stato != null)
			result.put(resultAll.get(id_stato), id_stato);
		else
			result.put(Costanti.NOME_STATO_ITALIANO, Costanti.CODICE_STATO_ITALIANO);
		return result;
	}
}
