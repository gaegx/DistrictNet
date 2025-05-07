package com.districtnet.model;

import com.districtnet.Enum.Auth_type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class NodeCreateDto {

    private Long nodeId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String hostname;

    @NotBlank
    @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address format")
    private String ipAddress;

    @NotBlank
    private String os;

    private String description;

    private Set<String> resource;

    @NotBlank
    @Size(min = 4, max = 255)
    private String userName;

    @NotBlank
    private String authKey;

    private Auth_type typeAuth;

    // Getters and setters

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

    public Set<String> getResource() {
        return resource;
    }

    public void setResource(Set<String> resource) {
        this.resource = resource;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Auth_type getTypeAuth() {
        return typeAuth;
    }

    public void setTypeAuth(Auth_type typeAuth) {
        this.typeAuth = typeAuth;
    }
}
