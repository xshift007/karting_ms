package cl.kartingrm.reservation_service.mapper;

import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationResponse toDto(Reservation entity) {
        return new ReservationResponse(
                entity.getId(),
                null,
                null,
                null,
                entity.getParticipants(),
                null,
                (double) entity.getBasePrice(),
                (double) entity.getDiscountPercent(),
                (double) entity.getFinalPrice(),
                entity.getStatus()
        );
    }
}
