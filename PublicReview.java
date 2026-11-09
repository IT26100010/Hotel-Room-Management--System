package com.hotel.model;

public class PublicReview extends Review {
    public PublicReview() {}
    public PublicReview(String id, String customerId, String reservationId, int rating, String comment, String date) {
        super(id, customerId, reservationId, rating, comment, date);
    }

    @Override public String getReviewType() { return "PUBLIC"; }

    @Override public String displayReview() {
        return getRating() + "/5 - " + getComment();
    }
}
