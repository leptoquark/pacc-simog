package it.avlp.simog.common.actions;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.contributo.ParametriContributo;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
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
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.GaraValidator;
import it.avlp.simog.validatore.LottoValidator;
import it.avlp.simog.validatore.PubblicazioneValidator;
import it.avlp.simog.validatore.RequisitiGLValidator;
import it.mef.serviziCUP.ElaborazioniCUPClient;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
         
public class BandoGaraAction extends BaseSharedAction 
{
	//protected Connection connection;
	//protected Logger logger;
	protected AllValidationBeans mEccezioni;
	protected SimogProperties conf;
	protected LottoManager lottoManager = null; 
	public BandoGaraAction(Connection activeConnection, Logger logger, SimogProperties conf) {
	   super(activeConnection, logger);
		//this.connection = activeConnection;
		//this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
		this.conf = conf;
		this.lottoManager = new LottoManager(activeConnection, logger);  
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
	
	public void validaPerfezionamentoLotti(Lotto lotto, boolean existLottiDaPerfezionare, int tipoProcedura)throws ActionException{
		LottoValidator lottoValidator = new LottoValidator(connection, logger);
		
		lottoValidator.setGiorni(conf.getGiorni_pubb_scadenza());
		if(existLottiDaPerfezionare){
		   if( tipoProcedura == PRO_RISTRETTA )
		      lottoValidator.valida(lotto, ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE1);
		   else if( tipoProcedura == PRO_NEGOZIATA )
		      lottoValidator.valida(lotto, ParametriServlet.PERFEZIONAMENTO_PROC_MISTA);
		   else
		      lottoValidator.valida(lotto,ParametriServlet.PERFEZIONAMENTO);
		}else{
		   lottoValidator.valida(lotto,ParametriServlet.PERFEZIONAMENTO_SENZA_LOTTI);
		}
		if(lottoValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(lottoValidator.getEccezioni());

// PP qui non serve		
//if( SimogFlags.is3025_REQUISITIActive() ){
//         try{
//      	    RequisitiGLValidator requisitiGLValidator = new RequisitiGLValidator(connection, logger);
//      	    requisitiGLValidator.validaRequisitoOE( importoGara );
//      	    this.getEccezioni().add(requisitiGLValidator.getEccezioni());
//         }catch(Exception e){
//            throw new ActionException(e);
//         }
//}
		
	}
	
	public void validaCancellazioneLotti(Lotto lotto, boolean existLottiDaCancellare)throws ActionException{
		LottoValidator lottoValidator = new LottoValidator(connection, logger);
		if(existLottiDaCancellare)
	    	lottoValidator.valida(lotto,ParametriServlet.CANCELLAZIONE);
		else
	    	lottoValidator.valida(lotto,ParametriServlet.CANCELLAZIONE_SENZA_LOTTI);
		if(lottoValidator.getEccezioni().getSize() != 0)			
			this.getEccezioni().add(lottoValidator.getEccezioni());
	}
	
	public void validaAllegati(String allBando, String allDisci, String allInvito, boolean pubblicabile, boolean invitabile, boolean bandoObbligatorio, boolean procCompleta)throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		pubValidator.validaAllegati(allBando, allDisci, allInvito, pubblicabile, invitabile, bandoObbligatorio, procCompleta);
		if(pubValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(pubValidator.getEccezioni());
	}
	
	public void validaAllegatiAvviso(String allAvviso)throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		pubValidator.validaAllegatiAvviso(allAvviso);
		if(pubValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(pubValidator.getEccezioni());
	}
	

	public void validaNumeroLotti(Integer numeroLottiGara, int numeroLottiDaPerfezionare)throws ActionException{
		GaraValidator garaValidator = new GaraValidator(connection, logger);
		garaValidator.validaNumeroLotti(numeroLottiGara,numeroLottiDaPerfezionare);
		if(garaValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(garaValidator.getEccezioni());
	}

	
	public void validaAllegatiWS (ArrayList <AllegatoBean> allegati, String tipoOperazione, boolean isBandoObbligatorio) throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		pubValidator.validaAllegatiWS(allegati, tipoOperazione, isBandoObbligatorio);
		if(pubValidator.getEccezioni().getSize() != 0)
			this.getEccezioni().add(pubValidator.getEccezioni());
	}
	
	//metodo per validare le pubblicazioni
	//public void validaPubblicazione(PubblicazioneBean pubblicazione, String tipoPubblicazione, String tipoOperazione, float totLotti)throws ActionException{
	public void validaPubblicazione(PubblicazioneBean pubblicazione, boolean isPubblicazione, boolean isInvito, 
	      BigDecimal totLotti, boolean hasRequisiti, Gara gara, boolean isProcRistretta) throws ActionException{
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
		
		//fix MARRA MEV 34470 3.04.8
        validaBandiEContratti(gara, pubblicazione.getLinkAffidamentoDiretto());
        //fix FINE MEV 3.04.8
       
        ////3.04.11 MEV 44999
        String currentDate = PageHelper.getCurrentDate();
        //il controllo si attiva solo se la data corrente è successiva o uguale alla data di attivazione
        if (SimogProperties.isDataAfterAttivazioneBloccoCig(currentDate)) {
        	// MEV 46181 3.04.11
			boolean isSoggNonBloccato = SimogProperties.getInstance().isSoggettoNonBloccato(gara.getCF_AMMINISTRAZIONE());			
	 		//se è un soggetto bloccato quindi da bloccare
			if(!isSoggNonBloccato) {
				if (pubblicazione.getDataInizioPubblicazione()!= null) {
	        		String dataPubblicazione = PageHelper.formatDate(pubblicazione.getDataInizioPubblicazione());
	                controlloBloccoCig(dataPubblicazione);
				}
			}
        	
        	 
		}
      //FINE //3.04.11 MEV 44999
       
        
        
        
     
        
        
		//TICKET ALM #3922
		String dataCreazione = gara.getData_creazione();
		if(!SimogFlags.is3042Active() || !SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione)) {
	        if ( isProcRistretta ) {
	           if (Costanti.FLAG_VALORE_SI.equals(pubblicazione.getFlag_benicult())) {
	              mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_210.replace("$1", "Procedura negoziata ex art. 204 comma 1 D.Lgs. 163/2006").replace("$2", "NO"));
	           }
	        }
		}
		
		//gm controllo per impedire contemporaneamente pubblicazione ed invito,
		//verificare se sara' mantenuto
		if(isPubblicazione && isInvito)
			this.getEccezioni().addValidationErr("I CIG interessati alla pubblicazione identificano una situazione ambigua (BANDO/LETTERA DI INVITO)");
		
		if (isPubblicazione){
			pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
			if(pubblicazione.getLinkSitoCommittente()!=null && !"".equals(pubblicazione.getLinkSitoCommittente()))
				pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
			else
				pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_NO);
	        pubblicazione.setTipoOperazione(PubblicazioneBean.TipoOperazione.BANDO.getCodice());
		
    		pubValidator.validaPubblicazione(pubblicazione,ParametriServlet.PUBBLICAZIONE_BANDO_GARA, ParametriServlet.SRV_BANDO_GARA);
			if(pubValidator.getEccezioni().getSize() != 0)			
				this.getEccezioni().add(pubValidator.getEccezioni());
		}
		else if (isInvito){ 
			pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
			if(pubblicazione.getLinkSitoCommittente()!=null && !"".equals(pubblicazione.getLinkSitoCommittente()))
				pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
			else
				pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_NO);
			pubblicazione.setTipoOperazione(PubblicazioneBean.TipoOperazione.LETTINV.getCodice());

			pubValidator.validaPubblicazione(pubblicazione,ParametriServlet.PUBBLICAZIONE_LETT_INV,  ParametriServlet.SRV_BANDO_GARA);
			if(pubValidator.getEccezioni().getSize() != 0)			
				this.getEccezioni().add(pubValidator.getEccezioni());
		}
		//altrimenti e un semplice perfezionamento
		else {
			pubValidator.validaSenzaPubblicazione(pubblicazione);
			if(pubValidator.getEccezioni().getSize() != 0)			
				this.getEccezioni().add(pubValidator.getEccezioni());
		}
		//gm fine gestione automatica dei flag profilo committente ed osservatorio CP	

		if( !hasRequisiti && conf.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){
            
         /* UN 12/12/2013
          * In caso di esclusione AVCPass non validare la presenza dei requisiti
          * (se la flag ESCLUSO_AVCPASS e' blank, suggerire la valorizzazione del campo)
          */
         if(conf.getDataEsclAvcpass().compareTo(gara.getData_creazione()) <= 0
               && (gara.getESCLUSO_AVCPASS() == null 
                     || "".equals(gara.getESCLUSO_AVCPASS())
                  //   || gara.getESCLUSO_AVCPASS().equals(Costanti.FLAG_VALORE_NO)
                  ) ){
            this.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_224); 
         }
         
         boolean esclusioneAVCPass = Costanti.FLAG_VALORE_SI.equals(gara.getESCLUSO_AVCPASS());

         if( !esclusioneAVCPass ){

            try{
               RequisitiGLValidator requisitiGLValidator = new RequisitiGLValidator(connection, logger);
               requisitiGLValidator.validaRequisitoOE( totLotti, gara.getData_creazione());
               this.getEccezioni().add(requisitiGLValidator.getEccezioni());
            }catch(Exception e){
               throw new ActionException(e);
            }
         }
		}
		// validazione codici CUP
		// per scrupolo, solo se la gara e successiva all'attivazione della MEV 
		if (conf.isCUPAttivo()
		      && conf.isCUPLotto(gara.getData_creazione())){
		   List<CupLottoAggExt> lista;
         CupLottoAggAction cla = new CupLottoAggAction(connection, logger);
         //MAC 34162 3.04.8.1 
         //prendo la lista di cup della gara non appartenenti a lotti cancellati
         lista = cla.getElencoCupGaraNoLottiCancellati(gara.getId_Gara());
//        lista = cla.getElencoCupGara(gara.getId_Gara());

         if (lista != null){
		      // imposto solo i dati che mi servono
		      Lotto lt = new Lotto();
		      lt.setId_Gara(gara.getId_Gara());
		      lt.setElencoCup(lista);
		      AllValidationBeans ret = validaCodiciCUPPerf(lt);
		      if(ret != null)
		         this.getEccezioni().add(ret);
		   }
		}
	}
	
	public void validaPubblicazioneAvviso(PubblicazioneBean pubblicazione) throws ActionException{

		pubblicazione.setSitoOsservatorioCP(Costanti.FLAG_VALORE_SI);
		if(pubblicazione.getLinkSitoCommittente()!=null && !"".equals(pubblicazione.getLinkSitoCommittente()))
			pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
		else
			pubblicazione.setProfiloCommitente(Costanti.FLAG_VALORE_NO);
	
	    pubblicazione.setTipoOperazione(PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice());
		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);

		pubValidator.validaPubblicazione(pubblicazione,ParametriServlet.PUBBLICAZIONE_AVVISO, ParametriServlet.SRV_BANDO_GARA);
		if(pubValidator.getEccezioni().getSize() != 0)			
			this.getEccezioni().add(pubValidator.getEccezioni());
	}
	
	public void pubblicaBandoGara(String utente, Gara gara, PubblicazioneBean pubblicazione, 
			Map<String, Lotto> lottiDaPerfezionare, Map<String,Lotto> lottiDaCancellare,
			String dataPubblicazione, String dataScadenza, 
			String id_motivazione, String note_canc,
			boolean garaPubblicabile, boolean garaInvitabile, ArrayList<AllegatoBean> allegati,
			BigDecimal totLottiContributoSA, AllValidationBeans msgs, String oraScadenza,
			String dataScadenzaRichiestaInvito, String dataLetteraInvito)throws ActionException{

	   try{
		 //MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
		   String motivoEsclusioneGara = null;
		    GaraManager garaManager = new GaraManager(connection, logger);	
		   	//LottoManager lottoManager = new LottoManager(connection, logger);	
		   	PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection, logger);	
		   	LogManager logManager = new LogManager(connection, logger);	
		   	String dataCorrente = PageHelper.getCurrentDate();
		   	BigDecimal importo = new BigDecimal(0);
            GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (conf.getContributoUrl(), connection, logger);

            gara.setDATA_PERFEZIONAMENTO_BANDO(dataCorrente);       
            gara.setDATA_CONFERMA_GARA(dataCorrente);

            //gm cancellazioni dei lotti, se ci sono
            if(lottiDaCancellare!=null && !lottiDaCancellare.isEmpty()){                
                Collection<Lotto> lottiCanc = lottiDaCancellare.values();
                for(Lotto l:lottiCanc){
                    String cig_reale = l.getCIG().concat((l.getCIG_kkk()));

// FIXMato: PP cosi no puo funzionare, per ora restano i requisiti, associati al lotto cancellato
                    if(SimogFlags.is3025_REQUISITIActive()){
                       Timestamp currentDatetime = new AccessiDB(connection, logger).getNow();
                       
                       //PP devo considerare la data di creazione della gara se esiste
                       if(gara.getData_creazione() != null)
                          currentDatetime = PageHelper.parseTimeYMD(gara.getData_creazione());
                       
                       // revoca dei requisiti associati al lotto
                       RequisitiGLAction rqa = new RequisitiGLAction(connection, logger);
                       rqa.revocaRequisitiByLotto(l.getId_Gara(), l.getId_Lotto(), currentDatetime, false);
                    }
                    
                    lottoManager.cancellaLotto(dataCorrente, String.valueOf(l.getId_Lotto()), id_motivazione, note_canc, false);
                    
                    logManager.log(dataCorrente, gara.getID_STAZIONE_APPALTANTE(), utente, cig_reale, LogManager.DEL_LOTTO, 
                        gara.getCF_AMMINISTRAZIONE(), String.valueOf(l.getId_Lotto()), String.valueOf(gara.getId_Gara()));          
                }
            }
		   	
            //is3030_RFWEBGL00Active
            boolean proceduraRistretta = isProceduraRistretta(lottiDaPerfezionare,gara.getID_SVOLGIMENTO()) || isProceduraNegoziata(lottiDaPerfezionare,gara.getID_SVOLGIMENTO());
            
    		//gm operazione di perfezionamento lotti, se ci sono
			if(lottiDaPerfezionare!=null && !lottiDaPerfezionare.isEmpty()){
				Collection<Lotto> lottiPerf = lottiDaPerfezionare.values();
				// array dei cig per i quali occorre aggiungere i requisiti riscossione
				List <Long> cigPerAR = new ArrayList<Long>();
				
			    for(Lotto l:lottiPerf){
			    	String idLotto = String.valueOf(l.getId_Lotto());
			    	String cig_reale = l.getCIG().concat((l.getCIG_kkk()));
			    	
			    	ParametriContributo parLotto = new ParametriContributo(gara, l, PageHelper.getCalendarFromStringDate(dataPubblicazione),connection,logger);
			    	
			    	// pp calcolo del contributo OE usando i WS se configurati altrimenti la tabella SIMOG			    		
	            BigDecimal impImpresa = new BigDecimal(0);
	            
	            boolean isRipetizione=Costanti.FLAG_VALORE_SI.equals(l.getFLAG_RIPETIZIONE()) || Costanti.COLL_CIG_RIP.equals(l.getID_MOTIVO_COLL_CIG());
   				
   				if(!isRipetizione) {
		            importo = gcwbc.getContributoOE(parLotto);
		            if(!gcwbc.hasErrors())
		               impImpresa = importo;
		            else if(SimogFlags.is30230_NRFWEBXX00Active())
		               impImpresa = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
   				}

	            // controlo messaggio di ritorno
                msgs.add(gcwbc.getErrors());
	                
			    	lottoManager.perfezionaLotto(String.valueOf(l.getId_Lotto()), dataPubblicazione, 
			    	      dataScadenza, impImpresa, oraScadenza, dataScadenzaRichiestaInvito, dataLetteraInvito);
			    	//MEV 43345 3.04.10
			    	//se la MEV sul controllo della qualificazione sa è attiva
			    	String currentDate = PageHelper.getCurrentDate();
					if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
						if (lottiDaPerfezionare.size() == 1) {
			    			String dataPubblicazioneQualificazione = dataPubblicazione;
			    			//se la data di pubblicazione inserita è maggiore o uguale al 1 Luglio faccio il controllo sulla qualificazione
			    			if (dataPubblicazioneQualificazione!= null && dataPubblicazioneQualificazione.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
				    			LottoManager lm = new LottoManager(connection, logger);
				    			Iterator<Entry<String, Lotto>> iterator = lottiDaPerfezionare.entrySet().iterator();
				    			Entry<String, Lotto> actualValue = iterator.next();
				    			Lotto lottoQualificazioneSA = lm.getLotto(actualValue.getValue().getId_Lotto());
				    			if (lottoQualificazioneSA.getDataCreazione().compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) < 0 ) {
				    				lottoManager.perfezionamentoLottoQualificazioneSA(idLotto, pubblicazione.getDerogaQualificazioneSA(), pubblicazione.getFlagIsQualificataKO());
				    			}
							}
			    			
			    		 }
					}
			    	
			    	// PP mancava l'aggiornamento dello stato dei cup e delle tipologie appalto e forniture
		         if(SimogFlags.is3031_RFWEBGL02Active() 
		               && conf.isCUPAttivo()){		            
		            CupLottoAggManager cupm = new CupLottoAggManager(connection, logger);
		            // leggo l'elenco dei cup presenti per il lotto
		            l.setElencoCup(cupm.getElencoCup(l.getId_Lotto(), null, null, false));
		            cupm.updateElencoCup(l, true);

		            if(SimogFlags.is3031_RFWEBGL00Active()){
		                  // aggiornamento tipologie appalto
		                  TipoAppaltoManager tam = new TipoAppaltoManager(connection, logger);
		                  
		                  l.setElencoTipoAppaltoLottoL(tam.loadMany(l.getId_Lotto(), Costanti.TIPO_SCHEDA_LAVORI, gara.getTIPO_SCHEDA_GARA(), false));
                        l.setElencoTipoAppaltoLottoF(tam.loadMany(l.getId_Lotto(), Costanti.TIPO_SCHEDA_SERVIZI, gara.getTIPO_SCHEDA_GARA(), false));
                        l.getElencoTipoAppaltoLottoF().addAll(tam.loadMany(l.getId_Lotto(), Costanti.TIPO_SCHEDA_FORNITURE, gara.getTIPO_SCHEDA_GARA(), false));
		                  tam.aggiornaTipoAppaltoLotto(l, true);
		             }
		         }


               // log operazione   
               String operazione = LogManager.PERF_LOTTO;
               
               // se fase 1 ristretta cambio operazione, altrimenti resta la standard
               // la fase 2 e gestita in altro metodo
               if(proceduraRistretta 
                     && (dataLetteraInvito == null || "".equals(dataLetteraInvito)) 
                     && (dataScadenza == null || "".equals(dataScadenza)))
                  operazione = LogManager.PERF_LOTTO_PR1;
		         
		         logManager.log(PageHelper.getCurrentDate(), gara.getID_STAZIONE_APPALTANTE(), utente, cig_reale, 
			                     operazione, 
			                     gara.getCF_AMMINISTRAZIONE(), String.valueOf(l.getId_Lotto()), 
			                     String.valueOf(gara.getId_Gara()));			   
			    
// 13.06.2013 richiesta modifica, i requisiti AR vanno associati a livello di gara, non valorizzo la lista         
//	                if( SimogFlags.is3025_REQUISITIActive()   
//	                   && !gcwbc.isEsente(parLotto.getImporto()) && impImpresa.floatValue() > 0){
//	                      cigPerAR.add(l.getId_Lotto());
//	                }
		         
		         //MAC 36255 3.04.8
		         //se la gara e singolo lotto salvo in una variabile la modalita esclusione da passare a getContributoSA
		         if (lottiPerf.size()==1) {
		        	 motivoEsclusioneGara = String.valueOf(l.getID_ESCLUSIONE()).trim();
				}
		         
		        
			    }
			    
			    if( SimogFlags.is3025_REQUISITIActive() && conf.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){               
                /*
                 * Aggiunta dei requisiti AR alla gara
                 */
                   RequisitiGLAction requisitiGLAction = new RequisitiGLAction(connection, logger);
                   boolean esistonoRequisitiGara = !requisitiGLAction.getRequisitoGaraList(gara.getId_Gara()).isEmpty();
                   if( esistonoRequisitiGara ){// 13.06.2013 richiesta modifica && !cigPerAR.isEmpty()
                         requisitiGLAction.updateRequisitiARbyLotto(gara.getId_Gara(), cigPerAR, 
                               !SimogFlags.is3028_RNFDBDT01Active(), PageHelper.parseTimeYMD(gara.getData_creazione()));
                      logger.debug("Inserimento dei requisiti di tipo uso AR per la gara[" + gara.getId_Gara() + "]" );
                   }
			    }    
	   		}
			
			//MAC 36255 3.04.8 aggiunto parametro motivoEsclusioneGara
            // pp calcolo del contributo SA usando i WS se configurati altrimenti la tabella SIMOG
            ParametriContributo params = new ParametriContributo(gara, null, motivoEsclusioneGara, PageHelper.getIncreasedDate(0));
            // PP patch dell'importo gara, uso il nuovo parametro
            params.setImporto(totLottiContributoSA);


               // devo ricalcolare i contributi della gara in base ai lotti pubblicati
               Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());
               params.setDataPubblicazione(gcwbc.getDataPubb(lotti));
               params.setImporto(gcwbc.getImportoGara(lotti.values(), false, true));
               gara.setIMPORTO_GARA(gcwbc.getImportoGara(lotti.values(), false, false));
                   
           //TICKET ALM #19119
           boolean isRipetizione = false;
           for (Iterator iter = lotti.values().iterator(); iter.hasNext();) {
				Lotto element = (Lotto) iter.next();
				if(Costanti.FLAG_VALORE_SI.equals(element.getFLAG_RIPETIZIONE()) || Costanti.COLL_CIG_RIP.equals(element.getID_MOTIVO_COLL_CIG())) {
					isRipetizione=true;
					break;
				}
            }        
            
           if(!isRipetizione) {
           
            importo = gcwbc.getContributoSA(params);
            if(!gcwbc.hasErrors())
               gara.setIMPORTO_SA_GARA(importo);
            else if(SimogFlags.is30230_NRFWEBXX00Active())
               gara.setIMPORTO_SA_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));
           } else {
        	   gara.setIMPORTO_SA_GARA(new BigDecimal(0.00));
           }
           //FINE TICKET ALM #19119
            
            // controlo messaggio di ritorno
            msgs.add(gcwbc.getErrors());

	    	if(garaPubblicabile || garaInvitabile){ 	    
	   	        //pubblico la gara ed i dati di pubblicazione	
    	   	    pubblicazioneManager.insertPubblicazione(pubblicazione);
    	   	    //garaManager.updateGaraPubblicazione(gara, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    
    	   	    //MAC 42787 3.04.9.2 
    	   	    //andiamo a fare l'update della tabella gara inserendo stavolta anche il link affidamento diretto
    	   	    garaManager.updateGaraPubblicazione(gara, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione(),pubblicazione.getLinkAffidamentoDiretto());
    	   	    //FINE MAC
    	   	    //la tabella SIMOG_PUBBLICAZIONE_BANDO deve essere popolata solo in caso di gara pubblicabile
    	   	    PubblicazioneBandoManager pbm = new PubblicazioneBandoManager(connection, logger);
    	   	    pbm.insert(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    	
    	   	    
    	   	    //gm aggiorno lo storico rel_pubb_agg
    		   	PubblicazioneAggiudicazioneManager pam = new PubblicazioneAggiudicazioneManager(connection, logger);	
                pam.insertFromGara(gara.getId_Gara(), pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
    		   	
    	   	    // associo gli allegati alla pubblicazione
    	   	    AllegatoManager aMan = new AllegatoManager(connection, logger);
    	   	    
    	   	    for(AllegatoBean all : allegati){
    	   	    	String idAll = String.valueOf(all.getIdAllegato());
					if(idAll != null && PageHelper.isNumeric(idAll) && idAll.compareTo("0") > 0)
				    	aMan.update(idAll, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
    	   	    }
           }
           else{
        	 //altrimenti perfeziono solo la gara
        	   //MAC 42787 3.04.9.2
        	   //vado a salvare nella gara il link affidamento diretto anche se non è una pubblicazione
//        	   garaManager.updateGaraPerfezionamento(gara);
    	       garaManager.updateGaraPerfezionamento(gara, pubblicazione.getLinkAffidamentoDiretto());
    	       //FINE MAC
	       }

	    	if(SimogFlags.is3028_RFWEBGL04Active()){
	    	   // se la gara non prevede requisiti ma esistono li cancello
            RequisitiGLValidator rqvl = new RequisitiGLValidator(connection, logger);
            rqvl.validaRequisitoOE(gara.getIMPORTO_GARA(), gara.getData_creazione());
            
            boolean okRequisiti = rqvl.getEsito().isRequisiti();
            
            if(!okRequisiti){
               RequisitiGLAction rgla = new RequisitiGLAction(connection, logger);  
                  boolean ret = rgla.revocaRequisitiByGara(gara.getId_Gara(), !SimogFlags.is3028_RNFDBDT01Active());
               if(ret)
                  msgs.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_218);
            }
	    	}

	    	logManager.log(dataCorrente, gara.getID_STAZIONE_APPALTANTE(), utente, "",
    	                  garaPubblicabile? LogManager.PUBB_GARA_LOTTI : LogManager.PERF_GARA_LOTTI, 
    	                  gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));		            
		}
		catch(Exception e){
            e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}
	
	//metodo per salvare gli avvisi di aggiudicazione
	public void pubblicaAvviso(String cfUtente, long idGara, PubblicazioneBean pubblicazione, ArrayList<AllegatoBean> allegati, long idAggiudicazione, Timestamp dataInizioAgg)throws ActionException{
		PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection, logger);
	    PubblicazioneBandoManager pbm = new PubblicazioneBandoManager(connection, logger);
	    AllegatoManager aMan = new AllegatoManager(connection, logger);
		AggiudicazioniManager aggManager = new AggiudicazioniManager(connection, logger);

		try{
		    //pubblico la gara ed i dati di pubblicazione	
   	        pubblicazioneManager.insertPubblicazione(pubblicazione);

   	 	    //long id_agg = Long.parseLong(idAggiudicazione);
	      	//Timestamp data_inizio_agg = PageHelper.parseTime(dataInizioAgg);
	   
   	        aggManager.updateAggiudicazionePubblicazione(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione(), idAggiudicazione, dataInizioAgg);

     	    //gm aggiorno lo storico rel_pubb_agg
		   	PubblicazioneAggiudicazioneManager pam = new PubblicazioneAggiudicazioneManager(connection, logger);	         
		   	pam.insertFromAggiudicazione(idGara, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione(), idAggiudicazione, dataInizioAgg);
		   	
     	    // associo gli allegati alla pubblicazione   
   	        for(AllegatoBean all : allegati){
	   	    	String idAll = String.valueOf(all.getIdAllegato());
				if(idAll != null && PageHelper.isNumeric(idAll) && idAll.compareTo("0") > 0)
			    	aMan.update(idAll, pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());
	   	    }

   	        pbm.insertFromAgg(pubblicazione.getIdPubblicazione(), pubblicazione.getDataInizioPubblicazione());    	

   	        // log operazione
   	        List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(idAggiudicazione);
			attributiChiave.add(dataInizioAgg);
   	        LogBloccoDatiManager.loggingAVVISO(connection, logger, cfUtente,IdentificativoSchede.TAB_AGGIUDICAZIONE,attributiChiave,false);
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
		LottoManager lm = new LottoManager(connection,logger);
		Map <String,Lotto> elenco=new HashMap<String,Lotto>();
        Lotto lotto = null;
		if (dettagliGara != null){
		    for(int rowIndex=0; rowIndex < dettagliGara.getTableSize(); rowIndex++){
			    TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
			    if(currentRow!=null){
			    	String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
		    	    //verifico eventuali duplicati, controllo superfluo
			    	//il confronto con "0" e necessario per le gare senza lotti
		    	    if((!"".equals(idLotto)) && (!"0".equals(idLotto)) && !elenco.containsKey(idLotto)){			 
			    	    lotto = new Lotto();	
			            lotto.setId_Lotto(Long.parseLong(idLotto));
			            lotto.setId_Gara(Long.parseLong(currentRow.getNulledField(LOTTO.ID_GARA)));
			            String cig = currentRow.getNulledField(LOTTO.CIG);
			    	    String cig_kkk = currentRow.getNulledField(LOTTO.CIG_KKK);
			    	    BigDecimal importoLotto = new BigDecimal(currentRow.getNulledField(LOTTO.IMPORTO_LOTTO));
			    	    String sceltaContraente = currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE);
			    	    String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
			            String dataCancellazione = currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO);
			    	    String dataInibPagamento = currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO);
			    	    String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE);
			    	    String idEsclusione = currentRow.getNulledField(LOTTO.ID_ESCLUSIONE);
			    	    String flagRipetizione = currentRow.getNulledField(LOTTO.FLAG_RIPETIZIONE);
			    	    String flagCup = currentRow.getNulledField(LOTTO.FLAG_CUP);
			            lotto.setCig(cig);
			            lotto.setCig_kkk(cig_kkk);
			            lotto.setDATA_CANCELLAZIONE_LOTTO(dataCancellazione);
			            lotto.setDataInibizionePagamento(dataInibPagamento);
			            lotto.setData_Pubblicazione(dataPubblicazione);
			            lotto.setImporto_Lotto(importoLotto);
			            lotto.setId_Scelta_Contraente(sceltaContraente);
			            lotto.setTIPO_CONTRATTO_LOTTO(tipoContratto);
			            lotto.setFLAG_RIPETIZIONE(flagRipetizione);
			            lotto.setID_MOTIVO_COLL_CIG(lm.getValueField(LOTTO.ID_MOTIVO,lotto.getId_Lotto()));
			            lotto.setFLAG_CUP(flagCup);
			            if (!"".equals(idEsclusione))
			            	lotto.setID_ESCLUSIONE(Integer.parseInt(idEsclusione));

			            if( SimogFlags.is3030_RFWEBGL00Active() ){
		                   String dataScadenzaPagamenti = currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI);
		                   String oraScadenzaPagamenti = currentRow.getNulledField(LOTTO.ORA_SCADENZA);
		                   String dataScadenzaRichiestaInvito = currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO);
		                   String dataLetteraInvito = currentRow.getNulledField(LOTTO.DATA_LETTERA_INVITO);
		                   String importoImpresa = currentRow.getNulledField(LOTTO.IMPORTO_IMPRESA);
			               
		                   lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
			               lotto.setDataLetteraInvito(dataLetteraInvito);
			               lotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
			               lotto.setORA_SCADENZA(oraScadenzaPagamenti);
			               lotto.setImporto_Impresa( new BigDecimal(importoImpresa) );
			            }
			            
			            //importo sceltacontraente tipocontratto
			            elenco.put(idLotto,lotto);  			    		
			    	}
			    }
		    }
		}
		return elenco;
	}
	
	public Map<String,Lotto> caricaElencoLottiDaCancellare (Map<String,Lotto> elencoLotti, String[] lottiDaCancellare){
		Map<String,Lotto> risultato=new HashMap<String,Lotto>();
		if (elencoLotti != null && lottiDaCancellare != null){
		    for(int i=0;i<lottiDaCancellare.length;i++){
		    	if (elencoLotti.containsKey(lottiDaCancellare[i])){
		    		//verifico eventuali lotti non cancellabili inseriti per errore,
		    		//controllo superfluo
		    		if("".equals(elencoLotti.get(lottiDaCancellare[i]).getDATA_CANCELLAZIONE_LOTTO())
		    			||"".equals(elencoLotti.get(lottiDaCancellare[i]).getDATA_INIB_PAGAMENTO()))
    		    	    risultato.put(lottiDaCancellare[i], elencoLotti.get(lottiDaCancellare[i]));
		    	}
		    }
		}
		return risultato;
	}
	
	public Map<String,Lotto> caricaElencoLottiDaPerfezionare (Map<String,Lotto> mappaLotti, String[] lottiDaCancellare){
		Map<String,Lotto> risultato=new HashMap<String,Lotto>();
		//risultato = mappaLotti;
		
		//inserisco solo i lotti che hanno lo stato = "in lavorazione"
		if(mappaLotti!=null){
			Collection<Lotto> lotti = mappaLotti.values();
			for(Lotto l : lotti){
				if("".equals(l.getDATA_CANCELLAZIONE_LOTTO())
				    &&"".equals(l.getDATA_INIB_PAGAMENTO())
				    &&"".equals(l.getData_Pubblicazione()))
					risultato.put(String.valueOf(l.getId_Lotto()),l);
			}
		}
		//sottraggo ai lotti in lavorazione quelli selezionati per la cancellazione
		if (risultato!=null && lottiDaCancellare!=null){
			for(int i=0;i<lottiDaCancellare.length;i++){
				if(risultato.containsKey(lottiDaCancellare[i]))
					risultato.remove(lottiDaCancellare[i]);
			}
		}
		//gm il risultato contiene solo lotti in lavorazione
		//non selezionati per la cancellazione
		return risultato;
	}
	
	public Map<String,Lotto> caricaElencoLottiDaPerfezionareOPerfezionati (Map<String,Lotto> mappaLotti, String[] lottiDaCancellare){
		Map<String,Lotto> risultato=new HashMap<String,Lotto>();
		//risultato = mappaLotti;
		
		//inserisco solo i lotti che hanno lo stato = "in lavorazione"
		if(mappaLotti!=null){
			Collection<Lotto> lotti = mappaLotti.values();
			for(Lotto l : lotti){
				if("".equals(l.getDATA_CANCELLAZIONE_LOTTO())
				    &&"".equals(l.getDATA_INIB_PAGAMENTO()))
				    //&&"".equals(l.getData_Pubblicazione()))
					risultato.put(String.valueOf(l.getId_Lotto()),l);
			}
		}
		//sottraggo ai lotti in lavorazione e perfezionati quelli selezionati per la cancellazione
		if (risultato!=null && lottiDaCancellare!=null){
			for(int i=0;i<lottiDaCancellare.length;i++){
				if(risultato.containsKey(lottiDaCancellare[i]))
					risultato.remove(lottiDaCancellare[i]);
			}
		}
		//gm il risultato contiene solo lotti in lavorazione o perfezionati
		//non selezionati per la cancellazione
		return risultato;
	}
	

	
	/*********************************************************************
	 * Il metodo serve per verificare se l'allegato Bando di Gara e obbligatorio.
	 * Se almeno un lotto rientra nella casistica ritorna true cioe obbligatorio, 
	 * altrimenti false cioe facoltativo.
	 * 
	 * @return  boolean
	 */
	
	public boolean isBandoObbligatorio(Map<String,Lotto> mappaLotti){
		boolean bandoObbligatorio = false;
		//boolean allCancellati = true;
		//gm se la gara contiene dei lotti, si effettua la scansione dei lotti
		if(mappaLotti!=null && !mappaLotti.isEmpty()){
			Collection<Lotto> elencoLotti = mappaLotti.values();
			for(Lotto lotto : elencoLotti){
				//gm se ho almeno un lotto non cancellato, verifico che rientri nella casistica
				if(lotto.getDATA_CANCELLAZIONE_LOTTO()==null || "".equals(lotto.getDATA_CANCELLAZIONE_LOTTO())){
	            //TICKET ALM #2847
					bandoObbligatorio = bandoObbligatorio || isBandoObbligatorio(lotto.getId_Scelta_Contraente(), lotto.getImporto_Lotto(), lotto.getTIPO_CONTRATTO_LOTTO(), lotto.getId_Gara());
				}
			}
		}
	    return bandoObbligatorio;
	}
	
	 public String garaPubblicabile (TableBean dettagliGara){
	     Map <String,Lotto> elenco=new HashMap<String,Lotto>();
	       Lotto lotto = null;
	     if (dettagliGara != null){
	         for(int rowIndex=0; rowIndex < dettagliGara.getTableSize(); rowIndex++){
	            TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
	            if(currentRow!=null){
	              String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
	               //verifico eventuali duplicati, controllo superfluo
	              //il confronto con "0" e necessario per le gare senza lotti
	               if((!"".equals(idLotto)) && (!"0".equals(idLotto)) && !elenco.containsKey(idLotto)){         
	                  lotto = new Lotto();   
	                    lotto.setId_Lotto(Long.parseLong(idLotto));
	                    String cig = currentRow.getNulledField(LOTTO.CIG);
	                  String cig_kkk = currentRow.getNulledField(LOTTO.CIG_KKK);
	                  BigDecimal importoLotto = new BigDecimal(currentRow.getNulledField(LOTTO.IMPORTO_LOTTO));
	                  String tipoContratto = currentRow.getNulledField(LOTTO.TIPO_CONTRATTO_LOTTO);
	                    String dataCancellazione = currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO);
	                  String dataInibPagamento = currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO);
	                  String dataPubblicazione = currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE);
	                  String sceltaContraente = currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE);
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
	     boolean pubblicabile = false;
	     boolean allCancellati = true;
	     //gm se la gara contiene dei lotti, si effettua la scansione dei lotti
	     if(elenco!=null && !elenco.isEmpty()){
	        Collection<Lotto> elencoLotti = elenco.values();
	        for(Lotto l : elencoLotti){
	           //gm se ho almeno un lotto non cancellato, verifico che rientri nella casistica
	           if(l.getDATA_CANCELLAZIONE_LOTTO()==null || "".equals(l.getDATA_CANCELLAZIONE_LOTTO())){
	               pubblicabile = pubblicabile || lottoManager.isPubblicabile(l.getId_Scelta_Contraente(), l.getImporto_Lotto(), l.getTIPO_CONTRATTO_LOTTO());
	                allCancellati = false;
	           }
	        }
	        //gm se tutti i lotti risultano cancellati, la gara e pubblicabile
	        if(allCancellati)
	           pubblicabile = true;
	     }
	     //gm altrimenti e una gara senza lotti quindi pubblicabile
	       else
	         pubblicabile = true;
	     if(pubblicabile)
	        return "vero";
	     else return "falso";
	  }	
	 
	 
/*
 * is3030_RFWEBGL00Active
 * Gestione delle procedure per la pubblicazione/perfezionamento dei lotti	 
 */
	 
	 private static final int PRO_INCOERENTE = 0;  
	 private static final int PRO_STANDARD  = 1;
	 private static final int PRO_RISTRETTA = 2;
	 private static final int PRO_NEGOZIATA = 3;
	 
	 /**
      * Restiuisce la tipologia di procedura da intraprendere per il perfezionamento dei lotti
	  * @param lottiDaPerfezionare
	  * @return 
	  * 0 : Procedura incoerente<br>
      * 1 : Procedura stantard<br>
      * 2 : Procedura ristretta<br>
      * 3 : Procedura negoziata<br>
	  */
	 public int getTipologiaProcedura(Map <String,Lotto> lottiDaPerfezionare, int idSvolgimento){
        String codiciProceduraRistretta = conf.getCodiciProceduraRistretta();
        List<String> listaCodiciProceduraRistretta = Arrays.asList(codiciProceduraRistretta.split(","));
        int numLotti = lottiDaPerfezionare.size();
        int standard = 0;
        int ristrette = 0;
        int negoziate = 0;
        boolean isProcRistretta= false;
        for(Lotto lotto: lottiDaPerfezionare.values()){
           String idSeltaContraente = lotto.getId_Scelta_Contraente();
           if(Costanti.PROC_RIS==Integer.parseInt(idSeltaContraente))
        	   isProcRistretta=true;
           negoziate += listaCodiciProceduraRistretta.contains(idSeltaContraente+Costanti.FLAG_PROCEDURA_NEGOZIATA) ? 1 : 0;
           ristrette += listaCodiciProceduraRistretta.contains(idSeltaContraente) ? 1 : 0;
           standard += !listaCodiciProceduraRistretta.contains(idSeltaContraente) 
                    && !listaCodiciProceduraRistretta.contains(idSeltaContraente+Costanti.FLAG_PROCEDURA_NEGOZIATA) ? 1 : 0;
        }
         
        //MAC 24395
        int res = negoziate == numLotti ? PRO_NEGOZIATA :
              (ristrette + negoziate) == numLotti ? PRO_RISTRETTA :
              (standard  + negoziate) == numLotti ? PRO_STANDARD  : 
              PRO_INCOERENTE;
	 
        if(isProcRistretta && idSvolgimento==Costanti.SVOLGIMENTO_SDA)
        	res = PRO_STANDARD;
        	
        return res;
     }
     
     public boolean isProceduraRistretta(Map <String,Lotto> lottiDaPerfezionare, int idSvolgimento){
        return getTipologiaProcedura(lottiDaPerfezionare,idSvolgimento) == PRO_RISTRETTA;
     }
     
     public boolean isProceduraNegoziata(Map <String,Lotto> lottiDaPerfezionare, int idSvolgimento){
        return getTipologiaProcedura(lottiDaPerfezionare,idSvolgimento) == PRO_NEGOZIATA;
     }
     
     public boolean isProceduraCompleta(Map <String,Lotto> lottiDaPerfezionare, Lotto lotto, int idSvolgimento){
        if( isProceduraRistretta(lottiDaPerfezionare,idSvolgimento) || isProceduraNegoziata(lottiDaPerfezionare,idSvolgimento) ){
           //perfezionamento completato
           if( lotto.getData_Pubblicazione() != null && !"".equals(lotto.getData_Pubblicazione())
                 && lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito())
                 && lotto.getDataLetteraInvito() != null  && !"".equals(lotto.getDataLetteraInvito())
                 && lotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lotto.getDATA_SCADENZA_PAGAMENTI()))
           {
              return true;
           }
        }
        return false;
     }
     
     public boolean isProceduraNegoziataRistretta(Map <String,Lotto> lottiDaPerfezionare, Lotto lotto, int idSvolgimento){
        if( isProceduraNegoziata(lottiDaPerfezionare, idSvolgimento) ){
           //perfezionamento a due fasi (procedura ristretta)
           if( lotto.getData_Pubblicazione() != null && !"".equals(lotto.getData_Pubblicazione())
              && lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito())
              && (lotto.getDataLetteraInvito() == null || "".equals(lotto.getDataLetteraInvito()))  
              && (lotto.getDATA_SCADENZA_PAGAMENTI() == null || "".equals(lotto.getDATA_SCADENZA_PAGAMENTI())))
           {
              return true;
           }
        }
        return false;
     }     
     
     public void validaPubblicabilita(Map<String, Lotto> lottiDaPerfezionare, int idSvolgimento) {
        if( getTipologiaProcedura(lottiDaPerfezionare, idSvolgimento) == PRO_INCOERENTE ){
           this.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_222);
        }
     }
     
     public void validaPerfezionamentoProceduraRistretta(Lotto lotto)throws ActionException{
        LottoValidator lottoValidator = new LottoValidator(connection, logger);
        
        lottoValidator.valida(lotto, ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE2);
        
        if(lottoValidator.getEccezioni().getSize() != 0)            
           this.getEccezioni().add(lottoValidator.getEccezioni());        
     }
     
     
     /**
      * Perfezionamento lotto per procedura ristretta (fase 2)
      * @param lotto
      * @throws ActionException
      */
     public void pubblicaBandoGaraProceduraRistretta(
           String utente,
           Gara gara,
           Map <String,Lotto> lottiDaPerfezionare,
           String dataLetteraInvito,
           String dataScadenzaPagamenti,
           String oraScadenzaPagamenti)
        throws ActionException{
        
        try {

           LogManager logManager = new LogManager(connection, logger);
           
           for(Lotto lottoCorrente: lottiDaPerfezionare.values() ){
        
              lottoManager.perfezionaLotto(
                    String.valueOf(lottoCorrente.getId_Lotto()), 
                    lottoCorrente.getData_Pubblicazione(), 
                    dataScadenzaPagamenti, 
                    lottoCorrente.getImporto_Impresa(),
                    oraScadenzaPagamenti, 
                    lottoCorrente.getDataScadenzaRichiestaInvito(), 
                    dataLetteraInvito);
              
              logManager.log(
                    PageHelper.getCurrentDate(), 
                    gara.getID_STAZIONE_APPALTANTE(), 
                    utente, 
                    lottoCorrente.getCIG().concat((lottoCorrente.getCIG_kkk())), 
                    // fase 2 di PR assimilata a perfezionamento standard LogManager.PERF_LOTTO_PR2,
                    LogManager.PERF_LOTTO,
                    gara.getCF_AMMINISTRAZIONE(), 
                    String.valueOf(lottoCorrente.getId_Lotto()), 
                    String.valueOf(gara.getId_Gara()));
           }
           
        /*
         * Associazione allegati alla pubblicazione
         */
           
           AllegatoManager allegatoManager = new AllegatoManager(connection, logger);
           
           AllegatoBean aBean = new AllegatoBean();
           aBean.setIdGara( new Long(gara.getId_Gara()).intValue() );
           aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice());

           List<AllegatoBean> aBeans = allegatoManager.load(aBean);
           
           for(AllegatoBean allegato: aBeans){
              String idAllegato = String.valueOf( allegato.getIdAllegato() );
              allegatoManager.update(idAllegato, gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
           }
           
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ActionException(e.getMessage());
        }
     }

     public AllValidationBeans validaCodiciCUPPerf(Lotto lt){
        // Per ogni CUP verifico la situazione
        ElaborazioniCUPClient cli = new ElaborazioniCUPClient(conf, logger);
        AllValidationBeans eccez = cli.validaCupDIPE(lt, true);
        
        // Se tutti i cup non sono validi DIPE devo bloccare la pubblicazione
        for(CupLottoAggExt elem: lt.getElencoCup()){
           if(elem.getDatiDIPE() != null && !Costanti.FLAG_VALORE_SI.equals(elem.getDatiDIPE().getVALIDO())){
              eccez.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_227.replace("$1", "pubblicazione"));
              break;
           }
        }
        
        return eccez;
     }
     
     // MARRA MEV 34470 3.04.8
     public void validaBandiEContratti(Gara gara, String linkAffidamentoDiretto)throws ActionException{
    	   
 		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
 		pubValidator.validaBandiEContratti(gara, linkAffidamentoDiretto);
 		if(pubValidator.getEccezioni().getSize() != 0)
 			this.getEccezioni().add(pubValidator.getEccezioni());
 	}
     
  // //3.04.11 MEV 44999
     public void controlloBloccoCig(String dataPubblicazione)throws ActionException{
    	   
 		PubblicazioneValidator pubValidator = new PubblicazioneValidator(connection, logger);
 		pubValidator.controlloBloccoCig(dataPubblicazione);
 		if(pubValidator.getEccezioni().getSize() != 0)
 			this.getEccezioni().add(pubValidator.getEccezioni());
 	}
     
   //MEV 43345 3.04.10
   		public PubblicazioneBean validaQualificazioneSAPerfezionamento(PubblicazioneBean pub, Lotto lotto, Gara g, String sessionId) {
   			//solo se è da web parte il controllo sulla qualificazione
   			if (!SimogFlags.isFromWS()) {
   				String currentDate = PageHelper.getCurrentDate();
   	   			
//   	   			if (pub.getDerogaQualificazioneSA() != null) {
//   					lotto.setDerogaQualificazioneSA(pub.getDerogaQualificazioneSA());
//   				}
   	   			
   	   			//3.04.9 MEV 40610 se la MEV è ATTIVA faccio partire la logica
   	   			if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
   	   				//3.04.9.2 se il CIG esiste siamo in modifica e non chiamo il servizio
   	   				
   	   					//se mockata
   	   					if (SimogProperties.getInstance().getQualificazioneIsMock().equals("true")) {
   	   						//MockValidaQualificazioneSA(lotto, g, sessionId);
   	   						
   	   						//NO MOCK-------------------------------------------------------------------
   	   					}else if (SimogProperties.getInstance().getQualificazioneIsMock().equals("false")) { //se non è mockata
   	   						logger.info("servizio qualificazione-sa no mock");
   	   						//3.04.9.2 se la gara è una adesione non chiama il servizio
   	   						if (g.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET ||
   	   								g.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET ) {
   	   							logger.info("servizio qualificazione-sa non chiamato perchè siamo la gara è una adesione");
   	   						}else {
   	   							//se non è stata inserita nessuna deroga o se tramite ws è stata inserita la deroga 11 allora faccio la chiamata
   	   							//e se la scelta del contraente è diversa da 16 allora faccio la chiamata MAD alla MEV 40610
   	   							//e se l'id esclusione è diverso da 34 e da 16 allora faccio la chiamata MAD alla MEV 40610
   	   							//e se il campo motivo del collegamento è diverso dai valori 1 o 2 allora faccio la chiamata MEV 44994 3.04.11
   	   							//e se il motivo dell'estrema urgenza è 1 oppure 2 allora faccio la chiamata MAD alla MEV 40610
   	   							//(TOLTO perchè aggiunta una voce nelle deroghe)in pratica se il flag pnrr è SI la chiamata non la faccio mai perchè la prima condizione dell' AND sarà sempre FALSE
//   	   							lotto.getFLAG_PNRR_PNC() != null && !lotto.getFLAG_PNRR_PNC().equals("") && isNFlag(lotto.getFLAG_PNRR_PNC())
   	   							if ((lotto.getId_Scelta_Contraente() != null && !lotto.getId_Scelta_Contraente().equals("16")) &&
   	   									(lotto.getID_ESCLUSIONE() != 34 && lotto.getID_ESCLUSIONE() != 22) &&
   	   								(!lotto.getID_MOTIVO_COLL_CIG().equals("1") && !lotto.getID_MOTIVO_COLL_CIG().equals("2")) && //MEV 44994 3.04.11
   	   									(lotto.getID_ART_REGIME() != 44 && lotto.getID_ART_REGIME() != 46) && //MEV 3.04.10
   	   									(g.getID_ESTREMA_URGENZA() != 1 && g.getID_ESTREMA_URGENZA() != 2) &&
   	   									((pub.getDerogaQualificazioneSA()== null || pub.getDerogaQualificazioneSA().equals("")) || 
   	   									(pub.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()))) {
   	   								logger.info("controlli deroganti superati");
   	   								
   	   								Unirest.setTimeouts(0, 0);
   	   								HttpResponse<String> response;
   	   								logger.info("parametri prima chiamata----------------------------");
   	   								logger.info("Codice Fisc SA: " + g.getCF_AMMINISTRAZIONE());
   	   								logger.info("oggetto qualificazione: " + lotto.getTIPO_CONTRATTO_LOTTO());
   	   								logger.info("importo qualificazione: " + lotto.getImporto_Lotto());
   	   								logger.info("anno qualificazione: " + currentDate.substring(0, 4));
   	   								logger.info("sessionid qualificazione: " + sessionId);
   	   								logger.info("fine parametri prima chiamata----------------------------");
   	   								try {
   	   									response = Unirest.get(SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
   	   											+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
   	   											+ "&importo=" + lotto.getImporto_Lotto()
   	   											+ "&anno=" + currentDate.substring(0, 4)
   	   											+ "&sessionId=" + sessionId
   	   											+ "&app=" + "@anac/microfrontend")
   	   									  .header("Accept", "application/json")
   	   									  .asString();
   	   									//LOG CHIAMATA-----------------------------------------------------------
   	   									if (response != null) {
   	   										logger.info("request URL QUALIFICAZIONE-----------" + SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
   	   											+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
   	   											+ "&importo=" + lotto.getImporto_Lotto()
   	   											+ "&anno=" + currentDate.substring(0, 4)
   	   											+ "&sessionId=" + sessionId
   	   											+ "&app=" + "@anac/microfrontend");
   	   										logger.info("response URL QUALIFICAZIONE-----------" + response.getBody());
   	   										logger.info("status URL QUALIFICAZIONE-----------" + response.getStatus());
   	   									}else {
   	   										logger.info("response null URL QUALIFICAZIONE-----------");
   	   									}
   	   									//fine LOG CHIAMATA-----------------------------------------------------------
   	   									
   	   									
   	   									if (response.getStatus()==200) {
   	   										JSONObject jsonObj = new JSONObject(response.getBody());
   	   										logger.info(jsonObj);
   	   										logger.info("FlagIsQualificataKO prima chiamata N");
   	   										//setta N solo se non è già S
	   	   									if (pub.getFlagIsQualificataKO() == null || "".equals(pub.getFlagIsQualificataKO())) {
	   											pub.setFlagIsQualificataKO("N");
	   										}
   	   										boolean isQualificataSA = jsonObj.getBoolean("data");
   	   										if (!isQualificataSA) {
   	   											logger.info("SA non qualificata dopo prima chiamata");
   	   											//se il servizio risponde false e se la cpv appartiene a 713 o 714 e il tipo di contratto è diverso da L e
   	   											//e non è stata inserita l'autodichiarazione di qualificazione (11)
   	   											//allora setto a L e faccio la seconda chiamata
   	   											if ((lotto.getId_CPV().substring(0,3).equals("713") || lotto.getId_CPV().substring(0,3).equals("714"))
   	   													&& !lotto.getTIPO_CONTRATTO_LOTTO().equals("L") && !pub.getDerogaQualificazioneSA().equals("11")) {
   	   												lotto.setTIPO_CONTRATTO_LOTTO("L");
   	   												
   	   												logger.info("parametri seconda chiamata----------------------------");
   	   												logger.info("Codice Fisc SA: " + g.getCF_AMMINISTRAZIONE());
   	   												logger.info("oggetto qualificazione: " + lotto.getTIPO_CONTRATTO_LOTTO());
   	   												logger.info("importo qualificazione: " + lotto.getImporto_Lotto());
   	   												logger.info("anno qualificazione: " + currentDate.substring(0, 4));
   	   												logger.info("sessionid qualificazione: " + sessionId);
   	   												logger.info("fine parametri seconda chiamata----------------------------");
   	   												
   	   												response = Unirest.get(SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
   	   														+ g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
   	   														+ "&importo=" + lotto.getImporto_Lotto()
   	   														+ "&anno=" + currentDate.substring(0, 4)
   	   														+ "&sessionId=" + sessionId
   	   														+ "&app=" + "@anac/microfrontend")
   	   															  .header("Accept", "application/json")
   	   															  .asString();	
   	   												//LOG CHIAMATA-----------------------------------------------------------
   	   												if (response != null) {
   	   													logger.info("request URL QUALIFICAZIONE-----------" + SimogProperties.getInstance().getQualificazioneUrl() + "?codice_fiscale="
   	   														+g.getCF_AMMINISTRAZIONE() + "&oggetto=" + lotto.getTIPO_CONTRATTO_LOTTO()
   	   														+ "&importo=" + lotto.getImporto_Lotto()
   	   														+ "&anno=" + currentDate.substring(0, 4)
   	   														+ "&sessionId=" + sessionId
   	   														+ "&app=" + "@anac/microfrontend");
   	   													logger.info("response URL QUALIFICAZIONE-----------" + response.getBody());
   	   													logger.info("status URL QUALIFICAZIONE-----------" + response.getStatus());
   	   												}else {
   	   													logger.info("response null URL QUALIFICAZIONE-----------");
   	   												}
   	   												//fine LOG CHIAMATA-----------------------------------------------------------
   	   												if (response.getStatus()==200) {
   	   													jsonObj = new JSONObject(response.getBody());
	   	   												//setta N solo se non è già S
   	   													if (pub.getFlagIsQualificataKO() == null || "".equals(pub.getFlagIsQualificataKO())) {
	   	   													pub.setFlagIsQualificataKO("N");
	   	   												}
   	   													logger.info("FlagIsQualificataKO seconda chiamata N");
   	   													isQualificataSA = jsonObj.getBoolean("data");
   	   													if (!isQualificataSA) {
   	   														logger.info("SIMOG_LOTTO_040 da seconda chiamata");
   	   														mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
   	   													}
   	   												}//se la seconda chiamata è andata KO setto in ogni caso il flagKO a S 
   	   												//ma solo se non è stata inserita la deroga 11 mostro il messaggio di errore
   	   												else if(pub.getDerogaQualificazioneSA()!= null && !pub.getDerogaQualificazioneSA().equals("") && pub.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
   	   													pub.setFlagIsQualificataKO("S");
   	   													return pub;
   	   												}else if (pub.getDerogaQualificazioneSA()!= null && pub.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
   	   													pub.setFlagIsQualificataKO("S");
   	   													return pub;
   	   												}else {
   	   													pub.setFlagIsQualificataKO("S");
   	   													mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
   	   													return pub;
   	   												}
   	   											}else {
   	   												logger.info("SIMOG_LOTTO_040 da prima chiamata");
   	   												mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_040);
   	   											}
   	   										}
   	   									}//se la prima chiamata è andata KO setto in ogni caso il flagKO a S 
   	   									//ma solo se non è stata inserita la deroga 11 mostro il messaggio di errore
   	   									else if(pub.getDerogaQualificazioneSA()!= null && !pub.getDerogaQualificazioneSA().equals("") && pub.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
   	   										pub.setFlagIsQualificataKO("S");
   	   										return pub;
   	   									}else if (pub.getDerogaQualificazioneSA()!= null && pub.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
   	   										pub.setFlagIsQualificataKO("S");
   	   										return pub;
   	   									}else {
   	   										pub.setFlagIsQualificataKO("S");
   	   										mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
   	   										return pub;
   	   									}
   	   									
   	   								} catch (UnirestException e) {
   	   									// TODO Auto-generated catch block
   	   									if(pub.getDerogaQualificazioneSA()!= null && !pub.getDerogaQualificazioneSA().equals("") && pub.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
   	   										pub.setFlagIsQualificataKO("S");
   	   										e.printStackTrace();
   	   										return pub;
   	   									}else {
   	   										pub.setFlagIsQualificataKO("S");
   	   										mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
   	   										e.printStackTrace();
   	   										return pub;
   	   									}
   	   									
   	   									
   	   								} catch (Exception ex) {
   	   									// TODO Auto-generated catch block
   	   									if(pub.getDerogaQualificazioneSA()!= null && !pub.getDerogaQualificazioneSA().equals("") && pub.getDerogaQualificazioneSA().equals("11") && SimogFlags.isFromWS()) {
   	   										ex.printStackTrace();
   	   										pub.setFlagIsQualificataKO("S");
   	   										return pub;
   	   									}else {
   	   										ex.printStackTrace();	
   	   										pub.setFlagIsQualificataKO("S");
   	   										mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_041);
   	   										return pub;
   	   									}
   	   									
   	   								}
   	   							}else if(pub.getDerogaQualificazioneSA()!= null && !pub.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) { //se la deroga è stata inserita tramite web dopo aver ricevuto false
   	   								if (pub.getFlagIsQualificataKO() == null || "".equals(pub.getFlagIsQualificataKO())) {
										pub.setFlagIsQualificataKO("N");
									}
   	   								
   	   								logger.info("Chiamata al servizio non fatta in quanto è stata inserita la deroga tramite web");
   	   								return pub;
   	   							}else if (pub.getDerogaQualificazioneSA()!= null && pub.getDerogaQualificazioneSA().equals("11") && !SimogFlags.isFromWS()) {
   	   								pub.setFlagIsQualificataKO("S");
   	   								
   	   								logger.info("Chiamata al servizio non fatta in quanto è stata inserita la deroga 11 tramite web");
   	   								return pub;
   	   							}else {
   	   								logger.info("Chiamata al servizio non fatta");
   	   								return pub;
   	   							}
   	   						}
   	   						
   	   					}
   	   				
   	   				
   	   				
   	   				
   	   			}
			}
   			
   			return pub;
   		}
}
