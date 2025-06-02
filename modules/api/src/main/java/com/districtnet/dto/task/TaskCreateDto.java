package com.districtnet.dto.task;


import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class TaskCreateDto {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 1024)
    private String dataPath;

    @NotBlank
    @Size(max = 1024)
    private String dockerPath;
}
