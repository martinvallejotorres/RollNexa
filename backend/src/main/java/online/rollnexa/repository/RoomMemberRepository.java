package online.rollnexa.repository;
import online.rollnexa.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface RoomMemberRepository extends JpaRepository<RoomMember,Long> {
 Optional<RoomMember> findByRoomIdAndUserId(Long roomId,Long userId); boolean existsByRoomIdAndUserId(Long roomId,Long userId); long countByRoomId(Long roomId);
 List<RoomMember> findByRoomIdOrderByRoleAscJoinedAtAsc(Long roomId); List<RoomMember> findByUserIdOrderByJoinedAtDesc(Long userId);
 @Query("select m from RoomMember m join fetch m.user where m.room.id=:roomId") List<RoomMember> findMembers(@Param("roomId") Long roomId);
}

