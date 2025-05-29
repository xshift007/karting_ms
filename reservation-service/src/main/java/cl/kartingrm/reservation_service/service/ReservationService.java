package cl.kartingrm.reservation_service.service;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.model.Reservation;
import cl.kartingrm.reservation_service.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service @RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${pricing.service.url}")
    private String pricingUrl;

    public ReservationResponse create(CreateReservationRequest req) {

        // 1. Llamar a pricing-service
        var priceReq = new cl.kartingrm.pricingclient.PricingRequest(
                req.laps(), req.participants(), req.weekend(),
                req.holiday(), req.clientVisits(), req.birthdayCount());

        var priceResp = restTemplate.postForObject(
                pricingUrl + "/calculate",
                priceReq,
                cl.kartingrm.pricingclient.PricingResponse.class);

        // 2. Guardar reserva
        Reservation res = Reservation.builder()
                .laps(req.laps())
                .participants(req.participants())
                .clientEmail(req.clientEmail())
                .basePrice(priceResp.basePrice())
                .discountPercent(priceResp.discountPercent())
                .finalPrice(priceResp.finalPrice())
                .status("PENDING")
                .build();

        repo.save(res);

        return new ReservationResponse(res.getId(), res.getFinalPrice(), res.getStatus());
    }
}
