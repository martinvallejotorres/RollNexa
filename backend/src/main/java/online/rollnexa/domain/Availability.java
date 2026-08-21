package online.rollnexa.domain;
import jakarta.persistence.*; import lombok.*; import java.time.*;
@Entity @Table(name="availabilities", uniqueConstraints=@UniqueConstraint(columnNames={"room_id","user_id","available_date"})) @Getter @Setter @NoArgsConstructor
public class Availability {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private Room room;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) private User user;
 @Column(name="available_date",nullable=false) private LocalDate date;
 @Column(name="start_time") private LocalTime startTime;
 @Column(name="end_time") private LocalTime endTime;
 @Column(nullable=false) private boolean available;
}

