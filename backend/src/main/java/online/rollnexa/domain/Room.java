package online.rollnexa.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity @Table(name="rooms") @Getter @Setter @NoArgsConstructor
public class Room {
    public enum CampaignType { ONE_SHOT, SHORT_CAMPAIGN, LONG_CAMPAIGN }
    public enum JoinMode { OPEN, APPROVAL_REQUIRED }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=100) private String name;
    @Column(nullable=false, length=2000) private String description;
    @Column(name="game_system", nullable=false, length=80) private String gameSystem;
    @Enumerated(EnumType.STRING) @Column(name="campaign_type", nullable=false) private CampaignType campaignType;
    @Column(name="max_participants", nullable=false) private int maxParticipants;
    @Column(nullable=false, length=500) private String tags = "";
    @Enumerated(EnumType.STRING) @Column(name="join_mode", nullable=false) private JoinMode joinMode;
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    @Column(nullable=false) private boolean completed;
    @Version private long version;
}

