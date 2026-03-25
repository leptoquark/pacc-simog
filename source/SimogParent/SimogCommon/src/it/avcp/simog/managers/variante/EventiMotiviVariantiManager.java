package it.avcp.simog.managers.variante;
import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.common.servlet.ParametriServletEventiMotiviVariante;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.EVENTI_MOTIVI_VARIANTI;
import it.avlp.simog.db.generated.MOTIVI_VARIANTE;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class EventiMotiviVariantiManager extends AccessiDB implements IAnnullamentoMulti{
	
	
	public EventiMotiviVariantiManager(Connection currentActiveConnection,Logger logger){
		super(currentActiveConnection,logger);
	}
	
	
	/******************************************************
	 *              query per la DELETE
	 ******************************************************/
	private static String QUERY_DELETE_EVENTI_MOTIVI_VAR = 
		"DELETE FROM "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME+
		" WHERE " + EVENTI_MOTIVI_VARIANTI.ID_VARIANTE + " = ?"+
		" AND "+ EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR + " = ?";
	// PP bah! +	" AND "+EVENTI_MOTIVI_VARIANTI.ID_STATO + " <> "+StatiScheda.ANNULLAMENTO_RICHIESTA;
	
	/***************************************************************************************************
	 * Cancella la riga della tabella {@link EVENTI_MOTIVI_VARIANTI} 
	 * relativamente all'ID della Variante 
	 *
	 * @param idVariante
	 * @param dataInizioVar
	 * @return 0 o il numero di righe cancellate
	 * @throws SQLException
	 ***************************************************************************************************/
	public int deleteRecord (long idVariante, Timestamp dataInizioVar) throws SQLException{
		
		int numRow=-1;
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELETE_EVENTI_MOTIVI_VAR);
			logger.debug("query per la delete di evento motivi variante : "+QUERY_DELETE_EVENTI_MOTIVI_VAR);
			int index = 1;
			stmt.setLong(index++, idVariante);
			
			stmt.setObject(index++,dataInizioVar);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(null, stmt);
		}
		return numRow;
	}
	
	
	/***************************************************
	 *              QUERY PER LA INSERT
	 ***************************************************/
	
	private final String INSERT_EVENTI_MOTIVI_VAR = " INSERT INTO " + EVENTI_MOTIVI_VARIANTI.TABLE_NAME +" ("
       	+EVENTI_MOTIVI_VARIANTI.ID_STATO+","
	    +EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD+","
		+EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD+","
		+EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR+","
		+EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR+","
		
		+EVENTI_MOTIVI_VARIANTI.ID_VARIANTE
		+") VALUES (?,?,?,?,?,?) ";
	
	

	
	
	private void update (EventiMotiviVariantiBean emvBean, String cfUtente, boolean confirm ) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			pstmt = activeConnection.prepareStatement(INSERT_EVENTI_MOTIVI_VAR);
			
			if(confirm){
				pstmt.setLong(index++, StatiScheda.CONFERMATO);
				pstmt.setTimestamp(index++, getNow()); // data fine 
			}else{
				//un aggiornamento normale
				pstmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				pstmt.setNull(index++, Types.TIMESTAMP);//data fine record
			}
			pstmt.setTimestamp(index++, getNow());
			pstmt.setTimestamp(index++, emvBean.getDataIniVariante());
			pstmt.setLong(index++, emvBean.getIdMotivoVariante());
			pstmt.setLong(index++, emvBean.getIdVariante());
			
			pstmt.execute();
			
			
		
		}
		finally {
			close(rs,pstmt);
		}
	}
	
	
	/**********************************************************************
	 *                        SAVE & CONFIRM
	 **********************************************************************/
	/**
	 * metodo per il salvataggio di un'oggetto eventiMotiviVarianti
	 * 
	 * @param bean EventiMotiviVarianti
	 * @param cfUtente
	 * @throws SQLException
	 */
	public void save(EventiMotiviVariantiBean bean, String cfUtente)throws SQLException{
		update(bean, cfUtente,false);
	}
	
	/**
	 * metodo per la conferma di un motivo evento variante
	 * 
	 * @param bean EventiMotiviVariantiBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void confirm(EventiMotiviVariantiBean bean, String cfUtente)throws SQLException{
		update(bean,cfUtente, true);
	}
	
	
	
	/********************************************************************
	 *    query per la selezione di un Evento Motivo variante
	 ********************************************************************/
	public final String SELECT_ONE_EVENTO_MOTIVO_VAR= "SELECT "
		+ EVENTI_MOTIVI_VARIANTI.ID_RECORD
		+ ", " + EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD
	    + ", " + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD
	    + ", " + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR
	    + ", " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO
	    + ", " + EVENTI_MOTIVI_VARIANTI.ID_VARIANTE 
	    
//	    + ", " + buildRichAnnQuery(ParametriServletEventiMotiviVariante.TAB_SCHEDA_EVENTI_MOTIVI_VAR, EVENTI_MOTIVI_VARIANTI.ID_RECORD,null) 
//		+" AS "+STATI_SCHEDA.DESCRIZIONE
		
		+ " FROM " + EVENTI_MOTIVI_VARIANTI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME
		
		+ " WHERE " + EVENTI_MOTIVI_VARIANTI.T_ID_RECORD + " = ? AND "
		+ EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD + " = ?  AND "
		+ EVENTI_MOTIVI_VARIANTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")"; 
	
	
	
	/**                                        
	 * metodo per il caricamento di un motivo variante di cui id e data inizio
	 *                                        
	 * @param idVariante long
	 * @param dataInizioVar Timestamp
	 * @return EventiMotiviVariantiBean
	 * @throws SQLException
	 **/
	
	public EventiMotiviVariantiBean loadOne(long idVariante, Timestamp dataInizioVar)throws SQLException{
		List<EventiMotiviVariantiBean> lista = load(idVariante, dataInizioVar, false);
		if(lista!= null && lista.size() > 0)
			return lista.get(0);
		else return null;
		
	}
	
	
	/*********************************************************************************
	 *    query per la selezione di pi� eventi motivi variante
	 *********************************************************************************/
	
	public  final String SELECT_MANY_EVENTI_MOTIVO_VAR = "SELECT "
		+ EVENTI_MOTIVI_VARIANTI.ID_RECORD
		+ ", " + EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD
	    + ", " + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD  
	    + ", " + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR
	    + ", " + EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR
	    + ", " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO
	    + ", " + EVENTI_MOTIVI_VARIANTI.ID_VARIANTE
	    + ", " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO 
//	    + ", " + STATI_SCHEDA.T_DESCRIZIONE + 
//	    " + " +
//		   buildRichAnnQuery(ParametriServletEventiMotiviVariante.TAB_SCHEDA_EVENTI_MOTIVI_VAR, EVENTI_MOTIVI_VARIANTI.T_ID_RECORD,null) 
//		+" AS "+STATI_SCHEDA.DESCRIZIONE
		
	    + " FROM " + EVENTI_MOTIVI_VARIANTI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME   
	    
	    + " WHERE " + EVENTI_MOTIVI_VARIANTI.T_ID_VARIANTE + " = ? AND "
	    + EVENTI_MOTIVI_VARIANTI.T_DATA_INIZIO_VAR+ " = ?  AND "
	    + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	    +" AND (" + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")"; 
	
	
	/**
	 * metodo per il caricamento di tutti i motivi
	 * 
	 * @param idVariante
	 * @param dataInizioVariante
	 * @return List&lt;EventiMotiviVariantiBean&gt;
	 * @throws SQLException
	 */
	public List<EventiMotiviVariantiBean> loadMany(long idVariante, Timestamp dataInizioVariante) throws SQLException{
		return load(idVariante, dataInizioVariante, true);
	}
	
	
	/***********************************************************************************
	 *                                load
	 ***********************************************************************************/
	private List<EventiMotiviVariantiBean> load(long idVariante, Timestamp date, boolean byAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<EventiMotiviVariantiBean> listaSchede = new ArrayList<EventiMotiviVariantiBean>();
		try{
			if(byAggiudicazione) 
				stmt = activeConnection.prepareStatement(SELECT_MANY_EVENTI_MOTIVO_VAR);
			else 
				stmt = activeConnection.prepareStatement(SELECT_ONE_EVENTO_MOTIVO_VAR);
			stmt.setLong(index++, idVariante );
			stmt.setTimestamp(index++, date);
			rs = stmt.executeQuery();
			while(rs.next()){
				EventiMotiviVariantiBean emvBean = new EventiMotiviVariantiBean();
				emvBean.setDataFinRecord(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD));
				emvBean.setDataIniRecord(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD));
				emvBean.setDataIniVariante(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR));
				emvBean.setIdMotivoVariante(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR));
				emvBean.setIdRecord(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_RECORD));
				emvBean.setIdStato(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_STATO));
				emvBean.setIdVariante(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_VARIANTE));
				listaSchede.add(emvBean);
			}
			listaSchede.trimToSize();
			return listaSchede;
		}finally{
			close(rs,stmt);
		}
	}
	
	
	/**************************************************************************
	 *        query per la selezione degli eventi motivi variante
	 **************************************************************************/
	
	private final String QUERY_SELECT_EVENTI_MOTIVI_VARIANTI = "SELECT "+
	EVENTI_MOTIVI_VARIANTI.T_ID_RECORD+ " ,"+
	EVENTI_MOTIVI_VARIANTI.T_DATA_INIZIO_RECORD + " ,"+
	EVENTI_MOTIVI_VARIANTI.T_DATA_FINE_RECORD + " ,"+
	EVENTI_MOTIVI_VARIANTI.T_ID_STATO + " ,"+
	EVENTI_MOTIVI_VARIANTI.T_ID_VARIANTE + " ,"+
	EVENTI_MOTIVI_VARIANTI.T_DATA_INIZIO_VAR + " ,"+
	EVENTI_MOTIVI_VARIANTI.T_ID_MOTIVO_VAR + " ,"+
	MOTIVI_VARIANTE.T_DESCRIZIONE +	
	" FROM " + 
	EVENTI_MOTIVI_VARIANTI.TABLE_NAME + " ," +
	MOTIVI_VARIANTE.TABLE_NAME +
	" WHERE " + 
	EVENTI_MOTIVI_VARIANTI.T_ID_VARIANTE + " = ? AND " +
	EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR + " = ? " + " AND " +
	EVENTI_MOTIVI_VARIANTI.T_ID_MOTIVO_VAR + " = " + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR ;
	
	
	/**
	 * Metodo che recupera la lista degli EventiMotiviVarianti associati ad una "Variante"
	 * 
	 * @param idVariante
	 * @param dataIniVariante
	 * @return List&lt;EventiMotiviVariantiBean&gt;
	 * @throws SQLException
	 */
	public List<EventiMotiviVariantiBean> loadMany(Long idVariante,Timestamp dataIniVariante)throws SQLException{
		logger.debug("motivivarianti: (Long "+idVariante+",Timestamp" +dataIniVariante+")");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<EventiMotiviVariantiBean> ris = new ArrayList<EventiMotiviVariantiBean>();
		try{
			EventiMotiviVariantiBean emvb = null;
			stmt = activeConnection.prepareStatement(QUERY_SELECT_EVENTI_MOTIVI_VARIANTI);
			int index = 1;
			stmt.setLong(index++, idVariante);
			stmt.setTimestamp(index++, dataIniVariante);
			rs = stmt.executeQuery();
			while(rs.next()){
				emvb = new EventiMotiviVariantiBean();
				emvb.setIdRecord(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_RECORD));
				emvb.setDataIniRecord(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD));
				emvb.setDataFinRecord(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD));
				emvb.setIdStato(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_STATO));
				emvb.setIdVariante(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_VARIANTE));
				emvb.setDataIniVariante(rs.getTimestamp(EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR));
				emvb.setIdMotivoVariante(rs.getLong(EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR));
				emvb.setDescrizione(rs.getString(MOTIVI_VARIANTE.DESCRIZIONE));
				ris.add(emvb);
			}
		}finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}

	/*******************************************************************************************************
	 * Metodo per la storicizzazione di un record
	 * 
	 * @param id_variante
	 * @param data_inizio_variante
	 * @param vecchiaData
	 * @return boolean
	 * @throws SQLException
	 *******************************************************************************************************/
	
	public boolean copyRecord(long id_variante,Timestamp data_inizio_variante, Timestamp vecchiaData) throws SQLException{
		/**
		 *  Prima di cambiare i dati si vuole salvare la variante
		 *  
		 */
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME+ " SET "
			+ EVENTI_MOTIVI_VARIANTI.ID_STATO+ " = ?, "
			+ EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD+ " = ?, "
//adds
			+ EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR+ " = ? "
//end
			
			+" WHERE "
			+ EVENTI_MOTIVI_VARIANTI.T_ID_VARIANTE+" = ?"
			+" AND "+ EVENTI_MOTIVI_VARIANTI.T_DATA_INIZIO_VAR +" = ?"
			+" AND "+ EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			"INSERT INTO "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME+" ("
			+EVENTI_MOTIVI_VARIANTI.ID_RECORD 	
			+","+EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD  // set
			+","+EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_RECORD // set
			+","+EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR
			+","+EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR
			+","+EVENTI_MOTIVI_VARIANTI.ID_STATO // set 
			+","+EVENTI_MOTIVI_VARIANTI.ID_VARIANTE+" ) "
			
			+"SELECT "
			+EVENTI_MOTIVI_VARIANTI.ID_RECORD	
			+", ?"
			+", ?"
			+"," + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR
			+","+EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR
			+", ?"
			+","+EVENTI_MOTIVI_VARIANTI.ID_VARIANTE

			+" FROM "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME
			+" WHERE "
			+ EVENTI_MOTIVI_VARIANTI.ID_VARIANTE+" = ? AND "
			+ EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR + " = ?"
			+" AND " + EVENTI_MOTIVI_VARIANTI.ID_STATO+" = "+StatiScheda.CONFERMATO;
		
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,EVENTI_MOTIVI_VARIANTI.TABLE_NAME));
			stmt.setNull(index++, Types.TIMESTAMP ); //data fine record
			stmt.setTimestamp(index++, getNow()); // data inizio record			
			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
			//where
			stmt.setLong(index++,id_variante);
			stmt.setTimestamp(index++,data_inizio_variante );
			
			int rowsCopied = stmt.executeUpdate();
			if(rowsCopied > 0){
				index = 1;
				stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
				stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
				stmt2.setTimestamp(index++, vecchiaData); //setto dataInizioRecord con oldDataInizioVariante
				stmt2.setTimestamp(index++, vecchiaData); //setto dataInizioVar con oldDataInizioVariante
				//where
				stmt2.setLong(index++, id_variante);
				stmt2.setTimestamp(index++, data_inizio_variante);
				rowsCopied = stmt2.executeUpdate();
				return (rowsCopied>0);
			}
			else {
				logger.debug("EVENTI_MOTIVI_VARIANTE.copyRecord: Nessun record da copiare");
				return true;
			}

				
		
		}
		finally{
			close(null, stmt2);
			close(null, stmt);
		}
	}
	
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME+
		" SET " + EVENTI_MOTIVI_VARIANTI.ID_STATO + " = ?," 
		+ EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD + " = " + buildGetDate()
		+ " WHERE " + EVENTI_MOTIVI_VARIANTI.ID_VARIANTE + " = ?"
		+ " AND " + EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR + " = ?";
	
	/**
	 * Metodo per l'aggiornamento dello stato di un MotiviEventiVarianti
	 * 
	 * @param idRecord
	 * @param dataInizioRecord
	 * @param statoScheda
	 * @return int - affected rows
	 * @throws SQLException
	 */
	public int updateRecord(long idRecord, Timestamp dataInizioRecord, String statoScheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

	
			stmt.setString(1, statoScheda);
			stmt.setLong(2, idRecord);
			stmt.setTimestamp(3,dataInizioRecord);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	
	public final String SELECT_DESCR_MOTIVO = "SELECT " + MOTIVI_VARIANTE.T_DESCRIZIONE + " FROM "
	 + EVENTI_MOTIVI_VARIANTI.TABLE_NAME + ", " + MOTIVI_VARIANTE.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME + 
	 " WHERE " + EVENTI_MOTIVI_VARIANTI.T_ID_MOTIVO_VAR + "=" + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR +
	 " AND " + EVENTI_MOTIVI_VARIANTI.T_ID_VARIANTE + " =? " +
	 " AND " + EVENTI_MOTIVI_VARIANTI.T_DATA_INIZIO_VAR + " =? " + 
	 " AND " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO 
	 +" AND (" + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
	 +" OR " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	/**
	 * Metodo che si occupa del recupero dei bean EventiMotiviVarianti associati ad una "variante" 
	 * 
	 * @param idVariante
	 * @param dataInizioVariante
	 * @return List&lt;EventiMotiviVariantiBean&gt;
	 * @throws SQLException
	 * @deprecated
	 */
	public List<EventiMotiviVariantiBean> laodDescrizione(long idVariante, Timestamp dataInizioVariante)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		EventiMotiviVariantiBean emvb = null;
		List<EventiMotiviVariantiBean> listaMotivi = new ArrayList<EventiMotiviVariantiBean>();
		try{
			stmt = activeConnection.prepareStatement(SELECT_DESCR_MOTIVO);
			stmt.setLong(1, idVariante);
			stmt.setTimestamp(2, dataInizioVariante);
			rs = stmt.executeQuery();
			while(rs.next()){
				emvb = new EventiMotiviVariantiBean();
				emvb.setDescrizione(rs.getString(MOTIVI_VARIANTE.DESCRIZIONE));
				listaMotivi.add(emvb);
			}			
			return listaMotivi;
		}catch(Exception e){
			throw new SQLException(e.getMessage());
		}finally{
			close(rs,stmt);
			//return listaMotivi;
		}
		
	}
	

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
	public boolean annulla(long idSchedaPadre, Timestamp dataInizioSchedaPadre)throws SQLException {
		return _annulla(idSchedaPadre, dataInizioSchedaPadre);
		
	}
	/**
	 * @param idVariante
	 * @param dataInizioVariante
	 * @throws SQLException
	 */
	private boolean _annulla(long idVariante, Timestamp dataInizioVariante) throws SQLException{
		PreparedStatement stmt = null;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_EVENTI_VARIANTI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idVariante);
			stmt.setTimestamp(index++, dataInizioVariante);
			return stmt.executeUpdate() > 0;
		}finally {
			close(null,stmt);
		}		
	}	
}
