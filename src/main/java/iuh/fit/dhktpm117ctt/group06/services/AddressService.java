package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Optional<Address> findByUserId(Long userId);

    List<Address> findAll();
    Address save(Address address);
}
