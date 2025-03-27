package com.ScattiFestosi.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PhotoRequest {

    @NotBlank(message = "Url della foto è obbligatorio")
    private String url;

    // Il timestamp può essere generato lato client o lato server
    @NotNull(message = "Timestamp è obbligatorio")
    private Date timestamp;

    // Questi campi sono utili per associare la foto all'evento e all'utente
    @NotNull(message = "Event ID è obbligatorio")
    private Long eventId;

    @NotNull(message = "Utente ID è obbligatorio")
    private Long userId;
}
