package online.rollnexa.repository;
import online.rollnexa.domain.Room; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param;
public interface RoomRepository extends JpaRepository<Room,Long> {
 @Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @Query("select r from Room r where r.id=:id") java.util.Optional<Room> lockById(@Param("id") Long id);
 @Query("select distinct r from Room r left join RoomMember m on m.room=r left join m.user gm where r.completed=false and (lower(r.name) like lower(concat('%',:q,'%')) or lower(r.gameSystem) like lower(concat('%',:q,'%')) or lower(r.tags) like lower(concat('%',:q,'%')) or lower(gm.username) like lower(concat('%',:q,'%'))) and (:tag='' or lower(r.tags) like lower(concat('%',:tag,'%')) or lower(r.gameSystem) like lower(concat('%',:tag,'%')))")
 Page<Room> search(@Param("q") String q,@Param("tag") String tag, Pageable pageable);
}
