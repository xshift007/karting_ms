package cl.kartingrm.client_service.service;

import cl.kartingrm.client_service.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client registerClient(Client clientData);
    Optional<Client> findByEmail(String email);
    int countVisitsThisMonth(String email);
    List<Client> getAllClients();
    void registerVisit(String email);
}
