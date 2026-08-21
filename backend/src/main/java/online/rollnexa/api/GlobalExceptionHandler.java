package online.rollnexa.api;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 public record ErrorBody(Instant timestamp,int status,String error,String message){}
 @ExceptionHandler(ApiException.class) ResponseEntity<ErrorBody> api(ApiException e){return ResponseEntity.status(e.status).body(new ErrorBody(Instant.now(),e.status.value(),e.code,e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorBody> validation(MethodArgumentNotValidException e){String message=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Datos inválidos"); return ResponseEntity.badRequest().body(new ErrorBody(Instant.now(),400,"VALIDATION_ERROR",message));}
 @ExceptionHandler(Exception.class) ResponseEntity<ErrorBody> fallback(Exception e){return ResponseEntity.internalServerError().body(new ErrorBody(Instant.now(),500,"INTERNAL_ERROR","Ocurrió un error inesperado"));}
}

