package com.districtnet.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "node")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="node_id")
    private Long nodeId;

    @Column(name="hostname", nullable = false, unique = true)
    private String hostname;

    @Column(name="ip_address", nullable = false)
    private String ipAddress;

    @Column(name="os", nullable = false)
    private String os;

    @Column(name="description")
    private String description;


    @Column(name = "cpu")
    private float cpu;

    @Column(name = "ram")
    private float ram;

    @Column(name = "disk")
    private float disk;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt;

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    public Node() {}

    public Node(Long nodeId, String hostname, String ipAddress, String os, String description,
                float cpu, float ram, float disk) {
        this.nodeId = nodeId;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.os = os;
        this.description = description;
        this.cpu = cpu;
        this.ram = ram;
        this.disk = disk;
        this.registeredAt = Instant.now();
        this.lastSeenAt = Instant.now();
    }


    public Long getNodeId() { return nodeId; }
    public void setNodeId(Long nodeId) { this.nodeId = nodeId; }

    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getCpu() { return cpu; }
    public void setCpu(float cpu) { this.cpu = cpu; }

    public float getRam() { return ram; }
    public void setRam(float ram) { this.ram = ram; }

    public float getDisk() { return disk; }
    public void setDisk(float disk) { this.disk = disk; }

    public Instant getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }

    public Instant getLastSeenAt() { return lastSeenAt; }
    public void setLastSeenAt(Instant lastSeenAt) { this.lastSeenAt = lastSeenAt; }
}
