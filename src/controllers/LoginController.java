package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MainController implements Initializable {
    public PasswordField inputPass;
    public TextField inputEmail;

    public void makeLogin(ActionEvent event) {
        showLoader();
        String userInput = inputEmail.getText();
        String passInput = inputPass.getText();
        if (!userInput.isEmpty() && passInput.length() >= 8) {
            try {
                if (new UserService().login(userInput, passInput)) {
                    clearScreen();
                    loadLeftUI("NavBar.fxml");
                    hideLoader();
                } else {
                    hideLoader();
                    showMsg("Email ou senha incorretos");
                }
            } catch (Exception e) {
                hideLoader();
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        } else {
            hideLoader();
            showMsg("Preencha os campos corretamente");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLeftUI("LogoLeftBar.fxml");
    }

}
