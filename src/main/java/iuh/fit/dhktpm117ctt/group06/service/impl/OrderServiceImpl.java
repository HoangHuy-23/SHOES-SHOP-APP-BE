package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.dto.request.OrderDetailRequest;
import iuh.fit.dhktpm117ctt.group06.dto.request.OrderRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderDetailResponse;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Account;
import iuh.fit.dhktpm117ctt.group06.entities.Address;
import iuh.fit.dhktpm117ctt.group06.entities.Order;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.entities.enums.OrderStatus;
import iuh.fit.dhktpm117ctt.group06.exception.AppException;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.repository.AccountRepository;
import iuh.fit.dhktpm117ctt.group06.repository.AddressRepository;
import iuh.fit.dhktpm117ctt.group06.repository.OrderRepository;
import iuh.fit.dhktpm117ctt.group06.repository.UserRepository;
import iuh.fit.dhktpm117ctt.group06.service.MailSenderService;
import iuh.fit.dhktpm117ctt.group06.service.OrderDetailService;
import iuh.fit.dhktpm117ctt.group06.service.OrderService;
import iuh.fit.dhktpm117ctt.group06.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	MailSenderService mailSenderService;

	@Autowired
	AccountRepository accountRepository;

	private final ModelMapper modelMapper = new ModelMapper();

//	@Autowired
//	public OrderServiceImpl(OrderRepository orderRepository) {
//		this.orderRepository = orderRepository;
//	}

	private OrderResponse mapToOrderResponse(Order order) {
		return modelMapper.map(order, OrderResponse.class);
	}

	private Order mapToOrder(OrderRequest orderRequest) {
		return modelMapper.map(orderRequest, Order.class);
	}

	@Override
	public Optional<OrderResponse> findById(String id) {
		return orderRepository.findById(id).map(this::mapToOrderResponse);
	}

	@Override
	public List<OrderResponse> findByUser(String userId) {
		return orderRepository.findByUserId(userId).stream().map(this::mapToOrderResponse).collect(Collectors.toList());
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

	@Override
	public Optional<OrderResponse> updateQuantity(String id, OrderDetailRequest orderDetailRequest) {
		Optional<Order> optionalOrder = orderRepository.findById(id);
		if (optionalOrder.isEmpty()) {
			return Optional.empty();
		}

		Order order = optionalOrder.get();

		try {
			orderDetailService.updateQuantity(orderDetailRequest.getId(), orderDetailRequest);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Optional.empty();
		}

		List<OrderDetailResponse> orderResponses = orderDetailService.findByOrder(id);
		double totalPrice = 0;
		for (OrderDetailResponse orderDetailResponse : orderResponses) {
			totalPrice += orderDetailResponse.getPricePerItem() * orderDetailResponse.getQuantity();
		}
		order.setTotalPrice(totalPrice);

		Order updatedOrder = orderRepository.save(order);
		return Optional.of(mapToOrderResponse(updatedOrder));
	}

	@Override
	public Optional<OrderResponse> saveOrder(OrderRequest orderRequest) {
		Optional<User> user = userRepository.findById(orderRequest.getUserId());
		Optional<Address> addresOptional = addressRepository.findById(orderRequest.getAddressId());

		if (user.isEmpty()) {
			throw new AppException(ErrorCode.USER_NOT_FOUND);
		}
		
		if (addresOptional.isEmpty()) {
			throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
		}

		User userEntity = user.get();

		Order order = new Order();

		BeanUtils.copyProperties(orderRequest, order);

		order.setUser(user.get());
		order.setAddress(addresOptional.get());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setPaymentMethod(orderRequest.getPaymentMethod());
		order.setCreatedDate(LocalDateTime.now());
		order.setTotalPrice(calculateTotalPrice(orderRequest.getOrderDetails()));

		Order savedOrder = orderRepository.save(order);



		List<OrderDetailRequest> detailRequests = orderRequest.getOrderDetails();
		
		List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();

		for (OrderDetailRequest orderDetailRequest : detailRequests) {
			orderDetailResponses.add(orderDetailService.addToOrder(savedOrder.getId(), orderDetailRequest).get());
		}
		
		Optional<OrderResponse> orderResponseOptional = Optional.of(mapToOrderResponse(savedOrder));

		if(savedOrder.getId() != null){
			Optional<Account> account = accountRepository.findByUser(userEntity.getId());

            account.ifPresent(value -> mailSenderService.sendMail(value.getEmail(), "Order Confirmation", "Your order has been placed successfully"));
		}

		return orderResponseOptional;
	}

	private double calculateTotalPrice(List<OrderDetailRequest> orderDetails) {
		double totalPrice = 0;
		for (OrderDetailRequest orderDetail : orderDetails) {
			totalPrice += orderDetail.getPricePerItem() * orderDetail.getQuantity();
		}
		return totalPrice;
	}
}
