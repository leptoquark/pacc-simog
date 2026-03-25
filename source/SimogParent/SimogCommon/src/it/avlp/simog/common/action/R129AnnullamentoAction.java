package it.avlp.simog.common.action;

import it.avcp.simog.managers.r129.R129Manager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class R129AnnullamentoAction extends BaseRichiestaAnnullamento {

	public R129AnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
	
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioR129 = PageHelper.parseTime(row.getNulledField(R129.DATA_INIZIO));
		long idR129 = Long.parseLong(row.getNulledField(R129.ID_RECORD));
		R129Manager r129Man = new R129Manager(connection, logger);
		try{
			numRow = r129Man.deleteRecord(idR129, dataInizioR129);
			
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
		Timestamp dataInizioR129 = PageHelper.parseTime(row.getNulledField(R129.DATA_INIZIO));
		long idR129 = Long.parseLong(row.getNulledField(R129.ID_RECORD));
		R129Manager r129Man = new R129Manager(connection, logger);
		try{
			numRow = r129Man.updateRecord(idR129, dataInizioR129, stato_scheda);
			
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
