package it.avlp.simog.beans;

import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.ws.xmlbeans.CheckLoginType;
import it.avlp.simog.ws.xmlbeans.CollaborazioneType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;


public class Utente implements  Serializable {

	
	private static final long serialVersionUID = 75748670439911858L;

	// PP aggiunto xmlBean
	//CheckLoginType xml = null;
	
	protected ProfiloEnum profilo = null;

	private Hashtable listaProfili = new Hashtable();
	private Hashtable listaUffici = new Hashtable();
	private Hashtable listaAmministrazioni = new Hashtable();
	
	private Hashtable listaUfficiRUP = new Hashtable();
	private Hashtable listaAmministrazioniRUP = new Hashtable();
	
	private Hashtable listaOsservatori = new Hashtable();
	//Diego
	private ArrayList listaRASA = new ArrayList();
	private String denomRasa = "";

	//private Logger logger = null;
	
	protected String login = null;
	protected String nome = null;
	protected String cognome = null;
	
	protected String tel = null;
	protected String fax = null;
	protected String email = null;
	
	protected String codiceAtecofin = null;
	
	protected String adminOr = null; 
	// PP organi costituzionali
	protected boolean isOrgano = false;
	protected boolean caricaRasa = false;
	
	public void setProfilo(String profiloID ) {
		this.profilo = ProfiloEnum.getEnumByProfilo(profiloID);	
		if (this.profilo != null)
		   listaProfili.put(profiloID, this.profilo.descrizione());
		//logger.debug("Assegnato RUOLO [" + profilo.descrizione() + "] a profilo [" + profiloID + "]");
	}

	
	
	/*******************************************************************************************************
	 * Aggiunge una StazioneAppaltante al profilo
	 * @param currentStazioneAppaltante : StazioneAppaltante
	 * @param profilo : ProfiloEnum
	 *******************************************************************************************************/
	public void addStazioneAppaltante(StazioneAppaltante currentStazioneAppaltante, ProfiloEnum profilo) {
		
		if(ProfiloEnum.RSSAOLD.equals(profilo)) {
			//logger.debug("aggiungo a profilo RSSA");
			listaUffici.put ( currentStazioneAppaltante.getIdUfficio(), currentStazioneAppaltante );
			listaAmministrazioni.put( currentStazioneAppaltante.getCodiceFiscaleAmministrazione(), currentStazioneAppaltante.getDenominazioneAmministrazione());
			listaOsservatori.put( currentStazioneAppaltante.getCodiceFiscaleAmministrazione(), currentStazioneAppaltante.getAmministrazione().getId_osservatorio());
		} else if(ProfiloEnum.RUP.equals(profilo)){
			//logger.debug("aggiungo a profilo RUP");
			listaUfficiRUP.put ( currentStazioneAppaltante.getIdUfficio(), currentStazioneAppaltante );
			listaAmministrazioniRUP.put( currentStazioneAppaltante.getCodiceFiscaleAmministrazione(), currentStazioneAppaltante.getDenominazioneAmministrazione());
			listaOsservatori.put( currentStazioneAppaltante.getCodiceFiscaleAmministrazione(), currentStazioneAppaltante.getAmministrazione().getId_osservatorio());
		}
	}
	
	//DIEGO
	public void addListaRasa(String codiceFiscale) {
			this.listaRASA.add(codiceFiscale);
	}
	
	public Hashtable<String, String> getOsservatori(){
		return listaOsservatori;
	}
	
	public String getCodiceAtecofin() {
		return codiceAtecofin;
	}

	public void setCodiceAtecofin(String codiceAtecofin) {
		this.codiceAtecofin = codiceAtecofin;
	}

	public Utente(Logger logger) {
		//this.logger = logger;
	}
	
	public Utente(String login, Logger logger) {
		this ( logger );
		this.login = login.toUpperCase();
	}

	public Utente(CheckLoginType inXml, Logger logger) {
		//this.logger = logger;
		//valorizzazione del bean Utente in base all'xml proveniente da auth
//		this.xml = inXml;		
		
		// campi standard
		this.setCognome(inXml.getSoggetto().getCognome());
		this.setEmail(inXml.getSoggetto().getEmail());
		this.setFax(inXml.getSoggetto().getFax());
		this.setNome(inXml.getSoggetto().getNome());
		this.setTel(inXml.getSoggetto().getTel());
		this.setAdminOr(inXml.getSoggetto().getAdminOr());

		// profilo di default per osservatorio regionale
		if(SimogFlags.isOSSNActive() && adminOr != null && adminOr.equals(ProfiloEnum.REGIONE_999))
			setProfilo(ProfiloEnum.OSSNAZ.codice());
		else if(adminOr != null && !adminOr.equals(ProfiloEnum.REGIONE_ZERO))
           setProfilo(ProfiloEnum.OSSREG.codice());
		
		//costruzione liste preesistenti
		if(inXml.getCollaborazioni() != null) {
			for(int i = 0 ; i<inXml.getCollaborazioni().sizeOfCollaborazioneArray();i++){
				CollaborazioneType coll = inXml.getCollaborazioni().getCollaborazioneArray(i);
				
				if(ProfiloEnum.RPNT.codice().toString().equalsIgnoreCase(coll.getUfficio().getProfilo().toString()))
					continue;
				
				setProfilo(coll.getUfficio().getProfilo().toString());
				
				StazioneAppaltante app = new StazioneAppaltante();
				Amministrazione amm = new Amministrazione();
				amm.setCodiceFiscale(coll.getAzienda().getCodiceFiscale());
				amm.setDenominazioneAmministrazione(coll.getAzienda().getDenominazione());
				
//				RicercaSAWS_Client ausaClient = new RicercaSAWS_Client();
				
				if(coll.getAzienda().getIdOsservatorio()!= null)
					amm.setId_osservatorio(coll.getAzienda().getIdOsservatorio());
				else
					amm.setId_osservatorio(ProfiloEnum.REGIONE_099);					
					
				app.setAmministrazione(amm);
				app.setDenominazione(coll.getUfficio().getDenominazione());
				app.setIdUfficio(coll.getUfficio().getIdUfficio());
				
				addStazioneAppaltante(app, ProfiloEnum.getEnumByProfilo(coll.getUfficio().getProfilo().toString()));
				
				//Diego
				if(ProfiloEnum.RASA.codice().toString().equalsIgnoreCase(coll.getUfficio().getProfilo().toString())) {
					
					caricaRasa=true;
				}
			}	
			
		}
	}
		
	public String getLogin() {
		return login;
	}

public boolean isCS() {
	// PP ritorno sempre false per non toccare il codice 
		// PP rimosso listaProfili.containsKey(ProfiloEnum.CS.value());
		return false; // PP rimosso ProfiloUtente.CS.equalsIgnoreCase(profilo);
	}
	
	public boolean isRUP() {
//PP		listaProfili.containsKey(ProfiloEnum.RUP.value());
		if (profilo==null) return false;
		return ProfiloEnum.RUP.codice().equals(profilo.codice());
	}

	// verifica che l'utente abbia un profilo RUP in almeno una delle collaborazioni
	// usato per l'inserimento automatico del RUP in rubrica
	public boolean hasRUP() {
		if (listaProfili==null) return false;
		return listaProfili.containsKey(ProfiloEnum.RUP.codice());
			}
	public boolean isAVLP() {
//PP		listaProfili.containsKey(ProfiloEnum.AVLP.value());
		if (profilo==null) return false;
		return ProfiloEnum.AVLP.codice().equals(profilo.codice());
	}
	
	public boolean isAmministratore() {
//PP listaProfili.containsKey(ProfiloEnum.AMMINISTRATORE.value());
		if (profilo==null) return false;
		return ProfiloEnum.AMMINISTRATORE.codice().equals(profilo.codice());
	}
	
	public boolean isRSSA() {
//		PP   listaProfili.containsKey(ProfiloEnum.RSSA.value());
		if (profilo==null) return false;
		return ProfiloEnum.RSSAOLD.codice().equals(profilo.codice());
	}

	public boolean isRSSAorRUP() {
		if (profilo==null) return false;
		return ProfiloEnum.RSSAOLD.codice().equals(profilo.codice()) || ProfiloEnum.RUP.codice().equals(profilo.codice());
	}

	public boolean isOssReg() {
		if (profilo==null) return false;
		
		return ProfiloEnum.OSSREG.codice().equals(profilo.codice())
		      || (SimogFlags.isOSSNActive() && ProfiloEnum.OSSNAZ.codice().equals(profilo.codice()));
			}
	
	public boolean isRASA() {
				if (profilo==null) return false;
				return ProfiloEnum.RASA.codice().equals(profilo.codice());
			}

	public void setLogin(String login) {
		this.login = login.toUpperCase();
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Hashtable getUffici() {
		return getUfficiByProfilo(this.profilo); // PPthis.listaUffici;
	}
	
	public Hashtable getAmministrazioni() {
		return getAmministrazioniByProfilo(this.profilo); // PPthis.listaAmministrazioni;
	}

	public Hashtable getProfili() {
		return this.listaProfili;
	}

	public Hashtable getUfficiByProfilo(ProfiloEnum profilo) {
		if(ProfiloEnum.RSSAOLD.equals(profilo)) {
			return this.listaUffici;
		} else if(ProfiloEnum.RUP.equals(profilo)){
			return this.listaUfficiRUP;
		} else {
			return new Hashtable(); // PP null;
		}
	}
	
	
	
	/*****************************************************************************************************
	 * Restituisce la lista di amministrazioni a seconda del profilo indicato 
	 * @param profilo : Stringa che indica il profilo 
	 * @return Hashtable
	 *****************************************************************************************************/
	public Hashtable getAmministrazioniByProfilo(ProfiloEnum profilo) {
		if(ProfiloEnum.RSSAOLD.equals(profilo)) {
			return this.listaAmministrazioni;
		} else if(ProfiloEnum.RUP.equals(profilo)){
			return this.listaAmministrazioniRUP;
		} else {
			return new Hashtable(); // PP null;
		}
	}
	
	/**
	 * @return Returns the profilo.
	 */
	public String getProfilo() {
		if (profilo==null) return "";
		return profilo.descrizione();
	}
	
	/**
	 * @return Returns the profilo enum.
	 */
	public ProfiloEnum getProfiloEnum() {
		return profilo;
	}
	

	/********************************************************************************************************
	 * restituisce il codice fiscale dell'amministrazione associata all'Id dell'ufficio
	 * @param idUfficio : Stringa per l'ID ufficio
	 * @return Stringa per il codice fiscale dell'amministrazione 
	 ********************************************************************************************************/
	public String getCodiceFiscaleAmministrazioneByIdUfficio( String idUfficio ) {
		if(listaUffici.containsKey(idUfficio))
			return ( (StazioneAppaltante) listaUffici.get(idUfficio) ).getCodiceFiscaleAmministrazione();
		else if(listaUfficiRUP.containsKey(idUfficio))
			return ( (StazioneAppaltante) listaUfficiRUP.get(idUfficio) ).getCodiceFiscaleAmministrazione();
		else return null;
	}

	
	
	/********************************************************************************************************
	 * Restituisce la denominazione dell'amministrazione associata la codice fiscale dell'aministrazione
	 * @param cfAmministrazione : Stringa per il codice fiscale
	 * @return String
	 ********************************************************************************************************/
	public String getDenomAmministrazByCf( String cfAmministrazione ) {
		if(listaAmministrazioni.containsKey(cfAmministrazione))
			return (String)listaAmministrazioni.get(cfAmministrazione);
		else if(listaAmministrazioniRUP.containsKey(cfAmministrazione))
			return (String)listaAmministrazioniRUP.get(cfAmministrazione);
		else return null;
	}
   
	/********************************************************************************************************
	 * Restituisce la denominazione in base all'ID dell'ufficio
	 * @param idUfficio : Stringa per l'id ufficio
	 * @return String
	 ********************************************************************************************************/
	public String getDenominazioneUfficioById ( String idUfficio ) {
		if(listaUffici.containsKey(idUfficio))
			return ( (StazioneAppaltante) listaUffici.get(idUfficio) ).getDenominazione();
		else if(listaUfficiRUP.containsKey(idUfficio))
			return ( (StazioneAppaltante) listaUfficiRUP.get(idUfficio) ).getDenominazione();
		else return null;
	}
	
	
	/********************************************************************************************************
	 * Restituisce l'id dell'osservatorio associato al codice fiscale dell'aministrazione
	 * @param cfAmministrazione : Stringa per il codice fiscale
	 * @return String
	 ********************************************************************************************************/
	public String getIdOssByCfAmm( String cfAmministrazione ) {
		if(listaOsservatori.containsKey(cfAmministrazione))
			return (String)listaOsservatori.get(cfAmministrazione);
		else return null;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

//	public CheckLoginType getXml() {
//		return xml;
//	}
//
//	public void setXml(CheckLoginType xml) {
//		this.xml = xml;
//	}
	


	public String getAdminOr() {
		return adminOr;
	}

	public void setAdminOr(String adminOr) {
		this.adminOr = adminOr;
	}



	public boolean isOrgano() {
		return isOrgano;
	}



	public void setOrgano(boolean isOrgano) {
		this.isOrgano = isOrgano;
	}



   /** cerca il tipoSA nella lista degli uffici del RUP
    * @param cf
    * @return
    */
   public String getTipoSA(String cf) {
      Hashtable <String, StazioneAppaltante> uffici = listaUfficiRUP;
      String retVal = null;
      
      for(StazioneAppaltante elem : uffici.values()){
         if(cf.equals(elem.getAmministrazione().getCodiceFiscale()) 
               && elem.getAmministrazione().getTipoSA() != null
               && !"".equals(elem.getAmministrazione().getTipoSA()))
            retVal = elem.getAmministrazione().getTipoSA();
         
            break;
      }
      
      return retVal;
   }



public ArrayList getListaRASA() {
	return listaRASA;
}



public boolean isCaricaRasa() {
	return caricaRasa;
}



public void setCaricaRasa(boolean caricaRasa) {
	this.caricaRasa = caricaRasa;
}



public String getDenomRasa() {
	return denomRasa;
}



public void setDenomRasa(String denomRasa) {
	this.denomRasa = denomRasa;
}



}
