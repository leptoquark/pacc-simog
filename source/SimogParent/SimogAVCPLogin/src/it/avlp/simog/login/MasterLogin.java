/**
 * 
 */
package it.avlp.simog.login;

import it.avlp.simog.ws.xmlbeans.CheckLoginDocument;

import org.apache.log4j.Logger;



/**
 * 
 * Classe che rappresenta le operazione di login
 * 
 * @author vletizia
 *
 */
public abstract class MasterLogin {

	protected Logger logger;
	
	protected String userName;
	protected CheckLoginDocument cld; 
	protected String codAmm;
	protected String codUff;

//	protected MasterLogin(Logger logger){}
	/**
	 * Metodo che si occupa delle operazioni di login
	 * 
	 * @param objects
	 * @return
	 */
	public String login(String targetHost, String login, String password, String simogIdentifier)  throws Exception{ 
		return this.subClassImplementationForLogin(targetHost, login, password, simogIdentifier);
	}
	
	/**
	 * Metodo che si occupa delle operazioni di login
	 * TICKET ALM - 3.04.3
	 * @param objects
	 * @return
	 */
	public String loginRPNT(String targetHost, String login, String password,String cfrup, String simogIdentifier)  throws Exception{ 
		return this.subClassImplementationForLoginRPNT(targetHost, login, password, cfrup, simogIdentifier);
	}
	
	/**
	 * Siccome e' astratto le sottoclassi devono implementarlo con il codice
	 * specifico del comportamento della classe con la quale si e' istanziato l'oggetto
	 * corrente.
	 * 
	 * @param objects
	 * @return
	 */
	protected abstract String subClassImplementationForLogin(String targetHost, String login, String password, String simogIdentifier) throws Exception;
	protected abstract String subClassImplementationForLoginRPNT(String targetHost, String login, String password, String cfrup, String simogIdentifier) throws Exception;

	public String getUserName() {
		return userName;
	}

	public CheckLoginDocument getCld() {
		return cld;
	}

	public void setCld(CheckLoginDocument cld) {
		this.cld = cld;
	}
	
	public void addAmmInfo (String cfAmm, String idOss, String idUff, String denUff){
		if (cld.getCheckLogin().getCollaborazioni() !=null){
			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setCodiceFiscale(cfAmm);
			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setIdOsservatorio(idOss);
			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setIdUfficio(idUff);
			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setDenominazione(denUff);
		}
	}

	public String getCodAmm() {
		return codAmm;
	}

	public String getCodUff() {
		return codUff;
	}
}
