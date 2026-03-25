package it.avlp.simog.ws.commons;

import it.avcp.simog.auth.RicercaProfiloRASA;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CODICI_NUTS;
import it.avlp.simog.db.generated.COMUNI_VIEW;
import it.avlp.simog.dbToXml.DbToXml;
import it.avlp.simog.dbToXml.XmlBeanManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.xmlbeans.LottoType;
import it.avlp.simog.massload.xmlbeans.SchedaDocument;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;

/**
 * Classe che si occupa del recupero della stringa xml
 * di interesse
 * 
 */


public class GaraXMLManager {
	
	private Logger logger;
	private String schede = null;
	private Connection con = null;
	private SchedaDocument sd = null;
	private String errore = null;
	private boolean auth = false;
	
	private Gara gara;
	
	/*******************************************************
	 * Costuttore
	 * @param schede : String
	 * @param con : Connection
	 * @throws SimogWSException
	 */
	public GaraXMLManager(String schede,Connection con)throws SimogWSException{
		logger = LoggerManager.getInstance().getLogger();
		if(con!=null){
			logger.debug("schede non nulle");
			this.con = con;
			this.schede = schede;
			this.logger = LoggerManager.getInstance().getLogger();
		}else{
			throw new SimogWSException(ErrorManager.SIMOGWS_WSSMANAGER_NULL_03);
		}
	}
	
	/**
	 * il metodo si occupa di recuperare da db i dati della gara 
	 * e del lotto ed eseguire poi il controllo per l'autorizzazione  
	 * @param CIG
	 * @param tm
	 * @throws SimogWSException
	 */
	public void checkAuth(String CIG,TicketManager tm)throws SimogWSException{

		if(CIG != null && !CIG.equals("")){
			try{
				this.getGara(CIG,true);
				this.checkAuthByType(CIG, tm,null);

				// PP patch per osservatorio
				// estesa anche agli accordi quadro
				if(this.auth){
					if (tm.isOsservatorioRegionale()) {
						this.auth = false;
						// solo quelle di competenza
						if (tm.getAdminOr().equals(this.gara.getID_OSSERVATORIO()))
							this.auth = true;
						// accordi quadro sovraregionali
						
						//pre-ticket #31050
//						if(ProfiloEnum.REGIONE_099.equals(this.gara.getID_OSSERVATORIO())
//							     //  && this.gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO)
//								//TICKET ALM #2847
//								&&  (SimogFlags.isAccordoQuadroOrConvenzione(this.gara.getID_MODO_REAL()) || SimogFlags.isSvolgimentoAccordoQuadro(this.gara.getID_SVOLGIMENTO()) ))
//							this.auth = true;
						
						//ticket #31050
						if((SimogFlags.isAccordoQuadroOrConvenzione(this.gara.getID_MODO_REAL()) || 
							SimogFlags.isSvolgimentoAccordoQuadro(this.gara.getID_SVOLGIMENTO()) ))
							this.auth = true;
						
						// 3.02.3 se la gara e' localizzata nella regione di competetenza dell'Osservatorio
						// allora ritorna true
						// verifico che il lotto, se esiste sia localizzato nella regione giusta
      						if(!this.auth)
      						   this.auth = isCompetenza(tm.getAdminOr());
						
					}
				}

				if(!this.auth){
					
	               if(tm.isOsservatorioRegionale()){
                  // devo impostare un messagio diverso da quello standard per gli osservatori
                  this.errore = Messaggi.SIMOG_RIC_004;
               }
               else
                  this.errore = new ErrorManager(ErrorManager.SIMOGWS_GARAXMLMANAGER_APP_03).getError();
					
				}				
			}catch(Exception e){
				logger.error("problema nel recupero della stringa garaxml "+e.getMessage());
				this.errore = new ErrorManager(e.getMessage()).getError();
			}
		}else{
			logger.error("cig passato nullo");
			this.errore = new ErrorManager(ErrorManager.SIMOGWS_GARAXMLMANAGER_NULL_02).getError();
		}
	}
	
	
	/** TICKET ALM #4508
	 * il metodo si occupa di recuperare da db i dati della gara 
	 * e del lotto ed eseguire poi il controllo per l'autorizzazione  
	 * @param CIG
	 * @param tm
	 * @throws SimogWSException
	 */
	public void checkAuthKey(String CIG,TicketManager tm, boolean keyFound, boolean allData, String cfUser)throws SimogWSException{

		if(CIG != null && !CIG.equals("")){
			try{
				this.getGara(CIG,allData);
				this.checkAuthByType(CIG, tm,cfUser);

				// PP patch per osservatorio
				// estesa anche agli accordi quadro
				if(this.auth){
					if (tm.isOsservatorioRegionale()) {
						this.auth = false;
						// solo quelle di competenza
						if (tm.getAdminOr().equals(this.gara.getID_OSSERVATORIO()))
							this.auth = true;
						// accordi quadro sovraregionali
						
						//pre-ticket #31050
//						if(ProfiloEnum.REGIONE_099.equals(this.gara.getID_OSSERVATORIO())
//								 //  && this.gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO)
//								//TICKET ALM #2847
//								&&  (SimogFlags.isAccordoQuadroOrConvenzione(this.gara.getID_MODO_REAL()) || SimogFlags.isSvolgimentoAccordoQuadro(this.gara.getID_SVOLGIMENTO()) ))
//							this.auth = true;
						
						//ticket #31050
						if((SimogFlags.isAccordoQuadroOrConvenzione(this.gara.getID_MODO_REAL()) || 
							SimogFlags.isSvolgimentoAccordoQuadro(this.gara.getID_SVOLGIMENTO()) ))
							this.auth = true;
						// 3.02.3 se la gara e' localizzata nella regione di competetenza dell'Osservatorio
						// allora ritorna true
						// verifico che il lotto, se esiste sia localizzato nella regione giusta
      						if(!this.auth)
      						   this.auth = isCompetenza(tm.getAdminOr());
						
					}
				}

				if(!keyFound && !this.auth){
					
						//TICKET ALM - 3.04.3
					    //Verifica se la gara e' delegata
						Hashtable amministrazioni = new Hashtable();
						boolean delega = false;
						//Converti recupera le amministrazioni dalle collaborazioni
						for(Collaborazione coll : tm.getCollaborazioni().getCollaborazioni())
						{
							if(coll.getAzienda_codiceFiscale()!= null && !"".equals(coll.getAzienda_codiceFiscale()) && !"11111111115".equals(coll.getAzienda_codiceFiscale()))
							amministrazioni.put(coll.getAzienda_codiceFiscale(), coll.getAzienda_codiceFiscale());
						}
						
						//Determina se stiamo cercando una gara o un cig
						//TICKET ALM #659 - 3.04.4
							LottoManager lm = new LottoManager(con,logger);
						GaraManager gm = new GaraManager(con,logger);
						if(!tm.isOsservatorio() && amministrazioni.size()>0) {
							if(CIG.length()==10) {
								List<Lotto> l = lm.getLottoByCigWS(CIG);
								if(l.size()>0) {
								     String cfAmm = gm.getCfAmmDelegata(0,l.get(0).getId_Lotto(), amministrazioni);
								     if(!"".equals(cfAmm))
								    	 delega=true;
								}
							} else {
								String cfAmm = gm.getCfAmmDelegata(Long.parseLong(CIG), 0, amministrazioni);
								if(!"".equals(cfAmm))
							    	 delega=true;
							}
							//Se non e' una gara delegata, verifica se e' una podstdelega
							if(!delega) {
								if(CIG.length()==10) {
									 if(gm.isGaraPostDelega(null, CIG, amministrazioni))
										 delega=true;
								} else 
									if(gm.isGaraPostDelega(CIG, null, amministrazioni))
										delega=true;
							}
							
						}
						//Se non e' delega e non e' osservatorio, restitutisci errore. Altrimenti verifica se la gara e' di competenza nazionale o sovraregionale
						if(!delega && !tm.isOsservatorio()) {
							this.errore = new ErrorManager(ErrorManager.SIMOGWS_GARAXMLMANAGER_APP_03).getError();
						} else	
							if(!delega && tm.isOsservatorio()) {
								//Se non e' una gara delegata, verifica se e' una gara accordo quadro non di competenza
								boolean isAccQNc = false;
								//Determina se stiamo cercando una gara o un cig
								
								if(CIG.length()==10) {
									 if(lm.checkCigAccQuadro(CIG))
									     isAccQNc=true;
								} else 
									if(gm.isGaraAccordoQuadroNonDiCompetenza(CIG))
										 isAccQNc=true;
								
								if(!isAccQNc) {
						               if(tm.isOsservatorioRegionale()){
						                  // devo impostare un messagio diverso da quello standard per gli osservatori
						                  this.errore = Messaggi.SIMOG_RIC_004;
						               }
						               else
						                  this.errore = new ErrorManager(ErrorManager.SIMOGWS_GARAXMLMANAGER_APP_03).getError();
								} else
									this.auth=true;
							} else
								this.auth=true;
				}				
			}catch(Exception e){
				e.printStackTrace();
				logger.error("problema nel recupero della stringa garaxml "+e.getMessage());
				this.errore = new ErrorManager(e.getMessage()).getError();
			}
		}else{
			logger.error("cig passato nullo");
			this.errore = new ErrorManager(ErrorManager.SIMOGWS_GARAXMLMANAGER_NULL_02).getError();
		}
	}
	
	// 3.02.3
	/** verifica se la gara ricade nella regione di competenza per l'osservatorio
	 * @param adminOr
	 * @return
	 */
	private boolean isCompetenza (String adminOr){
	   boolean retVal = false;
	   
	   // oss centrale e hekpdesk
	   if (ProfiloEnum.REGIONE_099.equals(adminOr) ||ProfiloEnum.REGIONE_099.equals(adminOr))
	      return true;
	   
	   // se la gara non ha lotti non ho modo di capire, ritorno che non e' competente 
	   if(this.getSd() == null) return false;
       if(this.getSd().getScheda() == null) return false;
       if(this.getSd().getScheda().getDatiGara() == null) return false;
	   
	   try {
	      LottoType lotto = this.getSd().getScheda().getDatiGara().getLotto();
	   
	      if(lotto != null){
	         String istat = lotto.getLUOGOISTAT();
	         String nuts = lotto.getLUOGONUTS();

	         AccessiDB adb = new AccessiDB(con, logger);
       
	         // verifica istat
	         if(istat != null && !"".equals(istat.trim())){
	            String whereCond = COMUNI_VIEW.ID_COMUNE + " = '" + istat + "'"; 
	            Map<String, String> aaa = adb.getTipologicaWhere(COMUNI_VIEW.TABLE_NAME, COMUNI_VIEW.ID_COMUNE, 
	                  COMUNI_VIEW.ID_REGIONE, null, null, whereCond);

	            retVal = aaa.containsValue(adminOr.substring(1)); // strip primo carattere
	         }

	         // verifica nuts
	         if(!retVal && nuts != null && !"".equals(nuts.trim())){
	            String whereCond = CODICI_NUTS.ID_NUTS + " = '" + nuts + "'";
	            Map<String, String> aaa = adb.getTipologicaWhere(CODICI_NUTS.TABLE_NAME, CODICI_NUTS.ID_NUTS, 
	                  CODICI_NUTS.ID_REGIONE, CODICI_NUTS.DATA_FINE_VALIDITA, adb.getNow(), whereCond );

	            retVal = aaa.containsValue(adminOr.substring(1)); // strip primo carattere
	         }
	      }
	   } catch (SQLException e) {
	      // TODO Auto-generated catch block
          e.printStackTrace();
	   }
	   
	   return retVal;
	}
	
	/**
	 * Controlla se l'utente puo e consultare la gara a seconda
	 * dei profili disponibili segue un'iter di controllo diverso
	 * 
	 * @param CIG
	 * @param tm
	 * @throws SimogWSException
	 */
	private void checkAuthByType(String CIG,TicketManager tm, String cfUser)throws SimogWSException{
		
	   // patch per amministratore, consento massima visibilita'
	   if(tm.isAdmin()){
	      this.auth = true;
	      return;
	   }
       
	   //caso solo collaborazioni
	    if(tm.isColl() && !tm.isOssAndColl()){
	    	//check diretto cf e id -> se ok true
	    	this.auth = this.ownership(tm.getCollaborazioni());
	    	logger.debug("caso solo collaborazioni");
	    	if(!this.auth && cfUser!=null && tm.isRasa())  {
	    		this.verificaCompetenzaRasa(tm, cfUser);
	    	}
	    	
	    	return;
		    //solo osservatorio centrale
	    }else if(tm.isOsservatorioCentrale() && tm.isOnlyOss()){
			this.auth = true;
			logger.debug("caso solo osservatorio centrale");
//			return;
		//solo osservatorio regionale
		}else if (tm.isOsservatorioRegionale() && tm.isOnlyOss()){
			//check if codice regione corrisponde
			try{
				logger.debug("adminor: "+tm.getAdminOr()+", cig: "+CIG);
				//devo cercare come regionale poi dal chiamante verifico se l'osservatorio della gara e' 099 o quello dell'utente
				//PP filtro WS tm.getAdminOr() 
				this.auth = DbToXml.Loader.verify(con, logger, ProfiloEnum.REGIONE_999, CIG);				
				
				logger.debug("caso solo osservatorio regionale");
//				return;
			}catch(Exception e){ 
				this.auth = false; 
				throw new SimogWSException(e.getMessage());
			}
	    }else if(tm.isColl() && tm.isOssAndColl()){
	    	//check collaborazioni
	    	logger.debug("caso solo osservatorio e collaborazioni");
	    		//true -> ok
	    	if(this.ownership(tm.getCollaborazioni())){
	    		this.auth = true; 
	    		logger.debug("controllo le collaborazioni");
//	    		return;
	    		//false ->
	    	}else{
	    		//isOssReg -> do stuff
	    		logger.debug("controllo gli osservatori");
	    		if(tm.isOsservatorioRegionale()){
	    			logger.debug("controllo gli osservatorio regionale");
	    			try{
	    				// devo cercare come regionale poi dal chiamante verifico se l'osservatorio della gara e' 099 o quello dell'utente
	    				//PP filtro WS tm.getAdminOr() 
	    				this.auth = DbToXml.Loader.verify(con, logger, ProfiloEnum.REGIONE_999, CIG);
//	    				return;
	    			}catch(Exception e){ 
	    				this.auth = false; 
	    				throw new SimogWSException(e.getMessage());}
	    		}else if(tm.isOsservatorioCentrale()){
	    			this.auth = true; 
	    			logger.debug("controllo gli osservatorio centrale");
//	    			return;
	    		}
	    	}
	    }

	    if(!this.auth && cfUser!=null && tm.isRasa())  {
    		this.verificaCompetenzaRasa(tm, cfUser);
    		return;
    	}
	}
	
		private void verificaCompetenzaRasa(TicketManager tm, String cfUser) throws SimogWSException {
			
				String wsIam = ConfigurationManager.getInstance().getSimogProperties().getWS_AUTH_TARGET_HOST();
				if(wsIam.contains("NEW"))
					wsIam = wsIam.split("NEW:")[1];
				
				wsIam = wsIam.replace("Ilogin", "rs/gestioneProfilo/ricercaProfilo");
				RicercaProfiloRASA rest = new RicercaProfiloRASA(wsIam);
				List<String> cfRasa = rest.callRicercaProfiloAUSA(cfUser);
		    	logger.debug("controllo competenza RASA");
					this.auth = this.ownershipRasa(cfRasa);
		    	return;
	    }

	/**
	 * metodo che controlla che esista una collaborazione che abbia gli stessi parametri della 
	 * gara e il profilo adeguato.
	 * @param colls
	 * @return
	 */
	private boolean ownership(Collaborazioni colls){
		try{
			//dati estratti dalla gara
			String cfamm = sd.getScheda().getDatiGara().getGara().getCFAMMINISTRAZIONE();
			String cfsa = sd.getScheda().getDatiGara().getGara().getIDSTAZIONEAPPALTANTE();
			Collaborazione colla = null;
			//confronto con le collaborazioni
			colla = colls.getPerCFAMMandCFSA(cfamm, cfsa);
			//se la collaborazione risultante ha anche il profilo giusto ritorna true
			if(colla != null &&
					(colla.getUfficio_profilo().equals(ProfiloEnum.RSSAOLD.codice()) ||
					colla.getUfficio_profilo().equals(ProfiloEnum.RUP.codice()) ||
					colla.getUfficio_profilo().equals(ProfiloEnum.AMMINISTRATORE.codice()))){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	private boolean ownershipRasa(List<String> cfRasa){
		try{
			//dati estratti dalla gara
			String cfamm = sd.getScheda().getDatiGara().getGara().getCFAMMINISTRAZIONE();
			Collaborazione colla = null;
			//confronto con le collaborazioni
			//se la collaborazione risultante ha anche il profilo giusto ritorna true
			for(String cf : cfRasa) {
				if(cfamm.equals(cf))
					return true;
			}
//			if(colla != null &&
//					(colla.getUfficio_profilo().equals(ProfiloEnum.RSSAOLD.codice()) ||
//					colla.getUfficio_profilo().equals(ProfiloEnum.RUP.codice()) ||
//					colla.getUfficio_profilo().equals(ProfiloEnum.AMMINISTRATORE.codice()))){
//				return true;
				return false;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * metodo per il recupero e la validazione della gara ed eventuali lotti
	 * NOTA: vengono restituiti anche i Lotti non perfezionati.
	 * 
	 * @param CIG
	 * @return
	 * @throws SimogWSException
	 */
	private void getGara(String CIG,boolean allData)throws SimogWSException{
		try{
			XmlBeanManager xbm = DbToXml.Loader.getInstance(con, logger, CIG, schede);
			xbm.setAllData(allData);
			sd = xbm.getXmlBean();
			
			// replico la gara letta
			this.gara = xbm.getGara();
			/*	verifico la correttezza dell'xml*/
			XmlOptions opts = new XmlOptions();
			ArrayList<XmlValidationError> errors = new ArrayList<XmlValidationError>();
			opts.setErrorListener(errors);
			sd = SchedaDocument.Factory.parse(sd.xmlText(),opts);	
			if(!sd.validate(opts)){
				Iterator<XmlValidationError> i = errors.iterator();
				while(i.hasNext()){
					XmlValidationError elem = i.next();
					logger.error("errore xml:\r\n"+"messaggio: "+elem.getMessage()+"\r\n");
				}
				this.errore = new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_XML_01).getError();
			}
		}catch(Exception e){
			throw new SimogWSException(e.getMessage());
		}
	}
	/*
	 * metodo che si occupa del recupero della stringa rappresentante la gara
	 * associata al cig passato
	 * 
	 * */
	
	/**********************************************************************************
	 * Genera la stringa relativa alla scheda documenti 
	 * @return String
	 * @throws SimogWSException
	 */
	public String getGaraXML()throws SimogWSException{
		logger.debug("eseguendo: String getGaraXML(String CIG,Collaborazioni colls)");
		return sd.xmlText(); //.toString();
	}
	public String getErrore() {
		return errore;
	}
	/********************************
	 * recupera lo stato dell'autorizzazione
	 * @return
	 */
	public boolean isAuth() {
		return auth;
	}

	public SchedaDocument getSd() {
		return sd;
	}
}
