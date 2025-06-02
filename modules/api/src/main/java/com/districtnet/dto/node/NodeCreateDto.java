package com.districtnet.dto.node;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class NodeCreateDto {

    private Long nodeId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String hostname;

    @NotBlank
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address format")
    private String ipAddress;

    @NotBlank
    private String os;

    private String description;

    @PositiveOrZero(message = "CPU must be 0 or positive")
    private Float cpu;

    @PositiveOrZero(message = "RAM must be 0 or positive")
    private Float ram;

    @PositiveOrZero(message = "Disk must be 0 or positive")
    private Float disk;
}
