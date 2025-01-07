package com.example.gymapp.model;

public class Review {
    private int reviewID;
    private int userID;
    private int productID;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(int reviewID, int userID, int productID, int rating, String comment) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.productID = productID;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", userID=" + userID +
                ", productID=" + productID +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
