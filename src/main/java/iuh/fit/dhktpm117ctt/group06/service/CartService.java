package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.Cart;

public interface CartService {
    // Tìm Cart của một người dùng dựa trên userId
    Cart findCartByUser(String userId);

    // Lưu các mục trong session vào database khi guest đăng ký
    Cart saveSessionItemsToDatabase(String sessionId, String userId);

}
