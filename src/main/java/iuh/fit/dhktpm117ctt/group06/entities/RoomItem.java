package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "RoomItem")
public class RoomItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
}
