package cl.kartingrm.reservation_service.controller;


import cl.kartingrm.reservation_service.dto.*;
import cl.kartingrm.reservation_service.service.ReservationService;
import cl.kartingrm.reservation_service.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    public ReservationResponse create(@RequestBody CreateReservationRequest req) {
        return service.create(req);
    }

    @GetMapping
    public java.util.List<ReservationResponse> list() { return service.all(); }

    @PatchMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable Long id) {
        var r = service.cancel(id);
        return mapper.toDto(r);
    }
}
