package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HotelServices {

    List<Hotel> findAllHotels();

    Optional<Hotel> findHotelById(String id);

    List<Hotel> findHotelsByNameContaining(String name);

    Hotel saveHotel(Hotel hotel);

    void deleteHotelById(String id);

}