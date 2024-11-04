package iuh.fit.dhktpm117ctt.group06.repository;
import iuh.fit.dhktpm117ctt.group06.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PaymentRepository extends JpaRepository<Payment, String> {
    // Tìm tất cả khoản thanh toán dựa trên bookingId

    List<Payment> findByBookingId(String bookingId);

    // Tìm tất cả khoản thanh toán dựa trên userId

    List<Payment> findByUserId(String userId);

}