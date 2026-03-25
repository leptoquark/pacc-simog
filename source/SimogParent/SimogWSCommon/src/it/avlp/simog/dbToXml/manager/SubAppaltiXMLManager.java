package it.avlp.simog.dbToXml.manager;

import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SoggSubappaltatoreType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.massload.xmlbeans.SubappaltiType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class SubAppaltiXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/***********************************************************************
	 * Costruttore
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletatype 
	 */
	public SubAppaltiXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda SubAppaltiType		-----		*/
	
	/***********************************************************************
	 * Recupera ed imposta i dati relativi ai subappalti
	 * @throws SimogWSException
	 */
	public void getSubAppalti()throws SimogWSException{
		logger.debug("eseguendo: void getSubAppalti()");
		SubappaltiManager sam = new SubappaltiManager(con,logger);
		try{
			List<SubappaltiBean> lsat = sam.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			if(!lsat.isEmpty()){
				//SubappaltiType st = scheda.addNewDatiSubappalti();
				Iterator<SubappaltiBean> i = lsat.iterator();
				//this.setSubAppalti(i, st);
				this.setSubAppalti(i);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"SubAppalti\"");
			logger.error("errore :"+e.getMessage());
		//	e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_09); 
		}
	}
	/***************************************************************
	 * Imposta i dati dei subappalti
	 * param Iterator SubappaltiBean : i
	 * param SubappaltiType : st
	 */
	//private void setSubAppalti(Iterator<SubappaltiBean> i,SubappaltiType st){
	private void setSubAppalti(Iterator<SubappaltiBean> i){
		logger.debug("eseguendo: void setSubAppalti(Iterator<SubappaltiBean> i,SubappaltiType st)");
		SubappaltiType st = null;
		while(i.hasNext()){
			SubappaltiBean sb = i.next();
			//X-XX: VL - [subappalti] solo se confermati
			if(sb.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){			      
				//X-XX: VL - [subappalti] creo il blocco solo se almeno una confermata
				if(st == null){ st = scheda.addNewDatiSubappalti();}
				SubappaltoType sat = st.addNewSubappalto();
				sat.setCFDITTA(sb.getCfDitta());
				sat.setFLAGDITTASUBESTERA(FlagSNType.Enum.forString(sb.getFlagDittaSubEstera()));//MEV 36771 3.04.8.1
				if(sb.getImportoEffettivo()!=null)
				   sat.setIMPORTOEFFETTIVO(sb.getImportoEffettivo());
				
				sat.setIMPORTOPRESUNTO(sb.getImportoPresunto());
				sat.setIDCATEGORIA(sb.getIdCategoria());
				sat.setIDCPV(sb.getIdCpv()); 

				/*gm nuovo codice 3.0 */
				if(sb.getCfAggiudicatario() != null){
					sat.setCODICEFISCALEAGGIUDICATARIO(sb.getCfAggiudicatario());
				}
				
				if(sb.getOggettoSubappalto() != null){sat.setOGGETTOSUBAPPALTO(sb.getOggettoSubappalto());}
				if(sb.getDataAutorizzazione() != null){sat.setDATAAUTORIZZAZIONE(PageHelper.getCalendarFromStringDate(sb.getDataAutorizzazione()));}
				
		        if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
		           sat.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(sb.getIdStato()) ));
		        }
	             if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
	                if(sb.getIdLocale() != null && !"".equals(sb.getIdLocale()))
	                   sat.setIDSCHEDALOCALE(sb.getIdLocale());
	                sat.setIDSCHEDASIMOG(String.valueOf(sb.getIdRecord()));           
	             }
	             
	             //TICKET ALM - 3.04.3 #4198
	             if(SimogFlags.is3043Active() & schedaVer.compareTo(Costanti.VERS_304_3_0) >= 0) {
	         
	            	 SubappaltiManager sm = new SubappaltiManager(con, logger);
	            	 List<SubappaltatoreBean> dittesub = new ArrayList<SubappaltatoreBean>();
	            	 try {
	            		 dittesub = sm.loadDitteSubappaltatrici(sb);
	            		 
		            	 for(SubappaltatoreBean subappaltatore : dittesub) {
		            		 SoggSubappaltatoreType sogg = sat.addNewSubappaltatore();
		            		 sogg.setCODICEFISCALESUBAPPALTATORE(subappaltatore.getSoggettoPartecipante().getCodiceFiscale());
		            		// sat.getSubappaltatoreArray()
		            	 }
		            	// sat.setSub
	            	 
	            	 } catch (SQLException e) {
	            		 // TODO Auto-generated catch block
	            		 e.printStackTrace();
	            	 }
	             }
		        
			}
		}
	}
}
