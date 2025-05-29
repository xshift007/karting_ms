package cl.kartingrm.reservation_service.dto;


public record ReservationResponse(
        Long id,
        int finalPrice,
        String status
) {}
