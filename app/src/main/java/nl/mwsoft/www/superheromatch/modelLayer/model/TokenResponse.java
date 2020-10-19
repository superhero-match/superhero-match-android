package nl.mwsoft.www.superheromatch.modelLayer.model;

public class TokenResponse {

    private int status;
    private String accessToken;
    private String refreshToken;

    public TokenResponse() {
    }

    public TokenResponse(int status, String accessToken, String refreshToken) {
        this.status = status;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "status=" + status +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
