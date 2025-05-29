package cl.kartingrm.pricing_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "tariff_config",
        uniqueConstraints = @UniqueConstraint(columnNames = "laps")
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TariffConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int laps;

    @Column(nullable = false)
    private int minutes;

    @Column(name = "base_price", nullable = false)
    private int basePrice;

    private boolean weekend;
    private boolean holiday;
}
