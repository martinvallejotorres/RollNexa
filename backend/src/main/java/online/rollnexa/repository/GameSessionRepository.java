package online.rollnexa.repository;
import online.rollnexa.domain.GameSession; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface GameSessionRepository extends JpaRepository<GameSession,Long> { List<GameSession> findByRoomIdOrderByScheduledAtAsc(Long roomId); }

