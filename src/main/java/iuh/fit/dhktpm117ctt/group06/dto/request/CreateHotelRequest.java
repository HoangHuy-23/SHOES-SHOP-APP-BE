package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.entities.Address;
import iuh.fit.dhktpm117ctt.group06.entities.Hotel;
import iuh.fit.dhktpm117ctt.group06.util.ValidationConstraints;
import iuh.fit.dhktpm117ctt.group06.validator.CloseTimeConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class CreateHotelRequest {
    @NotBlank(message = "HOTEL_NAME_INVALID")
    @NotNull(message = "HOTEL_NAME_INVALID")
    @Size(min = 2, max = 50, message = "HOTEL_NAME_INVALID")
    @Pattern(regexp = ValidationConstraints.VIETNAMESE_NAME_REGEX, message = "HOTEL_NAME_INVALID")
    private String name;
    @NotBlank(message = "PHONE_INVALID")
    @NotNull(message = "PHONE_INVALID")
    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    @Pattern(regexp = ValidationConstraints.PHONE_REGEX, message = "PHONE_INVALID")
    private String phone;
    @NotBlank(message = "EMAIL_INVALID")
    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;
    @NotNull(message = "OPEN_TIME_INVALID")
    private LocalTime openTime;
    @NotNull(message = "CLOSE_TIME_INVALID")
    @CloseTimeConstraint(message = "CLOSE_TIME_INVALID")
    private LocalTime closeTime;
    @NotNull(message = "AVATAR_INVALID")
    private MultipartFile avatar;
    @NotNull(message = "DETAIL_IMAGES_INVALID")
    private List<MultipartFile> listDetailImages;
    private Address address;

    public Hotel toHotel() {
        return Hotel.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .openTime(openTime)
                .closeTime(closeTime)
                .address(address)
                .build();
    }
}
