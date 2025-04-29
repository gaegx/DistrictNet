package com.districtnet.controller;

import com.districtnet.dto.NodeCreateDto;
import com.districtnet.dto.NodeDisplayDto;
import com.districtnet.dto.NodeViewDto;
import com.districtnet.service.NodeService;
import com.districtnet.mapper.NodeMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NodeController.class)
class NodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NodeService nodeService;

    @MockBean
    private NodeMapper nodeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateNode() throws Exception {
        NodeCreateDto input = new NodeCreateDto();
        input.setNodeId(1L);
        input.setHostname("node1");
        input.setIpAddress("192.168.0.1");
        input.setOs("Linux");
        input.setTags(Set.of("web", "db"));

        NodeDisplayDto result = new NodeDisplayDto();
        result.setNodeId("1");
        result.setHostname("node1");
        result.setIpAddress("192.168.0.1");
        result.setOs("Linux");
        result.setTags(Set.of("web", "db"));
        result.setRegisteredAt(Instant.now());
        result.setLastSeen(Instant.now());

        when(nodeService.create(input)).thenReturn(result);

        mockMvc.perform(post("/api/v1/nodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hostname").value("node1"))
                .andExpect(jsonPath("$.ipAddress").value("192.168.0.1"));
    }

    @Test
    void testGetNodeById() throws Exception {
        NodeDisplayDto node = new NodeDisplayDto();
        node.setNodeId("1");
        node.setHostname("node1");
        node.setIpAddress("192.168.0.1");
        node.setOs("Linux");
        node.setTags(Set.of("web"));
        node.setRegisteredAt(Instant.now());
        node.setLastSeen(Instant.now());

        when(nodeService.getById(1L)).thenReturn(node);

        mockMvc.perform(get("/api/v1/nodes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hostname").value("node1"));
    }

    @Test
    void testDeleteNode() throws Exception {
        doNothing().when(nodeService).delete(1L);

        mockMvc.perform(delete("/api/v1/nodes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllNodes() throws Exception {
        NodeViewDto node = new NodeViewDto();
        node.setNodeId("1");
        node.setHostname("node1");
        node.setStatus("online");

        when(nodeService.getAll()).thenReturn(List.of(node));

        mockMvc.perform(get("/api/v1/nodes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].hostname").value("node1"));
    }
}
