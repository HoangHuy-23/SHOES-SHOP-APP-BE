package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CitizenCard {
    private String cardNumber;
    private Date dateOfBirth;
    private Date validDate;
    private Date licenseDate;
    private String nationality;
}
