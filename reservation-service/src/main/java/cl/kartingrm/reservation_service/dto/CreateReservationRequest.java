package cl.kartingrm.reservation_service.dto;

public record CreateReservationRequest(
        int laps,
        int participants,
        String clientEmail,
        int clientVisits,
        boolean weekend,
        boolean holiday,
        int birthdayCount
) {}
