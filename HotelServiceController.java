package com.hotel.controller;

import com.hotel.model.*;
import com.hotel.service.HotelServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class HotelServiceController {
    private final HotelServiceManager service;

    public HotelServiceController(HotelServiceManager service) { this.service = service; }

    @PostMapping("/general")
    public HotelService createGeneral(@RequestBody HotelService s) { return service.create(s); }

    @PostMapping("/food")
    public HotelService createFood(@RequestBody FoodService s) { return service.create(s); }

    @PostMapping("/transport")
    public HotelService createTransport(@RequestBody TransportService s) { return service.create(s); }

    @PostMapping("/laundry")
    public HotelService createLaundry(@RequestBody LaundryService s) { return service.create(s); }

    @GetMapping
    public List<HotelService> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public HotelService getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/{id}/general")
    public HotelService updateGeneral(@PathVariable String id, @RequestBody HotelService s) { return service.update(id, s); }

    @PutMapping("/{id}/food")
    public HotelService updateFood(@PathVariable String id, @RequestBody FoodService s) { return service.update(id, s); }

    @PutMapping("/{id}/transport")
    public HotelService updateTransport(@PathVariable String id, @RequestBody TransportService s) { return service.update(id, s); }

    @PutMapping("/{id}/laundry")
    public HotelService updateLaundry(@PathVariable String id, @RequestBody LaundryService s) { return service.update(id, s); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
