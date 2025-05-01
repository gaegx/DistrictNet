package com.districtnet.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class NodeDisplayDto {
    private String nodeId;
    private String hostname;
    private String ipAddress;
    private String os;
    private String description;
    private Set<String> tags;
    private Instant registeredAt;
    private Instant lastSeen;
    
}
