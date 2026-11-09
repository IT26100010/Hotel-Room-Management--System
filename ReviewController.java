package com.hotel.controller;

import com.hotel.model.PublicReview;
import com.hotel.model.VerifiedReview;
import com.hotel.model.Review;
import com.hotel.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) { this.service = service; }

    @PostMapping("/public")
    public Review createPublic(@RequestBody PublicReview r) { return service.create(r); }

    @PostMapping("/verified")
    public Review createVerified(@RequestBody VerifiedReview r) { return service.create(r); }

    @GetMapping
    public List<Review> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Review getById(@PathVariable String id) { return service.getById(id); }

    @PutMapping("/public/{id}")
    public Review updatePublic(@PathVariable String id, @RequestBody PublicReview r) { return service.update(id, r); }

    @PutMapping("/verified/{id}")
    public Review updateVerified(@PathVariable String id, @RequestBody VerifiedReview r) { return service.update(id, r); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
