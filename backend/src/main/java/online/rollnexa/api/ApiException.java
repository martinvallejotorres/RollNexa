package online.rollnexa.api;
import org.springframework.http.HttpStatus;
public class ApiException extends RuntimeException { public final HttpStatus status; public final String code; public ApiException(HttpStatus status,String code,String message){super(message);this.status=status;this.code=code;} }

