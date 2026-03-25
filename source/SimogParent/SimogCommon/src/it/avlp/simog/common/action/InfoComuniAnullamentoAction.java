package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class InfoComuniAnullamentoAction extends BaseRichiestaAnnullamento {

	public InfoComuniAnullamentoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
	
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioInfo = PageHelper.parseTime(row.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
		String idInfo = row.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO);
	
		long idPubblicazione = Long.parseLong(row.getNulledField(INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_PUBB));
		
		InfoComuniManager infoComuni = new InfoComuniManager(connection,logger); 
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		
		try {	
			numRow=infoComuni.deleteRecordInfoAggiudicazioni(idInfo,dataInizioInfo, dataRecordDaAnnullare);
			pubMan.deletePubblicazione(idPubblicazione, dataPubblicazione);
					
		} catch (SQLException e) {
		
			throw new ActionException(e);				
		}
		return numRow > 0;
	}

	@Override
	protected boolean updateRecords(TableBean recordRichAnnullamento,
			String tableName, String stato_scheda) throws ActionException {
		int numRow=-1;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioInfo = PageHelper.parseTime(row.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
		String idInfo = row.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO);
	
		long idPubblicazione = Long.parseLong(row.getNulledField(INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_PUBB));
		
		InfoComuniManager infoComuniManager = new InfoComuniManager(connection,logger); 
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		
		try{
			numRow=infoComuniManager.updateRecord(idInfo,dataInizioInfo,stato_scheda);
			pubMan.updateRecordPubblicazione(idPubblicazione,dataPubblicazione, stato_scheda);
		}catch (SQLException e) {
		
			throw new ActionException(e);				
		}
		
		return numRow > 0;
	}

	@Override
	protected boolean annullaRecords(TableBean recordRichAnnullamento) throws ActionException {

		return updateRecords(recordRichAnnullamento, null, StatiScheda.ELIMINATO_STRING);
	}
}
