package online.rollnexa.config;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*; import org.springframework.http.HttpMethod; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.csrf.CookieCsrfTokenRepository; import org.springframework.web.cors.*; import java.util.*;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(12);}
 @Bean SecurityFilterChain security(HttpSecurity http,CorsConfigurationSource cors) throws Exception {
  http.cors(c->c.configurationSource(cors)).csrf(c->c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/ws/**").permitAll().requestMatchers(HttpMethod.GET,"/api/rooms/**","/api/users/**").permitAll().anyRequest().authenticated())
   .logout(l->l.logoutUrl("/api/auth/logout").logoutSuccessHandler((req,res,auth)->res.setStatus(204)))
   .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->{res.setStatus(401);res.setContentType("application/json");res.getWriter().write("{\"status\":401,\"error\":\"UNAUTHENTICATED\",\"message\":\"Debés iniciar sesión\"}");}));
  return http.build();
 }
 @Bean CorsConfigurationSource cors(@Value("${app.cors-allowed-origins}") String origins){var c=new CorsConfiguration();c.setAllowedOrigins(Arrays.stream(origins.split(",")).map(String::trim).toList());c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Content-Type","X-XSRF-TOKEN"));c.setAllowCredentials(true);var s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}

