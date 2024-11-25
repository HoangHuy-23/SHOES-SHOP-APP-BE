package iuh.fit.dhktpm117ctt.group06.controller;

import java.util.*;

import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iuh.fit.dhktpm117ctt.group06.dto.request.ProductItemRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductItemResponse;
import iuh.fit.dhktpm117ctt.group06.exception.ErrorCode;
import iuh.fit.dhktpm117ctt.group06.service.ProductItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product-items")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    @GetMapping("getListProductItems/{productId}")
    public ResponseEntity<?> getAllProductItems(@PathVariable String productId) {
        Map<String, Object> response = new LinkedHashMap();
        response.put("status", HttpStatus.OK.value());
        response.put("data", productItemService.findByProduct(productId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductItemById(@PathVariable String id, HttpSession httpSession) {
        Map<String, Object> response = new LinkedHashMap<>();

        Optional<ProductItem> productItemOptional = productItemService.findById(id);

        if (productItemOptional.isEmpty()) {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "Product item not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        saveProductToSession(httpSession, productItemOptional.get());

        response.put("status", HttpStatus.OK.value());
        response.put("data", productItemOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNewProductItem(@Valid @ModelAttribute ProductItemRequest productItemRequest,
                                               BindingResult bindingResult) {
        Map<String, Object> response = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("errors", errors);
        }

        Optional<ProductItemResponse> productItemResponse = productItemService.save(productItemRequest);

        if (productItemResponse.isEmpty()) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("data", ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("status", HttpStatus.OK.value());
        response.put("data", productItemService.save(productItemRequest));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProductItem(@PathVariable String id,
                                               @Valid @ModelAttribute ProductItemRequest productItemRequest, BindingResult bindingResult) {
        Map<String, Object> response = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("errors", errors);
        }

        Optional<ProductItemResponse> productItemResponse = productItemService.update(id, productItemRequest);

        if (productItemResponse.isEmpty()) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("data", ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("status", HttpStatus.OK.value());
        response.put("data", productItemService.update(id, productItemRequest));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductItem(@PathVariable String id) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            productItemService.findById(id);
        } catch (Exception e) {
            // TODO: handle exception
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("data", ErrorCode.PRODUCT_ITEM_EXISTED_IN_ORDER_DETAILS.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.put("status", HttpStatus.OK.value());
        response.put("data", "Product item deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentProducts(HttpSession httpSession) {
        Map<String, Object> response = new LinkedHashMap<>();
        List<ProductItem> products = (List<ProductItem>) httpSession.getAttribute("recentProducts");
        if (products == null) {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "No recent products");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("status", HttpStatus.OK.value());
        response.put("data", products);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    private void saveProductToSession(HttpSession httpSession, ProductItem product) {
        List<ProductItem> products = (List<ProductItem>) httpSession.getAttribute("recentProducts");
        if (products == null) {
            products = new ArrayList<>();
            products.add(product);
        } else {

            for (ProductItem p : products) {
                if (p.getId().equals(product.getId())) {
                    return;
                }
            }
            products.add(product);
        }
        httpSession.setAttribute("recentProducts", products);

    }
}
