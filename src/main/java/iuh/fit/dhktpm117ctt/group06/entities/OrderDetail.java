package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int quantity;
    private double pricePerItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
