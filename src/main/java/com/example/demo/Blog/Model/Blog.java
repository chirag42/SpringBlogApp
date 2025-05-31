package com.example.demo.Blog.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "blog")
@Data
@NoArgsConstructor
public class Blog {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private List<String> content;

    private LocalDateTime publishDate;

    @Override
    public String toString(){
        return "id: " + id + " author: " + title + " content: " + content;
    }
}
