package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant;
@Entity @Table(name="messages") @Getter @Setter @NoArgsConstructor
public class Message {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Room room;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private User user;
 @Column(nullable=false,length=2000) private String content;
 @Column(name="sent_at",nullable=false) private Instant sentAt=Instant.now();
}

