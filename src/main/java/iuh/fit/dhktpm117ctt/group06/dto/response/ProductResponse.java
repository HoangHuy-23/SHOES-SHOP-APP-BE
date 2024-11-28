package iuh.fit.dhktpm117ctt.group06.dto.response;

import java.util.Date;
import java.util.List;

import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductColor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String avatar;
    private double rating;
    private Date createdDate;
    private List<String> listDetailImages;
//    private BrandResponse brand;
    private CategoryResponse category;
    private List<ProductColor> colors;
    private List<String> sizes;
}
