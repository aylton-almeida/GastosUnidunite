package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Product;
import services.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterProductsController extends MainController implements Initializable {


    public JFXTextField nameInput;
    public JFXTextField codeInput;
    public JFXTextField valueInput;
    public JFXTextField factoryInput;
    public JFXTextField sizeInput;
    public JFXButton doneButton;
    public Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNumberValidator(valueInput);
        addNumberValidator(codeInput);
        addRequiredValidator(nameInput);
        addRequiredValidator(codeInput);
        addRequiredValidator(valueInput);

        //Define modo de edicao caso exista um produto atual
        if (actualProduct != null) {
            nameInput.setText(actualProduct.getName());
            codeInput.setText(actualProduct.getId() + "");
            valueInput.setText(actualProduct.getValue() + "");
            factoryInput.setText(actualProduct.getFactory());
            sizeInput.setText(actualProduct.getSize());
            codeInput.setDisable(true);

            doneButton.onActionProperty().set(ignored -> {
                showLoader();
                if (nameInput.validate() && codeInput.validate() && valueInput.validate()) {
                    Task task = new Task() {

                        @Override
                        protected Integer call() throws Exception {
                            new ProductService().updateProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", "."))));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            showMsg("Produto atualizado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Products.fxml");
                            hideLoader();
                            actualProduct = null;
                        }

                        @Override
                        protected void failed() {
                            showMsg("Ocorreu um erro ao editar o produto");
                            hideLoader();
                            actualProduct = null;
                        }
                    };
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
            titleLabel.setText("Editar Produto");
        } else {
            doneButton.onActionProperty().set(ignored -> {
                showLoader();
                if (nameInput.validate() && codeInput.validate() && valueInput.validate()) {
                    Task task = new Task() {

                        @Override
                        protected Integer call() throws Exception {
                            new ProductService().addProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", "."))));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            showMsg("Produto cadastrado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Products.fxml");
                            hideLoader();
                            actualProduct = null;
                        }

                        @Override
                        protected void failed() {
                            showMsg("Ocorreu um erro ao cadastrar o produto");
                            hideLoader();
                            actualProduct = null;
                        }
                    };
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
        }
    }
}
