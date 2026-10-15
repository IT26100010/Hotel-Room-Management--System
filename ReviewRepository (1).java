package com.hotel.repository;

import com.hotel.model.*;
import com.hotel.util.FileUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
    private static final String FILE = "data/reviews.txt";

    public void save(Review r) { FileUtil.append(FILE, toLine(r)); }

    public List<Review> findAll() {
        List<Review> result = new ArrayList<>();
        for (String line : FileUtil.readAll(FILE)) {
            if (!line.isBlank()) result.add(fromLine(line));
        }
        return result;
    }

    public Review findById(String id) {
        return findAll().stream().filter(r -> r.getReviewId().equals(id)).findFirst().orElse(null);
    }

    public void update(Review updated) {
        List<Review> list = findAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getReviewId().equals(updated.getReviewId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }
        if (!found) throw new IllegalArgumentException("Review not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    public void delete(String id) {
        List<Review> list = findAll();
        if (!list.removeIf(r -> r.getReviewId().equals(id))) throw new IllegalArgumentException("Review not found");
        FileUtil.writeAll(FILE, list.stream().map(this::toLine).toList());
    }

    private String toLine(Review r) {
        String type = r.getReviewType();
        String extra = r instanceof VerifiedReview v ? Boolean.toString(v.isVerifiedBooking()) : "";
        return String.join("|", r.getReviewId(), r.getCustomerId(), r.getReservationId(),
                Integer.toString(r.getRating()), r.getComment().replace("|", "/"),
                r.getReviewDate(), type, extra);
    }

    private Review fromLine(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length != 8) throw new IllegalArgumentException("Invalid review record");

        if ("VERIFIED".equalsIgnoreCase(p[6])) {
            return new VerifiedReview(p[0], p[1], p[2], Integer.parseInt(p[3]),
                    p[4], p[5], Boolean.parseBoolean(p[7]));
        }
        return new PublicReview(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], p[5]);
    }
}
