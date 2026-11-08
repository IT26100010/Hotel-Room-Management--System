package com.hotel.controller;

import com.hotel.model.Reservation;
import com.hotel.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) { this.service = service; }

    @PostMapping
    public Reservation create(@RequestBody Reservation r) { return service.create(r); }

    @GetMapping
    public List<Reservation> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable String id, @RequestBody Reservation r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
