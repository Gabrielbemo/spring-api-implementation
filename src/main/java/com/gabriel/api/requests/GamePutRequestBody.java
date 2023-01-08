package com.gabriel.api.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GamePutRequestBody {
    private Long id;
    private String name;
}
