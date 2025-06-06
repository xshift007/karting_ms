package cl.kartingrm.pricing_service.dto;

import java.time.LocalDate;

public record PricingRequest(
        int laps,
        int participants,
        String clientEmail,
        int birthdayCount,
        LocalDate sessionDate
) {}
