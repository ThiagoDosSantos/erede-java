package br.com.userede.erede;

import br.com.userede.erede.service.OAuthService;

public class OAuthRede {
		
	private OAuthService oAuthService;
	
	public OAuthRede(OAuthStore oAuthStore) {
		this.oAuthService = new OAuthService(oAuthStore);
	}

	/**Returns a new access_token*/
    public String generateAccessToken() {
    	return oAuthService.generateAccessToken();
    }
}
