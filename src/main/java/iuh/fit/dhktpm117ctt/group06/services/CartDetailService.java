package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    Optional<CartDetail> findById(String id);
    List<CartDetail> findByCartId(String cartId);
    CartDetail save(CartDetail cartDetail);
    void deleteById(String id);
}
