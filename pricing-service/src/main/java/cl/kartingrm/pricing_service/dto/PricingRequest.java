package cl.kartingrm.pricing_service.dto;

public record PricingRequest(
        int laps,
        int participants,
        boolean weekend,
        boolean holiday,
        int clientVisits,
        int birthdayCount      // nº de cumpleañeros en el grupo
) {}
