package it.avlp.simog.common.action;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class AccordoAnnullamentoAction extends BaseRichiestaAnnullamento {

	public AccordoAnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioAccordo = PageHelper.parseTime(row.getNulledField(ACCORDI.DATA_INIZIO_ACC));
		long idAccordo = Long.parseLong(row.getNulledField(ACCORDI.ID_ACCORDO));
		AccordoManager accMan = new AccordoManager(connection, logger);
		try{
			numRow = accMan.deleteRecord(idAccordo, dataInizioAccordo);
			
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
		Timestamp dataInizioAccordo = PageHelper.parseTime(row.getNulledField(ACCORDI.DATA_INIZIO_ACC));
		long idAccordo = Long.parseLong(row.getNulledField(ACCORDI.ID_ACCORDO));
		AccordoManager accMan = new AccordoManager(connection, logger);
		try{
			numRow = accMan.updateRecord(idAccordo, dataInizioAccordo, stato_scheda);
			
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
