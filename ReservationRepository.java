package com.hotel.repository;

import com.hotel.model.Reservation;
import com.hotel.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {
    private static final String FILE = "data/reservations.txt";

    public void save(Reservation r) { FileUtil.append(FILE, toLine(r)); }

    public List<Reservation> findAll() {
        List<Reservation> result = new ArrayList<>();
        for (String line : FileUtil.readAll(FILE)) {
            if (!line.isBlank()) result.add(fromLine(line));
        }
        return result;
    }

    public Reservation findById(String id) {
        return findAll().stream().filter(r -> r.getReservationId().equals(id)).findFirst().orElse(null);
    }

    public void update(Reservation updated) {
        List<Reservation> list = findAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getReservationId().equals(updated.getReservationId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new IllegalArgumentException("Reservation not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    public void delete(String id) {
        List<Reservation> list = findAll();
        if (!list.removeIf(r -> r.getReservationId().equals(id))) throw new IllegalArgumentException("Reservation not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    private String toLine(Reservation r) {
        return String.join("|", r.getReservationId(), r.getCustomerId(), r.getRoomId(),
                r.getCheckInDate(), r.getCheckOutDate(),
                Integer.toString(r.getNumberOfGuests()), r.getStatus(),
                Double.toString(r.getTotalAmount()));
    }

    private Reservation fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 8) throw new IllegalArgumentException("Invalid reservation record");
        return new Reservation(p[0], p[1], p[2], p[3], p[4],
                Integer.parseInt(p[5]), p[6], Double.parseDouble(p[7]));
    }
}
