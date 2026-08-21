package online.rollnexa.service;
import lombok.RequiredArgsConstructor; import online.rollnexa.api.*; import online.rollnexa.api.Dto.*; import online.rollnexa.domain.*; import online.rollnexa.repository.*; import org.springframework.data.domain.*; import org.springframework.http.HttpStatus; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service @RequiredArgsConstructor public class ChatService {
 private final MessageRepository messages; private final RoomRepository rooms; private final RoomService roomService; private final ViewMapper mapper;
 @Transactional public MessageView send(Long roomId,User user,String content){roomService.requireMember(roomId,user.getId());String clean=content==null?"":content.trim();if(clean.isEmpty()||clean.length()>2000)throw new ApiException(HttpStatus.BAD_REQUEST,"INVALID_MESSAGE","El mensaje debe tener entre 1 y 2000 caracteres");var m=new Message();m.setRoom(rooms.getReferenceById(roomId));m.setUser(user);m.setContent(clean);messages.save(m);return view(m);}
 @Transactional(readOnly=true) public List<MessageView> history(Long roomId,User user,int page){roomService.requireMember(roomId,user.getId());var result=messages.findByRoomIdOrderBySentAtDesc(roomId,PageRequest.of(Math.max(0,page),50)).getContent();var views=new ArrayList<>(result.stream().map(this::view).toList());Collections.reverse(views);return views;}
 private MessageView view(Message m){return new MessageView(m.getId(),mapper.user(m.getUser()),m.getContent(),m.getSentAt());}
}

