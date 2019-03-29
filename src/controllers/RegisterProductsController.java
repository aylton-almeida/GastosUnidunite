package controllers;


import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
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



    public void registerProduct() {
        if (!nameInput.getText().isEmpty() && !codeInput.getText().isEmpty() && !valueInput.getText().isEmpty()){
            try {
                new ProductService().addProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", "."))));
                showMsg("Produto cadastrado com sucesso");
                clearMainArea();
                loadCenterUI("Products.fxml");
            } catch (Exception e) {
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        }else showMsg("Preencha todos os campos corretamente");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNumberValidator(valueInput);
        addNumberValidator(codeInput);
        addRequiredValidator(nameInput);
        addRequiredValidator(codeInput);
        addRequiredValidator(valueInput);
    }
}
