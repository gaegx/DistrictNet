package com.districtnet.controller;

import com.districtnet.dto.NodeCreateDto;
import com.districtnet.dto.NodeDisplayDto;
import com.districtnet.dto.NodeViewDto;
import com.districtnet.service.NodeService;
import com.districtnet.mapper.NodeMapper;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {

    private final NodeService nodeService;
    private final NodeMapper nodeMapper;

    public NodeController(NodeService nodeService, NodeMapper nodeMapper) {
        this.nodeService = nodeService;
        this.nodeMapper = nodeMapper;
    }

    @PostMapping
    public ResponseEntity<NodeDisplayDto> createNode(@Valid @RequestBody NodeCreateDto dto) {
        var saved = nodeService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NodeDisplayDto> getNode(@PathVariable Long id) {
        var node = nodeService.getById(id);
        return ResponseEntity.ok(node);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        nodeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping
    public ResponseEntity<List<NodeViewDto>> getAllNodes() {
        var nodes = nodeService.getAll();
        return ResponseEntity.ok(nodes);
    }
}
