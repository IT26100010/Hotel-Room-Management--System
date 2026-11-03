package com.hotel.controller;

import com.hotel.model.*;
import com.hotel.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) { this.service = service; }

    @PostMapping("/cash")
    public Payment cash(@RequestBody CashPayment p) { return service.create(p); }

    @PostMapping("/card")
    public Payment card(@RequestBody CardPayment p) { return service.create(p); }

    @PostMapping("/online")
    public Payment online(@RequestBody OnlinePayment p) { return service.create(p); }

    @GetMapping
    public List<Payment> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/cash/{id}")
    public Payment updateCash(@PathVariable String id, @RequestBody CashPayment p) { return service.update(id, p); }

    @PutMapping("/card/{id}")
    public Payment updateCard(@PathVariable String id, @RequestBody CardPayment p) { return service.update(id, p); }

    @PutMapping("/online/{id}")
    public Payment updateOnline(@PathVariable String id, @RequestBody OnlinePayment p) { return service.update(id, p); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
