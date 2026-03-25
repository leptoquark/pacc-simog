package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.util.MyHelper;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.AccordiBonariType;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class AccordiBonariXMLManager {
	private AggiudicazioneBean aggiudicazioneBean = null;
	private Logger logger = null;
	private Connection connection = null;
	private SchedaCompletaType schedaCompletaType = null;
	private String schedaVer = null;
	
	public AccordiBonariXMLManager(Connection con,Logger logger,AggiudicazioneBean aggiudicazioneBean,SchedaCompletaType schedaCompletaType, String schedaVer){
		this.aggiudicazioneBean = aggiudicazioneBean;
		this.connection = con;
		this.logger = logger;
		this.schedaCompletaType = schedaCompletaType;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda AccordiBonarioType	-----		*/
	
	
	/**************************************************************************************
	 * Carica gli Accordi Bonari 
	 * @throws SimogWSException
	 */
	public void getAccordiBonari()throws SimogWSException{
		logger.debug("eseguendo: void getAccordiBonari()");
		try{
			AccordoManager accordoManager = new AccordoManager(connection,logger);
			List<AccordoBean> listOfAccordoBean = accordoManager.loadMany(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			if(!listOfAccordoBean.isEmpty()){
				//AccordiBonariType abt = scheda.addNewDatiAccordi();
				Iterator<AccordoBean> accordiBeanIterator = listOfAccordoBean.iterator();
				//this.setAccordiBonari(i, abt);
				this.setAccordiBonari(accordiBeanIterator);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Accordi Bonari\"");
			logger.error("errore :"+e.getMessage());
			//e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_06); 
		}
	}
	/***************************************************************************************
	 * Imposta gli accordi bonari
	 * param i Iterator 
	 * param abt AccordiBonariType
	 */
	//private void setAccordiBonari(Iterator<AccordoBean> i,AccordiBonariType abt){
	private void setAccordiBonari(Iterator<AccordoBean> accordiBonariIterator){
		logger.debug("eseguendo: void setAccordiBonari(Iterator<Object> i,AccordiBonariType abt)");
		AccordoBean accordoBean = null;
		AccordiBonariType accordiBonariType = null;
		while(accordiBonariIterator.hasNext()){
			accordoBean = accordiBonariIterator.next();
			//X-XX: VL - [accordi] solo se confermati
			if(accordoBean.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){
				//X-XX: VL - [accordi] creo il blocco solo se confermato
				if(accordiBonariType == null){accordiBonariType = schedaCompletaType.addNewDatiAccordi();}
				AccordoBonarioType accordoBonarioType = accordiBonariType.addNewAccordoBonario();
				accordoBonarioType.setDATAACCORDO(PageHelper.getCalendarFromStringDate(accordoBean.getDataAccordo()));
				accordoBonarioType.setNUMRISERVE(accordoBean.getNumeroRiserve());
				if(!MyHelper.isNull(accordoBean.getOneriDerivanti())){
					accordoBonarioType.setONERIDERIVANTI(accordoBean.getOneriDerivanti());
				}
		      if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		         accordoBonarioType.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(accordoBean.getIdStato()) ));
		      }
	          if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
	               if(accordoBean.getIdLocale() != null && !"".equals(accordoBean.getIdLocale()))
	                  accordoBonarioType.setIDSCHEDALOCALE(accordoBean.getIdLocale());
	               accordoBonarioType.setIDSCHEDASIMOG(String.valueOf(accordoBean.getIdAccordo()));           
	            }
			}
		}
	}
}
