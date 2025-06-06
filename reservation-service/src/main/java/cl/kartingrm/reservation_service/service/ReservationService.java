package cl.kartingrm.reservation_service.service;

import cl.kartingrm.pricingclient.PricingRequest;
import cl.kartingrm.pricingclient.PricingResponse;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repo;
    private final RestTemplate rest;

    @Value("${pricing.service.url}")           // http://localhost:8081
    private String pricingUrl;

    public ReservationResponse create(CreateReservationRequest req) {
        PricingResponse p = callPricing(req);
        Reservation saved = transactionalSave(req, p);
        return new ReservationResponse(saved.getId(), saved.getFinalPrice(), saved.getStatus());
    }

    private PricingResponse callPricing(CreateReservationRequest req) {
        try {
            PricingRequest pricingReq = new PricingRequest(
                    req.laps(),
                    req.participants(),
                    req.clientVisits(),
                    req.birthdayCount(),
                    req.sessionDate());

            return rest.postForObject(
                    pricingUrl + "/api/pricing/calculate",
                    pricingReq, PricingResponse.class);
        } catch (org.springframework.web.client.RestClientException ex) {
            throw new IllegalStateException("No se pudo obtener precio del servicio externo", ex);
        }
    }

    /**
     * Transacción corta — solo inserción en una tabla local;
     * admite REQUIRES_NEW para no propagar rollback a llamada externa ya realizada.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected Reservation transactionalSave(CreateReservationRequest req, PricingResponse p) {
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

