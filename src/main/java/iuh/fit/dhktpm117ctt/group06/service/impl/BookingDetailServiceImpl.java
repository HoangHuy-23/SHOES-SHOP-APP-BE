package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.BookingDetail;
import iuh.fit.dhktpm117ctt.group06.repository.BookingDetailRepository;
import iuh.fit.dhktpm117ctt.group06.services.BookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Override
    public Optional<BookingDetail> findById(String id) {
        return bookingDetailRepository.findById(id);
    }

    @Override
    public List<BookingDetail> findByCheckInDate(Date checkInDate) {
        return bookingDetailRepository.findByCheckInDate(checkInDate);
    }

    @Override
    public List<BookingDetail> findByBookingId(String bookingId) {
        return bookingDetailRepository.findByBookingId(bookingId);
    }

    @Override
    public List<BookingDetail> findByRoomItemId(String roomItemId) {
        return bookingDetailRepository.findByRoomItemId(roomItemId);
    }

    @Override
    public BookingDetail save(BookingDetail bookingDetail) {
        return bookingDetailRepository.save(bookingDetail);
    }

    @Override
    public void deleteById(String id) {
        bookingDetailRepository.deleteById(id);
    }
}
