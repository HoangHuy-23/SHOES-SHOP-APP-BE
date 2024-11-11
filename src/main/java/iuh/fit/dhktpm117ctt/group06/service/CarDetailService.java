package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;

import java.util.List;
import java.util.Optional;

public interface CarDetailService {

    // Tìm tất cả CartDetail theo cartId
    List<CartDetail> findAllCarDetailByCart(String cartId);

    // Thêm một CartDetail
    CartDetail addToCart(CartDetail cartDetail);

    // Cập nhật số lượng của CartDetail
    Optional<CartDetail> updateQuantity(CartDetailPK cartDetailPK, int newQuantity);

    // Xóa CartDetail theo CartDetailPK
    void removeById(CartDetailPK cartDetailPK);
}
