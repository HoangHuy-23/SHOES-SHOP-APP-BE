package iuh.fit.dhktpm117ctt.group06.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String homeNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
