package com.example.demo.Repository;

import com.example.demo.Model.Blog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Transactional
public class BlogRepositoryImpl implements BlogRepositoryInterface {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Blog saveBlog(Blog blog) {
        System.out.println("Saving blog via custom method...");
        return entityManager.merge(blog);
    }

    @Override
    public List<Blog> getAllBlogs() {
        String hql = "select b from Blog b";
        return entityManager.createQuery(hql, Blog.class).getResultList();
    }

    @Override
    public List<String> getAuthors() {
        String hql = "select b.author from Blog b";
        return entityManager.createQuery(hql, String.class).getResultList();

    }
}
