package core;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String password;
    private boolean isAdmin;
    private int code;

    public User (String email, String password, boolean isAdmin, int code) {
        setEmail(email);
        setPassword(password);
        setIsAdmin(isAdmin);
        setCode(code);
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
