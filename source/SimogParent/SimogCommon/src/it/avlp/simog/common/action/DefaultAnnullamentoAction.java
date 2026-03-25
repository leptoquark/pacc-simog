package it.avlp.simog.common.action;

import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class DefaultAnnullamentoAction extends BaseRichiestaAnnullamento {

	public DefaultAnnullamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		
		return false;
	}

	@Override
	protected boolean updateRecords(TableBean recordRichAnnullamento,
			String tableName, String stato_scheda) throws ActionException {
		
		return false;
	}

	@Override
	protected boolean annullaRecords(TableBean recordRichAnnullamento)
			throws ActionException {
		return false;
	}

}
