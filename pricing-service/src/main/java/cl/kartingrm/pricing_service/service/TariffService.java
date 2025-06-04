package cl.kartingrm.pricing_service.service;

import cl.kartingrm.pricing_service.model.RateType;
import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffConfigRepository repo;
    private final HolidayService holidays;

    public TariffConfig forDate(LocalDate date, int laps) {
        RateType req = RateType.WEEKDAY;
        if (holidays.isHoliday(date))         req = RateType.HOLIDAY;
        else if (date.getDayOfWeek().getValue() >= 6) req = RateType.WEEKEND;

        return repo.findById(req)
                .filter(t -> t.getMinutes() == laps)   // laps ≈ minutos
                .orElseThrow(() -> new IllegalArgumentException("Tarifa no hallada"));
    }
}
