public class ResponseUserModel {
    private boolean success;
    private UserRegistrationModel user;

    public UserRegistrationModel getUser() {
        return user;
    }

    public void setUser(UserRegistrationModel user) {
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
