package it.avcp.simog.managers.aggiudicazione;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione dei dati relativi alle prese incarico
 *
 */
public class PresaInCaricoManager extends AccessiDB {

	

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public PresaInCaricoManager(Connection currentActiveConnection,
			Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	
	/**
	 * metodo che ritorna gli id delle aggiudicazioni appartenti ai daticomuni di cui id
	 * 
	 * @param idInfo long
	 * @param dataInizioInfo Timestamp
	 * @return List&lt;Long&gt;
	 * @throws SQLException
	 */
	public List<Long> getDatiAggiudicazione(long idInfo, Timestamp dataInizioInfo)throws SQLException{
		String getAgg = "SELECT " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE
		+ " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME
		+ " WHERE " + INFO_AGGIUDICAZIONI.ID_INFO + " = ? "
		+ " AND " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ?";
		PreparedStatement stmt = null;
		int index = 1;
		ResultSet rs = null;
		ArrayList<Long> idList = new ArrayList<Long>();
		
		try{
			stmt = activeConnection.prepareStatement(getAgg);
			stmt.setLong(index++, idInfo);
			stmt.setTimestamp(index++, dataInizioInfo);
			rs = stmt.executeQuery();
			while(rs.next())
				idList.add(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
			
			idList.trimToSize();
			return idList;
		}finally{
			close(rs, stmt);
		}
		
	}
	
	private String BASE_HEADER =
		"SELECT " + STATI_SCHEDA.T_ID_STATO
		+ " FROM " + STATI_SCHEDA.TABLE_NAME + ", $TBL$ ";
	
	
	
	private String WHERE_ALL =
		" WHERE $CMP_ID$ = ? "
		+ " AND " + STATI_SCHEDA.T_ID_STATO + "= $STO$" 
		+ " AND " + STATI_SCHEDA.T_ID_STATO + "=" + StatiScheda.ANNULLAMENTO_RICHIESTA;
	
	
	
	/**
	 * metodo check richieste annullamento
	 * 
	 * param id long
	 * param tabella String
	 * param campoId String
	 * param campoStato String
	 * return boolean
	 * throws SQLException
	 */
	private boolean hasRichAnn(long id,String tabella, String campoId, String campoStato)throws SQLException{
		String lQuery = BASE_HEADER.replace("$TBL$", tabella);
		lQuery += WHERE_ALL.replace("$CMP_ID$", campoId);
		lQuery = lQuery.replace("$STO$", campoStato);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(lQuery);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			return rs.next();
		}finally{
			close(rs, stmt);
		}
		
	}
	
	/**
	 * metodo che controlla se info comuni di cui id ha una rischiesta annullamento
	 * 
	 * @param idInfo long
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean hasRichAnnInfoComuni(long idInfo) throws SQLException{
		return hasRichAnn(idInfo, INFO_AGGIUDICAZIONI.TABLE_NAME, INFO_AGGIUDICAZIONI.T_ID_INFO, INFO_AGGIUDICAZIONI.T_ID_STATO);
	}
	
	/**
	 * metodo che controlla se l'aggiudicazione di cui id ha una rischiesta annullamento
	 * 
	 * @param idAggiudicazione long
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean hasRichAnnAggiudicazioni(long idAggiudicazione) throws SQLException{
		return hasRichAnn(idAggiudicazione, AGGIUDICAZIONI.TABLE_NAME, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE, AGGIUDICAZIONI.T_ID_STATO);
	}

}
