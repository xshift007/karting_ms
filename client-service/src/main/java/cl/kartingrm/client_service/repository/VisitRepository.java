package cl.kartingrm.client_service.repository;

import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    int countByClientAndVisitDateBetween(Client client, LocalDate startOfMonth, LocalDate endOfMonth);
    List<Visit> findByClient(Client client);
}
