package com.hotel.service;

import com.hotel.model.HotelService;
import com.hotel.repository.HotelServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceManager {
    private final HotelServiceRepository repository;

    public HotelServiceManager(HotelServiceRepository repository) { this.repository = repository; }

    public HotelService create(HotelService s) {
        if (repository.findById(s.getServiceId()) != null) throw new IllegalArgumentException("Service ID already exists");
        repository.save(s);
        return s;
    }

    public List<HotelService> getAll() { return repository.findAll(); }

    public HotelService getById(String id) {
        HotelService s = repository.findById(id);
        if (s == null) throw new IllegalArgumentException("Service not found");
        return s;
    }

    public HotelService update(String id, HotelService s) {
        s.setServiceId(id);
        repository.update(s);
        return s;
    }

    public void delete(String id) { repository.delete(id); }
}
