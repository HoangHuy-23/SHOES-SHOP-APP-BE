package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Cart;
import iuh.fit.dhktpm117ctt.group06.repository.CartRepository;
import iuh.fit.dhktpm117ctt.group06.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Optional<Cart> findById(String id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> findByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(String id) {
        cartRepository.deleteById(id);
    }
}
