package cl.kartingrm.pricing_service.controller;


import cl.kartingrm.pricing_service.dto.*;
import cl.kartingrm.pricing_service.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    private final PricingService service;

    // Constructor explícito para inyección
    public PricingController(PricingService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public PricingResponse calculate(@RequestBody PricingRequest req) {
        return service.calculate(req);
    }
}
