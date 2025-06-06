package cl.kartingrm.client_service.service;

import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.model.Visit;
import cl.kartingrm.client_service.repository.ClientRepository;
import cl.kartingrm.client_service.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepo;
    private final VisitRepository visitRepo;

    public ClientServiceImpl(ClientRepository clientRepo, VisitRepository visitRepo) {
        this.clientRepo = clientRepo;
        this.visitRepo = visitRepo;
    }

    @Override
    @Transactional
    public Client registerClient(Client clientData) {
        clientData.setRegisteredAt(LocalDate.now().atStartOfDay());
        return clientRepo.save(clientData);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByEmail(String email) {
        return clientRepo.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public int countVisitsThisMonth(String email) {
        Optional<Client> opt = clientRepo.findByEmail(email);
        if (opt.isEmpty()) {
            return 0;
        }
        Client client = opt.get();
        YearMonth current = YearMonth.now();
        LocalDate start = current.atDay(1);
        LocalDate end = current.atEndOfMonth();
        return visitRepo.countByClientAndVisitDateBetween(client, start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    @Override
    @Transactional
    public void registerVisit(String email) {
        Optional<Client> opt = clientRepo.findByEmail(email);
        if (opt.isPresent()) {
            Client client = opt.get();
            Visit visit = Visit.builder()
                    .client(client)
                    .visitDate(LocalDate.now())
                    .build();
            visitRepo.save(visit);
        }
    }
}
