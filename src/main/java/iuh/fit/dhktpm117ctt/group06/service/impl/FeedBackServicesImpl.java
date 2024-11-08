//package iuh.fit.dhktpm117ctt.group06.service.impl;
//
//import iuh.fit.dhktpm117ctt.group06.entities.FeedBack;
//import iuh.fit.dhktpm117ctt.group06.repository.FeedBackRepository; // Giả sử bạn đã có repository này
//import iuh.fit.dhktpm117ctt.group06.service.FeedBackServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class FeedBackServicesImpl implements FeedBackServices {
//
//    private final FeedBackRepository feedBackRepository;
//
//    @Autowired
//    public FeedBackServicesImpl(FeedBackRepository feedBackRepository) {
//        this.feedBackRepository = feedBackRepository;
//    }
//
//    @Override
//    public List<FeedBack> findByUserId(String userId) {
//        return feedBackRepository.findByUserId(userId);
//    }
//
//    @Override
//    public List<FeedBack> findByRoomId(String roomId) {
//        return feedBackRepository.findByRoomId(roomId);
//    }
//
//    @Override
//    public List<FeedBack> findByRating(float rating) {
//        return feedBackRepository.findByRating(rating);
//    }
//
//    @Override
//    public List<FeedBack> findByBookingId(String bookingId) {
//        return feedBackRepository.findByBookingId(bookingId);
//    }
//
//    @Override
//    public void deleteByBookingId(String bookingId) {
//        feedBackRepository.deleteByBookingId(bookingId);
//    }
//}
