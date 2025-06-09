package cl.kartingrm.reservation_service.dto;

import java.time.LocalDate;

public record CreateReservationRequest(
        Long sessionId,
        int laps,
        int participants,
        String clientEmail,
        int birthdayCount,
        LocalDate sessionDate
) {}
