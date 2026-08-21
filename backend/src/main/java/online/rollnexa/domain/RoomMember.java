package online.rollnexa.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="room_members", uniqueConstraints=@UniqueConstraint(columnNames={"room_id","user_id"})) @Getter @Setter @NoArgsConstructor
public class RoomMember {
    public enum Role { GM, PLAYER }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private Room room;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private User user;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
    @Column(name="joined_at", nullable=false) private Instant joinedAt = Instant.now();
}

