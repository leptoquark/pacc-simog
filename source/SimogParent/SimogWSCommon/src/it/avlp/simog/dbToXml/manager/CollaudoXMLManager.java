package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.util.MyHelper;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.FlagEsitoCollaudoType;
import it.avlp.simog.massload.xmlbeans.FlagModoCollaudoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
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

public class CollaudoXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaType schedaS = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/*****************************************************************
	 * Costruttore
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletaType 
	 * @param schedaS SchedaType 
	 */
	public CollaudoXMLManager (Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda,SchedaType schedaS, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaS = schedaS;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda CollaudoType			-----		*/
	
	/************************************************************************
	 * Preleva il collaudo e lo imposta 
	 * @throws SimogWSException
	 */
	public void getCollaudo()throws SimogWSException{
		logger.debug("eseguendo: void getCollaudo()");
		CollaudoManager cm = new CollaudoManager(con,logger);
		try{
			CollaudoBean cb = cm.load(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			//se non e' vuoto crea la sezione nel xml e ci carica i dati altrimenti skippa
			if(cb.getIdAggiudicazione() != null && cb.getDataIniAggiudicazione() != null){
				//X-XX: VL - [collaudo] solo se confermato
				if(cb.getIdStato() == StatiScheda.CONFERMATO
				|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){
					DatiCollaudoType ct = scheda.addNewDatiCollaudo();
					this.setCollaudo(cb,ct);
				}
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Collaudo\"");
			logger.error("errore :"+e.getMessage());
//			e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_05); 
		}
	}
	/************************************************************************
	 * Imposta i dati del collaudo
	 * param CollaudoBean : cb
	 * param DatiCollaudoType : ct
	 */
	private void setCollaudo(CollaudoBean cb,DatiCollaudoType ct){
		logger.debug("eseguendo: void setCollaudo(CollaudoBean cb,DatiCollaudoType ct)");
		//set -> get | xmlType -> bean
		CollaudoType cllt = ct.addNewCollaudo();
		logger.debug("Collaudo: "+ObjectIntrospector.propertiesInfo(CollaudoBean.class, cb));
		if(!MyHelper.isNull(cb.getDataIniOper()))cllt.setDATAINIZIOOPER(PageHelper.getCalendarFromStringDate(cb.getDataIniOper()));
		if(!MyHelper.isNull(cb.getDataNominaColl()))cllt.setDATANOMINACOLL(PageHelper.getCalendarFromStringDate(cb.getDataNominaColl()));		
		if(!MyHelper.isNull(cb.getDataCertCollaudo())){cllt.setDATACERTCOLLAUDO(PageHelper.getCalendarFromStringDate(cb.getDataCertCollaudo()));}
		if(!MyHelper.isNull(cb.getDataCollaudoStat())){cllt.setDATACOLLAUDOSTAT(PageHelper.getCalendarFromStringDate(cb.getDataCollaudoStat()));}
		if(!MyHelper.isNull(cb.getDataRegolareEsec())){cllt.setDATAREGOLAREESEC(PageHelper.getCalendarFromStringDate(cb.getDataRegolareEsec()));}
		cllt.setESITOCOLLAUDO(FlagEsitoCollaudoType.Enum.forString(cb.getEsitoCollaudo()));
		if(!MyHelper.isNull(cb.getDataDelibera())){cllt.setDATADELIBERA(PageHelper.getCalendarFromStringDate(cb.getDataDelibera()));}
		if(cb.getImpDisposizione() != null){cllt.setIMPDISPOSIZIONE(cb.getImpDisposizione());}				
		if(cb.getImpFinaleFornit() != null){cllt.setIMPFINALEFORNIT(cb.getImpFinaleFornit());}				
		if(cb.getImpFinaleLavori() != null) {cllt.setIMPFINALELAVORI(cb.getImpFinaleLavori());}			
		if(cb.getImpFinaleSicur() != null) {cllt.setIMPFINALESECUR(cb.getImpFinaleSicur());}		
		if(cb.getImpFinaleServizi() != null){cllt.setIMPFINALESERVIZI(cb.getImpFinaleServizi());}			
		if(cb.getImpProgettazione() != null){cllt.setIMPPROGETTAZIONE(cb.getImpProgettazione());}
		
		if(cb.getModoCollaudo() != null)
		   cllt.setMODOCOLLAUDO(FlagModoCollaudoType.Enum.forString(cb.getModoCollaudo()));
		
		if(cb.getTraImportoDef() != null){cllt.setTRAIMPORTODEF(cb.getTraImportoDef());}		
		if(cb.getTraImportoRich() != null){cllt.setTRAIMPORTORICH(cb.getTraImportoRich());}		
		cllt.setTRANUMDADEF(cb.getTraNumDaDef());
		cllt.setTRANUMDEFINITE(cb.getTraNumDefinite());
		if(cb.getAmmImportoDef() != null){cllt.setAMMIMPORTODEF(cb.getAmmImportoDef());}		
		if(cb.getAmmImportoRich() != null){cllt.setAMMIMPORTORICH(cb.getAmmImportoRich());}		
		cllt.setAMMNUMDADEF(cb.getAmmNumDaDef());
		cllt.setAMMNUMDEFINITE(cb.getAmmNumDefinite());
		if(cb.getArbImportoDef() != null){cllt.setARBIMPORTODEF(cb.getArbImportoDef());}		
		if(cb.getArbImportoRich() != null){cllt.setARBIMPORTORICH(cb.getArbImportoRich());}		
		cllt.setARBNUMDADEF(cb.getArbNumDaDef());
		cllt.setARBNUMDEFINITE(cb.getArbNumDefinite());
		if(cb.getGiuImportoDef() != null){cllt.setGIUIMPORTODEF(cb.getGiuImportoDef());}
		if(cb.getGiuImportORich() != null){cllt.setGIUIMPORTORICH(cb.getGiuImportORich());}		
		cllt.setGIUNUMDADEF(cb.getGiuNumDaDef());
		cllt.setGIUNUMDEFINITE(cb.getGiuNumDefinite());
		cllt.setLAVORIESTESI(FlagSNType.Enum.forString(cb.getFlagLavoriEstesi()));
		List<ResponsabileBean> lrb = cb.getRespBean();
		Iterator<ResponsabileBean> irb = lrb.iterator();
		this.setCollaudoIncaricati(irb,ct);
		
		if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		   cllt.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(cb.getIdStato()) ));
		}
      if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
         if(cb.getIdLocale() != null && !"".equals(cb.getIdLocale()))
            cllt.setIDSCHEDALOCALE(cb.getIdLocale());
         cllt.setIDSCHEDASIMOG(String.valueOf(cb.getIdCollaudo()));           
      }  

	}
	/*************************************************************************************
	 * imposta i dati degli incaricati del collaudo
	 * param Iterator ResponsabiliBean : irb
	 * param DatiCollaudoType : ct
	 */
	private void setCollaudoIncaricati(Iterator<ResponsabileBean> irb,DatiCollaudoType ct){
		logger.debug("eseguendo: void setCollaudoIncaricati(ResponsabileBean rb,DatiCollaudoType ct)");
		while(irb.hasNext()){
			ResponsabileBean rb = irb.next();
			logger.debug("Incaricato collaudo: "+ObjectIntrospector.propertiesInfo(ResponsabileBean.class, rb));
		IncaricatoType it = ct.addNewIncaricati();
		//set -> get | xmlType -> bean
			//nested bean
			SoggettoResponsabileBean srb = rb.getSoggettoResponsabile();
		/*
		it.setCAP(srb.getCap());
		if(rb.getCigProgEsterna() != null && !rb.getCigProgEsterna().equals("")){
			it.setCIGPROGESTERNA(rb.getCigProgEsterna());
		}
		it.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
		it.setCODICEISTATCOMUNE(srb.getComuneIstat());
		it.setCOGNOME(srb.getCognome());
		if(rb.getDataAffProgEsterna()!=null && !rb.getDataAffProgEsterna().equals("")){
			it.setDATAAFFPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataAffProgEsterna()));
		}
		if(rb.getDataConsProgEsterna()!=null && !rb.getDataConsProgEsterna().equals("")){
			it.setDATACONSPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataConsProgEsterna()));
		}
		it.setEMAIL(srb.getEmail());
		it.setFAX(srb.getFax());
		it.setIDRUOLO(RuoloResponsabileType.Enum.forInt(rb.getIdRuolo()));
		it.setINDIRIZZO(srb.getIndirizzo());
		it.setNOME(srb.getNome());
		it.setSEZIONE(SezioneType.Enum.forString(rb.getSezione()));
		it.setTELEFONO(srb.getTelefono());
		*/
			it.setIDRUOLO(""+rb.getIdRuolo());
			it.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
			it.setSEZIONE(SezioneType.Enum.forString(PSBD.SEZIONE_CO));
			if(rb.getCigProgEsterna() != null && !rb.getCigProgEsterna().equals("")){
				it.setCIGPROGESTERNA(rb.getCigProgEsterna());
			}			
			if(rb.getDataAffProgEsterna() != null){
				it.setDATAAFFPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataAffProgEsterna()));
			}
			if(rb.getDataConsProgEsterna() != null){
				it.setDATACONSPROGESTERNA(PageHelper.getCalendarFromStringDate(rb.getDataConsProgEsterna()));
			}
			if(!ToolXMLManager.checkExist(schedaS.getResponsabili(), srb.getCodiceFiscaleResponsabile(),null)){
				ResponsabileType rp = schedaS.getResponsabili().addNewResponsabile();
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
				//XXX: sovrascrivo anagrafiche
				ToolXMLManager.sovrascriviAnagraficaResponsabile(schedaS.getResponsabili(), srb);
			}
		}
	}
}
