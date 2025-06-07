package com.agrotech.post.infrastructure.persistence.jpa.repositories;


import com.agrotech.post.domain.model.aggregates.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAdvisorId(Long advisorId);
    List<Post> findAllByOrderByUpdatedAtDesc();
}
