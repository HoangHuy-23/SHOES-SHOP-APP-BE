package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;
import iuh.fit.dhktpm117ctt.group06.repository.CartDetailRepository;
import iuh.fit.dhktpm117ctt.group06.services.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public Optional<CartDetail> findById(String id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public List<CartDetail> findByCartId(String cartId) {
        return cartDetailRepository.findByCartId(cartId);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void deleteById(String id) {
        cartDetailRepository.deleteById(id);
    }
}
