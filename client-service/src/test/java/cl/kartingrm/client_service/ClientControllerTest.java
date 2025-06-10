package cl.kartingrm.client_service;

import cl.kartingrm.client_service.controller.ClientController;
import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ClientService service;

    @Test
    void create_client_ok() throws Exception {
        Client cli = Client.builder().id(1L).email("a@b.com").name("Ana").build();
        given(service.findByEmail("a@b.com")).willReturn(Optional.empty());
        given(service.registerClient(any())).willReturn(cli);

        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"a@b.com\",\"name\":\"Ana\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_client_missing_name_bad_request() throws Exception {
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@x.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_visits_count() throws Exception {
        given(service.countVisitsThisMonth(eq("b@c.com"))).willReturn(3);

        mvc.perform(get("/api/clients/b@c.com/visits"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void add_visit_not_found() throws Exception {
        given(service.registerVisit("missing@ex.com")).willReturn(false);

        mvc.perform(post("/api/clients/missing@ex.com/visits"))
                .andExpect(status().isNotFound());
    }

    @Test
    void add_visit_ok() throws Exception {
        given(service.registerVisit("a@b.com")).willReturn(true);

        mvc.perform(post("/api/clients/a@b.com/visits"))
                .andExpect(status().isOk());
    }
}
