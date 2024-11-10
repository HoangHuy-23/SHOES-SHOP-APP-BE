package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> findById(String id);
    Optional<Cart> findByUserId(String userId);
    Cart save(Cart cart);
    void deleteById(String id);
}
