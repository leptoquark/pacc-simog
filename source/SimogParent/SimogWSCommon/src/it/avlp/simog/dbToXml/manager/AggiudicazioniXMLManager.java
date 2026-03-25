package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.util.MyHelper;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.OrigineSchedaType;
import it.avlp.simog.massload.xmlbeans.RequisitoType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SezioneType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class AggiudicazioniXMLManager {
	
	private AggiudicazioneBean aggiudicazioneBean = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType schedaCompletaType = null;
	private SchedaType schedaType = null;
	private DatiAggiudicazioneType datiAggiudicazioneType = null;
	private String schedaVer = null;
	
	public AggiudicazioniXMLManager(Connection con,Logger logger,AggiudicazioneBean aggiudicazioneBean,SchedaCompletaType schedaCompletaType,
			SchedaType schedaType,DatiAggiudicazioneType datiAggiudicazioneType, String schedaVer){
		this.aggiudicazioneBean = aggiudicazioneBean;
		this.con = con;
		this.logger = logger;
		this.schedaCompletaType = schedaCompletaType;
		this.schedaType = schedaType;
		this.datiAggiudicazioneType = datiAggiudicazioneType;
		this.schedaVer = schedaVer;
	}
	/***************************************************************************************
	 * Carica l'aggiudicazione 
	 * @param tipoEnte String 
	 * @throws SimogWSException
	 */
	public void getAggiudicazione(String tipoEnte) throws SimogWSException{
		try{
			//gm aggiunto per ditte ausiliarie
			DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(con, logger);
			AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(con, logger);
			CondizioniManager condizioniManager = new CondizioniManager(con,logger);
			RequisitiManager requisitiManager = new RequisitiManager(con,logger);
			TipoAppaltoManager tipoAppaltoManager = new TipoAppaltoManager(con,logger);
			ResponsabileManager responsabileManager = new ResponsabileManager(con,logger);
			FinanziamentoManager finanziamentoManager = new FinanziamentoManager(con,logger);
			AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(con,logger);
			aggiudicazioneBean = aggiudicazioniManager.getAggiudicazioni(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			//logger.debug(ObjectIntrospector.propertiesInfo(abb.getClass(),abb ));
			AggiudicazioneType aggiudicazioneType = schedaCompletaType.addNewAggiudicazione();
			/* oggetti con cardinalita' singola	(aggiudicazioni)*/
				/*REQUIRED	appalto	*/
			this.setAppalto(aggiudicazioneBean, aggiudicazioneType);
			//XX-X: CUI 
			//in sintesi il cui sul db e' una cosa tipo 0-[cig] noi lo vogliamo [cig]-[prog-cui]
			//val cui db
			String formattedCui = aggiudicazioneBean.getCui();
			//cui senza numero + trattino
			formattedCui = formattedCui.substring(aggiudicazioneBean.getCui().indexOf("-") + 1,aggiudicazioneBean.getCui().length());
			//cui concatenato con trattino + prog-cui
			formattedCui = formattedCui + "-" + aggiudicazioneBean.getProgCUI();
			//settaggio bean
			schedaCompletaType.setCUI(formattedCui);	
			
			/* oggetti con cardinalita' multipla	(aggiudicatari)*/
	 			/*	aggiudicatari [0,N]*/
			List<AggiudicatarioBean> listOfAggiudicatari = aggiudicatarioManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfAggiudicatari.isEmpty()){
				Iterator<AggiudicatarioBean> aggiudicatariIterator = listOfAggiudicatari.iterator();
				this.setAggiudicatari(aggiudicatariIterator, aggiudicazioneType);
			}
			//gm aggiunto per ditte ausiliarie
			/* oggetti con cardinalita' multipla	(ditte ausiliarie)*/
 			/*	ditte ausiliarie [0,N]*/
		    List<DittaAusiliariaBean> listOfDitteAusiliarie = dittaAusiliariaManager.loadManyByAggiudicazione(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
		    if(!listOfDitteAusiliarie.isEmpty()){
			    Iterator<DittaAusiliariaBean> ditteAusiliarieIterator = listOfDitteAusiliarie.iterator();
			    this.setDitteAusiliarie(ditteAusiliarieIterator, listOfAggiudicatari, aggiudicazioneType);
		    }		
				/* TipiAppalto	[1,*]*/	
			List<TipoAppaltoAggBean> listOfTipoAppalto = tipoAppaltoManager.loadManyFS(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(),tipoEnte, false);
			this.print(listOfTipoAppalto);
			Iterator<TipoAppaltoAggBean> tipoAppaltoIterator = listOfTipoAppalto.iterator();
			this.setTipiAppaltoForn(tipoAppaltoIterator, aggiudicazioneType);
				listOfTipoAppalto = null;
			listOfTipoAppalto = tipoAppaltoManager.loadManyL(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(),tipoEnte, false);
			this.print(listOfTipoAppalto);
				tipoAppaltoIterator = null;
			tipoAppaltoIterator = listOfTipoAppalto.iterator();
			this.setTipiAppaltoLav(tipoAppaltoIterator, aggiudicazioneType);
			
                /*OPTIONAL	Condizioni	[0,N]*/
			List<CondizioneAggBean> listOfCondizioni = condizioniManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfCondizioni.isEmpty()){
				Iterator<CondizioneAggBean> condizioniIterator = listOfCondizioni.iterator();
				this.setCondizioni(condizioniIterator,aggiudicazioneType);
			}
				/*OPTIONAL	Requisiti	[0,N]*/
			List<RequisitiBean> listOfRequisiti = requisitiManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfRequisiti.isEmpty()){
				Iterator<RequisitiBean> requisitiIterator = listOfRequisiti.iterator();
				this.setRequisiti(requisitiIterator, aggiudicazioneType);
			}
				/*OPTIONAL	Finanziamenti	[0,N]*/
			List<TipoFinanziamentoBean> listOfFinanaziamenti = finanziamentoManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfFinanaziamenti.isEmpty()){
				Iterator<TipoFinanziamentoBean> finanziamentiIterator = listOfFinanaziamenti.iterator();
				this.setFinanziamenti(finanziamentiIterator,aggiudicazioneType);
			}
				/*OPTIONAL	Incaricati progettazione	[0,N]*/
			List<ResponsabileBean> listOfProgettisti = responsabileManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), PSBD.SEZIONE_PA, false);
			if(!listOfProgettisti.isEmpty()){
				Iterator<ResponsabileBean> progettistiIterator = listOfProgettisti.iterator();
				this.setIncaricati(progettistiIterator, aggiudicazioneType,PSBD.SEZIONE_PA);
			}

			   /*OPTIONAL  Incaricati progettazione PG   [0,N]*/
			List<ResponsabileBean> listOfProgettistiPG = responsabileManager.loadManySoggPart(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), PSBD.SEZIONE_PA, false);
			if(!listOfProgettistiPG.isEmpty()){
			   Iterator<ResponsabileBean> progettistiIterator = listOfProgettistiPG.iterator();
			   this.setIncaricati(progettistiIterator, aggiudicazioneType,PSBD.SEZIONE_PA);
			}
			
				/*OPTIONAL	Incaricati responsabili	[0,N]*/
			List<ResponsabileBean> listOfIncaricati = responsabileManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), PSBD.SEZIONE_RA, false);
			if(!listOfIncaricati.isEmpty()){
				Iterator<ResponsabileBean> incaricatiIterator = listOfIncaricati.iterator();
				this.setIncaricati(incaricatiIterator, aggiudicazioneType,PSBD.SEZIONE_RA);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati gara");
			logger.error("errore :"+e.getMessage());
			e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_03);
		}
	}
	/*******************************************************************************
	 * Imposta i dati di Appalto
	 * param abb AggiudicazioneBean 
	 * param at AggiudicazioneType 
	 */
	private void setAppalto(AggiudicazioneBean aggiudicazioneBean,AggiudicazioneType aggiudicazioneType){
		logger.debug("eseguendo: void setAppalto(AggiudicazioneBean abb,AggiudicazioneType at)");
		//logger.debug(ObjectIntrospector.propertiesInfo(abb.getClass(), abb));
		AppaltoType appalto = aggiudicazioneType.addNewAppalto();
		appalto.setASTAELETTRONICA(FlagSNType.Enum.forString(aggiudicazioneBean.getAstaElettronica()));

		if(aggiudicazioneBean.getCriteriSelezioneStabilitiSA()!=null && !"".equals(aggiudicazioneBean.getCriteriSelezioneStabilitiSA()))
			appalto.setCRITERISELEZIONESTABILITISA(FlagSNType.Enum.forString(aggiudicazioneBean.getCriteriSelezioneStabilitiSA()));
		
		appalto.setCUP(aggiudicazioneBean.getCup());
		appalto.setCODSTRUMENTO(aggiudicazioneBean.getCodStrumento());
		appalto.setDATASCADENZAPRESOFFERTA(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataScadenzaPresOfferta()));
		appalto.setDATAVERBAGGIUDICAZIONE(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataVerbaleAggiudicazione()));
		appalto.setFLAGACCORDOQUADRO(FlagSNType.Enum.forString(aggiudicazioneBean.getFlagAccordoQuadro()));
		appalto.setFLAGRICHSUBAPPALTO(FlagSNType.Enum.forString(aggiudicazioneBean.getFlagRichSubappalto()));
		appalto.setIDMODOGARA(Long.toString(aggiudicazioneBean.getIdModalitaGara()));
		
		//TICKET ALM - 3.04.2
		//Mostra la scelta contraente solo se le schede sono precedenti la 3.04.2
		if( SimogFlags.is3042Active() && schedaVer.compareTo(Costanti.VERS_304_2_0) < 0 )
		    appalto.setIDSCELTACONTRAENTE(Long.toString(aggiudicazioneBean.getIdSceltaContraente()));
		    
		//TICKET ALM #15714 - 3.04.5
		if(aggiudicazioneBean.getIdTipoPrestazione()!=0)
		     appalto.setIDTIPOPRESTAZIONE(Long.toString(aggiudicazioneBean.getIdTipoPrestazione()));
		
		appalto.setIMPORTOAGGIUDICAZIONE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoAggiudicazione(), new BigDecimal(0)));
		appalto.setIMPORTOATTUAZIONESICUREZZA((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoAttuazioneSicurezza(), new BigDecimal(0)));
		appalto.setIMPORTODISPOSIZIONE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoDisposizione(), new BigDecimal(0)));
		appalto.setIMPORTOFORNITURE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoForniture(), new BigDecimal(0)));
		appalto.setIMPORTOLAVORI((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoLavori(), new BigDecimal(0)));
		appalto.setIMPORTOPROGETTAZIONE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoProgettazione(), new BigDecimal(0)));
		appalto.setIMPORTOSERVIZI((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoServizi(), new BigDecimal(0)));
		
		//TICKET ALM #15630 - MAC
		if(aggiudicazioneBean.getValSogliaAnomalia()!=null)
		    appalto.setVALSOGLIAANOMALIA((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getValSogliaAnomalia(), new BigDecimal(0)));
		/* optionals */
		
		//gm nuovo codice 3.0
		if(aggiudicazioneBean.getOpereUrbanizzazione() != null)
			appalto.setOPEREURBANIZSCOMPUTO(FlagSNType.Enum.forString(aggiudicazioneBean.getOpereUrbanizzazione()));
		//gm fine nuovo codice 3.0
		
		if(aggiudicazioneBean.getLuogoIstat() != null){
			appalto.setLUOGOISTAT(aggiudicazioneBean.getLuogoIstat());
		}if(aggiudicazioneBean.getLuogoNuts() != null){
			appalto.setLUOGONUTS(aggiudicazioneBean.getLuogoNuts());
		}		
		if (aggiudicazioneBean.getDataInvito() != null)
			appalto.setDATAINVITO(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataInvito()));
		if (aggiudicazioneBean.getDataManifInteresse() != null)
			appalto.setDATAMANIFINTERESSE(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataManifInteresse()));
		if (aggiudicazioneBean.getDataScadenzaRichiestaInvito() != null)
			appalto.setDATASCADENZARICHIESTAINVITO(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataScadenzaRichiestaInvito()));
		if (aggiudicazioneBean.getIdModoIndizione() != 0)
			appalto.setIDMODOINDIZIONE(Integer.toString(aggiudicazioneBean.getIdModoIndizione()));
		
		if(aggiudicazioneBean.getImportoNonAssog()!= null)
			appalto.setIMPNONASSOG(aggiudicazioneBean.getImportoNonAssog());
		/**/
		appalto.setNUMIMPESCLINSUFGIUST(aggiudicazioneBean.getNumImpEscluseInsufGiust());
		appalto.setNUMIMPRESEINVITATE(aggiudicazioneBean.getNumImpreseInvitate());
		appalto.setNUMIMPRESEOFFERENTI(aggiudicazioneBean.getNumImpreseOfferenti());
		appalto.setNUMIMPRESERICHIEDENTI(aggiudicazioneBean.getNumImpreseRichiedenti());
		appalto.setNUMMANIFINTERESSE(aggiudicazioneBean.getNumManifInteresse());
		appalto.setNUMOFFERTEAMMESSE(aggiudicazioneBean.getNumOfferteAmmesse());
		appalto.setNUMOFFERTEESCLUSE(aggiudicazioneBean.getNumOfferteEscluse());
		appalto.setNUMOFFERTEFUORISOGLIA(aggiudicazioneBean.getNumOfferteFuoriSoglia());
		
		if (aggiudicazioneBean.getOffertaMassimo() != null) {
			appalto.setOFFERTAMASSIMO(aggiudicazioneBean.getOffertaMassimo());
		}
		
		if (aggiudicazioneBean.getOffertaMinima() != null) {
			appalto.setOFFERTAMINIMA(aggiudicazioneBean.getOffertaMinima());
		}
		
		if(aggiudicazioneBean.getPercOffAumento() != null){
		appalto.setPERCOFFAUMENTO(aggiudicazioneBean.getPercOffAumento());
		}else{appalto.setPERCOFFAUMENTO(new BigDecimal(0));}
		if(aggiudicazioneBean.getPercRibassoAgg() != null){
			appalto.setPERCRIBASSOAGG(aggiudicazioneBean.getPercRibassoAgg());
		}else{
			appalto.setPERCRIBASSOAGG(new BigDecimal(0));
		}		
		appalto.setPREINFORMAZIONE(FlagSNType.Enum.forString(aggiudicazioneBean.getPreinformazione()));
		appalto.setPROCEDURAACC(FlagSNType.Enum.forString(aggiudicazioneBean.getProceduraAcc()));
		
		if(aggiudicazioneBean.getSistemaQualificazione()!=null && !"".equals(aggiudicazioneBean.getSistemaQualificazione()))
			appalto.setSISTEMAQUALIFICAZIONE(FlagSNType.Enum.forString(aggiudicazioneBean.getSistemaQualificazione()));
				
		appalto.setTERMINERIDOTTO(FlagSNType.Enum.forString(aggiudicazioneBean.getTermineRidotto()));						

		if(aggiudicazioneBean.getCodiceContratto() != null)
			appalto.setCODICECONTRATTO(aggiudicazioneBean.getCodiceContratto());
			
		if(aggiudicazioneBean.getFlagAggiudPrincipale() != null)
			appalto.setFLAGAGGIUDPRINCIPALE(FlagSNType.Enum.forString(aggiudicazioneBean.getFlagAggiudPrincipale()));
		
        if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           appalto.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(aggiudicazioneBean.getIdStato()) ));
        }	
        
        if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           appalto.setORIGINESCHEDA(OrigineSchedaType.Enum.forString( Long.toString(aggiudicazioneBean.getOrigine()) ));
           if(aggiudicazioneBean.getIdLocale() != null && !"".equals(aggiudicazioneBean.getIdLocale()))
              appalto.setIDSCHEDALOCALE(aggiudicazioneBean.getIdLocale());
           appalto.setIDSCHEDASIMOG(String.valueOf(aggiudicazioneBean.getIdAggiudicazione()));           
        }
        
        //TICKET ALM #14639 - 3.04.5
        if(aggiudicazioneBean.getRelazioneUnica() != null)
        	appalto.setRELAZIONEUNICA(FlagSNType.Enum.forString(aggiudicazioneBean.getRelazioneUnica()));
        
        // PP 16.04.2014 mancavano i dati della riaggiudicazione
        if(aggiudicazioneBean.getProgCuiRiaggiudicato() != 0)
           appalto.setPROGCUIRIAGGIUDICATO(aggiudicazioneBean.getProgCuiRiaggiudicato());
        if(aggiudicazioneBean.getModalitaRiaggiudicazione() != 0)
           appalto.setMODALITARIAGGIUDICAZIONE(String.valueOf(aggiudicazioneBean.getModalitaRiaggiudicazione()));
	}
	
	/******************************************************************************************
	 * Imposta le Ditte Ausiliarie
	 * param Iterator : i1
	 * param AggiudicazioneType : at
	 */
	//gm aggiunto per ditte ausiliarie
	private void setDitteAusiliarie(Iterator<DittaAusiliariaBean> ditteAusiliarieIterator, List<AggiudicatarioBean> listOfAggiudicatari, AggiudicazioneType aggiudicazioneType){	
		logger.debug("eseguendo: void setDitteAusiliarie(Iterator<DittaAusiliariaBean> i1,AggiudicazioneType at)");
		while(ditteAusiliarieIterator.hasNext()){
			DittaAusiliariaBean dab = ditteAusiliarieIterator.next();
			SoggettoPartecipanteBean spb = dab.getSoggettoPartecipante();
			
			//aggiungo il cf e l'id_stato dell'aggiudicatario nel bean ditta ausiliaria
			if(!listOfAggiudicatari.isEmpty()){
				for(AggiudicatarioBean agg : listOfAggiudicatari){
					if(dab.getIdAggiudicatario()==agg.getIdAggiudicatario()){
						dab.setCodiceFiscaleAggiudicatario(agg.getSoggettoPartecipante().getCodiceFiscale());
					    dab.setId_statoAggiudicatario(agg.getSoggettoPartecipante().getId_stato());
				    }
				}
			}
			String  e = null;
			if(!ToolXMLManager.checkExist(schedaType.getAggiudicatari(), spb.getCodiceFiscale(), spb.getId_stato())){
				AggiudicatariType ait = schedaType.getAggiudicatari();
				AggiudicatarioType atrio;
				if(ait == null){
					atrio = schedaType.addNewAggiudicatari().addNewAggiudicatario();				
				}else{
					atrio = schedaType.getAggiudicatari().addNewAggiudicatario();
				}
				atrio.setCAMERACOMMERCIO(spb.getCameraCommercio());
				atrio.setCAP(spb.getCap());				
				atrio.setCFRAPPRESENTANTE(spb.getCfRappresentante());
				atrio.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());
				atrio.setCITTA(spb.getCitta());
				atrio.setCIVICO(spb.getCivico());				
				atrio.setCOGNOME(spb.getCognome());
				atrio.setDENOMINAZIONE(spb.getDenominazione());			
				atrio.setINDIRIZZO(spb.getIndirizzo());
				atrio.setNOME(spb.getNome());
				atrio.setPARTITAIVA(spb.getPartitaIva());
				atrio.setPROVINCIA(spb.getProvincia());
				/** adds 26092008 */
				if(spb.getId_stato() != null && ! Costanti.CODICE_STATO_ITALIANO.equals(spb.getId_stato())){
					atrio.setCODICESTATO(spb.getId_stato());
					//caso in cui la flag esteri � settata correttamente
					if(spb.getFlagEsteri() != null && !"".equals(spb.getFlagEsteri())){
						atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(spb.getFlagEsteri()));
					//caso in cui la flag esteri non risulta settata
					}else{
						atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_SI));
					}
					e = spb.getId_stato();
				}else{
					atrio.setCODICESTATO("");
					atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString("N"));
					e = "";
				}	
			}else{
				//XXX: sovrascrittura anagrafica, vedi javadoc metodo
				e = ToolXMLManager.sovrascriviAnagraficaPartecipante(schedaType.getAggiudicatari(), spb);
			}
			DittaAusiliariaType dat = aggiudicazioneType.addNewDitteAusiliarie();
			dat.setCODICEFISCALEAGGIUDICATARIO(dab.getCodiceFiscaleAggiudicatario());
			dat.setCODICESTATOAGGIUDICATARIO(dab.getId_statoAggiudicatario());
			dat.setCODICEFISCALEAUSILIARIA(dab.getSoggettoPartecipante().getCodiceFiscale());
			dat.setCODICESTATOAUSILIARIA(dab.getSoggettoPartecipante().getId_stato());
			
			if(dab.getFlagAvvalimento() != null && !"".equals(dab.getFlagAvvalimento().trim()))
				dat.setFLAGAVVALIMENTO(FlagAvvalimentoType.Enum.forString(dab.getFlagAvvalimento()));		
			else
				dat.setFLAGAVVALIMENTO(FlagAvvalimentoType.X_0);
		}
	}
	
	
	/******************************************************************************************
	 * Imposta gli Aggiudicatari
	 * param Iterator : i1
	 * param AggiudicazioneType : at
	 */
	private void setAggiudicatari(Iterator<AggiudicatarioBean> aggiudicatariIterator,AggiudicazioneType aggiudicazioneType){	
		logger.debug("eseguendo: void setAggiudicatari(Iterator<AggiudicatarioBean> i1,AggiudicazioneType at)");
		while(aggiudicatariIterator.hasNext()){					
			AggiudicatarioBean abrio = aggiudicatariIterator.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(abrio.getClass(),abrio ));
			SoggettoPartecipanteBean spb = abrio.getSoggettoPartecipante();
			//logger.debug(ObjectIntrospector.propertiesInfo(spb.getClass(),spb ));
			/* prima di inserire un nuovo soggetto controllare che non sia gia presente */
			String  e = null;
			if(!ToolXMLManager.checkExist(schedaType.getAggiudicatari(), spb.getCodiceFiscale(), spb.getId_stato())){
				AggiudicatariType ait = schedaType.getAggiudicatari();
				AggiudicatarioType atrio;
				if(ait == null){
					atrio = schedaType.addNewAggiudicatari().addNewAggiudicatario();				
				}else{
					atrio = schedaType.getAggiudicatari().addNewAggiudicatario();
				}
				atrio.setCAMERACOMMERCIO(spb.getCameraCommercio());
				atrio.setCAP(spb.getCap());				
				atrio.setCFRAPPRESENTANTE(spb.getCfRappresentante());
				atrio.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());
				atrio.setCITTA(spb.getCitta());
				atrio.setCIVICO(spb.getCivico());				
				atrio.setCOGNOME(spb.getCognome());
				atrio.setDENOMINAZIONE(spb.getDenominazione());			
				atrio.setINDIRIZZO(spb.getIndirizzo());
				atrio.setNOME(spb.getNome());
				atrio.setPARTITAIVA(spb.getPartitaIva());
				atrio.setPROVINCIA(spb.getProvincia());
				
				/** adds 26092008 */
				if(spb.getId_stato() != null && ! Costanti.CODICE_STATO_ITALIANO.equals(spb.getId_stato())){
					atrio.setCODICESTATO(spb.getId_stato());
					//caso in cui la flag esteri � settata correttamente
					if(spb.getFlagEsteri() != null && !"".equals(spb.getFlagEsteri())){
						atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(spb.getFlagEsteri()));
					//caso in cui la flag esteri non risulta settata
					}else{
						atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_SI));
					}
					e = spb.getId_stato();
//					logger.debug("Stato estero: "+StatoEsteroType.Enum.forString(spb.getId_stato()));
//					logger.debug("Flag esteri: "+FlagSNType.Enum.forString(spb.getFlagEsteri()));
				}else{
//					logger.debug("[stato null || \"IT\"] - Stato estero: "+StatoEsteroType.Enum.forString(""));
//					logger.debug("[stato null || \"IT\"] - Flag esteri: "+FlagSNType.Enum.forString("N"));
					atrio.setCODICESTATO("");
					atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString("N"));
					e = "";
				}	
			}else{
				//XXX: sovrascrittura anagrafica, vedi javadoc metodo
				e = ToolXMLManager.sovrascriviAnagraficaPartecipante(schedaType.getAggiudicatari(), spb);
			}
			/**/
			/*setto cio che prima corrispondeva ad aggiudicatario*/
			SoggAggiudicatarioType sat = aggiudicazioneType.addNewAggiudicatari();
			/*gm adesso è gestito in ditte ausiliarie
			if(abrio.getCfAusiliaria() != null && !abrio.getCfAusiliaria().equals("")){
				sat.setCFAUSILIARIA(abrio.getCfAusiliaria());
			}
			*/
			sat.setCODICESTATO(e);
			sat.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());

			//gm adesso è gestito in ditte ausiliarie, PP si pero' è required!
			if(abrio.getFlagAvvalimento() != null && !"".equals(abrio.getFlagAvvalimento().trim()))
				sat.setFLAGAVVALIMENTO(FlagAvvalimentoType.Enum.forString(abrio.getFlagAvvalimento()));		
			else
				sat.setFLAGAVVALIMENTO(FlagAvvalimentoType.X_0);
			
			sat.setIDTIPOAGG(""+abrio.getIdTipoAgg());
			
			//MEV #654
			if(abrio.getImpAggiudicatario()!=null)
			    sat.setIMPORTOAGGIUDICAZIONE(abrio.getImpAggiudicatario());
			if(abrio.getPercAumentoAggiudicatario()!=null)
				sat.setPERCOFFAUMENTO(abrio.getPercAumentoAggiudicatario());
			if(abrio.getPercRibassoAggiudicatario()!=null)
				sat.setPERCRIBASSOAGG(abrio.getPercRibassoAggiudicatario());
			//Fine MEV #654
			
			if (abrio.getRuolo()!=null)
				sat.setRUOLO(abrio.getRuolo());
			if(abrio.getIdGruppo()!=0)
				sat.setIDGRUPPO((int)abrio.getIdGruppo());	
		}		
	}
	
	/*************************************************************************************************
	 * Imposta i tipi di appalto Forniture
	 * param Iterator : i2
	 * param AggiudicazioneType : at
	 */
	private void setTipiAppaltoForn(Iterator<TipoAppaltoAggBean> tipoFornitureIterator,AggiudicazioneType aggiudicazioneType){
		logger.debug("eseguendo: void setTipiAppaltoForn(Iterator<TipoAppaltoAggBean> i2,AggiudicazioneType at)");
		while(tipoFornitureIterator.hasNext()){
			TipoAppaltoAggBean taab = tipoFornitureIterator.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(taab.getClass(),taab ));
			TipiAppaltoType tat = aggiudicazioneType.addNewTipiAppaltoForn();
			tat.setIDAPPALTO(Long.toString(taab.getIdAppalto()));
			logger.debug("ID Appalto: "+Long.toString(taab.getIdAppalto()));
		}		
	}
	
	/**************************************************************************************************
	 * Imposta i tipi di appalto Lavori
	 * param Iterator : i2
	 * param AggiudicazioneType : at
	 */
	private void setTipiAppaltoLav(Iterator<TipoAppaltoAggBean> tipoLavoriIterator,AggiudicazioneType aggiudicazioneType){
		logger.debug("eseguendo: void setTipiAppaltoLav(Iterator<TipoAppaltoAggBean> i2,AggiudicazioneType at)");
		while(tipoLavoriIterator.hasNext()){
			TipoAppaltoAggBean taab = tipoLavoriIterator.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(taab.getClass(),taab ));
			TipiAppaltoType tat = aggiudicazioneType.addNewTipiAppaltoLav();
			tat.setIDAPPALTO(Long.toString(taab.getIdAppalto()));
			logger.debug("ID Appalto: "+Long.toString(taab.getIdAppalto()));
		}		
	}
	
	/***************************************************************************************************
	 * Imposta le condizioni
	 * param Iterator : i3
	 * param AggiudicazioenType: at
	 */
	private void setCondizioni(Iterator<CondizioneAggBean> i3,AggiudicazioneType aggiudicazioneType){	
		logger.debug("eseguendo: void setCondizioni(Iterator<CondizioneAggBean> i3,AggiudicazioneType at)");
		while(i3.hasNext()){
			CondizioneAggBean cab = i3.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(cab.getClass(),cab ));
			CondizioneType cat = aggiudicazioneType.addNewCondizioni();
			cat.setIDCONDIZIONE(""+cab.getIdCondizione());
		}		
	}
	/***************************************************************************************************
	 * Imposta i Requisiti
	 * param Iterator RequisitiBean : i4
	 * param AggiudicazioneType : at
	 */
	private void setRequisiti(Iterator<RequisitiBean> i4,AggiudicazioneType aggiudicazioneType){
		logger.debug("eseguendo: void setRequisiti(Iterator<RequisitiBean> i4,AggiudicazioneType at)");
		while(i4.hasNext()){
			RequisitiBean rb = i4.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(rb.getClass(),rb ));
			RequisitoType rt = aggiudicazioneType.addNewRequisiti();
			rt.setCLASSEIMPORTO(rb.getClasseImporto());
			rt.setIDCATEGORIA(rb.getIdCategoria());
			
			if(rb.getPrevalente()!=null && !"".equals(rb.getPrevalente()))
				rt.setPREVALENTE(FlagSNType.Enum.forString(rb.getPrevalente()));
			
			if(rb.getScorporabile()!=null && !"".equals(rb.getScorporabile()))
				rt.setSCORPORABILE(FlagSNType.Enum.forString(rb.getScorporabile()));
			
			if(rb.getSubAppaltabile()!=null && !"".equals(rb.getSubAppaltabile()))
				rt.setSUBAPPALTABILE(FlagSNType.Enum.forString(rb.getSubAppaltabile()));
		}
	}
	/***************************************************************************************
	 * Imposta i finanziamenti
	 * param Iterator TipoFinanziamentoBean : i5
	 * param AggiudicazioneType : at
	 */
	private void setFinanziamenti(Iterator<TipoFinanziamentoBean> i5,AggiudicazioneType aggiudicazioneType){
		logger.debug("eseguendo: void setFinanziamenti(Iterator<TipoFinanziamentoBean> i5,AggiudicazioneType at)");
		while(i5.hasNext()){
			TipoFinanziamentoBean tfb = i5.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(tfb.getClass(),tfb ));
			FinanziamentoType ft = aggiudicazioneType.addNewFinanziamenti();
			ft.setIDFINANZIAMENTO(tfb.getIdFinanziamento());
			ft.setIMPORTOFINANZIAMENTO(tfb.getImporto());
		}
	}
	/**********************************************************************************************
	 * Imposta gli incaricati 
	 * param Iterator ResponsabiliBean : i6
	 * param AggiudicazioneType : at
	 * param String : sezione
	 */
	private void setIncaricati(Iterator<ResponsabileBean> responsabiliIterator,AggiudicazioneType aggiudicazioneType, String sezione){
		logger.debug("eseguendo: void setIncaricati(Iterator<ResponsabileBean> i6,AggiudicazioneType at, String sezione)");
		while(responsabiliIterator.hasNext()){
			ResponsabileBean rb = responsabiliIterator.next();
			IncaricatoType it = aggiudicazioneType.addNewIncaricati();
			if(rb.getSoggettoPartecipante() != null)
				setIncaricatoGiuridico(rb, it, sezione);
			else
				setIncaricato(rb, it, sezione);
		}
	}
	
	private void setIncaricatoGiuridico(ResponsabileBean rb,IncaricatoType it, String sezione){
		SoggettoPartecipanteBean spb  = rb.getSoggettoPartecipante();
		it.setIDRUOLO(""+rb.getIdRuolo());
		it.setSEZIONE(SezioneType.Enum.forString(sezione));
		
		it.setPERSONAGIURIDICA(FlagSNType.S);
		if(rb.getCigProgEsterna() != null && !rb.getCigProgEsterna().equals("")){
			it.setCIGPROGESTERNA(rb.getCigProgEsterna());
		}
		it.setCODICEFISCALERESPONSABILE(spb.getCodiceFiscale());
		if(rb.getDataAffProgEsterna() != null){
			it.setDATAAFFPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataAffProgEsterna()));
		}
		if(rb.getDataConsProgEsterna() != null){
			it.setDATACONSPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataConsProgEsterna()));
		}
		String e;
		if(!ToolXMLManager.checkExist(schedaType.getAggiudicatari(), spb.getCodiceFiscale(), spb.getId_stato())){
			AggiudicatariType ait = schedaType.getAggiudicatari();
			AggiudicatarioType atrio;
			if(ait == null){
				atrio = schedaType.addNewAggiudicatari().addNewAggiudicatario();				
			}else{
				atrio = schedaType.getAggiudicatari().addNewAggiudicatario();
			}
			atrio.setCAMERACOMMERCIO(spb.getCameraCommercio());
			atrio.setCAP(spb.getCap());				
			atrio.setCFRAPPRESENTANTE(spb.getCfRappresentante());
			atrio.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());
			atrio.setCITTA(spb.getCitta());
			atrio.setCIVICO(spb.getCivico());				
			atrio.setCOGNOME(spb.getCognome());
			atrio.setDENOMINAZIONE(spb.getDenominazione());			
			atrio.setINDIRIZZO(spb.getIndirizzo());
			atrio.setNOME(spb.getNome());
			atrio.setPARTITAIVA(spb.getPartitaIva());
			atrio.setPROVINCIA(spb.getProvincia());
			/** adds 26092008 */
			if(spb.getId_stato() != null && ! Costanti.CODICE_STATO_ITALIANO.equals(spb.getId_stato())){
				atrio.setCODICESTATO(spb.getId_stato());
				//caso in cui la flag esteri � settata correttamente
				if(spb.getFlagEsteri() != null && !"".equals(spb.getFlagEsteri())){
					atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(spb.getFlagEsteri()));
				//caso in cui la flag esteri non risulta settata
				}else{
					atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_SI));
				}
				e = spb.getId_stato();
//				logger.debug("Stato estero: "+StatoEsteroType.Enum.forString(spb.getId_stato()));
//				logger.debug("Flag esteri: "+FlagSNType.Enum.forString(spb.getFlagEsteri()));
			}else{
//				logger.debug("[stato null || \"IT\"] - Stato estero: "+StatoEsteroType.Enum.forString(""));
//				logger.debug("[stato null || \"IT\"] - Flag esteri: "+FlagSNType.Enum.forString("N"));
				atrio.setCODICESTATO("");
				atrio.setSOGGETTOESTERO(FlagSNType.Enum.forString("N"));
				e = "";
			}	
		}else{
			//XXX: sovrascrittura anagrafica, vedi javadoc metodo
			e = ToolXMLManager.sovrascriviAnagraficaPartecipante(schedaType.getAggiudicatari(), spb);
		}
		it.setCODICESTATO(e);
		
		//TICKET ALM #10571 - 3.04.5
		if(rb.getIdGruppo()!=0) {
			it.setIDGRUPPOINCARICATO(new Long(rb.getIdGruppo()).intValue());
			it.setMANDANTE(rb.isMandante() ? FlagSNType.S : FlagSNType.N);
		}
		
	}
	private void setIncaricato(ResponsabileBean rb,IncaricatoType it, String sezione){
		SoggettoResponsabileBean srb = rb.getSoggettoResponsabile();
		it.setIDRUOLO(""+rb.getIdRuolo());
		it.setSEZIONE(SezioneType.Enum.forString(sezione));
		it.setPERSONAGIURIDICA(FlagSNType.N);
		if(rb.getCigProgEsterna() != null && !rb.getCigProgEsterna().equals("")){
			it.setCIGPROGESTERNA(rb.getCigProgEsterna());
		}
		it.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
		if(rb.getDataAffProgEsterna() != null){
			it.setDATAAFFPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataAffProgEsterna()));
		}
		if(rb.getDataConsProgEsterna() != null){
			it.setDATACONSPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataConsProgEsterna()));
		}			
		if(!ToolXMLManager.checkExist(schedaType.getResponsabili(), srb.getCodiceFiscaleResponsabile(),null)){
			ResponsabiliType rit = schedaType.getResponsabili();
			ResponsabileType rp;
			if(rit == null){
				rp = schedaType.addNewResponsabili().addNewResponsabile();
			}else{
				rp = schedaType.getResponsabili().addNewResponsabile();
			}				
			rp.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
			rp.setINDIRIZZO(srb.getIndirizzo());
			rp.setNOME(srb.getNome());
			rp.setTELEFONO(srb.getTelefono());
			rp.setEMAIL(srb.getEmail());
			rp.setFAX(srb.getFax());
			rp.setCODICEISTATCOMUNE(srb.getComuneIstat());
			rp.setCOGNOME(srb.getCognome());
			rp.setCAP(srb.getCap());
		}else{
			//XXX: sovrascrivo le anagrafiche
			ToolXMLManager.sovrascriviAnagraficaResponsabile(schedaType.getResponsabili(), srb);
		}
		
		//TICKET ALM #10571 - 3.04.5
				if(rb.getIdGruppo()!=0) {
					it.setIDGRUPPOINCARICATO(new Long(rb.getIdGruppo()).intValue());
					it.setMANDANTE(rb.isMandante() ? FlagSNType.S : FlagSNType.N);
				}
	}
	
	/************************************************************************
	 * Stampa le tipologie di appalto nel Log
	 * param List TipoAppaltoAggBean : l
	 */
	private void print(List<TipoAppaltoAggBean> l){
		for(TipoAppaltoAggBean tat : l){
			logger.debug(ObjectIntrospector.propertiesInfo(TipoAppaltoAggBean.class, tat));
		}
	}
	

}
