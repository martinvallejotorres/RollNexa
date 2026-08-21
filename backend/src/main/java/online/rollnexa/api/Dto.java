package online.rollnexa.api;
import jakarta.validation.constraints.*; import online.rollnexa.domain.*; import java.time.*; import java.util.*;
public final class Dto {
 private Dto(){}
 public record RegisterRequest(@NotBlank @Pattern(regexp="^[A-Za-z0-9_]{3,32}$") String username,@Email @NotBlank String email,@Size(min=8,max=100) String password){}
 public record LoginRequest(@NotBlank String username,@NotBlank String password){}
 public record UserSummary(Long id,String username,String avatarUrl,int level){}
 public record UserProfile(Long id,String username,String avatarUrl,String bio,int level,long experience,long nextLevelExperience,long activityMinutes,long activeCampaigns,long completedCampaigns,boolean friend){}
 public record AuthResponse(boolean authenticated,UserSummary user){}
 public record RoomCreate(@NotBlank @Size(max=100) String name,@NotBlank @Size(max=2000) String description,@NotBlank @Size(max=80) String gameSystem,@NotNull Room.CampaignType campaignType,@Min(2) @Max(20) int maxParticipants,@Size(max=10) List<@Size(max=30) String> tags,@NotNull Room.JoinMode joinMode){}
 public record RoomCard(Long id,String name,String description,String gameSystem,Room.CampaignType campaignType,List<String> tags,Room.JoinMode joinMode,long memberCount,int maxParticipants,String gm,boolean member){}
 public record RoomDetail(RoomCard room,List<MemberView> members,String currentRole){}
 public record MemberView(Long id,String username,String avatarUrl,int level,RoomMember.Role role){}
 public record JoinResult(String status,String message){}
 public record JoinRequestView(Long id,UserSummary user,Instant createdAt){}
 public record AvailabilityInput(@NotNull LocalDate date,LocalTime startTime,LocalTime endTime,boolean available){}
 public record AvailabilityView(Long id,LocalDate date,LocalTime startTime,LocalTime endTime,boolean available,UserSummary user){}
 public record AvailabilityDay(LocalDate date,long available,long total){}
 public record SessionInput(@NotBlank @Size(max=120) String title,@NotNull @Future Instant scheduledAt){}
 public record SessionView(Long id,String title,Instant scheduledAt,GameSession.Status status,long available,long total,Boolean myResponse){}
 public record SessionResponseInput(boolean available){}
 public record MessageInput(@NotBlank @Size(max=2000) String content){}
 public record MessageView(Long id,UserSummary user,String content,Instant sentAt){}
 public record FriendshipView(Long id,UserSummary user,Friendship.Status status,boolean incoming,boolean online){}
 public record ActivityInput(@Min(1) @Max(15) int minutes){}
}
