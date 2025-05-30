package com.districtnet.entity;

import com.districtnet.Enum.Auth_type;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "node")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="node_id")
    private Long nodeId;

    @Column(name="hostname",nullable = false, unique = true)
    private String hostname;

    @Column(name="user_name",nullable = false)
    private String userName;

    @Column(name="type_auth",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Auth_type typeAuth;

    @Column(name="auth_key",nullable = false)
    private String authKey;

    @Column(name = "ip_address",nullable = false)
    private String ipAddress;

    @Column(name ="os",nullable = false)
    private String os;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "node_res",
            joinColumns = @JoinColumn(name="node_id")
    )
    @Column(name = "res")
    private Set<String> resources;

    @OneToOne(mappedBy = "node", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private NodeResourceInfo resourceInfo;

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
        this.resources = tags;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Auth_type getTypeAuth() {
        return typeAuth;
    }

    public void setTypeAuth(Auth_type typeAuth) {
        this.typeAuth = typeAuth;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Set<String> getResources() {
        return resources;
    }

    public void setResources(Set<String> resources) {
        this.resources = resources;
    }

    public NodeResourceInfo getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(NodeResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
        if (resourceInfo != null) {
            resourceInfo.setNode(this);
        }
    }
}
