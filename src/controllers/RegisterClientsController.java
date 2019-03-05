package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import logic.Client;
import services.ClientService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClientsController extends MainController implements Initializable {
    public JFXTextField nameInput;
    public JFXTextField phoneInput;
    public JFXTextField adressInput;
    public JFXTextField emailInput;

    public void registerClient(ActionEvent event) {
        if (!nameInput.getText().isEmpty() && !adressInput.getText().isEmpty() && !phoneInput.getText().isEmpty()) {
            if (!emailInput.getText().isEmpty()) {
                if (isEmailValid(emailInput)) {
                    register();
                } else {
                    showMsg("Digite um email válido");
                }
            } else {
                register();
            }
        } else showMsg("Preencha todos os campos corretamente");
    }

    private void register() {
        try {
            new ClientService().addClient(new Client(nameInput.getText(), adressInput.getText(), emailInput.getText(), phoneInput.getText()));
            showMsg("Cliente cadastrado com sucesso");
            clearMainArea();
            loadCenterUI("Clients.fxml");
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNumberValidator(phoneInput);
        addRequiredValidator(nameInput);
        addRequiredValidator(phoneInput);
        addRequiredValidator(adressInput);
    }
}
