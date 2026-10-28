package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room create(Room room) {
        if (repository.findById(room.getRoomId()) != null) throw new IllegalArgumentException("Room ID already exists");
        repository.save(room);
        return room;
    }

    public List<Room> getAll() { return repository.findAll(); }

    public Room getById(String id) {
        Room room = repository.findById(id);
        if (room == null) throw new IllegalArgumentException("Room not found");
        return room;
    }

    public Room update(String id, Room room) {
        room.setRoomId(id);
        repository.update(room);
        return room;
    }

    public void delete(String id) { repository.delete(id); }
}
