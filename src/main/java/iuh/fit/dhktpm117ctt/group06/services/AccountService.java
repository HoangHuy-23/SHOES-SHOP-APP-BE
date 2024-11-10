package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<Account> findByUserId(Long userId);
    void deleteByEmail(String email);
    Optional<Account> findById(String id);

}