package it.avlp.simog.massload.cancellazione.business;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.commons.ConfigurationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Raggruppa tutte le operazioni per effettuare la cancellazione 
 * delle aggiudicazioni.
 * 
 * @author vletizia
 *
 */
public class EliminazioneAggiudicazione {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	public EliminazioneAggiudicazione(Connection con, Logger logger,String cfUtente) {
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	/**
	 * Cancellazione di aggiudicazioni (e dati correlati) tramite i riferimenti della scheda aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException, Exception{
		
		AggiudicazioniManager aggiudicazioniManger = new AggiudicazioniManager(con, logger);
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(con, logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(con, logger);
		CondizioniManager condizioniManager = new CondizioniManager(con, logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(con, logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(con, logger);
		RequisitiManager requisitiManagerv = new RequisitiManager(con, logger);
	    DittaAusiliariaManager ausiliarieManager = new DittaAusiliariaManager(con, logger);

		boolean esitoOperazione = true;
		
	    esitoOperazione = ausiliarieManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
	    if(!esitoOperazione) logger.debug("Non sono presenti Ditte Ausiliarie per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

		esitoOperazione = aggiudicatarioManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Aggiudicatari per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

		esitoOperazione = responsabileManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

		esitoOperazione = condizioniManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Condizioni per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

		esitoOperazione = finanziamentoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Finanziamenti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

      BaseSharedAction bsa = new BaseSharedAction(con, logger) {};
      Gara gara = bsa.getGaraByAgg(idAggiudicazione, dataInizioAggiudicazione);
      
      if(SimogFlags.is3031_RFWEBGL00Active() 
            && SimogProperties.getInstance().isCUPAttivo()){
         
         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(gara.getData_creazione());
         if( !okDataAttivazioneCup ){
            // Competenza Aggiudicazione
            esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
         } else {
            // Competenza Lotto
            tipoAppaltoManager.cancellaDatiAgg(Long.valueOf(idAggiudicazione), dataInizioAggiudicazione);
            esitoOperazione = true;
         }
      }
      else{
         esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
      }
		if(!esitoOperazione) logger.debug("Non sono presenti TipoAppalti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

		esitoOperazione = requisitiManagerv.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Requisiti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		
      if( SimogFlags.is3031_RFWEBGL02Active() 
            && SimogProperties.getInstance().isCUPAttivo()){

         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(PageHelper.getFormattedDBDate(gara.getData_creazione()));
         CupLottoAggManager claMan = new CupLottoAggManager(con, logger);
         if( !okDataAttivazioneCup ){
           // Competenza Aggiudicazione
           claMan.updateRecord(String.valueOf(idAggiudicazione), dataInizioAggiudicazione, StatiScheda.ANNULLATO_STRING);
           
           // devo anche annullare il flag su lotto!
           try {
              AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
              AggiudicazioneBean agg = am.getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, true);
              
              InfoComuniManager icm = new InfoComuniManager(con, logger);
              InfoComuniBean dat = icm.load(agg.getIdInfo(), agg.getDataInizioInfo());
              
              LottoManager lm = new LottoManager(con, logger);
              Lotto lotto;
              lotto = lm.getLotto(dat.getIdLotto());
              lotto.setFLAG_CUP(null);
              lm.updateFlagCup(lotto);
              
           } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }

        } else {
           // Competenza Lotto
           claMan.cancellaDatiAggCup(idAggiudicazione, dataInizioAggiudicazione);
        }
      }

		esitoOperazione = aggiudicazioniManger.annulla(idAggiudicazione, dataInizioAggiudicazione, cfUtente);
		
		return esitoOperazione;		
	}
	/**
	 * Cancellazione di aggiudicazioni (e dati correlati) tramite l'id della aggiudicazione
	 * 
	 * @param idAggiudicazioneString
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idAggiudicazioneString) throws SQLException{ 
		
		AggiudicazioniManager aggiudicazioniManger = new AggiudicazioniManager(con, logger);
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(con, logger);
		DittaAusiliariaManager ausiliarieManager = new DittaAusiliariaManager(con, logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(con, logger);
		CondizioniManager condizioniManager = new CondizioniManager(con, logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(con, logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(con, logger);
		RequisitiManager requisitiManagerv = new RequisitiManager(con, logger);
		AggiudicazioneBean aggiudicazioneBean = aggiudicazioniManger.loadByIdSimog(Long.parseLong(idAggiudicazioneString)); 
		long idAggiudicazione = aggiudicazioneBean.getIdAggiudicazione();
		Timestamp dataInizioAggiudicazione = aggiudicazioneBean.getDataInizioAggiudicazione();
		boolean esitoOperazione = true;
		
		esitoOperazione = ausiliarieManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Ditte Ausiliarie per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = aggiudicatarioManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Aggiudicatari per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = responsabileManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = condizioniManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Condizioni per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = finanziamentoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Finanziamenti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");

      BaseSharedAction bsa = new BaseSharedAction(con, logger) {};
      Gara gara = bsa.getGaraByAgg(idAggiudicazione, dataInizioAggiudicazione);
      
      if(SimogFlags.is3031_RFWEBGL00Active() 
            && SimogProperties.getInstance().isCUPAttivo()){
         
         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(gara.getData_creazione());
         if( !okDataAttivazioneCup ){
            // Competenza Aggiudicazione
            esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);

         } else {
            // Competenza Lotto
            tipoAppaltoManager.cancellaDatiAgg(Long.valueOf(idAggiudicazione), dataInizioAggiudicazione);
            esitoOperazione = true;
         }
      }
      else{
         esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
      }
		if(!esitoOperazione) logger.debug("Non sono presenti TipoAppalto per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = requisitiManagerv.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Requisiti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		
      if( SimogFlags.is3031_RFWEBGL02Active() 
            && SimogProperties.getInstance().isCUPAttivo()){

         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(PageHelper.getFormattedDBDate(gara.getData_creazione()));
         CupLottoAggManager claMan = new CupLottoAggManager(con, logger);
         if( !okDataAttivazioneCup ){
           // Competenza Aggiudicazione
           claMan.updateRecord(String.valueOf(idAggiudicazione), dataInizioAggiudicazione, StatiScheda.ANNULLATO_STRING);
           
           // devo anche annullare il flag su lotto!
           try {
              AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
              AggiudicazioneBean agg = am.getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, true);
              
              InfoComuniManager icm = new InfoComuniManager(con, logger);
              InfoComuniBean dat = icm.load(agg.getIdInfo(), agg.getDataInizioInfo());
              
              LottoManager lm = new LottoManager(con, logger);
              Lotto lotto;
              lotto = lm.getLotto(dat.getIdLotto());
              lotto.setFLAG_CUP(null);
              lm.updateFlagCup(lotto);
              
           } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }

        } else {
           // Competenza Lotto
           claMan.cancellaDatiAggCup(idAggiudicazione, dataInizioAggiudicazione);
        }
      }

		esitoOperazione = aggiudicazioniManger.annulla(Long.parseLong(idAggiudicazioneString), cfUtente);
		
		return esitoOperazione;
	}
	
	/**
	 * Cancellazione di aggiudicazioni (e dati correlati) tramite i riferimenti idLocale e cui
	 * @param idLocale
	 * @param CUI
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean annulla(String idLocale, String CUI) throws SQLException{ 
        DittaAusiliariaManager ausiliarieManager = new DittaAusiliariaManager(con, logger);
		AggiudicazioniManager aggiudicazioniManger = new AggiudicazioniManager(con, logger);
		AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(con, logger);
		ResponsabileManager responsabileManager = new ResponsabileManager(con, logger);
		CondizioniManager condizioniManager = new CondizioniManager(con, logger);
		FinanziamentoManager finanziamentoManager = new FinanziamentoManager(con, logger);
		TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(con, logger);
		RequisitiManager requisitiManagerv = new RequisitiManager(con, logger);
		AggiudicazioneBean aggiudicazioneBean = aggiudicazioniManger.loadByIdLocale(idLocale, CUI) ;
		long idAggiudicazione = aggiudicazioneBean.getIdAggiudicazione();
		Timestamp dataInizioAggiudicazione = aggiudicazioneBean.getDataInizioAggiudicazione();
		boolean esitoOperazione = true;
		
	    esitoOperazione = ausiliarieManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
	    if(!esitoOperazione) logger.debug("Non sono presenti Ditte Ausiliarie per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = aggiudicatarioManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Aggiudicatari per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = responsabileManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Responsabili per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = condizioniManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Condizioni per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = finanziamentoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Finanziamenti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
      BaseSharedAction bsa = new BaseSharedAction(con, logger) {};
      Gara gara = bsa.getGaraByAgg(idAggiudicazione, dataInizioAggiudicazione);
      
      if(SimogFlags.is3031_RFWEBGL00Active() 
            && SimogProperties.getInstance().isCUPAttivo()){
         
         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(gara.getData_creazione());
         if( !okDataAttivazioneCup ){
            // Competenza Aggiudicazione
            esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
         } else {
            // Competenza Lotto
            tipoAppaltoManager.cancellaDatiAgg(Long.valueOf(idAggiudicazione), dataInizioAggiudicazione);
            esitoOperazione = true;
         }
      }
      else{
         esitoOperazione = tipoAppaltoManager.annulla(idAggiudicazione, dataInizioAggiudicazione);
      }
		if(!esitoOperazione) logger.debug("Non sono presenti TipoAppalto per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		esitoOperazione = requisitiManagerv.annulla(idAggiudicazione, dataInizioAggiudicazione);
		if(!esitoOperazione) logger.debug("Non sono presenti Requisiti per l'aggiudicazione: " + "["+idAggiudicazione+" , "+dataInizioAggiudicazione+"]");
		
      if( SimogFlags.is3031_RFWEBGL02Active() 
            && SimogProperties.getInstance().isCUPAttivo()){

         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(PageHelper.getFormattedDBDate(gara.getData_creazione()));
         CupLottoAggManager claMan = new CupLottoAggManager(con, logger);
         if( !okDataAttivazioneCup ){
           // Competenza Aggiudicazione
           claMan.updateRecord(String.valueOf(idAggiudicazione), dataInizioAggiudicazione, StatiScheda.ANNULLATO_STRING);
           
           // devo anche annullare il flag su lotto!
           try {
              AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
              AggiudicazioneBean agg = am.getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, true);
              
              InfoComuniManager icm = new InfoComuniManager(con, logger);
              InfoComuniBean dat = icm.load(agg.getIdInfo(), agg.getDataInizioInfo());
              
              LottoManager lm = new LottoManager(con, logger);
              Lotto lotto;
              lotto = lm.getLotto(dat.getIdLotto());
              lotto.setFLAG_CUP(null);
              lm.updateFlagCup(lotto);
              
           } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
           }

        } else {
           // Competenza Lotto
           claMan.cancellaDatiAggCup(idAggiudicazione, dataInizioAggiudicazione);
        }
      }

		esitoOperazione = aggiudicazioniManger.annulla(idLocale, CUI, cfUtente);
		
		return esitoOperazione;		
	}

}
