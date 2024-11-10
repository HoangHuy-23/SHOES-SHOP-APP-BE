package iuh.fit.dhktpm117ctt.group06.dto.response;

import iuh.fit.dhktpm117ctt.group06.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String avatar;
    private Gender gender;
}
