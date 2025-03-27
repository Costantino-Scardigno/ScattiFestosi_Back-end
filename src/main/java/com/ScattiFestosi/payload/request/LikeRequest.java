package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeRequest {

    @NotNull(message = "L'ID della foto è necessario")
    private Long photoId;
}
