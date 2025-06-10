package cl.kartingrm.reservation_service.dto;

import cl.kartingrm.reservation_service.model.RateType;
import cl.kartingrm.reservation_service.model.ReservationStatus;

public record ReservationResponse(
        Long id,
        String reservationCode,
        ClientDTO client,
        SessionDTO session,
        Integer participants,
        RateType rateType,
        Double basePrice,
        Double discountPercentage,
        Double finalPrice,
        ReservationStatus status
) {}
