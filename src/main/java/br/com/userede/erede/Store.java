package br.com.userede.erede;

public class Store {

    private Environment environment;
    private String filiation; // clientId
    private String accessToken; // temporary access_token

    public Store(String filiation, String accessToken, Environment environment) {
    	this.filiation = filiation;
    	this.accessToken = accessToken;
		this.environment = environment;
    }

    public Store(String filiation, String accessToken) {
        this(filiation, accessToken, Environment.production());
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Store setEnvironment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public String getFiliation() {
        return filiation;
    }

    public Store setFiliation(String filiation) {
        this.filiation = filiation;
        return this;
    }
    
    public String getAccessToken() {
		return accessToken;
	}
    
    public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
