package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.log4j.Logger;

public class StipulaAnnullamentoAction extends BaseRichiestaAnnullamento {

	public StipulaAnnullamentoAction(Connection activeConnection,Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioStipula = PageHelper.parseTime(row.getNulledField(STIPULA.DATA_INIZIO_STIPULA));
		long idStipula = Long.parseLong(row.getNulledField(STIPULA.ID_STIPULA));
		long idPubblicazione = Long.parseLong(row.getNulledField(STIPULA.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(STIPULA.DATA_INIZIO_PUBB));
		StipulaManager sMan = new StipulaManager(connection, logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		
		
		try{
			numRow = sMan.deleteRecord(idStipula, dataInizioStipula);
			pubMan.deletePubblicazione(idPubblicazione, dataPubblicazione);
			
		}catch (SQLException e) {
			throw new ActionException(e);				
		}
		
		return numRow > 0;
	}

	@Override
	protected boolean updateRecords(TableBean recordRichAnnullamento,
			String tableName, String stato_scheda) throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioStipula = PageHelper.parseTime(row.getNulledField(STIPULA.DATA_INIZIO_STIPULA));
		long idStipula = Long.parseLong(row.getNulledField(STIPULA.ID_STIPULA));
		long idPubblicazione = Long.parseLong(row.getNulledField(STIPULA.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(STIPULA.DATA_INIZIO_PUBB));
		StipulaManager sMan = new StipulaManager(connection, logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		try{
			numRow = sMan.updateRecord(idStipula, dataInizioStipula, stato_scheda);
			pubMan.updateRecordPubblicazione(idPubblicazione, dataPubblicazione, stato_scheda);

		}
		catch (SQLException e) {
			throw new ActionException(e);				
		}	
		return numRow > 0;
	}

	@Override
	protected boolean annullaRecords(TableBean recordRichAnnullamento) throws ActionException {

		return updateRecords(recordRichAnnullamento, null, StatiScheda.ELIMINATO_STRING);
	}
}
