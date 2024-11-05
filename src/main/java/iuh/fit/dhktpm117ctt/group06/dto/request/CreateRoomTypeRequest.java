package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateRoomTypeRequest {
    private String name;
    private String description;
    private List<String> optionIds;
}
