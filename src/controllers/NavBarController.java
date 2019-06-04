package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NavBarController extends MainController implements Initializable {

    public JFXButton productsButton;
    public JFXButton clientsButton;
    public JFXButton employeesButton;
    public JFXButton salesButton;
    public JFXButton expensesButton;
    public JFXButton accountButton;
    public JFXButton generalButton;

    public void goToAccount(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(accountButton);
        loadCenterUI("/fxml/YourAccount.fxml");
    }

    public void goToGeneral(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(generalButton);
        loadCenterUI("/fxml/General.fxml");
    }

    public void goToProducts(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(productsButton);
        loadCenterUI("/fxml/Products.fxml");
    }

    public void goToSales(ActionEvent event) {
        showLoader();
        clearNavBar();
        normalizeButton();
        highLightButton(salesButton);
        loadCenterUI("/fxml/Sales.fxml");
        hideLoader();
    }

    public void goToExpenses(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(expensesButton);
        loadCenterUI("/fxml/Expenses.fxml");
    }

    public void goToEmployees(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(employeesButton);
        loadCenterUI("/fxml/Employees.fxml");
    }

    public void goToClients(ActionEvent event) {
        showLoader();
        clearNavBar();
        normalizeButton();
        highLightButton(clientsButton);
        loadCenterUI("/fxml/Clients.fxml");
        hideLoader();
    }

    private void normalizeButton() {
        List<JFXButton> buttons = new ArrayList<>();
        buttons.add(productsButton);
        buttons.add(salesButton);
        buttons.add(employeesButton);
        buttons.add(expensesButton);
        buttons.add(clientsButton);
        buttons.add(generalButton);
        buttons.stream()
                .filter(button -> button.buttonTypeProperty().get() == com.jfoenix.controls.JFXButton.ButtonType.RAISED)
                .forEach(button -> {
                    button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.FLAT);
                    button.setStyle("-fx-background-color: #3c8fdc;");
                });
    }

    private void highLightButton(JFXButton button){
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #3A7CF3;");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCenterUI("/fxml/General.fxml");
    }
}
