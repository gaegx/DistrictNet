package com.districnet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TaskCreateDto {

     @Size(max = 255)
    private String name;

    @Size(max = 1024)
    private String dataPath;

    @NotBlank
    @Size(max = 1024)
    private String dockerPath;
}
