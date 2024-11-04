package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
    @Email(message = "EMAIL_INVALID")
    private String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String firstName;
    private String lastName;
}
