package com.districtnet.service;

import com.districtnet.dto.NodeCreateDto;
import com.districtnet.dto.NodeDisplayDto;
import com.districtnet.dto.NodeViewDto;
import com.districtnet.mapper.NodeMapper;
import com.districtnet.model.Node;
import com.districtnet.repository.NodeRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService {
    
    private final NodeRepository nodeRepository;
    private final NodeMapper nodeMapper;

    public NodeService(NodeRepository nodeRepository, NodeMapper nodeMapper) {
        this.nodeRepository = nodeRepository;
        this.nodeMapper = nodeMapper;
    }

    public NodeDisplayDto getById(Long id) {
        Node node = nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found: " + id));
        return nodeMapper.toDisplayDto(node);
    }

    public List<NodeViewDto> getAll() {
        List<Node> nodes = nodeRepository.findAll();
        return nodes.stream()
                .map(nodeMapper::toViewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public NodeDisplayDto create(NodeCreateDto dto) {
        if (nodeRepository.existsById(dto.getNodeId())) {
            throw new RuntimeException("Node with id already exists: " + dto.getNodeId());
        }

        Node node = nodeMapper.toEntity(dto);
        nodeRepository.save(node);
        return nodeMapper.toDisplayDto(node);
    }

    public void delete(Long id) {
        if (!nodeRepository.existsById(id)) {
            throw new RuntimeException("Node not found: " + id);
        }
        nodeRepository.deleteById(id);
    }
}
