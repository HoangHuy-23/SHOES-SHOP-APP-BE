package iuh.fit.dhktpm117ctt.group06.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iuh.fit.dhktpm117ctt.group06.entities.enums.Gender;
import iuh.fit.dhktpm117ctt.group06.entities.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;

    private String lastName;
    private String phone;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Embedded
    private CitizenCard citizenCard;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    @ToString.Exclude
    private List<Hotel> hotels;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private List<FeedBack> feedBacks;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private List<Booking> bookings;

}
