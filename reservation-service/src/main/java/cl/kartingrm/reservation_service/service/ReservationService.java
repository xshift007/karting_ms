package cl.kartingrm.reservation_service.service;

import cl.kartingrm.pricingclient.PricingRequest;
import cl.kartingrm.pricingclient.PricingResponse;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import cl.kartingrm.reservation_service.service.ReservationPersister;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationPersister persister;
    private final WebClient web;

    private static final String PRICING_URL = "http://pricing-service";
    private static final String CLIENT_URL = "http://client-service";

    public ReservationResponse create(CreateReservationRequest req) {
        PricingResponse p = callPricing(req);
        Reservation saved = persister.save(req, p);
        web.post()
                .uri(CLIENT_URL + "/api/clients/{email}/visits", req.clientEmail())
                .retrieve()
                .toBodilessEntity()
                .retry(3)
                .block();
        return new ReservationResponse(saved.getId(), saved.getFinalPrice(), saved.getStatus());
    }

    private PricingResponse callPricing(CreateReservationRequest req) {
        try {
            PricingRequest pricingReq = new PricingRequest(
                    req.laps(),
                    req.participants(),
                    req.clientEmail(),
                    req.birthdayCount(),
                    req.sessionDate());

            return web.post()
                    .uri(PRICING_URL + "/api/pricing/calculate")
                    .bodyValue(pricingReq)
                    .retrieve()
                    .bodyToMono(PricingResponse.class)
                    .block();
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException ex) {
            throw new IllegalStateException("No se pudo obtener precio del servicio externo", ex);
        }
    }


}

