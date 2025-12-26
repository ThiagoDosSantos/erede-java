package br.com.userede.erede;

import com.google.gson.annotations.SerializedName;

public class OAuthEnvironment {

    private static final String PRODUCTION = "https://api.userede.com.br/redelabs/oauth2/token";
    private static final String SANDBOX = "https://rl7-sandbox-api.useredecloud.com.br/oauth2/token";

    @SerializedName("endpoint")
    private String endpoint;

    public OAuthEnvironment(String endpoint) {
        this.endpoint = endpoint;
    }

    public static OAuthEnvironment production() {
        return new OAuthEnvironment(OAuthEnvironment.PRODUCTION);
    }

    public static OAuthEnvironment sandbox() {
        return new OAuthEnvironment(OAuthEnvironment.SANDBOX);
    }
    
    public String getEndpoint() {
		return endpoint;
	}
}
