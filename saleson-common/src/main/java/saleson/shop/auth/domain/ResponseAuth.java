package saleson.shop.auth.domain;

import saleson.api.common.ApiResponse;

public class ResponseAuth extends ApiResponse {

    public ResponseAuth(boolean success, String message, String token) {
        super(success, message);
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
