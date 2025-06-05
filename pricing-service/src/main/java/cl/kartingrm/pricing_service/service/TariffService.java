// src/main/java/cl/kartingrm/pricing_service/service/TariffService.java
package cl.kartingrm.pricing_service.service;

import cl.kartingrm.pricing_service.model.RateType;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service @RequiredArgsConstructor
public class TariffService {
    private final TariffConfigRepository repo;
    private final HolidayService holidays;

    public TariffConfig forDate(LocalDate date, int laps) {
        RateType type;
        if (holidays.isHoliday(date)) type = RateType.HOLIDAY;
        else if (date.getDayOfWeek().getValue() >= 6) type = RateType.WEEKEND;
        else type = RateType.WEEKDAY;

        return repo.findByRateTypeAndLaps(type, laps)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tarifa no hallada para " + type + ", " + laps + " laps"));
    }
}
