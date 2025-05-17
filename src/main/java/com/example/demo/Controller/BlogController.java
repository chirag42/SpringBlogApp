package com.example.demo.Controller;

import com.example.demo.Model.Blog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@RestController
@RequestMapping("/blogs")
public class BlogController {

    Map<Long, Blog> blogsCache = new HashMap<>();
    private AtomicLong blogIdGenerator = new AtomicLong(1);

    @PostMapping("/create")
    public ResponseEntity<String> createBlog(@RequestBody Blog blog) {
        System.out.println(blog.getAuthor());
        long id = blogIdGenerator.getAndIncrement();
        blog.setId(id);
        blogsCache.put(id, blog);
        return ResponseEntity.ok("Blog created");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBlog(@RequestBody Blog blog) {
        long id = blog.getId();
        if (blogsCache.containsKey(id)) {
            for(String content : blog.getContent()){
                blogsCache.get(id).getContent().add(content);
            }
            return ResponseEntity.ok("Blog updated");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getBlog/{id}")
    public String getBlogById(@PathVariable Long id) {
        Blog blog = blogsCache.get(id);
        if(blog == null) return "Blog not found";
        return blog.toString();


    }

    @GetMapping("/allBlogs")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogsCache.values().stream().toList();
        return ResponseEntity.ok(blogs);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        if (blogsCache.containsKey(id)) {
            blogsCache.remove(id);
            return ResponseEntity.ok("Blog deleted");
        }
        return ResponseEntity.notFound().build();
    }


}
