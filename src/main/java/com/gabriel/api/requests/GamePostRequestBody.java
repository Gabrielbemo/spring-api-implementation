package com.gabriel.api.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GamePostRequestBody {

    @NotEmpty(message = "The game name cannot be empty")
    private String name;
}
