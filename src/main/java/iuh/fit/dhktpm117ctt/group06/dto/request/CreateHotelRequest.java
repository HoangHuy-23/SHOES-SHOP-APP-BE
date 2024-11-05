package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.entities.Address;
import iuh.fit.dhktpm117ctt.group06.util.ValidationConstraints;
import iuh.fit.dhktpm117ctt.group06.validator.CloseTimeConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class CreateHotelRequest {
    @Size(min = 2, max = 50, message = "HOTEL_NAME_INVALID")
    @Pattern(regexp = ValidationConstraints.VIETNAMESE_NAME_REGEX, message = "HOTEL_NAME_INVALID")
    private String name;
    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    @Pattern(regexp = ValidationConstraints.PHONE_REGEX, message = "PHONE_INVALID")
    private String phone;
    @Email(message = "EMAIL_INVALID")
    private String email;
    private LocalTime openTime;
    @CloseTimeConstraint()
    private LocalTime closeTime;
    private String avatar;
    private List<String> listDetailImages;
    private Address address;
}
