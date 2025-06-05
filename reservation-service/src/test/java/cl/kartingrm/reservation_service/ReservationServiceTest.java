package cl.kartingrm.reservation_service;

import cl.kartingrm.pricingclient.PricingResponse;
import cl.kartingrm.reservation_service.controller.ReservationController;
import cl.kartingrm.reservation_service.dto.ReservationResponse;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
import cl.kartingrm.reservation_service.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationController.class)
class ReservationServiceTest {

    @Autowired MockMvc mvc;

    @MockBean ReservationService service;      // <── NUEVO
    @MockBean RestTemplate rest;
    @MockBean ReservationRepository repo;

    @Test
    void reservationCreated_ok() throws Exception {

        // 1) simulamos la lógica completa dentro del propio servicio
        given(service.create(any())).willReturn(
                new ReservationResponse(1L, 70200, "PENDING")
        );

        mvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                   {
                     "laps":15,
                     "participants":4,
                     "clientEmail":"a@b.com",
                     "clientVisits":3,
                     "weekend":false,
                     "holiday":false,
                     "birthdayCount":0
                   }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.finalPrice").value(70200));

        then(service).should().create(any());
    }
}