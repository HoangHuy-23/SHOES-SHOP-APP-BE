package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String homeNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
}