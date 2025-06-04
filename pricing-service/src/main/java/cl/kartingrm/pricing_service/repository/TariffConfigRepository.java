package cl.kartingrm.pricing_service.repository;


import cl.kartingrm.pricing_service.model.TariffConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffConfigRepository
        extends JpaRepository<TariffConfig,Long> {
    Optional<TariffConfig> findByLaps(int laps);
}
