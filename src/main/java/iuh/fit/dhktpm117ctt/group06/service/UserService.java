package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(String id);


    Optional<User> findByPhone(String phone);

    List<User> findByLastName(String lastName);

    //add save
    User save(User user);

    void deleteById(String id);

    User update(User user);
}
