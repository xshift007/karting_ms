package cl.kartingrm.pricing_service.dto;


public record PricingResponse(
        double baseUnit,
        double groupPct,
        double frequentPctOwner,
        int    birthdayWinners,
        int    minutes,
        int    finalPrice,           // TOTAL
        double totalDiscountPct
) {}
