package com.districtnet.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "node")
public class Node {
    @Id
    @Column(name="mode_id",nullable = false,unique = true)
    private Long nodeId;

    @Column(name="hostname",nullable = false)
    private String hostname;

    @Column(name = "ip_address",nullable = false)
    private String ipAddress;

    @Column(name ="os",nullable = false)
    private String os;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(
        name = "node_tags",
        joinColumns = @JoinColumn(name="node_id")
    )
    @Column(name = "tag")
    private Set <String> tags;

    @Column(name = "regesteredAt",nullable = false)
    private Instant registeredAt;

    @Column(name ="last_seen_at")
    private Instant lastSeenAt;

    public Node(){};

    public Node(Long nodeId, String hostname, String ipAddress, String os, String description, Set<String> tags) {
        this.nodeId = nodeId;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.os = os;
        this.description = description;
        this.tags = tags;
        this.registeredAt = Instant.now();
        this.lastSeenAt = Instant.now();
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Instant getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(Instant lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }
    
}
