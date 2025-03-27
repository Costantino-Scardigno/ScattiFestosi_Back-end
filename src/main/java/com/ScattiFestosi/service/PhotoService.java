package com.ScattiFestosi.service;


import com.ScattiFestosi.model.Photo;
import com.ScattiFestosi.payload.response.CommentResponse;
import com.ScattiFestosi.payload.response.EventResponse;
import com.ScattiFestosi.payload.response.LikeResponse;
import com.ScattiFestosi.payload.response.PhotoResponse;
import com.ScattiFestosi.repository.CommentRepository;
import com.ScattiFestosi.repository.LikeRepository;
import com.ScattiFestosi.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final CommentService commentService;
    private final LikeService likeService;

    public PhotoService(PhotoRepository photoRepository,
                        CommentRepository commentRepository,
                        LikeRepository likeRepository,
                        CommentService commentService,
                        LikeService likeService) {
        this.photoRepository = photoRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public List<Photo> getPhotosByEventId(Long eventId) {
        return photoRepository.findByEventId(eventId);
    }

    public Optional<Photo> getPhotoById(Long id) {
        return photoRepository.findById(id);
    }

    @Transactional
    public Photo updatePhoto(Photo photoDetails) {
        Photo photo = photoRepository.findById(photoDetails.getId())
                .orElseThrow(() -> new RuntimeException("Foto non trovata"));

        photo.setUrl(photoDetails.getUrl());
        photo.setTimestamp(photoDetails.getTimestamp());
        return photoRepository.save(photo);
    }

    @Transactional
    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }

    public PhotoResponse convertToDto(Photo photo) {
        PhotoResponse dto = new PhotoResponse();
        dto.setId(photo.getId());
        dto.setUrl(photo.getUrl());
        dto.setTimestamp(photo.getTimestamp());
        dto.setEventId(photo.getEvent().getId());
        dto.setUserId(photo.getUser() != null ? photo.getUser().getId() : null);
        dto.setUsername(photo.getUser() != null ? photo.getUser().getUsername() : null);

        // Aggiunta delle informazioni di like e commenti
        dto.setLikeCount(photo.getLikeCount());
        dto.setCommentCount(photo.getComments() != null ? photo.getComments().size() : 0);

        // Aggiungi la lista di commenti
        if (photo.getComments() != null && !photo.getComments().isEmpty()) {
            List<CommentResponse> commentResponses = photo.getComments().stream()
                    .map(comment -> {
                        CommentResponse commentDto = new CommentResponse();
                        commentDto.setId(comment.getId());
                        commentDto.setContent(comment.getContent());
                        commentDto.setCreatedAt(comment.getCreatedAt());
                        commentDto.setPhotoId(photo.getId());
                        commentDto.setUserId(comment.getUser().getId());
                        commentDto.setUsername(comment.getUser().getUsername());
                        return commentDto;
                    })
                    .collect(Collectors.toList());
            dto.setComments(commentResponses);
        }

        return dto;
    }

    public PhotoResponse convertToDtoWithDetails(Photo photo) {
        PhotoResponse dto = convertToDto(photo);

        // Aggiungi commenti
        if (photo.getComments() != null) {
            List<CommentResponse> commentResponses = photo.getComments().stream()
                    .map(commentService::convertToDto)
                    .collect(Collectors.toList());
            dto.setComments(commentResponses);
        }

        // Aggiungi like
        if (photo.getLikes() != null) {
            List<LikeResponse> likeResponses = photo.getLikes().stream()
                    .map(likeService::convertToDto)
                    .collect(Collectors.toList());
            dto.setLikes(likeResponses);
        }

        // Aggiungi informazioni sull'evento
        if (photo.getEvent() != null) {
            EventResponse eventResponse = new EventResponse();
            eventResponse.setId(photo.getEvent().getId());
            eventResponse.setName(photo.getEvent().getName());
            eventResponse.setDescription(photo.getEvent().getDescription());
            eventResponse.setEventDate(photo.getEvent().getEventDate());
            eventResponse.setCreatedByUsername(photo.getEvent().getCreatedBy() != null ?
                    photo.getEvent().getCreatedBy().getUsername() : null);
            eventResponse.setCreatedById(photo.getEvent().getCreatedBy() != null ?
                    photo.getEvent().getCreatedBy().getId() : null);

            dto.setEvent(eventResponse);
        }

        return dto;
    }
}