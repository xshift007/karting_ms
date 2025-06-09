package cl.kartingrm.pricing_service;

import cl.kartingrm.pricing_service.dto.PricingRequest;
import cl.kartingrm.pricing_service.dto.PricingResponse;
import cl.kartingrm.pricing_service.service.PricingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PricingServiceTest {

    @Autowired
    PricingService service;

    @MockBean
    WebClient web;

    private void mockVisits(String email, int count) {
        WebClient.RequestHeadersUriSpec<?> uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec respSpec = mock(WebClient.ResponseSpec.class);

        when(web.get()).thenReturn(uriSpec);
        when(uriSpec.uri("http://client-service/api/clients/{email}/visits", email)).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(respSpec);
        when(respSpec.bodyToMono(Integer.class)).thenReturn(Mono.just(count));
    }

    @Test
    void weekdayCalculation_withDiscounts() {
        PricingRequest req = new PricingRequest(
                10, // laps
                3,  // participants
                "a@b.com",
                1,  // birthday count
                LocalDate.of(2025,6,4)  // Wednesday
        );
        mockVisits("a@b.com", 2);
        PricingResponse actual = service.calculate(req);
        assertThat(actual.baseUnit()).isEqualTo(15000);
        assertThat(actual.finalPrice()).isEqualTo(33075);
        assertThat(actual.totalDiscountPct()).isCloseTo(26.5, within(0.01));
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
        mockVisits("c@d.com", 0);
        PricingResponse actual = service.calculate(req);
        assertThat(actual.baseUnit()).isEqualTo(17000);
        assertThat(actual.finalPrice()).isEqualTo(61200);
        assertThat(actual.totalDiscountPct()).isCloseTo(10.0, within(0.01));
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
        mockVisits("e@f.com", 7);
        PricingResponse actual = service.calculate(req);
        assertThat(actual.baseUnit()).isEqualTo(26000);
        assertThat(actual.finalPrice()).isEqualTo(101790);
        assertThat(actual.totalDiscountPct()).isCloseTo(21.7, within(0.01));
    }
}
