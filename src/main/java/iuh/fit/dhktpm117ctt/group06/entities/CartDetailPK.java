package iuh.fit.dhktpm117ctt.group06.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDetailPK {
    private String cartId;
    private String productItemId;
}
