package com.districnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelResultDto {
    private String model;
    private Map<String, Double> weights;
    private double bias;
}

