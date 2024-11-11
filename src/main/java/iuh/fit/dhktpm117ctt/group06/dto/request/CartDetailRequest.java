package iuh.fit.dhktpm117ctt.group06.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDetailRequest {
    private String cartId;
    private String productId;
    private int quantity;
}
