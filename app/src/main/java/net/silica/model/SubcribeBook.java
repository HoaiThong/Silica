package net.silica.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SubcribeBook implements Serializable {
    private Customer customer;
    private Book book;
    private int isNotification;
    private float ratingStar;
    private boolean isLike;
    private Timestamp createAtSubcribe;

    public SubcribeBook() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(int isNotification) {
        this.isNotification = isNotification;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public Timestamp getCreateAtSubcribe() {
        return createAtSubcribe;
    }

    public void setCreateAtSubcribe(Timestamp createAtSubcribe) {
        this.createAtSubcribe = createAtSubcribe;
    }
}
