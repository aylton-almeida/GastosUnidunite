package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
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

    public void checkNumber(KeyEvent keyEvent) {
        JFXTextField field = ((JFXTextField)keyEvent.getSource());
        System.out.println(keyEvent.getCharacter());
        if (!keyEvent.getCharacter().matches("[0-9]") && !keyEvent.getCharacter().matches(".")){
            System.out.println("ok");
            if (field.getText().length() == 1){
                field.setText("");
            }else{
                if (field.getText().length() > 0){
                    String input = field.getText();
                    field.setText(input.substring(0, input.length() - 1));
                }
            }
        }
        field.positionCaret(field.getText().length());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
