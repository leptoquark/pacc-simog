package it.avlp.simog.common.action;

import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.rubricamanager.RubricaManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class PosizioneAggiudicataroSharedAction extends BaseSharedAction {
	public static String CLAZZ = "PosizioneAggiudicataroSharedAction";
	public PosizioneAggiudicataroSharedAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}

	
	/**
	 * metodo per il caricamento delle posizioni aggiudicatario legate all'id/datainizio di inizio lavori
	 * @param idInizioLavori long	
	 * @param dataInizioLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;PosizioneAggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	public List<PosizioneAggiudicatarioBean> loadMany(long idInizioLavori,Timestamp dataInizioLavori, boolean ignoraStato) throws ActionException {

		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		PosizAggiudManager posMan = new PosizAggiudManager(connection, logger);
				
		List<PosizioneAggiudicatarioBean> result = null;
		try {
			result =  posMan.loadMany(idInizioLavori, dataInizioLavori, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return result;
	}
	 

	/**
	 * metodo che inserisci nel db la lista di posizioni aggiudicatario nel db
	 * @param aggiudicatari List&lt;PosizioneAggiudicatarioBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void save(List<PosizioneAggiudicatarioBean> aggiudicatari,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {

		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PosizAggiudManager posMan = new PosizAggiudManager(connection, logger);
	
		try {
			posMan.deleteRecord(idInizioLavori, dataInizioLavori);
			for(PosizioneAggiudicatarioBean bean: aggiudicatari ){
				bean.setIdInizioLavori(idInizioLavori);
				bean.setDataInizioLavori(dataInizioLavori);
				posMan.save(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	/*
	 * @param request
	 * 
	 * @throws ActionException
	 * 
	 * Salva nel DB i dati inseriti dall'utente e conferma
	 */
	/**
	 * metodo che conferma le posizioni aggiudicaztario della lista in ingresso
	 * 
	 * @param aggiudicatari List&lt;PosizioneAggiudicatarioBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<PosizioneAggiudicatarioBean> aggiudicatari,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {

		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PosizAggiudManager posMan = new PosizAggiudManager(connection, logger);
	
		try {
			posMan.deleteRecord(idInizioLavori, dataInizioLavori);
			for(PosizioneAggiudicatarioBean bean: aggiudicatari ){
				bean.setIdInizioLavori(idInizioLavori);
				bean.setDataInizioLavori(dataInizioLavori);
				posMan.confirm(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	
	/******************************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		PosizAggiudManager posMan = new PosizAggiudManager(connection, logger);
		
		try {
		
			return posMan.copyRecord(Long.parseLong(bean.getId_record()), bean.getData_inizio_record(), datavecchia);
		
		} catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}	
	}
}
