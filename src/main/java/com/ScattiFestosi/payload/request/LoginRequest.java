package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoginRequest {

    @NotBlank(message = "Username obbligarotio")
    private String username;

    @NotBlank(message = "Password obbligatoria")
    private String password;
}
