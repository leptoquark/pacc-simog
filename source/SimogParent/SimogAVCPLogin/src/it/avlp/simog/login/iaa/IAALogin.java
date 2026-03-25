package it.avlp.simog.login.iaa;

import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.login.MasterLogin;
import it.avlp.simog.ws.xmlbeans.CheckLoginDocument;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import org.apache.log4j.Logger;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.xml.SAMLSchemaBuilder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IAALogin extends MasterLogin  {
		
	enum RuoloIAAEnum {

		RUOLO_ADM("ADMINSIMOG", ProfiloEnum.AMMINISTRATORE.codice())
		, RUOLO_RUP("RUP",ProfiloEnum.RUP.codice()) 
		, RUOLO_OSR("OSSR",ProfiloEnum.OSSREG.codice())
        , RUOLO_OSN("OSSN",ProfiloEnum.OSSNAZ.codice())
        , RUOLO_RASA("RASA",ProfiloEnum.RASA.codice())
		;
	    private String codIAA;
	    private String codSIMOG;
	    
	    public String codice() {return codIAA;}
	    public String codSIMOG() {return codSIMOG;}
	    
	    RuoloIAAEnum(String codIAA, String codSIMOG){
	    	this.codIAA = codIAA;
	    	this.codSIMOG = codSIMOG;
	    }
	    
	    public static RuoloIAAEnum getEnumByProfilo(String profilo) 
	    {
	    	RuoloIAAEnum lista [] = values();
	    	for(int i=0; i<lista.length;i++) {
	    		if (lista[i].codice().equals(profilo))
	    			return lista[i];
	    	}
	    	return null;
	    }
	} 
	
	public IAALogin(Logger logger){
		this.logger = logger;
		this.cld = CheckLoginDocument.Factory.newInstance();
	}

	/* (non-Javadoc)
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 *
	 * targetHost contiene il nome del certifcato saml (da configurazione) l'asserzione SAML si trova nel parametro login, gli altri non sono usati
	 */
	protected String subClassImplementationForLogin(String targetHost, String login, String password, String simogIdentifier) throws Exception {
		try {
			return SAMLLogin(targetHost, login);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * TICKET ALM - 3.04.3
	 * targetHost contiene il nome del certifcato saml (da configurazione) l'asserzione SAML si trova nel parametro login, gli altri non sono usati
	 */
	protected String subClassImplementationForLoginRPNT(String targetHost, String login, String password, String cfrup, String simogIdentifier) throws Exception {
		try {
			return SAMLLogin(targetHost, login);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String SAMLLogin(String certName, String xmlAssertion) throws Exception  {
		try
		{
			byte[] tmpBytes = Base64.decode (xmlAssertion);
			String decodedAssertion = "";

			if(tmpBytes==null)
				decodedAssertion = xmlAssertion;
			else
				decodedAssertion = new String(tmpBytes);
// logger.debug("*** DECODE SAML: " + decodedAssertion);			
			DefaultBootstrap.bootstrap(); 
			
			Schema schema = SAMLSchemaBuilder.getSAML11Schema();

			//get parser pool manager
			BasicParserPool parserPoolManager = new BasicParserPool();
			parserPoolManager.setNamespaceAware(true);
			parserPoolManager.setIgnoreElementContentWhitespace(true);
			parserPoolManager.setSchema(schema);
		
			InputStream is = new ByteArrayInputStream(decodedAssertion.getBytes("UTF-8"));
			Document document = parserPoolManager.parse(is);
			Element metadataRoot = document.getDocumentElement();

			QName qName= new QName(metadataRoot.getNamespaceURI(), metadataRoot.getLocalName(), metadataRoot.getPrefix());

			//get an unmarshaller
			Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(qName);

			//unmarshall using the document root element
			Response response = (Response)unmarshaller.unmarshall(metadataRoot);

			// se non è definito il certificato salto questa fase
// fuligni 16.04.2014 per ora non si attiva!
//			if (certName != null && !"".equals(certName)){
//	         //grab the certificate file
//	         InputStream isc = MasterLogin.class.getClassLoader().getResourceAsStream(certName);
//	         //File certificateFile = new File(Riscossione.SAML_CERTIFICATE);
//
//	         //get the certificate from the file
//	         //InputStream inputStream2 = new FileInputStream(certificateFile);
//	         CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//	         X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(isc);
//	         isc.close();
//
//	         //pull out the public key part of the certificate into a KeySpec
//	         X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(certificate.getPublicKey().getEncoded());
//
//	         //get KeyFactory object that creates key objects, specifying RSA
//	         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//	         //generate public key to validate signatures
//	         PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
//
//	         //create credentials
//	         BasicX509Credential publicCredential = new BasicX509Credential();
//
//	         //add public key value
//	         publicCredential.setPublicKey(publicKey);
//
//	         //create SignatureValidator
//	         SignatureValidator signatureValidator = new SignatureValidator(publicCredential);
//
//	         //get the signature to validate from the response object
//	         Signature signature = response.getSignature();
//
//	         //try to validate
//	         try
//	         {
//	            //System.out.println("Verifico il certificato");
//	            signatureValidator.validate(signature);
//	            //if (true)    throw new ValidationException("Eccezione di test");
//	         }
//	         catch (ValidationException ve)
//	         {
//	            throw new Exception("Certificato SAML non valido!");
//	         }
//			}
			
			Assertion assertion  = response.getAssertions().get(0);

			String username = null;
			String codFiscale = null;
			String codPersonaGiuridica = null;
			String tipologia = null;
			String ragioneSociale = null;
			String codSedeImpresa = null;
			String codCentroCosto = null;
			String indirizzo = null;
			String ruolo = null;
			String funzioni = null;
			String codSogCon = null;
			String email = null;
			String fullName = null;
			String adminOr = null;
			String cognome = null;
			String nome = null;
			String idOsservatorio = null;
			String cfAmm = null;
			String idUfficio = null;
			
			String valori = "" ; // per debug
			
			//loop through the nodes to get what we want
			List<AttributeStatement> attributeStatements = assertion.getAttributeStatements();
			for (int i = 0; i < attributeStatements.size(); i++)
			{
				List<Attribute> attributes = attributeStatements.get(i).getAttributes();
				for (int x = 0; x < attributes.size(); x++)
				{
					String strAttributeName = attributes.get(x).getDOM().getAttribute("Name");

					List<XMLObject> attributeValues = attributes.get(x).getAttributeValues();
					for (int y = 0; y < attributeValues.size(); y++)
					{
						//String strAttributeValue = attributeValues.get(y).getDOM().getTextContent();
						Object temp = attributeValues.get(y).getDOM().getFirstChild();
						
						String strAttributeValue = null;
						
						if(temp!=null)
							strAttributeValue = attributeValues.get(y).getDOM().getFirstChild().getNodeValue();
						
						// System.out.println(strAttributeName + ": " + strAttributeValue);
						
						// sembrerebbe che l'asserzione sia codificata in ISO-8859-1 (nonostante l'encoding 
						// dichiarato nel tag xml sia UTF-8) 
						if(strAttributeValue != null)
							strAttributeValue = new String(strAttributeValue.getBytes("ISO-8859-1"), "UTF-8");
						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.USERNAME))
							username = strAttributeValue;
						if(strAttributeName.equalsIgnoreCase(IAACostanti.CODICE_FISCALE))
							codFiscale = strAttributeValue;
						if(strAttributeName.equalsIgnoreCase(IAACostanti.COD_PERSONA_GIURIDICA))
							codPersonaGiuridica = strAttributeValue;
						if(strAttributeName.equalsIgnoreCase(IAACostanti.TIPOLOGIA))
							tipologia = strAttributeValue;
						if(strAttributeName.equalsIgnoreCase(IAACostanti.RAGIONE_SOCIALE))
							ragioneSociale = strAttributeValue;						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.COD_SEDE_IMPRESA))
							codSedeImpresa = strAttributeValue;						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.COD_CENTRO_COSTO))
							codCentroCosto = strAttributeValue;						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.INDIRIZZO))
							indirizzo = strAttributeValue;						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.RUOLO))
							ruolo = strAttributeValue;						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.COD_SOGGETTO_CON))
							codSogCon = strAttributeValue;		
						if(strAttributeName.equalsIgnoreCase(IAACostanti.EMAIL))
							email = strAttributeValue;		
						if(strAttributeName.equalsIgnoreCase(IAACostanti.FUNZIONE))
							funzioni = strAttributeValue;		
						if(strAttributeName.equalsIgnoreCase(IAACostanti.FULLNAME))
							fullName = strAttributeValue;		

						// admin or impostato a 000 se manca nell'asserzione
						if(strAttributeName.equalsIgnoreCase(IAACostanti.ADMINOR))
							adminOr = strAttributeValue == null ? ProfiloEnum.REGIONE_ZERO : strAttributeValue;	
						
						if(strAttributeName.equalsIgnoreCase(IAACostanti.COGNOME))
							cognome = strAttributeValue;		
						if(strAttributeName.equalsIgnoreCase(IAACostanti.NOME))
							nome = strAttributeValue;	
						
						valori += "[" + strAttributeName + ":" + strAttributeValue + "] " ;
					}
				}
			}

			// debug
			if(!"".equals(valori))
			   logger.debug ("*** SAML: " + valori);
			
			// se il ruolo è null non posso fare nulla!
			if(ruolo == null || decodificaRuolo(ruolo) == null)
			   throw (new Exception(Messaggi.SIMOG_LOGIN_002 + ": CODICE RUOLO (" + (ruolo == null ? "null" : ruolo) + ") mancante o non valido"));
			
			// resetto admin_or se non è OSSR
      			if (!ruolo.equals(RuoloIAAEnum.RUOLO_OSR.codice()) && !ruolo.equals(RuoloIAAEnum.RUOLO_OSN.codice()))
      				adminOr = ProfiloEnum.REGIONE_ZERO;
      			else if (ruolo.equals(RuoloIAAEnum.RUOLO_OSN.codice()))
      			    adminOr = ProfiloEnum.REGIONE_999;
			
			// aggiustamento valori per ruolo amministratore e ossr che non ha collaborazioni
			if (ruolo.equals(RuoloIAAEnum.RUOLO_ADM.codice()) || (!"".equals(adminOr) && !"000".equals(adminOr))){
				if(ragioneSociale == null) ragioneSociale = IAACostanti.DUMMY_VAL;
				if(idOsservatorio == null) idOsservatorio = IAACostanti.DUMMY_VAL;
				if(indirizzo == null) indirizzo = IAACostanti.DUMMY_VAL;
			}
			// creazione xml
			
			if(indirizzo == null) indirizzo = IAACostanti.DUMMY_VAL;
			
			cld.addNewCheckLogin();
			
			cld.getCheckLogin().addNewSoggetto();
			cld.getCheckLogin().getSoggetto().setAdminOr(adminOr);
			cld.getCheckLogin().getSoggetto().setCognome(cognome);
			cld.getCheckLogin().getSoggetto().setEmail(email);
			cld.getCheckLogin().getSoggetto().setNome(nome);
			 cld.getCheckLogin().setStato("1");
			 
			// aggiungo la collaborazione per il RUP e admin che non ne hanno
			// mentre per osservatorio non serve
			if(ruolo.equals(RuoloIAAEnum.RUOLO_RUP.codice()) || ruolo.equals(RuoloIAAEnum.RUOLO_ADM.codice())){
      			cld.getCheckLogin().addNewCollaborazioni();
      			cld.getCheckLogin().getCollaborazioni().addNewCollaborazione();
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).setIndex("0");
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).addNewAzienda();
      				
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setDenominazione(ragioneSociale);
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setCodiceFiscale(cfAmm);
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setIdOsservatorio(idOsservatorio);
      	
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).addNewUfficio();
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setDenominazione(indirizzo);
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setIdUfficio(idUfficio);
      			cld.getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setProfilo(decodificaRuolo(ruolo));
                cld.getCheckLogin().setStato("1");
			}
			
			// codice fiscale dell'utente che accedde
			this.userName = codFiscale;
			this.codAmm = codPersonaGiuridica;
			this.codUff = codCentroCosto;
			
			return cld.xmlText();
			
		}
		catch (Exception ex)
		{
		    logger.fatal("*** eccezione in lettura SAML: " + ex.getMessage());
		    logger.fatal("*** SAML: " + xmlAssertion);
			throw ex;
		}
	}
	/*
	 * decodifica il ruolo che viene da IAA
	 */
	private String decodificaRuolo(String ruolo){
		
		if(ruolo == null)
		   return null;
		
		if (ruolo.equals(RuoloIAAEnum.RUOLO_OSR.codice()))
			return(ProfiloEnum.OSSREG.codice());
		else if (ruolo.equals(RuoloIAAEnum.RUOLO_ADM.codice()))
			return(ProfiloEnum.AMMINISTRATORE.codice());
		else if (ruolo.equals(RuoloIAAEnum.RUOLO_RUP.codice()))
			return(ProfiloEnum.RUP.codice());
		else if (SimogFlags.isOSSNActive()&& ruolo.equals(RuoloIAAEnum.RUOLO_OSN.codice()))
           return(ProfiloEnum.OSSNAZ.codice());
		else if (ruolo.equals(RuoloIAAEnum.RUOLO_RASA.codice()))
			return(ProfiloEnum.RASA.codice());
		else
			return null;
	}
}
