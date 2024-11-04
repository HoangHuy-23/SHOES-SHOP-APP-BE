package iuh.fit.dhktpm117ctt.group06.repository;


import iuh.fit.dhktpm117ctt.group06.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//    Optional<User> findByAccount_Email(String email);
//    Boolean existsByAccount_Email(String email);
}
