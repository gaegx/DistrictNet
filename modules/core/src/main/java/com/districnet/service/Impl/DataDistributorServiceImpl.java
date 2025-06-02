package com.districnet.service.Impl;

import com.districnet.config.ActiveContex;
import com.districnet.dto.DataChunkDto;
import com.districnet.dto.TaskCreateDto;
import com.districnet.dto.NodeDisplayDto;
import com.districnet.service.CsvSplitterService;
import com.districnet.service.DataDistributorService;
import com.districnet.service.LoadBalancerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataDistributorServiceImpl implements DataDistributorService {

    private final CsvSplitterService csvSplitterService;
    private final LoadBalancerService loadBalancerService;
    private final ActiveContex activeContex;


    private static final int DEFAULT_CHUNK_SIZE = 16;

    @Override
    public void distribute(TaskCreateDto taskCreateDto) {
        try {
            List<DataChunkDto> chunkDtos = csvSplitterService.splitCsv(taskCreateDto.getDataPath(), DEFAULT_CHUNK_SIZE);
            log.info("Разделено {} чанков из CSV-файла {}", chunkDtos.size(), taskCreateDto.getDataPath());

            Map<String, NodeDisplayDto> activeNodes = activeContex.getAllNodes()
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().getWeight() != null && e.getValue().getWeight() > 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            if (activeNodes.isEmpty()) {
                log.warn("Нет доступных узлов для распределения данных");
                return;
            }

            Map<String, List<DataChunkDto>> assignments = loadBalancerService.distribute(chunkDtos, activeNodes);

            for (Map.Entry<String, List<DataChunkDto>> entry : assignments.entrySet()) {
                String nodeId = entry.getKey();
                NodeDisplayDto node = activeNodes.get(nodeId);
                List<DataChunkDto> assignedChunks = entry.getValue();
                log.info("Узел {} (IP: {}) получил {} чанков", node.getHostname(), node.getIpAddress(), assignedChunks.size());
            }

        } catch (Exception e) {
            log.error("Ошибка при распределении данных: {}", e.getMessage(), e);
        }
    }
}
