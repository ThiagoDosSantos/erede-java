package br.com.userede.erede;

public class OAuthStore {

    private OAuthEnvironment environment;
    private String clientId; // OLD PV
    private String clientSecret; // OLD CHAVE INTEGRACAO
    

    public OAuthStore(String clientId, String clientSecret, OAuthEnvironment environment) {
    	this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.environment = environment;
    }

    public OAuthStore(String filiation, String token) {
        this(filiation, token, OAuthEnvironment.production());
    }

    public OAuthEnvironment getEnvironment() {
        return environment;
    }

    public OAuthStore setEnvironment(OAuthEnvironment environment) {
        this.environment = environment;
        return this;
    }

    /**Old PV**/
    public String getClientId() {
		return clientId;
	}
    
    public void setClientId(String clientId) {
		this.clientId = clientId;
	}
    
    /**Old CHAVE INTEGRACAO**/
    public String getClientSecret() {
		return clientSecret;
	}
    
    public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
