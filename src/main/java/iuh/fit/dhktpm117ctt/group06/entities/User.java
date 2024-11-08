package iuh.fit.dhktpm117ctt.group06.entities;

import iuh.fit.dhktpm117ctt.group06.entities.enums.Gender;
import iuh.fit.dhktpm117ctt.group06.entities.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

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

    @OneToMany(mappedBy = "user")
    private List<Address> address;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<FeedBack> feedBacks;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

}
