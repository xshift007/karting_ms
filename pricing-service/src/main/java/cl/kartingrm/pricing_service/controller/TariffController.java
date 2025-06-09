package cl.kartingrm.pricing_service.controller;

import cl.kartingrm.pricing_service.model.*;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
public class TariffController {

    private final TariffConfigRepository repo;

    @GetMapping
    public List<TariffConfig> list() {
        return repo.findAll();
    }

    @PutMapping("/{rate}")
    public TariffConfig update(@PathVariable RateType rate,
                               @RequestBody TariffConfig body) {

        TariffConfig cfg = repo.findByRateTypeAndLaps(rate, body.getLaps())
                .orElseThrow(() -> new NoSuchElementException("tarifa no encontrada"));
        cfg.setBasePrice(body.getBasePrice());
        cfg.setMinutes(body.getMinutes());
        return repo.save(cfg);
    }
}
