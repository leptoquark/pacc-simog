package it.avcp.anagrafe.AnagrafeWS;

import it.avlp.simog.beans.Amministrazione;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.massload.xmlbeans.CentroDiCostoType;
import it.avlp.simog.massload.xmlbeans.ListaCentriCostoType;
import it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestDocument;
import it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseDocument;
import it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseType;
import it.avlp.simog.massload.xmlbeans.LoginWSType;
import it.avlp.simog.massload.xmlbeans.StazioneAppaltanteType;
import it.avlp.simog.massload.xmlbeans.ListaCentroCostoResponseDocument.Factory;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class AnagrafeWSClient {

	private String user;
	private String pass;
	private URL url;
	protected Logger logger = null;

	public AnagrafeWSClient(URL url, String user, String pass, Logger logger) {
		this.user = user;
		this.pass = pass;
		this.url = url;
		this.logger = logger;
	}

	/**
	 * Richiede al WS Anagrafe la lista dei centri di costo per la Stazione
	 * appaltante
	 * 
	 * @param stazioneAppaltante
	 * @return lista centri di costo(oggetto stazione appaltante)
	 * @throws RemoteException
	 * @throws XmlException
	 * @throws ServiceException
	 */
	public List<StazioneAppaltante> getListaSA(
			Amministrazione stazioneAppaltante) throws RemoteException,
			XmlException, ServiceException {
		AVCPWSLocator locator = new AVCPWSLocator();

		AnagrafeWS proxy = locator.getAnagrafeWS(url);
		List<StazioneAppaltante> saList = new ArrayList<StazioneAppaltante>();

		/* Creazione oggetto di richiesta, imposto codice fiscale SA */
		ListaCentroCostoRequestDocument reqDoc = it.avlp.simog.massload.xmlbeans.ListaCentroCostoRequestDocument.Factory
				.newInstance();
		reqDoc.addNewListaCentroCostoRequest();
		reqDoc.getListaCentroCostoRequest().setCodiceFiscaleStazioneAppaltante(
				stazioneAppaltante.getCodiceFiscale());

		/* Creazione oggetto di login, imposto user e pass da simog.ini */
		LoginWSType login = it.avlp.simog.massload.xmlbeans.LoginWSType.Factory
				.newInstance();
		login.setUtente(user);
		login.setPassword(pass);
		reqDoc.getListaCentroCostoRequest().setLogin(login);
		reqDoc.getListaCentroCostoRequest().setExtra("1");

		/*rimuovo il namespace...*/
		XmlObject xobj = reqDoc.copy();
		
		logger.debug("originale req:" + reqDoc.xmlText());
		
		localizeXmlFragment(xobj);
		
		logger.debug("no namespace req:" + xobj.toString());
		
		
		String xmlResult = proxy.listaCentriCosto(xobj.toString());
		
		
		xmlResult = xmlResult.replace("<listaCentroCostoResponse>", "<simog:listaCentroCostoResponse xmlns:simog=\"xmlbeans.massload.simog.avlp.it\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">").replace("</listaCentroCostoResponse>", "</simog:listaCentroCostoResponse>");
		
		logger.debug("risposta ws:" +  xmlResult);
		/* Costruisco gli oggetti Java partendo dalla risposta xml... */
		
		/*Aggiungo il namespace...   */
		XmlOptions xox = new XmlOptions();
		//Map<String, String> m = new HashMap<String, String>();
		
		
//		m.put("", "xmlbeans.massload.simog.avlp.it");
//		xox.setLoadAdditionalNamespaces(m);
//		
//		xox.setLoadSubstituteNamespaces(m);
//		xox.setDocumentType(ListaCentroCostoResponseDocument.type);
		
//		xox.setSaveImplicitNamespaces(m);
		ListaCentroCostoResponseDocument ccResDoc = Factory.parse(xmlResult,xox);
		logger.debug("risposta ws modificata:" +  ccResDoc.xmlText());
		
		
		ListaCentroCostoResponseType ccResType = ccResDoc
				.getListaCentroCostoResponse();
		StazioneAppaltanteType saType = ccResType.getStazioneAppaltante();
		if (saType != null) { // esito positivo...
			Amministrazione amm = new Amministrazione(saType.getCf(),
					saType.getDenominazione());
			amm.setId_osservatorio(saType.getIdOsservatorio());
			
			ListaCentriCostoType cCTypes = ccResType.getListaCentriCosto();
			
			
			for (CentroDiCostoType ccType : cCTypes.getCentroDiCostoArray()) {
				
				StazioneAppaltante sa = new StazioneAppaltante();
				sa.setAmministrazione(amm);
				sa.setDenominazione(ccType.getUfficio());
				sa.setIdUfficio(ccType.getId());
				saList.add(sa);
			}
		}

		return saList;
	}

	private void localizeXmlFragment(XmlObject x) {
		String s;
		XmlCursor c = x.newCursor();
		c.toNextToken();
		while (c.hasNextToken()) {
			if (c.isNamespace())
				c.removeXml();
			else {
				if (c.isStart() || c.isAttr()) {
					s = c.getName().getLocalPart();
					c.setName(new QName(s));
				}
				c.toNextToken();
			}
		}
		c.dispose();
	}
	
	public static void main(String[] args) throws Exception {
		Amministrazione amm = new Amministrazione("12345678901", null);
		AnagrafeWSClient cli = new AnagrafeWSClient(new URL("http://192.168.30.34/AnagrafeWS/"), "RUUPAA00A01H501E","a", null);
		cli.getListaSA(amm);
	}
}
