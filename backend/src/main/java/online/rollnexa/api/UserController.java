package online.rollnexa.api;
import jakarta.servlet.http.HttpSession; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import online.rollnexa.api.Dto.*; import online.rollnexa.service.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequiredArgsConstructor
public class UserController {
 private final UserService users; private final FriendService friends; private final CurrentUser current;
 @GetMapping("/api/users/{username}") public UserProfile profile(@PathVariable String username,Authentication a){Long viewer=null;try{viewer=current.require(a).getId();}catch(ApiException ignored){}return users.profile(username,viewer);}
 @GetMapping("/api/users/me/rooms") public List<RoomCard> rooms(Authentication a){return users.myRooms(current.require(a));}
 @PostMapping("/api/users/me/activity") public UserSummary activity(@Valid @RequestBody ActivityInput in,Authentication a,HttpSession session){return users.activity(current.require(a),in.minutes(),session);}
 @PostMapping("/api/users/me/presence") @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT) public void presence(Authentication a){users.presence(current.require(a));}
 @GetMapping("/api/friends") public List<FriendshipView> list(Authentication a){return friends.list(current.require(a));}
 @PostMapping("/api/friends/{username}") public FriendshipView request(@PathVariable String username,Authentication a){return friends.request(current.require(a),username);}
 @PostMapping("/api/friends/{id}/accept") public FriendshipView accept(@PathVariable Long id,Authentication a){return friends.resolve(current.require(a),id,true);}
 @PostMapping("/api/friends/{id}/reject") public FriendshipView reject(@PathVariable Long id,Authentication a){return friends.resolve(current.require(a),id,false);}
}
