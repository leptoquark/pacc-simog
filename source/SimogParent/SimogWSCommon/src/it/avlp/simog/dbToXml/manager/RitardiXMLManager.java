package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.r129.R129Manager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.FlagTCType;
import it.avlp.simog.massload.xmlbeans.RitardiType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class RitardiXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/**
	 * Costruttore
	 * 
	 * @param con Connection	
	 * @param logger Logger
	 * @param ab AggiudicazioneBean
	 * @param scheda SchedaCompletaType
	 */
	public RitardiXMLManager (Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda RitardiType			-----		*/	
	/**
	 * l'invocazione di questo metodo permette l'esecuzione delle operazioni
	 * di settaggio del bean xml, e l'aggiunta al xmlbean passato in fase di costruzione
	 * 
	 * @throws SimogWSException
	 */
	public void getRitardi()throws SimogWSException{
		logger.debug("eseguendo: void getRitardi()");
		try{
			R129Manager rm = new R129Manager(con,logger);
			List<R129Bean> lrm = rm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(!lrm.isEmpty()){
				//RitardiType rt = scheda.addNewDatiRitardi();
				Iterator<R129Bean> i = lrm.iterator();
				//this.setRitardi(i,rt);
				this.setRitardi(i);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Ritardi\"");
			logger.error("errore :"+e.getMessage());
		//	e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_10);
		}
	}
	//private void setRitardi(Iterator<R129Bean> i,RitardiType rt){
	private void setRitardi(Iterator<R129Bean> i){
		logger.debug("eseguendo: void setRitardi(Iterator<Object> i,RitardiType rt)");
		RitardiType rt = null;
		while(i.hasNext()){
			R129Bean rb = i.next();
			//X-XX: VL - [ritardi] solo se confermato
			if(rb.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){			      
				//X-XX: VL - [ritardi] creo blocco solamente se ho almeno un ritardo confermato
				if(rt == null){rt = scheda.addNewDatiRitardi();}
				RitardoType r1t = rt.addNewRitardo();
				
				if(rb.getDataConsegna() != null && ! rb.getDataConsegna().trim().equals("")){
					r1t.setDATACONSEGNA(PageHelper.getCalendarFromStringDate(rb.getDataConsegna()));
				}
				if(rb.getDataIstRecesso() != null && ! rb.getDataIstRecesso().trim().equals("")){
					r1t.setDATAISTRECESSO(PageHelper.getCalendarFromStringDate(rb.getDataIstRecesso()));
				}
				
				if(rb.getDataTermine() != null && ! rb.getDataTermine().trim().equals("")){
				   r1t.setDATATERMINE(PageHelper.getCalendarFromStringDate(rb.getDataTermine()));
				}
				
				r1t.setDURATASOSP(rb.getDurataSospensione());
				
				if(rb.getFlagAccolta()!=null && !"".equals(rb.getFlagAccolta()))
					r1t.setFLAGACCOLTA(FlagSNType.Enum.forString(rb.getFlagAccolta()));
				
				r1t.setFLAGRIPRESA(FlagSNType.Enum.forString(rb.getFlagRipresa()));
				r1t.setFLAGRISERVA(FlagSNType.Enum.forString(rb.getFlagRiserva()));
				r1t.setFLAGTARDIVA(FlagSNType.Enum.forString(rb.getFlagTardiva()));
				if(rb.getImportoOneri() != null){r1t.setIMPORTOONERI(rb.getImportoOneri());}
				if(rb.getImportoSpese() != null){r1t.setIMPORTOSPESE(rb.getImportoSpese());}
				if(rb.getMotivoSospensione() != null){r1t.setMOTIVOSOSP(rb.getMotivoSospensione());}
				r1t.setTIPOCOMUN(FlagTCType.Enum.forString(rb.getTipoComunicazione()));
				
		      if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		         r1t.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(rb.getIdStato()) ));
		      }
		      if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		         if(rb.getIdLocale() != null && !"".equals(rb.getIdLocale()))
		            r1t.setIDSCHEDALOCALE(rb.getIdLocale());
		         r1t.setIDSCHEDASIMOG(String.valueOf(rb.getIdRecord()));           
		      }
			}
		}
	}
}
