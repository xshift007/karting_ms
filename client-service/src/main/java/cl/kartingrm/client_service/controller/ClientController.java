package cl.kartingrm.client_service.controller;

import cl.kartingrm.client_service.dto.ClientDTO;
import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientDTO dto) {
        if (clientService.findByEmail(dto.email()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El correo ya existe");

        Client saved = clientService.registerClient(
                Client.builder()
                        .email(dto.email())
                        .name(dto.name())
                        .phone(dto.phone())
                        .build());

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        Optional<Client> opt = clientService.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(opt.get());
    }

    @GetMapping("/{email}/visits")
    public ResponseEntity<?> getVisitsCount(@PathVariable String email) {
        int count = clientService.countVisitsThisMonth(email);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{email}/visits")
    public ResponseEntity<Void> addVisit(@PathVariable String email) {
        boolean ok = clientService.registerVisit(email);
        if (!ok) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
