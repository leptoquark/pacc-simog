package it.avlp.simog.login;

import it.avlp.simog.login.iaa.IAALogin;
import it.avlp.simog.ws.xmlbeans.CheckLoginDocument;
import it.avlp.simog.login.local.LocalLogin;
import it.avlp.simog.login.remote.RemoteLogin;

import org.apache.log4j.Logger;

/**
 * - Layer -
 * 
 * Classe che si occupa della decisione e della istanziazione
 * oltre che della richiesta di login remota o locale
 * 
 * @author vletizia
 * @since 27/11/2009
 * @category Layer
 *
 */
public class LoginManager {

	private MasterLogin login;
	

	public enum TipoLogin {LOCALE, REMOTO, IAA};
		
	/**
	 * Il costruttore necessita di informazioni per creare la corretta istanza di uno
	 * degli oggetti di classe.
	 * 
	 * @param isLocalAuth
	 */
	public LoginManager(Logger logger, boolean isLocalAuth){
		if(isLocalAuth) this.login = new LocalLogin(logger);
		else this.login = new RemoteLogin(logger);
	}
	
	/**
	 * Nuovo costruttore per gestione login IAA
	 * 
	 * @param loginType
	 */
	public LoginManager(Logger logger, TipoLogin loginType){
		if(loginType.equals(TipoLogin.LOCALE)) 
			this.login = new LocalLogin(logger);
		else if(loginType.equals(TipoLogin.REMOTO)) this.login = new RemoteLogin(logger);
		else if(loginType.equals(TipoLogin.IAA)) this.login = new IAALogin(logger);
		else this.login = null;
	}

	/**
	 * Esegue il login e ritorna l'oggetto desiderato
	 * 
	 * @param objects
	 * @return
	 */
	public String login(String targetHost, String login, String password, String simogIdentifier) throws Exception{
		return this.login.login(targetHost, login, password, simogIdentifier);
	}
	
	/**
	 * Esegue il login e ritorna l'oggetto desiderato
	 * TICKET ALM - 3.04.3
	 * @param objects
	 * @return
	 */
	public String loginRPNT(String targetHost, String login, String password, String cfrup, String simogIdentifier) throws Exception{
		return this.login.loginRPNT(targetHost, login, password, cfrup,simogIdentifier);
	}

	public String getUserName() {
		return this.login.getUserName();
	}

	public String getCodAmm() {
		return this.login.getCodAmm();
	}

	public String getCodUff() {
		return this.login.getCodUff();
	}

	public CheckLoginDocument getCld() {
		return this.login.getCld();
	}

	public MasterLogin getLogin() {
		return login;
	}
}
