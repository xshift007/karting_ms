package cl.kartingrm.client_service;

import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.model.Visit;
import cl.kartingrm.client_service.repository.ClientRepository;
import cl.kartingrm.client_service.repository.VisitRepository;
import cl.kartingrm.client_service.service.ClientService;
import cl.kartingrm.client_service.service.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(ClientServiceImpl.class)
@ActiveProfiles("test")
class ClientServiceTest {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    VisitRepository visitRepository;
    @Autowired
    ClientService clientService;

    @BeforeEach
    void setup() {
        visitRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void register_and_find_client() {
        Client c = Client.builder().email("a@b.com").name("Ana").build();
        clientService.registerClient(c);
        assertThat(clientService.findByEmail("a@b.com")).isPresent();
    }

    @Test
    void count_visits_this_month() {
        Client c = clientService.registerClient(Client.builder().email("b@c.com").name("Bob").build());
        visitRepository.save(Visit.builder().client(c).visitDate(LocalDate.now()).build());
        visitRepository.save(Visit.builder().client(c).visitDate(LocalDate.now()).build());

        int count = clientService.countVisitsThisMonth("b@c.com");
        assertThat(count).isEqualTo(2);
    }

    @Test
    void register_visit_returns_false_when_client_missing() {
        boolean result = clientService.registerVisit("nouser@ex.com");
        assertThat(result).isFalse();
    }

    @Test
    void register_visit_persists_when_client_exists() {
        clientService.registerClient(Client.builder().email("c@d.com").name("Carl").build());
        boolean result = clientService.registerVisit("c@d.com");
        assertThat(result).isTrue();
        assertThat(clientService.countVisitsThisMonth("c@d.com")).isEqualTo(1);
    }
}
