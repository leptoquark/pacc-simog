package it.avlp.simog.common.action;

import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.gestioneannullamentomanager.AnnullamentoManager;
import it.avlp.simog.managers.log.LogBloccoDatiManager;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class BaseRichiestaAnnullamento extends BaseSharedAction{
	
	protected BaseRichiestaAnnullamento(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}
	
	/*********************************************************************************************
	 * Gestisce le richieste di annullamento
	 * @param tipoRichiesta String
	 * @return TableBean
	 * @throws ActionException
	 */
/* PP non usata?	public final  TableBean getRichiesteAnnullamento(String tipoRichiesta, String orderField) throws ActionException{
		AnnullamentoManager annMan = new AnnullamentoManager(connection,logger);
		try{
			return annMan.getRichiesteAnnullamento(tipoRichiesta, orderField);
		}catch (Exception e) {
			logger.fatal(e);
			throw new ActionException(e);
		}
	}
*/	
	/*********************************************************************************************
	 * Gestisce le richieste di annullamento
	 * @param tipoRichiesta String
	 * @return TableBean
	 * @throws ActionException
	 */
	public final  TableBean getRichiesteAnnullamento(String tipoRichiesta, String cig_lotto, String id_osservatorio, String orderField, int startRow, int maxRowsAllowed, boolean cancellazioni) throws ActionException{
		AnnullamentoManager annMan = new AnnullamentoManager(connection,logger);
		try{
			return annMan.getRichiesteAnnullamento(tipoRichiesta, cig_lotto, id_osservatorio, orderField, startRow, maxRowsAllowed, cancellazioni);
		}catch (Exception e) {
			logger.fatal(e);
			throw new ActionException(e);
		}		
		
	}

	public final  List<RichiestaAnnullamento> getRichiesteAnnullamentoBean(String tipoRichiesta, String cig_lotto, String id_osservatorio, String orderField, int startRow, int maxRowsAllowed, boolean cancellazioni) throws ActionException{
	        AnnullamentoManager annMan = new AnnullamentoManager(connection,logger);
	        try{
	            return annMan.getRichiesteAnnullamentoBean(tipoRichiesta, cig_lotto, id_osservatorio, orderField, startRow, maxRowsAllowed, cancellazioni);
	        }catch (Exception e) {
	            logger.fatal(e);
	            throw new ActionException(e);
	        }       
	        
	    }
/***********************************************************************************************
	 * Ottiene il dettaglio della richiesta di annullamento
	 * @param idRichiesta long
	 * @return TableBean
	 * @throws ActionException
	 */
	public final  TableBean getDettaglioRichiestaAnnullamento(long idRichiesta) throws ActionException{
		AnnullamentoManager annMan = new AnnullamentoManager(connection,logger);
		try{
			return annMan.getDettaglioRichiesteAnnullamento(idRichiesta);
		}catch (Exception e) {
			logger.fatal(e);
			throw new ActionException(e);
		}
		
		
	}
	
	
	/*****************************************************************************************************
	 * Gestisce la richiesta
	 * @param bean RichiestaAnnullamento
	 * @param cfUtente String
	 * @return boolean
	 * @throws ActionException
	 */
	public final boolean gestisciRichiesta(it.avlp.simog.beans.RichiestaAnnullamento bean, String cfUtente) throws ActionException{
		return gestisciRichiesta(bean, cfUtente, false);
	}
	
	/*****************************************************************************************************
	 * Gestisce la presa in carico
	 * @param bean RichiestaAnnullamento
	 * @param cfUtente String
	 * @return boolean
	 * @throws ActionException
	 */
	
	public boolean gestisciPresaInCarico(it.avlp.simog.beans.RichiestaAnnullamento bean, String cfUtente)throws ActionException{
		return gestisciRichiesta(bean, cfUtente, true);
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta , se presaInCarico risulta false viene effettuato l'inserimento altrimenti 
	 * param bean RichiestaAnnullamento
	 * param cfUtente String
	 * param presaInCarico boolean 
	 * return boolean
	 * throws ActionException
	 */
	
	private boolean gestisciRichiesta(it.avlp.simog.beans.RichiestaAnnullamento bean, String cfUtente, boolean presaInCarico)throws ActionException{
		AnnullamentoManager annMan = new AnnullamentoManager(connection, logger);
		TableBean recordRichAnnullamento =null; 
		TableBean recordAttivo = null;
		try{
			String tableName = AnnullamentoManager.returnTableName(bean.getBlocco());
			String columnName = AnnullamentoManager.returnColumnName(tableName);
			recordRichAnnullamento = presaInCarico? annMan.getRecordPresaInCarico(tableName,columnName, Long.parseLong(bean.getId_record())) : annMan.getRecordRichiestaAnnullamento(tableName,columnName, Long.parseLong(bean.getId_record()));
			recordAttivo = annMan.getRecordAttivo(tableName,columnName, Long.parseLong(bean.getId_record()));
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getId_record());
			attributiChiave.add(bean.getData_inizio_record());
			
			if(!presaInCarico)
				annMan.inserimentoEsito(bean);
			else annMan.deleteRecordAnnullamento(Long.parseLong(bean.getId_record()), bean.getData_inizio_record(), bean.getBlocco());
			
			if(presaInCarico || RichiestaAnnullamento.RICHIESTA_ACCETTATA.equals(bean.getEsito())){
				
				boolean result =  presaInCarico? updateRecords(recordRichAnnullamento,tableName,StatiScheda.PRESA_IN_CARICO_STRING) : updateRecords(recordRichAnnullamento,tableName,StatiScheda.ANNULLATO_STRING);
				if(result){
					if(presaInCarico)
					   LogBloccoDatiManager.loggingPRESACAR(connection, logger, cfUtente, bean.getBlocco(), attributiChiave);
					else
					   LogBloccoDatiManager.loggingCONFIRMANN(connection, logger, cfUtente, bean.getBlocco(), attributiChiave);
				}
				return result;
			}
			else if(RichiestaAnnullamento.RICHIESTA_RIFIUTATA.equals(bean.getEsito())){
				boolean result =  true;
				/** distinzione di comportamento per aggiudicazione per via delle FK **/
				//
				if(!tableName.equals(AGGIUDICAZIONI.TABLE_NAME)){
					result = result && deleteRecords(recordAttivo,tableName,bean.getData_inizio_record()) ;
					result = result && updateRecords(recordRichAnnullamento,tableName, StatiScheda.CONFERMATO_STRING);
				}else{
					//l'implementazione di questo metodo per aggiudicazione e' diverso comprende
					//anche update records in qualche modo
					result = result && rollbackRecords(recordAttivo,recordRichAnnullamento,tableName,bean.getData_inizio_record()) ;
				}
				if(result)
					LogBloccoDatiManager.loggingREVCANCEL(connection, logger,cfUtente, bean.getBlocco(), attributiChiave);
				return result;
			}
			else throw new Exception(Messaggi.SIMOG_VALIDAZIONE_000);
		}catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta , se presaInCarico risulta false viene effettuato l'inserimento altrimenti 
	 * param bean RichiestaAnnullamento
	 * param cfUtente String
	 * param presaInCarico boolean 
	 * return boolean
	 * throws ActionException
	 */
	
	public boolean gestisciRichiestaCancellazione(RichiestaAnnullamento bean, String login)throws ActionException{
		AnnullamentoManager annMan = new AnnullamentoManager(connection, logger);
		TableBean recordAttivo = null;
		try{
			String tableName = AnnullamentoManager.returnTableName(bean.getBlocco());
			String columnName = AnnullamentoManager.returnColumnName(tableName);
			recordAttivo = annMan.getRecordAttivo(tableName,columnName, Long.parseLong(bean.getId_record()));
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getId_record());
			attributiChiave.add(bean.getData_inizio_record());
			
			annMan.inserimentoEsito(bean);
			
			if(RichiestaAnnullamento.RICHIESTA_ACCETTATA.equals(bean.getEsito())){
				

				// TODO: PP manca la gestione della richiesta di cancellazione completa
				boolean result = annullaRecords(recordAttivo);
				if(result)
					LogBloccoDatiManager.loggingCONFIRMCANC(connection, logger, login, bean.getBlocco(), attributiChiave);
				return result;
			}
			else if(RichiestaAnnullamento.RICHIESTA_RIFIUTATA.equals(bean.getEsito())){
				// solo log del rifiuto
				LogBloccoDatiManager.loggingREVDELETE(connection, logger,login, bean.getBlocco(), attributiChiave);
				return true;
			}
			else throw new Exception(Messaggi.SIMOG_VALIDAZIONE_000);
		}catch (Exception e) {
			logger.fatal(e);
			throw new ActionException(e);
		}
	}

	protected abstract boolean updateRecords(TableBean recordRichAnnullamento, String tableName, String stato_scheda) throws ActionException;
	protected abstract boolean deleteRecords(TableBean recordRichAnnullamento, String tableName,Timestamp dataRecordDaAnnullare) throws ActionException;
	/** lo so non e' bello ma se non voglio cambiare tutto..**/
	protected boolean rollbackRecords(TableBean recordAttivo,TableBean recordRichAnnullamento, String tableName,Timestamp dataRecordDaAnnullare) throws ActionException{
		AggiudicazioniAnnullamentoAction richAnnAction = new AggiudicazioniAnnullamentoAction(connection,logger);
		return richAnnAction.rifiutaRichiestaAnnullamento(recordAttivo, recordRichAnnullamento, tableName, dataRecordDaAnnullare);
	}

	protected abstract boolean annullaRecords(TableBean recordRichAnnullamento) throws ActionException;
		
}
