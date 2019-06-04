package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.Expense;
import services.ExpenseService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterExpensesController extends MainController implements Initializable {
    public JFXTextField descriptionInput;
    public JFXTextField valueInput;
    public JFXButton doneButton;
    public JFXCheckBox privateInput;
    public Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNumberValidator(valueInput);
        addRequiredValidator(descriptionInput);
        addRequiredValidator(valueInput);

        //Define modo de edicao caso exista um produto atual
        if (actualExpense != null) {
            descriptionInput.setText(actualExpense.getDescription());
            valueInput.setText(actualExpense.getValue() + "");
            privateInput.selectedProperty().setValue(!actualExpense.isPublic());

            doneButton.onActionProperty().set(ignored -> {
                showLoader();
                if (descriptionInput.validate() && valueInput.validate()) {
                    Task retrieveSalesTask = new Task() {
                        @Override
                        protected Integer call() throws Exception {
                            new ExpenseService().updateExpense(new Expense(descriptionInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", ".")), privateInput.isDisable(), actualExpense.getDate(), actualExpense.getId()));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            hideLoader();
                            showMsg("Gasto atualizado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Expenses.fxml");
                            actualExpense = null;
                        }

                        @Override
                        protected void failed() {
                            getException().printStackTrace();
                            showMsg("Ocorreu um problema ao atualizar o gasto");
                            hideLoader();
                            actualExpense = null;
                        }
                    };
                    Thread t = new Thread(retrieveSalesTask);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
            titleLabel.setText("Editar Gasto");
        } else {
            doneButton.onActionProperty().set(ignored -> {
                showLoader();
                if (descriptionInput.validate() && valueInput.validate()) {
                    Task retrieveSalesTask = new Task() {
                        @Override
                        protected Integer call() throws Exception {
                            new ExpenseService().addExpense(new Expense(descriptionInput.getText(), Double.parseDouble(valueInput.getText().replaceAll(",", ".")), privateInput.isDisable()));
                            return 1;
                        }

                        @Override
                        protected void succeeded() {
                            hideLoader();
                            showMsg("Gasto cadastrado com sucesso");
                            clearMainArea();
                            loadCenterUI("/fxml/Expenses.fxml");
                        }

                        @Override
                        protected void failed() {
                            getException().printStackTrace();
                            showMsg("Ocorreu um problema ao cadastrar o gasto");
                            hideLoader();
                        }
                    };
                    Thread t = new Thread(retrieveSalesTask);
                    t.setDaemon(true);
                    t.start();
                } else showMsg("Preencha todos os campos corretamente");
            });
        }
    }
}
