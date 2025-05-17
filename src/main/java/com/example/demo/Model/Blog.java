package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Blog {
    @Id
    private Long id;
    private String author;
    @ElementCollection
    private List<String> content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
