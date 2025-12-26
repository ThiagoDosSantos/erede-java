package br.com.userede.erede;

public class AccessTokenResponse {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String scope;

    public String getAccessToken() {
        return access_token;
    }

    public String getTokenType() {
        return token_type;
    }

    public Long getExpiresIn() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }
    
    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public void setExpiresIn(Long expires_in) {
        this.expires_in = expires_in;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
