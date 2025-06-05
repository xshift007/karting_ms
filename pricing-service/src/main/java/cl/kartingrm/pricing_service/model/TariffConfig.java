package cl.kartingrm.pricing_service.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "tariff_config",
        uniqueConstraints = @UniqueConstraint(columnNames = {"rate_type", "laps"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** WEEKDAY / WEEKEND / HOLIDAY */
    @Enumerated(EnumType.STRING)
    @Column(name = "rate_type", length = 10, nullable = false)
    private RateType rateType;

    private int laps;      // == minutes (simplificación)
    private int minutes;
    private int basePrice; // CLP enteros
}
