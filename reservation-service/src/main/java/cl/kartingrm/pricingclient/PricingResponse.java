package cl.kartingrm.pricingclient;

/**
 * DTO que recibe ReservationService desde el pricing-service.
 */
public record PricingResponse(
        int basePrice,
        int discountPercent,
        int finalPrice
) {}
