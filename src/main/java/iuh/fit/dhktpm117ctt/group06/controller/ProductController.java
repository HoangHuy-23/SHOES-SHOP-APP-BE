package iuh.fit.dhktpm117ctt.group06.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.dhktpm117ctt.group06.dto.request.ProductRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductResponse;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.service.CategoryService;
import iuh.fit.dhktpm117ctt.group06.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable String id) {
		Map<String, Object> response = new LinkedHashMap<>();
		ProductResponse product = productService.getProductById(id);

		if (product == null) {
			response.put("status", ErrorCode.PRODUCT_NOT_FOUND.getCode());
			response.put("data", ErrorCode.PRODUCT_NOT_FOUND.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		return ResponseEntity.ok(product);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> saveProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});

			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("errors", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Optional<ProductResponse> productOpt = productService.addProduct(productRequest);

		if (!productOpt.isPresent()) {
			response.put("status", ErrorCode.PRODUCT_INVALID.getCode());
			response.put("data", ErrorCode.PRODUCT_INVALID.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.put("status", HttpStatus.OK.value());
		response.put("data", productOpt.get());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest,
			@RequestPart("avatar") MultipartFile avatar, BindingResult bindingResult) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new LinkedHashMap<>();
			bindingResult.getFieldErrors().stream().forEach(result -> {
				errors.put(result.getField(), result.getDefaultMessage());
			});

			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("errors", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		Optional<ProductResponse> productOpt = productService.updateProduct(id, productRequest);

		if (productOpt.isEmpty()) {
			response.put("status", ErrorCode.PRODUCT_NOT_FOUND.getCode());
			response.put("data", ErrorCode.PRODUCT_NOT_FOUND.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		response.put("status", HttpStatus.OK.value());
		response.put("data", productOpt.get());

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
