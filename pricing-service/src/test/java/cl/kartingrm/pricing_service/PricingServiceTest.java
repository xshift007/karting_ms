package cl.kartingrm.pricing_service;

import cl.kartingrm.pricing_service.dto.PricingRequest;
import cl.kartingrm.pricing_service.dto.PricingResponse;
import cl.kartingrm.pricing_service.service.PricingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class PricingServiceTest {

    @Autowired
    PricingService service;

    @MockBean
    RestTemplate rest;

    @Test
    void weekdayCalculation_withDiscounts() {
        PricingRequest req = new PricingRequest(
                10, // laps
                3,  // participants
                "a@b.com",
                1,  // birthday count
                LocalDate.of(2025,6,4)  // Wednesday
        );
        given(rest.getForObject("http://client-service/api/clients/{email}/visits",
                Integer.class, "a@b.com"))
                .willReturn(2);
        PricingResponse expected = new PricingResponse(
                15000,
                10.0,
                10.0,
                1,
                10,
                33075,
                26.5
        );
        assertThat(service.calculate(req)).isEqualTo(expected);
    }

    @Test
    void weekendCalculation_noBirthday() {
        PricingRequest req = new PricingRequest(
                10,
                4,
                "c@d.com",
                0,
                LocalDate.of(2025,6,7) // Saturday
        );
        given(rest.getForObject("http://client-service/api/clients/{email}/visits",
                Integer.class, "c@d.com"))
                .willReturn(0);
        PricingResponse expected = new PricingResponse(
                17000,
                10.0,
                0.0,
                0,
                10,
                61200,
                10.0
        );
        assertThat(service.calculate(req)).isEqualTo(expected);
    }

    @Test
    void holidayCalculation_multipleDiscounts() {
        PricingRequest req = new PricingRequest(
                15,
                5,
                "e@f.com",
                2,
                LocalDate.of(2025,9,18) // Holiday
        );
        given(rest.getForObject("http://client-service/api/clients/{email}/visits",
                Integer.class, "e@f.com"))
                .willReturn(7);
        PricingResponse expected = new PricingResponse(
                26000,
                10.0,
                30.0,
                1,
                15,
                101790,
                21.7
        );
        assertThat(service.calculate(req)).isEqualTo(expected);
    }
}
