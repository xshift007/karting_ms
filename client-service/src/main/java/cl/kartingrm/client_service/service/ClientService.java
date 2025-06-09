package cl.kartingrm.client_service.service;

import cl.kartingrm.client_service.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client registerClient(Client clientData);
    Optional<Client> findByEmail(String email);
    int countVisitsThisMonth(String email);
    List<Client> getAllClients();
    /**
     * Registra una visita para el cliente indicado.
     *
     * @param email correo del cliente
     * @return {@code true} si el cliente existe y la visita fue registrada,
     *         {@code false} en caso contrario
     */
    boolean registerVisit(String email);
}
