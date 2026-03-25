package it.avcp.spc.appalti.ejb.servizi;


import it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;



public class ContributoClient {
	private final static String JNDI_KEY_SERVER_NAME = "avcp.gateway.jar";
	private InitialContext fInitalContext = null;
	private String fJ2EEServerName = "AppaltiEar";
	private String GATEWAY_APPALTI_CLIENT_CONF_PATH = "AppaltiClient";

//	private SecurityDomain securityDomain = new SecurityDomain();
	
	public ContributoClient() {
	}
	
//	public void securityDomainLogin(String username, String password) throws LoginException {
//		this.securityDomain.login(username, password);
//	}
//	public void securityDomainLogout() throws LoginException {
//		this.securityDomain.logout();
//	}

//	public Object getServiceAppalti(String nomeejb) throws Exception {
//		try {
//			// ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
//			// Class miaclasse = myClassLoader.loadClass(nomeejb);
//			// Object service = miaclasse.newInstance();
//			Object service = null;
//			if (loadJNDIProperties() == true) {
//				String JNDI_NAMING_LOOKUP_PREFIX = fJ2EEServerName + "/";
//				String JNDI_NAMING_LOOKUP_SUFFIX = "/remote";
//				// String lJNDILookup = JNDI_NAMING_LOOKUP_PREFIX +
//				// "GatewayBean" + JNDI_NAMING_LOOKUP_SUFFIX;
//				String lJNDILookup = JNDI_NAMING_LOOKUP_PREFIX + nomeejb.substring(nomeejb.lastIndexOf('.') + 1) + "Bean" + JNDI_NAMING_LOOKUP_SUFFIX;
//				System.out.println("lJNDILookup" + lJNDILookup);
//				// try
//				// {
//				// NamingEnumeration<Binding> ne =
//				// fInitalContext.listBindings("");
//
//				service = fInitalContext.lookup(lJNDILookup);
//				return service;
//			} else {
//				System.out.println("Cannot load JNDI Settings");
//				throw new Exception();
//			}
//		} catch (NamingException e) {
//			e.printStackTrace();
//			throw new Exception();
//		}
//
//		/*
//		 * catch (ClassNotFoundException e) { e.printStackTrace(); throw new
//		 * Exception(); } catch (InstantiationException e) {
//		 * e.printStackTrace(); throw new Exception(); }
//		 */
//		catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception();
//		}
//
//		// return pippo;
//	}

	private Object getService(String nomeejb) throws Exception {
		try {
			// ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
			// Class miaclasse = myClassLoader.loadClass(nomeejb);
			// Object service = miaclasse.newInstance();
			Object service = null;
			if (loadJNDIProperties() == true) {
				String JNDI_NAMING_LOOKUP_PREFIX = fJ2EEServerName + "/";
				String JNDI_NAMING_LOOKUP_SUFFIX = "/remote";
				// String lJNDILookup = JNDI_NAMING_LOOKUP_PREFIX +
				// "GatewayBean" + JNDI_NAMING_LOOKUP_SUFFIX;
				String lJNDILookup = JNDI_NAMING_LOOKUP_PREFIX + nomeejb.substring(nomeejb.lastIndexOf('.') + 1) + "Bean" + JNDI_NAMING_LOOKUP_SUFFIX;
				System.out.println("lJNDILookup " + lJNDILookup);
				// try
				// {
				// NamingEnumeration<Binding> ne =
				// fInitalContext.listBindings("");

				service = fInitalContext.lookup(lJNDILookup);
				return service;
			} else {
				System.out.println("Cannot load JNDI Settings");
				throw new Exception();
			}
		} catch (NamingException e) {
			e.printStackTrace();
			throw new Exception();
		}

		/*
		 * catch (ClassNotFoundException e) { e.printStackTrace(); throw new
		 * Exception(); } catch (InstantiationException e) {
		 * e.printStackTrace(); throw new Exception(); }
		 */
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		// return pippo;
	}
		// metodo che carica le informazioni dal file jndi.properties
	private boolean loadJNDIProperties() {
		boolean lResult = false;

		Properties lJNDIProperties = new Properties();

		try {
			String path = null;
			path = "/opt/SIMOG/" + "jndi.properties";
			FileInputStream fis = new FileInputStream(path);
			lJNDIProperties.loadFromXML(fis);

			fInitalContext = new InitialContext(lJNDIProperties);

			Object lObjServerName = lJNDIProperties.get(JNDI_KEY_SERVER_NAME);
			if (lObjServerName != null) {
				fJ2EEServerName = lObjServerName.toString();
				lResult = true;
			} else {
				System.out.println("Chiave: '" + JNDI_KEY_SERVER_NAME + "' in jndi.properties non trovato.");
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return lResult;
	}
	
	public RicercaContributoTo determinazioneContributoOE(String codiceFiscale, BigDecimal importo, Date dataPubbl,
			String motivoEscusione, String tipoProcedura, String accordoQuadro,String applicazione, long idGara)throws Exception{
		try{
			GestioneContributoWrapper service = (GestioneContributoWrapper)getService(GestioneContributoWrapper.class.getName());

			return service.determinazioneContributoOE(codiceFiscale, importo, dataPubbl, motivoEscusione, tipoProcedura,  accordoQuadro,applicazione,idGara);

		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	public RicercaContributoTo determinazioneContributoSA(String codiceFiscale, BigDecimal importo, Date dataPubbl,
			String motivoEscusione, String tipoProcedura, String accordoQuadro,String applicazione, long idGara)throws Exception{
		try{
			GestioneContributoWrapper service = (GestioneContributoWrapper)getService(GestioneContributoWrapper.class.getName());

			return service.determinazioneContributoSA(codiceFiscale, importo, dataPubbl, motivoEscusione, tipoProcedura,  accordoQuadro,applicazione,idGara);

		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	public RicercaContributoTo listaDeterminazioneContributoOE(String codiceFiscale, List<BigDecimal> importo, Date dataPubbl,
			String motivoEscusione, String tipoProcedura, String accordoQuadro,String applicazione)throws Exception{
		try{
			GestioneContributoWrapper service = (GestioneContributoWrapper)getService(GestioneContributoWrapper.class.getName());

			return service.listaDeterminazioneContributoOE(codiceFiscale, importo, dataPubbl, motivoEscusione, tipoProcedura,  accordoQuadro,applicazione);

		}catch (Exception e) {
			throw new Exception(e);
		}
		
	}
	public RicercaContributoTo listaDeterminazioneContributoSA(String codiceFiscale, List<BigDecimal> importo, Date dataPubbl,
			String motivoEscusione, String tipoProcedura, String accordoQuadro,String applicazione)throws Exception{
		try{
			GestioneContributoWrapper service = (GestioneContributoWrapper)getService(GestioneContributoWrapper.class.getName());

			return service.listaDeterminazioneContributoSA(codiceFiscale, importo, dataPubbl, motivoEscusione, tipoProcedura,  accordoQuadro,applicazione);

		}catch (Exception e) {
			throw new Exception(e);
		}
	}
}