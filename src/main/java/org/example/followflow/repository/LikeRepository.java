package org.example.followflow.repository;

import org.example.followflow.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByPostId(Long postId);
}
