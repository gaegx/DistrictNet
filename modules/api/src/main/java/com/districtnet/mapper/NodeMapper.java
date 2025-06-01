package com.districtnet.mapper;

import com.districtnet.dto.node.NodeCreateDto;
import com.districtnet.dto.node.NodeDisplayDto;
import com.districtnet.dto.node.NodeViewDto;
import com.districtnet.entity.Node;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class NodeMapper {

    public NodeDisplayDto toDisplayDto(Node node) {
        if (node == null) {
            return null;
        }

        NodeDisplayDto dto = new NodeDisplayDto();
        dto.setNodeId(node.getNodeId());
        dto.setHostname(node.getHostname());
        dto.setIpAddress(node.getIpAddress());
        dto.setOs(node.getOs());
        dto.setDescription(node.getDescription());
        dto.setRegisteredAt(node.getRegisteredAt());
        dto.setLastSeen(node.getLastSeenAt());
        dto.setCpu(node.getCpu());
        dto.setRam(node.getRam());
        dto.setDisk(node.getDisk());

        return dto;
    }

    public NodeViewDto toViewDto(Node node) {
        if (node == null) {
            return null;
        }

        NodeViewDto dto = new NodeViewDto();
        dto.setNodeId(String.valueOf(node.getNodeId()));
        dto.setHostname(node.getHostname());
        dto.setStatus("ACTIVE"); // можно сделать динамически или через enum в будущем

        return dto;
    }

    public Node toEntity(NodeCreateDto dto) {
        if (dto == null) {
            return null;
        }

        Node node = new Node();
        node.setNodeId(dto.getNodeId());
        node.setHostname(dto.getHostname());
        node.setIpAddress(dto.getIpAddress());
        node.setOs(dto.getOs());
        node.setDescription(dto.getDescription());
        node.setRegisteredAt(Instant.now());
        node.setLastSeenAt(Instant.now());

        node.setCpu(dto.getCpu() != null ? dto.getCpu() : 0f);
        node.setRam(dto.getRam() != null ? dto.getRam() : 0f);
        node.setDisk(dto.getDisk() != null ? dto.getDisk() : 0f);

        return node;
    }
}
