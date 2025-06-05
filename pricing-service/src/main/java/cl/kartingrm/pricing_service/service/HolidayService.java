package cl.kartingrm.pricing_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Set;

/**
 * Servicio sencillo: festivos fijos + fines de semana largo.
 * En producción se reemplazaría por API oficial o tabla en BD.
 */
@Service
public class HolidayService {

    private static final Set<MonthDay> FIXED_HOLIDAYS = Set.of(
            MonthDay.of(1, 1),     // Año Nuevo
            MonthDay.of(5, 1),     // Trabajador
            MonthDay.of(9, 18),    // Chile – Fiestas Patrias
            MonthDay.of(9, 19),
            MonthDay.of(12, 25)    // Navidad
    );

    public boolean isHoliday(LocalDate date) {
        return FIXED_HOLIDAYS.contains(MonthDay.from(date));
    }
}
