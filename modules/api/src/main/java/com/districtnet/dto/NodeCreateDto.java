package com.districtnet.dto;


import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class NodeCreateDto {
    
    private Long nodeId;

    @NotBlank
    @Size(min = 4,max = 255)
    private String hostname;

    @NotBlank
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address format")
    private String ipAddress;

    @NotBlank
    private String os;

    private String description;
    private Set<String> tags;

    
}
