package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.PubblicazioneAggiudicazioneManager;
import it.avlp.simog.garamanager.PubblicazioneBandoManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GaraValidator;
import it.avlp.simog.validatore.LottoValidator;
import it.avlp.simog.validatore.PubblicazioneValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
         
public class GestioneRettificaAction extends BaseSharedAction 
{
	//protected Connection connection;
	//protected Logger logger;
	protected AllValidationBeans mEccezioni;

	public GestioneRettificaAction(Connection activeConnection, Logger logger) {
	   super(activeConnection, logger);
		//this.connection = activeConnection;
		//this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
	}
	
	/*********************************************************************
	 * il metodo si occupa di restituire il parametro mEccezioni 
	 * definito nella classe contenente i messaggi relativi alle accezioni rilevate
	 * 
	 * @return  AllValidatorBeans
	 */
	public AllValidationBeans getEccezioni() {
		return mEccezioni;
	}
	
	public void validaAllegatiRettifica(String allRettifica, String noteRettifica)throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		pubValidator.validaAllegatiRettifica(allRettifica, noteRettifica);
		if(pubValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(pubValidator.getEccezioni());
	}

	//metodo per validare le pubblicazioni (siano esse bandi, lettere o rettifiche)
	public void validaPubblicazioneRettifica(PubblicazioneBean pubblicazione, String tipoRettifica)throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		
        pubblicazione.setTipoOperazione(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice());
		
		//OLD: gm se isPubblicabile, profilo committente obbligatorio, sennò è opzionale 
		/*
        if(garaPubblicabile && garaInvitabile)
			this.getEccezioni().addValidationErr("Dati incongruenti: non e' possibile rettificare sia un bando di gara che una lettera di invito");
		
		else if (garaPubblicabile){
	    	pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
		    pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
		
	        pubValidator.validaPubblicazioneBase(pubblicazione, 0);
	   		pubValidator.validaPubblicazioneAvanzata(pubblicazione, tipoRettifica, ParametriServlet.SRV_GESTIONE_RETTIFICA);	        	
			if(pubValidator.getEccezioni().getSize() != 0)			
				this.getEccezioni().add(pubValidator.getEccezioni());
		}
		else if (garaInvitabile){ 
			pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
			if(pubblicazione.getLinkSitoCommittente()!=null && !"".equals(pubblicazione.getLinkSitoCommittente()))
    			pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
			else
				pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_NO);

			pubValidator.validaPubblicazioneAvanzata(pubblicazione, tipoRettifica, ParametriServlet.SRV_GESTIONE_RETTIFICA);
			if(pubValidator.getEccezioni().getSize() != 0)			
				this.getEccezioni().add(pubValidator.getEccezioni());
		}
		//OLD: gm come gestire l'else?
		else{
			
		}
		*/
        pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
		if(pubblicazione.getLinkSitoCommittente()!=null && !"".equals(pubblicazione.getLinkSitoCommittente()))
			pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
		else
			pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_NO);

   		pubValidator.validaPubblicazione(pubblicazione, tipoRettifica, ParametriServlet.SRV_GESTIONE_RETTIFICA);	        	
		if(pubValidator.getEccezioni().getSize() != 0)			
			this.getEccezioni().add(pubValidator.getEccezioni());

	}
	
	//metodo usato dal RUP per pubblicare una rettifica
	public void pubblicaRettifica(String utente, Gara gara, PubblicazioneBean pubblicazione, 
		ArrayList<AllegatoBean> allegati)throws ActionException{
		try{
		    GaraManager garaManager = new GaraManager(connection, logger);	
		   	PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection, logger);	
		   	LogManager logManager = new LogManager(connection, logger);	
		   	
	    	//gm gestione di rettifica comune (alle pubblicazioni sospese e non)
	   	    pubblicazioneManager.insertPubblicazione(pubblicazione);
	   	    
	   	    //gm aggiorno lo storico rel_pubb_agg
		   	PubblicazioneAggiudicazioneManager pam = new PubblicazioneAggiudicazioneManager(connection, logger);	
            pam.insertFromGara(gara.getId_Gara(), pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
		  
	   	    AllegatoManager aMan = new AllegatoManager(connection, logger);  
	     	for(AllegatoBean all : allegati){
	   	    	String idAll = String.valueOf(all.getIdAllegato());
				if(idAll != null && PageHelper.isNumeric(idAll) && idAll.compareTo("0") > 0)
			    	aMan.updateRettifica(all, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
	   	    }
 	        garaManager.updateGaraPubblicazione(gara, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    

	    	//se la rettifica non prevede sospensione, popolo la tabella Simog_Pubblicazione_Bando
	        if(Costanti.FLAG_VALORE_NO.equals(pubblicazione.getFlag_sospeso())){ 	    	    
    	   	    PubblicazioneBandoManager pbm = new PubblicazioneBandoManager(connection, logger);
    	   	    pbm.insert(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    	  	   
            }
    	    logManager.log(PageHelper.getCurrentDate(), gara.getID_STAZIONE_APPALTANTE(), utente, "",
    	    	Costanti.FLAG_VALORE_NO.equals(pubblicazione.getFlag_sospeso()) ? LogManager.RETTIFICA_BANDO_SENZA_SOSPENSIONE : LogManager.RETTIFICA_BANDO_CON_SOSPENSIONE, 
           		gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));		            
		}
		catch(Exception e){
            //e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}
	//metodo usato dall'ADMIN per confermare una rettifica
	public void confermaRettifica(String utente, String idGara, PubblicazioneBean pubblicazione)throws ActionException{
		try{
			
			GaraManager garaManager = new GaraManager(connection, logger);	
			Gara gara = garaManager.getGara(Long.parseLong(idGara));
		   	PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection, logger);	
	   	    pubblicazione.setFlag_sospeso(Costanti.FLAG_VALORE_NO);
	   	    //con la conferma dell'admin la pubblicazione non sarà più sospesa
		   	pubblicazioneManager.updateRettificaPubblicazione(pubblicazione);
    		PubblicazioneBandoManager pbm = new PubblicazioneBandoManager(connection, logger);
    		//quindi la tabella Simog_Pubblicazione_Bando verrà popolata in join con la gara
   	        pbm.insert(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    
   	          	
		   	LogManager logManager = new LogManager(connection, logger);	
    	    logManager.log(PageHelper.getCurrentDate(), gara.getID_STAZIONE_APPALTANTE(), utente, "",
        	    	LogManager.CONFERMA_RETTIFICA, 
               		gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));		            
		}
		catch(Exception e){
            //e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}
	//metodo per confermare una rettifica di un avviso di aggiudicazione
	public void pubblicaRettificaAvviso(String cfUtente, long idGara, PubblicazioneBean pubblicazione, long idAggiudicazione, Timestamp dataInizioAgg, ArrayList<AllegatoBean> allegati)throws ActionException{
		try{
		   	PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection, logger);	  	    
		   	pubblicazioneManager.insertPubblicazione(pubblicazione);
		
		   	AllegatoManager aMan = new AllegatoManager(connection, logger);  
		   	for(AllegatoBean all : allegati){
	   	    	String idAll = String.valueOf(all.getIdAllegato());
				if(idAll != null && PageHelper.isNumeric(idAll) && idAll.compareTo("0") > 0)
			    	aMan.updateRettifica(all, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
	   	    }
		   	
		   	//long id_agg = Long.parseLong(idAggiudicazione);
		   	//Timestamp data_inizio_agg = PageHelper.parseTime(dataInizioAgg);
		   	AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(connection, logger);	  	    
		   	aggiudicazioniManager.updateAggiudicazionePubblicazione(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione(), idAggiudicazione, dataInizioAgg);
		   	
    		PubblicazioneBandoManager pbm = new PubblicazioneBandoManager(connection, logger);
    		
    		//quindi la tabella Simog_Pubblicazione_Bando verrà popolata in join con l'aggiudicazione
   	        pbm.insertFromAgg(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    
   	        
   	        //gm aggiorno lo storico rel_pubb_agg
		   	PubblicazioneAggiudicazioneManager pam = new PubblicazioneAggiudicazioneManager(connection, logger);	         
		   	pam.insertFromAggiudicazione(idGara, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione(), idAggiudicazione, dataInizioAgg);
		 
   	        // log operazione
   	        List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(idAggiudicazione);
			attributiChiave.add(dataInizioAgg);
   	        LogBloccoDatiManager.loggingAVVISO(connection, logger, cfUtente,IdentificativoSchede.TAB_AGGIUDICAZIONE,attributiChiave,true);

		}
		catch(Exception e){
            //e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
		
	}
	
	/**
	 * @param dettagliGara
	 * @return Map 
	 */
	//caricamento di tutti i lotti di una gara senza distinzione,
	//gli altri metodi si occuperanno di verificare anche lo stato dei lotti
	public Map<String,Lotto> caricaElencoLotti (TableBean dettagliGara) {
		Map <String,Lotto> elenco=new HashMap<String,Lotto>();
        Lotto lotto = null;
		if (dettagliGara != null){
		    for(int rowIndex=0; rowIndex < dettagliGara.getTableSize(); rowIndex++){
			    TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
			    if(currentRow!=null){
			    	String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
		    	    //verifico eventuali duplicati, controllo superfluo
			    	//il confronto con "0" è necessario per le gare senza lotti
		    	    if((!"".equals(idLotto)) && (!"0".equals(idLotto)) && !elenco.containsKey(idLotto)){			 
			    	    lotto = new Lotto();	
			            lotto.setId_Lotto(Long.parseLong(idLotto));
			            String cig = currentRow.getNulledField(LOTTO.CIG);
			    	    String cig_kkk = currentRow.getNulledField(LOTTO.CIG_KKK);
			    	    BigDecimal importoLotto = new BigDecimal(currentRow.getNulledField(LOTTO.IMPORTO_LOTTO));
			    	    String sceltaContraente = currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE);
			    	    String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
			            String dataCancellazione = currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO);
			    	    String dataInibPagamento = currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO);
			    	    String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE);
			            lotto.setCig(cig);
			            lotto.setCig_kkk(cig_kkk);
			            lotto.setDATA_CANCELLAZIONE_LOTTO(dataCancellazione);
			            lotto.setDataInibizionePagamento(dataInibPagamento);
			            lotto.setData_Pubblicazione(dataPubblicazione);
			            lotto.setImporto_Lotto(importoLotto);
			            lotto.setId_Scelta_Contraente(sceltaContraente);
			            lotto.setTIPO_CONTRATTO_LOTTO(tipoContratto);
			            //importo sceltacontraente tipocontratto
			            elenco.put(idLotto,lotto);  			    		
			    	}
			    }
		    }
		}
		return elenco;
	}
	
	public Map<String,Lotto> caricaElencoLottiPerfezionati (Map<String,Lotto> mappaLotti){
		Map<String,Lotto> risultato=new HashMap<String,Lotto>();
		//risultato = mappaLotti;
		
		//inserisco solo i lotti che hanno lo stato = "in lavorazione"
		if(mappaLotti!=null){
			Collection<Lotto> lotti = mappaLotti.values();
			for(Lotto l : lotti){
				if("".equals(l.getDATA_CANCELLAZIONE_LOTTO())
				    &&!"".equals(l.getDATA_INIB_PAGAMENTO())
				    &&!"".equals(l.getData_Pubblicazione()))
					risultato.put(String.valueOf(l.getId_Lotto()),l);
			}
		}
		return risultato;
	}
	
	public String validaRettificaDate(String dataPubblicazione, 
									String dataTermine, 
									String dataScadenzaInvito, 
									String dataInvito,
									String oraScadenza,
									Lotto lotto) {
		String res = null;
		if(lotto.getData_Pubblicazione()==null)
			return Messaggi.SIMOG_GARA_028.replace("$1", "la gara non e' pubblicata");
		else if(lotto.getORA_SCADENZA()==null && oraScadenza!=null)
			return Messaggi.SIMOG_GARA_028.replace("$1", "Ora scadenza pagamenti non richiesta");
		else if(lotto.getORA_SCADENZA()!=null && oraScadenza==null)
			return Messaggi.SIMOG_GARA_028.replace("$1", "Indicare l'ora scadenza pagamenti");
		
		if(lotto.getDATA_SCADENZA_PAGAMENTI()!=null) {
			Date dataPag = new Date();
			try {
				dataPag = new SimpleDateFormat("yyyyMMdd").parse(lotto.getDATA_SCADENZA_PAGAMENTI());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(PageHelper.getCurrentUtilDate().after(dataPag))
				return Messaggi.SIMOG_GARA_028.replace("$1", "la data scadenza pagamenti e' antecedente alla data odierna");
		}
		
		//Gara pubblicata normalmente
		if(lotto.getData_Pubblicazione()!=null && lotto.getDATA_SCADENZA_PAGAMENTI()!=null 
				&& lotto.getDataScadenzaRichiestaInvito()==null && lotto.getDataLetteraInvito()==null) {

			if(dataScadenzaInvito!=null)
				return Messaggi.SIMOG_GARA_028.replace("$1", "la data scadenza della richiesta invito non e' richiesta");
			if(dataInvito!=null)
				return Messaggi.SIMOG_GARA_028.replace("$1", "la data della lettera di invito non e' richiesta");	
			if(dataTermine==null)
				return Messaggi.SIMOG_GARA_028.replace("$1", "indicare la data scadenza pagamenti");
		}
		
		//Gara pubblicata alla prima fase
		if(lotto.getData_Pubblicazione()!=null && lotto.getDATA_SCADENZA_PAGAMENTI()==null 
				&& lotto.getDataScadenzaRichiestaInvito()!=null && lotto.getDataLetteraInvito()==null) {
			if(dataTermine!=null)
				return Messaggi.SIMOG_GARA_028.replace("$1", "la data scadenza della richiesta invito non e' richiesta");
			if(dataInvito!=null)
				return Messaggi.SIMOG_GARA_028.replace("$1", "la data della lettera di invito non e' richiesta");
		}
		
		return res;
		
	}
	
}
