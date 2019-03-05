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
            try {
                new ClientService().addClient(new Client(nameInput.getText(), adressInput.getText(), emailInput.getText(), phoneInput.getText()));
                showMsg("Client cadastrado com sucesso");
                clearMainArea();
                loadCenterUI("Clients.fxml");
            } catch (Exception e) {
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Preencha todos os campos corretamente");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
