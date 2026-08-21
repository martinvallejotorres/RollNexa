package online.rollnexa.api;
import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import online.rollnexa.api.Dto.*; import online.rollnexa.service.*; import org.springframework.messaging.handler.annotation.*; import org.springframework.messaging.simp.SimpMessagingTemplate; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.security.Principal; import java.util.*;
@RestController @RequiredArgsConstructor
public class ChatController {
 private final ChatService chat; private final CurrentUser current; private final SimpMessagingTemplate messaging;
 @GetMapping("/api/rooms/{roomId}/messages") public List<MessageView> history(@PathVariable Long roomId,@RequestParam(defaultValue="0") int page,Authentication a){return chat.history(roomId,current.require(a),page);}
 @MessageMapping("/rooms/{roomId}/chat") public void send(@DestinationVariable Long roomId,@Valid MessageInput in,Principal principal){var view=chat.send(roomId,current.byName(principal.getName()),in.content());messaging.convertAndSend("/topic/rooms/"+roomId,view);}
}
