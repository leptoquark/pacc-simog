package it.mef.serviziCUP.rest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import it.mef.serviziCUP.rest.dto.CupRequest;
import it.mef.serviziCUP.rest.dto.CupResponse;

public class CupRestClient {

	private String url;

	public CupRestClient(String url) {
		this.url = url;
	}

	public CupResponse callRgs(CupRequest request) {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(this.url);
		CupResponse cP = null;

		Gson gson = new Gson();
		String stringJson = gson.toJson(request);
		StringEntity entity;
		try {
			entity = new StringEntity(stringJson);
			post.setEntity(entity);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(post);
			String respJson = EntityUtils.toString(response.getEntity());

			cP = gson.fromJson(respJson, CupResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cP;
	}
	
	public CupResponse callRgsMock() {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(this.url);
		CupResponse cP = null;

		Gson gson = new Gson();
		try {
			get.setHeader("Accept", "application/json");
			get.setHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(get);
			String respJson = EntityUtils.toString(response.getEntity());

			cP = gson.fromJson(respJson, CupResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cP;
	}
}
