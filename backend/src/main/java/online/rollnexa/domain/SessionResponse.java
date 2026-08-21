package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="session_responses",uniqueConstraints=@UniqueConstraint(columnNames={"session_id","user_id"})) @Getter @Setter @NoArgsConstructor
public class SessionResponse {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private GameSession session;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private User user;
 @Column(nullable=false) private boolean available;
}

