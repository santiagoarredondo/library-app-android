package com.sarredondo.datasource;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@DatabaseTable(tableName = "book")
public class Book  implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(index = true, canBeNull = false)
    private String name;
    @DatabaseField
    private String author;
    @DatabaseField
    private String ISBN;
    @DatabaseField
    private String language;
    @DatabaseField
    private String publisher;
    @DatabaseField
    private String imgPath;
    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnDefinition = "LONGBLOB")
    private byte[] img;

    public Book() {
    }

    public Book(String name, String author, String ISBN, String language, String publisher, String imgPath, byte[] img) {
        this.name = name;
        this.author = author;
        this.ISBN = ISBN;
        this.language = language;
        this.publisher = publisher;
        this.imgPath = imgPath;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(ISBN, book.ISBN) &&
                Objects.equals(language, book.language) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(imgPath, book.imgPath) &&
                Arrays.equals(img, book.img);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, author, ISBN, language, publisher, imgPath);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }
}
