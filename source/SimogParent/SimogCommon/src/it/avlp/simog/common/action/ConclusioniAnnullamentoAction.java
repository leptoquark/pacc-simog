package it.avlp.simog.common.action;

import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class ConclusioniAnnullamentoAction extends BaseRichiestaAnnullamento {

	public ConclusioniAnnullamentoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioUltim = PageHelper.parseTime(row.getNulledField(FINE_LAVORI.DATA_INIZIO_ULTIM));
		long idUltim = Long.parseLong(row.getNulledField(FINE_LAVORI.ID_ULTIM));
		ConclusioniManager conMan = new ConclusioniManager(connection, logger);
		try{
			numRow = conMan.deleteRecord(idUltim, dataInizioUltim);
			
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
		Timestamp dataInizioUltim = PageHelper.parseTime(row.getNulledField(FINE_LAVORI.DATA_INIZIO_ULTIM));
		long idUltim = Long.parseLong(row.getNulledField(FINE_LAVORI.ID_ULTIM));
		ConclusioniManager conMan = new ConclusioniManager(connection, logger);
		try{
			numRow = conMan.updateRecord(idUltim, dataInizioUltim, stato_scheda);
			
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
