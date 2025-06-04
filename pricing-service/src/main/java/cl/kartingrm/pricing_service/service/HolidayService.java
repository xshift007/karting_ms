package cl.kartingrm.pricing_service.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HolidayService {
    public boolean isHoliday(LocalDate d){ return false; }
}
