package com.example.demo.Blog.Service;

import com.example.demo.Blog.Model.Blog;
import com.example.demo.Blog.Model.UserResponseDTO;
import com.example.demo.Blog.Repository.BlogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogService {

    String userServiceURL = "http://localhost:8081/users/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    public Blog createBlogService(Blog blog, String username) {
        if (blog.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
       try{ String fetchUserServiceURL = userServiceURL + username;
        ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = restTemplate.getForEntity(fetchUserServiceURL, UserResponseDTO.class);
        UserResponseDTO user = userResponseDTOResponseEntity.getBody();
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        blog.setPublishDate(LocalDateTime.now());
        Blog blogFromDB = blogRepository.save(blog);
        user.setBlogIds(new ArrayList<>());
        user.getBlogIds().add(String.valueOf(blogFromDB.getId()));
        String saveUserServiceURL = userServiceURL + "update";
        restTemplate.put(saveUserServiceURL, user);
           return blogFromDB;
       }
       catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    public boolean removeBlog(ObjectId id) {
        if (!blogRepository.existsById(id)) return false;
        blogRepository.deleteById(id);
        return true;
    }

    public Blog getBlogById(ObjectId id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.orElse(null);
    }

    public Blog updateBlog(Blog blog) {
        Blog blogFromDB = getBlogById(blog.getId());
        if (blogFromDB != null) {
            blogFromDB.setTitle(!blog.getTitle().isEmpty() ? blog.getTitle() : blogFromDB.getTitle());
            List<String> updatedContent = new ArrayList<>();
            if (blog.getContent() != null) {
                for (String content : blog.getContent()) {
                    blogFromDB.getContent().add(content);
                }
            }
            blogRepository.save(blogFromDB);
        }
        return blogFromDB;
    }

    public List<String> getAuthors() {
        List<Blog> allBlogs =  blogRepository.findAll();
        return allBlogs.stream().map(Blog::getTitle).distinct()
                .collect(Collectors.toList());
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> findBlogByAuthor(String author) {
        //return blogRepository.findByAuthor(author);
        Query query = new Query(Criteria.where("title").is(author));
        return mongoTemplate.find(query, Blog.class);
    }
}