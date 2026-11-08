package com.hotel.service;

import com.hotel.model.Reservation;
import com.hotel.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final RoomService roomService;

    public ReservationService(ReservationRepository repository, RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public Reservation create(Reservation r) {
        if (repository.findById(r.getReservationId()) != null)
            throw new IllegalArgumentException("Reservation ID already exists");

        var room = roomService.getById(r.getRoomId());
        if (!"AVAILABLE".equalsIgnoreCase(room.getAvailability()))
            throw new IllegalArgumentException("Room is not available");
        if (r.getNumberOfGuests() > room.getCapacity())
            throw new IllegalArgumentException("Guest count exceeds room capacity");

        repository.save(r);
        room.setAvailability("BOOKED");
        roomService.update(room.getRoomId(), room);
        return r;
    }

    public List<Reservation> getAll() { return repository.findAll(); }

    public Reservation getById(String id) {
        Reservation r = repository.findById(id);
        if (r == null) throw new IllegalArgumentException("Reservation not found");
        return r;
    }

    public Reservation update(String id, Reservation r) {
        r.setReservationId(id);
        repository.update(r);
        return r;
    }

    public void delete(String id) {
        Reservation r = getById(id);
        repository.delete(id);
        try {
            var room = roomService.getById(r.getRoomId());
            room.setAvailability("AVAILABLE");
            roomService.update(room.getRoomId(), room);
        } catch (Exception ignored) {}
    }
}
