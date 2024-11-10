package iuh.fit.dhktpm117ctt.group06.repository;

import iuh.fit.dhktpm117ctt.group06.entities.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {
    List<BookingDetail> findByCheckInDate(Date checkInDate);

    List<BookingDetail> findByBookingId(String bookingId);

    List<BookingDetail> findByRoomItemId(String roomItemId);
}
