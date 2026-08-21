package online.rollnexa.repository;
import online.rollnexa.domain.Availability; import org.springframework.data.jpa.repository.JpaRepository; import java.time.LocalDate; import java.util.*;
public interface AvailabilityRepository extends JpaRepository<Availability,Long> { Optional<Availability> findByRoomIdAndUserIdAndDate(Long roomId,Long userId,LocalDate date); List<Availability> findByRoomIdAndDateBetweenOrderByDateAsc(Long roomId,LocalDate from,LocalDate to); }

