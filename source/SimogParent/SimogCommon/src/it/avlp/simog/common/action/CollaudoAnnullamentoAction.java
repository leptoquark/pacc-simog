package it.avlp.simog.common.action;

import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class CollaudoAnnullamentoAction extends BaseRichiestaAnnullamento {

	public CollaudoAnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioCollaudo = PageHelper.parseTime(row.getNulledField(COLLAUDO.DATA_INIZIO_COLL));
		long idCollaudo = Long.parseLong(row.getNulledField(COLLAUDO.ID_COLLAUDO));
		ResponsabileCollManager resMan = new ResponsabileCollManager(connection, logger);
		CollaudoManager collMan = new CollaudoManager(connection, logger);
		try{
			resMan.deleteRecord(idCollaudo, dataInizioCollaudo);
			numRow = collMan.deleteRecord(idCollaudo, dataInizioCollaudo);
			
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
		Timestamp dataInizioCollaudo = PageHelper.parseTime(row.getNulledField(COLLAUDO.DATA_INIZIO_COLL));
		long idCollaudo = Long.parseLong(row.getNulledField(COLLAUDO.ID_COLLAUDO));
		ResponsabileCollManager resMan = new ResponsabileCollManager(connection, logger);
		CollaudoManager collMan = new CollaudoManager(connection, logger);
		try{
			resMan.updateRecord(idCollaudo, dataInizioCollaudo, stato_scheda);
			numRow = collMan.updateRecord(idCollaudo, dataInizioCollaudo, stato_scheda);
			
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
