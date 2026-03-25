package it.avlp.simog.garamanager;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.REL_PUBB_AGG;
import it.avlp.simog.db.generated.SIMOG_PUBBLICAZIONE_BANDO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class PubblicazioneAggiudicazioneManager extends AccessiDB {

	/***********************************************************************
	 * <b>PubblicazioneAggiudicazioneManager</b><br>
	 * Costruttore 
	 * @param activeConnection Connection
	 * @param logger Logger
	 */
	public PubblicazioneAggiudicazioneManager ( Connection activeConnection, Logger logger ) {
		super ( activeConnection, logger );
	}
	
    private final String INSERT_RECORD_FROM_GARA =	
		"INSERT INTO " + REL_PUBB_AGG.TABLE_NAME
		+ " ( " +
		REL_PUBB_AGG.ID_GARA + ", " +
		REL_PUBB_AGG.ID_PUBBLICAZIONE + ", " +
		REL_PUBB_AGG.DATA_INIZIO_PUBB +
		" ) " + " VALUES (?,?,?)";
    
    private final String INSERT_RECORD_FROM_AGGIUDICAZIONE =	
		"INSERT INTO " + REL_PUBB_AGG.TABLE_NAME
		+ " ( " +
		REL_PUBB_AGG.ID_GARA + ", " +
		REL_PUBB_AGG.ID_PUBBLICAZIONE + ", " +
		REL_PUBB_AGG.DATA_INIZIO_PUBB + ", " +
		REL_PUBB_AGG.ID_AGGIUDICAZIONE + ", " +
		REL_PUBB_AGG.DATA_INIZIO_AGG +
		" ) " + " VALUES (?,?,?,?,?)";
		
	
	/**
	 * Inserisce un record nella tabella di scambio pubblicazione aggiudicazione
	 * provenendo da bando di gara o rettifica bando di gara
	 * @param idGara
	 * @param idPubblicazione
	 * @param dataInizioPubblicazione
	 * @throws SQLException
	 */
	public void insertFromGara(long idGara, long idPubblicazione, Timestamp dataInizioPubb) throws SQLException {

		String query = INSERT_RECORD_FROM_GARA;
		PreparedStatement pstmt = activeConnection.prepareStatement(query);		
		try{			
			int posCounter = 1;		
			pstmt.setLong(posCounter++, idGara);
			pstmt.setLong(posCounter++, idPubblicazione);
			pstmt.setTimestamp(posCounter++, dataInizioPubb);
			pstmt.execute();
		}
		finally{
				close(null,pstmt);
		}				
	}	
	
	/**
	 * Inserisce un record nella tabella di scambio pubblicazione aggiudicazione
	 * provenendo da avviso di aggiudicazione o rettifica di avviso
	 * @param idGara
	 * @param idAggiudicazione
	 * @param dataInizioAgg
	 * @throws SQLException
	 */
	public void insertFromAggiudicazione(long idGara, long idPubblicazione, Timestamp dataInizioPubb, long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException {

		String query = INSERT_RECORD_FROM_AGGIUDICAZIONE;
		PreparedStatement pstmt = activeConnection.prepareStatement(query);		
		try{			
			int posCounter = 1;	
			pstmt.setLong(posCounter++, idGara);
			pstmt.setLong(posCounter++, idPubblicazione);
			pstmt.setTimestamp(posCounter++, dataInizioPubb);
			pstmt.setLong(posCounter++, idAggiudicazione);
			pstmt.setTimestamp(posCounter++, dataInizioAgg);
			pstmt.execute();
		}
		finally{
				close(null,pstmt);
		}				
	}	
}
