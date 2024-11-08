package iuh.fit.dhktpm117ctt.group06.service;

import java.util.List;
import java.util.Optional;

public interface HotelServices {

    List<Hotel> findAllHotels();

    Optional<Hotel> findHotelById(String id);

    List<Hotel> findHotelsByNameContaining(String name);

    Hotel saveHotel(Hotel hotel);

    void deleteHotelById(String id);

}