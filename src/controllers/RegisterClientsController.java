package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyEvent;

public class RegisterClientsController extends MainController {
    public JFXTextField nameInput;
    public JFXTextField codeInput;
    public JFXTextField adressInput;
    public JFXTextField phoneInput;

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
}
