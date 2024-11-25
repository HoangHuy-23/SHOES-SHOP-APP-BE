package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDetailRequest {
	@Nullable
    private String cartId;
    private String productId;
    private int quantity;
}
