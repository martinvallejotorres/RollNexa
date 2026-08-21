package online.rollnexa.service;
import lombok.RequiredArgsConstructor; import online.rollnexa.repository.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
 private final UserRepository users;
 public UserDetails loadUserByUsername(String username){var u=users.findByUsernameIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException(username));return User.withUsername(u.getUsername()).password(u.getPasswordHash()).roles("USER").build();}
}

