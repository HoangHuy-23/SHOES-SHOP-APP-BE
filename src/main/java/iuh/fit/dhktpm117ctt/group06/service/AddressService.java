package iuh.fit.dhktpm117ctt.group06.service;

import iuh.fit.dhktpm117ctt.group06.dto.request.AddressRequest;
import iuh.fit.dhktpm117ctt.group06.dto.response.AddressResponse;
import iuh.fit.dhktpm117ctt.group06.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<AddressResponse> findAllByUser(String userId);
    Optional<AddressResponse> findById(String id);
//    Address update(Address address);
    void deleteById(String id);
    Address addNewAddress(String userId, AddressRequest addressRequest);
}
