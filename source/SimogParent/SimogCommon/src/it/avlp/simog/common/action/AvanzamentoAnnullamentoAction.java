package it.avlp.simog.common.action;

import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class AvanzamentoAnnullamentoAction extends BaseRichiestaAnnullamento {

	public AvanzamentoAnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	
	}


	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioAccordo = PageHelper.parseTime(row.getNulledField(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO));
		long idAccordo = Long.parseLong(row.getNulledField(STATI_AVANZ.ID_AVANZAMENTO));
		AvanzamentoManager avMan = new AvanzamentoManager(connection, logger);
		try{
			numRow = avMan.deleteRecord(idAccordo, dataInizioAccordo);

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
		Timestamp dataInizioAccordo = PageHelper.parseTime(row.getNulledField(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO));
		long idAvanzamento = Long.parseLong(row.getNulledField(STATI_AVANZ.ID_AVANZAMENTO));
		AvanzamentoManager avMan = new AvanzamentoManager(connection, logger);
		try{
			numRow = avMan.updateRecord(idAvanzamento, dataInizioAccordo, stato_scheda);
			
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
