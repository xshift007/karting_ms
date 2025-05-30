package cl.kartingrm.reservation_service.controller;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ReservationResponse create(@RequestBody CreateReservationRequest req) {
        return service.create(req);
    }
}
