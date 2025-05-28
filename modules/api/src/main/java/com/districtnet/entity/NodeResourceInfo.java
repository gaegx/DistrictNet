package com.districtnet.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "node_resource_info")
public class NodeResourceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpu")
    private float cpu;

    @Column(name = "ram")
    private float ram;

    @Column(name = "disk")
    private float disk;

    @OneToOne
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

    public NodeResourceInfo() {}

    public NodeResourceInfo(float cpu, float ram, float disk, Node node) {
        this.cpu = cpu;
        this.ram = ram;
        this.disk = disk;
        this.node = node;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public float getCpu() { return cpu; }
    public void setCpu(float cpu) { this.cpu = cpu; }

    public float getRam() { return ram; }
    public void setRam(float ram) { this.ram = ram; }

    public float getDisk() { return disk; }
    public void setDisk(float disk) { this.disk = disk; }

    public Node getNode() { return node; }
    public void setNode(Node node) { this.node = node; }
}
