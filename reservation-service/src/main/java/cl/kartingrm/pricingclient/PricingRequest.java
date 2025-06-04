package cl.kartingrm.pricingclient;

import java.time.LocalDate;

/**
 * DTO usado por ReservationService para invocar el pricing-service.
 */
// cl.kartingrm.pricingclient.PricingRequest
public record PricingRequest(
        int laps,
        int participants,
        int clientVisits,
        int birthdayCount,
        LocalDate sessionDate      // mismo orden que en pricing-service
) {}