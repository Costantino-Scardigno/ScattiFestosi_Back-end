package com.ScattiFestosi.repository;


import com.ScattiFestosi.model.Comment;
import com.ScattiFestosi.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPhotoOrderByCreatedAtDesc(Photo photo);
    List<Comment> findByPhotoIdOrderByCreatedAtDesc(Long photoId);
}
