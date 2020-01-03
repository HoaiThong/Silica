package net.silica.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class Book implements Serializable {

    @Expose
    private int idBook;
    @Expose
    private String nameBook;
    @Expose
    private String foreignNameBook;
    @Expose
    private String authorBook;
    @Expose
    private String summaryBook;
    @Expose
    private String countryBook;
    @Expose
    private String tagBook;
    @Expose
    private String iconBook;
    @Expose
    private float priceOriginBook;
    @Expose
    private float scoreRatingBook;
    @Expose
    private long viewSumBook;
    @Expose
    private int viewWeekBook;
    @Expose
    private int viewMonthBook;
    @Expose
    private int viewDayBook;
    @Expose
    private String namsxBook;
    @Expose
    private String nxbBook;
    @Expose(serialize = false, deserialize = true)
    private Timestamp createAtBook;
    @Expose(serialize = false, deserialize = true)
    private Timestamp updateAtBook;

    public Book() {
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getForeignNameBook() {
        return foreignNameBook;
    }

    public void setForeignNameBook(String foreignNameBook) {
        this.foreignNameBook = foreignNameBook;
    }

    public String getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(String authorBook) {
        this.authorBook = authorBook;
    }

    public String getSummaryBook() {
        return summaryBook;
    }

    public void setSummaryBook(String summaryBook) {
        this.summaryBook = summaryBook;
    }

    public String getCountryBook() {
        return countryBook;
    }

    public void setCountryBook(String countryBook) {
        this.countryBook = countryBook;
    }

    public String getTagBook() {
        return tagBook;
    }

    public void setTagBook(String tagBook) {
        this.tagBook = tagBook;
    }

    public String getIconBook() {
        return iconBook;
    }

    public void setIconBook(String iconBook) {
        this.iconBook = iconBook;
    }

    public float getPriceOriginBook() {
        return priceOriginBook;
    }

    public void setPriceOriginBook(float priceOriginBook) {
        this.priceOriginBook = priceOriginBook;
    }

    public float getScoreRatingBook() {
        return scoreRatingBook;
    }

    public void setScoreRatingBook(float scoreRatingBook) {
        this.scoreRatingBook = scoreRatingBook;
    }

    public long getViewSumBook() {
        return viewSumBook;
    }

    public void setViewSumBook(long viewSumBook) {
        this.viewSumBook = viewSumBook;
    }

    public int getViewWeekBook() {
        return viewWeekBook;
    }

    public void setViewWeekBook(int viewWeekBook) {
        this.viewWeekBook = viewWeekBook;
    }

    public int getViewMonthBook() {
        return viewMonthBook;
    }

    public void setViewMonthBook(int viewMonthBook) {
        this.viewMonthBook = viewMonthBook;
    }

    public int getViewDayBook() {
        return viewDayBook;
    }

    public void setViewDayBook(int viewDayBook) {
        this.viewDayBook = viewDayBook;
    }

    public String getNamsxBook() {
        return namsxBook;
    }

    public void setNamsxBook(String namsxBook) {
        this.namsxBook = namsxBook;
    }

    public String getNxbBook() {
        return nxbBook;
    }

    public void setNxbBook(String nxbBook) {
        this.nxbBook = nxbBook;
    }

    public Timestamp getCreateAtBook() {
        return createAtBook;
    }

    public void setCreateAtBook(Timestamp createAtBook) {
        this.createAtBook = createAtBook;
    }

    public Timestamp getUpdateAtBook() {
        return updateAtBook;
    }

    public void setUpdateAtBook(Timestamp updateAtBook) {
        this.updateAtBook = updateAtBook;
    }
}
