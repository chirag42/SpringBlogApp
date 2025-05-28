package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Blog {
    @Id
    private String id;

    private String author;

    private List<String> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "id: " + id + " author: " + author + " content: " + content;
    }
}
