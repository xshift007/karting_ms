package cl.kartingrm.pricing_service.dto;


public record PricingResponse(
        int basePrice,
        int discountPercent,
        int finalPrice
) {}
