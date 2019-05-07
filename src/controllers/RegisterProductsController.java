package controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
    public Label tituloLabel;

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

            doneButton.onActionProperty().set(ignored -> {
                if (nameInput.validate() && codeInput.validate() && valueInput.validate()) {
                    try {
                        new ProductService().updateProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", "."))));
                        showMsg("Produto atualizado com sucesso");
                        clearMainArea();
                        loadCenterUI("/fxml/Products.fxml");
                    } catch (Exception e) {
                        showMsg(e.getMessage());
                        e.printStackTrace();
                    }
                } else showMsg("Preencha todos os campos corretamente");
            });

            actualProduct = null;
            tituloLabel.setText("Editar Produto");
        } else {
            doneButton.onActionProperty().set(ignored -> {
                if (nameInput.validate() && codeInput.validate() && valueInput.validate()) {
                    try {
                        new ProductService().addProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", "."))));
                        showMsg("Produto cadastrado com sucesso");
                        clearMainArea();
                        loadCenterUI("/fxml/Products.fxml");
                    } catch (Exception e) {
                        showMsg(e.getMessage());
                        e.printStackTrace();
                    }
                } else showMsg("Preencha todos os campos corretamente");
            });
        }
    }
}
