package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NavBarController extends MainController implements Initializable {

    public JFXButton homeButton;
    public JFXButton productsButton;
    public JFXButton clientsButton;
    public JFXButton employeesButton;
    public JFXButton salesButton;
    public JFXButton expensesButton;
    public JFXButton totalsButton;
    public JFXButton accountButton;

    public void goToAccount(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(accountButton);
        loadCenterUI("/fxml/YourAccount.fxml");
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

    public void goToTotals(ActionEvent event) {
        showMsg(super.getLoggedUser().getEmail());
    }

    public void goToClients(ActionEvent event) {
        showLoader();
        clearNavBar();
        normalizeButton();
        highLightButton(clientsButton);
        loadCenterUI("/fxml/Clients.fxml");
        hideLoader();
    }

    public void goToHome(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        highLightButton(homeButton);
        loadCenterUI("/fxml/Home.fxml");
    }

    private void normalizeButton() {
        List<JFXButton> buttons = new ArrayList<>();
        buttons.add(homeButton);
        buttons.add(productsButton);
        buttons.add(salesButton);
        buttons.add(employeesButton);
        buttons.add(expensesButton);
        buttons.add(clientsButton);
        buttons.add(totalsButton);
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
        loadCenterUI("/fxml/Home.fxml");
    }
}
