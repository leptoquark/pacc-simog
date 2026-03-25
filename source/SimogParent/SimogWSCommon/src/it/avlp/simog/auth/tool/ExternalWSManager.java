package it.avlp.simog.auth.tool;

import it.avcp.simog.auth.XmlManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.login.LoginManager;
import it.avlp.simog.util.CodiceControllo;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.xmlbeans.CheckLoginType;

import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
/**
 * classe che si occupa della richiesta
 * del login al ws remoto
 * 
 */
public class ExternalWSManager {
	private String XMLresponse;
	private CheckLoginType clt = null;
	private Logger logger;
	private String target;
	// nome del servizio avlp
	private String idAppl; 
	
	public ExternalWSManager(Logger logger,SimogProperties configuration){
		this.logger = logger;
		this.target = configuration.getWS_AUTH_TARGET_HOST();
		this.idAppl = configuration.getSIMOG_IDENTIFIER();
		
//		System.setProperty("javax.net.ssl.trustStore", configuration.getTrustorePath() );			
//		logger.info ( "Caricamento TrustStore [" + configuration.getTrustorePath() + "]" );
//		
//		System.setProperty("javax.net.ssl.trustStorePassword", configuration.getTrustStorePassword());

	}
	
	/**
	 * metodo per il login remoto o locale a seconda di come &egrave settato sul
	 * file di configurazione
	 * 
	 * @param login String
	 * @param password String
	 * @return boolean - esito operazione
	 * @throws SimogWSException
	 */
	public boolean login(String login,String password)throws SimogWSException{
		logger.debug("eseguendo: login("+login+","+ CodiceControllo.getCodiceControllo(password)+")");
		boolean success = false;
		String XMLtemp = AVLPlogin(login,password);
		if(verificaStato(XMLtemp)){
			setXMLresponse(XMLtemp);
			success = true;
		}else{
			success = false;
		}		
		return success;
	}
	/**
	 * chiama il parser per l'utente e verifica che lo stato sia >0
	 * 
	 */
	public boolean verificaStato(String XMLUtente)throws SimogWSException{
		logger.debug("method verificaStato(String XMLUtente)");
		boolean success = false;
		XmlManager xm = new XmlManager(logger);
		if(xm.validaXsd(XMLUtente)){
			if(xm.validaXml()){
				this.clt = xm.getXmlBean();
				success = true;
			}
		}
		return success;
	}
	/**
	 * setta la varibile locale privato perche la variabile � settabile solo
	 * dal metodo login
	 * 
	 */
	private void setXMLresponse(String XMLUtente){
		this.XMLresponse=XMLUtente;
	}
	/**
	 * ritorna il valore della variabile locale &quot;XMLresponse&quot;, non nulla solo se stato valido
	 * 
	 * @return String - stringa xml con la risposta del ws di autenticazione remota 
	 * @throws SimogWSException - se la variabile risulta nulla
	 */
	public String getXMLresponse()throws SimogWSException{
		if(this.XMLresponse != null){
			return this.XMLresponse;
		}else{
			logger.fatal("la stringa rappresentante l'xml di autenticazione risulta nulla");
			throw new SimogWSException(ErrorManager.SIMOGWS_EXTERNALMANAGER_NULL_01);
		}
	}
	/**
	 * Metodo che recupera il bean xml (di autenticazione) costruito tramite la stringa xml
	 * @return CheckLoginType - xmlbean che rappresenta l'xml ricevuto dall'xml remoto
	 * @throws SimogWSException se il bean risulta nullo
	 */
	public CheckLoginType getXmlBean()throws SimogWSException{
		if(this.clt != null){
			return this.clt;
		}else{
			logger.fatal("il bean xml di autenticazione risulta nullo");
			throw new SimogWSException(ErrorManager.SIMOGWS_EXTERNALMANAGER_NULL_02);
		}		
		
	
	}
	/**
	 * metodo che si connette al ws remoto(o lo simula) e ritorna l'xml
	 * 
	 * */
	private synchronized String AVLPlogin(String login,String password)throws SimogWSException{
		String XML = "";


		try{				
			LoginManager loginManager = new LoginManager(logger,SimogProperties.isLocalAuth(target) );
			XML = loginManager.login(target,login, password, this.idAppl);

		}catch(Exception e){
			// ATTENZIONE! RemoteException e' un sotto-tipo di IOException quindi deve stare prima
			if(e instanceof RemoteException)throw new SimogWSException(ErrorManager.SIMOGWS_EXTERNALMANAGER_REMOTE_04);
			else if(e instanceof IOException) throw new SimogWSException(ErrorManager.SIMOGWS_EXTERNALMANAGER_IO_03); 
		}

		if(XML != null && !XML.equals("")){
			return XML;
		}else{
			logger.fatal("la stringa rappresentante l'xml di autenticazione risulta nulla");
			throw new SimogWSException(ErrorManager.SIMOGWS_EXTERNALMANAGER_NULL_05);
		}
	}
}
