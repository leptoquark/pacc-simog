package it.avlp.simog.gestioneannullamentomanager;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletRichAnnullamento;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class AnnullamentoManager extends AccessiDB{
	
	String whereCondition="";
	public AnnullamentoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private String creaSubCui(){
		return  " (select distinct ( substring(" + AGGIUDICAZIONI.T_CUI + ",1,2) + "
		+ "(select case when isnull(l.somma_Urgenza, 'N') = 'S' and substring(" + AGGIUDICAZIONI.T_CUI + ",3,1) = '0' then '9' else substring(" + AGGIUDICAZIONI.T_CUI + ",3,1) end "
		+ " from lotto l, info_aggiudicazioni i where i.id_info = aggiudicazioni.id_info and i.data_inizio_info = aggiudicazioni.data_inizio_info and l.id_lotto = i.id_lotto) " 
				+ " + substring(" + AGGIUDICAZIONI.T_CUI + ",4,10) + '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI+")))";
	}
	
	private String creaSubCig(){
		return " (select distinct (case when isnull(" + LOTTO.SOMMA_URGENZA + ", 'N') = 'S' and substring(" + LOTTO.T_CIG + ",1,1) = '0' then '9' else substring(" + LOTTO.T_CIG + ",1,1) end"
		+ " + substring(" + LOTTO.T_CIG + ",2,6) + " + LOTTO.CIG_KKK + ")";
	}
	
	/**
	*SELECT CHE ESTRAE TUTTE LE RICHIESTE DI ANNULLAMENTO
	*/
	private final String QUERY_BASE_SELECT_RICHIESTE_ANN =		
		"SELECT " + RICHIESTA_ANNULLAMENTO.TABLE_NAME + ".* "
		   + ", case " + RICHIESTA_ANNULLAMENTO.T_BLOCCO
				+ " when '" + IdentificativoSchede.TAB_ADESIONE + "' then"
				+ creaSubCui()
				+ " from " + AGGIUDICAZIONI.TABLE_NAME 
				+ " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " )"
			+ " when '" + IdentificativoSchede.TAB_AGGIUDICAZIONE + "' then"
				+ creaSubCui()
				+ " from " + AGGIUDICAZIONI.TABLE_NAME 
				+ " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " )"
			+ " when '" + IdentificativoSchede.TAB_SOTTOSOGLIA + "' then"
				+ creaSubCui()
				+ " from " + AGGIUDICAZIONI.TABLE_NAME 
				+ " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " )"
			+ " when '" + IdentificativoSchede.TAB_ESCLUSI + "' then"
				+ creaSubCui()
				+ " from " + AGGIUDICAZIONI.TABLE_NAME 
				+ " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " )"				
			+ " when '" + IdentificativoSchede.TAB_INFO_COMUNI + "' then"
				+ creaSubCig()
				+ " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME 
				+ " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
				+ " )"
			+ " when '" + IdentificativoSchede.TAB_INIZIO_LAVORI + "' then"
				+ creaSubCui()
				+ " from " + INIZIO_LAVORI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + INIZIO_LAVORI.T_ID_INIZIO + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + INIZIO_LAVORI.T_DATA_INIZIO_INIZIO + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + INIZIO_LAVORI.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			
			+ " when '" + IdentificativoSchede.TAB_STIPULA + "' then"
				+ creaSubCui()
				+ " from " + STIPULA.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + STIPULA.T_ID_STIPULA + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + STIPULA.T_DATA_INIZIO_STIPULA + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + STIPULA.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + STIPULA.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"	
			
			+ " when '" + ParametriServletAvanzamento.TAB_AVANZAMENTO + "' then"
				+ creaSubCui()
				+ " from " + STATI_AVANZ.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + STATI_AVANZ.T_ID_AVANZAMENTO + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + STATI_AVANZ.T_DATA_INIZIO_AVANZAMENTO + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + STATI_AVANZ.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + STATI_AVANZ.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " when '" + ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI + "' then"
				+ creaSubCui()
				+ " from " + FINE_LAVORI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + FINE_LAVORI.T_ID_ULTIM + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + FINE_LAVORI.T_DATA_INIZIO_ULTIM + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + FINE_LAVORI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + FINE_LAVORI.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " when '" + ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO + "' then"
				+ creaSubCui()
				+ " from " + COLLAUDO.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + COLLAUDO.T_ID_COLLAUDO + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + COLLAUDO.T_DATA_INIZIO_COLL + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + COLLAUDO.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + COLLAUDO.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"				
			+ " when '" + ParametriServletR129.TAB_SCHEDA_R129 + "' then"
				+ creaSubCui()
				+ " from " + R129.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + R129.T_ID_RECORD + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + R129.T_DATA_INIZIO + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + R129.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + R129.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " when '" + ParametriServletAccordo.TAB_SCHEDA_ACCORDO + "' then"
				+ creaSubCui()
				+ " from " + ACCORDI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + ACCORDI.T_ID_ACCORDO + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + ACCORDI.T_DATA_INIZIO_ACC + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + ACCORDI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + ACCORDI.T_DATA_INIZIO_AGGIUDICAZIONE			
				+ " )"
			+ " when '" + ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI + "' then"
				+ creaSubCui()
				+ " from " + SOSPENSIONI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + SOSPENSIONI.T_ID_SOSPENSIONE + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + SOSPENSIONI.T_DATA_INIZIO_SOSP + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + SOSPENSIONI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + SOSPENSIONI.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " when '" + ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI + "' then"
				+ creaSubCui()
				+ " from " + SUBAPPALTI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + SUBAPPALTI.T_ID_RECORD + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + SUBAPPALTI.T_DATA_INIZIO_RECORD + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + SUBAPPALTI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + SUBAPPALTI.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " when '" + ParametriServletVariante.TAB_SCHEDA_VARIANTE + "' then"
				+ creaSubCui()
				+ " from " + VARIANTI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME  
				+ " where " + VARIANTI.T_ID_VARIANTE + " = "  + RICHIESTA_ANNULLAMENTO.T_ID_RECORD
				+ " and " + VARIANTI.T_DATA_INIZIO_VAR + " = " + RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD
				+ " and " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = "  + VARIANTI.T_ID_AGGIUDICAZIONE
				+ " and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = " + VARIANTI.T_DATA_INIZIO_AGGIUDICAZIONE
				+ " )"
			+ " else (select 'nullo') end as " + LOTTO.CIG		
		+ " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME;
		
	/**
	 * SELECT CHE ESTRAE TUTTE LE RICHIESTE DI ANNULLAMENTO DA VALUTARE
	 */
	private final String WHERE_CANCELLAZIONE =" AND "+ RICHIESTA_ANNULLAMENTO.CANCELLAZIONE+" IS NOT NULL ";
	private final String WHERE_ANNULLAMENTO =" AND "+ RICHIESTA_ANNULLAMENTO.CANCELLAZIONE+" IS NULL ";
	 
	private final String QUERY_BASE_SELECT_RICHIESTE_ANN_DA_VALUTARE=
		QUERY_BASE_SELECT_RICHIESTE_ANN 
		+ " WHERE "+RICHIESTA_ANNULLAMENTO.T_DATA_FINE+" IS NULL ";
		
		
	private final String QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_COMPLETA =		
		QUERY_BASE_SELECT_RICHIESTE_ANN 
		+ " WHERE 1 = 1 ";
	
	private final String QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_VALUTATE =		
		QUERY_BASE_SELECT_RICHIESTE_ANN 
		+ " WHERE "+RICHIESTA_ANNULLAMENTO.T_DATA_FINE+" IS NOT NULL ";
	
	
	/*************************************************************************************************
	 * Recupera le richieste di annullamento passandogli l'opportuna query a seconda che 
	 * la ricerca sia affettuata sulle richieste di annullamento da valutare, valutate o entrambe. 
	 * 
	 * @param scelta String
	 * @param orderField String
 	 * @return TableBean
	 * @throws SQLException
	 */
/* PP non usata ?	
	public TableBean getRichiesteAnnullamento(String scelta, String orderField) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		String query = "";
		
		try{
			if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE))
				query = QUERY_BASE_SELECT_RICHIESTE_ANN_DA_VALUTARE;
			else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE))
				query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_VALUTATE;
			else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_COMPLETA))
				query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_COMPLETA;
			
			if(orderField!=null && !"".equals(orderField))
				query = query + " order by " + orderField;
			else
				query = query + " ORDER BY " + RICHIESTA_ANNULLAMENTO.T_ID_RICHIESTA;
			
			logger.debug("Select RICHIESTE ANNULLAMENTO, query ["+query+"]");
			stmt = activeConnection.prepareStatement(query);
			
			//stmt.setObject(1, idAggiudicazione);
			//stmt.setObject(2, dataInizioAgg);
			rs = stmt.executeQuery();
			tb = new TableBean(rs);
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		finally{
			close(rs,stmt);
		}
		logger.debug(tb.toString());
		return tb;
	}
*/	
	
	/*************************************************************************************************
	 * Verifica la valorizzazione dell' id_osservatorio
	 * 
	 * @param id_osservatorio String
 	 * @return boolean
	 */	
	@Deprecated
	public boolean checkId_Osservatorio(String id_osservatorio) {
		return !(ProfiloEnum.REGIONE_ZERO .equals(id_osservatorio) ||
				 ProfiloEnum.REGIONE_099.equals(id_osservatorio) ||
				 ProfiloEnum.REGIONE_999.equals(id_osservatorio) ||
				 "".equals(id_osservatorio)    ||
				 id_osservatorio == null	   );
	}
	
	/*************************************************************************************************
	 * Recupera le richieste di annullamento passandogli l'opportuna query a seconda che 
	 * la ricerca sia affettuata sulle richieste di annullamento da valutare, valutate o entrambe. 
	 * 
	 * @param scelta String
	 * @param cig_lotto String
	 * @param orderField String
 	 * @return TableBean
	 * @throws SQLException
	 */
	
	public TableBean getRichiesteAnnullamento(String scelta, String cig_lotto, String id_osservatorio, String orderField, int startRow, int maxRowsAllowed, boolean cancellazioni) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		String query = "";
		
		try{
			if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE))
				query = QUERY_BASE_SELECT_RICHIESTE_ANN_DA_VALUTARE;
			else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE))
				query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_VALUTATE;
			else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_COMPLETA))
				query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_COMPLETA;
			
			// filtro per selezionare le richieste di annullamento o cancellazione
			query += cancellazioni ? WHERE_CANCELLAZIONE : WHERE_ANNULLAMENTO;
			
			// PP su gara id_osservatorio e' da tre admin_or invece e' da due (codice regione), antepongo lo zero
			String padded_id_osservatorio = id_osservatorio.trim();
			if (padded_id_osservatorio.length()== 2)  
				padded_id_osservatorio =  "0" + padded_id_osservatorio; 
			
			logger.debug("!--- ID_OSSERVATORIO ["+id_osservatorio+"] ---!");
			
			query += " AND EXISTS ( SELECT " + GARA.T_ID_OSSERVATORIO + " FROM "
			      +  LOTTO.TABLE_NAME + " JOIN " + GARA.TABLE_NAME + " ON (" + LOTTO.T_ID_GARA + " = " + GARA.T_ID_GARA + ")"				      
			      +  " WHERE " + LOTTO.T_ID_LOTTO + " = " + RICHIESTA_ANNULLAMENTO.T_ID_LOTTO;
			
			// PP 999 vede tutto
			if(!ProfiloEnum.REGIONE_999.equals(padded_id_osservatorio)){
				//UN: Selezionare le richieste in base alle competenze dell'osservatorio regionale
				if(ProfiloEnum.REGIONE_099.equals(padded_id_osservatorio))
					query += " AND (" + GARA.T_ID_OSSERVATORIO + " = '" + ProfiloEnum.REGIONE_099 + "' OR " + GARA.T_ID_OSSERVATORIO + " = '" + ProfiloEnum.REGIONE_020 +"') ";
				else if(!ProfiloEnum.REGIONE_ZERO.equals(padded_id_osservatorio))
					query += " AND " + GARA.T_ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "'"; 
			}
			query += ") ";
			
			//UN: Aggiunto il filtro per CIG Lotto
			if(cig_lotto!=null && !"".equals(cig_lotto)){
				
				String cig = CIGBean.getCIGPart(cig_lotto); //  "0" + cig_lotto.substring(1, 7);						//Eliminazione CIG_KKK e sostituzione 9 con 0 
				String kkk = CIGBean.getCIGKKK(cig_lotto);  // cig_lotto.substring(7, 10);							//Estrazione CIG_KKK
				
				query +=" AND " + RICHIESTA_ANNULLAMENTO.T_ID_LOTTO + " = ( SELECT " +  LOTTO.T_ID_LOTTO
					  + " FROM " + LOTTO.TABLE_NAME
					  + " WHERE " + LOTTO.T_CIG + " = '" + cig + "'"
					  + " AND "+ LOTTO.T_CIG_CICLE + " = 0 "
					  + " AND "+ LOTTO.T_CIG_KKK + " = '" + kkk + "'"
					  // NEWCIG inutile + " AND "+ LOTTO.T_SOMMA_URGENZA + " = '" + sum + "'" 
					  + ") ";
			}
			
			// PP B302.2.0 nascondo le variazioni C.O.
			query +=" AND " + RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA + "<>'" + StatiScheda.VARIAZIONE_CO_STRING + "'";
			
			if(orderField!=null && !"".equals(orderField))
				query = query + " order by " + orderField;
			else
				query = query + " ORDER BY " + RICHIESTA_ANNULLAMENTO.T_ID_RICHIESTA;
			
			logger.debug("Select RICHIESTE ANNULLAMENTO, query ["+query+"]");
			stmt = activeConnection.prepareStatement(query);
			
			//stmt.setObject(1, idAggiudicazione);
			//stmt.setObject(2, dataInizioAgg);
			rs = stmt.executeQuery();
			tb = new TableBean(rs,startRow,maxRowsAllowed);
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		finally{
			close(rs,stmt);
		}

		logger.debug(tb.toString());

		return tb;
	}

    /*************************************************************************************************
     * Recupera le richieste di annullamento passandogli l'opportuna query a seconda che 
     * la ricerca sia affettuata sulle richieste di annullamento da valutare, valutate o entrambe. 
     * 
     * @param scelta String
     * @param cig_lotto String
     * @param orderField String
     * @return List<RichiestaAnnullamento>
     * @throws SQLException
     */
    
    public List<RichiestaAnnullamento> getRichiesteAnnullamentoBean(String scelta, String cig_lotto, String id_osservatorio, String orderField, int startRow, int maxRowsAllowed, boolean cancellazioni) throws SQLException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List <RichiestaAnnullamento> lista = new ArrayList<RichiestaAnnullamento>();
        String query = "";
        
        try{
            if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE))
                query = QUERY_BASE_SELECT_RICHIESTE_ANN_DA_VALUTARE;
            else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_RICHIESTE_VALUTATE))
                query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_VALUTATE;
            else if(scelta.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_COMPLETA))
                query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_COMPLETA;
            
            // filtro per selezionare le richieste di annullamento o cancellazione
            query += cancellazioni ? WHERE_CANCELLAZIONE : WHERE_ANNULLAMENTO;
            
            // PP su gara id_osservatorio e' da tre admin_or invece e' da due (codice regione), antepongo lo zero
            String padded_id_osservatorio = id_osservatorio.trim();
            if (padded_id_osservatorio.length()== 2)  
                padded_id_osservatorio =  "0" + padded_id_osservatorio; 
            
            logger.debug("!--- ID_OSSERVATORIO ["+id_osservatorio+"] ---!");
            
            query += " AND EXISTS ( SELECT " + GARA.T_ID_OSSERVATORIO + " FROM "
                  +  LOTTO.TABLE_NAME + " JOIN " + GARA.TABLE_NAME + " ON (" + LOTTO.T_ID_GARA + " = " + GARA.T_ID_GARA + ")"                     
                  +  " WHERE " + LOTTO.T_ID_LOTTO + " = " + RICHIESTA_ANNULLAMENTO.T_ID_LOTTO;
            
            // PP 999 vede tutto
            if(!ProfiloEnum.REGIONE_999.equals(padded_id_osservatorio)){
                //UN: Selezionare le richieste in base alle competenze dell'osservatorio regionale
                if(ProfiloEnum.REGIONE_099.equals(padded_id_osservatorio))
                    query += " AND (" + GARA.T_ID_OSSERVATORIO + " = '" + ProfiloEnum.REGIONE_099 + "' OR " + GARA.T_ID_OSSERVATORIO + " = '" + ProfiloEnum.REGIONE_020 +"') ";
                else if(!ProfiloEnum.REGIONE_ZERO.equals(padded_id_osservatorio))
                    query += " AND " + GARA.T_ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "'"; 
            }
            query += ") ";
            
            //UN: Aggiunto il filtro per CIG Lotto
            if(cig_lotto!=null && !"".equals(cig_lotto)){
                
                String cig = CIGBean.getCIGPart(cig_lotto); //  "0" + cig_lotto.substring(1, 7);                        //Eliminazione CIG_KKK e sostituzione 9 con 0 
                String kkk = CIGBean.getCIGKKK(cig_lotto);  // cig_lotto.substring(7, 10);                          //Estrazione CIG_KKK
                
                query +=" AND " + RICHIESTA_ANNULLAMENTO.T_ID_LOTTO + " = ( SELECT " +  LOTTO.T_ID_LOTTO
                      + " FROM " + LOTTO.TABLE_NAME
                      + " WHERE " + LOTTO.T_CIG + " = '" + cig + "'"
                      + " AND "+ LOTTO.T_CIG_CICLE + " = 0 "
                      + " AND "+ LOTTO.T_CIG_KKK + " = '" + kkk + "'"
                      // NEWCIG inutile + " AND "+ LOTTO.T_SOMMA_URGENZA + " = '" + sum + "'" 
                      + ") ";
            }
            
            // PP B302.2.0 nascondo le variazioni C.O.
            query +=" AND " + RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA + "<>'" + StatiScheda.VARIAZIONE_CO_STRING + "'";
            
            if(orderField!=null && !"".equals(orderField))
                query = query + " order by " + orderField;
            else
                query = query + " ORDER BY " + RICHIESTA_ANNULLAMENTO.T_ID_RICHIESTA;
            
            logger.debug("Select RICHIESTE ANNULLAMENTO, query ["+query+"]");
            stmt = activeConnection.prepareStatement(query);
            
            //stmt.setObject(1, idAggiudicazione);
            //stmt.setObject(2, dataInizioAgg);
            rs = stmt.executeQuery();
            
            while(rs.next()){
               RichiestaAnnullamento bean = new RichiestaAnnullamento();
               fillBean(rs, bean);
               lista.add(bean);
           }
        }
        catch (Exception e) {
            logger.fatal(e);
        }
        finally{
            close(rs,stmt);
        }

        return lista;
    }
	
    private void fillBean(ResultSet rs, RichiestaAnnullamento bean) throws SQLException {
       
       bean.setBlocco(rs.getString(RICHIESTA_ANNULLAMENTO.BLOCCO));
       bean.setCancellazione(rs.getString(RICHIESTA_ANNULLAMENTO.CANCELLAZIONE));
       bean.setData_fine(rs.getTimestamp(RICHIESTA_ANNULLAMENTO.DATA_FINE));
       bean.setData_inizio(rs.getTimestamp(RICHIESTA_ANNULLAMENTO.DATA_INIZIO));
       bean.setData_inizio_record(rs.getTimestamp(RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD));
       bean.setDecisore(rs.getString(RICHIESTA_ANNULLAMENTO.DECISORE));
       bean.setEsito(rs.getString(RICHIESTA_ANNULLAMENTO.ESITO));
       bean.setId_lotto(Long.toString(rs.getLong(RICHIESTA_ANNULLAMENTO.ID_LOTTO)));
       bean.setId_record(Long.toString(rs.getLong(RICHIESTA_ANNULLAMENTO.ID_RECORD)));
       bean.setId_richiesta(rs.getLong(RICHIESTA_ANNULLAMENTO.ID_RICHIESTA));
       bean.setIdMotivo(rs.getString(RICHIESTA_ANNULLAMENTO.ID_MOTIVO_RICH));
       bean.setMotivo_esito(rs.getString(RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO));
       bean.setMotivo_richiesta(rs.getString(RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA));
       bean.setRichiedente(rs.getString(RICHIESTA_ANNULLAMENTO.RICHIEDENTE));
    }

	/*************************************************************************************************
	 * Recupera il dettaglio delle richieste di annullamento relative ad una particolare scheda
	 * 
	 * @param scelta String
	 * @param cig_lotto String
	 * @param orderField String
 	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettaglioRichAnnByScheda(String id_scheda, String blocco, boolean cancellazioni, boolean soloVarAnag) throws SQLException{
		return _getDettaglioRichAnnByScheda( id_scheda,  blocco,  cancellazioni,  true, soloVarAnag);		
	}

	/*************************************************************************************************
	 * Recupera il dettaglio delle richieste di annullamento relative ad una particolare scheda
	 * 
	 * @param scelta String
	 * @param cig_lotto String
	 * @param orderField String
 	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean hasSchedaRichDelete(String id_scheda, String blocco, boolean cancellazioni) throws SQLException{
		return _getDettaglioRichAnnByScheda( id_scheda,  blocco,  cancellazioni,  false, false);
	}

	/*************************************************************************************************
	 * Recupera il dettaglio delle richieste di annullamento relative ad una particolare scheda
	 * 
	 * @param scelta String
	 * @param cig_lotto String
	 * @param orderField String
 	 * @return TableBean
	 * @throws SQLException
	 */

	private TableBean _getDettaglioRichAnnByScheda(String id_scheda, String blocco, 
							boolean cancellazioni, boolean pannello,
							boolean soloVarAnag) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		String query = "";
		
		try{
			query = QUERY_BASE_SELECT_RICHIESTE_ANNULLAMENTO_COMPLETA;

			// filtro per selezionare le richieste di annullamento o cancellazione
			// PP nel pannello le vedo tutte  
			query += pannello ? "" : cancellazioni ? WHERE_CANCELLAZIONE : WHERE_ANNULLAMENTO;
			query += pannello ? "" : " AND " + RICHIESTA_ANNULLAMENTO.ESITO + " IS NULL ";
			
			if(id_scheda!=null && !"".equals(id_scheda)){
				query +=" AND " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD +  " = " + id_scheda;
			}
			
			if(blocco!=null && !"".equals(blocco)){
				query +=" AND " + RICHIESTA_ANNULLAMENTO.T_BLOCCO + " = '" + blocco + "'";
			}

			// PP B302.2.0 nascondo le variazioni C.O.
			query +=" AND " + 
				(soloVarAnag ? RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA + "='" + StatiScheda.VARIAZIONE_CO_STRING + "'"
							: RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA + "<>'" + StatiScheda.VARIAZIONE_CO_STRING + "'");

			query = query + " ORDER BY " + RICHIESTA_ANNULLAMENTO.T_ID_RICHIESTA;
			
			logger.debug("Select DETTAGLIO SCHEDA RICHIESTE ANNULLAMENTO, query ["+query+"]");
			stmt = activeConnection.prepareStatement(query);
			
			rs = stmt.executeQuery();
			tb = new TableBean(rs);
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		finally{
			close(rs,stmt);
		}

		return tb;
	}
	
	private final String QUERY_DETTAGLIO_SELECT_RICHIESTE_ANNULLAMENTO =
		QUERY_BASE_SELECT_RICHIESTE_ANN 
		+ " WHERE "//+RICHIESTA_ANNULLAMENTO.DATA_FINE+" IS NULL AND "
		+ RICHIESTA_ANNULLAMENTO.T_ID_RICHIESTA +" = ?";


	/**************************************************************************************************
	 * recupera il dettaglio di una richiesta di annullamento inbase all'id della richiesta
	 *
	 * @param idRichiesta : long
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettaglioRichiesteAnnullamento(long idRichiesta) throws SQLException{
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
			
			stmt = activeConnection.prepareStatement(QUERY_DETTAGLIO_SELECT_RICHIESTE_ANNULLAMENTO);
			logger.debug("Select DETTAGLIO RICHIESTE ANNULLAMENTO, query ["+QUERY_DETTAGLIO_SELECT_RICHIESTE_ANNULLAMENTO+"]");
			stmt.setObject(1, idRichiesta);
			
			rs = stmt.executeQuery();
			tb = new TableBean(rs);
		}
		catch (Exception e) {
			logger.fatal(e);
		}
		finally{
			close(rs,stmt);
		}
		logger.debug(tb.toString());
		return tb;
	}
	
	
	private final String esitoRichiesta =
		"UPDATE "
		+ RICHIESTA_ANNULLAMENTO.TABLE_NAME
		+ " SET "
		+ RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO + "= ?, " // 1
		+ RICHIESTA_ANNULLAMENTO.ESITO + " =?, " //2
		+ RICHIESTA_ANNULLAMENTO.DECISORE + " =?, " //3
		+ RICHIESTA_ANNULLAMENTO.DATA_FINE + " =?" //4
		+ " WHERE "
		+ RICHIESTA_ANNULLAMENTO.ID_RICHIESTA + " = ?"; //5
	
	private final String UPDATE_ESITO_ID_DATA =
		"UPDATE "
		+ RICHIESTA_ANNULLAMENTO.TABLE_NAME
		+ " SET "
		+ RICHIESTA_ANNULLAMENTO.MOTIVO_ESITO + "= ?, " // 1
		+ RICHIESTA_ANNULLAMENTO.ESITO + " =?, " //2
		+ RICHIESTA_ANNULLAMENTO.DECISORE + " =?, " //3
		+ RICHIESTA_ANNULLAMENTO.DATA_FINE + " =?" //4
		+ " WHERE "
		+ RICHIESTA_ANNULLAMENTO.ID_RECORD + " = ? and " //5
		+ RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD + " = ? and "
		+ RICHIESTA_ANNULLAMENTO.ID_LOTTO + " = ? and " //6
		+ RICHIESTA_ANNULLAMENTO.BLOCCO + " = ? and " //7
		+ RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " is null and " //8
		+ RICHIESTA_ANNULLAMENTO.DATA_FINE + " is null"; //9
	  
	
	
	/***************************************************************************************************
	 * inserisce una richiesta di annullamento  
	 * 
	 * @param richAnnullamento RichiestaAnnullamento
	 * @return int - numero di richieste aggiornate
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int inserimentoEsito ( RichiestaAnnullamento richAnnullamento ) throws SQLException, ClassNotFoundException {

		logger.debug("inserimento esito [" + richAnnullamento + "]");
		PreparedStatement ura = null;
		int index=1;
		try {
			
			ura = activeConnection.prepareStatement(richAnnullamento.getId_richiesta() < 1 ? UPDATE_ESITO_ID_DATA : esitoRichiesta);
			logger.debug("Insert RICHIESTA_ANNULLAMENTO, query ["+esitoRichiesta+"]");
		
			ura.setString(index++, richAnnullamento.getMotivo_esito());
			logger.debug("Modifica richiesta motivazione 1[" + richAnnullamento.getMotivo_esito() + "]");
			
			ura.setString(index++, richAnnullamento.getEsito());
			logger.debug("Modifica esito 2[" + richAnnullamento.getEsito() + "]");
			
			ura.setString(index++, richAnnullamento.getDecisore());
			logger.debug("Modifica esito 3[" + richAnnullamento.getDecisore() + "]");
			
			ura.setObject(index++, getNow());
			logger.debug("Modifica esito 4[" + getNow() + "]");
			
			if(richAnnullamento.getId_richiesta() < 1){
				logger.debug("richiesta annullamento automatica...");
				ura.setInt(index++, Integer.parseInt(richAnnullamento.getId_record()));
				ura.setTimestamp(index++, richAnnullamento.getData_inizio_record());
				ura.setInt(index++, Integer.parseInt(richAnnullamento.getId_lotto()));
				ura.setString(index++, richAnnullamento.getBlocco());
			} else {
			ura.setLong(5, Long.valueOf(richAnnullamento.getId_richiesta()));
			logger.debug("Modifica id richiesta 5[" + richAnnullamento.getId_richiesta() + "]");
			}
			int result = ura.executeUpdate();
			
			return result;
		} finally {
			close(null,ura);		
		}
	}


	/******************************************************************************************************
	 * Recupera il record delle richieste di annullamento
	 * 
	 * @param table String
	 * @param columnName String
	 * @param idRecord long 
	 * @return TableBean
	 * @throws SQLException
	 */
	public  TableBean getRecordRichiestaAnnullamento(String table, String columnName, long idRecord) throws SQLException{
		return getRecordRichiestaAnnullamento(table, columnName, idRecord, false);
	}
	
	/******************************************************************************************************
	 * recupera il recor relativo alla presa in carico
	 * 
	 * @param table String
	 * @param columnName String
	 * @param idRecord long
	 * @return TableBean
	 * @throws SQLException
	 */
	public  TableBean getRecordPresaInCarico(String table, String columnName, long idRecord) throws SQLException{
		return getRecordRichiestaAnnullamento(table, columnName, idRecord, true);
	}
	
	/*******************************************************************************************************
	 * Metodo che restituisce il record su cui &egrave; stato richiesto l'annullamento (idStato = 3) e quello ora corrente (idStato in(1,2) )
	 * 
	 * @param table String
	 * @param columnName String 
	 * @param idRecord long
	 * @return TableBean
	 */
	private TableBean getRecordRichiestaAnnullamento(String table, String columnName, long idRecord, boolean presaInCarico) throws SQLException{
			
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
			String query = "SELECT * FROM "+table+" where "+columnName+" = "+idRecord+" and ID_STATO = " + (presaInCarico? StatiScheda.PRESA_IN_CARICO : StatiScheda.ANNULLAMENTO_RICHIESTA);
			stmt = activeConnection.prepareStatement(query);
			logger.debug("Select RECORD RICHIESTA ANNULLATA, query ["+query);
			
			rs = stmt.executeQuery();
			tb = new TableBean(rs);
		}
		finally{
			close(rs,stmt);
		}
		logger.debug(tb.toString());
		return tb;
	}
	
	/****************************************************************************************************************
	 * Metodo che restituisce il record ora attivo (idStato in(1,2) )
	 * 
	 * @param table String
	 * @param columnName String
	 * @param idRecord long
	 * @return TableBean
	 */
	public TableBean getRecordAttivo(String table, String columnName, long idRecord) throws SQLException{
			
		PreparedStatement stmt = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
			String query = "SELECT * FROM "+table+" where "+columnName+" = "+idRecord+" and ID_STATO in (1,2) ";
			stmt = activeConnection.prepareStatement(query);
			logger.debug("Select RECORD ATTUALMENTE ATTIVO, query ["+query);
			
			rs = stmt.executeQuery();
			tb = new TableBean(rs);
		}
		finally{
			close(rs,stmt);
		}
		logger.debug(tb.toString());
		return tb;
	}
	
	
	
	/*****************************************************************************************************
	 * restituisce il valore associato al parametro specificato nel parametro in ingresso value.
	 * 
	 * @param value String 
	 * @return String
	 */
	public static String returnTableName(String value){
		
		HashMap map = new HashMap();
		
		map.put(IdentificativoSchede.TAB_AGGIUDICAZIONE,AGGIUDICAZIONI.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_SOTTOSOGLIA,AGGIUDICAZIONI.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_ESCLUSI,AGGIUDICAZIONI.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_INFO_COMUNI,INFO_AGGIUDICAZIONI.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_INIZIO_LAVORI,INIZIO_LAVORI.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_STIPULA,STIPULA.TABLE_NAME);
		map.put(ParametriServletAvanzamento.TAB_AVANZAMENTO,STATI_AVANZ.TABLE_NAME);
		map.put(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI,FINE_LAVORI.TABLE_NAME);
		map.put(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO,COLLAUDO.TABLE_NAME);
		map.put(ParametriServletAccordo.TAB_SCHEDA_ACCORDO,ACCORDI.TABLE_NAME);
		map.put(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI,SOSPENSIONI.TABLE_NAME);
		map.put(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI,SUBAPPALTI.TABLE_NAME);
		map.put(ParametriServletVariante.TAB_SCHEDA_VARIANTE,VARIANTI.TABLE_NAME);
		map.put(ParametriServletR129.TAB_SCHEDA_R129, R129.TABLE_NAME);
		map.put(IdentificativoSchede.TAB_ADESIONE, AGGIUDICAZIONI.TABLE_NAME);
		
		String tableName = map.get(value).toString();
		
		return tableName;
	}
	
	/**************************************************************************************************
	 *  Recupera il nome della tabella associato alla stringa in ingresso value. 
	 *  
	 * @param value String
	 * @return String
	 */
	public static String returnTableNameAnnullamento(String value){
		
		HashMap map = IdentificativoSchede.getSchede();
		String tableName = map.get(value).toString();
		
		return tableName;
	}
	
	
	/***************************************************************************************************
	 * Restituisce il valore del campo indicato nel parametro
	 * 
	 * @param value String
	 * @return String
	 */
	public static String returnColumnName(String value){
		
		HashMap map = new HashMap();
		
		map.put(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE);
		
		map.put(INFO_AGGIUDICAZIONI.TABLE_NAME,INFO_AGGIUDICAZIONI.ID_INFO);
		map.put(INIZIO_LAVORI.TABLE_NAME,INIZIO_LAVORI.ID_INIZIO);
		map.put(STIPULA.TABLE_NAME,STIPULA.ID_STIPULA);
		map.put(STATI_AVANZ.TABLE_NAME,STATI_AVANZ.ID_AVANZAMENTO);
		map.put(FINE_LAVORI.TABLE_NAME,FINE_LAVORI.ID_ULTIM);
		map.put(ACCORDI.TABLE_NAME,ACCORDI.ID_ACCORDO);
		map.put(SOSPENSIONI.TABLE_NAME,SOSPENSIONI.ID_SOSPENSIONE);
		map.put(SUBAPPALTI.TABLE_NAME,SUBAPPALTI.ID_RECORD);
		map.put(VARIANTI.TABLE_NAME,VARIANTI.ID_VARIANTE);
		map.put(R129.TABLE_NAME,R129.ID_RECORD);
		map.put(COLLAUDO.TABLE_NAME,COLLAUDO.ID_COLLAUDO);
		
		String columnName = map.get(value).toString();		
		return columnName;
	}
	
	/**************************************************************************************************
	 * Effettua la cancellazione del record nella tabella di Richiesta, inserito in modo fittizio
	 * dalla funzione di presa in carico
	 * Annullamento identificato nei parametri in ingresso
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @param blocco String
	 * @throws SQLException
	 */
	public void deleteRecordAnnullamento(long idRecord, Timestamp dataInizioRecord, String blocco)throws SQLException{
		String query = 
			"DELETE FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
			+ " WHERE " + RICHIESTA_ANNULLAMENTO.ID_RECORD + " = ? "
			+ " AND " + RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD + " = ? "
			+ " AND " + RICHIESTA_ANNULLAMENTO.BLOCCO + " = ? "
			+ " AND " + RICHIESTA_ANNULLAMENTO.DATA_FINE + " is null "
			+ " AND " + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " IS NULL" ;  //NEWCIG
		
		PreparedStatement stmt = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(index++, idRecord);
			stmt.setTimestamp(index++, dataInizioRecord);
			stmt.setString(index++, blocco);
			stmt.execute();
		}finally{
			close(null,stmt);
		}
	}
	
    public void deleteRecordCancellazione(long idRecord, Timestamp dataInizioRecord, String blocco)throws SQLException{
       String query = 
           "DELETE FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
           + " WHERE " + RICHIESTA_ANNULLAMENTO.ID_RECORD + " = ? "
           + " AND " + RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD + " = ? "
           + " AND " + RICHIESTA_ANNULLAMENTO.BLOCCO + " = ? "
           + " AND " + RICHIESTA_ANNULLAMENTO.DATA_FINE + " is null ";
       
       PreparedStatement stmt = null;
       int index = 1;
       try{
           stmt = activeConnection.prepareStatement(query);
           stmt.setLong(index++, idRecord);
           stmt.setTimestamp(index++, dataInizioRecord);
           stmt.setString(index++, blocco);
           stmt.execute();
       }finally{
           close(null,stmt);
       }
    }
}
