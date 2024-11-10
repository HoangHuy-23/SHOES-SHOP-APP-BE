package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, String> {
    List<CartDetail> findByCartId(String cartId);

}
