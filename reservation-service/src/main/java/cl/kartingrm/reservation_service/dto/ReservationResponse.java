package cl.kartingrm.reservation_service.dto;

import cl.kartingrm.reservation_service.model.ReservationStatus;

public record ReservationResponse(
        Long id,
        int finalPrice,
        ReservationStatus status
) {}
