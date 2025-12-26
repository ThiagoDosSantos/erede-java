package br.com.userede.erede.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import br.com.userede.erede.AccessTokenResponse;
import br.com.userede.erede.HttpUtils;
import br.com.userede.erede.OAuthStore;
import br.com.userede.erede.eRede;

public class OAuthService {
	
	private OAuthStore store;

	public OAuthService(OAuthStore store) {
		this.store = store;
	}
	
	public String generateAccessToken() {
	    
		// Base64(clientId:clientSecret)
	    String clientId = store.getClientId();
	    String clientSecret = store.getClientSecret();
	    String basic = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
	    
	    String tokenUrl = store.getEnvironment().getEndpoint();

	    HttpPost post = new HttpPost(tokenUrl);

	    post.addHeader(HttpHeaders.USER_AGENT, String.format(eRede.USER_AGENT, store.getClientId()));
	    post.addHeader(HttpHeaders.ACCEPT, "application/json");
	    post.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
	    post.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + basic);

	    // grant_type=client_credentials
	    post.setEntity(new UrlEncodedFormEntity(Arrays.asList(new BasicNameValuePair("grant_type", "client_credentials")), StandardCharsets.UTF_8));

	    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
	        HttpResponse httpResponse = client.execute(post);
	        int status = httpResponse.getStatusLine().getStatusCode();

	        String response = HttpUtils.parseResponse(httpResponse);

	        if (status < 200 || status >= 400) {
	           throw new RuntimeException("Erro ao gerar access token. Status=" + status + " Body=" + response);
	        }

	        AccessTokenResponse tokenResponse = new Gson().fromJson(response, AccessTokenResponse.class);

	        if (tokenResponse == null || tokenResponse.getAccessToken() == null || tokenResponse.getAccessToken().isBlank()) {
	            throw new RuntimeException("Resposta inválida ao gerar access token: " + response);
	        }

	        return tokenResponse.getAccessToken();
	    } catch (IOException e) {
	        throw new RuntimeException("Falha ao gerar access token", e);
	    }
	}
}
