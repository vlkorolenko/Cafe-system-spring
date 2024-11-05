package org.example.cafespring.service;

import org.example.cafespring.model.Client;
import org.example.cafespring.repository.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientDAO clientDAO;

    @Autowired
    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    public void addClient(Client client) {
        clientDAO.add(client);
    }

    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    public Client findById(int id) {
        return clientDAO.findById(id);
    }

    public void updateClient(Client client) {
        clientDAO.update(client);
    }
}
