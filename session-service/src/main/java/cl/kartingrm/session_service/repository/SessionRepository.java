package cl.kartingrm.session_service.repository;

import cl.kartingrm.session_service.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findBySessionDateBetween(LocalDate from, LocalDate to);
}
