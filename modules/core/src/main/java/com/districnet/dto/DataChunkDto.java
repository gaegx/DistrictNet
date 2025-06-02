package com.districnet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DataChunkDto {
    private String filename;
    private long size;
}
