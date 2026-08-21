package online.rollnexa.service;
import lombok.RequiredArgsConstructor; import online.rollnexa.api.ApiException; import online.rollnexa.domain.User; import online.rollnexa.repository.UserRepository; import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Component;
@Component @RequiredArgsConstructor public class CurrentUser {
 private final UserRepository users;
 public User require(Authentication auth){if(auth==null||!auth.isAuthenticated()||"anonymousUser".equals(auth.getPrincipal()))throw new ApiException(HttpStatus.UNAUTHORIZED,"UNAUTHENTICATED","Debés iniciar sesión");return users.findByUsernameIgnoreCase(auth.getName()).orElseThrow(()->new ApiException(HttpStatus.UNAUTHORIZED,"UNAUTHENTICATED","La sesión no es válida"));}
 public User byName(String username){return users.findByUsernameIgnoreCase(username).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"USER_NOT_FOUND","El usuario no existe"));}
}

