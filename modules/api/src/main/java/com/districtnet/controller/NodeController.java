package com.districtnet.controller;

import com.districtnet.dto.node.NodeCreateDto;
import com.districtnet.dto.node.NodeDisplayDto;
import com.districtnet.dto.node.NodeViewDto;
import com.districtnet.service.NodeService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nodes")
public class NodeController {

    private final NodeService nodeService;


    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    public ResponseEntity<NodeDisplayDto> createNode(@Valid @RequestBody NodeCreateDto dto) {
        System.out.println(dto.toString());
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
