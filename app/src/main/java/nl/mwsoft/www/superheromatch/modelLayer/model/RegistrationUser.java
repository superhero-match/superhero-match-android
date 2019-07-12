package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcelable;


public class RegistrationUser extends User implements Parcelable {

    private String email;
    private String password;
    private String confirmPassword;

    public RegistrationUser() {
    }

    public RegistrationUser(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
