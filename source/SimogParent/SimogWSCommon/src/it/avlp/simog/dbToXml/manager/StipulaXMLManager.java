package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.stipula.StipulaManager;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.DatiStipulaType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class StipulaXMLManager {
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
	public StipulaXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda,SchedaType schedaS, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaS = schedaS;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda DatiInizioType		-----		*/
	
	/*****************************************************************************
	 * Imposta i dati della scheda stipula
	 * @throws SimogWSException
	 */
	public void getStipula()throws SimogWSException{
		logger.debug("eseguendo: void getStipula()throws SimogWSException");
		try{
			StipulaManager stipulaManager = new StipulaManager(con,logger);
			StipulaBean stipulaBean = stipulaManager.load(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(stipulaBean.getIdStipula() != 0){
				//X-XX: VL - [fase iniziale] solo confermati
				if(stipulaBean.getIdStato() == StatiScheda.CONFERMATO
				|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){				      
					DatiStipulaType datiStipulaType = scheda.addNewDatiStipula();
					this.setStipula(stipulaBean,datiStipulaType);
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
	 * Imposta i dati della stipula 
	 * param sb
	 * param dstipula
	 */
	private void setStipula(StipulaBean stipulaBean,DatiStipulaType datiStipulaType){
		logger.debug("eseguendo: void setStipula(StipulaBean sb,DatiStipulaType dstipula)");
		logger.debug(ObjectIntrospector.propertiesInfo(StipulaBean.class, stipulaBean));
		StipulaType stipulaType = datiStipulaType.addNewStipula();
		
		if(stipulaBean.getDataDecorrenza()!=null)
			stipulaType.setDATADECORRRENZA(PageHelper.getCalendarFromStringDate(stipulaBean.getDataDecorrenza()));
		if(stipulaBean.getDataScadenza()!=null)
			stipulaType.setDATASCADENZA(PageHelper.getCalendarFromStringDate(stipulaBean.getDataScadenza()));
		if(stipulaBean.getDataStipulaContratto()!=null)
			stipulaType.setDATASTIPULA(PageHelper.getCalendarFromStringDate(stipulaBean.getDataStipulaContratto()));
		
        if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           stipulaType.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(stipulaBean.getIdStato()) ));
        }
        if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           if(stipulaBean.getIdLocale() != null && !"".equals(stipulaBean.getIdLocale()))
              stipulaType.setIDSCHEDALOCALE(stipulaBean.getIdLocale());
           stipulaType.setIDSCHEDASIMOG(String.valueOf(stipulaBean.getIdStipula()));           
        }  

		this.setPubblicazione(stipulaBean.getPubblicazione(), datiStipulaType);
	}
	/******************************************************************************
	 * Imposta i dati della pubblicazione 
	 * param Pubblicazione : pb
	 * param DataStipulaType : dstipula
	 */
	private void setPubblicazione(PubblicazioneBean pubblicazioneBean,DatiStipulaType datiStipulaType){
		logger.debug("eseguendo: void setPubblicazione(PubblicazioneBean pb,DatiStipulaType dstipula)");
		logger.debug(ObjectIntrospector.propertiesInfo(PubblicazioneBean.class, pubblicazioneBean));
		PubblicazioneType pubblicazioneType = datiStipulaType.addNewPubblicazioneEsito();
 		if(pubblicazioneBean.getDataAlbo() != null){
			pubblicazioneType.setDATAALBO(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataAlbo()));
		}
 		if(pubblicazioneBean.getDataGuce() != null){
			pubblicazioneType.setDATAGUCE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuce()));
		}
 		if(pubblicazioneBean.getDataGuri() != null){
			pubblicazioneType.setDATAGURI(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuri()));
		}
 		if(pubblicazioneBean.getQuotidianiNaz() != null){
 			pubblicazioneType.setQUOTIDIANINAZ(pubblicazioneBean.getQuotidianiNaz());
 		}
 	    if(pubblicazioneBean.getQuotidianiReg() != null){
    		pubblicazioneType.setQUOTIDIANIREG(pubblicazioneBean.getQuotidianiReg());
	    }
 	    pubblicazioneType.setPROFILOCOMMITTENTE(FlagSNType.Enum.forString(pubblicazioneBean.getProfiloCommitente()));
    	pubblicazioneType.setSITOMINISTEROINFTRASP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoMinisteroInfTrasp()));
		pubblicazioneType.setSITOOSSERVATORIOCP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoOsservatorioCP()));
	}
}
