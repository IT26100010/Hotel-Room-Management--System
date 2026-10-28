package com.hotel.controller;

import com.hotel.model.Room;
import com.hotel.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService service;

    public RoomController(RoomService service) { this.service = service; }

    @PostMapping
    public Room create(@RequestBody Room room) {
        throw new IllegalArgumentException("For polymorphic Room JSON, use the typed endpoints below.");
    }

    @PostMapping("/standard")
    public Room createStandard(@RequestBody com.hotel.model.StandardRoom room) {
        return service.create(room);
    }

    @PostMapping("/deluxe")
    public Room createDeluxe(@RequestBody com.hotel.model.DeluxeRoom room) {
        return service.create(room);
    }

    @PostMapping("/suite")
    public Room createSuite(@RequestBody com.hotel.model.SuiteRoom room) {
        return service.create(room);
    }

    @GetMapping
    public List<Room> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Room getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/{id}/standard")
    public Room updateStandard(@PathVariable String id, @RequestBody com.hotel.model.StandardRoom room) {
        return service.update(id, room);
    }

    @PutMapping("/{id}/deluxe")
    public Room updateDeluxe(@PathVariable String id, @RequestBody com.hotel.model.DeluxeRoom room) {
        return service.update(id, room);
    }

    @PutMapping("/{id}/suite")
    public Room updateSuite(@PathVariable String id, @RequestBody com.hotel.model.SuiteRoom room) {
        return service.update(id, room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
