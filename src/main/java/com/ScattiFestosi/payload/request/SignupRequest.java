package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SignupRequest {

    @NotBlank(message = "Username obbligatorio")
    @Size(min = 3, max = 20, message = "Username deve contenere tra i 3 ed 20 caratteri")
    private String username;

    @NotBlank(message = "Email obbligatorio")
    @Size(max = 50)
    @Email(message = "Email dev'essere valida")
    private String email;

    @NotBlank(message = "Password obbligatoria")
    @Size(min = 6, max = 40, message = " La Password deve contenere tra i 6 ed 40 caratteri")
    private String password;
}
