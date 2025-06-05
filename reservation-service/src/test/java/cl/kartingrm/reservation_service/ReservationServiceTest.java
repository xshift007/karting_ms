package cl.kartingrm.reservation_service;

import cl.kartingrm.pricingclient.PricingResponse;
import cl.kartingrm.reservation_service.controller.ReservationController;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
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
    @MockBean RestTemplate rest;
    @MockBean ReservationRepository repo;

    @Test
    void reservationCreated_ok() throws Exception {
        // 1) stub pricing response
        PricingResponse price = new PricingResponse(20000,10,10,0,15,70200,12.25);
        given(rest.postForObject(anyString(), any(), eq(PricingResponse.class))).willReturn(price);
        given(repo.save(any())).willAnswer(i -> { Reservation r = i.getArgument(0); r.setId(1L); return r;});

        mvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"laps\":15,\"participants\":4,\"clientEmail\":\"a@b.com\",\"clientVisits\":3,\"weekend\":false,\"holiday\":false,\"birthdayCount\":0}"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.finalPrice").value(70200));

        // 2) verifica SAVE ejecutado dentro de transacción
        then(repo).should().save(any());
    }
}
