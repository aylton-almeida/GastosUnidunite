package logic;

import exceptions.UserException;
import interfaces.Registrabel;

import java.io.*;


public class User implements Registrabel {

    private String email;
    private String password;
    private boolean isAdmin;
    private int id;

    public User(String email, String password, boolean isAdmin, int id) throws UserException {
        setEmail(email);
        setPassword(password);
        setIsAdmin(isAdmin);
        setId(id);
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

    private boolean isAdmin() {
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
    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream record = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(record);
        output.writeUTF(getEmail());
        output.writeUTF(getPassword());
        output.writeBoolean(isAdmin());
        output.writeInt(getId());
        return record.toByteArray();
    }

    @Override
    public Registrabel setByteArray(byte[] b) throws Exception {
        ByteArrayInputStream record = new ByteArrayInputStream(b);
        DataInputStream input = new DataInputStream(record);
        setEmail(input.readUTF());
        setPassword(input.readUTF());
        setIsAdmin(input.readBoolean());
        setId(input.readInt());
        return this;
    }
}
