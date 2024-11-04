package iuh.fit.dhktpm117ctt.group06.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @OneToMany(mappedBy = "option")
    @JsonIgnore
    @ToString.Exclude
    private List<ServiceInformation> serviceInformations;

    @OneToMany(mappedBy = "option")
    @JsonIgnore
    @ToString.Exclude
    private List<RoomItem> roomItems;

    @ManyToMany(mappedBy = "options")
    private List<RoomType> roomTypes;
}
