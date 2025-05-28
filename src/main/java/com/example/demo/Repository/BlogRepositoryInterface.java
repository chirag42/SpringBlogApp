package com.example.demo.Repository;
import com.example.demo.Model.Blog;
import java.util.*;


public interface BlogRepositoryInterface {
    public Blog saveBlog(Blog blog);
    public List<Blog> getAllBlogs();
    public List<String> getAuthors();
}
