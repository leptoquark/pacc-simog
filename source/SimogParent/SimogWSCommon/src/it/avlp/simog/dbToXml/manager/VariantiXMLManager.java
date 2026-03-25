package it.avlp.simog.dbToXml.manager;

import it.avlp.simog.beans.variante.MotivoRevPrezziBean;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.massload.xmlbeans.MotivoRevPrezziType;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.RecVarianteType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class VariantiXMLManager {
	private AggiudicazioneBean ab = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaCompletaType scheda = null;
	private String schedaVer = null;
	
	/*************************************************************************
	 * Costruttore
	 * @param con Connection 
	 * @param logger Logger 
	 * @param ab AggiudicazioneBean 
	 * @param scheda SchedaCompletaType 
	 */
	public VariantiXMLManager(Connection con,Logger logger,AggiudicazioneBean ab,SchedaCompletaType scheda, String schedaVer){
		this.ab = ab;
		this.con = con;
		this.logger = logger;
		this.scheda = scheda;
		this.schedaVer = schedaVer;
	}
	/*		-----	setting scheda VariantiType			-----		*/
	
	/**************************************************************************
	 * imposta i dati relativi alle varianti
	 * @throws SimogWSException
	 */
	public void getVarianti()throws SimogWSException{
		logger.debug("eseguendo: void getVarianti()");
		VarianteManager vm = new VarianteManager(con,logger);
		try{
			List<VarianteBean> vbl = vm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			//se il manager ha ritornato una lista non vuota crea la sezione Varianti
			if(!vbl.isEmpty()){
				//VariantiType vt = scheda.addNewDatiVarianti();
				Iterator<VarianteBean> i = vbl.iterator();
				//this.setVarianti(i, vt);
				this.setVarianti(i);
			}
		}catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati \"Varianti\"");
			logger.error("errore :"+e.getMessage());
			//e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_08); 
		}
	}
	/***************************************************************************
	 * Imposta i dati relativi alle varianti
	 * param Iterator VarianteBean: i
	 * param VariantiType : vt
	 */
	//private void setVarianti(Iterator<VarianteBean> i,VariantiType vt){
	private void setVarianti(Iterator<VarianteBean> i){
		logger.debug("eseguendo: void setVarianti(Iterator<VarianteBean> i,VariantiType vt)");
		VariantiType vt = null;
		while(i.hasNext()){
			VarianteBean vb = i.next();
			//X-XX: VL - [varianti] solo se confermati
			if(vb.getIdStato() == StatiScheda.CONFERMATO
			|| (SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0)){			      
				//X-XX: VL - [varianti] creo blocco solamente se almeno una confermata	
				if(vt == null){vt = scheda.addNewDatiVarianti();}
				VarianteType vat = vt.addNewVariante();
				RecVarianteType rvt = vat.addNewVariante();// questo quello da settare l'altro solo contenitore
				//setting stuff
				rvt.setDATAVERBAPPR(PageHelper.getCalendarFromStringDate(vb.getDataVerbaleApprovazione()));
				if(vb.getImpDisposizione() != null){rvt.setIMPDISPOSIZIONE(vb.getImpDisposizione());}
				if(vb.getImpProgettazione() != null){rvt.setIMPPROGETTAZIONE(vb.getImpProgettazione());}
				if(vb.getImpRidetFornit() != null){rvt.setIMPRIDETFORNIT(vb.getImpRidetFornit());}
				if(vb.getImpRidetLavori() != null){rvt.setIMPRIDETLAVORI(vb.getImpRidetLavori());}
				if(vb.getImpRidetServizi() != null){rvt.setIMPRIDETSERVIZI(vb.getImpRidetServizi());}
				if(vb.getImpSicurezza() != null){rvt.setIMPSICUREZZA(vb.getImpSicurezza());}
				if(vb.getAltreMotivazioni() != null){rvt.setALTREMOTIVAZIONI(vb.getAltreMotivazioni());}
				if(vb.getDataAttoAggiuntivo() != null){rvt.setDATAATTOAGGIUNTIVO(PageHelper.getCalendarFromStringDate(vb.getDataAttoAggiuntivo()));}
	
				
				//Ticket ALM #651
				if(vb.getUlterioriSomme() != null){rvt.setULTERIORISOMME(vb.getUlterioriSomme());}
				//
				
				//TICKET ALM - 3.04.3 PT
				if(SimogFlags.is3043Active() && schedaVer.compareTo(Costanti.VERS_304_3_0) >= 0) {
					if(vb.getCigProcedura()!=null && !"".equals(vb.getCigProcedura()))
						rvt.setCIGPROCEDURA(vb.getCigProcedura());
				}
				//FINE TICKET ALM - 3.04.3 PT
				
				//MEV 34191 3.04.8
				if (vb.getLinkVarianti()!=null && !"".equals(vb.getLinkVarianti())) {
					rvt.setLINKVARIANTI(vb.getLinkVarianti());
				}
				//FINE MEV 34191 3.04.8
				
				//MEV 34469 3.04.8
				if (vb.getIdMotivoRevPrezzi()!=null) {
					rvt.setIDMOTIVOREVPREZZI(MotivoRevPrezziType.Enum.forString(vb.getIdMotivoRevPrezzi()));
				}
				//FINE MEV 34469 3.04.8
				
				// PP 12.06.2014
				rvt.setNUMGIORNIPROROGA(vb.getNumGiorniProroga());
				
				// MEV 34469 - 3.04.8
				//if(vb.getIdMotivoRevPrezzi() != null){rvt.setIDMOTIVOREVPREZZI(vb.getIdMotivoRevPrezzi());}
				
				//end setting stuff
				Iterator<EventiMotiviVariantiBean> i1 = vb.getEmvb().iterator();
					//SETTING NESTED BEAN (EventiMotiviBean)
					this.setMotiviVarianti(i1,vat);
				
			    if( SimogFlags.is3028_RFWSGL01Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
			       rvt.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(vb.getIdStato()) ));
			    }
             if( SimogFlags.is3028_RFWEBSC00Active() && schedaVer.compareTo(Costanti.VERS_302_8_0) >= 0 ){
                if(vb.getIdLocale() != null && !"".equals(vb.getIdLocale()))
                   rvt.setIDSCHEDALOCALE(vb.getIdLocale());
                rvt.setIDSCHEDASIMOG(String.valueOf(vb.getIdVariante()));           
             }
			}
		}
	}
	/******************************************************************************
	 * Ottiene ed imposta i dati relativi ai Motivi Variante
	 * param Iterator EventiMotiviVariante : i
	 * param Variantetype : vat
	 */
	private void setMotiviVarianti(Iterator<EventiMotiviVariantiBean> i,VarianteType vat){
		logger.debug("eseguendo: void setMotiviVarianti(Iterator<EventiMotiviVariantiBean> i,VarianteType vat)");
		while(i.hasNext()){
			EventiMotiviVariantiBean emvb = i.next();
			RecMotivoVarType rmvt = vat.addNewMotivi();
			//trasf dati
			logger.debug("motivi varianti[id]: "+emvb.getIdMotivoVariante());
			rmvt.setIDMOTIVOVAR(""+emvb.getIdMotivoVariante());
			//end
		}
	}
}
