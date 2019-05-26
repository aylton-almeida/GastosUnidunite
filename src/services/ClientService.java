package services;

import dao.Clients;
import logic.Client;

import java.util.List;

public class ClientService {
    private static Clients clients;

    public ClientService() throws Exception {
        this.clients = new Clients();
    }

    public List<Client> getAllClients() throws Exception {
        return clients.getAllObjects();
    }

    public void addClient(Client c) throws Exception{
        clients.addObject(c);
    }

    public void deleteClient(Client client) throws Exception{
        clients.deleteObject(client);
    }

    public void updateClient(Client client) throws Exception{
        clients.updateObject(client);
    }
}
