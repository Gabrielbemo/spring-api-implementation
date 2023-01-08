package com.gabriel.api.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GamePostRequestBody {

    @NotEmpty(message = "The game name cannot be empty")
    private String name;
}
