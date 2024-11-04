package iuh.fit.dhktpm117ctt.group06.service.impl;

import iuh.fit.dhktpm117ctt.group06.entities.Payment;
import iuh.fit.dhktpm117ctt.group06.repository.PaymentRepository; // Giả sử bạn đã có repository này
import iuh.fit.dhktpm117ctt.group06.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findPaymentById(String id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> findPaymentsByBookingId(String bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePaymentById(String id) {
        paymentRepository.deleteById(id);
    }
}
