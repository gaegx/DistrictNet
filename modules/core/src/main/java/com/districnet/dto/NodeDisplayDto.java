package com.districnet.dto;

import lombok.Data;

@Data
public class NodeDisplayDto {
    private Long nodeId;
    private String hostname;
    private String ipAddress;
    private String os;
    private String description;
    private Float cpu;
    private Float ram;
    private Float disk;
    private Float weight;

}
