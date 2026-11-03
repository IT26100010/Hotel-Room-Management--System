package com.hotel.repository;

import com.hotel.model.*;
import com.hotel.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private static final String FILE = "data/payments.txt";

    public void save(Payment p) { FileUtil.append(FILE, toLine(p)); }

    public List<Payment> findAll() {
        List<Payment> result = new ArrayList<>();
        for (String line : FileUtil.readAll(FILE)) {
            if (!line.isBlank()) result.add(fromLine(line));
        }
        return result;
    }

    public Payment findById(String id) {
        return findAll().stream().filter(p -> p.getPaymentId().equals(id)).findFirst().orElse(null);
    }

    public void update(Payment updated) {
        List<Payment> list = findAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPaymentId().equals(updated.getPaymentId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new IllegalArgumentException("Payment not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    public void delete(String id) {
        List<Payment> list = findAll();
        if (!list.removeIf(p -> p.getPaymentId().equals(id))) throw new IllegalArgumentException("Payment not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    private String toLine(Payment p) {
        if (p instanceof CardPayment c) {
            return String.join("|", p.getPaymentId(), p.getReservationId(), Double.toString(p.getAmount()),
                    p.getPaymentDate(), p.getPaymentStatus(), p.getPaymentMethod(),
                    c.getCardHolderName(), c.getLastFourDigits());
        }
        if (p instanceof OnlinePayment o) {
            return String.join("|", p.getPaymentId(), p.getReservationId(), Double.toString(p.getAmount()),
                    p.getPaymentDate(), p.getPaymentStatus(), p.getPaymentMethod(),
                    o.getTransactionId(), o.getProvider());
        }
        return String.join("|", p.getPaymentId(), p.getReservationId(), Double.toString(p.getAmount()),
                p.getPaymentDate(), p.getPaymentStatus(), p.getPaymentMethod(), "", "");
    }

    private Payment fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 8) throw new IllegalArgumentException("Invalid payment record");

        return switch (p[5].toUpperCase()) {
            case "CASH" -> new CashPayment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[4]);
            case "CARD" -> new CardPayment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[4], p[6], p[7]);
            case "ONLINE" -> new OnlinePayment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[4], p[6], p[7]);
            default -> throw new IllegalArgumentException("Unknown payment method");
        };
    }
}
