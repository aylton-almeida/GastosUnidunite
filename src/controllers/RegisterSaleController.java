package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
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
import logic.Sale;
import services.ClientService;
import services.EmployeeService;
import services.ProductService;
import services.SaleService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private List<Integer> productIdList = new ArrayList<>();
    public ScrollPane scrollPane;
    public JFXButton doneButton;
    private GridPane gridPane = new GridPane();
    private static int row = 1;
    private double productSumValue = 0;
    private double totalValue;

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

        showLoader();
        Task retriveClientsTask = new Task() {
            @Override
            protected Integer call() throws Exception {
                //Get employee list
                listEmployee = new EmployeeService().getAllEmployee();
                //Get clients list
                listClient = new ClientService().getAllClients();
                //Get product list
                listProduct = new ProductService().getAllProducts();
                return 1;
            }

            @Override
            protected void succeeded() {
                //Add values to comboBoxes
                listEmployee.forEach(e -> employeeInput.getItems().add(e.getName()));
                listClient.forEach(c -> clientInput.getItems().add(c.getName()));
                if (actualSale != null) {

                } else {
                    doneButton.onActionProperty().set(ignored -> {
                        int payType;
                        switch (payTypeInput.getValue()) {
                            case "Cartão de Crédito":
                                payType = 1;
                                break;
                            case "Cartão de Débito":
                                payType = 2;
                                break;
                            case "Cheque":
                                payType = 3;
                                break;
                            case "Crediário":
                                payType = 4;
                                break;
                            default:
                                payType = 5;
                                break;
                        }
                        try {
                            showLoader();
                            Task retriveClientsTask = new Task() {
                                @Override
                                protected Integer call() throws Exception {
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    SaleService.addSale(Double.parseDouble(df.format(totalValue).replaceAll(",", ".")),
                                            listClient.stream().filter(c -> c.getName().equals(clientInput.getValue())).findFirst().get().getId(),
                                            listEmployee.stream().filter(e -> e.getName().equals(employeeInput.getValue())).findFirst().get().getId(),
                                            payType,
                                            productIdList);
                                    return 1;
                                }

                                @Override
                                protected void succeeded() {
                                    showMsg("Venda efetuada com sucesso");
                                    clearMainArea();
                                    loadCenterUI("/fxml/Sales.fxml");
                                    hideLoader();
                                }

                                @Override
                                protected void failed() {
                                    getException().printStackTrace();
                                    showMsg("Ocorreu um problema ao deletar os dados");
                                    hideLoader();
                                }
                            };
                            Thread t = new Thread(retriveClientsTask);
                            t.setDaemon(true);
                            t.start();
                        } catch (Exception e) {
                            showMsg("Ocorreu um erro: " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
                }
                addRow();
                hideLoader();
            }

            @Override
            protected void failed() {
                getException().printStackTrace();
                showMsg("Ocorreu um problema ao recuperar os dados");
                hideLoader();
                clearMainArea();
                loadCenterUI("/fxml/Sales.fxml");
            }
        };
        Thread t = new Thread(retriveClientsTask);
        t.setDaemon(true);
        t.start();

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        scrollPane.setContent(gridPane);

        discountInput.textProperty().addListener((observable) -> changeTotalValue());
    }

    private void changeTotalValue() {
        DecimalFormat df = new DecimalFormat("#.##");
        ProductsValueSumLabel.setText(("R$" + df.format(productSumValue)).replaceAll("\\.", ","));
        totalValue = (productSumValue - Double.parseDouble(discountInput.getText().replaceAll(",", "\\.")));
        TotalLabel.setText(("R$" + df.format(totalValue)).replaceAll("\\.", ","));
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
            productIdList.add(listProduct.stream().filter(p -> p.getName().equals(newValue)).findFirst().get().getId());
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
                    productIdList.remove(listProduct.stream().filter(p -> p.getName().equals(comboBox.getValue())).findFirst().get().getId());
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
}
