package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public void goToRegisterProductButton(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterProducts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
