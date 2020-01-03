package net.silica.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    Customer customer;
    Book book;
    long idComment;
    String comment;
    Timestamp createAt;
    Timestamp updateAt;

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public long getIdComment() {
        return idComment;
    }

    public void setIdComment(long idComment) {
        this.idComment = idComment;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String toJSON(){
        String json="{\"idBook\": "+book.getIdBook()+",\"idCustomer\": "+customer.getIdCustomer()+", \"cmtCustomer\":\""+comment+"\"}";
        return json;
    }
}
