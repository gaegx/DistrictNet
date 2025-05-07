package com.districtnet.mapper;

import com.districtnet.dto.NodeCreateDto;
import com.districtnet.dto.NodeDisplayDto;
import com.districtnet.dto.NodeViewDto;
import com.districtnet.model.Node;
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



        return dto;
    }

    public NodeViewDto toViewDto(Node node) {
        if (node == null) {
            return null;
        }

        NodeViewDto dto = new NodeViewDto();
        dto.setNodeId(String.valueOf(node.getNodeId()));
        dto.setHostname(node.getHostname());


        dto.setStatus("ACTIVE");

        return dto;
    }


    public Node toEntity(NodeCreateDto nodeCreateDto) {
        if (nodeCreateDto == null) {
            return null;
        }

        Node node = new Node();
        node.setNodeId(nodeCreateDto.getNodeId());
        node.setHostname(nodeCreateDto.getHostname());
        node.setIpAddress(nodeCreateDto.getIpAddress());
        node.setOs(nodeCreateDto.getOs());
        node.setDescription(nodeCreateDto.getDescription());
        node.setResources(nodeCreateDto.getResource());
        node.setRegisteredAt(java.time.Instant.now());
        node.setLastSeenAt(java.time.Instant.now());
        node.setUserName(nodeCreateDto.getUserName());
        node.setAuthKey(nodeCreateDto.getAuthKey());
        node.setTypeAuth(nodeCreateDto.getTypeAuth());



        return node;
    }
}
