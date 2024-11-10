package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;
import iuh.fit.dhktpm117ctt.group06.repository.CarDetailRepository;
import iuh.fit.dhktpm117ctt.group06.service.CarDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CarDetailService {

    @Autowired
    private CarDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> findAllCarDetailByCart(String cartId) {
        return cartDetailRepository.findAllByCartId(cartId);
    }

    @Override
    public CartDetail addToCart(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public Optional<CartDetail> updateQuantity(CartDetailPK cartDetailPK, int newQuantity) {
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findByCartDetailPK(cartDetailPK);
        if (optionalCartDetail.isPresent()) {
            CartDetail cartDetail = optionalCartDetail.get();
            cartDetail.setQuantity(newQuantity);
            return Optional.of(cartDetailRepository.save(cartDetail));
        }
        return Optional.empty();
    }

    @Override
    public void removeById(CartDetailPK cartDetailPK) {
        cartDetailRepository.deleteByCartDetailPK(cartDetailPK);
    }
}
