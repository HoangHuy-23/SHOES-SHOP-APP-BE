package iuh.fit.dhktpm117ctt.group06.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
}
