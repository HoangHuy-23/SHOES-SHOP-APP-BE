package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class ProductRequest {
    @NotNull(message = "PRODUCT_NAME_INVALID")
    @NotBlank(message = "PRODUCT_NAME_INVALID")
    private String name;
    private MultipartFile avatar;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date createdDate;
    @NotNull(message = "BRAND_ID_INVALID")
    private String brandId;
    @NotNull(message = "CATEGORY_ID_INVALID")
    private String categoryId;
}
