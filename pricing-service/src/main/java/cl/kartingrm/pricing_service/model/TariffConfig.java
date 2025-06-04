package cl.kartingrm.pricing_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames="laps"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class TariffConfig {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int laps;
    private int minutes;
    private int basePrice;
}
