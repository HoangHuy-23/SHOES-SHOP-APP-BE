package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Booking;
import iuh.fit.dhktpm117ctt.group06.entities.enums.BookingStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Optional<Booking> findById(String id);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByDate(Date date);
    List<Booking> findByUserId(String userId);
    Booking save(Booking booking);
    void deleteById(String id);
}
