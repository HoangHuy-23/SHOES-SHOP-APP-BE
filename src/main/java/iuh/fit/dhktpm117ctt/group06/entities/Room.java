package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int floorNumber;
    private String roomNumber;
    private String capicity;
    @ElementCollection
    @CollectionTable(name = "room_detail_image", joinColumns = @JoinColumn(name = "room_id"))
    private List<String> listDetailImage;
    private String additionInformation;
    private String avatar;
    private double rating;
    private double estimatedPrice;
    @ElementCollection
    @CollectionTable(name = "room_rule", joinColumns = @JoinColumn(name = "room_id"))
    private List<String> listRule;
    private int numOfBed;
    private double acreage;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<RoomItem> roomItems;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<FeedBack> feedBacks;

}
