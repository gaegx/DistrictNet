package com.districtnet.model;

import com.districtnet.Enum.Auth_type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

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

    private Set<String> resource;

    @NotBlank
    @Size(min = 4, max = 255)
    private String userName;

    @NotBlank
    private String authKey;

    private Auth_type typeAuth;

}
