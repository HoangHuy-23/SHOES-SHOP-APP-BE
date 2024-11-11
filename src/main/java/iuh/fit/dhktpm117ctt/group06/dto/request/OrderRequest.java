package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.entities.enums.OrderStatus;
import iuh.fit.dhktpm117ctt.group06.entities.enums.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderRequest {
    private double totalPrice;
    private LocalDateTime createdDate;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private List<OrderDetailRequest> orderDetails;
    private String userId;
}
