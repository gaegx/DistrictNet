package com.districnet.service;

import java.util.List;

public interface AggregationService {
    String aggregate(List<String> partialResults);
}