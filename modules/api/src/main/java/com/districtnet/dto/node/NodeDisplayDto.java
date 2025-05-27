package com.districtnet.dto.node;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class NodeDisplayDto {
    private Long nodeId;
    private String hostname;
    private String ipAddress;
    private String os;
    private String description;
    private Set<String> resource;
    private Instant registeredAt;
    private Instant lastSeen;

}
