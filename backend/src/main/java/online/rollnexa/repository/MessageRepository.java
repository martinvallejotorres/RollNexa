package online.rollnexa.repository;
import online.rollnexa.domain.Message; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface MessageRepository extends JpaRepository<Message,Long> { Page<Message> findByRoomIdOrderBySentAtDesc(Long roomId,Pageable pageable); }

