package cl.kartingrm.pricingclient;

/**
 * DTO que recibe ReservationService desde el pricing-service.
 */
// cl.kartingrm.pricingclient.PricingResponse
public record PricingResponse(
        double baseUnit,
        double groupPct,
        double frequentPctOwner,
        int    birthdayWinners,
        int    minutes,
        int    finalPrice,
        double totalDiscountPct
) {}