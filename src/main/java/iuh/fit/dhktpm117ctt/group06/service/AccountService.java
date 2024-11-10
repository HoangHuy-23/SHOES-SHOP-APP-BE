package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.entities.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);
}
