package iuh.fit.dhktpm117ctt.group06.dto.response;

import iuh.fit.dhktpm117ctt.group06.entities.Brand;
import iuh.fit.dhktpm117ctt.group06.entities.Category;

import java.util.Date;

public class ProductResponse {
    private String id;
    private String description;
    private String avatar;
    private double rating;
    private Date createdDate;
    private BrandResponse brand;
    private CategoryResponse category;
}
