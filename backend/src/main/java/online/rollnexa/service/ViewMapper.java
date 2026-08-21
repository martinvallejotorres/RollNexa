package online.rollnexa.service;
import lombok.RequiredArgsConstructor; import online.rollnexa.api.Dto.*; import online.rollnexa.domain.*; import online.rollnexa.repository.*; import org.springframework.stereotype.Component; import java.util.*;
@Component @RequiredArgsConstructor
public class ViewMapper {
 private final RoomMemberRepository members;
 public UserSummary user(User u){return new UserSummary(u.getId(),u.getUsername(),u.getAvatarUrl(),u.getLevel());}
 public RoomCard room(Room r,Long viewerId){var all=members.findMembers(r.getId());String gm=all.stream().filter(x->x.getRole()==RoomMember.Role.GM).map(x->x.getUser().getUsername()).findFirst().orElse("—");boolean member=viewerId!=null&&all.stream().anyMatch(x->x.getUser().getId().equals(viewerId));return new RoomCard(r.getId(),r.getName(),r.getDescription(),r.getGameSystem(),r.getCampaignType(),parseTags(r.getTags()),r.getJoinMode(),all.size(),r.getMaxParticipants(),gm,member);}
 public MemberView member(RoomMember m){return new MemberView(m.getUser().getId(),m.getUser().getUsername(),m.getUser().getAvatarUrl(),m.getUser().getLevel(),m.getRole());}
 public List<String> parseTags(String tags){return tags==null||tags.isBlank()?List.of():Arrays.stream(tags.split(",")).map(String::trim).filter(s->!s.isBlank()).toList();}
}

