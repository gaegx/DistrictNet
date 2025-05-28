package com.districtnet.service;

import com.districtnet.config.ActiveContex;
import com.districtnet.dto.node.NodeDisplayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContexService {

    private final ActiveContex activeContex;

    public void registerOrUpdateNode(Long nodeId, NodeDisplayDto node) {
        Instant now = Instant.now();
        node.setLastSeen(now);
        if (node.getRegisteredAt() == null) {
            node.setRegisteredAt(now);
        }
        activeContex.addNode(nodeId.toString(), node);
    }

    public Optional<NodeDisplayDto> getNodeById(Long nodeId) {
        return Optional.ofNullable(activeContex.getNode(nodeId.toString()));
    }

    public void removeNode(Long nodeId) {
        activeContex.removeNode(nodeId.toString());
    }

    public boolean isNodePresent(Long nodeId) {
        return activeContex.containsNode(nodeId.toString());
    }

    public Collection<NodeDisplayDto> getAllActiveNodes() {
        return activeContex.getAllNodes().values();
    }

    public void updateNodeMetrics(Long nodeId, Float cpu, Float ram, Float disk) {
        NodeDisplayDto node = activeContex.getNode(nodeId.toString());
        if (node != null) {
            node.setCpu(cpu);
            node.setRam(ram);
            node.setDisk(disk);
            node.setLastSeen(Instant.now());
            activeContex.addNode(nodeId.toString(), node);
        }
    }

    public void removeInactiveNodes(long timeoutSeconds) {
        Instant now = Instant.now();
        activeContex.getAllNodes().values().removeIf(node ->
                node.getLastSeen() != null &&
                        node.getLastSeen().isBefore(now.minusSeconds(timeoutSeconds))
        );
    }


    @Scheduled(fixedRate = 60000)
    public void cleanupInactiveNodes() {
        removeInactiveNodes(120); // 2 минуты
    }
}
