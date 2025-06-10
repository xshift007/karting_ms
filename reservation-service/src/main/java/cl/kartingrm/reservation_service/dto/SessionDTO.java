package cl.kartingrm.reservation_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionDTO(Long id, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) {}
