package cl.kartingrm.reservation_service;

import cl.kartingrm.reservation_service.controller.ReservationController;
import cl.kartingrm.reservation_service.dto.ReservationResponse;
import cl.kartingrm.reservation_service.service.ReservationService;
import cl.kartingrm.reservation_service.model.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationController.class)
class ReservationServiceTest {

    @Autowired MockMvc mvc;

    @MockBean ReservationService service;

    @Test
    void reservationCreated_ok() throws Exception {

        // 1) simulamos la lógica completa dentro del propio servicio
        given(service.create(any())).willReturn(
                new ReservationResponse(1L, 70200, ReservationStatus.PENDING)
        );

        mvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                   {
                     "laps": 15,
                     "participants": 4,
                     "clientEmail": "a@b.com",
                     "birthdayCount": 0,
                     "sessionDate": "2025-06-07"
                  }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.finalPrice").value(70200))
                .andExpect(jsonPath("$.status").value("PENDING"));

        then(service).should().create(any());
    }

    @Test
    void cancel_reservation() throws Exception {
        given(service.cancel(eq(5L)))
                .willReturn(new cl.kartingrm.reservation_service.model.Reservation(5L,10,2,"a@b.com",1000,0,1000, ReservationStatus.CANCELLED));

        mvc.perform(patch("/api/reservations/5/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        then(service).should().cancel(5L);
    }
}