package com.thebookshop;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class Book  {
    private String title;
    private String author;
    private String imageUrl;
    private String publishedYear;
    private String content;
    private float price;
    protected String indexer;
    ImageIcon bgImage;


    public Book(String title, String author, String imageUrl, String publishedYear, String content, float price) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishedYear = publishedYear;
        this.content = content;
        this.price = price;
        try {
            bgImage =
                    new ImageIcon(Book.class.getResourceAsStream("../../"+imageUrl).readAllBytes());
            System.out.println(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        indexer =
                title.toLowerCase() + "-" + author.toLowerCase() + "-" + publishedYear.toLowerCase();

    }

    public Book(Book book) {
        author = book.author;
        title = book.title;
        imageUrl = book.imageUrl;
        publishedYear = book.publishedYear;
        content = book.content;
        price = book.price;
        indexer = book.indexer;
        bgImage = book.bgImage;
        ;
    }

    public JLabel getCover(){
        JLabel cover = new JLabel();
        cover.setIcon(bgImage);
        return cover;
    }


    String rotateAuthorName(){
        return author.split(",")[1] + " " + author.split(",")[0];
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIndexer() {
        return indexer;
    }

    public void setIndexer(String indexer) {
        this.indexer = indexer;
    }

}
