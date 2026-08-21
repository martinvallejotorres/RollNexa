package online.rollnexa.api;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import online.rollnexa.api.Dto.*; import online.rollnexa.service.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@RestController @RequestMapping("/api/rooms/{roomId}") @RequiredArgsConstructor
public class ScheduleController {
 private final ScheduleService service; private final CurrentUser current;
 @PutMapping("/availability") public AvailabilityView vote(@PathVariable Long roomId,@Valid @RequestBody AvailabilityInput in,Authentication a){return service.vote(roomId,current.require(a),in);}
 @GetMapping("/availability") public Map<String,Object> calendar(@PathVariable Long roomId,@RequestParam LocalDate from,@RequestParam LocalDate to,Authentication a){return service.calendar(roomId,current.require(a),from,to);}
 @PostMapping("/sessions") public SessionView create(@PathVariable Long roomId,@Valid @RequestBody SessionInput in,Authentication a){return service.createSession(roomId,current.require(a),in);}
 @GetMapping("/sessions") public List<SessionView> list(@PathVariable Long roomId,Authentication a){return service.listSessions(roomId,current.require(a));}
 @PutMapping("/sessions/{id}/response") public SessionView respond(@PathVariable Long roomId,@PathVariable Long id,@RequestBody SessionResponseInput in,Authentication a){return service.respond(roomId,id,current.require(a),in.available());}
 @PostMapping("/sessions/{id}/confirm") public SessionView confirm(@PathVariable Long roomId,@PathVariable Long id,Authentication a){return service.confirm(roomId,id,current.require(a));}
}

