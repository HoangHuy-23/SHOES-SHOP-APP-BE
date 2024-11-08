package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.Address;
import iuh.fit.dhktpm117ctt.group06.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User updateUserAddress(String token, Address address);

    Optional<User> findById(String id);

    User getUserByToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByLastName(String lastName);

    //add save
    User save(User user);

    void deleteMyAccount(String token);

    void deleteById(String id);

    User update(User user);
}
