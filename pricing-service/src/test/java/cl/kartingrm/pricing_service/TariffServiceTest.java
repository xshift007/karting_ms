package cl.kartingrm.pricing_service;

import cl.kartingrm.pricing_service.model.RateType;
import cl.kartingrm.pricing_service.service.TariffService;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TariffServiceTest {
    @Autowired TariffService tariff;
    @Autowired TariffConfigRepository repo;

    @Test
    void weekendAndHolidayDetection() {
        var weekend = tariff.forDate(LocalDate.of(2025,6,7), 10);   // sábado
        assertThat(weekend.getRateType()).isEqualTo(RateType.WEEKEND);

        var holiday = tariff.forDate(LocalDate.of(2025,9,18), 10);
        assertThat(holiday.getRateType()).isEqualTo(RateType.HOLIDAY);
    }

    @Test
    void initialSeedContainsNineRows() {
        assertThat(repo.findAll()).hasSize(9);
    }
}
