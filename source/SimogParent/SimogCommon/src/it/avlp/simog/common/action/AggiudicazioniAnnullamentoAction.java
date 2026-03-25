package it.avlp.simog.common.action;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class AggiudicazioniAnnullamentoAction extends BaseRichiestaAnnullamento {

	public AggiudicazioniAnnullamentoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare) throws ActionException {
		
//		return this.rifiutaRichiestaAnnullamento(recordRichAnnullamento, tableName, dataRecordDaAnnullare);
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioAggiudicazione = PageHelper.parseTime(row.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
		String idAggiudicazione = row.getNulledField(AGGIUDICAZIONI.ID_AGGIUDICAZIONE);
		//******MANAGER ***************/
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection,logger);
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(connection,logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(connection,logger);
		CondizioniManager condizioniManager = new CondizioniManager(connection,logger);
		RequisitiManager requisitiManager = new RequisitiManager(connection,logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(connection,logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(connection,logger);
		AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(connection,logger);		
		
		try {	
			dittaAusiliariaManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			aggiudicatarioManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			responsabileManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			condizioniManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			requisitiManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			tipoAppaltoManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			finanziamentoManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			numRow = aggiudicazioniManager.deleteRecord(idAggiudicazione, dataInizioAggiudicazione);
		} catch (SQLException e) {
			throw new ActionException(e);				
		}
		
		return numRow > 0;
	}

	@Override
	protected boolean updateRecords(TableBean recordRichAnnullamento,
			String tableName, String stato_scheda)throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		String idAggiudicazione = row.getNulledField(AGGIUDICAZIONI.ID_AGGIUDICAZIONE);
		Timestamp dataInizioAggiudicazione = PageHelper.parseTime(row.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
		//******MANAGER ***************/
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection,logger);
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(connection,logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(connection,logger);
		CondizioniManager condizioniManager = new CondizioniManager(connection,logger);
		RequisitiManager requisitiManager = new RequisitiManager(connection,logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(connection,logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(connection,logger);
		AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(connection,logger);
		try{
		   dittaAusiliariaManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			numRow=aggiudicazioniManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			aggiudicatarioManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			responsabileManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			condizioniManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			requisitiManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
			finanziamentoManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);

			Gara gara = getGaraByAgg(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione);
			
         if(SimogFlags.is3031_RFWEBGL00Active() 
               && SimogProperties.getInstance().isCUPAttivo()){
            
            boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(gara.getData_creazione());
            if( !okDataAttivazioneCup ){
               // Competenza Aggiudicazione
               tipoAppaltoManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
            } else {
               // Competenza Lotto
               tipoAppaltoManager.cancellaDatiAgg(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione);
            }
         }
         else{
            tipoAppaltoManager.updateRecord(idAggiudicazione,dataInizioAggiudicazione,stato_scheda);
         }

			//tolgo i riferimenti all'aggiudicazione sui cup associati al lotto o li cancello
			if( SimogFlags.is3031_RFWEBGL02Active() 
			      && SimogProperties.getInstance().isCUPAttivo()){

			   boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(gara.getData_creazione());
			   CupLottoAggManager claMan = new CupLottoAggManager(connection, logger);
			   if( !okDataAttivazioneCup ){
			     // Competenza Aggiudicazione
			     claMan.updateRecord(idAggiudicazione, dataInizioAggiudicazione, stato_scheda);

			     // devo anche annullare il flag su lotto!
	           try {
   			     AggiudicazioniManager am = new AggiudicazioniManager(connection, logger);
   			     AggiudicazioneBean agg = am.getAggiudicazioni(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione, true);
   			     
   			     InfoComuniManager icm = new InfoComuniManager(connection, logger);
   			     InfoComuniBean dat = icm.load(agg.getIdInfo(), agg.getDataInizioInfo());
   			     
   	           LottoManager lm = new LottoManager(connection, logger);
   	           Lotto lotto;
   	           lotto = lm.getLotto(dat.getIdLotto());
   	           lotto.setFLAG_CUP(null);
   	           lm.updateFlagCup(lotto);
   	           
	           } catch (Exception e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	           }
			   } 
			   else {
			     // Competenza Lotto
			     claMan.cancellaDatiAggCup(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione);
			  }
			}
			
						
		} catch (SQLException e) {
			throw new ActionException(e);				
		}
		
		return numRow > 0;
	}
	
   /**
	 * Metodo nato in replace della combinazione delete/update record per aggiudicazione
	 * in quanto la sequenza di operazioni non risulta banale.
	 * 
	 * @param recordRichAnnullamento
	 * @param tableName
	 * @param dataRecordDaAnnullare
	 * @return
	 * @throws ActionException
	 */
	public boolean rifiutaRichiestaAnnullamento(TableBean recordAttivo,TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)throws ActionException{

		int numRow = 0;
		TableBeanRow row = recordAttivo.getRow(0);
		TableBeanRow rowOld = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioAggiudicazione = PageHelper.parseTime(row.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
		Timestamp dataFineOldAggiudicazione = PageHelper.parseTime(rowOld.getNulledField(AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE));
		String idAggiudicazione = row.getNulledField(AGGIUDICAZIONI.ID_AGGIUDICAZIONE);
		//Timestamp dataInizioAggiudicazione = dataRecordDaAnnullare;
		//******MANAGER ***************/
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(connection,logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(connection,logger);
		CondizioniManager condizioniManager = new CondizioniManager(connection,logger);
		RequisitiManager requisitiManager = new RequisitiManager(connection,logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(connection,logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(connection,logger);
		AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(connection,logger);		
		DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection,logger);
		
		try {	
			//cancellazione nuovi record su schede collegate
			dittaAusiliariaManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			aggiudicatarioManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			responsabileManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			condizioniManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			requisitiManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			tipoAppaltoManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			finanziamentoManager.deleteRecord(idAggiudicazione,dataInizioAggiudicazione);
			
			//aggiornamento dei vecchi record delle schede collegate in modo che puntino al nuovo record di aggiudicazione (nel contempo aggiorno lo stato)
				
				Timestamp dataInizioAggiudicazioneVecchia = dataRecordDaAnnullare;
				String stato_scheda = StatiScheda.CONFERMATO_STRING;
				
			dittaAusiliariaManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			aggiudicatarioManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			responsabileManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			condizioniManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			requisitiManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			tipoAppaltoManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			finanziamentoManager.updateRecordToPointToNew(idAggiudicazione,dataInizioAggiudicazioneVecchia,dataInizioAggiudicazione,stato_scheda);
			
			//cancellazione del vecchio record di aggiudicazione
			numRow = aggiudicazioniManager.deleteRecord(idAggiudicazione, dataInizioAggiudicazioneVecchia);
			if(numRow > 0){
				//aggiornamento del nuovo record di aggiudicazione, con i dati vecchi (cascading per aggionramento delle date sulle schede dipendenti).
				numRow = aggiudicazioniManager.updateRecordAndData(idAggiudicazione,dataInizioAggiudicazione,dataInizioAggiudicazioneVecchia,dataFineOldAggiudicazione,stato_scheda);
			}else{
				throw new SQLException("No record found for ["+idAggiudicazione+", "+dataInizioAggiudicazioneVecchia+"]");
			}
			
		} catch (SQLException e) {
			throw new ActionException(e);				
		}
		
		return numRow > 0;

	}

	@Override
	protected boolean annullaRecords(TableBean recordRichAnnullamento) throws ActionException {

		return updateRecords(recordRichAnnullamento, null, StatiScheda.ELIMINATO_STRING);
	}	
}
