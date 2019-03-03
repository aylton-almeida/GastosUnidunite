package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import logic.Product;
import services.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterProductsController extends MainController {

    public JFXTextField nameInput;
    public JFXTextField codeInput;
    public JFXTextField valueInput;
    public JFXTextField factoryInput;
    public JFXTextField sizeInput;


    public void registerProduct() {
        if (!nameInput.getText().isEmpty() && !codeInput.getText().isEmpty() && !valueInput.getText().isEmpty() && !sizeInput.getText().isEmpty()) {
            try {
                new ProductService().addProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText())));
                showMsg("Produto cadastrado com sucesso");
                clearMainArea();
                loadCenterUI("Products.fxml");
            } catch (Exception e) {
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        } else showMsg("Preencha todos os campos corretamente");
    }
}
