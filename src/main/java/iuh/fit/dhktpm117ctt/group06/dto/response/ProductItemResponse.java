package iuh.fit.dhktpm117ctt.group06.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductItemResponse {
    private String id;
    private double price;
    private int quantity;
    private List<String> listDetailImages;
    private String color;
    private String size;
    private ProductResponse product;
    private String status;
}
