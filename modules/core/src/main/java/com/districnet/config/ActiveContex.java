package com.districnet.config;



import com.districnet.dto.NodeDisplayDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ActiveContex {

    private final Map<String, NodeDisplayDto> context = new ConcurrentHashMap<>();

    public void addNode(String nodeId, NodeDisplayDto node) {
        context.put(nodeId, node);
    }
    public NodeDisplayDto getNode(String nodeId) {
        return context.get(nodeId);
    }
    public void removeNode(String nodeId) {
        context.remove(nodeId);
    }
    public boolean containsNode(String nodeId) {
        return context.containsKey(nodeId);
    }
    public Map<String, NodeDisplayDto> getAllNodes() {
        return context;
    }
}

