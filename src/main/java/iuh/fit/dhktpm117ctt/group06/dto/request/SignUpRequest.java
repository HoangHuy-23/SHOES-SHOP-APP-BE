package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.util.ValidationConstraints;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SignUpRequest {
    @Email(message = "EMAIL_INVALID")
    private String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    @Size(min = 2, max = 50, message = "FIRSTNAME_INVALID")
    @Pattern(regexp = ValidationConstraints.VIETNAMESE_NAME_REGEX, message = "FIRSTNAME_INVALID")
    private String firstName;
    @Size(min = 2, max = 50, message = "LASTNAME_INVALID")
    @Pattern(regexp = ValidationConstraints.VIETNAMESE_NAME_REGEX, message = "LASTNAME_INVALID")
    private String lastName;
}
