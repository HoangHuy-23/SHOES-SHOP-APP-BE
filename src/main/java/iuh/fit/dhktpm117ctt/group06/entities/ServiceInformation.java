package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "ServiceInformation")
public class    ServiceInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private double price;
    private String description;
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
}
