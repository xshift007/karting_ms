package cl.kartingrm.pricing_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tariff_config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TariffConfig {
    @Id @Enumerated(EnumType.STRING)
    private RateType id;              // WEEKDAY, WEEKEND, HOLIDAY

    private int minutes;              // 10, 15, 20
    private int price;                // precio unitario CLP
}
