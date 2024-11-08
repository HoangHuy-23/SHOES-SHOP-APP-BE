package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String brandName;
    private String avatar;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
