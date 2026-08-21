package online.rollnexa.repository;
import online.rollnexa.domain.User; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long> { Optional<User> findByUsernameIgnoreCase(String username); Optional<User> findByEmailIgnoreCase(String email); boolean existsByUsernameIgnoreCase(String username); boolean existsByEmailIgnoreCase(String email); }

