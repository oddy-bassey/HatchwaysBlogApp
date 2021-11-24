package com.HatchwaysBlogApp.repository;

import com.HatchwaysBlogApp.model.Comment;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
