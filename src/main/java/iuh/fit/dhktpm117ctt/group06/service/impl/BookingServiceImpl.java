package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Booking;
import iuh.fit.dhktpm117ctt.group06.entities.enums.BookingStatus;
import iuh.fit.dhktpm117ctt.group06.repository.BookingRepository;
import iuh.fit.dhktpm117ctt.group06.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Optional<Booking> findById(String id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> findByStatus(BookingStatus status) {
        return bookingRepository.findByBookingStatus(status);
    }

    @Override
    public List<Booking> findByDate(Date date) {
        return bookingRepository.findByBookingDate(date);
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(String id) {
        bookingRepository.deleteById(id);
    }
}
