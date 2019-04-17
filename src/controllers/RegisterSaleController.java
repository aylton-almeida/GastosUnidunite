package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import logic.Client;
import logic.Employee;
import logic.Product;
import services.ClientService;
import services.EmployeeService;
import services.ProductService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RegisterSaleController extends MainController implements Initializable {

    public JFXComboBox<String> employeeInput;
    public JFXComboBox<String> clientInput;
    public JFXComboBox<String> payTypeInput;
    public JFXTextField discountInput;
    public Label ProductsValueSumLabel;
    public Label TotalLabel;
    private static List<Product> listProduct;
    private static List<Employee> listEmployee;
    private static List<Client> listClient;
    public ScrollPane scrollPane;
    private GridPane gridPane = new GridPane();
    private static int row = 1;
    private double productSumValue = 0;
    private double totalValue;

    public void RegisterSale(ActionEvent actionEvent) {
        System.out.println(payTypeInput.validate());
        System.out.println(payTypeInput.getValue());
    }

    private void changeTotalValue() {
        ProductsValueSumLabel.setText(("R$" + productSumValue).replaceAll("\\.", ","));
        totalValue = productSumValue - Double.parseDouble(discountInput.getText().replaceAll(",", "\\."));
        TotalLabel.setText(("R$" + totalValue).replaceAll("\\.", ","));
    }

    private void addRow() {
        //Create labels for price
        Label labelValue = new Label("Valor: ");
        labelValue.setFont(new Font(18));

        Label label = new Label("R$00,00");
        label.setFont(new Font(18));

        //Create comboBox
        JFXComboBox<String> comboBox = new JFXComboBox<>();
        comboBox.setFocusColor(Paint.valueOf("#3a7cf3"));
        comboBox.setPrefHeight(35);
        comboBox.setPrefWidth(200);
        comboBox.setPromptText("Produto *");
        listProduct.forEach(p -> comboBox.getItems().add(p.getName()));
        comboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            double value = listProduct.stream()
                    .filter(p -> p.getName().equals(newValue))
                    .collect(Collectors.toList())
                    .get(0)
                    .getValue();

            label.setText(("R$" + value).replaceAll("\\.", ","));
            try {
                productSumValue -= listProduct.stream()
                        .filter(p -> p.getName().equals(oldValue))
                        .collect(Collectors.toList())
                        .get(0)
                        .getValue();
            } catch (Exception ignored) {
            }
            productSumValue += value;
            changeTotalValue();
        });

        //Create plus button
        JFXButton plusBtn = new JFXButton("+");
        plusBtn.setPrefHeight(25);
        plusBtn.setPrefWidth(25);
        plusBtn.setStyle("-fx-background-color: #3a7cf3; -fx-background-radius: 50;");
        plusBtn.setTextFill(Paint.valueOf("WHITE"));
        plusBtn.setOnMouseClicked((observable) -> this.addRow());

        //Create minus button
        JFXButton minusBtn = new JFXButton("-");
        minusBtn.setPrefHeight(25);
        minusBtn.setPrefWidth(25);
        minusBtn.setStyle("-fx-background-color: #3a7cf3; -fx-background-radius: 50;");
        minusBtn.setTextFill(Paint.valueOf("WHITE"));
        minusBtn.setOnMouseClicked((observable) -> {
            if (row > 2) {
                row--;
                gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row);
                try {
                    productSumValue -= listProduct.stream()
                            .filter(p -> p.getName().equals(comboBox.getValue()))
                            .collect(Collectors.toList())
                            .get(0)
                            .getValue();
                    changeTotalValue();
                } catch (Exception ignored) {
                }
            }
        });

        //Set nodes on gridPane
        GridPane.setConstraints(comboBox, 0, row);
        GridPane.setConstraints(labelValue, 1, row);
        GridPane.setConstraints(label, 2, row);
        GridPane.setConstraints(plusBtn, 3, row);
        GridPane.setConstraints(minusBtn, 4, row);

        gridPane.getChildren().addAll(comboBox, labelValue, label, plusBtn, minusBtn);

        //Increment row number
        row++;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addRequiredValidator(payTypeInput);
        addNumberValidator(discountInput);
        addRequiredValidator(discountInput);

        payTypeInput.getItems().addAll(
                "A vista",
                "Cartão de Crédito",
                "Cartão de Débito",
                "Cheque",
                "Crediário"
        );

        try {
            //Get employee list
            listEmployee = new EmployeeService().getAllEmployee();
            //Get clients list
            listClient = new ClientService().getAllClients();
            //Get product list
            listProduct = new ProductService().getAllProducts();
            //Add values to comboBoxes
            listEmployee.forEach(e -> employeeInput.getItems().add(e.getName()));
            listClient.forEach(c -> clientInput.getItems().add(c.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        this.addRow();

        scrollPane.setContent(gridPane);

        discountInput.textProperty().addListener((observable) -> changeTotalValue());
    }
}
