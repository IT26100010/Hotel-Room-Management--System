package com.hotel.service;

import com.hotel.model.Review;
import com.hotel.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) { this.repository = repository; }

    public Review create(Review r) {
        if (r.getRating() < 1 || r.getRating() > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        if (repository.findById(r.getReviewId()) != null)
            throw new IllegalArgumentException("Review ID already exists");
        repository.save(r);
        return r;
    }

    public List<Review> getAll() { return repository.findAll(); }

    public Review getById(String id) {
        Review r = repository.findById(id);
        if (r == null) throw new IllegalArgumentException("Review not found");
        return r;
    }

    public Review update(String id, Review r) {
        r.setReviewId(id);
        repository.update(r);
        return r;
    }

    public void delete(String id) { repository.delete(id); }
}
