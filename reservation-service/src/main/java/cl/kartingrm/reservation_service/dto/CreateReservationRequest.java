package cl.kartingrm.reservation_service.dto;

import java.time.LocalDate;

public record CreateReservationRequest(
        int laps,
        int participants,
        String clientEmail,
        int clientVisits,
        int birthdayCount,
        LocalDate sessionDate
) {}
