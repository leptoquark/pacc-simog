package it.avcp.simog.auth;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.ProfiloEnum;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class RicercaProfiloRest {

	private final String P_CODICE_TIPO_STATO = "02";
	private final String P_CODICE_TIPO_PROFILO = "1";
	
	private String url_iam;
	
    private final String CODICEFISCALE = "codiceFiscale";
    private final String CODICETIPOSTATO = "codiceTipoStato";
    private final String CODICETIPOPROFILO = "codiceTipoProfilo";
	
    
    public RicercaProfiloRest(String url_iam) {
    	this.url_iam = url_iam;
    }
    
    public String callRicercaProfilo(String cfRup) {
    	String res = "";
    	
    	HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url_iam);
        
        // Create some NameValuePair for HttpPost parameters
        List<NameValuePair> arguments = new ArrayList<NameValuePair>(3);
        arguments.add(new BasicNameValuePair(CODICEFISCALE, cfRup));
        arguments.add(new BasicNameValuePair(CODICETIPOSTATO, P_CODICE_TIPO_STATO));
        arguments.add(new BasicNameValuePair(CODICETIPOPROFILO, P_CODICE_TIPO_PROFILO));
    	
        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            res = EntityUtils.toString(response.getEntity());
          
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        
    	return res;
    }
    
    
  /*  public static void main(String[] args) {
    	RicercaProfiloRest rpr = new RicercaProfiloRest("http://10.119.26.36:8080/avcp-iam-service-v1.0/rs/gestioneProfilo/ricercaProfilo");
    	String res = rpr.callRicercaProfilo("aaaaaa00a01h501z");
		Collaborazioni collaborazioni = rpr.getCollaborazioniFromXml(res);
        for(Collaborazione coll : collaborazioni.getCollaborazioni()) {
        	System.out.println(coll.getUfficio_id());
    }
    }*/
    
    public Collaborazioni getCollaborazioniFromXml(String xml) {
    	
    	DocumentBuilderFactory dbfaFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = dbfaFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Document doc = null;
		try {
			doc = documentBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		NodeList listItem = doc.getElementsByTagName("item");
		
		List<Collaborazione> listColl = new ArrayList<Collaborazione>();
		int index = 0;
		NodeList nodiDatiSA = null;
		for(int cItem=0;cItem<listItem.getLength();cItem++) {
			Node item = listItem.item(cItem);
			
			NodeList childsItem = item.getChildNodes();
			for(int x=0;x<childsItem.getLength();x++) {
			    Node nodo = childsItem.item(x);
				
				if(nodo.getNodeName().equals("datiStazioneAppaltante")) {
					NodeList temp = nodo.getChildNodes();
					for(int i=0;i<temp.getLength();i++) {
						Node saItem = temp.item(i);
						if(saItem.getNodeName().equals("datiStazioneAppaltante")) {
							nodiDatiSA = saItem.getChildNodes();
							break;
						}
					}
				}
				
				boolean isRup = false;
				if(nodo.getNodeName().equals("tipoProfilo")) {
					NodeList listaDatiProfilo = nodo.getChildNodes();
					for(int i=0;i<listaDatiProfilo.getLength();i++) {
						Node nodoProfilo = listaDatiProfilo.item(i);
						if(nodoProfilo.getNodeName().equals("codice") && ProfiloEnum.RUP.codice().equals(nodoProfilo.getTextContent())) {
							isRup=true;
							break;
						}
							
					}
				}
				
				if(isRup) {
					Collaborazione coll = new Collaborazione();
					for(int i=0;i<nodiDatiSA.getLength();i++) {
						Node child = nodiDatiSA.item(i);

							if("codice".equals(child.getNodeName()))
							   coll.setUfficio_id(child.getTextContent());
							else if("codiceFiscale".equals(child.getNodeName()))
								coll.setAzienda_codiceFiscale(child.getTextContent());
							else if("denominazione".equals(child.getNodeName()))
								coll.setUfficio_denominazione(child.getTextContent());
							else if("ragioneSociale".equals(child.getNodeName()))
								coll.setAzienda_denominazione(child.getTextContent());
							else if("codSezioneRegionale".equals(child.getNodeName())) 
								coll.setIdOsservatorio(String.format("%03d", Integer.parseInt(child.getTextContent())));
						}
	
						coll.setIndex(String.valueOf(index));
						coll.setUfficio_profilo(ProfiloEnum.RUP.codice());
						listColl.add(coll);
						index++;
						nodiDatiSA=null;
					}
				 }
			}
		
		Collaborazioni collaborazioni = new Collaborazioni();
		collaborazioni.setCollaborazioni(listColl.toArray(collaborazioni.getCollaborazioni()));
		return collaborazioni;
		
		}
						
    }
 
