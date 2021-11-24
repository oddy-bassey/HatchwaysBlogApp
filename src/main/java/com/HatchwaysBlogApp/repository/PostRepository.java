package com.HatchwaysBlogApp.repository;

import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
}
