package com.example.demo.Blog.Repository;

import com.example.demo.Blog.Model.Blog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, ObjectId>, BlogRepositoryInterface {


    @Query("{ 'author': ?0 }")
    List<Blog> findByAuthor(String author);

}
