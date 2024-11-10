package iuh.fit.dhktpm117ctt.group06.dto.response;

import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductItemResponse {
    private String id;
    private double price;
    private int quantity;
    private List<String> listDetailImages;
    private String color;
    private String size;
    private ProductResponse product;
    private ProductStatus status;
}
