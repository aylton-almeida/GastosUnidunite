package logic;

import exceptions.UserException;


public class User {

    private String email;
    private String password;
    private boolean isAdmin;
    private int id;

    public User(String email, String password, boolean isAdmin) throws UserException {
        setEmail(email);
        setPassword(password);
        setIsAdmin(isAdmin);
        setId(-1);
    }

    public User() {
        setEmail("");
        this.password = "12345678";
        setIsAdmin(false);
        setId(-1);
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

    public void setPassword(String password) throws UserException {
        if (password.length() >= 8)
            this.password = password;
        else
            throw new UserException("Senha invalida");
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.email + ", " + this.password;
    }
}
