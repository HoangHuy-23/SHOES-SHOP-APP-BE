package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetailPK;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailPKRepository extends JpaRepository<CartDetailPK, String> {

    // Tìm tất cả chi tiết giỏ hàng dựa trên cartId

    Optional<CartDetailPK> findByCartId(String cartId);

    // Tìm tất cả chi tiết giỏ hàng dựa trên roomItemId
    Optional<CartDetailPK> findByRoomItemId(String roomItemId);

    void deleteByCartId(String cartId);
}