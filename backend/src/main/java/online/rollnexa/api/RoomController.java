package online.rollnexa.api;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import online.rollnexa.api.Dto.*; import online.rollnexa.domain.User; import online.rollnexa.service.*; import org.springframework.data.domain.Page; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/rooms") @RequiredArgsConstructor
public class RoomController {
 private final RoomService service; private final CurrentUser current;
 @GetMapping public Page<RoomCard> search(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String tag,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="12") int size,Authentication auth){return service.search(q,tag,page,size,optionalId(auth));}
 @PostMapping public ResponseEntity<RoomCard> create(@Valid @RequestBody RoomCreate in,Authentication auth){return ResponseEntity.status(201).body(service.create(in,current.require(auth)));}
 @GetMapping("/{id}") public RoomDetail detail(@PathVariable Long id,Authentication auth){return service.detail(id,optionalId(auth));}
 @PutMapping("/{id}") public RoomCard edit(@PathVariable Long id,@Valid @RequestBody RoomCreate in,Authentication auth){return service.edit(id,in,current.require(auth));}
 @PostMapping("/{id}/join") public JoinResult join(@PathVariable Long id,Authentication auth){return service.join(id,current.require(auth));}
 @DeleteMapping("/{id}/members/me") @ResponseStatus(HttpStatus.NO_CONTENT) public void leave(@PathVariable Long id,Authentication auth){service.leave(id,current.require(auth));}
 @DeleteMapping("/{id}/members/{userId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void kick(@PathVariable Long id,@PathVariable Long userId,Authentication auth){service.kick(id,userId,current.require(auth));}
 @GetMapping("/{id}/requests") public List<JoinRequestView> pending(@PathVariable Long id,Authentication auth){return service.pending(id,current.require(auth));}
 @PostMapping("/{id}/requests/{requestId}/accept") @ResponseStatus(HttpStatus.NO_CONTENT) public void accept(@PathVariable Long id,@PathVariable Long requestId,Authentication auth){service.resolve(id,requestId,true,current.require(auth));}
 @PostMapping("/{id}/requests/{requestId}/reject") @ResponseStatus(HttpStatus.NO_CONTENT) public void reject(@PathVariable Long id,@PathVariable Long requestId,Authentication auth){service.resolve(id,requestId,false,current.require(auth));}
 private Long optionalId(Authentication auth){try{return current.require(auth).getId();}catch(ApiException e){return null;}}
}
