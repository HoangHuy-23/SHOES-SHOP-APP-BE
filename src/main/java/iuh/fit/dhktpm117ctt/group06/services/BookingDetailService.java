package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.BookingDetail;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingDetailService {
    Optional<BookingDetail> findById(String id);
    List<BookingDetail> findByCheckInDate(Date checkInDate);
    List<BookingDetail> findByBookingId(String bookingId);
    List<BookingDetail> findByRoomItemId(String roomItemId);
    BookingDetail save(BookingDetail bookingDetail);
    void deleteById(String id);
}
