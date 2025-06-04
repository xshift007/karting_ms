// src/main/java/cl/kartingrm/pricing_service/service/TariffService.java
package cl.kartingrm.pricing_service.service;

import cl.kartingrm.pricing_service.model.TariffConfig;
import cl.kartingrm.pricing_service.repository.TariffConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffConfigRepository repo;   // PK de tipo Long

    // Buscar tarifa por número de vueltas (laps), en lugar de usar RateType
    public TariffConfig forLaps(int laps) {
        return repo.findByLaps(laps)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tarifa no hallada para " + laps + " vueltas"));
    }
}
