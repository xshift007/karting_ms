package cl.kartingrm.client_service.controller;

import cl.kartingrm.client_service.model.Client;
import cl.kartingrm.client_service.service.ClientService;
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
    public ResponseEntity<Client> createClient(@RequestBody Client clientData) {
        if (clientService.findByEmail(clientData.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Client saved = clientService.registerClient(clientData);
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

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
