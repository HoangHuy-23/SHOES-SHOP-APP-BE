package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String phone;
    private String email;
    private float rating;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String avatar;
    @ElementCollection
    @CollectionTable(name = "hotel_detail_images",
            joinColumns = {
                    @JoinColumn(name = "hotel_id")
            })
    private List<String> listDetailImages;
    @Embedded
    private Address address;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(mappedBy = "hotel")
    private List<Post> posts;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;
}