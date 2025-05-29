package com.example.demo.Service;

import com.example.demo.Model.Blog;
import com.example.demo.Repository.BlogRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Blog createBlogService(Blog blog) {
        if (blog.getAuthor() == null) {
            throw new IllegalArgumentException("Author is required");
        }
        blog.setPublishDate(LocalDateTime.now());
        return blogRepository.save(blog);
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
            blogFromDB.setAuthor(blog.getAuthor() != null && !blog.getAuthor().isEmpty() ? blog.getAuthor() : blogFromDB.getAuthor());
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
        return allBlogs.stream().map(Blog::getAuthor).distinct().
                collect(Collectors.toList());
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> findBlogByAuthor(String author) {
        //return blogRepository.findByAuthor(author);
        Query query = new Query(Criteria.where("author").is(author));
        return mongoTemplate.find(query, Blog.class);

    }
}