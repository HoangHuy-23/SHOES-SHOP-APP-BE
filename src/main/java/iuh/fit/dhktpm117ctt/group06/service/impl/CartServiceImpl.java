package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Cart;
import iuh.fit.dhktpm117ctt.group06.entities.ProductItem;
import iuh.fit.dhktpm117ctt.group06.repository.CartRepository;
import iuh.fit.dhktpm117ctt.group06.repository.ProductItemRepository;
import iuh.fit.dhktpm117ctt.group06.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Override
    public Cart findCartByUser(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart saveSessionItemsToDatabase(String sessionId, String userId) {
        // Giả định có phương thức lấy danh sách ProductItem từ session
        List<ProductItem> sessionItems = getSessionItems(sessionId);

        return null;
    }

    // Phương thức giả định để lấy các ProductItem từ session
    private List<ProductItem> getSessionItems(String sessionId) {
        // Logic lấy ProductItem từ session (thay thế bằng cách xử lý thực tế)
        return List.of(); // Trả về danh sách item từ session
    }
}
