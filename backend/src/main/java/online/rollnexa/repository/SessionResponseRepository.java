package online.rollnexa.repository;
import online.rollnexa.domain.SessionResponse; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SessionResponseRepository extends JpaRepository<SessionResponse,Long> { Optional<SessionResponse> findBySessionIdAndUserId(Long sessionId,Long userId); List<SessionResponse> findBySessionId(Long sessionId); }

