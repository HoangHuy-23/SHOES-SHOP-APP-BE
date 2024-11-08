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

    private int quantity;

    @OneToOne
    @JoinColumn(name = "product_item_id")
    @MapsId("productItemId")
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @MapsId("cartId")
    private Cart cart;
}
