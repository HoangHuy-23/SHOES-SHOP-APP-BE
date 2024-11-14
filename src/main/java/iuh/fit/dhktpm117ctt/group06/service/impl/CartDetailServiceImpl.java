package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.dto.request.CartDetailRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.CartDetailResponse;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;
import iuh.fit.dhktpm117ctt.group06.repository.CartDetailRepository;
import iuh.fit.dhktpm117ctt.group06.service.CartDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;
    private ModelMapper modelMapper = new ModelMapper();

    private CartDetail mapToCartDetail(CartDetailRequest cartDetailRequest) {
        return modelMapper.map(cartDetailRequest, CartDetail.class);
    }

    private CartDetailResponse mapToCartDetailResponse(CartDetail cartDetail) {
        return modelMapper.map(cartDetail, CartDetailResponse.class);
    }

    @Override
    public List<CartDetailResponse> findAllCartDetailByCart(String cartId) {
        List<CartDetail> cartDetails = cartDetailRepository.findAllByCartId(cartId);
        return cartDetails.stream().map(this::mapToCartDetailResponse).toList();
    }

    @Override
    public Optional<CartDetailResponse> addToCart(CartDetailRequest cartDetailRequest) {
        CartDetail cartDetail = mapToCartDetail(cartDetailRequest);
        return Optional.of(mapToCartDetailResponse(cartDetailRepository.save(cartDetail)));
    }

    @Override
    public Optional<CartDetailResponse> updateQuantity(CartDetailPK cartDetailPK, int newQuantity) {
        Optional<CartDetail> cartDetail = cartDetailRepository.findById(cartDetailPK);
        if (cartDetail.isPresent()) {
            cartDetail.get().setQuantity(newQuantity);
            return Optional.of(mapToCartDetailResponse(cartDetailRepository.save(cartDetail.get())));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(CartDetailPK cartDetailPK) {
        cartDetailRepository.deleteByCartDetailPK(cartDetailPK);
    }
}
