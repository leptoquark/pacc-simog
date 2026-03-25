package it.avcp.simog.managers.invitati;

import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.INVITATI;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class InvitatiManager extends AccessiDB{
	//update table_name set idStato=eliminato WHERE "+ INVITATI.T_ID_GARA +"=?";
	//public static String CANCELLA_INVITATI="DELETE FROM " + INVITATI.TABLE_NAME +" WHERE "+ INVITATI.T_ID_GARA +"=?";
	public static String CANCELLA_INVITATI="UPDATE " + INVITATI.TABLE_NAME +" SET " +INVITATI.ID_STATO+"=5 WHERE "+ INVITATI.T_ID_GARA +"=?";
	
	public static String INSERT_INVITATI="INSERT INTO " + INVITATI.TABLE_NAME + " ( " +
	//INVITATI.ID_INVITATO + " , " +
	INVITATI.DATA_INIZIO_RECORD + ", " +
	INVITATI.ID_SOGGETTO_PARTECIPANTE + ", " +
	INVITATI.DATA_INIZIO_SOGG + ", " +
	INVITATI.ID_STATO + ", " +
	INVITATI.ID_GARA +
	" ) VALUES(?, ?, ?, ?, ?)";

	
	public static String CARICA_INVITATI="SELECT " +
	INVITATI.T_ID_RECORD + " , " +
	INVITATI.DATA_INIZIO_RECORD + " , " +
	INVITATI.ID_SOGGETTO_PARTECIPANTE + " , " +
	INVITATI.DATA_INIZIO_SOGG + " , " +
	INVITATI.ID_STATO + " , " +
	INVITATI.ID_GARA +
	" FROM " + INVITATI.TABLE_NAME + 
	" WHERE " + INVITATI.ID_STATO + "=?" +
	" AND " + INVITATI.ID_GARA +"=?";
	
	
	public static String SOGGETTO_PARTECIPANTE="SELECT " +
	SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + ", " +
	SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + ", " +
	SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE + ", " +
	SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + ", " +
	SOGGETTI_PARTECIPANTI.T_CAMERA_COMMERCIO + ", " +
	SOGGETTI_PARTECIPANTI.T_PARTITA_IVA + ", " + 
	SOGGETTI_PARTECIPANTI.T_CIVICO + ", " +
	SOGGETTI_PARTECIPANTI.T_CAP + ", " +
	SOGGETTI_PARTECIPANTI.T_PROVINCIA + ", " +
	SOGGETTI_PARTECIPANTI.T_CITTA + ", " +
	SOGGETTI_PARTECIPANTI.T_CF_RAPPRESENTANTE + ", " +
	SOGGETTI_PARTECIPANTI.T_DATA_FINE_SOGG + ", " +
	SOGGETTI_PARTECIPANTI.T_NOME + ", " +
	SOGGETTI_PARTECIPANTI.T_COGNOME + ", " +
	SOGGETTI_PARTECIPANTI.T_INDIRIZZO +", " +
	SOGGETTI_PARTECIPANTI.T_ID_STATO +
	" FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME +
	" WHERE " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + "=?" +
	" AND " + SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + "=?";
	
	
	
	

	
	
	/* <b>GaraManager</b><br>
	 * Costruttore 
	 * @param activeConnection Connection
	 * @param logger Logger
	 */
	public InvitatiManager( Connection activeConnection, Logger logger ) {
		super ( activeConnection, logger );
	}
	
	
	public void inserisciInvitato (InvitatoBean invitato ) {
		//logger.debug(ObjectIntrospector.propertiesInfo(InvitatoBean.class, invitato));
		
		Timestamp time= getNow();
		invitato.setDataInizioInvitato(time);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
		stmt = activeConnection.prepareStatement(INSERT_INVITATI);
		int index = 1;
		stmt.setTimestamp(index++, invitato.getDataInizioInvitato());
		stmt.setLong(index++, invitato.getSoggettoPartecipante().getIdSoggettoPartecipante());
		stmt.setTimestamp(index++, invitato.getSoggettoPartecipante().getDataInizioSogg());
		int statoScheda=StatiScheda.CONFERMATO;
		stmt.setLong(index++, statoScheda);
		stmt.setLong(index++, invitato.getIdGara());
		
		stmt.execute();
		}catch(SQLException e){
			//e.printStackTrace();
		   logger.fatal(e.getMessage());
		}
		finally{
			close(rs, stmt);
		}	

	}
	
	
	public void cancellaInvitato(Long idGara){
		
        // logger.debug(ObjectIntrospector.propertiesInfo(InvitatoBean.class, invitato));
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
		stmt = activeConnection.prepareStatement(CANCELLA_INVITATI);
		stmt.setLong(1, idGara);
		
		stmt.execute();
		
		}catch(SQLException e){
			//e.printStackTrace();
			logger.fatal(e.getMessage());
		}
		finally{
			close(rs, stmt);
		}	
		
	}
	
	
	public ArrayList<InvitatoBean> carica(long idGara){
		
		//InvitatoBean invitato=new InvitatoBean();
		SoggettoPartecipanteBean soggettoPartecipante=new SoggettoPartecipanteBean();
		ArrayList<InvitatoBean> invitati = new ArrayList<InvitatoBean>();	
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
      		stmt = activeConnection.prepareStatement(CARICA_INVITATI);
      		int index = 1;
      		stmt.setInt(index++, StatiScheda.CONFERMATO);
      		stmt.setLong(index++, idGara);
      		
      		rs = stmt.executeQuery();
      		
      		while(rs.next()){
      			InvitatoBean invitato=new InvitatoBean();
      			int idRecord=rs.getInt("ID_RECORD");
      			Timestamp data_inizio_record=rs.getTimestamp(INVITATI.DATA_INIZIO_RECORD);
      			long idSoggettoPartecipante=rs.getLong(INVITATI.ID_SOGGETTO_PARTECIPANTE);
      			Timestamp dataInizioSoggetto=rs.getTimestamp(INVITATI.DATA_INIZIO_SOGG);
      			int idStato=rs.getInt(INVITATI.ID_STATO);
      			
      			soggettoPartecipante=getSoggettoParecipante(idSoggettoPartecipante, dataInizioSoggetto);
      			
      			invitato.setIdInvitato(idRecord);
      			invitato.setDataInizioInvitato(data_inizio_record);
      			invitato.setSoggettoPartecipante(soggettoPartecipante);
      			invitato.setIdGara(idGara);
      			invitato.setIdStato(idStato);
      			invitati.add(invitato);
      		}
	
		}catch(SQLException e){
			//e.printStackTrace();
		   logger.fatal(e.getMessage());
		}
		finally{
           close(rs, stmt);
       }

		return invitati;
	}
	
	public SoggettoPartecipanteBean getSoggettoParecipante(long idSoggettoPartecipante, Timestamp dataInizioSoggetto){
		
		SoggettoPartecipanteBean soggettoPartecipante = new SoggettoPartecipanteBean();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
      		stmt = activeConnection.prepareStatement(SOGGETTO_PARTECIPANTE);
      		int index = 1;
      		stmt.setLong(index++, idSoggettoPartecipante);
      		stmt.setTimestamp(index++, dataInizioSoggetto);
      		
      		rs = stmt.executeQuery();
      		
      		while(rs.next()){
      			
      			 Timestamp dataInizioSogg=rs.getTimestamp("DATA_INIZIO_SOGG");
      			 String codiceFiscale=rs.getString("CODICE_FISCALE");
      			 String denominazione=rs.getString("DENOMINAZIONE");
      			 String cameraCommercio=rs.getString("CAMERA_COMMERCIO");
      			 String partitaIva=rs.getString("PARTITA_IVA");
      			 String civico=rs.getString("CIVICO");
      			 String cap=rs.getString("CAP");
      			 String provincia=rs.getString("PROVINCIA");
      			 String citta=rs.getString("CITTA");
      			 String cfRappresentante=rs.getString("CF_RAPPRESENTANTE");
      			 String nome=rs.getString("NOME");
      			 String cognome=rs.getString("COGNOME");
      			 String indirizzo=rs.getString("INDIRIZZO");
      			 String id_stato=rs.getString("ID_STATO");
      			  
      			 soggettoPartecipante.setIdSoggettoPartecipante(idSoggettoPartecipante);
      			 soggettoPartecipante.setDataInizioSogg(dataInizioSogg);
      			 soggettoPartecipante.setCodiceFiscale(codiceFiscale);
      			 soggettoPartecipante.setDenominazione(denominazione);
      			 soggettoPartecipante.setCameraCommercio(cameraCommercio);
      			 soggettoPartecipante.setPartitaIva(partitaIva);
      			 soggettoPartecipante.setCivico(civico);
      			 soggettoPartecipante.setCap(cap);
      			 soggettoPartecipante.setProvincia(provincia);
      			 soggettoPartecipante.setCitta(citta);
      			 soggettoPartecipante.setCfRappresentante(cfRappresentante);
      			 soggettoPartecipante.setNome(nome);
      			 soggettoPartecipante.setCognome(cognome);
      			 soggettoPartecipante.setIndirizzo(indirizzo);
      			 soggettoPartecipante.setId_stato(id_stato);
      			
      		}
      		
      		
   		}catch(SQLException e){
   			//e.printStackTrace();
   			logger.fatal(e.getMessage());
   		}
        finally{
           close(rs, stmt);
       }

		return soggettoPartecipante;
	}
}
