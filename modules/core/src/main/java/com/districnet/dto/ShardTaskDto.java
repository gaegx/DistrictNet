package com.districnet.dto;

import lombok.Data;

@Data
public class ShardTaskDto {
    private Long taskId;
    private Integer shardIndex;
    private String dataChunk; // содержимое чанка
    private String dockerPath;
    private String command;
    private String callbackUrl;
    private String parameters;
}
