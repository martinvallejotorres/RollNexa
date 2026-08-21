package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant;
@Entity @Table(name="join_requests", uniqueConstraints=@UniqueConstraint(columnNames={"room_id","user_id"})) @Getter @Setter @NoArgsConstructor
public class JoinRequest {
 public enum Status { PENDING, ACCEPTED, REJECTED }
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Room room;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private User user;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 @Column(name="resolved_at") private Instant resolvedAt;
}

