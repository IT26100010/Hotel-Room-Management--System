package com.hotel.repository;

import com.hotel.model.*;
import com.hotel.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelServiceRepository {
    private static final String FILE = "data/services.txt";

    public void save(HotelService s) { FileUtil.append(FILE, toLine(s)); }

    public List<HotelService> findAll() {
        List<HotelService> result = new ArrayList<>();
        for (String line : FileUtil.readAll(FILE)) {
            if (!line.isBlank()) result.add(fromLine(line));
        }
        return result;
    }

    public HotelService findById(String id) {
        return findAll().stream().filter(s -> s.getServiceId().equals(id)).findFirst().orElse(null);
    }

    public void update(HotelService updated) {
        List<HotelService> list = findAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getServiceId().equals(updated.getServiceId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new IllegalArgumentException("Service not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    public void delete(String id) {
        List<HotelService> list = findAll();
        if (!list.removeIf(s -> s.getServiceId().equals(id))) throw new IllegalArgumentException("Service not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    private String toLine(HotelService s) {
        String type = "GENERAL", e1 = "", e2 = "";
        if (s instanceof FoodService f) { type = "FOOD"; e1 = f.getMealType(); }
        if (s instanceof TransportService t) { type = "TRANSPORT"; e1 = t.getVehicleType(); e2 = t.getPickupLocation(); }
        if (s instanceof LaundryService) type = "LAUNDRY";

        return String.join("|", s.getServiceId(), s.getServiceName(), s.getDescription(),
                Double.toString(s.getPrice()), s.getAvailability(), type, e1, e2);
    }

    private HotelService fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 8) throw new IllegalArgumentException("Invalid service record");

        return switch (p[5].toUpperCase()) {
            case "FOOD" -> new FoodService(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4], p[6]);
            case "TRANSPORT" -> new TransportService(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4], p[6], p[7]);
            case "LAUNDRY" -> new LaundryService(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4]);
            default -> new HotelService(p[0], p[1], p[2], Double.parseDouble(p[3]), p[4]);
        };
    }
}
