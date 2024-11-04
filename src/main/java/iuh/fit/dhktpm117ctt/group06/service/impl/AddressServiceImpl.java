package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Address;
import iuh.fit.dhktpm117ctt.group06.repository.AddressRepository;
import iuh.fit.dhktpm117ctt.group06.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Optional<Address> findByUserId(Long userId) {
        // Thay đổi phương thức này để tìm kiếm địa chỉ theo userId
        return addressRepository.findById(userId); // Bạn cần đảm bảo repository có phương thức này
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }




}
