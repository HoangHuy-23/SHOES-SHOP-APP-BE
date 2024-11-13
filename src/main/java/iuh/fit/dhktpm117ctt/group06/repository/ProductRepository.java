package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Product;
import iuh.fit.dhktpm117ctt.group06.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Tìm kiếm  theo tên
    List<Product> findByDescription(String name);
    @Query("SELECT p FROM Product p WHERE p.brand.id = ?1")
    List<Product> findByCategory(String category);
}
