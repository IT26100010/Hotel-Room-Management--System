package com.hotel.service;

import com.hotel.model.Payment;
import com.hotel.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) { this.repository = repository; }

    public Payment create(Payment p) {
        if (repository.findById(p.getPaymentId()) != null) throw new IllegalArgumentException("Payment ID already exists");
        if (!p.processPayment()) throw new IllegalArgumentException("Payment processing failed");
        p.setPaymentStatus("PAID");
        repository.save(p);
        return p;
    }

    public List<Payment> getAll() { return repository.findAll(); }

    public Payment getById(String id) {
        Payment p = repository.findById(id);
        if (p == null) throw new IllegalArgumentException("Payment not found");
        return p;
    }

    public Payment update(String id, Payment p) {
        p.setPaymentId(id);
        repository.update(p);
        return p;
    }

    public void delete(String id) { repository.delete(id); }
}
