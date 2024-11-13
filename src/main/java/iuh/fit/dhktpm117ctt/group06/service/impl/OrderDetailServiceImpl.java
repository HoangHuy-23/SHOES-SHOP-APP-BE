package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.dto.request.OrderDetailRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderDetailResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Order;
import iuh.fit.dhktpm117ctt.group06.entities.OrderDetail;
import iuh.fit.dhktpm117ctt.group06.repository.OrderDetailRepository;
import iuh.fit.dhktpm117ctt.group06.repository.OrderRepository;
import iuh.fit.dhktpm117ctt.group06.service.OrderDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;
    private ModelMapper modelMapper=new ModelMapper();

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    private OrderDetailResponse mapToOrderDetailResponse(OrderDetail orderDetail) {return modelMapper.map(orderDetail, OrderDetailResponse.class);}
    private OrderDetail mapToOrderDetail(OrderDetailRequest orderDetailRequest) {return modelMapper.map(orderDetailRequest, OrderDetail.class);}

    @Override
    public List<OrderDetailResponse> findByOrder(String orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        return orderDetails.stream()
                .map(this::mapToOrderDetailResponse)
                .toList();
    }

    @Override
    public Optional<OrderDetailResponse> addToOrder(String orderId, OrderDetailRequest orderDetailRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        Order order = optionalOrder.get();

        OrderDetail orderDetail = mapToOrderDetail(orderDetailRequest);
        orderDetail.setOrder(order);

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        return Optional.of(mapToOrderDetailResponse(savedOrderDetail));
    }
}
