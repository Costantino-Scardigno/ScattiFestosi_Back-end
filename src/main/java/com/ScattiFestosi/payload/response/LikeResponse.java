package com.ScattiFestosi.payload.response;

import lombok.Data;
import java.util.Date;

@Data
public class LikeResponse {
    private Long id;
    private Date createdAt;
    private Long photoId;
    private Long userId;
    private String username;
}
