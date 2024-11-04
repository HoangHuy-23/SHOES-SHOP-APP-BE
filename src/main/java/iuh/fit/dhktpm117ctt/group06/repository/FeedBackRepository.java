package iuh.fit.dhktpm117ctt.group06.repository;



import iuh.fit.dhktpm117ctt.group06.entities.FeedBack;

import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;



public interface FeedBackRepository extends JpaRepository<FeedBack, String> {

    List<FeedBack> findByUserId(String userId);

    List<FeedBack> findByRoomId(String roomId);

    List<FeedBack> findByRating(float rating);

    List<FeedBack> findByBookingId(String bookingId);

    void deleteByBookingId(String bookingId);
}