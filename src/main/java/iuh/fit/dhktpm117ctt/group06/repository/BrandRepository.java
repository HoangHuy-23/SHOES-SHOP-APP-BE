package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,String> {
    @Query("SELECT b FROM Brand b WHERE b.brandName LIKE %?1%")
    List<Brand> search(String keyword);

    @Query("SELECT CASE WHEN COUNT(p) = 0 THEN true ELSE false END FROM Brand b LEFT JOIN b.productCollections p WHERE b.id = ?1")
    Boolean checkBrandNotProduct(String id);
}
