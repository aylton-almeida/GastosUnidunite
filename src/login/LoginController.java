package login;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    public Button btnLogin;
    public PasswordField inputPass;
    public TextField inputUser;

    public void makeLogin() {
        inputUser.setText("hello world");
    }
}
