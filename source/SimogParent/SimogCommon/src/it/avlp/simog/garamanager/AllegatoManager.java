 package it.avlp.simog.garamanager;

import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.ALLEGATI;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class AllegatoManager extends AccessiDB {
	
	/***********************************************************************************************
	 * Stringa relativa alla PreparedStatement per l'inserimento nella Tabella Allegati di un record;
	 * i parametri interessati sono :
	 * <ul>
	 * <li>nome documento
	 * <li>documento
	 * <li>id lotto
	 * </ul>
	 * 
	 */
	private final static String INSERIMENTO_ALLEGATO =
		"INSERT INTO "
		+ ALLEGATI.TABLE_NAME
		+ " (" + ALLEGATI.ID_GARA
		+ ", " + ALLEGATI.NOME_FILE
		+ ", " + ALLEGATI.NOTE
		+ ", " + ALLEGATI.TIPO_DOC
		+ ", " + ALLEGATI.DATA_UPLOAD		
		+ ", " + ALLEGATI.ESITO_CHECK
		+ ", " + ALLEGATI.PATH_FILE // MEV 34186 3.04.8
		+ " ) VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private final static String SELECT_ALLEGATI = 
		"SELECT * FROM " + ALLEGATI.TABLE_NAME
		+ " WHERE " 
		//+ ALLEGATI.ID_GARA + " = ? "
		//+ " AND " 
		+ ALLEGATI.ID_PUBBLICAZIONE + " = ?"
		+ " AND " + ALLEGATI.DATA_INIZIO_PUBB + " = ?"
		+ " AND " + ALLEGATI.ESITO_CHECK + " IS NULL"
		;


   private final static String SELECT_ALLEGATI_GARA = 
         "SELECT * FROM " + ALLEGATI.TABLE_NAME
         + " WHERE " 
         + ALLEGATI.ID_GARA + " = ? "
         + " AND " + ALLEGATI.ESITO_CHECK + " IS NULL"
         ;

	private final static String SELECT_ALLEGATO = 
		"SELECT * FROM " + ALLEGATI.TABLE_NAME
		+ " WHERE " + ALLEGATI.ID_ALLEGATO + " = ? "
		;

	private final static String SELECT_ALLEGATI_TIPODOC = 
		"SELECT * FROM " + ALLEGATI.TABLE_NAME
		+ " WHERE " + ALLEGATI.ID_GARA + " = ? "
		+ " AND " + ALLEGATI.TIPO_DOC + " = ?"
		+ " AND " + ALLEGATI.ESITO_CHECK + " IS NULL"
		+ " AND " + ALLEGATI.ID_PUBBLICAZIONE + " IS NULL"
		;

	private final static String ELIMINA_ALLEGATO = 
		"DELETE FROM " + ALLEGATI.TABLE_NAME
		+ " WHERE " + ALLEGATI.ID_ALLEGATO + " = ? ";

	private final static String AGGIORNA_ALLEGATO = 
		"UPDATE " + ALLEGATI.TABLE_NAME
		+ " SET " + ALLEGATI.ID_PUBBLICAZIONE + " =?"
		+ " , " + ALLEGATI.DATA_INIZIO_PUBB + " =?"
		+ " WHERE " + ALLEGATI.ID_ALLEGATO + " = ? ";
	
	private final static String AGGIORNA_ALLEGATO_RETTIFICA = 
		"UPDATE " + ALLEGATI.TABLE_NAME
		+ " SET " + ALLEGATI.ID_PUBBLICAZIONE + " =?"
		+ " , " + ALLEGATI.DATA_INIZIO_PUBB + " =?"
		+ " , " + ALLEGATI.NOTE + " =?"
		//+ " , " + ALLEGATI.T_TIPO_DOC + " =?"
		+ " WHERE " + ALLEGATI.ID_ALLEGATO + " = ? ";


	public AllegatoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/***********************************************************************************************
	 * Memorizza un documento sul db e nel filesystem   
	 *
	 * @param allegato
	 * @param boutDocumento
	 * @throws SQLException
	 */
	public AllegatoBean storeAllegato (AllegatoBean allegato) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AllegatoBean lBean = allegato;
				
		try {
			// aggiornamento tabella allegati			
			pstmt = activeConnection.prepareStatement(createInsertQuery(INSERIMENTO_ALLEGATO, ALLEGATI.ID_ALLEGATO));
			pstmt.setInt(1, lBean.getIdGara()); 
	        pstmt.setString(2, lBean.getNomeFile()); 
        	pstmt.setString(3, lBean.getNote());
	        pstmt.setString(4, lBean.getTipoDoc());
	        lBean.setDataUpload(getNow());
	        pstmt.setTimestamp(5, lBean.getDataUpload());
        	pstmt.setString(6, lBean.getEsitoCheck());
        	//MEV 34186 3.04.8
        	pstmt.setString(7, lBean.getPathFile());

        	if(pstmt.execute()){
				rs = pstmt.getResultSet();
				rs.next();
				lBean.setIdAllegato(rs.getInt(ALLEGATI.ID_ALLEGATO));
			}
	        		
		} catch ( SQLException sqle ) {
			String message = "Impossibile Archiviare il documento";
			//L'errore viene loggato qui, il chiamante fa solo un senderror
			logger.fatal(message, sqle );
			throw sqle;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
		return lBean;
	}
	
	public void delete (int idAllegato) throws SQLException {
		
		PreparedStatement pstmt = null;
				
		try {
			// eliminazione dell'allegato in tabela			
			pstmt = activeConnection.prepareStatement(ELIMINA_ALLEGATO);
			pstmt.setInt(1, idAllegato); 

			pstmt.execute();
			
		} catch ( SQLException sqle ) {
			String message = "Impossibile eliminare il documento";
			//L'errore viene loggato qui, il chiamante fa solo un senderror
			logger.fatal(message, sqle );
			throw sqle;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
	}

	public void update (String idAllegato, long idPubblicazione, Timestamp dataInizioPubb) throws SQLException {
		
		PreparedStatement pstmt = null;
				
		try {
			// aggiornamento tabella allegati			
			pstmt = activeConnection.prepareStatement(AGGIORNA_ALLEGATO);
			pstmt.setLong(1, idPubblicazione); 
			pstmt.setTimestamp(2, dataInizioPubb); 
			pstmt.setInt(3, Integer.parseInt(idAllegato)); 

			pstmt.execute();
	        		
		} catch ( SQLException sqle ) {
			String message = "Impossibile aggiornare il documento";
			//L'errore viene loggato qui, il chiamante fa solo un senderror
			logger.fatal(message, sqle );
			throw sqle;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
	}
	
    public void updateRettifica (AllegatoBean allegato, long idPubblicazione, Timestamp dataInizioPubb) throws SQLException {
		
		PreparedStatement pstmt = null;
				
		try {
			// aggiornamento tabella allegati			
			pstmt = activeConnection.prepareStatement(AGGIORNA_ALLEGATO_RETTIFICA);
			pstmt.setLong(1, idPubblicazione); 
			pstmt.setTimestamp(2, dataInizioPubb); 
			pstmt.setString(3, allegato.getNote());
			pstmt.setInt(4, allegato.getIdAllegato()); 
			//pstmt.setString(5, allegato.getTipoDoc()); 
			pstmt.execute();
	        		
		} catch ( SQLException sqle ) {
			String message = "Impossibile aggiornare il documento";
			//L'errore viene loggato qui, il chiamante fa solo un senderror
			logger.fatal(message, sqle );
			throw sqle;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
	}

	/*************************************************************************
	 * Il metodo si occupa di restituire gli allegati presenti per la gara
	 * 
	 * return Restituisce una lista di AllegatoBean
	 * throws SQLException
	 *************************************************************************/
	public List<AllegatoBean> load(AllegatoBean aBean) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<AllegatoBean> listaObj = new ArrayList<AllegatoBean>();
		AllegatoBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ALLEGATI_TIPODOC);

			stmt.setLong(index++, aBean.getIdGara() );
			stmt.setString(index++,aBean.getTipoDoc() );
			
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new AllegatoBean();
				fillBean(rs, bean);				
				listaObj.add(bean);
			}
			listaObj.trimToSize();
			return listaObj;
		}finally{
			close(rs,stmt);
		}
	}
	
	/*************************************************************************
	 * Il metodo si occupa di restituire gli allegati presenti 
	 * in base ad una pubblicazione
	 * return Restituisce una lista di AllegatoBean
	 * throws SQLException
	 *************************************************************************/
	public List<AllegatoBean> getAllegatiByPubblicazione(long idPubblicazione, Timestamp dataInizioPubb) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		
		ArrayList<AllegatoBean> listaObj = new ArrayList<AllegatoBean>();
		AllegatoBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ALLEGATI);

			stmt.setLong(index++, idPubblicazione);
			stmt.setTimestamp(index++, dataInizioPubb);
			
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new AllegatoBean();
				fillBean(rs, bean);				
				listaObj.add(bean);
			}
			listaObj.trimToSize();
			return listaObj;
		}finally{
			close(rs,stmt);
		}
	}

   /*************************************************************************
    * Il metodo si occupa di restituire gli allegati presenti 
    * in base ad una pubblicazione
    * return Restituisce una lista di AllegatoBean
    * throws SQLException
    *************************************************************************/
   public List<AllegatoBean> getAllegatiByGara(long idGara) throws SQLException{
      PreparedStatement stmt = null;
      ResultSet rs = null;
      int index = 1;
      
      ArrayList<AllegatoBean> listaObj = new ArrayList<AllegatoBean>();
      AllegatoBean bean = null;
      try{
         stmt = activeConnection.prepareStatement(SELECT_ALLEGATI_GARA);

         stmt.setLong(index++, idGara);
         
         rs = stmt.executeQuery();
         while(rs.next()){
            bean = new AllegatoBean();
            fillBean(rs, bean);           
            listaObj.add(bean);
         }
         listaObj.trimToSize();
         return listaObj;
      }finally{
         close(rs,stmt);
      }
   }

   /*************************************************************************
	 * Il metodo si occupa di restituire l'allegato per id allegato
	 * 
	 * return Restituisce AllegatoBean
	 * throws SQLException
	 *************************************************************************/
	public AllegatoBean load(long idAllegato) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		//ArrayList<AllegatoBean> listaObj = new ArrayList<AllegatoBean>();
		AllegatoBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ALLEGATO);

			stmt.setLong(index++, idAllegato );
			
			rs = stmt.executeQuery();
			if(rs.next()){
				bean = new AllegatoBean();
				fillBean(rs, bean);				
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * Caricamento bean allegato
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AllegatoBean bean) throws SQLException {
		bean.setIdAllegato(rs.getInt(ALLEGATI.ID_ALLEGATO));
		bean.setIdGara(rs.getInt(ALLEGATI.ID_GARA));
		bean.setNomeFile(rs.getString(ALLEGATI.NOME_FILE));
		bean.setNote(rs.getString(ALLEGATI.NOTE));
		bean.setTipoDoc(rs.getString(ALLEGATI.TIPO_DOC));
		bean.setDataUpload(rs.getTimestamp(ALLEGATI.DATA_UPLOAD));
		bean.setEsitoCheck(rs.getString(ALLEGATI.ESITO_CHECK));	
		bean.setIdPubblicazione(rs.getInt(ALLEGATI.ID_PUBBLICAZIONE));
		bean.setDataInizioPubblicazione(rs.getTimestamp(ALLEGATI.DATA_INIZIO_PUBB));
		bean.setPathFile(rs.getString(ALLEGATI.PATH_FILE)); // MEV 34186 3.04.8
	}
}
