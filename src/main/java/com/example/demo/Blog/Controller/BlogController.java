package com.example.demo.Blog.Controller;

import com.example.demo.Blog.Model.Blog;
import com.example.demo.Blog.Service.BlogService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;


//    Map<Long, Blog> blogsCache = new HashMap<>();
//    private AtomicLong blogIdGenerator = new AtomicLong(1);

    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        System.out.println(blog.getTitle());
        try {
            Blog saved = blogService.createBlogService(blog);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//        long id = blogIdGenerator.getAndIncrement();
//        blog.setId(id);
//        blogsCache.put(id, blog);
//        return ResponseEntity.ok("Blog created");
    }

    @PutMapping("/update")
    public ResponseEntity<Blog> updateBlog(@RequestBody Blog blog) {
        Blog updatedBlog = blogService.updateBlog(blog);
        if (updatedBlog != null) return ResponseEntity.ok(updatedBlog);
        return ResponseEntity.badRequest().body(updatedBlog);
    }

    @GetMapping("/getBlog/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable ObjectId id) {
//        Blog blog = blogsCache.get(id);
//        if(blog == null) return "Blog not found";
//        return blog.toString();
        Blog blog = blogService.getBlogById(id);
        if (blog != null) {
            return new ResponseEntity<>(blog, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allBlogs")
    public ResponseEntity<?> getAllBlogs() {
//        List<Blog> blogs = blogsCache.values().stream().toList();
//        return ResponseEntity.ok(blogs);
        List<Blog> allBlogs = blogService.getAllBlogs();
        if (!allBlogs.isEmpty())  return ResponseEntity.ok(blogService.getAllBlogs());
        return ResponseEntity.badRequest().body("No Blogs Found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable ObjectId id) {
//        if (blogsCache.containsKey(id)) {
//            blogsCache.remove(id);
//            return ResponseEntity.ok("Blog deleted");
//        }
//        return ResponseEntity.notFound().build();
        boolean deleted = blogService.removeBlog(id);
        if(deleted)  return ResponseEntity.noContent().build();
        else return ResponseEntity.badRequest().body("Blog Not Found");
    }

    @GetMapping("/authors")
    public ResponseEntity<?> getAuthors(){
        List<String> authors = blogService.getAuthors();
        if (!authors.isEmpty()) return ResponseEntity.ok(authors);
        return ResponseEntity.badRequest().body("No Author Found");
    }

    @GetMapping("/authors/{name}")
    public ResponseEntity<?> getAuthors(@PathVariable String name){
        List<Blog> authorBlog = blogService.findBlogByAuthor(name);
        if (!authorBlog.isEmpty()) return ResponseEntity.ok(authorBlog);
        return ResponseEntity.badRequest().body("No Author Found");
    }

}
