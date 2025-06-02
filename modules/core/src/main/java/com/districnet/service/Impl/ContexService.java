package com.districnet.service.Impl;


import com.districnet.config.ActiveContex;
import com.districnet.dto.NodeDisplayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContexService {

    private final ActiveContex activeContex;

    

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

   

   


    @Scheduled(fixedRate = 60000)
    public void cleanupInactiveNodes() {
        removeInactiveNodes(60);
    }

    private void removeInactiveNodes(int i) {
    }
}
