package com.gabriel.api.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GamePostRequestBody {

    @NotEmpty(message = "The game name cannot be empty")
    private String name;
}
