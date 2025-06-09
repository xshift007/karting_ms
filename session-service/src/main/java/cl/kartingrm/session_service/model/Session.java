package cl.kartingrm.session_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sessions",
       uniqueConstraints = @UniqueConstraint(columnNames = {"session_date","start_time","end_time"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    private Integer capacity;

    @Version
    private Long version;

    /** Inscritos actuales */
    @com.fasterxml.jackson.annotation.JsonProperty("participantsCount")
    private Integer participantsCount = 0;

    /** evita negativos / overflow */
    public void addParticipants(int n) {
        this.participantsCount = Math.min(capacity, this.participantsCount + n);
    }
}
