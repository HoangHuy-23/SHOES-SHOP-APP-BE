package iuh.fit.dhktpm117ctt.group06.dto.response;

import java.util.Date;
import java.util.List;

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
}
