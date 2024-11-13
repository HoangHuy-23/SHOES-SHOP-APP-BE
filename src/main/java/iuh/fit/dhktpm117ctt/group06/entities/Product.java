package iuh.fit.dhktpm117ctt.group06.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductGender;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private String warrantyInformation;
    private String returnInformation;
    private String avatar;
    private String shippingInformation;
    private double rating;
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    private ProductGender gender;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<ProductItem> productItems;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private ProductCollection collection;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @ToString.Exclude
    private List<FeedBack> feedBacks;

}
