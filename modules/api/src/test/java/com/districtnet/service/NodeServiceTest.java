package com.districtnet.service;

import com.districtnet.dto.NodeCreateDto;
import com.districtnet.dto.NodeDisplayDto;
import com.districtnet.dto.NodeViewDto;
import com.districtnet.mapper.NodeMapper;
import com.districtnet.model.Node;
import com.districtnet.repository.NodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NodeServiceTest {

    private NodeRepository nodeRepository;
    private NodeMapper nodeMapper;
    private NodeService nodeService;

    @BeforeEach
    void setUp() {
        nodeRepository = mock(NodeRepository.class);
        nodeMapper = mock(NodeMapper.class);
        nodeService = new NodeService(nodeRepository, nodeMapper);
    }

    @Test
    void testGetById_Found() {
        Long id = 1L;
        Node node = new Node(); 
        NodeDisplayDto dto = new NodeDisplayDto(); 

        when(nodeRepository.findById(id)).thenReturn(Optional.of(node));
        when(nodeMapper.toDisplayDto(node)).thenReturn(dto);

        NodeDisplayDto result = nodeService.getById(id);

        assertEquals(dto, result);
        verify(nodeRepository).findById(id);
        verify(nodeMapper).toDisplayDto(node);
    }

    @Test
    void testGetById_NotFound() {
        Long id = 2L;
        when(nodeRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nodeService.getById(id);
        });

        assertTrue(exception.getMessage().contains("Node not found"));
    }

    @Test
    void testGetAll() {
        Node node1 = new Node();
        Node node2 = new Node();
        List<Node> nodes = Arrays.asList(node1, node2);
        NodeViewDto dto1 = new NodeViewDto();
        NodeViewDto dto2 = new NodeViewDto();

        when(nodeRepository.findAll()).thenReturn(nodes);
        when(nodeMapper.toViewDto(node1)).thenReturn(dto1);
        when(nodeMapper.toViewDto(node2)).thenReturn(dto2);

        List<NodeViewDto> result = nodeService.getAll();

        assertEquals(2, result.size());
        verify(nodeRepository).findAll();
    }

    @Test
    void testCreate_Success() {
        NodeCreateDto createDto = new NodeCreateDto();
        createDto.setNodeId(10L);
        Node node = new Node();
        NodeDisplayDto dto = new NodeDisplayDto();

        when(nodeRepository.existsById(10L)).thenReturn(false);
        when(nodeMapper.toEntity(createDto)).thenReturn(node);
        when(nodeMapper.toDisplayDto(node)).thenReturn(dto);

        NodeDisplayDto result = nodeService.create(createDto);

        verify(nodeRepository).save(node);
        assertEquals(dto, result);
    }

    @Test
    void testCreate_AlreadyExists() {

        NodeCreateDto createDto = new NodeCreateDto();
        createDto.setNodeId(10L);
        createDto.setHostname("hostname");


        Node existingNode = new Node();
        existingNode.setHostname("hostname");

        when(nodeRepository.findByHostname("hostname")).thenReturn(Optional.of(existingNode));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nodeService.create(createDto);
        });
        assertTrue(exception.getMessage().contains("already exists"));
    }


    @Test
    void testDelete_Success() {
        Long id = 5L;
        when(nodeRepository.existsById(id)).thenReturn(true);

        nodeService.delete(id);

        verify(nodeRepository).deleteById(id);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 6L;
        when(nodeRepository.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nodeService.delete(id);
        });

        assertTrue(exception.getMessage().contains("Node not found"));
    }
}
