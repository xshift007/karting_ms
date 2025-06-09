package cl.kartingrm.session_service;

import cl.kartingrm.session_service.controller.SessionController;
import cl.kartingrm.session_service.model.Session;
import cl.kartingrm.session_service.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SessionController.class)
class SessionControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    SessionService service;

    @Test
    void availability_returns_ok() throws Exception {
        given(service.week(any(), any()))
                .willReturn(Map.of(DayOfWeek.MONDAY, List.of()));

        mvc.perform(get("/api/sessions/availability")
                        .param("from", "2025-06-09")
                        .param("to", "2025-06-15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
