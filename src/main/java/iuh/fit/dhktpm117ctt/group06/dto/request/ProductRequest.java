package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class ProductRequest {
    @NotNull(message = "PRODUCT_NAME_INVALID")
    @NotBlank(message = "PRODUCT_NAME_INVALID")
    private String description;
    private MultipartFile avatar;
    private Date createdDate;
    @NotNull(message = "BRAND_ID_INVALID")
    private String brandId;
    @NotNull(message = "CATEGORY_ID_INVALID")
    private String categoryId;
}
