package com.thebookshop;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Books extends RBTree {
    public Books (){
        super();
        addBooks();
    };
    public Book findBook(String title, String author, String publishedYear) {
        String key = title.toLowerCase() + "-" + author.toLowerCase() + "-" + publishedYear.toLowerCase();
        Node result = searchNode(key);
        if (result != null) {
            return result.book;
        } else {
            return null;
        }
    }

    public Book rentBook(Book book) {
        String key = book.getTitle().toLowerCase() + "-" + book.getAuthor().toLowerCase() + "-" + book.getPublishedYear().toLowerCase();
        Node result = deleteNode(key);

        if (result != null) {
            return result.book;
        } else {
            return null;
        }
    }

    public void returnBook(Book book) {
        insertNode(book);
    }

    private void addBooks() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> jsonMap =
                    objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("books.json"),
                    new TypeReference<List<Map<String, Object>>>() {
                    });

            jsonMap.forEach(result -> {
                System.out.println(result.get("title"));
                insertNode(new Book((String) result.get("title"), (String) result.get("author"),
                        (String) result.get("imageLink"), Integer.toString((Integer) result.get("year")),
                        (String) result.get("link"),
                       randomPrice(200, 700) ));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float randomPrice(int min, int max){
        Random random = new Random();
        return random.nextFloat(min, max);
    }
}
