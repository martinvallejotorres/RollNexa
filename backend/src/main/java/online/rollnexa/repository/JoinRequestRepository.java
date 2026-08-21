package online.rollnexa.repository;
import online.rollnexa.domain.JoinRequest; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface JoinRequestRepository extends JpaRepository<JoinRequest,Long> { Optional<JoinRequest> findByRoomIdAndUserId(Long roomId,Long userId); List<JoinRequest> findByRoomIdAndStatusOrderByCreatedAtAsc(Long roomId,JoinRequest.Status status); }

