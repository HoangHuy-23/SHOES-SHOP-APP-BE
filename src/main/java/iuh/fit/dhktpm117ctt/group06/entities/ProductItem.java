package iuh.fit.dhktpm117ctt.group06.entities;

import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductColor;
import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product_items")
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double price;
    private int quantity;
    @ElementCollection
    @CollectionTable(
            name = "product_item_detail_images",
            joinColumns = @JoinColumn(name = "product_item_id")
    )
    private List<String> listDetailImages;
    @Enumerated(EnumType.STRING)
    private ProductColor color;
    private String size;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
