package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    @Column(columnDefinition = "longtext")
    private String content;
    private String author;
    private String avatar;
    private String createdDate;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
