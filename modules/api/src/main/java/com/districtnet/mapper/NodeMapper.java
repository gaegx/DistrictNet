package com.districtnet.mapper;

import com.districtnet.dto.node.NodeCreateDto;
import com.districtnet.dto.node.NodeDisplayDto;
import com.districtnet.dto.node.NodeViewDto;
import com.districtnet.entity.Node;
import com.districtnet.entity.NodeResource;
import com.districtnet.entity.NodeResourceInfo;
import org.springframework.stereotype.Component;

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
        dto.setResource(node.getResources());
        dto.setRegisteredAt(node.getRegisteredAt());
        dto.setLastSeen(node.getLastSeenAt());

        if (node.getResourceInfo() != null) {
            dto.setCpu(node.getResourceInfo().getCpu());
            dto.setRam(node.getResourceInfo().getRam());
            dto.setDisk(node.getResourceInfo().getDisk());
        }

        return dto;
    }

    public NodeViewDto toViewDto(Node node) {
        if (node == null) {
            return null;
        }

        NodeViewDto dto = new NodeViewDto();
        dto.setNodeId(String.valueOf(node.getNodeId()));
        dto.setHostname(node.getHostname());
        dto.setStatus("ACTIVE"); // можно сделать статус через enum в будущем

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
        node.setResources(dto.getResource());
        node.setRegisteredAt(java.time.Instant.now());
        node.setLastSeenAt(java.time.Instant.now());
        node.setUserName(dto.getUserName());
        node.setAuthKey(dto.getAuthKey());
        node.setTypeAuth(dto.getTypeAuth());


        if (dto.getCpu() != null || dto.getRam() != null || dto.getDisk() != null) {
            NodeResourceInfo resource = new NodeResourceInfo();
            resource.setCpu(dto.getCpu() != null ? dto.getCpu() : 0f);
            resource.setRam(dto.getRam() != null ? dto.getRam() : 0f);
            resource.setDisk(dto.getDisk() != null ? dto.getDisk() : 0f);
            resource.setNode(node);
            node.setResourceInfo(resource);
        }

        return node;
    }
}
