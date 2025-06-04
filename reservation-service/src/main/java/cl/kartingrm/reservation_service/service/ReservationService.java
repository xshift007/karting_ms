package cl.kartingrm.reservation_service.service;

import cl.kartingrm.pricingclient.PricingRequest;
import cl.kartingrm.pricingclient.PricingResponse;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repo;
    private final RestTemplate rest;
    @Value("${pricing.service.url}") String pricingUrl;

    public ReservationResponse create(CreateReservationRequest req){

        PricingRequest pricingReq = new PricingRequest(
                req.laps(), req.participants(), req.clientVisits(),
                req.birthdayCount(), LocalDate.now());

        PricingResponse pr = rest.postForObject(
                pricingUrl + "/api/pricing/calculate",
                pricingReq, PricingResponse.class);

        Reservation r = Reservation.builder()
                .laps(req.laps())
                .participants(req.participants())
                .clientEmail(req.clientEmail())
                /* precios ya son totales */
                .basePrice((int) (pr.baseUnit() * req.participants()))
                .discountPercent((int) pr.totalDiscountPct())
                .finalPrice(pr.finalPrice())
                .status("PENDING")
                .build();

        repo.save(r);
        return new ReservationResponse(r.getId(), r.getFinalPrice(), r.getStatus());
    }
}

