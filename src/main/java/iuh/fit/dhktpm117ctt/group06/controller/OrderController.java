package iuh.fit.dhktpm117ctt.group06.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iuh.fit.dhktpm117ctt.group06.dto.request.OrderDetailRequest;
import iuh.fit.dhktpm117ctt.group06.dto.request.OrderRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.OrderResponse;
import iuh.fit.dhktpm117ctt.group06.service.OrderDetailService;
import iuh.fit.dhktpm117ctt.group06.service.OrderService;
import iuh.fit.dhktpm117ctt.group06.service.ProductItemService;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductItemService productItemService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getAllOrders() {
		return null;
	}

	@PostMapping
	public ResponseEntity<?> saveOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult bindingResult) {
		Map<String, Object> response = new LinkedHashMap();

		if (bindingResult.hasErrors()) {
			Map<String, Object> errorsMap = new LinkedHashMap<>();

			bindingResult.getFieldErrors().forEach(fieldError -> {
				errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			});

			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("data", bindingResult.getAllErrors());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Optional<OrderResponse> orderResponse = orderService.saveOrder(orderRequest);

		if (orderResponse.isEmpty()) {
			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("data", "Cannot save order");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.put("status", HttpStatus.OK);
		response.put("data", orderResponse.get());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable String id) {
		Map<String, Object> response = new LinkedHashMap<>();

		Optional<OrderResponse> resOptional = orderService.findById(id);

		if (resOptional.isEmpty()) {
			response.put("status", HttpStatus.NOT_FOUND);
			response.put("data", "Order not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		response.put("status", HttpStatus.OK);
		response.put("data", resOptional.get());
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateOrder(@PathVariable String id, @Valid @RequestBody OrderDetailRequest orderRequest,
			BindingResult bindingResult) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (bindingResult.hasErrors()) {
			Map<String, Object> errorsMap = new LinkedHashMap<>();

			bindingResult.getFieldErrors().forEach(fieldError -> {
				errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			});

			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("data", bindingResult.getAllErrors());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Optional<OrderResponse> orderResponse = orderService.updateQuantity(id, orderRequest);

		if (orderResponse.isEmpty()) {
			response.put("status", HttpStatus.BAD_REQUEST);
			response.put("data", "Cannot update order");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.put("status", HttpStatus.OK);
		response.put("data", orderResponse.get());
		return ResponseEntity.ok(response);
	}
	

}