public class ResponseRegistrationModel {
private boolean success;

    private String accessToken;
    private String refreshToken;
    private UserRegistrationModel  user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public UserRegistrationModel getUser() {
        return user;
    }

    public void setUser(UserRegistrationModel user) {
        this.user = user;
    }

}
