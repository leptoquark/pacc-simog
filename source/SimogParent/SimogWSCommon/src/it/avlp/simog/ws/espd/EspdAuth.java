package it.avlp.simog.ws.espd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

//CEF-ESPD

public class EspdAuth {

	private String p_username;
	private String p_password;
	private String url_auth;
	
	private final String GRANT_TYPE="grant_type";
	private final String USERNAME="username";
	private final String PASSWORD="password";
	private final String SCOPE="scope";
	private final String READ_WRITE="read write";
	private final String AUTHORIZATION="Authorization";
	private final String V_AUTH="Basic UlVQX1dFQjpzZWNyZXQ=";
	
	
	public EspdAuth(String username, String password, String url_auth) {
		super();
		this.p_username = username;
		this.p_password = password;
		this.url_auth = url_auth;
	}
	
	
	public String getEspdToken() {

			HttpClient client = HttpClientBuilder.create().build();
	        HttpPost post = new HttpPost(url_auth);
	
	        // Create some NameValuePair for HttpPost parameters
	        List<NameValuePair> arguments = new ArrayList<NameValuePair>(3);
	        arguments.add(new BasicNameValuePair(USERNAME, p_username));
	        arguments.add(new BasicNameValuePair(PASSWORD, p_password));
	        arguments.add(new BasicNameValuePair(GRANT_TYPE, PASSWORD));
	        arguments.add(new BasicNameValuePair(SCOPE, READ_WRITE));
	        
	        post.addHeader(GRANT_TYPE, PASSWORD);
	        post.addHeader(AUTHORIZATION, V_AUTH);
	        
	        
	        try {
	            post.setEntity(new UrlEncodedFormEntity(arguments));
	            HttpResponse response = client.execute(post);
	
	            return EntityUtils.toString(response.getEntity());
	          
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "";
	        }
		
		}
	}
