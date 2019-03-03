package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import logic.Client;
import logic.Product;
import services.ClientService;
import services.ProductService;

public class RegisterClientsController extends MainController {

    public JFXTextField nameInput;
    public JFXTextField adressInput;
    public JFXTextField phoneInput;
    public JFXTextField emailInput;

    public void registerClient(ActionEvent event) {
        if (!nameInput.getText().isEmpty() && !adressInput.getText().isEmpty() && !phoneInput.getText().isEmpty()) {
            try {
                new ClientService().addClient(new Client(nameInput.getText(), adressInput.getText(), emailInput.getText(), Integer.parseInt(phoneInput.getText())));
                showMsg("Client cadastrado com sucesso");
                clearMainArea();
                loadCenterUI("Clients.fxml");
            } catch (Exception e) {
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Preencha todos os campos corretamente");
    }
}
