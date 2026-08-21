package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant;
@Entity @Table(name="friendships",uniqueConstraints=@UniqueConstraint(columnNames={"requester_id","addressee_id"})) @Getter @Setter @NoArgsConstructor
public class Friendship {
 public enum Status { PENDING, ACCEPTED, REJECTED }
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="requester_id") private User requester;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="addressee_id") private User addressee;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
}
