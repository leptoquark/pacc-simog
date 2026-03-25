package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.OrigineSchedaType;
import it.avlp.simog.massload.xmlbeans.PosizioneType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.SezioneType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class DatiInzioXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private SchedaType schedaS = null;
	private String schedaVer = null;
	
	/****************************************************************************
	 * Costruttore 
	 * @param con Connection 
	 * @param logger logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletaType 
	 * @param schedaS SchedaType 
	 */
	public DatiInzioXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda,SchedaType schedaS, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaS = schedaS;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda DatiInizioType		-----		*/
	
	/*****************************************************************************
	 * Imposta i dati della fase iniziale
	 * @throws SimogWSException
	 */
	public void getDatiInizio()throws SimogWSException{
		logger.debug("eseguendo: void getDatiInizio()throws SimogWSException");
		try{
			InizioLavoriManager inizioLavoriManager = new InizioLavoriManager(con,logger);
			InizioLavoriBean inizioLavoriBean = inizioLavoriManager.load(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(inizioLavoriBean.getIdInizioLavori() != 0){
				//X-XX: VL - [fase iniziale] solo confermati
				if(inizioLavoriBean.getIdStato() == StatiScheda.CONFERMATO
				|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){
					DatiInizioType datiInizioType = scheda.addNewDatiInizio();
					this.setInizio(inizioLavoriBean,datiInizioType);
					PosizAggiudManager posizioneAggiudicatarioManager = new PosizAggiudManager(con,logger);
					List<PosizioneAggiudicatarioBean> listOfPosizioni = posizioneAggiudicatarioManager.loadMany(inizioLavoriBean.getIdInizioLavori(), inizioLavoriBean.getDataInizioLavori(), false);
					if(!listOfPosizioni.isEmpty()){
						Iterator<PosizioneAggiudicatarioBean> posizioniIterator = listOfPosizioni.iterator();
						this.setPosizioni(posizioniIterator,datiInizioType);
					}
					ResponsabileInizioManager responsabileInizioManager = new ResponsabileInizioManager(con,logger);
					List<ResponsabileBean> listOfResponsabili = responsabileInizioManager.loadMany(inizioLavoriBean.getIdInizioLavori(), inizioLavoriBean.getDataInizioLavori(), false);
					if(!listOfResponsabili.isEmpty()){
						Iterator<ResponsabileBean> responsabiliIterator = listOfResponsabili.iterator();
						this.setIncaricati(responsabiliIterator,datiInizioType);
					}
					/*----*/
				}
			}
			
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati gara");
			logger.error("errore :"+e.getMessage());
			e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_02);
		}		
	}
	/*******************************************************
	 * Imposta la data di inizio 
	 * param ilb
	 * param dinizio
	 */
	private void setInizio(InizioLavoriBean inizioLavoriBean,DatiInizioType datiInizioType){
		logger.debug("eseguendo: void setInizio(InizioLavoriBean ilb,DatiInizioType dinizio)");
		logger.debug(ObjectIntrospector.propertiesInfo(InizioLavoriBean.class, inizioLavoriBean));
		InizioType inizioType = datiInizioType.addNewInizio();
		
		// PP 02.07.2009 il campo è facoltativo
		if(inizioLavoriBean.getDataEsecutivita()!=null)
			inizioType.setDATAESECUTIVITA(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataEsecutivita()));
		
		if(inizioLavoriBean.getDataStipula()!=null)
			inizioType.setDATASTIPULA(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataStipula()));

		// PP 16.07.2014 il campo è facoltativo
		if(inizioLavoriBean.getDataTermine()!=null)
		   inizioType.setDATATERMINE(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataTermine()));
		
		if(inizioLavoriBean.getDataVerbaleDef()!=null)
	      inizioType.setDATAVERBALEDEF(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataVerbaleDef()));
		   
		if(inizioLavoriBean.getDataVerbaleInizio()!=null)
			inizioType.setDATAVERBINIZIO(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataVerbaleInizio()));
		
		inizioType.setFLAGFRAZIONATA(FlagSNType.Enum.forString(inizioLavoriBean.getFlagFrazionata()));
		inizioType.setFLAGRISERVA(FlagSNType.Enum.forString(inizioLavoriBean.getFlagRiserva()));
		inizioType.setIMPORTOCAUZIONE(inizioLavoriBean.getImportoCauzione());
		
		if(inizioLavoriBean.getDataVerbaleCons() != null){inizioType.setDATAVERBALECONS(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataVerbaleCons()));}
		if(inizioLavoriBean.getDataIniProgEsec() != null){inizioType.setDATAINIPROGESEC(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataIniProgEsec()));}
		if(inizioLavoriBean.getDataAppProgEsec() != null){inizioType.setDATAAPPPROGESEC(PageHelper.getCalendarFromStringDate(inizioLavoriBean.getDataAppProgEsec()));}
		
		this.setPubblicazione(inizioLavoriBean.getPubblicazione(), datiInizioType);
		
      if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
         inizioType.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(inizioLavoriBean.getIdStato()) ));
      }
      if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
         if(inizioLavoriBean.getIdLocale() != null && !"".equals(inizioLavoriBean.getIdLocale()))
            inizioType.setIDSCHEDALOCALE(inizioLavoriBean.getIdLocale());
         inizioType.setIDSCHEDASIMOG(String.valueOf(inizioLavoriBean.getIdInizioLavori()));           
      }  
	}
	/******************************************************************************
	 * Imposta i dati della pubblicazione 
	 * param Pubblicazione : pb
	 * param DataInizioType : dinizio
	 */
	private void setPubblicazione(PubblicazioneBean pubblicazioneBean,DatiInizioType datiInizioType){
		logger.debug("eseguendo: void setPubblicazione(PubblicazioneBean pb,DatiInizioType dinizio)");
		logger.debug(ObjectIntrospector.propertiesInfo(PubblicazioneBean.class, pubblicazioneBean));
		PubblicazioneType pubblicazioneType = datiInizioType.addNewPubblicazioneEsito();
// 		if(pubblicazioneBean.getDataAlbo() != null){
//			pubblicazioneType.setDATAALBO(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataAlbo()));
//		}if(pubblicazioneBean.getDataGuce() != null){
//			pubblicazioneType.setDATAGUCE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuce()));
//		}if(pubblicazioneBean.getDataGuri() != null){
//			pubblicazioneType.setDATAGURI(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuri()));
//		}
//		pubblicazioneType.setPROFILOCOMMITTENTE(FlagSNType.Enum.forString(pubblicazioneBean.getProfiloCommitente()));
//		pubblicazioneType.setQUOTIDIANINAZ(pubblicazioneBean.getQuotidianiNaz());
//		pubblicazioneType.setQUOTIDIANIREG(pubblicazioneBean.getQuotidianiReg());
//		pubblicazioneType.setSITOMINISTEROINFTRASP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoMinisteroInfTrasp()));
//		pubblicazioneType.setSITOOSSERVATORIOCP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoOsservatorioCP()));
		
		if(pubblicazioneBean.getDataAlbo() != null)
			pubblicazioneType.setDATAALBO(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataAlbo()));
		
		if(pubblicazioneBean.getDataGuce() != null)
			pubblicazioneType.setDATAGUCE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuce()));
		
		if(pubblicazioneBean.getDataGuri() != null)
			pubblicazioneType.setDATAGURI(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuri()));
		
		if(pubblicazioneBean.getProfiloCommitente() != null)
		pubblicazioneType.setPROFILOCOMMITTENTE(FlagSNType.Enum.forString(pubblicazioneBean.getProfiloCommitente()));
		
		if(pubblicazioneBean.getQuotidianiNaz() != null)
		pubblicazioneType.setQUOTIDIANINAZ(pubblicazioneBean.getQuotidianiNaz());
		
		if(pubblicazioneBean.getQuotidianiReg() != null)
		pubblicazioneType.setQUOTIDIANIREG(pubblicazioneBean.getQuotidianiReg());

		if(pubblicazioneBean.getSitoMinisteroInfTrasp() != null)
		pubblicazioneType.setSITOMINISTEROINFTRASP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoMinisteroInfTrasp()));
		
		if(pubblicazioneBean.getSitoOsservatorioCP() != null)		
		pubblicazioneType.setSITOOSSERVATORIOCP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoOsservatorioCP()));

		if(pubblicazioneBean.getDataBore()!=null)
			pubblicazioneType.setDATABORE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataBore()));
		
		if(pubblicazioneBean.getPeriodici()!=null)
			pubblicazioneType.setPERIODICI(pubblicazioneBean.getPeriodici());
	}
	/*****************************************************************************
	 * Imposta i dati di posizione
	 * param Iterator PosizioneAggiudicatarioBean : i
	 * param DatiIniziotype : dinizio
	 */
	private void setPosizioni(Iterator<PosizioneAggiudicatarioBean> posizioniIterator,DatiInizioType datiInizioType){
		logger.debug("eseguendo: void setPosizioni(Iterator<PosizioneAggiudicatario> i,DatiInizioType dinizio)");	
		while(posizioniIterator.hasNext()){
			PosizioneType posizioneType = datiInizioType.addNewPosizioni();
			PosizioneAggiudicatarioBean posizioneBean = posizioniIterator.next();
			if(posizioneBean.getCodiceCassa() != null){
				posizioneType.setCODICECASSA(posizioneBean.getCodiceCassa());
			}
			if(posizioneBean.getCodiceINAIL() != null){
				posizioneType.setCODICEINAIL(posizioneBean.getCodiceINAIL());
			}
			if(posizioneBean.getCodiceINPS() != null){
				posizioneType.setCODICEINPS(posizioneBean.getCodiceINPS());
			}
			// aggiunto codice fiscale dell'aggiudicatario
			posizioneType.setCODICEFISCALEAGGIUDICATARIO(posizioneBean.getSoggettoPartecipante().getCodiceFiscale());
			/** adds 26092008 */
			posizioneType.setCODICESTATO(posizioneBean.getSoggettoPartecipante().getId_stato());
		}
	}
	/*********************************************************************************+
	 * Imposta i dati degli incaricati
	 * param Iterator ResponsabileBean : i
	 * param DatiInizioType : dinizio
	 */
	private void setIncaricati(Iterator<ResponsabileBean> responsabiliIterator,DatiInizioType datiInizioType){
		logger.debug("eseguendo: void setIncaricati(Iterator<ResponsabileInizioBean> i,DatiInizioType dinizio)");
		while(responsabiliIterator.hasNext()){
			IncaricatoType incaricatoType = datiInizioType.addNewIncaricati();
			ResponsabileBean responsabileInizioBean = responsabiliIterator.next();
			logger.debug(ObjectIntrospector.propertiesInfo(ResponsabileBean.class, responsabileInizioBean));
			SoggettoResponsabileBean soggettoResponsabile = responsabileInizioBean.getSoggettoResponsabile();
			incaricatoType.setCODICEFISCALERESPONSABILE(soggettoResponsabile.getCodiceFiscaleResponsabile());
			incaricatoType.setIDRUOLO(""+responsabileInizioBean.getIdRuolo());
			//it.setSEZIONE(SezioneType.Enum.forString(rib.getSezione()));
			incaricatoType.setSEZIONE(SezioneType.Enum.forString(PSBD.SEZIONE_IN));
			if(!ToolXMLManager.checkExist(schedaS.getResponsabili(), soggettoResponsabile.getCodiceFiscaleResponsabile(),null)){
				if(schedaS.getResponsabili() == null){schedaS.addNewResponsabili();}
				ResponsabileType responsabileType = schedaS.getResponsabili().addNewResponsabile();
				responsabileType.setCODICEFISCALERESPONSABILE(soggettoResponsabile.getCodiceFiscaleResponsabile());
				responsabileType.setINDIRIZZO(soggettoResponsabile.getIndirizzo());
				responsabileType.setNOME(soggettoResponsabile.getNome());
				responsabileType.setTELEFONO(soggettoResponsabile.getTelefono());
				responsabileType.setEMAIL(soggettoResponsabile.getEmail());
				responsabileType.setFAX(soggettoResponsabile.getFax());
				responsabileType.setCODICEISTATCOMUNE(soggettoResponsabile.getComuneIstat());
				responsabileType.setCOGNOME(soggettoResponsabile.getCognome());
				responsabileType.setCAP(soggettoResponsabile.getCap());
			}else{
				//sovrascrivo anagrafica
				ToolXMLManager.sovrascriviAnagraficaResponsabile(schedaS.getResponsabili(), soggettoResponsabile);
			}
		}
	}
}
