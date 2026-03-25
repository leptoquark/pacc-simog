package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.FlagOneriType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;

import org.apache.log4j.Logger;
public class ConclusioneXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/*************************************************************
	 * Costruttore
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletaType 
	 */
	public ConclusioneXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	
	/*		-----	setting scheda ConclusioneType		-----		*/
	
	/***********************************************************************
	 * Ottiene i dati di conclusione 
	 * @throws SimogWSException
	 */
	public void getConclusione()throws SimogWSException{
		logger.debug("eseguendo: void getConclusione()");
		try{
			ConclusioniManager cm = new ConclusioniManager(con,logger);
			ConclusioneBean cb = cm.load(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			//controllo se il bean e' carico per dare seguito al settaggio..
			if(cb != null){
				if(cb.getIdStato() == StatiScheda.CONFERMATO
				|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){
					ConclusioneType ct = this.scheda.addNewDatiConclusione();
					this.setConclusione(cb,ct);
				}
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Conclusione\"");
			logger.error("errore :"+e.getMessage());
		//	e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_04); 
		}
	}
	/*************************************************************************
	 * Imposta i dati della conclusione
	 * param Comnclusione : cb
	 * param ConclusioneType :  ct
	 */
	private void setConclusione(ConclusioneBean cb,ConclusioneType ct){
		logger.debug("eseguendo: void setConclusione(Object o,ConclusioneType ct)");
		logger.debug(ObjectIntrospector.propertiesInfo(cb.getClass(),cb ));

		// PP 01.07.2009 il campo diventa opzionale
		if(cb.getDataUltimazione() != null)
			ct.setDATAULTIMAZIONE(PageHelper.getCalendarFromStringDate(cb.getDataUltimazione()));
		
		// PP opzionale
		if(cb.getFlagOneri() != null && !"".equals(cb.getFlagOneri()))
			ct.setFLAGONERI(FlagOneriType.Enum.forString(cb.getFlagOneri()));
		
		/*gm nuovi campi 3.0 dataConsegna, termineUltimazione, giorniProroga */
		// PP riattivati, non sapremo mai perchè erano commentati !
		if(cb.getDataConsegna() != null && !"".equals(cb.getDataConsegna()))
			ct.setDATAVERBCONSEGNAAVVIO(PageHelper.getCalendarFromStringDate(cb.getDataConsegna()));
		if(cb.getTermineUltimazione() != null && !"".equals(cb.getTermineUltimazione()))
			ct.setTERMINECONTRATTULTIMAZIONE(PageHelper.getCalendarFromStringDate(cb.getTermineUltimazione()));
		if(cb.getGiorniProroga() != null)
			ct.setNUMGIORNIPROROGA(cb.getGiorniProroga().intValue());
		
		if(cb.getFlagPolizza() != null)
			ct.setFLAGPOLIZZA(FlagSNType.Enum.forString(cb.getFlagPolizza()));
		
		if(cb.getNumInfMort() != null)
		   ct.setNUMINFMORT(cb.getNumInfMort().intValue());
		
      if(cb.getNumInfortuni() != null)
         ct.setNUMINFORTUNI(cb.getNumInfortuni().intValue());

      if(cb.getNumInfPerm() != null)
         ct.setNUMINFPERM(cb.getNumInfPerm().intValue());
		
		if(cb.getOneriRisoluzione() != null){
			ct.setONERIRISOLUZIONE(cb.getOneriRisoluzione()); 
		}
		if(cb.getDataRisoluzione() != null)
			ct.setDATARISOLUZIONE(PageHelper.getCalendarFromStringDate(cb.getDataRisoluzione()));
		if(cb.getMotiviInterruzione().longValue() != 0)
			ct.setIDMOTIVOINTERR(Long.toString(cb.getMotiviInterruzione()));
		if(cb.getMotiviRisoluzione().longValue() != 0)
			ct.setIDMOTIVORISOL(Long.toString(cb.getMotiviRisoluzione()));
		
        if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           ct.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(cb.getIdStato()) ));
        }		
        if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           if(cb.getIdLocale() != null && !"".equals(cb.getIdLocale()))
              ct.setIDSCHEDALOCALE(cb.getIdLocale());
           ct.setIDSCHEDASIMOG(String.valueOf(cb.getIdUltim()));           
        }  

	}
}
