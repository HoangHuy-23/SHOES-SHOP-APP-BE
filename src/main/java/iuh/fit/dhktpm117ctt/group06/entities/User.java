package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
