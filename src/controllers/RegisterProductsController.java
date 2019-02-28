package controllers;


import com.jfoenix.controls.JFXTextField;
import logic.Product;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
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
        if (!nameInput.getText().isEmpty() && !codeInput.getText().isEmpty() && !valueInput.getText().isEmpty() && !sizeInput.getText().isEmpty()){
            try {
                new ProductService().addProduct(new Product(Integer.parseInt(codeInput.getText()), nameInput.getText(), factoryInput.getText(), sizeInput.getText(), Double.parseDouble(valueInput.getText())));
                showMsg("Produto cadastrado com sucesso");
                clearMainArea();
                loadCenterUI("Products.fxml");
            } catch (Exception e) {
                showMsg(e.getMessage());
                e.printStackTrace();
            }
        }else showMsg("Preencha todos os campos corretamente");
    }

    public void checkNumber(KeyEvent keyEvent) {
        JFXTextField field = ((JFXTextField)keyEvent.getSource());
        if (!keyEvent.getCharacter().matches("[0-9]*") && !keyEvent.getCharacter().matches(".")){
            if (field.getText().length() == 1){
                field.setText("");
            }else{
                if (field.getText().length() > 0){
                    String input = field.getText();
                    field.setText(input.substring(0, input.length() - 1));
                }
            }
        }
        field.positionCaret(field.getText().length());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
