package cl.kartingrm.reservation_service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="reservation")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int laps;
    private int participants;
    private String clientEmail;

    private int basePrice;
    private int discountPercent;
    private int finalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
