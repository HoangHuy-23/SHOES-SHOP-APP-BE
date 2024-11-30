package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    List<Category> search(String keyword);

    @Query("SELECT CASE WHEN COUNT(p) = 0 THEN true ELSE false END FROM Category c LEFT JOIN c.products p WHERE c.id = ?1")
    Boolean checkCategoryNotProduct(String id);
}
