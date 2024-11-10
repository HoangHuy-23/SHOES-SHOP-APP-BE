package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import iuh.fit.dhktpm117ctt.group06.service.ProductItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductItemServiceImpl implements ProductItemService {

    private List<ProductItem> productItems = new ArrayList<>();

    @Override
    public ProductItem add(ProductItem productItem) {
        productItems.add(productItem);
        return productItem;
    }

    @Override
    public ProductItem update(ProductItem productItem) {
        Optional<ProductItem> existingItem = productItems.stream()
                .filter(item -> item.getId().equals(productItem.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            productItems.remove(existingItem.get());
            productItems.add(productItem);
            return productItem;
        }
        return null;
    }

    @Override
    public void remove(String productItemId) {
        productItems.removeIf(item -> item.getId().equals(productItemId));
    }

    @Override
    public ProductItem updateDetailImage(String productItemId, List<String> newDetailImages) {
        for (ProductItem item : productItems) {
            if (item.getId().equals(productItemId)) {
                item.setListDetailImages(newDetailImages);
                return item;
            }
        }
        return null;
    }

    @Override
    public ProductItem updateQuantity(String productItemId, int qty) {
        for (ProductItem item : productItems) {
            if (item.getId().equals(productItemId)) {
                item.setQuantity(qty);
                return item;
            }
        }
        return null;
    }

    @Override
    public ProductItem decreaseQuantity(String productItemId, int qty) {
        for (ProductItem item : productItems) {
            if (item.getId().equals(productItemId)) {
                item.setQuantity(item.getQuantity() - qty);
                return item;
            }
        }
        return null; // Hoặc ném ra ngoại lệ nếu không tìm thấy
    }

    @Override
    public List<ProductItem> findByProduct(String productId) {
        List<ProductItem> result = new ArrayList<>();
        for (ProductItem item : productItems) {
            if (item.getProduct().equals(productId)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public List<String> findListColor() {
        // Giả sử bạn có một phương thức để lấy màu từ ProductItem
        return productItems.stream()
                .map(ProductItem::getColor)
                .distinct()
                .toList();
    }

    @Override
    public List<String> findListSize() {
        // Giả sử bạn có một phương thức để lấy kích thước từ ProductItem
        return productItems.stream()
                .map(ProductItem::getSize)
                .distinct()
                .toList();
    }

    @Override
    public ProductItem findByColorAndSize(String color, String size, String productId) {
        for (ProductItem item : productItems) {
            if (item.getColor().equals(color) && item.getSize().equals(size) && item.getProduct().equals(productId)) {
                return item;
            }
        }
        return null;
    }
}
