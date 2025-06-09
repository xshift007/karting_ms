package cl.kartingrm.session_service;

import cl.kartingrm.session_service.model.Session;
import cl.kartingrm.session_service.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SessionServiceTest {
    @Autowired SessionService svc;

    @Test
    void register_caps_limit() {
        Session s = svc.save(Session.builder()
                .sessionDate(LocalDate.now())
                .startTime(LocalTime.of(14,0))
                .endTime(LocalTime.of(14,15))
                .capacity(10).build());
        svc.register(s.getId(), 12);
        assertThat(svc.all().get(0).getParticipantsCount()).isEqualTo(10);
    }
}
