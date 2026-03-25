/**
 * 
 */
package it.avlp.simog.login.local;


import it.avlp.simog.login.MasterLogin;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
/**
 * @author vletizia
 *
 * Nota: final per la sicurezza, la classe non e' ulteriormente implementabile
 */
public final class LocalLogin extends MasterLogin {

	public LocalLogin(Logger logger){
		this.logger = logger;
	}

	/* (non-Javadoc)
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected String subClassImplementationForLogin(String targetHost, String login, String password, String simogIdentifier) throws IOException {
		
		String loginResponse = new String();
		
		logger.info( "Impostazioni di Accesso Fittizio - non sara' invocato il WS remoto" );
		String filename = login + ".xml";
		BufferedInputStream buf = null;
		
		try{
			
			logger.debug( "cerco il file " + filename);
			URL l4jp = MasterLogin.class.getClassLoader().getResource(filename);
			if(l4jp == null) throw new IOException("File non trovato");
			buf = new BufferedInputStream ( l4jp.openStream() );

			StringBuilder st = new StringBuilder();
			while (buf.available()!=0) {
				st.append((char)buf.read());
			}
			loginResponse = st.toString();
	    	logger.debug( filename + " letto con successo [" + loginResponse + "]");
	    	
	    	// pp non chiudeva il file di login
	    	buf.close();
	    	buf = null;
	    	
		} catch(IOException ioe) {
	    	logger.warn("Impossibile trovare il file " + filename);
	    	logger.warn(ioe);
	    	throw ioe;
		}
    	
		return loginResponse;
	}

	/* (non-Javadoc)
	 * TICKET ALM - 3.04.3
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected String subClassImplementationForLoginRPNT(String targetHost, String login, String password, String cfrup,String simogIdentifier) throws IOException {
		
		String loginResponse = new String();
		
		logger.info( "Impostazioni di Accesso Fittizio - non sara' invocato il WS remoto" );
		String filename = login + ".xml";
		BufferedInputStream buf = null;
		
		try{
			
			logger.debug( "cerco il file " + filename);
			URL l4jp = MasterLogin.class.getClassLoader().getResource(filename);
			if(l4jp == null) throw new IOException("File non trovato");
			buf = new BufferedInputStream ( l4jp.openStream() );

			StringBuilder st = new StringBuilder();
			while (buf.available()!=0) {
				st.append((char)buf.read());
			}
			loginResponse = st.toString();
	    	logger.debug( filename + " letto con successo [" + loginResponse + "]");
	    	
	    	// pp non chiudeva il file di login
	    	buf.close();
	    	buf = null;
	    	
		} catch(IOException ioe) {
	    	logger.warn("Impossibile trovare il file " + filename);
	    	logger.warn(ioe);
	    	throw ioe;
		}
    	
		return loginResponse;
	}
	
}
