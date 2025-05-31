package com.example.demo.Blog.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDTO {

    private ObjectId id;

    private String username;

    private String password;

    private List<String> blogIds = new ArrayList<>();

    private List<Blog> blogs = new ArrayList<>();

}
