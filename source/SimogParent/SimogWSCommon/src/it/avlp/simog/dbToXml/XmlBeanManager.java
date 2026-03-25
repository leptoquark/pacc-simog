package it.avlp.simog.dbToXml;
import it.avcp.avcpass.AVCPassAction;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.beans.RichiestaCUP;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.dbToXml.manager.AccordiBonariXMLManager;
import it.avlp.simog.dbToXml.manager.AdesioneXMLManager;
import it.avlp.simog.dbToXml.manager.AggiudicazioniXMLManager;
import it.avlp.simog.dbToXml.manager.AvanzamentiXMLManager;
import it.avlp.simog.dbToXml.manager.CollaudoXMLManager;
import it.avlp.simog.dbToXml.manager.ConclusioneXMLManager;
import it.avlp.simog.dbToXml.manager.DatiInzioXMLManager;
import it.avlp.simog.dbToXml.manager.EsclusoXMLManager;
import it.avlp.simog.dbToXml.manager.RitardiXMLManager;
import it.avlp.simog.dbToXml.manager.SospensioniXMLManager;
import it.avlp.simog.dbToXml.manager.SottosogliaXMLManager;
import it.avlp.simog.dbToXml.manager.StipulaXMLManager;
import it.avlp.simog.dbToXml.manager.SubAppaltiXMLManager;
import it.avlp.simog.dbToXml.manager.ToolXMLManager;
import it.avlp.simog.dbToXml.manager.VariantiXMLManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.garamanager.lotto.RichiesteCUPManager;
import it.avlp.simog.massload.xmlbeans.CPVSecondariaType;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.CondizioneLottoType;
import it.avlp.simog.massload.xmlbeans.CondizioneLtType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.DatiGaraType;
import it.avlp.simog.massload.xmlbeans.DerogaQualificazioneSA;
import it.avlp.simog.massload.xmlbeans.ElencoCategMercType;
import it.avlp.simog.massload.xmlbeans.EsitoProceduraType;
import it.avlp.simog.massload.xmlbeans.FlagSNQType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.FlagSOType;
import it.avlp.simog.massload.xmlbeans.GaraType;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.MisuraPremialeType;
import it.avlp.simog.massload.xmlbeans.MotivoDerogaType;
import it.avlp.simog.massload.xmlbeans.OrigineSchedaType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.ReqDocType;
import it.avlp.simog.massload.xmlbeans.ReqGaraType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaDocument;
import it.avlp.simog.massload.xmlbeans.SchedaType;
import it.avlp.simog.massload.xmlbeans.StatoSchedaType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.massload.xmlbeans.TipoSchedaType;
import it.avlp.simog.massload.xmlbeans.impl.FlagSNQTypeImpl;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.DummyValidator;
import it.avlp.simog.ws.commons.ConfigurationManager;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.axis.attachments.MimeMultipartDataSource;
import org.apache.log4j.Logger;

public class XmlBeanManager implements DbToXml {

	private String cig = null;
	/**X-XX: espansioni future, &egrave; il parametro richiesto da consulta gara qualora si necessiti di stringhe per la 
	 * 	consultazione di parti di gara o di schede sopra gara **/
	private String schede = null;
	private Logger logger = null;
	private Connection con = null;
	private SchedaDocument contenitoreSchede = null;
	private SchedaType schedaS = null;
	private SchedaCompletaType scheda = null;
	//serve per passare i dati necessari al caricamento di altre parti (cambia con l'iterazione su aggiudicazioni)
	private AggiudicazioneBean aggiudicazioneBean = null; 
	private Lotto lotto = null;
	private Gara gara = null;
	
	private boolean allData = true;
	//private Agg
	
   protected boolean isFlag(String val){
      return val != null && (Costanti.FLAG_VALORE_SI.equals(val.trim()) || Costanti.FLAG_VALORE_NO.equals(val.trim()));
   }
	  
	/******************************************************
	 * Costruttore 
	 * @param con Connection 
	 * @param logger Logger
	 * @param cig String
	 * @param schede String
	 * @throws SimogWSException
	 */
	public XmlBeanManager(Connection con,Logger logger,String cig,String schede) throws SimogWSException{
		super();
		logger.debug("istanziando XmlBeanManager(Connection con,Logger logger,String cig,String schede)");
		this.cig = cig;
		this.schede = schede;
		this.logger = logger;
		this.con = con;
      //logger.debug("prima istanziando SchedaDocument");
		contenitoreSchede = SchedaDocument.Factory.newInstance();
      //logger.debug("dopo istanziando SchedaDocument");
	}
	
	/********************************************************
	 * Restituisce True se la tabella risulta piena, 
	 * False altrimenti
	 * @param con Connection
	 * @param logger Logger
	 * @param adminOr String
	 * @param cig String
	 * @return boolean
	 * @throws SimogWSException
	 */
	public static boolean verify(Connection con,Logger logger, String adminOr, String cig)throws SimogWSException{
		GaraManager garaManager = new GaraManager(con,logger);
		try{
			TableBean tableBeanGara = garaManager.getGaraList(null, null, 
			      cig.length()==10 ? cig : null, 
			      cig.length()!=10 ? cig : null, 
			      new Hashtable(), null, null, null, null,0,0, true, null,adminOr, null, null, null, null, null,null);
			if(tableBeanGara.getFullSize() == 0){ 
				logger.debug("table bean empty for: "+adminOr);
				return false;
			}
		}catch(SQLException sqle){
			logger.error("errore nel recupero dei dati necessari: "+sqle.getMessage());
			throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_27 + ": "+sqle.getMessage());
		}
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see it.avlp.simog.dbToXml.DbToXml#getXmlString()
	 */
	public String getXmlString() throws Exception {
		logger.debug("eseguendo: String getXmlString()");
		if(contenitoreSchede != null){
			return contenitoreSchede.toString();
		}
		this.populate();
		return contenitoreSchede.toString();
	}	
	
	/* (non-Javadoc)
	 * @see it.avlp.simog.dbToXml.DbToXml#getXmlBean()
	 */
	public SchedaDocument getXmlBean() throws Exception {
		logger.debug("eseguendo: SchedaDocument getXmlBean()");
		//
		this.populate();
		return contenitoreSchede;
	}
	
	/*******************************************************
	 * Carica i dati della Gara e della Scheda
	 * throws Exception
	 */
	private void populate() throws Exception {
		logger.debug("eseguendo: void populate()");
		//invoca i metodi per caricare le schede richieste
		this.getDatiGara();
		//SETTA LA SCHEDA A SOLAMENTE SE CI SONO I DATI(..)
		if(this.lotto!=null && allData)
    		this.getSchedaA();
	}

	/*		-----	setting scheda DatiGaraType			-----		*/
	
	/********************************************************
	 * Ottiene e carica i dati della Gara
	 * throws SimogWSException
	 */
	private void getDatiGara()throws SimogWSException{
		//che sono garatype,lottotype
		try{
			logger.debug("eseguendo: void getDatiGara()");
			/* MANAGERS */
			LottoManager lottoManager = new LottoManager(con,logger);
			GaraManager garaManager = new GaraManager(con,logger);
            RequisitiGLManager rqMan = new RequisitiGLManager(con,logger);
            
			//XXX: PP prendo fisso il primo elemento, quello piu' recente (0_o)    
			//logger.debug("full-cig: "+cig);
			
			//gm nuovo codice per gestire anche gare senza lotti
			List<Lotto> listOfLotti = null;
			if(cig.length()==10)
			    listOfLotti = lottoManager.getLottoByCigWS(cig);	
			
			Gara gara = null;
			Lotto lotto = null;
			LottoType lt = null;
			List<RequisitoGara> reql = new ArrayList<RequisitoGara>();
			
			if(listOfLotti==null || listOfLotti.isEmpty()){
				//logger.fatal("cig non valido ha ritornato una lista vuota");
				//throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_01);
				try {
					if(Long.parseLong(cig) > 0 && cig.length() < 10)
						gara = garaManager.getGara(Long.parseLong(cig));
				} catch (NumberFormatException e) {}   
				
				if(gara == null)
					throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_30);
			}
			else{
			    lotto = listOfLotti.get(0);		
			    gara = garaManager.getGara(lotto.getId_Gara());
			    
			}			    
			    
			SchedaType scheda = contenitoreSchede.addNewScheda();
            schedaS = scheda;
            DatiGaraType dgt = scheda.addNewDatiGara();
            if(lotto != null){
            	lt = dgt.addNewLotto();
            }
            GaraType gt = dgt.addNewGara();
            this.setDatiGara(gt,lt,lotto,gara);
            //logger.debug("prima dei requisiti");
            // aggiungo i requisiti
            if(ConfigurationManager.getInstance().getSimogProperties().getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){
               RequisitiGLAction requisitiGLAction = new RequisitiGLAction(con, logger);
               reql = requisitiGLAction.getRequisitoGaraList(gara.getId_Gara());
               
               if(reql.size()>0)
                  setRequisiti(dgt, reql);
            }
            
		}catch(SimogWSException swe){
         logger.debug("errore incorso durante il caricamento dei dati gara");
         logger.fatal("errore :"+swe.getMessage());
			throw new SimogWSException(swe.getCode());
		}
		catch(Exception e){
			logger.debug("errore incorso durante il caricamento dei dati gara");
			logger.fatal("errore :"+e.getMessage());
			//e.printStackTrace();			
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_02);
		}		
	}
	
	/**********************************************************************
	 * Imposta i dati della Gara e del Lotto
	 * param gt GaraType 
	 * param lt LottoType 
	 * param l Lotto 
	 * param g Gara 
	 */
	private void setDatiGara(GaraType garaType,LottoType lottoType,Lotto lotto,Gara gara){
		logger.debug("eseguendo: void setDatiGara(GaraType gt,LottoType lt,Lotto l,Gara g)");
		this.gara = gara;
		//gm nuovo codice per gestire gare senza lotti
		this.setGara(garaType);
		if(lotto!=null)
		    this.lotto = lotto;
		if(lottoType!=null)
	    	this.setLotto(lottoType);
	}
	
	/**********************************************************************
	 * Imposta la gara
	 * param gt GaraType
	 */
	private void setGara(GaraType garaType){
		logger.debug("eseguendo: void setGara(GaraType gt)");
		//logger.debug(ObjectIntrospector.propertiesInfo(g.getClass(), g));
		garaType.setCFAMMINISTRAZIONE(gara.getCF_AMMINISTRAZIONE());
		garaType.setDENOMAMMINISTRAZIONE(gara.getDENOM_AMMINISTRAZIONE());
		garaType.setDENOMSTAZIONEAPPALTANTE(gara.getDENOM_STAZIONE_APPALTANTE());
		garaType.setIDSTAZIONEAPPALTANTE(gara.getID_STAZIONE_APPALTANTE());
		garaType.setOGGETTO(gara.getOggetto());
		garaType.setCFUTENTE(gara.getCF_UTENTE());
		//X-XX: VL - supporto id_gara OK
		garaType.setIDGARA(gara.getId_Gara());
		//X-XX: VL - [18112008] - nuovi campi per nuova normativa

		if(gara.getDATA_COMUN() != null && !gara.getDATA_COMUN().equals("")){
			garaType.setDATACOMUN(PageHelper.getCalendarFromStringDate(gara.getDATA_COMUN()));
	   }
	
		if(gara.getIMPORTO_GARA() != null){
			garaType.setIMPORTOGARA(gara.getIMPORTO_GARA());
		}
		else
			garaType.setIMPORTOGARA(new BigDecimal(-1)); // PP importo non determinabile se nullo

		if(gara.getIMPORTO_SA_GARA()!= null){
			garaType.setIMPORTOSAGARA(gara.getIMPORTO_SA_GARA());
		}

		if(gara.getDATA_INIB_PAGAM() != null && !gara.getDATA_INIB_PAGAM().equals("")){
			garaType.setDATAINIBPAGAM(PageHelper.getCalendarFromStringDate(gara.getDATA_INIB_PAGAM()));
		}

		if(gara.getDATA_TERMINE_PAGAMENTO() != null && !gara.getDATA_TERMINE_PAGAMENTO().equals("")){
			garaType.setDATATERMINEPAGAMENTO(PageHelper.getCalendarFromStringDate(gara.getDATA_TERMINE_PAGAMENTO()));
		}

		if(gara.getDATA_CANCELLAZIONE_GARA() != null && !gara.getDATA_CANCELLAZIONE_GARA().equals("")){
			garaType.setDATACANCELLAZIONEGARA(PageHelper.getCalendarFromStringDate(gara.getDATA_CANCELLAZIONE_GARA()));
		}

		if(gara.getDATA_CONFERMA_GARA() != null && !gara.getDATA_CONFERMA_GARA().equals("")){
			garaType.setDATACONFERMAGARA(PageHelper.getCalendarFromStringDate(gara.getDATA_CONFERMA_GARA()));
		}
	
		
		if(gara.getTIPO_SCHEDA_GARA() != null && !gara.getTIPO_SCHEDA_GARA().equals("")){
			garaType.setTIPOSCHEDA(FlagSOType.Enum.forString(gara.getTIPO_SCHEDA_GARA()));
		}
		
		if(gara.getID_MODO_GARA() != 0){
			garaType.setMODOINDIZIONE(String.valueOf(gara.getID_MODO_GARA()));
		}		
		if(gara.getID_MODO_REAL() != 0){
			garaType.setMODOREALIZZAZIONE(String.valueOf(gara.getID_MODO_REAL()));
		}
		
		if(gara.getID_MOTIVAZIONE_CANC() != 0){
			garaType.setIDMOTIVAZIONE(String.valueOf(gara.getID_MOTIVAZIONE_CANC()));
		}
		
		if(gara.getNOTE_CANC_GARA() != null && !gara.getNOTE_CANC_GARA().equals("")){
			garaType.setNOTECANC(gara.getNOTE_CANC_GARA());
		}
		
		if(gara.getIMPORTO_SA_GARA()!= null){
			garaType.setIMPORTOSAGARA(gara.getIMPORTO_SA_GARA());
		}
		
		if (gara.getCIG_ACC_QUADRO() != null ) {
			garaType.setCIGACCQUADRO(gara.getCIG_ACC_QUADRO()) ;
		}
		
		
		//Setta i campi se la versione è 3.02.1.4 o superiore perchè il campo esiste solo
		//a partire da quella
		if(schede.compareTo(Costanti.VERS_302_1_4) >= 0){
			if(gara.getData_creazione() != null && !"".equals(gara.getData_creazione())){
				garaType.setDATACREAZIONE(PageHelper.getCalendarFromStringDate(gara.getData_creazione()));
			}
			
			if(gara.getID_OSSERVATORIO() != null && !"".equals(gara.getID_OSSERVATORIO())){
				garaType.setIDOSSERVATORIO(gara.getID_OSSERVATORIO());
			}
			
			if(gara.getID_STATO_GARA() != 0){
				garaType.setIDSTATOGARA(Long.toString(gara.getID_STATO_GARA()));
			}
			
			if(gara.getDATA_PERFEZIONAMENTO_BANDO() != null && !"".equals(gara.getDATA_PERFEZIONAMENTO_BANDO())){
				garaType.setDATAPERFEZIONAMENTOBANDO(PageHelper.getCalendarFromStringDate(gara.getDATA_PERFEZIONAMENTO_BANDO()));
			}
	  }
		
		//Setta il numero di lotto se la versione è 3.02.1.3 o superiore perchè il campo esiste solo
		//a partire da quella
		if(schede.compareTo(Costanti.VERS_302_1_3) >= 0){
			if(gara.getNumeroLotti() != null){
				garaType.setNUMEROLOTTI(gara.getNumeroLotti());
			}
		}
		
      if( SimogFlags.is3028_RFWSSC00Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
         garaType.setPROVVPRESACARICO(gara.getPROVV_PRESA_CARICO());
      }

      if( SimogFlags.is3031_ESCL_AVCPASS() 
//            && gara.getESCLUSO_AVCPASS() != null
            && isFlag(gara.getESCLUSO_AVCPASS())
            && schede.compareTo(Costanti.VERS_303_0_0) >= 0 ){
         garaType.setESCLUSOAVCPASS(FlagSNType.Enum.forString(gara.getESCLUSO_AVCPASS()));
      }

      if( SimogFlags.isINT87_RFSIMOGWS01Active() 
//            && gara.getURGENZA_DL133() != null
            && isFlag(gara.getURGENZA_DL133())
            && schede.compareTo(Costanti.VERS_303_3_0) >= 0 ){
         garaType.setURGENZADL133(FlagSNType.Enum.forString(gara.getURGENZA_DL133()));
      }

      // enti aggregatori comuni
      if(SimogFlags.isINT85_RFWEBGL01Active() && gara.getSCELTA_LEGGE89() > 0 && schede.compareTo(Costanti.VERS_303_5_0) >= 0){
         garaType.setMOTIVORICHCIGCOMUNI(String.valueOf(gara.getSCELTA_LEGGE89()));
      }

      // enti aggregatori DPCM
      if(SimogFlags.is30350_RFWSGL01Active()&& schede.compareTo(Costanti.VERS_303_5_0) >= 0){
         if(gara.getCOD_MOTIVO_EAGG()>0)
            garaType.setMOTIVORICHCIG(String.valueOf(gara.getCOD_MOTIVO_EAGG()));
         
         if(gara.getCatMerc().size()>0){
            ElencoCategMercType lista = garaType.addNewCATEGORIEMERC();
            for (int i = 0; i < gara.getCatMercArray().length; i++) {
               lista.addCATEGORIA(gara.getCatMercArray()[i]);
            }
         }
      }
      
      if(SimogFlags.is3042Active() && schede.compareTo(Costanti.VERS_304_2_0) >= 0)
      {
	      //TICKET ALM #664
	      if(gara.getID_SVOLGIMENTO() != 0) {
	    	  garaType.setSTRUMENTOSVOLGIMENTO(String.valueOf(gara.getID_SVOLGIMENTO()));
	      }
	      //FINE TICKET ALM #664
	      
	    //TICKET ALM #3832
	      if(gara.getID_ESTREMA_URGENZA() != 0) {
	    	  garaType.setESTREMAURGENZA(String.valueOf(gara.getID_ESTREMA_URGENZA()));
	      }
	      //FINE TICKET ALM #3832
	      
	    //TICKET ALM #3834
	      if(gara.getID_ALLEGATO_IX() != 0) {
	    	  garaType.setALLEGATOIX(String.valueOf(gara.getID_ALLEGATO_IX()));
	      }
	      //FINE TICKET ALM #3834
	      
      }
      
    //TICKET ALM - 3.04.3
      if(SimogFlags.is3043Active() && schede.compareTo(Costanti.VERS_304_3_0) >= 0)
      {
	      if(gara.getDurataGiorni() != null && !"".equals(String.valueOf(gara.getDurataGiorni())))
	    	  garaType.setDURATAACCQUADROCONVENZIONEGARA(gara.getDurataGiorni());
      }
    //FINE TICKET ALM - 3.04.3
      
      
      //TICKET ALM #659 - 3.04.4
      if(schede.compareTo(Costanti.VERS_304_4_0) >= 0) {
    	  if(gara.getFlagSAAgente()!=null && !"".equals(gara.getFlagSAAgente()))
    		  garaType.setFLAGSAAGENTEGARA(FlagSNType.Enum.forString(gara.getFlagSAAgente()));
    	  else
    		  garaType.setFLAGSAAGENTEGARA(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
    	  
    	  if(gara.getID_F_DELEGATE() != 0) {
    		  garaType.setIDFDELEGATE(String.valueOf(gara.getID_F_DELEGATE()));
    	  }
    	  if(gara.getCF_AMM_AGENTE()!=null && !"".equals(gara.getCF_AMM_AGENTE()))
    		  garaType.setCFAMMAGENTEGARA(gara.getCF_AMM_AGENTE());
    	  if(gara.getDEN_AMM_AGENTE()!=null && !"".equals(gara.getDEN_AMM_AGENTE()))
    		  garaType.setDENAMMAGENTEGARA(gara.getDEN_AMM_AGENTE());
 
      }
      //FINE TICKET ALM #659 - 3.04.4
      
     //TICKET ALM #12088 - 3.04.5
      if(schede.compareTo(Costanti.VERS_304_5_0) >= 0) {
    	  if(gara.getCodiceAusa()!=null)
    		  garaType.setCODICEAUSA(gara.getCodiceAusa());
      }
      
}
	
	/**********************************************************************
	 * Imposta i dati del Lotto
	 * param lt LottoType 
	 */
	private void setLotto(LottoType lottoType){
		logger.debug("eseguendo: void setLotto(LottoType lt)");
		
		try {
		   //logger.debug("inizio campi lotto");
         //logger.debug(ObjectIntrospector.propertiesInfo(l.getClass(), l));
         lottoType.setCPV(lotto.getId_CPV());
         /* optionals se chiamo i setter crea l'attributo(?), implementato cosi per questa ragione	*/
         if(lotto.getData_Comunicazione() != null){
         	lottoType.setDATACOMUNICAZIONE(PageHelper.getCalendarFromStringDate(lotto.getData_Comunicazione()));
         	//logger.debug("\r\n setDATACOMUNICAZIONE"+PageHelper.getCalendarFromStringDate(l.getData_Comunicazione()));
         }
         if(lotto.getDATA_INIB_PAGAMENTO() != null){
         	lottoType.setDATAINIBPAGAMENTO(PageHelper.getCalendarFromStringDate(lotto.getDATA_INIB_PAGAMENTO()));
         	//logger.debug("\r\n setDATAINIBPAGAMENTO"+PageHelper.getCalendarFromStringDate(l.getDATA_INIB_PAGAMENTO()));
         }
         if(lotto.getData_Pubblicazione() != null){
         	lottoType.setDATAPUBBLICAZIONE(PageHelper.getCalendarFromStringDate(lotto.getData_Pubblicazione()));
         	//logger.debug("\r\n setDATAPUBBLICAZIONE"+PageHelper.getCalendarFromStringDate(l.getData_Pubblicazione()));
         }
         // PP per i contratti derivanti da accordo quadro la data scadenza pagamenti è blank!
         if(lotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lotto.getDATA_SCADENZA_PAGAMENTI())){
         	lottoType.setDATASCADENZAPAGAMENTI(PageHelper.getCalendarFromStringDate(lotto.getDATA_SCADENZA_PAGAMENTI()));
         	//logger.debug("\r\n setDATASCADENZAPAGAMENTI"+PageHelper.getCalendarFromStringDate(l.getDATA_SCADENZA_PAGAMENTI()));
         }
         
         /**/
         lottoType.setIDCATEGORIAPREVALENTE(lotto.getId_Categoria_prevalente()); 
         
         lottoType.setIDSCELTACONTRAENTE(lotto.getId_Scelta_Contraente());
         
         lottoType.setIMPORTOIMPRESA(lotto.getImporto_Impresa());
         lottoType.setIMPORTOLOTTO(lotto.getImporto_Lotto());
         lottoType.setIMPORTOSA(lotto.getImporto_SA());
         lottoType.setOGGETTO(lotto.getOggetto());
         
         
         
         //TICKET ALM - 3.04.2
         if(schede.compareTo(Costanti.VERS_304_2_0) < 0) {
	         if(isFlag(String.valueOf(lotto.getSomma_Urgenza())))
	            lottoType.setSOMMAURGENZA(FlagSNType.Enum.forString(""+lotto.getSomma_Urgenza()));
	         else
	            lottoType.setSOMMAURGENZA(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
         }
        //FINE TICKET ALM - 3.04.2
         
         if (lotto.getId_motivazione()!= null && !"".equals(lotto.getId_motivazione()))	
         	lottoType.setIDMOTIVAZIONE(lotto.getId_motivazione()); 
         
         if(lotto.getNoteCancellazione() != null)
         	lottoType.setNOTECANC(lotto.getNoteCancellazione());
         
         if (lotto.getTIPO_CONTRATTO_LOTTO() != null && !lotto.getTIPO_CONTRATTO_LOTTO().equals(""))
         	lottoType.setTIPOCONTRATTO(TipoSchedaType.Enum.forString(lotto.getTIPO_CONTRATTO_LOTTO())) ;
         	
         if (lotto.getFLAG_ESCLUSO() != null && !lotto.getFLAG_ESCLUSO().equals("")){
         	lottoType.setFLAGESCLUSO(FlagSNType.Enum.forString(lotto.getFLAG_ESCLUSO())) ;
         }

         //TICKET ALM - 3.04.2 20015
         if (isFlag(lotto.getFLAG_ESCLUSO()) && Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_ESCLUSO()) && lotto.getID_ESCLUSIONE() != 0) 
         	lottoType.setIDESCLUSIONE(String.valueOf(lotto.getID_ESCLUSIONE())) ;
         	
       //TICKET ALM - 3.04.2
         if(schede.compareTo(Costanti.VERS_304_2_0) < 0) {
	         if(lotto.getTRIENNIO_ANNO_INIZIO() != null && !"".equals(lotto.getTRIENNIO_ANNO_INIZIO()))
	         	lottoType.setTRIENNIOANNOINIZIO(Integer.parseInt(lotto.getTRIENNIO_ANNO_INIZIO()));
	         if(lotto.getTRIENNIO_ANNO_FINE() != null && !"".equals(lotto.getTRIENNIO_ANNO_FINE()))
	         	lottoType.setTRIENNIOANNOFINE(Integer.parseInt(lotto.getTRIENNIO_ANNO_FINE()));
	         if(lotto.getTRIENNIO_PROGRESSIVO() != null && !"".equals(lotto.getTRIENNIO_PROGRESSIVO()))
	         	lottoType.setTRIENNIOPROGRESSIVO(Integer.parseInt(lotto.getTRIENNIO_PROGRESSIVO()));
         }//FINE TICKET ALM - 3.04.2
         
         if(lotto.getANNUALE_CUI_MININF() != null && !"".equals(lotto.getANNUALE_CUI_MININF()))
         	lottoType.setANNUALECUIMININF(lotto.getANNUALE_CUI_MININF());
         if(lotto.getLUOGO_ISTAT() != null && !"".equals(lotto.getLUOGO_ISTAT()))
         	lottoType.setLUOGOISTAT(lotto.getLUOGO_ISTAT());
         if(lotto.getLUOGO_NUTS() != null && !"".equals(lotto.getLUOGO_NUTS()))
         	lottoType.setLUOGONUTS(lotto.getLUOGO_NUTS());
         if(lotto.getIMPORTO_ATTUAZIONE_SICUREZZA() != null)
         	lottoType.setIMPORTOATTUAZIONESICUREZZA(lotto.getIMPORTO_ATTUAZIONE_SICUREZZA());

         // CIG introdotto da questa versione in poi
         if(schede.compareTo(Costanti.VERS_302_1_3) >= 0)
         	lottoType.setCIG(CIGBean.getFeCig(String.valueOf(lotto.getSomma_Urgenza()), lotto.getCIG()+lotto.getCIG_kkk(), lotto.getDataCreazione()));


         //Setta il numero di lotto se la versione è 3.02.1.4 o superiore perchè il campo esiste solo
         //a partire da quella
         if(schede.compareTo(Costanti.VERS_302_1_4) >= 0){
         	
         	if(lotto.getDATA_CANCELLAZIONE_LOTTO() != null && !"".equals(lotto.getDATA_CANCELLAZIONE_LOTTO())){
         		lottoType.setDATACANCELLAZIONELOTTO(PageHelper.getCalendarFromStringDate(lotto.getDATA_CANCELLAZIONE_LOTTO()));			
         	}
         	
         	if(lotto.getDataCreazione() != null && !"".equals(lotto.getDataCreazione())){
         		lottoType.setDATACREAZIONELOTTO(PageHelper.getCalendarFromStringDate(lotto.getDataCreazione()));
         	}	
         }

         // PP B302.3.3 nuovi campi
         if(schede.compareTo(Costanti.VERS_302_2_0) >= 0){

	         	if(lotto.getFLAG_PREVEDE_RIP() != null && !"".equals(lotto.getFLAG_PREVEDE_RIP())){
	         		lottoType.setFLAGPREVEDERIP(FlagSNType.Enum.forString(lotto.getFLAG_PREVEDE_RIP()));			
	         	}
	         	
         	 //TICKET ALM - 3.04.3 #7849
        	 //Restituisci il campo solo se si indica una versione inferiore a 3.04.3
        	 if(SimogFlags.is3043Active() && schede.compareTo(Costanti.VERS_304_3_0) < 0) 
	         	if(lotto.getFLAG_RIPETIZIONE() != null && !"".equals(lotto.getFLAG_RIPETIZIONE()))
	         		lottoType.setFLAGRIPETIZIONE(FlagSNType.Enum.forString(lotto.getFLAG_RIPETIZIONE()));			
	         			
	          
	         	//FINE TICKET ALM - 3.04.3 #7849
         	if(lotto.getCIG_ORIGINE_RIP() != null && !"".equals(lotto.getCIG_ORIGINE_RIP())){
         		lottoType.setCIGORIGINERIP(lotto.getCIG_ORIGINE_RIP());
         	}	
         }

         if(schede.compareTo(Costanti.VERS_302_5_0) >= 0 
               && lotto.getORA_SCADENZA() != null
               && !"".equals(lotto.getORA_SCADENZA().trim()))
            lottoType.setORASCADENZA(lotto.getORA_SCADENZA());
         
         if( schede.compareTo(Costanti.VERS_303_0_0) >= 0 ){
            if(lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito().trim()) )
               lottoType.setDATASCADENZARICHIESTAINVITO(PageHelper.getCalendarFromStringDate(lotto.getDataScadenzaRichiestaInvito()));
            
            if(lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito().trim()) )
               lottoType.setDATALETTERAINVITO(PageHelper.getCalendarFromStringDate(lotto.getDataLetteraInvito()));
         }
         
         //logger.debug("fine campi lotto");
         
         // restituzione dello stato avcpass
         if( SimogFlags.is3028_RFWEBSC00Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
            try {
               //logger.debug("controllo avcpass");
               AVCPassAction avpa = new AVCPassAction(con, logger, ConfigurationManager.getInstance(logger).getSimogProperties());
               //logger.debug("1 controllo avcpass");
               
               ArrayList<Lotto> lotti = new ArrayList<Lotto>();
               lotti.add(lotto);         
               boolean retVal  = avpa.isAVCPass(null, lotti, null);
               //logger.debug("2 controllo avcpass");
               
               if (avpa.getLastStatus() != null)
                     lottoType.setSTATOAVCPASS(avpa.getLastStatus().codice());
               //logger.debug("fine controllo avcpass");
            } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               logger.fatal(e.getMessage());
            }
         }	   
         //logger.debug("inizio categorie scorporabili");

         // categorie scorporabili
         HashMap<String, String> categorieScorporabili = lotto.getCategorieScorporabili();
         if(categorieScorporabili != null && !categorieScorporabili.isEmpty()){
         	Iterator categorieScorpIterator = categorieScorporabili.keySet().iterator();
         	lottoType.addNewCATEGORIE();
         	while(categorieScorpIterator.hasNext()){
         		//if(lottoType.getCATEGORIE() == null){logger.debug("categorie null");}
         		lottoType.getCATEGORIE().addCATEGORIA((String)categorieScorpIterator.next());	
         	}
         }
         
         // restituzione di tipiAppalto
         if( SimogFlags.is3031_RFWEBGL00Active() && schede.compareTo(Costanti.VERS_303_1_0) >= 0 ){
            if(lotto.getElencoTipoAppaltoLottoL() != null && lotto.getElencoTipoAppaltoLottoL().size() > 0){
               for(TipoAppaltoAggBean elem : lotto.getElencoTipoAppaltoLottoL()){
                  TipiAppaltoType lista = lottoType.addNewTipiAppaltoLav();
                  lista.setIDAPPALTO(String.valueOf(elem.getIdAppalto()));
               }
            }
            if(lotto.getElencoTipoAppaltoLottoF() != null && lotto.getElencoTipoAppaltoLottoF().size() > 0){
               for(TipoAppaltoAggBean elem : lotto.getElencoTipoAppaltoLottoF()){
                  TipiAppaltoType lista = lottoType.addNewTipiAppaltoForn();
                  lista.setIDAPPALTO(String.valueOf(elem.getIdAppalto()));
               }
            }
         }     

         // restituzione di codici CUP
         if( SimogFlags.is3031_RFWEBGL02Active() && schede.compareTo(Costanti.VERS_303_1_0) >= 0 ){
            
            // flag CUP
            if(isFlag(lotto.getFLAG_CUP()))
                  //lotto.getFLAG_CUP() != null
               lottoType.setFLAGCUP(FlagSNType.Enum.forString(lotto.getFLAG_CUP()));
            
            if(lotto.getElencoCup() != null && lotto.getElencoCup().size() > 0){
               CUPLOTTOType lista = lottoType.addNewCUPLOTTO();
               for(CupLottoAgg elem : lotto.getElencoCup()){
                  DatiCUPType item = lista.addNewCODICICUP();
                  item.setCUP(elem.getCup());
                  if(isFlag(elem.getOkUtente())) //)elem.getOkUtente() != null)
                     item.setOKUTENTE(FlagSNType.Enum.forString(elem.getOkUtente()));

                  // lettura dati DIPE se esistono
                  RichiesteCUPManager rcm = new RichiesteCUPManager(con, logger);
                  
                  RichiestaCUP dipe = rcm.getByCup(elem.getCup());
                  if(dipe != null){
                     if(dipe.getESITO_RICHIESTA() != null)
                        item.setDATIDIPE(dipe.getESITO_RICHIESTA());
                     
                     if(isFlag(dipe.getVALIDO())) //dipe.getVALIDO() != null)
                        item.setVALIDO(FlagSNType.Enum.forString(dipe.getVALIDO()));
                     
                     if(dipe.getTematica()!=null && !"".equals(dipe.getTematica()))
                    	 item.setTEMATICAPNRR(dipe.getTematica());
                  }
               }
            }
         }
            
            //TICKET ALM #2845
            if(SimogFlags.is3042Active() && schede.compareTo(Costanti.VERS_304_2_0) >= 0)
            {
	            if(isFlag(lotto.getFLAG_DL50())){
	            	lottoType.setFLAGDL50(FlagSNType.Enum.forString(lotto.getFLAG_DL50()));
	            }
	            if(lotto.getPRIMA_ANNUALITA()!= null && !"".equals(lotto.getPRIMA_ANNUALITA().trim())) {
	            	lottoType.setPRIMAANNUALITA(lotto.getPRIMA_ANNUALITA());
	            }
	            //FINE TICKET ALM #2845
	            
	            //TICKET ALM #3835
	            
	            if(lotto.getID_AFF_RISERVATI() != 0) {
	            	lottoType.setIDAFFRISERVATI(String.valueOf(lotto.getID_AFF_RISERVATI()));
	            }
	            

	            if(lotto.getCondizioni() != null && lotto.getCondizioni().size() > 0) {
	              for(CondizioneLottoBean elem : lotto.getCondizioni()){
	            	  CondizioneLtType lista = lottoType.addNewCondizioni();
	            	    lista.setIDCONDIZIONE(String.valueOf(elem.getIdCondizione()));
	                 }
	               }
	          //FINE TICKET ALM #3835
	            
	            
	            //TICKET ALM #3836
	            if(isFlag(lotto.getFLAG_REGIME())){
	            	lottoType.setFLAGREGIME(FlagSNType.Enum.forString(lotto.getFLAG_REGIME()));
	            	if(Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_REGIME()) && lotto.getID_ESCLUSIONE() != 0) {
		            	lottoType.setARTREGIME(String.valueOf(lotto.getID_ESCLUSIONE()));
		            }
	            }
	            //FINE TICKET ALM #3836
	                   
               }
	            //FINE TICKET ALM #3836
           
            
            //TICKET ALM - 3.04.3
            if(SimogFlags.is3043Active() && schede.compareTo(Costanti.VERS_304_3_0) >= 0)
            {
	            if(lotto.getID_MOTIVO_COLL_CIG() != null && !"".equals(lotto.getID_MOTIVO_COLL_CIG()))
	            	lottoType.setIDMOTIVOCOLLCIG(lotto.getID_MOTIVO_COLL_CIG());
            }
            if(SimogFlags.is3043Active() && schede.compareTo(Costanti.VERS_304_3_0) < 0) {
            	
            }
            //FINE TICKET ALM - 3.04.3

            
    	    //TICKET ALM #4222 - 3.04.4
            if(schede.compareTo(Costanti.VERS_304_4_0) >= 0) {
            	if(lotto.getCOD_CATEGORIA()!=null && !"".equals(lotto.getCOD_CATEGORIA()))
            		lottoType.setCATEGORIAMERC(lotto.getCOD_CATEGORIA());
  
            	//TICKET ALM #4223-#4224 - 3.04.4
            	if(lotto.getFlagNoAdesione()!=null && !"".equals(lotto.getFlagNoAdesione()))
            		lottoType.setFLAGNOADESIONEINIZIATIVA(FlagSNType.Enum.forString(lotto.getFlagNoAdesione()));
            	if(lotto.getFlagSANonClass()!=null && !"".equals(lotto.getFlagSANonClass()))
            		lottoType.setFLAGSANONCLASSIFICATA(FlagSNType.Enum.forString(lotto.getFlagSANonClass()));
            	//FINE TICKET ALM #4223-#4224 - 3.04.4
            	
            }
    	    //FINE TICKET ALM #4222 - 3.04.4
            
            
            //TICKET ALM #4219 - 3.04.4
            //Non c'e' il controllo sulla versione in quanto le cpv secondarie sono disponibili anche per le gare antecedenti
            for(CpvLotto cpvSec : lotto.getElencoCpvSecondarie()) {
        		CPVSecondariaType item = lottoType.addNewCPVSecondaria();
        		item.setCODCPVSECONDARIA(cpvSec.getIdCpv());
        	}
 
            //TICKET ALM #13691 - 3.04.5
            	if(lotto.getImporto_opzioni()!=null && lotto.getImporto_opzioni().doubleValue()>0)
            		lottoType.setIMPORTOOPZIONI(lotto.getImporto_opzioni());
            
            	if(lotto.getDurataAffidamentoGiorni()!=0)
            		lottoType.setDURATAAFFIDAMENTO(lotto.getDurataAffidamentoGiorni());
            	
            	if(lotto.getDurataRipetizioni()!=0)
            		lottoType.setDURATARINNOVI(lotto.getDurataRipetizioni());
            
      
            	
            //TICKET 3.04.5
                if(lotto.getFLAG_PNRR_PNC()!=null )
                	lottoType.setFLAGPNRRPNC(FlagSNType.Enum.forString(lotto.getFLAG_PNRR_PNC()));

                if(lotto.getFLAG_PREVISIONE_QUOTA()!=null )
                	lottoType.setFLAGPREVISIONEQUOTA(FlagSNQType.Enum.forString(lotto.getFLAG_PREVISIONE_QUOTA()));

                if(lotto.getQuotaFemminile()!=null )
                	lottoType.setQUOTAFEMMINILE(lotto.getQuotaFemminile());

                if(lotto.getQuotaGiovanile()!=null )
                    lottoType.setQUOTAGIOVANILE(lotto.getQuotaGiovanile());

                if(lotto.getFLAG_MISURE_PREMIALI()!=null )
                    lottoType.setFLAGMISUREPREMIALI(FlagSNType.Enum.forString(lotto.getFLAG_MISURE_PREMIALI()));
            
              //MEV 37010 3.04.8.1 
                if(lotto.getFLAG_DEROGA_ADESIONE()!=null )
                    lottoType.setFLAGDEROGAADESIONE(FlagSNType.Enum.forString(lotto.getFLAG_DEROGA_ADESIONE()));
            
                
                //MEV 38205 3.04.8.1 
                if(lotto.getFLAG_USO_METODI_EDILIZIA()!=null )
                    lottoType.setFLAGUSOMETODIEDILIZIA(FlagSNType.Enum.forString(lotto.getFLAG_USO_METODI_EDILIZIA()));
            
//    			//3.04.9 MEV 40610
    			if (lotto.getDerogaQualificazioneSA()!=null) {
    				lottoType.setDEROGAQUALIFICAZIONESA(DerogaQualificazioneSA.Enum.forString(lotto.getDerogaQualificazioneSA()));
    			}
                
                for(MisuraPremialeBean  m : lotto.getElencoMisurePremiali()) {
                	MisuraPremialeType misuraPremialeType=lottoType.addNewMisuraPremiale();
                	misuraPremialeType.setStringValue(m.getIdMisuraPremiale()+"");
            	}
        
                for(MotivoDerogaBean  m : lotto.getElencoMotivoDeroga()) {
                	MotivoDerogaType motivoDerogaType=lottoType.addNewMotivoDeroga();
                	motivoDerogaType.setStringValue(m.getIdMotivoDeroga()+"");
            	}
		
                
		
		
		
		
		
		
		} catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         logger.fatal(e.getMessage());
      }
	}
	private String convertToFlagSNPType(String flag_PREVISIONE_QUOTA) {

		String out=flag_PREVISIONE_QUOTA;
		if (out.equals("Q")) {
			out="SP"; 
		}
		
		// TODO Auto-generated method stub
		return out;
	}

	/*		-----	setting scheda A 				------		*/
	
	/*******************************************************************************
	 * Carica le informazioni per la scheda A
	 * throws SimogWSException
	 */
	private void getSchedaA()throws SimogWSException{
		try{		
			logger.debug("eseguendo: void getSchedaA()");
			/*	local managers	*/
			InfoComuniManager infoComuniManager = new InfoComuniManager(con,logger);
			AggiudicazioniManager aggiudicazioniManager = new AggiudicazioniManager(con,logger);	
			PubblicazioneManager pubblicazioniManager = new PubblicazioneManager(con,logger);
			//logger.debug("----- instanziazione riuscita ----");
			/*	local bean	*/
			logger.debug(ObjectIntrospector.propertiesInfo(lotto.getClass(),lotto ));
			InfoGaraBean infoGaraBean = infoComuniManager.loadInfoGara(lotto.getId_Lotto());
			logger.debug(ObjectIntrospector.propertiesInfo(infoGaraBean.getClass(),infoGaraBean ));
			//logger.debug("----- caricamento infogarabean ----");
			//--!QUI CONTROLLO SE CARICARE O MENO LA SCHEDA A//
			//XXX: jumper : restituisce true / false in base a rs.next o va in eccezione se la scheda non e' comfermata
			boolean controlloDatiComuniConfermati = false;
			try{
				String numericCIG = null; 
				
				if(cig !=  null)
					numericCIG = CIGBean.getCIGPart(cig);
				
				controlloDatiComuniConfermati = infoComuniManager.checkDatiComuni(new InfoComuniBean(), numericCIG);
				
				if(SimogFlags.is3028_RFWSGL01Active()){
				   // se controllo è false e la scheda esiste forzo a true
				   if(!controlloDatiComuniConfermati && infoGaraBean.getIdInfo() > 0)
				      controlloDatiComuniConfermati = true;
				}
				
			}catch(Exception e){
			   e.printStackTrace();
				logger.fatal(e.getMessage());
			}
			if(infoGaraBean.getIdInfo()!= 0 && controlloDatiComuniConfermati){
				//logger.debug("----- CONTROLLO SE CARICARE O MENO LA SCHEDA ----");
				//logger.debug(ObjectIntrospector.propertiesInfo(igb.getClass(),igb ));
				InfoComuniBean infoComuniBean = infoComuniManager.load(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo());
				//logger.debug(ObjectIntrospector.propertiesInfo(icb.getClass(),icb ));
				PubblicazioneBean pubblicazioneBean = infoComuniBean.getPubblicazione();
				//logger.debug(ObjectIntrospector.propertiesInfo(pb.getClass(),pb ));
				pubblicazioneBean = pubblicazioniManager.getPubblicazione(pubblicazioneBean.getIdPubblicazione(), pubblicazioneBean.getDataInizioPubblicazione());
				//logger.debug(ObjectIntrospector.propertiesInfo(pb.getClass(),pb ));
				
				/*	xmlbeans	*/
				DatiAggiudicazioneType schedaA = contenitoreSchede.getScheda().addNewDatiScheda();
				DatiComuniType datiComuni = schedaA.addNewDatiComuni();
				PubblicazioneType pubblicazione = schedaA.addNewPubblicazione();
				/*	operations	*/
					/* oggetti con cardinalita' singola	(schedaA)*/
						/*REQUIRED dati comuni	*/					
				this.setDatiComuni(datiComuni, infoComuniBean, infoGaraBean, schedaA);
				
// XXX PATCH - GESTIONE RUP, attenzione nel caso in cui ci sia un resp con lo stesso cf l'anagrafic viene sovrascritta

				RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,logger);
				SoggettoResponsabileBean srb = rrm.getAllSoggettoResponsabileByCF(schedaS.getDatiScheda().getDatiComuni().getCFRUP());
				if(srb != null){
					if(!ToolXMLManager.checkExist(schedaS.getResponsabili(), srb.getCodiceFiscaleResponsabile(),null)){
						ResponsabiliType rit = schedaS.getResponsabili();
						ResponsabileType rp;
						if(rit == null){
							rp = schedaS.addNewResponsabili().addNewResponsabile();
						}else{
							rp = schedaS.getResponsabili().addNewResponsabile();
						}				
						rp.setCODICEFISCALERESPONSABILE(srb.getCodiceFiscaleResponsabile());
						rp.setINDIRIZZO(srb.getIndirizzo());
						rp.setNOME(srb.getNome());
						rp.setTELEFONO(srb.getTelefono());
						rp.setEMAIL(srb.getEmail());
						rp.setFAX(srb.getFax());
						rp.setCODICEISTATCOMUNE(srb.getComuneIstat());
						rp.setCOGNOME(srb.getCognome());
						rp.setCAP(srb.getCap());
					}
				}else{
					logger.error("L'anagrafica del responsabile unico risulta nulla");
				}
				
						/*REQUIRED pubblicazione	*/
				this.setPubblicazione(pubblicazione, pubblicazioneBean, schedaA);			
				//logger.debug("toString():\r\n"+schedaA.toString());
				/* oggetti con cardinalita' multipla	(schedaA)*/
						/*OPTIONAL aggiudicazioni	*/
				List<AggiudicazioneBean> listOfAggiudicazioni = aggiudicazioniManager.getAggiudicazioniList(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo());
				//logger.debug("Numero di Aggiudicazioni: "+listOfAggiudicazioni.size());
				/*QUI CONTROLLO SE CARICARE O MENO AGGIUDICAZIONI*/
				if(!listOfAggiudicazioni.isEmpty()){
					Iterator<AggiudicazioneBean> aggiudicazioniIterator = listOfAggiudicazioni.iterator();
					this.setAggiudicazioni(aggiudicazioniIterator, schedaA, infoGaraBean.getTipoEnte());
				}
			}		
		}catch(Exception e){
			logger.debug("problema nell'uso di oggetti simog common");
			logger.fatal("Error Message: "+e.getMessage());
		//	e.printStackTrace();
			if(e instanceof SimogWSException){
				throw (SimogWSException)e;
			}
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_03);
		}
	}
	
	/******************************************************************
	 * Imposta i dati della pubblicazione
	 * param PubblicazioneType : pubblicazione
	 * param PubblicazioneBean : pb
	 * param DatiAggiudicazioneType : schedaA
	 */
	private void setPubblicazione(PubblicazioneType pubblicazioneType,PubblicazioneBean pubblicazioneBean,DatiAggiudicazioneType schedaA) {
		logger.debug("eseguendo: void setPubblicazione(PubblicazioneType pubblicazione,PubblicazioneBean pb,SchedaAType schedaA)");
		//logger.debug(ObjectIntrospector.propertiesInfo(pb.getClass(),pb ));
		/*	date optionals */
		if(pubblicazioneBean.getDataAlbo() != null)
			pubblicazioneType.setDATAALBO(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataAlbo()));
		
		if(pubblicazioneBean.getDataGuce() != null)
			pubblicazioneType.setDATAGUCE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuce()));
		
		if(pubblicazioneBean.getDataGuri() != null)
			pubblicazioneType.setDATAGURI(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataGuri()));
		
		if(isFlag(pubblicazioneBean.getProfiloCommitente()))
		   pubblicazioneType.setPROFILOCOMMITTENTE(FlagSNType.Enum.forString(pubblicazioneBean.getProfiloCommitente()));
		
		if(pubblicazioneBean.getQuotidianiNaz() != null)
		   pubblicazioneType.setQUOTIDIANINAZ(pubblicazioneBean.getQuotidianiNaz());
		
		if(pubblicazioneBean.getQuotidianiReg() != null)
		   pubblicazioneType.setQUOTIDIANIREG(pubblicazioneBean.getQuotidianiReg());

		if(isFlag(pubblicazioneBean.getSitoMinisteroInfTrasp())) // != null)
		      pubblicazioneType.setSITOMINISTEROINFTRASP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoMinisteroInfTrasp()));
		
		if(isFlag(pubblicazioneBean.getSitoOsservatorioCP())) // != null)		
		   pubblicazioneType.setSITOOSSERVATORIOCP(FlagSNType.Enum.forString(pubblicazioneBean.getSitoOsservatorioCP()));

		if(pubblicazioneBean.getDataBore()!=null)
			pubblicazioneType.setDATABORE(PageHelper.getCalendarFromStringDate(pubblicazioneBean.getDataBore()));
		if(pubblicazioneBean.getPeriodici()!=null)
			pubblicazioneType.setPERIODICI(pubblicazioneBean.getPeriodici());
		
		//TICKET ALM 16367 - MAC 3.04.5
		if(pubblicazioneBean.getLinkSitoCommittente()!=null && !"".equals(pubblicazioneBean.getLinkSitoCommittente()))
				pubblicazioneType.setLINKSITO(pubblicazioneBean.getLinkSitoCommittente());
		//MEV 37523 3.04.8.1
		if(pubblicazioneBean.getLinkAffidamentoDiretto()!=null && !"".equals(pubblicazioneBean.getLinkAffidamentoDiretto()))
			pubblicazioneType.setLINKAFFIDAMENTODIRETTO(pubblicazioneBean.getLinkAffidamentoDiretto());
	
		
	}
	
	/***********************************************************
	 * imposta le informazioni inerenti i dati comuni
	 * param DatiComuniType : datiComuni
	 * param InfoComuniBean : icb
	 * param InfoGaraBean : igb
	 * param DatiAggiudicazioneType : schedaA
	 */
	private void setDatiComuni(DatiComuniType datiComuniType,InfoComuniBean infoComuniBean,InfoGaraBean infoGaraBean,DatiAggiudicazioneType schedaA){
		logger.debug("eseguendo: void setDatiComuni(DatiComuniType datiComuni,InfoComuniBean icb,InfoGaraBean igb,DatiAggiudicazioneType schedaA)");
		//logger.debug(ObjectIntrospector.propertiesInfo(igb.getClass(),igb ));
		//logger.debug(ObjectIntrospector.propertiesInfo(icb.getClass(),icb ));
		datiComuniType.setCFAMM(infoComuniBean.getCfAmministrazione());
		datiComuniType.setCFAMMAGENTE(infoComuniBean.getCfAmmAgente());
		datiComuniType.setCFRUP(infoComuniBean.getCfRup());
		datiComuniType.setCFSA(infoComuniBean.getCfStazioneAppaltante());
		//-------------------
		// SIMOG-46 PP 18-05-09 impostato il 9 in caso di somma urgenza
		datiComuniType.setCIG(CIGBean.getFullCIG(infoGaraBean.getSommaUrgenza(), infoGaraBean.getCig(), infoGaraBean.getCigKKK(),infoGaraBean.getDataCreazione())); 
		datiComuniType.setCODICECC(infoComuniBean.getCodiceCC());
		datiComuniType.setDENAMM(infoComuniBean.getDenAmministrazione());
		datiComuniType.setDENAMMAGENTE(infoComuniBean.getDenAmmAgente());
		datiComuniType.setDENOMCC(infoComuniBean.getDenomCC());
		datiComuniType.setDENSA(infoComuniBean.getDenStazioneAppaltante());
		datiComuniType.setFLAGENTESPECIALE(FlagSOType.Enum.forString(infoComuniBean.getFlagEnteSpeciale()));

		if(isFlag(infoComuniBean.getFlagSAAgente()))
		   datiComuniType.setFLAGSAAGENTE(FlagSNType.Enum.forString(infoComuniBean.getFlagSAAgente()));
		else
		   datiComuniType.setFLAGSAAGENTE(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
		
		//gm nuovo codice nuovi dati comuni
		if(infoComuniBean.getTipologiaProcedura()>0)
			datiComuniType.setTIPOLOGIAPROCEDURA(String.valueOf(infoComuniBean.getTipologiaProcedura()));
		if(infoComuniBean.getDurataConvenzione()>0)
			datiComuniType.setDURATAACCQUADROCONVENZIONE(infoComuniBean.getDurataConvenzione());
		if(isFlag(infoComuniBean.getFlagProcedeStipula()))//!=null && !"".equals(infoComuniBean.getFlagProcedeStipula().trim()))
			datiComuniType.setFLAGCENTRALESTIPULA(FlagSNType.Enum.forString(infoComuniBean.getFlagProcedeStipula().trim()));
		
		if(infoComuniBean.getIdCategSa() != null && !"".equals(infoComuniBean.getIdCategSa()))
			datiComuniType.setIDCATEGSA(infoComuniBean.getIdCategSa());
		
		datiComuniType.setESITOPROCEDURA(EsitoProceduraType.Enum.forString(infoComuniBean.getEsitoProcedura()));
		if(infoComuniBean.getTipologiaSA() != 0 ){
			datiComuniType.setIDTIPOLOGIASA(Long.toString(infoComuniBean.getTipologiaSA()));
		}
		datiComuniType.setTIPOCONTRATTO(TipoSchedaType.Enum.forString(infoComuniBean.getTipoContratto()));
		
		if(infoComuniBean.getID_MODO_REAL() != 0){
			datiComuniType.setMODOREALIZZAZIONE(String.valueOf(infoComuniBean.getID_MODO_REAL()));
		}

		if (isFlag(infoComuniBean.getFLAG_ESCLUSO())) // != null && !infoComuniBean.getFLAG_ESCLUSO().equals(""))
			datiComuniType.setFLAGESCLUSO(FlagSNType.Enum.forString(infoComuniBean.getFLAG_ESCLUSO())) ;
		
		if (infoComuniBean.getID_ESCLUSIONE() != 0) 
			datiComuniType.setIDESCLUSIONE(String.valueOf(infoComuniBean.getID_ESCLUSIONE())) ;
		
        if( SimogFlags.is3028_RFWSGL01Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           datiComuniType.setIDSTATOSCHEDA(StatoSchedaType.Enum.forString( Long.toString(infoComuniBean.getIdStato()) ));
        }

        if( SimogFlags.is3028_RFWEBSC00Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           datiComuniType.setORIGINESCHEDA(OrigineSchedaType.Enum.forString( Long.toString(infoComuniBean.getOrigine()) ));
           if(infoComuniBean.getIdLocale() != null && !"".equals(infoComuniBean.getIdLocale()))
              datiComuniType.setIDSCHEDALOCALE(infoComuniBean.getIdLocale());
           
           datiComuniType.setIDSCHEDASIMOG(String.valueOf(infoComuniBean.getIdInfo()));
        }
        
        if( SimogFlags.is3028_RFWSSC00Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0 ){
           datiComuniType.setPROVVPRESACARICO(infoComuniBean.getProvvPresaCarico());
        }

}
	
	
	
	/*************************************************************
	 * Imposta le informazioni per l'aggiudicazione
	 * param Iterator AggiudicazioniBean : i
	 * param DatiAggiudicazioneType : schedaA
	 * param String : tipoEnte
	 * throws Exception
	 */
	private void setAggiudicazioni(Iterator<AggiudicazioneBean> aggiudiazioniIterator,DatiAggiudicazioneType schedaA, String tipoEnte) throws Exception{
		//aggiunge aggiudicazioni a scheda a

		while(aggiudiazioniIterator.hasNext()){
			AggiudicazioneBean ab = aggiudiazioniIterator.next();
			//ATTENZIONE ASSEGNAZIONE IMPORTANTE USATA DA TUTTI I SETTER DOPO
			//X-XX VL - verifica se aggiudicazione confermata.
			if(ab.getIdStato() == StatiScheda.CONFERMATO 
		    || (SimogFlags.is3028_RFWSGL01Active() && schede.compareTo(Costanti.VERS_302_8_0) >= 0)){
				this.aggiudicazioneBean = ab;
				this.scheda = schedaA.addNewSchedaCompleta();
				//la riga seguente non ritorna nulla setta semplicemente alcuni degli oggetti in ingresso
				
				//gm nuovo codice per aggiudicazione, contratti esclusi, contratti sottosoglia, adesioni
				if(TipoAggiudicazione.E.equals(aggiudicazioneBean.getSottotipo()))
				    new EsclusoXMLManager(con,logger,this.aggiudicazioneBean,scheda,schedaS,schedaA,schede).getEscluso(tipoEnte); 
				else if(TipoAggiudicazione.Q.equals(aggiudicazioneBean.getSottotipo()))
				    new AdesioneXMLManager(con,logger,this.aggiudicazioneBean,scheda,schedaS,schedaA,schede).getAdesione(tipoEnte); 
				else if(TipoAggiudicazione.S.equals(aggiudicazioneBean.getSottotipo()))
				    new SottosogliaXMLManager(con,logger,this.aggiudicazioneBean,scheda,schedaS,schedaA,schede).getSottosoglia(tipoEnte); 			
				else //if(TipoAggiudicazione.A.equals(aggiudicazioneBean.getSottotipo()))
				    new AggiudicazioniXMLManager(con,logger,this.aggiudicazioneBean,scheda,schedaS,schedaA,schede).getAggiudicazione(tipoEnte); 
				
				
				/* avendo la stessa cardinalita' penso debbano stare qui anche perche scrivono in "scheda" */
					
					this.getDatiInizio();
					this.getStipula();
					this.getAvanzamenti();
					this.getConclusione();
					this.getCollaudo();
					this.getRitardi();
					this.getAccordiBonari();
					this.getSospensioni();
					this.getVarianti();
					this.getSubAppalti();
					
				/* end */
			}
		}		
	}
/*	begin 28feb2008	*/
	/*		-----	setting scheda DatiInizioType		-----		*/
	
	/************************************************************************
	 * carica i Dati di inizio
	 * throws SimogWSException
	 */
	private void getDatiInizio()throws SimogWSException{
		new DatiInzioXMLManager(con,logger,aggiudicazioneBean,scheda,schedaS,schede).getDatiInizio();		
	}
	/*		-----	setting scheda StipulaType		-----		*/
	
	/************************************************************************
	 * carica i Dati di stipula
	 * throws SimogWSException
	 */
	private void getStipula()throws SimogWSException{
		new StipulaXMLManager(con,logger,aggiudicazioneBean,scheda,schedaS,schede).getStipula();		
	}
	/*		-----	setting scheda AvanzamentiType		-----		*/
	
	/************************************************************************
	 * Carica le informazioni degli Avanzamenti
	 * throws SimogWSException
	 */
	private void getAvanzamenti()throws SimogWSException{
		new AvanzamentiXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getAvanzamenti();
	}
	/*		-----	setting scheda ConclusioneType		-----		*/
	
	/*************************************************************************
	 * Carica le informazioni di Conclusione
	 * throws SimogWSException
	 */
	private void getConclusione()throws SimogWSException{
		new ConclusioneXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getConclusione();
	}
	/*		-----	setting scheda CollaudoType			-----		*/
	
	/*************************************************************************
	 * Carica le informazioni di Collaudo
	 * throws SimogWSException
	 */
	private void getCollaudo()throws SimogWSException{
		new CollaudoXMLManager(con,logger,aggiudicazioneBean,scheda,schedaS,schede).getCollaudo();
	}
	/*		-----	setting scheda RitardiType			-----		*/	
	
	/*************************************************************************
	 * Carica le informazioni dei ritardi
	 * throws SimogWSException
	 */
	private void getRitardi()throws SimogWSException{
		new RitardiXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getRitardi(); 
	}
	/*		-----	setting scheda AccordiBonarioType	-----		*/
	
	/*************************************************************************
	 * Carica le informazioni relative agli accordi bonari
	 * throws SimogWSException
	 */
	private void getAccordiBonari()throws SimogWSException{
		new AccordiBonariXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getAccordiBonari();
	}
	/*		-----	setting scheda SospensioniType		-----		*/
	
	/*************************************************************************
	 * Carica le informazioni delle Sospensioni
	 * throws SimogWSException
	 */
	private void getSospensioni()throws SimogWSException{
		new SospensioniXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getSospensioni();
	}
	/*		-----	setting scheda VariantiType			-----		*/
	
	/*************************************************************************
	 * Carica le informazioni relative alle Varianti
	 * throws SimogWSException
	 */
	private void getVarianti()throws SimogWSException{
		new VariantiXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getVarianti();
	}
	/*		-----	setting scheda SubAppaltiType		-----		*/
	
	/*************************************************************************
	 * Carica le informazioni di SubAppalti
	 * throws SimogWSException
	 */
	private void getSubAppalti()throws SimogWSException{
		new SubAppaltiXMLManager(con,logger,aggiudicazioneBean,scheda,schede).getSubAppalti();
	}

	/**
	 * @deprecated
	 * Metodo che si occupa di valorizzare i campi dell'xml bean se i valori non sono nulli (reflection)
	 * questo perche non so quali sono i valori che possono essere nulli..
	 * 
	 * @param overInvokeMethod 			//tipo dell'oggetto sul quale voglio invocare il metodo
	 * @param objectOverInvokeMethod 	//Object oggetto(istanza) sulla quale invoco il metodo
	 * @param methodName 				//nome del metodo da invocare
	 * @param typeToSet 				//tipo del valore che il metodo vuole in ingresso
	 * @param value						//valore
	 */
	private void setField(Class overInvokeMethod,Object objectOverInvokeMethod,String methodName,Class typeToSet,Object value){
		try{
			if(value != null){
				Method m = null;
				//define method to invoke
				m = overInvokeMethod.getMethod(methodName, typeToSet);
				//invoke method
				m.invoke(overInvokeMethod.cast(objectOverInvokeMethod), (typeToSet.cast(value)));
			}//else do nothing
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error during setting of xmlbean: ["+e.getMessage()+"]");
}
	}

	public Gara getGara() {
		return gara;
	}

	public void setGara(Gara gara) {
		this.gara = gara;
	}

	 /**********************************************************************
     * Imposta i dati dei requisiti 
     * param dgt DatiGaraType 
     * param idGara  
     */
    
    private void setRequisiti(DatiGaraType dgt,List <RequisitoGara> rql){
       if(schede.compareTo(Costanti.VERS_302_5_0) >= 0){
          try { 
             //logger.debug("settaggio dei requisiti");
             //ciclo sui requisiti per prendere i documenti e le associazioni
	       	   LottoManager lm = new LottoManager(con, logger);
	       	   List<Lotto> lista = lm.getListaCIGByIdGara(dgt.getGara().getIDGARA()); //MAC #7833
             for (Iterator<RequisitoGara> iterator = rql.iterator(); iterator.hasNext();) {
               RequisitoGara rq = (RequisitoGara) iterator.next();
               
               ReqGaraType elem = dgt.addNewRequisito();
               elem.setCodiceDettaglio(Long.toString(rq.getCodice_dettaglio()));

               // patch per errata impostazione degli xsd
               if(rq.getDescrizione().length() > 80)
                  elem.setDescrizione(rq.getDescrizione().substring(0, 80));
               else
                  elem.setDescrizione(rq.getDescrizione());
               
               if (isFlag(rq.getFlag_avvalimento()))
                  elem.setFlagAvvalimento(FlagSNType.Enum.forString(rq.getFlag_avvalimento()));
               else
                  elem.setFlagAvvalimento(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
               
               if (isFlag(rq.getFlag_bando_tipo()))
                  elem.setFlagBandoTipo(FlagSNType.Enum.forString(rq.getFlag_bando_tipo()));
               else
                  elem.setFlagBandoTipo(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));

               if (isFlag(rq.getFlag_comprova_offerta()))
                  elem.setFlagComprovaOfferta(FlagSNType.Enum.forString(rq.getFlag_comprova_offerta()));
               else
                  elem.setFlagComprovaOfferta(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
                  
               if (isFlag(rq.getFlag_esclusione()))
                  elem.setFlagEsclusione(FlagSNType.Enum.forString(rq.getFlag_esclusione()));
               else
                  elem.setFlagEsclusione(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
               
               if (isFlag(rq.getFlag_riservatezza()))
                  elem.setFlagRiservatezza(FlagSNType.Enum.forString(rq.getFlag_riservatezza()));
               else
                  elem.setFlagRiservatezza(FlagSNType.Enum.forString(Costanti.FLAG_VALORE_NO));
               
               elem.setValore(rq.getValore());
               
               //eventuali documenti
               if (rq.getDocumenti().size() > 0) {
                  for (Iterator<Documento> iterator2 = rq.getDocumenti().iterator(); iterator2.hasNext();) {
                     Documento doc = (Documento) iterator2.next();
                     
                     ReqDocType ildoc = elem.addNewDOCUMENTO();
                     ildoc.setCodiceTipoDoc(Long.toString(doc.getCodice_tipo_doc()));
                     
                     //patch per errata impostazione degli xsd
                     if(doc.getDescrizione_documento().length()>80)
                        ildoc.setDescrizioneDocumento(doc.getDescrizione_documento().substring(0, 80));
                     else
                        ildoc.setDescrizioneDocumento(doc.getDescrizione_documento());
                     
                     ildoc.setEmettitore(doc.getEmettitore());

                     // patch per attributo richiesto ma con pattern che non ammette valore vuoto
                     if(doc.getFax() == null || "".equals(doc.getFax().trim()))
                        ildoc.setFax("0");
                     else
                        ildoc.setFax(doc.getFax());
                     
                     ildoc.setMail(doc.getMail());
                     ildoc.setMailPec(doc.getMail_pec());
                     
                     // patch per attributo richiesto ma con pattern che non ammette valore vuoto
                     if(doc.getTelefono() == null || "".equals(doc.getTelefono().trim()))
                        ildoc.setTelefono("0");
                     else   
                        ildoc.setTelefono(doc.getTelefono());
                  }
               }
               
               // eventuali associazioni cig
               if(rq.getLotti_associati().size()>0){
                          
                  for (Iterator<Long> iterator2 = rq.getLotti_associati().iterator(); iterator2.hasNext();) {
                     Long idLotto = (long) iterator2.next();
                            
                     elem.addNewCIG().setStringValue( cercaCIG(lista, idLotto));
                  }
               }
            }
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   private String cercaCIG(List<Lotto> lista, Long idLotto) {
      String cig = "";

      for (Iterator<Lotto> iterator = lista.iterator(); iterator.hasNext();) {
         Lotto lotto = (Lotto) iterator.next();
         if(lotto.getId_Lotto() == idLotto.longValue())
            return lotto.getCIG()+lotto.getCIG_kkk();
      }
      
      return cig;
   }

   public void setCig(String cig) {
      this.cig = cig;
   }
   
   public void setAllData(boolean value) {
	   this.allData = value;
   }
   
}
