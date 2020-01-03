package net.silica.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class Content implements Serializable {
    @Expose
    private int idContent;
    @Expose
    private Book book;
    @Expose
    private String urlFileContent;
    @Expose
    private String mimeTypeContent;
    @Expose
    private float priceContent;
    @Expose(serialize = false)
    private Timestamp createAtContent;
    @Expose(serialize = false)
    private Timestamp updateAtContent;

    public Content() {
    }

    public int getIdContent() {
        return idContent;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setIdContent(int idContent) {
        this.idContent = idContent;
    }

    public String getUrlFileContent() {
        return urlFileContent;
    }

    public void setUrlFileContent(String urlFileContent) {
        this.urlFileContent = urlFileContent;
    }

    public String getMimeTypeContent() {
        return mimeTypeContent;
    }

    public void setMimeTypeContent(String mimeTypeContent) {
        this.mimeTypeContent = mimeTypeContent;
    }

    public float getPriceContent() {
        return priceContent;
    }

    public void setPriceContent(float priceContent) {
        this.priceContent = priceContent;
    }

    public Timestamp getCreateAtContent() {
        return createAtContent;
    }

    public void setCreateAtContent(Timestamp createAtContent) {
        this.createAtContent = createAtContent;
    }

    public Timestamp getUpdateAtContent() {
        return updateAtContent;
    }

    public void setUpdateAtContent(Timestamp updateAtContent) {
        this.updateAtContent = updateAtContent;
    }
}
