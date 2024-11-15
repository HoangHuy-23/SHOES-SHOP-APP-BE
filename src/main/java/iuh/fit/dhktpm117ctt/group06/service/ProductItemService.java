package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.dto.request.ProductItemRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.ProductItemResponse;
import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import iuh.fit.dhktpm117ctt.group06.entities.enums.ProductColor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductItemService {
    Optional<ProductItemResponse> save(ProductItemRequest productItemRequest);
    Optional<ProductItemResponse> update(String id, ProductItemRequest productItem);
    void deleteById(String productItemId);
    Optional<ProductItemResponse> updateDetailImage(String productItemId, MultipartFile[] newDetailImages);
    Optional<ProductItemResponse> updateQuantity(String productItemId, int qty);
    Optional<ProductItemResponse> decreaseQuantity(String productItemId, int qty);
    List<ProductItemResponse> findByProduct(String productId);
    Optional<ProductItemResponse> findByColorAndSize(String color, String size, String productId);
    List<ProductColor> findDistinctColorsByProductId(String productId);
    List<String> findDistinctSizesByProductId(String productId);
	Optional<ProductItem> findById(String id);
	List<ProductItem> findAll();
}
