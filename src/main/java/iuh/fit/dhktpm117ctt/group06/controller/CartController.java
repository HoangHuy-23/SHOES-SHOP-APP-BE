package iuh.fit.dhktpm117ctt.group06.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iuh.fit.dhktpm117ctt.group06.dto.request.CartDetailRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.CartDetailResponse;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductItemResponse;
import iuh.fit.dhktpm117ctt.group06.dto.response.UserResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Cart;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;
import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import iuh.fit.dhktpm117ctt.group06.service.CartDetailService;
import iuh.fit.dhktpm117ctt.group06.service.CartService;
import iuh.fit.dhktpm117ctt.group06.service.ProductItemService;
import iuh.fit.dhktpm117ctt.group06.service.UserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@Autowired
	private CartDetailService cartDetailService;

	@Autowired
	private ProductItemService productItemService;

	@Autowired
	private UserService userService;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(HttpSession httpSession, @RequestBody CartDetailRequest cartDetail) {
		Map<String, Object> response = new LinkedHashMap<>();

		List<CartDetail> cartDetails = getCartFromSession(httpSession);

		try {
			handleCartDetail(cartDetails, cartDetail);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("data", "Error in add to cart!");
			return ResponseEntity.badRequest().body(response);
		}
		httpSession.setAttribute("cart", cartDetails);

		syncCartWithDatabase(cartDetails);

		response.put("status", HttpStatus.OK.value());
		response.put("data", "Add to cart successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/updateQuantity/{productId}")
	public ResponseEntity<?> updateQuantity(HttpSession session, @PathVariable String productId,
			@RequestBody CartDetailRequest cartDetailRequest) {
		Map<String, Object> response = new LinkedHashMap();
		List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("cart");

		if (cartDetails == null) {
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("data", "Cart is empty");
			return ResponseEntity.badRequest().body(response);
		}

		CartDetail cartDetailEntity = null;

		for (CartDetail cartDetail : cartDetails) {
			if (cartDetail.getProductItem().getId().equals(productId)) {
				cartDetailEntity = cartDetail;
				cartDetail.setQuantity(cartDetailRequest.getQuantity());
				session.setAttribute("cart", cartDetails);

				response.put("status", HttpStatus.OK.value());
				response.put("data", "Update quantity successfully");
				return ResponseEntity.ok(response);
			}
		}

		// check user login
		var context = SecurityContextHolder.getContext();
		String emailString = context.getAuthentication().getName();
		if (emailString != null) {
			Cart cart = cartService.findCartByUser(userService.findByEmail(emailString).get().getId());

			// update cart detail in database
			CartDetailPK cartDetailPK = new CartDetailPK(cart.getId(), productId);
			if (cartDetailService.findById(cartDetailPK).isPresent()) {
				cartDetailService.updateQuantity(cartDetailPK, cartDetailRequest.getQuantity());
			}
		}

		response.put("status", HttpStatus.OK.value());
		response.put("data", "Update quantity successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/delete/{productId}")
	public ResponseEntity<?> deleteCartDetail(HttpSession session, @PathVariable String productId) {
		Map<String, Object> response = new LinkedHashMap();
		List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("cart");

		if (cartDetails == null) {
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("data", "Cart is empty");
			return ResponseEntity.badRequest().body(response);
		}

		for (CartDetail cartDetail : cartDetails) {
			if (cartDetail.getProductItem().getId().equals(productId)) {
				cartDetails.remove(cartDetail);
				session.setAttribute("cart", cartDetails);

				response.put("status", HttpStatus.OK.value());
				response.put("data", "Delete cart detail successfully");
				return ResponseEntity.ok(response);
			}
		}

		// check user login
		var context = SecurityContextHolder.getContext();
		String emailString = context.getAuthentication().getName();
		if (emailString != null) {
			Cart cart = cartService.findCartByUser(userService.findByEmail(emailString).get().getId());

			// delete cart detail in database
			CartDetailPK cartDetailPK = new CartDetailPK(cart.getId(), productId);
			cartDetailService.deleteById(cartDetailPK);
		}

		response.put("status", HttpStatus.OK.value());
		response.put("data", "Delete cart detail successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<?> viewCart(HttpSession session) {
		Map<String, Object> response = new LinkedHashMap();
		List<CartDetail> cartDetails = (List<CartDetail>) session.getAttribute("cart");
		if (cartDetails == null) {
			response.put("status", HttpStatus.OK.value());
			response.put("data", "Cart is empty");
			return ResponseEntity.ok(response);
		}
		
		try {
			syncCartWithDatabase(cartDetails);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		response.put("status", HttpStatus.OK.value());
		response.put("data", cartDetails);
		return ResponseEntity.ok(response);
	}

	private void saveNewCartDetail(CartDetailRequest cartDetailRequest, Cart cart) {
		CartDetail cartDetail = new CartDetail();

		cartDetail.setProductItem(productItemService.findById(cartDetailRequest.getProductId()).get());
		cartDetail.setQuantity(cartDetailRequest.getQuantity());
		if (cart != null) {
			cartDetail.setCart(cart);
			cartDetailService.save(cartDetail);
		}
	}

	private List<CartDetail> getCartFromSession(HttpSession httpSession) {
		List<CartDetail> cartDetails = (List<CartDetail>) httpSession.getAttribute("cart");
		return cartDetails == null ? new ArrayList<>() : cartDetails;
	}

	private void handleCartDetail(List<CartDetail> cartDetails, CartDetailRequest cartDetailRequest)
			throws IllegalArgumentException {

		Optional<ProductItem> productItemResponseOpt = productItemService.findById(cartDetailRequest.getProductId());

		if (productItemResponseOpt.isEmpty()) {
			throw new IllegalArgumentException("Product not found");
		}

		ProductItem productItem = productItemResponseOpt.get();

		for (CartDetail detail : cartDetails) {
			if (detail.getProductItem().getId().equals(cartDetailRequest.getProductId())) {
				int currentQty = detail.getQuantity() + cartDetailRequest.getQuantity();
				if (currentQty > productItem.getQuantity()) {
					throw new IllegalArgumentException("Quantity must be less than current quantity");
				}

				detail.setQuantity(currentQty);
				return;
			}
		}

		CartDetail newCartDetail = new CartDetail();

		if (productItem.getQuantity() < cartDetailRequest.getQuantity()) {
			throw new IllegalArgumentException("Quantity must be less than current quantity");
		}

		newCartDetail.setQuantity(cartDetailRequest.getQuantity());
		newCartDetail.setProductItem(productItem);
		cartDetails.add(newCartDetail);
	}

	private void syncCartWithDatabase(List<CartDetail> cartDetails) throws IllegalArgumentException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (email == null)
			return;
		
		System.out.println("Email: " + email);

		Optional<UserResponse> userOpt = userService.findByEmail(email);
		if (userOpt.isEmpty())
			return;

		UserResponse user = userOpt.get();
		Cart cart = cartService.findCartByUser(user.getId());
		if (cart == null) {
			cart = new Cart();
			cart.setUser(userService.getUserById(user.getId())
					.orElseThrow(() -> new IllegalArgumentException("User not found")));
			cartService.save(cart);
		}

		for (CartDetail cartDetail : cartDetails) {
			if (cartDetail.getCart() == null) {
				CartDetailPK cartDetailPK = new CartDetailPK(cart.getId(), cartDetail.getProductItem().getId());
				cartDetail.setCartDetailPK(cartDetailPK);
				cartDetail.setCart(cart);
				cartDetailService.save(cartDetail);
			}
		}
	}

}