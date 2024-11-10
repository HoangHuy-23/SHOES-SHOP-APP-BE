package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @UniqueElements(message = "Title must be unique")
    private String title;
    private String content;
}
