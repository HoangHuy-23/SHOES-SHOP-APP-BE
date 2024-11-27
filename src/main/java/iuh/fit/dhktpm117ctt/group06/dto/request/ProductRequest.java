package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import iuh.fit.dhktpm117ctt.group06.entities.Brand;
import iuh.fit.dhktpm117ctt.group06.entities.Category;

import java.util.Date;

@NoArgsConstructor
@Data
public class ProductRequest {
    @NotNull(message = "PRODUCT_NAME_INVALID")
    @NotBlank(message = "PRODUCT_NAME_INVALID")
    private String name;
    private MultipartFile avatar;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdDate;
//    @NotNull(message = "BRAND_ID_INVALID")
//    private Brand brand;
    @NotNull(message = "CATEGORY_ID_INVALID")
    private Category category;
}
