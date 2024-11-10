package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.Booking;
import iuh.fit.dhktpm117ctt.group06.entities.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);

    List<Booking> findByBookingDate(Date bookingDate);

    List<Booking> findByUserId(String userId);
}
