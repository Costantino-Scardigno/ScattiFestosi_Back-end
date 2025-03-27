package com.ScattiFestosi.repository;


import com.ScattiFestosi.model.Like;
import com.ScattiFestosi.model.Photo;
import com.ScattiFestosi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPhotoId(Long photoId);
    Optional<Like> findByPhotoAndUser(Photo photo, User user);
    boolean existsByPhotoIdAndUserId(Long photoId, Long userId);

}
