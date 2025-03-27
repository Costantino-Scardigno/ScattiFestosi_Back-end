package com.ScattiFestosi.service;


import com.ScattiFestosi.model.Comment;
import com.ScattiFestosi.model.Photo;
import com.ScattiFestosi.model.User;
import com.ScattiFestosi.payload.response.CommentResponse;
import com.ScattiFestosi.repository.CommentRepository;
import com.ScattiFestosi.repository.PhotoRepository;
import com.ScattiFestosi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PhotoRepository photoRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Comment addComment(String content, Long photoId, User user) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Foto non trovata"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPhoto(photo);
        comment.setUser(user);
        comment.setCreatedAt(new Date());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPhotoId(Long photoId) {
        return commentRepository.findByPhotoIdOrderByCreatedAtDesc(photoId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment updateComment(Long id, String content, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commento non trovato"));

        // Verifica che l'utente sia il proprietario del commento
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Non sei autorizzato a modificare il commento");
        }

        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commento non trovato"));

        // Verifica che l'utente sia il proprietario del commento
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Non sei autorizzato ad eliminare il commento");
        }

        commentRepository.delete(comment);
    }

    public CommentResponse convertToDto(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setPhotoId(comment.getPhoto().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setProfileImage(comment.getUser().getProfileImage());
        return dto;
    }
}