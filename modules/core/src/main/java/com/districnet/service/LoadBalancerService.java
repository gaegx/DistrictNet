package com.districnet.service;

import com.districnet.dto.DataChunkDto;
import com.districnet.dto.NodeDisplayDto;

import java.util.List;
import java.util.Map;


public interface LoadBalancerService {
    Map<String, List<DataChunkDto>> distribute(List<DataChunkDto> chunks, Map<String, NodeDisplayDto> nodes);
}
