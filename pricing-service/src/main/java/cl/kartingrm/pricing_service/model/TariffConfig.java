package cl.kartingrm.pricing_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="tariff_config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TariffConfig {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int laps;          // 10, 15, 20
    private int minutes;       // 10, 15, 20
    private int basePrice;     // en CLP

    // ejemplo: finDeSemana=true aplica recargo %
    private boolean weekend;
    private boolean holiday;
}
