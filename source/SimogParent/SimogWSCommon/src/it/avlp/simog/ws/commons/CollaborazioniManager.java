package it.avlp.simog.ws.commons;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.xmlbeans.AziendaType;
import it.avlp.simog.ws.xmlbeans.CheckLoginType;
import it.avlp.simog.ws.xmlbeans.CollaborazioneList;
import it.avlp.simog.ws.xmlbeans.CollaborazioneType;
import it.avlp.simog.ws.xmlbeans.UfficioType;

import org.apache.log4j.Logger;

	/**
	 * Classe che si occupa di caricare i dati sui bean Collaborazioni
	 * e Collaborazione (creati per fornire un formato "legibile" delle colla-
	 * borazioni nella response per l'utente finale)a partire dagli xmlbean 
	 * 
	 */

public class CollaborazioniManager {
	
	private Logger logger = null;
	
	/*	costruttore	*/
	public CollaborazioniManager(){
		
		logger = LoggerManager.getInstance().getLogger();
	}
	
	/*	costruisce Collaborazioni da XMLbean	*/
	
	/*************************************************************************************
	 * Costruisce una Collaborazioni partendo da WMLBean
	 * @param clt : CheckLoginType
	 * @return Collaborazioni
	 * @throws SimogWSException
	 */
	public Collaborazioni getCollaborazioni(CheckLoginType clt)throws SimogWSException{
		logger.debug("eseguendo: Collaborazioni getCollaborazioni(CheckLoginType clt)");
		if(clt != null){
			Collaborazioni mycollaborazioni = new Collaborazioni();
			
			CollaborazioneList collaborazioni = clt.getCollaborazioni();
			if(collaborazioni == null){return null;}
			CollaborazioneType[] c = collaborazioni.getCollaborazioneArray();
			Collaborazione[] myListOfCollaborazioni = new Collaborazione[c.length];
			int i;
			for(i=0;i<c.length;i++){
				CollaborazioneType collaborazione = c[i];
				AziendaType azienda = collaborazione.getAzienda();
				UfficioType ufficio = collaborazione.getUfficio();
				Collaborazione coll = new Collaborazione();
				coll.setIndex(collaborazione.getIndex());
				coll.setAzienda_codiceFiscale(azienda.getCodiceFiscale());
				coll.setAzienda_denominazione(azienda.getDenominazione());
				coll.setUfficio_denominazione(ufficio.getDenominazione());
				coll.setUfficio_id(ufficio.getIdUfficio());
				coll.setUfficio_profilo(ufficio.getProfilo().toString());
				//coll.setUfficio_profilo(ProfiloEnum.getEnumByProfilo(ufficio.getProfilo()).name());
				coll.setIdOsservatorio(azienda.getIdOsservatorio());
				myListOfCollaborazioni[i]=coll;

			}
			mycollaborazioni.setCollaborazioni(myListOfCollaborazioni);
			return mycollaborazioni;
		}
		logger.error("l'xml bean passato risulta nullo");
		throw new SimogWSException(ErrorManager.SIMOGWS_COLLABORAZIONIMANAGER_NULL_01);
	}
}
