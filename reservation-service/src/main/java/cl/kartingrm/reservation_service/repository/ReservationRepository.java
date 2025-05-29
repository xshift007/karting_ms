package cl.kartingrm.reservation_service.repository;


import cl.kartingrm.reservation_service.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
