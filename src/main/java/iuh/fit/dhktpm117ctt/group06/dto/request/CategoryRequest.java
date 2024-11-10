package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    @NotNull(message = "CATEGORY_NAME_INVALID")
    @NotBlank(message = "CATEGORY_NAME_INVALID")
    private String name;
    @NotNull(message = "CATEGORY_DESCRIPTION_INVALID")
    @NotBlank(message = "CATEGORY_DESCRIPTION_INVALID")
    private String description;
}
