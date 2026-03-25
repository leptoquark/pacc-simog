package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class InizioLavoriAnnullamentoAction extends BaseRichiestaAnnullamento {

	public InizioLavoriAnnullamentoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}

	@Override
	protected boolean deleteRecords(TableBean recordRichAnnullamento,
			String tableName, Timestamp dataRecordDaAnnullare)
			throws ActionException {
		int numRow = 0;
		TableBeanRow row = recordRichAnnullamento.getRow(0);
		Timestamp dataInizioLavori = PageHelper.parseTime(row.getNulledField(INIZIO_LAVORI.DATA_INIZIO_INIZIO));
		long idInizioLavori = Long.parseLong(row.getNulledField(INIZIO_LAVORI.ID_INIZIO));
		long idPubblicazione = Long.parseLong(row.getNulledField(INIZIO_LAVORI.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(INIZIO_LAVORI.DATA_INIZIO_PUBB));
		ResponsabileInizioManager responsabileMan = new ResponsabileInizioManager(connection, logger);
		PosizAggiudManager posizioneMan = new PosizAggiudManager(connection, logger);
		InizioLavoriManager inizioMan = new InizioLavoriManager(connection, logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		
		
		try{
			
			responsabileMan.deleteRecord(idInizioLavori, dataInizioLavori);
			posizioneMan.deleteRecord(idInizioLavori, dataInizioLavori);
			numRow = inizioMan.deleteRecord(idInizioLavori, dataInizioLavori);
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
		Timestamp dataInizioLavori = PageHelper.parseTime(row.getNulledField(INIZIO_LAVORI.DATA_INIZIO_INIZIO));
		long idInizioLavori = Long.parseLong(row.getNulledField(INIZIO_LAVORI.ID_INIZIO));
		long idPubblicazione = Long.parseLong(row.getNulledField(INIZIO_LAVORI.ID_PUBBLICAZIONE));
		Timestamp dataPubblicazione = PageHelper.parseTime(row.getNulledField(INIZIO_LAVORI.DATA_INIZIO_PUBB));
		ResponsabileInizioManager responsabileMan = new ResponsabileInizioManager(connection, logger);
		PosizAggiudManager posizioneMan = new PosizAggiudManager(connection, logger);
		InizioLavoriManager inizioMan = new InizioLavoriManager(connection, logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection, logger);
		try{
			numRow = inizioMan.updateRecord(idInizioLavori, dataInizioLavori, stato_scheda);
			pubMan.updateRecordPubblicazione(idPubblicazione, dataPubblicazione, stato_scheda);

			posizioneMan.updateRecord(idInizioLavori, dataInizioLavori, stato_scheda);
			responsabileMan.updateRecord(idInizioLavori, dataInizioLavori, stato_scheda);
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
