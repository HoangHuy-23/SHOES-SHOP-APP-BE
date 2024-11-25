package iuh.fit.dhktpm117ctt.group06.dto.request;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderDetailRequest {
	@Nullable
	private String id;
    private String productItemId;
    private int quantity;
    private double pricePerItem;
    private String orderId;
}
