package cl.kartingrm.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @PostMapping
    public ResponseEntity<Void> pay() {
        return ResponseEntity.ok().build();
    }
}
