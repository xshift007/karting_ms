package cl.kartingrm.pricingclient;

/**
 * DTO usado por ReservationService para invocar el pricing-service.
 */
public record PricingRequest(
        int laps,
        int participants,
        boolean weekend,
        boolean holiday,
        int clientVisits,
        int birthdayCount
) {}
