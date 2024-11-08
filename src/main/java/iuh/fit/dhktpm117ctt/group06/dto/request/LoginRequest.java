package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class LoginRequest {
    @NotNull(message = "EMAIL_INVALID")
    @Email(message = "EMAIL_INVALID")
    private String email;
    @NotNull(message = "PASSWORD_INVALID")
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
}
