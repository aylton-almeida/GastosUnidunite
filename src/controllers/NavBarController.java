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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCenterUI("Home.fxml");
    }

    public void goToProducts(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        productsButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        productsButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Products.fxml");
    }

    public void goToSales(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        salesButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        salesButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Sales.fxml");
    }

    public void goToExpenses(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        expensesButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        expensesButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Expenses.fxml");
    }

    public void goToEmployees(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        employeesButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        employeesButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Employees.fxml");
    }

    public void goToTotals(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        totalsButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        totalsButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Totals.fxml");
    }

    public void goToClients(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        clientsButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        clientsButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Clients.fxml");
    }

    public void goToHome(ActionEvent event) {
        clearNavBar();
        normalizeButton();
        homeButton.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        homeButton.setStyle("-fx-background-color: #3A7CF3;");
        loadCenterUI("Home.fxml");
    }

    public void normalizeButton() {
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
}
