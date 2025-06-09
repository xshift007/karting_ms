package cl.kartingrm.session_service.service;

import cl.kartingrm.session_service.model.Session;
import cl.kartingrm.session_service.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repo;

    @Transactional(readOnly = true)
    public List<Session> all() { return repo.findAll(); }

    @Transactional
    public Session save(Session s) { return repo.save(s); }

    @Transactional
    public Session update(Long id, Session dto) {
        dto.setId(id);
        return repo.save(dto);
    }

    @Transactional
    public void delete(Long id) { repo.deleteById(id); }

    @Transactional
    public void register(Long id, int n) {
        Session s = repo.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("session " + id));
        s.addParticipants(n);
        // dirty-checking JPA ⇒ no repo.save()
    }

    @Transactional(readOnly = true)
    public Map<DayOfWeek, List<Session>> week(LocalDate from, LocalDate to) {
        return repo.findBySessionDateBetween(from, to).stream()
                   .collect(Collectors.groupingBy(
                       sess -> sess.getSessionDate().getDayOfWeek(),
                       TreeMap::new, Collectors.toList()));
    }
}
