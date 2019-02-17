package login;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LoginController {
    public Button btnLogin;
    public PasswordField inputPass;
    public TextField inputUser;
    public Pane msgPane;
    public Button closeMsgBtn;
    public Text msgText;

    public void makeLogin() {
        String user = inputUser.getText();
        String pass = inputPass.getText();
        if (user != null && pass.length() >= 8){
           showMsg("Login efetuado com sucesso", 1);
        }else{
            showMsg("Email ou senha incorretos", 0);
        }
    }

    public void hideMsg() {
        msgPane.setOpacity(0);
        msgPane.setStyle("-fx-background-color: #ee831e");
        msgText.setText("");
    }

    public void showMsg(String text, int type){
        msgText.setText(text);
        if (type == 1)
            msgPane.setStyle("-fx-background-color: #6cb235; -fx-background-radius: 30;");
        else
            msgPane.setStyle("-fx-background-color: #ee831e; -fx-background-radius: 30;");
        msgPane.setOpacity(100);
    }
}
