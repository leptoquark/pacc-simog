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

public class RicercaProfiloRASA {

	private final String P_CODICE_TIPO_STATO = "02";
	private final String P_CODICE_TIPO_PROFILO = "19";
	
	private String url_iam;
	
    private final String CODICEFISCALE = "codiceFiscale";
    private final String CODICETIPOSTATO = "codiceTipoStato";
    private final String CODICETIPOPROFILO = "codiceTipoProfilo";
	
    private String denominazioniRasa="";
    
    public RicercaProfiloRASA(String url_iam) {
    	this.url_iam = url_iam;
    }
    
    public List<String> callRicercaProfiloAUSA(String cfRup) {
    	String xml = "";
    	
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

            xml = EntityUtils.toString(response.getEntity());
          
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
		
		
		NodeList listItem = doc.getElementsByTagName("cfCodPersonaGiuridicaCon");
		
		List<String> codiciFiscali = new ArrayList<String>();
		
		for(int cItem=0;cItem<listItem.getLength();cItem++) {
			Node item = listItem.item(cItem);
			String cf = item.getTextContent();
			codiciFiscali.add(cf);
		}
		
        listItem = doc.getElementsByTagName("ragioneSocialeSoggettoRappresentato");
		
		
		for(int cItem=0;cItem<listItem.getLength();cItem++) {
			Node item = listItem.item(cItem);
			String denom = item.getTextContent();
			denominazioniRasa+=denom+" ";
		}
		
		return codiciFiscali;
		
        
    }

	public String getDenominazioniRasa() {
		return denominazioniRasa;
	}
    
    
//    public static void main(String[] args) {
//    	RicercaProfiloRASA rpr = new RicercaProfiloRASA("http://10.119.26.36:8080/avcp-iam-service-v1.0/rs/gestioneProfilo/ricercaProfilo");
//    	List<String> collaborazioni = rpr.callRicercaProfiloAUSA("aaaaaa00a01h501z");
//        for(String coll : collaborazioni) {
//        	System.out.println(coll);
//    }
//    }
    
						
    }
 
