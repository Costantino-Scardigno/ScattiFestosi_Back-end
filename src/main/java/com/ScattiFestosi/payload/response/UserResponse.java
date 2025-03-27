package com.ScattiFestosi.payload.response;

import com.ScattiFestosi.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String profileImage; // Base64

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }
}
