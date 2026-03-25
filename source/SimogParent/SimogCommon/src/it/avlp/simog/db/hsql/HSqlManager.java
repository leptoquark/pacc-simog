package it.avlp.simog.db.hsql;


import it.avlp.simog.db.AccessiDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 
 * @author Fabio Cirillo
 * 
 * HSqlManager si occupa della creazione e della popolazion delle tabelle: ISTAT, NUTS, CPV.
 * Tali tabelle saranno memorizzati in memoria(memoria volatile) e il nome del db sarà mynamedb.
 * Alla chiusura dell'applicazione le tabelle saranno cancellate.
 *
 */

public class HSqlManager extends AccessiDB{
	
	private static HSqlManager mInstance = null;
	private static Connection hSqlConnection;
		
	/**
	 * La variabile hSqlConnection di tipo Connection viene valorizzata una sola volta
	 */
	private HSqlManager(Connection simogConnection,Logger logger){
		super(simogConnection, logger);	
		
		logger.debug("HSqlManager - init");
		getConnection();
		createInsertTable(this.activeConnection);
	}
	
	public synchronized static HSqlManager getInstance(Connection simogConnection,Logger logger){
		if(mInstance == null){
			mInstance = new HSqlManager(simogConnection, logger);			
		}
		return mInstance;
	}
	
	/**
	 * @return la connection al db hsqldb
	 */
	public synchronized Connection getConnection(){
		
		if(hSqlConnection == null){
			try {
				logger.debug("HSqlManager - getConnection");
				Class.forName("org.hsqldb.jdbcDriver" );
				hSqlConnection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
			} catch (ClassNotFoundException e) {
				logger.fatal(e.getMessage());
				e.printStackTrace();
			}catch (SQLException e) {
				logger.fatal(e.getMessage());
				e.printStackTrace();
			}
		}
			return hSqlConnection;
	}
	
	
	/**
	 * 
	 * @param simogConnection 
	 * 
	 * simogConnection restituisce la connessione al db di simog.
	 * Tramite essa possimo prelevare i dati necessari a popolare le tabelle ISTAT, NUTS, CPV.
	 * Sarà questo metodo infatti a occuparsi della creazione e popolazione delle tabelle sopra elencaate.
	 */
	private void createInsertTable(Connection simogConnection){
		
		try {
			long timeInizio=  System.currentTimeMillis();
			long timeFine;
			long totale;
			int i=0;
			ResultSet rs;

			
			//ISTAT
			
			String idComune;
			String id_provincia;
			String denominazione;
			String data_fine_validita;
			String data_ultima_modifica;

			logger.debug("HSqlManager - caricamento dati ISTAT");
			
			String codiciIstat="CREATE TABLE CODICI_ISTAT ( id_comune varchar(6) NOT NULL, id_provincia varchar(3) NOT NULL, denominazione varchar(100) NOT NULL, data_fine_validita varchar(8) NULL, data_ultima_modifica varchar(8) NULL )" ;	
			String indexCodiciIstat="CREATE INDEX IX_PROVINCIA ON CODICI_ISTAT(id_provincia)" ; 
			String pkCodiciIstat="ALTER TABLE CODICI_ISTAT ADD CONSTRAINT PK_CODICI_ISTAT PRIMARY KEY (id_comune)" ;
			String regProv = "CREATE TABLE REGIONE_PROVINCIA ( id_regione varchar(2) NOT NULL, id_provincia varchar(3) NOT NULL, denom_regione varchar(50) NOT NULL, denom_provincia varchar(50) NOT NULL, sigla_provincia char(2) NOT NULL, data_fine_validita varchar(8) NULL, data_ultima_modifica varchar(8) NULL ); ";
			String indxProvincia_Reg="CREATE INDEX IX_PROVINCIA_REG ON REGIONE_PROVINCIA(id_provincia)";
			String indexRegioneProvincia=" ALTER TABLE REGIONE_PROVINCIA ADD CONSTRAINT PK_REGIONE_PROVINCIA PRIMARY KEY (id_provincia, id_regione)";
			String viewIstat="CREATE VIEW COMUNI_VIEW AS SELECT REGIONE_PROVINCIA.id_regione, REGIONE_PROVINCIA.denom_regione, REGIONE_PROVINCIA.id_provincia,  REGIONE_PROVINCIA.denom_provincia, REGIONE_PROVINCIA.sigla_provincia, CODICI_ISTAT.id_comune,  CODICI_ISTAT.denominazione  FROM  CODICI_ISTAT INNER JOIN  REGIONE_PROVINCIA ON CODICI_ISTAT.id_provincia = REGIONE_PROVINCIA.id_provincia  WHERE     (CODICI_ISTAT.data_fine_validita IS NULL) AND (REGIONE_PROVINCIA.data_fine_validita IS NULL)"; 
			
			PreparedStatement st = hSqlConnection.prepareStatement(codiciIstat);
            PreparedStatement stOut = hSqlConnection.prepareStatement(codiciIstat);
	        st.execute();
	        st = hSqlConnection.prepareStatement(indexCodiciIstat);
	        st.execute();
	        st = hSqlConnection.prepareStatement(pkCodiciIstat);
	        st.execute();
	        st = hSqlConnection.prepareStatement(regProv);
	        st.execute();
	        st = hSqlConnection.prepareStatement(indxProvincia_Reg);
	        st.execute();
	        st = hSqlConnection.prepareStatement(indexRegioneProvincia);
	        st.execute();
	        st = hSqlConnection.prepareStatement(viewIstat);
	        st.execute();
	        
	        st = simogConnection.prepareStatement("SELECT * FROM CODICI_ISTAT");   
            stOut = hSqlConnection.prepareStatement("insert into CODICI_ISTAT values(?, ?, ?, ?, ?)");

            st.execute(); 
	        rs=st.getResultSet();
	        while(rs.next()){
	        	idComune=rs.getString("id_comune");
	        	id_provincia=rs.getString("id_provincia");
	        	denominazione=rs.getString("denominazione");
	        	data_fine_validita=rs.getString("data_fine_validita");
	        	data_ultima_modifica=rs.getString("data_ultima_modifica");
	        	stOut.setString(1, idComune);
	        	stOut.setString(2, id_provincia);
	        	stOut.setString(3, denominazione);
	        	stOut.setString(4, data_fine_validita);
	        	stOut.setString(5, data_ultima_modifica);
	        	stOut.execute();
	        	
	        	i++;
	        }
	        
	        close(null, stOut);
	        close(rs, st);
	        
	        //REGIONE_PROVINCIA
	        String idRegione;
	        String id_prov;
	        String denom_regione;
	        String denom_provincia;
	        String sigla_provincia; //sigla_provincia char(2) NOT NULL
	        
	    	st = simogConnection.prepareStatement("SELECT * FROM REGIONE_PROVINCIA");   
            stOut = hSqlConnection.prepareStatement("insert into REGIONE_PROVINCIA values(?, ?, ?, ?, ?, ?, ?)");

            st.execute(); 
	        rs=st.getResultSet();
	        while(rs.next()){
	        	idRegione=rs.getString("id_regione");
	        	id_prov=rs.getString("id_provincia");
	        	denom_regione=rs.getString("denom_regione");
	        	denom_provincia=rs.getString("denom_provincia");
	        	sigla_provincia=rs.getString("sigla_provincia");
	        	data_fine_validita=rs.getString("data_fine_validita");
	        	data_ultima_modifica=rs.getString("data_ultima_modifica");
	        	
	        	stOut.setString(1, idRegione);
	        	stOut.setString(2, id_prov);
	        	stOut.setString(3, denom_regione);
	        	stOut.setString(4, denom_provincia);
	        	stOut.setString(5, sigla_provincia);
	        	stOut.setString(6, data_fine_validita);
	        	stOut.setString(7, data_ultima_modifica);
	        	stOut.execute();
	        
	        	i++;
	        }
	        
	        close(null, stOut);
	        close(rs, st);

	        //NUTS
	        	        
	        String id_nuts;
			String descrizione;
			int livello;
			String id_regione;
			String date_fine_validita;
			String date_ultima_modifica;
	        int n=0;

			logger.debug("HSqlManager - caricamento dati NUTS");
	        
	        String codiciNut="CREATE TABLE CODICI_NUTS ( id_nuts varchar(10) NOT NULL, descrizione varchar(50) NOT NULL, livello int NOT NULL, id_regione varchar(2) NULL, data_fine_validita varchar(8) NULL, data_ultima_modifica varchar(8) NULL )" ;	
			String indexCodiciNut="CREATE INDEX IX_CODICI_NUTS ON CODICI_NUTS(id_regione)" ; 
			String indexLivelloCodice="CREATE INDEX IX_LIV_CODICE ON CODICI_NUTS(livello, id_nuts)" ; 
			String pkCodiciNut="ALTER TABLE CODICI_NUTS ADD CONSTRAINT PK_CODICI_NUTS PRIMARY KEY (id_nuts)" ;
	        
			PreparedStatement stNuts = hSqlConnection.prepareStatement(codiciNut);
			stNuts.execute();
			stNuts = hSqlConnection.prepareStatement(indexCodiciNut);
			stNuts.execute();
			stNuts = hSqlConnection.prepareStatement(indexLivelloCodice);
			stNuts.execute();
			stNuts = hSqlConnection.prepareStatement(pkCodiciNut);
			stNuts.execute();
	        
			stNuts = simogConnection.prepareStatement("SELECT * FROM CODICI_NUTS");   
            stOut = hSqlConnection.prepareStatement("insert into CODICI_NUTS values(?, ?, ?, ?, ?, ?)");

            stNuts.execute(); 
	        rs=stNuts.getResultSet();
	        while(rs.next()){
	        	id_nuts=rs.getString("id_nuts");
	        	descrizione=rs.getString("descrizione");
	        	livello=rs.getInt("livello");
	        	id_regione=rs.getString("id_regione");
	        	date_fine_validita=rs.getString("data_fine_validita");
	        	date_ultima_modifica=rs.getString("data_ultima_modifica");
	        	
	        	stOut.setString(1, id_nuts);
	        	stOut.setString(2, descrizione);
	        	stOut.setInt(3, livello);
	        	stOut.setString(4, id_regione);
	        	stOut.setString(5, date_fine_validita);
	        	stOut.setString(6, date_ultima_modifica);
	        	
	        	stOut.execute();
	       
	        	n++;
	        }
	        
	        close(null, stOut);
	        close(rs, stNuts);

	        // CPV
	        String id_div;
	        String id_grp;
	        String id_cls;
	        String id_ctg;
	        String id_vox;
	        String chk;
			String versione;
			int k=0;
	        
			logger.debug("HSqlManager - caricamento dati CPV");
	        
	        String cpvEU="CREATE TABLE CPVEU ( id_div char(2) NOT NULL, id_grp char(1) NOT NULL, id_cls char(1) NOT NULL, id_ctg char(1) NOT NULL, id_vox char(3) NOT NULL, chk char(1) NOT NULL, descrizione varchar(512) NOT NULL, data_fine_validita varchar(8) NULL, data_ultima_modifica varchar(8) NULL, versione varchar(5) NOT NULL)" ;	
			String indexCPVEU_1="CREATE INDEX IX_CPVEU_1 ON CPVEU(id_grp)" ; 
			String indexCPVEU="CREATE INDEX IX_CPVEU ON CPVEU(id_div)";
			String indexCPVEU_5="CREATE INDEX IX_CPVEU_5 ON CPVEU(descrizione)";
			String indexCPVEU_4="CREATE INDEX IX_CPVEU_4 ON CPVEU(id_vox)";
			String indexCPVEU_3="CREATE INDEX IX_CPVEU_3 ON CPVEU(id_ctg)";
			String indexCPVEU_2="CREATE INDEX IX_CPVEU_2 ON CPVEU(id_cls)";	        
			String pk_CPVEU="ALTER TABLE CPVEU ADD CONSTRAINT PK_CPVEU PRIMARY KEY (id_cls, id_ctg, id_div, id_grp, id_vox, versione)" ;
	        
			PreparedStatement stCPV = hSqlConnection.prepareStatement(cpvEU);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU_1);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU_5);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU_4);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU_3);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(indexCPVEU_2);
			stCPV.execute();
			stCPV = hSqlConnection.prepareStatement(pk_CPVEU);
			stCPV.execute();
	        
			stCPV = simogConnection.prepareStatement("SELECT * FROM CPVEU");   
			stOut = hSqlConnection.prepareStatement("insert into CPVEU values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");			
			
			stCPV.execute(); 
	        rs=stCPV.getResultSet();
	        
	        while(rs.next()){
	        	id_div=rs.getString("id_div");
	        	id_grp=rs.getString("id_grp");
	        	id_cls=rs.getString("id_cls");
	        	id_ctg=rs.getString("id_ctg");
	        	id_vox=rs.getString("id_vox");
	        	chk=rs.getString("chk");
	        	descrizione=rs.getString("descrizione");
	        	date_fine_validita=rs.getString("data_fine_validita");
	        	date_ultima_modifica=rs.getString("data_ultima_modifica");
	        	versione=rs.getString("versione");
	        	
	        	stOut.setString(1, id_div);
	        	stOut.setString(2, id_grp);
	        	stOut.setString(3, id_cls);
	        	stOut.setString(4, id_ctg);
	        	stOut.setString(5, id_vox);
	        	stOut.setString(6, chk);
	        	stOut.setString(7, descrizione);
	        	stOut.setString(8, date_fine_validita);
	        	stOut.setString(9, date_ultima_modifica);
	        	stOut.setString(10, versione);
	        	
	        	stOut.execute();
	        	
	        	k++;
	        }
	        
           close(null, stOut);
           close(rs, stCPV);
 	        
           timeFine=System.currentTimeMillis();
	       totale=(timeFine-timeInizio);
		       
	       logger.debug("HSqlManager - tabelle caricate in: " + String.valueOf(totale) + " millisecondi");

		}catch (SQLException e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		}catch (Exception e) {
	        logger.fatal("ERROR: failed to load HSQLDB JDBC driver.");
	        e.printStackTrace();
	        return;
	
		}
		
	}
	
	
	/**
	 * Quando tutte le operazioni sono state concluse la connessione può essere chiusa
	 */
	
//	public void closeConnetion(){
//		
//		try {
//			hSqlConnection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	

}