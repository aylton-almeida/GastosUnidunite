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
    private List<Client> clientsList;
    private ClientService clientService;

    private void showOnTable() {
        this.clientsList.stream()
                .sorted(Client::compareTo)
                .forEach(client -> mainTableView.getItems().add(client));
    }

    public void goToRegisterClients(ActionEvent event) {
        clearMainArea();
        loadCenterUI("/fxml/RegisterClients.fxml");
    }

    public void filterSearch(ActionEvent event) {

        String input = searchInput.getText();

        mainTableView.getItems().clear();

        clientsList.forEach(client -> {
            if (("" + client.getId()).equals(input) || client.getName().equalsIgnoreCase(input))
                mainTableView.getItems().add(client);
        });
    }

    public void clearSearch(ActionEvent event) {
        searchInput.setText(null);
        mainTableView.getItems().clear();
        showOnTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        try {
            clientService = new ClientService();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.clientsList = clientService.getAllClients();
        } catch (Exception e) {
            showMsg(e.getMessage());
            e.printStackTrace();
        }

        showOnTable();
    }
}
