package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.dto.request.OrderRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<OrderResponse> findById(String id);
    List<OrderResponse> findByUser(String userId);
    Optional<OrderResponse> updateStatus(String id, OrderRequest orderRequest);
}
