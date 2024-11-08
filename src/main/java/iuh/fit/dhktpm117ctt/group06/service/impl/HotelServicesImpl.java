package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.repository.HotelRepository; // Giả sử bạn đã có repository này
import iuh.fit.dhktpm117ctt.group06.service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServicesImpl implements HotelServices {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServicesImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> findHotelById(String id) {
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> findHotelsByNameContaining(String name) {
        return hotelRepository.findByNameContaining(name);
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotelById(String id) {
        hotelRepository.deleteById(id);
    }
}
