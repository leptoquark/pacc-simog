package it.avcp.simog.auth;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlValidationError;

import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.xmlbeans.CheckLoginDocument;
import it.avlp.simog.ws.xmlbeans.CheckLoginType;

public class XmlManager {
	
	/*	contiene gli errori della validazione tramite xsd	*/
	private ArrayList<XmlValidationError> errors = null;
	private Logger logger = null;
	/*	supertipo della rappresentazione dell'xml auth	*/
	private CheckLoginDocument cld = null;
	/*	bean di rappresentazione del file xml auth	*/
	private CheckLoginType clt = null;
	
	public XmlManager(Logger logger){
		errors = new ArrayList<XmlValidationError>();
		this.logger = logger;
	}
	/**
	 * Valida l'xml in ingresso tramite xsd	inizializza la variabile di classe che
	 * rappresenta il supertipo del bean xml
	 * @param xml String
	 * @return boolean - esito operazione
	 * @throws SimogWSException - 
	 * [SIMOGWS_XMLMANAGER_XML_01] se la stringa non risulta congrua con il il file di definizione 
	 * [SIMOGWS_XMLMANAGER_XML_02] nel caso di una eccezione di tipo &quot;XmlException&quot; 
	 * [SIMOGWS_XMLMANAGER_NULL_03] nel caso in cui la stringa risulta vuota o nulla 
	 */
	public boolean validaXsd(String xml)throws SimogWSException{
		logger.debug("eseguendo validaXsd(String xml)");
		if(xml != null && !xml.equals("")){
			try{
				String temp = this.replaceNameSpaceHeader(xml);
				XmlOptions opts = new XmlOptions();
				opts.setErrorListener(errors);
				cld = CheckLoginDocument.Factory.parse(temp,opts);	
				if(!cld.validate(opts)){
					Iterator<XmlValidationError> i = errors.iterator();
					while(i.hasNext()){
						XmlValidationError elem = i.next();
						logger.fatal("errore xml: "+elem.getMessage());
					}throw new SimogWSException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01);
					//this.error = "errore di validazione xml con xsd";
				}
			}catch(XmlException xmle){
				logger.fatal("eccezione durante la validazione del xml: "+xmle.getMessage());
				//xmle.printStackTrace();
				throw new SimogWSException(ErrorManager.SIMOGWS_XMLMANAGER_XML_02);
				//return false;
			}
			// creo l'oggetto che conterr� i valori del file xml
			clt = cld.getCheckLogin();
	        if(SimogFlags.isOSSNActive()){ 
               // patch per OSSN, imposto a 999 admin_or
	           if(this.clt.getSoggetto() != null)
                  if (this.clt.getSoggetto().getAdminOr() == null || "".equals(this.clt.getSoggetto().getAdminOr()))
                     this.clt.getSoggetto().setAdminOr(ProfiloEnum.REGIONE_999);
	        }
			return true;	
		}else{
			logger.fatal("xml immesso per la validazione risulta nullo o vuoto ");
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLMANAGER_NULL_03);
		}
	}
	/**
	 * valida l'xml ovvero si occupa di verificare che lo stato impostato nell'xml sia maggiore di
	 * zero il che vuol dire che l'autenticazione tramite ws remoto &egrave; andata a buon fine
	 * altrimenti restituisce false e il messaggio associato all'errore
	 * 
	 * @return boolean - esito operazione
	 * @throws SimogWSException
	 */
	public boolean validaXml()throws SimogWSException{
		logger.debug("eseguendo: validaXml()");
		//logger.debug("[xml] - "+clt.toString());
		if(Integer.parseInt(clt.getStato())>=0){
			return true;
		}else{
			logger.fatal("lo stato dell'xml restituito da servizio remoto risulta a -1");
			//mod per forwardare l'errore restituito dal ws remoto
			throw new SimogWSException(clt.getMessaggio());
		}
	}
	/**
	 * permette di recuperare gli errori trovati durante la validazione tramite xsd
	 * @return ArrayList&lt;XmlValidationError&gt;
	 */
	public ArrayList<XmlValidationError> getErrors(){
		logger.debug("eseguendo: getErrors()");
		return this.errors;
	}
	/**
	 * metodo per il recupero del bean che rappresenta il file xml, serve 
	 * per la response
	 *
	 * @return CheckLoginType
	 * @throws SimogWSException - in caso la variabile locale &quot;clt&quot; � nulla 
	 */
	public CheckLoginType getXmlBean()throws SimogWSException{
		logger.debug("eseguendo: getXmlBean()");
		if(this.clt != null){
			return this.clt;
		}else{
			logger.fatal("l'xml bean risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLMANAGER_XML_04);
		}
	}
	/**
	 * l'xml ricevuto necessita di uno string replace altrimenti d&agrave un'errore 
	 * di namespace
	 * 
	 */
	private String replaceNameSpaceHeader(String xml)throws SimogWSException{
		logger.debug("eseguendo replaceNameSpaceHeader(String xml)");
		final CharSequence old_intestazione = "<check_login>";
		final CharSequence old_chiusura = "</check_login>";
		final CharSequence new_intestazione = "<simog:check_login xmlns:simog=\"xmlbeans.ws.simog.avlp.it\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"xmlbeans.ws.simog.avlp.it XmlAuth.xsd\">";
		final CharSequence new_chiusura = "</simog:check_login>";
		final CharSequence old_index = "<collaborazione index=";
		final CharSequence new_index = "<collaborazione simog:index=";
		if(xml != null){
			String temp = xml;
			temp = temp.replace(old_intestazione, new_intestazione);
			temp = temp.replace(old_chiusura,new_chiusura);
			temp = temp.replace(old_index, new_index);
			return temp;
		}else{
			logger.fatal("xml immesso per la formattazione risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05);
		}
	}
//	Spostato nel progetto SimogAVCPLogin.RemoteLogin.java
//	/**
//	 * riscostruzione del file xml a partre dai dati ritornati dal nuovo servizio di login 
//	 */
//	public String getXMLfromNewLogin(Check_login inpData){
//		
//		String retVal = "";
//		
//		CheckLoginDocument dLogin = CheckLoginDocument.Factory.newInstance();
//		
//		// tag iniziale
//		dLogin.addNewCheckLogin();
//		
//		// dati del soggetto
//		if(inpData.getSoggetto() != null){
//			dLogin.getCheckLogin().addNewSoggetto();
//			dLogin.getCheckLogin().getSoggetto().setCognome(inpData.getSoggetto().getCognome());
//			dLogin.getCheckLogin().getSoggetto().setNome(inpData.getSoggetto().getNome());
//			dLogin.getCheckLogin().getSoggetto().setTel(inpData.getSoggetto().getTel());
//			dLogin.getCheckLogin().getSoggetto().setFax(inpData.getSoggetto().getFax());
//			dLogin.getCheckLogin().getSoggetto().setEmail(inpData.getSoggetto().getEmail());
//			dLogin.getCheckLogin().getSoggetto().setAdminOr(inpData.getSoggetto().getAdmin_or());
//		}
//		
//		// collaborazioni
//		if(inpData.getCollaborazioni() != null){
//			dLogin.getCheckLogin().addNewCollaborazioni();
//			for (int i = 0; i < inpData.getCollaborazioni().length; i++) {
//				Collaborazione coll = inpData.getCollaborazioni(i);
//				
//				// nuova collaborazione
//				CollaborazioneType elem = dLogin.getCheckLogin().getCollaborazioni().addNewCollaborazione();
//					
//				// indice della collaborazione
//				elem.setIndex(coll.getIndex());
//				
//				// azienda
//				elem.addNewAzienda();
//				elem.getAzienda().setCodiceFiscale(coll.getAzienda().getCodice_fiscale());
//				elem.getAzienda().setDenominazione(coll.getAzienda().getDenominazione());
//				elem.getAzienda().setIdOsservatorio(coll.getAzienda().getId_osservatorio());
//
//				// ufficio
//				elem.addNewUfficio();
//				elem.getUfficio().setIdUfficio(coll.getUfficio().getId_ufficio());
//				elem.getUfficio().setDenominazione(coll.getUfficio().getDenominazione());
//				elem.getUfficio().setProfilo(ProfiloType.Enum.forString(coll.getUfficio().getProfilo()));
//			}
//		}
//		
//		// stato
//		if(inpData.getStato() != null){
//			dLogin.getCheckLogin().setStato(inpData.getStato());
//		}
//
//		// ritorno l'xml
//		retVal = dLogin.toString();
//		
//		return retVal;
//	}
}
