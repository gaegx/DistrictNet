package com.districnet.service.Impl;



import com.districnet.dto.DataChunkDto;
import com.districnet.dto.NodeDisplayDto;
import com.districnet.service.LoadBalancerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class LoadBalancerServiceImpl implements LoadBalancerService {

    @Override
    public Map<String, List<DataChunkDto>> distribute(List<DataChunkDto> chunks, Map<String, NodeDisplayDto> nodes) {
        Map<String, List<DataChunkDto>> distribution = new HashMap<>();
        Map<String, Float> availableCapacity = new HashMap<>();
        float totalWeight = 0f;

        // Подготовка: фильтруем только узлы с положительным весом
        for (Map.Entry<String, NodeDisplayDto> entry : nodes.entrySet()) {
            String nodeId = entry.getKey();
            Float weight = entry.getValue().getWeight();
            if (weight == null || weight <= 0f) continue;

            availableCapacity.put(nodeId, weight);
            distribution.put(nodeId, new ArrayList<>());
            totalWeight += weight;
        }

        if (totalWeight <= 0f) {
            throw new IllegalStateException("Нет доступных узлов с положительным весом");
        }


        int totalChunks = chunks.size();
        Map<String, Integer> chunksPerNode = new LinkedHashMap<>();
        int assignedChunks = 0;

        for (Map.Entry<String, Float> entry : availableCapacity.entrySet()) {
            String nodeId = entry.getKey();
            float weight = entry.getValue();
            int chunkCount = Math.round((weight / totalWeight) * totalChunks);
            chunksPerNode.put(nodeId, chunkCount);
            assignedChunks += chunkCount;
        }


        int remaining = totalChunks - assignedChunks;
        Iterator<String> nodeIterator = availableCapacity.entrySet().stream()
                .sorted((a, b) -> Float.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .iterator();

        while (remaining > 0 && nodeIterator.hasNext()) {
            String nodeId = nodeIterator.next();
            chunksPerNode.put(nodeId, chunksPerNode.get(nodeId) + 1);
            remaining--;
            if (!nodeIterator.hasNext()) nodeIterator = availableCapacity.keySet().iterator();
        }


        Iterator<DataChunkDto> chunkIterator = chunks.iterator();
        for (Map.Entry<String, Integer> entry : chunksPerNode.entrySet()) {
            String nodeId = entry.getKey();
            int count = entry.getValue();

            List<DataChunkDto> list = distribution.get(nodeId);
            for (int i = 0; i < count && chunkIterator.hasNext(); i++) {
                list.add(chunkIterator.next());
            }
        }

        log.info("Балансировка завершена: {} чанков распределено между {} узлами.", chunks.size(), distribution.size());
        return distribution;
    }

}
