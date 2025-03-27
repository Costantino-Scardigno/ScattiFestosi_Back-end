package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class EventRequest {

    @NotBlank(message = "Nome dell'evento richiesto")
    private String name;

    private String description;


    private Date eventDate;
}
