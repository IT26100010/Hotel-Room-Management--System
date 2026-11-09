package com.hotel.model;

public abstract class Review {
    private String reviewId;
    private String customerId;
    private String reservationId;
    private int rating;
    private String comment;
    private String reviewDate;

    protected Review() {}

    protected Review(String reviewId, String customerId, String reservationId,
                     int rating, String comment, String reviewDate) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.reservationId = reservationId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public abstract String getReviewType();
    public abstract String displayReview();

    public String getReviewId() { return reviewId; }
    public void setReviewId(String v) { reviewId = v; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { customerId = v; }
    public String getReservationId() { return reservationId; }
    public void setReservationId(String v) { reservationId = v; }
    public int getRating() { return rating; }
    public void setRating(int v) { rating = v; }
    public String getComment() { return comment; }
    public void setComment(String v) { comment = v; }
    public String getReviewDate() { return reviewDate; }
    public void setReviewDate(String v) { reviewDate = v; }
}
