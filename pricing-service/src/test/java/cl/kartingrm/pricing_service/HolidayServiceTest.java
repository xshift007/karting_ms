package cl.kartingrm.pricing_service;

import cl.kartingrm.pricing_service.service.HolidayService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class HolidayServiceTest {
    private final HolidayService svc = new HolidayService();

    @Test
    void fixedHolidays() {
        assertThat(svc.isHoliday(LocalDate.of(2025, 9, 18))).isTrue();
        assertThat(svc.isHoliday(LocalDate.of(2025, 9, 19))).isTrue();
        assertThat(svc.isHoliday(LocalDate.of(2025, 9, 17))).isFalse();
    }
}
