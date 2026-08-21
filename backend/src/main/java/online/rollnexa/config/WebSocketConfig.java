package online.rollnexa.config;
import lombok.RequiredArgsConstructor; import online.rollnexa.repository.*; import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.Configuration; import org.springframework.messaging.*; import org.springframework.messaging.simp.config.*; import org.springframework.messaging.support.ChannelInterceptor; import org.springframework.web.socket.config.annotation.*;
@Configuration @EnableWebSocketMessageBroker @RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 private final UserRepository users; private final RoomMemberRepository members;
 @Value("${app.cors-allowed-origins}") private String allowedOrigins;
 public void configureMessageBroker(MessageBrokerRegistry r){r.enableSimpleBroker("/topic");r.setApplicationDestinationPrefixes("/app");}
 public void registerStompEndpoints(StompEndpointRegistry r){r.addEndpoint("/ws").setAllowedOrigins(java.util.Arrays.stream(allowedOrigins.split(",")).map(String::trim).toArray(String[]::new));}
 public void configureClientInboundChannel(ChannelRegistration reg){reg.interceptors(new ChannelInterceptor(){public Message<?> preSend(Message<?> msg,MessageChannel ch){var accessor=org.springframework.messaging.simp.stomp.StompHeaderAccessor.wrap(msg);if(accessor.getCommand()==org.springframework.messaging.simp.stomp.StompCommand.SUBSCRIBE&&accessor.getDestination()!=null&&accessor.getDestination().startsWith("/topic/rooms/")){var principal=accessor.getUser();if(principal==null)throw new org.springframework.security.access.AccessDeniedException("Authentication required");Long roomId=Long.valueOf(accessor.getDestination().substring("/topic/rooms/".length()));var user=users.findByUsernameIgnoreCase(principal.getName()).orElseThrow();if(!members.existsByRoomIdAndUserId(roomId,user.getId()))throw new org.springframework.security.access.AccessDeniedException("Members only");}return msg;}});}
}
