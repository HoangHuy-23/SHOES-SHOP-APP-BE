package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;

import java.util.List;

public interface ProductItemService {
    // Thêm một ProductItem mới
    ProductItem add(ProductItem productItem);

    // Cập nhật một ProductItem
    ProductItem update(ProductItem productItem);

    // Xóa một ProductItem
    void remove(String productItemId);

    // Cập nhật hình ảnh chi tiết
    ProductItem updateDetailImage(String productItemId, List<String> newDetailImages);

    // Cập nhật số lượng
    ProductItem updateQuantity(String productItemId, int qty);

    // Giảm số lượng
    ProductItem decreaseQuantity(String productItemId, int qty);

    // Tìm ProductItem theo productId
    List<ProductItem> findByProduct(String productId);

    // Lấy danh sách màu
    List<String> findListColor();

    // Lấy danh sách kích thước
    List<String> findListSize();

    // Tìm ProductItem theo màu và kích thước
    ProductItem findByColorAndSize(String color, String size, String productId);
}
