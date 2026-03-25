package it.anticorruzione.ted.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.codec.binary.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.anticorruzione.ted.util.UtilityClass;

/**
 * Classe che gestisce le richieste e le risposte da e verso il TED
 *
 */
public class TEDConnection implements ITEDConnection {

	private ConnectionInfo connInfo;
	
	public TEDConnection()  {
		this.connInfo=new ConnectionInfo();
	}
	
	public TEDConnection(boolean test) {
		this.connInfo=new ConnectionInfo(test);
	}
	
	
   /**
    * Invoca il servizio REST per recuperare lo stato del notice su TED
    * @param submission_id codice del notice
    * @return oggetto json restituito da TED
    */
	public TEDNoticeInformation getNotice(String submission_id) {
		 Client client = Client.create();
	     WebResource webResource = client.resource(connInfo.getUrl()+submission_id);
	     ClientResponse response = webResource.header(HttpHeaders.AUTHORIZATION, "Basic "+UtilityClass.getEncodedAuth(connInfo.getUsername(),connInfo.getPassword()))
	        			.accept("application/json")
	                    .get(ClientResponse.class);
		
	        String output = response.getEntity(String.class);
	        System.out.println(output);
		return new TEDNoticeInformation(output);
	}
	
	/**
	 * Invoca il servizio REST per inoltrare il notice su TED
	 * @param encodedNotice il notice codificato
	 * @return oggetto json restituto da TED
	 */
	public TEDNoticeInformation submit(String encodedNotice) throws TEDErrorException {
		
		TEDNoticeInformation res = null;
		HttpURLConnection http = null;
		try {
			http = openConnection("application/x-www-form-urlencoded","POST",connInfo.getUrl()+"submit");

			String urlEncodedNotice = URLEncoder.encode(encodedNotice, "UTF-8");
			
			String data = "notice="+urlEncodedNotice;
			byte[] out = StringUtils.getBytesUtf8(data);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			if(http.getResponseCode()!=200) 
				throw new TEDErrorException(http.getResponseCode());
			
			BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
			  sb.append(output);
			} 
			http.disconnect();
			
			res = new TEDNoticeInformation(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(http!=null )
				http.disconnect();
		}
		
		return res;
	}

	@Override
	public boolean stopPublication(String submission_id) throws TEDErrorException, Exception  {
		HttpURLConnection http = null;
		try {
			http = openConnection("application/json","POST",connInfo.getUrl()+submission_id+"/stop-publication");
			
			if(http.getResponseCode()!=200) 
				throw new TEDErrorException(http.getResponseCode());
			
			BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
			  sb.append(output);
			} 
			http.disconnect();
			   
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if(http!=null )
				http.disconnect();
		}
		return true;
	}
	
	private HttpURLConnection openConnection(String contentType, String getPost, String urlTed) throws IOException {

			URL url = new URL(urlTed);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("accept", "application/json");
			http.setRequestProperty("authorization", "Basic "+UtilityClass.getEncodedAuth(connInfo.getUsername(),connInfo.getPassword()));
			http.setRequestProperty("Content-Type", contentType);

		
		return http;
	}
}
