package iuh.fit.dhktpm117ctt.group06.dto.request;

import iuh.fit.dhktpm117ctt.group06.entities.enums.OrderStatus;
import iuh.fit.dhktpm117ctt.group06.entities.enums.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
public class OrderRequest {
    private double totalPrice;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    
    private LocalDateTime createdDate;
    @Nullable
    private OrderStatus orderStatus;
    @NotNull(message = "Payment method must not be null")
    private PaymentMethod paymentMethod;
    @NotNull(message = "Order details must not be null")
    private List<OrderDetailRequest> orderDetails;
    @NotEmpty(message = "User id must not be null")
    private String userId;
    
}
