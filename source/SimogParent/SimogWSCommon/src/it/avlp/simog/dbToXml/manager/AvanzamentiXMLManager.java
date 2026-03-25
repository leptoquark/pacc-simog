package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.common.util.MyHelper;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.AvanzamentiType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.FlagPagamentoType;
import it.avlp.simog.massload.xmlbeans.FlagRitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class AvanzamentiXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/***************************************************
	 * Costruttore 
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean
	 * @param scheda SchedaCompletaType 
	 */
	public AvanzamentiXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda AvanzamentiType		-----		*/
	
	/*****************************************************
	 * Inserisce gli avanzamenti 
	 * @throws SimogWSException
	 */
	public void getAvanzamenti()throws SimogWSException{
		logger.debug("eseguendo: void getAvanzamenti()");
		try{
			AvanzamentoManager am = new AvanzamentoManager(con,logger);
			List<AvanzamentoBean> lab = am.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(!lab.isEmpty()){
				//AvanzamentiType at = scheda.addNewDatiAvanzamenti();
				Iterator<AvanzamentoBean> i = lab.iterator();
				//this.setAvanzamenti(i,at);
				this.setAvanzamenti(i);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Avanzamenti\"");
			logger.error("errore :"+e.getMessage());
			//e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_11); 
		}
	}
	/******************************************************
	 * Imposta gli avanzamenti
	 * param Iterator avanzamentoBean : i
	 * param AvanzamentiType : at
	 */
	//private void setAvanzamenti(Iterator<AvanzamentoBean> i,AvanzamentiType at){
	private void setAvanzamenti(Iterator<AvanzamentoBean> i){
		logger.debug("eseguendo: void setAvanzamenti(Iterator<Object> i,AvanzamentiType at)");
		AvanzamentoBean avb = null;
		AvanzamentiType at = null;
		while(i.hasNext()){
			avb = i.next();
			//X-XX: VL - [avanzamenti] solo se confermata
			if(avb.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){
				//X-XX: VL - [avanzamenti] creo il blocco avanzamenti solamente se c'e' almeno un avanzamento confermato
				if(at == null){at = scheda.addNewDatiAvanzamenti();}
				AvanzamentoType a1t = at.addNewAvanzamento();
				if(!MyHelper.isNull(avb.getDataAnticipazione())){
					a1t.setDATAANTICIPAZIONE(PageHelper.getCalendarFromStringDate(avb.getDataAnticipazione()));
				}
				if(!MyHelper.isNull(avb.getDataCertificato())){
					a1t.setDATACERTIFICATO(PageHelper.getCalendarFromStringDate(avb.getDataCertificato()));
				}
				a1t.setDATARAGGIUNGIMENTO(PageHelper.getCalendarFromStringDate(avb.getDataRaggiungimento()));
				a1t.setFLAGPAGAMENTO(FlagPagamentoType.Enum.forString(avb.getFlagPagamento()));
				a1t.setFLAGRITARDO(FlagRitardoType.Enum.forString(avb.getFlagRitardo()));
				if(!MyHelper.isNull(avb.getImportoAnticipazione())){
					a1t.setIMPORTOANTICIPAZIONE(avb.getImportoAnticipazione());
				}
				if(!MyHelper.isNull(avb.getImportoCertificato())){
					a1t.setIMPORTOCERTIFICATO(avb.getImportoCertificato());
				}
				a1t.setIMPORTOSAL(avb.getImportoSal());
				a1t.setNUMGIORNIPROROGA(avb.getNumeroGiorniProroga());
				a1t.setNUMGIORNISCOST(avb.getNumeroGiorniScost());
				if(avb.getDenomStatoAvanz() != null){
					a1t.setDENOMAVANZAMENTO(avb.getDenomStatoAvanz());
				}
				
		        if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		           a1t.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(avb.getIdStato()) ));
		        }
		        if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		           if(avb.getIdLocale() != null && !"".equals(avb.getIdLocale()))
		              a1t.setIDSCHEDALOCALE(avb.getIdLocale());
		           a1t.setIDSCHEDASIMOG(String.valueOf(avb.getIdAvanzamento()));           
		        }  
			}
		}
	}
}
