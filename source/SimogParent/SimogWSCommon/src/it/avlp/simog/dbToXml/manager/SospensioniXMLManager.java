package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SospensioniType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class SospensioniXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/***************************************************************************
	 * Costruttore
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletaType 
	 */
	public SospensioniXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda SospensioniType		-----		*/
	
	/********************************************************************
	 * imposta i dati delle sospensioni
	 * @throws SimogWSException
	 */
	public void getSospensioni()throws SimogWSException{
		logger.debug("eseguendo: void getSospensioni()");
		SospensioniManager sm = new SospensioniManager(con,logger);
		try{
			List<SospensioniBean> lsb = sm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(!lsb.isEmpty()){
				//SospensioniType st = scheda.addNewDatiSospensioni();
				Iterator<SospensioniBean> i = lsb.iterator();
				//this.setSospensioni(i, st);
				this.setSospensioni(i);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Sospensioni\"");
			logger.error("errore :"+e.getMessage());
		//	e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_07); 
		}
	}
	/***************************************************************
	 * Imposta i dati delle sospensioni
	 * param Iterator SospensioniBean : i
	 * param SospensioniType : st
	 */
	//private void setSospensioni(Iterator<SospensioniBean> i,SospensioniType st){
	private void setSospensioni(Iterator<SospensioniBean> i){
		logger.debug("eseguendo: void setSospensioni(Iterator<SospensioniBean> i,SospensioniType st)");
		SospensioniType st = null;
		while(i.hasNext()){
			SospensioniBean sb = i.next();
			//X-XX VL - [sospensioni] solo se confermati
			if(sb.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){			      
				//X-XX: VL - [sospensioni] creo blocco solo se una confermata
				if(st == null){st = scheda.addNewDatiSospensioni();}
				SospensioneType s1t = st.addNewSospensione();				
				if(sb.getDataVerbRipr() != null){s1t.setDATAVERBRIPR(PageHelper.getCalendarFromStringDate(sb.getDataVerbRipr()));}
				s1t.setDATAVERBSOSP(PageHelper.getCalendarFromStringDate(sb.getDataVerbSosp()));
				s1t.setFLAGRISERVE(FlagSNType.Enum.forString(sb.getFlagRiserve()));
				s1t.setFLAGSUPEROTEMPO(FlagSNType.Enum.forString(sb.getFlagSuperoTemp()));
				s1t.setFLAGVERBALE(FlagSNType.Enum.forString(sb.getFlagVerbale()));
				s1t.setIDMOTIVOSOSP(""+sb.getIdMotivoSosp());
				
		      if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		         s1t.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(sb.getIdStato()) ));
		      }
	           if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
                 if(sb.getIdLocale() != null && !"".equals(sb.getIdLocale()))
                    s1t.setIDSCHEDALOCALE(sb.getIdLocale());
                 s1t.setIDSCHEDASIMOG(String.valueOf(sb.getIdSospensione()));           
              }
			}
		}	
	}
}
