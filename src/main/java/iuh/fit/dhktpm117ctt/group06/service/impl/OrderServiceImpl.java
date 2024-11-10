package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.dto.request.OrderRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Order;
import iuh.fit.dhktpm117ctt.group06.repository.OrderRepository;
import iuh.fit.dhktpm117ctt.group06.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service  // Thêm annotation này để Spring nhận diện lớp này là một Bean
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

    private Order mapToOrder(OrderRequest orderRequest) {
        return modelMapper.map(orderRequest, Order.class);
    }

    @Override
    public Optional<OrderResponse> findById(String id) {
        return orderRepository.findById(id)
                .map(this::mapToOrderResponse);
    }

    @Override
    public List<OrderResponse> findByUser(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponse> updateStatus(String id, OrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }

        Order order = optionalOrder.get();
        order.setOrderStatus(orderRequest.getOrderStatus());

        Order updatedOrder = orderRepository.save(order);
        return Optional.of(mapToOrderResponse(updatedOrder));
    }
}
