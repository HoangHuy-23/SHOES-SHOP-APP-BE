package iuh.fit.dhktpm117ctt.group06.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetailRequest {
    private String productItemId;
    private int quantity;
    private double pricePerItem;
    private String orderId;
}
