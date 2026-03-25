package it.avlp.simog.common.action;

import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class SubappaltiAnnullamentoAction extends BaseRichiestaAnnullamento {

	public SubappaltiAnnullamentoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioSubappalto = PageHelper.parseTime(row.getNulledField(SUBAPPALTI.DATA_INIZIO_RECORD));
		long idSubappalto = Long.parseLong(row.getNulledField(SUBAPPALTI.ID_RECORD));
		SubappaltiManager sospMan = new SubappaltiManager(connection, logger);
		try{
			numRow = sospMan.deleteRecord(idSubappalto, dataInizioSubappalto);
			
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
		Timestamp dataInizioSubappalto = PageHelper.parseTime(row.getNulledField(SUBAPPALTI.DATA_INIZIO_RECORD));
		long idSubappalto = Long.parseLong(row.getNulledField(SUBAPPALTI.ID_RECORD));
		SubappaltiManager sospMan = new SubappaltiManager(connection, logger);
		try{
			numRow = sospMan.updateRecord(idSubappalto, dataInizioSubappalto, stato_scheda);
			
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
