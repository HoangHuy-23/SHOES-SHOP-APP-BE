package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductItemRequest {
    @Min(value = 0, message = "PRICE_INVALID")
    @NotNull(message = "PRICE_INVALID")
    @NotBlank(message = "PRICE_INVALID")
    private double price;
    @Min(value = 0, message = "QUANTITY_INVALID")
    @NotNull(message = "QUANTITY_INVALID")
    @NotBlank(message = "QUANTITY_INVALID")
    private int quantity;
    private MultipartFile[] listDetailImages;
    @NotNull(message = "COLOR_INVALID")
    @NotBlank(message = "COLOR_INVALID")
    private String color;
    @NotNull(message = "SIZE_INVALID")
    @NotBlank(message = "SIZE_INVALID")
    private String size;
    @NotNull(message = "PRODUCT_ID_INVALID")
    private String productId;
    @NotNull(message = "STATUS_INVALID")
    private ProductStatus status;
}