package iuh.fit.dhktpm117ctt.group06.services;

import iuh.fit.dhktpm117ctt.group06.entities.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> findAllPayments();

    Optional<Payment> findPaymentById(String id);

    List<Payment> findPaymentsByBookingId(String bookingId);

    Payment savePayment(Payment payment);

    void deletePaymentById(String id);

}