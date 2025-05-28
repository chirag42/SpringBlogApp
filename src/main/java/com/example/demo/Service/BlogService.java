package com.example.demo.Service;

import com.example.demo.Model.Blog;
import com.example.demo.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlogService(Blog blog) {
        if (blog.getAuthor() == null) {
            throw new IllegalArgumentException("Author is required");
        }
        return blogRepository.saveBlog(blog);
    }

    public boolean removeBlog(Long id) {
        if (!blogRepository.existsById(id)) return false;
        blogRepository.deleteById(id);
        return true;
    }

    public Blog getBlogById(Long id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.orElse(null);
    }

    public Blog updateBlog(Blog blog) {
        Blog blogFromDB = getBlogById(blog.getId());
        if (blogFromDB != null) {
            List<String> updatedContent = new ArrayList<>();
            for (String content : blog.getContent()) {
                blogFromDB.getContent().add(content);
            }
            blogRepository.save(blogFromDB);
        }
        return blogFromDB;
    }

    public List<String> getAuthors() {
        return blogRepository.getAuthors();
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.getAllBlogs();
    }
}