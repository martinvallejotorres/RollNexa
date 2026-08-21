package online.rollnexa.service;

import online.rollnexa.api.ApiException;
import online.rollnexa.api.Dto.RoomCreate;
import online.rollnexa.domain.*;
import online.rollnexa.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
 @Mock RoomRepository rooms; @Mock RoomMemberRepository members; @Mock JoinRequestRepository requests;
 RoomService service; User gm; User player; Room room;
 @BeforeEach void setup(){service=new RoomService(rooms,members,requests,new ViewMapper(members));gm=user(1L,"GM");player=user(2L,"Player");room=new Room();room.setId(10L);room.setName("Barovia");room.setDescription("Una campaña");room.setGameSystem("D&D 5e");room.setCampaignType(Room.CampaignType.LONG_CAMPAIGN);room.setJoinMode(Room.JoinMode.OPEN);room.setMaxParticipants(4);lenient().when(members.findMembers(anyLong())).thenReturn(List.of());}
 @Test void creatorBecomesGm(){service.create(new RoomCreate("Mesa","Descripción","D&D 5e",Room.CampaignType.ONE_SHOT,5,List.of("Terror"),Room.JoinMode.OPEN),gm);verify(rooms).save(any(Room.class));verify(members).save(argThat(m->m.getUser()==gm&&m.getRole()==RoomMember.Role.GM));}
 @Test void openRoomAddsPlayer(){when(rooms.lockById(10L)).thenReturn(Optional.of(room));when(members.existsByRoomIdAndUserId(10L,2L)).thenReturn(false);when(members.countByRoomId(10L)).thenReturn(1L);assertThat(service.join(10L,player).status()).isEqualTo("JOINED");verify(members).save(argThat(m->m.getRole()==RoomMember.Role.PLAYER));}
 @Test void approvalRoomCreatesRequest(){room.setJoinMode(Room.JoinMode.APPROVAL_REQUIRED);when(rooms.lockById(10L)).thenReturn(Optional.of(room));when(members.countByRoomId(10L)).thenReturn(1L);when(requests.findByRoomIdAndUserId(10L,2L)).thenReturn(Optional.empty());assertThat(service.join(10L,player).status()).isEqualTo("PENDING");verify(requests).save(argThat(r->r.getStatus()==JoinRequest.Status.PENDING));}
 @Test void fullRoomRejectsJoin(){when(rooms.lockById(10L)).thenReturn(Optional.of(room));when(members.countByRoomId(10L)).thenReturn(4L);assertThatThrownBy(()->service.join(10L,player)).isInstanceOf(ApiException.class).extracting("code").isEqualTo("ROOM_FULL");}
 @Test void gmAcceptsPendingRequest(){var membership=new RoomMember();membership.setUser(gm);membership.setRoom(room);membership.setRole(RoomMember.Role.GM);var request=new JoinRequest();request.setId(20L);request.setRoom(room);request.setUser(player);when(members.findByRoomIdAndUserId(10L,1L)).thenReturn(Optional.of(membership));when(requests.findById(20L)).thenReturn(Optional.of(request));when(rooms.lockById(10L)).thenReturn(Optional.of(room));when(members.countByRoomId(10L)).thenReturn(1L);service.resolve(10L,20L,true,gm);assertThat(request.getStatus()).isEqualTo(JoinRequest.Status.ACCEPTED);verify(members).save(argThat(m->m.getUser()==player&&m.getRole()==RoomMember.Role.PLAYER));}
 @Test void playerCannotUseGmActions(){var membership=new RoomMember();membership.setUser(player);membership.setRoom(room);membership.setRole(RoomMember.Role.PLAYER);when(members.findByRoomIdAndUserId(10L,2L)).thenReturn(Optional.of(membership));assertThatThrownBy(()->service.requireGm(10L,2L)).isInstanceOf(ApiException.class).extracting("code").isEqualTo("GM_ONLY");}
 private User user(Long id,String name){var u=new User();u.setId(id);u.setUsername(name);return u;}
}
