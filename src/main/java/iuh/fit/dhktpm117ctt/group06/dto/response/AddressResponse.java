package iuh.fit.dhktpm117ctt.group06.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressResponse {
    private String id;
    private String homeNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
}
