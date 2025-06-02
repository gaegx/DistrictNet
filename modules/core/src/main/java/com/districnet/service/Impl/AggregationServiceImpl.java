package com.districnet.service.Impl;

import com.districnet.dto.ModelResultDto;
import com.districnet.service.AggregationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AggregationServiceImpl implements AggregationService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String aggregate(List<String> partialResults) {
        List<ModelResultDto> models = new ArrayList<>();

        try {
            for (String resultJson : partialResults) {
                models.add(objectMapper.readValue(resultJson, ModelResultDto.class));
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при разборе результатов моделей", e);
        }

        if (models.isEmpty()) return "{}";


        Map<String, Double> aggregatedWeights = new HashMap<>();
        double aggregatedBias = 0.0;

        for (ModelResultDto model : models) {
            for (Map.Entry<String, Double> entry : model.getWeights().entrySet()) {
                aggregatedWeights.merge(entry.getKey(), entry.getValue(), Double::sum);
            }
            aggregatedBias += model.getBias();
        }

        int count = models.size();
        for (String key : aggregatedWeights.keySet()) {
            aggregatedWeights.put(key, aggregatedWeights.get(key) / count);
        }
        aggregatedBias /= count;

        ModelResultDto finalModel = new ModelResultDto(models.get(0).getModel(), aggregatedWeights, aggregatedBias);

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(finalModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сериализации итоговой модели", e);
        }
    }
}
