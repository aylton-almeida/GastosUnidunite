package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Client;
import services.ClientService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsController extends MainController implements Initializable {
    public JFXTextField searchInput;
    public TableView mainTableView;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn adressColumn;
    public TableColumn emailColumn;
    public TableColumn phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("Adress"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));


        List<Client> list = null;
        try {
            list = new ClientService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .sorted(Client::compareTo)
                .forEach(client -> mainTableView.getItems().add(client));
    }

    public void goToRegisterClients(ActionEvent event) {
        clearMainArea();
        loadCenterUI("RegisterClients.fxml");
    }

    public void filterSearch(ActionEvent event) {

        mainTableView.getItems().clear();

        String input = searchInput.getText();

        List<Client> list = null;
        try {
            list = new ClientService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .filter(client -> client.getId() == Integer.parseInt(input))
                .forEach(client -> mainTableView.getItems().add(client));
    }

    public void clearSearch(ActionEvent event) {

        searchInput.setText(null);
        mainTableView.getItems().clear();
        List<Client> list = null;
        try {
            list = new ClientService().getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        list.stream()
                .sorted(Client::compareTo)
                .forEach(client -> mainTableView.getItems().add(client));
    }
}
