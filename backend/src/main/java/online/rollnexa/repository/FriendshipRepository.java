package online.rollnexa.repository;
import online.rollnexa.domain.Friendship; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface FriendshipRepository extends JpaRepository<Friendship,Long> {
 @Query("select f from Friendship f where (f.requester.id=:a and f.addressee.id=:b) or (f.requester.id=:b and f.addressee.id=:a)") Optional<Friendship> between(@Param("a") Long a,@Param("b") Long b);
 @Query("select f from Friendship f where f.status='ACCEPTED' and (f.requester.id=:id or f.addressee.id=:id)") List<Friendship> acceptedFor(@Param("id") Long id);
 List<Friendship> findByAddresseeIdAndStatus(Long id,Friendship.Status status);
}

