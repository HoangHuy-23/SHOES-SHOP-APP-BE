package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPhone(String phone);
    Boolean existsByPhone(String phone);
    @Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1% OR u.phone LIKE %?1%")
    List<User> search(String keyword);

    @Query("SELECT CASE WHEN COUNT(o) = 0 THEN true ELSE false END FROM User u LEFT JOIN u.orders o WHERE u.id = ?1")
    Boolean checkUserNotOrder(String id);

}