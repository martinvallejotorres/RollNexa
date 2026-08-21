package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*; import java.time.*;
@Entity @Table(name="game_sessions") @Getter @Setter @NoArgsConstructor
public class GameSession {
 public enum Status { PROPOSED, CONFIRMED, CANCELLED }
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Room room;
 @Column(name="scheduled_at",nullable=false) private Instant scheduledAt;
 @Column(nullable=false,length=120) private String title;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PROPOSED;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
}

