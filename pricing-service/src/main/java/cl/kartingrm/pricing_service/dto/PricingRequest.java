package cl.kartingrm.pricing_service.dto;

import java.time.LocalDate;

public record PricingRequest(
        int laps,
        int participants,
        int clientVisits,
        int birthdayCount,
        LocalDate sessionDate        // ¡nuevo!
) {}
