package com.hotel.repository;

import com.hotel.model.*;
import com.hotel.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomRepository {
    private static final String FILE = "data/rooms.txt";

    public void save(Room room) {
        FileUtil.append(FILE, toLine(room));
    }

    public List<Room> findAll() {
        List<Room> result = new ArrayList<>();
        for (String line : FileUtil.readAll(FILE)) {
            if (!line.isBlank()) result.add(fromLine(line));
        }
        return result;
    }

    public Room findById(String id) {
        return findAll().stream().filter(r -> r.getRoomId().equals(id)).findFirst().orElse(null);
    }

    public void update(Room updated) {
        List<Room> list = findAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRoomId().equals(updated.getRoomId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new IllegalArgumentException("Room not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    public void delete(String id) {
        List<Room> list = findAll();
        if (!list.removeIf(r -> r.getRoomId().equals(id))) throw new IllegalArgumentException("Room not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    private String toLine(Room r) {
        String extra = "";
        if (r instanceof DeluxeRoom d) extra = Boolean.toString(d.isBalcony());
        if (r instanceof SuiteRoom s) extra = Boolean.toString(s.isLivingArea());

        return String.join("|", r.getRoomId(), r.getRoomNumber(),
                r.getRoomType(), Double.toString(r.getBasePrice()),
                Integer.toString(r.getCapacity()), r.getAvailability(), extra);
    }

    private Room fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 7) throw new IllegalArgumentException("Invalid room record");

        return switch (p[2].toUpperCase()) {
            case "STANDARD" -> new StandardRoom(p[0], p[1], Double.parseDouble(p[3]),
                    Integer.parseInt(p[4]), p[5]);
            case "DELUXE" -> new DeluxeRoom(p[0], p[1], Double.parseDouble(p[3]),
                    Integer.parseInt(p[4]), p[5], Boolean.parseBoolean(p[6]));
            case "SUITE" -> new SuiteRoom(p[0], p[1], Double.parseDouble(p[3]),
                    Integer.parseInt(p[4]), p[5], Boolean.parseBoolean(p[6]));
            default -> throw new IllegalArgumentException("Unknown room type");
        };
    }
}
