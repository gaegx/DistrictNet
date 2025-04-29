package com.districtnet.dto;

import lombok.Data;


@Data
public class NodeViewDto {
    private String nodeId;
    private String hostname;
    private String status;
}

