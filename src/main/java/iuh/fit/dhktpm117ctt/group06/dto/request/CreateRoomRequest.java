package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateRoomRequest {
    @Min(0)
    private int floorNumber;
    @NotBlank
    private String roomNumber;
    private String capicity;
    private List<String> listDetailImage;
    private String additionInformation;
    private String avatar;
    @Min(0)
    private double estimatedPrice;
    private List<String> listRule;
    @Min(1)
    private int numOfBed;
    @Min(0)
    private double acreage;
    @NotBlank
    private String hotelId;
    @NotBlank
    private String roomTypeId;
}
