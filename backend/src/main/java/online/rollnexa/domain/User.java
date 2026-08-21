package online.rollnexa.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=32) private String username;
    @Column(nullable=false, unique=true) private String email;
    @Column(name="password_hash", nullable=false) private String passwordHash;
    @Column(name="avatar_url", length=500) private String avatarUrl;
    @Column(length=280) private String bio;
    @Column(name="registered_at", nullable=false) private Instant registeredAt = Instant.now();
    @Column(name="last_seen_at") private Instant lastSeenAt;
    @Column(name="activity_minutes", nullable=false) private long activityMinutes;
    @Column(nullable=false) private long experience;
    @Column(nullable=false) private int level = 1;
}
