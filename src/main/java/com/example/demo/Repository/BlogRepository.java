package com.example.demo.Repository;

import com.example.demo.Model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String>, BlogRepositoryInterface {


    @Query("{ 'author': ?0 }")
    List<Blog> findByAuthor(String author);

}
