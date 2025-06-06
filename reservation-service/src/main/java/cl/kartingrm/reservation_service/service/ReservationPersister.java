package cl.kartingrm.reservation_service.service;

import cl.kartingrm.pricingclient.PricingResponse;
import cl.kartingrm.reservation_service.dto.CreateReservationRequest;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationPersister {
    private final ReservationRepository repo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Reservation save(CreateReservationRequest req, PricingResponse p) {
        Reservation r = Reservation.builder()
                .laps(req.laps())
                .participants(req.participants())
                .clientEmail(req.clientEmail())
                .basePrice((int) (p.baseUnit() * req.participants()))
                .discountPercent((int) p.totalDiscountPct())
                .finalPrice(p.finalPrice())
                .status("PENDING")
                .build();
        return repo.save(r);
    }
}
