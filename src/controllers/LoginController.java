package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import logic.User;
import services.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends MainController implements Initializable {
    public JFXPasswordField inputPass;
    public JFXTextField inputEmail;

    public void makeLogin(ActionEvent event) {
        showLoader();
        String userInput = inputEmail.getText();
        String passInput = inputPass.getText();
        if (!userInput.isEmpty() && passInput.length() >= 8 && isEmailValid(inputEmail)) {
            try {
                User user = new UserService().login(userInput, passInput);
                if (user.getId() != -1) {
                    setLoggedUser(user);
                    clearScreen();
                    loadLeftUI("/fxml/NavBar.fxml");
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
        addRequiredValidator(inputEmail);
        addRequiredValidator(inputPass);
        loadLeftUI("/fxml/LogoLeftBar.fxml");
    }

}
