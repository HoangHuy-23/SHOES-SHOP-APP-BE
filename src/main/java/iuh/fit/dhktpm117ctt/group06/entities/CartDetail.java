package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "cart_details")
public class CartDetail {
    @EmbeddedId
    private CartDetailPK cartDetailPK;
    private Date checkInDate;
    private Date checkOutDate;
    private int numOfAdult;
    private int numOfChildren;
    private int quantity;

    @OneToOne
    @JoinColumn(name = "room_item_id")
    @MapsId("roomItemId")
    private RoomItem roomItem;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @MapsId("cartId")
    private Cart cart;
}
