package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Commento necessario")
    private String content;

    @NotNull(message = "L'ID della foto è necessario")
    private Long photoId;
}
