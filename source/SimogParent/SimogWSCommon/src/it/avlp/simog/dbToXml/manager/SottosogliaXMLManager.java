package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.util.MyHelper;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.FlagAvvalimentoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.OrigineSchedaType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SezioneType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class SottosogliaXMLManager {
	
	private AggiudicazioneBean aggiudicazioneBean = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType schedaCompletaType = null;
	private SchedaType schedaType = null;
	private DatiAggiudicazioneType datiAggiudicazioneType = null;
	private String schede = null;
	
	public SottosogliaXMLManager(Connection con,Logger logger,AggiudicazioneBean aggiudicazioneBean,SchedaCompletaType schedaCompletaType,
			SchedaType schedaType,DatiAggiudicazioneType datiAggiudicazioneType, String schede){
		this.aggiudicazioneBean = aggiudicazioneBean;
		this.con = con;
		this.logger = logger;
		this.schedaCompletaType = schedaCompletaType;
		this.schedaType = schedaType;
		this.datiAggiudicazioneType = datiAggiudicazioneType;
		this.schede = schede;
	}
	/***************************************************************************************
	 * Carica l'aggiudicazione 
	 * @param tipoEnte String 
	 * @throws SimogWSException
	 */
	public void getSottosoglia(String tipoEnte) throws SimogWSException{
		try{
			AggiudicatarioManager aggiudicatarioManager = new AggiudicatarioManager(con, logger);
			CondizioniManager condizioniManager = new CondizioniManager(con,logger);
			ResponsabileManager responsabileManager = new ResponsabileManager(con,logger);
			AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(con,logger);
			aggiudicazioneBean = aggiudicazioniManager.getAggiudicazioni(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			//logger.debug(ObjectIntrospector.propertiesInfo(abb.getClass(),abb ));
			//AggiudicazioneType aggiudicazioneType = schedaCompletaType.addNewAggiudicazione();
			SchedaSottosogliaType sottosogliaType = schedaCompletaType.addNewSottosoglia();
			/* oggetti con cardinalita' singola	(aggiudicazioni)*/
				/*REQUIRED	appalto	*/
			this.setAppalto(aggiudicazioneBean, sottosogliaType);
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
						
			/* oggetti con cardinalita' multipla	(aggiudicazioni)*/
	 			/*	aggiudicatari [0,N]*/
			List<AggiudicatarioBean> listOfAggiudicatari = aggiudicatarioManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfAggiudicatari.isEmpty()){
				Iterator<AggiudicatarioBean> aggiudicatariIterator = listOfAggiudicatari.iterator();
				this.setAggiudicatari(aggiudicatariIterator, sottosogliaType);
			}
			
            /*OPTIONAL Condizioni	[0,N]*/
			List<CondizioneAggBean> listOfCondizioni = condizioniManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), false);
			if(!listOfCondizioni.isEmpty()){
				Iterator<CondizioneAggBean> condizioniIterator = listOfCondizioni.iterator();
				this.setCondizioni(condizioniIterator,sottosogliaType);
			}
			
			/*OPTIONAL	Incaricati responsabili	[0,N]*/
			List<ResponsabileBean> listOfIncaricati = responsabileManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), PSBD.SEZIONE_RS, false);
			if(!listOfIncaricati.isEmpty()){
				Iterator<ResponsabileBean> incaricatiIterator = listOfIncaricati.iterator();
				this.setIncaricati(incaricatiIterator, sottosogliaType,PSBD.SEZIONE_RS);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati gara");
			logger.error("errore :"+e.getMessage());
		//	e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_03);
		}
	}
	/*******************************************************************************
	 * Imposta i dati di Appalto
	 * param abb AggiudicazioneBean 
	 * param st SottosogliaType 
	 */
	private void setAppalto(AggiudicazioneBean aggiudicazioneBean,SchedaSottosogliaType sottosogliaType){
		logger.debug("eseguendo: void setAppalto(AggiudicazioneBean abb,SchedaSottosogliaType st)");
		//logger.debug(ObjectIntrospector.propertiesInfo(abb.getClass(), abb));
		SottoEsclusoType appalto = sottosogliaType.addNewAppalto();
		
		if(aggiudicazioneBean.getLuogoIstat() != null){
			appalto.setLUOGOISTAT(aggiudicazioneBean.getLuogoIstat());
		}
		if(aggiudicazioneBean.getLuogoNuts() != null){
			appalto.setLUOGONUTS(aggiudicazioneBean.getLuogoNuts());
		}
		if(aggiudicazioneBean.getCup()!=null){
	    	appalto.setCUP(aggiudicazioneBean.getCup());
		}
		appalto.setIMPORTOCOMPLESSIVO((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoComplessivo(), new BigDecimal(0)));
		appalto.setIMPORTODISPOSIZIONE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoDisposizione(), new BigDecimal(0)));
		if(aggiudicazioneBean.getIdSceltaContraente()!=0){
		    appalto.setIDSCELTACONTRAENTE(Long.toString(aggiudicazioneBean.getIdSceltaContraente()));
		}
		appalto.setASTAELETTRONICA(FlagSNType.Enum.forString(aggiudicazioneBean.getAstaElettronica()));
		if(aggiudicazioneBean.getPercOffAumento() != null){
			appalto.setPERCOFFAUMENTO(aggiudicazioneBean.getPercOffAumento());
		}
		else{
			appalto.setPERCOFFAUMENTO(new BigDecimal(0));
		}
		if(aggiudicazioneBean.getPercRibassoAgg() != null){
			appalto.setPERCRIBASSOAGG(aggiudicazioneBean.getPercRibassoAgg());
		}
		else{
			appalto.setPERCRIBASSOAGG(new BigDecimal(0));
		}
		appalto.setIMPORTOAGGIUDICAZIONE((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoAggiudicazione(), new BigDecimal(0)));
		appalto.setDATAAGGIUDICAZIONE(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataVerbaleAggiudicazione()));
        if(aggiudicazioneBean.getDataStipula()!=null){
        	appalto.setDATASTIPULA(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getDataStipula()));
        }
        if(aggiudicazioneBean.getTermineContrattuale()!=null){
        	appalto.setTERMINECONTRATTUALE(PageHelper.getCalendarFromStringDate(aggiudicazioneBean.getTermineContrattuale()));
        }
        if(aggiudicazioneBean.getDurataContrattuale()!=null){
        	appalto.setDURATACONTRATTUALE(aggiudicazioneBean.getDurataContrattuale());
        }    
		// PP 3.02.1.6
		// PP 3.02.1.6
        if(schede.compareTo(Costanti.VERS_302_1_6) >= 0)
        	appalto.setIMPORTOATTUAZIONESICUREZZA((BigDecimal)MyHelper.isNull(aggiudicazioneBean.getImportoAttuazioneSicurezza(), new BigDecimal(0)));
        
        if( SimogFlags.is3028_RFWSGL01Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           appalto.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(aggiudicazioneBean.getIdStato()) ));
        }
        
        if( SimogFlags.is3028_RFWEBSC00Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           appalto.setORIGINESCHEDA(OrigineSchedaType.Enum.forString( Long.toString(aggiudicazioneBean.getOrigine()) ));
           if(aggiudicazioneBean.getIdLocale() != null && !"".equals(aggiudicazioneBean.getIdLocale()))
              appalto.setIDSCHEDALOCALE(aggiudicazioneBean.getIdLocale());
           appalto.setIDSCHEDASIMOG(String.valueOf(aggiudicazioneBean.getIdAggiudicazione()));           
        }
	}
	/******************************************************************************************
	 * Imposta gli Aggiudicatari
	 * param Iterator : i1
	 * param st SottosogliaType 
	 */
	private void setAggiudicatari(Iterator<AggiudicatarioBean> aggiudicatariIterator,SchedaSottosogliaType sottosogliaType){
		logger.debug("eseguendo: void setAggiudicatari(Iterator<AggiudicatarioBean> i1,SottosogliaType st)");
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
			SoggAggiudicatarioType sat = sottosogliaType.addNewAggiudicatari();
			if(abrio.getCfAusiliaria() != null && !abrio.getCfAusiliaria().equals("")){
				sat.setCFAUSILIARIA(abrio.getCfAusiliaria());
			}
			sat.setCODICESTATO(e);
			sat.setCODICEFISCALEAGGIUDICATARIO(spb.getCodiceFiscale());
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
	
	/***************************************************************************************************
	 * Imposta le condizioni
	 * param Iterator : i3
	 * param SchedaSottosogliaType: st
	 */
	private void setCondizioni(Iterator<CondizioneAggBean> i3,SchedaSottosogliaType sottosogliaType){
		logger.debug("eseguendo: void setCondizioni(Iterator<CondizioneAggBean> i3,SchedaSottosogliaType st)");
		while(i3.hasNext()){
			CondizioneAggBean cab = i3.next();
			//logger.debug(ObjectIntrospector.propertiesInfo(cab.getClass(),cab ));
			CondizioneType cat = sottosogliaType.addNewCondizioni();
			cat.setIDCONDIZIONE(""+cab.getIdCondizione());
		}		
	}
	
	/**********************************************************************************************
	 * Imposta gli incaricati 
	 * param Iterator ResponsabiliBean : i6
	 * param SchedaSottosogliaType: st
	 * param String : sezione
	 */
	private void setIncaricati(Iterator<ResponsabileBean> responsabiliIterator,SchedaSottosogliaType sottosogliaType, String sezione){
		logger.debug("eseguendo: void setIncaricati(Iterator<ResponsabileBean> i6,SchedaSottosogliaType st, String sezione)");
		while(responsabiliIterator.hasNext()){
			logger.debug("*------------------responsabili----------------------*");
			ResponsabileBean rb = responsabiliIterator.next();
			logger.debug(ObjectIntrospector.propertiesInfo(rb.getClass(),rb ));
			SoggettoResponsabileBean srb = rb.getSoggettoResponsabile();
			logger.debug(ObjectIntrospector.propertiesInfo(srb.getClass(),srb ));
			logger.debug("*--------------------end responsabili---------------------*");
			IncaricatoType it = sottosogliaType.addNewIncaricati();
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
	}
}
