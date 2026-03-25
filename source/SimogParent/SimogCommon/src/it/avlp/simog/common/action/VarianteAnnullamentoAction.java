package it.avlp.simog.common.action;

import it.avcp.simog.managers.variante.EventiMotiviVariantiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.VARIANTI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class VarianteAnnullamentoAction extends BaseRichiestaAnnullamento {

	public VarianteAnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioVariante = PageHelper.parseTime(row.getNulledField(VARIANTI.DATA_INIZIO_VAR));
		long idVariante = Long.parseLong(row.getNulledField(VARIANTI.ID_VARIANTE));
		VarianteManager varMan = new VarianteManager(connection, logger);
		EventiMotiviVariantiManager evMan = new EventiMotiviVariantiManager(connection,logger);
		
		try{
			evMan.deleteRecord(idVariante, dataInizioVariante);
			numRow = varMan.deleteRecord(idVariante, dataInizioVariante);
			
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
		Timestamp dataInizioVariante = PageHelper.parseTime(row.getNulledField(VARIANTI.DATA_INIZIO_VAR));
		long idVariante = Long.parseLong(row.getNulledField(VARIANTI.ID_VARIANTE));
		VarianteManager varMan = new VarianteManager(connection, logger);
		EventiMotiviVariantiManager evMan = new EventiMotiviVariantiManager(connection,logger);
		try{
			numRow = varMan.updateRecord(idVariante, dataInizioVariante, stato_scheda);
			evMan.updateRecord(idVariante, dataInizioVariante, stato_scheda);
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
