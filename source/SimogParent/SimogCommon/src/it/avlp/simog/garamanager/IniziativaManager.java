package it.avlp.simog.garamanager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.AMBITO_LOTTO_INIZIATIVA;
import it.avlp.simog.db.generated.AUTODICHIARAZIONI_ADESIONE;
import it.avlp.simog.db.generated.EAGG_CATEGORIE_SOGLIE;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INIZIATIVE_CATEGORIE;
import it.avlp.simog.db.generated.INIZIATIVE_SOGG_AGGR;
import it.avlp.simog.db.generated.INIZIATIVE_TERRITORI;
import it.avlp.simog.db.generated.LISTA_SOGGETTI_AGGREGATORI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.TIPO_AMBITO_LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

public class IniziativaManager extends AccessiDB {

	
	public IniziativaManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	
	
	/**
	 * Recupera la lista delle iniziative in base ai parametri
	 * @param pCIG CIG dell'iniziativa
	 * @param pTERRITORI regioni dell'iniziativa
	 * @param pCATEGORIE categorie merceologiche dell'iniziativa
	 * @param pAMBITO ambiti dell'iniziativa
	 * @param idIniziativa id dell'ininziativa
	 * @param valid se true, recupera solo le iniziative con stato attivo
	 * @return lista dell'iniziativa
	 * @throws SQLException
	 */
	public List<IniziativaSoggAggr> getIniziative(String pCIG, 
												List<String> pTERRITORI, 
												List<String> pCATEGORIE,
												List<String> pAMBITO,
												Long idIniziativa,
												boolean valid) throws SQLException {
	    String select = "SELECT DISTINCT "+INIZIATIVE_SOGG_AGGR.TABLE_NAME+".* FROM "+INIZIATIVE_SOGG_AGGR.TABLE_NAME;
	    
	    if(pTERRITORI != null && pTERRITORI.size()>0) 
	    	select+=" INNER JOIN "+INIZIATIVE_TERRITORI.TABLE_NAME+" ON "+INIZIATIVE_TERRITORI.T_ID_INIZIATIVA+" = "+INIZIATIVE_SOGG_AGGR.T_ID_INIZIATIVA;
	    
	    if(pCATEGORIE != null && pCATEGORIE.size()>0) 
	    	select+=" INNER JOIN "+INIZIATIVE_CATEGORIE.TABLE_NAME+" ON "+INIZIATIVE_CATEGORIE.T_ID_INIZIATIVA+" = "+INIZIATIVE_SOGG_AGGR.T_ID_INIZIATIVA;
	    
	    if(pAMBITO != null && pAMBITO.size()>0) {
	    	select+=" INNER JOIN "+AMBITO_LOTTO_INIZIATIVA.TABLE_NAME+" ON "+AMBITO_LOTTO_INIZIATIVA.T_ID_INIZIATIVA+" = "+INIZIATIVE_SOGG_AGGR.T_ID_INIZIATIVA;
	        select+=" INNER JOIN "+TIPO_AMBITO_LOTTO.TABLE_NAME+" ON "+TIPO_AMBITO_LOTTO.T_ID_TIPO_AMBITO_LOTTO+" "+AMBITO_LOTTO_INIZIATIVA.ID_AMBITO_LOTTO;
	    }
	    
	    String where = " WHERE 1=1 "+((pCIG!=null && !"".equals(pCIG)) ? "AND "+INIZIATIVE_SOGG_AGGR.CIG+"= ?": "");
	    if(pTERRITORI != null && pTERRITORI.size()>0) {
	    	where+=" AND "+INIZIATIVE_TERRITORI.ID_REGIONE+" IN (";
	    	for(int i=0;i<pTERRITORI.size();i++) {
	    		where+="?";
	    		if((i+1) < pTERRITORI.size())
	    			where+=",";
	    	}
	    	where+=")";
	    }
	    if(pCATEGORIE != null && pCATEGORIE.size()>0) {
	    	where+=" AND "+INIZIATIVE_CATEGORIE.COD_CATEGORIA+" IN (";
	    	for(int i=0;i<pCATEGORIE.size();i++) {
	    		where+="?";
	    	if((i+1) < pCATEGORIE.size())
	    		where+=",";
	    	}
	    	where+=")";
	    }
	    
	    if(pAMBITO != null && pAMBITO.size()>0) {
	    	where+=" AND "+TIPO_AMBITO_LOTTO.COD_AMBITO_LOTTO+" IN (";
	    	for(int i=0;i<pAMBITO.size();i++) {
	    		where+="?";
	    		if((i+1) < pAMBITO.size())
		    		where+=",";
	    	}
	    	where+=")";
	    }
		
	    if(valid)
	    	where+=" AND "+INIZIATIVE_SOGG_AGGR.STATO_INIZIATIVA+"= ?";
	    
	    if(idIniziativa!=null && idIniziativa!=0)
	    	where += " AND "+INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA+"= ?";
	    
		List<IniziativaSoggAggr> list = new ArrayList<IniziativaSoggAggr>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ " + select+where+ " ]");
		
		stmt = activeConnection.prepareStatement(select+where);
		int idx=0;
		
		if(pCIG!=null && !"".equals(pCIG))
			stmt.setString(++idx, pCIG);
		
	    if(pTERRITORI != null && pTERRITORI.size()>0) 
	    	for(String regione : pTERRITORI)
	    		stmt.setString(++idx, regione);
	    
	    if(pCATEGORIE != null && pCATEGORIE.size()>0) 
	    	for(String cat : pCATEGORIE)
	    		stmt.setLong(++idx, Long.parseLong(cat));
	    
	    if(pAMBITO != null && pAMBITO.size()>0) 
	    	for(String ambito : pAMBITO)
	    		stmt.setString(++idx, ambito);
		
	    if(valid)
	    	stmt.setString(++idx, Costanti.FLAG_VALORE_SI);
	    
	    if(idIniziativa!=null && idIniziativa!=0)
	    	stmt.setLong(++idx, idIniziativa);
	    
	    try {

			rs = stmt.executeQuery();

			while(rs.next()) {
				IniziativaSoggAggr el = new IniziativaSoggAggr();
				el.setIdIniziativa(rs.getLong(INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA));
				el.setIdGara(rs.getLong(INIZIATIVE_SOGG_AGGR.ID_GARA));
				el.setCIG(rs.getString(INIZIATIVE_SOGG_AGGR.CIG));
				el.setDescrizioneSoggAggr(rs.getString(INIZIATIVE_SOGG_AGGR.DESCRIZIONE_SOGG_AGGR));
				el.setDescrizioneIniziativa(rs.getString(INIZIATIVE_SOGG_AGGR.DESCRIZIONE_INIZIATIVA));
				el.setSSAARif(rs.getString(INIZIATIVE_SOGG_AGGR.SSAA_RIF));
				el.setStatoIniziativa(rs.getString(INIZIATIVE_SOGG_AGGR.STATO_INIZIATIVA));
				el.setFlagConfrontoComp(rs.getString(INIZIATIVE_SOGG_AGGR.FLAG_CONFRONTO_COMP));
				el.setNote(rs.getString(INIZIATIVE_SOGG_AGGR.NOTE));
				el.setLink(rs.getString(INIZIATIVE_SOGG_AGGR.LINK));
				list.add(el);
			}
			
		} finally {
			close(rs, stmt);
		}
	    
	    if(list.size()>0) {
	    	for(IniziativaSoggAggr el : list) {
	    		el.setListaCatIniziativa(this.getCategorieIniziativa(el.getIdIniziativa()));
	    		el.setListaTerritoriIniziativa(this.getTerritoriIniziativa(el.getIdIniziativa()));
	    		el.setAmbitoLotto(this.getAmbitiLottoIniziativa(el.getIdIniziativa()));
	    	}
	    }
	    
		return list;
	}
	
	/**
	 * Verifica se esiste gia' una iniziativa per il CIG indicato
	 * @param CIG
	 * @return
	 * @throws SQLException
	 */
	public long checkIniziativa(String CIG) throws SQLException {
		long idIniziativa= 0;
		
		String query = "SELECT "+INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA+" FROM "+INIZIATIVE_SOGG_AGGR.TABLE_NAME+" WHERE "+INIZIATIVE_SOGG_AGGR.CIG+ " = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ " + query+ " ]");
		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setString(1, CIG);
			rs =  stmt.executeQuery();
			if(rs.next())
				idIniziativa = rs.getLong(1);
		} finally {
			close(rs, stmt);
		}
		return idIniziativa;
	}
	
	/**
	 * Recupera i territori di una iniziativa
	 * @param idIniziativa
	 * @return
	 * @throws SQLException
	 */
	public List<String> getTerritoriIniziativa(Long idIniziativa) throws SQLException {
		List<String> terr = new ArrayList<String>();
		
		String query = "SELECT "+INIZIATIVE_TERRITORI.ID_REGIONE
				       +" FROM "+INIZIATIVE_TERRITORI.TABLE_NAME
				       +" WHERE "+INIZIATIVE_TERRITORI.ID_INIZIATIVA+" = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ " + query+ " ]");
		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1,idIniziativa);
			
			rs =  stmt.executeQuery();
			
			while(rs.next()) 
				terr.add(rs.getString(1));
			
		} finally {
			close(rs, stmt);
		}
		return terr;
	}
	
	/**
	 * Recupera gli ambiti del lotto dell'iniziativa
	 * @param idIniziativa
	 * @return
	 * @throws SQLException
	 */
	public List<String> getAmbitiLottoIniziativa(Long idIniziativa) throws SQLException {
		List<String> ambiti = new ArrayList<String>();
		
		String query = "SELECT "+TIPO_AMBITO_LOTTO.COD_AMBITO_LOTTO+" FROM "+TIPO_AMBITO_LOTTO.TABLE_NAME
				      +" INNER JOIN "+AMBITO_LOTTO_INIZIATIVA.TABLE_NAME+" ON "+AMBITO_LOTTO_INIZIATIVA.T_ID_AMBITO_LOTTO+" = "+TIPO_AMBITO_LOTTO.T_ID_TIPO_AMBITO_LOTTO
				      +" WHERE "+AMBITO_LOTTO_INIZIATIVA.ID_INIZIATIVA+" = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ "+query+" ]");
		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idIniziativa);
			rs=stmt.executeQuery();
			while(rs.next())
				ambiti.add(rs.getString(1));
		
		} finally {
			close(rs, stmt);
		}
		return ambiti;
	}
	
	/**
	 * Recupera le categorie di una iniziativa
	 * @param idIniziativa
	 * @return
	 * @throws SQLException
	 */
	public List<String> getCategorieIniziativa(Long idIniziativa) throws SQLException {
		List<String> categorie = new ArrayList<String>();
		String query = "SELECT "+INIZIATIVE_CATEGORIE.COD_CATEGORIA
				       +" FROM "+INIZIATIVE_CATEGORIE.TABLE_NAME
				       +" WHERE "+INIZIATIVE_CATEGORIE.ID_INIZIATIVA+"=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ "+query+" ]");
		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idIniziativa);
			rs=stmt.executeQuery();
			while(rs.next())
				categorie.add(String.valueOf(rs.getLong(1)));
					
		} finally {
			close(rs, stmt);
		}
		
		return categorie;
	}
	
	/**
	 * Inserisci una nuova iniziativa e retiusciti l'id della nuova iniziativa
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public long insertIniziativa(IniziativaSoggAggr bean)  throws SQLException {
		String insertQuery = "INSERT INTO "+INIZIATIVE_SOGG_AGGR.TABLE_NAME+" ("+INIZIATIVE_SOGG_AGGR.ID_GARA+","
	                                                                            +INIZIATIVE_SOGG_AGGR.CIG+","
	                                                                            +INIZIATIVE_SOGG_AGGR.DESCRIZIONE_SOGG_AGGR+","
	                                                                            +INIZIATIVE_SOGG_AGGR.DESCRIZIONE_INIZIATIVA+","
	                                                                            +INIZIATIVE_SOGG_AGGR.SSAA_RIF+","
	                                                                            +INIZIATIVE_SOGG_AGGR.STATO_INIZIATIVA+","
	                                                                            +INIZIATIVE_SOGG_AGGR.FLAG_CONFRONTO_COMP+","
	                                                                            +INIZIATIVE_SOGG_AGGR.NOTE+","
	                                                                            +INIZIATIVE_SOGG_AGGR.LINK+") VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long idIniziativa=0;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(insertQuery,INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA));
			logger.debug ( "Tentativo di creazione nuova iniziativa [" + bean.getCIG() + "]" );
			int index=1;
			stmt.setLong(index++, bean.getIdGara());
			stmt.setString(index++, bean.getCIG());
			stmt.setString(index++, bean.getDescrizioneSoggAggr());
			stmt.setString(index++, bean.getDescrizioneIniziativa());
			stmt.setString(index++, bean.getSSAARif());
			stmt.setString(index++, bean.getStatoIniziativa());
			if(bean.getFlagConfrontoComp()==null||"".equals(bean.getFlagConfrontoComp()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, bean.getFlagConfrontoComp());
			stmt.setString(index++, bean.getNote());
			stmt.setString(index++, bean.getLink());
			
			stmt.execute();
			rs = stmt.getResultSet();
			rs.next();
			 idIniziativa = rs.getLong(INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA);
			
		} finally {
			close(rs, stmt);
		}
	
		 return idIniziativa;
	}
	
	/**
	 * Aggiorna iniziativa
	 * @param bean
	 * @throws SQLException
	 */
	public void updateIniziativa(IniziativaSoggAggr bean)  throws SQLException {
		String updateQuery = "UPDATE "+INIZIATIVE_SOGG_AGGR.TABLE_NAME+" SET "+INIZIATIVE_SOGG_AGGR.ID_GARA+"= ?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.CIG+" = ?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.DESCRIZIONE_SOGG_AGGR+"= ?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.DESCRIZIONE_INIZIATIVA+"=?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.SSAA_RIF+"=?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.STATO_INIZIATIVA+"=?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.FLAG_CONFRONTO_COMP+"=?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.NOTE+"=?,"
	                                                                            +INIZIATIVE_SOGG_AGGR.LINK+"=? "
	                                                                + "WHERE "+INIZIATIVE_SOGG_AGGR.ID_INIZIATIVA+"= ?";
		PreparedStatement stmt = null;

		try {
			stmt = activeConnection.prepareStatement(updateQuery);
			logger.debug ( "Tentativo di update iniziativa [" + bean.getCIG() + "]" );
			int index=1;
			stmt.setLong(index++, bean.getIdGara());
			stmt.setString(index++, bean.getCIG());
			stmt.setString(index++, bean.getDescrizioneSoggAggr());
			stmt.setString(index++, bean.getDescrizioneIniziativa());
			stmt.setString(index++, bean.getSSAARif());
			stmt.setString(index++, bean.getStatoIniziativa());
			if(bean.getFlagConfrontoComp()==null||"".equals(bean.getFlagConfrontoComp()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, bean.getFlagConfrontoComp());
			stmt.setString(index++, bean.getNote());
			stmt.setString(index++, bean.getLink());
			stmt.setLong(index++, bean.getIdIniziativa());
			stmt.executeUpdate();

			
		} finally {
			if(stmt!=null)stmt.close();
		}

	}
	
	/**
	 * Inserisci ambito lotto per una iniziativa
	 * @param idIniziativa
	 * @param idAmbito
	 * @throws SQLException
	 */
	public void insertAmbitoLotto(long idIniziativa, long idAmbito) throws SQLException {
		String insert = "INSERT INTO "+AMBITO_LOTTO_INIZIATIVA.TABLE_NAME+" ("+AMBITO_LOTTO_INIZIATIVA.ID_AMBITO_LOTTO+","+AMBITO_LOTTO_INIZIATIVA.ID_INIZIATIVA+") VALUES (?,?)";
		
		PreparedStatement stmt = null;

		try {
			stmt = activeConnection.prepareStatement(insert);
			logger.debug ( "Tentativo di insert ambito [" + String.valueOf(idIniziativa) + ","+String.valueOf(idAmbito)+"]" );
			stmt.setLong(1, idAmbito);
			stmt.setLong(2, idIniziativa);
			stmt.execute();
		} finally {
			if(stmt!=null)stmt.close();
			}

	}
	
	public long selectAmbitoLottoByCod(String codAmbito) throws SQLException {
		String select = "SELECT "+TIPO_AMBITO_LOTTO.ID_TIPO_AMBITO_LOTTO+" FROM "+TIPO_AMBITO_LOTTO.TABLE_NAME+" WHERE "+TIPO_AMBITO_LOTTO.COD_AMBITO_LOTTO+" = ?";
		long res = 0;
		

		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ "+select+" ]");
		try {
			stmt = activeConnection.prepareStatement(select);
			stmt.setString(1, codAmbito);
			rs=stmt.executeQuery();
			if(rs.next())
				res = rs.getLong(1);
					
		} finally {
			close(rs, stmt);
		}
		
		return res;
		
	}
	
	/**
	 * Inserisci categoria per una iniziativa
	 * @param idIniziativa
	 * @param codCategoria
	 * @throws SQLException
	 */
	public void insertCategoriaIniziativa(long idIniziativa, long codCategoria) throws SQLException {
		String insert = "INSERT INTO "+INIZIATIVE_CATEGORIE.TABLE_NAME+" ("+INIZIATIVE_CATEGORIE.ID_INIZIATIVA+","+INIZIATIVE_CATEGORIE.COD_CATEGORIA+") VALUES (?,?)";
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(insert);
			logger.debug ( "Tentativo di insert categoria [" + String.valueOf(idIniziativa) + ","+String.valueOf(codCategoria)+"]" );
			stmt.setLong(1, idIniziativa);
			stmt.setLong(2, codCategoria);
			stmt.execute();
		} finally {
			if(stmt!=null)stmt.close();
			}
		
	}
	
	/**
	 * Inserisci territorio per una iniziativa
	 * @param idIniziativa
	 * @param idRegione
	 * @throws SQLException
	 */
	public void insertTerritorioIniziativa(long idIniziativa, String idRegione) throws SQLException {
		String insert = "INSERT INTO "+INIZIATIVE_TERRITORI.TABLE_NAME+" ("+INIZIATIVE_TERRITORI.ID_INIZIATIVA+","+INIZIATIVE_TERRITORI.ID_REGIONE+") VALUES (?,?)";
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(insert);
			logger.debug ( "Tentativo di insert territorio [" + String.valueOf(idIniziativa) + ","+String.valueOf(idRegione)+"]" );
			stmt.setLong(1, idIniziativa);
			stmt.setString(2, idRegione);
			stmt.execute();
		} finally {
			if(stmt!=null)stmt.close();
			}
	}
	
	/**
	 * cancella gli ambiti del lotto di una iniziativa
	 * @param idIniziativa
	 * @throws SQLException
	 */
	public void deleteAmbitiLotto(long idIniziativa) throws SQLException {
		String delete = "DELETE FROM "+AMBITO_LOTTO_INIZIATIVA.TABLE_NAME+" WHERE "+AMBITO_LOTTO_INIZIATIVA.ID_INIZIATIVA+" = ?";
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(delete);
			logger.debug ( "Tentativo di delete ambito lotto [" + String.valueOf(idIniziativa) + "]" );
			stmt.setLong(1, idIniziativa);

			stmt.executeUpdate();
		} finally {
			if(stmt!=null)stmt.close();
		}
		
	}
	
	/**
	 * cancella le categorie di una iniziativa
	 * @param idIniziativa
	 * @throws SQLException
	 */
	public void deleteCategorieIniziativa(long idIniziativa) throws SQLException {
		String delete = "DELETE FROM "+INIZIATIVE_CATEGORIE.TABLE_NAME+" WHERE "+INIZIATIVE_CATEGORIE.ID_INIZIATIVA+"= ?";
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(delete);
			logger.debug ( "Tentativo di delete categorie iniziativa [" + String.valueOf(idIniziativa) + "]" );
			stmt.setLong(1, idIniziativa);

			stmt.executeUpdate();
		} finally {
			if(stmt!=null)stmt.close();
		}
	}

	/**
	 * Cancella i territori di una iniziativa
	 * @param idIniziativa
	 * @throws SQLException
	 */
	public void deleteTerritoriIniziativa(long idIniziativa)throws SQLException {
		String delete = "DELETE FROM "+INIZIATIVE_TERRITORI.TABLE_NAME+" WHERE "+INIZIATIVE_TERRITORI.ID_INIZIATIVA+"= ?";
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(delete);
			logger.debug ( "Tentativo di delete territori iniziativa [" + String.valueOf(idIniziativa) + "]" );
			stmt.setLong(1, idIniziativa);

			stmt.executeUpdate();
		} finally {
			if(stmt!=null)stmt.close();
		}
	}
	
	/**
	 * A fronte di un codice tipo ambito, restituisci il corrispondente id numerico
	 * @param codAmbito
	 * @return
	 * @throws SQLException
	 */
	public long getIdTipoAmbito(String codAmbito)throws SQLException {
		String select = "SELECT "+TIPO_AMBITO_LOTTO.ID_TIPO_AMBITO_LOTTO+" FROM "+TIPO_AMBITO_LOTTO.TABLE_NAME+" WHERE "+TIPO_AMBITO_LOTTO.COD_AMBITO_LOTTO+" = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		long res = 0;
		
		logger.debug("Executing query [ "+select+" ]");
		try {
			stmt = activeConnection.prepareStatement(select);
			stmt.setString(1, codAmbito);
			rs=stmt.executeQuery();
			if(rs.next())
				res = rs.getLong(1);
					
		} finally {
			close(rs, stmt);
		}
		
		return res;
	}
	
	/**
	 * Verifica se il CIG sia in carico a un soggetto aggregatore
	 * @param fullCIG
	 * @return
	 * @throws SQLException
	 */
	public boolean checkCigSoggAggr(String fullCIG) throws SQLException {
		boolean res = false;
		String cig = fullCIG.substring(0,7);
		String cigKKK = fullCIG.substring(7,10);
		String select = "SELECT 1 FROM "+LOTTO.TABLE_NAME
				        +" INNER JOIN "+GARA.TABLE_NAME+" ON "+GARA.T_ID_GARA +" = "+LOTTO.T_ID_GARA
				        +" INNER JOIN "+LISTA_SOGGETTI_AGGREGATORI.TABLE_NAME+" ON "+LISTA_SOGGETTI_AGGREGATORI.CF_SOGG_AGGREGATORE+" = "+GARA.CF_AMMINISTRAZIONE
				        + " AND "+LISTA_SOGGETTI_AGGREGATORI.T_ID_STAZIONE_APPALTANTE +" = "+GARA.T_ID_STAZIONE_APPALTANTE
				        +" WHERE "+LOTTO.CIG+" = ? AND "+LOTTO.CIG_KKK+" = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(select);
			stmt.setString(1, cig);
			stmt.setString(2, cigKKK);
			rs=stmt.executeQuery();
			if(rs.next())
				res = true;
					
		} finally {
			close(rs, stmt);
		}
		return res;
	}
	
	/**
	 * Verifica se una amministrazione sia un soggetto aggregatore
	 * @param cfSA
	 * @return
	 * @throws SQLException
	 */
	public boolean checkSASoggAggr(String cfSA, String id_stazione_appaltante)throws SQLException {
		boolean res = false;
		final String CHECK_SA_SOGG_AGGR = "SELECT * FROM "+LISTA_SOGGETTI_AGGREGATORI.TABLE_NAME+
				                          " WHERE "+LISTA_SOGGETTI_AGGREGATORI.CF_SOGG_AGGREGATORE+" = ? AND "
				                          +LISTA_SOGGETTI_AGGREGATORI.T_ID_STAZIONE_APPALTANTE+" = ? AND "
				                          +LISTA_SOGGETTI_AGGREGATORI.DATA_FINE+" IS NULL ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(CHECK_SA_SOGG_AGGR);
			stmt.setString(1, cfSA);
			stmt.setString(2,id_stazione_appaltante);
			rs=stmt.executeQuery();
			if(rs.next())
				res = true;
					
		} finally {
			close(rs, stmt);
		}
		return res;
	}
	
	/**
	 * Verifica se per questa gara ci sono le condizioni per bloccare il rilascio del cig e richiede l'adesione 
	 * all'iniziative presso i soggetti aggregatori.
	 * Tecnicamente il controllo dovrebbe essere applicato solo ai WS in quanto su WEB i controlli avvengono 
	 * in modo preliminare e l'utente viene guidato da interfaccia
	 * @param gara dati della gara
	 * @param dati del lotto (se si sta inserendo/modificando un lotto)
	 * @return true (bloccare rilascio CIG) false (non bloccare il rilascio CIG)
	 */
	public boolean checkBloccoCIGPerIniziative(Gara gara, Lotto lotto) throws SQLException {
		boolean res = true;
				
		if(this.checkSASoggAggr(gara.getCF_AMMINISTRAZIONE(),gara.getID_STAZIONE_APPALTANTE()) 
				|| SimogProperties.getInstance().isCfEsclusa(gara.getCF_AMMINISTRAZIONE())) {
			System.out.println("TECHNIS 1");
			res = false; //Verifica che la SA sia esclusa dagli obblighi del DPCM (es. Bolzano) o se sia un soggetto aggregatore
		} else if(gara.getCIG_ACC_QUADRO()!=null && !"".equals(gara.getCIG_ACC_QUADRO()) && this.getIniziative(gara.getCIG_ACC_QUADRO(), null, null, null, null, true).size()>0) {
			System.out.println("TECHNIS 2");
			res = false;//Verifica che l'eventuale CIG accordo quadro indicato faccia parte dell'iniziative
	    } else if(gara.getCIG_ACC_QUADRO()!=null && !"".equals(gara.getCIG_ACC_QUADRO()) && this.checkCigSoggAggr(gara.getCIG_ACC_QUADRO())) {
	    	System.out.println("TECHNIS 3");
			res = false;//Verifica che l'eventuale CIG accordo quadro indicato sia in carico a un soggetto aggregatore
        } else if(Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione()) || Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass())) {
        	System.out.println("TECHNIS 4");
			res = false; //Verifica che almeno una delle due autodichiarazioni sia impostata a sì
        } else if(Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())) {
        	System.out.println("TECHNIS 5");
			res = false; //Verifica che la categoria sia 999
        } else if(lotto.getCOD_CATEGORIA()!=null) {
        	System.out.println("TECHNIS 6");
			List<String> listCat = new ArrayList<String>();
			listCat.add(lotto.getCOD_CATEGORIA());
			if(this.getIniziative(null, null, listCat, null, null, true).isEmpty()) {
				System.out.println("TECHNIS 6.1");
				res = false; //Verifica che non esistano iniziative per la categoria impostata
			} else if(this.callFGetEAGGCategorieSoglie(gara.getCF_AMMINISTRAZIONE(), lotto.getCOD_CATEGORIA(), lotto.getImporto_Lotto().doubleValue())) {
				System.out.println("TECHNIS 6.2");
				res = false; //Verifica che per questa amministrazione e per questa categoria non viene superata la soglia massima consentita
			}
		}

		return res;
	}
	
	
	/**
	 * Verifica se per una gara o un lotto e' stata impostato un certo tipo di autodichiarazione e restituisci l'esito della verifica
	 * @param idGara
	 * @param idLotto
	 * @param tipoAutodichiarazione
	 * @return
	 * @throws SQLException
	 */
	public boolean checkAutodichiarazione(long idLotto, long tipoAutodichiarazione) throws SQLException{
		String GET_AUTODICHIARAZIONE_GARA = "SELECT * FROM "+AUTODICHIARAZIONI_ADESIONE.TABLE_NAME+
				                            " WHERE "+AUTODICHIARAZIONI_ADESIONE.ID_TIPO_AUTODICHIARAZIONE_ADESIONE + " = ? "+
				                            " AND "+AUTODICHIARAZIONI_ADESIONE.DATA_FINE+" IS NULL ";
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		boolean res = false;

		if(idLotto!=0)
			GET_AUTODICHIARAZIONE_GARA +=" AND "+AUTODICHIARAZIONI_ADESIONE.ID_LOTTO+" = ? ";
		
		try {
			pstmt = activeConnection.prepareStatement(GET_AUTODICHIARAZIONE_GARA);
			if(idLotto!=0)
			   pstmt.setLong(1, idLotto);
			
			pstmt.setLong(2, tipoAutodichiarazione);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				res = true;
			}
			
		}finally{
	           if(rs != null) rs.close();
	           if(pstmt != null) pstmt.close();
			}
		
		return res;
	}
	
	/**
	 * Recupera la lista della autodichiarazioni attive
	 * @param idGara
	 * @param idLotto
	 * @param tipoAutodichiarazione
	 * @return
	 * @throws SQLException
	 */
	public List<Long> getAutodichiarazioni(long idLotto, String codCategoria) throws SQLException{
		String GET_AUTODICHIARAZIONE = "SELECT "+AUTODICHIARAZIONI_ADESIONE.ID_TIPO_AUTODICHIARAZIONE_ADESIONE
				                             +" FROM "+AUTODICHIARAZIONI_ADESIONE.TABLE_NAME+
				                            " WHERE "+AUTODICHIARAZIONI_ADESIONE.DATA_FINE+" IS NULL ";
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<Long> res = new ArrayList<Long>();
		if(idLotto!=0)
			GET_AUTODICHIARAZIONE +=" AND "+AUTODICHIARAZIONI_ADESIONE.ID_LOTTO+" = ? ";
		if(codCategoria!=null && !"".equals(codCategoria))
			GET_AUTODICHIARAZIONE +=" AND "+AUTODICHIARAZIONI_ADESIONE.COD_CATEGORIA+" = ? ";
		
		try {
			pstmt = activeConnection.prepareStatement(GET_AUTODICHIARAZIONE);
			if(idLotto!=0)
			   pstmt.setLong(1, idLotto);
			if(codCategoria!=null && !"".equals(codCategoria))
				   pstmt.setLong(2, Long.parseLong(codCategoria));
		
			rs = pstmt.executeQuery();

			while(rs.next()) {
				res.add(rs.getLong(1));
			}
			
		}finally{
	           if(rs != null) rs.close();
	           if(pstmt != null) pstmt.close();
			}
		return res;
	}
	
	/**
	 * Invalida l'autodichiarazione di una gara o di un lotto
	 * @param idGara
	 * @param idLotto
	 * @param tipoAutodichiarazione
	 * @throws SQLException
	 */
	public void expireAutodichiarazione(long idLotto, long tipoAutodichiarazione) throws SQLException {
		String EXPIRE_AUTODICHIARAZIONE = "UPDATE "+AUTODICHIARAZIONI_ADESIONE.TABLE_NAME+" SET "+AUTODICHIARAZIONI_ADESIONE.DATA_FINE+" = ? "+
	                                      "WHERE "+AUTODICHIARAZIONI_ADESIONE.ID_LOTTO+" = ? AND "+AUTODICHIARAZIONI_ADESIONE.DATA_FINE+" IS NULL";
		
		if(tipoAutodichiarazione!=0)
			EXPIRE_AUTODICHIARAZIONE+=" AND "+AUTODICHIARAZIONI_ADESIONE.ID_TIPO_AUTODICHIARAZIONE_ADESIONE+" = ?";
		
		PreparedStatement stmt = null;
		int index = 1;
		
		
		try{
			stmt = activeConnection.prepareStatement(EXPIRE_AUTODICHIARAZIONE);
			stmt.setTimestamp(index++,getNow());
			stmt.setLong(index++,idLotto);
			if(tipoAutodichiarazione!=0)
				stmt.setLong(index++,tipoAutodichiarazione);
			
			stmt.execute();
		}finally{
			if(stmt!=null)stmt.close();
		}
	}
	
	/**
	 * Inserisci una nuova autodichiarazione per una gara o un lotto
	 * @param idGara
	 * @param idLotto
	 * @param tipoAutodichiarazione
	 * @throws SQLException
	 */
	public void insertAutodichiarazione(long codCategoria, long idLotto, long tipoAutodichiarazione) throws SQLException {
		String INSERT_AUTODICHIARAZIONE = "INSERT INTO "+AUTODICHIARAZIONI_ADESIONE.TABLE_NAME+" ("+AUTODICHIARAZIONI_ADESIONE.COD_CATEGORIA+","+
				AUTODICHIARAZIONI_ADESIONE.ID_LOTTO+","+
				AUTODICHIARAZIONI_ADESIONE.ID_TIPO_AUTODICHIARAZIONE_ADESIONE+","+ 
				AUTODICHIARAZIONI_ADESIONE.DATA_INIZIO
						+ ") VALUES(?,?,?,?)";
		PreparedStatement stmt = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(INSERT_AUTODICHIARAZIONE);
			stmt.setLong(index++, codCategoria);
			if(idLotto!=0)
				stmt.setLong(index++, idLotto);
			else
				stmt.setNull(index++, Types.BIGINT);
			stmt.setLong(index++, tipoAutodichiarazione);
			stmt.setTimestamp(index++, getNow());
			stmt.execute();
		}finally{
			if(stmt!=null)stmt.close();
		}
	}
	
	/**
	 * Chiamata della funzione per la verifica del superamento soglia
	 * @param cfAmministrazione
	 * @param codCategoria
	 * @return true (soglia non superata) false (soglia superata)
	 */
	public boolean callFGetEAGGCategorieSoglie(String cfAmministrazione, String codCategoria,double importoLotto) {
		boolean res = true;
		
		String query = "SELECT simog.dbo.f_get_eagg_categorie_soglie(?,?,?) AS ESITO";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int esito=0;
		try {
			try{
				stmt = activeConnection.prepareStatement(query);
				stmt.setString(1, cfAmministrazione);
				stmt.setLong(2, Long.parseLong(codCategoria));
				stmt.setDouble(3, importoLotto);
	
				rs = stmt.executeQuery();
	
				if(rs.next()) {
					 esito = rs.getInt("ESITO");
					if(esito==-1)
						res = false;
					
				}
			}catch(SQLException e) {
					e.printStackTrace();
					return true;
				}
		}finally{
			close(rs,stmt);
		}
		return res;
	}
	
	public void insertErr(Long idGara, String CIG,String descSoggAgg, String msgErr) throws SQLException {
		String query = "INSERT INTO INIZIATIVE_ERR(ID_GARA,CIG,DESCRIZIONE_SOGG_AGGR,MSG_ERR,DATA_ERR) VALUES(?,?,?,?,getdate())";
		PreparedStatement stmt = null;
		try{
			stmt = activeConnection.prepareStatement(query);
			if(idGara!=null)
				stmt.setLong(1, idGara);
			else
				stmt.setNull(1, java.sql.Types.BIGINT);
			if(CIG!=null)
				stmt.setString(2, CIG);
			else
				stmt.setNull(2, java.sql.Types.VARCHAR);
			if(descSoggAgg!=null)
				stmt.setString(3, descSoggAgg);
			else
				stmt.setNull(3, java.sql.Types.VARCHAR);
			stmt.setString(4, msgErr);
			stmt.execute();
		}finally{
			if(stmt!=null)stmt.close();
		}
	}
	
	public String checkCigIniziativaFromModificaGara(Gara gara) throws SQLException,Exception {
  	    String cigStr = "";
		  //TICKET ALM #4222 - 3.04.4
        //Abilita questo controllo:
        //- solo alle gare create dopo la 3.04.4
        //- solo se la gara e' in modifica
        //- solo se la gara non ha solo 999 come categoria DPCM
        //- nella gara non e' indicato il CIG accordo quadro
  	    System.out.println("TECHNIS INIZIO");
        boolean cigAccAssente = gara.getCIG_ACC_QUADRO()==null || "".equals(gara.getCIG_ACC_QUADRO());
        System.out.println("TECHNIS cigAccAssente "+cigAccAssente);
//        cigAccOk = cigAccOk || im.checkCigSoggAggr(gara.getCIG_ACC_QUADRO())
        if(SimogProperties.getInstance().isDataCreatedAfter3044(gara.getData_creazione()) 
       		 && gara.getId_Gara()!=0
       		 && (gara.getCatMercArray().length>1 ||
       				 (gara.getCatMercArray().length==1 
       				   && !Costanti.EAGG_CATMERC_999.equals(gara.getCatMercArray()[0])
       				 )
       			)
       	) {
        	System.out.println("TECHNIS INIZIO IF 1");
        	    //Verifica se la SA sia esclusa dagli obblighi del DPCM (es. Bolzano)
        	    if(SimogProperties.getInstance().isCfEsclusa(gara.getCF_AMMINISTRAZIONE()))
        	    	return "";
        	    System.out.println("TECHNIS INIZIO IF 2");
	        	 //Verifica se la SA sia un soggetto aggregatore
	        	 if(this.checkSASoggAggr(gara.getCF_AMMINISTRAZIONE(), gara.getID_STAZIONE_APPALTANTE()))
	        		 return "";
	        	 System.out.println("TECHNIS INIZIO IF 3");
	        	//Verifica se il CIG e' in carico a un soggetto aggr.
	        	 if(!cigAccAssente && this.checkCigSoggAggr(gara.getCIG_ACC_QUADRO()))
	        		 return "";
	        	 System.out.println("TECHNIS INIZIO IF 4"); 
	        	 //Se indicato, verifica se il CIG sia una iniziativa
	        	if(!cigAccAssente && this.getIniziative(gara.getCIG_ACC_QUADRO(), null, null, null, null, true).size()>0)
	        		return "";
	        	System.out.println("TECHNIS INIZIO IF 5"); 
        		    List<String> cigErr = new ArrayList<String>();
		        	 LottoManager lm = new LottoManager(activeConnection, logger);
		        	List<Lotto> listaLotti = lm.getListaCIGByIdGara(gara.getId_Gara());
		        	List<String> listaTerritori = new ArrayList<String>();
		        	if(gara.getID_OSSERVATORIO()!=null && !ProfiloEnum.REGIONE_099.equals(gara.getID_OSSERVATORIO())) {
						if(gara.getID_OSSERVATORIO().length()==3)
							listaTerritori.add(gara.getID_OSSERVATORIO().substring(1));
					}  
		        		
		        	for(int i=0;i<gara.getCatMercArray().length;i++) {
		        		String catMerc=gara.getCatMercArray()[i];
		        		//Se la categoria e' 999, passa alla categoria successiva
		        		if(Costanti.EAGG_CATMERC_999.equals(catMerc))
		        			continue;
		        		else {
		        			List<String> listCat = new ArrayList<String>();
		        			listCat.add(catMerc);
		        			
		        			//Se non esistono iniziative, passa alla categoria successiva
		        			if(this.getIniziative(null, listaTerritori, listCat, null, null, true).isEmpty())
		        				continue;
		        			else {
			        			for(Lotto l : listaLotti) {
			        				if(catMerc.equals(l.getCOD_CATEGORIA())) {
		                                 boolean check = this.callFGetEAGGCategorieSoglie(gara.getCF_AMMINISTRAZIONE(), 
			                                        l.getCOD_CATEGORIA(), 
			                                        l.getImporto_Lotto().doubleValue());
				        					if(!check)
				        						cigErr.add(l.getCIG()+l.getCIG_kkk());
			        				}
			        			}
			        		}
		        		}
		        		
		        	}
		        	
		          if(cigErr.size()>0) {
		        	  for(int i=0;i<cigErr.size();i++) {
		        		  cigStr+=cigErr.get(i);
		        		  if(i<cigErr.size()-1)
		        			  cigStr+=", ";
		        	  }
	        	        	  
		          }
	        	 
        }
        return cigStr;
	}
	
}
